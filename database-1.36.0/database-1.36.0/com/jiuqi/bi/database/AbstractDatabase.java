/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.ddl.DDLException;
import com.jiuqi.bi.database.ddl.DefaultDDLExcecutor;
import com.jiuqi.bi.database.ddl.IDDLExecutor;
import com.jiuqi.bi.database.desc.IDatabaseDescriptor;
import com.jiuqi.bi.database.metadata.DefaultSQLMetadata;
import com.jiuqi.bi.database.metadata.ISQLMetadata;
import com.jiuqi.bi.database.operator.DefaultTableRefactor;
import com.jiuqi.bi.database.operator.ITableRefactor;
import com.jiuqi.bi.database.sql.loader.DefaultTableWriter;
import com.jiuqi.bi.database.sql.loader.ITableLoader;
import com.jiuqi.bi.database.sql.loader.ITableWriter;
import com.jiuqi.bi.database.sql.loader.TableLoaderException;
import com.jiuqi.bi.database.sql.loader.defaultdb.DefaultInsertLoader;
import com.jiuqi.bi.database.sql.loader.defaultdb.DefaultMergeLoader;
import com.jiuqi.bi.database.sql.model.ISQLModelPrinter;
import com.jiuqi.bi.database.sql.model.ISQLPrintable;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.bi.database.statement.interpret.ISQLInterpretor;
import com.jiuqi.bi.database.syntax.ISyntaxInterpretor;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public abstract class AbstractDatabase
implements IDatabase {
    @Override
    public boolean match(Connection conn) throws SQLException {
        String url = conn.getMetaData().getURL();
        return this.match(url);
    }

    @Override
    public boolean isDatabase(String databaseName) {
        return databaseName != null && databaseName.equalsIgnoreCase(this.getName());
    }

    @Override
    public abstract IDatabaseDescriptor getDescriptor();

    @Override
    public abstract ISQLInterpretor createSQLInterpretor(Connection var1) throws SQLInterpretException;

    @Override
    public abstract ISyntaxInterpretor getSyntaxInterpretor();

    @Override
    public ISQLMetadata createMetadata(Connection conn) throws SQLException {
        return new DefaultSQLMetadata(conn);
    }

    @Override
    public ITableLoader createInsertLoader(Connection conn) throws TableLoaderException {
        return new DefaultInsertLoader(conn, this);
    }

    @Override
    public ITableLoader createMergeLoader(Connection conn, boolean updateOnly) throws TableLoaderException {
        return new DefaultMergeLoader(conn, this, !updateOnly);
    }

    @Override
    public final Properties parseProperties(String url) {
        Properties properties = new Properties();
        if (this.match(url)) {
            this.parseProperties(url, properties);
        }
        return properties;
    }

    @Override
    public ISQLModelPrinter createModelPrinter(ISQLPrintable model) throws SQLModelException {
        return null;
    }

    @Override
    public ITableWriter createTableWriter(Connection conn) throws SQLException {
        return new DefaultTableWriter(conn);
    }

    protected abstract void parseProperties(String var1, Properties var2);

    @Override
    public ITableRefactor createTableOperator(Connection conn) {
        DefaultTableRefactor refactor = new DefaultTableRefactor();
        refactor.setConnection(conn);
        return refactor;
    }

    @Override
    public IDDLExecutor createDDLExcecutor(Connection conn) throws DDLException {
        return new DefaultDDLExcecutor(conn, this);
    }
}

