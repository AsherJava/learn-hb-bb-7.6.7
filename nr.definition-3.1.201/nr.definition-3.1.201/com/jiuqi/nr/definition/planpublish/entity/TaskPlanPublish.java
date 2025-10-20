/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IMetaItem
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.planpublish.entity;

import com.jiuqi.np.definition.facade.IMetaItem;
import com.jiuqi.np.definition.internal.anno.DBAnno;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="NR_TASK_PLANPUBLISH")
public class TaskPlanPublish
implements IMetaItem {
    private static final long serialVersionUID = -158667905318561782L;
    @DBAnno.DBField(dbField="TPP_KEY", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="TPP_TASKKEY")
    private String taskKey;
    @DBAnno.DBField(dbField="TPP_JOBKEY")
    private String jobKey;
    @DBAnno.DBField(dbField="TPP_MESSAGEKEY")
    private String messagejobKeys;
    @DBAnno.DBField(dbField="TPP_PUBLISHDATE")
    private String publishDate;
    @DBAnno.DBField(dbField="TPP_PUBLISHSTATUS")
    private String publishStatus;
    @DBAnno.DBField(dbField="TPP_WORKSTATUS")
    private String workStatus;
    @DBAnno.DBField(dbField="TPP_USERNAME")
    private String userName;
    @DBAnno.DBField(dbField="TPP_USERKEY")
    private String userKey;
    @DBAnno.DBField(dbField="TPP_UPDATETIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, autoDate=true)
    private Date updateTime;
    @DBAnno.DBField(dbField="TPP_COMMENT", dbType=Clob.class)
    private String comment;
    @DBAnno.DBField(dbField="TPP_DDL_STATUS_BIT", dbType=Integer.class)
    private int ddlStatusBit;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getJobKey() {
        return this.jobKey;
    }

    public void setJobKey(String jobKey) {
        this.jobKey = jobKey;
    }

    public String getMessagejobKeys() {
        return this.messagejobKeys;
    }

    public void setMessagejobKeys(String messagejobKeys) {
        this.messagejobKeys = messagejobKeys;
    }

    public String getPublishDate() {
        return this.publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getPublishStatus() {
        return this.publishStatus;
    }

    public void setPublishStatus(String publishStatus) {
        this.publishStatus = publishStatus;
    }

    public String getWorkStatus() {
        return this.workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserKey() {
        return this.userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Deprecated
    public String getTitle() {
        return null;
    }

    @Deprecated
    public String getOrder() {
        return null;
    }

    @Deprecated
    public String getVersion() {
        return null;
    }

    @Deprecated
    public String getOwnerLevelAndId() {
        return null;
    }

    public int getDdlStatusBit() {
        return this.ddlStatusBit;
    }

    public void setDdlStatusBit(int ddlStatusBit) {
        this.ddlStatusBit = ddlStatusBit;
    }
}

