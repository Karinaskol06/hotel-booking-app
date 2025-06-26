package practice.hotel_system.controller.ManagerControllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ManagerController {

    //when in browser localhost/manager
    @GetMapping("/manager")
    public String getManagerPage() {

        return "manager_main";
    }

    @GetMapping("/admin")
    public String getAdminPage() {

        return "admin_main";
    }

}
