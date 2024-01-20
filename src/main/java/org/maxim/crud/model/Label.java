package org.maxim.crud.model;

import lombok.*;
import org.maxim.crud.enums.LabelStatus;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Label {
    private Long id;
    private String name;
    private LabelStatus labelStatus;
}
