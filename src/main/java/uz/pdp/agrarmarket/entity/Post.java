package uz.pdp.agrarmarket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import uz.pdp.agrarmarket.entity.address.City;
import uz.pdp.agrarmarket.entity.address.District;
import uz.pdp.agrarmarket.entity.address.Province;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private PostCategory postCategory;

    @OneToMany( cascade = CascadeType.ALL)
    private List<AttachmentEntity> photos;

    @NotNull
    private Double price;

    @ManyToOne
    private Currency currency;

    @NotBlank
    @Size(min = 9, max = 9)
    private String phoneNumber;

    private LocalDateTime createdTime;

    @ManyToOne
    @NotNull
    private Province province;

    @ManyToOne
    private City city;

    @ManyToOne
    private District district;

    @JsonIgnore
    @ManyToOne
    @NotNull
    private User user;

    private boolean active;
}
