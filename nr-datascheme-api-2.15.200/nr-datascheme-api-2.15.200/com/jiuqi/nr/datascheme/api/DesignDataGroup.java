/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api;

import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.core.BasicSetter;
import com.jiuqi.nr.datascheme.api.core.GroupSetter;
import com.jiuqi.nr.datascheme.api.core.OrderSetter;
import com.jiuqi.nr.datascheme.api.type.DataGroupKind;

public interface DesignDataGroup
extends DataGroup,
GroupSetter,
BasicSetter,
OrderSetter {
    public void setLevel(String var1);

    public void setDataSchemeKey(String var1);

    public void setDataGroupKind(DataGroupKind var1);

    public void setVersion(String var1);
}

