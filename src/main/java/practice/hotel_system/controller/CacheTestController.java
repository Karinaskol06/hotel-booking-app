package practice.hotel_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import practice.hotel_system.service.ApartmentClassService;

@RestController
@RequestMapping("/test")
public class CacheTestController {

    @Autowired
    private ApartmentClassService apartmentClassService;

    @GetMapping("/test-cache")
    @ResponseBody
    public String testCache() {
        // Викликаємо метод двічі з однаковим id для перевірки кешу
        apartmentClassService.getApartmentClassById(1L);
        apartmentClassService.getApartmentClassById(1L);

        // Повертаємо підтвердження тесту
        return "Cache tested!";
    }
}
