/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.nrdx.data.dto;

public enum FileType {
    NRD("NRD"),
    NRDX("NRDX");

    private final String value;

    private FileType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

