package com.findyou.service;

import java.sql.SQLException;

import com.findyou.data.DataContext;
import com.findyou.data.IDataContext;
import com.findyou.model.UserInfo;



public class PhoneService implements IPhoneService {
	
	//声明了一个ctx变量
	private IDataContext ctx;
	
	//构造函数
	public PhoneService(){
		ctx = new DataContext();		
	}
	
	@Override
	public UserInfo getUserInfo(int id) {
		UserInfo userinfo=null;
		try {
			userinfo=ctx.queryById(UserInfo.class, Integer.class,id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userinfo;
	}

	@Override
	public void saveUserInfo(UserInfo userinfo) {
		try {
			ctx.add(userinfo, UserInfo.class, Integer.class);
		} catch (SQLException e) {
			throw new RuntimeException("saveUserInfo error which userInfo:" + userinfo, e);
		}
	}

	@Override
	public void updateUserInfo(UserInfo userinfo) {
		try {
			ctx.update(userinfo, UserInfo.class, Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
