package uz.pdp.agrarmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.agrarmarket.entity.AttachmentEntity;

import java.util.Optional;

public interface AttachmentRepository extends JpaRepository<AttachmentEntity,Long> {
    Optional<AttachmentEntity> findByNewName(String newName);
}
