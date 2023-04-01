package uz.pdp.agrarmarket.AdminController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.agrarmarket.model.request.AddUserFromAdminDto;
import uz.pdp.agrarmarket.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admin/user")
public class AdminControllerUpToUser {
    private final UserService userService;

    @PostMapping("/addUser")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> getAddUser(@Valid @RequestBody AddUserFromAdminDto addUserFromAdminDto) {
        return userService.addUserFromAdmin(addUserFromAdminDto);
    }

    @DeleteMapping("/deleteUser/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        return userService.deleteUser(id);
    }

    @GetMapping("/userList")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> getUsersList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "joinedDate") String sort) {
        return userService.getUserListWithPagination(page, size, sort);
    }

    @GetMapping("/getUserById/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> getPersonById(@PathVariable Integer id) {
        return userService.getById(id);
    }


}
