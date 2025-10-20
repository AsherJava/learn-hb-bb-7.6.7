/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 */
package com.jiuqi.bde.common.constant;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;

public enum AgingPeriodTypeEnum {
    Y("Y", "\u5e74", 1),
    M("M", "\u6708", 2);

    private final String code;
    private final String name;
    private int calendarField;

    private AgingPeriodTypeEnum(String code, String name, int calendarField) {
        this.code = code;
        this.name = name;
        this.calendarField = calendarField;
    }

    public int getCalendarField() {
        return this.calendarField;
    }

    public void setCalendarField(int calendarField) {
        this.calendarField = calendarField;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static AgingPeriodTypeEnum fromCode(String code) {
        Assert.isNotEmpty((String)code);
        for (AgingPeriodTypeEnum type : AgingPeriodTypeEnum.values()) {
            if (!type.getCode().equals(code)) continue;
            return type;
        }
        throw new BusinessRuntimeException(String.format("\u4e0d\u652f\u6301\u7684\u671f\u95f4\u7c7b\u578b\u3010%1$s\u3011", code));
    }

    public static AgingPeriodTypeEnum getAgingPeriodTypeEnumByName(String name) {
        for (AgingPeriodTypeEnum type : AgingPeriodTypeEnum.values()) {
            if (!type.getName().equals(name)) continue;
            return type;
        }
        return null;
    }
}

