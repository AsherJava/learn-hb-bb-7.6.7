/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.activiti.engine.task.IdentityLink
 */
package com.jiuqi.nr.bpm.todo.entity;

import java.util.Set;
import org.activiti.engine.task.IdentityLink;

public class TodoItemEntity {
    private String id;
    private String title;
    private int todoType;
    private Object queryParam;
    private Set<IdentityLink> actors;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTodoType() {
        return this.todoType;
    }

    public void setTodoType(int todoType) {
        this.todoType = todoType;
    }

    public Object getQueryParam() {
        return this.queryParam;
    }

    public void setQueryParam(Object queryParam) {
        this.queryParam = queryParam;
    }

    public Set<IdentityLink> getActors() {
        return this.actors;
    }

    public void setActors(Set<IdentityLink> actors) {
        this.actors = actors;
    }
}

