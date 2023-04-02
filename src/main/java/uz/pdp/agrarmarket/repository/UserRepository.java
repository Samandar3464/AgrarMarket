package uz.pdp.agrarmarket.repository;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.agrarmarket.entity.User;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByPhoneNumber(@NotBlank @Size(min = 9) String phoneNumber);

    int countAllByJoinedDateBetween(LocalDateTime joinedDate, LocalDateTime joinedDate2);

    long count();
}
