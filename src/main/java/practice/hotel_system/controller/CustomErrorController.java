package practice.hotel_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController {
    @GetMapping("/403")
    public String error403() {
        return "error403";
    }
}
