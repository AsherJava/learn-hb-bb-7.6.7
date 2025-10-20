/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.statement;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.LogicIndex;
import com.jiuqi.bi.database.metadata.LogicIndexField;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.bi.database.statement.SqlStatement;
import com.jiuqi.bi.database.statement.interpret.ISQLInterpretor;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class CreateIndexStatement
extends SqlStatement {
    private LogicIndex logicIndex = new LogicIndex();
    private boolean isJudgeExists;

    public CreateIndexStatement(String sql, String indexName) {
        this(sql, indexName, false);
    }

    public CreateIndexStatement(String sql, String indexName, boolean isUnique) {
        super(sql);
        this.logicIndex.setIndexName(indexName);
        this.logicIndex.setUnique(isUnique);
    }

    public LogicIndex getLogicIndex() {
        return this.logicIndex;
    }

    public void setLogicIndex(LogicIndex logicIndex) {
        this.logicIndex = logicIndex;
    }

    public CreateIndexStatement clone() {
        try {
            CreateIndexStatement cloned = (CreateIndexStatement)super.clone();
            cloned.logicIndex = this.logicIndex.clone();
            return cloned;
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setTableName(String tableName) {
        this.logicIndex.setTableName(tableName);
    }

    public void addIndexColumn(String columnName) {
        LogicIndexField field = new LogicIndexField();
        field.setFieldName(columnName);
        field.setSortType(1);
        this.logicIndex.addIndexField(field);
    }

    public void setJudgeExists(boolean isJudgeExists) {
        this.isJudgeExists = isJudgeExists;
    }

    public boolean isJudgeExists() {
        return this.isJudgeExists;
    }

    public String getTableName() {
        return this.logicIndex.getTableName();
    }

    public String getIndexName() {
        return this.logicIndex.getIndexName();
    }

    public List<String> getColumnNames() {
        List<LogicIndexField> fields = this.logicIndex.getIndexFields();
        ArrayList<String> columnNames = new ArrayList<String>();
        for (LogicIndexField f : fields) {
            columnNames.add(f.getFieldName());
        }
        return columnNames;
    }

    public boolean isUniqueIndex() {
        return this.logicIndex.isUnique();
    }

    public void setUnique(boolean isUnique) {
        this.logicIndex.setUnique(isUnique);
    }

    @Override
    public List<String> interpret(IDatabase database, Connection conn) throws SQLInterpretException {
        ISQLInterpretor interpretor = database.createSQLInterpretor(conn);
        return interpretor.createIndex(this);
    }
}

