/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.metadata.LogicIndex
 *  com.jiuqi.bi.database.metadata.LogicIndexField
 *  com.jiuqi.bi.database.sql.parser.SQLInterpretException
 *  com.jiuqi.bi.database.statement.CreateIndexStatement
 *  com.jiuqi.bi.database.statement.interpret.ISQLInterpretor
 *  com.jiuqi.bi.database.temp.DefaultTempTableProvider
 *  com.jiuqi.bi.database.temp.ITempTableMeta
 *  com.jiuqi.bi.database.temp.ITempTableProvider
 *  com.jiuqi.bi.database.temp.TempTable
 *  com.jiuqi.bi.database.temp.TempTableProviderFactory
 *  com.jiuqi.np.log.BeanUtils
 */
package com.jiuqi.nr.common.temptable;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.LogicIndex;
import com.jiuqi.bi.database.metadata.LogicIndexField;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.bi.database.statement.CreateIndexStatement;
import com.jiuqi.bi.database.statement.interpret.ISQLInterpretor;
import com.jiuqi.bi.database.temp.DefaultTempTableProvider;
import com.jiuqi.bi.database.temp.ITempTableMeta;
import com.jiuqi.bi.database.temp.ITempTableProvider;
import com.jiuqi.bi.database.temp.TempTable;
import com.jiuqi.bi.database.temp.TempTableProviderFactory;
import com.jiuqi.np.log.BeanUtils;
import com.jiuqi.nr.common.db.DatabaseInstance;
import com.jiuqi.nr.common.params.DynamicTempTableParam;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.common.temptable.dynamic.DynamicTempTableProvider;
import com.jiuqi.nr.common.temptable.dynamic.DynamicTempTableProxy;
import com.jiuqi.nr.common.temptable.inner.NrTempTableProvider;
import com.jiuqi.nr.common.temptable.inner.TempTableWrapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TempTableActuator {
    private static final Logger logger = LoggerFactory.getLogger(TempTableActuator.class);

    public static ITempTable getOneKeyTempTable(Connection connection) throws SQLException {
        TempTableProviderFactory instance = TempTableProviderFactory.getInstance();
        ITempTableProvider tempTableProvider = instance.getTempTableProvider();
        TempTable tempTable = tempTableProvider.getOneKeyTempTable(connection);
        TempTableActuator.truncateTable(connection, tempTable.getTableName());
        if (logger.isDebugEnabled()) {
            logger.info("\u83b7\u53d6 {} \u4e34\u65f6\u8868 {}", (Object)"OneKey", (Object)tempTable.getTableName());
        }
        return new TempTableWrapper(tempTableProvider, tempTable);
    }

    public static ITempTable getKeyValueTempTable(Connection connection) throws SQLException {
        TempTableProviderFactory instance = TempTableProviderFactory.getInstance();
        ITempTableProvider tempTableProvider = instance.getTempTableProvider();
        TempTable tempTable = tempTableProvider.getKeyValueTempTable(connection);
        TempTableActuator.truncateTable(connection, tempTable.getTableName());
        if (logger.isDebugEnabled()) {
            logger.info("\u83b7\u53d6 {} \u4e34\u65f6\u8868 {}", (Object)"KeyValue", (Object)tempTable.getTableName());
        }
        return new TempTableWrapper(tempTableProvider, tempTable);
    }

    public static ITempTable getKeyOrderTempTable(Connection connection) throws SQLException {
        TempTableProviderFactory instance = TempTableProviderFactory.getInstance();
        ITempTableProvider tempTableProvider = instance.getTempTableProvider();
        TempTable tempTable = tempTableProvider.getKeyOrderTempTable(connection);
        TempTableActuator.truncateTable(connection, tempTable.getTableName());
        if (logger.isDebugEnabled()) {
            logger.info("\u83b7\u53d6 {} \u4e34\u65f6\u8868 {}", (Object)"KeyOrder", (Object)tempTable.getTableName());
        }
        return new TempTableWrapper(tempTableProvider, tempTable);
    }

    public static ITempTable getTempTableByType(Connection connection, String type) throws SQLException {
        TempTableProviderFactory instance = TempTableProviderFactory.getInstance();
        ITempTableProvider tempTableProvider = instance.getTempTableProvider();
        TempTable tempTable = tempTableProvider.getTempTableByType(connection, type);
        TempTableActuator.truncateTable(connection, tempTable.getTableName());
        if (logger.isDebugEnabled()) {
            logger.info("\u83b7\u53d6 {} \u4e34\u65f6\u8868 {}", (Object)type, (Object)tempTable.getTableName());
        }
        return new TempTableWrapper(tempTableProvider, tempTable);
    }

    public static ITempTable getTempTableByMeta(Connection connection, ITempTableMeta meta) throws SQLException {
        DynamicTempTableParam dynamicTempTableParam = (DynamicTempTableParam)BeanUtils.getBean(DynamicTempTableParam.class);
        String databaseLimitMode = dynamicTempTableParam.getDatabaseLimitMode();
        DefaultTempTableProvider provider = !"true".equals(databaseLimitMode) ? new NrTempTableProvider() : new DynamicTempTableProvider(dynamicTempTableParam);
        provider.registerTempTableMeta(meta);
        TempTable tempTable = provider.getTempTableByType(connection, meta.getType());
        if (logger.isDebugEnabled()) {
            logger.info("\u83b7\u53d6 {} \u52a8\u6001\u4e34\u65f6\u8868 {}", (Object)meta.getType(), (Object)tempTable.getTableName());
        }
        if (!"true".equals(databaseLimitMode)) {
            return new TempTableWrapper((ITempTableProvider)provider, tempTable);
        }
        return new DynamicTempTableProxy((ITempTableProvider)provider, tempTable, connection);
    }

    public static void truncateTable(Connection connection, String tableName) throws SQLException {
    }

    public static void createIndex(Connection connection, List<LogicIndex> indexList) throws SQLException {
        try {
            IDatabase iDatabase = DatabaseInstance.getDatabase();
            ISQLInterpretor sqlInterpret = iDatabase.createSQLInterpretor(connection);
            ArrayList sqlList = new ArrayList();
            for (LogicIndex logicIndex : indexList) {
                CreateIndexStatement indexStatement = new CreateIndexStatement("", logicIndex.getIndexName());
                indexStatement.setTableName(logicIndex.getTableName());
                indexStatement.setUnique(logicIndex.isUnique());
                for (LogicIndexField logicIndexField : logicIndex.getIndexFields()) {
                    indexStatement.getLogicIndex().addIndexField(logicIndexField);
                }
                List indexSqlList = sqlInterpret.createIndex(indexStatement);
                sqlList.addAll(indexSqlList);
            }
            for (String sql : sqlList) {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                Object object = null;
                try {
                    preparedStatement.execute();
                }
                catch (Throwable throwable) {
                    object = throwable;
                    throw throwable;
                }
                finally {
                    if (preparedStatement == null) continue;
                    if (object != null) {
                        try {
                            preparedStatement.close();
                        }
                        catch (Throwable throwable) {
                            ((Throwable)object).addSuppressed(throwable);
                        }
                        continue;
                    }
                    preparedStatement.close();
                }
            }
        }
        catch (SQLInterpretException e) {
            throw new SQLException(e);
        }
    }
}

