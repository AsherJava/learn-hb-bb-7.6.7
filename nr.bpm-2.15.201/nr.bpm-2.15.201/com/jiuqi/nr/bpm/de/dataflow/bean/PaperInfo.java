/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.print.PrintPaperDefine
 */
package com.jiuqi.nr.bpm.de.dataflow.bean;

import com.jiuqi.nr.definition.facade.print.PrintPaperDefine;

public class PaperInfo
implements PrintPaperDefine {
    private int direction;
    private double marginBottom;
    private double marginLeft;
    private double marginRight;
    private double marginTop;
    private int marginUnit;
    private int paperType;

    public int getDirection() {
        return this.direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public double getMarginBottom() {
        return this.marginBottom;
    }

    public void setMarginBottom(double marginBottom) {
        this.marginBottom = marginBottom;
    }

    public double getMarginLeft() {
        return this.marginLeft;
    }

    public void setMarginLeft(double marginLeft) {
        this.marginLeft = marginLeft;
    }

    public double getMarginRight() {
        return this.marginRight;
    }

    public void setMarginRight(double marginRight) {
        this.marginRight = marginRight;
    }

    public double getMarginTop() {
        return this.marginTop;
    }

    public void setMarginTop(double marginTop) {
        this.marginTop = marginTop;
    }

    public int getMarginUnit() {
        return this.marginUnit;
    }

    public void setMarginUnit(int marginUnit) {
        this.marginUnit = marginUnit;
    }

    public int getPaperType() {
        return this.paperType;
    }

    public void setPaperType(int paperType) {
        this.paperType = paperType;
    }
}

