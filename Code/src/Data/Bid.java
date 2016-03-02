package Data;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Collections;

/**
 * Bid - used as a data object for an Bid.
 * @author Archit
 */
public class Bid {
	private int id;
	private int accountid;
	private int itemid;
	private float value;
	private String userName;
	private String name;
	
	public Bid(ResultSet rs){
		try{
			setId(rs.getInt("id"));
			setItemid(rs.getInt("itemid"));
			setAccountid(rs.getInt("accountid"));
			setValue(rs.getFloat("bidvalue"));
			setCreatedTimeStamp(rs.getTimestamp("createdtimestamp"));
		}
		catch(Exception ex){}
	}	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	private Timestamp createdTimeStamp;
	public Timestamp getCreatedTimeStamp() {
		return createdTimeStamp;
	}
	public void setCreatedTimeStamp(Timestamp timestamp) {
		this.createdTimeStamp = timestamp;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAccountid() {
		return accountid;
	}
	public void setAccountid(int accountid) {
		this.accountid = accountid;
	}
	public int getItemid() {
		return itemid;
	}
	public void setItemid(int itemid) {
		this.itemid = itemid;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	
	public String encryptBidUsername(String userName){
		if(userName.length() > 2) {
			userName = userName.replace(userName.substring(1,userName.length()-1),String.join("", Collections.nCopies(userName.length()-2, "*")));
		}
		else {
			userName = userName.substring(0,userName.length()-1) +"*";
		}
		return userName;
	}
}
