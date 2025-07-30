package gift.service;
import gift.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class KakaoMessageSendTest{

    @Autowired
    KakaoAuthService kakaoAuthService;

    @Test
    void test() {
        String accessToken = "p3GpZuXKmZOcOex71YBJdIpLyPBuckifAAAAAQoNIZYAAAGYW92VbbG7d-HwzTGR";
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
