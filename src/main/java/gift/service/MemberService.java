package gift.service;

import gift.dto.LoginRequestDto;
import gift.dto.LoginResponse;
import gift.dto.MemberProfileDto;
import gift.dto.RegisterRequestDto;
import gift.dto.kakao.KakaoUserInfoResponse;
import gift.entity.Member;
import gift.entity.Role;
import gift.exception.LoginFailedException;
import gift.repository.MemberRepository;
import gift.security.JwtTokenProvider;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public MemberService(MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider) {
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional
    public LoginResponse register(RegisterRequestDto request) {
        String hashedPassword = BCrypt.hashpw(request.password(), BCrypt.gensalt());
        Member newMember = new Member(null, request.email(), hashedPassword,Role.USER,null, null);
        memberRepository.save(newMember);

        String accessToken = jwtTokenProvider.createToken(newMember.getEmail());
        return new LoginResponse(accessToken);
    }

    public LoginResponse login(LoginRequestDto request) {
        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> new LoginFailedException("가입되지 않은 이메일입니다."));
        if (!BCrypt.checkpw(request.password(), member.getPassword())) {
            throw new LoginFailedException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtTokenProvider.createToken(member.getEmail());
        return new LoginResponse(accessToken);
    }

    @Transactional
    public Member loginOrRegister(KakaoUserInfoResponse userInfo, String accessToken) {
        String email = userInfo.getEmail();
        if (email == null) {
            email = userInfo.id() + "@gmail.com";
        }
        String nickname = userInfo.getNickname();
        String profileImageUrl = userInfo.getProfileImageUrl();
        final String finalEmail = email;

        Member member = memberRepository.findByEmail(finalEmail)
                .map(m -> {
                    m.updateProfile(nickname, profileImageUrl);
                    return m;
                })
                .orElseGet(() -> {
                    String tempPassword = "jrqp37kls6vm^20!";
                    String hashedPassword = BCrypt.hashpw(tempPassword, BCrypt.gensalt());
                    Member newMember = new Member(null, finalEmail, hashedPassword, Role.USER, nickname, profileImageUrl);
                    return memberRepository.save(newMember);
                });
        member.updateKakaoAccessToken(accessToken);
        return member;
    }

    public MemberProfileDto findMemberProfileById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. ID: " + memberId));
        return new MemberProfileDto(member.getId(), member.getEmail());
    }
}
