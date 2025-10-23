/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api;

import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.BasicSetter;
import com.jiuqi.nr.datascheme.api.core.OrderSetter;
import com.jiuqi.nr.datascheme.api.type.DataTableGatherType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;

public interface DesignDataTable
extends DataTable,
BasicSetter,
OrderSetter {
    public void setDataSchemeKey(String var1);

    public void setDataGroupKey(String var1);

    public void setBizKeys(String[] var1);

    public void setDataTableType(DataTableType var1);

    public void setDataTableGatherType(DataTableGatherType var1);

    public void setVersion(String var1);

    public void setLevel(String var1);

    public void setRepeatCode(Boolean var1);

    public void setOwner(String var1);

    public void setTrackHistory(Boolean var1);

    public void setGatherFieldKeys(String[] var1);

    public void setSyncError(Boolean var1);

    public void setExpression(String var1);

    public void setAlias(String var1);
}

