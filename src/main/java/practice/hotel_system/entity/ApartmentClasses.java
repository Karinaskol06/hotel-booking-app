package practice.hotel_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "apartment_classes")
public class ApartmentClasses implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 70) //limit amount of symbols
    private String apartmentClass;
    private String facilities;
    @Column(length = 200)
    private String classDescription;
    private String linkImg;


    @OneToMany(mappedBy = "apartmentClass")
    private List<Apartments> apartments; //One to Many

}
