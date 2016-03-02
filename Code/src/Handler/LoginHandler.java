package Handler;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Data.Account;

/**
 * Class login, used to query the database with the user requested username,hashed password. Attempts to validate that the user entered
 * credentials exist in the database
 * @author Brodie
 *
 */
public class LoginHandler {
	/**
	 * Validate, used to validate that the user/pass exist in the database
	 * @param name username the user wishes to login with
	 * @param pass hashed password the user wishes to login with
	 * @return boolean if the user exists in the database with the requested user/pass
	 */
	public static Account validate(String name, String pass) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Account account = null;
		
		//Attempt to connect to the database and query the Accounts table
		try {
			//Prepare statement to query the Accounts table for username/password
			pst = DatabaseHandler.getSQLConnection().prepareStatement("Select * from Account where username=? and passwordhash=?");
			pst.setString(1, name);
			pst.setString(2, pass);	
			rs = pst.executeQuery();
			rs.next();		
			account = new Account(rs);
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DatabaseHandler.closeConnection(rs, pst);
		}
		return account;
	}
}
