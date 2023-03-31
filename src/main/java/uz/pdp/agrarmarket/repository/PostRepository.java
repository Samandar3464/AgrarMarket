package uz.pdp.agrarmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.agrarmarket.entity.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUserId(Integer user_id);

    void deleteByIdAndUserId(Long id, Integer user_id);
}
