/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.temp.ITempTableMeta
 *  com.jiuqi.bi.database.temp.ITempTableProvider
 *  com.jiuqi.bi.database.temp.TempTable
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.common.temptable.dynamic;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.temp.ITempTableMeta;
import com.jiuqi.bi.database.temp.ITempTableProvider;
import com.jiuqi.bi.database.temp.TempTable;
import com.jiuqi.nr.common.temptable.dynamic.NRDynamicTempTableMeta;
import com.jiuqi.nr.common.temptable.inner.BaseTempTable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class DynamicTempTableProxy
extends BaseTempTable {
    private final Logger logger = LoggerFactory.getLogger(DynamicTempTableProxy.class);
    private DataSource dataSource;
    private Connection connection;
    private final Map<String, String> columnNameMap;
    private final int extraColumnCount;
    private int primaryKeyNumber = 0;
    private final ITempTableMeta meta;

    public DynamicTempTableProxy(ITempTableProvider tempTableProvider, TempTable tempTable, DataSource dataSource) {
        this(tempTableProvider, tempTable);
        this.dataSource = dataSource;
    }

    public DynamicTempTableProxy(ITempTableProvider tempTableProvider, TempTable tempTable, Connection connection) {
        this(tempTableProvider, tempTable);
        this.connection = connection;
    }

    public DynamicTempTableProxy(ITempTableProvider tempTableProvider, TempTable tempTable) {
        super(tempTableProvider, tempTable);
        ITempTableMeta nrDunamicMeta = tempTable.getMeta();
        if (nrDunamicMeta instanceof NRDynamicTempTableMeta) {
            this.meta = ((NRDynamicTempTableMeta)nrDunamicMeta).getMeta();
            List tempTableColumns = tempTable.getMeta().getLogicFields();
            List externalColumns = this.meta.getLogicFields();
            this.extraColumnCount = tempTableColumns.size() - externalColumns.size();
            this.columnNameMap = new HashMap<String, String>();
            for (int i = 0; i < externalColumns.size(); ++i) {
                this.columnNameMap.put(((LogicField)externalColumns.get(i)).getFieldName(), ((LogicField)tempTableColumns.get(i)).getFieldName());
            }
        } else {
            throw new UnsupportedOperationException("\u4e0d\u652f\u6301\u7684\u7c7b\u578b\uff1a" + nrDunamicMeta);
        }
    }

    @Override
    public void insertRecords(Connection connection, List<Object[]> records) throws SQLException {
        this.dealInsertValues(records);
        this.batchUpdate(connection, this.createInsertSql(), records);
    }

    @Override
    public void insertRecords(List<Object[]> records) throws SQLException {
        this.dealInsertValues(records);
        if (this.dataSource != null) {
            this.connection = DataSourceUtils.getConnection((DataSource)this.dataSource);
        }
        try {
            this.batchUpdate(this.connection, this.createInsertSql(), records);
        }
        finally {
            if (this.dataSource != null) {
                DataSourceUtils.releaseConnection((Connection)this.connection, (DataSource)this.dataSource);
            }
        }
    }

    @Override
    public void close(Connection connection) throws SQLException {
        try {
            this.tempTableProvider.releaseTempTable(this.tempTable, "");
        }
        catch (Exception e) {
            this.logger.error("\u91ca\u653e\u4e34\u65f6\u8868\u3010{}\u3011\u65f6\u51fa\u9519\uff1a{}", this.tempTable.getTableName(), e.getMessage(), e);
            throw new SQLException("\u91ca\u653e\u4e34\u65f6\u8868\u3010" + this.tempTable.getTableName() + "\u3011\u65f6\u51fa\u9519", e);
        }
        finally {
            this.closed = true;
        }
    }

    @Override
    public String getTableName() {
        return this.tempTable.getTableName();
    }

    @Override
    public ITempTableMeta getMeta() {
        return this.meta;
    }

    @Override
    public String getRealColName(String colName) {
        return this.columnNameMap.get(colName);
    }

    @Override
    protected String createInsertSql() {
        StringBuilder insertSql = new StringBuilder();
        insertSql.append("insert into ").append(this.tempTable.getTableName()).append(" (");
        insertSql.append("ID").append(",");
        List logicFields = this.tempTable.getMeta().getLogicFields();
        String valueField = logicFields.stream().map(LogicField::getFieldName).collect(Collectors.joining(","));
        insertSql.append(valueField);
        insertSql.append(") values (?,");
        String valueSoft = logicFields.stream().map(r -> "?").collect(Collectors.joining(","));
        insertSql.append(valueSoft);
        insertSql.append(")");
        return insertSql.toString();
    }

    @Override
    public void close() throws IOException {
        try {
            this.tempTableProvider.releaseTempTable(this.tempTable, "");
        }
        catch (Exception e) {
            this.logger.error("\u91ca\u653e\u4e34\u65f6\u8868\u3010{}\u3011\u65f6\u51fa\u9519\uff1a{}", this.tempTable.getTableName(), e.getMessage(), e);
            throw new IOException("\u91ca\u653e\u4e34\u65f6\u8868\u3010" + this.tempTable.getTableName() + "\u3011\u65f6\u51fa\u9519", e);
        }
        finally {
            this.closed = true;
        }
    }

    @Override
    public void deleteAll() throws SQLException {
        try {
            this.tempTableProvider.releaseTempTable(this.tempTable, "");
        }
        catch (Exception e) {
            this.logger.error("\u91ca\u653e\u4e34\u65f6\u8868\u3010{}\u3011\u65f6\u51fa\u9519\uff1a{}", this.tempTable.getTableName(), e.getMessage(), e);
            throw new SQLException("\u91ca\u653e\u4e34\u65f6\u8868\u3010" + this.tempTable.getTableName() + "\u3011\u65f6\u51fa\u9519", e);
        }
    }

    private void dealInsertValues(List<Object[]> records) {
        ListIterator<Object[]> iterator = records.listIterator();
        while (iterator.hasNext()) {
            Object[] original = iterator.next();
            Object[] newArray = new Object[original.length + 1 + this.extraColumnCount];
            newArray[0] = this.primaryKeyNumber++;
            System.arraycopy(original, 0, newArray, 1, original.length);
            if (this.extraColumnCount > 0) {
                for (int i = original.length + 1; i < newArray.length; ++i) {
                    newArray[i] = null;
                }
            }
            iterator.set(newArray);
        }
    }
}

