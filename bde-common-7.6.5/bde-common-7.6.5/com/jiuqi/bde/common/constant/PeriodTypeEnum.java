/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.bde.common.constant;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.StringUtils;

public enum PeriodTypeEnum {
    Y("Y", "\u6708\u5ea6"),
    J("J", "\u5b63\u5ea6"),
    H("H", "\u534a\u5e74"),
    N("N", "\u5e74\u5ea6");

    private String code;
    private String name;

    private PeriodTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static PeriodTypeEnum getByCode(String code) {
        if (StringUtils.isEmpty((String)code)) {
            return null;
        }
        for (PeriodTypeEnum periodTypeEnum : PeriodTypeEnum.values()) {
            if (!periodTypeEnum.getCode().equals(code)) continue;
            return periodTypeEnum;
        }
        return null;
    }

    public int converterToMonth(int period) {
        switch (this) {
            case Y: {
                return period;
            }
            case J: {
                return period * 3;
            }
            case H: {
                return period * 6;
            }
            case N: {
                return 12;
            }
        }
        throw new BusinessRuntimeException("\u4e0d\u652f\u6301\u7684\u7c7b\u578b" + this.name);
    }

    public int monthToPeriod(int month) {
        switch (this) {
            case Y: {
                return month;
            }
            case J: {
                return month / 3;
            }
            case H: {
                return month / 6;
            }
            case N: {
                return 1;
            }
        }
        throw new BusinessRuntimeException("\u4e0d\u652f\u6301\u7684\u7c7b\u578b" + this.name);
    }
}

