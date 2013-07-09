/**
 * 
 */
package com.findyou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.findyou.model.CodeMsg;
import com.findyou.model.LocationInfo;
import com.findyou.service.LocationService;

/**
 * 
 * @author liaohongliu
 *
 * 创建日期:2013-7-9 下午9:40:48
 */
@Controller
public class LocationController {

	private LocationService locationService = new LocationService();
	
	@ResponseBody
	@RequestMapping(value = "/getAddr")
	public CodeMsg<LocationInfo> getLocationInfo(@RequestParam("userId") long userId) {
		LocationInfo info = this.locationService.getLocationInfo(userId);
		if(info == null) {
			return new CodeMsg<LocationInfo>(CodeMsg.FAIL_CODE, "LocationInfo not found which userId:" + userId);
		}
		return new CodeMsg<LocationInfo>(CodeMsg.SUCCESS_CODE, "success").setData(info);
	}
	
	@ResponseBody
	@RequestMapping(value = "/saveAddr")
	public CodeMsg<Void> saveLocationInfo(
			@RequestParam("userId") long userId,
			@RequestParam("latitude") double latitude,
			@RequestParam("lontitude") double lontitude,
			@RequestParam("radius") float radius,
			@RequestParam("addr") String addr) {
		boolean result = locationService.saveLocationInfo(userId, latitude, lontitude, radius, addr);
		if(result) {
			return new CodeMsg<Void>(CodeMsg.SUCCESS_CODE, "success");
		}else {
			return new CodeMsg<Void>(CodeMsg.FAIL_CODE, "save fail!");
		}
	}
}
