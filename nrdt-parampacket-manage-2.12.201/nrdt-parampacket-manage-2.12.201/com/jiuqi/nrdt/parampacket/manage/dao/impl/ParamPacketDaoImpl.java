/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.PreparedStatementCallback
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback
 *  org.springframework.jdbc.support.lob.DefaultLobHandler
 *  org.springframework.jdbc.support.lob.LobCreator
 *  org.springframework.jdbc.support.lob.LobHandler
 */
package com.jiuqi.nrdt.parampacket.manage.dao.impl;

import com.jiuqi.nrdt.parampacket.manage.bean.ParamPacket;
import com.jiuqi.nrdt.parampacket.manage.dao.IParamPacketDao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class ParamPacketDaoImpl
implements IParamPacketDao {
    private static final Logger logger = LoggerFactory.getLogger(ParamPacketDaoImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String QUERY_PARAMPACKET_BY_PARENRT = String.format("SELECT %s,%s,%s,%s,%s,%s,%s,%s,%s FROM %s WHERE %s = ?", "P_GUID", "P_CODE", "P_TITLE", "P_UPDATETIME", "P_PARENT", "P_FILE_KEY", "P_SCHEME_KEY", "P_ENABLE", "P_TASKS", "NRDT_PARAMPACKET", "P_PARENT");
    private static final String ADD_PARAM_PAACKET = String.format("INSERT INTO %s(%s,%s,%s,%s,%s,%s,%s,%s,%s) VALUES(?,?,?,?,?,?,?,?,?)", "NRDT_PARAMPACKET", "P_GUID", "P_CODE", "P_TITLE", "P_UPDATETIME", "P_PARENT", "P_FILE_KEY", "P_SCHEME_KEY", "P_ENABLE", "P_TASKS");
    private static final String UPDATE_PARAM_PACKET = String.format("UPDATE %s SET %s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=? WHERE %s=?", "NRDT_PARAMPACKET", "P_GUID", "P_CODE", "P_TITLE", "P_UPDATETIME", "P_PARENT", "P_FILE_KEY", "P_SCHEME_KEY", "P_ENABLE", "P_TASKS", "P_GUID");
    private static final String DELETE_PARAN_PACKET = String.format("DELETE FROM %s WHERE %s = ?", "NRDT_PARAMPACKET", "P_GUID");
    private static final String QUERY_PARAM_PACKET = String.format("SELECT %s,%s,%s,%s,%s,%s,%s,%s,%s FROM %s WHERE %s = ?", "P_GUID", "P_CODE", "P_TITLE", "P_UPDATETIME", "P_PARENT", "P_FILE_KEY", "P_SCHEME_KEY", "P_ENABLE", "P_TASKS", "NRDT_PARAMPACKET", "P_GUID");
    private static final String QUERY_PARAM_PACKET_BY_CODE = String.format("SELECT %s,%s,%s,%s,%s,%s,%s,%s,%s FROM %s WHERE %s = ?", "P_GUID", "P_CODE", "P_TITLE", "P_UPDATETIME", "P_PARENT", "P_FILE_KEY", "P_SCHEME_KEY", "P_ENABLE", "P_TASKS", "NRDT_PARAMPACKET", "P_CODE");
    private static final String QUERY_ALL_PARAM_PACKET = String.format("SELECT %s,%s,%s,%s,%s,%s,%s,%s,%s FROM %s", "P_GUID", "P_CODE", "P_TITLE", "P_UPDATETIME", "P_PARENT", "P_FILE_KEY", "P_SCHEME_KEY", "P_ENABLE", "P_TASKS", "NRDT_PARAMPACKET");

    @Override
    public List<ParamPacket> queryParamPacketByParent(String parent) {
        return this.jdbcTemplate.query(QUERY_PARAMPACKET_BY_PARENRT, (RowMapper)new RowMapper<ParamPacket>(){

            public ParamPacket mapRow(ResultSet rs, int rowNum) throws SQLException {
                return ParamPacketDaoImpl.this.buildParamPacket(rs);
            }
        }, new Object[]{parent});
    }

    private ParamPacket buildParamPacket(ResultSet rs) throws SQLException {
        ParamPacket paramPacket = new ParamPacket();
        paramPacket.setGuid(rs.getString("P_GUID"));
        paramPacket.setCode(rs.getString("P_CODE"));
        paramPacket.setTitle(rs.getString("P_TITLE"));
        paramPacket.setUpdateTime(rs.getTimestamp("P_UPDATETIME"));
        paramPacket.setParent(rs.getString("P_PARENT"));
        paramPacket.setFileKey(rs.getString("P_FILE_KEY"));
        paramPacket.setSchemeKey(rs.getString("P_SCHEME_KEY"));
        paramPacket.setEnable(rs.getInt("P_ENABLE") == 1);
        paramPacket.setTasks(rs.getString("P_TASKS"));
        return paramPacket;
    }

    @Override
    public void addParamPacket(final ParamPacket paramPacket) {
        this.jdbcTemplate.execute(ADD_PARAM_PAACKET, (PreparedStatementCallback)new AbstractLobCreatingPreparedStatementCallback((LobHandler)new DefaultLobHandler()){

            protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                ps.setString(1, paramPacket.getGuid());
                ps.setString(2, paramPacket.getCode());
                ps.setString(3, paramPacket.getTitle());
                if (paramPacket.getUpdateTime() == null) {
                    ps.setTimestamp(4, null);
                } else {
                    ps.setTimestamp(4, new Timestamp(paramPacket.getUpdateTime().getTime()));
                }
                ps.setString(5, paramPacket.getParent());
                ps.setString(6, paramPacket.getFileKey());
                ps.setString(7, paramPacket.getSchemeKey());
                ps.setInt(8, paramPacket.getEnable() != false ? 1 : 0);
                ps.setString(9, paramPacket.getTasks());
            }
        });
    }

    @Override
    public void updateParamPacket(final ParamPacket paramPacket) {
        this.jdbcTemplate.execute(UPDATE_PARAM_PACKET, (PreparedStatementCallback)new AbstractLobCreatingPreparedStatementCallback((LobHandler)new DefaultLobHandler()){

            protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                ps.setString(1, paramPacket.getGuid());
                ps.setString(2, paramPacket.getCode());
                ps.setString(3, paramPacket.getTitle());
                if (paramPacket.getUpdateTime() == null) {
                    ps.setTimestamp(4, null);
                } else {
                    ps.setTimestamp(4, new Timestamp(paramPacket.getUpdateTime().getTime()));
                }
                ps.setString(5, paramPacket.getParent());
                ps.setString(6, paramPacket.getFileKey());
                ps.setString(7, paramPacket.getSchemeKey());
                ps.setInt(8, paramPacket.getEnable() != false ? 1 : 0);
                ps.setString(9, paramPacket.getTasks());
                ps.setString(10, paramPacket.getGuid());
            }
        });
    }

    @Override
    public void deleteParamPacket(String guid) {
        this.jdbcTemplate.update(DELETE_PARAN_PACKET, new Object[]{guid});
    }

    @Override
    public ParamPacket queryParamPacket(String guid) {
        List paremPacketDtos = this.jdbcTemplate.query(QUERY_PARAM_PACKET, (RowMapper)new RowMapper<ParamPacket>(){

            public ParamPacket mapRow(ResultSet rs, int rowNum) throws SQLException {
                return ParamPacketDaoImpl.this.buildParamPacket(rs);
            }
        }, new Object[]{guid});
        if (CollectionUtils.isEmpty(paremPacketDtos)) {
            return null;
        }
        return (ParamPacket)paremPacketDtos.get(0);
    }

    @Override
    public ParamPacket queryParamPacketByCode(String code) {
        List paremPacketDtos = this.jdbcTemplate.query(QUERY_PARAM_PACKET_BY_CODE, (RowMapper)new RowMapper<ParamPacket>(){

            public ParamPacket mapRow(ResultSet rs, int rowNum) throws SQLException {
                return ParamPacketDaoImpl.this.buildParamPacket(rs);
            }
        }, new Object[]{code});
        if (CollectionUtils.isEmpty(paremPacketDtos)) {
            return null;
        }
        return (ParamPacket)paremPacketDtos.get(0);
    }

    @Override
    public List<ParamPacket> queryAllParamPacket() {
        return this.jdbcTemplate.query(QUERY_ALL_PARAM_PACKET, (RowMapper)new RowMapper<ParamPacket>(){

            public ParamPacket mapRow(ResultSet rs, int rowNum) throws SQLException {
                return ParamPacketDaoImpl.this.buildParamPacket(rs);
            }
        });
    }
}

