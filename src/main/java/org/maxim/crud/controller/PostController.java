package org.maxim.crud.controller;

import lombok.RequiredArgsConstructor;

import org.maxim.crud.enums.PostStatus;
import org.maxim.crud.model.Label;
import org.maxim.crud.model.Post;
import org.maxim.crud.repository.impl.JDBCPostRepository;
import org.maxim.crud.service.PostService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@RequiredArgsConstructor
public class PostController {
    private final PostService postService = new PostService();

    public Post createPost(String   content, List<Label> postLabels, Long writerId) {
        Post createdpost = new Post();
        createdpost.setContent(content);
        createdpost.setPostStatus(PostStatus.ACTIVE);
        createdpost.setCreated(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Calendar.getInstance().getTime()));
        createdpost.setUpdated("NEW");
        createdpost.setLabels(postLabels);
        createdpost.setId(writerId);
        return postService.save(createdpost);
    }
    public List<Post> getAll() {
        return postService.getAll();
    }

    public Post getById(Long id) {
        return postService.getById(id);
    }
    public void update(Post post) {
        postService.update(post);
    }
    public void deleteById(Long id) {
        postService.deleteById(id);
    }
}
