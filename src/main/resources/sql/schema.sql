Create Table product
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(255)  NOT NULL,
    price     INT           NOT NULL,
    image_url VARCHAR(1000) NOT NULL
);

CREATE TABLE member
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    kakao_access_token VARCHAR(255)
);

CREATE TABLE wish
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id  BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE,
    UNIQUE (member_id, product_id)
);
CREATE TABLE product_options
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(50) NOT NULL,
    quantity   INT         NOT NULL,
    product_id BIGINT      NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE,
    UNIQUE (product_id, name)
);

CREATE TABLE orders (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        option_id BIGINT NOT NULL,
                        member_id BIGINT NOT NULL,
                        quantity INT NOT NULL,
                        order_date_time DATETIME NOT NULL,
                        message TEXT,
                        FOREIGN KEY (option_id) REFERENCES product_options(id),
                        FOREIGN KEY (member_id) REFERENCES member(id)
);