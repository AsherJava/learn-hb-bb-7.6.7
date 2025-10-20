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

@DBTable(name="GC_ANALYSISREPORT", title="\u5206\u6790\u62a5\u544a", inStorage=true)
public class AnalysisReportEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_ANALYSISREPORT";
    @DBColumn(nameInDB="refTemplateType", title="\u5173\u8054\u6a21\u677f\u7c7b\u578b", dbType=DBColumn.DBType.Varchar, length=10)
    private String refTemplateType;
    @DBColumn(nameInDB="title", title="\u6807\u9898", dbType=DBColumn.DBType.NVarchar, length=200)
    private String title;
    @DBColumn(nameInDB="parentid", title="\u7236\u7ea7ID", dbType=DBColumn.DBType.Varchar, length=36)
    private String parentId;
    @DBColumn(nameInDB="nodetype", title="\u8282\u70b9\u7c7b\u578b", dbType=DBColumn.DBType.Varchar, isRequired=true, length=36)
    private String nodeType;
    @DBColumn(nameInDB="sortorder", title="\u6392\u5e8f\u5b57\u6bb5", dbType=DBColumn.DBType.Long)
    private Long sortOrder;
    @DBColumn(nameInDB="leafflag", title="\u662f\u5426\u53f6\u5b50\u8282\u70b9", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer leafFlag;
    @DBColumn(nameInDB="startflag", title="\u662f\u5426\u542f\u7528", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer startFlag;
    @DBColumn(nameInDB="refIds", title="\u5173\u8054\u62a5\u8868\u5206\u6790\u62a5\u544a\u6a21\u677f/\u5206\u7ec4", dbType=DBColumn.DBType.Text)
    private String refIds;
    @DBColumn(nameInDB="refTitles", title="\u5173\u8054\u62a5\u8868\u5206\u6790\u62a5\u544a\u6a21\u677f/\u5206\u7ec4\u6807\u9898", dbType=DBColumn.DBType.Text)
    private String refTitles;
    @DBColumn(nameInDB="description", title="\u63cf\u8ff0", dbType=DBColumn.DBType.NVarchar, length=200)
    private String description;
    @DBColumn(nameInDB="secretLevel", title="\u5bc6\u7ea7", dbType=DBColumn.DBType.Varchar)
    private String secretLevel;
    @DBColumn(nameInDB="versionState", title="\u7248\u672c\u72b6\u6001", dbType=DBColumn.DBType.Varchar)
    private String versionState;
    @DBColumn(nameInDB="creator", title="\u521b\u5efa\u4eba", dbType=DBColumn.DBType.NVarchar, length=100)
    private String creator;
    @DBColumn(nameInDB="createtime", title="\u521b\u5efa\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date createTime;
    @DBColumn(nameInDB="updator", title="\u66f4\u65b0\u4eba", dbType=DBColumn.DBType.NVarchar, length=100)
    private String updator;
    @DBColumn(nameInDB="updatetime", title="\u66f4\u65b0\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date updateTime;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getNodeType() {
        return this.nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public Long getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Long sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getLeafFlag() {
        return this.leafFlag;
    }

    public void setLeafFlag(Integer leafFlag) {
        this.leafFlag = leafFlag;
    }

    public Integer getStartFlag() {
        return this.startFlag;
    }

    public void setStartFlag(Integer startFlag) {
        this.startFlag = startFlag;
    }

    public String getRefIds() {
        return this.refIds;
    }

    public void setRefIds(String refIds) {
        this.refIds = refIds;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSecretLevel() {
        return this.secretLevel;
    }

    public void setSecretLevel(String secretLevel) {
        this.secretLevel = secretLevel;
    }

    public String getVersionState() {
        return this.versionState;
    }

    public void setVersionState(String versionState) {
        this.versionState = versionState;
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

    public String getRefTemplateType() {
        return this.refTemplateType;
    }

    public void setRefTemplateType(String refTemplateType) {
        this.refTemplateType = refTemplateType;
    }

    public String getRefTitles() {
        return this.refTitles;
    }

    public void setRefTitles(String refTitles) {
        this.refTitles = refTitles;
    }
}

