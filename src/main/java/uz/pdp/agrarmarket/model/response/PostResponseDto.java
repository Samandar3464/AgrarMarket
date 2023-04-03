package uz.pdp.agrarmarket.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.agrarmarket.entity.Post;
import uz.pdp.agrarmarket.service.AttachmentService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostResponseDto {
    private Long id;
    private String categoryName;
    private String currencyName;
    private LocalDateTime createdTime;
    private Double price;
    private String phone;
    private String provinceName;
    private String cityName;
    private String districtName;
    private List<String> photoUrl;
    private UserResponseDtoForWork userResponseDtoForWork;

    public static PostResponseDto of(Post post, AttachmentService attachmentService){
        List<String> postPhotos = post.getPhotos().stream().map((attachment) -> {
            return attachmentService.attachDownloadUrl + attachment.getPath() + "/" + attachment.getNewName() + "." + attachment.getType();
        }).toList();
        return PostResponseDto.builder()
                .id(post.getId())
                .categoryName(post.getPostCategory().getName())
                .currencyName(post.getCurrency().getName())
                .createdTime(post.getCreatedTime())
                .price(post.getPrice())
                .phone(post.getPhoneNumber())
                .provinceName(post.getProvince().getName())
                .cityName(post.getCity().getName())
                .districtName(post.getDistrict().getName())
                .photoUrl(postPhotos)
                .userResponseDtoForWork(
                        new UserResponseDtoForWork(
                                post.getUser().getId(),
                                post.getUser().getName(),
                                post.getUser().getPhoneNumber()))
                .build();
    }
}
