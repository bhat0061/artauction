package Servlet;


import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.*;
import javax.servlet.http.*;

import Data.DutchAuction;
import Data.EnglishAuction;
import Handler.AuctionHandler;

/**
 * AdminViewAuctionListServlet, handles the backend logic for retrieving unapproved auctions
 * @author Richard Fyffe
 */
public class AdminViewAuctionListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Gets all unapproved auctions (English and Dutch) and stores them in the session, 
	 * redirects administrator to AdminControlsAuctionList to view them
	 * 
	 * @author Richard Fyffe
	 * @param request
	 * @param response
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		LinkedList<EnglishAuction> approvedEnglishAuctionList = AuctionHandler.getAllEnglishAuctionRows(false);
		LinkedList<DutchAuction> approvedDutchAuctionList = AuctionHandler.getAllDutchAuctionRows(false);
		session.setAttribute("unapprovedEnglishAuctionList", approvedEnglishAuctionList);
		session.setAttribute("unapprovedDutchAuctionList", approvedDutchAuctionList);
		
		RequestDispatcher rd = request.getRequestDispatcher("AdminControlsAuctionList.jsp");
		rd.forward(request, response);
	}
}
