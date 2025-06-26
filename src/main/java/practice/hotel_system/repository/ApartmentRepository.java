package practice.hotel_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import practice.hotel_system.entity.ApartmentClasses;
import practice.hotel_system.entity.Apartments;

import java.util.List;

public interface ApartmentRepository extends JpaRepository<Apartments, Long> {

    List<Apartments> findAllByApartmentClass(ApartmentClasses apartmentClass);

}
