/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.conditionalstyle.facade;

import com.jiuqi.nr.conditionalstyle.facade.ConditionalStyle;
import java.util.Date;

public interface DesignConditionalStyle
extends ConditionalStyle {
    public void setKey(String var1);

    public void setFormKey(String var1);

    public void setPosX(int var1);

    public void setPosY(int var1);

    public void setStyleExpression(String var1);

    public void setFontColor(String var1);

    public void setForeGroundColor(String var1);

    public void setBold(Boolean var1);

    public void setItalic(Boolean var1);

    public void setReadOnly(Boolean var1);

    public void setOrder(String var1);

    public void setUpdateTime(Date var1);

    public void setLinkKey(String var1);

    public void setHorizontalBar(Boolean var1);

    public void setStrikeThrough(Boolean var1);
}

