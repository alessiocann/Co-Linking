package main;

import main.controller.UserController;
import main.controller.WorkstationController;
import main.exception.BookingException;
import main.exception.LoginException;
import main.exception.WorkstationException;
import main.model.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;



public class CoLinking {
    private static CoLinking coLinking = null;
    public User currentUser;

    private HashMap<String, User> userMap;
    private HashMap<Integer, Booking> pendingBookingMap;
    private HashMap<Integer, Booking> bookingMap;
    private HashMap<String, Workstation> workstationMap;
    private HashMap<String, Activity> activityMap;
    private HashMap<String, Booking> bookActivityMap;


    public static CoLinking getInstance() {
        if (coLinking == null) {
            coLinking = new CoLinking();
        }
        return coLinking;
    }

    private CoLinking() {
        userMap = new HashMap<String, User>();
        workstationMap = new HashMap<String, Workstation>();
        pendingBookingMap = new HashMap<Integer, Booking>();
        bookingMap = new HashMap<Integer, Booking>();
        activityMap = new HashMap<String, Activity>();
        bookActivityMap = new HashMap<String, Booking>();
    }

    public void signup(String vatNumber, String name, String city, String address, String mail, String phoneNumber) throws LoginException {
        if (exist(userMap, vatNumber)) {
            throw new LoginException("Already exists");
        }
        UserController userController = new UserController();
        Host host = userController.setHost(vatNumber, name, address, city, mail, phoneNumber);
        userMap.put(userController.getId(host), host);

        currentUser = host;
        System.out.println("User has been created");
    }

    public void signup(String taxCode, String name, String mail, String phoneNumber, String numberCreditCard, String cvv, String expireDate) throws LoginException {
        if (exist(userMap, taxCode)) {
            throw new LoginException("Already exists");
        }

        UserController userController = new UserController();
        CreditCard creditCard  = userController.setCreditCard(numberCreditCard, cvv, expireDate);
        User user = userController.setUser(taxCode, name, mail, phoneNumber, creditCard);
        userMap.put(userController.getId(user), user);
        currentUser = user;
    }

    public void login(String id) throws LoginException {
        if (!exist(userMap, id)) {
            throw new LoginException("No user found");
        }
        currentUser = userMap.get(id);
        System.out.println("User has been logged. Welcome!");
    }

    public void createWorkStation(Host host, String description, Integer capacity, float price) throws WorkstationException {
        UserController userController = new UserController();
        WorkstationController workstationController = new WorkstationController();

        String id = userController.getName(host) + userController.getCity(host);

        if (exist(workstationMap, id)) {
            throw new WorkstationException("Workstation already exist");
        }

        Workstation workstation = workstationController.setWorkstation(id, userController.getName(host), capacity,
                userController.getCity(host), userController.getAddress(host), description, price);

        workstationMap.put(workstationController.getId(workstation), workstation);

        System.out.println("Workstation has been created");
    }

    public void bookWorkstation(User user, String id_workstation, String date) throws BookingException {
        if (!exist(workstationMap, id_workstation)) {
            throw new BookingException("Workstation doesn't exist");
        }
        UserController userController = new UserController();
        WorkstationController workstationController = new WorkstationController();

        Workstation workstation = workstationMap.get(id_workstation);
        int capacity = workstationController.getCapacityByDate(workstation, date);
        if(capacity > 0) {
            Booking booking = userController.bookWorkstation(user, workstation, date);
            pendingBookingMap.put(booking.getId(), booking);
            System.out.println("Your booking request is send");
        }
        else throw new BookingException("Maximum Capability");
    }

