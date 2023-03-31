package uz.pdp.agrarmarket.model.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCategoryRegisterDto {
    @NotBlank
    @Column(unique = true)
    private String name;
}
