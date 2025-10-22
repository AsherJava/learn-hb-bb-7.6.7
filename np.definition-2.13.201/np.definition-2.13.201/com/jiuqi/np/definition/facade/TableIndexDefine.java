/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.facade;

import com.jiuqi.np.definition.common.TableIndexType;

public interface TableIndexDefine {
    public String getKey();

    public String getName();

    public String[] getColumnsFieldKeys();

    public TableIndexType getIndexType();

    public String getDBName();

    public String getTableKey();
}

