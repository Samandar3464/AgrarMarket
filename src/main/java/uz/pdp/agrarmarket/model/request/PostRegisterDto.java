package uz.pdp.agrarmarket.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private List<MultipartFile> photos;
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
