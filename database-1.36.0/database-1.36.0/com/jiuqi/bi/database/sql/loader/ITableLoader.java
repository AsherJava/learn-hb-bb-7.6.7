/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.loader;

import com.jiuqi.bi.database.sql.loader.ILoadListener;
import com.jiuqi.bi.database.sql.loader.LoadFieldMap;
import com.jiuqi.bi.database.sql.loader.TableLoaderException;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.tables.SimpleTable;
import java.util.List;

public interface ITableLoader {
    public static final int DEFAULT_TRANSACTION_SIZE = 100000;
    public static final int OPT_NOLOGGING = 1;
    public static final int OPT_MERGE_BY_INDEX = 2;

    public ISQLTable getSourceTable();

    public void setSourceTable(ISQLTable var1);

    public SimpleTable getDestTable();

    public void setDestTable(SimpleTable var1);

    public List<LoadFieldMap> getFieldMaps();

    public int execute() throws TableLoaderException;

    public void setListener(ILoadListener var1);

    public void setTransactionSize(int var1);

    public void setOption(int var1);
}

