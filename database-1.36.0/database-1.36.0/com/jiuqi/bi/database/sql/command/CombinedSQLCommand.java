/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.database.sql.command;

import com.jiuqi.bi.database.sql.command.SQLCommand;
import com.jiuqi.bi.database.sql.command.SQLCommandType;
import com.jiuqi.bi.util.StringUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CombinedSQLCommand
extends SQLCommand {
    private String comment;
    private List<SQLCommand> commands = new ArrayList<SQLCommand>();
    private Map<SQLCommand, Boolean> expectExecutes = new HashMap<SQLCommand, Boolean>();

    public CombinedSQLCommand() {
        this(null);
    }

    public CombinedSQLCommand(String comment) {
        this.comment = comment;
        this.type = SQLCommandType.COMBINED;
    }

    public CombinedSQLCommand addCommand(SQLCommand cmd) {
        return this.addCommand(cmd, true);
    }

    public CombinedSQLCommand addCommand(SQLCommand cmd, boolean expectExecuteRs) {
        this.commands.add(cmd);
        this.expectExecutes.put(cmd, expectExecuteRs);
        return this;
    }

    public SQLCommand getCommand(int index) {
        if (index < 0 || index > this.commands.size()) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        return this.commands.get(index);
    }

    public int size() {
        return this.commands.size();
    }

    @Override
    public boolean execute(Connection conn) throws SQLException {
        for (SQLCommand cmd : this.commands) {
            boolean expect = this.expectExecutes.get(cmd);
            if (this.doExecute(cmd, conn) == expect) continue;
            return false;
        }
        return true;
    }

    protected boolean doExecute(SQLCommand command, Connection conn) throws SQLException {
        return command.execute(conn);
    }

    @Override
    public String toString() {
        if (this.comment != null && !this.comment.isEmpty()) {
            return this.comment;
        }
        StringBuilder b = new StringBuilder();
        for (SQLCommand cmd : this.commands) {
            b.append(cmd.toString()).append(";").append(StringUtils.LINE_SEPARATOR);
        }
        return b.toString();
    }

    @Override
    public String toString(String dbName) {
        StringBuilder b = new StringBuilder();
        for (SQLCommand cmd : this.commands) {
            b.append(cmd.toString(dbName)).append(";").append(StringUtils.LINE_SEPARATOR);
        }
        return b.toString();
    }

    @Override
    public void toString(List<String> dst) {
        for (SQLCommand cmd : this.commands) {
            cmd.toString(dst);
        }
    }
}

