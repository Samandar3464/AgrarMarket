package uz.pdp.agrarmarket.AdminController;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.pdp.agrarmarket.model.request.PostCategoryRegisterDto;
import uz.pdp.agrarmarket.service.PostCategoryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/category")
public class AdminControllerUpToWorkCategory {
    private final PostCategoryService postCategoryService;

    @PostMapping("/addCategory")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> addCategory(@Validated @RequestBody PostCategoryRegisterDto postCategoryRegisterDto) {
        return postCategoryService.add(postCategoryRegisterDto);
    }

    @GetMapping("/getCategoryList")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> getCategoryList() {
        return postCategoryService.getList();
    }

    @GetMapping("/getCategoryById/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> getCategoryById(@PathVariable Integer id) {
        return postCategoryService.getById(id);
    }


    @PutMapping("/updateCategory/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> updateCategory(@PathVariable Integer id, @RequestBody PostCategoryRegisterDto postCategoryRegisterDto) {
        return postCategoryService.update(postCategoryRegisterDto, id);
    }
    @DeleteMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id) {
        return postCategoryService.delete(id);
    }

}
