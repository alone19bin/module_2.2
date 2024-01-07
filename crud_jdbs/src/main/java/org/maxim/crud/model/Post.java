package org.maxim.crud.model;

import lombok.*;
import org.maxim.crud.enums.PostStatus;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    private Long id;
    private String content;
    private String created;
    private String updated;
    private List<Label> labels;
    private PostStatus postStatus;

    public void addLabel(Label label){
        if (labels == null) {
            labels = new ArrayList<>();
        }
        labels.add(label);
    }
}
