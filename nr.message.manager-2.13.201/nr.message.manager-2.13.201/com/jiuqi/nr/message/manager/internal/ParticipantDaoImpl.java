/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package com.jiuqi.nr.message.manager.internal;

import com.jiuqi.nr.message.manager.internal.ParticipantDao;
import com.jiuqi.nr.message.manager.pojo.ParticipantPO;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class ParticipantDaoImpl
implements ParticipantDao {
    private static final Logger logger = LoggerFactory.getLogger(ParticipantDaoImpl.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ParticipantDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<String> findMsgIdByParticipantIdAndValidTimeAndInvalidTime(List<String> participantIds, Timestamp validTime, Timestamp invalidTime) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("participantIds", participantIds);
        parameterSource.addValue("validTime", (Object)validTime);
        parameterSource.addValue("invalidTime", (Object)invalidTime);
        return this.jdbcTemplate.queryForList("select MSGID from MSG_PARTICIPANT where PARTICIPANTID in  (:participantIds) and VALIDTIME < :validTime and INVALIDTIME > :invalidTime", (SqlParameterSource)parameterSource, String.class);
    }

    @Override
    public void deleteById(String messageId) {
        this.jdbcTemplate.getJdbcOperations().update("delete from MSG_PARTICIPANT where MSGID = ?", new Object[]{messageId});
    }

    @Override
    public boolean saveAll(List<ParticipantPO> participantPOList) {
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>();
        try {
            for (ParticipantPO participantPO : participantPOList) {
                Object[] batchArg = new Object[]{participantPO.getMsgId(), participantPO.getParticipantId(), participantPO.getInvalidTime(), participantPO.getParticipantType(), participantPO.getValidTime()};
                batchArgs.add(batchArg);
            }
            this.jdbcTemplate.getJdbcOperations().batchUpdate("INSERT into MSG_PARTICIPANT (MSGID, PARTICIPANTID, INVALIDTIME, PARTICIPANTTYPE, VALIDTIME) values (?, ?, ?, ?, ?)", batchArgs);
            return true;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public List<String> findMsgIdByParticipantIdAndType(List<String> participantIds, Timestamp validTime, Timestamp invalidTime, int type) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("participantIds", participantIds);
        parameterSource.addValue("validTime", (Object)validTime);
        parameterSource.addValue("invalidTime", (Object)invalidTime);
        parameterSource.addValue("type", (Object)type);
        return this.jdbcTemplate.queryForList("select mp.MSGID from MSG_PARTICIPANT mp JOIN MSG_MAIN  mm on mp.MSGID = mm.MSGID  where mp.PARTICIPANTID in  (:participantIds) and mp.VALIDTIME < :validTime and mp.INVALIDTIME > :invalidTime and mm.type = :type ", (SqlParameterSource)parameterSource, String.class);
    }

    @Override
    public List<String> findMsgIdByParticipantId(List<String> participantIds, Timestamp validTime, Timestamp invalidTime) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("participantIds", participantIds);
        parameterSource.addValue("validTime", (Object)validTime);
        parameterSource.addValue("invalidTime", (Object)invalidTime);
        return this.jdbcTemplate.queryForList("select mp.MSGID from MSG_PARTICIPANT mp JOIN MSG_MAIN  mm on mp.MSGID = mm.MSGID  where mp.PARTICIPANTID in  (:participantIds) and mp.VALIDTIME < :validTime and mp.INVALIDTIME > :invalidTime  order by mp.validtime  desc ", (SqlParameterSource)parameterSource, String.class);
    }
}

