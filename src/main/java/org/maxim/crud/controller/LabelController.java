package org.maxim.crud.controller;

import lombok.RequiredArgsConstructor;
import org.maxim.crud.enums.LabelStatus;
import org.maxim.crud.model.Label;
import org.maxim.crud.repository.LabelRepository;
import org.maxim.crud.repository.impl.JDBCLabelRepository;

import java.util.List;

@RequiredArgsConstructor
public class LabelController {
    private final LabelRepository labelRepository = new JDBCLabelRepository();

    public Label createLabel(String name, LabelStatus labelStatus){
        Label newLabel = Label.builder()
                .name(name)
                .labelStatus(labelStatus)
                .build();

        return labelRepository.save(newLabel);
    }

    public Label getLabelById(Long id){
        return labelRepository.getById(id);
    }

    public List<Label> getAllLabels(){
        return labelRepository.getAll();
    }

    public Label updateLabel(Long id, String name, LabelStatus labelStatus){
        Label updateLabel = Label.builder()
                .name(name)
                .labelStatus(labelStatus)
                .build();

        updateLabel.setId(id);

        return labelRepository.update(updateLabel);
    }

    public void deleteLabel(Long id){
        labelRepository.deleteById(id);
    }
}