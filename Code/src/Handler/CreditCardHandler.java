package Handler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class for handling the credit card database crud operations
 * @author Brodie
 *
 */
public class CreditCardHandler {
	
	/**
	 * Database handler for adding a credit card to the database
	 * @param name
	 * @param number
	 * @param month
	 * @param year
	 * @param cvv
	 * @param accId
	 * @param address
	 * @param city
	 * @param province
	 * @param postalCode
	 * @return
	 */
	public static boolean addCreditCard(String name, String number, String month, String year, int cvv, int accId, String address, String city, String province, String postalCode) {
		PreparedStatement pst = null;
		ResultSet rs = null;	
		try {
			java.util.Date today = new java.util.Date();
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			//Setup date format
			Date d = format.parse("01/" + month + "/" + year);
			java.sql.Date sqlDate = new java.sql.Date(d.getTime());
			Timestamp timeStamp = new Timestamp(sqlDate.getTime());
			Timestamp timeStamp2 = new Timestamp(today.getTime());
			//insert credit card information to credit card
			pst = DatabaseHandler.getSQLConnection().prepareStatement("INSERT INTO CreditCard (accountid,cardtype,ccv,cardnumber,expireydatestamp,holdername,createdtimestamp"
					+ ") VALUES(?,?,?,?,?,?,?);", PreparedStatement.RETURN_GENERATED_KEYS);
			pst.setInt(1, accId);
			pst.setString(2,"MasterCard");
			pst.setInt(3, cvv);
			pst.setString(4, number);
			pst.setTimestamp(5, timeStamp);
			pst.setString(6, name);
			pst.setTimestamp(7, timeStamp2);
			pst.executeUpdate();
			rs = pst.getGeneratedKeys();
			pst.close();
			//Use the database handler to get the sql connection and insert the data into the address table
			pst = DatabaseHandler.getSQLConnection().prepareStatement("INSERT INTO Address (accountid,streetaddress,postalcode,apartmentnumber,city,province,type,phone" + ") VALUES(?,?,?,?,?,?,?,?);", PreparedStatement.RETURN_GENERATED_KEYS);
			pst.setInt(1, accId);
			pst.setString(2, address);
			pst.setString(3, postalCode);
			pst.setString(4, null);
			pst.setString(5, city);
			pst.setString(6, province);
			pst.setString(7, "HOME");
			pst.setString(8, "N/A");
			pst.executeUpdate();
			rs = pst.getGeneratedKeys();
			return true;
		}
		catch(Exception ex){
			ex.printStackTrace();
		}finally{
			DatabaseHandler.closeConnection(rs, pst);
		}
		return false;
		
	}
	
	/**
	 * Verify to check if the selected user has a credit card in the database -> 
	 * @param accId
	 * @return false if no credit card, else true
	 */
	public static boolean verifyUserCard(int accId){
		PreparedStatement pst = null;
		ResultSet rs = null;	
		boolean status = false;
		
		try {
			//Prepare statement to query the credit card table for the requested accountid
			pst = DatabaseHandler.getSQLConnection().prepareStatement("Select * from CreditCard where accountid=?");
			pst.setInt(1, accId);
			rs = pst.executeQuery();
			status = rs.next();	
			//If the user has an account - return true
			if(status) {
				return status;
			}
			//They had no card linked
			return status;		
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DatabaseHandler.closeConnection(rs, pst);
		}
		return status;
	}

}
