package learn.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 直接使用 jdbc API 创建连接
 */
public class UseJdbc {

    private static final String url = "jdbc:mysql://localhost:3306/test?serverTimezone=Asia/Shanghai";
    private static final String user = "root";
    private static final String password = "123456";

    public static void main(String[] args) throws Exception {
        usePreparedStatementManualCommit();
    }

    /**
     * 使用 Statement 执行数据库操作
     * @throws Exception 加载驱动类、数据库操作出现异常
     */
    public static void useStatement() throws Exception {
        //1、加载驱动程序
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 2、创建连接
        try(Connection conn = DriverManager.getConnection(url, user, password)) {
            // 3、执行数据库操作
            StatementOperation dbOp = new StatementOperation(conn);
            dbOp.insert();
            int lastId = dbOp.select();
            dbOp.update(lastId);
            lastId = dbOp.select();
            dbOp.delete(lastId);
            dbOp.select();
        }
        // 4、执行完成自动关闭连接
    }

    /**
     * 使用 Statement 执行数据库操作，手动提交
     * @throws Exception 加载驱动类、数据库操作出现异常
     */
    public static void useStatementManualCommit() throws Exception {
        //1、加载驱动程序
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 2、创建连接
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url, user, password);
            // 3、开启手动提交
            conn.setAutoCommit(false);
            // 4、执行数据库操作
            StatementOperation dbOp = new StatementOperation(conn);
            dbOp.insert();
            int lastId = dbOp.select();
            dbOp.update(lastId);
            lastId = dbOp.select();
            dbOp.delete(lastId);
            dbOp.select();
            // 5、提交
            conn.commit();
        } catch (Exception e){
            // 5、出现异常回滚
            if (conn!=null){
                conn.rollback();
            }
            throw e;
        } finally {
            // 6、关闭连接
            if (conn!=null){
                conn.close();
            }
        }
    }

    /**
     * 使用 PreparedStatement 执行数据库操作，手动提交
     * @throws Exception 加载驱动类、数据库操作出现异常
     */
    public static void usePreparedStatementManualCommit() throws Exception {
        //1、加载驱动程序
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 2、创建连接
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url, user, password);
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
            // 5、出现异常回滚
            if (conn!=null){
                conn.rollback();
            }
            throw e;
        } finally {
            // 6、关闭连接
            if (conn!=null){
                conn.close();
            }
        }
    }

    /**
     * 使用 PreparedStatement 批量插入，手动提交
     * @throws Exception 加载驱动类、数据库操作出现异常
     */
    public static void usePreparedStatementBatchInsert() throws Exception {
        //1、加载驱动程序
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 2、创建连接
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url, user, password);
            // 3、开启手动提交
            conn.setAutoCommit(false);
            // 4、执行数据库操作
            PreparedStatementOperation dbOp = new PreparedStatementOperation(conn);
            dbOp.batchInsert(1000);
            // 5、提交
            conn.commit();
        } catch (Exception e){
            // 5、出现异常回滚
            if (conn!=null){
                conn.rollback();
            }
            throw e;
        } finally {
            // 6、关闭连接
            if (conn!=null){
                conn.close();
            }
        }
    }
}
