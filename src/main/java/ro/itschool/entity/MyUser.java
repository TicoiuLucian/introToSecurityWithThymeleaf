package ro.itschool.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class MyUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 30)
    private String fullName;

    @Column(nullable = false, length = 30, unique = true)
    private String email;

    //Required By Spring security
    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Transient
    private List<GrantedAuthority> authorities = null;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_restaurant",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "restaurant_id", referencedColumnName = "restaurant_id"))
    private Set<Restaurant> restaurants = new HashSet<>();

    public MyUser(MyUser myUser) {
        this.enabled = myUser.isEnabled();
        this.roles = myUser.getRoles();
        this.username = myUser.getUsername();
        this.fullName = myUser.getFullName();
        this.id = myUser.getId();
        this.accountNonExpired = myUser.isAccountNonExpired();
        this.accountNonLocked = myUser.isAccountNonLocked();
        this.credentialsNonExpired = myUser.isCredentialsNonExpired();
        this.email = myUser.getEmail();
    }

    public MyUser(String username,
                  String password,
                  boolean enabled,
                  boolean accountNonExpired,
                  boolean accountNonLocked,
                  boolean credentialsNonExpired,
                  List<GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.authorities = authorities;
    }
}
