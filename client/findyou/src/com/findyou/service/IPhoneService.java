package com.findyou.service;

import com.findyou.model.UserInfo;

public interface IPhoneService {
public UserInfo getUserInfo(int id);
public void saveUserInfo(UserInfo userinfo);
public void updateUserInfo(UserInfo userinfo);
}
