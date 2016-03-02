package Handler;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import Data.Account;
/**
 * Register Class, used to connect and query the database the check if the request username/email the user wishes to register, also provides
 * functionality to insert the requested user into the database. Includes two method's validate(to validate if user/enail are free) and 
 * RegisterUser, used to insert the user into the database.
 * @author Brodie
 *
 */

public class RegisterHandler {
	/**
	 * Used to validate if the requested username and email exist in the database currently.
	 * @param username  the username the user requested
	 * @param email  the email the user request
	 * @return String, returns an string with the error text if the username/email already exist
	 */
	@SuppressWarnings("resource")
	public static String validate(String username, String email) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		boolean status = false;	
		
		//Attempt to connect to the database and execute the query.
		try {
			//Query to see if the username already exists in the Accounts table
			pst = DatabaseHandler.getSQLConnection().prepareStatement("Select * from Account where username=?");
			pst.setString(1, username);		
			rs = pst.executeQuery();
			status = rs.next();
			//the username already exists, return error string
			if(status) {
				return "This username already exists!";
			}
			status = false;
			//Query to see if the email already exists in the Accounts table
			pst = DatabaseHandler.getSQLConnection().prepareStatement("Select * from Account where email=?");
			pst.setString(1, email);		
			rs = pst.executeQuery();
			status = rs.next();
			//the email already exists, return error string
			if(status) {
				return "This email already exists!";
			}
			status = false;
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DatabaseHandler.closeConnection(rs, pst);
		}
		//No error, return null string
		return "";
	}
	
	/**
	 * Method RegisterUser, used to register requested user into the database, the user has already been validated, so we know it's safe to
	 * insert the user into the database.
	 * @param username username the user wishes to use
	 * @param email email the user wishes to use
	 * @param password password that is hashed, that the user wishes to use
	 * @param name the users full name
	 * @param dateofBirth the users date of birth
	 * @return boolean True if the user could be inserted, false if not
	 * @throws ParseException
	 */
	@SuppressWarnings("resource")
	public static Account RegisterUser(String username, String email, String password, String name, String dateofBirth) throws ParseException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Account account = null;
		int rows = 0;
		
		//Create DateFormat of 3LetterMonth  Day Year , example Nov 12 2009
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
		//Parse our current string into the correct date format
		Date result = df.parse(dateofBirth);	
		//Convert the java.util.Date to a java.util.sql Date
		java.sql.Date sqlDate = new java.sql.Date(result.getTime());
		try {
			//Prepared Statement to insert the Account into the database
			pst = DatabaseHandler.getSQLConnection().prepareStatement("Insert into Account (username, email, name, passwordhash, createdtimestamp, modifiedtimestamp, dateofbirth) VALUES (?,?,?,?,?,?,?)");
			pst.setString(1, username);	
			pst.setString(2, email);
			pst.setString(3, name);
			pst.setString(4, password);
			//Need to convert the current string date into a timestamp
			Date today = new Date();
			Timestamp timeStamp = new Timestamp(today.getTime());
			pst.setTimestamp(5, timeStamp);
			pst.setTimestamp(6, timeStamp);
			pst.setDate(7, sqlDate);
			rows = pst.executeUpdate();
			//Check to see if any rows were updated, if yes the Account was registered
			if(rows > 0) {
				pst = DatabaseHandler.getSQLConnection().prepareStatement("Select * from Account where username=? and passwordhash=?");
				pst.setString(1, username);
				pst.setString(2, password);	
				rs = pst.executeQuery();
				rs.next();
				
				account = new Account(rs);
				
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DatabaseHandler.closeConnection(rs, pst);
		}
		return account;	
	}

}
