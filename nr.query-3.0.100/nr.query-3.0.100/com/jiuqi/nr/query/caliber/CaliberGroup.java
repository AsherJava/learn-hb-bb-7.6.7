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

@DBAnno.DBTable(dbTable="SYS_QUERYCALIBERGROUP")
@Service
public class CaliberGroup
extends BusinessObject {
    @DBAnno.DBField(dbField="QCG_ID", dbType=String.class, isPk=true)
    private String id;
    @DBAnno.DBField(dbField="QCG_TASKID", dbType=String.class, isPk=false)
    private String taskid;
    @DBAnno.DBField(dbField="QCG_TITLE")
    private String title;
    @DBAnno.DBField(dbField="QCG_PARENTID", dbType=String.class, isPk=false)
    private String parentId;
    @DBAnno.DBField(dbField="QCG_ORDER")
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

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String groupid) {
        this.parentId = groupid;
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

