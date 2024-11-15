package main.model;

public class Host extends User {
    private String city;
    private String address;

    public Host(String id, String name, String address, String city, String mail, String phoneNumber){
        super(id, name, mail, phoneNumber);
        this.city = city;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

}
