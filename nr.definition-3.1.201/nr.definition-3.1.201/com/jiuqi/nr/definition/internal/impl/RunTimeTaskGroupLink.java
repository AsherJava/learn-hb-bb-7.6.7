/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBLink
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBLinks
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.definition.facade.TaskGroupLinkDefine;
import com.jiuqi.nr.definition.internal.impl.RunTimeTaskDefineImpl;
import com.jiuqi.nr.definition.internal.impl.RunTimeTaskGroupDefineImpl;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="NR_PARAM_TASKGROUPLINK")
@DBAnno.DBLinks(value={@DBAnno.DBLink(linkWith=RunTimeTaskGroupDefineImpl.class, linkField="key", field="groupKey"), @DBAnno.DBLink(linkWith=RunTimeTaskDefineImpl.class, linkField="key", field="taskKey")})
public class RunTimeTaskGroupLink
implements TaskGroupLinkDefine {
    private static final long serialVersionUID = 6534927245337709656L;
    @DBAnno.DBField(dbField="tl_group_key")
    private String groupKey;
    @DBAnno.DBField(dbField="tl_task_key")
    private String taskKey;
    @DBAnno.DBField(dbField="tl_updatetime", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, notUpdate=true)
    private Date updateTime;
    @DBAnno.DBField(dbField="tl_order", isOrder=true)
    private String order;

    @Override
    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    @Override
    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    @Override
    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}

