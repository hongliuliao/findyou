/**
 * 
 */
package com.findyou.utils;

/**
 * @author Administrator
 *
 */
public class StringUtils {

	public static boolean isBlank(String str) {
		return str == null || str.trim().equals(""); 
	}
	
	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}
}
