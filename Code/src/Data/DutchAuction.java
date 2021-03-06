package Data;
import java.sql.ResultSet;
import java.sql.Timestamp;
/**
 * DutchAuction object
 * Stores all the relevant data to a Dutch auction
 * @author Shawn Pottle
 *
 */
public class DutchAuction {
	private int id;
	private int accountid;
	private int bidderid;
	private String title;
	private int imageid;
	private String imageUrl;
	private String description;
	private boolean isActive;
	private boolean isApproved;
	private boolean isNotified;
	private Timestamp createdTimeStamp;
	private Timestamp modifiedTimeStamp;
	private float initialCost;
	private float minimumCost;
	
	private float bidValue;
	private float decrement;
	private String interval;

	/**
	 * Constructor to build the DutchAuction object
	 * Generated by extracting the values from a ResultSet
	 * @param rs
	 */
	public DutchAuction(ResultSet rs){
		try{
			setTitle(rs.getString("Title"));
			setId(rs.getInt("id"));
			setAccountid(rs.getInt("accountid"));
			setBidderid(rs.getInt("bidderid"));
			setImageid(rs.getInt("imageid"));
			setDescription(rs.getString("description"));
			setActive(rs.getBoolean("isactive"));
			setApproved(rs.getBoolean("isapproved"));
			setNotified(rs.getBoolean("isnotified"));
			setCreatedTimeStamp(rs.getTimestamp("createdtimestamp"));
			setModifiedTimeStamp(rs.getTimestamp("modifiedtimestamp"));
			setInitialCost(rs.getFloat("initialcost"));
			setMinimumCost(rs.getFloat("minimumcost"));
			
			setBidValue(rs.getFloat("bidvalue"));
			setDecrement(rs.getFloat("decrement"));
			setInterval(rs.getString("step"));
		}
		catch(Exception e){}
	}

	/**
	 * Get id
	 * @return
	 */
	public int getId() {
		return id;
	}
	/**
	 * Set id
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Get accountid
	 * @return
	 */
	public int getAccountid() {
		return accountid;
	}
	/**
	 * Set accountid
	 * @param accountid
	 */
	public void setAccountid(int accountid) {
		this.accountid = accountid;
	}
	/**
	 * Get title
	 * @return
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * Set title
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * Get imageid
	 * @return
	 */
	public int getImageid() {
		return imageid;
	}
	/**
	 * Set imageid
	 * @param imageid
	 */
	public void setImageid(int imageid) {
		this.imageid = imageid;
	}
	/**
	 * Get imageUrl
	 * @return
	 */
	public String getImageUrl() {
		return imageUrl;
	}
	/**
	 * Set imageUrl
	 * @param imageUrl
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	/**
	 * Get description
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * Set description
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * Get isActive
	 * @return
	 */
	public boolean isActive() {
		return isActive;
	}
	/**
	 * Set isActive
	 * @param isActive
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	/**
	 * Get isApproved
	 * @return
	 */
	public boolean isApproved() {
		return isApproved;
	}
	/**
	 * Set isApproved
	 * @param isApproved
	 */
	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}
	/**
	 * Get createdTimeStamp
	 * @return
	 */
	public Timestamp getCreatedTimeStamp() {
		return createdTimeStamp;
	}
	/**
	 * Set createdTimeStamp
	 * @param createdTimeStamp
	 */
	public void setCreatedTimeStamp(Timestamp createdTimeStamp) {
		this.createdTimeStamp = createdTimeStamp;
	}
	/**
	 * Get modifiedTimeStamp
	 * @return
	 */
	public Timestamp getModifiedTimeStamp() {
		return modifiedTimeStamp;
	}
	/**
	 * Set modifiedTimeStamp
	 * @param modifiedTimeStamp
	 */
	public void setModifiedTimeStamp(Timestamp modifiedTimeStamp) {
		this.modifiedTimeStamp = modifiedTimeStamp;
	}
	/**
	 * Get bidderid
	 * @return
	 */
	public int getBidderid() {
		return bidderid;
	}
	/**
	 * Set bidderid
	 * @param bidderid
	 */
	public void setBidderid(int bidderid) {
		this.bidderid = bidderid;
	}
	/**
	 * Get initialCost
	 * @return
	 */
	public float getInitialCost() {
		return initialCost;
	}
	/**
	 * Set initialCost
	 * @param initialCost
	 */
	public void setInitialCost(float initialCost) {
		this.initialCost = initialCost;
	}
	/**
	 * Get minimumCost
	 * @return
	 */
	public float getMinimumCost() {
		return minimumCost;
	}
	/**
	 * Set minimumCost
	 * @param minimumCost
	 */
	public void setMinimumCost(float minimumCost) {
		this.minimumCost = minimumCost;
	}
	/**
	 * Get bidValue
	 * @return
	 */
	public float getBidValue() {
		return bidValue;
	}
	/**
	 * Set bidValue
	 * @param bidValue
	 */
	public void setBidValue(float bidValue) {
		this.bidValue = bidValue;
	}
	/**
	 * Get interval
	 * @return
	 */
	public String getInterval() {
		return interval;
	}
	/**
	 * Set interval
	 * @param interval
	 */
	public void setInterval(String interval) {
		this.interval = interval;
	}
	/**
	 * Get decrement
	 * @return
	 */
	public float getDecrement() {
		return decrement;
	}
	/**
	 * Set decrement
	 * @param decrement
	 */
	public void setDecrement(float decrement) {
		this.decrement = decrement;
	}
	/**
	 * Get isNotified
	 * @return
	 */
	public boolean isNotified() {
		return isNotified;
	}
	/**
	 * Set isNotified
	 * @param isNotified
	 */
	public void setNotified(boolean isNotified) {
		this.isNotified = isNotified;
	}
}
