package com.driver.controllers;

import com.driver.controllers.HotelManagementRepositry;
import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelManagementService {


    HotelManagementRepositry hotelManagementRepositry = new HotelManagementRepositry();

    public String addHotel(Hotel hotel) {
        String ans = hotelManagementRepositry.addHotel(hotel);

        return ans;
    }

    public Integer addUser(User user) {
        Integer ans  = hotelManagementRepositry.addUser(user);

        return ans;
    }

    public String getHotelWithMostFacilities() {
        String ans = hotelManagementRepositry.getHotelWithMostFacilities();

        return ans;
    }

    public int bookAaRoom(Booking booking) {
        int ans = hotelManagementRepositry.bookARoom(booking);

        return ans;
    }

    public int getBookings(Integer aadharCard) {
        int ans = hotelManagementRepositry.getBookings(aadharCard);

        return ans;
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {

        Hotel hotel = hotelManagementRepositry.updateFacilities(newFacilities, hotelName);
        return hotel;
    }
}
