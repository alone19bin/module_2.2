package org.maxim.crud.view;

import org.maxim.crud.controller.LabelController;
import org.maxim.crud.controller.PostController;
import org.maxim.crud.controller.WriterController;
import org.maxim.crud.enums.WriterStatus;
import org.maxim.crud.model.Post;
import org.maxim.crud.model.Writer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WriterView implements Crud {
    private final Scanner scanner = new Scanner(System.in);
    private final WriterController writerController = new WriterController();
    private final PostController postController = new PostController();
    private final LabelController labelController = new LabelController();

    @Override
    public void create() {
        System.out.println("Enter Writer firstname: ");
        String firstName = scanner.next();
        System.out.println("Enter Writer lastname: ");
        String lastName = scanner.next();
        System.out.println("Enter Writer posts: ");
        List<Post> post = addPost();
        Writer creatWriter = writerController.createWriter(firstName, lastName,
                post, WriterStatus.ACTIVE);
    }

    @Override
    public void update() {
        System.out.println("Update Writer" + " ID: ");
        Long id = scanner.nextLong();
        System.out.println("Enter Writer firstname: ");
        String firstName = scanner.next();
        System.out.println("Enter Writer lastname: ");
        String lastName = scanner.next();
        System.out.println("Enter Writer posts: ");
        List<Post> posts = addPost();
        Writer updateWriter = writerController.updateWriter(id, firstName, lastName, posts, WriterStatus.ACTIVE);
    }

    @Override
    public void delete() {
        System.out.println("Delete Writer" + " ID: ");
        Long id = scanner.nextLong();
        Writer deleteWriter = writerController.getWriterById(id);
        try {
            writerController.deleteWriter(id);
            System.out.println("Deleted developer");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void read() {
        System.out.println("Writers ");
        List<Writer> writers = writerController.getAllWriters();
        System.out.println(" Id  firstName  lastName  posts  STATUS ");
        writers.forEach((x) ->
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

    private List<Post> addPost() {
        List<Post> result = new ArrayList<>();
        System.out.println("Select Post id: ");
        List<Post> post = postController.getAllPosts();
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
