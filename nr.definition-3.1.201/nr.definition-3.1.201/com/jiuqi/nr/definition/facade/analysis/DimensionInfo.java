/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade.analysis;

import com.jiuqi.nr.definition.common.DimensionType;
import com.jiuqi.nr.definition.facade.analysis.DimensionAttribute;

public interface DimensionInfo {
    public DimensionType getType();

    public void setType(DimensionType var1);

    public String getKey();

    public void setKey(String var1);

    public String getTitle();

    public void setTitle(String var1);

    public String getCode();

    public void setCode(String var1);

    public DimensionAttribute getConfig();

    public void setConfig(DimensionAttribute var1);

    public String getName();

    public void setName(String var1);

    public String getViewKey();

    public void setViewKey(String var1);
}

