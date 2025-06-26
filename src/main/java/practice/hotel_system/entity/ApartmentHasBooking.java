package practice.hotel_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "apartment_has_booking")

//for saving info abt chosen apartments
public class ApartmentHasBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "apartment_id")
    private Apartments apartment;

    @ManyToOne()
    @JoinColumn(name = "booking_id")
    private Bookings booking;

    @Column(name = "check_in")
    private LocalDate checkIn;

    @Column(name = "check_out")
    private LocalDate checkOut;

    public ApartmentHasBooking(Apartments apartment, Bookings booking) {
        this.apartment = apartment;
        this.booking = booking;
    }

    public ApartmentHasBooking(Apartments apartment, Bookings booking, LocalDate checkIn, LocalDate checkOut) {
        this.apartment = apartment;
        this.booking = booking;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

}
