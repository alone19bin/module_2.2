package org.maxim.crud;

import liquibase.Labels;
import org.junit.jupiter.api.Test;
import org.maxim.crud.enums.LabelStatus;
import org.maxim.crud.model.Label;
import org.maxim.crud.repository.impl.JDBCLabelRepository;
import org.maxim.crud.service.LabelService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class LabelTest {
    private final JDBCLabelRepository labelMock= mock(JDBCLabelRepository.class);
    private final LabelService labelService =new LabelService(labelMock);
    private final List<Label> mockList = Arrays.asList(new Label(1L, "label", LabelStatus.ACTIVE));
    private Label mockLabel = mockList.get(0);


    //positive tests
    @Test
    void getByIdTestSuccess() {
        when(labelMock.getById(1L)).thenReturn(mockList.get(0));
        assertEquals(mockLabel, labelService.getById(1L));
    }

    @Test
    void getAllTestSuccess() {
        when(labelMock.getAll()).thenReturn(mockList);
        assertEquals(3, labelService.getAll().size());
    }

    @Test
    void saveTestSuccess() {
        labelService.save(mockLabel);
        verify(labelMock, times(1)).save(mockLabel);
    }

    @Test
    void updateTestSuccess() {
        labelService.update(mockLabel);
        verify(labelMock, times(1)).update(mockLabel);
    }

    @Test
    void deleteByIdTestSuccess() {
        labelService.deleteById(1L);
        verify(labelMock, times(1)).deleteById(1L);
    }
}
