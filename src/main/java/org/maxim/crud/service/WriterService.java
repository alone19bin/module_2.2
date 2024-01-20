package org.maxim.crud.service;

import org.maxim.crud.model.Writer;
import org.maxim.crud.repository.WriterRepository;
import org.maxim.crud.repository.impl.JDBCWriterRepository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class WriterService {
    private final WriterRepository writerRepository;
    public WriterService(WriterRepository writerRepository){
        this.writerRepository = writerRepository;
    }
    public WriterService(){
        this.writerRepository = new JDBCWriterRepository();
    }
    public Writer getById(Long id){
        return writerRepository.getById(id);
    }
    public List<Writer> getAll(){
        return writerRepository.getAll();
    }
    public Writer save (Writer writer){
        return writerRepository.save(writer);
    }
    public void update(Writer writer){
        writerRepository.update(writer);
    }
    public void deleteById(Long id){
        writerRepository.deleteById(id);
    }
}
