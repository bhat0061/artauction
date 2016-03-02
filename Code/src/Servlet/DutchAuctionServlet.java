package Servlet;



import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Data.DutchAuction;
import Data.Bid;
import Handler.AuctionHandler;
import Handler.BidHandler;
import Handler.DatabaseHandler;

/**
 * DutchAuction Servlet, handles the backend logic for 
 * DutchAuction URL 
 * @author Shawn Pottle
 */
public class DutchAuctionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * Queries database for information required for dutchAuction.jsp view
	 * and redirects to the view
	 * @author Shawn Pottle
	 * @param request
	 * @param response
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Get the auction id via the request parameters
		int auctId = -1;
		String tmpId = request.getParameter("auctid");
		//Get the current session
		HttpSession session = request.getSession(false);
		DutchAuction auctionInfo;
		//Retrieve the language currently being viewed with, if none selected, default to english
		String lang;
		if(session==null || session.getAttribute("language") == null )
			lang = "Language.lang_en_ca";
		else
			lang = session.getAttribute("language").toString();
		ResourceBundle resource = ResourceBundle.getBundle(lang);
		
		//parse out auctionid
		if(tmpId!=null)
			auctId = Integer.parseInt(request.getParameter("auctid"));
		
		//redirect if failure
		if(auctId < 0){
			session.setAttribute("redirectLink", "index.jsp");
			session.setAttribute("redirectMsg", resource.getString("redirecterror"));
			response.sendRedirect("redirect.jsp");
		}
		
		//Create a DutchAuction object with the values of the current auction
		DutchAuction currentDutchAuction = AuctionHandler.getDutchAuctionInfo(auctId);
		//Calculate the current bid value
		long elapse = (System.currentTimeMillis() - currentDutchAuction.getModifiedTimeStamp().getTime())/(1000*60);
		int step = (int)elapse/Integer.parseInt(currentDutchAuction.getInterval());
		float currentBidValue = currentDutchAuction.getInitialCost() - (step*currentDutchAuction.getDecrement());
		//Set the current bid value to the DutchAuction object
		currentDutchAuction.setBidValue(currentBidValue);
		
		//Calculate the time until the auction closes
		float steps = ((currentDutchAuction.getInitialCost() - currentDutchAuction.getMinimumCost()) / currentDutchAuction.getDecrement());
		int interval = Integer.parseInt(currentDutchAuction.getInterval());
		int timeInMinutesTilClose = (int)steps*interval;
		
		Connection updateConnection = null;
		PreparedStatement updatePST = null;
		//Check if the current bid value is less than the minimum cost
		//If yes, then set the auction to inactive
		if(currentBidValue < currentDutchAuction.getMinimumCost())
		{
			try {
				updateConnection = DatabaseHandler.getNewSQLConnection();
				updatePST = updateConnection.prepareStatement("UPDATE DutchAuction SET isactive=false WHERE id=?");
				updatePST.setInt(1, auctId);
				updatePST.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				DatabaseHandler.closeConnection(updateConnection, null, updatePST);
			}
			
			session.setAttribute("redirectLink", "/ArtAuction/AuctionListServlet");
			session.setAttribute("redirectMsg", resource.getString("redirecterror"));
			response.sendRedirect("redirect.jsp");
			return;
		}
		//Else set the new bid value to the auction in the database
		else
		{
			try
			{
				updateConnection = DatabaseHandler.getNewSQLConnection();
				updatePST = updateConnection.prepareStatement("UPDATE DutchAuction SET bidvalue=? WHERE id=?");
				updatePST.setFloat(1, currentBidValue);
				updatePST.setInt(2, auctId);
				updatePST.executeUpdate();
			
			}catch(Exception ex){
				ex.printStackTrace();
			}finally{
				DatabaseHandler.closeConnection(updateConnection, null, updatePST);
			}
		}
		
		//Get all the bids for the current auction
		LinkedList<Bid> allBids = BidHandler.getAllBidsInAuction(auctId);
		//Get the auction information for the current auction
		auctionInfo = AuctionHandler.getDutchAuctionInfo(auctId);
		
		//get end time stamp
		long ONE_MINUTE_IN_MILLIS = 60000;//millisecs
	    long curTimeInMs = auctionInfo.getCreatedTimeStamp().getTime();
	    Date afterAddingMins = new Date(curTimeInMs + (timeInMinutesTilClose * ONE_MINUTE_IN_MILLIS));
		
	    //Set the needed information into the session
		session.setAttribute("EndTime", afterAddingMins.toString());
		session.setAttribute("Auction", auctionInfo);
		session.setAttribute("AllBids", allBids);
		RequestDispatcher rd = request.getRequestDispatcher("DutchAuction.jsp");
		rd.forward(request, response);		
	}
}
