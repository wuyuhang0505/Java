package COM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 抓取数据和插入数据
 * 
 * @param
 *
 */
public class FetchAndInsertLog {
	// insert_pst 准备句柄
	PreparedStatement fetch_pst = null;
	// 数据库连接
	Connection conn = new DBUtil().getConn();

	/**
	 * 抓取数据功能
	 * 
	 * @param
	 * @return
	 */
	public FetchAndInsertLog() {

		try {
			fetchLog(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭抓取句柄
				fetch_pst.close();
				// 关闭连接
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * 插入数据功能
	 * 
	 * @param
	 * @return void
	 */
	private void insertLog(List<String> list) {

		try {
			// insert_sql 插入的sql语句
			String insert_sql = "insert into log_new (USER_ID," + "AREA_NAME," + "SERVICE_SUFFIX," + "SERVICE_ID,"
					+ "ACCESS_TYPE," + "NAS_IP," + "NAS_PORT," + "USER_IPV4," + "USER_IPV6," + "USER_MAC,"
					+ "REJECT_CAUSE,"+"create_time)VALUES('" + list.get(0) + "','" + list.get(1) + "','" 
					+ list.get(2) + "','"+ list.get(3) + "','" + list.get(4) + "','"+ list.get(8) + "','"
					+ list.get(10) + "','"+ list.get(11) + "','" + list.get(12) + "','" 
					+ list.get(13) + "','" + list.get(14) +"','" + list.get(15)+ "');";

			// insert_pst 插入数据库的句柄
			PreparedStatement insert_pst = conn.prepareStatement(insert_sql);
			// 将list元素插入数据库
			insert_pst.execute();
			System.out.println("update data successfully");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 抓取数据功能
	 * 
	 * @param sql
	 * @return void
	 */
	private void fetchLog(Connection conn) throws SQLException {
		// fetch_sql 抓取msg字段
		String fetch_sql = " SELECT MSG,CREATE_TIME FROM log";
		// fetch_pst 抓取数据句柄
		fetch_pst = conn.prepareStatement(fetch_sql);
		System.out.println("get msg !!!");
		// ResultSet 结果集
		ResultSet fetch_rs = fetch_pst.executeQuery();
		// list 存放字段拆分的列表

		List<String> list = new ArrayList<String>();
		// tag 判断是否取到最后一条的标签
		boolean tag = true;
		while (tag) {
			/**
			 * 如果没有到最后一条则拆分和插入，否则结束
			 */
			if (fetch_rs.next()) {
				String msg = fetch_rs.getString(1);
				String create_time = fetch_rs.getString(2);
				System.out.println(msg);
			
				
				// 切割数据
				SplitLog splitlog = new SplitLog();
				list = splitlog.Split(msg,create_time);
				if (list != null) {
					// list 不为空则插入
					insertLog(list);
				}
			
			} else {
				tag = false;
			}

		}
	}
}
