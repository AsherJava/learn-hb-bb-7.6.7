/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.formulaschemeconfig.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_FORMULASCHEMECONFIG", title="\u516c\u5f0f\u65b9\u6848\u914d\u7f6e", inStorage=true, indexs={@DBIndex(name="IDX_GC_FORMULACONFIG_SCHEMEID", columnsFields={"SCHEMEID"}), @DBIndex(name="IDX_GC_FORMULACONFIG_ORGID", columnsFields={"ORGID"}), @DBIndex(name="IDX_GC_FORMULACONFIG_ASSDIM", columnsFields={"ASSISTDIM"}), @DBIndex(name="IDX_GC_FORMULACONFIG_BILLID", columnsFields={"BILLID"})})
public class FormulaSchemeConfigEO
extends DefaultTableEntity {
    private static final long serialVersionUID = -959168012383149124L;
    public static final String TABLENAME = "GC_FORMULASCHEMECONFIG";
    @DBColumn(nameInDB="TASKID", title="\u4efb\u52a1ID", dbType=DBColumn.DBType.NVarchar, length=60, isRequired=true)
    private String taskId;
    @DBColumn(nameInDB="SCHEMEID", title="\u62a5\u8868\u65b9\u6848ID", dbType=DBColumn.DBType.NVarchar, length=60, isRequired=true)
    private String schemeId;
    @DBColumn(nameInDB="BILLID", title="\u5355\u636eID", dbType=DBColumn.DBType.NVarchar, length=60)
    private String billId;
    @DBColumn(nameInDB="ENTITYID", title="\u53e3\u5f84", dbType=DBColumn.DBType.NVarchar, length=60)
    private String entityId;
    @DBColumn(nameInDB="CATEGORY", title="\u65b9\u6848\u7c7b\u522b", dbType=DBColumn.DBType.NVarchar, length=20)
    private String category;
    @DBColumn(nameInDB="ORGID", title="\u5355\u4f4d\u7ef4\u5ea6ID", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String orgId;
    @DBColumn(nameInDB="ASSISTDIM", title="\u5176\u4ed6\u7ef4\u5ea6", dbType=DBColumn.DBType.NVarchar, length=200)
    private String assistDim;
    @DBColumn(nameInDB="BBLX", title="\u62a5\u8868\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar, length=20)
    private String bblx;
    @DBColumn(nameInDB="FETCHSCHEMEID", title="\u53d6\u6570\u65b9\u6848ID", dbType=DBColumn.DBType.NVarchar, length=60)
    private String fetchSchemeId;
    @DBColumn(nameInDB="FETCHAFTERSCHEMEID", title="\u53d6\u6570\u540e\u8fd0\u7b97\u65b9\u6848ID", dbType=DBColumn.DBType.NVarchar, length=400)
    private String fetchAfterSchemeId;
    @DBColumn(nameInDB="CONVERTAFTERSCHEMEID", title="\u6298\u7b97\u540e\u8fd0\u7b97\u65b9\u6848ID", dbType=DBColumn.DBType.NVarchar, length=200)
    private String convertAfterSchemeId;
    @DBColumn(nameInDB="CONVERTSYSTEMSCHEMEID", title="\u5141\u8bb8\u5916\u5e01\u6298\u7b97", dbType=DBColumn.DBType.NVarchar, length=10)
    private String convertSystemSchemeId;
    @DBColumn(nameInDB="POSTINGSCHEMEID", title="\u8fc7\u8d26\u65b9\u6848ID", dbType=DBColumn.DBType.NVarchar, length=60)
    private String postingSchemeId;
    @DBColumn(nameInDB="COMPLETEMERGEID", title="\u5b8c\u6210\u5408\u5e76\u65b9\u6848ID", dbType=DBColumn.DBType.NVarchar, length=400)
    private String completeMergeId;
    @DBColumn(nameInDB="SPLITSCHEMEID", title="\u6bd4\u4f8b\u62c6\u5206\u65b9\u6848ID", dbType=DBColumn.DBType.NVarchar, length=60)
    private String splitSchemeId;
    @DBColumn(nameInDB="UNSACTDEEXTLAYENUMSAPERID", title="\u975e\u540c\u63a7\u5904\u7f6e\u63d0\u53d6\u4e0a\u5e74\u540c\u671f\u6570\u516c\u5f0f\u65b9\u6848ID", dbType=DBColumn.DBType.NVarchar, length=400)
    private String unSaCtDeExtLaYeNumSaPerId;
    @DBColumn(nameInDB="SAMECTRLEXTAFTERSCHEMEID", title="\u540c\u63a7\u3001\u975e\u540c\u63a7\u63d0\u53d6\u540e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848ID", dbType=DBColumn.DBType.NVarchar, length=400)
    private String sameCtrlExtAfterSchemeId;
    @DBColumn(nameInDB="CREATOR", title="\u521b\u5efa\u4eba", dbType=DBColumn.DBType.NVarchar, length=100)
    private String creator;
    @DBColumn(nameInDB="CREATETIME", title="\u521b\u5efa\u65f6\u95f4", dbType=DBColumn.DBType.DateTime, length=60)
    private Date createTime;
    @DBColumn(nameInDB="UPDATOR", title="\u4fee\u6539\u4eba", dbType=DBColumn.DBType.NVarchar, length=100)
    private String updator;
    @DBColumn(nameInDB="UPDATETIME", title="\u4fee\u6539\u65f6\u95f4", dbType=DBColumn.DBType.DateTime, length=60)
    private Date updateTime;
    @DBColumn(nameInDB="SORTORDER", title="\u6392\u5e8f", dbType=DBColumn.DBType.Double)
    private Double sortOrder;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getBillId() {
        return this.billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getAssistDim() {
        return this.assistDim;
    }

    public void setAssistDim(String assistDim) {
        this.assistDim = assistDim;
    }

    public String getBblx() {
        return this.bblx;
    }

    public void setBblx(String bblx) {
        this.bblx = bblx;
    }

    public String getFetchSchemeId() {
        return this.fetchSchemeId;
    }

    public void setFetchSchemeId(String fetchSchemeId) {
        this.fetchSchemeId = fetchSchemeId;
    }

    public String getFetchAfterSchemeId() {
        return this.fetchAfterSchemeId;
    }

    public void setFetchAfterSchemeId(String fetchAfterSchemeId) {
        this.fetchAfterSchemeId = fetchAfterSchemeId;
    }

    public String getConvertAfterSchemeId() {
        return this.convertAfterSchemeId;
    }

    public void setConvertAfterSchemeId(String convertAfterSchemeId) {
        this.convertAfterSchemeId = convertAfterSchemeId;
    }

    public String getConvertSystemSchemeId() {
        return this.convertSystemSchemeId;
    }

    public void setConvertSystemSchemeId(String convertSystemSchemeId) {
        this.convertSystemSchemeId = convertSystemSchemeId;
    }

    public String getPostingSchemeId() {
        return this.postingSchemeId;
    }

    public void setPostingSchemeId(String postingSchemeId) {
        this.postingSchemeId = postingSchemeId;
    }

    public String getCompleteMergeId() {
        return this.completeMergeId;
    }

    public void setCompleteMergeId(String completeMergeId) {
        this.completeMergeId = completeMergeId;
    }

    public String getSplitSchemeId() {
        return this.splitSchemeId;
    }

    public void setSplitSchemeId(String splitSchemeId) {
        this.splitSchemeId = splitSchemeId;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdator() {
        return this.updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Double getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Double sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getUnSaCtDeExtLaYeNumSaPerId() {
        return this.unSaCtDeExtLaYeNumSaPerId;
    }

    public void setUnSaCtDeExtLaYeNumSaPerId(String unSaCtDeExtLaYeNumSaPerId) {
        this.unSaCtDeExtLaYeNumSaPerId = unSaCtDeExtLaYeNumSaPerId;
    }

    public String getSameCtrlExtAfterSchemeId() {
        return this.sameCtrlExtAfterSchemeId;
    }

    public void setSameCtrlExtAfterSchemeId(String sameCtrlExtAfterSchemeId) {
        this.sameCtrlExtAfterSchemeId = sameCtrlExtAfterSchemeId;
    }
}

