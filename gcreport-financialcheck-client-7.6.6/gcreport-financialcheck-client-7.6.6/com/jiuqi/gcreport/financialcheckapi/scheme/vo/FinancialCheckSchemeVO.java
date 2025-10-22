/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckapi.scheme.vo;

import com.jiuqi.gcreport.financialcheckapi.scheme.dto.FinancialCheckBilateralSubSettingDTO;
import com.jiuqi.gcreport.financialcheckapi.scheme.dto.FinancialCheckProjectDTO;
import com.jiuqi.gcreport.financialcheckapi.scheme.dto.FinancialCheckUnilateralSubSettingDTO;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class FinancialCheckSchemeVO {
    private String id;
    private String schemeName;
    private String checkMode;
    private String checkAttribute;
    private Map<String, String> checkAttributeItem;
    private List<String> unitCodes;
    private List<Map<String, String>> units;
    private List<FinancialCheckProjectDTO> projects;
    private List<FinancialCheckProjectDTO> deleteProjects;
    private List<String> checkDimensions;
    private String toleranceEnable;
    private Double toleranceAmount;
    private Double toleranceRate;
    private Integer checkAmount;
    private Integer acctYear;
    private String description;
    private Integer schemeType;
    private String parentId;
    private Integer enable;
    private String specialCheck;
    private Double sortOrder;
    private String creator;
    private Date createTime;
    private Date updateTime;
    private String schemeCondition;
    private List<FinancialCheckBilateralSubSettingDTO> bilateralSubSettings;
    private String unilateralCondition;
    private List<FinancialCheckUnilateralSubSettingDTO> unilateralSubSettings;

    public String getSchemeName() {
        return this.schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getCheckMode() {
        return this.checkMode;
    }

    public void setCheckMode(String checkMode) {
        this.checkMode = checkMode;
    }

    public String getCheckAttribute() {
        return this.checkAttribute;
    }

    public void setCheckAttribute(String checkAttribute) {
        this.checkAttribute = checkAttribute;
    }

    public String getToleranceEnable() {
        return this.toleranceEnable;
    }

    public void setToleranceEnable(String toleranceEnable) {
        this.toleranceEnable = toleranceEnable;
    }

    public Double getToleranceAmount() {
        return this.toleranceAmount;
    }

    public void setToleranceAmount(Double toleranceAmount) {
        this.toleranceAmount = toleranceAmount;
    }

    public Double getToleranceRate() {
        return this.toleranceRate;
    }

    public void setToleranceRate(Double toleranceRate) {
        this.toleranceRate = toleranceRate;
    }

    public Integer getCheckAmount() {
        return this.checkAmount;
    }

    public void setCheckAmount(Integer checkAmount) {
        this.checkAmount = checkAmount;
    }

    public Integer getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(Integer acctYear) {
        this.acctYear = acctYear;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSchemeType() {
        return this.schemeType;
    }

    public void setSchemeType(Integer schemeType) {
        this.schemeType = schemeType;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getEnable() {
        return this.enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Double getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Double sortOrder) {
        this.sortOrder = sortOrder;
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

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getUnitCodes() {
        return this.unitCodes;
    }

    public void setUnitCodes(List<String> unitCodes) {
        this.unitCodes = unitCodes;
    }

    public List<String> getCheckDimensions() {
        return this.checkDimensions;
    }

    public void setCheckDimensions(List<String> checkDimensions) {
        this.checkDimensions = checkDimensions;
    }

    public String getSpecialCheck() {
        return this.specialCheck;
    }

    public void setSpecialCheck(String specialCheck) {
        this.specialCheck = specialCheck;
    }

    public List<FinancialCheckProjectDTO> getProjects() {
        return this.projects;
    }

    public void setProjects(List<FinancialCheckProjectDTO> projects) {
        this.projects = projects;
    }

    public List<FinancialCheckProjectDTO> getDeleteProjects() {
        return this.deleteProjects;
    }

    public void setDeleteProjects(List<FinancialCheckProjectDTO> deleteProjects) {
        this.deleteProjects = deleteProjects;
    }

    public List<Map<String, String>> getUnits() {
        return this.units;
    }

    public void setUnits(List<Map<String, String>> units) {
        this.units = units;
    }

    public Map<String, String> getCheckAttributeItem() {
        return this.checkAttributeItem;
    }

    public void setCheckAttributeItem(Map<String, String> checkAttributeItem) {
        this.checkAttributeItem = checkAttributeItem;
    }

    public String getSchemeCondition() {
        return this.schemeCondition;
    }

    public void setSchemeCondition(String schemeCondition) {
        this.schemeCondition = schemeCondition;
    }

    public List<FinancialCheckBilateralSubSettingDTO> getBilateralSubSettings() {
        return this.bilateralSubSettings;
    }

    public void setBilateralSubSettings(List<FinancialCheckBilateralSubSettingDTO> bilateralSubSettings) {
        this.bilateralSubSettings = bilateralSubSettings;
    }

    public String getUnilateralCondition() {
        return this.unilateralCondition;
    }

    public void setUnilateralCondition(String unilateralCondition) {
        this.unilateralCondition = unilateralCondition;
    }

    public List<FinancialCheckUnilateralSubSettingDTO> getUnilateralSubSettings() {
        return this.unilateralSubSettings;
    }

    public void setUnilateralSubSettings(List<FinancialCheckUnilateralSubSettingDTO> unilateralSubSettings) {
        this.unilateralSubSettings = unilateralSubSettings;
    }
}

