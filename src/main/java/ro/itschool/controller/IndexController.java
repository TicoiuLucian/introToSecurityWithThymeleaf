package ro.itschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.itschool.repository.RestaurantRepository;
import ro.itschool.service.MyUserService;

@Controller
public class IndexController {

    @Autowired
    private MyUserService userService;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @RequestMapping(value = {"/index"})
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("restaurants", restaurantRepository.findAll());
        return "index";
    }
}