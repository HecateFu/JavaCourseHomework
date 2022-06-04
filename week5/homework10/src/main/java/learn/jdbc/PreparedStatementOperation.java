package learn.jdbc;

import java.sql.*;
import java.util.Arrays;

/**
 * 使用 PreparedStatement 实现增删改查和批量插入
 */
public class PreparedStatementOperation {

    private Connection conn;

    public PreparedStatementOperation(Connection conn) {
        this.conn = conn;
    }

    public void insert() throws SQLException {
        String sql = "insert into user(name) value(?)";
        try(PreparedStatement state = conn.prepareStatement(sql)) {
            state.setString(1,"aaa");
            int rowCount = state.executeUpdate();
            System.out.println("插入行数" + rowCount);
        }
    }

    public int select() throws SQLException {
        try(PreparedStatement state = conn.prepareStatement("select * from user");
        ResultSet resultSet = state.executeQuery()) {
            int lastId = 0;
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                System.out.println(id + "\t" + name);
                lastId = id;
            }
            return lastId;
        }
    }

    public void update(int id) throws SQLException {
        String sql ="update user set name='newName' where id=?";
        try(PreparedStatement state = conn.prepareStatement(sql)) {
            state.setInt(1,id);
            int rowCount = state.executeUpdate();
            System.out.println("修改行数" + rowCount);
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "delete from user where id=?";
        try(PreparedStatement state = conn.prepareStatement(sql)) {
            state.setInt(1,id);
            int rowCount = state.executeUpdate();
            System.out.println("删除行数" + rowCount);
        }
    }

    public void batchInsert(int count) throws SQLException{
        String sql = "insert into user(name) value(?)";
        try(PreparedStatement state = conn.prepareStatement(sql)) {
            int segment = 0;
            for (int i = 0; i <count; i++) {
                state.setString(1,"aaa"+i);
                state.addBatch();
                segment++;
                if (segment==200){
                    // 每 200 条批量执行
                    int[] rowCounts = state.executeBatch();
                    int rowCount = Arrays.stream(rowCounts).sum();
                    System.out.println("插入行数" + rowCount);
                    // 执行完分段计数清零
                    segment = 0;
                }
            }
            if(segment!=0){
                // 执行不足200的
                int[] rowCounts = state.executeBatch();
                int rowCount = Arrays.stream(rowCounts).sum();
                System.out.println("插入行数" + rowCount);
                // 执行完分段计数清零
                segment = 0;
            }
        }
    }
}
