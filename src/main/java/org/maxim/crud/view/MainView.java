package org.maxim.crud.view;

import java.util.Scanner;

public class MainView {
    private final Scanner scanner = new Scanner(System.in);
    private final WriterView writerView = new WriterView();


    public void run() {
        System.out.println("Choose " +
                "   1. Writer.\n" +
                "   2. Post.\n" +
                "   3. Label.\n" +
                "   4. Exit ");
        Integer choice = scanner.nextInt();

        switch (choice) {
            case 1:
                writerView.consoleStart();
                break;

            default:
                System.out.println("Error");
                break;
        }
        scanner.close();
    }
}
