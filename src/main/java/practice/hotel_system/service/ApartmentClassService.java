package practice.hotel_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import practice.hotel_system.entity.ApartmentClasses;
import practice.hotel_system.repository.ApartmentClassRepository;

import java.util.List;
import java.util.Objects;

@Service
public class ApartmentClassService {

    private final ApartmentClassRepository apartmentClassRepository;
    private final CacheManager cacheManager;

    @Autowired
    public ApartmentClassService(ApartmentClassRepository apartmentClassRepository, CacheManager cacheManager) {
        this.apartmentClassRepository = apartmentClassRepository;
        this.cacheManager = cacheManager;
    }

    //dynamic key based on method parameter
    @Cacheable(cacheNames = "apartClassId", key = "#id", unless = "#result == null")
    public ApartmentClasses getApartmentClassById(Long id) {
        System.out.println("Fetching from database for id = " + id);
        return apartmentClassRepository.findById(id).orElse(null);
    }

    //cache put to update values
    @CachePut(cacheNames = "apartClassId", key = "#result.id")
    @CacheEvict(cacheNames = "apartClassStr", allEntries = true)
    public ApartmentClasses save(ApartmentClasses apartmentClass) {
        return apartmentClassRepository.save(apartmentClass);
    }

    @CachePut(cacheNames = "apartClassId", key = "#apartmentClass.id")
    @CacheEvict(cacheNames = "apartClassStr", allEntries = true)
    public ApartmentClasses update(ApartmentClasses apartmentClass) {
        return apartmentClassRepository.save(apartmentClass);
    }

    //deletes both caches, they are not relevant
    @CacheEvict(cacheNames = {"apartClassId", "apartClassStr"}, allEntries = true)
    public void delete(ApartmentClasses apartmentClass) {
        apartmentClassRepository.delete(apartmentClass);
    }

    @CacheEvict(cacheNames = "apartClassId", key = "#id")
    public void deleteApartClassById(Long id) {
        apartmentClassRepository.deleteById(id);
        //clear because object was removed
        Objects.requireNonNull(cacheManager.getCache("apartClassStr")).clear();
    }

    //static key, stores the same info
    @Cacheable(cacheNames = "apartClassStr", key = "'allClasses'")
    public List<ApartmentClasses> getAllApartmentClasses() {
        System.out.println("Fetching apartment classes from DB");
        return apartmentClassRepository.findAll();
    }

    public void clearAllCaches() {
        Objects.requireNonNull(cacheManager.getCache("apartClassId")).clear();
        Objects.requireNonNull(cacheManager.getCache("apartClassStr")).clear();
    }
}