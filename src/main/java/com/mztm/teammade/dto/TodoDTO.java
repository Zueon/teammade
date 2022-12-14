package com.mztm.teammade.dto;

import com.mztm.teammade.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoDTO {

    private String id;
    private String title;
    private String creator;
    private boolean done;

}
