/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.invest.monthcalcscheme.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_MONTHCALCSCHEME", title="\u6309\u6708\u8ba1\u7b97\u7ba1\u7406\u65b9\u6848\u8868")
public class MonthCalcSchemeEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_MONTHCALCSCHEME";
    @DBColumn(nameInDB="CODE", dbType=DBColumn.DBType.Varchar, length=32)
    private String code;
    @DBColumn(nameInDB="TITLE", dbType=DBColumn.DBType.Varchar, length=30)
    private String title;
    @DBColumn(nameInDB="TASKID_Y", dbType=DBColumn.DBType.Varchar, length=36)
    private String taskId_Y;
    @DBColumn(nameInDB="TASKID_J", dbType=DBColumn.DBType.Varchar, length=36)
    private String taskId_J;
    @DBColumn(nameInDB="TASKID_H", dbType=DBColumn.DBType.Varchar, length=36)
    private String taskId_H;
    @DBColumn(nameInDB="TASKID_N", dbType=DBColumn.DBType.Varchar, length=120)
    private String taskId_N;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTaskId_Y() {
        return this.taskId_Y;
    }

    public void setTaskId_Y(String taskId_Y) {
        this.taskId_Y = taskId_Y;
    }

    public String getTaskId_J() {
        return this.taskId_J;
    }

    public void setTaskId_J(String taskId_J) {
        this.taskId_J = taskId_J;
    }

    public String getTaskId_H() {
        return this.taskId_H;
    }

    public void setTaskId_H(String taskId_H) {
        this.taskId_H = taskId_H;
    }

    public String getTaskId_N() {
        return this.taskId_N;
    }

    public void setTaskId_N(String taskId_N) {
        this.taskId_N = taskId_N;
    }
}

