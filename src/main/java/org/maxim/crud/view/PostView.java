package org.maxim.crud.view;

import org.maxim.crud.controller.LabelController;
import org.maxim.crud.controller.PostController;
import org.maxim.crud.controller.WriterController;
import org.maxim.crud.enums.PostStatus;
import org.maxim.crud.model.Label;
import org.maxim.crud.model.Post;
import org.maxim.crud.model.Writer;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

public class PostView {
    private final PostController postController = new PostController();
    private final LabelController labelController = new LabelController();
    private final WriterController writerController = new WriterController();
    private final Scanner scanner = new Scanner(System.in);


        public void create() {
            List<Label> postLabels = new ArrayList<>();

            System.out.println("Enter post");
            String content = scanner.nextLine();

            List<Label> labels = labelController.getAll();
            if (labels != null) {
                labels.sort(Comparator.comparing(Label::getId));
                for (Label l : labels) {
                    System.out.println(l.getId() + " " + l.getName());
                }
            }
            System.out.println("Enter Id label to add to posst:");

            long labelID = Long.parseLong(scanner.nextLine());
            postLabels.add(labelController.getById(labelID));


            List<Writer> writers = writerController.getAll();
            if (writers != null) {
                writers.sort(Comparator.comparing(Writer::getId));
                for (Writer w : writers) {
                    System.out.println(w.getId() + " " + w.getFirstName());
                }
            }
            System.out.println("Enter Id writer:");
            Long writerId = Long.parseLong(scanner.nextLine());

            try {
                Post createdPost = postController.createPost(content, postLabels, writerId);
                System.out.println("Пост создан:" + createdPost);
            } catch (Exception e) {
                System.out.println("Ошибка при создании поста");
            }
        }


    public void update() {
        LabelController labelController = new LabelController();
        read();
        System.out.println("Enter Id to Update");
        Long id = Long.parseLong(scanner.nextLine());
        try {
            Post postToUpdate = postController.getById(id);
            postToUpdate.setUpdated(new SimpleDateFormat("yyyy-mm-dd").format(Calendar.getInstance()));
            System.out.println("Enter Content:");
            String content = scanner.nextLine();
            postToUpdate.setContent(content);

            LabelView labelView = new LabelView();
            labelView.read();
            System.out.println("Enter Id label to add to Post:");
            Long labelID = scanner.nextLong();
            List<Label> postLabels = new ArrayList<>();
            postLabels.add(labelController.getById(labelID));


            postController.update(postToUpdate);
        } catch (Exception e) {
            System.out.println("Error in Update Post");
        }
    }

    public void delete() {
        System.out.println("Delete Post ID");
        Long id = scanner.nextLong();

        try {
            postController.deleteById(id);
            System.out.println("Deleted Post");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void read() {
        List<Post> posts = null;
        try {
            posts = postController.getAll();
        } catch (Exception e) {
            System.out.println("Error in  post view read");
        }
        if (posts != null) {
            posts.sort(Comparator.comparing(Post::getId));
            for (Post p : posts) {
                System.out.println(p.getId() + " " + p.getContent());
            }
        }
    }


    public void consoleStart() {
        int views;
        do {
            System.out.print("Select: \n" +
                    "1. Create Post\n" +
                    "2. Update Post\n" +
                    "3. Delete Post\n" +
                    "4. Read Post\n" +
                    "5. Exit. \n");
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
                case 5:
                    break;
                default:
                    System.out.println("Select correct number " + views);

            }
        }
        while (views != 0);
        scanner.close();
    }


}

