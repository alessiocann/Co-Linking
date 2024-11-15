package main.model;

public class Activity {
    private String companyName;
    private boolean accomodation;
    private boolean lunch;
    private boolean teamBuilding;
    private float priceAccomodation;
    private float priceTeamBuilding;
    private float priceLunch;

    public Activity(String companyName, Integer accomodation, Integer lunch, Integer teamBuilding, Float priceAccomodation, Float priceLunch, Float priceTeamBuilding){
        this.companyName = companyName;
        this.accomodation = (accomodation != 0);
        this.lunch = (lunch != 0);
        this.teamBuilding = (teamBuilding != 0);
        this.priceAccomodation = priceAccomodation;
        this.priceLunch = priceLunch;
        this.priceTeamBuilding = priceTeamBuilding;
    }

    public float getPriceAccomodation() {
        return priceAccomodation;
    }

    public boolean isAccomodation() {
        return accomodation;
    }

    public boolean isLunch() {
        return lunch;
    }

    public boolean isTeamBuilding() {
        return teamBuilding;
    }

    public float getPriceTeamBuilding() {
        return priceTeamBuilding;
    }

    public float getPriceLunch() {
        return priceLunch;
    }

    public String getCompanyName() {
        return companyName;
    }
}
