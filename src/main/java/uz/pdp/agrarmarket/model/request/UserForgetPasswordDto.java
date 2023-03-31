package uz.pdp.agrarmarket.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserForgetPasswordDto {
    @NotBlank(message = "mobileNumber is required")
    @Size(min = 9, max = 10)
    private String phoneNumber;
}
