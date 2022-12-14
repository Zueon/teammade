package com.mztm.teammade.entity;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Entity
public class Todo extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long tid;
    private String creator;
    private String title;
    private Integer isDone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_pid")
    private Project project;
}
