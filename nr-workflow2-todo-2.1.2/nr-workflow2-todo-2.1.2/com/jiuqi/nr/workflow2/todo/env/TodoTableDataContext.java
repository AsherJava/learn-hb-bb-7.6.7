/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.todo.env;

import com.jiuqi.nr.workflow2.todo.env.TodoBaseContext;
import com.jiuqi.nr.workflow2.todo.envimpl.PageInfo;
import java.util.List;

public interface TodoTableDataContext
extends TodoBaseContext {
    public String getWorkflowNode();

    public List<String> getRangeUnits();

    public List<String> getRangeForms();

    public List<String> getUploadState();

    public String getTodoType();

    public PageInfo getPageInfo();
}

