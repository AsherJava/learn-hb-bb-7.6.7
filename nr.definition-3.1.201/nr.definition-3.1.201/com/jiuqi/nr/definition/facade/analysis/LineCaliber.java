/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade.analysis;

import com.jiuqi.nr.definition.common.LineType;

public interface LineCaliber {
    public LineType getType();

    public void setType(LineType var1);

    public int getLineNumber();

    public void setLineNumber(int var1);

    public String getCondition();

    public void setCondition(String var1);

    public int getColNumber();

    public void setColNumber(int var1);
}

