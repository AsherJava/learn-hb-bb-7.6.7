/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.operator;

import com.jiuqi.bi.database.operator.ITableExecutableOperation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SQLTableOperation
implements ITableExecutableOperation {
    private String operationName;
    private List<String> sqlList;

    public SQLTableOperation(String name, List<String> sqlList) {
        this.operationName = name;
        this.sqlList = sqlList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void execute(Connection conn) throws SQLException {
        for (String sql : this.sqlList) {
            PreparedStatement st = null;
            try {
                st = conn.prepareStatement(sql);
                st.execute();
            }
            finally {
                if (st == null) continue;
                st.close();
            }
        }
    }

    @Override
    public String getOperationName() {
        return this.operationName;
    }

    @Override
    public String getOperationDescription() {
        StringBuilder builder = new StringBuilder();
        for (String sql : this.sqlList) {
            builder.append(sql).append("\n");
        }
        return builder.toString();
    }
}

