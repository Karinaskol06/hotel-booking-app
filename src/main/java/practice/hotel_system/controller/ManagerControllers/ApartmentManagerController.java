package practice.hotel_system.controller.ManagerControllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import practice.hotel_system.entity.ApartmentClasses;
import practice.hotel_system.entity.Apartments;
import practice.hotel_system.service.ApartmentClassService;
import practice.hotel_system.service.ApartmentService;

@Controller
public class ApartmentManagerController {

    private final ApartmentService apartmentService;
    private final ApartmentClassService apartmentClassService;

    public ApartmentManagerController(ApartmentService apartmentService, ApartmentClassService apartmentClassService) {
        this.apartmentService = apartmentService;
        this.apartmentClassService = apartmentClassService;
    }

    @GetMapping("/manager/apartments")
    public String getApartmentsManagerPage(Model model) {
        model.addAttribute("apartments", apartmentService.findAll());
        model.addAttribute("apartment_classes", apartmentClassService.getAllApartmentClasses());
        return "manager_apartments";
    }

    @PostMapping("/saveNewApartment")
    public String saveNewApartment(@RequestParam(name = "name") String name,
                                   @RequestParam(name = "apartClass") ApartmentClasses apartClass,
                                   @RequestParam(name = "apartNumber") int apartmentNumber,
                                   @RequestParam(name = "capacity") int capacity,
                                   @RequestParam(name = "pricePerNight") double pricePerNight,
                                   @RequestParam(name = "image") String linkImg,
                                   @RequestParam(name = "area") double area,
                                   @RequestParam(name = "numOfRooms") int numOfRooms){

        Apartments apartment = new Apartments();

        apartment.setName(name);
        apartment.setApartmentClass(apartClass);
        apartment.setApartmentNumber(apartmentNumber);
        apartment.setCapacity(capacity);
        apartment.setPricePerNight(pricePerNight);
        apartment.setLinkImg(linkImg);
        apartment.setArea(area);
        apartment.setNumOfRooms(numOfRooms);

        apartmentService.save(apartment);

        return "redirect:/manager/apartments";
    }

    @PostMapping("/updateApartment")
    public String updateApartment(@RequestParam(name = "idUpd") Apartments apartment,
                                  @RequestParam(name = "name") String name,
                                  @RequestParam(name = "apartClass") Long apartClassId,
                                  @RequestParam(name = "apartNumber") int apartmentNumber,
                                  @RequestParam(name = "capacity") int capacity,
                                  @RequestParam(name = "pricePerNight") double pricePerNight,
                                  @RequestParam(name = "image") String linkImg,
                                  @RequestParam(name = "area") double area,
                                  @RequestParam(name = "numOfRooms") int numOfRooms) {
        apartment.setName(name);
        ApartmentClasses apartClass = apartmentClassService.getApartmentClassById(apartClassId);
        apartment.setApartmentClass(apartClass);
        apartment.setApartmentNumber(apartmentNumber);
        apartment.setCapacity(capacity);
        apartment.setPricePerNight(pricePerNight);
        apartment.setLinkImg(linkImg);
        apartment.setArea(area);
        apartment.setNumOfRooms(numOfRooms);

        apartmentService.update(apartment);

        return "redirect:/manager/apartments";
    }

    @PostMapping("/deleteApartment")
    public String deleteApartment(@RequestParam(name = "idDel") Apartments apartment) {

        apartmentService.deleteById(apartment.getId());

        return "redirect:/manager/apartments";
    }
}
