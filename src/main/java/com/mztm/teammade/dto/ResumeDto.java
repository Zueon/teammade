package com.mztm.teammade.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ResumeDto implements Serializable {
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final Long rid;
    private final String name;
    private final String type;
    private final byte[] data;
}
