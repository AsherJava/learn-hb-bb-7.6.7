/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.np.definition.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

@Component
public class JDBCHelper {
    private final Logger logger = LoggerFactory.getLogger(JDBCHelper.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    public Connection getConnection() {
        return DataSourceUtils.getConnection((DataSource)this.jdbcTemplate.getDataSource());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ResultSet executeQuery(Connection conn, String sql, String[] args) {
        PreparedStatement prep = null;
        ResultSet reset = null;
        try {
            prep = conn.prepareStatement(sql);
            if (args != null && args.length > 0) {
                for (int i = 0; i < args.length; ++i) {
                    prep.setString(i + 1, args[i]);
                }
            }
            reset = prep.executeQuery();
        }
        catch (SQLException e) {
            this.logger.error(e.getMessage(), e);
        }
        finally {
            this.closePrep(prep);
        }
        return reset;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ResultSet executeQueryByScroll(Connection conn, String sql, Object[] args) {
        PreparedStatement prep = null;
        ResultSet reset = null;
        try {
            prep = conn.prepareStatement(sql, 1004, 1007);
            if (args != null && args.length > 0) {
                for (int i = 0; i < args.length; ++i) {
                    prep.setObject(i + 1, args[i]);
                }
            }
            reset = prep.executeQuery();
            this.closePrep(prep);
        }
        catch (SQLException e) {
            try {
                this.logger.error(e.getMessage(), e);
                this.closePrep(prep);
            }
            catch (Throwable throwable) {
                this.closePrep(prep);
                throw throwable;
            }
        }
        return reset;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void executeDml(String sql, String[] args) {
        Connection conn = this.getConnection();
        PreparedStatement prep = null;
        try {
            prep = conn.prepareStatement(sql);
            if (args != null && args.length > 0) {
                for (int i = 0; i < args.length; ++i) {
                    prep.setString(i + 1, args[i]);
                }
            }
            prep.execute();
        }
        catch (SQLException e) {
            this.logger.error(e.getMessage(), e);
        }
        finally {
            this.close(conn, prep, null);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void executeDml(String sql, Object[] args) {
        Connection conn = this.getConnection();
        PreparedStatement prep = null;
        try {
            prep = conn.prepareStatement(sql);
            if (args != null && args.length > 0) {
                for (int i = 0; i < args.length; ++i) {
                    prep.setObject(i + 1, args[i]);
                }
            }
            prep.execute();
        }
        catch (SQLException e) {
            this.logger.error(e.getMessage(), e);
        }
        finally {
            this.close(conn, prep, null);
        }
    }

    public void close(Connection conn, PreparedStatement prep, ResultSet res) {
        this.closeResult(res);
        this.closePrep(prep);
        this.closeConn(conn);
    }

    public void closeConn(Connection conn) {
        DataSourceUtils.releaseConnection((Connection)conn, (DataSource)this.jdbcTemplate.getDataSource());
    }

    public void closePrep(PreparedStatement prep) {
        if (prep != null) {
            try {
                prep.close();
            }
            catch (SQLException e) {
                prep = null;
                this.logger.error(e.getMessage(), e);
            }
        }
    }

    public void closeResult(ResultSet res) {
        if (res != null) {
            try {
                res.close();
            }
            catch (SQLException e) {
                res = null;
                this.logger.error(e.getMessage(), e);
            }
        }
    }
}

