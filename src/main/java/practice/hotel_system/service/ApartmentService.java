package practice.hotel_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import practice.hotel_system.entity.ApartmentClasses;
import practice.hotel_system.entity.Apartments;
import practice.hotel_system.repository.ApartmentClassRepository;
import practice.hotel_system.repository.ApartmentRepository;

import java.util.List;

@Service
public class ApartmentService {

    private final ApartmentRepository apartmentsRepository;
    private final BookingService bookingService;
    private final AvailabilityService availabilityService;
    //provides direct access to all caches (useful for delete)
    private final CacheManager cacheManager;
    private final ApartmentClassRepository apartmentClassRepository;

    @Autowired
    public ApartmentService(ApartmentRepository apartmentRepository, BookingService bookingService,
                            AvailabilityService availabilityService, CacheManager cacheManager,
                            ApartmentClassRepository apartmentClassRepository) {
        this.apartmentsRepository = apartmentRepository;
        this.bookingService = bookingService;
        this.availabilityService = availabilityService;
        this.cacheManager = cacheManager;
        this.apartmentClassRepository = apartmentClassRepository;
    }

    @CachePut(cacheNames = "apartmentId", key = "#result.id")
    @CacheEvict(cacheNames = "apartmentStr", allEntries = true)
    public Apartments save(Apartments apartment) {
        return apartmentsRepository.save(apartment);
    }

    @CachePut(cacheNames = "apartmentId", key = "#apartment.id")
    @CacheEvict(cacheNames = "apartmentStr", allEntries = true)
    public Apartments update(Apartments apartment) {
        return apartmentsRepository.save(apartment);
    }

    @CacheEvict(cacheNames = "apartmentId", key = "#id")
    public void deleteById(Long id) {
        apartmentsRepository.deleteById(id);
        //clear list, because item was removed
        if (cacheManager.getCache("apartmentStr") != null) {
            cacheManager.getCache("apartmentStr").clear();
        }
    }

    @CacheEvict(cacheNames = {"apartmentId", "apartmentStr"}, allEntries = true)
    public void deleteAll() {
        apartmentsRepository.deleteAll();
    }

    @CacheEvict(cacheNames = {"apartmentId", "apartmentStr"}, allEntries = true)
    public void deleteByApartment(Apartments apartment) {
        apartmentsRepository.delete(apartment);
    }

    @Cacheable(cacheNames = "apartmentId", key = "#id", unless = "#result == null")
    public Apartments findById(Long id) {
        System.out.println("Fetching apartment from DB for id = " + id);
        return apartmentsRepository.findById(id).orElse(null);
    }

    @Cacheable(cacheNames = "apartmentStr", key = "'allApartments'")
    public List<Apartments> findAll() {
        System.out.println("Fetching all apartments from DB");
        return apartmentsRepository.findAll();
    }

    public List<Apartments> getApartmentsByApartmentClass(ApartmentClasses apartmentClass) {
        return apartmentsRepository.findAllByApartmentClass(apartmentClass);
    }

    public ApartmentClasses getApartmentClassById(Long id) {
        return apartmentClassRepository.findById(id).orElse(null);
    }

}