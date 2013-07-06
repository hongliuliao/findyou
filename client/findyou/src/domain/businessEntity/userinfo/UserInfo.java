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
	public void setUserID(int id){
		this.userID=id;
	}
	public int getUserID(){
		return this.userID;
	}
	
	public void setPhoneNumber(String phone){
		this.phoneNumber=phone;
	}
	public String getPhoneNumber(){
		return this.phoneNumber;
	}
}
