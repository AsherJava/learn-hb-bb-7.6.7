/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 */
package com.jiuqi.nr.multcheck2.common;

import com.jiuqi.nvwa.definition.common.ColumnModelType;

public class MCColumnDefine {
    private String name;
    private ColumnModelType type;
    private int precision;
    private boolean nullAble;
    private boolean primary;
    private boolean index;
    private boolean dimension;

    public MCColumnDefine() {
    }

    public MCColumnDefine(String name, ColumnModelType type) {
        this.name = name;
        this.type = type;
    }

    public MCColumnDefine(String name, ColumnModelType type, int precision, boolean nullAble) {
        this.name = name;
        this.type = type;
        this.precision = precision;
        this.nullAble = nullAble;
    }

    public MCColumnDefine(String name, ColumnModelType type, int precision, boolean nullAble, boolean primary, boolean index) {
        this.name = name;
        this.type = type;
        this.precision = precision;
        this.nullAble = nullAble;
        this.primary = primary;
        this.index = index;
    }

    public MCColumnDefine(String name, ColumnModelType type, int precision, boolean nullAble, boolean primary, boolean index, boolean dimension) {
        this.name = name;
        this.type = type;
        this.precision = precision;
        this.nullAble = nullAble;
        this.primary = primary;
        this.index = index;
        this.dimension = dimension;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ColumnModelType getType() {
        return this.type;
    }

    public void setType(ColumnModelType type) {
        this.type = type;
    }

    public int getPrecision() {
        return this.precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public boolean isNullAble() {
        return this.nullAble;
    }

    public void setNullAble(boolean nullAble) {
        this.nullAble = nullAble;
    }

    public boolean isPrimary() {
        return this.primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public boolean isIndex() {
        return this.index;
    }

    public void setIndex(boolean index) {
        this.index = index;
    }

    public boolean isDimension() {
        return this.dimension;
    }

    public void setDimension(boolean dimension) {
        this.dimension = dimension;
    }
}

