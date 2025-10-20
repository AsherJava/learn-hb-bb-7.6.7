/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.carryover.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_CARRYOVER_TASK_REL", title="\u5e74\u7ed3\u4efb\u52a1\u4e0e\u5f02\u6b65\u4efb\u52a1\u5173\u8054\u5173\u7cfb\u8868", inStorage=true)
public class CarryOverTaskRelEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_CARRYOVER_TASK_REL";
    @DBColumn(nameInDB="GROUP_ID", title="\u4efb\u52a1\u6240\u5c5e\u5206\u7ec4", dbType=DBColumn.DBType.Varchar)
    private String groupId;
    @DBColumn(nameInDB="carryoverTaskId", title="\u5e74\u7ed3\u4efb\u52a1Id", dbType=DBColumn.DBType.Varchar)
    private String carryoverTaskId;
    @DBColumn(nameInDB="asyncTaskId", title="\u5f02\u6b65\u4efb\u52a1Id", dbType=DBColumn.DBType.Varchar, length=50)
    private String asyncTaskId;
    @DBColumn(nameInDB="taskState", title="\u4efb\u52a1\u72b6\u6001", dbType=DBColumn.DBType.Varchar)
    private String taskState;

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getCarryoverTaskId() {
        return this.carryoverTaskId;
    }

    public void setCarryoverTaskId(String carryoverTaskId) {
        this.carryoverTaskId = carryoverTaskId;
    }

    public String getAsyncTaskId() {
        return this.asyncTaskId;
    }

    public void setAsyncTaskId(String asyncTaskId) {
        this.asyncTaskId = asyncTaskId;
    }

    public String getTaskState() {
        return this.taskState;
    }

    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }
}

