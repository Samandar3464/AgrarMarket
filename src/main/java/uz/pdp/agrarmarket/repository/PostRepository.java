package uz.pdp.agrarmarket.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.agrarmarket.entity.Post;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUserIdAndActive(Integer user_id, boolean active, PageRequest pageRequest);

    List<Post> findAllByActive(boolean active, PageRequest pageRequest);

    void deleteByIdAndUserId(Long id, Integer user_id);

    Post getByIdAndUserId(Long id, Integer user_id);

    int countAllByCreatedTimeBetween(LocalDateTime createdTime, LocalDateTime createdTime2);
    long count();
}
