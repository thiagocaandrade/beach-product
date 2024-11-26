CREATE TABLE product_order (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_name VARCHAR(50) NOT NULL,
  product_type VARCHAR(50) NOT NULL,
  time_hour INT NOT NULL,
  product_value BIGINT NOT NULL,
  product_total BIGINT NOT NULL,
  user_amount BIGINT NOT NULL
) ;