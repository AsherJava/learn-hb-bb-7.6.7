/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.GetClobDataUtil
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package com.jiuqi.np.message.internal;

import com.jiuqi.np.definition.common.GetClobDataUtil;
import com.jiuqi.np.message.constants.MessageHandleStateEnum;
import com.jiuqi.np.message.internal.MessagePacketDao;
import com.jiuqi.np.message.pojo.MessagePacketPO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class MessagePacketDaoImpl
implements MessagePacketDao {
    private final NamedParameterJdbcTemplate template;

    public MessagePacketDaoImpl(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    private MessagePacketPO createEntity(ResultSet rs) throws SQLException {
        MessagePacketPO messagePacketPO = new MessagePacketPO();
        messagePacketPO.setState(rs.getInt("state"));
        messagePacketPO.setId(rs.getString("id"));
        messagePacketPO.setLatestHandleTime(rs.getTimestamp("latesthandletime"));
        messagePacketPO.setMsgBody(GetClobDataUtil.getClobFieldData((ResultSet)rs, (String)"msgbody"));
        messagePacketPO.setSendTime(rs.getTimestamp("sendtime"));
        messagePacketPO.setThreadId(rs.getInt("threadid"));
        messagePacketPO.setTitle(rs.getString("title"));
        messagePacketPO.setType(rs.getInt("type"));
        return messagePacketPO;
    }

    @Override
    public List<MessagePacketPO> findByState(Integer state) {
        String querySql = "SELECT * FROM MSG_PACKET WHERE STATE = ?";
        return this.template.getJdbcOperations().query(querySql, (rs, row) -> this.createEntity(rs), new Object[]{state});
    }

    @Override
    public void save(MessagePacketPO messagePacketPO) {
        String insertSql = "insert into MSG_PACKET (ID, LATESTHANDLETIME, MSGBODY, SENDTIME, STATE, THREADID, TITLE, TYPE) values (?, ?, ?, ?, ?, ?, ?, ?)";
        this.template.getJdbcOperations().update(insertSql, new Object[]{messagePacketPO.getId(), messagePacketPO.getLatestHandleTime(), messagePacketPO.getMsgBody(), messagePacketPO.getSendTime(), messagePacketPO.getState(), messagePacketPO.getThreadId(), messagePacketPO.getTitle(), messagePacketPO.getType()});
    }

    @Override
    public void batchUpdateState(List<String> messagePacketIds, MessageHandleStateEnum stateEnum, Timestamp latestHandleTime) {
        int chunkSize = 500;
        AtomicInteger counter = new AtomicInteger();
        Collection<List<String>> chunkedMessageIds = messagePacketIds.stream().collect(Collectors.groupingBy(e -> counter.getAndIncrement() / chunkSize)).values();
        String updateSql = "update MSG_PACKET set state = :state, latestHandleTime = :latestHandleTime where id in (:messagePacketIds)";
        for (List<String> chunkedMessageId : chunkedMessageIds) {
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("state", (Object)stateEnum.getNum());
            parameterSource.addValue("messagePacketIds", chunkedMessageId);
            parameterSource.addValue("latestHandleTime", (Object)latestHandleTime);
            this.template.update(updateSql, (SqlParameterSource)parameterSource);
        }
    }

    @Override
    public MessagePacketPO get(String id) {
        String querySql = "SELECT * FROM MSG_PACKET WHERE ID = ?";
        Optional first = this.template.getJdbcOperations().query(querySql, (rs, row) -> this.createEntity(rs), new Object[]{id}).stream().findFirst();
        return first.orElse(null);
    }
}

