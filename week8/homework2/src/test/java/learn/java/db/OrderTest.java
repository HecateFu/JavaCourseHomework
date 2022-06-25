package learn.java.db;

import learn.java.db.dao.mapper.TBizOrderMapper;
import learn.java.db.model.TBizOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class OrderTest {
    @Autowired
    private TBizOrderMapper mapper;

    @Test
    public void testCreateTable() throws Exception {
        mapper.createTable();
    }

    @Test
    public void testInsert() throws InterruptedException {
        for (int i=0; i<100; i++) {
            TBizOrder order = new TBizOrder();
            order.setUserId(new Random().nextLong());
            order.setState(0);
            long time =System.currentTimeMillis();
            order.setCreateTime(time);
            order.setUpdateTime(time);
            order.setId((long) i);
            mapper.insert(order);
            System.out.println(order.getId());
        }
    }

    @Test
    public void testSelect(){
        List<TBizOrder> orderList = mapper.select();
        orderList.forEach(System.out::println);
    }

    @Test
    public void test(){
        long id = 747425804647923713L;
        TBizOrder order = mapper.selectByPrimaryKey(id);
        System.out.println(order);

        order.setState(1);
        long time = System.currentTimeMillis();
        order.setUpdateTime(time);
        mapper.updateByPrimaryKey(order);

        order = mapper.selectByPrimaryKey(id);
        System.out.println(order);

        mapper.deleteByPrimaryKey(id);

        order = mapper.selectByPrimaryKey(id);
        System.out.println(order);
    }

}
