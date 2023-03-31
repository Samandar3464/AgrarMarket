package uz.pdp.agrarmarket.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkResponseDto {
    private Long id;
    private String workTitle;
    private String workDescription;
    private Double startPrice;
    private Double endPrice;
    private LocalDate createdTime;
    private String workCategoryName;
    private String provinceName;
    private String cityOrDistrictName;
    private String village;
    private PersonResponseDtoForWork personResponseDtoForWork;
}
