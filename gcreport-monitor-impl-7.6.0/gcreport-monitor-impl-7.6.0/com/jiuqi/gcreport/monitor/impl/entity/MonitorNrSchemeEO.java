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
import java.util.Date;

@DBTable(name="GC_MONITORREPORTSCHEME", inStorage=true, title="\u7b56\u7565\u5173\u8054\u62a5\u8868\u65b9\u6848")
public class MonitorNrSchemeEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_MONITORREPORTSCHEME";
    @DBColumn(nameInDB="nrid", dbType=DBColumn.DBType.Varchar, length=36, title="\u62a5\u8868\u65b9\u6848ID")
    private String nrId;
    @DBColumn(nameInDB="monitorid", dbType=DBColumn.DBType.Varchar, length=36, title="\u7b56\u7565ID")
    private String monitorId;
    @DBColumn(nameInDB="startdate", dbType=DBColumn.DBType.Varchar, title="\u8d77\u59cb\u65e5\u671f", length=20)
    private String startDate;
    @DBColumn(nameInDB="enddate", dbType=DBColumn.DBType.Varchar, title="\u7ec8\u6b62\u65e5\u671f", length=20)
    private String endDate;
    @DBColumn(nameInDB="creatorid", dbType=DBColumn.DBType.Varchar, length=36, title="\u521b\u5efa\u4eba")
    private String creatorId;
    @DBColumn(nameInDB="createtime", dbType=DBColumn.DBType.Date, title="\u521b\u5efa\u65f6\u95f4")
    private Date createTime;
    @DBColumn(nameInDB="modifierid", dbType=DBColumn.DBType.Varchar, length=36, title="\u7ef4\u62a4\u4eba")
    private String modifierId;
    @DBColumn(nameInDB="modifyTime", dbType=DBColumn.DBType.Date, title="\u7ef4\u62a4\u65f6\u95f4")
    private Date modifyTime;
    @DBColumn(nameInDB="sortorder", title="\u6392\u5e8f\u5b57\u6bb5", dbType=DBColumn.DBType.Double)
    private Double sortOrder;

    public String getNrId() {
        return this.nrId;
    }

    public void setNrId(String nrId) {
        this.nrId = nrId;
    }

    public String getMonitorId() {
        return this.monitorId;
    }

    public void setMonitorId(String monitorId) {
        this.monitorId = monitorId;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCreatorId() {
        return this.creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getModifierId() {
        return this.modifierId;
    }

    public void setModifierId(String modifierId) {
        this.modifierId = modifierId;
    }

    public Date getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Double getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Double sortOrder) {
        this.sortOrder = sortOrder;
    }
}

