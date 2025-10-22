/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.temp.TempTable
 *  com.jiuqi.bi.database.temp.TempTableProviderFactory
 *  com.jiuqi.np.dataengine.IConnectionProvider
 */
package com.jiuqi.nr.bql.dataengine.query;

import com.jiuqi.bi.database.temp.TempTable;
import com.jiuqi.bi.database.temp.TempTableProviderFactory;
import com.jiuqi.np.dataengine.IConnectionProvider;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderTempAssistantTable {
    public static final String ID_COLUMNNAME = "TEMP_KEY";
    public static final String ORDER_COLUMNNAME = "TEMP_ORDER";
    private List<String> filterValues;
    private Map<String, Integer> orderFilterValues;
    private String tableName;
    private TempTable tempTable;
    private IConnectionProvider connectionProvider;

    public OrderTempAssistantTable(List<String> filterValues) {
        this.filterValues = filterValues;
    }

    public OrderTempAssistantTable(Map<String, Integer> orderFilterValues) {
        this.orderFilterValues = orderFilterValues;
    }

    public void dropTempTable(Connection connection) throws Exception {
        TempTableProviderFactory.getInstance().getTempTableProvider().releaseTempTable(this.tempTable, connection);
    }

    public void createTempTable(Connection connection) throws Exception {
        this.tempTable = TempTableProviderFactory.getInstance().getTempTableProvider().getKeyOrderTempTable(connection);
        this.tableName = this.tempTable.getTableName();
    }

    public void insertIntoTempTable(Connection connection) throws Exception {
        ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
        int order = 0;
        if (this.filterValues != null) {
            for (String filterValue : this.filterValues) {
                Object[] batchArray = new Object[]{filterValue, order};
                batchValues.add(batchArray);
                ++order;
            }
        } else if (this.orderFilterValues != null) {
            for (Map.Entry<String, Integer> entry : this.orderFilterValues.entrySet()) {
                Object[] batchArray = new Object[]{entry.getKey(), entry.getValue()};
                batchValues.add(batchArray);
                ++order;
            }
        }
        this.tempTable.insertRecords(batchValues);
    }

    public String getSelectSql() {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("select ").append(ID_COLUMNNAME).append(" from ").append(this.tableName);
        return selectSql.toString();
    }

    public String getExistsSelectSql(String relationCol) {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("(Select 1 From ").append(this.tableName).append(" where ").append(ID_COLUMNNAME).append("=").append(relationCol).append(")");
        return selectSql.toString();
    }

    public String getJoinSql(String relationCol) {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append(" join ").append(this.tableName).append(" ").append(this.tableName).append(" on ").append(relationCol).append("=").append(this.tableName).append(".").append(ID_COLUMNNAME);
        return selectSql.toString();
    }

    public int size() {
        if (this.filterValues != null) {
            return this.filterValues.size();
        }
        if (this.orderFilterValues != null) {
            return this.orderFilterValues.size();
        }
        return 0;
    }

    public String getOrderColumnSql() {
        return this.tableName + "." + ORDER_COLUMNNAME;
    }

    public String getTableName() {
        return this.tableName;
    }

    public Map<String, Integer> getOrderFilterValues() {
        return this.orderFilterValues;
    }

    public IConnectionProvider getConnectionProvider() {
        return this.connectionProvider;
    }

    public void setConnectionProvider(IConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }
}

