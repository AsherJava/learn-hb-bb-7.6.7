/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database;

import com.jiuqi.bi.database.ddl.DDLException;
import com.jiuqi.bi.database.ddl.IDDLExecutor;
import com.jiuqi.bi.database.desc.IDatabaseDescriptor;
import com.jiuqi.bi.database.metadata.ISQLMetadata;
import com.jiuqi.bi.database.operator.ITableRefactor;
import com.jiuqi.bi.database.paging.IPagingSQLBuilder;
import com.jiuqi.bi.database.paging.UnsupportPagingException;
import com.jiuqi.bi.database.sql.loader.ITableLoader;
import com.jiuqi.bi.database.sql.loader.ITableWriter;
import com.jiuqi.bi.database.sql.loader.TableLoaderException;
import com.jiuqi.bi.database.sql.model.ISQLModelPrinter;
import com.jiuqi.bi.database.sql.model.ISQLPrintable;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.bi.database.statement.interpret.ISQLInterpretor;
import com.jiuqi.bi.database.syntax.ISyntaxInterpretor;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public interface IDatabase {
    public static final String PROP_DATABASE_SERVER = "_server";
    public static final String PROP_DATABASE_PORT = "_port";
    public static final String PROP_DATABASE_DBNAME = "_databaseName";
    public static final String CUSTOM_PROP_DBNAME = "_jq_dbname";

    public String getName();

    public String getTitle();

    public boolean isDatabase(String var1);

    public String getJDBCClassName();

    public boolean match(String var1);

    public boolean match(Connection var1) throws SQLException;

    public String toUrl(String var1, int var2, String var3);

    public Properties parseProperties(String var1);

    public IDatabaseDescriptor getDescriptor();

    public ISQLInterpretor createSQLInterpretor(Connection var1) throws SQLInterpretException;

    public ISyntaxInterpretor getSyntaxInterpretor();

    public ISQLMetadata createMetadata(Connection var1) throws SQLException;

    public IPagingSQLBuilder createPagingSQLBuilder() throws UnsupportPagingException;

    public ITableLoader createInsertLoader(Connection var1) throws TableLoaderException;

    public ITableLoader createMergeLoader(Connection var1, boolean var2) throws TableLoaderException;

    public ISQLModelPrinter createModelPrinter(ISQLPrintable var1) throws SQLModelException;

    public ITableWriter createTableWriter(Connection var1) throws SQLException;

    public ITableRefactor createTableOperator(Connection var1) throws SQLException;

    public IDDLExecutor createDDLExcecutor(Connection var1) throws DDLException;
}

