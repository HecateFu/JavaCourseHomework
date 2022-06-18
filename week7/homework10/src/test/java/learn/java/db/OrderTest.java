package learn.java.db;

import learn.java.db.model.TBizOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class OrderTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void insertTest() throws Exception {
        String insertUrl = "http://localhost:8080/order/add";
        //插入订单数据
        TBizOrder order = new TBizOrder();
        long userId = new Random().nextLong();
        order.setUserId(userId);
        order.setState(1);
        long time = System.currentTimeMillis();
        order.setCreateTime(time);
        order.setUpdateTime(time);
        HttpEntity<TBizOrder> request = new HttpEntity<>(order);
        ResponseEntity<String> resp = restTemplate.postForEntity(insertUrl,request,String.class);
        assertEquals(HttpStatus.OK,resp.getStatusCode());
        String result = resp.getBody();
        assertNotNull(result);
        System.out.println(result);

        // 查询订单数据
        String getUrl = "http://localhost:8080/order/"+result;
        TBizOrder getOrder = restTemplate.getForObject(getUrl,TBizOrder.class);
        assertNotNull(getOrder);
        System.out.println(getOrder);
    }
}
