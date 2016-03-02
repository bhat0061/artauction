package Handler;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/** 
 * @author Archit
 * This class is used to handle the email system of the project. This class uses Session and Properties to complete the task.
 */
public class MailHandler {

	/** SendEmail method is used to send emails to the winner and owner of the auction using the SMTP port
	 *  @param String winnerEmail, String auctioneerEmail, String subject, String message.
	 *  @return void
	 * 
	 * */	public static void SendEmail(String winnerEmail, String auctioneerEmail, String subject,
			String message) {

		final String fromEmail = "auctionsite11@gmail.com"; /*this is the email address of the sender, since we have only one email, we make it final*/

		Properties props = System.getProperties();/*creates a new object props of type properties */
		props.put("mail.smtp.host", "mail.smtp2go.com"); /*sets the host of the props*/
		props.put("mail.smtp.auth", "true");/*sets the authorization as true*/
		props.put("mail.smtp.port", "2525"); /*Sets the port number used to send the mails.*/
		props.put("mail.smtp.starttls.enable", "true"); /*sets the tls security to on*/

		/*creates a new object of type Session which is uses javax.mail to send the email to the winner*/
		Session mailSession = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(
								fromEmail, "auctionlist");
					}
				}); // for the winner
		
		/*creates a new object of type Session which is uses javax.mail to send the email to the owner*/
		Session mailSession1 = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(
								fromEmail, "auctionlist");
					}
				}); // for the owner
		
		/*try and catch block to trace the error if the message sending fails */
		try {

			
			Message messageForOwner = new MimeMessage(mailSession); /*message object to set the message details for the Auction owner*/
			messageForOwner.setFrom(new InternetAddress(fromEmail));/*Sets the from email as the email of the auction website */
			messageForOwner.setRecipient(Message.RecipientType.TO, new InternetAddress(auctioneerEmail)); /*Sets the recipient as the email address of the auction owner*/
			messageForOwner.setSubject("Your Auction Ended");/*Sets the subject of the email to be sent to the auction owner*/

			
			/*Checks if there is any winner of the auction or if there is any bidder on the auction*/
			if(winnerEmail != null)
			{
				/*Sets the message for the owner*/
				messageForOwner
						.setText(String.format("Congratulations %s!! Your Auction has ended. The winner is %s. The winner has been notified",auctioneerEmail, winnerEmail));
				
				/*Creates a new MimeMesaage object*/
				Message messageForWinner = new MimeMessage(mailSession1);
				messageForWinner.setFrom(new InternetAddress(fromEmail));/*Sets the from email as the email of the auction website */
				messageForWinner.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(winnerEmail)); /*Sets the recipient as the email address of the auction Winner*/
				
				messageForWinner.setSubject(subject);/*Sets the subject of the email of the winner */
 
				messageForWinner
						.setText(String.format("Congratulations %s!! You Won the Auction. Please visit the website auctionList :D", winnerEmail));/*Sets the messgage for the winner's email*/

				// Send message
				Transport.send(messageForWinner); /*Sends the message to the winner using transport*/
			}
			/*If there is no winner, sets the message for the owner */
			
			else
			{
				messageForOwner
						.setText(String.format("Sorry %s!! Your Auction has ended and there wasn't any bidders. :(",auctioneerEmail));
				
			}
			Transport.send(messageForOwner); /*Sends the message to the winner using the Transport.*/
			System.out.println("Sent message successfully....");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}