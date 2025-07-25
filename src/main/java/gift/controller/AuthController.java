package gift.controller;

import gift.service.KakaoAuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final KakaoAuthService kakaoAuthService;

    public AuthController(KakaoAuthService kakaoAuthService) {
        this.kakaoAuthService = kakaoAuthService;
    }

    @GetMapping(value = "/", params = "code")
    public String kakaoLoginCallback(@RequestParam("code") String code, Model model) {
        String accessToken = kakaoAuthService.getAccessToken(code);
        model.addAttribute("accessToken", accessToken);
        return "auth-success";
    }
}
