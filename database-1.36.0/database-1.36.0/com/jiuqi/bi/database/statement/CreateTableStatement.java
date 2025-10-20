/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.statement;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.bi.database.statement.SqlStatement;
import com.jiuqi.bi.database.statement.interpret.ISQLInterpretor;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CreateTableStatement
extends SqlStatement {
    private String tableName;
    private String comment;
    private boolean isColumnStore;
    private boolean isJudgeExists;
    private List<LogicField> columns = new ArrayList<LogicField>();
    private String pkName;
    private List<String> primaryKeys = new ArrayList<String>();
    private Map<String, String> props = new HashMap<String, String>();

    public CreateTableStatement(String sql, String tableName) {
        super(sql);
        this.tableName = tableName;
    }

    public CreateTableStatement clone() {
        try {
            CreateTableStatement cloned = (CreateTableStatement)super.clone();
            cloned.columns = new ArrayList<LogicField>();
            for (LogicField lf : this.columns) {
                cloned.columns.add(lf.clone());
            }
            return cloned;
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public void addColumn(LogicField column) {
        this.columns.add(column);
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setColumnStore(boolean isColumnStore) {
        this.isColumnStore = isColumnStore;
    }

    public void setJudgeExists(boolean isJudgeExists) {
        this.isJudgeExists = isJudgeExists;
    }

    public boolean isJudgeExists() {
        return this.isJudgeExists;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return this.comment;
    }

    public boolean isColumnStore() {
        return this.isColumnStore;
    }

    public List<LogicField> getColumns() {
        return this.columns;
    }

    public void setPkName(String pkName) {
        this.pkName = pkName;
    }

    public String getPkName() {
        return this.pkName;
    }

    public List<String> getPrimaryKeys() {
        return this.primaryKeys;
    }

    public boolean hasPrimaryKey() {
        return !this.primaryKeys.isEmpty();
    }

    public Set<String> keySet() {
        return this.props.keySet();
    }

    public String getProperty(String key) {
        return this.props.get(key.toUpperCase());
    }

    public boolean containsProperty(String key) {
        return this.props.containsKey(key.toUpperCase());
    }

    public void setProperty(String key, String value) {
        this.props.put(key.toUpperCase(), value);
    }

    @Override
    public List<String> interpret(IDatabase database, Connection conn) throws SQLInterpretException {
        ISQLInterpretor interpretor = database.createSQLInterpretor(conn);
        return interpretor.createTable(this);
    }
}

