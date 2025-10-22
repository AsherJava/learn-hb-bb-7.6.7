/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.bi.transfer.engine.intf.IExportContext
 *  com.jiuqi.bi.transfer.engine.intf.IImportContext
 *  com.jiuqi.nr.param.transfer.definition.spi.TaskTransfer
 *  javax.annotation.Resource
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.PreparedStatementCallback
 *  org.springframework.jdbc.core.RowCallbackHandler
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.definitionext.taskExtConfig.service.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.bi.transfer.engine.intf.IExportContext;
import com.jiuqi.bi.transfer.engine.intf.IImportContext;
import com.jiuqi.nr.definitionext.taskExtConfig.internal.service.TaskExtConfigSession;
import com.jiuqi.nr.definitionext.taskExtConfig.model.TaskExtConfigDefine;
import com.jiuqi.nr.param.transfer.definition.spi.TaskTransfer;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

@Service
public class TaskExtConfigExport
implements TaskTransfer {
    private static final Logger log = LoggerFactory.getLogger(TaskExtConfigExport.class);
    private static final String PREFIX = "taskextension-";
    ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Resource
    private TaskExtConfigSession taskExtConfigSession;

    public Connection getConnection() {
        return DataSourceUtils.getConnection((DataSource)this.jdbcTemplate.getDataSource());
    }

    public String getId() {
        return "bc8226bc-0988-4da0-9cc0-7550951c5a5b";
    }

    private TaskExtConfigDefine getConfigResultSet(ResultSet rs) throws SQLException {
        TaskExtConfigDefine config = new TaskExtConfigDefine();
        config.setExtKey(rs.getString("EXTKEY"));
        config.setExtType(rs.getString("EXTTYPE"));
        config.setExtCode(rs.getString("EXTCODE"));
        config.setTaskKey(rs.getString("TASKKEY"));
        config.setSchemaKey(rs.getString("SCHEMAKEY"));
        config.setExtData(rs.getString("EXTDATA"));
        return config;
    }

    public byte[] exportTaskData(IExportContext iExportContext, String taskKey) throws TransferException {
        try {
            log.info("\u4efb\u52a1\u914d\u7f6e\u53c2\u6570\u5bfc\u51fa\u5f00\u59cb\u3002");
            final ArrayList configs = new ArrayList();
            String libTableName = "SYS_FORMSCHEMA_EXTPARA";
            String sql = "SELECT * FROM " + libTableName + " WHERE TASKKEY=?";
            this.jdbcTemplate.query(sql, new Object[]{taskKey}, new RowCallbackHandler(){

                public void processRow(ResultSet rs) throws SQLException {
                    configs.add(TaskExtConfigExport.this.getConfigResultSet(rs));
                }
            });
            log.info("\u4efb\u52a1\u914d\u7f6e\u53c2\u6570\u6570\u636e\u67e5\u8be2\u6210\u529f\u3002");
            byte[] bytes = this.mapper.writeValueAsBytes(configs);
            log.info("\u4efb\u52a1\u914d\u7f6e\u53c2\u6570\u6570\u636e\u5bfc\u51fa\u6210\u529f\u3002");
            return bytes;
        }
        catch (JsonProcessingException e) {
            log.error("\u4efb\u52a1\u914d\u7f6e\u53c2\u6570\u6570\u636e\u5bfc\u51fa\u5f02\u5e38\uff1a" + e.getMessage());
            return new byte[0];
        }
    }

    public void importTaskData(IImportContext context, String taskKey, byte[] data) throws TransferException {
        try {
            log.info("\u4efb\u52a1\u914d\u7f6e\u53c2\u6570\u5bfc\u5165\u5f00\u59cb\uff0c\u5bfc\u5165\u5931\u8d25\u5219\u8df3\u8fc7\u3002");
            List configs = (List)this.mapper.readValue(data, (TypeReference)new TypeReference<List<TaskExtConfigDefine>>(){});
            log.info("\u4efb\u52a1\u914d\u7f6e\u53c2\u6570\u89e3\u6790\u8f6c\u6362\u6210\u529f\u3002");
            if (!configs.isEmpty()) {
                if (!((TaskExtConfigDefine)configs.get(0)).getExtType().startsWith(PREFIX)) {
                    log.warn("\u4efb\u52a1\u914d\u7f6e\u53c2\u6570\uff0c\u6570\u636e\u7248\u672c\u8fc7\u8001\uff0c\u4e0d\u652f\u6301\u5bfc\u5165\u3002");
                    return;
                }
                String libTableName = "SYS_FORMSCHEMA_EXTPARA";
                this.clearOldData(libTableName, taskKey);
                this.insertNewData(configs, libTableName);
                log.info("\u4efb\u52a1\u914d\u7f6e\u53c2\u6570\u6570\u636e\u5bfc\u5165\u6210\u529f\uff0c\u5bfc\u5165\u6210\u529f\u6761\u6570\uff1a" + configs.size());
            } else {
                log.warn("\u4efb\u52a1\u914d\u7f6e\u53c2\u6570\u89e3\u67900\u6761\u6570\u636e\u3002");
            }
        }
        catch (IOException e) {
            log.error("\u4efb\u52a1\u914d\u7f6e\u53c2\u6570\u5bfc\u5165\u5f02\u5e38\uff1a" + e.getMessage());
        }
    }

    private void clearOldData(String tableName, final String taskKey) {
        String deleteSql = "DELETE FROM " + tableName + " WHERE TASKKEY=?";
        this.jdbcTemplate.execute(deleteSql, (PreparedStatementCallback)new PreparedStatementCallback<Object>(){

            public Object doInPreparedStatement(PreparedStatement ps) throws SQLException {
                ps.setString(1, taskKey);
                ps.execute();
                return null;
            }
        });
        log.info("\u5220\u9664\u5bf9\u5e94\u4efb\u52a1\u6570\u636e\u6210\u529f");
        this.taskExtConfigSession.clearResult();
        log.info("\u6e05\u7406\u7f13\u5b58\u6210\u529f");
    }

    private void insertNewData(final List<TaskExtConfigDefine> configs, String tableName) {
        String insertSql = "INSERT INTO " + tableName + "(" + "EXTKEY" + "," + "EXTTYPE" + "," + "EXTCODE" + "," + "TASKKEY" + "," + "SCHEMAKEY" + "," + "EXTDATA" + ") VALUES (?,?,?,?,?,?)";
        this.jdbcTemplate.batchUpdate(insertSql, new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                TaskExtConfigDefine config = (TaskExtConfigDefine)configs.get(i);
                ps.setString(1, config.getExtKey());
                ps.setString(2, config.getExtType());
                ps.setString(3, config.getExtCode());
                ps.setString(4, config.getTaskKey());
                ps.setString(5, config.getSchemaKey());
                ps.setString(6, config.getExtData());
            }

            public int getBatchSize() {
                return configs.size();
            }
        });
    }
}

