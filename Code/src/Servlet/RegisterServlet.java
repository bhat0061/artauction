package Servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Data.Account;
import Handler.RegisterHandler;

/**
 * Register Servlet is used to collect the user submitted form data and call
 * various methods to validate the data. Depending on the outcome of validation
 * it will either request user information again or redirect to succesfull
 * register page
 * 
 * @author Brodie
 *
 */
public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * doPost called when the form is submitted, validates the form data and
	 * decides on next action
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String lang;
		if(session==null || session.getAttribute("language") == null )
			lang = "Language.lang_en_ca";
		else
			lang = session.getAttribute("language").toString();
		ResourceBundle resource = ResourceBundle.getBundle(lang);
		boolean isValid = true;
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		String email = request.getParameter("email");
		String name = request.getParameter("name");
		String dateofBirth = request.getParameter("dateofbirth");
		// Create byte [] of the password
		byte[] bufferPass = password.getBytes(Charset.forName("UTF-8"));
		// Create byte [] of the confirm password
		byte[] bufferConfirmPass = confirmPassword.getBytes(Charset.forName("UTF-8"));
		// Create MessageDigest to provide functionality of the encryption
		// algorithm
		MessageDigest md = null;
		Account account = null;
		// Attempt to set the encryption algorithm to MD5
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// Update the message digest with the byte[] of the password
		md.update(bufferPass);
		// Complete the hash computation of the bye[] to receive the hash of the
		// password
		bufferPass = md.digest();

		md.update(bufferConfirmPass);
		bufferConfirmPass = md.digest();

		// Convert the byte array's to hex strings
		String hexHash = new BigInteger(1, bufferPass).toString(16);
		String hexHashConfirm = new BigInteger(1, bufferConfirmPass).toString(16);

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		// Validate that the user doesn't already exist and return error strings
		// if they exist
		String validate = RegisterHandler.validate(username, email);
		if (validate != "") {
			out.print("<p style=\"color:red\">" + resource.getString("emailexists") + "</p>");
			isValid = false;
		}

		// Validate that the two password hashes are the same, If not the
		// passwords don't match
		if (hexHash.compareTo(hexHashConfirm) != 0) {
			out.print("<p style=\"color:red\">"+ resource.getString("passwordmatch") + "</p>");
			isValid = false;
		}
		// Validate that the email addresses is valid (Based on common regex)
		if (!isValidEmailAddress(email)) {
			out.print("<p style=\"color:red\">" + resource.getString("emailnotvalid") + "</p>");
			isValid = false;
		}
		if(dateofBirth == "") {
			out.print("<p style=\"color:red\">" + resource.getString("enterdate") +"</p>");
			isValid=false;
		}

		// Do we have valid user data
		if (isValid && validate == "") {
			// Setup to request the welcome page
			RequestDispatcher rd = request.getRequestDispatcher("redirect.jsp");
			// Attempt to register the user in the database, pre-validation has
			// been done
			try {
				account = RegisterHandler.RegisterUser(username, email, hexHash, name, dateofBirth);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			// create session for newly registered user
			session.setAttribute("account", account);
			session.setAttribute("redirectLink", "/ArtAuction/AuctionListServlet");
			session.setAttribute("redirectMsg", resource.getString("redirectregister"));
			rd.forward(request, response);
		}
		// Not Valid info, either invalid form data or username/email already
		// exists, redirect to the register page
		else {
			RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
			rd.include(request, response);
		}

		out.close();
	}

	/**
	 * Validate the email according to the specified regex, if the email applies
	 * to the regex it is valid in our eyes
	 * 
	 * @param email
	 * @return boolean, if the email is valid according to the specified regex
	 */
	public boolean isValidEmailAddress(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(email);
		return m.matches();
	}
}
