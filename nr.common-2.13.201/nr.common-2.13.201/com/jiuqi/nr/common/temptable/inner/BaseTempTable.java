/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.temp.ITempTableProvider
 *  com.jiuqi.bi.database.temp.TempTable
 */
package com.jiuqi.nr.common.temptable.inner;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.temp.ITempTableProvider;
import com.jiuqi.bi.database.temp.TempTable;
import com.jiuqi.nr.common.temptable.ITempTable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseTempTable
implements ITempTable {
    protected final TempTable tempTable;
    protected final ITempTableProvider tempTableProvider;
    protected boolean closed = false;

    public BaseTempTable(ITempTableProvider tempTableProvider, TempTable tempTable) {
        this.tempTableProvider = tempTableProvider;
        this.tempTable = tempTable;
    }

    protected String createInsertSql() {
        StringBuilder insertSql = new StringBuilder();
        insertSql.append("insert into ").append(this.tempTable.getTableName()).append(" (");
        List logicFields = this.tempTable.getMeta().getLogicFields();
        String valueField = logicFields.stream().map(LogicField::getFieldName).collect(Collectors.joining(","));
        insertSql.append(valueField);
        insertSql.append(") values (");
        String valueSoft = logicFields.stream().map(r -> "?").collect(Collectors.joining(","));
        insertSql.append(valueSoft);
        insertSql.append(")");
        return insertSql.toString();
    }

    protected void batchUpdate(Connection conn, String sql, List<Object[]> batchValues) throws SQLException {
        try (PreparedStatement prep = conn.prepareStatement(sql);){
            int count = 0;
            for (Object[] batchValue : batchValues) {
                for (int i = 0; i < batchValue.length; ++i) {
                    prep.setObject(i + 1, batchValue[i]);
                }
                prep.addBatch();
                if (++count % 10000 != 0) continue;
                prep.executeBatch();
            }
            prep.executeBatch();
        }
        catch (Exception var13) {
            throw new SQLException("\u6279\u91cf\u63d2\u5165\u4e34\u65f6\u8868\u8bb0\u5f55\u51fa\u9519" + var13.getMessage(), var13);
        }
    }

    @Override
    public boolean isClosed() {
        return this.closed;
    }
}

