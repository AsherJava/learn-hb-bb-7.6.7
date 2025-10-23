/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.web.facade.ValidationRuleVO
 */
package com.jiuqi.nr.task.form.field.dto;

import com.jiuqi.nr.datascheme.web.facade.ValidationRuleVO;
import com.jiuqi.nr.task.form.field.dto.DataFieldDTO;
import com.jiuqi.nr.task.form.link.dto.FormatDTO;
import java.util.List;

public class DataFieldSettingDTO
extends DataFieldDTO {
    private String alias;
    private String version;
    private String refDataFieldKey;
    private Integer dimension = 0;
    private String measureUnit;
    private Integer secretLevel = 0;
    private Boolean useAuthority = false;
    private Integer applyType = 0;
    private String tableName;
    private String fieldName;
    private String refDataEntityTitle;
    private FormatDTO fieldFormat;
    private List<ValidationRuleVO> validationRules;
    private Boolean generateVersion = false;
    private Boolean changeWithPeriod = false;
    private Boolean allowTreeSum = false;

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRefDataFieldKey() {
        return this.refDataFieldKey;
    }

    public void setRefDataFieldKey(String refDataFieldKey) {
        this.refDataFieldKey = refDataFieldKey;
    }

    public Integer getDimension() {
        return this.dimension;
    }

    public void setDimension(Integer dimension) {
        this.dimension = dimension == null ? 0 : dimension;
    }

    public String getMeasureUnit() {
        return this.measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public Integer getSecretLevel() {
        return this.secretLevel;
    }

    public void setSecretLevel(Integer secretLevel) {
        this.secretLevel = secretLevel == null ? 0 : secretLevel;
    }

    public Boolean getUseAuthority() {
        return this.useAuthority;
    }

    public void setUseAuthority(Boolean useAuthority) {
        this.useAuthority = useAuthority != null && useAuthority != false;
    }

    public Integer getApplyType() {
        return this.applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType == null ? 0 : applyType;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String getRefDataEntityTitle() {
        return this.refDataEntityTitle;
    }

    @Override
    public void setRefDataEntityTitle(String refDataEntityTitle) {
        this.refDataEntityTitle = refDataEntityTitle;
    }

    public FormatDTO getFieldFormat() {
        return this.fieldFormat;
    }

    public void setFieldFormat(FormatDTO fieldFormat) {
        this.fieldFormat = fieldFormat;
    }

    public List<ValidationRuleVO> getValidationRules() {
        return this.validationRules;
    }

    public void setValidationRules(List<ValidationRuleVO> validationRules) {
        this.validationRules = validationRules;
    }

    public Boolean getGenerateVersion() {
        return this.generateVersion;
    }

    public void setGenerateVersion(Boolean generateVersion) {
        this.generateVersion = generateVersion != null && generateVersion != false;
    }

    public Boolean getChangeWithPeriod() {
        return this.changeWithPeriod;
    }

    public void setChangeWithPeriod(Boolean changeWithPeriod) {
        this.changeWithPeriod = changeWithPeriod != null && changeWithPeriod != false;
    }

    public Boolean getAllowTreeSum() {
        return this.allowTreeSum;
    }

    public void setAllowTreeSum(Boolean allowTreeSum) {
        this.allowTreeSum = allowTreeSum != null && allowTreeSum != false;
    }
}

