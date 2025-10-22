/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.enumcheck.common;

import com.jiuqi.nr.enumcheck.common.DataFdInfoBase;

public class DataFdInfo
extends DataFdInfoBase {
    private String code;
    private String name;
    private int fixedSize;
    private boolean nullAble;
    private String tableName;
    private boolean isEntityTable;
    private String key;

    public int getFixedSize() {
        return this.fixedSize;
    }

    public void setIsFixedSize(int fixSize) {
        this.fixedSize = fixSize;
    }

    public boolean isNullAble() {
        return this.nullAble;
    }

    public void setNullAble(boolean nullAble) {
        this.nullAble = nullAble;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEntityTable() {
        return this.isEntityTable;
    }

    public void setIsEntityTable(boolean b) {
        this.isEntityTable = b;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

