package org.maxim.crud.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.maxim.crud.enums.LabelStatus;


@Getter
@Setter
@ToString
@Builder
public class Label {
    private Long id;
    private String name;
    private LabelStatus labelStatus;
}
