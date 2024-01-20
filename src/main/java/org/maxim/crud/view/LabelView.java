package org.maxim.crud.view;

import org.maxim.crud.controller.LabelController;
import org.maxim.crud.enums.LabelStatus;
import org.maxim.crud.enums.PostStatus;
import org.maxim.crud.enums.WriterStatus;
import org.maxim.crud.model.Label;
import org.maxim.crud.model.Post;
import org.maxim.crud.model.Writer;

import java.sql.Date;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class LabelView {

    private final Scanner scanner = new Scanner(System.in);
    LabelController labelController = new LabelController();



    public void create() {
        System.out.println("Enter label name");
        String name = scanner.nextLine();
        try {
            Label createdLabel = labelController.createLabel(name);
            System.out.println("Лейбл создан: " + createdLabel);
        } catch (Exception e) {
            System.out.println("Ошибка при создании лейбла");
        }
    }

    public void update() {
        readLabels();
        System.out.println("Enter id to update");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            Label labelToUpdate = labelController.getById(id);
            if (labelToUpdate.getId() == -1) {
                System.out.println("id lable " + id + " not exist");
                return;
            }
            System.out.println("Enter new name:");
            String name = scanner.nextLine();
            labelToUpdate.setName(name);
            labelController.update(labelToUpdate);
            System.out.println("Update saved");
        } catch (Exception e) {
            e.getMessage();
            System.out.println("Error in label update");
        }
    }


    public void delete() {
            readLabels();
            System.out.println("Enter id to delete");
            Long id = Long.parseLong(scanner.nextLine());
            try {
                labelController.deleteById(id);
                System.out.println("Label deleted");
            } catch (NoSuchElementException e) {
                System.out.println("Error in label delete");
            }
    }

    public void read() {
        System.out.println("Labels ");
        List<Label> labels = labelController.getAll();
        System.out.println(" Id  name  STATUS ");
        labels.forEach((x) ->
                System.out.println((x.toString())));
    }

        public void readLabels() {
            List<Label> labels = null;
            try {
                labels = labelController.getAll();
            } catch (Exception e) {
                System.out.println("Error in label read");
            }
            System.out.println("Labels ");
            if (labels != null) {
                labels.sort(Comparator.comparing(Label::getId));
                for (Label l : labels) {
                    System.out.println(l.getId() + " " + l.getName());
                }
            }
        }

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
