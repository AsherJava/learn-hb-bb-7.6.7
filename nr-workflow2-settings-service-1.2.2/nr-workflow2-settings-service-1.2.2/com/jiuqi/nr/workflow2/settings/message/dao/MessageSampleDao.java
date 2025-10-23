/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.events.executor.msg.enumeration.MessageType
 *  com.jiuqi.nr.workflow2.todo.utils.NamedParameterSqlBuilder
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.JdbcOperations
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package com.jiuqi.nr.workflow2.settings.message.dao;

import com.jiuqi.nr.workflow2.events.executor.msg.enumeration.MessageType;
import com.jiuqi.nr.workflow2.settings.message.domain.MessageSampleDO;
import com.jiuqi.nr.workflow2.settings.message.domain.MessageSampleDOImpl;
import com.jiuqi.nr.workflow2.settings.message.dto.MessageSampleSaveAsContext;
import com.jiuqi.nr.workflow2.todo.utils.NamedParameterSqlBuilder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class MessageSampleDao {
    private static final String TABLE_NAME = "NR_MESSAGE_SAMPLE";
    private static final String COLUMN_ID = "MS_ID";
    private static final String COLUMN_TYPE = "MS_TYPE";
    private static final String COLUMN_ACTION_CODE = "MS_ACTION_CODE";
    private static final String COLUMN_TITLE = "MS_TITLE";
    private static final String COLUMN_SUBJECT = "MS_SUBJECT";
    private static final String COLUMN_CONTENT = "MS_CONTENT";
    private static final String COLUMN_CREATE_TIME = "MS_CREATE_TIME";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<MessageSampleDO> queryMessageSample(String filterType, String filterActionCode) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.selectSQL(new String[]{COLUMN_ID, COLUMN_TYPE, COLUMN_ACTION_CODE, COLUMN_TITLE, COLUMN_SUBJECT, COLUMN_CONTENT, COLUMN_CREATE_TIME});
        sqlBuilder.adaptiveWhere();
        MapSqlParameterSource source = new MapSqlParameterSource();
        if (filterType != null && !filterType.equals(MessageType.ALL.code)) {
            sqlBuilder.and().andColumn(COLUMN_TYPE);
            source.addValue(COLUMN_TYPE, (Object)filterType);
        }
        if (filterActionCode != null && !filterActionCode.equals(MessageType.ALL.code)) {
            sqlBuilder.and().andColumn(COLUMN_ACTION_CODE);
            source.addValue(COLUMN_ACTION_CODE, (Object)filterActionCode);
        }
        sqlBuilder.orderByDesc(COLUMN_CREATE_TIME);
        return this.executeQuery(sqlBuilder.toString(), source);
    }

    public MessageSampleDO queryMessageSampleByTitle(String title) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.selectSQL(new String[]{COLUMN_ID, COLUMN_TYPE, COLUMN_ACTION_CODE, COLUMN_TITLE, COLUMN_SUBJECT, COLUMN_CONTENT, COLUMN_CREATE_TIME});
        sqlBuilder.andWhere(new String[]{COLUMN_TITLE});
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(COLUMN_TITLE, (Object)title);
        return this.executeQuerySingle(sqlBuilder.toString(), source);
    }

    public boolean addMessageSample(MessageSampleSaveAsContext context) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.insertSQL(new String[]{COLUMN_ID, COLUMN_TYPE, COLUMN_ACTION_CODE, COLUMN_TITLE, COLUMN_SUBJECT, COLUMN_CONTENT, COLUMN_CREATE_TIME});
        MapSqlParameterSource source = this.buildInsertMapSource(context);
        return this.executeUpdate(sqlBuilder.toString(), source) > 0;
    }

    public boolean batchAddMessageSample(List<MessageSampleSaveAsContext> contexts) {
        int[] operateResult;
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.insertSQL(new String[]{COLUMN_ID, COLUMN_TYPE, COLUMN_ACTION_CODE, COLUMN_TITLE, COLUMN_SUBJECT, COLUMN_CONTENT, COLUMN_CREATE_TIME});
        MapSqlParameterSource[] batchUpdateMapSource = new MapSqlParameterSource[contexts.size()];
        for (int i = 0; i < contexts.size(); ++i) {
            batchUpdateMapSource[i] = this.buildInsertMapSource(contexts.get(i));
        }
        for (int num : operateResult = this.executeBatchUpdate(sqlBuilder.toString(), batchUpdateMapSource)) {
            if (num >= 1) continue;
            return false;
        }
        return true;
    }

    public boolean deleteMessageSample(String id) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.deleteSQL().andWhere(new String[]{COLUMN_ID});
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(COLUMN_ID, (Object)id);
        return this.executeUpdate(sqlBuilder.toString(), source) > 0;
    }

    public boolean clearMessageSample() {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.deleteSQL();
        return this.executeUpdate(sqlBuilder.toString(), new MapSqlParameterSource()) > 0;
    }

    private MessageSampleDO executeQuerySingle(String sql, MapSqlParameterSource source) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return (MessageSampleDO)template.query(sql, (SqlParameterSource)source, (ResultSetExtractor)new ResultSetExtractor<MessageSampleDO>(){

            public MessageSampleDO extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (!rs.next()) {
                    return null;
                }
                MessageSampleDOImpl messageSampleDO = new MessageSampleDOImpl();
                messageSampleDO.setId(rs.getString(MessageSampleDao.COLUMN_ID));
                messageSampleDO.setType(rs.getString(MessageSampleDao.COLUMN_TYPE));
                messageSampleDO.setActionCode(rs.getString(MessageSampleDao.COLUMN_ACTION_CODE));
                messageSampleDO.setTitle(rs.getString(MessageSampleDao.COLUMN_TITLE));
                messageSampleDO.setSubject(rs.getString(MessageSampleDao.COLUMN_SUBJECT));
                messageSampleDO.setContent(rs.getString(MessageSampleDao.COLUMN_CONTENT));
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(rs.getDate(MessageSampleDao.COLUMN_CREATE_TIME));
                messageSampleDO.setCreateTime(calendar);
                return messageSampleDO;
            }
        });
    }

    private List<MessageSampleDO> executeQuery(String sql, MapSqlParameterSource source) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.query(sql, (SqlParameterSource)source, this::mapRow);
    }

    private int executeUpdate(String sql, MapSqlParameterSource source) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.update(sql, (SqlParameterSource)source);
    }

    private int[] executeBatchUpdate(String sql, MapSqlParameterSource[] sources) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.batchUpdate(sql, (SqlParameterSource[])sources);
    }

    private MessageSampleDO mapRow(ResultSet rs, int rowIdx) throws SQLException {
        MessageSampleDOImpl messageSampleDO = new MessageSampleDOImpl();
        messageSampleDO.setId(rs.getString(COLUMN_ID));
        messageSampleDO.setType(rs.getString(COLUMN_TYPE));
        messageSampleDO.setActionCode(rs.getString(COLUMN_ACTION_CODE));
        messageSampleDO.setTitle(rs.getString(COLUMN_TITLE));
        messageSampleDO.setSubject(rs.getString(COLUMN_SUBJECT));
        messageSampleDO.setContent(rs.getString(COLUMN_CONTENT));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(rs.getDate(COLUMN_CREATE_TIME));
        messageSampleDO.setCreateTime(calendar);
        return messageSampleDO;
    }

    private MapSqlParameterSource buildInsertMapSource(MessageSampleSaveAsContext context) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(COLUMN_ID, (Object)(context.getId() == null || context.getId().isEmpty() ? UUID.randomUUID().toString() : context.getId()));
        source.addValue(COLUMN_TYPE, (Object)context.getType());
        source.addValue(COLUMN_ACTION_CODE, (Object)context.getActionCode());
        source.addValue(COLUMN_TITLE, (Object)context.getTitle());
        source.addValue(COLUMN_SUBJECT, (Object)context.getSubject());
        source.addValue(COLUMN_CONTENT, (Object)context.getContent());
        source.addValue(COLUMN_CREATE_TIME, (Object)Calendar.getInstance().getTime());
        return source;
    }
}

