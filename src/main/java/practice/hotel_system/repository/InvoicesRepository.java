package practice.hotel_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.hotel_system.entity.Invoices;

public interface InvoicesRepository extends JpaRepository<Invoices, Long> {
}
