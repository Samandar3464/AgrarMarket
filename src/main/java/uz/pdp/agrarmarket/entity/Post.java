package uz.pdp.agrarmarket.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import uz.pdp.agrarmarket.entity.address.City;
import uz.pdp.agrarmarket.entity.address.District;
import uz.pdp.agrarmarket.entity.address.Province;

import java.time.LocalDate;

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
    private String workTitle;

    @NotNull
    private String workDescription;

    @NotNull
    private Double startPrice;

    private Double endPrice;

    private LocalDate createdTime;

    @NotNull
    @ManyToOne
    private PostCategory postCategory;

    @ManyToOne
    @NotNull
    private Province province;

    @ManyToOne
    private City city;

    @ManyToOne
    private District district;

    @ManyToOne
    @NotNull
    private User user;
}
