package main.ui;

import main.CoLinking;
import main.exception.BookingException;
import main.exception.LoginException;
import main.exception.WorkstationException;
import main.model.Host;
import main.model.User;

import java.util.Scanner;


public class Menu {
    private static CoLinking coLinking;

    public Menu() {
        coLinking = CoLinking.getInstance();
    }

    public void optionsLogin(int option) {
        switch (option) {
            case 0:
                exit();
                break;
            case 1:
                login();
                break;
            case 2:
                signup();
                break;
            default:
                java.lang.System.out.println("Please, select one of the available numbers");
                break;
        }
    }

    public void optionsHost(int option) {
        switch (option) {
            case 0:
                logout();
                break;
            case 1:
                createWorkStation();
                break;
            case 2:
                confirmBooking();
                break;
            case 3:
                createActivities();
                break;
            default:
                java.lang.System.out.println("Please, select one of the available numbers");
                break;
        }
    }

    public void optionsUser(int option) {
        switch (option) {
            case 0:
                logout();
                break;
            case 1:
                bookWorkstation();
                break;
            case 2:
                showBooking();
                break;
            case 3:
                deleteBooking();
                break;
            case 4:
                bookActivity();
                break;
            default:
                java.lang.System.out.println("Please, select one of the available numbers");
                break;
        }
    }

