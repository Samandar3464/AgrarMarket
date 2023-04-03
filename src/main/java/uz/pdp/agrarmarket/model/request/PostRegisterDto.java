package uz.pdp.agrarmarket.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostRegisterDto {
    @NotNull
    private int categoryId;
    @NotNull
    private Double price;

    @NotNull
    private int currencyId;

    @NotBlank
    @Size(min = 9, max = 9)
    private String phoneNumber;

    @NotNull
    private Integer provinceId;

    private Integer cityId;

    private Integer districtId;
}