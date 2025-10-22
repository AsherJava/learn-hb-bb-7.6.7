/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.GroupLink
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBLink
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.definition.facade.GroupLink;
import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.definition.internal.impl.DesignCaliberGroupDefineImpl;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="des_sys_calibergrouplink")
@DBAnno.DBLink(linkWith=DesignCaliberGroupDefineImpl.class, linkField="key", field="groupKey")
public class DesignCaliberGroupLink
implements GroupLink {
    private static final long serialVersionUID = 1L;
    @DBAnno.DBField(dbField="cal_group_key")
    private String groupKey;
    @DBAnno.DBField(dbField="cal_table_key")
    private String tableKey;
    @DBAnno.DBField(dbField="cal_level")
    private String level;
    @DBAnno.DBField(dbField="cal_updatetime", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, autoDate=true)
    private Date updateTime;

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getObjectKey() {
        return this.tableKey;
    }

    public void setObjectKey(String tableKey) {
        this.tableKey = tableKey;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}

