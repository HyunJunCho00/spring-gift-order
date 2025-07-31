package gift.service;

import gift.client.KakaoClient;
import gift.entity.Member;
import gift.entity.Option;
import gift.entity.Product;
import gift.entity.Role;
import gift.entity.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.MultiValueMap;

import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class KakaoMessageSendTest {

    @Mock
    private KakaoClient kakaoClient;

    @InjectMocks
    private KakaoAuthService kakaoAuthService;

    @Test
    void test() {
        String accessToken = "fake-token-for-test";
        Order order = getOrder();

        kakaoAuthService.sendMessageToMe(accessToken, order);

        verify(kakaoClient).sendKakaoTalkMessage(
                eq(accessToken),
                any(MultiValueMap.class)
        );
    }

    private static Order getOrder() {
        Product product = new Product("name", 1, "url");
        Option option = new Option("name", 1, product);
        Member member = new Member(1L, "email", "password", Role.USER, "nickname", "url");
        Order order = new Order(option, member, 1, "helo world");
        return order;
    }
}
