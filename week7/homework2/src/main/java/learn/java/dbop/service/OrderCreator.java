package learn.java.dbop.service;

import learn.java.dbop.annotation.Elapse;
import learn.java.dbop.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 模拟订单数据生成
 */
@Slf4j
@Component
public class OrderCreator {

    @Elapse
    public List<Order> generateOrders(){
        int size = 1000000;
        List<Order> orders = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            String orderCode = String.format("o%019d",i);
            String userCode = String.format("u%019d",i);
            Order order = new Order(orderCode,new BigDecimal(i),new BigDecimal(i),userCode);
            order.setId((long)i);
            orders.add(order);
        }
        return orders;
    }

}
