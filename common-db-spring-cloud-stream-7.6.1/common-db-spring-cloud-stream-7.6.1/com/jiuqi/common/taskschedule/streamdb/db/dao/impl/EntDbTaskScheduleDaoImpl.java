/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  javax.annotation.Resource
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.common.taskschedule.streamdb.db.dao.impl;

import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.common.taskschedule.streamdb.db.dao.EntDbTaskScheduleDao;
import com.jiuqi.common.taskschedule.streamdb.db.enums.EntTaskState;
import com.jiuqi.common.taskschedule.streamdb.db.eo.EntTaskInfoEo;
import com.jiuqi.common.taskschedule.streamdb.db.util.EntDbServerInfoUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EntDbTaskScheduleDaoImpl
implements EntDbTaskScheduleDao {
    private static final String FIELD_STR = " ID,VER,QUEUE_NAME,MESSAGE_BODY,STATUS,CREATE_TIME,START_TIME,END_TIME,SERVE_ID,SERVE_NAME,STORE_TYPE ";
    private static final Long AUTO_INSERT_HISTORY_TIME = 604800000L;
    private static final Long AUTO_DELETE_HISTORY_TIME = 2592000000L;
    private static final int MESSAGE_BODY_MAX_LENGTH = 1000;
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private EntDbServerInfoUtil serverInfoUtil;

    @Override
    public List<EntTaskInfoEo> listQueueFirstByQueueNameList(List<String> queueNameList) {
        String querySql = "SELECT  ID,VER,QUEUE_NAME,MESSAGE_BODY,STATUS,CREATE_TIME,START_TIME,END_TIME,SERVE_ID,SERVE_NAME,STORE_TYPE  FROM ENT_TASK_INFO         WHERE STATUS = " + EntTaskState.WAITING.getValue() + " AND " + SqlBuildUtil.getStrInCondi((String)"QUEUE_NAME", queueNameList);
        return this.jdbcTemplate.query(querySql, (rs, row) -> this.getEntTaskInfoEo(rs));
    }

    @Override
    public boolean receive(String id) {
        String sql = "UPDATE ENT_TASK_INFO\n      SET SERVE_ID = ?,SERVE_NAME = ?, STATUS = " + EntTaskState.PROCESSING.getValue() + "\n    WHERE STATUS = " + EntTaskState.WAITING.getValue() + " AND ID = ? ";
        return this.jdbcTemplate.update(sql, new Object[]{this.serverInfoUtil.getServeId(), this.serverInfoUtil.getServerName(), id}) > 0;
    }

    @Override
    public void publish(EntTaskInfoEo eo) {
        if (eo.getMessageBody().length() > 1000) {
            String clobSql = "INSERT INTO ENT_TASK_INFO_CLOB ( ID, MESSAGE_BODY) values( ?,?)";
            this.jdbcTemplate.update(clobSql, new Object[]{eo.getId(), eo.getMessageBody()});
            eo.setMessageBody("");
            eo.setStoreType(0);
        }
        String insertSql = "insert into ENT_TASK_INFO \n         (  ID,VER,QUEUE_NAME,MESSAGE_BODY,STATUS,CREATE_TIME,START_TIME,END_TIME,SERVE_ID,SERVE_NAME,STORE_TYPE ) values( ?,?,?,?,?, ?,?,?,?,?,?)";
        Object[] args = new Object[]{eo.getId(), eo.getVer(), eo.getQueueName(), eo.getMessageBody(), eo.getStatus(), eo.getCreateTime(), eo.getStartTime(), eo.getEndTime(), eo.getServeId(), eo.getServeName(), eo.getStoreType()};
        this.jdbcTemplate.update(insertSql, args);
    }

    @Override
    public List<String> listFinishMessageId(List<String> sendMessageIdList) {
        String sql = " SELECT ID from ENT_TASK_INFO\n  WHERE STATUS not in ( " + EntTaskState.WAITING.getValue() + "," + EntTaskState.PROCESSING.getValue() + ")\n   AND " + SqlBuildUtil.getStrInCondi((String)"ID", sendMessageIdList);
        return this.jdbcTemplate.query(sql, (rs, row) -> rs.getString(1));
    }

    @Override
    public void updateTime(List<String> sendMessageIdList) {
        String sql = "UPDATE ENT_TASK_INFO SET VER = ? WHERE " + SqlBuildUtil.getStrInCondi((String)"id", sendMessageIdList);
        this.jdbcTemplate.update(sql, new Object[]{System.currentTimeMillis()});
    }

    @Override
    public List<EntTaskInfoEo> listAllTimeoutMessage() {
        String sql = "SELECT  ID,VER,QUEUE_NAME,MESSAGE_BODY,STATUS,CREATE_TIME,START_TIME,END_TIME,SERVE_ID,SERVE_NAME,STORE_TYPE  FROM ENT_TASK_INFO\n    WHERE STATUS = " + EntTaskState.PROCESSING.getValue() + "\n      AND VER < ? ";
        return this.jdbcTemplate.query(sql, (rs, row) -> this.getEntTaskInfoEo(rs), new Object[]{DateUtils.shifting((Date)DateUtils.now(), (int)12, (int)-5).getTime()});
    }

    @Override
    public void retry(EntTaskInfoEo timeoutMessage) {
        String sql = "UPDATE ENT_TASK_INFO\n      SET STATUS=" + EntTaskState.WAITING.getValue() + "\n    WHERE STATUS = " + EntTaskState.PROCESSING.getValue() + "\n      AND ID = ? AND VER = ? ";
        this.jdbcTemplate.update(sql, new Object[]{timeoutMessage.getId(), timeoutMessage.getVer()});
    }

    @Override
    public void updateMessageStatus(String id, EntTaskState state) {
        String sql = "UPDATE ENT_TASK_INFO\n      SET STATUS = ?, END_TIME = ? \n    WHERE  ID = ? ";
        this.jdbcTemplate.update(sql, new Object[]{state.getValue(), DateUtils.now(), id});
    }

    private EntTaskInfoEo getEntTaskInfoEo(ResultSet rs) throws SQLException {
        EntTaskInfoEo eo = new EntTaskInfoEo();
        eo.setId(rs.getString(1));
        eo.setVer(rs.getLong(2));
        eo.setQueueName(rs.getString(3));
        eo.setMessageBody(rs.getString(4));
        eo.setStatus(rs.getInt(5));
        eo.setCreateTime(rs.getDate(6));
        eo.setStartTime(rs.getDate(7));
        eo.setEndTime(rs.getDate(8));
        eo.setServeId(rs.getString(9));
        eo.setServeName(rs.getString(10));
        eo.setStoreType(rs.getInt(11));
        return eo;
    }

    @Override
    public void insertHistoryTableData(Date now) {
        StringBuilder sql = new StringBuilder();
        sql.append(" INSERT INTO ENT_TASK_INFO_HISTORY ( ID,VER,QUEUE_NAME,MESSAGE_BODY,STATUS,CREATE_TIME,START_TIME,END_TIME,SERVE_ID,SERVE_NAME,STORE_TYPE ) \n");
        sql.append(" SELECT  ID,VER,QUEUE_NAME,MESSAGE_BODY,STATUS,CREATE_TIME,START_TIME,END_TIME,SERVE_ID,SERVE_NAME,STORE_TYPE  from ENT_TASK_INFO \n");
        sql.append("  WHERE STATUS in( ?, ?, ?, ?)");
        sql.append("   AND VER < ? ");
        Object[] args = new Object[]{EntTaskState.CANCELED.getValue(), EntTaskState.FINISHED.getValue(), EntTaskState.ERROR.getValue(), EntTaskState.OVERTIME.getValue(), DateUtils.shifting((Date)now, (int)5, (int)-7).getTime()};
        this.jdbcTemplate.update(sql.toString(), args);
    }

    @Override
    public void deleteHistoryData(Date now) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM  ").append("ENT_TASK_INFO");
        sql.append(" WHERE STATUS IN( ?, ?, ?, ?)");
        sql.append("   AND VER < ? ");
        Object[] args = new Object[]{EntTaskState.CANCELED.getValue(), EntTaskState.FINISHED.getValue(), EntTaskState.ERROR.getValue(), EntTaskState.OVERTIME.getValue(), DateUtils.shifting((Date)now, (int)5, (int)-7).getTime()};
        this.jdbcTemplate.update(sql.toString(), args);
    }

    @Override
    public void deleteHistoryTableData(List<String> idList) {
        String sql = "DELETE FROM ENT_TASK_INFO_HISTORY WHERE " + SqlBuildUtil.getStrInCondi((String)"ID", idList);
        this.jdbcTemplate.update(sql);
    }

    @Override
    public List<String> listIdByUpdateTime(Date now) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ID FROM ").append("ENT_TASK_INFO_HISTORY");
        sql.append(" WHERE STATUS IN( ?, ?, ?, ?)");
        sql.append("   AND VER < ? ");
        Object[] args = new Object[]{EntTaskState.CANCELED.getValue(), EntTaskState.FINISHED.getValue(), EntTaskState.ERROR.getValue(), EntTaskState.OVERTIME.getValue(), DateUtils.shifting((Date)now, (int)5, (int)-30).getTime()};
        return this.jdbcTemplate.query(sql.toString(), (rs, row) -> rs.getString(1), args);
    }

    @Override
    public void deleteClobByIdList(List<String> idList) {
        String sql = "DELETE FROM ENT_TASK_INFO_CLOB WHERE " + SqlBuildUtil.getStrInCondi((String)"ID", idList);
        this.jdbcTemplate.update(sql);
    }

    @Override
    public void deleteErrorClobByIdList(List<String> idList) {
        String sql = "DELETE FROM ENT_TASK_MESSAGE_ERRORRESULT WHERE " + SqlBuildUtil.getStrInCondi((String)"ID", idList);
        this.jdbcTemplate.update(sql);
    }

    @Override
    public void updateErrorMessage(String id, String errorInfo) {
        String sql = "UPDATE ENT_TASK_MESSAGE_ERRORRESULT\n      SET ERRORRESULT = ? \n    WHERE  ID = ? ";
        this.jdbcTemplate.update(sql, new Object[]{errorInfo, id});
    }

    @Override
    public String getMessageBodyFormClob(String id) {
        String sql = "SELECT MESSAGE_BODY FROM ENT_TASK_INFO_CLOB WHERE ID= ? ";
        return (String)this.jdbcTemplate.query(sql, rs -> rs.next() ? rs.getString(1) : "", new Object[]{id});
    }
}

