CREATE TABLE customer (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255)
);

CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    price DOUBLE PRECISION
);

CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT,
    created_at TIMESTAMP,
    CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customer(id)
);

CREATE TABLE orders_products (
    order_id BIGINT,
    product_id BIGINT,
     CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES orders(id),
     CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES product(id)
);
