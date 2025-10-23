/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.subdatabase.facade;

import java.util.Date;

public interface DataTableDDLInfo {
    public String getDataSchemeKey();

    public void setDataSchemeKey(String var1);

    public String getTableModelKey();

    public void setTableModelKey(String var1);

    public Date getDDLTime();

    public void setDDLTime(Date var1);

    public Integer getSyncOrder();

    public void setSyncOrder(Integer var1);
}

