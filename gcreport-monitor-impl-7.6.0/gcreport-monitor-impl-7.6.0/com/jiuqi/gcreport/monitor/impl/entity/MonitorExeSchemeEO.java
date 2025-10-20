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

@DBTable(name="GC_MONITORSOLUTION", inStorage=true, title="\u76d1\u63a7\u6267\u884c")
public class MonitorExeSchemeEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_MONITORSOLUTION";
    @DBColumn(nameInDB="code", dbType=DBColumn.DBType.Varchar, title="\u7f16\u7801", length=50)
    private String code;
    @DBColumn(nameInDB="title", dbType=DBColumn.DBType.Varchar, title="\u6807\u9898", length=60)
    private String title;
    @DBColumn(nameInDB="nrid", dbType=DBColumn.DBType.Varchar, length=36, title="\u62a5\u8868\u65b9\u6848ID")
    private String nrId;
    @DBColumn(nameInDB="unittype", dbType=DBColumn.DBType.Varchar, title="\u673a\u6784\u7c7b\u578b", length=60)
    private String unitType;
    @DBColumn(nameInDB="monitornode", dbType=DBColumn.DBType.Varchar, title="\u76d1\u63a7\u8282\u70b9", length=100)
    private String monitorNode;
    @DBColumn(nameInDB="shownode", dbType=DBColumn.DBType.Varchar, title="\u5c55\u793a\u8282\u70b9", length=1000)
    private String showNode;
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

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNrId() {
        return this.nrId;
    }

    public void setNrId(String nrId) {
        this.nrId = nrId;
    }

    public String getUnitType() {
        return this.unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getMonitorNode() {
        return this.monitorNode;
    }

    public void setMonitorNode(String monitorNode) {
        this.monitorNode = monitorNode;
    }

    public String getShowNode() {
        return this.showNode;
    }

    public void setShowNode(String showNode) {
        this.showNode = showNode;
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

