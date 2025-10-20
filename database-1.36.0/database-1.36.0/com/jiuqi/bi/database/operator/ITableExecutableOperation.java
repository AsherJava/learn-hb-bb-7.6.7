/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.operator;

import com.jiuqi.bi.database.operator.ITableOperation;
import java.sql.Connection;
import java.sql.SQLException;

public interface ITableExecutableOperation
extends ITableOperation {
    public void execute(Connection var1) throws SQLException;
}

