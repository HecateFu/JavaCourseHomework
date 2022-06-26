package org.example.controller;

import org.dromara.hmily.annotation.HmilyTCC;
import org.example.entity.Inventory;
import org.example.entity.InventoryDTO;
import org.example.mapper.InventoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("inventory")
@RestController
public class InventoryController {
    @Autowired
    private InventoryMapper inventoryMapper;

    @HmilyTCC(confirmMethod = "reduceConfirm",cancelMethod = "reduceCancel")
    @PostMapping("reduceTry")
    public String reduceTry(@RequestBody InventoryDTO dto){
        inventoryMapper.reduceTry(dto);
        return "success";
    }

    public String reduceConfirm(InventoryDTO dto){
        inventoryMapper.reduceConfirm(dto);
        return "success";
    }

    public String reduceCancel(InventoryDTO dto){
        inventoryMapper.reduceCancel(dto);
        return "success";
    }

    @GetMapping("{productId}")
    public Inventory selectById(@PathVariable String productId){
        return inventoryMapper.selectById(productId);
    }
}
