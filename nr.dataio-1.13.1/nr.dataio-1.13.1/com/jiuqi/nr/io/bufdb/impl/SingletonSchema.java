/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.bufdb.BufDBException
 *  com.jiuqi.bi.bufdb.config.SchemaConfig
 *  com.jiuqi.bi.bufdb.config.TableConfig
 *  com.jiuqi.bi.bufdb.db.IKVTable
 *  com.jiuqi.bi.bufdb.db.ISchema
 *  com.jiuqi.bi.bufdb.db.ITable
 *  com.jiuqi.bi.bufdb.define.TableDefine
 *  com.jiuqi.bi.logging.ILogger
 */
package com.jiuqi.nr.io.bufdb.impl;

import com.jiuqi.bi.bufdb.BufDBException;
import com.jiuqi.bi.bufdb.config.SchemaConfig;
import com.jiuqi.bi.bufdb.config.TableConfig;
import com.jiuqi.bi.bufdb.db.IKVTable;
import com.jiuqi.bi.bufdb.db.ISchema;
import com.jiuqi.bi.bufdb.db.ITable;
import com.jiuqi.bi.bufdb.define.TableDefine;
import com.jiuqi.bi.logging.ILogger;
import com.jiuqi.nr.io.bufdb.impl.SingletonIOBufDBProvider;
import java.util.List;

public final class SingletonSchema
implements ISchema {
    private final ISchema schema;
    private final SingletonIOBufDBProvider singletonIOBufDBProvider;

    public SingletonSchema(ISchema schema, SingletonIOBufDBProvider singletonIOBufDBProvider) {
        this.schema = schema;
        this.singletonIOBufDBProvider = singletonIOBufDBProvider;
    }

    public String getName() {
        return this.schema.getName();
    }

    public SchemaConfig getConfig() {
        return this.schema.getConfig();
    }

    public ITable createTable(TableDefine tableDefine, TableConfig tableConfig) throws BufDBException {
        return this.schema.createTable(tableDefine, tableConfig);
    }

    public ITable createTempTable(TableDefine tableDefine) throws BufDBException {
        return this.schema.createTempTable(tableDefine);
    }

    public ITable openTable(String s, TableConfig tableConfig) throws BufDBException {
        return this.schema.openTable(s, tableConfig);
    }

    public ITable openBufferedTable(String s) throws BufDBException {
        return this.schema.openBufferedTable(s);
    }

    public IKVTable createKVTable(String s, TableConfig tableConfig) throws BufDBException {
        return this.schema.createKVTable(s, tableConfig);
    }

    public IKVTable openKVTable(String s, TableConfig tableConfig) throws BufDBException {
        return this.schema.openKVTable(s, tableConfig);
    }

    public void dropTable(String s) throws BufDBException {
        this.schema.dropTable(s);
    }

    public void truncateTable(String s) throws BufDBException {
        this.schema.truncateTable(s);
    }

    public void renameTable(String s, String s1) throws BufDBException {
        this.schema.renameTable(s, s1);
    }

    public void close() throws BufDBException {
        this.schema.close();
        this.singletonIOBufDBProvider.close();
    }

    public boolean exists(String s) throws BufDBException {
        return this.schema.exists(s);
    }

    public List<String> getTables() throws BufDBException {
        return this.schema.getTables();
    }

    public void setLogger(ILogger iLogger) {
        this.schema.setLogger(iLogger);
    }

    public ILogger getLogger() {
        return this.schema.getLogger();
    }
}

