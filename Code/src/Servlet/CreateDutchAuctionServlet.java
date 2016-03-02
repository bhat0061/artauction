package Servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ResourceBundle;
import java.text.NumberFormat;
import java.util.Currency;

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
 * Servlet implementation class CreateDutchAuctionServlet
 */
@MultipartConfig
public class CreateDutchAuctionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String lang;
		if(session==null || session.getAttribute("language") == null )
			lang = "Language.lang_en_ca";
		else
			lang = session.getAttribute("language").toString();
		ResourceBundle resource = ResourceBundle.getBundle(lang);
		
		String title = request.getParameter("title");
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
		String description = request.getParameter("description");
		float bidValue = Float.NaN;
		int bidderID = 0;
		float initialValue = Float.NaN;
		float minimumCost = Float.NaN;
		float decrement = Float.NaN;
		int interval = 0;
		try
		{
			 bidValue = Float.parseFloat(request.getParameter("initialValue"));
			 initialValue = Float.parseFloat(request.getParameter("initialValue"));
			 minimumCost = Float.parseFloat(request.getParameter("minimumCost"));
			 decrement = Float.parseFloat(request.getParameter("decrement"));
			 interval = Integer.parseInt(request.getParameter("interval"));
		}
		catch(Exception e)
		{
			request.setAttribute("errorMessage", resource.getString("properprice"));
			request.getRequestDispatcher("createDutchAuction.jsp").forward(request, response);
			return;
		}
		if(bidValue < 0.01 || initialValue < 0.01 || minimumCost < 0.01 || decrement < 0.01 || interval < 0){
			request.setAttribute("errorMessage", resource.getString("properneg"));
			request.getRequestDispatcher("createDutchAuction.jsp").forward(request,response);
			return;
		}
		if(interval < 1 || interval > 10){
			request.setAttribute("errorMessage", resource.getString("properinterval"));
			request.getRequestDispatcher("createDutchAuction.jsp").forward(request, response);
			return;
		}

		String[] fileExtSplit = fileName.split("\\.");
		String fileExt = fileExtSplit[fileExtSplit.length-1];
		if(!fileExt.equals("png") && !fileExt.equals("jpg")){
			request.setAttribute("errorMessage", resource.getString("properimage"));
			request.getRequestDispatcher("createDutchAuction.jsp").forward(request, response);
			return;
		}
		
		AuctionHandler.createDutchAuction(((Account)session.getAttribute("account")).getId(), title, fullFileName, description, bidValue, bidderID, initialValue, minimumCost, decrement, interval);
		session.setAttribute("redirectLink", "/ArtAuction/AuctionListServlet");
		session.setAttribute("redirectMsg", resource.getString("auctionredirect"));
		RequestDispatcher rd = request.getRequestDispatcher("redirect.jsp");
		rd.forward(request, response);
	}

}
