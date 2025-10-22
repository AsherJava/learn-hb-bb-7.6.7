/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.bpm.todo.web;

import com.jiuqi.nr.bpm.todo.entity.TodoItemEntity;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoItemController {
    @GetMapping(value={"/personTodo"})
    public List<TodoItemEntity> personTodo() {
        ArrayList<TodoItemEntity> todoItemList = new ArrayList<TodoItemEntity>();
        return todoItemList;
    }
}

