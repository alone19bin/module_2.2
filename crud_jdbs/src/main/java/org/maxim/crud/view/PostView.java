package org.maxim.crud.view;

import org.maxim.crud.controller.LabelController;
import org.maxim.crud.controller.PostController;
import org.maxim.crud.enums.PostStatus;
import org.maxim.crud.model.Label;
import org.maxim.crud.model.Post;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

public class PostView implements Crud{
        private static PostController postController = new PostController();
        private static Scanner scanner = new Scanner(System.in);
        private final LabelController labelController = new LabelController();
        @Override
        public void create() {
            List<Label> postLabels = new ArrayList<>();
            LabelController labelController = new LabelController();
            System.out.println("Enter Content to Post");
            String content = scanner.nextLine();

            LabelView labelView = new LabelView();
            labelView.read();
            System.out.println("Enter ID Label to dd to Post:");

            Long labelID = scanner.nextLong();
            postLabels.add(labelController.getLabelById(labelID));
            try {
                Post createdPost = postController.createPost(content, postLabels);
                System.out.println("Posty crated:" + createdPost);
            } catch (Exception e) {
                System.out.println("Error in create post");
            }
        }


    @Override
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
            postLabels.add(labelController.getLabelById(labelID));


            postController.update(postToUpdate);
        } catch (Exception e) {
            System.out.println("Error in Update Post");
        }
    }

    @Override
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

    @Override
    public void read() {
        List<Post> posts = null;
        try {
            posts = postController.getAll();
        } catch (Exception e) {
            System.out.println("Error in posts getting");
        }
        System.out.println("List of posts");
        if (posts != null) {
            posts.sort(Comparator.comparing(Post::getId));
            for (Post p : posts) {
                System.out.println(p.getId() + " " + p.getContent());
            }
        }
    }

    @Override
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

