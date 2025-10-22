/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.onekeymerge.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_MERGETASK_REL", title="\u4e00\u952e\u5408\u5e76\u4efb\u52a1\u4e0e\u5f02\u6b65\u4efb\u52a1\u5173\u8054\u5173\u7cfb\u8868", inStorage=true)
public class MergeTaskRelEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_MERGETASK_REL";
    @DBColumn(nameInDB="GROUP_ID", title="\u4efb\u52a1\u6240\u5c5e\u5206\u7ec4", dbType=DBColumn.DBType.Varchar, length=36)
    private String groupId;
    @DBColumn(nameInDB="mergeTaskId", title="\u4e00\u952e\u5408\u5e76\u4efb\u52a1Id", dbType=DBColumn.DBType.Varchar, length=36)
    private String mergeTaskId;
    @DBColumn(nameInDB="asyncTaskId", title="\u5f02\u6b65\u4efb\u52a1Id", dbType=DBColumn.DBType.Varchar, length=40)
    private String asyncTaskId;
    @DBColumn(nameInDB="taskState", title="\u4efb\u52a1\u72b6\u6001", dbType=DBColumn.DBType.Varchar)
    private String taskState;
    @DBColumn(nameInDB="taskCode", title="\u4efb\u52a1\u6807\u8bc6", dbType=DBColumn.DBType.Varchar)
    private String taskCode;

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getTaskState() {
        return this.taskState;
    }

    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getMergeTaskId() {
        return this.mergeTaskId;
    }

    public void setMergeTaskId(String mergeTaskId) {
        this.mergeTaskId = mergeTaskId;
    }

    public String getAsyncTaskId() {
        return this.asyncTaskId;
    }

    public void setAsyncTaskId(String asyncTaskId) {
        this.asyncTaskId = asyncTaskId;
    }
}

