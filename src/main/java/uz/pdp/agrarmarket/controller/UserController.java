package uz.pdp.agrarmarket.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.pdp.agrarmarket.model.request.*;
import uz.pdp.agrarmarket.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated @RequestBody UserRegisterDto personRegisterDto) {
        return userService.register(personRegisterDto);
    }
    @PostMapping("/verifyCode")
    public ResponseEntity<?> verifyCodeForRestorePassword(@RequestBody Verification verification) {
        return userService.verifyCodeForActivateUser(verification);
    }

    @PostMapping("/setPassword")
    public ResponseEntity<?> setPassword(@Validated @RequestBody UserRegisterDto personRegisterDto) {
        return userService.setUserPassword(personRegisterDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequestDto personLoginRequestDto) {
        return userService.login(personLoginRequestDto);
    }

    @PostMapping("/forgetPassword")
    public ResponseEntity<?> forgetPassword(@RequestBody UserForgetPasswordDto userForgetPasswordDto) {
        return userService.generateCodeForForgetPassword(userForgetPasswordDto);
    }

    @PostMapping("/reVerify")
    public ResponseEntity<?> verifyCodeCheckerForRestorePassword(@RequestBody Verification verification) {
        return userService.verifyCodeCheckerForRestorePassword(verification);
    }

    @PostMapping("/change/password")
    public ResponseEntity<?> changePassword(@Validated @RequestBody UserRegisterDto personRegisterDto) {
        return userService.changePassword(personRegisterDto);
    }

    @PutMapping("/updateUser")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateRequestDto personUpdateRequestDto) {
        return userService.updateUser(personUpdateRequestDto);
    }

    @GetMapping("/regenerate/access/token")
    public ResponseEntity<?> getAccessTokenFromRefreshToken(HttpServletRequest request){
        return userService.getAccessToken(request);
    }
}
