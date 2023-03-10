
import java.sql.*;

public class SQL{
    public static void main(String[] args) {
        Connection c = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC 드라이버를 로드하는데 문제 발생" + e.getMessage());
            e.printStackTrace();
        }

        try {
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "1234");
            System.out.println("연결 완료!!!");
        } catch (SQLException e) {
            System.out.println("연결 오류" + e.getMessage());
            e.printStackTrace();
        }

        try {
            if(c != null) {
                c.close();
            }
        } catch (SQLException e) {}
    }
}