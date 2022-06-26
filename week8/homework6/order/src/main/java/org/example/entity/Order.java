package org.example.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Order {
    private Long id;
    private LocalDateTime createTime;
    private Integer status;
    private String productId;
    private BigDecimal totalAmount;
    private Integer count;
    private String userId;
}
