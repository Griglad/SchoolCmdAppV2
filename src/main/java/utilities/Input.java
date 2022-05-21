package utilities;

import java.util.Scanner;

public class Input {

    private static Scanner instance;

    private Input() {

    }
    public static Scanner getInstance() {
        if (instance == null) {
            instance = new Scanner(System.in);
        }
        return instance;
    }
}
