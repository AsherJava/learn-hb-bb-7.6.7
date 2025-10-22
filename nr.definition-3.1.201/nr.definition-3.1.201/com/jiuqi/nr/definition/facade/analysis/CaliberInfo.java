/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade.analysis;

import com.jiuqi.nr.definition.common.DimensionType;

@Deprecated
public interface CaliberInfo {
    public String getKey();

    public void setKey(String var1);

    public String getTitle();

    public void setTitle(String var1);

    public String getTableKey();

    public void setTableKey(String var1);

    public String getCode();

    public void setCode(String var1);

    public String getTableCode();

    public void setTableCode(String var1);

    public String getTableName();

    public void setTableName(String var1);

    public DimensionType getType();

    public void setType(DimensionType var1);
}

