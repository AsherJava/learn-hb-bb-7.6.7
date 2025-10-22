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
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.message.manager.internal;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.paging.IPagingSQLBuilder;
import com.jiuqi.nr.common.db.DatabaseInstance;
import com.jiuqi.nr.message.manager.internal.MessageMainDao;
import com.jiuqi.nr.message.manager.pojo.MessageMainPO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class MessageMainDaoImpl
implements MessageMainDao {
    private static final Logger logger = LoggerFactory.getLogger(MessageMainDaoImpl.class);
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private MessageMainPO createEntity(ResultSet rs) throws SQLException {
        MessageMainPO messageMainPO = new MessageMainPO();
        messageMainPO.setParam(rs.getString("param"));
        messageMainPO.setMsgId(rs.getString("msgid"));
        messageMainPO.setInvalidTime(rs.getTimestamp("invalidtime"));
        messageMainPO.setCreateTime(rs.getTimestamp("createtime"));
        messageMainPO.setYear(rs.getInt("year_"));
        messageMainPO.setValidTime(rs.getTimestamp("validtime"));
        messageMainPO.setAppName(rs.getString("appname"));
        String content = rs.getString("content");
        if (content == null) {
            content = rs.getString("content_clob");
        }
        messageMainPO.setContent(content);
        messageMainPO.setCreateTime(rs.getTimestamp("createtime"));
        messageMainPO.setCreateUser(rs.getString("createuser"));
        messageMainPO.setType(rs.getInt("type"));
        messageMainPO.setSticky(rs.getBoolean("issticky"));
        messageMainPO.setTitle(rs.getString("title"));
        return messageMainPO;
    }

    @Override
    public List<MessageMainPO> findByIds(List<String> messageIdList, int currPage, int pageSize) {
        List<MessageMainPO> query = new ArrayList<MessageMainPO>();
        try {
            String baseSql = "SELECT * FROM MSG_MAIN";
            IDatabase iDatabase = DatabaseInstance.getDatabase();
            IPagingSQLBuilder pagingSQLBuilder = iDatabase.createPagingSQLBuilder();
            String sql = baseSql + this.buildSql(messageIdList) + " ORDER BY VALIDTIME  DESC ";
            pagingSQLBuilder.setRawSQL(sql);
            String pageingSql = pagingSQLBuilder.buildSQL((currPage - 1) * pageSize, currPage * pageSize);
            query = this.namedParameterJdbcTemplate.query(pageingSql, (rs, rowNum) -> this.createEntity(rs));
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return query;
    }

    @Override
    public Optional<MessageMainPO> findById(String messageId) {
        return this.namedParameterJdbcTemplate.getJdbcOperations().query("select * from msg_main m where m.MSGID = ?", (rs, row) -> this.createEntity(rs), new Object[]{messageId}).stream().findFirst();
    }

    @Override
    public void deleteById(String messageId) {
        this.namedParameterJdbcTemplate.getJdbcOperations().update("delete from msg_main  where MSGID = ?", new Object[]{messageId});
    }

    @Override
    @Transactional
    public boolean save(MessageMainPO messageMainPO) {
        if (messageMainPO == null) {
            messageMainPO = new MessageMainPO();
            messageMainPO.setMsgId(UUID.randomUUID().toString());
        }
        try {
            this.namedParameterJdbcTemplate.getJdbcOperations().update("INSERT INTO msg_main (msgid, appname, content_clob, createtime, createuser, invalidtime, issticky, param, title, type, validtime, year_) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[]{messageMainPO.getMsgId(), messageMainPO.getAppName(), messageMainPO.getContent(), messageMainPO.getCreateTime(), messageMainPO.getCreateUser(), messageMainPO.getInvalidTime(), messageMainPO.isSticky() ? 1 : 0, messageMainPO.getParam(), messageMainPO.getTitle(), messageMainPO.getType(), messageMainPO.getValidTime(), messageMainPO.getYear()});
            return true;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public List<MessageMainPO> findByIdsAndType(List<String> messageIdList, int type) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("messageIds", messageIdList);
        parameterSource.addValue("type", (Object)type);
        return this.namedParameterJdbcTemplate.query("select * from MSG_MAIN m where m.MSGID in (:messageIds) and m.type = :type", (SqlParameterSource)parameterSource, (rs, row) -> this.createEntity(rs));
    }

    private String buildSql(List<String> messageIdList) {
        StringBuilder sql = new StringBuilder();
        sql.append(" WHERE 1 = 1 ");
        if (messageIdList != null && messageIdList.size() > 0) {
            StringBuilder str = new StringBuilder();
            for (String id : messageIdList) {
                str.append("'");
                str.append(id);
                str.append("',");
            }
            sql.append(" AND MSGID IN ( ").append(str.substring(0, str.length() - 1)).append(" )");
        }
        return sql.toString();
    }
}

