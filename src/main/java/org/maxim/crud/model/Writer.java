package org.maxim.crud.model;

import lombok.*;
import org.maxim.crud.enums.WriterStatus;


import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Writer {
    private Long id;
    private String firstName;
    private String lastName;
    private List<Post> posts;
    private WriterStatus writerStatus;

}