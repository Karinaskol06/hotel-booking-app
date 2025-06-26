package practice.hotel_system.entity;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "clients")

public class Clients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60, name = "first_name")
    private String firstName;
    @Column(nullable = false, length = 60, name = "last_name")
    private String lastName;
    private String email;
    private String phone;

    @OneToMany(mappedBy = "client")
    private List<Bookings> bookings; //link with booking One to Many

    @OneToOne
    @MapKey
    @MapsId
    @JoinColumn(name = "id")
    private Users user; //One to One
}
