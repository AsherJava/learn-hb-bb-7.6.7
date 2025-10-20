/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.database.sql.command;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.DefaultDatabase;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.ddl.DDLException;
import com.jiuqi.bi.database.ddl.IDDLExecutor;
import com.jiuqi.bi.database.sql.command.SQLCommand;
import com.jiuqi.bi.database.sql.command.SQLCommandType;
import com.jiuqi.bi.database.sql.parser.SQLCommandParser;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.bi.database.sql.parser.SQLParserException;
import com.jiuqi.bi.database.statement.AlterColumnStatement;
import com.jiuqi.bi.database.statement.AlterCommentStatement;
import com.jiuqi.bi.database.statement.AlterIndexStatement;
import com.jiuqi.bi.database.statement.AlterPrimarykeyStatement;
import com.jiuqi.bi.database.statement.AlterTableStatement;
import com.jiuqi.bi.database.statement.CreateIndexStatement;
import com.jiuqi.bi.database.statement.CreateTableStatement;
import com.jiuqi.bi.database.statement.CreateViewStatement;
import com.jiuqi.bi.database.statement.SpecificStatement;
import com.jiuqi.bi.database.statement.SqlStatement;
import com.jiuqi.bi.util.StringUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class StatementCommand
extends SQLCommand {
    private SqlStatement statement;
    private String sql;

    public StatementCommand(SqlStatement statement, String sql) {
        this.statement = statement;
        this.sql = sql;
        try {
            this.type = this.parseType();
        }
        catch (SQLParserException e) {
            this.type = SQLCommandType.UNKNOWN;
        }
    }

    public StatementCommand(SqlStatement statement) {
        this(statement, null);
    }

    public SqlStatement getStatement() {
        return this.statement;
    }

    private SQLCommandType parseType() throws SQLParserException {
        if (this.statement instanceof CreateTableStatement) {
            return SQLCommandType.CREATE_TABLE;
        }
        if (this.statement instanceof CreateViewStatement) {
            return SQLCommandType.CREATE_VIEW;
        }
        if (this.statement instanceof CreateIndexStatement) {
            return SQLCommandType.CREATE_INDEX;
        }
        if (this.statement instanceof AlterTableStatement || this.statement instanceof AlterPrimarykeyStatement || this.statement instanceof AlterColumnStatement || this.statement instanceof AlterCommentStatement) {
            return SQLCommandType.ALTER_TABLE;
        }
        if (this.statement instanceof AlterIndexStatement) {
            return SQLCommandType.ALTER_INDEX;
        }
        if (this.statement instanceof SpecificStatement) {
            String sql = this.statement.getOrginSql();
            return SQLCommandParser.exploreSQLCommandType(sql);
        }
        return SQLCommandType.UNKNOWN;
    }

    @Override
    public boolean execute(Connection conn) throws SQLException {
        List<String> sqls;
        IDatabase db = DatabaseManager.getInstance().findDatabaseByConnection(conn);
        try {
            sqls = this.statement.interpret(db, conn);
        }
        catch (SQLInterpretException e) {
            throw new SQLException(e);
        }
        if (this.getType().isDDLType()) {
            try {
                IDDLExecutor executor = db.createDDLExcecutor(conn);
                for (String sql : sqls) {
                    executor.execute(sql);
                }
            }
            catch (DDLException e) {
                throw new SQLException(e);
            }
        }
        for (String sql : sqls) {
            PreparedStatement ps = conn.prepareStatement(sql);
            Throwable throwable = null;
            try {
                ps.execute();
            }
            catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            }
            finally {
                if (ps == null) continue;
                if (throwable != null) {
                    try {
                        ps.close();
                    }
                    catch (Throwable throwable3) {
                        throwable.addSuppressed(throwable3);
                    }
                    continue;
                }
                ps.close();
            }
        }
        return false;
    }

    @Override
    public String toString() {
        try {
            if (this.sql == null) {
                return this.statement.interpret(new DefaultDatabase(), null).get(0);
            }
            return this.sql;
        }
        catch (SQLInterpretException e) {
            return e.getMessage();
        }
    }

    @Override
    public String toString(String dbName) {
        IDatabase db = DatabaseManager.getInstance().findDatabaseByName(dbName);
        try {
            List<String> sqls = this.statement.interpret(db, null);
            return sqls.isEmpty() ? "" : StringUtils.join(sqls.iterator(), (String)";");
        }
        catch (SQLInterpretException e) {
            return e.getMessage();
        }
    }
}

