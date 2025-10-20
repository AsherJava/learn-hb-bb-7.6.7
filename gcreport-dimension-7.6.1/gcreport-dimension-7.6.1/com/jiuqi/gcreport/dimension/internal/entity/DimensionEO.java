/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.dimension.internal.entity;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.io.Serializable;
import java.util.Date;

public class DimensionEO
extends DefaultTableEntity
implements Cloneable,
Serializable {
    private static final long serialVersionUID = -2832137643000558405L;
    private String dimensionType;
    private String code;
    private String title;
    private Integer fieldType;
    private Integer fieldSize;
    private Integer fieldDecimal;
    private String referField;
    private String referTable;
    private String description;
    private Integer publishedFlag;
    private Integer sortOrder;
    private String creator;
    private Date createTime;
    private Date updateTime;
    private Integer nullAble;
    private Integer convertByOpposite;
    private Integer periodMappingFlag;
    private String matchRule;
    private Integer groupDimFlag;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDimensionType() {
        return this.dimensionType;
    }

    public void setDimensionType(String dimensionType) {
        this.dimensionType = dimensionType;
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

    public Integer getFieldSize() {
        return this.fieldSize;
    }

    public void setFieldSize(Integer fieldSize) {
        this.fieldSize = fieldSize;
    }

    public String getMatchRule() {
        return this.matchRule;
    }

    public void setMatchRule(String matchRule) {
        this.matchRule = matchRule;
    }

    public Integer getPeriodMappingFlag() {
        return this.periodMappingFlag;
    }

    public void setPeriodMappingFlag(Integer periodMappingFlag) {
        this.periodMappingFlag = periodMappingFlag;
    }

    public Integer getConvertByOpposite() {
        return this.convertByOpposite;
    }

    public void setConvertByOpposite(Integer convertByOpposite) {
        this.convertByOpposite = convertByOpposite;
    }

    @Deprecated
    public Integer getNullAble() {
        return this.nullAble;
    }

    @Deprecated
    public void setNullAble(Integer nullAble) {
        this.nullAble = nullAble;
    }

    public String queryReferTableName() {
        TableModelDefine table;
        if (StringUtils.isEmpty((String)this.referTable)) {
            return "";
        }
        DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
        try {
            table = dataModelService.getTableModelDefineById(this.referTable);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u83b7\u53d6\u7ef4\u5ea6\u3010" + this.code + "\u3011\u5173\u8054\u8868\u4fe1\u606f\u5931\u8d25\u3002", (Throwable)e);
        }
        return table == null ? "" : table.getName();
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

    public DimensionEO clone() {
        try {
            DimensionEO clone = (DimensionEO)super.clone();
            return clone;
        }
        catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}

