package org.maxim.crud.controller;

import lombok.RequiredArgsConstructor;

import org.maxim.crud.enums.PostStatus;
import org.maxim.crud.model.Label;
import org.maxim.crud.model.Post;
import org.maxim.crud.model.Writer;
import org.maxim.crud.repository.PostRepository;
import org.maxim.crud.repository.impl.JDBCPostRepository;

import java.sql.Date;
import java.util.List;

@RequiredArgsConstructor
public class PostController {
    private final PostRepository postRepository = new JDBCPostRepository();

    public Post createWriter(String content, Date created,
                             Date updated, List<Label> labels, PostStatus postStatus){

        Post newPost = Post.builder()
                .content(content)
                .created(created)
                .updated(updated)
                .labels(labels)
                .postStatus(postStatus)
                .build();

        return postRepository.save(newPost);
    }

    public Post getPostById(Long id){
        return postRepository.getById(id);
    }

    public List<Post> getAllPosts(){
        return postRepository.getAll();
    }

    public Post updatePost(Long id,String content, Date created,
                           Date updated, List<Label> labels, PostStatus postStatus){

        Post updatePost = Post.builder()
                .content(content)
                .created(created)
                .updated(updated)
                .labels(labels)
                .postStatus(postStatus)
                .build();

        updatePost.setId(id);

        return postRepository.update(updatePost);
    }

    public void deletePost(Long id){
        postRepository.deleteById(id);
    }

    public Post addLabelToPost(Long postId, Label label){
        Post post = postRepository.getById(postId);
        post.addLabel(label);
        return postRepository.update(post);
    }
}