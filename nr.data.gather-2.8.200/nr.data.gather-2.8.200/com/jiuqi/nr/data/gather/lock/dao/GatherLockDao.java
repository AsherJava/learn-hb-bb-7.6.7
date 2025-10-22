/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  com.jiuqi.np.dataengine.common.SqlQueryHelper
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.data.gather.lock.dao;

import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.common.SqlQueryHelper;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class GatherLockDao {
    private static final Logger logger = LoggerFactory.getLogger(GatherLockDao.class);
    @Autowired
    private DataSource dataSource;
    @Autowired
    private DataModelService dataModelService;
    private static final Long OVERTIME_MINUTE = 5L;
    private static final String SELECT_SQL = "select * from %s where HEART_TIME < ?";
    private static final String DELETE_SQL = "delete from %s where %s";

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void deleteOverTimeLock(String tableName) {
        try (Connection connection = this.dataSource.getConnection();
             SqlQueryHelper sqlQueryHelper = DataEngineUtil.createSqlQueryHelper();){
            Object[] queryArgs = new Object[]{new Timestamp(System.currentTimeMillis() - OVERTIME_MINUTE * 60L * 1000L)};
            TableModelDefine tableModel = this.dataModelService.getTableModelDefineByName(tableName);
            if (tableModel == null) {
                return;
            }
            String[] split = tableModel.getKeys().split(",");
            HashSet<String> keys = new HashSet<String>(Arrays.asList(split));
            List columnModelDefines = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
            List primaryKeyColumns = columnModelDefines.stream().filter(a -> keys.contains(a.getID())).map(ColumnModelDefine::getName).collect(Collectors.toList());
            String select = String.format(SELECT_SQL, tableName);
            ResultSet resultSet = sqlQueryHelper.executeQuery(connection, select, queryArgs);
            ArrayList<Object[]> compoundKeys = new ArrayList<Object[]>();
            while (resultSet.next()) {
                Object[] keyValues = new Object[primaryKeyColumns.size()];
                for (int i = 0; i < primaryKeyColumns.size(); ++i) {
                    keyValues[i] = resultSet.getObject((String)primaryKeyColumns.get(i));
                }
                compoundKeys.add(keyValues);
            }
            if (CollectionUtils.isEmpty(compoundKeys)) {
                return;
            }
            StringBuilder whereClause = new StringBuilder();
            for (int j = 0; j < primaryKeyColumns.size(); ++j) {
                if (j > 0) {
                    whereClause.append(" AND ");
                }
                whereClause.append((String)primaryKeyColumns.get(j)).append(" = ?");
            }
            String deleteSql = String.format(DELETE_SQL, tableName, whereClause);
            DataEngineUtil.batchUpdate((Connection)connection, (String)deleteSql, compoundKeys);
            return;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}

