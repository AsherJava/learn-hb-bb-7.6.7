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

import com.jiuqi.nrdt.parampacket.manage.bean.ParamPacketGroup;
import com.jiuqi.nrdt.parampacket.manage.dao.IParamPacketGroupDao;
import com.jiuqi.nrdt.parampacket.manage.dao.impl.ParamPacketDaoImpl;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class ParamPacketGroupDaoImpl
implements IParamPacketGroupDao {
    private static final Logger logger = LoggerFactory.getLogger(ParamPacketDaoImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String QUERY_PARAMPACKET_GTOUP_BY_PARENRT = String.format("SELECT %s,%s,%s,%s FROM %s WHERE %s = ?", "G_GUID", "G_TITLE", "G_PARENT", "G_SCHEME_KEY", "NRDT_PARAMPACKET_GROUP", "G_PARENT");
    private static final String ADD_PARAM_PAACKET_GTOUP = String.format("INSERT INTO %s(%s,%s,%s,%s) VALUES(?,?,?,?)", "NRDT_PARAMPACKET_GROUP", "G_GUID", "G_TITLE", "G_PARENT", "G_SCHEME_KEY");
    private static final String UPDATE_PARAM_PACKET_GTOUP = String.format("UPDATE %s SET %s=?,%s=?,%s=?,%s=? WHERE %s=?", "NRDT_PARAMPACKET_GROUP", "G_GUID", "G_TITLE", "G_PARENT", "G_SCHEME_KEY", "G_GUID");
    private static final String DELETE_PARAN_PACKET_GTOUP = String.format("DELETE FROM %s WHERE %s = ?", "NRDT_PARAMPACKET_GROUP", "G_GUID");
    private static final String QUERY_PARAM_PACKET_GTOUP = String.format("SELECT %s,%s,%s,%s FROM %s WHERE %s = ?", "G_GUID", "G_TITLE", "G_PARENT", "G_SCHEME_KEY", "NRDT_PARAMPACKET_GROUP", "G_GUID");

    @Override
    public List<ParamPacketGroup> queryParamPacketGroupByParent(String parent) {
        return this.jdbcTemplate.query(QUERY_PARAMPACKET_GTOUP_BY_PARENRT, (RowMapper)new RowMapper<ParamPacketGroup>(){

            public ParamPacketGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
                return ParamPacketGroupDaoImpl.this.buildParamPacketGroup(rs);
            }
        }, new Object[]{parent});
    }

    private ParamPacketGroup buildParamPacketGroup(ResultSet rs) throws SQLException {
        ParamPacketGroup paramPacketGroup = new ParamPacketGroup();
        paramPacketGroup.setGuid(rs.getString("G_GUID"));
        paramPacketGroup.setTitle(rs.getString("G_TITLE"));
        paramPacketGroup.setParent(rs.getString("G_PARENT"));
        paramPacketGroup.setSchemeKey(rs.getString("G_SCHEME_KEY"));
        return paramPacketGroup;
    }

    @Override
    public void addParamPacketGroup(final ParamPacketGroup paramPacketGroup) {
        this.jdbcTemplate.execute(ADD_PARAM_PAACKET_GTOUP, (PreparedStatementCallback)new AbstractLobCreatingPreparedStatementCallback((LobHandler)new DefaultLobHandler()){

            protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                ps.setString(1, paramPacketGroup.getGuid());
                ps.setString(2, paramPacketGroup.getTitle());
                ps.setString(3, paramPacketGroup.getParent());
                ps.setString(4, paramPacketGroup.getSchemeKey());
            }
        });
    }

    @Override
    public void updateParamPacketGroup(final ParamPacketGroup paramPacketGroup) {
        this.jdbcTemplate.execute(UPDATE_PARAM_PACKET_GTOUP, (PreparedStatementCallback)new AbstractLobCreatingPreparedStatementCallback((LobHandler)new DefaultLobHandler()){

            protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                ps.setString(1, paramPacketGroup.getGuid());
                ps.setString(2, paramPacketGroup.getTitle());
                ps.setString(3, paramPacketGroup.getParent());
                ps.setString(4, paramPacketGroup.getSchemeKey());
                ps.setString(5, paramPacketGroup.getGuid());
            }
        });
    }

    @Override
    public void deleteParamPacketGroup(String guid) {
        this.jdbcTemplate.update(DELETE_PARAN_PACKET_GTOUP, new Object[]{guid});
    }

    @Override
    public ParamPacketGroup queryParamPacketGroup(String guid) {
        List paramPacketGroups = this.jdbcTemplate.query(QUERY_PARAM_PACKET_GTOUP, (RowMapper)new RowMapper<ParamPacketGroup>(){

            public ParamPacketGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
                return ParamPacketGroupDaoImpl.this.buildParamPacketGroup(rs);
            }
        }, new Object[]{guid});
        if (CollectionUtils.isEmpty(paramPacketGroups)) {
            return null;
        }
        return (ParamPacketGroup)paramPacketGroups.get(0);
    }
}

