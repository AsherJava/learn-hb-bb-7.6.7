/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 */
package com.jiuqi.nr.formtype.common;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape=JsonFormat.Shape.OBJECT)
public enum RelatedState {
    NONE(0, "\u6ca1\u6709\u88ab\u5173\u8054"),
    RELATED(1, "\u88ab\u5173\u8054"),
    USED(2, "\u88ab\u5173\u8054\u5207\u6709\u6570\u636e");

    private int state;
    private String desc;

    private RelatedState(int state, String desc) {
        this.state = state;
        this.desc = desc;
    }

    public int getState() {
        return this.state;
    }

    public String getDesc() {
        return this.desc;
    }
}

