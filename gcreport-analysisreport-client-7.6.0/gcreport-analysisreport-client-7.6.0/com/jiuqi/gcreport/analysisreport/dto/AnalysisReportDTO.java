/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.analysisreport.dto;

import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportRefOrgDTO;
import com.jiuqi.gcreport.analysisreport.enums.AnalysisReportRefTemplateType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AnalysisReportDTO {
    private String id;
    private String title;
    private String parentId;
    private String refTemplateType = AnalysisReportRefTemplateType.NR.getCode();
    private String nodeType;
    private Long sortOrder;
    private Integer notLeafFlag;
    private Integer startFlag;
    private List<String> refIds;
    private String refTitles;
    private List<AnalysisReportRefOrgDTO> refOrgs;
    private String description;
    private String secretLevel;
    private String versionState;
    private String creator;
    private Date createTime;
    private String updator;
    private Date updateTime;
    private List<AnalysisReportDTO> children = new ArrayList<AnalysisReportDTO>();

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Integer getNotLeafFlag() {
        return this.notLeafFlag;
    }

    public void setNotLeafFlag(Integer notLeafFlag) {
        this.notLeafFlag = notLeafFlag;
    }

    public Integer getStartFlag() {
        return this.startFlag;
    }

    public void setStartFlag(Integer startFlag) {
        this.startFlag = startFlag;
    }

    public List<String> getRefIds() {
        return this.refIds;
    }

    public void setRefIds(List<String> refIds) {
        this.refIds = refIds;
    }

    public String getRefTitles() {
        return this.refTitles;
    }

    public void setRefTitles(String refTitles) {
        this.refTitles = refTitles;
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

    public List<AnalysisReportDTO> getChildren() {
        return this.children;
    }

    public void setChildren(List<AnalysisReportDTO> children) {
        this.children = children;
    }

    public String getRefTemplateType() {
        return this.refTemplateType;
    }

    public void setRefTemplateType(String refTemplateType) {
        this.refTemplateType = refTemplateType;
    }

    public List<AnalysisReportRefOrgDTO> getRefOrgs() {
        return this.refOrgs;
    }

    public void setRefOrgs(List<AnalysisReportRefOrgDTO> refOrgs) {
        this.refOrgs = refOrgs;
    }
}

