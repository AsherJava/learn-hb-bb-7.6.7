/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.sql.loader.ITableLoader
 *  com.jiuqi.bi.database.sql.loader.TableLoaderException
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.io.tz.service.impl;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.loader.ITableLoader;
import com.jiuqi.bi.database.sql.loader.TableLoaderException;
import com.jiuqi.nr.io.tz.service.impl.BaseLoader;
import java.sql.Connection;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class BaseInsertLoader
extends BaseLoader {
    public BaseInsertLoader(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected ITableLoader buildLoader(IDatabase database, Connection connection) throws TableLoaderException {
        return database.createInsertLoader(connection);
    }

    @Override
    protected Integer getTransactionSize() {
        return null;
    }
}