    private void login() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Insert your VAT number o TAX code:");
            String id = scanner.nextLine();
            coLinking.login(id);
        } catch (LoginException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error, retry please");
        }
    }

    private void signup() {
        try {
            Scanner scanner = new Scanner(java.lang.System.in);

            System.out.println("1) Business account");
            System.out.println("2) Personal account");
            int account = scanner.nextInt();

            if (account == 1) {
                Scanner scanner2 = new Scanner(java.lang.System.in);
                System.out.println("Insert your VAT number:");
                String vatNumber = scanner2.nextLine();
                System.out.println("Insert name: ");
                String name = scanner2.nextLine();
                System.out.println("Insert city: ");
                String city = scanner2.nextLine();
                System.out.println("Insert address: ");
                String address = scanner2.nextLine();
                System.out.println("Insert mail: ");
                String mail = scanner2.nextLine();
                System.out.println("Insert phoneNumber: ");
                String phoneNumber = scanner2.nextLine();

                coLinking.signup(vatNumber, name, city, address, mail, phoneNumber);
            } else if (account == 2) {
                Scanner scanner2 = new Scanner(java.lang.System.in);
                System.out.println("Insert your TAX code:");
                String taxCode = scanner2.nextLine();
                System.out.println("Insert name: ");
                String name = scanner2.nextLine();
                System.out.println("Insert mail: ");
                String mail = scanner2.nextLine();
                System.out.println("Insert phoneNumber: ");
                String phoneNumber = scanner2.nextLine();
                System.out.println("--- Insert creditCard info ---");
                System.out.println("Insert number: ");
                String numberCreditCard = scanner2.nextLine();
                System.out.println("Insert cvv: ");
                String cvv = scanner2.nextLine();
                System.out.println("Insert expire date: ");
                String expireDate = scanner2.nextLine();

                coLinking.signup(taxCode, name, mail, phoneNumber, numberCreditCard, cvv, expireDate);
            }

        } catch (LoginException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error, retry please");
        }
    }

    public void exit() {
        System.out.println("TERMINATING... DONE.");
        System.out.println("Bye!");

    }

    public void logout() {
        coLinking.currentUser = null;
        System.out.println("Logout success! You will be redirect to the login page");
        System.out.println("You will be redirect to the login page");
    }

    public void handleLogin() {
        MenuView.menuLogin();
    }

    public void handleHost() {
        MenuView.menuHost();
    }

    public void handleUser() {
        MenuView.menuUser();
    }

    public void createWorkStation() {
        if (coLinking.currentUser instanceof Host host) {
            try {
                Scanner scanner = new Scanner(java.lang.System.in);
                System.out.println("description: ");
                String description = scanner.nextLine();
                System.out.println("Insert capacity: ");
                Integer capacity = scanner.nextInt();
                System.out.println("Insert price: ");
                float price = scanner.nextInt();

                coLinking.createWorkStation(host, description, capacity, price);
            } catch (WorkstationException e) {
                System.err.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error, retry please");
            }
        }
    }

    public void createActivities() {
        if (coLinking.currentUser instanceof Host host) {
            try {
                if(coLinking.existWorkstation(host)){
                    Float priceLunch = null;
                    Float priceTeamBuilding = null;
                    Float priceAccomodation = null;

                    Scanner scanner = new Scanner(java.lang.System.in);
                    System.out.println("Accomodation: ");
                    System.out.println("1) yes: ");
                    System.out.println("2) no: ");
                    int accomodation = scanner.nextInt();
                    if(accomodation == 1) {
                        System.out.println("Insert price for accomodation (for night): ");
                        priceAccomodation = scanner.nextFloat();
                    }

                    System.out.println("Lunch: ");
                    System.out.println("1) yes: ");
                    System.out.println("2) no: ");
                    int lunch = scanner.nextInt();
                    if(lunch == 1) {
                        System.out.println("Insert price for lunch: ");
                        priceLunch = scanner.nextFloat();
                    }
                    System.out.println("TeamBuilding: ");
                    System.out.println("1) yes: ");
                    System.out.println("2) no: ");
                    int teamBuilding = scanner.nextInt();
                    if(accomodation > 1){
                        if(teamBuilding == 1){
                            System.out.println("Insert price for team building: ");
                            priceTeamBuilding = scanner.nextFloat();
                        }
                    } else System.err.println("To add TeamBuilding you have to agree to an accomodation");

                    coLinking.createActivities(host, accomodation, lunch, teamBuilding, priceAccomodation, priceLunch, priceTeamBuilding);
                } else System.err.println("Workstation doesn't exist");
            }  catch (WorkstationException e) {
                System.err.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error, retry please");
            }
        }
    }

    public void bookWorkstation() {
        if (coLinking.currentUser instanceof User user) {
            try {
                coLinking.showWorkstationMap();
                Scanner scanner = new Scanner(java.lang.System.in);
                System.out.println("Insert workstation's id for booking: ");
                String id_workstation = scanner.nextLine();
                System.out.println("Insert a date for booking: ");
                String date = scanner.nextLine();
                coLinking.bookWorkstation(user, id_workstation, date);
            } catch (BookingException e) {
                System.err.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error, retry please");
            }
            coLinking.makePayment(user);
        }
    }

    public void bookActivity() {
        if (coLinking.currentUser instanceof User user) {
            try {
                Integer accomodation = null;
                Integer lunch = null;
                Integer teamBuilding = null;
                Float totalPriceActivity = null;

                coLinking.showBookingUser(user);
                Scanner scanner = new Scanner(java.lang.System.in);
                System.out.println("Choose the booking to book an activity (press 0 to exit): ");
                int id_booking = scanner.nextInt();
                String companyName = coLinking.showActivities(id_booking);

                boolean isAccomodation = coLinking.isAccomodation(companyName);
                if (isAccomodation) {
                    System.out.println("Do you want to book accomodation? ");
                    System.out.println("1) Yes");
                    System.out.println("2) No");
                    accomodation = scanner.nextInt();
                }

                boolean isLunch = coLinking.isLunch(companyName);
                if (isLunch) {
                    System.out.println("Do you want to book for lunch? ");
                    System.out.println("1) Yes");
                    System.out.println("2) No");
                    lunch = scanner.nextInt();
                }

                boolean isTeamBuilding = coLinking.isTeamBuilding(companyName);
                if (isTeamBuilding) {
                    System.out.println("Do you want to book for team building? ");
                    System.out.println("1) Yes");
                    System.out.println("2) No");
                    teamBuilding = scanner.nextInt();
                }

                System.out.println("How many days?");
                int days = scanner.nextInt();

                totalPriceActivity = coLinking.bookActivity(companyName, accomodation, lunch, teamBuilding, days);
                System.out.println("The total price for the activities is: " + totalPriceActivity);
                coLinking.makePayment(user);
                System.out.println("Booked");

            } catch (Exception e) {
                System.err.println("Error, retry please");
            }
        }
    }

    public void deleteBooking(){
        if(coLinking.currentUser instanceof User user){
            coLinking.showBookingUser(user);
            System.out.println("Choose the booking to delete (press 0 to exit): ");
            Scanner scanner = new Scanner(java.lang.System.in);
            int id_booking = scanner.nextInt();
            if(id_booking == 0) return;
            if(coLinking.existBookingPending(id_booking) || coLinking.existBooking(id_booking)){
                System.out.println("1) Confirm");
                System.out.println("2) Refute");
                int choose = scanner.nextInt();
                try {
                    coLinking.deleteBooking(choose, id_booking);
                } catch (BookingException e) {
                System.err.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error, retry please");
            }
            }
            coLinking.requestRefund();
        }
    }

    public void showBooking(){
        if(coLinking.currentUser instanceof User user) {
            coLinking.showBookingUser(user);
        }
    }

    public void confirmBooking() {
        if (coLinking.currentUser instanceof Host host) {
            coLinking.showPendingBookingMapHost(host);
            Scanner scanner = new Scanner(java.lang.System.in);
            System.out.println("Select booking (press 0 to exit): ");
            int id_booking = scanner.nextInt();
            if (id_booking == 0) return;
            if(coLinking.existBookingPending(id_booking)) {
                System.out.println("1) Confirm");
                System.out.println("2) Refute");
                int choose = scanner.nextInt();
                try {
                    coLinking.confirmBooking(choose, id_booking);
                } catch (BookingException e) {
                    System.err.println("Error: " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("Error, retry please");
                }
            }
        }
    }
}