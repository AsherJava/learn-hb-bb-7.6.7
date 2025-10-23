/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api;

import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.Grouped;
import com.jiuqi.nr.datascheme.api.core.Ordered;
import com.jiuqi.nr.datascheme.api.type.DataGroupKind;

public interface DataGroup
extends Basic,
Grouped,
Ordered {
    public String getLevel();

    public String getDataSchemeKey();

    public DataGroupKind getDataGroupKind();

    public String getVersion();
}

