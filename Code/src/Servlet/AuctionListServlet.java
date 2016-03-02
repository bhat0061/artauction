package Servlet;


import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Data.DutchAuction;
import Data.EnglishAuction;
import Handler.AuctionHandler;

/**
 * AuctionList Servlet, handles the backend logic for 
 * AuctionList URL 
 * @author Mike
 */
public class AuctionListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * 
	 * Calls auction list handler on get request to retrieve all auction row information
	 * and stores the table information in a request to be displayed in the 
	 * auctionlist.jsp view.
	 * @author Mike
	 * @param request
	 * @param response
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		//Get all english auctions that are approved
		LinkedList<EnglishAuction> approvedEnglishAuctionList = AuctionHandler.getAllEnglishAuctionRows(true);
		
		//Get all dutch auctions that are approved
		LinkedList<DutchAuction> approvedDutchAuctionList = AuctionHandler.getAllDutchAuctionRows(true);
		 
		//pass auction objects to session
		session.setAttribute("englishAuctionList", approvedEnglishAuctionList);
		session.setAttribute("dutchAuctionList", approvedDutchAuctionList);
		
		//redirect
		RequestDispatcher rd = request.getRequestDispatcher("auctionlist.jsp");
		rd.forward(request, response);		
	}
}
