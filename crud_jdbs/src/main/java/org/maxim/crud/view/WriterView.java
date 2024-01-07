package org.maxim.crud.view;

import org.maxim.crud.controller.LabelController;
import org.maxim.crud.controller.PostController;
import org.maxim.crud.controller.WriterController;
import org.maxim.crud.enums.WriterStatus;
import org.maxim.crud.model.Post;
import org.maxim.crud.model.Writer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class WriterView implements Crud {
    private final Scanner scanner = new Scanner(System.in);
    private final WriterController writerController = new WriterController();
    private final PostController postController = new PostController();



    @Override
    public void consoleStart() {
        int views;
        do {
            System.out.print("Select: \n" +
                    "1. Create Writer\n" +
                    "2. Update Writer\n" +
                    "3. Delete Writer\n" +
                    "4. Read Writer\n" +
                    "5. Add Post to Writer\n" +
                    "6. Exit. \n");
            views = scanner.nextInt();
            scanner.nextLine();
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
                //case 5:
                   // addPost();
                   // break;
                case 6:
                    break;
                default:
                    System.out.println("Select correct number " + views);

            }
        }
        while (views != 0);
        scanner.close();
        }



    @Override
    public void create() {
        List<Post> writerPosts = new ArrayList<>();
        PostController postController = new PostController();

        System.out.println("Enter Writer Firstname");
        String firstName = scanner.nextLine();
        System.out.println("\"Enter Writer lastname");
        String lastName = scanner.nextLine();

        PostView postView = new PostView();
        postView.read();
        System.out.println("Enter Id post to add Writer");
        Long postID = scanner.nextLong();
        writerPosts.add(postController.getById(postID));
        try {
            Writer createdWriter = writerController.createWriter(firstName, lastName, writerPosts);
            System.out.println("Writer created:" + createdWriter);
        } catch (Exception e) {
            System.out.println("Error in writer creating");
        }
    }

    @Override
    public void update() {
        read();
        System.out.println("Enter Writer Id to update");
        Long id = Long.parseLong(scanner.nextLine());
        try{
            Writer writerToUpdate = writerController.getById(id);
            System.out.println("Enter new Firstname");
            String firstName = scanner.nextLine();
            writerToUpdate.setFirstName(firstName);
            System.out.println("Enter new Lasttname");
            String lastName = scanner.nextLine();
            writerToUpdate.setLastName(lastName);
            writerController.update(writerToUpdate);
            System.out.println("Writer updated");
        } catch (Exception e){
            System.out.println("Error in Writer update");
        }

    }

    @Override
    public void delete() {
        read();
        System.out.println("Delete Writer Id");
        Long id = scanner.nextLong();
        try{
            writerController.deleteById(id);
            System.out.println("Writer deleted");
        } catch (Exception e){
            System.out.println("Error in Writer delete");
        }
    }

    @Override
    public void read() {
        List<Writer> writers = null;
        try{
            writers =writerController.getAll();
        } catch (Exception e){
            System.out.println("Error oin read writer");
        }
        System.out.println("List of writers");
        if(writers != null){
            writers.sort(Comparator.comparing(Writer::getId));
            for (Writer w : writers){
                System.out.println(w.getId() + " " + w.getFirstName() + " " + w.getLastName());
            }
        }
    }



    private List<Post> addPost() {
        List<Post> result = new ArrayList<>();
        System.out.println("Select Post id: ");
        List<Post> post = postController.getAll();
        System.out.println(post);
        long view = scanner.nextLong();

        while (view != -1) {
            Long finalView = view;
            post
                    .stream()
                    .filter(s -> s.getId().equals(finalView)).findFirst().ifPresent(result::add);
            view = scanner.nextLong();
        }

        return result;
    }
}
