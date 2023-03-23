package ro.itschool.startup;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ro.itschool.entity.MyUser;
import ro.itschool.entity.Restaurant;
import ro.itschool.entity.Role;
import ro.itschool.repository.RestaurantRepository;
import ro.itschool.repository.RoleRepository;
import ro.itschool.service.MyUserService;
import ro.itschool.util.Constants;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class RunAtStartup {

    private final RestaurantRepository restaurantRepository;

    private final RoleRepository roleRepository;

    private final MyUserService myUserService;

    @EventListener(ApplicationReadyEvent.class)
    public void insertRestaurantsIntoDB() {

        Restaurant r1 = new Restaurant();
        r1.setName("Restaurant1");
        r1.setRating(8.7F);
        restaurantRepository.save(r1);


        r1 = new Restaurant();
        r1.setName("Restaurant2");
        r1.setRating(9.3F);
        restaurantRepository.save(r1);


        r1 = new Restaurant();
        r1.setName("Restaurant3");
        r1.setRating(8.9F);
        restaurantRepository.save(r1);


        Role role = new Role("ROLE_USER");
        Role savedRole = roleRepository.save(role);

        role = new Role("ROLE_ADMIN");
        roleRepository.save(role);

        MyUser myUser = new MyUser();
        myUser.setPassword("pass");
        myUser.setEmail("user@email.com");
        myUser.setUsername("user");
        myUser.setFullName("Full name");
        myUser.setRoles(Collections.singleton(savedRole));
        myUser.setEnabled(true);
        myUser.setAccountNonExpired(true);
        myUser.setAccountNonLocked(true);
        myUser.setCredentialsNonExpired(true);
        myUserService.registerUser(myUser);

    }
}
