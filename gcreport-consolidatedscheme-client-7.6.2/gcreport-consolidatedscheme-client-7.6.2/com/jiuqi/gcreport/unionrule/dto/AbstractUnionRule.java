/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nHelper
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.unionrule.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nHelper;
import com.jiuqi.gcreport.unionrule.enums.OffsetTypeEnum;
import com.jiuqi.gcreport.unionrule.enums.ToleranceTypeEnum;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import java.util.List;
import javax.validation.constraints.NotNull;

public abstract class AbstractUnionRule {
    private String id;
    private String title;
    private String reportSystem;
    private String ruleType;
    private String ruleTypeDescription;
    private String dataType;
    private String ruleCode;
    private String ruleCondition;
    private String businessTypeCode;
    private Boolean enableToleranceFlag = false;
    private OffsetTypeEnum offsetType;
    private String offsetFormula;
    private ToleranceTypeEnum toleranceType;
    private Double toleranceRange = 0.0;
    private Boolean initTypeFlag;
    private Integer sortOrder;
    private Boolean leafFlag;
    private Boolean startFlag;
    private List<String> applyGcUnits;
    @NotNull(message="\u7236\u7ea7\u8282\u70b9\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u7236\u7ea7\u8282\u70b9\u4e0d\u80fd\u4e3a\u7a7a") String parentId;
    private GcI18nHelper helper = (GcI18nHelper)SpringBeanUtils.getBean(GcI18nHelper.class);

    @JsonIgnore
    public List<String> getSrcDebitSubjectCodeList() {
        throw new UnsupportedOperationException();
    }

    @JsonIgnore
    public List<String> getSrcCreditSubjectCodeList() {
        throw new UnsupportedOperationException();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOffsetFormula() {
        return this.offsetFormula;
    }

    public void setOffsetFormula(String offsetFormula) {
        this.offsetFormula = offsetFormula;
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

    public String getRuleCondition() {
        return this.ruleCondition;
    }

    public void setRuleCondition(String ruleCondition) {
        this.ruleCondition = ruleCondition;
    }

    public String getBusinessTypeCode() {
        return this.businessTypeCode;
    }

    public void setBusinessTypeCode(String businessTypeCode) {
        this.businessTypeCode = businessTypeCode;
    }

    public Boolean getEnableToleranceFlag() {
        return this.enableToleranceFlag;
    }

    public void setEnableToleranceFlag(Boolean enableToleranceFlag) {
        this.enableToleranceFlag = enableToleranceFlag;
    }

    public OffsetTypeEnum getOffsetType() {
        return this.offsetType;
    }

    public void setOffsetType(OffsetTypeEnum offsetType) {
        this.offsetType = offsetType;
    }

    public ToleranceTypeEnum getToleranceType() {
        return this.toleranceType;
    }

    public void setToleranceType(ToleranceTypeEnum toleranceType) {
        this.toleranceType = toleranceType;
    }

    public Double getToleranceRange() {
        return this.toleranceRange;
    }

    public void setToleranceRange(Double toleranceRange) {
        this.toleranceRange = toleranceRange;
    }

    public String getLocalizedName() {
        String localizedName = this.helper.getMessage(this.getId());
        if (!StringUtils.isEmpty((String)localizedName)) {
            return localizedName;
        }
        return this.title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getInitTypeFlag() {
        return this.initTypeFlag;
    }

    public void setInitTypeFlag(Boolean initTypeFlag) {
        this.initTypeFlag = initTypeFlag;
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

    public Integer getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getRuleTypeDescription() {
        return this.ruleTypeDescription;
    }

    public void setRuleTypeDescription(String ruleTypeDescription) {
        this.ruleTypeDescription = ruleTypeDescription;
    }

    public List<String> getApplyGcUnits() {
        return this.applyGcUnits;
    }

    public void setApplyGcUnits(List<String> applyGcUnits) {
        this.applyGcUnits = applyGcUnits;
    }
}

