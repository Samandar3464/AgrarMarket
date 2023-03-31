package uz.pdp.agrarmarket.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.agrarmarket.entity.AttachmentEntity;
import uz.pdp.agrarmarket.entity.User;
import uz.pdp.agrarmarket.entity.address.City;
import uz.pdp.agrarmarket.entity.address.District;
import uz.pdp.agrarmarket.entity.address.Province;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private Integer id;
    private String name;
    private String surname;
    private String phoneNumber;
    private String gender;
    private Province province;
    private City city;
    private District district;
    private AttachmentEntity profilePhoto;
    public UserResponseDto(Integer id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public static UserResponseDto of(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .phoneNumber(user.getPhoneNumber())
                .gender(user.getGender().toString())
                .province(user.getProvince())
                .city(user.getCity())
                .district(user.getDistrict())
                .profilePhoto(user.getProfilePhoto())
                .build();
    }
}
