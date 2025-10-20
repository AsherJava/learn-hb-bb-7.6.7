/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.excel.print;

import java.io.Serializable;

public final class Margin
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 5599843499944364754L;
    private double left = 1.8;
    private double right = 1.8;
    private double top = 1.9;
    private double bottom = 1.9;
    private double header = 0.8;
    private double footer = 0.8;

    public double getLeft() {
        return this.left;
    }

    public void setLeft(double left) {
        this.left = left;
    }

    public double getRight() {
        return this.right;
    }

    public void setRight(double right) {
        this.right = right;
    }

    public double getTop() {
        return this.top;
    }

    public void setTop(double top) {
        this.top = top;
    }

    public double getBottom() {
        return this.bottom;
    }

    public void setBottom(double bottom) {
        this.bottom = bottom;
    }

    public double getHeader() {
        return this.header;
    }

    public void setHeader(double header) {
        this.header = header;
    }

    public double getFooter() {
        return this.footer;
    }

    public void setFooter(double footer) {
        this.footer = footer;
    }

    protected Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}

