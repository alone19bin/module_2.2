package org.maxim.crud.controller;

import lombok.RequiredArgsConstructor;
import org.maxim.crud.enums.LabelStatus;
import org.maxim.crud.model.Label;
import org.maxim.crud.repository.LabelRepository;
import org.maxim.crud.repository.impl.JDBCLabelRepository;
import org.maxim.crud.service.LabelService;

import java.util.List;

@RequiredArgsConstructor
public class LabelController {
    private final LabelService labelService = new LabelService();

    public Label createLabel(String name) {
        Label createdLabel = new Label();
        createdLabel.setName(name);
        createdLabel.setLabelStatus(LabelStatus.ACTIVE);
        return labelService.save(createdLabel);
    }

    public List<Label> getAll() {
        return labelService.getAll();
    }

    public Label getById(Long id) {
        return labelService.getById(id);
    }

    public void update(Label label) {
        labelService.update(label);
    }

    public void deleteById(Long id) {
        labelService.deleteById(id);
    }
}