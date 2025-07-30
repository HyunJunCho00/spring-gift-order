package gift.controller.api;

import gift.annotation.LoginMember;
import gift.dto.OrderRequestDto;
import gift.dto.OrderResponseDto;
import gift.entity.Member;
import gift.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(
            @LoginMember Member member,
            @Valid @RequestBody OrderRequestDto orderDto
    ) {
        OrderResponseDto createdOrder = orderService.createOrder(orderDto, member);
        URI location = URI.create("/api/orders/" + createdOrder.id());
        return ResponseEntity.created(location).body(createdOrder);
    }
}
