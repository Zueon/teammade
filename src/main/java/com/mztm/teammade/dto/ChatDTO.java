package com.mztm.teammade.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatDTO {
    private String cid;
    private String msg;
    private String sender;
    private String roomNum;
    private LocalDateTime creatAt;
}
