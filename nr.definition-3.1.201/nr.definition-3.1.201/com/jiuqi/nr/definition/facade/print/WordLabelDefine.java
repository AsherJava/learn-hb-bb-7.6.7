/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.grid.Font
 */
package com.jiuqi.nr.definition.facade.print;

import com.jiuqi.np.grid.Font;
import java.io.Serializable;

public interface WordLabelDefine
extends Serializable,
Cloneable {
    public int getElement();

    public Font getFont();

    public int getHorizontalPos();

    public String getLocationCode();

    public String getOrder();

    public int getScope();

    public String getText();

    public int getVerticalPos();

    public void setElement(int var1);

    public void setFont(Font var1);

    public void setHorizontalPos(int var1);

    public void setLocationCode(String var1);

    public void setOrder(String var1);

    public void setScope(int var1);

    public void setText(String var1);

    public void setVerticalPos(int var1);

    public double getLetterSpace();

    public void setLetterSpace(double var1);

    public double getLineSpace();

    public void setLineSpace(double var1);

    public boolean isAutoWrap();

    public void setAutoWrap(boolean var1);
}

