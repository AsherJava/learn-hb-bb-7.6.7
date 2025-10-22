/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.QueueItem
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.np.asynctask.impl.dao;

import com.jiuqi.np.asynctask.QueueItem;
import com.jiuqi.np.asynctask.dao.DBQueueDao;
import com.jiuqi.np.asynctask.impl.QueueItemImpl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class DBQueueDaoImpl
implements DBQueueDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final String SYSTEM_NAME;
    static final String FIELD_NAME_INSERT = "id_, task_id, taskpool_type, priority, join_time, system_name";
    static final RowMapper<QueueItem> OBJECT_MAPPER = new RowMapper<QueueItem>(){

        public QueueItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            int colIndex = 0;
            QueueItemImpl queueItem = new QueueItemImpl();
            queueItem.setId(rs.getString(++colIndex));
            queueItem.setTaskId(rs.getString(++colIndex));
            return queueItem;
        }
    };

    public DBQueueDaoImpl(String serverName) {
        this.SYSTEM_NAME = serverName;
    }

    @Override
    public String receive(String queueName, String serveCode) {
        StringBuilder sql = new StringBuilder();
        sql.append("select id_, task_id from ");
        sql.append(queueName);
        sql.append(" where system_name = '" + this.SYSTEM_NAME + "'");
        sql.append(" order by priority desc, join_time asc");
        ArrayList args = new ArrayList();
        List list = this.jdbcTemplate.query(sql.toString(), args.toArray(), OBJECT_MAPPER);
        for (int count = 0; list.size() > count; ++count) {
            QueueItem queueItem = (QueueItem)list.get(count);
            String id = queueItem.getId();
            String taskId = queueItem.getTaskId();
            StringBuilder lockSql = new StringBuilder("delete from " + queueName);
            lockSql.append(" where id_ = ? ");
            Object[] args_ = new Object[]{id};
            int update = this.jdbcTemplate.update(lockSql.toString(), args_);
            if (update <= 0) continue;
            return taskId;
        }
        return null;
    }

    @Override
    public void publishSimpleQueue(String taskId, String taskPoolType, Integer priority) {
        String sql = "insert into np_asynctask_simple_queue ( id_, task_id, taskpool_type, priority, join_time, system_name ) values(?,?,?,?,?,?)";
        Object[] args = new Object[]{UUID.randomUUID().toString(), taskId, taskPoolType, priority, Timestamp.from(Instant.now()), this.SYSTEM_NAME};
        this.jdbcTemplate.update(sql, args);
    }

    @Override
    public void publishSplitQueue(String taskId, String taskPoolType, Integer priority) {
        String sql = "insert into np_asynctask_split_queue ( id_, task_id, taskpool_type, priority, join_time, system_name ) values(?,?,?,?,?,?)";
        Object[] args = new Object[]{UUID.randomUUID().toString(), taskId, taskPoolType, priority, Timestamp.from(Instant.now()), this.SYSTEM_NAME};
        this.jdbcTemplate.update(sql, args);
    }
}

