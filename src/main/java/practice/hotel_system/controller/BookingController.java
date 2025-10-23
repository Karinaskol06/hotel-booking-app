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
import practice.hotel_system.bl.discount_strategy.PriceCalcResult;
import practice.hotel_system.entity.*;
import practice.hotel_system.service.*;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class BookingController {

    private final BookingService bookingService;
    private final ClientService clientService;
    private final UserService userService;
    private final ApartmentHasBookingService apartmentHasBookingService;
    private final DiscountService discountService;

    private final ApartmentService apartmentService;
    private final ApartmentClassService apartmentClassService;

    public BookingController(BookingService bookingService, ClientService clientService, UserService userService, DiscountService discountService,
                             ApartmentService apartmentService,
                             ApartmentHasBookingService apartmentHasBookingService,

                             ApartmentClassService apartmentClassService) {
        this.bookingService = bookingService;
        this.clientService = clientService;
        this.userService = userService;
        this.discountService = discountService;
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
        //calculating total price with discounts
        Map<String, PriceCalcResult> cartPriceMap = new HashMap<>();
        for (ItemCart item : cart.getCart()) {
            long nights = ChronoUnit.DAYS.between(item.getCheckin(), item.getCheckout());
            BigDecimal basePrice = item.getApartment().getPricePerNight()
                    .multiply(BigDecimal.valueOf(nights));

            Bookings tempBooking = new Bookings();
            tempBooking.setCheckIn(item.getCheckin());
            tempBooking.setCheckOut(item.getCheckout());

            PriceCalcResult priceCalc = discountService.calcFinalPrice(tempBooking, basePrice);
            cartPriceMap.put(item.getApartment().getId().toString(), priceCalc);
        }

        model.addAttribute("client", clientService.getClientById(userId));
        model.addAttribute("cart", cart.getCart());
        model.addAttribute("values", cart.getTotalValue());
        model.addAttribute("el", cart.getSumElements());
        model.addAttribute("nights", nightsList);
        model.addAttribute("apartments", apartmentService.findAll());
        model.addAttribute("apartment_classes", apartmentClassService.getAllApartmentClasses());
        model.addAttribute("cartPriceMap", cartPriceMap);

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
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.getCart() == null || cart.getCart().isEmpty()) {
            return "redirect:/";
        }

        //delegate business logic to service
        Bookings savedBooking = bookingService.createNewBooking(userId, payment, cart);
        //clearing the cart
        cart.deleteAllFromCart();
        session.setAttribute("cart", cart);

        redirectAttributes.addAttribute("bookingId", savedBooking.getId());
        return "redirect:/thank";
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
        BigDecimal originalTotalAmount = BigDecimal.ZERO;
        BigDecimal discountedTotalAmount = BigDecimal.ZERO;

        for (ApartmentHasBooking ahb : apartmentHasBookings) {
            long nights = ChronoUnit.DAYS.between(ahb.getCheckIn(), ahb.getCheckOut());
            Map<String, Object> map = new HashMap<>();
            map.put("ahb", ahb);
            map.put("nights", nights);

            BigDecimal originalPrice = ahb.getApartment().getPricePerNight()
                    .multiply(BigDecimal.valueOf(nights));
            originalTotalAmount = originalTotalAmount.add(originalPrice);

            discountedTotalAmount = discountedTotalAmount.add(ahb.getBooking().getInvoice().getTotalAmount());
            apartmentsWithNights.add(map);
        }

        model.addAttribute("apartmentsWithNights", apartmentsWithNights);
        if (originalTotalAmount.compareTo(discountedTotalAmount) > 0) {
            model.addAttribute("originalTotalAmount", originalTotalAmount);
        }

        return "thank";
    }

}
