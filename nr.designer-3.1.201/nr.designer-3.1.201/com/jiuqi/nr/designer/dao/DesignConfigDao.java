/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.PreparedStatementCallback
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback
 *  org.springframework.jdbc.support.lob.DefaultLobHandler
 *  org.springframework.jdbc.support.lob.LobCreator
 *  org.springframework.jdbc.support.lob.LobHandler
 */
package com.jiuqi.nr.designer.dao;

import com.jiuqi.nr.designer.web.rest.vo.ConfigVo;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;

@Repository
public class DesignConfigDao {
    @Autowired
    JdbcTemplate designConfigTpl;
    final String tblConfig = "SYS_HSHD_CONFIG";

    public Boolean saveDesignConfig(final ConfigVo configVo, String method) throws IOException {
        int execStatus = -1;
        String designConfigSql = "";
        if (method == "INSERT") {
            designConfigSql = "INSERT INTO SYS_HSHD_CONFIG(CONFIG_KEY, TSK_KEY, FC_KEY, TYPE, TYPEST2, STRDATA, UPDATEDATE) VALUES(?, ?, ?, ?, ?, ?, ?)";
            execStatus = (Integer)this.designConfigTpl.execute(designConfigSql, (PreparedStatementCallback)new AbstractLobCreatingPreparedStatementCallback((LobHandler)new DefaultLobHandler()){

                protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                    ps.setObject(1, configVo.getConfigKey());
                    ps.setObject(2, configVo.getTaskKey());
                    ps.setObject(3, configVo.getFormSchemeKey());
                    ps.setString(4, configVo.getConfigType());
                    ps.setString(5, configVo.getConfigTypeSt2());
                    lobCreator.setClobAsString(ps, 6, configVo.getData());
                    Date utilDate = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                    ps.setDate(7, sqlDate);
                }
            });
        } else if (method == "UPDATE") {
            designConfigSql = "UPDATE SYS_HSHD_CONFIG SET STRDATA=?, UPDATEDATE=? WHERE FC_KEY=? AND TYPE=?";
            execStatus = (Integer)this.designConfigTpl.execute(designConfigSql, (PreparedStatementCallback)new AbstractLobCreatingPreparedStatementCallback((LobHandler)new DefaultLobHandler()){

                protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                    lobCreator.setClobAsString(ps, 1, configVo.getData());
                    Date utilDate = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                    ps.setDate(2, sqlDate);
                    ps.setObject(3, configVo.getFormSchemeKey());
                    ps.setString(4, configVo.getConfigType());
                }
            });
        }
        if (execStatus > 0) {
            return true;
        }
        return false;
    }

    public Boolean hasDesignConfig(String fc_key, String configType) {
        int execStatus = -1;
        String designConfigSql = "SELECT COUNT(CONFIG_KEY) FROM SYS_HSHD_CONFIG WHERE FC_KEY=? AND TYPE=?";
        Object[] params = new Object[]{fc_key, configType};
        int[] argTyps = new int[]{12, 12};
        execStatus = (Integer)this.designConfigTpl.query(designConfigSql, params, argTyps, (ResultSetExtractor)new ResultSetExtractor<Integer>(){

            public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }
        });
        if (execStatus > 0) {
            return true;
        }
        return false;
    }

    public Boolean hasTblDesignConfig() {
        int execStatus = -1;
        String sysTableName = "user_tables";
        String tblDesignConfigSql = String.format("SELECT COUNT(1) FROM %s WHERE TABLE_NAME=?", "user_tables");
        Object[] params = new Object[]{"SYS_HSHD_CONFIG"};
        int[] argTyps = new int[]{12};
        execStatus = (Integer)this.designConfigTpl.query(tblDesignConfigSql, params, argTyps, (ResultSetExtractor)new ResultSetExtractor<Integer>(){

            public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }
        });
        if (execStatus > 0) {
            return true;
        }
        return false;
    }

    public Boolean createTblDesignConfig() {
        String tblDesignConfigCreate = String.format("CREATE TABLE %s(CONFIG_KEY NVARCHAR2(40), TSK_KEY NVARCHAR2(40), FC_KEY NVARCHAR2(40), TYPE NVARCHAR2(5), TYPEST2 NVARCHAR2(5), DATA CLOB, UPDATEDATE TIMESTAMP(6))", "SYS_HSHD_CONFIG");
        return this.designConfigTpl.update(tblDesignConfigCreate) > 0;
    }

    public String getDesignConfig(String fc_key, String configType) {
        String designConfigSql = "SELECT STRDATA FROM SYS_HSHD_CONFIG WHERE FC_KEY=? AND TYPE=?";
        Object[] params = new Object[]{fc_key, configType};
        int[] argTyps = new int[]{12, 12};
        return (String)this.designConfigTpl.query(designConfigSql.toString(), params, argTyps, (ResultSetExtractor)new ResultSetExtractor<Object>(){

            public String extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    return rs.getString(1);
                }
                return null;
            }
        });
    }
}

