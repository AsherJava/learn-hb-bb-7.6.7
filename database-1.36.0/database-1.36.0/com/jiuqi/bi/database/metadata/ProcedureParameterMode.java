/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.metadata;

public enum ProcedureParameterMode {
    IN("IN", "\u8f93\u5165"),
    OUT("OUT", "\u8f93\u51fa"),
    INOUT("IN/OUT", "\u8f93\u5165\u8f93\u51fa");

    private String value;
    private String title;

    private ProcedureParameterMode(String value, String title) {
        this.value = value;
        this.title = title;
    }

    public String getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public String toString() {
        return this.getTitle();
    }

    public static final ProcedureParameterMode find(String value) {
        ProcedureParameterMode[] modes;
        for (ProcedureParameterMode mode : modes = ProcedureParameterMode.values()) {
            if (!mode.getValue().equals(value)) continue;
            return mode;
        }
        return null;
    }
}

