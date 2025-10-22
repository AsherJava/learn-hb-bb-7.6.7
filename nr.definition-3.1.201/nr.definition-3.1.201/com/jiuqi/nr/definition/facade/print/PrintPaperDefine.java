/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade.print;

import java.io.Serializable;

public interface PrintPaperDefine
extends Serializable {
    public int getDirection();

    public double getMarginBottom();

    public double getMarginLeft();

    public double getMarginRight();

    public double getMarginTop();

    public int getMarginUnit();

    public int getPaperType();

    public void setDirection(int var1);

    public void setMarginBottom(double var1);

    public void setMarginLeft(double var1);

    public void setMarginRight(double var1);

    public void setMarginTop(double var1);

    public void setMarginUnit(int var1);

    public void setPaperType(int var1);
}

