/**
 * 
 */
package com.findyou.model;

import java.io.Serializable;

import com.findyou.data.FindyouConstants;

/**
 * @author Administrator
 *
 */
public class GetFriendLocationResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int NOT_FOUND_CODE = 10;
	
	public final static int SHOW_FRIEND = 1;

	private int id = SHOW_FRIEND;
	
	private int code = FindyouConstants.SUCCESS_CODE;
	
	private String msg;
	
	private double latitude;
	
	private double lontitude;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the lontitude
	 */
	public double getLontitude() {
		return lontitude;
	}

	/**
	 * @param lontitude the lontitude to set
	 */
	public void setLontitude(double lontitude) {
		this.lontitude = lontitude;
	}
	
}
