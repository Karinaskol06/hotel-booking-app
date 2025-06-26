package practice.hotel_system.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import practice.hotel_system.bl.Cart;
import practice.hotel_system.bl.ItemCart;
import practice.hotel_system.entity.Apartments;
import practice.hotel_system.service.ApartmentClassService;
import practice.hotel_system.service.AvailabilityService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class CartController {

    //service for handling apartment-related operations
    private final ApartmentClassService apartmentClassService;
    private final AvailabilityService availabilityService;

    public CartController(ApartmentClassService apartmentClassService, AvailabilityService availabilityService) {
        this.apartmentClassService = apartmentClassService;
        this.availabilityService = availabilityService;
    }

    //maps "get" request to cart page
    @GetMapping("/cart")
    //model is used to pass data to the view
    //HttpServletRequest represents HTTP request from the browser
    public String getPageCart(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) cart = new Cart();

        List<Long> nightsList = cart.getCart().stream()
                .map(item -> java.time.temporal.ChronoUnit.DAYS.between(item.getCheckin(), item.getCheckout()))
                .collect(Collectors.toList());

        //passing data to the view
        model.addAttribute("cart", cart.getCart()); //list of cart items
        model.addAttribute("totalPrice", cart.getTotalValue());
        model.addAttribute("sumEl", cart.getSumElements());
        model.addAttribute("nights", nightsList);
        model.addAttribute("apartment_classes", apartmentClassService.getAllApartmentClasses());

        return "cart";
    }

    //maps "post" request to addToCart page
    @PostMapping("/addToCart")
    public String addToCart(@RequestParam(name = "id") Apartments apartment,
                            @RequestParam(name = "checkin") String checkinStr,
                            @RequestParam(name = "checkout") String checkoutStr,
                            HttpServletRequest request, //accessing the session
                            RedirectAttributes redirectAttributes
    ){
        LocalDate checkin = LocalDate.parse(checkinStr);
        LocalDate checkout = LocalDate.parse(checkoutStr);
        LocalDate today = LocalDate.now();

        //check if checkin or checkout is in the past
        if (checkin.isBefore(today) || checkout.isBefore(today)) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Виберіть актуальні дати.");
            return "redirect:/apartment_class/" + apartment.getApartmentClass().getId();
        }

        //check if checkin is after or equals checkout
        if (checkin.isAfter(checkout) || checkin.isEqual(checkout)) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Дата виїзду не може бути пізнішою або рівною даті заїзду.");
            return "redirect:/apartment_class/" + apartment.getApartmentClass().getId();
        }

        //check if apartment is free for chosen dates
        if (!availabilityService.isApartmentAvailable(apartment.getId(), checkin, checkout)) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Цей апартамент вже заброньований на вибрані дати.");
            return "redirect:/apartment_class/" + apartment.getApartmentClass().getId();
        }

        //if dates are valid, proceed with adding to cart
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) cart = new Cart();

        cart.addNewItemToCart(apartment, checkin, checkout);
        session.setAttribute("cart", cart);

        return "redirect:/cart";
    }

    @PostMapping("/updateNights")
    public String updateNights(@RequestParam(name = "id") Apartments apartment,
                               @RequestParam(name = "checkin") String checkinStr,
                               @RequestParam(name = "nights") int nights,
                               HttpServletRequest request,
                               RedirectAttributes redirectAttributes) {

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            return "redirect:/cart";
        }

        //find the cart item to update
        Optional<ItemCart> itemToUpdate = cart.getCart().stream()
                .filter(item -> item.getApartment().getId().equals(apartment.getId()))
                .findFirst();

        if (itemToUpdate.isPresent()) {
            ItemCart item = itemToUpdate.get();
            LocalDate checkin = LocalDate.parse(checkinStr);

            //calculating new checkout based on the number of nights
            LocalDate newCheckout = checkin.plusDays(nights);

            // Check if the apartment is available for the new dates
            if (!availabilityService.isApartmentAvailable(apartment.getId(), checkin, newCheckout)) {
                redirectAttributes.addFlashAttribute("errorMessage",
                        "Цей апартамент вже заброньований на вибрані дати.");
                return "redirect:/cart";
            }

            item.setCheckout(newCheckout);
            session.setAttribute("cart", cart);
        }
        return "redirect:/cart";
    }

    @PostMapping("/deleteItemFromCart")
    public String deleteItemFromCart(@RequestParam(name = "id") Apartments apartment,
                                     HttpServletRequest request
    ){
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null) cart = new Cart();

        cart.deleteItemFromCart(apartment);
        session.setAttribute("cart", cart);

        return "redirect:/cart";
    }

    @PostMapping("/deleteAllFromCart")
    public String deleteAllFromCart(HttpServletRequest request){
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart != null) {
            cart.deleteAllFromCart();
            session.setAttribute("cart", cart);
        }

        return "redirect:/";
    }

}
