package main.model;

import java.util.HashMap;


public class Workstation {
    private String id;
    private String companyName;
    private Integer maxCapacity;
    private String city;
    private String address;
    private String description;
    private float price;

    private HashMap<String, Integer> capacityByDate;

    public Workstation(String id, String companyName, Integer maxCapacity, String city, String address, String description, float price){
        this.id = id;
        this.companyName = companyName;
        this.maxCapacity = maxCapacity;
        this.city = city;
        this.address = address;
        this.description = description;
        this.capacityByDate = new HashMap<>();
        this.price = price;
    }

    public int getCapacityByDate(String date) {
        return capacityByDate.getOrDefault(date, maxCapacity);
    }

    public void setCapacityByDate(String date, int capacity) {
        this.capacityByDate.put(date, capacity);
    }

    public float getPrice() {
        return price;
    }

    public String getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }
}
