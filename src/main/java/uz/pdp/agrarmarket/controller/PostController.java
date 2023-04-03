package uz.pdp.agrarmarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.agrarmarket.model.request.PostRegisterDto;
import uz.pdp.agrarmarket.service.PostService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/post")
public class PostController {
    private final PostService postService;

    @PostMapping("/addPost")
    public ResponseEntity<?> addPost(@Validated @ModelAttribute PostRegisterDto postRegisterDto , @RequestParam("files")  List<MultipartFile> files) {
        return postService.add(postRegisterDto , files);
    }

    @GetMapping("/getPostById/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        return postService.getByIdAndUserId(id);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<?> deletePersonPostById(@PathVariable Long id) {
        return postService.deletePersonPost(id);
    }

//    @PutMapping("/updateCategory/{id}")
//    public ResponseEntity<?> updatePost(@PathVariable Long id,@Validated @RequestBody PostRegisterDto workRegisterDto) {
//        return postService.update(workRegisterDto, id);
//    }
    @GetMapping("/list")
    public ResponseEntity<?> getPostList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "createdTime") String sort) {
        return postService.getUserPostsList(page, size, sort);
    }

}
