/**
 * 
 */
package com.findyou.service;

import android.test.AndroidTestCase;

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
}
