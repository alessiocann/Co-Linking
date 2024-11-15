package main;

import main.ui.Menu;
import main.model.Host;
import main.model.User;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Menu menu;
        int option;
        int optionAccess;

        CoLinking coLinking = CoLinking.getInstance();

        do {
            Scanner scanner = new Scanner(java.lang.System.in);
            menu = new Menu();
            menu.handleLogin();
            optionAccess = scanner.nextInt();
            menu.optionsLogin(optionAccess);
            if (coLinking.currentUser instanceof Host){
                do {
                    menu.handleHost();
                    option = scanner.nextInt();
                    menu.optionsHost(option);
                } while(option != 0);
            }
            else if(coLinking.currentUser instanceof User) {
                do {
                    menu.handleUser();
                    option = scanner.nextInt();
                    menu.optionsUser(option);
                } while(option != 0);
            }

        } while(optionAccess != 0);
    }

}