package uz.pdp.agrarmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.agrarmarket.entity.PostCategory;

import java.util.List;
import java.util.Optional;

public interface PostCategoryRepository extends JpaRepository<PostCategory, Integer> {
    Optional<PostCategory> findByName(String string);
    List<PostCategory> findAllByActive(boolean active);
}
