package uz.pdp.agrarmarket.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonResponseDtoForWork {
    private Integer id;
    private String name;
    private String phoneNumber;

}
