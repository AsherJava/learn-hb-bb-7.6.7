/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.paging.IPagingSQLBuilder
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.util.StringUtils
 *  org.apache.ibatis.jdbc.SQL
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 */
package com.jiuqi.nr.todo.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.paging.IPagingSQLBuilder;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.todo.TodoRepository;
import com.jiuqi.nr.todo.entity.TodoPO;
import com.jiuqi.nr.todo.entity.TodoVO;
import com.jiuqi.util.StringUtils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class TodoRepositoryImplNew
implements TodoRepository {
    private static final Logger logger = LoggerFactory.getLogger(TodoRepositoryImplNew.class);
    private final JdbcTemplate jdbc;
    private static final String TABLE_TODO = "msg_todo";
    private static final String TABLE_TODO_LANG = "msg_todo_lang";
    private static final String MSGID = "msgid";
    private static final String TAG = "tag";
    private static final String TODO_TYPE = "todo_type";
    private static final String ACTION_NAME = "actionname";
    private static final String FORMSCHEMEKEY = "formschemekey";
    private static final String TODOPARAM_CLOB = "todoparam_clob";
    private static final String ACTION_NAME_LANG = "actionName_lang";
    private static final String TITLE_LANG = "title_lang";
    private static final String CONTENT_LANG = "content_lang";
    private static final String PARAM_LANG = "param_lang";
    private static final String LANG_TYPE = "lang_type";
    private static final String FORMSCHEME_KEY = "FORMSCHEME_KEY";
    private static final String COMPLETETIME = "completetime";

    public TodoRepositoryImplNew(NamedParameterJdbcTemplate jdbcTemplate, JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<TodoVO> list(List<String> participantIds) {
        String sql = this.getSql();
        sql = this.bindParticipantParameter(sql, "participants", participantIds);
        ArrayList<TodoVO> todoVOList = new ArrayList<TodoVO>();
        DataSource dataSource = this.jdbc.getDataSource();
        Assert.notNull((Object)dataSource, "dataSource must not be null.");
        try (Connection connection = dataSource.getConnection();){
            IDatabase iDatabase = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            IPagingSQLBuilder sqlBuilder = iDatabase.createPagingSQLBuilder();
            sqlBuilder.setRawSQL(sql);
            sql = sqlBuilder.buildSQL(0, 1000);
            this.jdbc.query(sql, rs -> todoVOList.add(this.createTodoVO(rs)));
        }
        catch (Exception e) {
            throw new RuntimeException("\u67e5\u8be2\u5931\u8d25", e);
        }
        return todoVOList;
    }

    private String getSql() {
        StringBuffer sb = new StringBuffer();
        sb.append("select t.msgid,t.param,t.content,t.content_clob, t.createtime,t.title,t.appname, s.actionname,s.todo_type,s.todoParam,s.todoParam_clob");
        if (!this.getLanguage()) {
            sb.append(", ml.content_lang,ml.actionName_lang,ml.title_lang, ml.param_lang");
        }
        sb.append(" from msg_main t").append(" inner join msg_participant p on t.msgid = p.msgid ").append(" inner join msg_todo s on t.msgid = s.msgid ");
        if (!this.getLanguage()) {
            sb.append(" inner join msg_todo_lang ml on t.msgid = ml.msgid");
        }
        sb.append(" where t.type = 4 and p.participantid in (:participants) and s.completetime is null ").append(" order by t.createtime desc ");
        return sb.toString();
    }

    @Override
    public boolean save(TodoPO todoPO) {
        try {
            String insertSql = String.format("insert into %s (%s,%s,%s,%s,%s,%s) values (?,?,?,?,?,?)", TABLE_TODO, MSGID, TAG, TODO_TYPE, ACTION_NAME, FORMSCHEMEKEY, TODOPARAM_CLOB);
            this.jdbc.update(insertSql, new Object[]{todoPO.getMsgId(), todoPO.getTag(), todoPO.getTodoType(), todoPO.getActionName(), todoPO.getFormSchemeKey(), todoPO.getTodoParam()});
            todoPO.setLangType("en");
            this.saveLang(todoPO);
            return true;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    private void saveLang(TodoPO todoPO) {
        TodoVO todoLang = this.getTodoLang(todoPO.getMsgId());
        if (todoLang == null || todoLang.getId() == null) {
            try {
                String insertSql = String.format("insert into %s (%s,%s,%s,%s,%s,%s,%s) values (?,?,?,?,?,?,?)", TABLE_TODO_LANG, MSGID, ACTION_NAME_LANG, TITLE_LANG, CONTENT_LANG, PARAM_LANG, LANG_TYPE, FORMSCHEME_KEY);
                this.jdbc.update(insertSql, new Object[]{todoPO.getMsgId(), todoPO.getOtherLangActionName(), todoPO.getOtherTitle(), todoPO.getOtherContent(), todoPO.getOtherParam(), todoPO.getLangType(), todoPO.getFormSchemeKey()});
            }
            catch (Exception e) {
                logger.error("\u65b0\u589e\u6570\u636e\u91cd\u590d,msgId\u4e3a" + todoPO.getMsgId());
            }
        }
    }

    public TodoVO getTodoLang(String id) {
        String sql = "SELECT * FROM msg_todo_lang t WHERE t.MSGID = ?";
        Optional first = this.jdbc.query(sql, (rs, row) -> this.createTodo(rs), new Object[]{id}).stream().findFirst();
        return first.orElse(null);
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
        this.jdbc.update(sql, new Object[]{id});
    }

    private TodoVO createTodoVO(ResultSet resultSet) throws SQLException {
        String content;
        byte[] todoByte;
        Object todoParamObj;
        TodoVO todoVO = new TodoVO();
        todoVO.setId(resultSet.getString(MSGID));
        Timestamp timeStamp = resultSet.getTimestamp("createtime");
        todoVO.setCreateTime(timeStamp != null ? timeStamp : null);
        todoVO.setAppName(resultSet.getString("appname"));
        String raw = resultSet.getString("param");
        if (!this.getLanguage()) {
            raw = resultSet.getString(PARAM_LANG);
        }
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
        todoVO.setType(resultSet.getString(TODO_TYPE));
        todoVO.setTodoParam(resultSet.getString("todoParam_clob"));
        if (StringUtils.isEmpty((String)todoVO.getTodoParam()) && (todoParamObj = resultSet.getObject("todoParam")) != null && todoParamObj instanceof Blob && null != (todoByte = resultSet.getBytes("todoParam"))) {
            try {
                String str = new String(todoByte, "UTF-8");
                todoVO.setTodoParam(str);
            }
            catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage(), e);
            }
        }
        if ((content = resultSet.getString("content")) == null) {
            content = resultSet.getString("content_clob");
        }
        String title = resultSet.getString("title");
        String actionName = resultSet.getString(ACTION_NAME);
        if (!this.getLanguage()) {
            content = resultSet.getString(CONTENT_LANG);
            title = resultSet.getString(TITLE_LANG);
            actionName = resultSet.getString(ACTION_NAME_LANG);
        }
        todoVO.setContent(content);
        todoVO.setTitle(title);
        todoVO.setActionName(actionName);
        return todoVO;
    }

    private TodoVO createTodo(ResultSet resultSet) throws SQLException {
        TodoVO todoVO = new TodoVO();
        todoVO.setId(resultSet.getString(MSGID));
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
        try {
            String sql = String.format("update %s set %s=? where %s like ?", TABLE_TODO, COMPLETETIME, MSGID);
            Object[] values = new Object[]{Timestamp.valueOf(time), messageId};
            int update = this.jdbc.update(sql, values);
            String conditionSql = String.format("%s = ?", MSGID);
            String sql0 = ((SQL)((SQL)new SQL().DELETE_FROM(TABLE_TODO_LANG)).WHERE(conditionSql)).toString();
            int updateMsgLang = this.jdbc.update(sql0, new Object[]{messageId});
            return update > 0 && updateMsgLang > 0;
        }
        catch (Exception e) {
            logger.error("\u5220\u9664\u5f02\u5e38", (Object)("msgId\u4e3a" + messageId));
            return true;
        }
    }

    @Override
    public boolean updateCompleteTime(List<String> formSchemeKey, LocalDateTime time) {
        try {
            String sql = String.format("update %s set %s=? where %s like ?", TABLE_TODO, COMPLETETIME, FORMSCHEMEKEY);
            List<Object[]> values = this.values(formSchemeKey, time);
            int[] batchUpdate = this.jdbc.batchUpdate(sql, values);
            String delSql = String.format("delete from %s where %s = ?", TABLE_TODO_LANG, FORMSCHEME_KEY);
            List<Object[]> deleteValues = this.values(formSchemeKey);
            int[] updateMsgLang = this.jdbc.batchUpdate(delSql, deleteValues);
            return batchUpdate.length > 0 && updateMsgLang.length > 0;
        }
        catch (Exception e) {
            logger.error("\u5220\u9664\u5f02\u5e38", e);
            return true;
        }
    }

    private List<Object[]> values(List<String> formSchemeKeys, LocalDateTime time) {
        ArrayList<Object[]> values = new ArrayList<Object[]>();
        if (formSchemeKeys != null && formSchemeKeys.size() > 0) {
            for (String formSchemeKey : formSchemeKeys) {
                Object[] object = new Object[]{Timestamp.valueOf(time), formSchemeKey};
                values.add(object);
            }
        }
        return values;
    }

    private List<Object[]> values(List<String> formSchemeKeys) {
        ArrayList<Object[]> values = new ArrayList<Object[]>();
        if (formSchemeKeys != null && formSchemeKeys.size() > 0) {
            for (String formSchemeKey : formSchemeKeys) {
                Object[] object = new Object[]{formSchemeKey};
                values.add(object);
            }
        }
        return values;
    }

    @Override
    public boolean updateCompleteTimeByFormSchemeKey(String formSchemeKey, String period, LocalDateTime time) {
        try {
            String sql = String.format("update %s set %s=? where %s like ?", TABLE_TODO, COMPLETETIME, MSGID);
            Object[] values = new Object[]{Timestamp.valueOf(time), formSchemeKey};
            int update = this.jdbc.update(sql, values);
            String delSql = String.format("delete from %s where %s = ?", TABLE_TODO_LANG, FORMSCHEME_KEY);
            Object[] deleteValues = new Object[]{formSchemeKey};
            int updateMsgLang = this.jdbc.update(delSql, deleteValues);
            return update > 0 && updateMsgLang > 0;
        }
        catch (Exception e) {
            logger.error("\u5220\u9664\u5f02\u5e38", e);
            return true;
        }
    }

    private boolean getLanguage() {
        boolean chineseLang = true;
        String language = NpContextHolder.getContext().getLocale().getLanguage();
        if (!"zh".equals(language)) {
            chineseLang = false;
        }
        return chineseLang;
    }

    @Override
    public List<TodoVO> compeleteTodoList(List<String> participantIds) {
        String sql = "select t.msgid,\n       t.param,\n       t.content,\n       t.content_clob,\n       t.createtime,\n       t.title,\n       t.appname,\n       s.actionname,\n       s.todo_type,\n       s.todoParam,\n       s.todoParam_clob\n  from msg_main t\n         inner join msg_participant p on t.msgid = p.msgid\n         inner join msg_todo s on t.msgid = s.msgid\nwhere t.type = 4\n  and p.participantid in (:participants)\n and s.completetime is not null order by t.createtime desc";
        sql = this.bindParticipantParameter(sql, "participants", participantIds);
        ArrayList<TodoVO> todoVOList = new ArrayList<TodoVO>();
        try {
            this.jdbc.query(sql, rs -> todoVOList.add(this.createTodoVO(rs)));
            return todoVOList;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}

