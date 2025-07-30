package gift.study;

import gift.entity.*;
import gift.service.KakaoAuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Study {

    @Autowired
    KakaoAuthService kakaoAuthService;

    @Test
    void test() {
        String accessToken = "0beP_4nKeS5uKEluK7eM2HJkqOYSTVTfAAAAAQoNH5cAAAGYW6TvQcYNwJ_muSR4";
        Order order = getOrder();
        kakaoAuthService.sendMessageToMe(accessToken, order);
    }

    private static Order getOrder() {
        Product product = new Product("name", 1, "url");
        Option option = new Option("name", 1, product);
        Member member = new Member(1L, "email", "password", Role.USER, "nickname", "url");
        Order order = new Order(option, member, 1, "helo world");
        return order;
    }
}
