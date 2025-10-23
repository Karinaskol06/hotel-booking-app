package practice.hotel_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "apartments")
public class Apartments implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //AI ID

    private String name;
    private int apartmentNumber;
    private int capacity;
    private BigDecimal pricePerNight;

    private String linkImg; //preview image

    @OneToMany(mappedBy = "apartmentImages", fetch = FetchType.EAGER)
    private List<Images> apartmentImages; //multiple images

    @ManyToOne()
    @JoinColumn(name = "apartment_class_id")
    private ApartmentClasses apartmentClass; //link with class

    //many to many
    @OneToMany(mappedBy = "apartment")
    private List<ApartmentHasBooking> apartmentHasBookings;

    private double area;
    private int numOfRooms;

}
