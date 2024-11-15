package main.controller;

import main.model.Activity;
import main.model.Workstation;
import main.view.WorkStationView;

import java.util.HashMap;
import java.util.Map;

public class WorkstationController {
    private static WorkstationController workstationController;


    public WorkstationController() {}

    public Workstation setWorkstation(String id, String companyName, Integer capacity, String city, String address, String description, float price) {
        Workstation workstation = new Workstation(id, companyName, capacity, city, address, description, price);

        return workstation;
    }

    public Activity setActivity(String companyName, Integer accomodation, Integer lunch, Integer teamBuilding, Float priceAccomodation, Float priceLunch, Float priceTeamBuilding) {
        Activity activity = new Activity(companyName, accomodation, lunch, teamBuilding, priceAccomodation, priceLunch, priceTeamBuilding);

        return activity;
    }

    public int getCapacityByDate(Workstation workstation, String date){
        return workstation.getCapacityByDate(date);
    }

    public void setCapacityByDate(Workstation workstation, String date, int capacity){
         workstation.setCapacityByDate(date, capacity);
    }

    public String getCompanyName(Workstation workstation){
        String companyName = workstation.getCompanyName();

        return companyName;
    }

    public String getId(Workstation workstation){

        return workstation.getId();
    }

    public Float getPriceLunch(Activity activity){
        float priceLunch = activity.getPriceLunch();

        return priceLunch;
    }

    public Float getPriceAccomodation(Activity activity){
        float priceAccomodation = activity.getPriceAccomodation();

        return priceAccomodation;
    }

    public Float getPriceTeamBuilding(Activity activity){
        float priceTeamBuilding = activity.getPriceTeamBuilding();

        return priceTeamBuilding;
    }

    public void showData(HashMap<String, Workstation> workstationHashMap){
        for (Map.Entry<String, Workstation> entry : workstationHashMap.entrySet()) {
            WorkStationView.workstationDetails(entry.getValue().getId(),entry.getValue().getCompanyName(),
                    entry.getValue().getMaxCapacity(), entry.getValue().getCity(), entry.getValue().getAddress(),
                    entry.getValue().getDescription(), entry.getValue().getPrice());
        }
    }

    public void showActivity(HashMap<String, Activity> activityMap, String companyName){
        for (Map.Entry<String, Activity> entry : activityMap.entrySet()) {
            if(entry.getValue().getCompanyName().equals(companyName)) {
                WorkStationView.activityDetails(entry.getValue().getCompanyName(), entry.getValue().isAccomodation(),
                        entry.getValue().isLunch(), entry.getValue().isTeamBuilding(), entry.getValue().getPriceAccomodation(), entry.getValue().getPriceLunch(),
                        entry.getValue().getPriceTeamBuilding());
            }
        }
    }
}
