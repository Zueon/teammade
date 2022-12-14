package com.mztm.teammade.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class FileDto implements Serializable {
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final Long id;
    private final String name;
    private final String type;
    private final byte[] data;
}
