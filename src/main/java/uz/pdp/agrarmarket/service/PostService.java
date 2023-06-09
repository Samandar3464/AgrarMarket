package uz.pdp.agrarmarket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.agrarmarket.entity.AttachmentEntity;
import uz.pdp.agrarmarket.entity.Post;
import uz.pdp.agrarmarket.entity.User;
import uz.pdp.agrarmarket.exception.RecordNotFoundException;
import uz.pdp.agrarmarket.exception.UserNonAuthenticate;
import uz.pdp.agrarmarket.exception.UserNotFoundException;
import uz.pdp.agrarmarket.model.request.PostRegisterDto;
import uz.pdp.agrarmarket.model.response.PostResponseDto;
import uz.pdp.agrarmarket.repository.Address.CityRepository;
import uz.pdp.agrarmarket.repository.Address.DistrictRepository;
import uz.pdp.agrarmarket.repository.Address.ProvinceRepository;
import uz.pdp.agrarmarket.repository.CurrencyRepository;
import uz.pdp.agrarmarket.repository.PostCategoryRepository;
import uz.pdp.agrarmarket.repository.PostRepository;
import uz.pdp.agrarmarket.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostCategoryRepository postCategoryRepository;
    private final DistrictRepository districtRepository;
    private final CityRepository cityRepository;
    private final ProvinceRepository provinceRepository;
    private final CurrencyRepository currencyRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AttachmentService attachmentService;

    public ResponseEntity<?>add(PostRegisterDto postRegisterDto , List<MultipartFile> fileList) {
        Post save = postRepository.save(postBuilder(postRegisterDto , fileList));
        return ResponseEntity.ok(PostResponseDto.of(save, attachmentService));
    }


    public ResponseEntity<?> getList(int page, int size, String sort) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        List<Post> all = postRepository.findAllByActive(true,pageable);
        return ResponseEntity.ok(all);
    }

    public ResponseEntity<?> getUserPostsList(int page, int size, String sort) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()|| authentication.getPrincipal().equals("anonymousUser")) {
            throw new UserNotFoundException("User not found");
        }
        String phoneNumber = (String) authentication.getPrincipal();
        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new UserNotFoundException("User not found"));
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sort).descending());
            return ResponseEntity.ok(postRepository.findAllByUserIdAndActive(user.getId(), true, pageable));
    }

    public ResponseEntity<?> delete(Long id) {
        Post post = postRepository.findById(id).orElseThrow((() -> new RecordNotFoundException("Post not found ")));
        post.setActive(false);
        postRepository.save(post);
        return ResponseEntity.ok("Successfully deleted");
    }

    public ResponseEntity<?> deletePersonPost(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            throw new UserNotFoundException("User not found");
        }
        String phoneNumber = (String) authentication.getPrincipal();
        Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);
        Post post = postRepository.findById(id).orElseThrow((() -> new RecordNotFoundException("Post not found ")));
        if (post.getUser().getId() == user.get().getId()) {
            postRepository.deleteById(id);
        }
        return ResponseEntity.ok("Successfully deleted");
    }

    public ResponseEntity<?> getByIdAndUserId(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            throw new UserNotFoundException("User not found");
        }
        String phoneNumber = (String) authentication.getPrincipal();
        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new UserNotFoundException("User not found"));
        Post post = postRepository.getByIdAndUserId(id, user.getId());
        return ResponseEntity.ok(post);
    }
    public ResponseEntity<?> getById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new RecordNotFoundException("Post not found"));
        return ResponseEntity.ok(post);
    }

//    public ResponseEntity<?> update(PostRegisterDto postRegisterDto, Long id) {
//        postRepository.findById(id).orElseThrow((() -> new RecordNotFoundException("Post not found ")));
//        Post save = postRepository.save(postBuilderUpdate(postRegisterDto, id));
//        return ResponseEntity.ok(save);
//    }

    private Post postBuilder(PostRegisterDto postRegisterDto  , List<MultipartFile> fileList) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            throw new UserNonAuthenticate("User not Authenticated");
        }
        User userFromToken = (User) authentication.getPrincipal();
        String phone = userFromToken.getPhoneNumber();
        User user = userRepository.findByPhoneNumber(phone).orElseThrow(() -> new UserNotFoundException("User not found"));
        List<AttachmentEntity> photos = new ArrayList<>();
        for (int i = 0; i < fileList.size(); i++) {
            AttachmentEntity attachmentEntity = attachmentService.saveToSystem(fileList.get(i));
            photos.add(attachmentEntity);
        }
        return Post.builder()
                .price(postRegisterDto.getPrice())
                .phoneNumber(postRegisterDto.getPhoneNumber())
                .user(user)
                .photos(photos)
                .postCategory(postCategoryRepository.getById(postRegisterDto.getCategoryId()))
                .currency(currencyRepository.getById(postRegisterDto.getCurrencyId()))
                .province(provinceRepository.getById(postRegisterDto.getProvinceId()))
                .district(districtRepository.getById(postRegisterDto.getDistrictId()))
                .city(cityRepository.getById(postRegisterDto.getCityId()))
                .createdTime(LocalDateTime.now())
                .active(true)
                .build();
    }

//    private Post postBuilderUpdate(PostRegisterDto postRegisterDto, Long id) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication.isAuthenticated()) {
//            throw new UserNotFoundException("User not found");
//        }
//        User User = (User) authentication.getPrincipal();
//        return Post.builder()
//                .id(id)
//                .postTitle(postRegisterDto.getPostTitle())
//                .postDescription(postRegisterDto.getPostDescription())
//                .startPrice(postRegisterDto.getStartPrice())
//                .endPrice(postRegisterDto.getEndPrice())
//                .postCategory(postCategoryRepository.getByIdAndUserId(postRegisterDto.getPostCategoryId()))
//                .province(provinceRepository.getByIdAndUserId(postRegisterDto.getProvinceId()))
//                .cityOrDistrict(cityOrDistrictRepository.getByIdAndUserId(postRegisterDto.getCityOrDistrictId()))
//                .village(postRegisterDto.getVillage())
//                .User(User)
//                .build();
//    }

//    private List<PostResponseDto> postResponseBuilder(Page<Post> post){
//        return    post.stream().map(
//                post1 -> {
//                    return PostResponseDto.builder()
//                            .id(post1.getId())
//                            .postTitle(post1.getPostTitle())
//                            .postDescription(post1.getPostDescription())
//                            .startPrice(post1.getStartPrice())
//                            .endPrice(post1.getEndPrice())
//                            .createdTime(post1.getCreatedTime())
//                            .postCategoryName(post1.getPostCategory().getName())
//                            .provinceName(post1.getProvince().getName())
//                            .cityOrDistrictName(post1.getCityOrDistrict().getName())
//                            .village(post1.getVillage())
//                            .personResponseDtoForPost(
//                                    new PersonResponseDtoForPost(
//                                            post1.getPerson().getId(),
//                                            post1.getPerson().getName()
//                                            , post1.getPerson().getPhoneNumber()))
//                            .build();
//                }
//        ).toList();
//    }
}
