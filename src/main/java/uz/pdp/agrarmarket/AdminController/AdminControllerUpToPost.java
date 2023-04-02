package uz.pdp.agrarmarket.AdminController;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.agrarmarket.service.PostService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admin/post")
public class AdminControllerUpToPost {
    private final PostService postService;
//    @GetMapping("/getWorkList")
//    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
//    public ResponseEntity<?> getWorkList(@RequestParam(defaultValue = "0") int page,
//                                      @RequestParam(defaultValue = "5") int size,
//                                      @RequestParam(defaultValue = "name") String sort) {
//        return postService.getList(page, size, sort);
//    }

    @DeleteMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> deleteWorkById(@PathVariable Long id) {
        return postService.delete(id);
    }
}
