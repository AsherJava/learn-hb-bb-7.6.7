/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.billcore.enums;

public enum AccountLockInfoEnum {
    INVEST("invest", "\u6295\u8d44\u53f0\u8d26"),
    ASSET("asset", "\u8d44\u4ea7\u53f0\u8d26"),
    LEASE("lease", "\u79df\u8d41\u53f0\u8d26"),
    UNLOCK("0", "\u5df2\u5f00\u542f"),
    LOCK("1", "\u5df2\u9501\u5b9a");

    private String code;
    private String title;

    private AccountLockInfoEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public static String getEnumTitleByValue(String code) {
        for (AccountLockInfoEnum lockStatusEnum : AccountLockInfoEnum.values()) {
            if (!lockStatusEnum.getCode().equals(code)) continue;
            return lockStatusEnum.getTitle();
        }
        return null;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

