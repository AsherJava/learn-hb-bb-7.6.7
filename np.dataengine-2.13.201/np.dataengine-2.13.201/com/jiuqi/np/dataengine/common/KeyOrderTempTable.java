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

public class KeyOrderTempTable
extends TempAssistantTable {
    public static final String ID_COLUMNNAME = "TEMP_KEY";
    public static final String ORDER_COLUMNNAME = "TEMP_ORDER";

    public KeyOrderTempTable(IConnectionProvider connectionProvider, List<?> filterValues, int dataType) {
        super(connectionProvider, filterValues, dataType);
    }

    @Override
    public void createTempTable(Connection connection) throws Exception {
        this.tempTable = TempTableActuator.getKeyOrderTempTable((Connection)connection);
        this.tableName = this.tempTable.getTableName();
    }

    @Override
    public void insertIntoTempTable(Connection connection) throws SQLException {
        ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
        int order = 0;
        if (this.filterValues != null) {
            for (Object filterValue : this.filterValues) {
                Object[] batchArray = new Object[]{filterValue, order};
                batchValues.add(batchArray);
                ++order;
            }
        }
        try {
            this.tempTable.insertRecords(batchValues);
        }
        catch (Exception e) {
            throw new SQLException(e.getMessage(), e);
        }
    }

    public String getOrderColumnSql() {
        return this.tableName + "." + ORDER_COLUMNNAME;
    }
}

