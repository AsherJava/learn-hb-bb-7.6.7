/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.sql.SQLQueryException
 *  com.jiuqi.bi.syntax.sql.SQLQueryExecutor
 *  com.jiuqi.np.authz2.Role
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.UserService
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.ibatis.jdbc.SQL
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 *  org.springframework.jdbc.core.simple.SimpleJdbcInsert
 */
package com.jiuqi.nr.reminder.impl;

import com.jiuqi.bi.syntax.sql.SQLQueryException;
import com.jiuqi.bi.syntax.sql.SQLQueryExecutor;
import com.jiuqi.np.authz2.Role;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.reminder.infer.ReminderRepository;
import com.jiuqi.nr.reminder.internal.Reminder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ReminderRepositoryImpl
implements ReminderRepository {
    private static final Logger log = LoggerFactory.getLogger(ReminderRepositoryImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String TABLE_REMINDER = "nr_reminder";
    private static final String TABLE_REMINDER_ROLE = "nr_reminder_role";
    private static final String TABLE_REMINDER_CC = "nr_reminder_cc";
    private static final String TABLE_REMINDER_UNIT = "nr_reminder_unit";
    private static final String TABLE_REMINDER_FORM = "nr_reminder_form";
    private static final String COL_REM_ID = "rem_id";
    private static final String COL_REM_TITLE = "rem_title";
    private static final String COL_CREATE_TIME = "create_time";
    private static final String COL_TYPE = "type";
    private static final String COL_TASK_ID = "task_id";
    private static final String COL_FORMSCHEME_ID = "formscheme_id";
    private static final String COL_EXECUTE_TIME = "execute_time";
    private static final String COL_STATUS = "status";
    private static final String COL_MSG_CONTENT = "msg_content";
    private static final String COL_SEND_METHOD = "send_method";
    private static final String COL_UNIT_RANGE = "unit_range";
    private static final String COL_ROLE_RANGE = "role_range";
    private static final String COL_FORM_RANGE = "form_range";
    private static final String COL_GROUP_RANGE = "group_range";
    private static final String COL_ROLE_ID = "role_id";
    private static final String COL_UNIT_ID = "unit_id";
    private static final String COL_FORMKEY = "formKey";
    private static final String COL_CC_INFO = "cc_info";
    private static final String COL_REPEAT_TIME = "repeat_mode";
    private static final String COL_CURRUSERID = "curr_unit_id";
    private static final String COL_BRFOREDEADLINE = "send_before_deadline";
    private static final String COL_AFTERDEADLINE = "send_after_deadline";
    private static final String COL_SEND_TYPE = "send_type";
    private static final String COL_SHOW_SEND_TIME = "showSendTime";
    private static final String COL_FREQUENCY_MODE = "frequencyMode";
    private static final String COL_TRIGER_CORN = "trigerCorn";
    private static final String COL_REGULAR_TIME = "regularTime";
    private static final String COL_VALID_START_TIME = "validStartTime";
    private static final String COL_VALID_END_TIME = "validEndTime";
    private static final String COL_EXECUTENUMS = "executeNums";
    private static final String COL_NEXTSEND_TIME = "nextSendTime";
    private static final String COL_PERIOD_TYPE = "periodType";
    private static final String COL_WORKFLOWTYPE = "workFlowType";
    private static final int CC_TYPE_ROLE = 1;
    private static final int CC_TYPE_USER = 0;
    private static final String CC_INFO_SPLIT = ":";
    @Autowired
    private UserService<User> userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private NamedParameterJdbcTemplate template;

    @Override
    public String save(Reminder reminder) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(Objects.requireNonNull(this.template.getJdbcTemplate().getDataSource())).withTableName(TABLE_REMINDER).usingColumns(new String[]{COL_REM_ID, COL_REM_TITLE, COL_CREATE_TIME, COL_TYPE, COL_FORMSCHEME_ID, COL_TASK_ID, COL_MSG_CONTENT, COL_SEND_METHOD, COL_UNIT_RANGE, COL_ROLE_RANGE, COL_REPEAT_TIME, COL_CURRUSERID, COL_BRFOREDEADLINE, COL_AFTERDEADLINE, COL_SEND_TYPE, COL_SHOW_SEND_TIME, COL_FREQUENCY_MODE, COL_TRIGER_CORN, COL_EXECUTENUMS, COL_NEXTSEND_TIME, COL_VALID_START_TIME, COL_VALID_END_TIME, COL_REGULAR_TIME, COL_PERIOD_TYPE, COL_FORM_RANGE, COL_GROUP_RANGE, COL_WORKFLOWTYPE});
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        if (reminder.getId() == null) {
            String remId = Reminder.generateId();
            reminder.setId(remId);
        }
        String handleMethod = null;
        if (reminder.getHandleMethod() != null) {
            String flattedArray = Arrays.toString((Object[])reminder.getHandleMethod().toArray(new Integer[0]));
            handleMethod = flattedArray.substring(1, flattedArray.length() - 1);
        }
        parameterSource.addValue(COL_REM_ID, (Object)reminder.getId()).addValue(COL_REM_TITLE, (Object)reminder.getTitle()).addValue(COL_CREATE_TIME, (Object)Timestamp.valueOf(LocalDateTime.now())).addValue(COL_TYPE, (Object)reminder.getType()).addValue(COL_TASK_ID, (Object)reminder.getTaskId()).addValue(COL_FORMSCHEME_ID, (Object)reminder.getFormSchemeId()).addValue(COL_MSG_CONTENT, (Object)reminder.getContent()).addValue(COL_SEND_METHOD, (Object)handleMethod).addValue(COL_UNIT_RANGE, (Object)reminder.getUnitRange()).addValue(COL_ROLE_RANGE, (Object)reminder.getRoleType()).addValue(COL_REPEAT_TIME, (Object)reminder.getRepeatMode()).addValue(COL_CURRUSERID, (Object)reminder.getUnitId()).addValue(COL_BRFOREDEADLINE, (Object)reminder.getDayBeforeDeadline()).addValue(COL_AFTERDEADLINE, (Object)reminder.getDayAfterDeadline()).addValue(COL_SEND_TYPE, (Object)reminder.getSendTime()).addValue(COL_SHOW_SEND_TIME, (Object)reminder.getShowSendTime()).addValue(COL_FREQUENCY_MODE, (Object)reminder.getFrequencyMode()).addValue(COL_TRIGER_CORN, (Object)reminder.getTrigerCorn()).addValue(COL_EXECUTENUMS, (Object)reminder.getExecuteNums()).addValue(COL_NEXTSEND_TIME, (Object)reminder.getNextSendTime()).addValue(COL_VALID_START_TIME, (Object)reminder.getValidStartTime()).addValue(COL_VALID_END_TIME, (Object)reminder.getValidEndTime()).addValue(COL_REGULAR_TIME, (Object)reminder.getRegularTime()).addValue(COL_PERIOD_TYPE, (Object)reminder.getPeriodType()).addValue(COL_FORM_RANGE, (Object)reminder.getFormRange()).addValue(COL_GROUP_RANGE, (Object)reminder.getGroupRange()).addValue(COL_WORKFLOWTYPE, (Object)reminder.getWorkFlowType());
        try {
            jdbcInsert.execute((SqlParameterSource)parameterSource);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        this.saveUnit(reminder);
        this.saveRole(reminder);
        this.saveFormKey(reminder);
        this.saveCc(reminder);
        return reminder.getId();
    }

    private void saveUnit(Reminder reminder) {
        if (reminder.getUnitIds() == null || reminder.getUnitIds().isEmpty()) {
            return;
        }
        SimpleJdbcInsert insert = new SimpleJdbcInsert(Objects.requireNonNull(this.template.getJdbcTemplate().getDataSource())).withTableName(TABLE_REMINDER_UNIT).usingColumns(new String[]{COL_REM_ID, COL_UNIT_ID});
        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[reminder.getUnitIds().size()];
        for (int i = 0; i < reminder.getUnitIds().size(); ++i) {
            MapSqlParameterSource parameterSource;
            parameterSources[i] = parameterSource = new MapSqlParameterSource().addValue(COL_REM_ID, (Object)reminder.getId()).addValue(COL_UNIT_ID, (Object)reminder.getUnitIds().get(i));
        }
        insert.executeBatch((SqlParameterSource[])parameterSources);
    }

    private void saveRole(Reminder reminder) {
        if (reminder.getRoleIds() == null || reminder.getRoleIds().isEmpty()) {
            return;
        }
        SimpleJdbcInsert insert = new SimpleJdbcInsert(Objects.requireNonNull(this.template.getJdbcTemplate().getDataSource())).withTableName(TABLE_REMINDER_ROLE).usingColumns(new String[]{COL_REM_ID, COL_ROLE_ID});
        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[reminder.getRoleIds().size()];
        for (int i = 0; i < reminder.getRoleIds().size(); ++i) {
            MapSqlParameterSource parameterSource;
            parameterSources[i] = parameterSource = new MapSqlParameterSource().addValue(COL_REM_ID, (Object)reminder.getId()).addValue(COL_ROLE_ID, (Object)reminder.getRoleIds().get(i));
        }
        insert.executeBatch((SqlParameterSource[])parameterSources);
    }

    private void saveFormKey(Reminder reminder) {
        if (reminder.getFormKeys() == null || reminder.getFormKeys().isEmpty()) {
            return;
        }
        SimpleJdbcInsert insert = new SimpleJdbcInsert(Objects.requireNonNull(this.template.getJdbcTemplate().getDataSource())).withTableName(TABLE_REMINDER_FORM).usingColumns(new String[]{COL_REM_ID, COL_FORMKEY});
        MapSqlParameterSource parameterSources = new MapSqlParameterSource();
        String formKeys = "";
        if (reminder.getFormKeys() != null && !reminder.getFormKeys().isEmpty()) {
            formKeys = reminder.getFormKeys().stream().collect(Collectors.joining(";"));
        }
        parameterSources.addValue(COL_REM_ID, (Object)reminder.getId()).addValue(COL_FORMKEY, (Object)formKeys);
        insert.executeBatch(new SqlParameterSource[]{parameterSources});
    }

    private void saveCc(Reminder reminder) {
        if (reminder.getCcInfos() == null || reminder.getCcInfos().isEmpty()) {
            return;
        }
        SimpleJdbcInsert insert = new SimpleJdbcInsert(Objects.requireNonNull(this.template.getJdbcTemplate().getDataSource())).withTableName(TABLE_REMINDER_CC).usingColumns(new String[]{COL_REM_ID, COL_CC_INFO});
        MapSqlParameterSource parameterSources = new MapSqlParameterSource();
        String ccInfos = "";
        if (reminder.getCcInfos() != null && !reminder.getCcInfos().isEmpty()) {
            ccInfos = reminder.getCcInfos().stream().collect(Collectors.joining(";"));
        }
        parameterSources.addValue(COL_REM_ID, (Object)reminder.getId()).addValue(COL_CC_INFO, (Object)ccInfos);
        insert.executeBatch(new SqlParameterSource[]{parameterSources});
    }

    private void updateCc(Reminder reminder) {
        String sql = ((SQL)((SQL)((SQL)new SQL().UPDATE(TABLE_REMINDER_CC)).SET("cc_info=:cc_info")).WHERE("rem_id=:rem_id")).toString();
        String ccInfos = "";
        if (reminder.getCcInfos() != null && !reminder.getCcInfos().isEmpty()) {
            ccInfos = reminder.getCcInfos().stream().collect(Collectors.joining(";"));
        }
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue(COL_REM_ID, (Object)reminder.getId()).addValue(COL_CC_INFO, (Object)ccInfos);
        this.template.update(sql, (SqlParameterSource)parameterSource);
    }

    private void updateUnitId(Reminder reminder) {
        String sql = ((SQL)((SQL)((SQL)new SQL().UPDATE(TABLE_REMINDER_UNIT)).SET("unit_id=:unit_id")).WHERE("rem_id=:rem_id")).toString();
        String unitIds = "";
        if (reminder.getUnitIds() != null && !reminder.getUnitIds().isEmpty()) {
            unitIds = reminder.getUnitIds().stream().collect(Collectors.joining(";"));
        }
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue(COL_REM_ID, (Object)reminder.getId()).addValue(COL_UNIT_ID, (Object)unitIds);
        this.template.update(sql, (SqlParameterSource)parameterSource);
    }

    private void updateFormKeys(Reminder reminder) {
        String sql = ((SQL)((SQL)((SQL)new SQL().UPDATE(TABLE_REMINDER_FORM)).SET("formKey=:formKey")).WHERE("rem_id=:rem_id")).toString();
        String formKeys = "";
        if (reminder.getFormKeys() != null && !reminder.getFormKeys().isEmpty()) {
            formKeys = reminder.getFormKeys().stream().collect(Collectors.joining(";"));
        }
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue(COL_REM_ID, (Object)reminder.getId()).addValue(COL_FORMKEY, (Object)formKeys);
        this.template.update(sql, (SqlParameterSource)parameterSource);
    }

    @Override
    public void updateExecuteTime(String remId, LocalDateTime executeTime) {
        String sql = ((SQL)((SQL)((SQL)((SQL)new SQL().UPDATE(TABLE_REMINDER)).SET("execute_time=:execute_time")).SET("status=:status")).WHERE("rem_id=:rem_id")).toString();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue(COL_REM_ID, (Object)remId).addValue(COL_EXECUTE_TIME, (Object)Timestamp.valueOf(executeTime)).addValue(COL_STATUS, (Object)2);
        this.template.update(sql, (SqlParameterSource)parameterSource);
    }

    @Override
    public void updateExecuteTimeAndStatus(String remId, LocalDateTime executeTime, int status) {
        String sql = ((SQL)((SQL)((SQL)((SQL)new SQL().UPDATE(TABLE_REMINDER)).SET("execute_time=:execute_time")).SET("status=:status")).WHERE("rem_id=:rem_id")).toString();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue(COL_REM_ID, (Object)remId).addValue(COL_EXECUTE_TIME, (Object)Timestamp.valueOf(executeTime)).addValue(COL_STATUS, (Object)status);
        this.template.update(sql, (SqlParameterSource)parameterSource);
    }

    @Override
    public void updateExecuteStatus(String remId, int status) {
        String sql = ((SQL)((SQL)((SQL)new SQL().UPDATE(TABLE_REMINDER)).SET("status=:status")).WHERE("rem_id=:rem_id")).toString();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue(COL_REM_ID, (Object)remId).addValue(COL_STATUS, (Object)status);
        this.template.update(sql, (SqlParameterSource)parameterSource);
    }

    @Override
    public Reminder find(String key) {
        String conditionSql = String.format("%s = ?", COL_REM_ID);
        String sql = ((SQL)((SQL)((SQL)new SQL().SELECT(new String[]{COL_REM_ID, COL_REM_TITLE, COL_STATUS, COL_CREATE_TIME, COL_TYPE, COL_FORMSCHEME_ID, COL_TASK_ID, COL_MSG_CONTENT, COL_SEND_METHOD, COL_UNIT_RANGE, COL_ROLE_RANGE, COL_REPEAT_TIME, COL_CURRUSERID, COL_BRFOREDEADLINE, COL_AFTERDEADLINE, COL_EXECUTE_TIME, COL_SEND_TYPE, COL_SHOW_SEND_TIME, COL_FREQUENCY_MODE, COL_TRIGER_CORN, COL_EXECUTENUMS, COL_NEXTSEND_TIME, COL_VALID_START_TIME, COL_VALID_END_TIME, COL_REGULAR_TIME, COL_PERIOD_TYPE, COL_FORM_RANGE, COL_GROUP_RANGE, COL_WORKFLOWTYPE})).FROM(TABLE_REMINDER)).WHERE(conditionSql)).toString();
        ReminderRepositoryImpl r = this;
        ResultSetExtractor rse = rs -> {
            if (rs.next()) {
                return r.createReminder(rs);
            }
            return null;
        };
        return (Reminder)this.template.getJdbcTemplate().query(sql, rse, new Object[]{key});
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    @Override
    public List<Reminder> find(int pageIndex, int pageSize) {
        LinkedList<Reminder> linkedList;
        Throwable throwable;
        Connection connection;
        SQLQueryExecutor executor;
        block26: {
            block27: {
                String sql = ((SQL)((SQL)((SQL)new SQL().SELECT(new String[]{COL_REM_ID, COL_REM_TITLE, COL_STATUS, COL_CREATE_TIME, COL_TYPE, COL_FORMSCHEME_ID, COL_TASK_ID, COL_MSG_CONTENT, COL_SEND_METHOD, COL_UNIT_RANGE, COL_ROLE_RANGE, COL_REPEAT_TIME, COL_CURRUSERID, COL_BRFOREDEADLINE, COL_AFTERDEADLINE, COL_EXECUTE_TIME, COL_SEND_TYPE, COL_SHOW_SEND_TIME, COL_FREQUENCY_MODE, COL_TRIGER_CORN, COL_EXECUTENUMS, COL_NEXTSEND_TIME, COL_VALID_START_TIME, COL_VALID_END_TIME, COL_REGULAR_TIME, COL_PERIOD_TYPE, COL_FORM_RANGE, COL_GROUP_RANGE, COL_WORKFLOWTYPE})).FROM(TABLE_REMINDER)).ORDER_BY(COL_CREATE_TIME)).toString() + " DESC";
                executor = null;
                connection = Objects.requireNonNull(this.template.getJdbcTemplate().getDataSource()).getConnection();
                throwable = null;
                executor = new SQLQueryExecutor(connection);
                executor.open(sql);
                ResultSet resultSet = executor.executeQuery((pageIndex - 1) * pageSize, pageIndex * pageSize);
                LinkedList<Reminder> reminders = new LinkedList<Reminder>();
                while (resultSet.next()) {
                    Reminder reminder = this.createReminder(resultSet);
                    reminder.setUnitIds(this.findUnitIds(reminder.getId()));
                    reminder.setRoleIds(this.findRoleIds(reminder.getId()));
                    reminder.setFormKeys(this.findFormKeys(reminder.getId()));
                    reminder.setCcInfos(this.findCcInfos(reminder.getId()));
                    reminders.add(reminder);
                }
                linkedList = reminders;
                if (connection == null) break block26;
                if (throwable == null) break block27;
                try {
                    connection.close();
                }
                catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                break block26;
            }
            connection.close();
        }
        try {
            if (executor != null) {
                executor.close();
            }
        }
        catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return linkedList;
        {
            catch (Throwable throwable3) {
                try {
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
                        log.error(e.getMessage(), e);
                        try {
                            if (executor != null) {
                                executor.close();
                            }
                        }
                        catch (SQLException e2) {
                            log.error(e2.getMessage(), e2);
                        }
                    }
                }
                catch (Throwable throwable6) {
                    try {
                        if (executor != null) {
                            executor.close();
                        }
                    }
                    catch (SQLException e) {
                        log.error(e.getMessage(), e);
                    }
                    throw throwable6;
                }
            }
        }
        return Collections.emptyList();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    @Override
    public int findAllNums() {
        String sql = ((SQL)((SQL)new SQL().SELECT("COUNT(*)")).FROM(TABLE_REMINDER)).toString();
        SQLQueryExecutor executor = null;
        try {
            int n;
            Throwable throwable;
            Connection connection;
            block25: {
                block26: {
                    connection = Objects.requireNonNull(this.template.getJdbcTemplate().getDataSource()).getConnection();
                    throwable = null;
                    executor = new SQLQueryExecutor(connection);
                    executor.open(sql);
                    ResultSet resultSet = executor.executeQuery();
                    String count = null;
                    int nums = 0;
                    while (resultSet.next()) {
                        count = resultSet.getString(1);
                    }
                    n = nums = Integer.parseInt(count);
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
            return n;
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
                    log.error(e.getMessage(), e);
                }
            }
        }
        finally {
            try {
                if (executor != null) {
                    executor.close();
                }
            }
            catch (SQLException e) {
                log.error(e.getMessage(), e);
            }
        }
        return 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    @Override
    public List<Reminder> findAll() {
        LinkedList<Reminder> linkedList;
        Throwable throwable;
        Connection connection;
        SQLQueryExecutor executor;
        block26: {
            block27: {
                String sql = ((SQL)((SQL)((SQL)new SQL().SELECT(new String[]{COL_REM_ID, COL_REM_TITLE, COL_STATUS, COL_CREATE_TIME, COL_TYPE, COL_FORMSCHEME_ID, COL_TASK_ID, COL_MSG_CONTENT, COL_SEND_METHOD, COL_UNIT_RANGE, COL_ROLE_RANGE, COL_REPEAT_TIME, COL_CURRUSERID, COL_BRFOREDEADLINE, COL_AFTERDEADLINE, COL_EXECUTE_TIME, COL_SEND_TYPE, COL_SHOW_SEND_TIME, COL_FREQUENCY_MODE, COL_TRIGER_CORN, COL_EXECUTENUMS, COL_NEXTSEND_TIME, COL_VALID_START_TIME, COL_VALID_END_TIME, COL_REGULAR_TIME, COL_PERIOD_TYPE, COL_FORM_RANGE, COL_GROUP_RANGE, COL_WORKFLOWTYPE})).FROM(TABLE_REMINDER)).ORDER_BY(COL_CREATE_TIME)).toString() + " DESC";
                executor = null;
                connection = Objects.requireNonNull(this.template.getJdbcTemplate().getDataSource()).getConnection();
                throwable = null;
                executor = new SQLQueryExecutor(connection);
                executor.open(sql);
                ResultSet resultSet = executor.executeQuery();
                LinkedList<Reminder> reminders = new LinkedList<Reminder>();
                while (resultSet.next()) {
                    Reminder reminder = this.createReminder(resultSet);
                    reminder.setUnitIds(this.findUnitIds(reminder.getId()));
                    reminder.setRoleIds(this.findRoleIds(reminder.getId()));
                    reminder.setFormKeys(this.findFormKeys(reminder.getId()));
                    reminder.setCcInfos(this.findCcInfos(reminder.getId()));
                    reminders.add(reminder);
                }
                linkedList = reminders;
                if (connection == null) break block26;
                if (throwable == null) break block27;
                try {
                    connection.close();
                }
                catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                break block26;
            }
            connection.close();
        }
        try {
            if (executor != null) {
                executor.close();
            }
        }
        catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return linkedList;
        {
            catch (Throwable throwable3) {
                try {
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
                        log.error(e.getMessage(), e);
                        try {
                            if (executor != null) {
                                executor.close();
                            }
                        }
                        catch (SQLException e2) {
                            log.error(e2.getMessage(), e2);
                        }
                    }
                }
                catch (Throwable throwable6) {
                    try {
                        if (executor != null) {
                            executor.close();
                        }
                    }
                    catch (SQLException e) {
                        log.error(e.getMessage(), e);
                    }
                    throw throwable6;
                }
            }
        }
        return Collections.emptyList();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int getPageInfo(String id, int pageSize) {
        int i = 0;
        int pageIndex = 0;
        String sql = ((SQL)((SQL)((SQL)new SQL().SELECT(new String[]{COL_REM_ID, COL_REM_TITLE, COL_STATUS, COL_CREATE_TIME, COL_TYPE, COL_FORMSCHEME_ID, COL_TASK_ID, COL_MSG_CONTENT, COL_SEND_METHOD, COL_UNIT_RANGE, COL_ROLE_RANGE, COL_REPEAT_TIME, COL_CURRUSERID, COL_BRFOREDEADLINE, COL_AFTERDEADLINE, COL_EXECUTE_TIME, COL_SEND_TYPE, COL_SHOW_SEND_TIME, COL_FREQUENCY_MODE, COL_TRIGER_CORN, COL_EXECUTENUMS, COL_NEXTSEND_TIME, COL_VALID_START_TIME, COL_VALID_END_TIME, COL_REGULAR_TIME, COL_PERIOD_TYPE, COL_FORM_RANGE, COL_GROUP_RANGE, COL_WORKFLOWTYPE})).FROM(TABLE_REMINDER)).ORDER_BY(COL_CREATE_TIME)).toString() + " DESC";
        SQLQueryExecutor executor = null;
        try (Connection connection = Objects.requireNonNull(this.template.getJdbcTemplate().getDataSource()).getConnection();){
            executor = new SQLQueryExecutor(connection);
            executor.open(sql);
            ResultSet resultSet = executor.executeQuery();
            while (resultSet.next()) {
                Reminder reminder = this.createReminder(resultSet);
                if (id.equals(reminder.getId())) {
                    if (i == i % pageSize) {
                        pageIndex = 1;
                        continue;
                    }
                    pageIndex = (i - i % pageSize) / pageSize;
                    continue;
                }
                ++i;
            }
        }
        catch (SQLQueryException | SQLException e) {
            log.error(e.getMessage(), e);
        }
        finally {
            try {
                if (executor != null) {
                    executor.close();
                }
            }
            catch (SQLException e) {
                log.error(e.getMessage(), e);
            }
        }
        return pageIndex;
    }

    public List<String> findRoleIds(String remId) {
        String sql = ((SQL)((SQL)((SQL)new SQL().SELECT(COL_ROLE_ID)).FROM(TABLE_REMINDER_ROLE)).WHERE("rem_id=:rem_id")).toString();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue(COL_REM_ID, (Object)remId);
        return this.template.query(sql, (SqlParameterSource)parameterSource, (rs, rowNum) -> rs.getString(COL_ROLE_ID));
    }

    public List<String> findUnitIds(String remId) {
        String sql = ((SQL)((SQL)((SQL)new SQL().SELECT(COL_UNIT_ID)).FROM(TABLE_REMINDER_UNIT)).WHERE("rem_id=:rem_id")).toString();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue(COL_REM_ID, (Object)remId);
        return this.template.query(sql, (SqlParameterSource)parameterSource, (rs, rowNum) -> rs.getString(COL_UNIT_ID));
    }

    public List<String> findFormKeys(String remId) {
        MapSqlParameterSource parameterSource;
        ArrayList<String> result = new ArrayList<String>();
        List formKeys = new ArrayList();
        String sql = ((SQL)((SQL)((SQL)new SQL().SELECT(COL_FORMKEY)).FROM(TABLE_REMINDER_FORM)).WHERE(new String[]{"rem_id=:rem_id", "formKey is not null"})).toString();
        formKeys = this.template.query(sql, (SqlParameterSource)(parameterSource = new MapSqlParameterSource().addValue(COL_REM_ID, (Object)remId)), (rs, rowNum) -> rs.getString(COL_FORMKEY));
        if (formKeys != null && !formKeys.isEmpty()) {
            String[] formKeyArray;
            String temp = (String)formKeys.get(0);
            for (String formKey : formKeyArray = temp.split(";")) {
                result.add(formKey);
            }
        }
        return result;
    }

    public List<String> findCcInfos(String remId) {
        MapSqlParameterSource parameterSource;
        ArrayList<String> result = new ArrayList<String>();
        List ccInfos = new ArrayList();
        String sql = ((SQL)((SQL)((SQL)new SQL().SELECT(COL_CC_INFO)).FROM(TABLE_REMINDER_CC)).WHERE(new String[]{"rem_id=:rem_id", "cc_info is not null"})).toString();
        ccInfos = this.template.query(sql, (SqlParameterSource)(parameterSource = new MapSqlParameterSource().addValue(COL_REM_ID, (Object)remId)), (rs, rowNum) -> rs.getString(COL_CC_INFO));
        if (ccInfos != null && !ccInfos.isEmpty()) {
            String[] ccInfoArray;
            String temp = (String)ccInfos.get(0);
            for (String ccInfo : ccInfoArray = temp.split(";")) {
                Optional role;
                String oneResult = "";
                String[] ccs = ccInfo.split(CC_INFO_SPLIT);
                if (Integer.parseInt(ccs[1]) == 1 && (role = this.roleService.get(ccs[0])).isPresent()) {
                    oneResult = ccs[0] + CC_INFO_SPLIT + ((Role)role.get()).getTitle() + CC_INFO_SPLIT + 1;
                }
                if (Integer.parseInt(ccs[1]) == 0) {
                    User user = this.userService.get(ccs[0]);
                    oneResult = ccs[0] + CC_INFO_SPLIT + user.getName() + CC_INFO_SPLIT + 0;
                }
                result.add(oneResult);
            }
        }
        return result;
    }

    @Override
    public void delete(String remId) {
        String conditionSql = String.format("%s = ?", COL_REM_ID);
        String sql0 = ((SQL)((SQL)new SQL().DELETE_FROM(TABLE_REMINDER)).WHERE(conditionSql)).toString();
        String sql1 = ((SQL)((SQL)new SQL().DELETE_FROM(TABLE_REMINDER_ROLE)).WHERE(conditionSql)).toString();
        String sql2 = ((SQL)((SQL)new SQL().DELETE_FROM(TABLE_REMINDER_UNIT)).WHERE(conditionSql)).toString();
        String sql3 = ((SQL)((SQL)new SQL().DELETE_FROM(TABLE_REMINDER_CC)).WHERE(conditionSql)).toString();
        String sql4 = ((SQL)((SQL)new SQL().DELETE_FROM(TABLE_REMINDER_FORM)).WHERE(conditionSql)).toString();
        this.getJdbcTemplate().update(sql0, new Object[]{remId});
        this.getJdbcTemplate().update(sql1, new Object[]{remId});
        this.getJdbcTemplate().update(sql2, new Object[]{remId});
        this.getJdbcTemplate().update(sql3, new Object[]{remId});
        this.getJdbcTemplate().update(sql4, new Object[]{remId});
    }

    @Override
    public void batchDelete(List<String> remIds) {
        String conditionSql = String.format("%s in (:%s)", COL_REM_ID, COL_REM_ID);
        String sql0 = ((SQL)((SQL)new SQL().DELETE_FROM(TABLE_REMINDER)).WHERE(conditionSql)).toString();
        String sql1 = ((SQL)((SQL)new SQL().DELETE_FROM(TABLE_REMINDER_ROLE)).WHERE(conditionSql)).toString();
        String sql2 = ((SQL)((SQL)new SQL().DELETE_FROM(TABLE_REMINDER_UNIT)).WHERE(conditionSql)).toString();
        String sql3 = ((SQL)((SQL)new SQL().DELETE_FROM(TABLE_REMINDER_CC)).WHERE(conditionSql)).toString();
        String sql4 = ((SQL)((SQL)new SQL().DELETE_FROM(TABLE_REMINDER_FORM)).WHERE(conditionSql)).toString();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue(COL_REM_ID, remIds);
        this.template.update(sql0, (SqlParameterSource)parameterSource);
        this.template.update(sql1, (SqlParameterSource)parameterSource);
        this.template.update(sql2, (SqlParameterSource)parameterSource);
        this.template.update(sql3, (SqlParameterSource)parameterSource);
        this.template.update(sql4, (SqlParameterSource)parameterSource);
    }

    private Reminder createReminder(ResultSet resultSet) throws SQLException {
        Reminder reminder = new Reminder();
        reminder.setId(resultSet.getString(COL_REM_ID));
        reminder.setTitle(resultSet.getString(COL_REM_TITLE));
        reminder.setType(resultSet.getByte(COL_TYPE));
        reminder.setExecuteStatus(resultSet.getByte(COL_STATUS));
        reminder.setExecuteTime(resultSet.getTimestamp(COL_EXECUTE_TIME) != null ? resultSet.getTimestamp(COL_EXECUTE_TIME).toLocalDateTime() : null);
        reminder.setTaskId(resultSet.getString(COL_TASK_ID));
        reminder.setFormSchemeId(resultSet.getString(COL_FORMSCHEME_ID));
        reminder.setContent(resultSet.getString(COL_MSG_CONTENT));
        reminder.setUnitRange(resultSet.getByte(COL_UNIT_RANGE));
        reminder.setRoleType(resultSet.getByte(COL_ROLE_RANGE));
        if (resultSet.getString(COL_SEND_METHOD) != null) {
            String[] str = resultSet.getString(COL_SEND_METHOD).split(",");
            ArrayList<Integer> ints = new ArrayList<Integer>();
            for (int i = 0; i < str.length; ++i) {
                String e = str[i].trim();
                if (!StringUtils.isNotEmpty((CharSequence)e)) continue;
                ints.add(Integer.parseInt(e));
            }
            reminder.setHandleMethod(ints);
        }
        reminder.setRepeatMode(resultSet.getInt(COL_REPEAT_TIME));
        reminder.setDayAfterDeadline(resultSet.getInt(COL_AFTERDEADLINE));
        reminder.setDayBeforeDeadline(resultSet.getInt(COL_BRFOREDEADLINE));
        reminder.setSendTime(resultSet.getInt(COL_SEND_TYPE));
        reminder.setUnitId(resultSet.getString(COL_CURRUSERID));
        reminder.setShowSendTime(resultSet.getString(COL_SHOW_SEND_TIME));
        reminder.setFrequencyMode(resultSet.getInt(COL_FREQUENCY_MODE));
        reminder.setTrigerCorn(resultSet.getString(COL_TRIGER_CORN));
        reminder.setExecuteNums(resultSet.getInt(COL_EXECUTENUMS));
        reminder.setNextSendTime(resultSet.getString(COL_NEXTSEND_TIME));
        reminder.setValidStartTime(resultSet.getString(COL_VALID_START_TIME));
        reminder.setValidEndTime(resultSet.getString(COL_VALID_END_TIME));
        reminder.setRegularTime(resultSet.getString(COL_REGULAR_TIME));
        reminder.setPeriodType(resultSet.getString(COL_PERIOD_TYPE));
        reminder.setFormRange(resultSet.getString(COL_FORM_RANGE));
        reminder.setGroupRange(resultSet.getString(COL_GROUP_RANGE));
        reminder.setWorkFlowType(resultSet.getString(COL_WORKFLOWTYPE));
        return reminder;
    }

    private JdbcTemplate getJdbcTemplate() {
        return this.template.getJdbcTemplate();
    }

    @Override
    public void updateReminder(Reminder reminder) {
        List<String> ccInfos;
        List<String> selectUnitIds;
        List<String> unitIds;
        String handleMethod = null;
        if (reminder.getHandleMethod() != null) {
            String flattedArray = Arrays.toString((Object[])reminder.getHandleMethod().toArray(new Integer[0]));
            handleMethod = flattedArray.substring(1, flattedArray.length() - 1);
        }
        String sql = ((SQL)((SQL)((SQL)((SQL)((SQL)((SQL)((SQL)((SQL)((SQL)((SQL)((SQL)((SQL)((SQL)((SQL)((SQL)((SQL)((SQL)((SQL)((SQL)((SQL)((SQL)((SQL)((SQL)((SQL)((SQL)((SQL)new SQL().UPDATE(TABLE_REMINDER)).SET("rem_title=:rem_title")).SET("status=:status")).SET("type=:type")).SET("msg_content=:msg_content")).SET("send_method=:send_method")).SET("unit_range=:unit_range")).SET("role_range=:role_range")).SET("repeat_mode=:repeat_mode")).SET("curr_unit_id=:curr_unit_id")).SET("send_before_deadline=:send_before_deadline")).SET("send_after_deadline=:send_after_deadline")).SET("send_type=:send_type")).SET("showSendTime=:showSendTime")).SET("frequencyMode=:frequencyMode")).SET("trigerCorn=:trigerCorn")).SET("executeNums=:executeNums")).SET("nextSendTime=:nextSendTime")).SET("validStartTime=:validStartTime")).SET("validEndTime=:validEndTime")).SET("regularTime=:regularTime")).SET("periodType=:periodType")).SET("form_range=:form_range")).SET("group_range=:group_range")).SET("workFlowType=:workFlowType")).WHERE("rem_id=:rem_id")).toString();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue(COL_REM_TITLE, (Object)reminder.getTitle()).addValue(COL_STATUS, (Object)reminder.getExecuteStatus()).addValue(COL_TYPE, (Object)reminder.getType()).addValue(COL_MSG_CONTENT, (Object)reminder.getContent()).addValue(COL_SEND_METHOD, (Object)handleMethod).addValue(COL_UNIT_RANGE, (Object)reminder.getUnitRange()).addValue(COL_ROLE_RANGE, (Object)reminder.getRoleType()).addValue(COL_REPEAT_TIME, (Object)reminder.getRepeatMode()).addValue(COL_CURRUSERID, (Object)reminder.getUnitId()).addValue(COL_BRFOREDEADLINE, (Object)reminder.getDayBeforeDeadline()).addValue(COL_AFTERDEADLINE, (Object)reminder.getDayAfterDeadline()).addValue(COL_SEND_TYPE, (Object)reminder.getSendTime()).addValue(COL_SHOW_SEND_TIME, (Object)reminder.getShowSendTime()).addValue(COL_FREQUENCY_MODE, (Object)reminder.getFrequencyMode()).addValue(COL_TRIGER_CORN, (Object)reminder.getTrigerCorn()).addValue(COL_EXECUTENUMS, (Object)reminder.getExecuteNums()).addValue(COL_NEXTSEND_TIME, (Object)reminder.getNextSendTime()).addValue(COL_VALID_START_TIME, (Object)reminder.getValidStartTime()).addValue(COL_VALID_END_TIME, (Object)reminder.getValidEndTime()).addValue(COL_REGULAR_TIME, (Object)reminder.getRegularTime()).addValue(COL_PERIOD_TYPE, (Object)reminder.getPeriodType()).addValue(COL_FORM_RANGE, (Object)reminder.getFormRange()).addValue(COL_GROUP_RANGE, (Object)reminder.getGroupRange()).addValue(COL_WORKFLOWTYPE, (Object)reminder.getWorkFlowType()).addValue(COL_REM_ID, (Object)reminder.getId());
        this.template.update(sql, (SqlParameterSource)parameterSource);
        List<String> roleIds = this.findRoleIds(reminder.getId());
        List<String> selectRoleIds = reminder.getRoleIds();
        if (!roleIds.containsAll(selectRoleIds) || !selectRoleIds.containsAll(roleIds)) {
            this.deleteRoleIds(reminder.getId());
            this.saveRole(reminder);
        }
        if (!(unitIds = this.findUnitIds(reminder.getId())).containsAll(selectUnitIds = reminder.getUnitIds()) || !selectUnitIds.containsAll(unitIds)) {
            this.deleteUnitIds(reminder.getId());
            this.saveUnit(reminder);
        }
        if ((ccInfos = this.findCcInfos(reminder.getId())) != null && ccInfos.size() != 0) {
            this.updateCc(reminder);
        } else {
            this.saveCc(reminder);
        }
        List<String> formKeys = this.findFormKeys(reminder.getId());
        if (formKeys != null && formKeys.size() != 0) {
            this.updateFormKeys(reminder);
        } else {
            this.saveFormKey(reminder);
        }
    }

    public void deleteRoleIds(String remId) {
        String conditionSql = String.format("%s = ?", COL_REM_ID);
        String sql = ((SQL)((SQL)new SQL().DELETE_FROM(TABLE_REMINDER_ROLE)).WHERE(conditionSql)).toString();
        this.getJdbcTemplate().update(sql, new Object[]{remId});
    }

    public void deleteUnitIds(String remId) {
        String conditionSql = String.format("%s = ?", COL_REM_ID);
        String sql = ((SQL)((SQL)new SQL().DELETE_FROM(TABLE_REMINDER_UNIT)).WHERE(conditionSql)).toString();
        this.getJdbcTemplate().update(sql, new Object[]{remId});
    }

    @Override
    public void updateRegularTime(String remId, String regularTime) {
        String sql = ((SQL)((SQL)((SQL)new SQL().UPDATE(TABLE_REMINDER)).SET("regularTime=:regularTime")).WHERE("rem_id=:rem_id")).toString();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue(COL_REM_ID, (Object)remId).addValue(COL_REGULAR_TIME, (Object)regularTime);
        this.template.update(sql, (SqlParameterSource)parameterSource);
    }

    @Override
    public boolean findUserState(String userId) {
        String sql = ((SQL)((SQL)((SQL)new SQL().SELECT("ENABLED")).FROM("np_user")).WHERE("ID=:ID")).toString();
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ID", (Object)userId);
        List enabled = this.template.query(sql, (SqlParameterSource)parameters, (rs, i) -> rs.getString(1));
        return Integer.parseInt((String)enabled.get(0)) == 1;
    }

    @Override
    public String findUserName(String userId) {
        String sql = ((SQL)((SQL)((SQL)new SQL().SELECT("NAME")).FROM("np_user")).WHERE("ID=:ID")).toString();
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ID", (Object)userId);
        List userIds = this.template.query(sql, (SqlParameterSource)parameters, (rs, i) -> rs.getString(1));
        return (String)userIds.get(0);
    }

    @Override
    public String findUserEmail(String userId) {
        String sql = ((SQL)((SQL)((SQL)new SQL().SELECT("EMAIL")).FROM("np_user")).WHERE("ID=:ID")).toString();
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ID", (Object)userId);
        List emails = this.template.query(sql, (SqlParameterSource)parameters, (rs, i) -> rs.getString(1));
        return (String)emails.get(0);
    }
}

