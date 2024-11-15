package main.controller;

import main.model.*;
import main.view.UserView;

import java.util.HashMap;
import java.util.Map;

public class UserController {

    private static UserController userController;

    public UserController() {}

    public Host setHost(String id, String name, String address, String city, String mail, String phoneNumber) {
        Host host = new Host(id, name, address, city, mail, phoneNumber);

        return host;
    }

    public User setUser(String id, String name, String mail, String phoneNumber, CreditCard creditCard) {
        User user = new User(id, name, mail, phoneNumber, creditCard);

        return user;
    }

    public CreditCard setCreditCard(String number, String cvv, String expireDate){
        CreditCard creditCard = new CreditCard(number, cvv, expireDate);

        return creditCard;
    }

    public Booking bookWorkstation(User user, Workstation workstation, String date){

        Booking booking = new Booking(user, workstation, date);

        return booking;
    }

    public String getCity(Host host) {
        return host.getCity();
    }

    public String getId(User user) {
        return user.getId();
    }

    public String getName(User user) {
        return user.getName();
    }

    public String getAddress(Host host) {
        return host.getAddress();
    }

    public String getBookingDate(Booking booking) {
        return booking.getDateBooking();
    }

    public CreditCard getCreditCard(User user) { return  user.getCreditCard(); }

    public String getNumberCard(CreditCard creditCard){
        return creditCard.getNumber();
    }

    public String getCvv(CreditCard creditCard){
        return creditCard.getCvv();
    }

    public String getExpireDate(CreditCard creditCard){
        return creditCard.getExpireDate();
    }

    public void showDataBookingHost(HashMap<Integer, Booking> pendingBookingMap, Host host){
        for (Map.Entry<Integer, Booking> entry : pendingBookingMap.entrySet()) {
            String id_workstation = host.getName() + host.getCity();
            if(id_workstation.equals(entry.getValue().getWorkstation().getId())) {
                UserView.showBooking(entry.getValue().getId(), entry.getValue().getTimestamp(),
                        entry.getValue().getUser().getName(),
                        entry.getValue().getWorkstation().getCompanyName(),
                        entry.getValue().getWorkstation().getId());
            }
        }
    }

    public boolean showDataBookingUser(HashMap<Integer, Booking> map, User user) {
        boolean isBooking = false;
        for (Map.Entry<Integer, Booking> entry : map.entrySet()) {
            String id_user = user.getId();
            if (id_user.equals(entry.getValue().getUser().getId())) {
                UserView.showBooking(entry.getValue().getId(), entry.getValue().getTimestamp(),
                        entry.getValue().getUser().getName(),
                        entry.getValue().getWorkstation().getCompanyName(),
                        entry.getValue().getWorkstation().getId());

                isBooking = true;
            }
        }
        if(isBooking) return true;
        else return false;
    }
}