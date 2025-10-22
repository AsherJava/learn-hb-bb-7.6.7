/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.nr.todo;

import com.jiuqi.nr.todo.bean.TodoParam;
import com.jiuqi.nr.todo.bean.TodoResult;
import com.jiuqi.nr.todo.entity.QueryUnitDetailCommand;
import com.jiuqi.nr.todo.entity.TodoActionCommand;
import com.jiuqi.nr.todo.entity.TodoVO;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface TodoController {
    @GetMapping(value={"/todo"})
    public List<TodoVO> list();

    @PostMapping(value={"/todo/action"})
    public void performAction(@RequestBody TodoActionCommand var1);

    @PostMapping(value={"/todo/unitInfo"})
    public List<Map<String, String>> getUnitDetail(@RequestBody QueryUnitDetailCommand var1);

    @PostMapping(value={"/todo/fromorgroupinfo"})
    public List<Map<String, String>> getFormOrGroupDetail(@RequestBody QueryUnitDetailCommand var1);

    @GetMapping(value={"/todo/check"})
    public boolean checkNewTodo(@RequestParam String var1);

    @PostMapping(value={"/todo/todoparam"})
    public TodoResult queryTodoParam(@RequestBody TodoParam var1);

    @GetMapping(value={"/todo/list"})
    public List<TodoVO> todoList(int var1);

    @GetMapping(value={"/todo/list2"})
    public List<TodoVO> todoList(@RequestParam(value="taskKey") String var1, @RequestParam(value="period") String var2, @RequestParam int var3);
}

