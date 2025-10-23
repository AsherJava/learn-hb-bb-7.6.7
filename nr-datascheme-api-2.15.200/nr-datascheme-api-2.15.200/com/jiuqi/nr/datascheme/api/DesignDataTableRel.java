/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api;

import com.jiuqi.nr.datascheme.api.DataTableRel;
import com.jiuqi.nr.datascheme.api.type.RelationType;

public interface DesignDataTableRel
extends DataTableRel {
    public void setKey(String var1);

    public void setType(RelationType var1);

    public void setDataSchemeKey(String var1);

    public void setSrcTableKey(String var1);

    public void setDesTableKey(String var1);

    public void setSrcFieldKeys(String[] var1);

    public void setDesFieldKeys(String[] var1);
}

