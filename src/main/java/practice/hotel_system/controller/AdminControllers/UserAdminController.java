package practice.hotel_system.controller.AdminControllers;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import practice.hotel_system.entity.Roles;
import practice.hotel_system.entity.Users;
import practice.hotel_system.service.ClientService;
import practice.hotel_system.service.UserService;

import java.util.Set;

@Controller
public class UserAdminController {
    private final UserService userService;
    private final ClientService clientService;

    public UserAdminController(UserService userService, ClientService clientService) {
        this.userService = userService;
        this.clientService = clientService;
    }

    @GetMapping("/admin/list-user")
    public String getUsersList(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("clients", clientService.getAllClients());

        return "list-users-admin";
    }

    @GetMapping("/admin/update-user")
    public String getUpdateUserPage(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("clients", clientService.getAllClients());

        return "update_username_password";
    }

    @PostMapping("/updateUsernameAndPassword")
    public String updateUsernameAndPassword (@RequestParam(name = "username") String username,
                                             @RequestParam(name = "password") String password,
                                             @RequestParam(name = "id") Users user,
                                             RedirectAttributes redirectAttributes,
                                             Model model) {
        Users user_var = userService.getUserByUsername(username);
        //if user doesn't have given username and if given user is the same we edit
        if (user_var == null || user_var.getId().equals(user.getId())) {
            user.setUsername(username);
            user.setPassword(new BCryptPasswordEncoder().encode(password));
            userService.saveNewUser(user);

            redirectAttributes.addFlashAttribute("successMess", "Дані користувача оновлено успішно.");
        } else {
            redirectAttributes.addFlashAttribute("errorMess", "Цей логін вже зайнятий.");
        }

        return "redirect:/admin/list-user";
    }

    @GetMapping("/admin/roles-have-users")
    public String getRolesHaveUsersPage(Model model) {
        model.addAttribute("users", userService.findAllUsers());

        return "roles_have_users";
    }

    @PostMapping("/saveNewRoleForUser")
    public String saveNewRoleForUser(@RequestParam(name = "id") Users user,
                                     @RequestParam(name = "roles") Long roleId){

        Set<Roles> rolesSet = user.getRolesSet();
        boolean logic = true;
        for (Roles role : rolesSet) {
            if (role.getId().equals(roleId)) {
                logic = false;
                break;
            }
        }

        if (logic) userService.saveNewUserRole(user.getId(), roleId);

        return "redirect:/admin/roles-have-users";
    }
}
