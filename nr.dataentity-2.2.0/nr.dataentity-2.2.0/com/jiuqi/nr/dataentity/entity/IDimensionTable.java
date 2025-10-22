/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentity.entity;

import com.jiuqi.nr.dataentity.entity.IDimensionRow;
import java.util.List;

public interface IDimensionTable
extends Iterable<IDimensionRow> {
    public String getDimensionName();

    public String getDimensionEntityId();

    public List<IDimensionRow> getDatas();
}

