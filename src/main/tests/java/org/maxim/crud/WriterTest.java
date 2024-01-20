package org.maxim.crud;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

import org.maxim.crud.enums.LabelStatus;
import org.maxim.crud.enums.PostStatus;
import org.maxim.crud.enums.WriterStatus;
import org.maxim.crud.model.Label;
import org.maxim.crud.model.Post;
import org.maxim.crud.model.Writer;
import org.maxim.crud.repository.WriterRepository;
import org.maxim.crud.repository.impl.JDBCWriterRepository;
import org.maxim.crud.service.WriterService;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;



public class WriterTest {
    private final JDBCWriterRepository writerMock = mock(JDBCWriterRepository.class);
    private final WriterService writerService = new WriterService(writerMock);
    private final List<Label> labels =
            Arrays.asList(new Label(1L, "label1", LabelStatus.ACTIVE));

    private final List<Post> posts =
            Arrays.asList(new Post(1L, "content", "created", "updated",labels, PostStatus.ACTIVE));


    private final List<Writer> mockWriterList =
            Arrays.asList(new Writer(1L, "writer1", "created", posts, WriterStatus.ACTIVE));

    private Writer mockWriter = mockWriterList.get(0);

    @Test
    void getByI() {
        when(writerMock.getById(1L)).thenReturn(mockWriterList.get(0));
        assertEquals(mockWriter, writerService.getById(1L));
    }

    @Test
    void getAll() {
        when(writerMock.getAll()).thenReturn(mockWriterList);
        assertEquals(3, writerService.getAll().size());
    }

    @Test
    void save() {
        writerService.save(mockWriter);
        verify(writerMock, times(1)).save(mockWriter);
    }

    @Test
    void update() {
        writerService.update(mockWriter);
        verify(writerMock, times(1)).update(mockWriter);
    }

    @Test
    void deleteById() {
        writerService.deleteById(1L);
        verify(writerMock, times(1)).deleteById(1L);
    }

}
