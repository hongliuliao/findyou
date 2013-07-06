/**
 * 
 */
package com.findyou.service;

import android.test.AndroidTestCase;

import com.findyou.model.CodeMsg;
import com.findyou.model.LocationInfo;

/**
 * @author Administrator
 *
 */
public class LocationServiceTest extends AndroidTestCase {

	LocationService locationService = new LocationService();
	
	public void testGetUserLocation() {
		LocationInfo info = locationService.getUserLocation("1234");
		assertTrue(info != null);
	}
	
	public void testSaveUserLocaltion() {
		LocationInfo info = new LocationInfo();
		info.setUserId("36");
		info.setLatitude(39.915);
		info.setLontitude(116.404);
		info.setRadius(44.5f);
		info.setAddr("ÖÐ¹ú");
		
		CodeMsg result = this.locationService.saveUserLocaltion(info);
		assertTrue(result.getCode() == 0);
	}
}
