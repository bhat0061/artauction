package Data;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * EnglishAuction - used as a data object for an EnglishAuction.
 * @author Mike
 */
public class EnglishAuction {
	private int id;
	private int accountid;
	private String title;
	private int imageid;
	private String imageUrl;
	private String description;
	private boolean isActive;
	private boolean isApproved;
	private boolean isNotified;
	private float startingPrice;
	private Timestamp createdTimeStamp;
	private Timestamp modifiedTimeStamp;
	private Timestamp closingTimeStamp;

	
	public EnglishAuction(ResultSet rs){
		try{
			setTitle(rs.getString("Title"));
			setId(rs.getInt("id"));
			setAccountid(rs.getInt("accountid"));
			setImageid(rs.getInt("imageid"));
			setDescription(rs.getString("description"));
			setActive(rs.getBoolean("isactive"));
			setApproved(rs.getBoolean("isapproved"));
			setNotified(rs.getBoolean("isnotified"));
			setStartingPrice(rs.getFloat("startingPrice"));
			setCreatedTimeStamp(rs.getTimestamp("createdtimestamp"));
			setModifiedTimeStamp(rs.getTimestamp("modifiedtimestamp"));
			setClosingTimeStamp(rs.getTimestamp("closingtimestamp"));
		}
		catch(Exception e){}
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

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public int getImageid() {
		return imageid;
	}
	public void setImageid(int imageid) {
		this.imageid = imageid;
	}

	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isApproved() {
		return isApproved;
	}
	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	public float getStartingPrice() {
		return startingPrice;
	}
	public void setStartingPrice(float startingPrice) {
		this.startingPrice = startingPrice;
	}

	public Timestamp getCreatedTimeStamp() {
		return createdTimeStamp;
	}
	public void setCreatedTimeStamp(Timestamp createdTimeStamp) {
		this.createdTimeStamp = createdTimeStamp;
	}

	public Timestamp getModifiedTimeStamp() {
		return modifiedTimeStamp;
	}
	public void setModifiedTimeStamp(Timestamp modifiedTimeStamp) {
		this.modifiedTimeStamp = modifiedTimeStamp;
	}

	public Timestamp getClosingTimeStamp() {
		return closingTimeStamp;
	}
	public void setClosingTimeStamp(Timestamp closingTimeStamp) {
		this.closingTimeStamp = closingTimeStamp;
	}


	public boolean isNotified() {
		return isNotified;
	}


	public void setNotified(boolean isNotified) {
		this.isNotified = isNotified;
	}
}
