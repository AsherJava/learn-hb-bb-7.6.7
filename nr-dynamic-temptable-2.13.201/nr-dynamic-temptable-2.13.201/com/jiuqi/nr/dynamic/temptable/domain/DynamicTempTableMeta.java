/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dynamic.temptable.domain;

import com.jiuqi.nr.dynamic.temptable.framework.pool.IDynamicTempTablePool;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DynamicTempTableMeta
implements Closeable {
    private String tableName;
    private final List<String> columns = new ArrayList<String>();
    private String primaryKey;
    private final IDynamicTempTablePool dynamicTempTablePool;

    public DynamicTempTableMeta(IDynamicTempTablePool dynamicTempTablePool) {
        this.dynamicTempTablePool = dynamicTempTablePool;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getColumns() {
        return this.columns;
    }

    public String getPrimaryKey() {
        return this.primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    @Override
    public void close() throws IOException {
        this.dynamicTempTablePool.releaseTempTable(this.tableName);
    }
}

