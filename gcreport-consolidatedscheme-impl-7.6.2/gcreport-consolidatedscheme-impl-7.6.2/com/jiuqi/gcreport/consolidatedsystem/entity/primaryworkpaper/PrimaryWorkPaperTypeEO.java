/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.consolidatedsystem.entity.primaryworkpaper;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_PRIMARY_WORKPAPER_TYPE", title="\u4e3b\u8868\u5de5\u4f5c\u5e95\u7a3f\u7c7b\u578b")
public class PrimaryWorkPaperTypeEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_PRIMARY_WORKPAPER_TYPE";
    @DBColumn(nameInDB="title", title="\u8282\u70b9\u540d\u79f0", dbType=DBColumn.DBType.NVarchar)
    private String title;
    @DBColumn(nameInDB="reportsystem", title="\u5408\u5e76\u62a5\u8868\u4f53\u7cfb", dbType=DBColumn.DBType.Varchar)
    private String reportSystem;
    @DBColumn(nameInDB="sortorder", title="\u6392\u5e8f\u5b57\u6bb5", dbType=DBColumn.DBType.Int)
    private Integer sortOrder;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReportSystem() {
        return this.reportSystem;
    }

    public void setReportSystem(String reportSystem) {
        this.reportSystem = reportSystem;
    }

    public Integer getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}

