/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.statement;

import com.jiuqi.bi.database.statement.AlterType;
import com.jiuqi.bi.database.statement.SqlStatement;

public abstract class AlterStatement
extends SqlStatement {
    private AlterType alterType;

    public AlterStatement(String sql, AlterType alterType) {
        super(sql);
        this.setAlterType(alterType);
    }

    public void setAlterType(AlterType alterType) {
        this.alterType = alterType;
    }

    public AlterType getAlterType() {
        return this.alterType;
    }
}

