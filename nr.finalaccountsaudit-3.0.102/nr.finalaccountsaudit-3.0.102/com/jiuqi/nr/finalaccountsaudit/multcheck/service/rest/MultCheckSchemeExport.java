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
 *  com.jiuqi.nr.definitionext.taskExtConfig.model.TaskExtTransUtils
 *  com.jiuqi.nr.param.transfer.definition.spi.TaskTransfer
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowCallbackHandler
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.finalaccountsaudit.multcheck.service.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.bi.transfer.engine.intf.IExportContext;
import com.jiuqi.bi.transfer.engine.intf.IImportContext;
import com.jiuqi.nr.definitionext.taskExtConfig.model.TaskExtTransUtils;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.MultCheckScheme;
import com.jiuqi.nr.param.transfer.definition.spi.TaskTransfer;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

@Service
public class MultCheckSchemeExport
implements TaskTransfer {
    private static final Logger log = LoggerFactory.getLogger(MultCheckSchemeExport.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TaskExtTransUtils taskExtTransUtils;
    ObjectMapper mapper = new ObjectMapper();
    PreparedStatement ps = null;

    public Connection getConnection() {
        return DataSourceUtils.getConnection((DataSource)this.jdbcTemplate.getDataSource());
    }

    public String getId() {
        return "bc8226bc-0988-4da0-9rf0-75509558635b";
    }

    private MultCheckScheme getConfigResultSet(ResultSet rs) throws SQLException {
        MultCheckScheme scheme = new MultCheckScheme();
        scheme.setKey(rs.getString("S_KEY"));
        scheme.setContent(rs.getString("S_CONTENT"));
        scheme.setFormSchemeKey(rs.getString("S_FORMSCHEMEKEY"));
        scheme.setTaskKey(rs.getString("S_TASKKEY"));
        scheme.setDw(rs.getString("S_DW"));
        scheme.setName(rs.getString("S_NAME"));
        scheme.setOrder(rs.getString("S_ORDER"));
        return scheme;
    }

    public byte[] exportTaskData(IExportContext iExportContext, String taskKey) throws TransferException {
        try {
            log.info("\u7efc\u5408\u5ba1\u6838\u65b9\u6848\u53c2\u6570\u5bfc\u51fa\u5f00\u59cb\u3002");
            final ArrayList schemes = new ArrayList();
            String libTableName = "SYS_MULTCHECK_SCHEME";
            String sql = "SELECT * FROM " + libTableName + " WHERE S_TASKKEY=?";
            Object[] params = new Object[]{taskKey};
            int[] argTyps = new int[]{12};
            this.jdbcTemplate.query(sql, params, argTyps, new RowCallbackHandler(){

                public void processRow(ResultSet rs) throws SQLException {
                    MultCheckScheme config = MultCheckSchemeExport.this.getConfigResultSet(rs);
                    schemes.add(config);
                }
            });
            log.info("\u7efc\u5408\u5ba1\u6838\u65b9\u6848\u53c2\u6570\u6570\u636e\u67e5\u8be2\u6210\u529f\u3002");
            byte[] bytes = this.mapper.writeValueAsBytes(schemes);
            log.info("\u7efc\u5408\u5ba1\u6838\u65b9\u6848\u53c2\u6570\u6570\u636e\u5bfc\u51fa\u6210\u529f\u3002");
            return bytes;
        }
        catch (JsonProcessingException e) {
            log.info("\u7efc\u5408\u5ba1\u6838\u65b9\u6848\u53c2\u6570\u6570\u636e\u5bfc\u51fa\u5f02\u5e38\uff1a" + e.getMessage());
            return new byte[0];
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void importTaskData(IImportContext context, String taskKey, byte[] data) throws TransferException {
        try {
            log.info("\u7efc\u5408\u5ba1\u6838\u65b9\u6848\u53c2\u6570\u5bfc\u5165\u5f00\u59cb\u3002");
            Connection conn = this.getConnection();
            List schemes = (List)this.mapper.readValue(data, (TypeReference)new TypeReference<List<MultCheckScheme>>(){});
            log.info("\u7efc\u5408\u5ba1\u6838\u65b9\u6848\u53c2\u6570\u89e3\u6790\u8f6c\u6362\u6210\u529f\u3002");
            String libTableName = "SYS_MULTCHECK_SCHEME";
            String deleteSql = "DELETE FROM " + libTableName + " WHERE S_TASKKEY=?";
            this.jdbcTemplate.update(deleteSql, new Object[]{taskKey});
            log.info("\u5220\u9664\u5bf9\u5e94\u4efb\u52a1\u6570\u636e\u6210\u529f");
            String insertSql = "insert into SYS_MULTCHECK_SCHEME (S_KEY,S_FORMSCHEMEKEY,S_CONTENT,S_DW,S_TASKKEY,S_NAME,S_ORDER)  values (?,?,?,?,?,?,?)";
            this.ps = conn.prepareStatement(insertSql);
            try {
                for (MultCheckScheme scheme : schemes) {
                    this.ps.setString(1, scheme.getKey());
                    this.ps.setString(2, scheme.getFormSchemeKey());
                    this.ps.setClob(3, this.taskExtTransUtils.transClob(scheme.getContent()));
                    this.ps.setClob(4, this.taskExtTransUtils.transClob(scheme.getDw()));
                    this.ps.setString(5, scheme.getTaskKey());
                    this.ps.setString(6, scheme.getName());
                    this.ps.setString(7, scheme.getOrder());
                    this.ps.addBatch();
                }
                this.ps.executeBatch();
                log.info("\u7efc\u5408\u5ba1\u6838\u65b9\u6848\u53c2\u6570\u6570\u636e\u5bfc\u5165\u6210\u529f\uff0c\u5bfc\u5165\u6210\u529f\u6761\u6570\uff1a" + schemes.size());
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
                log.info("\u7efc\u5408\u5ba1\u6838\u65b9\u6848\u53c2\u6570\u5bfc\u5165\u5f02\u5e38\uff1a " + e.getMessage());
            }
            finally {
                if (conn != null) {
                    DataSourceUtils.releaseConnection((Connection)conn, (DataSource)this.jdbcTemplate.getDataSource());
                }
            }
        }
        catch (IOException | SQLException e) {
            log.info("\u7efc\u5408\u5ba1\u6838\u65b9\u6848\u53c2\u6570\u5bfc\u5165\u5f02\u5e38\uff1a " + e.getMessage());
        }
    }
}

