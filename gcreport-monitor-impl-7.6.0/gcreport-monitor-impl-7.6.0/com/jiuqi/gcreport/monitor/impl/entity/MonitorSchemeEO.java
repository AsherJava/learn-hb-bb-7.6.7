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

@DBTable(name="GC_MONITORSCHEME", inStorage=true, title="\u76d1\u63a7\u7b56\u7565\u8868")
public class MonitorSchemeEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_MONITORSCHEME";
    @DBColumn(nameInDB="group_id", dbType=DBColumn.DBType.Varchar, length=36, title="\u5206\u7ec4ID")
    private String groupId;
    @DBColumn(nameInDB="schemecode", dbType=DBColumn.DBType.Varchar, title="\u7b56\u7565\u7f16\u7801", length=50)
    private String schemeCode;
    @DBColumn(nameInDB="schemename", dbType=DBColumn.DBType.Varchar, title="\u7b56\u7565\u540d\u79f0", length=60)
    private String schemeName;
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
    @DBColumn(nameInDB="shownode", title="\u662f\u5426\u4ec5\u663e\u793a\u53ef\u7528\u8282\u70b9", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer showNode;

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getSchemeCode() {
        return this.schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getSchemeName() {
        return this.schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
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

    public Integer getShowNode() {
        return this.showNode;
    }

    public void setShowNode(Integer showNode) {
        this.showNode = showNode;
    }
}

