/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="nr_progress_loading")
public class ProgressLoadingImpl {
    @DBAnno.DBField(dbField="progressId")
    private String progressId;
    @DBAnno.DBField(dbField="p_taskID")
    private String taskId;
    @DBAnno.DBField(dbField="p_userId")
    private String userId;
    @DBAnno.DBField(dbField="p_opertime", tranWith="transTimeStamp", autoDate=true, dbType=Timestamp.class, appType=Date.class, isOrder=true)
    private Date operTime;
    @DBAnno.DBField(dbField="p_opertype")
    private int operType;
    @DBAnno.DBField(dbField="p_status")
    private int operStatus;
    @DBAnno.DBField(dbField="p_info")
    private String info;
    @DBAnno.DBField(dbField="p_needshow")
    private int needShow;
    @DBAnno.DBField(dbField="p_stackinfos", dbType=Clob.class)
    private String stackinfos;

    public String getStackinfos() {
        return this.stackinfos;
    }

    public void setStackinfos(String stackinfos) {
        this.stackinfos = stackinfos;
    }

    public String getProgressId() {
        return this.progressId;
    }

    public void setProgressId(String progressId) {
        this.progressId = progressId;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getOperTime() {
        return this.operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    public int getOperType() {
        return this.operType;
    }

    public void setOperType(int operType) {
        this.operType = operType;
    }

    public int getOperStatus() {
        return this.operStatus;
    }

    public void setOperStatus(int operStatus) {
        this.operStatus = operStatus;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getNeedShow() {
        return this.needShow;
    }

    public void setNeedShow(int needShow) {
        this.needShow = needShow;
    }
}

