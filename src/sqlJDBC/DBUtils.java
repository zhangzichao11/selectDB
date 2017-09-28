package sqlJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 查询结果处理接口
 * 
 * @author zhangzichao
 * @version 创建时间：2016-3-31 下午07:26:41
 */

public class DBUtils {

	private static String dbType = TestProperties.getDbType();// get dbType
	private static String dbUserName = TestProperties.getDbUserName();// get
																		// dbUser
	private static String dbPassWord = TestProperties.getDbPassWord();// get
																		// dbPwd
	// private static String dbUrl = TestProperties.getDbUrl();//get dbUrl
	private Connection con = DBUtils.getConnection();//
	private static SshConnect sshConect = new SshConnect();

	// database connect
	public static Connection getConnection() {

		Connection conn = null;
		sshConect.sshConnect();// 调用sshConnect

		try {

			if (dbType.equals("mysql")) {

				Class.forName("com.mysql.jdbc.Driver");

			} else if (dbType.equals("oracle")) {

				Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();

			} else {

				System.out.println("类型：" + dbType);
				System.out.print("undefined db type !");
			}

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/pspm", dbUserName, dbPassWord);// ssh
																				// connect
			// conn = DriverManager.getConnection(dbUrl, dbUserName,
			// dbPassWord);//local connect
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * get ResultSet
	 * 
	 * @param conn
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public ResultSet getResultSet(Connection conn, String sql) throws Exception {
		ResultSet resultSet = null;
		Statement statement = null;
		try {
			// PreparedStatement pstmt;
			// ResultSet rset;
			statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			// pstmt = conn.prepareStatement(sql);
			resultSet = statement.executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e.toString());
		}

		return resultSet;
	}

	/**
	 * get ColumnCount
	 * 
	 * @param resultSet
	 * @return
	 * @throws Exception
	 */
	private int getColumnCount(ResultSet resultSet) throws Exception {
		int columnCount = 0;
		try {
			// ResultSet resultSet = this.getResultSet(conn, sql);
			columnCount = resultSet.getMetaData().getColumnCount();
			if (columnCount == 0) {
				System.out.print("sql error!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e.toString());
		}
		return columnCount;
	}

	/**
	 * get ColumnCount
	 * 
	 * @param conn
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public int getColumnCount(Connection conn, String sql) throws Exception {
		int columnCount = 0;
		try {
			// ResultSet resultSet = this.getResultSet(conn, sql);
			columnCount = getResultSet(conn, sql).getMetaData()
					.getColumnCount();
			if (columnCount == 0) {
				System.out.print("sql error!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e.toString());
		}
		return columnCount;
	}

	/**
	 * get RowCount
	 * 
	 * @param conn
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public int getRowCount(Connection conn, String sql) throws Exception {
		int rowCount = 0;
		try {
			ResultSet resultSet = getResultSet(conn, sql);
			resultSet.last();
			rowCount = resultSet.getRow();
			if (rowCount == 0) {
				System.out.print("sql query no data!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e.toString());
		}
		return rowCount;
	}

	/**
	 * get RowCount
	 * 
	 * @param resultSet
	 * @return
	 * @throws Exception
	 */
	private int getRowCount(ResultSet resultSet) throws Exception {
		int rowCount = 0;
		try {
			resultSet.last();
			rowCount = resultSet.getRow();
			if (rowCount == 0) {
				System.out.print("sql query no data!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e.toString());
		}
		return rowCount;
	}

	/**
	 * get data by row index and col index
	 * 
	 * @param conn
	 * @param sql
	 * @param row
	 * @param col
	 * @return
	 * @throws Exception
	 */
	public String getData(Connection conn, String sql, int row, int col)
			throws Exception {
		String data = null;
		int rownum = 0;
		int rowcount = 0;
		int colcount = 0;
		ResultSet resultSet = null;
		try {

			resultSet = getResultSet(conn, sql);
			colcount = getColumnCount(resultSet);
			rowcount = getRowCount(resultSet);
			resultSet.beforeFirst();
			if (rowcount > 0) {
				if (row <= 0 || row > rowcount) {
					System.out.print("error row index!");
				} else {
					if (col <= 0 || col > colcount) {
						System.out.print("error col index!");
					} else {
						while (resultSet.next()) {
							rownum++;
							if (rownum == row) {
								data = resultSet.getString(col);
								break;
							}
						}
					}
				}
			} else {
				System.out.print("sql query no data!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e.toString());
		}

		return data;
	}

	/**
	 * get data by row index and col index
	 * 
	 * @param conn
	 * @param sql
	 * @param row
	 * @param field
	 * @return
	 * @throws Exception
	 */
	public String getData(Connection conn, String sql, int row, String field)
			throws Exception {
		String data = null;
		int rownum = 0;
		int rowcount = 0;
		// int colcount = 0;
		try {
			ResultSet resultSet = getResultSet(conn, sql);
			// colcount = getColumnCount(resultSet);
			rowcount = getRowCount(resultSet);
			resultSet.beforeFirst();
			if (rowcount > 0) {
				if (row <= 0 || row > rowcount) {
					System.out.print("error row index!");
				} else {
					while (resultSet.next()) {
						rownum++;
						if (rownum == row) {
							data = resultSet.getString(field);
							break;
						}
					}
				}
			} else {
				System.out.print("sql query no data!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e.toString());
		}
		return data;
	}

	// update database
	public int update(String sql, Object[] params) throws SQLException {

		int m = 0;
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ParameterMetaData psm = ps.getParameterMetaData();// get query info
			int paramNum = psm.getParameterCount();// get params count
			for (int i = 1; i <= paramNum; i++) {
				ps.setObject(i, params[i - 1]);
			}

			m = ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {

			// con.close();
		}

		return m;
	}

	// update database
	public int updateN(String sql, Object[] params, int N) throws SQLException {

		int m = 0;
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ParameterMetaData psm = ps.getParameterMetaData();// get query info
			int paramNum = psm.getParameterCount();// get params count
			for (int i = 1; i <= paramNum; i++) {
				ps.setObject(i, params[i - 1]);
			}

			m = ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {

			// con.close();
		}

		return m;
	}

	// query database
	public ResultSet query(String sql, Object[] params) throws SQLException {

		ResultSet rs = null;
		try {

			PreparedStatement ps = con.prepareStatement(sql);
			ParameterMetaData psm = ps.getParameterMetaData();// get param info
			int paramNum = psm.getParameterCount();// get params count
			for (int i = 1; i <= paramNum; i++) {
				ps.setObject(i, params[i - 1]); // 给参数赋值（param里面的值要和sql里面的占位符一一对应）
			}
			rs = ps.executeQuery();
			return rs;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int delete(String sql, Object[] params) throws SQLException {

		int m = 0;

		try {

			PreparedStatement ps = con.prepareStatement(sql);
			ParameterMetaData psm = ps.getParameterMetaData(); // get param info
			int paramNum = psm.getParameterCount();// get params count

			for (int i = 1; i <= paramNum; i++) {
				ps.setObject(i, params[i - 1]);
			}

			m = ps.executeUpdate();
			System.out.println("Delete resutl: " + m);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return m;
	}
	
	
	public int delete(String sql) throws SQLException {

		int m = 0;

		try {

			PreparedStatement ps = con.prepareStatement(sql);
			m = ps.executeUpdate();
			System.out.println("Delete resutl: " + m);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return m;
	}

	// close connect
	public void close() {

		try {

			SshConnect.sshDisConnect();
			con.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// 最优经验是按照ResultSet，Statement，Connection的顺序执行close；

}
