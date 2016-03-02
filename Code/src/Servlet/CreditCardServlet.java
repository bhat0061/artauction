package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Data.Account;
import Handler.CreditCardHandler;
import Handler.EncryptionHandler;

/**
 * Class used to validate and collect the data submitted in credit card servlet
 * @author Brodie
 *
 */
public class CreditCardServlet extends HttpServlet {
	 
	/** Unique generated serial version ID */
	private static final long serialVersionUID = 351296085678977582L;
	
	/**
	 * Implemented doPost for the collection of posted data from the credit card servlet
	 * Grabs all the post parameters from the stream, encrypting the credit card number and 
	 * Inserting the card information into the DB
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Set the current session
		HttpSession session = request.getSession(); 
		String lang;
		if(session==null || session.getAttribute("language") == null ) {
			lang = "Language.lang_en_ca";
		}
		else {
			lang = session.getAttribute("language").toString();
		}
		//Set the current resource bundle
		ResourceBundle resource = ResourceBundle.getBundle(lang);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		//Collect the various request parameters from the request
		String cardholder = request.getParameter("card-holder-name").toString();
		String cardNumber = request.getParameter("card-number").toString();
		String expiryMonth = request.getParameter("expiry-month").toString();
		String expiryYear = request.getParameter("expiry-year").toString();
		String ccv = request.getParameter("cvv").toString();
		String address = request.getParameter("address").toString();
		String city = request.getParameter("city").toString();
		String province = request.getParameter("province").toString();
		String postalCode = request.getParameter("postalcode").toString();

		//Regex validation to validate the credit card number -> 0-9 and 16 digits
		if(!cardNumber.matches("^[0-9]{16}")) {
			request.setAttribute("errorMessage", resource.getString("invalidcreditcard"));
			request.getRequestDispatcher("AddCreditCard.jsp").forward(request, response);
		}
		//Regex validation to validate the CCV -> 0-9 and 3 digits
		else if(!ccv.matches("^[0-9]{3}")){
			request.setAttribute("errorMessage", resource.getString("invalidccv"));
			request.getRequestDispatcher("AddCreditCard.jsp").forward(request, response);
		}
		//Regex valdiation to validate a postal code 
		else if(!postalCode.matches("[a-zA-Z][0-9][a-zA-Z] ?[0-9][a-zA-Z][0-9]")) {
			request.setAttribute("errorMessage", resource.getString("invalidpostalcode"));
			request.getRequestDispatcher("AddCreditCard.jsp").forward(request, response);
		}
		//The form has valid information -> attempt to add the information to the DB
		else {
			String encryptedString = null;
			try {
				//Encrypt the credit card number for security purposes
				encryptedString = EncryptionHandler.encrypt(cardNumber);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Parse our string ccv to an int
			int ccvNum = Integer.parseInt(ccv);
			//Grab the account id of the current user
			int accid  = ((Account)session.getAttribute("account")).getId();
			//Attempt to insert the credit card information into the database
			if(CreditCardHandler.addCreditCard(cardholder, encryptedString, expiryMonth, expiryYear, ccvNum, accid, address,city,province,postalCode)) {
				session.setAttribute("redirectLink", "/ArtAuction/AuctionListServlet");
				session.setAttribute("redirectMsg", resource.getString("creditcardapprove"));
				RequestDispatcher rd = request.getRequestDispatcher("redirect.jsp");
				rd.forward(request, response);
			}			
		}
	}
}
