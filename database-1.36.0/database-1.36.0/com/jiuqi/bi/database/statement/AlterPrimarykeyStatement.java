/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.statement;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.bi.database.statement.AlterStatement;
import com.jiuqi.bi.database.statement.AlterType;
import com.jiuqi.bi.database.statement.interpret.ISQLInterpretor;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class AlterPrimarykeyStatement
extends AlterStatement {
    private final String tableName;
    private String pkName;
    private String rename;
    private List<String> primaryKeys = new ArrayList<String>();

    public AlterPrimarykeyStatement(String sql, String tableName) {
        this(sql, tableName, null);
    }

    public AlterPrimarykeyStatement(String sql, String tableName, AlterType alterType) {
        super(sql, alterType);
        this.tableName = tableName;
    }

    public AlterPrimarykeyStatement clone() {
        try {
            AlterPrimarykeyStatement cloned = (AlterPrimarykeyStatement)super.clone();
            cloned.primaryKeys = new ArrayList<String>();
            for (String key : this.primaryKeys) {
                cloned.primaryKeys.add(key);
            }
            return cloned;
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setPrimarykeyName(String pkName) {
        this.pkName = pkName;
    }

    public void setRename(String rename) {
        this.rename = rename;
    }

    public void addPrimaryKey(String columnName) {
        this.primaryKeys.add(columnName);
    }

    public String getTableName() {
        return this.tableName;
    }

    public String getPrimarykeyName() {
        return this.pkName;
    }

    public String getRename() {
        return this.rename;
    }

    public List<String> getPrimaryKeys() {
        return this.primaryKeys;
    }

    @Override
    public List<String> interpret(IDatabase database, Connection conn) throws SQLInterpretException {
        ISQLInterpretor interpretor = database.createSQLInterpretor(conn);
        return interpretor.alterPrimarykey(this);
    }
}

