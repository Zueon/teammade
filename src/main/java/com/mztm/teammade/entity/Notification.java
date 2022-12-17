package com.mztm.teammade.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long nid;

    private String type;

    private String sender;
    private String receiver;

    private String content;
}
