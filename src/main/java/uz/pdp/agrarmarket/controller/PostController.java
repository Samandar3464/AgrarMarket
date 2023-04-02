package uz.pdp.agrarmarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.pdp.agrarmarket.model.request.PostRegisterDto;
import uz.pdp.agrarmarket.service.PostService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/post")
public class PostController {
    private final PostService postService;

    @PostMapping("/addPost")
    public ResponseEntity<?> addPost(@Validated @RequestBody PostRegisterDto workRegisterDto) {
        return postService.add(workRegisterDto);
    }

    @GetMapping("/getPostById/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        return postService.getById(id);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<?> deletePersonPostById(@PathVariable Long id) {
        return postService.deletePersonPost(id);
    }

//    @PutMapping("/updateCategory/{id}")
//    public ResponseEntity<?> updatePost(@PathVariable Long id,@Validated @RequestBody PostRegisterDto workRegisterDto) {
//        return postService.update(workRegisterDto, id);
//    }
    @GetMapping("/workList")
    public ResponseEntity<?> getPostList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "createdTime") String sort) {
        return postService.getUserPostsList(page, size, sort);
    }

}
