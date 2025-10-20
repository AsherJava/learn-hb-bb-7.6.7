/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.DefinitionAutoCollectionService
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.gcreport.archive.historydataupdate;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.DefinitionAutoCollectionService;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

public class HistoryArchiveDataUpdate
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(HistoryArchiveDataUpdate.class);
    private static final String[] TABLES = new String[]{"GC_ARCHIVE_LOG", "GC_ARCHIVE_INFO", "GC_ARCHIVECONFIG"};
    private static final String SELECT_SQL_TEMPLATE = "SELECT TASKID FROM %s WHERE ORGTYPE IS NULL OR ORGTYPE = '' GROUP BY TASKID ";
    private static final String UPDATE_SQL_TEMPLATE = "UPDATE %s SET ORGTYPE = ? WHERE TASKID = ? AND  (ORGTYPE IS NULL OR ORGTYPE = '')";

    public void execute(DataSource dataSource) throws Exception {
        JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringContextUtils.getBean(JdbcTemplate.class);
        DefinitionAutoCollectionService definitionAutoCollectionService = (DefinitionAutoCollectionService)SpringContextUtils.getBean(DefinitionAutoCollectionService.class);
        try {
            logger.info("\u5f00\u59cb\u540c\u6b65\u8868\u7ed3\u6784...");
            for (String tableName : TABLES) {
                definitionAutoCollectionService.initTableDefineByTableName(tableName);
                logger.info("\u5df2\u540c\u6b65\u8868\u7ed3\u6784: {}", (Object)tableName);
            }
            for (String tableName : TABLES) {
                List<String> taskIds = this.fetchTaskIds(jdbcTemplate, tableName);
                if (taskIds.isEmpty()) {
                    logger.info("\u8868 {} \u4e2d\u6ca1\u6709\u9700\u8981\u66f4\u65b0\u7684\u8bb0\u5f55\u3002", (Object)tableName);
                    continue;
                }
                ArrayList<Object[]> batchArgs = new ArrayList<Object[]>();
                for (String taskId : taskIds) {
                    if (StringUtils.isEmpty(taskId)) {
                        logger.warn("taskId \u4e3a\u7a7a\uff0c\u8df3\u8fc7\u66f4\u65b0\u3002");
                        continue;
                    }
                    String orgType = GcOrgTypeUtils.getOrgTypeByTaskIdOrEntityId((String)taskId, (String)"");
                    if (StringUtils.isEmpty(orgType)) {
                        logger.warn("Taskid: {} \u5bf9\u5e94\u7684 orgType \u4e3a\u7a7a\uff0c\u8df3\u8fc7\u66f4\u65b0\u3002", (Object)taskId);
                        continue;
                    }
                    batchArgs.add(new Object[]{orgType, taskId});
                }
                if (batchArgs.isEmpty()) continue;
                try {
                    int[] updateCounts = jdbcTemplate.batchUpdate(String.format(UPDATE_SQL_TEMPLATE, tableName), batchArgs);
                    logger.info("\u8868 {} \u6210\u529f\u66f4\u65b0\u4e86 {} \u884c\u7684 ORGTYPE \u5b57\u6bb5\u3002", (Object)tableName, (Object)updateCounts.length);
                }
                catch (Exception e) {
                    logger.error("\u66f4\u65b0\u8868 {} \u65f6\u53d1\u751f\u9519\u8bef: {}", tableName, e.getMessage(), e);
                    throw e;
                }
            }
            logger.info("\u6240\u6709\u8868\u7684 ORGTYPE \u5b57\u6bb5\u66f4\u65b0\u6210\u529f.");
        }
        catch (Exception e) {
            logger.error("\u6267\u884c\u5386\u53f2\u6570\u636e\u66f4\u65b0\u65f6\u53d1\u751f\u9519\u8bef: {}", (Object)e.getMessage(), (Object)e);
            throw e;
        }
    }

    private List<String> fetchTaskIds(JdbcTemplate jdbcTemplate, String tableName) {
        String selectSql = String.format(SELECT_SQL_TEMPLATE, tableName);
        return jdbcTemplate.query(selectSql, (RowMapper)new RowMapper<String>(){

            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                String taskid = rs.getString("TASKID");
                return StringUtils.hasText(taskid) ? taskid.trim() : null;
            }
        });
    }
}

