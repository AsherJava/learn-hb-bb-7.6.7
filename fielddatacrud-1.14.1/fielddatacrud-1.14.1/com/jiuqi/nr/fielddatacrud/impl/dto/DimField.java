/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.fielddatacrud.impl.dto;

public class DimField {
    private String code;
    private String title;
    private String dimName;
    public static int P_DIM = 1;
    public static int T_DIM = 2;
    private int type;
    private int index = -1;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDimName() {
        return this.dimName;
    }

    public void setDimName(String dimName) {
        this.dimName = dimName;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

