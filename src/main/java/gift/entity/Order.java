package gift.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY, optional = false)
    @JoinColumn(name = "option_id",nullable = false)
    private Option option;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @Column(columnDefinition = "TEXT")
    private String message;

    protected Order() {}

    public Order(Option option, Member member, int quantity, String message) {
        this.option = option;
        this.member = member;
        this.quantity = quantity;
        this.message = message;
    }
    @PrePersist
    public void prePersist() {
        this.orderDateTime = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Option getOption() { return option; }
    public Member getMember() { return member; }
    public int getQuantity() { return quantity; }
    public LocalDateTime getOrderDateTime() { return orderDateTime; }
    public String getMessage() { return message; }

}
