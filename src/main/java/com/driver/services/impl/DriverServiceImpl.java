package com.driver.services.impl;

import com.driver.model.Cab;
import com.driver.model.TripStatus;
import com.driver.repository.CabRepository;
import com.driver.services.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Driver;
import com.driver.repository.DriverRepository;

@Service
public class DriverServiceImpl implements DriverService {

	@Autowired
	DriverRepository driverRepository3;

	@Autowired
	CabRepository cabRepository3;

	@Override
	public void register(String mobile, String password){
		//Save a driver in the database having given details and a cab with ratePerKm as 10 and availability as True by default.
        Driver driver = new Driver();
		Cab cab = new Cab();
		driver.setMobNo(mobile);
		driver.setPassword(password);

		cab.setCabNo("754");
		cab.setFarPerKm(10);
		cab.setAvailable(true);
		cab.setCarModel("innova");


		driver.setCab(cab);

		driverRepository3.save(driver);
	}

	@Override
	public void removeDriver(int driverId){
		// Delete driver without using deleteById function
       driverRepository3.deleteById(driverId);
	}

	@Override
	public void updateStatus(int driverId){
		//Set the status of respective car to unavailable
        Driver driver = driverRepository3.findById(driverId).orElse(null);
		if(driver==null)
		{
			return;
		}
		if(driver.getTripStatus()== TripStatus.CONFIRMED)
		{
			driver.getCab().setAvailable(false);
		}


		driverRepository3.save(driver);
	}
}
