package org.maxim.crud.service;

import org.maxim.crud.model.Label;
import org.maxim.crud.repository.LabelRepository;
import org.maxim.crud.repository.impl.JDBCLabelRepository;

import java.util.List;

public class LabelService {
    private final LabelRepository labelRepository;

    public LabelService(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }
    public LabelService(){
        this.labelRepository = new JDBCLabelRepository();
    }
    public Label getById(Long id){
        return labelRepository.getById(id);
    }
    public List<Label> getAll(){
        return labelRepository.getAll();
    }
    public Label save (Label label){
        return labelRepository.save(label);
    }
    public Label update (Label label){
        return labelRepository.update(label);
    }
    public void deleteById(Long id){
        labelRepository.deleteById(id);
    }
}
