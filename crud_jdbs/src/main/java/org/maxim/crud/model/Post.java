package org.maxim.crud.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.maxim.crud.enums.PostStatus;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;


@Getter
@Setter
@ToString
@Builder
public class Post {
    private Long id;
    private String content;
    private Date created;
    private Date updated;
    private List<Label> labels;
    private PostStatus postStatus;

    public void addLabel(Label label){
        if (labels == null) {
            labels = new ArrayList<>();
        }
        labels.add(label);
    }
}
