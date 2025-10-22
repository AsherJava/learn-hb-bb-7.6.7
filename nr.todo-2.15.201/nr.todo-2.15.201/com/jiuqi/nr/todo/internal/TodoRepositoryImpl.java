/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.syntax.sql.SQLQueryException
 *  com.jiuqi.bi.syntax.sql.SQLQueryExecutor
 *  com.jiuqi.util.StringUtils
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 *  org.springframework.jdbc.core.simple.SimpleJdbcInsert
 */
package com.jiuqi.nr.todo.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.syntax.sql.SQLQueryException;
import com.jiuqi.bi.syntax.sql.SQLQueryExecutor;
import com.jiuqi.nr.todo.TodoRepository;
import com.jiuqi.nr.todo.entity.TodoPO;
import com.jiuqi.nr.todo.entity.TodoVO;
import com.jiuqi.util.StringUtils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class TodoRepositoryImpl
implements TodoRepository {
    private static final Logger logger = LoggerFactory.getLogger(TodoRepositoryImpl.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final JdbcTemplate jdbc;
    private static final String TABLE_TODO = "msg_todo";

    public TodoRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate, JdbcTemplate jdbc) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbc = jdbc;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    @Override
    public List<TodoVO> list(List<String> participantIds) {
        String sql = "select t.msgid,\n       t.param,\n       t.content,\n       t.content_clob,\n       t.createtime,\n       t.title,\n       t.appname,\n       s.actionname,\n       s.todo_type,\n       s.todoParam,\n       s.todoParam_clob\n  from msg_main t\n         inner join msg_participant p on t.msgid = p.msgid\n         inner join msg_todo s on t.msgid = s.msgid\nwhere t.type = 4\n  and p.participantid in (:participants)\n and s.completetime is null order by t.createtime desc";
        sql = this.bindParticipantParameter(sql, "participants", participantIds);
        SQLQueryExecutor executor = null;
        try {
            ArrayList<TodoVO> arrayList;
            Throwable throwable;
            Connection connection;
            block25: {
                block26: {
                    connection = this.jdbcTemplate.getJdbcTemplate().getDataSource().getConnection();
                    throwable = null;
                    executor = new SQLQueryExecutor(connection);
                    executor.open(sql);
                    ResultSet resultSet = executor.executeQuery(0, 1000);
                    ArrayList<TodoVO> todoVOList = new ArrayList<TodoVO>();
                    while (resultSet.next()) {
                        todoVOList.add(this.createTodoVO(resultSet));
                    }
                    arrayList = todoVOList;
                    if (connection == null) break block25;
                    if (throwable == null) break block26;
                    try {
                        connection.close();
                    }
                    catch (Throwable throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                    break block25;
                }
                connection.close();
            }
            return arrayList;
            catch (Throwable throwable3) {
                try {
                    try {
                        throwable = throwable3;
                        throw throwable3;
                    }
                    catch (Throwable throwable4) {
                        if (connection != null) {
                            if (throwable != null) {
                                try {
                                    connection.close();
                                }
                                catch (Throwable throwable5) {
                                    throwable.addSuppressed(throwable5);
                                }
                            } else {
                                connection.close();
                            }
                        }
                        throw throwable4;
                    }
                }
                catch (SQLQueryException | SQLException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        finally {
            try {
                if (executor != null) {
                    executor.close();
                }
            }
            catch (SQLException sQLException) {}
        }
        return null;
    }

    @Override
    public boolean save(TodoPO todoPO) {
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(Objects.requireNonNull(this.jdbcTemplate.getJdbcTemplate().getDataSource())).withTableName(TABLE_TODO);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue("msgid", (Object)todoPO.getMsgId()).addValue("tag", (Object)todoPO.getTag()).addValue("todo_type", (Object)todoPO.getTodoType()).addValue("actionname", (Object)todoPO.getActionName()).addValue("formSchemeKey", (Object)todoPO.getFormSchemeKey()).addValue("todoParam_clob", (Object)todoPO.getTodoParam());
        try {
            insertActor.execute((SqlParameterSource)parameterSource);
            return true;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public TodoVO get(String id) {
        String sql = "SELECT * FROM MSG_TODO t WHERE t.MSGID = ?";
        Optional first = this.jdbc.query(sql, (rs, row) -> this.createTodo(rs), new Object[]{id}).stream().findFirst();
        return first.orElse(null);
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM MSG_TODO WHERE MSGID = ?";
        this.jdbcTemplate.getJdbcTemplate().update(sql, new Object[]{id});
    }

    private TodoVO createTodoVO(ResultSet resultSet) throws SQLException {
        byte[] todoByte;
        TodoVO todoVO = new TodoVO();
        todoVO.setId(resultSet.getString("msgid"));
        String content = resultSet.getString("content");
        if (content == null) {
            content = resultSet.getString("content_clob");
        }
        todoVO.setContent(content);
        Timestamp timeStamp = resultSet.getTimestamp("createtime");
        todoVO.setCreateTime(timeStamp != null ? timeStamp : null);
        todoVO.setTitle(resultSet.getString("title"));
        todoVO.setActionName(resultSet.getString("actionname"));
        todoVO.setAppName(resultSet.getString("appname"));
        String raw = resultSet.getString("param");
        ObjectMapper mapper = new ObjectMapper();
        Map params = null;
        try {
            if (raw != null) {
                params = (Map)mapper.readValue(raw, Map.class);
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
        todoVO.setParams(params);
        todoVO.setType(resultSet.getString("todo_type"));
        todoVO.setTodoParam(resultSet.getString("todoParam_clob"));
        if (StringUtils.isEmpty((String)todoVO.getTodoParam()) && null != (todoByte = resultSet.getBytes("todoParam"))) {
            try {
                String str = new String(todoByte, "UTF-8");
                todoVO.setTodoParam(str);
            }
            catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return todoVO;
    }

    private TodoVO createTodo(ResultSet resultSet) throws SQLException {
        TodoVO todoVO = new TodoVO();
        todoVO.setId(resultSet.getString("msgid"));
        return todoVO;
    }

    private String bindParticipantParameter(String sql, String parameterName, List<String> participantIdList) {
        StringBuilder idSqlList = new StringBuilder();
        for (String id : participantIdList) {
            idSqlList.append("'");
            idSqlList.append(id);
            idSqlList.append("'");
            idSqlList.append(",");
        }
        idSqlList.deleteCharAt(idSqlList.length() - 1);
        return sql.replace(":" + parameterName, idSqlList.toString());
    }

    @Override
    public boolean updateCompleteTime(String messageId, String userId, LocalDateTime time) {
        String sql = "update msg_todo set completetime = :completetime where msgid = :msgid";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue("msgid", (Object)messageId).addValue("completetime", (Object)Timestamp.valueOf(time));
        return this.jdbcTemplate.update(sql, (SqlParameterSource)parameterSource) > 0;
    }

    @Override
    public boolean updateCompleteTime(List<String> formSchemeKey, LocalDateTime time) {
        String sql = "update msg_todo set completetime = :completetime where formSchemeKey in (:formSchemeKey)";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue("formSchemeKey", formSchemeKey).addValue("completetime", (Object)Timestamp.valueOf(time));
        return this.jdbcTemplate.update(sql, (SqlParameterSource)parameterSource) > 0;
    }

    @Override
    public boolean updateCompleteTimeByFormSchemeKey(String formSchemeKey, String period, LocalDateTime time) {
        String sql = "update msg_todo set completetime = :completetime where msgid like (:formSchemeKey)";
        String param = formSchemeKey + period + "%";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue("formSchemeKey", (Object)param).addValue("completetime", (Object)Timestamp.valueOf(time));
        return this.jdbcTemplate.update(sql, (SqlParameterSource)parameterSource) > 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    @Override
    public List<TodoVO> compeleteTodoList(List<String> participantIds) {
        String sql = "select t.msgid,\n       t.param,\n       t.content,\n       t.content_clob,\n       t.createtime,\n       t.title,\n       t.appname,\n       s.actionname,\n       s.todo_type,\n       s.todoParam,\n       s.todoParam_clob\n  from msg_main t\n         inner join msg_participant p on t.msgid = p.msgid\n         inner join msg_todo s on t.msgid = s.msgid\nwhere t.type = 4\n  and p.participantid in (:participants)\n and s.completetime is not null order by t.createtime desc";
        sql = this.bindParticipantParameter(sql, "participants", participantIds);
        SQLQueryExecutor executor = null;
        try {
            ArrayList<TodoVO> arrayList;
            Throwable throwable;
            Connection connection;
            block25: {
                block26: {
                    connection = this.jdbcTemplate.getJdbcTemplate().getDataSource().getConnection();
                    throwable = null;
                    executor = new SQLQueryExecutor(connection);
                    executor.open(sql);
                    ResultSet resultSet = executor.executeQuery(0, 1000);
                    ArrayList<TodoVO> todoVOList = new ArrayList<TodoVO>();
                    while (resultSet.next()) {
                        todoVOList.add(this.createTodoVO(resultSet));
                    }
                    arrayList = todoVOList;
                    if (connection == null) break block25;
                    if (throwable == null) break block26;
                    try {
                        connection.close();
                    }
                    catch (Throwable throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                    break block25;
                }
                connection.close();
            }
            return arrayList;
            catch (Throwable throwable3) {
                try {
                    try {
                        throwable = throwable3;
                        throw throwable3;
                    }
                    catch (Throwable throwable4) {
                        if (connection != null) {
                            if (throwable != null) {
                                try {
                                    connection.close();
                                }
                                catch (Throwable throwable5) {
                                    throwable.addSuppressed(throwable5);
                                }
                            } else {
                                connection.close();
                            }
                        }
                        throw throwable4;
                    }
                }
                catch (SQLQueryException | SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        finally {
            try {
                if (executor != null) {
                    executor.close();
                }
            }
            catch (SQLException sQLException) {}
        }
        return null;
    }
}

