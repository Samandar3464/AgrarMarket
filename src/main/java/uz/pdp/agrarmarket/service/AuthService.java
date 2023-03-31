package uz.pdp.agrarmarket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.agrarmarket.entity.User;
import uz.pdp.agrarmarket.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        Optional<User> person = userRepository.findByPhoneNumber(phoneNumber);
        return person.orElseThrow(() -> new UsernameNotFoundException(String.format("username %s not found", phoneNumber)));
    }
}
