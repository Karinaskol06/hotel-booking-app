package practice.hotel_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import practice.hotel_system.entity.BookingStatus;
import practice.hotel_system.entity.Bookings;

import java.util.List;

public interface BookingsRepository extends JpaRepository<Bookings, Long> {
    //List<Bookings> findBookingsForApartment(Long apartmentId);

    List<Bookings> findByApartmentHasBookings_Apartment_Id(Long apartmentId);
    List<Bookings> findByStatus(BookingStatus status);
    List<Bookings> findByClientId(Long clientId);

    //sorting bookings so that unprocessed would be above others
    //gives priority to status "not processed" and sorts in descending order
    @Query("SELECT b FROM Bookings b ORDER BY CASE WHEN b.status = practice.hotel_system.entity.BookingStatus.NOT_PROCESSED THEN 0 ELSE 1 END, b.dateCreated DESC")
    List<Bookings> findAllSorted();


}
