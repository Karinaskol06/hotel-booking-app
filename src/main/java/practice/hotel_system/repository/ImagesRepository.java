package practice.hotel_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.hotel_system.entity.Images;

public interface ImagesRepository extends JpaRepository<Images, Long> {
}
