package study.security.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Setter
@Table(name = "Users")
public class User extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //h2,오라클
    private Long id;
    private String username;
    private String password;
    private String email;
    private String role; //ROLE
}
