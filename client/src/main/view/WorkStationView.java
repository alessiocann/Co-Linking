package main.view;

public class WorkStationView {
    public static void workstationDetails(String id, String companyName, Integer capacity,
                                   String city, String address, String description, float price) {
        System.out.println("Workstation:");
        System.out.println("Id: " + id);
        System.out.println("Company: " + companyName);
        System.out.println("Capacity: " + capacity);
        System.out.println("City: " + city);
        System.out.println("Address: " + address);
        System.out.println("Description: " + description);
        System.out.println("Price: " + price);
    }

    public static void activityDetails(String hostname, boolean accomodation, boolean lunch,
                                          boolean teamBuilding, float priceAccomodation, float priceLunch, float priceTeamBuilding) {
        System.out.println("Activity:");
        System.out.println("Host: " + hostname);
        System.out.println("Accomodation: " + accomodation);
        System.out.println("Price Accomodation: " + priceAccomodation);
        System.out.println("Lunch: " + lunch);
        System.out.println("Price Lunch: " + priceLunch);
        System.out.println("Team Building: " + teamBuilding);
        System.out.println("Price Team Building: " + priceTeamBuilding);
    }


}
