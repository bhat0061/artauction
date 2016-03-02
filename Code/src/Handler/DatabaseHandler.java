package Handler;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Handles storing Database information and the creation of new database connections
 * @author Richard Fyffe
 *
 */
public class DatabaseHandler {
	
	/**
	 * Database URL and Port
	 */
	private static String url = "jdbc:mysql://art-auction.cloudapp.net:3306/";

	/**
	 * Database name
	 */
	private static String dbName = "artAuction";
	
	/**
	 * JDBC driver
	 */
	private static String driver = "com.mysql.jdbc.Driver";
	
	/**
	 * Database username credential
	 */
	private static String sqlUser = "website";
	
	/**
	 * Database password credential
	 */
	private static String sqlPassword = "cHe3syS4ndwich2s>!";
	 
	/*
	 * Static connection, used for single connection pages
	 */
	private static Connection connection;
			
	  /**
     * {@link DatabaseHandler#url}
     */
	public static String getUrl(){return url;}
	
	/**
     * {@link DatabaseHandler#dbName}
     */
	public static String getdbName(){return dbName;}
	
	/**
     * {@link DatabaseHandler#driver}
     */
	public static String getDriver() {return driver;}
	
	/**
     * {@link DatabaseHandler#sqlUser}
     */
	public static String getSqlUser() {return sqlUser;}
	
	/**
     * {@link DatabaseHandler#sqlPassword}
     */
	public static String getSqlPassword() {return sqlPassword;}
	
	/**
	 * Reinitializes static connection for use on single-connection pages
	 * @return Connection: Initialized to connect to database
	 */
	public static Connection getSQLConnection(){
		try{
			try{
				if(connection != null){
					connection.close();
				}
			}catch(SQLException e){}
			Class.forName(driver).newInstance();
			connection = DriverManager.getConnection(url + dbName, sqlUser, sqlPassword); 
			return connection;
		}
		catch(Exception e){
			return null;
		}
	}
	
	/**
	 * Creates new connection for multi-connection pages
	 * @return Connection: Initialized to connect to database
	 */
	public static Connection getNewSQLConnection(){
		try{
			Class.forName(driver).newInstance();
			return DriverManager.getConnection(url + dbName, sqlUser, sqlPassword);
		}
		catch(Exception e){
			return null;
		}
	}
	
	/**
	 * Closes static connection with optional ResultSet and PreparedStatment that may have been used with connection
	 * @param rs: Optional
	 * @param pst: Optional
	 */
	public static void closeConnection(ResultSet rs, PreparedStatement pst){
		if(connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(pst != null) {
			try {
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Closes any connection with optional ResultSet and PreparedStatment that may have been used with the connection
	 * @param conn: optional
	 * @param rs: optional
	 * @param pst: optional
	 */
	public static void closeConnection(Connection conn, ResultSet rs, PreparedStatement pst){
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(pst != null) {
			try {
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
