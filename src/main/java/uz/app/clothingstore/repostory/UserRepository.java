package uz.app.clothingstore.repostory;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.app.clothingstore.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
