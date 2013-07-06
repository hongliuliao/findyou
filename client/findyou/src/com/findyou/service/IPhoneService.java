package com.findyou.service;

import domain.businessEntity.userinfo.UserInfo;

public interface IPhoneService {
public UserInfo getUserInfo(int id);
public void saveUserInfo(UserInfo userinfo);
public void updateUserInfo(UserInfo userinfo);
}
