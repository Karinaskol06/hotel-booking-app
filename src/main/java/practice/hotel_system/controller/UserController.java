package practice.hotel_system.controller;

import jakarta.validation.Valid;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import practice.hotel_system.entity.Clients;
import practice.hotel_system.entity.Roles;
import practice.hotel_system.entity.Users;
import practice.hotel_system.service.ClientService;
import practice.hotel_system.service.UserService;

import java.util.Collections;

@Controller
public class UserController {
    private final UserService userService;
    private final ClientService clientService;

    public UserController(UserService userService, ClientService clientService) {
        this.userService = userService;
        this.clientService = clientService;
    }

    @GetMapping("/login")
    public String getPageLogin() {
        return "login";
    }

    @GetMapping("/registration")
    public String getPageRegistration(Model model) {
        model.addAttribute("clients", new Clients());
        model.addAttribute("users", new Users());

        return "registration";
    }

    @PostMapping("/registration")
    public String registrationNewUser(@Valid Clients client,
                                      BindingResult bindingResult1,
                                      @Valid Users user,
                                      BindingResult bindingResult2)
    {
        if (bindingResult1.hasErrors()) {
            return "registration";
        }
        if (bindingResult2.hasErrors()) {
            return "registration";
        }

        //checking if login id already in db
        if (userService.getUserByLogin(user.getUsername())) {
            return "redirect:/registration";
        }
        //encoding password
        String password = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(password);

        Users savedUser = userService.saveNewUser(user);
        savedUser.setRolesSet(Collections.singleton(new Roles(1L, "ROLE_user")));
        client.setUser(savedUser);
        clientService.saveNewClient(client);

        return "redirect:/login";
    }

}