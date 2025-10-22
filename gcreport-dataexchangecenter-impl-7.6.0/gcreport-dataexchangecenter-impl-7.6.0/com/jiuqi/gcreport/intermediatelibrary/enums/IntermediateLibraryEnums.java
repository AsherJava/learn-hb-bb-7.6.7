/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.intermediatelibrary.enums;

public enum IntermediateLibraryEnums {
    INTERMEDIATE_LIBRARY_SOURCE_TYPE("1", "\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u6570\u636e\u6e90"),
    INTERMEDIATE_LIBRARY_OTHER_TYPE("2", "\u5176\u4ed6\u6570\u636e\u6e90"),
    INTERMEDIATE_LIBRARY_FULL_COVERAGE("3", "\u5168\u91cf\u8986\u76d6"),
    INTERMEDIATE_LIBRARY_INCREMENTAL_UPDATE("4", "\u589e\u91cf\u66f4\u65b0");

    private String value;
    private String label;

    private IntermediateLibraryEnums(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}

