package domain.businessEntity.userinfo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "T_User")
public class UserInfo {
	@DatabaseField(generatedId = true)
	private int userID;
	@DatabaseField(canBeNull = false)
	private String phoneNumber;
	
	// ¹¹Ôìº¯Êý
	public UserInfo() {
		// ORMLite needs a no-arg constructor
	}
	
	public int getUserID(){
		return this.userID;
	}
	public String getPhoneNumber(){
		return this.phoneNumber;
	}
}
