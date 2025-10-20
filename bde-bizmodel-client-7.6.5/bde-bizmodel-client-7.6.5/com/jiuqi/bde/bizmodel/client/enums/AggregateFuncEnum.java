/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.common.base.BusinessRuntimeException
 */
package com.jiuqi.bde.bizmodel.client.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.bde.bizmodel.client.vo.SelectField;
import com.jiuqi.common.base.BusinessRuntimeException;

@JsonFormat(shape=JsonFormat.Shape.OBJECT)
public enum AggregateFuncEnum {
    SUM("SUM", "\u6c42\u548c", false, "SUM(%1$s)"),
    COUNT("COUNT", "\u6c42\u6570\u91cf", false, "COUNT(%1$s)"),
    AVG("AVG", "\u6c42\u5e73\u5747", false, "AVG(%1$s)"),
    ORIGINAL("ORIGINAL", "\u539f\u503c", true, "%1$s");

    private String funcCode;
    private String funcName;
    @JsonIgnore
    private boolean enableGroup;
    @JsonIgnore
    private String funcStr;

    private AggregateFuncEnum(String funcCode, String funcName, boolean enableGroup, String funcStr) {
        this.funcCode = funcCode;
        this.funcName = funcName;
        this.enableGroup = enableGroup;
        this.funcStr = funcStr;
    }

    public String getFuncCode() {
        return this.funcCode;
    }

    public String getFuncName() {
        return this.funcName;
    }

    public boolean isEnableGroup() {
        return this.enableGroup;
    }

    public String getFuncStr() {
        return this.funcStr;
    }

    public static String buildSelectFieldSql(SelectField selectField) {
        AggregateFuncEnum enumByCode = AggregateFuncEnum.getEnumByCode(selectField.getAggregateFuncCode());
        return String.format(enumByCode.getFuncStr(), String.format("%1$s.%2$s", "MAINTABLE", selectField.getFieldCode()));
    }

    public static AggregateFuncEnum getEnumByCode(String funcCode) {
        for (AggregateFuncEnum aggregateFuncEnum : AggregateFuncEnum.values()) {
            if (!aggregateFuncEnum.getFuncCode().equalsIgnoreCase(funcCode)) continue;
            return aggregateFuncEnum;
        }
        throw new BusinessRuntimeException(String.format("\u672a\u627e\u5230%1$s\u5bf9\u5e94\u7684AggregateFuncEnum\u679a\u4e3e", funcCode));
    }
}

