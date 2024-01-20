package org.maxim.crud.service;

import org.maxim.crud.model.Post;
import org.maxim.crud.repository.PostRepository;
import org.maxim.crud.repository.impl.JDBCPostRepository;

import java.util.List;

public class PostService {
    private final PostRepository postRepository;
    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }
    public PostService(){
        this.postRepository = new JDBCPostRepository();
    }
    public Post getById(Long id){
        return postRepository.getById(id);
    }
    public List<Post> getAll(){
        return postRepository.getAll();
    }
    public Post save (Post post){
        return postRepository.save(post);
    }
    public void update(Post post){
        postRepository.update(post);
    }
    public void deleteById(Long id){
        postRepository.deleteById(id);
    }
}
