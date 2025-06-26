package practice.hotel_system.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "roles")

public class Roles implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roleName;

    @ManyToMany(mappedBy = "rolesSet") //bidirectional relationship
    private Set<Users> usersSet; //Many to Many

    public Roles(Long id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "Roles [id=" + id + ", roleName=" + roleName + "]";
    }

    @Override
    public String getAuthority() {
        return roleName;
    }
}
