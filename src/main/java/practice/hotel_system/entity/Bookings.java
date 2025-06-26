package practice.hotel_system.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "bookings")

public class Bookings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateCreated;
    private String checkIn;
    private String checkOut;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @ManyToOne()
    @JoinColumn(name = "client_id") //FK
    private Clients client; //link with client Many to One

    @OneToOne()
    @JoinColumn(name = "invoice_id") //FK
    private Invoices invoice; //One to One

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payments payment;

    //Many to Many
    @OneToMany(mappedBy = "booking")
    private List<ApartmentHasBooking> apartmentHasBookings;

    public Bookings(Payments payment, Date dateCreated, Clients client,
                    String checkIn, String checkOut) {
        this.payment = payment;
        this.dateCreated = dateCreated;
        this.client = client;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }
}
