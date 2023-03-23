package ro.itschool.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.itschool.entity.MyUser;

@Service
public interface MyUserService {

    MyUser registerUser(MyUser myUser);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
