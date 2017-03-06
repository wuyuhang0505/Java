package COM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * 通过账号密码连接数据库
 * @author YH
 */
public class DBUtil {
	Connection conn = null;
/**
 * 通过账号密码连接数据库
 */
	public DBUtil() {
		String url = "jdbc:mysql://127.0.0.1/sam";
		String name = "com.mysql.jdbc.Driver";
		String user = "root";
		String password = "123456";

		try {
			// 1.使用了反射机制
			Class.forName(name);
			// 2.调用到数据库连接
			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(conn);

	}
	/**
	 * 获取连接
	 */
	public Connection getConn() {
		return conn;
	}
	/**
	 * 关闭连接
	 */
	public void close(Statement st, Connection conn) {
		// TODO Auto-generated method stub
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
