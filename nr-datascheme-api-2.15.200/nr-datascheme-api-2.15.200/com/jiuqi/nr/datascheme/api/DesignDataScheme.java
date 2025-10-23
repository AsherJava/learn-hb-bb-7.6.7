/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api;

import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.core.BasicSetter;
import com.jiuqi.nr.datascheme.api.core.OrderSetter;
import com.jiuqi.nr.datascheme.api.type.DataSchemeType;

public interface DesignDataScheme
extends DataScheme,
BasicSetter,
OrderSetter {
    public void setPrefix(String var1);

    public void setAuto(Boolean var1);

    public void setCreator(String var1);

    public void setType(DataSchemeType var1);

    public void setVersion(String var1);

    public void setLevel(String var1);

    public void setDataGroupKey(String var1);

    public void setBizCode(String var1);

    public void setGatherDB(Boolean var1);

    public void setEncryptScene(String var1);

    public void setZbSchemeKey(String var1);

    public void setZbSchemeVersion(String var1);

    public void setCalibre(String var1);
}

