package practice.hotel_system.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import practice.hotel_system.bl.discount_strategy.PriceCalcResult;
import practice.hotel_system.entity.ApartmentClasses;
import practice.hotel_system.entity.Apartments;
import practice.hotel_system.entity.Bookings;
import practice.hotel_system.service.ApartmentClassService;
import practice.hotel_system.service.ApartmentService;
import practice.hotel_system.service.AvailabilityService;
import practice.hotel_system.service.DiscountService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ApartmentController {
    private final ApartmentService apartmentService;
    private final ApartmentClassService apartmentClassService;
    private final AvailabilityService availabilityService;
    private final DiscountService discountService;

    public ApartmentController(ApartmentService apartmentService, ApartmentClassService apartmentClassService, AvailabilityService availabilityService, DiscountService discountService) {
        this.apartmentService = apartmentService;
        this.apartmentClassService = apartmentClassService;
        this.availabilityService = availabilityService;
        this.discountService = discountService;
    }

    //getting apartments by apartment class
    @GetMapping("/apartment_class/{id}")
    public String getApartmentsByClass(
            @PathVariable(name = "id") Long classId,
            @RequestParam(required = false) String checkin,
            @RequestParam(required = false) String checkout,
            HttpServletRequest request,
            Model model) {

        //storing dates (for checking availability) in session
        HttpSession session = request.getSession();
        if (checkin != null && checkout != null) {
            session.setAttribute("checkin", checkin);
            session.setAttribute("checkout", checkout);
        } else {
            checkin = (String) session.getAttribute("checkin");
            checkout = (String) session.getAttribute("checkout");
        }

        ApartmentClasses apartmentClass = apartmentService.getApartmentClassById(classId);

        if (checkin == null || checkout == null || checkin.isEmpty() || checkout.isEmpty()) {
            model.addAttribute("warningMessage",
                    "Please select your stay dates to view available apartments.");
            return "apartmentsByApartmentClass";
        }

        LocalDate checkinLoc = LocalDate.parse(checkin);
        LocalDate checkoutLoc = LocalDate.parse(checkout);
        LocalDate today = LocalDate.now();

        if (checkinLoc.isBefore(today) || checkoutLoc.isBefore(today)) {
            model.addAttribute("errorMessage",
                    "Please select valid dates");
            return "apartmentsByApartmentClass";
        }

        if (checkinLoc.isAfter(checkoutLoc) || checkinLoc.isEqual(checkoutLoc)) {
            model.addAttribute("errorMessage",
                    "Check-out date must be later than the check-in date");
            return "apartmentsByApartmentClass";
        }

        model.addAttribute("apartments", List.of());
        model.addAttribute("apartment_classes",
                apartmentClassService.getAllApartmentClasses());

        List<Apartments> apartmentsList = availabilityService
                .getAvailableApartmentsByClass(apartmentClass, checkinLoc, checkoutLoc);
        Map<String, PriceCalcResult> priceMap = new HashMap<>();

        for (Apartments apartment : apartmentsList) {
            BigDecimal basePrice = apartment.getPricePerNight()
                    .multiply(BigDecimal.valueOf(ChronoUnit.DAYS.between(checkinLoc, checkoutLoc)));
            Bookings tempBooking = new Bookings();
            tempBooking.setCheckIn(checkinLoc);
            tempBooking.setCheckOut(checkoutLoc);

            PriceCalcResult priceCalc = discountService.calcFinalPrice(tempBooking, basePrice);
            priceMap.put(apartment.getId().toString(), priceCalc);
        }

        if (apartmentsList.isEmpty()) {
            model.addAttribute("infoMessage",
                    "All apartments of this class are fully booked for the selected dates");
        }

        model.addAttribute("priceMap", priceMap);
        model.addAttribute("checkin", checkin);
        model.addAttribute("checkout", checkout);
        model.addAttribute("apartments", apartmentsList);
        model.addAttribute("apartment_classes", apartmentClassService
                .getAllApartmentClasses());
        model.addAttribute("id", classId);

        return "apartmentsByApartmentClass";
    }

    @GetMapping("/apartmentsByApartmentClass")
    public String getApartments(
            @RequestParam("apartmentClass") Long apartmentClassId,
            @RequestParam(value = "checkin", required = false)
            LocalDate checkin,
            @RequestParam(value = "checkout", required = false)
            LocalDate checkout,
            HttpSession session,
            Model model) {

        if (checkin == null || checkout == null) {
            checkin = (LocalDate) session.getAttribute("checkin");
            checkout = (LocalDate) session.getAttribute("checkout");
        } else {
            session.setAttribute("checkin", checkin);
            session.setAttribute("checkout", checkout);
        }

        ApartmentClasses apartmentClass = apartmentService
                .getApartmentClassById(apartmentClassId);
        List<Apartments> availableApartments = availabilityService
                .getAvailableApartmentsByClass(apartmentClass, checkin, checkout);

        model.addAttribute("apartments", availableApartments);
        model.addAttribute("checkIn", checkin);
        model.addAttribute("checkOut", checkout);
        model.addAttribute("id", apartmentClassId);

        return "apartmentsByApartmentClass";
    }

    @PostMapping("/apartments/save")
    public String saveApartment(@RequestParam String name,
                                @RequestParam String number,
                                @RequestParam Long classId) {
        ApartmentClasses apartmentClass = apartmentClassService
                .getApartmentClassById(classId);
        Apartments apartment = new Apartments();
        apartment.setName(name);
        apartment.setApartmentNumber(Integer.parseInt(number));
        apartment.setApartmentClass(apartmentClass);
        apartmentService.save(apartment);
        return "redirect:/apartments";
    }

    @PostMapping("/apartments/update")
    public String updateApartment(@RequestParam Long id,
                                  @RequestParam String name,
                                  @RequestParam String number,
                                  @RequestParam Long classId) {
        Apartments apartment = apartmentService.findById(id);
        ApartmentClasses apartmentClass = apartmentClassService
                .getApartmentClassById(classId);

        apartment.setName(name);
        apartment.setApartmentNumber(Integer.parseInt(number));
        apartment.setApartmentClass(apartmentClass);

        apartmentService.update(apartment);
        return "redirect:/apartments";
    }

    @PostMapping("/apartments/delete/{id}")
    public String deleteApartment(@PathVariable Long id) {
        apartmentService.deleteById(id);
        return "redirect:/apartments";
    }

}
