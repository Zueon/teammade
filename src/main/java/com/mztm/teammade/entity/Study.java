package com.mztm.teammade.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Study extends Post{
    @OneToOne(fetch = FetchType.LAZY)
    private Member host;

    @OneToMany(mappedBy = "study")
    protected List<Member> members = new ArrayList<>();


    public List<Member> getMembers() {
        return members;
    }

    public void addMember(Member member){
        members.add(member);
        member.setStudy(this);
    }
}
