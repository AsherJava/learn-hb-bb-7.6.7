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

@DBTable(name="GC_MONITORCONFIGITEM", inStorage=true, title="\u76d1\u63a7\u7b56\u7565\u5b50\u8868\u4fe1\u606f")
public class MonitorConfigDetailItemEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_MONITORCONFIGITEM";
    @DBColumn(nameInDB="configid", dbType=DBColumn.DBType.Varchar, length=36, title="\u76d1\u63a7\u7b56\u7565ID", isRequired=true)
    private String configId;
    @DBColumn(nameInDB="nodestatecode", dbType=DBColumn.DBType.Varchar, title="\u8282\u70b9\u72b6\u6001\u7f16\u7801", length=50)
    private String nodeStateCode;
    @DBColumn(nameInDB="cnodenewname", dbType=DBColumn.DBType.Varchar, title="\u8282\u70b9\u72b6\u6001\u522b\u540d", length=50)
    private String cNodeNewName;
    @DBColumn(nameInDB="cnodecolor", dbType=DBColumn.DBType.Varchar, title="\u5b50\u8282\u70b9\u989c\u8272", length=20)
    private String cNodeColor;
    @DBColumn(nameInDB="flag", title="\u6570\u636e\u7edf\u8ba1\u72b6\u6001", dbType=DBColumn.DBType.Int)
    private Integer flag;
    @DBColumn(nameInDB="grelationnode", dbType=DBColumn.DBType.Varchar, title="\u7236\u8282\u70b9\u5173\u8054\u8282\u70b9\u7f16\u7801", length=50)
    private String gRelationNode;
    @DBColumn(nameInDB="grelationnodestate", dbType=DBColumn.DBType.Varchar, title="\u5173\u8054\u8282\u70b9\u72b6\u6001\uff08\u5206\u7ec4\u8282\u70b9\u65f6\u4f7f\u7528\uff09")
    private String gRelationNodeState;
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

    public String getConfigId() {
        return this.configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getNodeStateCode() {
        return this.nodeStateCode;
    }

    public void setNodeStateCode(String nodeStateCode) {
        this.nodeStateCode = nodeStateCode;
    }

    public String getcNodeNewName() {
        return this.cNodeNewName;
    }

    public void setcNodeNewName(String cNodeNewName) {
        this.cNodeNewName = cNodeNewName;
    }

    public String getcNodeColor() {
        return this.cNodeColor;
    }

    public void setcNodeColor(String cNodeColor) {
        this.cNodeColor = cNodeColor;
    }

    public Integer getFlag() {
        return this.flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getgRelationNode() {
        return this.gRelationNode;
    }

    public void setgRelationNode(String gRelationNode) {
        this.gRelationNode = gRelationNode;
    }

    public String getgRelationNodeState() {
        return this.gRelationNodeState;
    }

    public void setgRelationNodeState(String gRelationNodeState) {
        this.gRelationNodeState = gRelationNodeState;
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

