package practice.hotel_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.hotel_system.entity.Clients;

public interface ClientsRepository extends JpaRepository<Clients, Long>{
}
