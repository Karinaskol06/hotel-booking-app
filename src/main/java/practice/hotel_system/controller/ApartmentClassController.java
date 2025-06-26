package practice.hotel_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import practice.hotel_system.entity.ApartmentClasses;
import practice.hotel_system.entity.Apartments;
import practice.hotel_system.service.ApartmentClassService;
import practice.hotel_system.service.ApartmentService;

import java.util.List;

@Controller
public class ApartmentClassController {
    private final ApartmentClassService apartmentClassService;
    private final ApartmentService apartmentService;

    public ApartmentClassController(ApartmentClassService apartmentClassService, ApartmentService apartmentService) {
        this.apartmentClassService = apartmentClassService;
        this.apartmentService = apartmentService;
    }

    //loads all apartment classes and apartments
    @GetMapping({"/", "/apartment_classes"})
    public String getHomePage(Model model) {

        model.addAttribute("apartment_classes", apartmentClassService.getAllApartmentClasses());
        model.addAttribute("apartments", apartmentService.findAll());
        model.addAttribute("showCarousel", true);
        return "index1";
    }

    @GetMapping("/booking/class")
    public String getBooking(Model model) {
        model.addAttribute("apartment_classes", apartmentClassService.getAllApartmentClasses());
        model.addAttribute("apartments", apartmentService.findAll());
        return "booking";
    }

    @GetMapping("/about_us")
    public String getAboutUs(Model model) {
        model.addAttribute("apartment_classes", apartmentClassService.getAllApartmentClasses());
        model.addAttribute("apartments", apartmentService.findAll());
        return "about_us";
    }

    @GetMapping("/contacts")
    public String getContacts(Model model) {
        model.addAttribute("apartment_classes", apartmentClassService.getAllApartmentClasses());
        model.addAttribute("apartments", apartmentService.findAll());
        return "contacts";
    }

    @GetMapping("/payment")
    public String getPayment(Model model) {
        model.addAttribute("apartment_classes", apartmentClassService.getAllApartmentClasses());
        model.addAttribute("apartments", apartmentService.findAll());
        return "payment";
    }

    //written for lab3 OOP (adding new aparts)
    @GetMapping("/test-page")
    public String getTestPage(Model model) {
        List<ApartmentClasses> classes = apartmentClassService.getAllApartmentClasses();
        List<Apartments> apartments = apartmentService.findAll();

        model.addAttribute("apartmentClasses", classes);
        model.addAttribute("apartments", apartments);

        return "test-page";
    }

}
