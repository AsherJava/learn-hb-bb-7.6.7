/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.migration.transferdata.bean;

import java.io.Serializable;

public enum DataSynRange implements Serializable
{
    dataOnly(0, "\u53ea\u540c\u6b65\u6570\u636e"),
    stateOnly(1, "\u53ea\u540c\u6b65\u72b6\u6001"),
    dataAndState(2, "\u540c\u6b65\u6570\u636e\u548c\u72b6\u6001");

    private int value;
    private String title;

    private DataSynRange(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public int getValue() {
        return this.value;
    }

    public static DataSynRange valueOf(int value) {
        if (value == 0) {
            return dataOnly;
        }
        if (value == 1) {
            return stateOnly;
        }
        return dataAndState;
    }
}

