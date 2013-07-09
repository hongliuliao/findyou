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
	
	public static final int SUCCESS_CODE = 0;
	
	public static final int FAIL_CODE = 1;

	private int code;
	
	private String msg;
	
	private T data;
	
	public CodeMsg(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	public T getData() {
		return data;
	}
	public CodeMsg<T> setData(T data) {
		this.data = data;
		return this;
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
