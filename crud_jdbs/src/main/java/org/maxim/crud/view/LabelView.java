package org.maxim.crud.view;

import org.maxim.crud.controller.LabelController;
import org.maxim.crud.enums.LabelStatus;
import org.maxim.crud.enums.PostStatus;
import org.maxim.crud.enums.WriterStatus;
import org.maxim.crud.model.Label;
import org.maxim.crud.model.Post;
import org.maxim.crud.model.Writer;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class LabelView implements Crud{

    private final Scanner scanner = new Scanner(System.in);
    LabelController labelController = new LabelController();
    @Override
    public void create() {
        System.out.println("Enter Label name: ");
        String name = scanner.next();
        Label createLabel = labelController.createLabel(name, LabelStatus.ACTIVE);
    }

    @Override
    public void update() {
        System.out.println("Update Label ID");
        Long id = scanner.nextLong();
        System.out.println("Enter Label name: ");
        String name = scanner.next();
        Label updateLabel = labelController.updateLabel(id, name, LabelStatus.ACTIVE);
    }

    @Override
    public void delete() {
        System.out.println("Delete Label ID");
        Long id = scanner.nextLong();
        Label deleteLabel = labelController.getLabelById(id);
        try {
            labelController.deleteLabel(id);
            System.out.println("Deleted Label");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void read() {
        System.out.println("Labels ");
        List<Label> labels = labelController.getAllLabels();
        System.out.println(" Id  name  STATUS ");
        labels.forEach((x) ->
                System.out.println((x.toString())));
    }

    @Override
    public void consoleStart() {
        boolean isExit = false;
        while (true) {
            Integer views = scanner.nextInt();
            switch (views) {
                case 1:
                    create();
                    break;
                case 2:
                    update();
                    break;
                case 3:
                    delete();
                    break;
                case 4:
                    read();
                    break;
                case 5:
                    isExit = true;
                    break;
                default:
                    System.out.println("Error");
                    break;
            }
            if (isExit)
                break;
        }
        scanner.close();
    }
}