    public void makePayment(User user) {
        UserController userController = new UserController();
        CreditCard creditCard = userController.getCreditCard(user);

        String url = "http://localhost:8080/pay";
        HttpClient client = HttpClient.newHttpClient();

        String jsonBody = "{"
                + "\"numberCard\": \"" + userController.getNumberCard(creditCard) + "\", "
                + "\"cvv\": " + userController.getCvv(creditCard) + "\", "
                + "\"expireDate\": " + userController.getExpireDate(creditCard)
                + "}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(jsonBody))
                .build();
        try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            System.out.println(response.body());
        }
        catch (Exception e){
            System.out.println("Error payment");
        }
    }

    public void confirmBooking(int choose, int id_booking) throws BookingException {
        if(choose == 1) {
            WorkstationController workstationController = new WorkstationController();
            UserController userController = new UserController();

            Workstation workstation = pendingBookingMap.get(id_booking).getWorkstation();
            String date = userController.getBookingDate(pendingBookingMap.get(id_booking));
            int capacity = workstationController.getCapacityByDate(workstation, date);
            if (capacity > 0) {
                bookingMap.put(id_booking, pendingBookingMap.get(id_booking));
                workstationController.setCapacityByDate(workstation, date, capacity-1);
                System.out.println("Confirmed reservation: " + bookingMap.get(id_booking).getUser());
                System.out.println("In this date " + date + " the capacity is: " + workstationController.getCapacityByDate(workstation, date));
            } else throw new BookingException("Maximum Capability");
        } else if (choose == 2) {
            System.out.println("Booking refused");
        }
        pendingBookingMap.remove(id_booking);
    }

    public void showWorkstationMap(){
        WorkstationController workstationController = new WorkstationController();
        workstationController.showData(workstationMap);
    }

    public void showPendingBookingMapHost(Host host) {
        UserController userController = new UserController();
        userController.showDataBookingHost(pendingBookingMap, host);
    }

    public void showBookingUser(User user){
        UserController userController = new UserController();
        boolean pending = userController.showDataBookingUser(pendingBookingMap, user);
        if(pending) System.out.println("Your request is still pending");
        boolean confirmed = userController.showDataBookingUser(bookingMap, user);
        if(confirmed) System.out.println("Your booking is confirmed");
    }

    public String showActivities(Integer id_booking){
        String companyName = null;
        if(existBookingPending(id_booking) || existBooking(id_booking)) {
            WorkstationController workstationController = new WorkstationController();

            companyName = pendingBookingMap.get(id_booking).getWorkstation().getCompanyName();
            if(companyName == null)  companyName = bookingMap.get(id_booking).getWorkstation().getCompanyName();

            workstationController.showActivity(activityMap, companyName);
        }
        return companyName;
    }

    public Float bookActivity(String companyName, Integer accomodation, Integer lunch, Integer teamBuilding, int days){
        Float totalPriceActivity = null;
        WorkstationController workstationController = new WorkstationController();
        Activity activity = activityMap.get(companyName);
        if(accomodation == 1){
            totalPriceActivity = (workstationController.getPriceAccomodation(activity)) * days;
        }
        if(lunch == 1){
            totalPriceActivity = (workstationController.getPriceLunch(activity)) * days;
        }
        if(teamBuilding == 1){
            totalPriceActivity = (workstationController.getPriceTeamBuilding(activity)) * days;
        }

        Booking booking = new Booking(currentUser, activity);
        bookActivityMap.put(companyName, booking);

        return totalPriceActivity;
    }

    public void createActivities(Host host, Integer accomodation, Integer lunch, Integer teamBuilding, Float priceAccomodation,
                                 Float priceLunch, Float priceTeamBuilding) throws WorkstationException {
        WorkstationController workstationController = new WorkstationController();

        System.out.println("new workstation controller");
        if(exist(activityMap, host.getName())) {
            throw new WorkstationException("Activity already exist");
        }

        Activity activity = workstationController.setActivity(host.getName(), accomodation, lunch, teamBuilding, priceAccomodation, priceLunch, priceTeamBuilding);

        activityMap.put(host.getName(), activity);

        System.out.println("Activity has been created");
    }

    public void deleteBooking(Integer choose, int id_booking) throws BookingException{
        if(choose == 1) {
            WorkstationController workstationController = new WorkstationController();
            UserController userController = new UserController();
            String companyName;

            Booking booking = bookingMap.get(id_booking);

            if(booking == null) {
                Booking pending_booking = pendingBookingMap.get(id_booking);
                if(pending_booking == null) throw new BookingException("Booking not found");
                else {
                    Workstation workstation = pending_booking.getWorkstation();
                    String date = userController.getBookingDate(pendingBookingMap.get(id_booking));
                    int capacity = workstationController.getCapacityByDate(workstation, date);
                    workstationController.setCapacityByDate(workstation, date, capacity + 1);
                    companyName = workstationController.getCompanyName(workstation);
                    pendingBookingMap.remove(id_booking);
                }
            }
            else {
                Workstation workstation = booking.getWorkstation();
                String date = userController.getBookingDate(bookingMap.get(id_booking));
                int capacity = workstationController.getCapacityByDate(workstation, date);
                workstationController.setCapacityByDate(workstation, date, capacity + 1);
                companyName = workstationController.getCompanyName(workstation);
                bookingMap.remove(id_booking);
            }
            System.out.println("Booking removed");
            Booking id_activtyBooking = bookActivityMap.get(companyName);
            activityMap.remove(id_activtyBooking);
            System.out.println("Activities deleted");
        } else if (choose == 2) {
            System.out.println("Operation rejected");
        }
    }

    public void requestRefund(){
        String url = "http://localhost:8080/refund";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (Exception e) {
            System.out.println("Error refund");
        }
    }

    public boolean existBookingPending(Integer id){
        if(pendingBookingMap.get(id) != null) return true;
        else return false;
    }

    public boolean existBooking(Integer id){
        if(bookingMap.get(id) != null) return true;
        else return false;
    }

    public boolean existWorkstation(Host host) throws WorkstationException{
        UserController userController = new UserController();
        String id = userController.getName(host) + userController.getCity(host);

        if(workstationMap.get(id) != null) return true;
        else throw new WorkstationException("There isn’t any workstation");
    }

    public boolean exist(Map map, String id){
        if(map.get(id) != null) return true;
        else return false;
    }

    public void setWorkstationMap(String id, Workstation workstation) {
        workstationMap.put(id, workstation);
    }

    public HashMap<Integer, Booking> getBookingMap() {
        return bookingMap;
    }

    public void setPendingBookingMap(Integer id, Booking booking) {
        pendingBookingMap.put(id, booking);
    }

    public void setBookingMap(Integer id, Booking booking) {
        bookingMap.put(id, booking);
    }

    public HashMap<String, Workstation> getWorkstationMap() {
        return workstationMap;
    }

    public HashMap<Integer, Booking> getPendingBookingMap() {
        return pendingBookingMap;
    }

    public boolean isAccomodation(String companyName){
        boolean accomodation = activityMap.get(companyName).isAccomodation();

        return accomodation;
    }

    public boolean isLunch(String companyName){
        boolean lunch = activityMap.get(companyName).isLunch();

        return lunch;
    }

    public boolean isTeamBuilding(String companyName){
        boolean teamBuilding = activityMap.get(companyName).isTeamBuilding();

        return teamBuilding;
    }

    public HashMap<String, Activity> getActivityMap() {
        return activityMap;
    }

    public void setActivityMap(String companyName, Activity activity) {
        activityMap.put(companyName, activity);
    }

    public HashMap<String, Booking> getBookActivityMap() {
        return bookActivityMap;
    }
}


