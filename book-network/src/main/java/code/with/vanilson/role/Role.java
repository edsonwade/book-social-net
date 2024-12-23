package code.with.vanilson.role;

import code.with.vanilson.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Role
 *
 * @author vamuhong
 * @version 1.0
 * @since 2024-12-23
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class) // to enable auditing fields created_at and updated_at
public class Role {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles") // mappedBy is used to avoid creating a join table
    @JsonIgnore // to avoid infinite recursion when serializing to JSON (Jackson)
    private List<User> users;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdDate;
    @LastModifiedDate
    @Column(insertable = false, nullable = false)
    private LocalDateTime lastModifiedDate;

}