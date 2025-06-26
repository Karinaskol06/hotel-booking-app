package practice.hotel_system.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import practice.hotel_system.entity.ApartmentHasBooking;
import practice.hotel_system.entity.Apartments;
import practice.hotel_system.entity.Bookings;
import practice.hotel_system.repository.ApartmentHasBookingRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class ApartmentHasBookingService {
    private final ApartmentHasBookingRepository apartmentHasBookingRepository;

    public ApartmentHasBookingService(ApartmentHasBookingRepository apartmentHasBookingRepository) {
        this.apartmentHasBookingRepository = apartmentHasBookingRepository;
    }

    @Transactional
    public ApartmentHasBooking saveNewApartmentByBooking(Apartments apartment, Bookings booking,
                                                         LocalDate checkIn, LocalDate checkOut) {
        ApartmentHasBooking apartmentHasBooking = new ApartmentHasBooking(apartment, booking, checkIn, checkOut);
        return apartmentHasBookingRepository.save(apartmentHasBooking);
    }

    @Transactional
    public ApartmentHasBooking saveNewApartmentByBooking(Apartments apartment, Bookings booking) {
        ApartmentHasBooking apartmentHasBooking = new ApartmentHasBooking(apartment, booking);
        return apartmentHasBookingRepository.save(apartmentHasBooking);
    }

    public List<ApartmentHasBooking> getApartmentHasBookingByBooking(Bookings booking) {
        return apartmentHasBookingRepository.findAllAHBByBooking(booking);
    }
}