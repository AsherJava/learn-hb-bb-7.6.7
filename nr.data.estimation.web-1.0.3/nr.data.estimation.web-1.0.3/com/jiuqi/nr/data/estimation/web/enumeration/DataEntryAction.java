/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.estimation.web.enumeration;

public enum DataEntryAction {
    selectForms("selectForms", "\u9009\u62e9\u62a5\u8868"),
    restoreFromDataentry("restoreFromDataentry", "\u4ece\u5f55\u5165\u8fd8\u539f"),
    restoreFromSnapshot("restoreFromSnapshot", "\u4ece\u5feb\u7167\u8fd8\u539f");

    public String name;
    public String title;

    private DataEntryAction(String name, String title) {
        this.name = name;
        this.title = title;
    }

    public static DataEntryAction findAction(String name) {
        for (DataEntryAction action : DataEntryAction.values()) {
            if (!action.name.equals(name)) continue;
            return action;
        }
        return null;
    }
}

