package learn.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 使用 Statement 实现增删改查
 */
public class StatementOperation {

    private Connection conn;

    public StatementOperation(Connection conn) {
        this.conn = conn;
    }

    public void insert() throws SQLException {
        try(Statement state = conn.createStatement()) {
            int rowCount = state.executeUpdate("insert into user(name) value('aaaa')");
            System.out.println("插入行数" + rowCount);
        }
    }

    public int select() throws SQLException {
        try(Statement state = conn.createStatement();
        ResultSet resultSet = state.executeQuery("select * from user")) {
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
        try(Statement state = conn.createStatement()) {
            int rowCount = state.executeUpdate("update user set name='newName' where id=" + id);
            System.out.println("修改行数" + rowCount);
        }
    }

    public void delete(int id) throws SQLException {
        try(Statement state = conn.createStatement()) {
            int rowCount = state.executeUpdate("delete from user where id=" + id);
            System.out.println("删除行数" + rowCount);
        }
    }
}
