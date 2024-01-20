package org.maxim.crud;


import org.junit.jupiter.api.Test;
import org.maxim.crud.enums.LabelStatus;
import org.maxim.crud.enums.PostStatus;

import org.maxim.crud.model.Label;
import org.maxim.crud.model.Post;
import org.maxim.crud.repository.impl.JDBCPostRepository;
import org.maxim.crud.service.PostService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


public class PostTest {

    private final JDBCPostRepository postMock= mock(JDBCPostRepository.class);
    private final PostService postService =new PostService(postMock);
    private final List<Label> labels =
            Arrays.asList(new Label (1L, "label1", LabelStatus.ACTIVE));
    private final List<Post> mockPostList =
            Arrays.asList(new Post(1L, "content", "created", "updated",labels, PostStatus.ACTIVE));

    private Post mockPost = mockPostList.get(0);

    @Test
    void getById() {
        when(postMock.getById(1L)).thenReturn(mockPostList.get(0));
        assertEquals(mockPost, postService.getById(1L));
    }

    @Test
    void getAll() {
        when(postMock.getAll()).thenReturn(mockPostList);
        assertEquals(3, postService.getAll().size());
    }

    @Test
    void save() {
        postService.save(mockPost);
        verify(postMock, times(1)).save(mockPost);
    }

    @Test
    void update() {
        postService.update(mockPost);
        verify(postMock, times(1)).update(mockPost);
    }

    @Test
    void deleteById() {
        postService.deleteById(1L);
        verify(postMock, times(1)).deleteById(1L);
    }
    }
