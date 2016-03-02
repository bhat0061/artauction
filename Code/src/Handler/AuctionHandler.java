package Handler;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

import Data.DutchAuction;
import Data.EnglishAuction;
/**
 * Handler for database requests regarding auctions. 
 * @author Michael
 * @author Shawn Pottle
 */
public class AuctionHandler {
	
	/**
	 * Takes passed parameters and inputs a row into the EnglishAuction table
	 * in the relational database 
	 * @author Michael
	 * @param username 
	 * @param title
	 * @param imageFileName
	 * @param description
	 * @param endOfAuction
	 */
	public static void createEnglishAuction(int id, String title, String imageFileName, String description,String endOfAuction,float startingPrice)
	{
		Connection insertImageConnection = null;
		PreparedStatement insertImagePST = null;
		ResultSet insertImageRS = null;
		
		Connection insertAuctionConnection = null;
		PreparedStatement insertAuctionPST = null;
		
		String imagePath = imageFileName;
		java.sql.Timestamp endAuctionTimeStamp = null;
		
		//Create DateFormat of 3LetterMonth  Day Year , example Nov 12 2009
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd hh:mm", Locale.ENGLISH);
		Date date;
		try {
			date = df.parse(endOfAuction);
			endAuctionTimeStamp = new Timestamp(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			//insert image information to image table and return id
			int imageId = -1;
			insertImageConnection = DatabaseHandler.getNewSQLConnection();
			insertImagePST = insertImageConnection.prepareStatement("INSERT INTO Image (imageurl,createdtimestamp,modifiedtimestamp) VALUES(?,?,?);", PreparedStatement.RETURN_GENERATED_KEYS);
			insertImagePST.setString(1, imagePath);
			Timestamp timeStamp = new Timestamp(new Date().getTime());
			insertImagePST.setTimestamp(2, timeStamp);
			insertImagePST.setTimestamp(3, timeStamp);
			insertImagePST.executeUpdate();
			insertImageRS = insertImagePST.getGeneratedKeys();
			
			if (insertImageRS != null && insertImageRS.next()) {
				imageId = insertImageRS.getInt(1);
			}
			
			DatabaseHandler.closeConnection(insertImageConnection,  insertImageRS, insertImagePST);
	
			//insert auction information to auction table
			insertAuctionConnection = DatabaseHandler.getNewSQLConnection();
			insertAuctionPST = insertAuctionConnection.prepareStatement("INSERT INTO EnglishAuction (accountid, title, imageid, description, createdtimestamp, modifiedtimestamp, closingtimestamp, startingprice) VALUES(?,?,?,?,?,?,?,?)");
			insertAuctionPST.setInt(1, id);//userID);
			insertAuctionPST.setString(2, title);
			insertAuctionPST.setInt(3, imageId);
			insertAuctionPST.setString(4, description);
			insertAuctionPST.setTimestamp(5, timeStamp);
			insertAuctionPST.setTimestamp(6, timeStamp);
			insertAuctionPST.setTimestamp(7, endAuctionTimeStamp);
			insertAuctionPST.setFloat(8,startingPrice);
			insertAuctionPST.executeUpdate();
		
			DatabaseHandler.closeConnection(insertAuctionConnection, null, insertAuctionPST);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			DatabaseHandler.closeConnection(insertAuctionConnection, null, insertAuctionPST);
		}
	}
	
	/**
	 * 
	 * Takes the passed auction id, queries the database table EnglishAuction
	 * and returns a map with the table information
	 * @author Michael
	 * @param id EnglishAuctionId
	 * @return EnglishAuction
	 */
	public static EnglishAuction getEnglishAuctionInfo(int id){
		Connection auctionConnection = null;
		PreparedStatement auctionpst = null;
		ResultSet auctionRS = null;	
		
		Connection imageConnection = null;
		PreparedStatement imagepst = null;
		ResultSet imageRS = null;	
		
		EnglishAuction auctionInfo = null;
		
		try {
			//query DB for auction table
			auctionConnection = DatabaseHandler.getNewSQLConnection();
			auctionpst = auctionConnection.prepareStatement("Select * from EnglishAuction where id=?");
			auctionpst.setInt(1, id);		
			auctionRS = auctionpst.executeQuery();	
			auctionRS.next();
			auctionInfo = new EnglishAuction(auctionRS);
			DatabaseHandler.closeConnection(auctionConnection, auctionRS, auctionpst);
			//query image table for file path
			
			imageConnection = DatabaseHandler.getNewSQLConnection();
			imagepst = imageConnection.prepareStatement("Select * from Image where id=?");
			imagepst.setInt(1, auctionInfo.getImageid());		
			imageRS = imagepst.executeQuery();	
			imageRS.next();
			auctionInfo.setImageUrl(imageRS.getString("imageurl"));
			if(auctionInfo.getImageUrl() != null){
				File f = new File(auctionInfo.getImageUrl());
				if(!f.exists()) { 
					auctionInfo.setImageUrl("C:\\Uploads\\auction.jpg");
				}
			}
			DatabaseHandler.closeConnection(imageConnection, imageRS, imagepst);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}finally{
			DatabaseHandler.closeConnection(auctionConnection, auctionRS, auctionpst);
			DatabaseHandler.closeConnection(imageConnection, imageRS, imagepst);
		}
		return auctionInfo;
	}
	
	/**
	 * Queries the database for all auctions and returns a list referencing all
	 * auction information
	 * @author Michael
	 * @return LinkedList<EnglishAuction>
	 */
	public static LinkedList<EnglishAuction> getAllEnglishAuctionRows(boolean isApproved){
		PreparedStatement pst = null;	
		PreparedStatement pst2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		int isApprovedInt;
		LinkedList<EnglishAuction> englishAuctionList = new LinkedList<EnglishAuction>();
		
		//set is approved integer for database
		if(isApproved)
			isApprovedInt = 1;
		else
			isApprovedInt = 0;
		
		try {
			//query DB for all english auctions
			pst = DatabaseHandler.getSQLConnection().prepareStatement("select * from EnglishAuction where isapproved=? AND isactive=1 ORDER BY createdtimestamp DESC");
			pst.setInt(1, isApprovedInt);
			rs = pst.executeQuery();
			//iterate through results
			while(rs.next()){
				EnglishAuction auction = new EnglishAuction(rs);
				Connection connection = DatabaseHandler.getNewSQLConnection(); 
				pst2 = connection.prepareStatement("Select * from Image where id=?");
				pst2.setInt(1, auction.getImageid());
				rs2 = pst2.executeQuery();	
				rs2.next();
				auction.setImageUrl(rs2.getString("imageurl"));
				//set image fields in data base
				if(auction.getImageUrl() != null){
					File f = new File(auction.getImageUrl());
					if(!f.exists()) { 
						auction.setImageUrl("C:\\Uploads\\auction.jpg");
					}
				}
				englishAuctionList.add(auction);
				DatabaseHandler.closeConnection(connection, rs2, pst2);
			}
			DatabaseHandler.closeConnection(rs, pst);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}finally{
			DatabaseHandler.closeConnection(rs, pst);
		}
		return englishAuctionList;
	}
	
	/**
	 * Updates the isApproved flag on the server to declare the auction safe for public viewing
	 * @author Richard Fyffe
	 * @param auctionID: id of auction to be approved
	 * @return boolean: 1 = success, 0 = failsure
	 */
	public static boolean approveEnglishAuction(int auctionID){
		PreparedStatement pst = null;
		boolean success = false;
		
		try {//query DB for all english auctions
			pst = DatabaseHandler.getSQLConnection().prepareStatement("UPDATE EnglishAuction SET isApproved=1 WHERE id=?");
			pst.setInt(1, auctionID);
			success = pst.executeUpdate()>0;
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			DatabaseHandler.closeConnection(null, pst);
		}
		return success;
	}
	
	/**
	 * Updates the isActive flag on the server to declare the auction over and no longer visible to anyone
	 * @author Richard Fyffe
	 * @param auctionID: id of auction to be approved
	 * @return boolean: 1 = success, 0 = failsure
	 */
	public static boolean rejectEnglishAuction(int auctionID){
		PreparedStatement pst = null;
		boolean success = false;
		
		try {
			//query DB for all english auctions
			pst = DatabaseHandler.getSQLConnection().prepareStatement("UPDATE EnglishAuction SET isActive=0 WHERE id=?");
			pst.setInt(1, auctionID);
			success = pst.executeUpdate()>0;
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			DatabaseHandler.closeConnection(null, pst);
		}
		return success;
	}

	/**
	 * Method responsible for creating the reference of the Dutch Auction 
	 * in the database
	 * @author Shawn Pottle
	 * @param id
	 * @param title
	 * @param imageFileName
	 * @param description
	 * @param bidValue
	 * @param bidderID
	 * @param initialCost
	 * @param minimumCost
	 * @param decrement
	 * @param interval
	 */
	public static void createDutchAuction(int id, String title, String imageFileName, String description, float bidValue, int bidderID, float initialCost, float minimumCost, float decrement, int interval)
	{
		PreparedStatement pst = null;
		ResultSet rs = null;	
		String imagePath = imageFileName;
		
		try {
			//insert image information to image table and return id
			int imageId = -1;
			pst = DatabaseHandler.getSQLConnection().prepareStatement("INSERT INTO Image (imageurl,createdtimestamp,modifiedtimestamp) VALUES(?,?,?);", PreparedStatement.RETURN_GENERATED_KEYS);
			pst.setString(1, imagePath);
			Timestamp timeStamp = new Timestamp(new Date().getTime());
			pst.setTimestamp(2, timeStamp);
			pst.setTimestamp(3, timeStamp);
			pst.executeUpdate();
			rs = pst.getGeneratedKeys();
			pst.close();
	
			if (rs != null && rs.next()) {
				imageId = rs.getInt(1);
			}
			//insert auction information to auction table
			pst = DatabaseHandler.getSQLConnection().prepareStatement("INSERT INTO DutchAuction (accountid, title, imageid, step, initialcost, minimumcost, description, bidvalue, createdtimestamp, modifiedtimestamp, decrement) VALUES(?,?,?,?,?,?,?,?,?,?,?);");
			pst.setInt(1, id);
			pst.setString(2, title);
			pst.setInt(3, imageId);
			pst.setInt(4, interval);
			pst.setFloat(5, initialCost);
			pst.setFloat(6, minimumCost);
			pst.setString(7, description);
			pst.setFloat(8, bidValue);
			pst.setTimestamp(9, timeStamp);
			pst.setTimestamp(10, timeStamp);
			pst.setFloat(11, decrement);
			pst.executeUpdate();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}finally{
			DatabaseHandler.closeConnection(rs, pst);
		}
	}
	/**
	 * Method responsible for returning a DutchAuction object with
	 * the information of the Dutch auction in the database with
	 * ID 'id'
	 * @author Shawn Pottle
	 * @param id
	 * @return
	 */
	public static DutchAuction getDutchAuctionInfo(int id){
		PreparedStatement pst = null;
		ResultSet rs = null;			
		DutchAuction auctionInfo = null;
		
		try {
			//query DB for auction table
			pst = DatabaseHandler.getSQLConnection().prepareStatement("Select * from DutchAuction where id=?");
			pst.setInt(1, id);		
			rs = pst.executeQuery();	
			rs.next();
			auctionInfo = new DutchAuction(rs);
			
			//query image table for file path
			pst.close();
			pst = DatabaseHandler.getSQLConnection().prepareStatement("Select * from Image where id=?");
			pst.setInt(1, auctionInfo.getImageid());		
			rs = pst.executeQuery();	
			rs.next();
			auctionInfo.setImageUrl(rs.getString("imageurl"));
			if(auctionInfo.getImageUrl() != null){
				File f = new File(auctionInfo.getImageUrl());
				if(!f.exists()) { 
					auctionInfo.setImageUrl("C:\\Uploads\\auction.jpg");
				}
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}finally{
			DatabaseHandler.closeConnection(rs, pst);
		}
		return auctionInfo;
	}
	/**
	 * Method responsible for getting a list of all Dutch Auctions
	 * in DutchAuction objects
	 * @author Shawn Pottle
	 * @param isApproved
	 * @return
	 */
	public static LinkedList<DutchAuction> getAllDutchAuctionRows(boolean isApproved) {
		PreparedStatement pst = null;		
		ResultSet rs = null;
		int isApprovedInt;
		LinkedList<DutchAuction> dutchAuctionList = new LinkedList<DutchAuction>();
		
		if(isApproved)
			isApprovedInt = 1;
		else
			isApprovedInt = 0;
		
		try {
			//query DB for all dutch auctions
			pst = DatabaseHandler.getSQLConnection().prepareStatement("select * from DutchAuction where isapproved=? AND isactive=1 ORDER BY createdtimestamp DESC");
			pst.setInt(1, isApprovedInt);
			rs = pst.executeQuery();
			//iterate through results
			while(rs.next()){
				ResultSet rs2 = null;
				PreparedStatement pst2 = null;
				DutchAuction auction = new DutchAuction(rs);
				Connection connection = DatabaseHandler.getNewSQLConnection();
				pst2 = connection.prepareStatement("Select * from Image where id=?");
				pst2.setInt(1, rs.getInt("imageid"));		
				rs2 = pst2.executeQuery();	
				rs2.next();
				auction.setImageUrl(rs2.getString("imageurl"));
				if(auction.getImageUrl() != null){
					File f = new File(auction.getImageUrl());
					if(!f.exists()) { 
						auction.setImageUrl("C:\\Uploads\\auction.jpg");
					}
				}
				dutchAuctionList.add(auction);
				DatabaseHandler.closeConnection(connection, rs2, pst2);
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}finally{
			DatabaseHandler.closeConnection(rs, pst);
		}
		return dutchAuctionList;
	}
	
	/**
	 * Updates the isApproved flag on the server to declare the auction safe for public viewing
	 * @author Richard Fyffe
	 * @param auctionID: id of auction to be approved
	 * @return boolean: 1 = success, 0 = failsure
	 * 
	 */
	public static boolean approveDutchAuction(int auctionID){
		PreparedStatement pst = null;
		boolean success = false;
		
		try {
			//query DB for all dutch auctions
			pst = DatabaseHandler.getSQLConnection().prepareStatement("UPDATE DutchAuction SET isApproved=1 WHERE id=?");
			pst.setInt(1, auctionID);
			success = pst.executeUpdate()>0;
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			DatabaseHandler.closeConnection(null, pst);
		}
		return success;
	}
	
	/**
	 * Updates the isActive flag on the server to declare the auction over and no longer visible to anyone
	 * @author Richard Fyffe
	 * @param auctionID: id of auction to be approved
	 * @return boolean: 1 = success, 0 = failsure
	 */
	public static boolean rejectDutchAuction(int auctionID){
		PreparedStatement pst = null;
		boolean success = false;
		
		try {
			//query DB for all dutch auctions
			pst = DatabaseHandler.getSQLConnection().prepareStatement("UPDATE DutchAuction SET isActive=0 WHERE id=?");
			pst.setInt(1, auctionID);
			success = pst.executeUpdate()>0;
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			DatabaseHandler.closeConnection(null, pst);
		}
		return success;
	}
}