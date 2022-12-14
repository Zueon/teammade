package com.mztm.teammade.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class TodoDto implements Serializable {
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final Long tid;
    private final String creator;
    private final String title;
    private final boolean done;
}
