package Servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Data.Account;
import Handler.LoginHandler;

/**
 * Login Servlet to collect the user submitted form data and call various methods to validate the user data.
 * Depending on the outcome of validation the Servlet either requests the login.jsp to allow the user to fix the data
 * or loginWelcome.jsp to welcome the user after a succesfull login
 * @author Brodie
 *
 */
public class LoginServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	/**
	 * doPost called when the form is submitted, validates the form data and decides on next action
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		//Get username from form
		String user = request.getParameter("username");
		//Get userpass from form
		String password = request.getParameter("userpass");
				
		HttpSession session = request.getSession();		
		String lang;
		if(session==null || session.getAttribute("language") == null )
			lang = "Language.lang_en_ca";
		else
			lang = session.getAttribute("language").toString();
		ResourceBundle resource = ResourceBundle.getBundle(lang);
		
		//Create a byte [] of the userpass, getting ready to encrypt the password
		byte [] bufferPass = password.getBytes(Charset.forName("UTF-8"));
		//Create MessageDigest to provide functionality of the encryption algorithm
		MessageDigest md = null;
		
		//Attempt to set the encryption algorithm to MD5
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		//Update the message digest with the byte[] of the password
		md.update(bufferPass);
		//Complete the hash computation of the bye[] to receive the hash of the password
		bufferPass = md.digest();
		
		//Convert the byte[] of the hash to hex
		String hexHash = new BigInteger(1,bufferPass).toString(16);
					
		//Attempt to validate that the username and hashed password are present in the database
		Account account = LoginHandler.validate(user, hexHash);
		if(account != null && account.getEmail() != null){
			session.setAttribute("account", account);
			session.setAttribute("redirectLink", "/ArtAuction/MailDispatcherServlet");
			session.setAttribute("redirectMsg", resource.getString("redirectlogin"));
			RequestDispatcher rd = request.getRequestDispatcher("redirect.jsp");
			rd.forward(request, response);
		}
		//User entered wrong password/user doesn't exist
		else {
			out.print("<p style=\"color:red\">Sorry username or password error</p>");
			//Request login page for the user to try again
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.include(request, response);
		}
		out.close();
	}
}
