/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.ddl.DDLException
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.temp.DefaultTempTableProvider
 *  com.jiuqi.bi.database.temp.ITempTableMeta
 *  com.jiuqi.bi.database.temp.ITempTableProvider
 *  com.jiuqi.bi.database.temp.TempTable
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.dynamic.temptable.domain.DynamicTempTableMeta
 *  com.jiuqi.nr.dynamic.temptable.exception.NoAvailableTempTableException
 *  com.jiuqi.nr.dynamic.temptable.service.IDynamicTempTableUseService
 */
package com.jiuqi.nr.common.temptable.dynamic;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.ddl.DDLException;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.temp.DefaultTempTableProvider;
import com.jiuqi.bi.database.temp.ITempTableMeta;
import com.jiuqi.bi.database.temp.ITempTableProvider;
import com.jiuqi.bi.database.temp.TempTable;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.common.params.DynamicTempTableParam;
import com.jiuqi.nr.common.temptable.dynamic.NRDynamicTempTableMeta;
import com.jiuqi.nr.dynamic.temptable.domain.DynamicTempTableMeta;
import com.jiuqi.nr.dynamic.temptable.exception.NoAvailableTempTableException;
import com.jiuqi.nr.dynamic.temptable.service.IDynamicTempTableUseService;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicTempTableProvider
extends DefaultTempTableProvider {
    private final IDynamicTempTableUseService dynamicTableUseService;
    private boolean retryUntilTimeout = false;
    private int retryInterval = -1;
    private int retryTimeout = -1;
    private final Logger logger = LoggerFactory.getLogger(DynamicTempTableProvider.class);

    public DynamicTempTableProvider(IDynamicTempTableUseService dynamicTableUseService) {
        this.dynamicTableUseService = dynamicTableUseService;
    }

    public DynamicTempTableProvider(IDynamicTempTableUseService dynamicTableUseService, boolean retryUntilTimeout, int retryInterval, int retryTimeout) {
        this.dynamicTableUseService = dynamicTableUseService;
        this.retryUntilTimeout = retryUntilTimeout;
        this.retryInterval = retryInterval;
        this.retryTimeout = retryTimeout;
    }

    public DynamicTempTableProvider(DynamicTempTableParam dynamicTempTableParam) {
        this.dynamicTableUseService = dynamicTempTableParam.getDynamicTempTableUseService();
        this.retryUntilTimeout = dynamicTempTableParam.isRetryUntilTimeout();
        this.retryInterval = dynamicTempTableParam.getRetryInterval();
        this.retryTimeout = dynamicTempTableParam.getRetryTimeout();
    }

    public TempTable getTempTableByType(Connection conn, String type) throws SQLException {
        return this.getTempTableByType(conn, null, type);
    }

    public TempTable getTempTableByType(String connName, String type) throws SQLException {
        return this.getTempTableByType(null, connName, type);
    }

    protected TempTable getTempTableByType(Connection conn, String connName, String type) throws SQLException {
        DynamicTempTableMeta dynamicTempTableMeta;
        ITempTableMeta meta;
        block5: {
            meta = (ITempTableMeta)this.metaMap.get(type);
            if (meta == null) {
                throw new SQLException("\u67e5\u627e\u4e34\u65f6\u8868\u5143\u6570\u636e\u4e0d\u5b58\u5728\uff1a" + type);
            }
            int columnCount = meta.getLogicFields().size();
            dynamicTempTableMeta = null;
            try {
                dynamicTempTableMeta = this.dynamicTableUseService.getTempTable(columnCount, true, NpContextHolder.getContext().getUserId());
            }
            catch (NoAvailableTempTableException e) {
                if (this.retryUntilTimeout && this.retryTimeout >= 0) {
                    this.threadSleep(this.retryInterval);
                    dynamicTempTableMeta = this.retryGetTempTable(columnCount);
                }
                if (dynamicTempTableMeta != null) break block5;
                this.logger.error("\u5f53\u524d\u4e34\u65f6\u8868\u6570\u91cf\u4e0d\u8db3\uff0c\u672a\u83b7\u53d6\u5230\u4e34\u65f6\u8868\uff1a{}", (Object)e.getMessage(), (Object)e);
                throw e;
            }
        }
        ArrayList<LogicField> columns = new ArrayList<LogicField>();
        for (String column : dynamicTempTableMeta.getColumns()) {
            LogicField logicField = new LogicField();
            logicField.setFieldName(column);
            columns.add(logicField);
        }
        NRDynamicTempTableMeta nrDynamicTempTableMeta = new NRDynamicTempTableMeta(meta, columns, dynamicTempTableMeta.getPrimaryKey());
        return new TempTable(conn, connName, dynamicTempTableMeta.getTableName(), (ITempTableMeta)nrDynamicTempTableMeta, (ITempTableProvider)this);
    }

    public void releaseTempTable(TempTable tempTable, String connName) throws SQLException {
        this.dynamicTableUseService.releaseTempTable(tempTable.getTableName());
    }

    public void releaseTempTable(TempTable tempTable, Connection conn) throws SQLException {
        this.dynamicTableUseService.releaseTempTable(tempTable.getTableName());
    }

    public TempTable getOneKeyTempTable(Connection conn) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public TempTable getKeyValueTempTable(Connection conn) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public TempTable getKeyOrderTempTable(Connection conn) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    protected TempTable createTableByMeta(Connection conn, String connName, String type, ITempTableMeta meta) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    protected void doCreateDBTable(Connection conn, ITempTableMeta meta, IDatabase database, String tableName) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    protected void executeSql(Connection conn, IDatabase database, String sql) throws DDLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    protected String createTableName(String type) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public TempTable getOneKeyTempTable(String connName) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public TempTable getKeyValueTempTable(String connName) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public TempTable getKeyOrderTempTable(String connName) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private DynamicTempTableMeta retryGetTempTable(int columnCount) throws SQLException {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < (long)this.retryTimeout) {
            DynamicTempTableMeta dynamicTempTableMeta;
            try {
                dynamicTempTableMeta = this.dynamicTableUseService.getTempTable(columnCount, true, NpContextHolder.getContext().getUserId());
            }
            catch (IllegalArgumentException e) {
                this.threadSleep(this.retryInterval);
                continue;
            }
            if (dynamicTempTableMeta == null) continue;
            return dynamicTempTableMeta;
        }
        return null;
    }

    private void threadSleep(long millis) {
        if (millis <= 0L) {
            return;
        }
        try {
            Thread.sleep(millis);
        }
        catch (InterruptedException e) {
            this.logger.error(e.getMessage(), e);
        }
    }
}

