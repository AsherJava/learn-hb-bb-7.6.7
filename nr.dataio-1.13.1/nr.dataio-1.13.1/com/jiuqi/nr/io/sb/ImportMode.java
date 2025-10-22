/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.fielddatacrud.ImpMode
 */
package com.jiuqi.nr.io.sb;

import com.jiuqi.nr.fielddatacrud.ImpMode;

public enum ImportMode {
    ALL(1, "\u5168\u91cf\u5bfc\u5165"),
    INCREMENT(2, "\u589e\u91cf\u5bfc\u5165");

    private final int value;
    private final String title;

    private ImportMode(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    static ImpMode toImpMode(ImportMode importMode) {
        ImpMode result = null;
        switch (importMode) {
            case ALL: {
                result = ImpMode.FULL;
                break;
            }
            case INCREMENT: {
                result = ImpMode.ADD;
            }
        }
        return result;
    }
}

