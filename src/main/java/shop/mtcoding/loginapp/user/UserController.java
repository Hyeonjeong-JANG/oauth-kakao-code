package shop.mtcoding.loginapp.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final HttpSession session; // IoC 등록되어 있음 (스프링 실행 되면)

    @GetMapping("/oauth/naver/code-request")
    public String oauthNaver(HttpServletRequest request) {
        String state = UUID.randomUUID().toString();
        String authUrl = userService.createNaverUrl(state);
        HttpSession session = request.getSession();
        session.setAttribute("oauthState", state);

        return "redirect:" + authUrl;
    }

    @GetMapping("/oauth/naver-callback")
    public String oauthNaverCallback(
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "state") String state,
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "error_description", required = false) String errorDescription
    ) throws Exception {

        String saveState = (String) session.getAttribute("oauthState");
        System.out.println("naver call back code: " + code);
        System.out.println("naver state: " + state);

        User sessionUser = userService.네이버로그인(code, saveState, state, error, errorDescription);
        session.setAttribute("sessionUser", sessionUser);

        return "redirect:/shop";
    }

    // http://localhost:8080/oauth/callback?code=3u9fk
    @GetMapping("/oauth/kakao-callback")
    public String oauthCallback(String code) {
        System.out.println("kakao call back code : " + code);
        User sessionUser = userService.카카오로그인(code);
        session.setAttribute("sessionUser", sessionUser);
        return "redirect:/shop";
    }

    @GetMapping("/join-form")
    public String joinForm() {
        return "join-form";
    }

    @GetMapping("/login-form")
    public String loginForm() {
        return "login-form";
    }

    @PostMapping("/join")
    public String join(String username, String password, String email) {
        userService.회원가입(username, password, email);
        return "redirect:/login-form";
    }

    @PostMapping("/login")
    public String login(String username, String password) {
        User sessionUser = userService.로그인(username, password);
        session.setAttribute("sessionUser", sessionUser); // 로그인 되면 세션 저장
        return "redirect:/shop";
    }
}











