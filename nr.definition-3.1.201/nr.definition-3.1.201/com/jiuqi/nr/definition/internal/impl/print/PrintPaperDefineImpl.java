/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.impl.print;

import com.jiuqi.nr.definition.facade.print.PrintPaperDefine;

public class PrintPaperDefineImpl
implements PrintPaperDefine {
    private static final long serialVersionUID = 8938537957650463489L;
    private int paperType;
    private int direction;
    private double marginLeft = 5.0;
    private double marginRight = 5.0;
    private double marginTop = 5.0;
    private double marginBottom = 5.0;
    private int marginUnit;

    @Override
    public int getDirection() {
        return this.direction;
    }

    @Override
    public int getPaperType() {
        return this.paperType;
    }

    @Override
    public void setPaperType(int paperType) {
        this.paperType = paperType;
    }

    @Override
    public void setDirection(int direction) {
        this.direction = direction;
    }

    @Override
    public double getMarginBottom() {
        return this.marginBottom;
    }

    @Override
    public double getMarginLeft() {
        return this.marginLeft;
    }

    @Override
    public double getMarginRight() {
        return this.marginRight;
    }

    @Override
    public double getMarginTop() {
        return this.marginTop;
    }

    @Override
    public int getMarginUnit() {
        return this.marginUnit;
    }

    @Override
    public void setMarginBottom(double marginBottom) {
        this.marginBottom = marginBottom;
    }

    @Override
    public void setMarginLeft(double marginLeft) {
        this.marginLeft = marginLeft;
    }

    @Override
    public void setMarginRight(double marginRight) {
        this.marginRight = marginRight;
    }

    @Override
    public void setMarginTop(double marginTop) {
        this.marginTop = marginTop;
    }

    @Override
    public void setMarginUnit(int marginUnit) {
        this.marginUnit = marginUnit;
    }
}

