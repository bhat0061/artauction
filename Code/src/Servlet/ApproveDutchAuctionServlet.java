package Servlet;


import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import Data.DutchAuction;
import Handler.AuctionHandler;

/**
 * ApproveDutchAuctionServlet handles the approve call and redirects back to adminViewAuctionListServlet
 * @author Richard Fyffe
 */
public class ApproveDutchAuctionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	/**
	 * 
	 * Calls AuctionHandler function to update auction to be approved then redirects to AdminViewAuctionListServlet
	 * @author Richard Fyffe
	 * @param request
	 * @param response
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		String lang;
		HttpSession session = request.getSession(false);
		if(session==null || session.getAttribute("language") == null )
			lang = "Language.lang_en_ca";
		else
			lang = session.getAttribute("language").toString();
		ResourceBundle resource = ResourceBundle.getBundle(lang);
		
		DutchAuction auction = (DutchAuction)session.getAttribute("Auction");
		
		
		if(AuctionHandler.approveDutchAuction(auction.getId())){
			session.setAttribute("redirectLink", "AdminViewAuctionListServlet");
			session.setAttribute("redirectMsg", resource.getString("auctionapproved"));
			response.sendRedirect("redirect.jsp");
		}
	}
}
