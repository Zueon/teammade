package com.mztm.teammade.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class FileDto implements Serializable {

    private String name;
    private String url;
    private String type;
    private long size;


}
