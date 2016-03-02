package Handler;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import Data.Bid;
/**
 * Class for handling all database logic regarding bidding
 * @author Archit
 *
 */
public class BidHandler {

	/**
	 * 
	 * This method is used to create a new bid 
	 * @author Archit
	 * @param acntid = accountid with which the user logs in.
	 * 			itemId = the id of the auction id.
	 * 			value = the amount of the bid added by the user
	 * 			userName = username of the user
	 * 			name = name of the user
	 * @return void
	 */
	
	
	
	public static void createNewBid(int acntId , int itemId , float value , String userName , String name ){
		PreparedStatement pst = null;
		ResultSet rs = null;
		boolean test = false;
		
		try {
			pst = DatabaseHandler.getSQLConnection().prepareStatement("SELECT * FROM Bid where itemid=? ORDER BY createdtimestamp ASC");
			pst.setInt(1, itemId);
			rs = pst.executeQuery();
			
			if(test == false){
			java.util.Date today = new java.util.Date();
			
			pst.close();
	 		pst = DatabaseHandler.getSQLConnection().prepareStatement("INSERT INTO Bid (accountid,itemid,bidvalue,createdtimestamp ) VALUES(?,?,?,?)");
			pst.setInt(1,acntId);
			pst.setInt(2,itemId);
			pst.setFloat(3,value );
			pst.setTimestamp(4,new java.sql.Timestamp(today.getTime())  );
 			pst.execute();
			}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DatabaseHandler.closeConnection(rs, pst);
		}
	
	}
	 /**
	  * When this method is called, it returns a list of all the bids that are currently placed on the Auction
	  * @return LinkedList of the Bids
	  * @param id = id of the auction
	  * */
	public static LinkedList<Bid> getAllBidsInAuction(int id) {
		PreparedStatement pst = null;
		ResultSet rs = null;
	 	LinkedList<Bid> allBidsInAuction = new LinkedList<Bid>(); //creates a new empty list of all the bids
		
		try {
			pst = DatabaseHandler.getSQLConnection().prepareStatement("Select * from Bid where itemid=? ORDER BY createdtimestamp DESC  ");
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if(rs==null)
				return null;
			// iterate through results
			while (rs.next()) { //while there are rows in the Bid
				allBidsInAuction.add(buildBid(rs)); //calls buildBid function and passes rs as param to set user name to the bid
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			DatabaseHandler.closeConnection(rs, pst); //closes the connections
		}
		return allBidsInAuction;
	}
	/**
	 * This method is used to get the account associated with the bid and assigns a userName to the Bid associated woith the account.
	 * @param rs = ResultSet
	 * @return Bid 
	 * */
	
	public static Bid buildBid(ResultSet rs){
		Bid bid = new Bid(rs); //new object of Bid
		PreparedStatement pst = null;
		Connection connection = null;
		try{
			connection = DatabaseHandler.getNewSQLConnection();
			pst = connection.prepareStatement("Select * from Account where id=?");
			pst.setInt(1, bid.getAccountid());
			rs = pst.executeQuery();
			rs.next();
			bid.setUserName(rs.getString("username"));
		}
		catch(Exception ex){}
		finally{
			DatabaseHandler.closeConnection(connection, rs, pst);
		}
		return bid;
	}
}

