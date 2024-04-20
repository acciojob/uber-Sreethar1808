package com.driver.services.impl;

import com.driver.model.TripBooking;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;
import com.driver.model.TripStatus;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Override
	public void register(Customer customer) {
		//Save the customer in database
		customerRepository2.save(customer);
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function
		Customer customer = customerRepository2.findById(customerId).orElse(null);
		if(customer==null)
		{
			return;
		}

		customerRepository2.deleteById(customerId);

	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query
		Customer customer = customerRepository2.findById(customerId).orElse(null);
        List<Driver> driver = driverRepository2.findAll();
		Driver pickedDriver = null;
		for(Driver driver1: driver)
		{
			if(driver1.getCab().isAvailable()==true)
			{
				pickedDriver=driver1;
				break;
			}
		}


		if(pickedDriver==null)
		{
           throw new Exception("No cab available!");
		}

		pickedDriver.setTripStatus(TripStatus.CONFIRMED);



		TripBooking tripBooking = new TripBooking();
		tripBooking.setSource(fromLocation);
		tripBooking.setDestination(toLocation);
		tripBooking.setTripDistanceInKm(distanceInKm);
        tripBooking.setTotalFare((pickedDriver.getCab().getFarPerKm()) * distanceInKm);
        tripBooking.setTripStatus(TripStatus.CONFIRMED);
//		tripBooking.getDriver().getCab().setAvailable(false);
		tripBooking.setCustomer(customer);
		tripBooking.setDriver(pickedDriver);



		TripBooking savedTrip =tripBookingRepository2.save(tripBooking);

		pickedDriver.getBookingList().add(savedTrip);
		customer.getBookings().add(savedTrip);

		customerRepository2.save(customer);
		driverRepository2.save(pickedDriver);

		return savedTrip;
	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly

		TripBooking tripBooking = tripBookingRepository2.findById(tripId).orElse(null);
		tripBooking.setTripStatus(TripStatus.CANCELED);
		tripBooking.getDriver().getCab().setAvailable(true);

		TripBooking saved = tripBookingRepository2.save(tripBooking);
	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
        TripBooking tripBooking = tripBookingRepository2.findById(tripId).orElse(null);
		tripBooking.setTripStatus(TripStatus.COMPLETED);
		tripBooking.getDriver().getCab().setAvailable(true);

		TripBooking saved = tripBookingRepository2.save(tripBooking);
	}
}
