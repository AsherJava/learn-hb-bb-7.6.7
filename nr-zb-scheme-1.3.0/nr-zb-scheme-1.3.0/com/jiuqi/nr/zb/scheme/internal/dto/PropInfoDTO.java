/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.dto;

import com.jiuqi.nr.zb.scheme.common.PropDataType;
import com.jiuqi.nr.zb.scheme.core.PropInfo;
import java.time.Instant;

public class PropInfoDTO
implements PropInfo {
    private String key;
    private String title;
    private String order;
    private String fieldName;
    private Integer decimal;
    private Integer precision;
    private Object defaultValue;
    private String referEntityId;
    private PropDataType dataType;
    private String level;
    private Instant updateTime;
    private Boolean multiple = false;
    private Object value;

    @Override
    public String getCode() {
        return this.fieldName;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getOrder() {
        return this.order;
    }

    @Override
    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public String getFieldName() {
        return this.fieldName;
    }

    @Override
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public Integer getDecimal() {
        return this.decimal;
    }

    @Override
    public void setDecimal(Integer decimal) {
        this.decimal = decimal;
    }

    @Override
    public Integer getPrecision() {
        return this.precision;
    }

    @Override
    public void setPrecision(Integer precision) {
        this.precision = precision;
    }

    @Override
    public String getReferEntityId() {
        return this.referEntityId;
    }

    @Override
    public void setReferEntityId(String referEntityId) {
        this.referEntityId = referEntityId;
    }

    @Override
    public PropDataType getDataType() {
        return this.dataType;
    }

    @Override
    public void setDataType(PropDataType dataType) {
        this.dataType = dataType;
    }

    @Override
    public String getLevel() {
        return this.level;
    }

    @Override
    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public Instant getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public Boolean getMultiple() {
        return this.multiple;
    }

    @Override
    public void setMultiple(Boolean multiple) {
        this.multiple = multiple;
    }

    @Override
    public Object getDefaultValue() {
        return this.defaultValue;
    }

    @Override
    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public void setValue(Object value) {
        this.value = value;
    }

    protected PropInfoDTO clone() throws CloneNotSupportedException {
        try {
            return (PropInfoDTO)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException("\u514b\u9686\u6570\u636e\u51fa\u9519", e);
        }
    }

    public static PropInfoDTO valueOf(PropInfo propInfo) {
        if (propInfo == null) {
            return null;
        }
        PropInfoDTO dto = new PropInfoDTO();
        PropInfoDTO.copyProperties(propInfo, dto);
        return dto;
    }

    private static void copyProperties(PropInfo propInfo, PropInfoDTO dto) {
        dto.setCode(propInfo.getCode());
        dto.setDataType(propInfo.getDataType());
        dto.setDecimal(propInfo.getDecimal());
        dto.setDefaultValue(propInfo.getDefaultValue());
        dto.setFieldName(propInfo.getFieldName());
        dto.setKey(propInfo.getKey());
        dto.setLevel(propInfo.getLevel());
        dto.setMultiple(propInfo.getMultiple());
        dto.setOrder(propInfo.getOrder());
        dto.setPrecision(propInfo.getPrecision());
        dto.setReferEntityId(propInfo.getReferEntityId());
        dto.setTitle(propInfo.getTitle());
        dto.setUpdateTime(propInfo.getUpdateTime());
        dto.setValue(propInfo.getValue());
    }
}

