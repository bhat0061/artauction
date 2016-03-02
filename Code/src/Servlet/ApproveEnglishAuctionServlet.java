package Servlet;


import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import Data.EnglishAuction;
import Handler.AuctionHandler;

/**
 * ApproveEnglishAuctionServlet handles the approve call and redirects back to adminViewAuctionListServlet
 * @author Richard Fyffe
 */
public class ApproveEnglishAuctionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * Calls AuctionHandler function to update auction to be approved then redirects to AdminViewAuctionListServlet
	 * @author Richard Fyffe
	 * @param request
	 * @param response
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String lang;
		if(session==null || session.getAttribute("language") == null )
			lang = "Language.lang_en_ca";
		else
			lang = session.getAttribute("language").toString();
		ResourceBundle resource = ResourceBundle.getBundle(lang);
		EnglishAuction auction = (EnglishAuction)session.getAttribute("Auction");
		
		
		if(AuctionHandler.approveEnglishAuction(auction.getId())){
			session.setAttribute("redirectLink", "AdminViewAuctionListServlet");
			session.setAttribute("redirectMsg", resource.getString("auctionapproved"));
			response.sendRedirect("redirect.jsp");
		}
	}
}
