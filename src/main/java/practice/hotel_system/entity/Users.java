package practice.hotel_system.entity;

import java.util.Collection;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "users")

public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 4, max = 50, message = "Значення username повинно бути в діапазоні від 2 до 50 знаків")
    private String username;
    @Size(min = 4, message = "Значення password повинно бути більшим за 4 знаки")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Roles> rolesSet; //Many to Many (one user can be manager, admin, buyer etc.)

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRolesSet();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    //for system to understand what fields are used as login and password
    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
