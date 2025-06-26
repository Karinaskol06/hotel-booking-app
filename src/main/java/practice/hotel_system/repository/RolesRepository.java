package practice.hotel_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.hotel_system.entity.Roles;

public interface RolesRepository extends JpaRepository<Roles, Long> {
}
