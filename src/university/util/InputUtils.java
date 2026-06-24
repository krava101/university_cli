package university.util;

import java.util.Scanner;

public class InputUtils {
    public static int readInt(Scanner scanner, String message) {
        System.out.print(message);

        while (!scanner.hasNextInt()) {
            System.out.println("Будь ласка введіть цифру!");
            scanner.nextLine();
            System.out.print(message);
        }

        int value = scanner.nextInt();
        scanner.nextLine();

        return value;
    }

    public static String readString(Scanner scanner, String message) {
        System.out.print(message);
        return scanner.nextLine();
    }
}