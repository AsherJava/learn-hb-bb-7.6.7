/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 */
package com.jiuqi.nr.zb.scheme.web.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.zb.scheme.common.PropDataType;
import com.jiuqi.nr.zb.scheme.utils.DataTypeUtils;
import com.jiuqi.nr.zb.scheme.utils.json.PropValueSerializer;
import com.jiuqi.nr.zb.scheme.web.vo.BaseVO;
import java.io.Serializable;
import java.util.List;

public class PropInfoVO
implements Serializable {
    private String key;
    private String title;
    private String order;
    private String fieldName;
    private Integer decimal;
    private Integer precision;
    private Object defaultValue;
    private String referEntityId;
    private String referEntityTitle;
    private List<BaseVO> selectRows;
    private PropDataType dataType;
    private Boolean multiple = false;
    @JsonSerialize(using=PropValueSerializer.class)
    private Object value;
    private String valueName;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Integer getDecimal() {
        return this.decimal;
    }

    public void setDecimal(Integer decimal) {
        this.decimal = decimal;
    }

    public Integer getPrecision() {
        return this.precision;
    }

    public void setPrecision(Integer precision) {
        this.precision = precision;
    }

    public String getReferEntityId() {
        return this.referEntityId;
    }

    public void setReferEntityId(String referEntityId) {
        this.referEntityId = referEntityId;
    }

    public PropDataType getDataType() {
        return this.dataType;
    }

    public void setDataType(PropDataType dataType) {
        this.dataType = dataType;
    }

    public boolean isMultiple() {
        return this.multiple != null && this.multiple != false;
    }

    public void setMultiple(Boolean multiple) {
        this.multiple = multiple;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getShowType() {
        return DataTypeUtils.getShowType(this);
    }

    public List<BaseVO> getSelectRows() {
        return this.selectRows;
    }

    public void setSelectRows(List<BaseVO> selectRows) {
        this.selectRows = selectRows;
    }

    public String getReferEntityTitle() {
        return this.referEntityTitle;
    }

    public void setReferEntityTitle(String referEntityTitle) {
        this.referEntityTitle = referEntityTitle;
    }

    public String getValueName() {
        return this.valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }
}

