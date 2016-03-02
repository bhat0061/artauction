package Data;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * Credit card class - used as a data object for a Credit Card.
 * @author Brodie
 *
 */
public class CreditCard {
	private int id;
	private int accountid;
	private String cardtype;
	private int ccv;
	private String cardnumber;
	private String holdername;
	private Timestamp expiryTimeStamp;
	private Timestamp createdTimeStamp;
	
	/**
	 * Default constructor for creating CreditCard object
	 * @param rs
	 */
	public CreditCard(ResultSet rs){
		try{
			setId(rs.getInt("id"));
			setAccountid(rs.getInt("accountid"));
			setCardType(rs.getString("cardtype"));
			setCcv(rs.getInt("ccv"));
			setCardNumber(rs.getString("cardnumber"));
			setExpiryDate(rs.getTimestamp("expirydatestamp"));
			setCreatedTimeStamp(rs.getTimestamp("createdtimestamp"));
		}
		catch(Exception ex){}
	}	
	
	/**
	 * Get the created timestamp
	 * @return
	 */
	public Timestamp getCreatedTimeStamp() {
		return createdTimeStamp;
	}
	
	/**
	 * Set the created timestamp
	 * @param timestamp
	 */
	public void setCreatedTimeStamp(Timestamp timestamp) {
		this.createdTimeStamp = timestamp;
	}
	
	/**
	 * Get the credit card id
	 * @return
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Set the credit card id
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Get the credit card id
	 * @return
	 */
	public int getAccountid() {
		return accountid;
	}
	
	/**
	 * Set the account id
	 * @param accountid
	 */
	public void setAccountid(int accountid) {
		this.accountid = accountid;
	}
	
	/**
	 * Get the credit card type
	 * @return
	 */
	public String getCardType() {
		return cardtype;
	}
	
	/**
	 * Set the credit card type
	 * @param cardtype
	 */
	public void setCardType(String cardtype) {
		this.cardtype = cardtype;
	}
	
	/**
	 * Get the credit card number
	 * @return
	 */
	public String getCardNumber() {
		return cardnumber;
	}
	
	/**
	 * Set the credit card number
	 * @param cardNumber
	 */
	public void setCardNumber(String cardNumber) {
		this.cardnumber = cardNumber;
	}
	
	/**
	 * Get the card holder name
	 * @return
	 */
	public String getHolderName(){
		return holdername;
	}
	
	/**
	 * Set the card holder name
	 * @param holdername
	 */
	public void setHolderName(String holdername){
		this.holdername = holdername;
	}
	
	/**
	 * Get the expiry date
	 * @return
	 */
	public Timestamp getExpiryDate(){
		return expiryTimeStamp;
	}
	
	/**
	 * Set the expiry date
	 * @param stamp
	 */
	public void setExpiryDate(Timestamp stamp){
		this.expiryTimeStamp = stamp;
	}
	
	/**
	 * Get the ccv
	 * @return
	 */
	public int getCcv(){
		return ccv;
	}
	
	/**
	 * Set the ccv
	 * @param ccv
	 */
	public void setCcv(int ccv){
		this.ccv = ccv;
	}
}
