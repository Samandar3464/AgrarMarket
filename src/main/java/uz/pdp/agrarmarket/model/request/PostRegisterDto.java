package uz.pdp.agrarmarket.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostRegisterDto {
    @NotNull
    private String workTitle;
    @NotNull
    private String workDescription;
    @NotNull
    private Double startPrice;
    private Double endPrice;

    @NotNull
    private Integer workCategoryId;

    @NotNull
    private Integer provinceId;

    @NotNull
    private Integer cityOrDistrictId;

    private String  village;


}
