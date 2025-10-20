/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.database.statement;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.bi.database.sql.parser.SQLParser;
import com.jiuqi.bi.database.sql.parser.SQLParserException;
import com.jiuqi.bi.database.statement.SqlStatement;
import com.jiuqi.bi.util.StringUtils;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SpecificStatement
extends SqlStatement {
    private boolean isBlockSql;
    private static final String OTHER_SQL = "OTHER";
    private static final String DEFAULT_SQL = "DEFAULT";
    private Map<String, List<String>> dbSqlMap = new HashMap<String, List<String>>();
    private Set<String> emptySqlSet = new HashSet<String>();

    public SpecificStatement(String sql, boolean isBlockSql) {
        super(sql);
        this.isBlockSql = isBlockSql;
    }

    public SpecificStatement clone() {
        try {
            SpecificStatement cloned = (SpecificStatement)super.clone();
            cloned.emptySqlSet = new HashSet<String>();
            for (String sql : this.emptySqlSet) {
                cloned.emptySqlSet.add(sql);
            }
            cloned.dbSqlMap = new HashMap<String, List<String>>();
            for (String key : this.dbSqlMap.keySet()) {
                ArrayList<String> sqlList = new ArrayList<String>();
                List<String> thisSqlList = this.dbSqlMap.get(key);
                for (String sql : thisSqlList) {
                    sqlList.add(sql);
                }
                cloned.dbSqlMap.put(key, sqlList);
            }
            return cloned;
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public void addSpecificSql(String dbName, String sql) {
        if (StringUtils.isEmpty((String)sql)) {
            this.emptySqlSet.add(dbName.toUpperCase());
            return;
        }
        String name = dbName.toUpperCase();
        List<String> value = this.dbSqlMap.get(name);
        if (value == null) {
            value = new ArrayList<String>();
            this.dbSqlMap.put(name, value);
        }
        value.add(sql);
    }

    @Override
    public List<String> interpret(IDatabase database, Connection conn) throws SQLInterpretException {
        if (this.isBlockSql) {
            List<String> otherSqls;
            if (database == null) {
                return this.dbSqlMap.get(DEFAULT_SQL);
            }
            String name = database.getName().toUpperCase();
            if (this.emptySqlSet.contains(name)) {
                return new ArrayList<String>();
            }
            List<Object> sql = this.dbSqlMap.get(name);
            if (sql == null && (otherSqls = this.dbSqlMap.get(OTHER_SQL)) != null) {
                sql = new ArrayList();
                SQLParser p = new SQLParser();
                for (String s : otherSqls) {
                    if (!s.endsWith(";")) {
                        s = s + ";";
                    }
                    try {
                        List<SqlStatement> sqlstatement = p.parse(s);
                        for (SqlStatement ss : sqlstatement) {
                            sql.addAll(ss.interpret(database, conn));
                        }
                    }
                    catch (SQLParserException e) {
                        throw new SQLInterpretException(e.getMessage(), e);
                    }
                }
                return sql;
            }
            if (sql == null) {
                sql = this.dbSqlMap.get(DEFAULT_SQL);
            }
            return sql == null ? new ArrayList() : sql;
        }
        if (this.orginSql == null) {
            return null;
        }
        ArrayList<String> sqls = new ArrayList<String>();
        sqls.add(this.orginSql);
        return sqls;
    }
}

