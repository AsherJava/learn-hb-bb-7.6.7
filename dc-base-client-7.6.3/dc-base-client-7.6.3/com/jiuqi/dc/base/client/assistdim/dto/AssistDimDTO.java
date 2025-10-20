/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.dc.base.common.enums.PublishFlagEnum
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 */
package com.jiuqi.dc.base.client.assistdim.dto;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.base.common.enums.PublishFlagEnum;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import java.util.List;
import java.util.Objects;

public class AssistDimDTO
extends BaseDataDO {
    public static final String FD_EFFECTTABLELIST = "effectTableList";
    public static final String FD_EFFECTSCOPE = "effectScope";
    public static final Integer FN_DEFAULT_VALUELENGTH = 60;
    public static final Integer FD_DEFAULT_VALUEPRECISION = 0;
    public static final String FD_VALUETYPE = "valuetype";
    public static final String FD_VALUETYPEVO = "valuetypeVo";
    public static final String FD_VALUELENGTH = "valuelength";
    public static final String FD_VALUEPRECISION = "valueprecision";
    public static final String FD_PUBLISHFLAG = "publishflag";
    public static final String FD_CONVERTBYOPPOSITE = "convertbyopposite";
    public static final String FD_REMARK = "remark";

    public String getEffectScope() {
        return (String)this.get(FD_EFFECTSCOPE);
    }

    public void setEffectScope(String effectScope) {
        this.put(FD_EFFECTSCOPE, effectScope);
    }

    public List<String> getEffectTableList() {
        Object effectTableList = this.get(FD_EFFECTTABLELIST);
        if (effectTableList == null) {
            return CollectionUtils.newArrayList();
        }
        if (effectTableList instanceof List) {
            return (List)effectTableList;
        }
        return CollectionUtils.newArrayList();
    }

    public void setEffectTableList(List<String> effectTableList) {
        this.put(FD_EFFECTTABLELIST, effectTableList);
    }

    public String getValueType() {
        return (String)this.get(FD_VALUETYPE);
    }

    public void setValueType(String valueType) {
        this.put(FD_VALUETYPE, valueType);
    }

    public String getValueTypeVo() {
        return (String)this.get(FD_VALUETYPEVO);
    }

    public void setValueTypeVo(String valueType) {
        this.put(FD_VALUETYPEVO, valueType);
    }

    public Integer getValueLength() {
        Object valueLength = this.get(FD_VALUELENGTH);
        if (valueLength == null) {
            return null;
        }
        if (valueLength instanceof Integer) {
            return (Integer)valueLength;
        }
        return Integer.parseInt(valueLength.toString());
    }

    public void setValueLength(Integer valueLength) {
        this.put(FD_VALUELENGTH, valueLength);
    }

    public Integer getValuePrecision() {
        Object valuePrecision = this.get(FD_VALUEPRECISION);
        if (valuePrecision == null) {
            return null;
        }
        if (valuePrecision instanceof Integer) {
            return (Integer)valuePrecision;
        }
        return Integer.parseInt(valuePrecision.toString());
    }

    public void setValuePrecision(Integer valuePrecision) {
        this.put(FD_VALUEPRECISION, valuePrecision);
    }

    public Integer getPublishFlag() {
        Object publishFlag = this.get(FD_PUBLISHFLAG);
        if (publishFlag == null) {
            return PublishFlagEnum.UNPUBLISHED.getCode();
        }
        if (publishFlag instanceof Integer) {
            return (Integer)publishFlag;
        }
        return Integer.parseInt(publishFlag.toString());
    }

    public void setPublishFlag(Integer publishFlag) {
        this.put(FD_PUBLISHFLAG, publishFlag);
    }

    public Boolean isConvertByOpposite() {
        Object convertByOpposite = this.get(FD_CONVERTBYOPPOSITE);
        if (Objects.isNull(convertByOpposite)) {
            return Boolean.FALSE;
        }
        if (convertByOpposite instanceof Boolean) {
            return (Boolean)convertByOpposite;
        }
        return Integer.parseInt(String.valueOf(convertByOpposite)) == 1;
    }

    public void setConvertByOpposite(Boolean convertByOpposite) {
        this.put(FD_CONVERTBYOPPOSITE, convertByOpposite);
    }

    public String getRemark() {
        return (String)this.get(FD_REMARK);
    }

    public void setRemark(String remark) {
        this.put(FD_REMARK, remark);
    }
}

