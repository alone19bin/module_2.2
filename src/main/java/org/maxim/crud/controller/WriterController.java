package org.maxim.crud.controller;

import lombok.RequiredArgsConstructor;
import org.maxim.crud.enums.WriterStatus;
import org.maxim.crud.model.Post;
import org.maxim.crud.model.Writer;
import org.maxim.crud.repository.WriterRepository;
import org.maxim.crud.repository.impl.JDBCWriterRepository;
import org.maxim.crud.service.WriterService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class WriterController {


    private final WriterService writerService = new WriterService();
    public Writer createWriter(String firsName, String lastName){
        Writer createdWriter = new Writer();
        List<Post> writerPosts = new ArrayList<>();
        createdWriter.setFirstName(firsName);
        createdWriter.setLastName(lastName);
        createdWriter.setWriterStatus(WriterStatus.ACTIVE);
        createdWriter.setPosts(writerPosts);
        return writerService.save(createdWriter);
    }
    public List<Writer> getAll(){
        return writerService.getAll();
    }
    public Writer getById(Long id){
        return writerService.getById(id);
    }
    public void update(Writer writer){
        writerService.update(writer);
    }
    public void deleteById(Long id){
        writerService.deleteById(id);
    }
}