/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.query.querymodal;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.query.querymodal.QueryModelType;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="SYS_QUERYMODELGROUP")
public class QueryModalGroup {
    @DBAnno.DBField(dbField="QMG_TASKID", dbType=String.class, isPk=false)
    private String taskId;
    @DBAnno.DBField(dbField="QMG_DESCRIPTION")
    private String description;
    @DBAnno.DBField(dbField="UPDATETIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, isOrder=true)
    private Date updateTime;
    @DBAnno.DBField(dbField="QMG_ID", dbType=String.class, isPk=true)
    private String GroupId;
    @DBAnno.DBField(dbField="QMG_TYPE", tranWith="transModelType", dbType=String.class, appType=QueryModelType.class)
    private QueryModelType QMG_TYPE;
    @DBAnno.DBField(dbField="QMG_TITLE")
    private String GroupName;
    @DBAnno.DBField(dbField="QMG_PARENTID", tranWith="transString", dbType=String.class, appType=String.class, isPk=false)
    private String ParentGroupId;
    @DBAnno.DBField(dbField="QMG_ORDER")
    private String GroupOrder;
    @DBAnno.DBField(dbField="QMG_CREATOR", dbType=String.class, isPk=false)
    private String QMG_CREATOR;

    public void setModelType(QueryModelType type) {
        this.QMG_TYPE = type;
    }

    public QueryModelType getModelType() {
        return this.QMG_TYPE;
    }

    public String getGroupId() {
        return this.GroupId;
    }

    public void setGroupId(String groupId) {
        this.GroupId = groupId;
    }

    public String getGroupName() {
        return this.GroupName;
    }

    public void setGroupName(String groupName) {
        this.GroupName = groupName;
    }

    public String getParentGroupId() {
        return this.ParentGroupId;
    }

    public void setParentGroupId(String parentGroupId) {
        this.ParentGroupId = parentGroupId;
    }

    public String getGroupOrder() {
        return this.GroupOrder;
    }

    public void setGroupOrder(String groupOrder) {
        this.GroupOrder = groupOrder;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreator() {
        return this.QMG_CREATOR;
    }

    public String setCreator(String QMG_CREATOR) {
        this.QMG_CREATOR = QMG_CREATOR;
        return this.QMG_CREATOR;
    }
}

