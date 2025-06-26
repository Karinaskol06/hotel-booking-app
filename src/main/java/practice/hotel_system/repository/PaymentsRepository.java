package practice.hotel_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.hotel_system.entity.Payments;

public interface PaymentsRepository extends JpaRepository<Payments, Long> {
}
