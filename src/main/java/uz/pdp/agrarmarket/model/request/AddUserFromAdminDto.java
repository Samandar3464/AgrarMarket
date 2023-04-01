package uz.pdp.agrarmarket.model.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.agrarmarket.entity.ENUM.Role;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddUserFromAdminDto {
    @NotBlank(message = "mobileNumber is required")
    @Size(min = 9, max = 9)
    private String phoneNumber;

    @Pattern(regexp = "^[A-Za-z]*$")
    private String name;

    @Pattern(regexp = "^[A-Za-z]*$")
    private String surname;
    @NotBlank
    @Size(min = 6)
    private String password;

    @Enumerated(EnumType.STRING)
    private List<Role> roleList;

}
