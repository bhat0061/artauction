package Data;

import java.sql.ResultSet;

/**
 * Account class - used as a data object for an Account.
 * @author Mike
 */
public class Account {
	private int id;
	private String username;
	private String email;
	private String name;
	private boolean isAdmin;
	
	//sets the Account object based on the result set
	public Account(ResultSet rs){
		try{
			setId(rs.getInt("id"));
			setUsername(rs.getString("username"));
			setEmail(rs.getString("email"));
			setName(rs.getString("name"));
			setAdmin(rs.getBoolean("isadmin"));
		}
		catch(Exception e){}
	}
	
	//ID
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	//Username
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	//Email
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	//Name
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	//IsAdmin
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	@Override
	public String toString() {
		if(this.username != null) {
			return this.username;
		}
		else {
			return "";
		}
	}
	
}