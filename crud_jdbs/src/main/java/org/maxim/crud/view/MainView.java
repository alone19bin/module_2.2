package org.maxim.crud.view;

import java.util.Scanner;

public class MainView {
    private final Scanner scanner = new Scanner(System.in);
    private final WriterView writerView = new WriterView();
    private final PostView postView = new PostView();
    private final LabelView labelView = new LabelView();


    public void run() {
        System.out.println("Choose \n" +
                "   1. Writer.\n" +
                "   2. Post.\n" +
                "   3. Label.\n" +
                "   4. Exit, \n" );
        Integer choice = scanner.nextInt();

        switch (choice) {
            case 1:
                writerView.consoleStart();
                break;
            case 2:
                postView.consoleStart();
                break;
            case 3:
                labelView.consoleStart();
                break;
            default:
                System.out.println("Error");
                break;
        }
        scanner.close();
    }
}
