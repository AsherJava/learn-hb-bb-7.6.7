/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database;

import com.jiuqi.bi.database.AbstractDatabase;
import com.jiuqi.bi.database.DBException;
import com.jiuqi.bi.database.desc.DefaultDatabaseDescriptor;
import com.jiuqi.bi.database.desc.IDatabaseDescriptor;
import com.jiuqi.bi.database.paging.IPagingSQLBuilder;
import com.jiuqi.bi.database.paging.UnsupportPagingException;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.bi.database.statement.interpret.DefaultInterpretor;
import com.jiuqi.bi.database.statement.interpret.ISQLInterpretor;
import com.jiuqi.bi.database.syntax.ISyntaxInterpretor;
import java.sql.Connection;
import java.util.Properties;

public final class DefaultDatabase
extends AbstractDatabase {
    public static final String DATABASE_NAME = "DEFAULT";
    public static final String DATABASE_TITLE = "\u9ed8\u8ba4\u6570\u636e\u5e93\u5b9e\u73b0";
    public static final String DATABASE_JDBCCLASSNAME = "";
    private IDatabaseDescriptor descriptor = new DefaultDatabaseDescriptor();
    private ISQLInterpretor sqlInterpretor = new DefaultInterpretor(this);
    private ISyntaxInterpretor syntaxInterpretor = new ISyntaxInterpretor(){

        @Override
        public boolean toSQL(Object context, Object astNode, Object info, StringBuilder buffer) throws DBException {
            return false;
        }
    };

    @Override
    public String getName() {
        return DATABASE_NAME;
    }

    @Override
    public String getTitle() {
        return DATABASE_TITLE;
    }

    @Override
    public String getJDBCClassName() {
        return DATABASE_JDBCCLASSNAME;
    }

    @Override
    public boolean match(String url) {
        return false;
    }

    @Override
    public String toUrl(String server, int port, String dbName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IPagingSQLBuilder createPagingSQLBuilder() throws UnsupportPagingException {
        throw new UnsupportPagingException();
    }

    @Override
    public IDatabaseDescriptor getDescriptor() {
        return this.descriptor;
    }

    @Override
    public ISQLInterpretor createSQLInterpretor(Connection conn) throws SQLInterpretException {
        return this.sqlInterpretor;
    }

    @Override
    public ISyntaxInterpretor getSyntaxInterpretor() {
        return this.syntaxInterpretor;
    }

    @Override
    protected void parseProperties(String url, Properties properties) {
        throw new UnsupportedOperationException();
    }
}

