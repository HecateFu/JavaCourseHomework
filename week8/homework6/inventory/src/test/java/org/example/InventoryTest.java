package org.example;

import org.example.entity.Inventory;
import org.example.entity.InventoryDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class InventoryTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testTry(){

        String tryUrl = "http://localhost:8080/inventory/reduceTry";
        InventoryDTO dto = new InventoryDTO();
        dto.setCount(10);
        dto.setProductId("123");
        HttpEntity<InventoryDTO> request = new HttpEntity<>(dto);
        ResponseEntity<String> resp = restTemplate.postForEntity(tryUrl,request,String.class);
        assertEquals(HttpStatus.OK,resp.getStatusCode());
        String result = resp.getBody();
        System.out.println(result);

        String selectUrl = "http://localhost:8080/inventory/123";
        Inventory inventory = restTemplate.getForObject(selectUrl, Inventory.class);
        System.out.println(inventory);
    }

    @Test
    public void testCancel(){

        String tryUrl = "http://localhost:8080/inventory/reduceCancel";
        InventoryDTO dto = new InventoryDTO();
        dto.setCount(10);
        dto.setProductId("123");
        HttpEntity<InventoryDTO> request = new HttpEntity<>(dto);
        ResponseEntity<String> resp = restTemplate.postForEntity(tryUrl,request,String.class);
        assertEquals(HttpStatus.OK,resp.getStatusCode());
        String result = resp.getBody();
        System.out.println(result);

        String selectUrl = "http://localhost:8080/inventory/123";
        Inventory inventory = restTemplate.getForObject(selectUrl, Inventory.class);
        System.out.println(inventory);
    }

    @Test
    public void testConfirm(){

        String tryUrl = "http://localhost:8080/inventory/reduceConfirm";
        InventoryDTO dto = new InventoryDTO();
        dto.setCount(10);
        dto.setProductId("123");
        HttpEntity<InventoryDTO> request = new HttpEntity<>(dto);
        ResponseEntity<String> resp = restTemplate.postForEntity(tryUrl,request,String.class);
        assertEquals(HttpStatus.OK,resp.getStatusCode());
        String result = resp.getBody();
        System.out.println(result);

        String selectUrl = "http://localhost:8080/inventory/123";
        Inventory inventory = restTemplate.getForObject(selectUrl, Inventory.class);
        System.out.println(inventory);
    }
}
