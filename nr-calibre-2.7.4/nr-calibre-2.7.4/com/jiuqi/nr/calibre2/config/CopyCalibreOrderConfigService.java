/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.nr.calibre2.config;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.calibre2.domain.CalibreDataDO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CopyCalibreOrderConfigService
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(CopyCalibreOrderConfigService.class);

    public void execute(DataSource dataSource) throws Exception {
        try (Connection conn = dataSource.getConnection();){
            Throwable throwable;
            Throwable throwable2;
            String tableExistSql = "select 1 from NR_CALIBRE_DATA";
            String selectSql = "select * from NR_CALIBRE_DATA ORDER BY CDT_ORDER";
            ArrayList<CalibreDataDO> CalibreDataDOS = new ArrayList<CalibreDataDO>();
            try {
                throwable2 = null;
                try (PreparedStatement statement = conn.prepareStatement(tableExistSql);){
                    statement.executeQuery();
                }
                catch (Throwable throwable3) {
                    throwable2 = throwable3;
                    throw throwable3;
                }
            }
            catch (Exception e) {
                this.logger.warn("\u6267\u884c\u5347\u7ea7\u811a\u672c\u51fa\u9519");
                if (conn != null) {
                    if (var3_4 != null) {
                        try {
                            conn.close();
                        }
                        catch (Throwable throwable4) {
                            var3_4.addSuppressed(throwable4);
                        }
                    } else {
                        conn.close();
                    }
                }
                return;
            }
            try {
                throwable2 = null;
                try (PreparedStatement ps = conn.prepareStatement(selectSql);){
                    throwable = null;
                    try (ResultSet rs = ps.executeQuery();){
                        this.check(rs, conn);
                        while (rs.next()) {
                            CalibreDataDO calibreDataDO = this.buildObjectByRs(rs);
                            calibreDataDO.setOrder(OrderGenerator.newOrderID());
                            CalibreDataDOS.add(calibreDataDO);
                        }
                    }
                    catch (Throwable calibreDataDO) {
                        throwable = calibreDataDO;
                        throw calibreDataDO;
                    }
                }
                catch (Throwable rs) {
                    throwable2 = rs;
                    throw rs;
                }
            }
            catch (Exception e) {
                this.logger.warn("\u6267\u884c\u5347\u7ea7\u811a\u672c\u51fa\u9519");
                if (conn != null) {
                    if (var3_4 != null) {
                        try {
                            conn.close();
                        }
                        catch (Throwable throwable5) {
                            var3_4.addSuppressed(throwable5);
                        }
                    } else {
                        conn.close();
                    }
                }
                return;
            }
            ResultSet tables = conn.getMetaData().getTables(null, null, "NR_CALIBRE_DATA", null);
            String updateOrdinalsql = "UPDATE NR_CALIBRE_DATA SET CDT_ORDINAL = ? WHERE CDT_CALIBRE_CODE = ? AND CDT_CODE = ?";
            try {
                throwable = null;
                try (PreparedStatement ps = conn.prepareStatement(updateOrdinalsql);){
                    conn.setAutoCommit(false);
                    for (CalibreDataDO query : CalibreDataDOS) {
                        ps.setLong(1, query.getOrder());
                        ps.setString(2, query.getCalibreCode());
                        ps.setString(3, query.getCode());
                        ps.addBatch();
                    }
                    int[] insertCount = ps.executeBatch();
                    this.logger.info("\u53e3\u5f84\u6570\u636e\u8868NR_CALIBRE_DATA\u91cc\u7684order\u8f6c\u6362\u4e3a\u4e86\u65b0\u7684String\u7c7b\u578b\u7684ordinal");
                    conn.commit();
                    conn.setAutoCommit(true);
                }
                catch (Throwable throwable6) {
                    throwable = throwable6;
                    throw throwable6;
                }
            }
            catch (Exception e) {
                this.logger.error(e.getMessage(), e);
                conn.rollback();
                conn.setAutoCommit(true);
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    private CalibreDataDO buildObjectByRs(ResultSet rs) throws Exception {
        CalibreDataDO calibreDataDO = new CalibreDataDO();
        calibreDataDO.setKey(rs.getString("CDT_KEY"));
        calibreDataDO.setCode(rs.getString("CDT_CODE"));
        calibreDataDO.setCalibreCode(rs.getString("CDT_CALIBRE_CODE"));
        return calibreDataDO;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean check(ResultSet rs, Connection conn) throws Exception {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        boolean check = false;
        for (int i = 1; i <= columnCount; ++i) {
            if (!"CDT_ORDINAL".equals(metaData.getColumnName(i))) continue;
            check = true;
            break;
        }
        if (!check) {
            try (PreparedStatement preparedStatement = null;){
                String insertColumnSql = "ALTER TABLE NR_CALIBRE_DATA ADD CDT_ORDINAL NUMBER(19, 6)";
                preparedStatement = conn.prepareStatement(insertColumnSql);
                preparedStatement.executeUpdate();
            }
            this.logger.info("\u5347\u7ea7\u811a\u672c\u6ca1\u6709\u628a\u6392\u5e8f\u5b57\u6bb5\u52a0\u5165\uff0c\u7531call class\u52a0\u5165\u4e86");
        }
        return check;
    }
}

