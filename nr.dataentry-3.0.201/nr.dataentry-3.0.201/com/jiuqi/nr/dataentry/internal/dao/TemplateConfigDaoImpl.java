/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.PreparedStatementCallback
 *  org.springframework.jdbc.core.RowCallbackHandler
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback
 *  org.springframework.jdbc.support.lob.DefaultLobHandler
 *  org.springframework.jdbc.support.lob.LobCreator
 *  org.springframework.jdbc.support.lob.LobHandler
 */
package com.jiuqi.nr.dataentry.internal.dao;

import com.jiuqi.nr.dataentry.bean.FTemplateConfig;
import com.jiuqi.nr.dataentry.bean.impl.TemplateConfigImpl;
import com.jiuqi.nr.dataentry.dao.TemplateConfigDao;
import com.jiuqi.nr.dataentry.gather.ExtendTemplateImpl;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;

@Repository
public class TemplateConfigDaoImpl
implements TemplateConfigDao {
    private static final Logger logger = LoggerFactory.getLogger(TemplateConfigDaoImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String CUSTOM_TEMPLATE = "extend_template";

    @Override
    public boolean updateTemplateConfigByCode(final TemplateConfigImpl templateConfig) {
        String sql = "UPDATE DATAENTRY_TEMPLATE T SET TEMPLATE_TITLE = ?, TEMPLATE_CONFIG = ?,UPDATE_TIME = ?, TEMPLATE_KIND = ? WHERE T.TEMPLATE_CODE = ?";
        int execute = (Integer)this.jdbcTemplate.execute(sql, (PreparedStatementCallback)new AbstractLobCreatingPreparedStatementCallback((LobHandler)new DefaultLobHandler()){

            protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                ps.setString(1, templateConfig.getTitle());
                ps.setObject(5, templateConfig.getCode());
                ps.setString(4, templateConfig.getTemplate());
                ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                ps.setString(2, templateConfig.getTemplateConfig());
            }
        });
        return execute > 0;
    }

    @Override
    public boolean updateTemplateConfig(final TemplateConfigImpl templateConfig) {
        String sql = "UPDATE DATAENTRY_TEMPLATE T SET TEMPLATE_TITLE = ?, TEMPLATE_CONFIG = ?,UPDATE_TIME = ? WHERE T.TEMPLATE_ID = ?";
        int execute = (Integer)this.jdbcTemplate.execute(sql, (PreparedStatementCallback)new AbstractLobCreatingPreparedStatementCallback((LobHandler)new DefaultLobHandler()){

            protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                ps.setString(1, templateConfig.getTitle());
                ps.setObject(4, templateConfig.getTemplateId());
                ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                ps.setString(2, templateConfig.getTemplateConfig());
            }
        });
        return execute > 0;
    }

    @Override
    public FTemplateConfig getTemplateConfigById(String templateId) {
        String sql = "SELECT T.TEMPLATE_ID,T.TEMPLATE_CODE,T.TEMPLATE_TITLE,T.TEMPLATE_CONFIG,T.TEMPLATE_KIND ,T.UPDATE_TIME FROM DATAENTRY_TEMPLATE T WHERE T.TEMPLATE_ID = ?";
        Object[] params = new Object[]{templateId};
        final TemplateConfigImpl impl = new TemplateConfigImpl();
        this.jdbcTemplate.query(sql, params, new RowCallbackHandler(){

            public void processRow(ResultSet rs) throws SQLException {
                impl.setTemplateId(rs.getString(1));
                impl.setCode(rs.getString(2));
                impl.setTitle(rs.getString(3));
                impl.setTemplate(rs.getString(5));
                impl.setUpdateTime(rs.getTimestamp(6));
                String content = rs.getString(4);
                impl.setTemplateConfig(content);
            }
        });
        return impl;
    }

    @Override
    public FTemplateConfig getTemplateConfigByCode(String code) {
        String sql = "SELECT T.TEMPLATE_ID,T.TEMPLATE_CODE,T.TEMPLATE_TITLE,T.TEMPLATE_CONFIG,T.TEMPLATE_KIND ,T.UPDATE_TIME FROM DATAENTRY_TEMPLATE T WHERE T.TEMPLATE_CODE = ?";
        Object[] params = new Object[]{code};
        final TemplateConfigImpl impl = new TemplateConfigImpl();
        this.jdbcTemplate.query(sql, params, new RowCallbackHandler(){

            public void processRow(ResultSet rs) throws SQLException {
                impl.setTemplateId(rs.getString(1));
                impl.setCode(rs.getString(2));
                impl.setTitle(rs.getString(3));
                impl.setTemplate(rs.getString(5));
                impl.setUpdateTime(rs.getTimestamp(6));
                String content = rs.getString(4);
                impl.setTemplateConfig(content);
            }
        });
        return impl;
    }

    @Override
    public boolean addTemplate_old(final TemplateConfigImpl templateConfig) {
        String sql = "INSERT INTO DATAENTRY_TEMPLATE(TEMPLATE_ID, TEMPLATE_CODE,TEMPLATE_TITLE,TEMPLATE_CONFIG,TEMPLATE_KIND) VALUES (?,?,?,?,?)";
        byte[] bytes = null;
        try {
            bytes = templateConfig.getTemplateConfig().getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        final ByteArrayInputStream contentStream = new ByteArrayInputStream(bytes);
        final int contentLength = bytes.length;
        int execute = (Integer)this.jdbcTemplate.execute(sql, (PreparedStatementCallback)new AbstractLobCreatingPreparedStatementCallback((LobHandler)new DefaultLobHandler()){

            protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                ps.setObject(1, templateConfig.getTemplateId());
                ps.setString(2, templateConfig.getCode());
                ps.setString(3, templateConfig.getTitle());
                lobCreator.setBlobAsBinaryStream(ps, 4, (InputStream)contentStream, contentLength);
                ps.setString(5, templateConfig.getTemplate());
            }
        });
        return execute > 0;
    }

    @Override
    public boolean addTemplate(final TemplateConfigImpl templateConfig) {
        String sql = "INSERT INTO DATAENTRY_TEMPLATE(TEMPLATE_ID, TEMPLATE_CODE,TEMPLATE_TITLE,TEMPLATE_CONFIG,TEMPLATE_KIND,UPDATE_TIME) VALUES (?,?,?,?,?,?)";
        Object bytes = null;
        int execute = (Integer)this.jdbcTemplate.execute(sql, (PreparedStatementCallback)new AbstractLobCreatingPreparedStatementCallback((LobHandler)new DefaultLobHandler()){

            protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                ps.setObject(1, templateConfig.getTemplateId());
                ps.setString(2, templateConfig.getCode());
                ps.setString(3, templateConfig.getTitle());
                ps.setString(4, templateConfig.getTemplateConfig());
                ps.setString(5, templateConfig.getTemplate());
                ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            }
        });
        return execute > 0;
    }

    @Override
    public boolean verifyCode(TemplateConfigImpl templateConfig) {
        String sql = "SELECT COUNT(*) FROM DATAENTRY_TEMPLATE T WHERE T.TEMPLATE_CODE = ?";
        Object[] params = new Object[]{templateConfig.getCode()};
        int count = (Integer)this.jdbcTemplate.queryForObject(sql, params, Integer.class);
        boolean noRepeat = true;
        if (count > 0) {
            noRepeat = false;
        }
        return noRepeat;
    }

    @Override
    public boolean updateCode(final TemplateConfigImpl templateConfig) {
        String sql = "UPDATE DATAENTRY_TEMPLATE T SET  TEMPLATE_CONFIG = ? ,TEMPLATE_CODE = ?,TEMPLATE_TITLE = ? ,UPDATE_TIME = ?WHERE T.TEMPLATE_ID = ?";
        int execute = (Integer)this.jdbcTemplate.execute(sql, (PreparedStatementCallback)new AbstractLobCreatingPreparedStatementCallback((LobHandler)new DefaultLobHandler()){

            protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                ps.setString(2, templateConfig.getCode());
                ps.setString(3, templateConfig.getTitle());
                ps.setObject(5, templateConfig.getTemplateId());
                ps.setString(1, templateConfig.getTemplateConfig());
                ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            }
        });
        return execute > 0;
    }

    @Override
    public List<TemplateConfigImpl> getAllTemplateConfig() {
        String sql = "SELECT * FROM DATAENTRY_TEMPLATE T ";
        List impls = this.jdbcTemplate.query(sql, (RowMapper)new TemplateConfigImplRowMapper());
        return impls;
    }

    @Override
    public void deleteTemplateConfig(String templateId) {
        String sql = "DELETE FROM DATAENTRY_TEMPLATE WHERE TEMPLATE_ID = ?";
        Object[] params = new Object[]{templateId};
        this.jdbcTemplate.update(sql, params);
    }

    @Override
    public List<TemplateConfigImpl> getMiniTemplateConfig() {
        String sql = "SELECT * FROM DATAENTRY_TEMPLATE T WHERE T.TEMPLATE_KIND = 'miniTemplate'";
        ArrayList<TemplateConfigImpl> impls = new ArrayList<TemplateConfigImpl>();
        List queryForList = this.jdbcTemplate.queryForList(sql);
        for (Map map : queryForList) {
            TemplateConfigImpl impl = new TemplateConfigImpl();
            impl.setTemplateId(map.get("TEMPLATE_ID").toString());
            impl.setCode(map.get("TEMPLATE_CODE").toString());
            impl.setTitle(map.get("TEMPLATE_TITLE").toString());
            impl.setTemplate(map.get("TEMPLATE_KIND") != null ? map.get("TEMPLATE_KIND").toString() : null);
            impls.add(impl);
        }
        return impls;
    }

    @Override
    public void deleteTemplateByKind(String kind) {
        StringBuilder delSql = new StringBuilder();
        delSql.append("delete from  ").append("DATAENTRY_TEMPLATE");
        delSql.append(" where ").append("TEMPLATE_KIND").append("=?");
        this.jdbcTemplate.update(delSql.toString(), new Object[]{kind});
    }

    @Override
    public List<ExtendTemplateImpl> getExtendTemplateImpls() {
        StringBuilder querySql = new StringBuilder();
        querySql.append("select * from ").append(CUSTOM_TEMPLATE);
        return this.jdbcTemplate.query(querySql.toString(), (rs, rowNum) -> this.fromatResult(rs, rowNum));
    }

    @Override
    public boolean addExtendTemplate(ExtendTemplateImpl template) {
        StringBuilder insertSql = new StringBuilder();
        Object[] args = new Object[]{template.getCode(), template.getTitle(), template.getDesc(), template.getContent()};
        insertSql.append("insert into ").append(CUSTOM_TEMPLATE);
        insertSql.append(" values (?,?,?,?)");
        int i = this.jdbcTemplate.update(insertSql.toString(), args);
        return i > 0;
    }

    @Override
    public boolean updateExtendTemplate(ExtendTemplateImpl extendTemplate) {
        StringBuilder updateSql = new StringBuilder();
        Object[] args = new Object[]{extendTemplate.getTitle(), extendTemplate.getDesc(), extendTemplate.getCode()};
        updateSql.append("update ").append(CUSTOM_TEMPLATE);
        updateSql.append(" set ");
        updateSql.append("extend_title = ? ").append(",");
        updateSql.append("extend_desc = ?");
        updateSql.append(" where extend_code = ?");
        int i = this.jdbcTemplate.update(updateSql.toString(), args);
        return i > 0;
    }

    @Override
    public boolean deleteExtendTemplate(String code) {
        StringBuilder delSql = new StringBuilder();
        delSql.append("delete from  ").append(CUSTOM_TEMPLATE);
        delSql.append(" where ").append("extend_code").append("=?");
        int i = this.jdbcTemplate.update(delSql.toString(), new Object[]{code});
        return i > 0;
    }

    private ExtendTemplateImpl fromatResult(ResultSet rs, int rowNum) throws SQLException {
        ExtendTemplateImpl extendTemplate = new ExtendTemplateImpl();
        extendTemplate.setCode(rs.getString("extend_code"));
        extendTemplate.setTitle(rs.getString("extend_title"));
        extendTemplate.setDesc(rs.getString("extend_desc"));
        extendTemplate.setContent(rs.getString("extend_content"));
        return extendTemplate;
    }

    @Override
    public ExtendTemplateImpl getExtendTemplateImpl(String code) {
        StringBuilder querySql = new StringBuilder();
        querySql.append("select *  from ").append(CUSTOM_TEMPLATE);
        querySql.append(" where ").append("extend_code").append("=?");
        return this.jdbcTemplate.query(querySql.toString(), (rs, rowNum) -> this.fromatResult(rs, rowNum), new Object[]{code}).stream().findFirst().orElse(null);
    }

    class TemplateConfigImplRowMapper
    implements RowMapper<TemplateConfigImpl> {
        TemplateConfigImplRowMapper() {
        }

        public TemplateConfigImpl mapRow(ResultSet rs, int rowNum) throws SQLException {
            TemplateConfigImpl templateConfig = new TemplateConfigImpl();
            templateConfig.setTemplateId(rs.getString("TEMPLATE_ID"));
            templateConfig.setCode(rs.getString("TEMPLATE_CODE"));
            templateConfig.setTitle(rs.getString("TEMPLATE_TITLE"));
            templateConfig.setTemplate(rs.getString("TEMPLATE_KIND"));
            templateConfig.setUpdateTime(rs.getTimestamp("UPDATE_TIME"));
            return templateConfig;
        }
    }
}

