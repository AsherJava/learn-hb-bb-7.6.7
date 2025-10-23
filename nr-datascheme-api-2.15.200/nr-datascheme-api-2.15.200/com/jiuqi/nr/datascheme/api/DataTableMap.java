/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api;

import java.io.Serializable;

public interface DataTableMap
extends Cloneable,
Serializable {
    public String getTableCode();

    public String getTableKey();

    public String getSrcKey();

    public String getSrcCode();

    public String getSrcType();
}

