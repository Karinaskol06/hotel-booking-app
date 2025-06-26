package practice.hotel_system.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import practice.hotel_system.bl.Cart;
import practice.hotel_system.bl.ItemCart;
import practice.hotel_system.entity.*;
import practice.hotel_system.service.*;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class BookingController {

    private final BookingService bookingService;
    private final ClientService clientService;
    private final UserService userService;
    private final ApartmentHasBookingService apartmentHasBookingService;

    private final ApartmentService apartmentService;
    private final ApartmentClassService apartmentClassService;

    public BookingController(BookingService bookingService, ClientService clientService, UserService userService,
                             ApartmentService apartmentService,
                             ApartmentHasBookingService apartmentHasBookingService,

                             ApartmentClassService apartmentClassService) {
        this.bookingService = bookingService;
        this.clientService = clientService;
        this.userService = userService;
        this.apartmentHasBookingService = apartmentHasBookingService;
        this.apartmentService = apartmentService;
        this.apartmentClassService = apartmentClassService;
    }

    @GetMapping("/booking")
    public String getPageBooking(Model model, HttpServletRequest request) {
        //getting authentication from SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() ||
                "anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/login";
        }

        Long userId = userService.getUserIdByUsername(authentication.getName());
        if (userId == null) {
            return "redirect:/login";
        }

        HttpSession session = request.getSession();

        //storing user ID in session if it is not already there
        if (session.getAttribute("user") == null) {
            session.setAttribute("user", userId);
        }

        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        //if cart is empty, redirect to main
        if (cart.getCart() == null || cart.getCart().isEmpty()) {
            return "redirect:/";
        }

        //calculating nights for each booking item
        List<Long> nightsList = cart.getCart().stream()
                .map(item -> java.time.temporal.ChronoUnit.DAYS.between(
                        item.getCheckin(),
                        item.getCheckout())
                ).collect(Collectors.toList());

        model.addAttribute("client", clientService.getClientById(userId));
        model.addAttribute("cart", cart.getCart());
        model.addAttribute("values", cart.getTotalValue());
        model.addAttribute("el", cart.getSumElements());
        model.addAttribute("nights", nightsList);
        model.addAttribute("apartments", apartmentService.findAll());
        model.addAttribute("apartment_classes", apartmentClassService.getAllApartmentClasses());

        return "booking";
    }

    @PostMapping("/booking")
    public String saveNewBookingToDB(
            @RequestParam(name = "payment") Payments payment,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes) {

        //getting authentication from SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() ||
                "anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/login";
        }

        Long userId = userService.getUserIdByUsername(authentication.getName());
        if (userId == null) {
            return "redirect:/login";
        }

        HttpSession session = request.getSession();

        //getting cart from session
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.getCart() == null || cart.getCart().isEmpty()) {
            return "redirect:/";
        }

        //create new booking
        Bookings booking = new Bookings();

        booking.setPayment(payment);
        booking.setDateCreated(new Date());
        //all bookings initially are not processed
        booking.setStatus(BookingStatus.valueOf("NOT_PROCESSED"));

        Clients client = clientService.getClientById(userId);
        booking.setClient(client);

        LocalDate checkin = cart.getCart().stream()
                .map(ItemCart::getCheckin)
                .min(LocalDate::compareTo)
                .orElse(null);

        LocalDate checkout = cart.getCart().stream()
                .map(ItemCart::getCheckout)
                .max(LocalDate::compareTo)
                .orElse(null);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //if checkin and checkout are not empty, they're formatted
        booking.setCheckIn(checkin != null ? checkin.format(formatter) : null);
        booking.setCheckOut(checkout != null ? checkout.format(formatter) : null);

        BigDecimal totalAmount = calculateTotalAmount(cart);
        Bookings savedBooking = bookingService.saveNewBooking(booking, totalAmount);

        for (ItemCart el : cart.getCart()) {
            apartmentHasBookingService.saveNewApartmentByBooking(el.getApartment(), savedBooking,
                    el.getCheckin(), el.getCheckout()
            );
        }
        //clearing the cart
        cart.deleteAllFromCart();
        session.setAttribute("cart", cart);

        redirectAttributes.addAttribute("bookingId", savedBooking.getId());

        return "redirect:/thank";
    }

    private BigDecimal calculateTotalAmount(Cart cart) {
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (ItemCart item : cart.getCart()) {
            long nights = java.time.temporal.ChronoUnit.DAYS.between(item.getCheckin(), item.getCheckout());
            double price = item.getApartment().getPricePerNight();
            BigDecimal pricePerNight = BigDecimal.valueOf(price);
            BigDecimal itemTotal = pricePerNight.multiply(BigDecimal.valueOf(nights));

            totalAmount = totalAmount.add(itemTotal);
        }

        return totalAmount;
    }



    @GetMapping("/thank")
    public String getThankPage(
            @RequestParam(name = "bookingId") Long bookingId,
            Model model) {

        //getting booking by id
        Bookings booking = bookingService.getBookingById(bookingId);
        model.addAttribute("booking", booking);
        if (booking.getInvoice() != null) {
            model.addAttribute("invoice", booking.getInvoice());
        }

        //list with apartments attached to the booking
        List<ApartmentHasBooking> apartmentHasBookings =
                apartmentHasBookingService.getApartmentHasBookingByBooking(booking);

        //calculating the number of nights for every single apart
        List<Map<String, Object>> apartmentsWithNights = new ArrayList<>();
        for (ApartmentHasBooking ahb : apartmentHasBookings) {
            long nights = ChronoUnit.DAYS.between(ahb.getCheckIn(), ahb.getCheckOut());
            Map<String, Object> map = new HashMap<>();
            map.put("ahb", ahb);
            map.put("nights", nights);
            apartmentsWithNights.add(map);
        }
        model.addAttribute("apartmentsWithNights", apartmentsWithNights);

        return "thank";
    }

}
