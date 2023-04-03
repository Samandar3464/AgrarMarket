package uz.pdp.agrarmarket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class PostCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true,nullable = false)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "postCategory"  , fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Post> post;

    private boolean active;
}
