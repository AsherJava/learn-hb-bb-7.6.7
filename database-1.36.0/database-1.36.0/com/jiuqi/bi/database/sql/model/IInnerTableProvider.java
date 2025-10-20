/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.model;

import com.jiuqi.bi.database.sql.model.ISQLTable;
import java.util.List;

public interface IInnerTableProvider {
    public List<ISQLTable> getInnerTables();
}

