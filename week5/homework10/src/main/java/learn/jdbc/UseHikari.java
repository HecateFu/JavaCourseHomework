package learn.jdbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 使用 Hikari 数据源获取连接
 */
@SpringBootApplication
public class UseHikari {
    public static void main(String[] args) throws SQLException {
        ApplicationContext context = SpringApplication.run(UseHikari.class,args);
        // 1、获取数据源
        DataSource dataSource = context.getBean(DataSource.class);
        System.out.println(dataSource);
        Connection conn = null;
        try{
            // 2、获取连接
            conn = dataSource.getConnection();
            System.out.println(conn);
            // 3、开启手动提交
            conn.setAutoCommit(false);
            // 4、执行数据库操作
            PreparedStatementOperation dbOp = new PreparedStatementOperation(conn);
            dbOp.insert();
            int lastId = dbOp.select();
            dbOp.update(lastId);
            lastId = dbOp.select();
            dbOp.delete(lastId);
            dbOp.select();
            // 5、提交
            conn.commit();
        } catch (Exception e){
            e.printStackTrace();
            // 5、出现异常回滚
            if (conn!=null){
                conn.rollback();
            }
        } finally {
            // 6、关闭连接
            if (conn!=null){
                conn.close();
            }
        }
    }
}
