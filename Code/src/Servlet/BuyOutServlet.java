package Servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Handler.DatabaseHandler;

/**
 * Servlet implementation class BuyOutServlet
 */
public class BuyOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PreparedStatement pst = null;
		String lang;
		
		try
		{
			pst = DatabaseHandler.getSQLConnection().prepareStatement("UPDATE DutchAuction SET isactive=false, bidderid=? WHERE id=?");
			pst.setInt(1, Integer.parseInt(request.getParameter("accountid")));
			pst.setInt(2, Integer.parseInt(request.getParameter("auctionid")));
			pst.execute();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally{
			DatabaseHandler.closeConnection(null, pst);
		}
		HttpSession session = request.getSession();	
		if(session==null || session.getAttribute("language") == null )
			lang = "Language.lang_en_ca";
		else
			lang = session.getAttribute("language").toString();
		ResourceBundle resource = ResourceBundle.getBundle(lang);
		
		session.setAttribute("redirectLink", "/ArtAuction/AuctionListServlet");
		session.setAttribute("redirectMsg", resource.getString("buyoutmsg"));
		RequestDispatcher rd = request.getRequestDispatcher("redirect.jsp");
		rd.forward(request, response);
	}

}
