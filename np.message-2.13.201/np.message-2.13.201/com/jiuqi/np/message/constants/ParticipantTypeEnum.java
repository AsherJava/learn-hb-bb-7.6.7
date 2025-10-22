/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.message.constants;

import com.jiuqi.np.message.constants.MessageTypeEnum;

public enum ParticipantTypeEnum {
    USER(1, "\u7528\u6237"),
    ROLE(2, "\u89d2\u8272"),
    ENTITY(3, "\u4e3b\u4f53"),
    SYSTEM(4, "\u7cfb\u7edf");

    private Integer code;
    private String title;

    private ParticipantTypeEnum(Integer code, String title) {
        this.code = code;
        this.title = title;
    }

    public static MessageTypeEnum getEnumByCode(Integer code) {
        MessageTypeEnum[] types;
        for (MessageTypeEnum tempType : types = MessageTypeEnum.values()) {
            if (tempType.getCode() != code) continue;
            return tempType;
        }
        return null;
    }

    public static MessageTypeEnum getEnumByTitle(String title) {
        MessageTypeEnum[] types;
        for (MessageTypeEnum tempType : types = MessageTypeEnum.values()) {
            if (!tempType.getTitle().equals(title)) continue;
            return tempType;
        }
        return null;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }
}

