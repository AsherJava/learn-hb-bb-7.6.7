/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.enums;

public enum UnitSceneEnum {
    ALL("ALL", "\u6240\u6709\u6570\u636e"),
    UNILATERAL("UNILATERAL", "\u5355\u8fb9\u6302\u8d26"),
    BILATERAL("BILATERAL", "\u591a\u8fb9\u6302\u8d26");

    private String code;
    private String title;

    private UnitSceneEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static boolean needFilter(UnitSceneEnum value) {
        return null != value && !ALL.equals((Object)value);
    }

    public static boolean isUnilateral(UnitSceneEnum value) {
        return UNILATERAL.equals((Object)value);
    }

    public static boolean isBilateral(UnitSceneEnum value) {
        return BILATERAL.equals((Object)value);
    }
}

