package uz.pdp.agrarmarket.dashboard;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.servlet.http.HttpServletRequest;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ActiveRequests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime createdTime;
    private String url;
    private String method;
    //    private String body;

    public static ActiveRequests of(HttpServletRequest request) {
        return ActiveRequests.builder()
                .createdTime(LocalDateTime.now())
                .url(request.getRequestURL().toString())
                .method(request.getMethod())
                .build();
    }
}
