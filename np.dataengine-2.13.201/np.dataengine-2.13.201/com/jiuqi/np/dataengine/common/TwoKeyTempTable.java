/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.temptable.TempTableActuator
 */
package com.jiuqi.np.dataengine.common;

import com.jiuqi.np.dataengine.IConnectionProvider;
import com.jiuqi.np.dataengine.common.TempAssistantTable;
import com.jiuqi.nr.common.temptable.TempTableActuator;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TwoKeyTempTable
extends TempAssistantTable {
    private Map<String, List<String>> keyValues;

    public TwoKeyTempTable(IConnectionProvider connectionProvider, Map<String, List<String>> keyValues) {
        super(connectionProvider, null, 6);
        this.keyValues = keyValues;
    }

    public TwoKeyTempTable(Map<String, List<String>> keyValues) {
        super(null, 6);
        this.keyValues = keyValues;
    }

    @Override
    public void createTempTable(Connection connection) throws Exception {
        this.tempTable = TempTableActuator.getTempTableByType((Connection)connection, (String)"twoKey");
        this.tableName = this.tempTable.getTableName();
    }

    @Override
    public void insertIntoTempTable(Connection connection) throws SQLException {
        ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
        for (Map.Entry<String, List<String>> entry : this.keyValues.entrySet()) {
            String keyValue1 = entry.getKey();
            for (String keyValue2 : entry.getValue()) {
                Object[] args = new Object[]{keyValue1, keyValue2};
                batchValues.add(args);
            }
        }
        this.tempTable.insertRecords(batchValues);
    }

    public String getExistsSelectSql(String keyCol1, String keyCol2) {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("(select 1 From ").append(this.tableName);
        selectSql.append(" where ").append(this.tableName).append(".").append("TEMP_KEY1").append("=").append(keyCol1).append(")");
        selectSql.append(" and ").append(this.tableName).append(".").append("TEMP_KEY2").append("=").append(keyCol2).append(")");
        return selectSql.toString();
    }

    public String getJoinSql(String keyCol1, String keyCol2) {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append(" join ").append(this.tableName).append(" ").append(this.tableName);
        selectSql.append(" on ");
        selectSql.append(this.tableName).append(".").append("TEMP_KEY1").append("=").append(keyCol1);
        selectSql.append(" and ");
        selectSql.append(this.tableName).append(".").append("TEMP_KEY2").append("=").append(keyCol2);
        return selectSql.toString();
    }
}

