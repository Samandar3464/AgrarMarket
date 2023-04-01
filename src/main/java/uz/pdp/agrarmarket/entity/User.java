package uz.pdp.agrarmarket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.pdp.agrarmarket.entity.ENUM.Gender;
import uz.pdp.agrarmarket.entity.ENUM.Role;
import uz.pdp.agrarmarket.entity.address.City;
import uz.pdp.agrarmarket.entity.address.District;
import uz.pdp.agrarmarket.entity.address.Province;
import uz.pdp.agrarmarket.model.request.AddUserFromAdminDto;
import uz.pdp.agrarmarket.model.request.UserUpdateRequestDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Pattern(regexp = "^[A-Za-z]*$")
    private String name;
    @Pattern(regexp = "^[A-Za-z]*$")
    private String surname;
    @Size(min = 6)
    private String password;
    @NotBlank
    @Size(min = 9)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private List<Role> roleList;

    @OneToOne
    private AttachmentEntity profilePhoto;

    @ManyToOne
    private Province province;

    @ManyToOne
    private City city;

    @ManyToOne
    private District district;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> postList;

    private boolean enableUser = false;

    private int verificationCode;

    private LocalDateTime verificationCodeLiveTime;
    private String joinedDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        roleList.forEach((role) -> {
            roles.add(new SimpleGrantedAuthority("ROLE_" + role));
        });
        return roles;
    }

    @Override
    public String getUsername() {
        return this.phoneNumber;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    @JsonIgnoreProperties
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnoreProperties
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return enableUser;
    }

    public static User of(AddUserFromAdminDto addUserFromAdminDto) {

        return User.builder()
                .phoneNumber(addUserFromAdminDto.getPhoneNumber())
                .name(addUserFromAdminDto.getName())
                .surname(addUserFromAdminDto.getSurname())
                .roleList(addUserFromAdminDto.getRoleList())
                .joinedDate(LocalDateTime.now().toString())
                .enableUser(true)
                .build();
    }

}