package Servlet;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Data.DutchAuction;
import Handler.AuctionHandler;
import Handler.DatabaseHandler;


/**
 * UpdateBid Servlet, handles the backend logic for 
 * the Ajax on the Dutch Auctions 
 * @author Mike
 */
public class UpdateBidServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdateBidServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
	 * Queries the database and calculates the current bid price
	 * for Dutch Auctions 
	 * @author Michael
	 * @return LinkedList<EnglishAuction>
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int auctId = -1;
		//string representation of auction id
		String tmpId = request.getParameter("auctid");
		//get current session
		HttpSession session = request.getSession(false);
		DutchAuction auctionInfo;
		String lang;
		//set the languge to English as default, if French then set to french
		if(session==null || session.getAttribute("language") == null )
			lang = "Language.lang_en_ca";
		else
			lang = session.getAttribute("language").toString();
		//Set the resource bundle to the current language
		ResourceBundle resource = ResourceBundle.getBundle(lang);
		Locale curLocale;
		//set locale
		if(lang == "Language.lang_en_ca") 
			curLocale = Locale.CANADA;
		else 
			curLocale = Locale.CANADA_FRENCH;
		//format number to current instance
		NumberFormat format = NumberFormat.getCurrencyInstance(curLocale);
		//parse out auctionid
		if(tmpId!=null)
			auctId = Integer.parseInt(request.getParameter("auctid"));
		
		//redirect if failure
		if(auctId < 0){
			session.setAttribute("redirectLink", "index.jsp");
			session.setAttribute("redirectMsg", resource.getString("redirecterror"));
			response.sendRedirect("redirect.jsp");
		}
		
		//Get all Dutch Auction Info referenced from auction ID
		DutchAuction currentDutchAuction = AuctionHandler.getDutchAuctionInfo(auctId);
		
		//calculate current bid value
		long elapse = (System.currentTimeMillis() - currentDutchAuction.getModifiedTimeStamp().getTime())/(1000*60);
		int step = (int)elapse/Integer.parseInt(currentDutchAuction.getInterval());
		float currentBidValue = currentDutchAuction.getInitialCost() - (step*currentDutchAuction.getDecrement());
		currentDutchAuction.setBidValue(currentBidValue);
		
		PreparedStatement pst = null;
		
		//end auction if current bid is lower than minimum value
		if(currentBidValue < currentDutchAuction.getMinimumCost())
		{
			try {
				pst = DatabaseHandler.getSQLConnection().prepareStatement("UPDATE DutchAuction SET isactive=false WHERE id=?");
				pst.setInt(1, auctId);
				pst.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				DatabaseHandler.closeConnection(null, pst);
			}
			
			session.setAttribute("redirectLink", "/ArtAuction/AuctionListServlet");
			session.setAttribute("redirectMsg", resource.getString("redirecterror"));
			response.sendRedirect("redirect.jsp");
			return;
		}
		//else store current bidValue in the database
		else
		{
			try
			{
				pst = DatabaseHandler.getSQLConnection().prepareStatement("UPDATE DutchAuction SET bidvalue=? WHERE id=?");
				pst.setFloat(1, currentBidValue);
				pst.setInt(2, auctId);
				pst.executeUpdate();
			
			}catch(Exception ex){
				ex.printStackTrace();
			}finally{
				DatabaseHandler.closeConnection(null, pst);
			}
		}
		//format bid value to Locale and return the value
		String value = format.format(currentBidValue);
		response.getWriter().append(value);
	}
}
