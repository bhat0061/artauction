package Servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Handler.DatabaseHandler;

/**
 * Servlet implementation class ImageRequestServlet
 * @author Shawn Pottle
 */
public class ImageRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * Queries the database for the image location by imageID
	 * Responds with the image found, or responds with an error image
	 * @author Shawn Pottle
	 * @param request
	 * @param response
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Retrieves the imageID via the request parameters(in doGet will be shown in the URL)
		int imageID = Integer.parseInt(request.getParameter("id"));
		
		File image;
		String imageLocation = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		//Set the response type to "image/jpg" to allow the browser to know that
		//the response is an image
		response.setContentType("image/jpg");
		//Image to use if no image could be found at the desired location
		String imageError = "C:\\Uploads\\not-found.png";
		
		try {
			//query DB for Image with id 'imageID'
			pst = DatabaseHandler.getSQLConnection().prepareStatement("Select * from Image where id=?");
			pst.setInt(1, imageID);
			rs = pst.executeQuery();
			//If found, set imageLocation to the found database's entries imageurl
			if(rs.next()) 
			{
				imageLocation = rs.getString("imageurl");
			} 
			//Else, set imageLocation to the imageError
			else 
			{
				imageLocation = imageError;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			//Close the database connection through the handler
			DatabaseHandler.closeConnection(rs, pst);
		}
		//Set the 'image' file to the image at 'imageLocatin'
		if(imageLocation != null)
		{
			image = new File(imageLocation);
		}
		else
		{
			image = null;
		}
		//Create the FileInputStream pointing to the 'image' file
		FileInputStream inputStream;
		try{

			 inputStream = new FileInputStream(image);
		}
		catch(Exception ex)
		{
			inputStream = new FileInputStream(new File(imageError));
		}
		//Create the output stream for the response to send the image to the location requested
		OutputStream outputStream = response.getOutputStream();
		int read = 0;
		byte[] bytes = new byte[1024];
		//Send the image byte by byte from the inputStream to the outputStream
		while ((read = inputStream.read(bytes)) != -1) {
			outputStream.write(bytes, 0, read);
		}
		//Close both streams
		inputStream.close();
		outputStream.close();
	}

}