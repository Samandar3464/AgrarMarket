package uz.pdp.agrarmarket.entity.address;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import uz.pdp.agrarmarket.entity.Post;
import uz.pdp.agrarmarket.entity.User;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;


    @JsonIgnore
    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL)
    private List<City> cityList;

    @JsonIgnore
    @OneToMany(mappedBy = "province")
    private List<District> districtList;

    @JsonIgnore
    @OneToMany(mappedBy = "province")
    private List<User> userList;

    @JsonIgnore
    @OneToMany(mappedBy = "province")
    private List<Post> postList;
}

