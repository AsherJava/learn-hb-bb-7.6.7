/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bill.domain.constant;

public enum BillSyncExtendType {
    AFTER_INIT("afterInit", "\u521d\u59cb\u540e"),
    BEFORE_SAVE("beforeSave", "\u4fdd\u5b58\u524d"),
    AFTER_SAVE("afterSave", "\u4fdd\u5b58\u540e"),
    BEFORE_TEMP_SAVE("beforeTempSave", "\u6682\u5b58\u524d"),
    AFTER_TEMP_SAVE("afterTempSave", "\u6682\u5b58\u540e");

    private final String name;
    private final String title;

    private BillSyncExtendType(String name, String title) {
        this.name = name;
        this.title = title;
    }

    public String getName() {
        return this.name;
    }

    public String getTitle() {
        return this.title;
    }
}

