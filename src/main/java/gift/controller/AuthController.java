package gift.controller;

import gift.dto.kakao.KakaoUserInfoResponse;
import gift.entity.Member;
import gift.security.JwtTokenProvider;
import gift.service.KakaoAuthService;
import gift.service.MemberService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/oauth")
public class AuthController {

    private final KakaoAuthService kakaoAuthService;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${kakao.client.id}")
    private String kakaoClientId;

    @Value("${kakao.redirect.uri}")
    private String kakaoRedirectUri;

    public AuthController(KakaoAuthService kakaoAuthService, MemberService memberService,
                          JwtTokenProvider jwtTokenProvider) {
        this.kakaoAuthService = kakaoAuthService;
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/kakao")
    public String kakaoLogin() {
        String kakaoAuthUrl = "https://kauth.kakao.com/oauth/authorize?client_id=" + kakaoClientId
                + "&redirect_uri=" + kakaoRedirectUri
                + "&response_type=code";
        return "redirect:" + kakaoAuthUrl;
    }

    @GetMapping("/kakao/callback")
    public String kakaoCallback(@RequestParam("code") String code, Model model) {
        String kakaoAccessToken = kakaoAuthService.getAccessToken(code);
        KakaoUserInfoResponse userInfo = kakaoAuthService.getUserInfo(kakaoAccessToken);
        Member member = memberService.loginOrRegister(userInfo, kakaoAccessToken);
        String accessToken = jwtTokenProvider.createToken(member.getId());
        model.addAttribute("accessToken", accessToken);
        return "auth-success";
    }
}