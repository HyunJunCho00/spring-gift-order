package gift.controller.api;

import gift.dto.LoginRequestDto;
import gift.dto.LoginResponse;
import gift.dto.MemberProfileDto;
import gift.dto.RegisterRequestDto;
import gift.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
public class MemberApiController {

    private final MemberService memberService;

    public MemberApiController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(
            @Valid @RequestBody RegisterRequestDto request) {
        LoginResponse response = memberService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequestDto request) {
        LoginResponse response = memberService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<MemberProfileDto> getMyInfo(HttpServletRequest request) {
        Long memberId = (Long) request.getAttribute("memberId");
        MemberProfileDto memberProfile = memberService.findMemberProfileById(memberId);
        return ResponseEntity.ok(memberProfile);
    }
}
