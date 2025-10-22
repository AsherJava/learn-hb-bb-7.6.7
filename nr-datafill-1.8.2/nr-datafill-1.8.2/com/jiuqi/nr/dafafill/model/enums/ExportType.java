/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.model.enums;

public enum ExportType {
    EXCEL(0, "Excel"),
    PDF(1, "Pdf"),
    TXT(2, "Txt"),
    CSV(3, "Csv");

    private int value;
    private String title;

    private ExportType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static ExportType valueOf(int value) {
        for (ExportType type : ExportType.values()) {
            if (value != type.value()) continue;
            return type;
        }
        return null;
    }
}

