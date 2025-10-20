/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.exception.BusinessException
 */
package com.jiuqi.gcreport.aidocaudit.enums;

import com.jiuqi.np.core.exception.BusinessException;

public enum ResultTypeEnum {
    ALL(0, "\u5168\u90e8"),
    MATCH(1, "\u901a\u8fc7"),
    SUSPECTMATCH(2, "\u7591\u4f3c\u901a\u8fc7"),
    UNMATCH(3, "\u4e0d\u901a\u8fc7");

    private int code;
    private String title;

    private ResultTypeEnum(int code, String title) {
        this.code = code;
        this.title = title;
    }

    public int getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static ResultTypeEnum getEnumByCode(int code) {
        for (ResultTypeEnum fetchTypeEnum : ResultTypeEnum.values()) {
            if (fetchTypeEnum.getCode() != code) continue;
            return fetchTypeEnum;
        }
        throw new BusinessException("\u65e0\u6b64\u7c7b\u578b\u7684ResultTypeEnum\u679a\u4e3e code=" + code);
    }
}

