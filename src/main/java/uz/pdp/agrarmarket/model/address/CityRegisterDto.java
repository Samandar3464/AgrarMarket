package uz.pdp.agrarmarket.model.address;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityRegisterDto {
    @NotNull
    private String name;
    @NotNull
    private int provinceId;
}
