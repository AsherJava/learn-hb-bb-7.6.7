/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.sql.loader.ILoadListener
 *  com.jiuqi.bi.database.sql.loader.ITableLoader
 *  com.jiuqi.bi.database.sql.loader.TableLoaderException
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.io.tz.service.impl;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.loader.ILoadListener;
import com.jiuqi.bi.database.sql.loader.ITableLoader;
import com.jiuqi.bi.database.sql.loader.TableLoaderException;
import com.jiuqi.nr.io.tz.exception.TzImportException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;

public abstract class BaseLoader {
    protected IDatabase database;
    protected JdbcTemplate jdbcTemplate;
    protected ITableLoader loader;
    protected static Logger logger = LoggerFactory.getLogger(BaseLoader.class);

    public BaseLoader(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    protected abstract ITableLoader buildLoader(IDatabase var1, Connection var2) throws TableLoaderException;

    protected abstract void loadSrc();

    protected abstract void loadDest();

    protected abstract void loadFieldMaps();

    protected abstract Integer getTransactionSize();

    public int execute() throws TableLoaderException {
        Connection connection = null;
        try {
            connection = Objects.requireNonNull(this.jdbcTemplate.getDataSource()).getConnection();
            if (this.database == null) {
                this.database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            }
            this.loader = this.buildLoader(this.database, connection);
            this.loader.setListener(this.getListener());
            if (this.getTransactionSize() != null) {
                this.loader.setTransactionSize(this.getTransactionSize().intValue());
            }
            this.loadSrc();
            this.loadDest();
            this.loadFieldMaps();
            int n = this.loader.execute();
            return n;
        }
        catch (SQLException e) {
            throw new TzImportException(e);
        }
        finally {
            DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
        }
    }

    protected ILoadListener getListener() {
        return new ILoadListener(){

            public void executeSQL(String sql) {
                logger.info("\u6267\u884cSQL\uff1a" + sql);
            }

            public void executeProcedure(String sql) {
                logger.info("\u8c03\u7528\u5b58\u50a8\u8fc7\u7a0b\uff1a" + sql);
            }

            public void createProcedure(String procedure) {
                logger.info("\u521b\u5efa\u5b58\u50a8\u8fc7\u7a0b\uff1a" + procedure);
            }
        };
    }
}

