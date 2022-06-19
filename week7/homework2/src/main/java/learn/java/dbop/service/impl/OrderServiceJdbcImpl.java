package learn.java.dbop.service.impl;

import com.google.common.collect.Lists;
import learn.java.dbop.annotation.Elapse;
import learn.java.dbop.entity.Order;
import learn.java.dbop.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Call;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 直接使用jdbc batch 实现的订单插入
 * 有效的优化参数
 * 1、jdbcUrl参数：rewriteBatchedStatements=true
 * 2、mysql参数：innodb_io_capacity
 * 3、减少事务提交次数
 */
@Slf4j
@Component
public class OrderServiceJdbcImpl implements OrderService {
    @Autowired
    private DataSource dataSource;

    @Elapse
    @Override
    public void insertMillion(List<Order> orders) {
        int segmentSize = 200000;
        int poolSize = orders.size()/segmentSize;

        // 100万分成20万由5个线程执行
        List<List<Order>> segment = Lists.partition(orders,segmentSize);
        // 创建线程池
        ExecutorService execPool = Executors.newFixedThreadPool(poolSize);
        // 创建线程任务
        List<Callable<Integer>> taskList = new ArrayList<>(segment.size());
//        String sql = "INSERT INTO `t_order`(`order_code`,`order_status`,`amount`,`post_fee`,`payment`,`order_user`,`id`)VALUES(?,?,?,?,?,?,?)";
        String sql = "INSERT INTO `t_order`(`order_code`,`order_status`,`id`)VALUES(?,?,?)";
        for (List<Order> subList :segment) {
            Callable<Integer> task = ()->{
                int exeSize = 10000;
                int addCount = 0;
                int[] result = new int[0];
                long start = System.currentTimeMillis();
                log.info("segment start {}",start);
                // 每个线程使用1个连接和statement
                try(Connection conn = dataSource.getConnection();
                    PreparedStatement state = conn.prepareStatement(sql)){
                    // 开启手动提交
                    conn.setAutoCommit(false);
                    // 4、执行数据库操作
                    long startInsert = System.currentTimeMillis();
                    log.info("segment startInsert {},create conn: {}",startInsert,(startInsert-start));
                    for (Order order : subList) {
                        state.setString(1, order.getOrderCode());
                        state.setString(2, order.getOrderStatus());
                        state.setLong(3,order.getId());
                        state.addBatch();
                        addCount++;
                        // 每 1万条提交数据库执行一次
                        if(exeSize==addCount){
                            result = state.executeBatch();
                            addCount=0;
                        }

                    }
                    if(addCount!=0) {
                        result = state.executeBatch();
                    }
                    // 执行完所有数据，提交事务
                    conn.commit();
                    long end = System.currentTimeMillis();
                    log.info("segment end:{}, elapse {}",end,(end-startInsert));
                    return result.length;
                } catch (Exception e){
                    e.printStackTrace();
                }
                return 0;
            };
            taskList.add(task);
        }
        // 调用线程执行任务
        try {
            execPool.invokeAll(taskList);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        execPool.shutdown();

    }

}
