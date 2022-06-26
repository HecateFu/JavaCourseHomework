package org.example.service;

import org.dromara.hmily.annotation.HmilyTCC;
import org.example.client.InventoryClient;
import org.example.entity.InventoryDTO;
import org.example.entity.Order;
import org.example.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private InventoryClient inventoryClient;

    @HmilyTCC(confirmMethod = "createConfirm",cancelMethod = "createCancel")
    public void create(Order order){
        orderMapper.insert(order);
        InventoryDTO dto = new InventoryDTO();
        dto.setProductId(order.getProductId());
        dto.setCount(order.getCount());
        inventoryClient.reduceTry(dto);
    }

    public void createConfirm(Order order){
        orderMapper.update(order);
    }

    public void createCancel(Order order){
        orderMapper.deleteById(order);
    }
}
