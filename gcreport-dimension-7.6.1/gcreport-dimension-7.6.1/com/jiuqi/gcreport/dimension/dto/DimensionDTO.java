/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.dimension.dto;

import com.jiuqi.gcreport.dimension.dto.DimTableRelDTO;
import java.util.Date;
import java.util.List;

public class DimensionDTO {
    private String id;
    private String code;
    private String title;
    private Integer fieldType;
    private Integer fieldSize;
    private Integer fieldDecimal;
    private String referField;
    private String referTable;
    private String dictTableName;
    private String description;
    private Integer publishedFlag;
    private Integer sortOrder;
    private String creator;
    private Date createTime;
    private Date updateTime;
    private Boolean nullAble;
    private Integer convertByOpposite;
    private Integer periodMappingFlag;
    private String matchRule;
    private Integer groupDimFlag;
    private List<DimTableRelDTO> dimTableRelDTOS;
    private List<String> effectScopeCodes;
    private String defaultValue;

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

    public Integer getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
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

    public String getMatchRule() {
        return this.matchRule;
    }

    public void setMatchRule(String matchRule) {
        this.matchRule = matchRule;
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

    public List<String> getEffectScopeCodes() {
        return this.effectScopeCodes;
    }

    public void setEffectScopeCodes(List<String> effectScopeCodes) {
        this.effectScopeCodes = effectScopeCodes;
    }

    public String getDictTableName() {
        return this.dictTableName;
    }

    public void setDictTableName(String dictTableName) {
        this.dictTableName = dictTableName;
    }

    public Boolean getNullAble() {
        return this.nullAble;
    }

    public void setNullAble(Boolean nullAble) {
        this.nullAble = nullAble;
    }

    public List<DimTableRelDTO> getDimTableRelDTOS() {
        return this.dimTableRelDTOS;
    }

    public void setDimTableRelDTOS(List<DimTableRelDTO> dimTableRelDTOS) {
        this.dimTableRelDTOS = dimTableRelDTOS;
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

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}

