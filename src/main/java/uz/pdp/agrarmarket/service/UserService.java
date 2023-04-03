package uz.pdp.agrarmarket.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.agrarmarket.entity.ENUM.Role;
import uz.pdp.agrarmarket.entity.User;
import uz.pdp.agrarmarket.exception.*;
import uz.pdp.agrarmarket.jwtConfig.JwtFilter;
import uz.pdp.agrarmarket.jwtConfig.JwtGenerate;
import uz.pdp.agrarmarket.model.request.*;
import uz.pdp.agrarmarket.model.response.TokenResponse;
import uz.pdp.agrarmarket.model.response.UserResponseDto;
import uz.pdp.agrarmarket.repository.Address.CityRepository;
import uz.pdp.agrarmarket.repository.Address.DistrictRepository;
import uz.pdp.agrarmarket.repository.Address.ProvinceRepository;
import uz.pdp.agrarmarket.repository.UserRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.random.RandomGenerator;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtFilter jwtFilter;
    private final ProvinceRepository provinceRepository;
    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;
    private final SmsSendingService smsSendingService;

    public ResponseEntity<?> addUserFromAdmin(AddUserFromAdminDto addUserFromAdminDto) {
        Optional<User> byPhoneNumber = userRepository.findByPhoneNumber(addUserFromAdminDto.getPhoneNumber());
        if (byPhoneNumber.isPresent()) {
            throw new UserAlreadyExist("This phone number already exist");
        }
        User user = User.of(addUserFromAdminDto);
        user.setPassword(passwordEncoder.encode(addUserFromAdminDto.getPassword()));
        return ResponseEntity.ok(userRepository.save(user));
    }

//    Sms sending ishlaydigon versiyasi asosiy method

//        public ResponseEntity<?> register(UserRegisterDto userRegisterDto) {
//        Optional<User> byPhoneNumber = userRepository.findByPhoneNumber(userRegisterDto.getPhoneNumber());
//        if (byPhoneNumber.isPresent()) {
//            throw new UserAlreadyExist("Username already exist");
//        }
//        verificationCode = verificationCodeGenerator();
//        System.out.println(verificationCode);
//        String massageResponse = smsSendingService.verificationCode(userRegisterDto.getPhoneNumber(), verificationCode);
//        if (massageResponse.equals("queued")) {
//            User user = User.builder()
//                .phoneNumber(userRegisterDto.getPhoneNumber())
//                .roleList(List.of(Role.USER))
//                .verificationCode(verificationCode)
//                .verificationCodeLiveTime(LocalDateTime.now())
//                .build();
//        userRepository.save(user);
//            return  ResponseEntity.ok( String.format(" Code sent to %s phone number ", userRegisterDto.getPhoneNumber()));
//        }
//
//        return  ResponseEntity.badRequest().body("Can not send verification code");
//    }

    //    Sms sending olib tashlangan versiyasi
    public ResponseEntity<?> register(UserRegisterDto userRegisterDto) {
        Optional<User> byPhoneNumber = userRepository.findByPhoneNumber(userRegisterDto.getPhoneNumber());
        if (byPhoneNumber.isPresent()) {
            throw new UserAlreadyExist("Username already exist");
        }
        int verificationCode = verificationCodeGenerator();
        System.out.println(verificationCode);
        User user = User.builder()
                .phoneNumber(userRegisterDto.getPhoneNumber())
                .roleList(List.of(Role.USER))
                .password(passwordEncoder.encode(userRegisterDto.getPassword()))
                .joinedDate(LocalDateTime.now())
                .verificationCode(verificationCode)
                .verificationCodeLiveTime(LocalDateTime.now())
                .build();
        userRepository.save(user);
        return ResponseEntity.ok(String.format(" Code sent to %s phone number ", userRegisterDto.getPhoneNumber()));
    }

    public ResponseEntity<?> verifyCodeForActivateUser(Verification verification) {
        User user = userRepository.findByPhoneNumber(verification.getPhoneNumber()).orElseThrow(() -> new UserNotFoundException("User not found"));
        if (user.getVerificationCode() == verification.getCode()) {
            Duration duration = Duration.between(LocalDateTime.now(), user.getVerificationCodeLiveTime());
            if (duration.getSeconds() > 60) {
                throw new VerificationCodeLiveTimeEnd("Verification code live time end");
            }
            user.setEnableUser(true);
            user.setVerificationCode(0);
            userRepository.save(user);
        }
        return ResponseEntity.ok("User verified successfully");
    }

    public ResponseEntity<?> setUserPassword(UserRegisterDto userRegisterDto) {
        User user = userRepository.findByPhoneNumber(userRegisterDto.getPhoneNumber()).orElseThrow(() -> new UserNotFoundException("User not found"));
        if (!user.isEnableUser()) {
            throw new UserNotVerified("User registered but not verified");
        }
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        userRepository.save(user);
        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(userRegisterDto.getPhoneNumber(), userRegisterDto.getPassword());
            Authentication authenticate = authenticationManager.authenticate(authentication);
            String accessToken = "Bearer " + JwtGenerate.generateAccessToken((User) authenticate.getPrincipal());
            String refreshToken = "RefreshToken " + JwtGenerate.generateRefreshToken((User) authenticate.getPrincipal());
            return ResponseEntity.ok(new TokenResponse(accessToken, refreshToken));
        } catch (BadCredentialsException e) {
            throw new UserNotFoundException("User not found");
        }
    }

    public ResponseEntity<?> getById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        return ResponseEntity.ok(UserResponseDto.of(user));
    }

    public ResponseEntity<?> login(UserLoginRequestDto userLoginRequestDto) {
        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(userLoginRequestDto.getPhoneNumber(), userLoginRequestDto.getPassword());
            Authentication authenticate = authenticationManager.authenticate(authentication);
            String accessToken = "Bearer " + JwtGenerate.generateAccessToken((User) authenticate.getPrincipal());
            String refreshToken = "RefreshToken " + JwtGenerate.generateRefreshToken((User) authenticate.getPrincipal());
            return ResponseEntity.ok(new TokenResponse(accessToken, refreshToken));
        } catch (BadCredentialsException e) {
            throw new UserNotFoundException("User not found");
        }
    }

    public ResponseEntity<?> generateCodeForForgetPassword(UserForgetPasswordDto userForgetPasswordDto) {
        User user = userRepository.findByPhoneNumber(userForgetPasswordDto.getPhoneNumber()).orElseThrow(() -> new UserNotFoundException("User not found"));
        int verificationCode = verificationCodeGenerator();
        user.setVerificationCode(verificationCode);
        System.out.println(verificationCode);
        userRepository.save(user);
        return ResponseEntity.ok(String.format("Verification code sent to your %s number ", userForgetPasswordDto.getPhoneNumber()));
    }


    public ResponseEntity<?> verifyCodeCheckerForRestorePassword(Verification verification) {
        User user = userRepository.findByPhoneNumber(verification.getPhoneNumber()).orElseThrow(() -> new UserNotFoundException("User not found"));
        if (user.getVerificationCode() == verification.getCode()) {
            user.setVerificationCode(0);
            userRepository.save(user);
        }
        return ResponseEntity.ok("User verified successfully");
    }

    public ResponseEntity<?> changePassword(UserRegisterDto userRegisterDto) {
        User user = userRepository.findByPhoneNumber(userRegisterDto.getPhoneNumber()).orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("Password  successfully changed");
    }

    //    Sms jonatish qo'shilmagan versiyasi
    public ResponseEntity<?> changeUserPhoneNumber(UserRegisterDto userRegisterDto) {
        User user = userRepository.findByPhoneNumber(userRegisterDto.getPhoneNumber()).orElseThrow(() -> new UserNotFoundException(String.format("User %s number not found", userRegisterDto.getPhoneNumber())));
        int verificationCode = verificationCodeGenerator();
        System.out.println(verificationCode);
        user.setVerificationCode(verificationCode);
        user.setVerificationCodeLiveTime(LocalDateTime.now());
        userRepository.save(user);
        return ResponseEntity.ok(String.format(" Code sent to %s phone number ", userRegisterDto.getPhoneNumber()));
    }

    //    Raqamga sms jonatadigon versiya
