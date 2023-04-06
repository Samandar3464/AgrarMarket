package uz.pdp.agrarmarket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String newName;
    private String originName;
    private Long size;
    private String type;
    private String contentType;
    private String path;
    private Double duration;
    @CreationTimestamp
    private LocalDateTime createdDate;
    @ManyToOne
    @JsonIgnore
    private Post post;
}
