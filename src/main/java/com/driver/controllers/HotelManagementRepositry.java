package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;

import java.util.*;

public class HotelManagementRepositry {

    HashMap<String , Hotel> hotelDb = new HashMap<>(); // hotel database
    HashMap<Integer , User> userDb = new HashMap<>(); // user database
    HashMap<String, Booking> bookingDb = new HashMap<String, Booking>(); // booking database

    HashMap<String, Integer> userRent = new HashMap<>(); // bookingId and amountToBePaid handling database

    public String addHotel(Hotel hotel) {
        String hotelName = hotel.getHotelName();

        if(hotelName==null) {
            return "FAILURE";
        } else if(hotelDb.containsKey(hotelName)) {
            return "FAILURE";
        } else {
            hotelDb.put(hotelName, hotel);
        }
        return "SUCCESS";
    }

    public Integer addUser(User user) {
        Integer aadharNo  = user.getaadharCardNo();

        userDb.put(aadharNo, user);

        return aadharNo;
    }

    public String getHotelWithMostFacilities() {
//        String ans  = "";
//        int size=-1;
//
//        for(String k: hotelDb.keySet()) {
//            Hotel hotel= hotelDb.get(k);
//            List<Facility> listFacility = hotel.getFacilities();
//
//
//            if(listFacility.size()>size) {
//                size=listFacility.size();
//                ans = k;
//
//            } else if(listFacility.size()==size) {
//                String temp=k;
//                if(ans.compareTo(temp)<0) {
//
//                } else {
//                    ans = k;
//                }
//            }
//        }
//
//        if(size==-1) return "";
//        else return ans;
        int maxFacility = 0;
        for(String key : hotelDb.keySet()) {
            List<Facility> facilities = hotelDb.get(key).getFacilities();
            maxFacility = Math.max(maxFacility, facilities.size());
        }

        if(maxFacility ==0) return "";

        List<String> hotelNames = new ArrayList<>();
        for(String key: hotelDb.keySet()) {
            List<Facility> facilities= hotelDb.get(key).getFacilities();
            if(facilities.size() == maxFacility) hotelNames.add(key);
        }

        Collections.sort(hotelNames);
        return hotelNames.get(0);
    }

    public int bookARoom(Booking booking) {
//        String bookingid = booking.getBookingId();
//        String hotelName = booking.getHotelName();
//
//        Hotel hotel = hotelDb.get(hotelName);
//        int roomCount = hotel.getAvailableRooms();
//
//        // if available room is insufficient then return -1
//        int getNoOfRooms = booking.getNoOfRooms();
//        if(roomCount< getNoOfRooms) {
//            return -1;
//        }
//
//        booking.setAmountToBePaid(1000);
//
//
//        int totalCost= getNoOfRooms * booking.getAmountToBePaid();
//
//        bookingDb.put(bookingid, booking);
//
//        return totalCost;

        String hotelName = booking.getHotelName();
        if(!hotelDb.containsKey(hotelName)) return -1;
        if(hotelDb.get(hotelName).getAvailableRooms()>=booking.getNoOfRooms()) {
            Hotel hotel = hotelDb.get(hotelName);
            int totalRoomAvailable  = hotel.getAvailableRooms();
            totalRoomAvailable -= booking.getNoOfRooms();
            hotel.setAvailableRooms(totalRoomAvailable);
            hotelDb.put(hotelName, hotel);

            String bookingId = UUID.randomUUID() + "";
            System.out.println(bookingId+ "bookingId");
            int amountToBePaid = hotel.getPricePerNight() * booking.getNoOfRooms();
            bookingDb.put(bookingId, booking);
            userRent.put(bookingId, amountToBePaid);
            System.out.println(amountToBePaid+"Amount to Paid");
            return amountToBePaid;
        }
        return -1;
    }

    public int getBookings(Integer aadharCard) {

        int ans = 0;
        for(String k: bookingDb.keySet()) {
            if(aadharCard.equals(bookingDb.get(k).getBookingAadharCard())) {
                ans++;
            }
        }
        return ans;
    }

    Facility facility;
    public  Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {

        if(hotelDb.containsKey(hotelName)==false) return null;
        Hotel hotel= hotelDb.get(hotelName);
        List<Facility> facilities = hotel.getFacilities();

        // here we are checking that if it not contains then add else not add
        for(int i=0; i<newFacilities.size(); i++) {
            if(!facilities.contains(newFacilities.get(i))) facilities.add(newFacilities.get(i));
        }

        // updating the hotel db as well
        hotel.setFacilities(facilities);
        hotelDb.put(hotelName, hotel);
        return hotel;
    }
}
