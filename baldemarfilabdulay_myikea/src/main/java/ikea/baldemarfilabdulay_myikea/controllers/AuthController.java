package ikea.baldemarfilabdulay_myikea.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ikea.baldemarfilabdulay_myikea.models.User;
import ikea.baldemarfilabdulay_myikea.services.UserService_Rep;
// import jakarta.transaction.Transactional;

@Controller
// @Transactional
public class AuthController {
    @Autowired
    private UserService_Rep usr;
    
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String Register(Model model) {
        model.addAttribute("registereduser", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String Register(@ModelAttribute("registereduser") User newUser, BindingResult result){
        if (!result.hasErrors()) {
            if (!(usr.Exist(newUser.getEmail()))) {
                usr.Create(newUser);
                return "redirect:/";
            }
        }
        return "auth/register";
    }
}