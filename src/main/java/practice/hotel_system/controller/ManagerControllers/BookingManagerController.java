package practice.hotel_system.controller.ManagerControllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import practice.hotel_system.entity.ApartmentHasBooking;
import practice.hotel_system.entity.BookingStatus;
import practice.hotel_system.entity.Bookings;
import practice.hotel_system.service.ApartmentHasBookingService;
import practice.hotel_system.service.BookingService;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BookingManagerController {

    private final BookingService bookingService;
    private final ApartmentHasBookingService apartmentHasBookingService;

    public BookingManagerController(BookingService bookingService, ApartmentHasBookingService apartmentHasBookingService) {
        this.bookingService = bookingService;
        this.apartmentHasBookingService = apartmentHasBookingService;
    }

    @GetMapping("/manager/bookings")
    public String getManagerBookingPage(Model model) {
        model.addAttribute("bookings", bookingService.getSortedBookings());
        model.addAttribute("statuses", BookingStatus.values());

        return "manager/pages/manager_booking";
    }

    @GetMapping("/manager/bookings/{id}")
    public String getViewBookingPage(@PathVariable(name = "id") Bookings booking,
                                     Model model) {
        List<ApartmentHasBooking> apartmentHasBookingList = apartmentHasBookingService
                .getApartmentHasBookingByBooking(booking);

        BigDecimal totalValue = BigDecimal.ZERO;
        long nights = 0;

        List<Map<String, Object>> apartmentsWithNights = new ArrayList<>();
        for (ApartmentHasBooking ahb : apartmentHasBookingList) {
            nights = ChronoUnit.DAYS.between(ahb.getCheckIn(), ahb.getCheckOut());
            Map<String, Object> map = new HashMap<>();
            map.put("ahb", ahb);
            map.put("nights", nights);
            apartmentsWithNights.add(map);

            BigDecimal pricePerNight = ahb.getApartment().getPricePerNight();
            BigDecimal bookingTotal = pricePerNight.multiply(BigDecimal.valueOf(nights));

            totalValue = totalValue.add(bookingTotal);
        }

        model.addAttribute("statuses", BookingStatus.values());
        model.addAttribute("booking", booking);
        model.addAttribute("totalValue", totalValue);
        model.addAttribute("nights", nights);
        model.addAttribute("apartmentsWithNights", apartmentsWithNights);

        return "manager/pages/view_booking";
    }

    @PostMapping("/manager/bookings/{id}/status")
    public String updateBookingStatus(@PathVariable Long id,
                                      @RequestParam("status")
                                      BookingStatus status) {
        bookingService.updateBookingStatus(id, status);
        return "redirect:/manager/bookings/" + id;
    }

}
