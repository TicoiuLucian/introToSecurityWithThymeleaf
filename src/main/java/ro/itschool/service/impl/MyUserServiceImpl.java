package ro.itschool.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ro.itschool.entity.MyUser;
import ro.itschool.entity.Role;
import ro.itschool.repository.MyUserRepository;
import ro.itschool.repository.RoleRepository;
import ro.itschool.service.MyUserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class MyUserServiceImpl implements MyUserService, UserDetailsService {

    @Autowired
    private MyUserRepository myUserRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public MyUser registerUser(MyUser receivedUser) { //received User from FE
        MyUser myUser = new MyUser(receivedUser);
        myUser.setPassword(new BCryptPasswordEncoder().encode(receivedUser.getPassword()));

        myUser.getRoles().forEach(role -> {
            final Role roleByName = roleRepository.findByName(role.getName());
            role.setId(roleByName.getId());
        });

        return myUserRepository.save(myUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser = myUserRepository.findByUsername(username);
        List<GrantedAuthority> authorities = getUserAuthority(myUser.getRoles());
        return new MyUser(
                myUser.getUsername(),
                myUser.getPassword(),
                myUser.isEnabled(),
                myUser.isAccountNonExpired(),
                myUser.isAccountNonLocked(),
                myUser.isCredentialsNonExpired(),
                authorities);

    }

    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (Role role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new ArrayList<>(roles);
    }
}
