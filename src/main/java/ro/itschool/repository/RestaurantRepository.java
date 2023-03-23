package ro.itschool.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ro.itschool.entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
}