//    public ResponseEntity<?> changeUserPhoneNumber(UserRegisterDto userRegisterDto) {
//        User user = userRepository.findByPhoneNumber(userRegisterDto.getPhoneNumber()).orElseThrow(()->new UserNotFoundException(String.format("User %s number not found",userRegisterDto.getPhoneNumber())));
//        int verificationCode = verificationCodeGenerator();
//        System.out.println(verificationCode);
//        String massageResponse = smsSendingService.verificationCode(userRegisterDto.getPhoneNumber(), verificationCode);
//        if (massageResponse.equals("queued")) {
//            user.setVerificationCode(verificationCode);
//            user.setVerificationCodeLiveTime(LocalDateTime.now());
//            userRepository.save(user);
//            return ResponseEntity.ok(String.format(" Code sent to %s phone number ", userRegisterDto.getPhoneNumber()));
//        }
//        return  ResponseEntity.badRequest().body("Can not send verification code");
//    }
    public ResponseEntity<?> updateUser(UserUpdateRequestDto update) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            throw new UserNotFoundException("User not found");
        }
        String phoneNumber = (String) authentication.getPrincipal();
        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new UserNotFoundException("User not found"));
        User updatedPerson = updateUser(update, user);
        User save = userRepository.save(updatedPerson);
        return ResponseEntity.ok("User updated");
    }

    public ResponseEntity<?> getAccessToken(HttpServletRequest request) {
        String accessToken = jwtFilter.checkRefreshTokenValidAndGetAccessToken(request);
        return ResponseEntity.ok(accessToken);
    }

    public ResponseEntity<?> getUserListWithPagination(int page, int size, String sort) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        Page<User> all = userRepository.findAll(pageable);
        return ResponseEntity.ok(all);
    }

    public ResponseEntity<?> deleteUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setEnableUser(false);
        userRepository.save(user);
        return ResponseEntity.ok("User deleted");
    }

    private int verificationCodeGenerator() {
        return RandomGenerator.getDefault().nextInt(100000, 999999);
    }

    private User updateUser(UserUpdateRequestDto update, User user) {
        user.setName(update.getName());
        user.setSurname(update.getSurname());
        user.setGender(update.getGender());
        user.setProvince(provinceRepository.getById(update.getProvinceId()));
        user.setCity(cityRepository.getById(update.getCityId()));
        user.setDistrict(districtRepository.getById(update.getDistrictId()));
        return user;
    }




}
