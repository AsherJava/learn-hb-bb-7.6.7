/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.analysisreport.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_ANALYSISREPORT_DATA", title="\u5408\u5e76\u5206\u6790\u62a5\u544a\u7248\u672c\u6570\u636e", inStorage=true)
public class AnalysisReportDataEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_ANALYSISREPORT_DATA";
    @DBColumn(nameInDB="orgType", title="\u5355\u4f4d\u7c7b\u578b", dbType=DBColumn.DBType.Varchar, length=36)
    private String orgType;
    @DBColumn(nameInDB="orgid", title="\u5355\u4f4dID", dbType=DBColumn.DBType.Varchar, length=36)
    private String orgId;
    @DBColumn(nameInDB="orgTitle", title="\u5355\u4f4d\u6807\u9898", dbType=DBColumn.DBType.NVarchar, length=200)
    private String orgTitle;
    @DBColumn(nameInDB="templateid", title="\u5408\u5e76\u5206\u6790\u62a5\u544a\u6a21\u677fID", dbType=DBColumn.DBType.Varchar, length=36)
    private String templateId;
    @DBColumn(nameInDB="templateTitle", title="\u5408\u5e76\u5206\u6790\u62a5\u544a\u6a21\u677f\u6807\u9898", dbType=DBColumn.DBType.NVarchar, length=200)
    private String templateTitle;
    @DBColumn(nameInDB="docFileKey", title="\u5408\u5e76\u5206\u6790\u62a5\u544a\u7248\u672c\u6570\u636eOSS\u6587\u6863\u6587\u4ef6ID", dbType=DBColumn.DBType.Varchar, length=36)
    private String docFileKey;
    @DBColumn(nameInDB="dimensions", title="\u5408\u5e76\u5206\u6790\u62a5\u544a\u7248\u672c\u6570\u636e\u975e\u5355\u4f4d\u7ef4\u5ea6\u4fe1\u606f", dbType=DBColumn.DBType.NVarchar, length=1000)
    private String dimensions;
    @DBColumn(nameInDB="versionname", title="\u7248\u672c\u540d", dbType=DBColumn.DBType.NVarchar, length=100)
    private String versionName;
    @DBColumn(nameInDB="flowstate", title="\u6d41\u7a0b\u72b6\u6001", dbType=DBColumn.DBType.NVarchar, length=20)
    private String flowState;
    @DBColumn(nameInDB="creator", title="\u521b\u5efa\u4eba", dbType=DBColumn.DBType.NVarchar, length=100)
    private String creator;
    @DBColumn(nameInDB="createtime", title="\u521b\u5efa\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date createTime;
    @DBColumn(nameInDB="updator", title="\u66f4\u65b0\u4eba", dbType=DBColumn.DBType.NVarchar, length=100)
    private String updator;
    @DBColumn(nameInDB="updatetime", title="\u66f4\u65b0\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date updateTime;
    @DBColumn(nameInDB="sortorder", title="\u6392\u5e8f\u5b57\u6bb5", dbType=DBColumn.DBType.Long)
    private Long sortOrder;

    public String getTemplateTitle() {
        return this.templateTitle;
    }

    public void setTemplateTitle(String templateTitle) {
        this.templateTitle = templateTitle;
    }

    public String getDocFileKey() {
        return this.docFileKey;
    }

    public void setDocFileKey(String docFileKey) {
        this.docFileKey = docFileKey;
    }

    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getTemplateId() {
        return this.templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getVersionName() {
        return this.versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getFlowState() {
        return this.flowState;
    }

    public void setFlowState(String flowState) {
        this.flowState = flowState;
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

    public String getDimensions() {
        return this.dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public Long getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Long sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getOrgTitle() {
        return this.orgTitle;
    }

    public void setOrgTitle(String orgTitle) {
        this.orgTitle = orgTitle;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }
}

