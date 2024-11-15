package main.model;

public class CreditCard {
    String number;
    String cvv;
    String expireDate;

    public CreditCard(String number, String cvv, String expireDate) {
        this.number = number;
        this.cvv = cvv;
        this.expireDate = expireDate;
    }

    public String getNumber() {
        return number;
    }

    public String getCvv() {
        return cvv;
    }

    public String getExpireDate() {
        return expireDate;
    }
}
