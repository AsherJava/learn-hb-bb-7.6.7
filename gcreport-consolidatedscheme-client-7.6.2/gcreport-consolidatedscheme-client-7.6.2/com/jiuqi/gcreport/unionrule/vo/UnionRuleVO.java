/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.unionrule.vo;

import com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO;
import com.jiuqi.gcreport.unionrule.vo.BaseRuleVO;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;

public class UnionRuleVO
extends BaseRuleVO
implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotNull(message="\u7236\u7ea7\u8282\u70b9\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u7236\u7ea7\u8282\u70b9\u4e0d\u80fd\u4e3a\u7a7a") String parentId;
    private Integer sortOrder;
    private Boolean leafFlag;
    private Boolean startFlag;
    private String creator;
    private Date createTime;
    private String updator;
    private Date updateTime;
    private String jsonString;
    @NotNull(message="\u5408\u5e76\u4f53\u7cfb\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u5408\u5e76\u4f53\u7cfb\u4e0d\u80fd\u4e3a\u7a7a") String reportSystem;
    @NotNull(message="\u89c4\u5219\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u89c4\u5219\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a") String ruleType;
    private String ruleTypeDescription;
    private String ruleCode;
    private String code;
    private String ruleCondition;
    @NotNull(message="\u5408\u5e76\u4e1a\u52a1\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u5408\u5e76\u4e1a\u52a1\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a") GcBaseDataVO businessTypeCode;
    private Boolean enableToleranceFlag;
    private String offsetType;
    private String offsetFormula;
    private String toleranceType;
    private Double toleranceRange;
    private Boolean initTypeFlag;
    private List<UnionRuleVO> children;
    private List<String> applyGcUnits;
    private List<Map<String, String>> applyGcUnitMap;

    public Boolean getInitTypeFlag() {
        return this.initTypeFlag;
    }

    public void setInitTypeFlag(Boolean initTypeFlag) {
        this.initTypeFlag = initTypeFlag;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean getLeafFlag() {
        return this.leafFlag;
    }

    public void setLeafFlag(Boolean leafFlag) {
        this.leafFlag = leafFlag;
    }

    public Boolean getStartFlag() {
        return this.startFlag;
    }

    public void setStartFlag(Boolean startFlag) {
        this.startFlag = startFlag;
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

    public String getJsonString() {
        return this.jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public String getReportSystem() {
        return this.reportSystem;
    }

    public void setReportSystem(String reportSystem) {
        this.reportSystem = reportSystem;
    }

    public String getRuleType() {
        return this.ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getRuleCode() {
        return this.ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public String getCode() {
        return this.getId();
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRuleCondition() {
        return this.ruleCondition;
    }

    public void setRuleCondition(String ruleCondition) {
        this.ruleCondition = ruleCondition;
    }

    public GcBaseDataVO getBusinessTypeCode() {
        return this.businessTypeCode;
    }

    public void setBusinessTypeCode(GcBaseDataVO businessTypeCode) {
        this.businessTypeCode = businessTypeCode;
    }

    public Boolean getEnableToleranceFlag() {
        return this.enableToleranceFlag;
    }

    public void setEnableToleranceFlag(Boolean enableToleranceFlag) {
        this.enableToleranceFlag = enableToleranceFlag;
    }

    public String getOffsetType() {
        return this.offsetType;
    }

    public void setOffsetType(String offsetType) {
        this.offsetType = offsetType;
    }

    public String getOffsetFormula() {
        return this.offsetFormula;
    }

    public void setOffsetFormula(String offsetFormula) {
        this.offsetFormula = offsetFormula;
    }

    public String getToleranceType() {
        return this.toleranceType;
    }

    public void setToleranceType(String toleranceType) {
        this.toleranceType = toleranceType;
    }

    public Double getToleranceRange() {
        return this.toleranceRange;
    }

    public void setToleranceRange(Double toleranceRange) {
        this.toleranceRange = toleranceRange;
    }

    public List<UnionRuleVO> getChildren() {
        return this.children;
    }

    public void setChildren(List<UnionRuleVO> children) {
        this.children = children;
    }

    public String getRuleTypeDescription() {
        return this.ruleTypeDescription;
    }

    public void setRuleTypeDescription(String ruleTypeDescription) {
        this.ruleTypeDescription = ruleTypeDescription;
    }

    public String getLabel() {
        return this.getTitle();
    }

    public List<String> getApplyGcUnits() {
        return this.applyGcUnits;
    }

    public void setApplyGcUnits(List<String> applyGcUnits) {
        this.applyGcUnits = applyGcUnits;
    }

    public List<Map<String, String>> getApplyGcUnitMap() {
        return this.applyGcUnitMap;
    }

    public void setApplyGcUnitMap(List<Map<String, String>> applyGcUnitMap) {
        this.applyGcUnitMap = applyGcUnitMap;
    }
}

