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

@DBTable(name="GC_MONITORCONFIG", inStorage=true, title="\u76d1\u63a7\u7b56\u7565\u4fe1\u606f")
public class MonitorConfigDetailEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_MONITORCONFIG";
    @DBColumn(nameInDB="monitorid", dbType=DBColumn.DBType.Varchar, length=36, title="\u7b56\u7565ID", isRequired=true)
    private String monitorId;
    @DBColumn(nameInDB="nodecode", dbType=DBColumn.DBType.Varchar, title="\u8282\u70b9\u7f16\u7801", isRequired=true, length=50)
    private String nodeCode;
    @DBColumn(nameInDB="CHECKEDFLAG", title="\u662f\u5426\u52fe\u9009", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer checked;
    @DBColumn(nameInDB="cleafnodeflag", title="\u662f\u5426\u663e\u793a\u5206\u7ec4\u72b6\u6001", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer cLeafNodeFlag;
    @DBColumn(nameInDB="nodeorgtype", title="\u7ec4\u7ec7\u673a\u6784\u54cd\u5e94\u7c7b\u578b", dbType=DBColumn.DBType.Int)
    private Integer nodeOrgType;
    @DBColumn(nameInDB="unitids", dbType=DBColumn.DBType.NText, title="\u9002\u5e94\u7ec4\u7ec7\u673a\u6784")
    private String unitIds;
    @DBColumn(nameInDB="showtype", dbType=DBColumn.DBType.Int, title="\u5c55\u793a\u6837\u5f0f")
    private Integer showType;
    @DBColumn(nameInDB="renodetitle", dbType=DBColumn.DBType.Varchar, title="\u91cd\u547d\u540d\u6807\u9898", length=50)
    private String reNodeTitle;
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

    public String getMonitorId() {
        return this.monitorId;
    }

    public void setMonitorId(String monitorId) {
        this.monitorId = monitorId;
    }

    public String getNodeCode() {
        return this.nodeCode;
    }

    public void setNodeCode(String nodeCode) {
        this.nodeCode = nodeCode;
    }

    public Integer getChecked() {
        return this.checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public Integer getNodeOrgType() {
        return this.nodeOrgType;
    }

    public void setNodeOrgType(Integer nodeOrgType) {
        this.nodeOrgType = nodeOrgType;
    }

    public Integer iscLeafNodeFlag() {
        return this.cLeafNodeFlag;
    }

    public Integer getcLeafNodeFlag() {
        return this.cLeafNodeFlag;
    }

    public void setcLeafNodeFlag(Integer cLeafNodeFlag) {
        this.cLeafNodeFlag = cLeafNodeFlag;
    }

    public String getUnitIds() {
        return this.unitIds;
    }

    public void setUnitIds(String unitIds) {
        this.unitIds = unitIds;
    }

    public Integer getShowType() {
        return this.showType;
    }

    public void setShowType(Integer showType) {
        this.showType = showType;
    }

    public String getReNodeTitle() {
        return this.reNodeTitle;
    }

    public void setReNodeTitle(String reNodeTitle) {
        this.reNodeTitle = reNodeTitle;
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

