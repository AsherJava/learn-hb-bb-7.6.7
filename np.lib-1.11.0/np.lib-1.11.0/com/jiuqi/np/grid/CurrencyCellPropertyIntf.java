/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.grid;

import com.jiuqi.np.grid.NumberCellPropertyIntf;

public interface CurrencyCellPropertyIntf
extends NumberCellPropertyIntf {
    public int getUnitIndex();

    public void setUnitIndex(int var1);

    public int getUnitShowType();

    public void setUnitShowType(int var1);

    public String getUnitTitle();

    public void setUnitTitle(String var1);

    public boolean getShowCurrencyBox();

    public void setShowCurrencyBox(boolean var1);
}

