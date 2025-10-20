/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.impl.anslysis;

import com.jiuqi.nr.definition.common.LineType;
import com.jiuqi.nr.definition.facade.analysis.LineCaliber;

public class LineCaliberImpl
implements LineCaliber {
    private LineType type;
    private int lineNumber;
    private int colNumber;
    private String condition;

    @Override
    public LineType getType() {
        return this.type;
    }

    @Override
    public void setType(LineType type) {
        this.type = type;
    }

    @Override
    public int getLineNumber() {
        return this.lineNumber;
    }

    @Override
    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    @Override
    public String getCondition() {
        return this.condition;
    }

    @Override
    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public int getColNumber() {
        return this.colNumber;
    }

    @Override
    public void setColNumber(int colNumber) {
        this.colNumber = colNumber;
    }
}

