/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.temp.ITempTableMeta
 *  com.jiuqi.bi.database.temp.TempTableProviderFactory
 *  com.jiuqi.nr.common.temptable.ITempTable
 *  com.jiuqi.nr.common.temptable.TempTableActuator
 */
package com.jiuqi.np.dataengine.common;

import com.jiuqi.bi.database.temp.ITempTableMeta;
import com.jiuqi.bi.database.temp.TempTableProviderFactory;
import com.jiuqi.np.dataengine.IConnectionProvider;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.config.TempTableMeta;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.common.temptable.TempTableActuator;
import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TempAssistantTable
implements Closeable {
    private static final Logger LOGGER = LoggerFactory.getLogger(TempAssistantTable.class);
    private static final String COLUMN_NAME = "TEMP_KEY";
    protected List<?> filterValues;
    protected int dataType;
    protected String tableName;
    protected ITempTable tempTable;
    protected IConnectionProvider connectionProvider;

    public TempAssistantTable(IConnectionProvider connectionProvider, List<?> filterValues, int dataType) {
        this.connectionProvider = connectionProvider;
        this.filterValues = filterValues;
        this.dataType = dataType;
    }

    public static boolean supoort(int dataType) {
        return dataType == 10 || dataType == 6 || dataType == 4 || dataType == 3 || dataType == 1 || dataType == 33;
    }

    public TempAssistantTable(List<?> filterValues, int dataType) {
        this.filterValues = filterValues;
        this.dataType = dataType;
    }

    @Deprecated
    public void dropTempTable(Connection connection) throws SQLException {
        try {
            this.tempTable.close();
        }
        catch (Exception e) {
            throw new SQLException(e.getMessage(), e);
        }
    }

    public void createTempTable() throws SQLException {
        if (this.connectionProvider != null) {
            Connection connection = this.connectionProvider.getConnection();
            try {
                this.createTempTable(connection);
            }
            catch (Exception e) {
                throw new SQLException(e);
            }
            finally {
                this.connectionProvider.closeConnection(connection);
            }
            return;
        }
        try (Connection connection = TempTableProviderFactory.getInstance().getConnectionProvider().openDefault();){
            this.createTempTable(connection);
        }
        catch (Exception e) {
            throw new SQLException(e);
        }
    }

    public void createTempTable(Connection connection) throws Exception {
        if (this.dataType == 6) {
            this.tempTable = TempTableActuator.getOneKeyTempTable((Connection)connection);
            this.tableName = this.tempTable.getTableName();
            return;
        }
        TempTableMeta tempTableMeta = new TempTableMeta(this.dataType);
        this.tempTable = TempTableActuator.getTempTableByMeta((Connection)connection, (ITempTableMeta)tempTableMeta);
        this.tableName = this.tempTable.getTableName();
    }

    public void insertIntoTempTable() throws SQLException {
        if (this.connectionProvider != null) {
            Connection connection = this.connectionProvider.getConnection();
            try {
                this.insertIntoTempTable(connection);
            }
            catch (Exception e) {
                throw new SQLException(e);
            }
            finally {
                this.connectionProvider.closeConnection(connection);
            }
            return;
        }
        this.insertIntoTempTable(null);
    }

    public void insertIntoTempTable(Connection connection) throws SQLException {
        ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
        HashSet<Object> hashSet = new HashSet<Object>();
        StringBuilder repeatValue = new StringBuilder();
        for (Object filterValue : this.filterValues) {
            Object[] batchArray = new Object[1];
            Object resultValue = DataEngineConsts.formatData(this.dataType, filterValue);
            if (hashSet.contains(resultValue)) {
                repeatValue.append(resultValue).append(";");
                continue;
            }
            hashSet.add(resultValue);
            batchArray[0] = resultValue;
            batchValues.add(batchArray);
        }
        if (repeatValue.length() > 0) {
            LOGGER.warn("\u4e34\u65f6\u8868\u5b58\u5728\u91cd\u590d\u6570\u636e\uff0c\u91cd\u590d\u6570\u636e\u4e3a\uff1a".concat(repeatValue.toString()));
        }
        if (connection != null) {
            this.tempTable.insertRecords(connection, batchValues);
        } else {
            this.tempTable.insertRecords(batchValues);
        }
    }

    public String getSelectSql() {
        return "select TEMP_KEY from " + this.tableName;
    }

    public String getExistsSelectSql(String relationCol) {
        return "(select 1 From " + this.tableName + " where " + this.tableName + "." + COLUMN_NAME + "=" + relationCol + ")";
    }

    public String getJoinSql(String relationCol) {
        return " join " + this.tableName + " " + this.tableName + " on " + relationCol + "=" + this.tableName + "." + COLUMN_NAME;
    }

    public String getTableName() {
        return this.tableName;
    }

    @Override
    public void close() throws IOException {
        if (this.connectionProvider != null) {
            Connection connection = this.connectionProvider.getConnection();
            try {
                this.tempTable.close(connection);
            }
            catch (SQLException e) {
                throw new IOException(e);
            }
            finally {
                this.connectionProvider.closeConnection(connection);
            }
            return;
        }
        this.tempTable.close();
    }
}

