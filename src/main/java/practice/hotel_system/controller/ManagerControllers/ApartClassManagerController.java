package practice.hotel_system.controller.ManagerControllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import practice.hotel_system.entity.ApartmentClasses;
import practice.hotel_system.service.ApartmentClassService;

@Controller
public class ApartClassManagerController {
    private final ApartmentClassService apartClassService;

    public ApartClassManagerController(ApartmentClassService apartClassService) {
        this.apartClassService = apartClassService;
    }

    @GetMapping("/manager/apartment_classes")
    public String getApartmentClasses(Model model) {
        model.addAttribute("apartment_classes", apartClassService.getAllApartmentClasses());
        return "manager_apartClasses";
    }

    @PostMapping("/saveNewApartmentClass")
    public String saveNewApartmentClass(@RequestParam(name = "name") String name,
                                        @RequestParam(name = "description") String description,
                                        @RequestParam(name = "facilities") String facilities,
                                        @RequestParam(name = "image") String image){
        ApartmentClasses apartmentClass = new ApartmentClasses();
        apartmentClass.setApartmentClass(name);
        apartmentClass.setClassDescription(description);
        apartmentClass.setFacilities(facilities);
        apartmentClass.setLinkImg(image);

        apartClassService.save(apartmentClass);

        return "redirect:/manager/apartment_classes";
    }

    @PostMapping("/deleteApartClassFromList")
    public String deleteApartClassFromList(@RequestParam(name = "idDel") Long id) {
        apartClassService.deleteApartClassById(id);
        return "redirect:/manager/apartment_classes";
    }


    @PostMapping("/updateApartClass")
    public String updateApartClass(@RequestParam(name = "idUpd") Long id,
                                   @RequestParam(name = "name") String name,
                                   @RequestParam(name = "description") String description,
                                   @RequestParam(name = "facilities") String facilities,
                                   @RequestParam(name = "image") String image) {

        ApartmentClasses apartClass = apartClassService.getApartmentClassById(id);
        if (apartClass != null) {
            apartClass.setApartmentClass(name);
            apartClass.setClassDescription(description);
            apartClass.setFacilities(facilities);
            apartClass.setLinkImg(image);
            apartClassService.update(apartClass);
        }

        return "redirect:/manager/apartment_classes";
    }


}
