/**
 * 
 */
package com.findyou.model;

/**
 * 
 * @author liaohongliu
 *
 * 创建日期:2013-7-6 上午9:59:57
 */
public class CodeMsg<T> {

	private int code;
	
	private String msg;
	
	private T data;
	
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
