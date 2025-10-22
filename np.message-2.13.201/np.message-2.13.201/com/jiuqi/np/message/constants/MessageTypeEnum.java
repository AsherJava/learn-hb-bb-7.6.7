/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.message.constants;

public enum MessageTypeEnum {
    ANNOUNCE(1, "\u516c\u544a"),
    NEWS(2, "\u65b0\u95fb"),
    NOTICE(3, "\u901a\u77e5"),
    TODO(4, "\u5f85\u529e"),
    ACTIVITY(5, "\u5de5\u4f5c\u6d41"),
    SCHEDULE(6, "\u65e5\u7a0b"),
    APPROVAL(7, "\u5ba1\u6279");

    private Integer code;
    private String title;

    private MessageTypeEnum(Integer code, String title) {
        this.code = code;
        this.title = title;
    }

    public static MessageTypeEnum getEnumByCode(Integer code) {
        MessageTypeEnum[] types;
        for (MessageTypeEnum tempType : types = MessageTypeEnum.values()) {
            if (!tempType.getCode().equals(code)) continue;
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

