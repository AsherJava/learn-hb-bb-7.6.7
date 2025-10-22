/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.rest.resultBean;

import com.jiuqi.nr.designer.web.facade.EntityTables;
import com.jiuqi.nr.designer.web.treebean.TaskObject;
import java.util.List;

public class SaveTaskResult {
    private TaskObject taskObject;
    private List<EntityTables> entityList;

    public TaskObject getTaskObject() {
        return this.taskObject;
    }

    public void setTaskObject(TaskObject taskObject) {
        this.taskObject = taskObject;
    }

    public List<EntityTables> getEntityList() {
        return this.entityList;
    }

    public void setEntityList(List<EntityTables> entityList) {
        this.entityList = entityList;
    }
}

