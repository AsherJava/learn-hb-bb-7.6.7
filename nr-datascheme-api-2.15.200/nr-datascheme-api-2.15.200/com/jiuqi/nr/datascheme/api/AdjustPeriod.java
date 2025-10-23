/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api;

import com.jiuqi.nr.datascheme.api.core.OrderSetter;
import com.jiuqi.nr.datascheme.api.core.Ordered;
import java.io.Serializable;

public interface AdjustPeriod
extends Serializable,
Ordered,
OrderSetter {
    public String getDataSchemeKey();

    public String getPeriod();

    public String getCode();

    public String getTitle();
}

