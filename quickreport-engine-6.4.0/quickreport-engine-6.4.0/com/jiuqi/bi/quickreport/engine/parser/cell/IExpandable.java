/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.engine.parser.cell;

public interface IExpandable {
    public boolean isRowExpanding();

    public boolean isColExpanding();

    public void setRowExpanding(boolean var1);

    public void setColExpanding(boolean var1);
}

