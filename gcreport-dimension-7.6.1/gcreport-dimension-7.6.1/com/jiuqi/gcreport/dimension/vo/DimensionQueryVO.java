/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.dimension.vo;

import java.util.Date;
import java.util.List;

public class DimensionQueryVO {
    private String id;
    private String code;
    private String title;
    private Integer fieldType;
    private Integer fieldPrecision;
    private Integer fieldDecimal;
    private String fieldTypeTitle;
    private Integer fieldSize;
    private String referField;
    private String referTable;
    private String dictTableName;
    private String referTableTitle;
    private String description;
    private Integer convertByOpposite;
    private Integer periodMappingFlag;
    private String matchRule;
    private String matchRuleTitle;
    private Integer publishedFlag;
    private List<String> effectScopeCodes;
    private String effectScopeTitles;
    private String effectTableTitles;
    private Date createTime;
    private String creator;
    private Date updateTime;
    private String publishInfo;
    private Integer groupDimFlag;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(Integer fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldTypeTitle() {
        return this.fieldTypeTitle;
    }

    public void setFieldTypeTitle(String fieldTypeTitle) {
        this.fieldTypeTitle = fieldTypeTitle;
    }

    public Integer getFieldSize() {
        return this.fieldSize;
    }

    public void setFieldSize(Integer fieldSize) {
        this.fieldSize = fieldSize;
    }

    public String getReferField() {
        return this.referField;
    }

    public void setReferField(String referField) {
        this.referField = referField;
    }

    public String getReferTable() {
        return this.referTable;
    }

    public void setReferTable(String referTable) {
        this.referTable = referTable;
    }

    public String getDictTableName() {
        return this.dictTableName;
    }

    public void setDictTableName(String dictTableName) {
        this.dictTableName = dictTableName;
    }

    public String getReferTableTitle() {
        return this.referTableTitle;
    }

    public void setReferTableTitle(String referTableTitle) {
        this.referTableTitle = referTableTitle;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPublishedFlag() {
        return this.publishedFlag;
    }

    public void setPublishedFlag(Integer publishedFlag) {
        this.publishedFlag = publishedFlag;
    }

    public List<String> getEffectScopeCodes() {
        return this.effectScopeCodes;
    }

    public void setEffectScopeCodes(List<String> effectScopeCodes) {
        this.effectScopeCodes = effectScopeCodes;
    }

    public String getEffectScopeTitles() {
        return this.effectScopeTitles;
    }

    public void setEffectScopeTitles(String effectScopeTitles) {
        this.effectScopeTitles = effectScopeTitles;
    }

    public String getEffectTableTitles() {
        return this.effectTableTitles;
    }

    public void setEffectTableTitles(String effectTableTitles) {
        this.effectTableTitles = effectTableTitles;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getConvertByOpposite() {
        return this.convertByOpposite;
    }

    public void setConvertByOpposite(Integer convertByOpposite) {
        this.convertByOpposite = convertByOpposite;
    }

    public Integer getPeriodMappingFlag() {
        return this.periodMappingFlag;
    }

    public void setPeriodMappingFlag(Integer periodMappingFlag) {
        this.periodMappingFlag = periodMappingFlag;
    }

    public String getMatchRule() {
        return this.matchRule;
    }

    public void setMatchRule(String matchRule) {
        this.matchRule = matchRule;
    }

    public String getMatchRuleTitle() {
        return this.matchRuleTitle;
    }

    public void setMatchRuleTitle(String matchRuleTitle) {
        this.matchRuleTitle = matchRuleTitle;
    }

    public String getPublishInfo() {
        return this.publishInfo;
    }

    public void setPublishInfo(String publishInfo) {
        this.publishInfo = publishInfo;
    }

    public Integer getFieldPrecision() {
        return this.fieldPrecision;
    }

    public void setFieldPrecision(Integer fieldPrecision) {
        this.fieldPrecision = fieldPrecision;
    }

    public Integer getFieldDecimal() {
        return this.fieldDecimal;
    }

    public void setFieldDecimal(Integer fieldDecimal) {
        this.fieldDecimal = fieldDecimal;
    }

    public Integer getGroupDimFlag() {
        return this.groupDimFlag;
    }

    public void setGroupDimFlag(Integer groupDimFlag) {
        this.groupDimFlag = groupDimFlag;
    }
}

