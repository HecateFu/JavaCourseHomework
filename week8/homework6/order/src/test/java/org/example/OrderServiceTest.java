package org.example;

import org.example.entity.Order;
import org.example.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootTest
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @Test
    public void createTest(){
        Order order = new Order();
        order.setProductId("123");
        order.setCount(10);
        order.setCreateTime(LocalDateTime.now());
        order.setStatus(0);
        order.setTotalAmount(new BigDecimal("100"));
        order.setUserId("aaa");
        orderService.create(order);
    }
}
