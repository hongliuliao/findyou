package com.findyou.service;

import com.findyou.utils.HttpClientUtils;

public class FriendService {
	public String getFriendResult(int friendID){
		HttpClientUtils friendService=new HttpClientUtils();
		//friendService.getHttpGetResult(requestUrl, phonenumber);
		return "ID:userID;Name:уехЩ;Latitude:49.915* 1E6;Longitude:117.404* 1E6;";
	}
}
