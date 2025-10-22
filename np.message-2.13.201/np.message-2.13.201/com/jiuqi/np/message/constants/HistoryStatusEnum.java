/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.message.constants;

@Deprecated
public enum HistoryStatusEnum {
    READ(1, "\u5df2\u8bfb"),
    RUBBISH(2, "\u56de\u6536\u7ad9"),
    DELETE(3, "\u5220\u9664");

    private Integer code;
    private String title;

    private HistoryStatusEnum(Integer code, String title) {
        this.code = code;
        this.title = title;
    }

    public static HistoryStatusEnum getEnumByCode(Integer code) {
        HistoryStatusEnum[] types;
        for (HistoryStatusEnum tempType : types = HistoryStatusEnum.values()) {
            if (!tempType.getCode().equals(code)) continue;
            return tempType;
        }
        return null;
    }

    public static HistoryStatusEnum getEnumByTitle(String title) {
        HistoryStatusEnum[] types;
        for (HistoryStatusEnum tempType : types = HistoryStatusEnum.values()) {
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

