/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.workingpaper.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_WORKPAPER_QUERYWAY", title="\u5de5\u4f5c\u5e95\u7a3f\u67e5\u770b\u65b9\u5f0f\u8868", inStorage=true)
public class WorkingPaperQueryWayItemEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_WORKPAPER_QUERYWAY";
    @DBColumn(title="\u67e5\u770b\u65b9\u5f0f\u540d\u79f0", dbType=DBColumn.DBType.NVarchar)
    private String title;
    @DBColumn(title="\u671f\u672b\u6570\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar, length=60)
    private String qmsType;
    @DBColumn(title="\u62b5\u9500\u6570\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar, length=60)
    private String dxsType;
    @DBColumn(title="\u591a\u7ef4\u5e95\u7a3f\u7c7b\u578b", dbType=DBColumn.DBType.Int, description="0: \u56fa\u5316\u901a\u7528\u5e95\u7a3f\u67e5\u770b\u65b9\u5f0f, 1\uff1a\u666e\u901a\u5e95\u7a3f\u81ea\u5b9a\u4e49\u67e5\u770b\u65b9\u5f0f\uff0c2\uff1a\u591a\u7ef4\u5e95\u7a3f\u81ea\u5b9a\u4e49\u67e5\u770b\u65b9\u5f0f")
    private Integer workType;
    @DBColumn(title="\u521b\u5efa\u4ebaID", dbType=DBColumn.DBType.NVarchar, length=60)
    private String creator;
    @DBColumn(title="\u6392\u5e8f\u53f7", dbType=DBColumn.DBType.Double)
    private Double floatOrder;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQmsType() {
        return this.qmsType;
    }

    public void setQmsType(String qmsType) {
        this.qmsType = qmsType;
    }

    public String getDxsType() {
        return this.dxsType;
    }

    public void setDxsType(String dxsType) {
        this.dxsType = dxsType;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Double getFloatOrder() {
        return this.floatOrder;
    }

    public void setFloatOrder(Double floatOrder) {
        this.floatOrder = floatOrder;
    }

    public Integer getWorkType() {
        return this.workType;
    }

    public void setWorkType(Integer workType) {
        this.workType = workType;
    }
}

