package uz.pdp.agrarmarket.model.request;

import lombok.*;
import uz.pdp.agrarmarket.entity.ENUM.Gender;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequestDto {

    private String name;

    private String surname;

    private Gender gender;

    private Integer provinceId;

    private Integer cityId;

    private Integer districtId;

}
