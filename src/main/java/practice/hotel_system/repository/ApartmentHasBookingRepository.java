package practice.hotel_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.hotel_system.entity.ApartmentHasBooking;
import practice.hotel_system.entity.Bookings;

import java.util.List;

public interface ApartmentHasBookingRepository extends JpaRepository<ApartmentHasBooking, Long> {
    List<ApartmentHasBooking> findAllAHBByBooking(Bookings booking);

}
