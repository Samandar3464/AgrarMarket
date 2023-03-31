package uz.pdp.agrarmarket.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {
    @NotBlank(message = "mobileNumber is required")
    @Size(min = 9, max = 10)
    private String phoneNumber;
//    @Pattern(regexp = "^[A-Za-z]*$")
//    private String name;

    @Size(min = 6)
    private String password;

}
