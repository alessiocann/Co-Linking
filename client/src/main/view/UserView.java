package main.view;

import java.time.LocalDateTime;

public class UserView {

    public static void showHost(String vatNumber, String name, String city, String address, String mail, String phoneNumber) {
        System.out.println("Host");
        System.out.println("Name: " + name);
        System.out.println("VAT Number: " + vatNumber);
        System.out.println("City: " + city);
        System.out.println("Address: " + address);
        System.out.println("Mail: " + mail);
        System.out.println("Phone Number: " + phoneNumber);
    }

    public static void showUser(String id, String name, String mail, String phoneNumber) {
        System.out.println("User");
        System.out.println("Id: " + id);
        System.out.println("Name: " + name);
        System.out.println("Mail: " + mail);
        System.out.println("Phone Number: " + phoneNumber);
    }

    public static void showBooking(Integer id, LocalDateTime timestamp, String username, String companyName, String workstation) {
        System.out.println("User");
        System.out.println("Id: " + id);
        System.out.println("Timestamp: " + timestamp);
        System.out.println("User: " + username);
        System.out.println("Company name: " + companyName);
        System.out.println("Workstation: " + workstation);
    }

}
