package uz.pdp.agrarmarket.entity.address;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import uz.pdp.agrarmarket.entity.Post;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,unique = true)
    private String name;
    @ManyToOne
    private Province province;

    @JsonIgnore
    @OneToMany(mappedBy = "district")
    private List<Post> postList;
}
