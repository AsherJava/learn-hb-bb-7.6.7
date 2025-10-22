/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.excel.annotation.Excel
 *  com.jiuqi.common.expimp.dataexport.excel.annotation.ExcelColumn
 */
package com.jiuqi.gcreport.onekeymerge.executor.model;

import com.jiuqi.common.expimp.dataexport.excel.annotation.Excel;
import com.jiuqi.common.expimp.dataexport.excel.annotation.ExcelColumn;

@Excel(title="\u4e00\u952e\u5408\u5e76\u6267\u884c\u65e5\u5fd7\u660e\u7ec6")
public class MergeTaskLogsExcelModel {
    @ExcelColumn(index=0, title={"\u5355\u4f4d"})
    private String orgTitle;
    @ExcelColumn(index=1, title={"\u4e8b\u9879"})
    private String taskTitle;
    @ExcelColumn(index=2, title={"\u72b6\u6001"})
    private String taskState;
    @ExcelColumn(index=3, title={"\u8be6\u7ec6\u65e5\u5fd7"})
    private String taskData;

    public String getOrgTitle() {
        return this.orgTitle;
    }

    public void setOrgTitle(String orgTitle) {
        this.orgTitle = orgTitle;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskState() {
        return this.taskState;
    }

    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }

    public String getTaskData() {
        return this.taskData;
    }

    public void setTaskData(String taskData) {
        this.taskData = taskData;
    }
}

