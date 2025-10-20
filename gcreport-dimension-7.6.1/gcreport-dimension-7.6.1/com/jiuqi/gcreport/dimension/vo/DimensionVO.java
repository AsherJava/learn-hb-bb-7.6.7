/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.dimension.vo;

import com.jiuqi.gcreport.dimension.internal.entity.DimensionEO;
import com.jiuqi.gcreport.dimension.vo.DimTableRelVO;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DimensionVO
implements Serializable,
Cloneable {
    private DimensionEO eo;
    private String dictTableName;
    private List<DimTableRelVO> dimTableRelVOS;
    private List<String> effectScopeCodes;
    private Integer fieldPrecision;

    public DimensionVO() {
        this.eo = new DimensionEO();
    }

    public DimensionVO(DimensionEO eo) {
        this.eo = eo;
        this.fieldPrecision = eo.getFieldSize();
    }

    public String getId() {
        return this.eo.getId();
    }

    public void setId(String id) {
        this.eo.setId(id);
    }

    public String getDimensionType() {
        return this.eo.getDimensionType();
    }

    public void setDimensionType(String dimensionType) {
        this.eo.setDimensionType(dimensionType);
    }

    public String getCode() {
        return this.eo.getCode();
    }

    public void setCode(String code) {
        this.eo.setCode(code);
    }

    public String getTitle() {
        return this.eo.getTitle();
    }

    public void setTitle(String title) {
        this.eo.setTitle(title);
    }

    public Integer getFieldType() {
        return this.eo.getFieldType();
    }

    public void setFieldType(Integer fieldType) {
        this.eo.setFieldType(fieldType);
    }

    public Integer getFieldSize() {
        return this.eo.getFieldSize();
    }

    public void setFieldSize(Integer fieldSize) {
        this.eo.setFieldSize(fieldSize);
    }

    public String getReferField() {
        return this.eo.getReferField();
    }

    public void setReferField(String referField) {
        this.eo.setReferField(referField);
    }

    public String getReferTable() {
        return this.eo.getReferTable();
    }

    public void setReferTable(String referTable) {
        this.eo.setReferTable(referTable);
    }

    public String getDescription() {
        return this.eo.getDescription();
    }

    public void setDescription(String description) {
        this.eo.setDescription(description);
    }

    public Integer getPublishedFlag() {
        return this.eo.getPublishedFlag();
    }

    public void setPublishedFlag(Integer publishedFlag) {
        this.eo.setPublishedFlag(publishedFlag);
    }

    public Integer getSortOrder() {
        return this.eo.getSortOrder();
    }

    public void setSortOrder(Integer sortOrder) {
        this.eo.setSortOrder(sortOrder);
    }

    public String getCreator() {
        return this.eo.getCreator();
    }

    public void setCreator(String creator) {
        this.eo.setCreator(creator);
    }

    public Date getCreateTime() {
        return this.eo.getCreateTime();
    }

    public void setCreateTime(Date createTime) {
        this.eo.setCreateTime(createTime);
    }

    public Date getUpdateTime() {
        return this.eo.getUpdateTime();
    }

    public void setUpdateTime(Date updateTime) {
        this.eo.setUpdateTime(updateTime);
    }

    public String getMatchRule() {
        return this.eo.getMatchRule();
    }

    public void setMatchRule(String matchRule) {
        this.eo.setMatchRule(matchRule);
    }

    public Integer getConvertByOpposite() {
        return this.eo.getConvertByOpposite();
    }

    public void setConvertByOpposite(Integer convertByOpposite) {
        this.eo.setConvertByOpposite(convertByOpposite);
    }

    public Integer getPeriodMappingFlag() {
        return this.eo.getPeriodMappingFlag();
    }

    public void setPeriodMappingFlag(Integer periodMappingFlag) {
        this.eo.setPeriodMappingFlag(periodMappingFlag);
    }

    public List<String> getEffectScopeCodes() {
        return this.effectScopeCodes;
    }

    public void setEffectScopeCodes(List<String> effectScopeCodes) {
        this.effectScopeCodes = effectScopeCodes;
    }

    public List<DimTableRelVO> getDimTableRelVOS() {
        return this.dimTableRelVOS;
    }

    public void setDimTableRelVOS(List<DimTableRelVO> dimTableRelVOS) {
        this.dimTableRelVOS = dimTableRelVOS;
    }

    public String getDictTableName() {
        return this.dictTableName;
    }

    public void setDictTableName(String dictTableName) {
        this.dictTableName = dictTableName;
    }

    public Boolean getNullAble() {
        return new Integer(0).equals(this.eo.getNullAble());
    }

    public void setNullAble(Boolean nullAble) {
        if (nullAble.booleanValue()) {
            this.eo.setNullAble(0);
        } else {
            this.eo.setNullAble(1);
        }
    }

    public Integer getFieldDecimal() {
        return this.eo.getFieldDecimal();
    }

    public void setFieldDecimal(Integer fieldDecimal) {
        this.eo.setFieldDecimal(fieldDecimal);
    }

    public Integer getFieldPrecision() {
        return this.fieldPrecision;
    }

    public void setFieldPrecision(Integer fieldPrecision) {
        this.fieldPrecision = fieldPrecision;
    }

    public Integer getGroupDimFlag() {
        return this.eo.getGroupDimFlag();
    }

    public void setGroupDimFlag(Integer groupDimFlag) {
        this.eo.setGroupDimFlag(groupDimFlag);
    }

    public DimensionVO clone() {
        try {
            DimensionVO clone = (DimensionVO)super.clone();
            clone.eo = this.eo.clone();
            return clone;
        }
        catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}

