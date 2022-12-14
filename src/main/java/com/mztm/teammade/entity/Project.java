package com.mztm.teammade.entity;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Project extends Post{

    @OneToOne(fetch = FetchType.LAZY)
    private Member host;

    @OneToMany(mappedBy = "project")
    protected List<Member> members = new ArrayList<>();

    @OneToMany(mappedBy = "project")
    protected List<File> files = new ArrayList<>();

    @OneToMany(mappedBy = "project")
    private List<Todo> todos = new ArrayList<>();

    public List<Member> getMembers() {
        return members;
    }

    public List<File> getFiles() {
        return files;
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public void addMember(Member member){
        members.add(member);
      member.setProject(this);
    }


    public void addTodo(Todo todo){
        todos.add(todo);
        todo.setProject(this);
    }
}
