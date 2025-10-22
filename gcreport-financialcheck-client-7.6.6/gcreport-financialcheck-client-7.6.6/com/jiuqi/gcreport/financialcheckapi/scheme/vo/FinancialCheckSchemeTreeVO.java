/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckapi.scheme.vo;

import java.util.ArrayList;
import java.util.List;

public class FinancialCheckSchemeTreeVO {
    private String id;
    private String parentId;
    private List<FinancialCheckSchemeTreeVO> children = new ArrayList<FinancialCheckSchemeTreeVO>();
    private String title;
    private String schemeTypeTitle;
    private Integer schemeType;
    private String checkAttributeTitle;
    private String checkModeTitle;
    private String checkMode;
    private String unitTitles;
    private String assetProjects;
    private String debtProjects;
    private String checkDimensionTitles;
    private String toleranceEnableTitle;
    private Double sortOrder;
    private String description;
    private Integer enable;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<FinancialCheckSchemeTreeVO> getChildren() {
        return this.children;
    }

    public void setChildren(List<FinancialCheckSchemeTreeVO> children) {
        this.children = children;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSchemeTypeTitle() {
        return this.schemeTypeTitle;
    }

    public void setSchemeTypeTitle(String schemeTypeTitle) {
        this.schemeTypeTitle = schemeTypeTitle;
    }

    public String getCheckAttributeTitle() {
        return this.checkAttributeTitle;
    }

    public void setCheckAttributeTitle(String checkAttributeTitle) {
        this.checkAttributeTitle = checkAttributeTitle;
    }

    public String getCheckModeTitle() {
        return this.checkModeTitle;
    }

    public void setCheckModeTitle(String checkModeTitle) {
        this.checkModeTitle = checkModeTitle;
    }

    public String getUnitTitles() {
        return this.unitTitles;
    }

    public void setUnitTitles(String unitTitles) {
        this.unitTitles = unitTitles;
    }

    public String getAssetProjects() {
        return this.assetProjects;
    }

    public void setAssetProjects(String assetProjects) {
        this.assetProjects = assetProjects;
    }

    public String getDebtProjects() {
        return this.debtProjects;
    }

    public void setDebtProjects(String debtProjects) {
        this.debtProjects = debtProjects;
    }

    public String getCheckDimensionTitles() {
        return this.checkDimensionTitles;
    }

    public void setCheckDimensionTitles(String checkDimensionTitles) {
        this.checkDimensionTitles = checkDimensionTitles;
    }

    public String getToleranceEnableTitle() {
        return this.toleranceEnableTitle;
    }

    public void setToleranceEnableTitle(String toleranceEnableTitle) {
        this.toleranceEnableTitle = toleranceEnableTitle;
    }

    public void addChildren(FinancialCheckSchemeTreeVO financialCheckSchemeTreeVO) {
        this.children.add(financialCheckSchemeTreeVO);
    }

    public Double getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Double sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getEnable() {
        return this.enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Integer getSchemeType() {
        return this.schemeType;
    }

    public void setSchemeType(Integer schemeType) {
        this.schemeType = schemeType;
    }

    public String getCheckMode() {
        return this.checkMode;
    }

    public void setCheckMode(String checkMode) {
        this.checkMode = checkMode;
    }
}

