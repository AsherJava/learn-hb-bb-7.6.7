/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 */
package com.jiuqi.nr.formulaeditor.vo;

import com.jiuqi.nr.definition.facade.DataLinkDefine;

public class LinkData {
    private int colNum;
    private int rowNum;
    private int x;
    private int y;
    private String text;
    private int type = -1;

    public LinkData() {
    }

    public LinkData(DataLinkDefine linkDefine) {
        this.x = linkDefine.getPosX();
        this.y = linkDefine.getPosY();
        this.rowNum = linkDefine.getRowNum();
        this.colNum = linkDefine.getColNum();
        this.type = linkDefine.getType().getValue();
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getColNum() {
        return this.colNum;
    }

    public void setColNum(int colNum) {
        this.colNum = colNum;
    }

    public int getRowNum() {
        return this.rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

