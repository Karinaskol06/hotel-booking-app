package practice.hotel_system.service;

import org.springframework.stereotype.Service;
import practice.hotel_system.entity.ApartmentClasses;
import practice.hotel_system.entity.Apartments;
import practice.hotel_system.entity.BookingStatus;
import practice.hotel_system.entity.Bookings;
import practice.hotel_system.repository.ApartmentRepository;
import practice.hotel_system.repository.BookingsRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

@Service
public class AvailabilityService {
    private final BookingsRepository bookingsRepository;
    private final ApartmentRepository apartmentRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public AvailabilityService(BookingsRepository bookingsRepository, ApartmentRepository apartmentRepository) {
        this.bookingsRepository = bookingsRepository;
        this.apartmentRepository = apartmentRepository;
    }

    public boolean isApartmentAvailable(Long apartmentId, LocalDate checkin, LocalDate checkout) {
        List<Bookings> bookings = bookingsRepository.findByApartmentHasBookings_Apartment_Id(apartmentId);
        for (Bookings booking : bookings){
            //skipping cancelled bookings
            if (booking.getStatus().equals(BookingStatus.CANCELLED)) {
                continue;
            }

            //we compare dates which user passes with dates that already exist in the db
            LocalDate bookedCheckIn = LocalDate.parse(booking.getCheckIn(), formatter);
            LocalDate bookedCheckOut = LocalDate.parse(booking.getCheckOut(), formatter);

            if (!(checkout.isBefore(bookedCheckIn) || checkin.compareTo(bookedCheckOut) >= 0)) {
                return false;
            }
        }
        return true;
    }

    public List<Apartments> getAvailableApartments(LocalDate checkin, LocalDate checkout) {
        List<Apartments> allApartments = apartmentRepository.findAll();

        //for each apartment in the list method isAvailable is called
        //predicate return true or false
        Predicate<Apartments> available = a -> isApartmentAvailable(a.getId(), checkin, checkout);
        //filtering apartments and collecting to list
        return allApartments.stream()
                .filter(available)
                .collect(toList());
    }

    public List<Apartments> getAvailableApartmentsByClass(ApartmentClasses apartmentClass,
                                                          LocalDate checkin, LocalDate checkout) {
        //same method as before, but for each class
         List<Apartments> apartmentsByClass = apartmentRepository.findAllByApartmentClass(apartmentClass);
         Predicate<Apartments> available = a -> isApartmentAvailable(a.getId(), checkin, checkout);
         return apartmentsByClass.stream()
                 .filter(available)
                 .collect(toList());
    }
}
