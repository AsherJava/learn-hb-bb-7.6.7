/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.query.caliber;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.query.common.BusinessObject;
import java.sql.Timestamp;
import java.util.Date;
import org.springframework.stereotype.Service;

@DBAnno.DBTable(dbTable="SYS_QUERYCALIBERDEFINE")
@Service
public class CaliberDefine
extends BusinessObject {
    @DBAnno.DBField(dbField="QCD_ID", dbType=String.class, isPk=true)
    private String id;
    @DBAnno.DBField(dbField="QCD_TASKID", dbType=String.class, isPk=false)
    private String taskid;
    @DBAnno.DBField(dbField="QCD_GROUPID", dbType=String.class, isPk=false)
    private String groupid;
    @DBAnno.DBField(dbField="QCD_TITLE")
    private String title;
    @DBAnno.DBField(dbField="QCD_ORDER")
    private String order;
    @DBAnno.DBField(dbField="UPDATETIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, autoDate=true)
    private Date updatetime;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getTaskId() {
        return this.taskid;
    }

    public void setTaskId(String taskid) {
        this.taskid = taskid;
    }

    public String getGroupId() {
        return this.groupid;
    }

    public void setGroupId(String groupid) {
        this.groupid = groupid;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getOrder() {
        return this.order;
    }

    @Override
    public void setOrder(String order) {
        this.order = order;
    }

    public Date getUpdateTime() {
        return this.updatetime;
    }

    public void setUpdateTime(Date updatetime) {
        this.updatetime = updatetime;
    }
}

