/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.paging.IPagingSQLBuilder
 *  com.jiuqi.nr.common.db.DatabaseInstance
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 *  org.springframework.jdbc.core.simple.SimpleJdbcInsert
 */
package com.jiuqi.nr.message.manager.internal;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.paging.IPagingSQLBuilder;
import com.jiuqi.nr.common.db.DatabaseInstance;
import com.jiuqi.nr.message.manager.internal.MessageStatusDao;
import com.jiuqi.nr.message.manager.pojo.MessageStatusPO;
import com.jiuqi.nr.message.manager.pojo.PageDTO;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.util.StringUtils;

public class MessageStatusDaoImpl
implements MessageStatusDao {
    private static final Logger logger = LoggerFactory.getLogger(MessageStatusDaoImpl.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private JdbcTemplate jdbc;

    public MessageStatusDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveOrUpdate(String userId, List<String> messageIdList, int status) {
        LinkedList<String> toUpdateIds = new LinkedList<String>();
        LinkedList<String> toSaveIds = new LinkedList<String>();
        for (String messageId : messageIdList) {
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("status", (Object)status);
            parameterSource.addValue("messageId", (Object)messageId);
            parameterSource.addValue("userId", (Object)userId);
            Integer i = (Integer)this.jdbcTemplate.queryForObject("select count(*) from msg_status where msgid = :messageId and userid = :userId", (SqlParameterSource)parameterSource, Integer.class);
            if (i != null && i > 0) {
                toUpdateIds.add(messageId);
                continue;
            }
            toSaveIds.add(messageId);
        }
        if (!toUpdateIds.isEmpty()) {
            this.updateStatus(userId, toUpdateIds, status);
        }
        if (!toSaveIds.isEmpty()) {
            this.saveStatus(userId, toSaveIds, status);
        }
    }

    private void saveStatus(String userId, List<String> messageIdList, int status) {
        DataSource dataSource = this.jdbcTemplate.getJdbcTemplate().getDataSource();
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("msg_status").usingColumns(new String[]{"msgid", "userid", "status"});
        SqlParameterSource[] parameterSources = this.buildBatchArgs(userId, messageIdList, status);
        jdbcInsert.executeBatch(parameterSources);
    }

    @Override
    public void updateStatus(String userId, List<String> messageIdList, int status) {
        this.buildBatchArgs(userId, messageIdList, status);
        SqlParameterSource[] parameterSources = this.buildBatchArgs(userId, messageIdList, status);
        this.jdbcTemplate.batchUpdate("UPDATE MSG_STATUS set STATUS = :status where MSGID = :msgid and  USERID = :userid", parameterSources);
    }

    private SqlParameterSource[] buildBatchArgs(String userId, List<String> messageIdList, int status) {
        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[messageIdList.size()];
        for (int i = 0; i < messageIdList.size(); ++i) {
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("msgid", (Object)messageIdList.get(i));
            parameterSource.addValue("userid", (Object)userId);
            parameterSource.addValue("status", (Object)status);
            parameterSources[i] = parameterSource;
        }
        return parameterSources;
    }

    @Override
    public List<String> findMessageIdByUserIdAndStatus(String userId, int status, PageDTO pageDTO) {
        List<String> query = new ArrayList<String>();
        try {
            String baseSql = "SELECT MSGID FROM MSG_STATUS";
            StringBuffer sql = new StringBuffer();
            sql.append(baseSql);
            sql.append(this.buildSql(userId, status));
            IDatabase iDatabase = DatabaseInstance.getDatabase();
            IPagingSQLBuilder pagingSQLBuilder = iDatabase.createPagingSQLBuilder();
            pagingSQLBuilder.setRawSQL(sql.toString());
            String pageingSql = pagingSQLBuilder.buildSQL(pageDTO.getPageSize() * (pageDTO.getCurrentPage() - 1), pageDTO.getCurrentPage() * pageDTO.getPageSize());
            query = this.jdbcTemplate.query(pageingSql, (rs, rowNum) -> rs.getString("MSGID"));
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return query;
    }

    @Override
    public List<String> findMessageIdByUserId(String userId) {
        return this.jdbcTemplate.getJdbcOperations().queryForList("SELECT MSGID from  MSG_STATUS where USERID = ?", String.class, new Object[]{userId});
    }

    @Override
    public List<String> findUserIdByMessageId(String msgId) {
        return this.jdbcTemplate.getJdbcOperations().queryForList("SELECT USERID from  MSG_STATUS where MSGID = ?", String.class, new Object[]{msgId});
    }

    @Override
    public void save(MessageStatusPO messageStatusPO) {
        this.jdbcTemplate.getJdbcOperations().update("insert into MSG_STATUS (MSGID, USERID, STATUS) values (?, ?, ?)", new Object[]{messageStatusPO.getMsgId(), messageStatusPO.getUserId(), messageStatusPO.getStatus()});
    }

    @Override
    public void deleteById(String messageId) {
        this.jdbcTemplate.getJdbcOperations().update("delete from MSG_STATUS where MSGID = ? ", new Object[]{messageId});
    }

    private String buildSql(String userId, int status) {
        StringBuffer sql = new StringBuffer();
        sql.append(" WHERE 1 = 1 ");
        if (!StringUtils.isEmpty(userId)) {
            sql.append("   AND USERID = '" + userId + "'");
        }
        if (!StringUtils.isEmpty(status)) {
            sql.append("   AND STATUS =  " + status);
        }
        return sql.toString();
    }
}

