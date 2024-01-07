package org.maxim.crud.service;

import org.maxim.crud.model.Label;
import org.maxim.crud.repository.LabelRepository;
import org.maxim.crud.repository.impl.JDBCLabelRepository;

public class LabelService {
    private final LabelRepository labelRepository = new JDBCLabelRepository();
    public Label create(Label label) {
        return labelRepository.save(label);
    }
}
