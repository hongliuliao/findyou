package com.findyou.service;

import java.sql.SQLException;

import com.findyou.data.DataContext;
import com.findyou.data.IDataContext;

import domain.businessEntity.userinfo.UserInfo;
import android.R.integer;

import domain.businessEntity.*;


public class PhoneService implements IPhoneService {
	//�������ַ���
	private static String Tag="CardinfoService";
	
	//������һ��ctx����
	private IDataContext ctx;
	
	//���캯��
	public PhoneService(){
		ctx=new DataContext();		
	}
	
	@Override
	public UserInfo getUserInfo(int id) {
		// TODO Auto-generated method stub
		UserInfo userinfo=null;
		try {
			userinfo=ctx.queryById(UserInfo.class, Integer.class,id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userinfo;
	}

	@Override
	public void saveUserInfo(UserInfo userinfo) {
		// TODO Auto-generated method stub
		try {
			ctx.add(userinfo, UserInfo.class, Integer.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void updateUserInfo(UserInfo userinfo) {
		// TODO Auto-generated method stub
		try {
			ctx.update(userinfo, UserInfo.class, Integer.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
