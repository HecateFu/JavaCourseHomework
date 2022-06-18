package learn.java.dbop.service;

import learn.java.dbop.entity.Order;

import java.util.List;

public interface OrderService {

    void insertMillion(List<Order> orders);
}
