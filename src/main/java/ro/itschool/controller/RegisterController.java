package ro.itschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ro.itschool.entity.MyUser;
import ro.itschool.entity.Role;
import ro.itschool.service.MyUserService;
import ro.itschool.util.Constants;

import java.util.Collections;

@Controller
public class RegisterController {

    @Autowired
    private MyUserService userService;

    @GetMapping(value = "/register")
    public String registerForm(Model model) {
        MyUser user = new MyUser();
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping(value = "/register")
    public String registerUser(@ModelAttribute("user") @RequestBody MyUser user) {
        user.setRoles(Collections.singleton(new Role(Constants.ROLE_USER)));
        userService.registerUser(user);
        return Constants.REDIRECT_TO_LOGIN;
    }

}