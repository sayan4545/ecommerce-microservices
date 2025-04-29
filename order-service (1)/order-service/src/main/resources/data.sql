INSERT INTO orders (total_price,order_status) VALUES
                                                  (100.50,'PENDING'),
                                                  (6777,'CONFIRMED');

INSERT INTO order_item(order_id,product_id,quantity) VALUES
                                                         (1,101,2),
                                                         (2,103,1),
                                                         (2,100,9);