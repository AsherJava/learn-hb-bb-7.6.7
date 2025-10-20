/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.monitor.impl.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_MONITORUSERCONFIG", inStorage=true, title="\u76d1\u63a7\u6267\u884c\u7528\u6237\u4e2a\u6027\u914d\u7f6e")
public class MonitorExeUserConfigEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_MONITORUSERCONFIG";
    @DBColumn(nameInDB="userid", dbType=DBColumn.DBType.Varchar, length=36, title="\u767b\u5f55\u7528\u6237ID", isRequired=true)
    private String userId;
    @DBColumn(nameInDB="nodes", dbType=DBColumn.DBType.Varchar, title="\u76d1\u63a7\u8282\u70b9", length=100)
    private String nodes;
    @DBColumn(nameInDB="sortorder", title="\u6392\u5e8f\u5b57\u6bb5", dbType=DBColumn.DBType.Double)
    private Double sortOrder;

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNodes() {
        return this.nodes;
    }

    public void setNodes(String nodes) {
        this.nodes = nodes;
    }

    public Double getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Double sortOrder) {
        this.sortOrder = sortOrder;
    }
}

