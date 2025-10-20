/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.sql.DBException
 *  com.jiuqi.bi.sql.SQLPretreatment
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.database.sql.model.tables;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.SQLHelper;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.database.sql.model.tables.AbstractTable;
import com.jiuqi.bi.sql.DBException;
import com.jiuqi.bi.sql.SQLPretreatment;
import com.jiuqi.bi.util.StringUtils;
import java.util.List;

public class InnerTable
extends AbstractTable {
    private static final long serialVersionUID = 4290455691991257458L;
    private String sql;

    public InnerTable() {
    }

    public InnerTable(String sql) {
        this(sql, null);
    }

    public InnerTable(String sql, String alias) {
        super(null, alias);
        this.sql = sql;
    }

    @Override
    public String name() {
        return null;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String sql() {
        return this.sql;
    }

    @Override
    public boolean isSimpleMode() {
        return true;
    }

    @Override
    protected void toTableSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        buffer.append('(').append(this.sql);
        if (this.needNewLine()) {
            buffer.append(StringUtils.LINE_SEPARATOR);
        }
        buffer.append(')');
        SQLHelper.printAlias(database, this.alias(), options, buffer);
    }

    private boolean needNewLine() {
        if (this.sql == null) {
            return false;
        }
        int lastLine = this.sql.lastIndexOf(13);
        if (lastLine == -1) {
            lastLine = 0;
        }
        return this.sql.indexOf("--", lastLine) >= 0;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append('(').append(this.sql).append(')');
        if (!StringUtils.isEmpty((String)this.alias())) {
            buffer.append(' ').append(this.alias());
        }
        return buffer.toString();
    }

    @Override
    public boolean containsParameter() {
        SQLPretreatment p = new SQLPretreatment(this.sql);
        try {
            p.execute();
        }
        catch (DBException e) {
            throw new RuntimeException(e);
        }
        return p.getParamNames().length > 0;
    }

    @Override
    public List<ISQLTable> getInnerTables() {
        return null;
    }
}

