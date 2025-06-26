package practice.hotel_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.hotel_system.entity.ApartmentClasses;

public interface ApartmentClassRepository extends JpaRepository<ApartmentClasses, Long> {
}
