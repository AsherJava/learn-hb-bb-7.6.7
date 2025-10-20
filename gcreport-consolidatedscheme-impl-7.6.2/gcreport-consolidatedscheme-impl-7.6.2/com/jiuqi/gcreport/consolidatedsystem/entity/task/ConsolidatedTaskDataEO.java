/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.consolidatedsystem.entity.task;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_CONSTASKDATA", title="\u5408\u5e76\u4f53\u7cfb\u5173\u8054\u4efb\u52a1\u6570\u636e\u5b50\u8868", inStorage=true)
public class ConsolidatedTaskDataEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_CONSTASKDATA";
    @DBColumn(nameInDB="CONSTASKID", title="\u4f53\u7cfb\u5173\u8054\u4efb\u52a1\u4e3b\u8868ID", dbType=DBColumn.DBType.Varchar, length=36)
    private String consTaskId;
    @DBColumn(nameInDB="TASKDATA", dbType=DBColumn.DBType.Text)
    private String taskData;

    public String getConsTaskId() {
        return this.consTaskId;
    }

    public void setConsTaskId(String consTaskId) {
        this.consTaskId = consTaskId;
    }

    public String getTaskData() {
        return this.taskData;
    }

    public void setTaskData(String taskData) {
        this.taskData = taskData;
    }
}

