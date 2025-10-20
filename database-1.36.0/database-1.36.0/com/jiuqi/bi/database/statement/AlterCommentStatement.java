/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.statement;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.bi.database.statement.AlterStatement;
import com.jiuqi.bi.database.statement.AlterType;
import com.jiuqi.bi.database.statement.interpret.ISQLInterpretor;
import java.sql.Connection;
import java.util.List;

public class AlterCommentStatement
extends AlterStatement {
    private final String tableName;
    private LogicField column;
    private final String comment;

    public AlterCommentStatement(String tableName, String comment, AlterType alterType) {
        this(null, tableName, null, comment, alterType);
    }

    public AlterCommentStatement(String tableName, LogicField column, String comment, AlterType alterType) {
        this(null, tableName, column, comment, alterType);
    }

    public AlterCommentStatement(String sql, String tableName, LogicField column, String comment, AlterType alterType) {
        super(sql, alterType);
        this.tableName = tableName;
        this.column = column;
        this.comment = comment;
    }

    public AlterCommentStatement clone() {
        try {
            AlterCommentStatement cloned = (AlterCommentStatement)super.clone();
            cloned.column = this.column.clone();
            return cloned;
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isTableComment() {
        return this.column == null;
    }

    public String getTableName() {
        return this.tableName;
    }

    public LogicField getColumn() {
        return this.column;
    }

    public String getComment() {
        if (this.comment == null || this.comment.isEmpty()) {
            return null;
        }
        char[] chars = this.comment.toCharArray();
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < chars.length; ++i) {
            if (chars[i] == '\'') {
                buf.append("\\'");
                continue;
            }
            buf.append(chars[i]);
        }
        return buf.toString();
    }

    @Override
    public List<String> interpret(IDatabase database, Connection conn) throws SQLInterpretException {
        ISQLInterpretor interpretor = database.createSQLInterpretor(conn);
        return interpretor.alterComment(this);
    }
}

