package services;

import utilities.Input;
import utilities.InputValidator;
import java.util.Scanner;

public class UsersMainMenuService {

    private static UsersMainMenuService instance;
    private final Scanner input = Input.getInstance();
    private final CreateAllDataService createAllDataService = new CreateAllDataService();


    private UsersMainMenuService() {

    }

    public static UsersMainMenuService getInstance() {
        if (instance == null) {
            instance = new UsersMainMenuService();
        }
        instance.showMenu();
        return instance;
    }

    public void showMenu() {
        System.out.println(("WELCOME TO REMEDIAL TEACHING APPLICATION"));
        System.out.println(("THIS APPLICATION SIMULATES A PRIVATE SCHOOL WHICH OFFERS REMEDIAL TEACHING SERVICES\n"));
        userChoice();
    }

    private void userChoice() {

        int correctInput;


        while (true) {
            System.out.println("PLEASE PROVIDE THE RIGHT NUMBER");
            System.out.println(("1") + " - CREATE YOUR DATA ");
            System.out.println(("2") + " - EXIT");
            correctInput = InputValidator.provideNumToInteger(input);

            switch (correctInput) {
                case 1 -> createAllDataService.create();
                case 2 -> System.exit(0);
                default -> showMenu();
            }
        }
    }
}
