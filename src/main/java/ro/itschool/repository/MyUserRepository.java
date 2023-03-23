package ro.itschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.itschool.entity.MyUser;

public interface MyUserRepository extends JpaRepository<MyUser, Integer> {
    MyUser findByUsername(String username);
}
