/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.interpret.DBSQLInfo
 */
package com.jiuqi.nr.data.engine.summary.parse;

import com.jiuqi.bi.syntax.interpret.DBSQLInfo;
import java.sql.Connection;

public class SumDBSQLInfo
extends DBSQLInfo {
    private boolean hasAlias;

    public SumDBSQLInfo(Connection conn, boolean isCondition, boolean hasAlias) {
        super(conn, isCondition);
        this.hasAlias = hasAlias;
    }

    public boolean hasAlias() {
        return this.hasAlias;
    }

    public void setHasAlias(boolean hasAlias) {
        this.hasAlias = hasAlias;
    }
}

