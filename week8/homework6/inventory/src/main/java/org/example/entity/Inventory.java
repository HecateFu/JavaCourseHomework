package org.example.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Inventory implements Serializable {
    private Long id;
    private String productId;
    private Integer totalInventory;
    private Integer lockInventory;
}
