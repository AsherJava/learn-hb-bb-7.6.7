/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonGetter
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonSetter
 */
package com.jiuqi.nr.zb.scheme.internal.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.jiuqi.nr.zb.scheme.common.PropDataType;
import com.jiuqi.nr.zb.scheme.common.ZbDataType;
import com.jiuqi.nr.zb.scheme.core.PropInfo;
import com.jiuqi.nr.zb.scheme.internal.anno.DBAnno;
import com.jiuqi.nr.zb.scheme.internal.entity.PropLinkDO;
import java.sql.Timestamp;
import java.time.Instant;
import org.springframework.util.StringUtils;

@DBAnno.DBLink(linkWith=PropLinkDO.class, linkField="propKey", field="key")
@DBAnno.DBTable(dbTable="NR_ZB_PROP")
public class PropInfoDO
implements PropInfo {
    @DBAnno.DBField(dbField="ZP_KEY", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="ZP_TITLE")
    private String title;
    @DBAnno.DBField(dbField="ZP_ORDER", isOrder=true)
    private String order;
    @DBAnno.DBField(dbField="ZP_FIELD_NAME")
    private String fieldName;
    @DBAnno.DBField(dbField="ZP_DECIMAL")
    private Integer decimal;
    @DBAnno.DBField(dbField="ZP_PRECISION")
    private Integer precision;
    @DBAnno.DBField(dbField="ZP_DATA_TYPE", tranWith="transPropDataType", dbType=Integer.class, appType=ZbDataType.class)
    private PropDataType dataType;
    @DBAnno.DBField(dbField="ZP_DEFAULT_VALUE", get="getDefaultValueJson", set="setDefaultValueJson")
    private transient Object defaultValue;
    @DBAnno.DBField(dbField="ZP_ENTITY_ID")
    private String referEntityId;
    @DBAnno.DBField(dbField="ZP_LEVEL")
    private String level;
    @DBAnno.DBField(dbField="ZP_UPDATE_TIME", tranWith="transTimeStampByInstant", dbType=Timestamp.class, appType=Instant.class)
    private Instant updateTime;
    @DBAnno.DBField(dbField="ZP_MULTIPLE", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private Boolean multiple;
    private Object value;

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

    @JsonGetter(value="defaultValue")
    public String getDefaultValueJson() {
        if (this.defaultValue != null) {
            return String.valueOf(this.defaultValue);
        }
        return null;
    }

    @JsonSetter(value="defaultValue")
    public void setDefaultValueJson(String defaultValue) {
        if (StringUtils.hasLength(defaultValue)) {
            if (this.dataType == null) {
                this.defaultValue = defaultValue;
            } else {
                switch (this.dataType) {
                    case STRING: {
                        this.defaultValue = defaultValue;
                        break;
                    }
                    case BOOLEAN: {
                        this.defaultValue = Boolean.valueOf(defaultValue);
                        break;
                    }
                    case INTEGER: {
                        this.defaultValue = Integer.valueOf(defaultValue);
                        break;
                    }
                    case DOUBLE: {
                        this.defaultValue = Double.valueOf(defaultValue);
                        break;
                    }
                }
            }
        }
    }

    @Override
    @JsonIgnore
    public Object getDefaultValue() {
        return this.defaultValue;
    }

    @Override
    @JsonIgnore
    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
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
    public Object getValue() {
        return this.value;
    }

    @Override
    public void setValue(Object value) {
        this.value = value;
    }

    protected PropInfoDO clone() throws CloneNotSupportedException {
        try {
            return (PropInfoDO)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException("\u514b\u9686\u6570\u636e\u51fa\u9519", e);
        }
    }

    public static PropInfoDO valueOf(PropInfo propInfo) {
        if (propInfo == null) {
            return null;
        }
        PropInfoDO res = new PropInfoDO();
        PropInfoDO.copyProperties(propInfo, res);
        return res;
    }

    private static void copyProperties(PropInfo propInfo, PropInfoDO res) {
        res.setCode(propInfo.getCode());
        res.setDataType(propInfo.getDataType());
        res.setDecimal(propInfo.getDecimal());
        res.setDefaultValue(propInfo.getDefaultValue());
        res.setFieldName(propInfo.getFieldName());
        res.setKey(propInfo.getKey());
        res.setLevel(propInfo.getLevel());
        res.setMultiple(propInfo.getMultiple());
        res.setOrder(propInfo.getOrder());
        res.setPrecision(propInfo.getPrecision());
        res.setReferEntityId(propInfo.getReferEntityId());
        res.setTitle(propInfo.getTitle());
        res.setUpdateTime(propInfo.getUpdateTime());
        res.setValue(propInfo.getValue());
    }
}

