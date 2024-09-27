CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(19, 2) NOT NULL,
    max_quantity INT NOT NULL
);

CREATE TABLE inventory (
    product_id INT PRIMARY KEY,
    quantity INT NOT NULL,
    version INT DEFAULT 0 NOT NULL,
    CONSTRAINT fk_product FOREIGN KEY(product_id) REFERENCES product(id)
);

CREATE TABLE inventory_transaction (
    id SERIAL PRIMARY KEY,
    product_id INT NOT NULL,
    transaction_type VARCHAR(10) NOT NULL,
    quantity INT NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_product_transaction FOREIGN KEY(product_id) REFERENCES product(id)
);
