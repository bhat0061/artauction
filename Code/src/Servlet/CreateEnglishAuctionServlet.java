package Servlet;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import Data.Account;
import Handler.AuctionHandler;

/**
 * CreateEnglishAuction Servlet, handles the backend logic for 
 * CreateEnglishAuction URL 
 * @author Mike
 */
@MultipartConfig
public class CreateEnglishAuctionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * Handles form parameters from user input for auction information and submits it.
	 * @author Michael
	 * @param request
	 * @param response
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String lang;
		//set language for Locale
		if(session==null || session.getAttribute("language") == null )
			lang = "Language.lang_en_ca";
		else
			lang = session.getAttribute("language").toString();
		//set Resource bundle to language
		ResourceBundle resource = ResourceBundle.getBundle(lang);
		
		//Grab info from form fields
		String title = request.getParameter("title");
		float startingPrice = 0;
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		//get file name of image
		final Part filePart = request.getPart("filePath");
		String fileName = filePart.getSubmittedFileName();
		String[] splitName = fileName.split("\\\\");
		String fileEnd = splitName[splitName.length -1 ];
		String fullFileName = "C:\\Uploads\\" + fileEnd;
		InputStream fileContent = filePart.getInputStream();
		FileInputStream fileStream = (FileInputStream) fileContent;
		OutputStream outputStream = new FileOutputStream(new File(fullFileName));
		int read = 0;
		byte[] bytes = new byte[1024];

		while ((read = fileStream.read(bytes)) != -1) {
			outputStream.write(bytes, 0, read);
		}
		outputStream.close();
		
		//String imageFileName = imagePathSplit[imagePathSplit.length -1 ];		
		String description = request.getParameter("description");
		String endOfAuction = request.getParameter("datetimepicker");
		Date date = new Date(endOfAuction);
		if(date.before(new Date())){
			request.setAttribute("errorMessage", resource.getString("properdate"));
			request.getRequestDispatcher("createEnglishAuction.jsp").forward(request, response);
			return;
		}
		String startingPriceString = request.getParameter("startingPrice");
		try {
			startingPrice = Float.parseFloat(startingPriceString);
		}catch(Exception ex){
			request.setAttribute("errorMessage", resource.getString("propervalues"));
			request.getRequestDispatcher("createEnglishAuction.jsp").forward(request, response);
			return;
		}
		if(startingPrice < 0.01){ 
			request.setAttribute("errorMessage", resource.getString("properprice"));
			request.getRequestDispatcher("createEnglishAuction.jsp").forward(request,response);
			return;
		}
		String[] fileExtSplit = fileName.split("\\.");
		String fileExt = fileExtSplit[fileExtSplit.length-1];
		if(!fileExt.equals("png") && !fileExt.equals("jpg")){
			request.setAttribute("errorMessage", resource.getString("properimage"));
			request.getRequestDispatcher("createEnglishAuction.jsp").forward(request, response);
			return;
		}
		AuctionHandler.createEnglishAuction(((Account)session.getAttribute("account")).getId(), title, fullFileName, description, endOfAuction,startingPrice);
		session.setAttribute("redirectLink", "/ArtAuction/AuctionListServlet");
		session.setAttribute("redirectMsg", resource.getString("auctionredirect"));
		RequestDispatcher rd = request.getRequestDispatcher("redirect.jsp");
		rd.forward(request, response);
		
	}
}
