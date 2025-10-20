/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.sql.command.StatementCommand
 *  com.jiuqi.bi.database.sql.parser.SQLCommandParser
 *  com.jiuqi.bi.database.statement.AlterColumnStatement
 *  com.jiuqi.bi.database.statement.AlterIndexStatement
 *  com.jiuqi.bi.database.statement.interpret.ISQLInterpretor
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.datasource.GcBizJdbcTemplate
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.dc.base.common.definition.service.ITableCheckService
 *  com.jiuqi.dc.base.common.jdbc.extractor.StringRowMapper
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.gcreport.definition.impl.basic.entity.ShardingBaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.init.EntityTableCollector
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.dc.mappingscheme.impl.service.executor;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.command.StatementCommand;
import com.jiuqi.bi.database.sql.parser.SQLCommandParser;
import com.jiuqi.bi.database.statement.AlterColumnStatement;
import com.jiuqi.bi.database.statement.AlterIndexStatement;
import com.jiuqi.bi.database.statement.interpret.ISQLInterpretor;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.datasource.GcBizJdbcTemplate;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.dc.base.common.definition.service.ITableCheckService;
import com.jiuqi.dc.base.common.jdbc.extractor.StringRowMapper;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.gcreport.definition.impl.basic.entity.ShardingBaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.init.EntityTableCollector;
import java.sql.Connection;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class DataMappingUpdateService {
    @Autowired
    private ITableCheckService tableCheckService;
    private static final Logger logger = LoggerFactory.getLogger("com.jiuqi.dc.mappingscheme.impl.service.tableCheckAndSchemeUpdate");

    public void execute(String dataSourceCode) throws Exception {
        logger.info("\u6570\u636e\u6620\u5c04\u5f00\u59cb\u5347\u7ea7");
        EntityTableCollector entityTableCollector = EntityTableCollector.getInstance();
        ShardingBaseEntity entity = (ShardingBaseEntity)entityTableCollector.getEntityByName("REF");
        List shardingList = entity.getShardingList();
        logger.info("\u9700\u8981\u5347\u7ea7\u7684\u6570\u636e\u6620\u5c04\u8868\u5171{}\u4e2a", (Object)shardingList.size());
        if (CollectionUtils.isEmpty((Collection)shardingList)) {
            logger.info("\u6ca1\u6709\u9700\u8981\u540c\u6b65\u7684\u6620\u5c04\u8868\uff0c\u8df3\u8fc7\u6267\u884c");
            return;
        }
        logger.info("\u540c\u6b65\u8868\u7ed3\u6784");
        HashSet tableList = CollectionUtils.newHashSet();
        for (String dimCode : shardingList) {
            tableList.add(entity.getTableNamePrefix() + "_" + dimCode);
        }
        this.tableCheckService.tableCheck((Set)tableList, true);
        DataSource dataSource = OuterDataSourceUtils.getDataSource();
        GcBizJdbcTemplate bizJdbcTemplate = OuterDataSourceUtils.getJdbcTemplate();
        IDbSqlHandler dbSqlHandler = bizJdbcTemplate.getIDbSqlHandler();
        for (String tableName : tableList) {
            logger.info("\u5f00\u59cb\u540c\u6b65\u8868{}", (Object)tableName);
            List columnList = bizJdbcTemplate.query(dbSqlHandler.getTableColumnSql(tableName), (RowMapper)new StringRowMapper());
            if (CollectionUtils.isEmpty((Collection)columnList)) {
                logger.info("\u6620\u5c04\u8868{}\u4e0d\u5b58\u5728\uff0c\u8df3\u8fc7\u6267\u884c", (Object)tableName);
                continue;
            }
            try {
                Connection connection = dataSource.getConnection();
                Throwable throwable = null;
                try {
                    IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
                    if (database == null) {
                        throw new BusinessRuntimeException("\u83b7\u53d6IDatabase\u5931\u8d25");
                    }
                    if (columnList.contains("ODS_ID")) {
                        try {
                            String baseSql = "ALTER TABLE " + tableName + " MODIFY ODS_ID NVARCHAR(60) NULL";
                            List sqlCommands = new SQLCommandParser().parse(database, baseSql);
                            StatementCommand statementCommand = (StatementCommand)sqlCommands.get(0);
                            ISQLInterpretor sqlInterpretor = database.createSQLInterpretor(connection);
                            List sqlList = sqlInterpretor.alterColumn((AlterColumnStatement)statementCommand.getStatement());
                            sqlList.forEach(arg_0 -> ((GcBizJdbcTemplate)bizJdbcTemplate).update(arg_0));
                        }
                        catch (Exception e) {
                            logger.error("{}\u8868\u66f4\u65b0ODS_ID\u4e3a\u975e\u5fc5\u586b\u5931\u8d25\uff1a{}\uff0c\u8df3\u8fc7\u6267\u884c", (Object)tableName, (Object)e.getMessage());
                        }
                    }
                    String indexStr = "DATASCHEMECODE,ODS_ID";
                    HashMap resultMap = CollectionUtils.newHashMap();
                    bizJdbcTemplate.query(dbSqlHandler.getTableIndexSql(tableName), (rs, rowNum) -> {
                        String title = rs.getString(1);
                        String indexColumn = rs.getString(2);
                        resultMap.compute(title, (k, existing) -> {
                            if (Objects.isNull(existing)) {
                                return indexColumn;
                            }
                            return existing + "," + indexColumn;
                        });
                        return null;
                    });
                    for (Map.Entry entry : resultMap.entrySet()) {
                        if (!indexStr.equals(entry.getValue())) continue;
                        String dropIndexSql = "DROP INDEX " + (String)entry.getKey() + " ON " + tableName;
                        List dropIndexSqlCommands = new SQLCommandParser().parse(database, dropIndexSql);
                        StatementCommand dropIndexStatementCommand = (StatementCommand)dropIndexSqlCommands.get(0);
                        ISQLInterpretor sqlInterpretor = database.createSQLInterpretor(connection);
                        List dropIndexSqlList = sqlInterpretor.alterIndex((AlterIndexStatement)dropIndexStatementCommand.getStatement());
                        dropIndexSqlList.forEach(arg_0 -> ((GcBizJdbcTemplate)bizJdbcTemplate).update(arg_0));
                    }
                    logger.info("\u540c\u6b65\u8868\u7ed3\u6784{}\u5b8c\u6210", (Object)tableName);
                    String updateSql = "UPDATE " + tableName + " SET HANDLESTATUS = 1";
                    logger.info("{}\u8868\u66f4\u65b0{}\u6761\u6570\u636e", (Object)tableName, (Object)bizJdbcTemplate.update(updateSql));
                    logger.info("\u6620\u5c04\u8868{}\u5904\u7406\u5b8c\u6bd5", (Object)tableName);
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                }
                finally {
                    if (connection == null) continue;
                    if (throwable != null) {
                        try {
                            connection.close();
                        }
                        catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                        continue;
                    }
                    connection.close();
                }
            }
            catch (Exception e) {
                logger.error("{}\u8868\u66f4\u65b0\u5931\u8d25\uff1a{}", (Object)tableName, (Object)e.getMessage());
            }
        }
    }
}

