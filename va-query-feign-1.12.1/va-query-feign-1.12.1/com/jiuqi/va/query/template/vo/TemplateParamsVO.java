/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 */
package com.jiuqi.va.query.template.vo;

import com.jiuqi.va.domain.common.JSONUtil;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.springframework.util.StringUtils;

public class TemplateParamsVO {
    private String id;
    private String templateId;
    private String name;
    private String title;
    @Deprecated
    private String refColumnName;
    private String paramType;
    private String mode;
    private String modeOperator;
    private Boolean mustInput;
    private String refTableName;
    private String filterCondition;
    private Boolean unitCodeFlag;
    private String defaultValue;
    private Integer sortOrder;
    private String source;
    private Integer maxInt;
    private Integer minInt;
    private Boolean foldFlag;
    private Boolean enableAuth;
    private Boolean visibleFlag;
    private Boolean readonly;
    private String showType;
    private List<TemplateParamsVO> driverItems;
    private String configParam;
    private HashSet<String> triggerFields = new HashSet();

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemplateId() {
        return this.templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Deprecated
    public String getRefColumnName() {
        return this.refColumnName;
    }

    public void setRefColumnName(String refColumnName) {
        this.refColumnName = refColumnName;
    }

    public String getParamType() {
        return this.paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public String getMode() {
        return this.mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public boolean isMustInput() {
        return this.mustInput;
    }

    public void setMustInput(boolean mustInput) {
        this.mustInput = mustInput;
    }

    public String getRefTableName() {
        return this.refTableName;
    }

    public void setRefTableName(String refTableName) {
        this.refTableName = refTableName;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Integer getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getMaxInt() {
        return this.maxInt;
    }

    public void setMaxInt(Integer maxInt) {
        this.maxInt = maxInt;
    }

    public Integer getMinInt() {
        return this.minInt;
    }

    public void setMinInt(Integer minInt) {
        this.minInt = minInt;
    }

    public Boolean getFoldFlag() {
        return this.foldFlag;
    }

    public void setFoldFlag(Boolean foldFlag) {
        this.foldFlag = foldFlag;
    }

    public Boolean getEnableAuth() {
        return this.enableAuth == null || this.enableAuth != false;
    }

    public void setEnableAuth(Boolean enableAuth) {
        this.enableAuth = enableAuth;
    }

    public Boolean getVisibleFlag() {
        return this.visibleFlag == null || this.visibleFlag != false;
    }

    public void setVisibleFlag(Boolean visibleFlag) {
        this.visibleFlag = visibleFlag;
    }

    public String getFilterCondition() {
        return this.filterCondition;
    }

    public void setFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
    }

    public Boolean getUnitCodeFlag() {
        return this.unitCodeFlag != null && this.unitCodeFlag != false;
    }

    public void setUnitCodeFlag(Boolean unitCodeDriver) {
        this.unitCodeFlag = unitCodeDriver;
    }

    public List<TemplateParamsVO> getDriverItems() {
        return this.driverItems;
    }

    public void setDriverItems(List<TemplateParamsVO> driverItems) {
        this.driverItems = driverItems;
    }

    public String getShowType() {
        return this.showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public Boolean getReadonly() {
        return this.readonly;
    }

    public void setReadonly(Boolean readonly) {
        this.readonly = readonly;
    }

    public String toString() {
        return "TemplateParamsVO{id='" + this.id + '\'' + ", templateId='" + this.templateId + '\'' + ", name='" + this.name + '\'' + ", title='" + this.title + '\'' + ", refColumnName='" + this.refColumnName + '\'' + ", paramType='" + this.paramType + '\'' + ", mode='" + this.mode + '\'' + ", modeOperator='" + this.modeOperator + '\'' + ", mustInput=" + this.mustInput + ", refTableName='" + this.refTableName + '\'' + ", filterCondition='" + this.filterCondition + '\'' + ", unitCodeFlag=" + this.unitCodeFlag + ", defaultValue='" + this.defaultValue + '\'' + ", sortOrder=" + this.sortOrder + ", source='" + this.source + '\'' + ", maxInt=" + this.maxInt + ", minInt=" + this.minInt + ", foldFlag=" + this.foldFlag + ", enableAuth=" + this.enableAuth + ", readonly=" + this.readonly + ", visibleFlag=" + this.visibleFlag + ", showType='" + this.showType + '\'' + ", driverItems=" + this.driverItems + '}';
    }

    public String getModeOperator() {
        return this.modeOperator;
    }

    public void setModeOperator(String modeOperator) {
        this.modeOperator = modeOperator;
    }

    public String getConfigParam() {
        return this.configParam;
    }

    public void setConfigParam(String configParam) {
        this.configParam = configParam;
    }

    public HashSet<String> getTriggerFields() {
        return this.triggerFields;
    }

    public void setTriggerFields(HashSet<String> triggerFields) {
        this.triggerFields = triggerFields;
    }

    public Map<String, Object> getConfigParamMap() {
        if (StringUtils.hasText(this.configParam)) {
            return JSONUtil.parseMap((String)this.configParam);
        }
        return new HashMap<String, Object>();
    }
}

