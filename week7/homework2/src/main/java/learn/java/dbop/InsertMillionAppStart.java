package learn.java.dbop;

import learn.java.dbop.entity.Order;
import learn.java.dbop.service.OrderCreator;
import learn.java.dbop.service.OrderService;
import learn.java.dbop.service.impl.OrderServiceJdbcImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class InsertMillionAppStart {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(InsertMillionAppStart.class,args);
        OrderCreator creator = context.getBean(OrderCreator.class);
        // 生成订单数据
        List<Order> orders = creator.generateOrders();
        OrderService orderService = context.getBean(OrderServiceJdbcImpl.class);
        // 插入订单
        orderService.insertMillion(orders);
    }

}
