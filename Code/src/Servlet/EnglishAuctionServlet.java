package Servlet;



import java.io.IOException;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Data.EnglishAuction;
import Data.Bid;
import Handler.AuctionHandler;
import Handler.BidHandler;

/**
 * EnglishAuction Servlet, handles the backend logic for 
 * EnglishAuction URL 
 */
public class EnglishAuctionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * Queries database for information required for englishAuction.jsp view
	 * and redirects to the view
	 * @author Mike
	 * @param request
	 * @param response
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int auctId = -1;
		String tmpId = request.getParameter("auctid");
		String errorMsg = request.getParameter("errorMessage");
		HttpSession session = request.getSession(false);
		EnglishAuction auctionInfo;
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
		
		//Grab all bids referenced by auction ID
		LinkedList<Bid> allBids = BidHandler.getAllBidsInAuction(auctId);
		//Get info of english auction referenced by auction ID
		auctionInfo = AuctionHandler.getEnglishAuctionInfo(auctId);	
		session.setAttribute("Auction", auctionInfo);
		session.setAttribute("AllBids", allBids);
		if(errorMsg != null){
			request.setAttribute("errorMessage", errorMsg);
		}
		RequestDispatcher rd = request.getRequestDispatcher("EnglishAuction.jsp");
		rd.forward(request, response);		
	}
}
