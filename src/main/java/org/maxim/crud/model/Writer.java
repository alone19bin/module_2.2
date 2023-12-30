package org.maxim.crud.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.maxim.crud.enums.WriterStatus;


import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class Writer {
    private Long id;
    private String firstName;
    private String lastName;
    private List<Post> posts;
    private WriterStatus writerStatus;

    public void addPost(Post post) {
        if (posts == null) {
            posts = new ArrayList<>();
        }
        posts.add(post);
    }


}