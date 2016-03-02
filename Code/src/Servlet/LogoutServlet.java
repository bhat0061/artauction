package Servlet;


import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Logout Servlet, handles the backend logic for 
 * Logout URL 
 * @author Mike
 */
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Destroys user session on post for Logout URL and redirects to login.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().invalidate();
		
		HttpSession session = request.getSession();
		String lang;
		if(session==null || session.getAttribute("language") == null )
			lang = "Language.lang_en_ca";
		else
			lang = session.getAttribute("language").toString();
		ResourceBundle resource = ResourceBundle.getBundle(lang);
		session.setAttribute("redirectLink", "index.jsp");
		session.setAttribute("redirectMsg", resource.getString("redirectlogout"));
        response.sendRedirect("redirect.jsp");
	}

}
