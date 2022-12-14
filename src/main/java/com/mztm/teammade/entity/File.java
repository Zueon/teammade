package com.mztm.teammade.entity;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;



@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="files")
public class File extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private String type;

    // large object 데이터를 저장하기 위한 어노테이션  blob : 바이너리 데이터 clob : 텍스트 데이터
    @Lob
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "pid")
    private Project project;



}
