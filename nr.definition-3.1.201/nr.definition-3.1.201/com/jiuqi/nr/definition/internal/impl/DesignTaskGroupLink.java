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
import com.jiuqi.nr.definition.internal.impl.DesignTaskDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignTaskGroupDefineImpl;
import com.jiuqi.nr.definition.internal.impl.RunTimeTaskDefineImpl;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="NR_PARAM_TASKGROUPLINK")
@DBAnno.DBLinks(value={@DBAnno.DBLink(linkWith=DesignTaskGroupDefineImpl.class, linkField="key", field="groupKey"), @DBAnno.DBLink(linkWith=DesignTaskDefineImpl.class, linkField="key", field="taskKey"), @DBAnno.DBLink(linkWith=RunTimeTaskDefineImpl.class, linkField="key", field="taskKey")})
@Deprecated
public class DesignTaskGroupLink
implements TaskGroupLinkDefine {
    private static final long serialVersionUID = 4043242941332738639L;
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

