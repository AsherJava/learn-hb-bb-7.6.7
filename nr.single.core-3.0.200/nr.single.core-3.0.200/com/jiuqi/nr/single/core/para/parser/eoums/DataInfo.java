/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.para.parser.eoums;

public class DataInfo {
    private String code;
    private String mapCode;
    private String name;
    private boolean used = false;
    private boolean startFlag = true;
    private String parent;
    private int rowNum;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMapCode() {
        return this.mapCode;
    }

    public void setMapCode(String mapCode) {
        this.mapCode = mapCode;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public int getRowNum() {
        return this.rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public boolean isUsed() {
        return this.used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public boolean isStartFlag() {
        return this.startFlag;
    }

    public void setStartFlag(boolean startFlag) {
        this.startFlag = startFlag;
    }
}

