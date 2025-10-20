/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.statement.interpret;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.bi.database.statement.AlterColumnStatement;
import com.jiuqi.bi.database.statement.AlterCommentStatement;
import com.jiuqi.bi.database.statement.AlterIndexStatement;
import com.jiuqi.bi.database.statement.AlterPrimarykeyStatement;
import com.jiuqi.bi.database.statement.AlterTableStatement;
import com.jiuqi.bi.database.statement.AlterViewStatement;
import com.jiuqi.bi.database.statement.CreateIndexStatement;
import com.jiuqi.bi.database.statement.CreateTableStatement;
import com.jiuqi.bi.database.statement.CreateViewStatement;
import java.util.Date;
import java.util.List;

public interface ISQLInterpretor {
    public List<String> alterColumn(AlterColumnStatement var1) throws SQLInterpretException;

    public List<String> alterComment(AlterCommentStatement var1) throws SQLInterpretException;

    public List<String> alterIndex(AlterIndexStatement var1) throws SQLInterpretException;

    public List<String> alterPrimarykey(AlterPrimarykeyStatement var1) throws SQLInterpretException;

    public List<String> alterTable(AlterTableStatement var1) throws SQLInterpretException;

    public List<String> createIndex(CreateIndexStatement var1) throws SQLInterpretException;

    public List<String> createTable(CreateTableStatement var1) throws SQLInterpretException;

    public String getDataTypeSQL(LogicField var1) throws SQLInterpretException;

    public List<String> createView(CreateViewStatement var1) throws SQLInterpretException;

    public List<String> alterView(AlterViewStatement var1) throws SQLInterpretException;

    public String formatSQLDate(Date var1);

    public List<String> createBakTable(String var1, String var2) throws SQLInterpretException;

    public List<String> renameTable(String var1, String var2) throws SQLInterpretException;

    public String formatSQLTimestamp(String var1);

    public String analyzeTableEstimate(String var1) throws SQLInterpretException;

    public String analyzeTableFull(String var1) throws SQLInterpretException;
}

