/**
 * 
 */
package com.findyou.model;

/**
 * @author Administrator
 *
 */
public class LocationInfo {

	/**
	 * �û���ϢΨһ��ʾ
	 */
	private String id;
	/**
	 * �û�Ψһ��ʾ,�������ֻ���
	 */
	private String userId;
	
	/**
	 * γ��
	 */
	private double latitude;
	/**
	 * ����
	 */
	private double lontitude;
	
	private float radius;
	
	private String addr;
	
	/**
	 * @return the radius
	 */
	public float getRadius() {
		return radius;
	}
	/**
	 * @param radius the radius to set
	 */
	public void setRadius(float radius) {
		this.radius = radius;
	}
	/**
	 * @return the addr
	 */
	public String getAddr() {
		return addr;
	}
	/**
	 * @param addr the addr to set
	 */
	public void setAddr(String addr) {
		this.addr = addr;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
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
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	
}
