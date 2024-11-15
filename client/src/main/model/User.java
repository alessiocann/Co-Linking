package main.model;

public class User {
    private String id;
    private String name;
    private String mail;
    private String phoneNumber;
    private CreditCard creditCard;

    public User(String id, String name, String mail, String phoneNumber){
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
    }

    public User(String id, String name, String mail, String phoneNumber, CreditCard creditCard){
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
        this.creditCard = creditCard;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CreditCard getCreditCard(){
        return creditCard;
    }

}
