/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.attachment.service.impl;

import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.attachment.message.FileCategoryInfo;
import com.jiuqi.nr.attachment.provider.param.FileBucketNameParam;
import com.jiuqi.nr.attachment.service.FileCategoryService;
import com.jiuqi.nr.attachment.tools.AttachmentFileAreaService;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
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
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

@Service
public class FileCategoryServiceImpl
implements FileCategoryService {
    private static final Logger logger = LoggerFactory.getLogger(FileCategoryServiceImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private AttachmentFileAreaService attachmentFileAreaService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;

    @Override
    public String getDefaultFileCategoryCode() {
        List<FileCategoryInfo> fileCategoryInfos = this.getFileCategoryMap();
        for (FileCategoryInfo fileCategoryInfo : fileCategoryInfos) {
            if (!fileCategoryInfo.isDefaultCategory()) continue;
            return fileCategoryInfo.getCode();
        }
        return "";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<FileCategoryInfo> getFileCategoryMap() {
        ArrayList<FileCategoryInfo> fileCategoryInfos = new ArrayList<FileCategoryInfo>();
        Connection connection = DataSourceUtils.getConnection((DataSource)this.jdbcTemplate.getDataSource());
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ").append("NR_FILE_CATEGORY").append(" ORDER BY ").append("CATEGORY_ORDER").append(" ASC");
        try (PreparedStatement statement = connection.prepareStatement(sql.toString());){
            ResultSet rs = statement.executeQuery();
            Object object = null;
            try {
                while (rs.next()) {
                    FileCategoryInfo fileCategoryInfo = new FileCategoryInfo(rs.getString("CATEGORY_CODE"), rs.getString("CATEGORY_TITLE"), rs.getInt("CATEGORY_ORDER"), rs.getInt("CATEGORY_DEFAULT") == 1);
                    fileCategoryInfos.add(fileCategoryInfo);
                }
            }
            catch (Throwable fileCategoryInfo) {
                object = fileCategoryInfo;
                throw fileCategoryInfo;
            }
            finally {
                if (rs != null) {
                    if (object != null) {
                        try {
                            rs.close();
                        }
                        catch (Throwable fileCategoryInfo) {
                            ((Throwable)object).addSuppressed(fileCategoryInfo);
                        }
                    } else {
                        rs.close();
                    }
                }
            }
        }
        catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        finally {
            if (null != connection) {
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
            }
        }
        block24: for (FileCategoryInfo fileCategoryInfo : fileCategoryInfos) {
            List allDataScheme = this.runtimeDataSchemeService.getAllDataScheme();
            for (DataScheme dataScheme : allDataScheme) {
                FileBucketNameParam param = new FileBucketNameParam(dataScheme.getKey());
                List<ObjectInfo> objectInfoByProp = this.attachmentFileAreaService.getObjectInfoByProp(param, "category", fileCategoryInfo.getCode(), false);
                if (null == objectInfoByProp || objectInfoByProp.isEmpty()) continue;
                fileCategoryInfo.setReferenced(true);
                continue block24;
            }
        }
        return fileCategoryInfos;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<FileCategoryInfo> getFileCategoryMapForSystem() {
        ArrayList<FileCategoryInfo> fileCategoryInfos = new ArrayList<FileCategoryInfo>();
        Connection connection = DataSourceUtils.getConnection((DataSource)this.jdbcTemplate.getDataSource());
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ").append("NR_FILE_CATEGORY").append(" ORDER BY ").append("CATEGORY_ORDER").append(" ASC");
        try (PreparedStatement statement = connection.prepareStatement(sql.toString());
             ResultSet rs = statement.executeQuery();){
            while (rs.next()) {
                FileCategoryInfo fileCategoryInfo = new FileCategoryInfo(rs.getString("CATEGORY_CODE"), rs.getString("CATEGORY_TITLE"), rs.getInt("CATEGORY_ORDER"), rs.getInt("CATEGORY_DEFAULT") == 1);
                fileCategoryInfos.add(fileCategoryInfo);
            }
        }
        catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        finally {
            if (null != connection) {
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
            }
        }
        return fileCategoryInfos;
    }

    @Override
    public String getFileCategoryTitle(String code) {
        List<FileCategoryInfo> fileCategoryInfos = this.getFileCategoryMap();
        if (StringUtils.isEmpty((String)code)) {
            for (FileCategoryInfo fileCategoryInfo : fileCategoryInfos) {
                if (!fileCategoryInfo.isDefaultCategory()) continue;
                return fileCategoryInfo.getTitle();
            }
        } else {
            for (FileCategoryInfo fileCategoryInfo : fileCategoryInfos) {
                if (!code.equals(fileCategoryInfo.getCode())) continue;
                return fileCategoryInfo.getTitle();
            }
        }
        return "";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    @Override
    public boolean updateFileCategory(List<FileCategoryInfo> fileCategoryInfos) {
        Connection connection = DataSourceUtils.getConnection((DataSource)this.jdbcTemplate.getDataSource());
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append("DELETE FROM ").append("NR_FILE_CATEGORY");
        StringBuilder insertSql = new StringBuilder();
        if (!fileCategoryInfos.isEmpty()) {
            insertSql.append("INSERT INTO ").append("NR_FILE_CATEGORY").append(" (");
            insertSql.append("CATEGORY_CODE").append(",").append("CATEGORY_TITLE").append(",").append("CATEGORY_ORDER").append(",").append("CATEGORY_DEFAULT").append(")");
            insertSql.append(" VALUES (?,?,?,?)");
        }
        try {
            boolean bl;
            Throwable throwable;
            PreparedStatement statement;
            block26: {
                block27: {
                    block23: {
                        boolean bl2;
                        block24: {
                            block25: {
                                statement = connection.prepareStatement(deleteSql.toString());
                                throwable = null;
                                statement.execute();
                                if (fileCategoryInfos.isEmpty()) break block23;
                                bl2 = this.executeInsert(fileCategoryInfos, connection, insertSql);
                                if (statement == null) break block24;
                                if (throwable == null) break block25;
                                try {
                                    statement.close();
                                }
                                catch (Throwable throwable2) {
                                    throwable.addSuppressed(throwable2);
                                }
                                break block24;
                            }
                            statement.close();
                        }
                        return bl2;
                    }
                    bl = true;
                    if (statement == null) break block26;
                    if (throwable == null) break block27;
                    try {
                        statement.close();
                    }
                    catch (Throwable throwable3) {
                        throwable.addSuppressed(throwable3);
                    }
                    break block26;
                }
                statement.close();
            }
            return bl;
            catch (Throwable throwable4) {
                try {
                    try {
                        throwable = throwable4;
                        throw throwable4;
                    }
                    catch (Throwable throwable5) {
                        if (statement != null) {
                            if (throwable != null) {
                                try {
                                    statement.close();
                                }
                                catch (Throwable throwable6) {
                                    throwable.addSuppressed(throwable6);
                                }
                            } else {
                                statement.close();
                            }
                        }
                        throw throwable5;
                    }
                }
                catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                    boolean bl3 = false;
                    return bl3;
                }
            }
        }
        finally {
            if (null != connection) {
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean executeInsert(List<FileCategoryInfo> fileCategoryInfos, Connection connection, StringBuilder insertSql) {
        try (PreparedStatement statementIns = connection.prepareStatement(insertSql.toString());){
            for (FileCategoryInfo fileCategoryInfo : fileCategoryInfos) {
                statementIns.setString(1, fileCategoryInfo.getCode());
                statementIns.setString(2, fileCategoryInfo.getTitle());
                statementIns.setInt(3, fileCategoryInfo.getOrder());
                statementIns.setInt(4, fileCategoryInfo.isDefaultCategory() ? 1 : 0);
                statementIns.addBatch();
            }
            statementIns.executeBatch();
            boolean bl = true;
            return bl;
        }
        catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }
}

