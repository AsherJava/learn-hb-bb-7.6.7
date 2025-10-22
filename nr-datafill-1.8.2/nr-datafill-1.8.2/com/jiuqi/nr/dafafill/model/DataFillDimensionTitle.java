/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.model;

import java.io.Serializable;

public class DataFillDimensionTitle
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String code;
    private Integer level;
    private String title;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getLevel() {
        return this.level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

