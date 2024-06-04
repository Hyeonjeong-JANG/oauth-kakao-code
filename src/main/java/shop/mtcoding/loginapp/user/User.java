package shop.mtcoding.loginapp.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.mtcoding.loginapp._core.OauthProvider;

@NoArgsConstructor
@Getter
@Table(name = "user_tb")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String username;
    private String password;
    private String email;
    private String address; // 상품 구매시 받기
    private OauthProvider oauthProvider; // facebook, kakao, apple, naver

    @Builder
    public User(Integer id, String username, String password, String email, String address, OauthProvider oauthProvider) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.oauthProvider = oauthProvider;
    }
}



