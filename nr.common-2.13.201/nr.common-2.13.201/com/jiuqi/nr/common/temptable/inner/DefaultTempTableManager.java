/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicIndex
 *  com.jiuqi.bi.database.temp.DefaultTempTableProvider
 *  com.jiuqi.bi.database.temp.ITempTableMeta
 *  com.jiuqi.bi.database.temp.ITempTableProvider
 *  com.jiuqi.bi.database.temp.TempTable
 *  com.jiuqi.bi.database.temp.TempTableProviderFactory
 *  com.jiuqi.nr.dynamic.temptable.service.IDynamicTempTableUseService
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.common.temptable.inner;

import com.jiuqi.bi.database.metadata.LogicIndex;
import com.jiuqi.bi.database.temp.DefaultTempTableProvider;
import com.jiuqi.bi.database.temp.ITempTableMeta;
import com.jiuqi.bi.database.temp.ITempTableProvider;
import com.jiuqi.bi.database.temp.TempTable;
import com.jiuqi.bi.database.temp.TempTableProviderFactory;
import com.jiuqi.nr.common.temptable.BaseTempTableDefine;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.common.temptable.ITempTableManager;
import com.jiuqi.nr.common.temptable.IndexMeta;
import com.jiuqi.nr.common.temptable.TempTableActuator;
import com.jiuqi.nr.common.temptable.dynamic.DynamicTempTableProvider;
import com.jiuqi.nr.common.temptable.dynamic.DynamicTempTableProxy;
import com.jiuqi.nr.common.temptable.inner.NrTempTableProvider;
import com.jiuqi.nr.common.temptable.inner.TempTableProxy;
import com.jiuqi.nr.dynamic.temptable.service.IDynamicTempTableUseService;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class DefaultTempTableManager
implements ITempTableManager {
    @Value(value="${jiuqi.nvwa.databaseLimitMode:false}")
    private String databaseLimitMode;
    @Value(value="${jiuqi.nr.dynamic-temp-table.retry-until-timeout: false}")
    private boolean retryUntilTimeout;
    @Value(value="${jiuqi.nr.dynamic-temp-table.retry-interval: 2000}")
    private int retryInterval;
    @Value(value="${jiuqi.nr.dynamic-temp-table.retry-timeout: 300000}")
    private int retryTimeout;
    @Autowired
    private IDynamicTempTableUseService dynamicTempTableUseService;
    @Autowired
    private DataSource dataSource;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ITempTable getOneKeyTempTable() throws SQLException {
        TempTableProviderFactory instance = TempTableProviderFactory.getInstance();
        Connection connection = DataSourceUtils.getConnection((DataSource)this.dataSource);
        try {
            ITempTableProvider tempTableProvider = instance.getTempTableProvider();
            TempTable tempTable = tempTableProvider.getOneKeyTempTable(connection);
            DefaultTempTableManager.truncateTable(connection, tempTable.getTableName());
            if (this.LOGGER.isDebugEnabled()) {
                this.LOGGER.debug("\u83b7\u53d6 {} \u4e34\u65f6\u8868 {}", (Object)"OneKey", (Object)tempTable.getTableName());
            }
            TempTableProxy tempTableProxy = new TempTableProxy(tempTableProvider, tempTable, this.dataSource);
            return tempTableProxy;
        }
        finally {
            DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.dataSource);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ITempTable getKeyValueTempTable() throws SQLException {
        TempTableProviderFactory instance = TempTableProviderFactory.getInstance();
        Connection connection = DataSourceUtils.getConnection((DataSource)this.dataSource);
        try {
            ITempTableProvider tempTableProvider = instance.getTempTableProvider();
            TempTable tempTable = tempTableProvider.getKeyValueTempTable(connection);
            DefaultTempTableManager.truncateTable(connection, tempTable.getTableName());
            if (this.LOGGER.isDebugEnabled()) {
                this.LOGGER.debug("\u83b7\u53d6 {} \u4e34\u65f6\u8868 {}", (Object)"KeyValue", (Object)tempTable.getTableName());
            }
            TempTableProxy tempTableProxy = new TempTableProxy(tempTableProvider, tempTable, this.dataSource);
            return tempTableProxy;
        }
        finally {
            DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.dataSource);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ITempTable getKeyOrderTempTable() throws SQLException {
        TempTableProviderFactory instance = TempTableProviderFactory.getInstance();
        Connection connection = DataSourceUtils.getConnection((DataSource)this.dataSource);
        try {
            ITempTableProvider tempTableProvider = instance.getTempTableProvider();
            TempTable tempTable = tempTableProvider.getKeyOrderTempTable(connection);
            DefaultTempTableManager.truncateTable(connection, tempTable.getTableName());
            if (this.LOGGER.isDebugEnabled()) {
                this.LOGGER.debug("\u83b7\u53d6 {} \u4e34\u65f6\u8868 {}", (Object)"KeyOrder", (Object)tempTable.getTableName());
            }
            TempTableProxy tempTableProxy = new TempTableProxy(tempTableProvider, tempTable, this.dataSource);
            return tempTableProxy;
        }
        finally {
            DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.dataSource);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ITempTable getTempTableByType(String type) throws SQLException {
        TempTableProviderFactory instance = TempTableProviderFactory.getInstance();
        Connection connection = DataSourceUtils.getConnection((DataSource)this.dataSource);
        try {
            ITempTableProvider tempTableProvider = instance.getTempTableProvider();
            TempTable tempTable = instance.getTempTableProvider().getTempTableByType(connection, type);
            if (this.LOGGER.isDebugEnabled()) {
                this.LOGGER.debug("\u83b7\u53d6 {} \u4e34\u65f6\u8868 {}", (Object)type, (Object)tempTable.getTableName());
            }
            DefaultTempTableManager.truncateTable(connection, tempTable.getTableName());
            TempTableProxy tempTableProxy = new TempTableProxy(tempTableProvider, tempTable, this.dataSource);
            return tempTableProxy;
        }
        finally {
            DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.dataSource);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ITempTable getTempTableByMeta(ITempTableMeta meta) throws SQLException {
        DefaultTempTableProvider provider = !"true".equals(this.databaseLimitMode) ? new NrTempTableProvider() : new DynamicTempTableProvider(this.dynamicTempTableUseService, this.retryUntilTimeout, this.retryInterval, this.retryTimeout);
        provider.registerTempTableMeta(meta);
        Connection connection = DataSourceUtils.getConnection((DataSource)this.dataSource);
        try {
            TempTable tempTable = provider.getTempTableByType(connection, meta.getType());
            if (this.LOGGER.isDebugEnabled()) {
                this.LOGGER.info("\u83b7\u53d6 {} \u52a8\u6001\u4e34\u65f6\u8868 {}", (Object)meta.getType(), (Object)tempTable.getTableName());
            }
            if (!"true".equals(this.databaseLimitMode)) {
                TempTableProxy tempTableProxy = new TempTableProxy((ITempTableProvider)provider, tempTable, this.dataSource);
                return tempTableProxy;
            }
            DynamicTempTableProxy dynamicTempTableProxy = new DynamicTempTableProxy((ITempTableProvider)provider, tempTable, this.dataSource);
            return dynamicTempTableProxy;
        }
        finally {
            DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.dataSource);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ITempTable getTempTableByMeta(BaseTempTableDefine meta) throws SQLException {
        DefaultTempTableProvider provider = !"true".equals(this.databaseLimitMode) ? new NrTempTableProvider() : new DynamicTempTableProvider(this.dynamicTempTableUseService, this.retryUntilTimeout, this.retryInterval, this.retryTimeout);
        provider.registerTempTableMeta((ITempTableMeta)meta);
        Connection connection = DataSourceUtils.getConnection((DataSource)this.dataSource);
        try {
            TempTable tempTable = provider.getTempTableByType(connection, meta.getType());
            if (this.LOGGER.isDebugEnabled()) {
                this.LOGGER.info("\u83b7\u53d6 {} \u52a8\u6001\u4e34\u65f6\u8868 {}", (Object)meta.getType(), (Object)tempTable.getTableName());
            }
            if (!"true".equals(this.databaseLimitMode)) {
                TempTableProxy tableProxy = new TempTableProxy((ITempTableProvider)provider, tempTable, this.dataSource);
                this.createIndex(connection, meta, tempTable.getTableName());
                TempTableProxy tempTableProxy = tableProxy;
                return tempTableProxy;
            }
            DynamicTempTableProxy dynamicTempTableProxy = new DynamicTempTableProxy((ITempTableProvider)provider, tempTable, this.dataSource);
            return dynamicTempTableProxy;
        }
        finally {
            DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.dataSource);
        }
    }

    @Override
    public ITempTable getOneKeyTempTable(Connection connection) throws SQLException {
        return TempTableActuator.getOneKeyTempTable(connection);
    }

    @Override
    public ITempTable getKeyValueTempTable(Connection connection) throws SQLException {
        return TempTableActuator.getKeyValueTempTable(connection);
    }

    @Override
    public ITempTable getKeyOrderTempTable(Connection connection) throws SQLException {
        return TempTableActuator.getKeyOrderTempTable(connection);
    }

    @Override
    public ITempTable getTempTableByType(Connection connection, String type) throws SQLException {
        return TempTableActuator.getTempTableByType(connection, type);
    }

    @Override
    public ITempTable getTempTableByMeta(Connection connection, ITempTableMeta meta) throws SQLException {
        return TempTableActuator.getTempTableByMeta(connection, meta);
    }

    @Override
    public ITempTable getTempTableByMeta(Connection connection, BaseTempTableDefine meta) throws SQLException {
        ITempTable tableByMeta = TempTableActuator.getTempTableByMeta(connection, meta);
        this.createIndex(connection, meta, tableByMeta.getTableName());
        return tableByMeta;
    }

    private void createIndex(Connection connection, BaseTempTableDefine meta, String tableName) throws SQLException {
        List<IndexMeta> indexes = meta.getIndexes();
        if (!CollectionUtils.isEmpty(indexes)) {
            ArrayList<LogicIndex> indexList = new ArrayList<LogicIndex>();
            int idx = 0;
            for (IndexMeta index : indexes) {
                LogicIndex logicIndex = index.toLogicIndex();
                logicIndex.setTableName(tableName);
                logicIndex.setIndexName("IDX" + tableName + idx++);
                indexList.add(logicIndex);
            }
            TempTableActuator.createIndex(connection, indexList);
        }
    }

    public static void truncateTable(Connection connection, String tableName) throws SQLException {
    }
}

