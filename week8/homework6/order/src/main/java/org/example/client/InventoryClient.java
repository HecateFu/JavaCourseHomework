package org.example.client;

import org.dromara.hmily.annotation.Hmily;
import org.example.entity.InventoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "http://localhost:8080/inventory/")
public interface InventoryClient {

    @Hmily
    @PostMapping("reduceTry")
    String reduceTry(InventoryDTO dto);
}
