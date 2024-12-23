package code.with.vanilson.user;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@SuppressWarnings("all")
public class Token {

    @Id
    @GeneratedValue
    private Long id;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime validateAt;

    @ManyToOne
    @JoinColumn(nullable = false, name = "userId")
    private User user;

}
