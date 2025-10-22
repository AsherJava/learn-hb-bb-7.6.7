/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobsException
 *  com.jiuqi.bi.core.jobs.bean.JobInstanceBean
 *  com.jiuqi.bi.core.jobs.manager.JobOperationManager
 *  com.jiuqi.bi.core.jobs.monitor.JobMonitorManager
 *  com.jiuqi.bi.core.jobs.monitor.State
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJobManager
 *  com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyJobInfo
 *  com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyJobRunner
 *  com.jiuqi.bi.util.Html
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTask
 *  com.jiuqi.np.asynctask.AsyncTaskTypeCollecter
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.asynctask.exception.NpAsyncTaskExecption
 *  com.jiuqi.np.asynctask.exception.ParameterConversionException
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  com.jiuqi.nvwa.jobmanager.api.dto.RealTimeQueryParam
 *  com.jiuqi.nvwa.jobmanager.realtime.RealTimeErrorEnum
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.np.asynctask.impl.dao;

import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.bean.JobInstanceBean;
import com.jiuqi.bi.core.jobs.manager.JobOperationManager;
import com.jiuqi.bi.core.jobs.monitor.JobMonitorManager;
import com.jiuqi.bi.core.jobs.monitor.State;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJobManager;
import com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyJobInfo;
import com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyJobRunner;
import com.jiuqi.bi.util.Html;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTask;
import com.jiuqi.np.asynctask.AsyncTaskTypeCollecter;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.asynctask.dao.AsyncTaskDao;
import com.jiuqi.np.asynctask.exception.NpAsyncTaskExecption;
import com.jiuqi.np.asynctask.exception.ParameterConversionException;
import com.jiuqi.np.asynctask.impl.AsyncTaskImpl;
import com.jiuqi.np.asynctask.util.BinaryStreamFactory;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.nvwa.jobmanager.api.dto.RealTimeQueryParam;
import com.jiuqi.nvwa.jobmanager.realtime.RealTimeErrorEnum;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.CollectionUtils;

public class AsyncTaskDaoImpl
implements AsyncTaskDao {
    private static final Logger logger = LoggerFactory.getLogger(AsyncTaskDaoImpl.class);
    @Autowired
    private AsyncTaskTypeCollecter collecter;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;
    private final String SYSTEM_NAME;
    private static final Long OVERTIME_MINUTE = 5L;
    private static final Long AUTOINSERTHISTORYDATA_DAY = 7L;
    private static final Long AUTODELETEHISTORYDATA_DAY = 30L;
    private static final Long TODO_DAY = 4L;
    private static final int PAGE_SIZE = 1000;
    static final String TABLE_NAME = "NP_ASYNCTASK";
    static final String COMPLETEFLAG_TABLE_NAME = "NP_ASYNCTASK_COMPLETEFLAG";
    static final String FIELD_NAME_INSERT = "TASK_ID, TASKPOOL_TYPE, DEPEND_TASK_ID, CREATEUSER_ID, STATE, PRIORITY, ARGS, CREATE_TIME, WAITING_TIME, DIMENSION_IDENTIFY, CONTEXT_, SYSTEM_NAME, PROCESS_TIME, SERVE_ID, EFFECT_TIME, EFFECT_TIME_LONG, TASK_KEY, FORMSCHEME_KEY";
    static final String FIELD_NAME_ALL = "TASK_ID, TASKPOOL_TYPE, DEPEND_TASK_ID, PROCESS, CREATEUSER_ID, STATE, PRIORITY, ARGS, CREATE_TIME, WAITING_TIME, PROCESS_TIME, FINISH_TIME, CANCEL_TIME, RESULT, SERVE_ID, EFFECT_TIME, DIMENSION_IDENTIFY, CONTEXT_";
    static final String FIELD_NAME_WITHOUTCLOB = "TASK_ID, TASK_KEY, FORMSCHEME_KEY, TASKPOOL_TYPE, DEPEND_TASK_ID, PROCESS, CREATEUSER_ID, STATE, PRIORITY, CREATE_TIME, WAITING_TIME, PROCESS_TIME, FINISH_TIME, CANCEL_TIME, RESULT, SERVE_ID, EFFECT_TIME, DIMENSION_IDENTIFY";
    static final String FIELD_NAME_FOR_HISTORY = "TASK_ID, TASK_KEY, FORMSCHEME_KEY, TASKPOOL_TYPE, DEPEND_TASK_ID, PROCESS, CREATEUSER_ID, STATE, PRIORITY, ARGS, CREATE_TIME, WAITING_TIME, PROCESS_TIME, FINISH_TIME, CANCEL_TIME, RESULT, SERVE_ID, EFFECT_TIME, EFFECT_TIME_LONG, DIMENSION_IDENTIFY, CONTEXT_, DETAIL, ERROR_, SYSTEM_NAME";
    static final String WHERE_EFFECT_TIME_LONG = " AND EFFECT_TIME_LONG < ? ";
    static final String SELECT = "SELECT ";
    static final String UPDATE = "UPDATE ";
    static final String TASKPOOLTYPE_CONDITION = " AND TASKPOOL_TYPE =?";
    static final String FLAGANDTIME_CONDITION = " WHERE COMPLETE_FLAG = 0 AND CREATE_TIME > ?";
    static final String SELECT_TASKID_FROM = "SELECT TASK_ID FROM ";
    static final RowMapper<AsyncTask> OBJECT_MAPPER = new RowMapper<AsyncTask>(){

        public AsyncTask mapRow(ResultSet rs, int rowNum) throws SQLException {
            int colIndex = 0;
            AsyncTaskImpl task = new AsyncTaskImpl();
            task.setTaskId(rs.getString(++colIndex));
            task.setTaskPoolType(rs.getString(++colIndex));
            task.setDependTaskId(rs.getString(++colIndex));
            task.setProcess(rs.getDouble(++colIndex));
            task.setCreateUserId(rs.getString(++colIndex));
            task.setState(TaskState.getTaskStateByValue((int)rs.getInt(++colIndex)));
            task.setPriority(rs.getInt(++colIndex));
            task.setArgs(rs.getString(++colIndex) == null ? null : rs.getString(colIndex));
            task.setCreateTime(null == rs.getTimestamp(++colIndex) ? null : rs.getTimestamp(colIndex).toInstant());
            task.setWaitingTime(null == rs.getTimestamp(++colIndex) ? null : rs.getTimestamp(colIndex).toInstant());
            task.setProcessTime(null == rs.getTimestamp(++colIndex) ? null : rs.getTimestamp(colIndex).toInstant());
            task.setFinishTime(null == rs.getTimestamp(++colIndex) ? null : rs.getTimestamp(colIndex).toInstant());
            task.setCancelTime(null == rs.getTimestamp(++colIndex) ? null : rs.getTimestamp(colIndex).toInstant());
            task.setResult(rs.getString(++colIndex));
            task.setServeId(rs.getString(++colIndex));
            task.setEffectTime(null == rs.getTimestamp(++colIndex) ? null : rs.getTimestamp(colIndex).toInstant());
            task.setDimensionIdentify(rs.getString(++colIndex));
            task.setContext(rs.getString(++colIndex) == null ? null : rs.getString(colIndex));
            return task;
        }
    };
    static final RowMapper<AsyncTask> OBJECT_MAPPER_2 = new RowMapper<AsyncTask>(){

        public AsyncTask mapRow(ResultSet rs, int rowNum) throws SQLException {
            int colIndex = 0;
            AsyncTaskImpl task = new AsyncTaskImpl();
            task.setTaskId(rs.getString(++colIndex));
            task.setProcess(rs.getDouble(++colIndex));
            task.setState(TaskState.getTaskStateByValue((int)rs.getInt(++colIndex)));
            task.setProcessTime(null == rs.getTimestamp(++colIndex) ? null : rs.getTimestamp(colIndex).toInstant());
            task.setTaskPoolType(rs.getString(++colIndex));
            task.setWaitingTime(null == rs.getTimestamp(++colIndex) ? null : rs.getTimestamp(colIndex).toInstant());
            task.setPriority(rs.getInt(++colIndex));
            task.setResult(rs.getString(++colIndex));
            return task;
        }
    };
    static final RowMapper<AsyncTask> OBJECT_WITHOUTCLOB_MAPPER = (rs, rowNum) -> {
        int colIndex = 0;
        AsyncTaskImpl task = new AsyncTaskImpl();
        task.setTaskId(rs.getString(++colIndex));
        task.setTaskKey(rs.getString(++colIndex));
        task.setFormSchemeKey(rs.getString(++colIndex));
        task.setTaskPoolType(rs.getString(++colIndex));
        task.setDependTaskId(rs.getString(++colIndex));
        task.setProcess(rs.getDouble(++colIndex));
        task.setCreateUserId(rs.getString(++colIndex));
        task.setState(TaskState.getTaskStateByValue((int)rs.getInt(++colIndex)));
        task.setPriority(rs.getInt(++colIndex));
        task.setCreateTime(null == rs.getTimestamp(++colIndex) ? null : rs.getTimestamp(colIndex).toInstant());
        task.setWaitingTime(null == rs.getTimestamp(++colIndex) ? null : rs.getTimestamp(colIndex).toInstant());
        task.setProcessTime(null == rs.getTimestamp(++colIndex) ? null : rs.getTimestamp(colIndex).toInstant());
        task.setFinishTime(null == rs.getTimestamp(++colIndex) ? null : rs.getTimestamp(colIndex).toInstant());
        task.setCancelTime(null == rs.getTimestamp(++colIndex) ? null : rs.getTimestamp(colIndex).toInstant());
        task.setResult(rs.getString(++colIndex));
        task.setServeId(rs.getString(++colIndex));
        task.setEffectTime(null == rs.getTimestamp(++colIndex) ? null : rs.getTimestamp(colIndex).toInstant());
        task.setDimensionIdentify(rs.getString(++colIndex));
        return task;
    };
    static final RowMapper<AsyncTask> SHORTER_OBJECT_MAPPER = new RowMapper<AsyncTask>(){

        public AsyncTask mapRow(ResultSet rs, int rowNum) throws SQLException {
            int colIndex = 0;
            AsyncTaskImpl task = new AsyncTaskImpl();
            task.setTaskId(rs.getString(++colIndex));
            task.setState(TaskState.getTaskStateByValue((int)rs.getInt(++colIndex)));
            return task;
        }
    };
    static final RowMapper<String> DETAIL_MAPPER = new RowMapper<String>(){

        public String mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getString(1) == null ? null : rs.getString(1);
        }
    };

    public AsyncTaskDaoImpl(String serverName) {
        this.SYSTEM_NAME = serverName;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static byte[] readStreamToBytes(InputStream in) {
        byte[] resByte = null;
        if (in == null) {
            return null;
        }
        BinaryStreamFactory factory = new BinaryStreamFactory();
        try {
            resByte = factory.read(in);
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        finally {
            factory.closeStream();
        }
        return resByte;
    }

    @Override
    public void insert(AsyncTask task) {
        String sql = "INSERT INTO NP_ASYNCTASK( TASK_ID, TASKPOOL_TYPE, DEPEND_TASK_ID, CREATEUSER_ID, STATE, PRIORITY, ARGS, CREATE_TIME, WAITING_TIME, DIMENSION_IDENTIFY, CONTEXT_, SYSTEM_NAME, PROCESS_TIME, SERVE_ID, EFFECT_TIME, EFFECT_TIME_LONG, TASK_KEY, FORMSCHEME_KEY ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Object[] args = new Object[]{task.getTaskId(), task.getTaskPoolType(), task.getDependTaskId(), task.getCreateUserId(), task.getState().getValue(), task.getPriority(), SimpleParamConverter.SerializationUtils.serializeToString(task.getArgs()), Timestamp.from(task.getCreateTime()), Timestamp.from(task.getWaitingTime()), task.getDimensionIdentify(), SimpleParamConverter.SerializationUtils.serializeToString(task.getContext()), this.SYSTEM_NAME, task.getProcessTime() == null ? null : Timestamp.from(task.getProcessTime()), task.getServeId(), task.getEffectTime() == null ? null : Timestamp.from(task.getEffectTime()), task.getEffectTimeLong(), task.getTaskKey(), task.getFormSchemeKey()};
        this.update(sql, args);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private int update(String sql, Object[] args) {
        try (Connection connection = this.dataSource.getConnection();){
            int n = DataEngineUtil.executeUpdate((Connection)connection, (String)sql, (Object[])args);
            return n;
        }
        catch (Exception e) {
            throw new NpAsyncTaskExecption("\u5f02\u6b65\u4efb\u52a1\u5185\u90e8\u9519\u8bef", (Throwable)e);
        }
    }

    private void update(String sql, List<Object[]> args) {
        try (Connection connection = this.dataSource.getConnection();){
            DataEngineUtil.batchUpdate((Connection)connection, (String)sql, args);
        }
        catch (Exception e) {
            throw new NpAsyncTaskExecption("\u5f02\u6b65\u4efb\u52a1\u5185\u90e8\u9519\u8bef", (Throwable)e);
        }
    }

    @Override
    public void updateProgressAndMessage(String taskId, double progress, String message) {
        String sql = "UPDATE NP_ASYNCTASK SET PROCESS=? , RESULT=? WHERE TASK_ID=?";
        Object[] args = new Object[]{progress, message, taskId};
        this.update(sql, args);
    }

    @Override
    public void updateResultAndDetail(TaskState state, String taskId, String result, Object detail) {
        StringBuilder sql = new StringBuilder("UPDATE NP_ASYNCTASK SET STATE=? , PROCESS = ? ,");
        int process = 0;
        if (state.equals((Object)TaskState.WAITING) || state.equals((Object)TaskState.OVERTIME)) {
            sql.append("WAITING_TIME");
        } else if (state.equals((Object)TaskState.PROCESSING)) {
            sql.append("PROCESS_TIME");
        } else if (state.equals((Object)TaskState.CANCELED) || state.equals((Object)TaskState.CANCELING)) {
            sql.append("CANCEL_TIME");
        } else if (state.equals((Object)TaskState.FINISHED) || state.equals((Object)TaskState.ERROR)) {
            sql.append("FINISH_TIME");
            process = 1;
        }
        sql.append("=? , RESULT=? , DETAIL=? WHERE TASK_ID=?");
        if (state.equals((Object)TaskState.FINISHED)) {
            sql.append(" AND STATE!=").append(TaskState.ERROR.getValue());
        }
        Object[] args = new Object[]{state.getValue(), process, Timestamp.from(Instant.now()), result.length() > 900 ? result.substring(0, 900) : result, SimpleParamConverter.SerializationUtils.serializeToString(detail), taskId};
        this.update(sql.toString(), args);
    }

    @Override
    public void updateState(TaskState state, String taskId) {
        StringBuilder sql = new StringBuilder("UPDATE NP_ASYNCTASK SET STATE=? , ");
        if (state.equals((Object)TaskState.WAITING) || state.equals((Object)TaskState.OVERTIME)) {
            sql.append("WAITING_TIME");
        } else if (state.equals((Object)TaskState.PROCESSING)) {
            sql.append("PROCESS_TIME");
        } else if (state.equals((Object)TaskState.CANCELED) || state.equals((Object)TaskState.CANCELING)) {
            sql.append("CANCEL_TIME");
        } else if (state.equals((Object)TaskState.FINISHED) || state.equals((Object)TaskState.ERROR)) {
            sql.append("FINISH_TIME");
        }
        sql.append("=? WHERE TASK_ID=?");
        Object[] args = new Object[]{state.getValue(), Timestamp.from(Instant.now()), taskId};
        this.update(sql.toString(), args);
    }

    @Override
    public void updateEffectTime(String serveId) {
        Object[] args = new Object[]{serveId, TaskState.PROCESSING.getValue()};
        String querySql = "SELECT TASK_ID FROM NP_ASYNCTASK WHERE SERVE_ID = ? AND STATE = ?";
        List taskIds = this.jdbcTemplate.queryForList(querySql, String.class, args);
        long timeMillis = System.currentTimeMillis();
        ArrayList<Object[]> argList = new ArrayList<Object[]>();
        for (String taskId : taskIds) {
            Object[] arg = new Object[]{timeMillis, taskId, serveId, TaskState.PROCESSING.getValue()};
            argList.add(arg);
        }
        if (!argList.isEmpty()) {
            String sql = "UPDATE NP_ASYNCTASK SET EFFECT_TIME_LONG = ? WHERE TASK_ID = ? AND SERVE_ID = ? AND STATE = ?";
            this.update(sql, argList);
        }
    }

    @Override
    public void updateEffectTime(String serveId, String taskId) {
        String sql = "UPDATE NP_ASYNCTASK SET SERVE_ID=? , EFFECT_TIME=? , EFFECT_TIME_LONG=? WHERE TASK_ID=?";
        Object[] args = new Object[]{serveId, Timestamp.from(Instant.now()), System.currentTimeMillis(), taskId};
        this.update(sql, args);
    }

    @Override
    public void updateOverTimeState() {
        try {
            Object[] queryArgs = new Object[]{TaskState.PROCESSING.getValue(), System.currentTimeMillis() - OVERTIME_MINUTE * 60L * 1000L};
            String querySql = "SELECT TASK_ID FROM NP_ASYNCTASK WHERE STATE = ? AND EFFECT_TIME_LONG < ? AND SERVE_ID IS NOT NULL";
            List taskIds = this.jdbcTemplate.queryForList(querySql, String.class, queryArgs);
            if (!taskIds.isEmpty()) {
                ArrayList<Object[]> list = new ArrayList<Object[]>();
                for (String taskId : taskIds) {
                    Object[] args = new Object[6];
                    list.add(args);
                    args[0] = TaskState.OVERTIME.getValue();
                    args[1] = Timestamp.from(Instant.now());
                    args[2] = null;
                    args[3] = taskId;
                    args[4] = TaskState.PROCESSING.getValue();
                    args[5] = System.currentTimeMillis() - OVERTIME_MINUTE * 60L * 1000L;
                }
                String sql = "UPDATE NP_ASYNCTASK SET STATE=? , WAITING_TIME=? , SERVE_ID=? WHERE TASK_ID = ? AND STATE = ? AND SERVE_ID IS NOT NULL AND EFFECT_TIME_LONG < ? ";
                this.update(sql, list);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void updateOverTimeState(String severId) {
        try {
            String getsql = "SELECT TASK_ID FROM NP_ASYNCTASK WHERE STATE = 1 AND SERVE_ID = ? AND TASK_ID NOT IN (SELECT TASK_ID FROM NP_ASYNCTASK_SIMPLE_QUEUE)";
            List taskIds = this.jdbcTemplate.queryForList(getsql, String.class, new Object[]{severId});
            if (!CollectionUtils.isEmpty(taskIds)) {
                ArrayList<Object[]> list = new ArrayList<Object[]>();
                String sql = "UPDATE NP_ASYNCTASK SET STATE=? , WAITING_TIME=? ,SERVE_ID=? WHERE TASK_ID = ?";
                for (String taskId : taskIds) {
                    Object[] args = new Object[]{TaskState.OVERTIME.getValue(), Timestamp.from(Instant.now()), null, taskId};
                    list.add(args);
                }
                this.update(sql, list);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void deleteHistoryData(long time) {
        StringBuilder sql = new StringBuilder("DELETE FROM NP_ASYNCTASK WHERE");
        sql.append(" STATE IN( ?, ?, ?, ?, ?)");
        sql.append(WHERE_EFFECT_TIME_LONG);
        Object[] args = new Object[]{TaskState.CANCELED.getValue(), TaskState.CANCELING.getValue(), TaskState.FINISHED.getValue(), TaskState.ERROR.getValue(), TaskState.OVERTIME.getValue(), time - AUTOINSERTHISTORYDATA_DAY * 24L * 60L * 60L * 1000L};
        this.update(sql.toString(), args);
    }

    @Override
    public void deleteHistoryTableData(long time) {
        StringBuilder sql = new StringBuilder("DELETE FROM NP_ASYNCTASK_HISTORY WHERE");
        sql.append(" STATE IN( ?, ?, ?, ?, ?)");
        sql.append(WHERE_EFFECT_TIME_LONG);
        Object[] args = new Object[]{TaskState.CANCELED.getValue(), TaskState.CANCELING.getValue(), TaskState.FINISHED.getValue(), TaskState.ERROR.getValue(), TaskState.OVERTIME.getValue(), time - AUTODELETEHISTORYDATA_DAY * 24L * 60L * 60L * 1000L};
        this.update(sql.toString(), args);
    }

    @Override
    public void insertHistoryData(long time) {
        StringBuilder sql = new StringBuilder("INSERT INTO NP_ASYNCTASK_HISTORY (TASK_ID, TASK_KEY, FORMSCHEME_KEY, TASKPOOL_TYPE, DEPEND_TASK_ID, PROCESS, CREATEUSER_ID, STATE, PRIORITY, ARGS, CREATE_TIME, WAITING_TIME, PROCESS_TIME, FINISH_TIME, CANCEL_TIME, RESULT, SERVE_ID, EFFECT_TIME, EFFECT_TIME_LONG, DIMENSION_IDENTIFY, CONTEXT_, DETAIL, ERROR_, SYSTEM_NAME) SELECT TASK_ID, TASK_KEY, FORMSCHEME_KEY, TASKPOOL_TYPE, DEPEND_TASK_ID, PROCESS, CREATEUSER_ID, STATE, PRIORITY, ARGS, CREATE_TIME, WAITING_TIME, PROCESS_TIME, FINISH_TIME, CANCEL_TIME, RESULT, SERVE_ID, EFFECT_TIME, EFFECT_TIME_LONG, DIMENSION_IDENTIFY, CONTEXT_, DETAIL, ERROR_, SYSTEM_NAME FROM NP_ASYNCTASK A WHERE");
        sql.append(" A.STATE IN( ?, ?, ?, ?, ?)");
        sql.append(" AND A.EFFECT_TIME_LONG < ? ");
        Object[] args = new Object[]{TaskState.CANCELED.getValue(), TaskState.CANCELING.getValue(), TaskState.FINISHED.getValue(), TaskState.ERROR.getValue(), TaskState.OVERTIME.getValue(), time - AUTOINSERTHISTORYDATA_DAY * 24L * 60L * 60L * 1000L};
        this.update(sql.toString(), args);
    }

    @Override
    public List<String> queryTaskIdsToClear(String taskType, long time) {
        StringBuilder sql = new StringBuilder("SELECT TASK_ID FROM NP_ASYNCTASK WHERE ");
        sql.append(" STATE IN( ?, ?, ?, ?)");
        sql.append(WHERE_EFFECT_TIME_LONG);
        sql.append(TASKPOOLTYPE_CONDITION);
        Object[] args = new Object[]{TaskState.CANCELED.getValue(), TaskState.FINISHED.getValue(), TaskState.ERROR.getValue(), TaskState.OVERTIME.getValue(), time - AUTOINSERTHISTORYDATA_DAY * 24L * 60L * 60L * 1000L, taskType};
        List list = this.jdbcTemplate.queryForList(sql.toString(), args, String.class);
        if (null != list && list.size() > 0) {
            return list;
        }
        return null;
    }

    @Override
    public AsyncTask query(String taskId) {
        if (taskId.startsWith("nr-")) {
            ArrayList<String> args = new ArrayList<String>();
            String sql = "SELECT TASK_ID, TASKPOOL_TYPE, DEPEND_TASK_ID, PROCESS, CREATEUSER_ID, STATE, PRIORITY, ARGS, CREATE_TIME, WAITING_TIME, PROCESS_TIME, FINISH_TIME, CANCEL_TIME, RESULT, SERVE_ID, EFFECT_TIME, DIMENSION_IDENTIFY, CONTEXT_ FROM NP_ASYNCTASK WHERE TASK_ID=? ";
            args.add(taskId);
            Optional task = this.jdbcTemplate.query(sql, args.toArray(), OBJECT_MAPPER).stream().findFirst();
            if (task.isPresent()) {
                this.ConvertArgs((AsyncTask)task.get());
                return (AsyncTask)task.get();
            }
            return null;
        }
        return this.queryRealTimeJobInstance(taskId, false);
    }

    @Override
    public AsyncTask querySimpleTask(String taskId) {
        if (taskId.startsWith("nr-")) {
            ArrayList<String> args = new ArrayList<String>();
            String sql = "SELECT TASK_ID,PROCESS,STATE,PROCESS_TIME,TASKPOOL_TYPE,WAITING_TIME,PRIORITY,RESULT FROM NP_ASYNCTASK WHERE TASK_ID=? ";
            args.add(taskId);
            Optional task = this.jdbcTemplate.query(sql, args.toArray(), OBJECT_MAPPER_2).stream().findFirst();
            return task.orElse(null);
        }
        return this.queryRealTimeJobInstance(taskId, true);
    }

    @Override
    public Map<String, AsyncTask> batchQuerySimpleTasks(List<String> taskIds) {
        HashMap<String, AsyncTask> asyncTaskMap = new HashMap<String, AsyncTask>();
        ArrayList asyncTaskIds = new ArrayList();
        ArrayList realTimeJobIds = new ArrayList();
        taskIds.forEach(taskId -> {
            if (taskId.startsWith("nr-")) {
                asyncTaskIds.add(taskId);
            } else {
                realTimeJobIds.add(taskId);
            }
        });
        if (!asyncTaskIds.isEmpty()) {
            StringBuilder sql = new StringBuilder("SELECT TASK_ID,PROCESS,STATE,PROCESS_TIME,TASKPOOL_TYPE,WAITING_TIME,PRIORITY,RESULT FROM NP_ASYNCTASK WHERE TASK_ID IN (");
            Object[] args = new Object[asyncTaskIds.size()];
            for (int index = 0; index < taskIds.size(); ++index) {
                if (index > 0) {
                    sql.append(",");
                }
                sql.append("?");
                args[index] = taskIds.get(index);
            }
            sql.append(")");
            List asyncTasks = this.jdbcTemplate.query(sql.toString(), OBJECT_MAPPER_2, args);
            asyncTasks.forEach(asyncTask -> asyncTaskMap.put(asyncTask.getTaskId(), (AsyncTask)asyncTask));
        }
        if (!realTimeJobIds.isEmpty()) {
            try {
                Map jobInstanceBeans;
                Map immediatelyJobInfos = ImmediatelyJobRunner.getInstance().getJobInfos(realTimeJobIds);
                if (Objects.nonNull(immediatelyJobInfos)) {
                    immediatelyJobInfos.forEach((key, immediatelyJobInfo) -> {
                        if (immediatelyJobInfo != null) {
                            AsyncTaskImpl asyncTask = new AsyncTaskImpl();
                            asyncTask.setTaskId(immediatelyJobInfo.getInstanceId());
                            asyncTask.setPublishType("immediately");
                            asyncTask.setProcess((double)immediatelyJobInfo.getProgress() / 100.0);
                            asyncTask.setState(TaskState.convertRealTimeTaskState((int)immediatelyJobInfo.getState(), (int)immediatelyJobInfo.getResult()));
                            asyncTask.setResult(immediatelyJobInfo.getPrompt());
                            asyncTask.setDetail(null);
                            asyncTaskMap.put((String)key, asyncTask);
                        }
                    });
                }
                if (Objects.nonNull(jobInstanceBeans = RealTimeJobManager.getInstance().getMonitorManager().getJobInstanceByGuid(realTimeJobIds, true))) {
                    jobInstanceBeans.forEach((key, jobInstanceBean) -> asyncTaskMap.put((String)key, this.jobInstanceBeanToAsyncTask((JobInstanceBean)jobInstanceBean, true)));
                }
            }
            catch (JobsException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return asyncTaskMap;
    }

    @Override
    public List<AsyncTask> queryToDo() {
        long currentTimeMillis = System.currentTimeMillis();
        StringBuilder sql = new StringBuilder("SELECT TASK_ID, TASKPOOL_TYPE, DEPEND_TASK_ID, PROCESS, CREATEUSER_ID, STATE, PRIORITY, ARGS, CREATE_TIME, WAITING_TIME, PROCESS_TIME, FINISH_TIME, CANCEL_TIME, RESULT, SERVE_ID, EFFECT_TIME, DIMENSION_IDENTIFY, CONTEXT_ FROM NP_ASYNCTASK WHERE CREATEUSER_ID=? AND STATE IN (?,?,?,?) AND COMPLETE_FLAG=0 AND EFFECT_TIME_LONG > ? ORDER BY CREATE_TIME");
        Object[] args = new Object[]{NpContextHolder.getContext().getUser().getId(), TaskState.PROCESSING.getValue(), TaskState.WAITING.getValue(), TaskState.FINISHED.getValue(), TaskState.ERROR.getValue(), currentTimeMillis - TODO_DAY * 24L * 60L * 60L * 1000L};
        List tasks = this.jdbcTemplate.query(sql.toString(), args, OBJECT_MAPPER);
        tasks.forEach(this::ConvertArgs);
        ArrayList<JobInstanceBean> jobs = new ArrayList<JobInstanceBean>();
        RealTimeQueryParam queryParam = new RealTimeQueryParam();
        queryParam.setPageSize(Integer.valueOf(1000));
        queryParam.setStates(State.RUNNING + "," + State.FINISHED + "," + State.WAITING);
        queryParam.setStartTime(Long.valueOf(System.currentTimeMillis() - TODO_DAY * 24L * 60L * 60L * 1000L));
        try {
            jobs.addAll(this.getAllInstancesByCondition(queryParam, NpContextHolder.getContext().getUserId(), new ArrayList<String>()));
        }
        catch (JQException e) {
            logger.error(e.getMessage(), e);
        }
        if (!jobs.isEmpty()) {
            String queryCompleteFlagSql = "SELECT TASK_ID FROM NP_ASYNCTASK_COMPLETEFLAG WHERE COMPLETE_FLAG = 0 AND CREATE_TIME > ?";
            Object[] qcArgs = new Object[1];
            args[0] = currentTimeMillis - TODO_DAY * 24L * 60L * 60L * 1000L;
            List taskIds = this.jdbcTemplate.queryForList("SELECT TASK_ID FROM NP_ASYNCTASK_COMPLETEFLAG WHERE COMPLETE_FLAG = 0 AND CREATE_TIME > ?", qcArgs, String.class);
            jobs.forEach(jobInstanceBean -> {
                for (String taskId : taskIds) {
                    if (!jobInstanceBean.getInstanceId().equals(taskId)) continue;
                    tasks.add(this.jobInstanceBeanToAsyncTask((JobInstanceBean)jobInstanceBean, true));
                    taskIds.remove(taskId);
                    break;
                }
            });
            tasks.sort(Comparator.comparing(AsyncTask::getCreateTime));
        }
        return tasks;
    }

    @Override
    public List<AsyncTask> queryTaskToDoWithoutClob() {
        long currentTimeMillis = System.currentTimeMillis();
        StringBuilder sql = new StringBuilder("SELECT TASK_ID, TASK_KEY, FORMSCHEME_KEY, TASKPOOL_TYPE, DEPEND_TASK_ID, PROCESS, CREATEUSER_ID, STATE, PRIORITY, CREATE_TIME, WAITING_TIME, PROCESS_TIME, FINISH_TIME, CANCEL_TIME, RESULT, SERVE_ID, EFFECT_TIME, DIMENSION_IDENTIFY FROM NP_ASYNCTASK WHERE CREATEUSER_ID=? AND STATE IN (?,?,?,?) AND COMPLETE_FLAG=0 AND EFFECT_TIME_LONG > ? ORDER BY CREATE_TIME");
        Object[] args = new Object[]{NpContextHolder.getContext().getUser().getId(), TaskState.PROCESSING.getValue(), TaskState.WAITING.getValue(), TaskState.FINISHED.getValue(), TaskState.ERROR.getValue(), currentTimeMillis - TODO_DAY * 24L * 60L * 60L * 1000L};
        List tasks = this.jdbcTemplate.query(sql.toString(), args, OBJECT_WITHOUTCLOB_MAPPER);
        tasks.forEach(this::ConvertArgs);
        ArrayList<JobInstanceBean> jobs = new ArrayList<JobInstanceBean>();
        RealTimeQueryParam queryParam = new RealTimeQueryParam();
        queryParam.setPageSize(Integer.valueOf(1000));
        queryParam.setStates(State.RUNNING + "," + State.FINISHED + "," + State.WAITING);
        queryParam.setStartTime(Long.valueOf(System.currentTimeMillis() - TODO_DAY * 24L * 60L * 60L * 1000L));
        try {
            jobs.addAll(this.getAllInstancesByCondition(queryParam, NpContextHolder.getContext().getUserId(), new ArrayList<String>()));
        }
        catch (JQException e) {
            logger.error(e.getMessage(), e);
        }
        if (!jobs.isEmpty()) {
            String queryCompleteFlagSql = "SELECT TASK_ID FROM NP_ASYNCTASK_COMPLETEFLAG WHERE COMPLETE_FLAG = 0 AND CREATE_TIME > ?";
            Object[] qcArgs = new Object[]{currentTimeMillis - TODO_DAY * 24L * 60L * 60L * 1000L};
            List taskIds = this.jdbcTemplate.queryForList("SELECT TASK_ID FROM NP_ASYNCTASK_COMPLETEFLAG WHERE COMPLETE_FLAG = 0 AND CREATE_TIME > ?", qcArgs, String.class);
            jobs.forEach(jobInstanceBean -> {
                for (String taskId : taskIds) {
                    if (!jobInstanceBean.getInstanceId().equals(taskId)) continue;
                    tasks.add(this.jobInstanceBeanToAsyncTask((JobInstanceBean)jobInstanceBean, false));
                    taskIds.remove(taskId);
                    break;
                }
            });
            tasks.sort(Comparator.comparing(AsyncTask::getCreateTime));
        }
        return tasks;
    }

    @Override
    public List<AsyncTask> queryTaskToDoWithoutClob(String taskKey) {
        return this.queryTaskToDoWithoutClob(taskKey, NpContextHolder.getContext().getUser().getId());
    }

    @Override
    public List<AsyncTask> queryTaskToDoWithoutClob(String taskKey, List<String> taskPoolTypes) {
        return this.queryTaskToDoWithoutClob(taskKey, NpContextHolder.getContext().getUser().getId(), taskPoolTypes);
    }

    @Override
    public List<AsyncTask> queryTaskToDoWithoutClob(String taskKey, String createUserId) {
        return this.queryTaskToDoWithoutClob(taskKey, createUserId, new ArrayList<String>());
    }

    @Override
    public List<AsyncTask> queryTaskToDoWithoutClob(String taskKey, String createUserId, List<String> taskPoolTypes) {
        long currentTimeMillis = System.currentTimeMillis();
        StringBuilder sql = new StringBuilder("SELECT TASK_ID, TASK_KEY, FORMSCHEME_KEY, TASKPOOL_TYPE, DEPEND_TASK_ID, PROCESS, CREATEUSER_ID, STATE, PRIORITY, CREATE_TIME, WAITING_TIME, PROCESS_TIME, FINISH_TIME, CANCEL_TIME, RESULT, SERVE_ID, EFFECT_TIME, DIMENSION_IDENTIFY FROM NP_ASYNCTASK WHERE TASK_KEY = ? AND CREATEUSER_ID=? AND STATE IN (?,?,?,?) AND COMPLETE_FLAG=0 AND EFFECT_TIME_LONG > ? ORDER BY CREATE_TIME");
        Object[] args = new Object[]{taskKey, createUserId, TaskState.PROCESSING.getValue(), TaskState.WAITING.getValue(), TaskState.FINISHED.getValue(), TaskState.ERROR.getValue(), currentTimeMillis - TODO_DAY * 24L * 60L * 60L * 1000L};
        List tasks = this.jdbcTemplate.query(sql.toString(), args, OBJECT_WITHOUTCLOB_MAPPER);
        tasks.forEach(this::ConvertArgs);
        ArrayList<JobInstanceBean> jobs = new ArrayList<JobInstanceBean>();
        RealTimeQueryParam queryParam = new RealTimeQueryParam();
        queryParam.setPageSize(Integer.valueOf(1000));
        queryParam.setQueryField1(taskKey);
        queryParam.setStates(State.RUNNING + "," + State.FINISHED + "," + State.WAITING);
        queryParam.setStartTime(Long.valueOf(System.currentTimeMillis() - TODO_DAY * 24L * 60L * 60L * 1000L));
        try {
            jobs.addAll(this.getAllInstancesByCondition(queryParam, NpContextHolder.getContext().getUserId(), taskPoolTypes));
        }
        catch (JQException e) {
            logger.error(e.getMessage(), e);
        }
        if (!jobs.isEmpty()) {
            String queryCompleteFlagSql = "SELECT TASK_ID FROM NP_ASYNCTASK_COMPLETEFLAG WHERE COMPLETE_FLAG = 0 AND CREATE_TIME > ?";
            Object[] qcArgs = new Object[]{currentTimeMillis - TODO_DAY * 24L * 60L * 60L * 1000L};
            List taskIds = this.jdbcTemplate.queryForList("SELECT TASK_ID FROM NP_ASYNCTASK_COMPLETEFLAG WHERE COMPLETE_FLAG = 0 AND CREATE_TIME > ?", qcArgs, String.class);
            jobs.forEach(jobInstanceBean -> {
                for (String taskId : taskIds) {
                    if (jobInstanceBean.getState() != State.WAITING.getValue() && !jobInstanceBean.getInstanceId().equals(taskId)) continue;
                    tasks.add(this.jobInstanceBeanToAsyncTask((JobInstanceBean)jobInstanceBean, false));
                    taskIds.remove(taskId);
                    break;
                }
            });
            tasks.sort(Comparator.comparing(AsyncTask::getCreateTime));
        }
        return tasks;
    }

    private void ConvertArgs(AsyncTask task) {
        Object args = task.getArgs();
        Object context = task.getContext();
        try {
            if (context != null) {
                Object contextParam = SimpleParamConverter.SerializationUtils.deserialize(context.toString());
                ((AsyncTaskImpl)task).setContext(contextParam);
            }
            if (args != null) {
                Object argsObj = SimpleParamConverter.SerializationUtils.deserialize(args.toString());
                ((AsyncTaskImpl)task).setArgs(argsObj);
            }
        }
        catch (ParameterConversionException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public AsyncTask queryPrevTaskPool(String taskPoolType) {
        StringBuilder sql = new StringBuilder("SELECT TASK_ID, STATE FROM NP_ASYNCTASK WHERE SYSTEM_NAME = '" + this.SYSTEM_NAME + "'");
        sql.append(TASKPOOLTYPE_CONDITION);
        sql.append(" AND CREATEUSER_ID =?");
        sql.append(" ORDER BY PRIORITY DESC, WAITING_TIME ASC");
        ArrayList<String> args = new ArrayList<String>();
        args.add(taskPoolType);
        args.add(NpContextHolder.getContext().getUserId());
        List list = this.jdbcTemplate.query(sql.toString(), args.toArray(), SHORTER_OBJECT_MAPPER);
        if (null != list && list.size() > 1) {
            return this.query(((AsyncTask)list.get(0)).getTaskId());
        }
        return null;
    }

    @Override
    public Integer queryLocation(String taskId, AsyncTask task) {
        if (taskId.startsWith("nr-")) {
            StringBuilder sql = new StringBuilder("SELECT COUNT(TASK_ID) FROM NP_ASYNCTASK WHERE SYSTEM_NAME = '" + this.SYSTEM_NAME + "' AND STATE=? AND TASKPOOL_TYPE=?");
            sql.append(" AND (( WAITING_TIME < ? AND PRIORITY = ? ) OR PRIORITY > ? )");
            Object[] args = new Object[]{TaskState.WAITING.getValue(), task.getTaskPoolType(), Timestamp.from(task.getWaitingTime()), task.getPriority(), task.getPriority()};
            return (Integer)this.jdbcTemplate.query(sql.toString(), args, (ResultSetExtractor)new ResultSetExtractor<Integer>(){

                public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                    if (rs.next()) {
                        Integer count = 0;
                        count = rs.getInt(1);
                        return count;
                    }
                    return null;
                }
            });
        }
        if (Objects.nonNull(task.getCreateTime())) {
            try {
                long waitingTime = task.getCreateTime().toEpochMilli();
                return RealTimeJobManager.getInstance().getMonitorManager().getNumsBeforeTimeUseCache(waitingTime, State.WAITING);
            }
            catch (JobsException e) {
                logger.error(e.getMessage(), e);
                return null;
            }
        }
        return null;
    }

    @Override
    public TaskState queryState(String taskId) {
        if (taskId.startsWith("nr-")) {
            StringBuilder sql = new StringBuilder("SELECT TASK_ID, STATE FROM NP_ASYNCTASK WHERE ");
            sql.append("TASK_ID =?");
            ArrayList<String> args = new ArrayList<String>();
            args.add(taskId);
            Optional task = this.jdbcTemplate.query(sql.toString(), args.toArray(), SHORTER_OBJECT_MAPPER).stream().findFirst();
            if (task.isPresent()) {
                return ((AsyncTask)task.get()).getState();
            }
            return null;
        }
        try {
            ImmediatelyJobInfo immediatelyJobInfo = ImmediatelyJobRunner.getInstance().getJobInfo(taskId);
            if (Objects.nonNull(immediatelyJobInfo)) {
                return TaskState.convertRealTimeTaskState((int)immediatelyJobInfo.getState(), (int)immediatelyJobInfo.getResult());
            }
            JobInstanceBean jobInstanceBean = RealTimeJobManager.getInstance().getMonitorManager().getJobInstanceByGuid(taskId, true);
            return TaskState.convertRealTimeTaskState((int)jobInstanceBean.getState(), (int)jobInstanceBean.getResult());
        }
        catch (JobsException e) {
            logger.error(e.getMessage(), e);
            return TaskState.NONE;
        }
    }

    @Override
    public List<AsyncTask> queryByTaskPool(String taskPoolType, TaskState state) {
        StringBuilder sql = new StringBuilder("SELECT /*+TOP_ORDER_OPT_FLAG(1)*/TASK_ID, STATE FROM NP_ASYNCTASK WHERE SYSTEM_NAME = ?");
        sql.append(TASKPOOLTYPE_CONDITION);
        sql.append(" AND STATE =? ORDER BY PRIORITY DESC, WAITING_TIME ASC");
        ArrayList<Object> args = new ArrayList<Object>();
        args.add(this.SYSTEM_NAME);
        args.add(taskPoolType);
        args.add(state.getValue());
        List list = this.jdbcTemplate.query(sql.toString(), args.toArray(), SHORTER_OBJECT_MAPPER);
        return list;
    }

    @Override
    public Object queryDetail(String taskId) {
        String detail = this.queryDetailString(taskId);
        if (detail != null && (detail.startsWith("DETAILSTRING:") || taskId.startsWith("nr-"))) {
            if (!taskId.startsWith("nr-")) {
                detail = detail.substring("DETAILSTRING:".length());
            }
            return SimpleParamConverter.SerializationUtils.deserialize(detail);
        }
        return detail;
    }

    @Override
    public String queryDetailString(String taskId) {
        String detail = "";
        if (taskId.startsWith("nr-")) {
            String sql = "SELECT DETAIL FROM NP_ASYNCTASK WHERE TASK_ID= ? ";
            ArrayList<String> args = new ArrayList<String>();
            args.add(taskId);
            detail = (String)this.jdbcTemplate.queryForObject(sql, args.toArray(), DETAIL_MAPPER);
        } else {
            try {
                RealTimeJobManager.getInstance().getMonitorManager();
                detail = JobMonitorManager.getInstanceDetail((String)taskId);
            }
            catch (JobsException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return detail;
    }

    @Override
    public void updateErrorInfo(String taskId, String cause, Throwable t, Object detail) {
        StringBuilder sql = new StringBuilder("UPDATE NP_ASYNCTASK");
        sql.append(" SET STATE = ? , FINISH_TIME = ? , RESULT = ? , DETAIL = ? , ERROR_ = ? WHERE TASK_ID=?");
        Object[] args = new Object[]{TaskState.ERROR.getValue(), Timestamp.from(Instant.now()), cause, SimpleParamConverter.SerializationUtils.serializeToString(detail), this.getStackTrace(t), taskId};
        this.jdbcTemplate.update(sql.toString(), args);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String getStackTrace(Throwable throwable) {
        if (null == throwable) {
            return null;
        }
        StringWriter sw = new StringWriter();
        PrintWriter printWriter = new PrintWriter(sw);
        try {
            throwable.printStackTrace(printWriter);
            StringBuilder sb = new StringBuilder();
            sb.append("\u3010StackTrace\u3011\n");
            sb.append(sw.toString());
            sb.append("\u3010Message\u3011\n");
            sb.append(throwable.toString());
            String string = sb.toString();
            return string;
        }
        finally {
            printWriter.close();
            try {
                sw.close();
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public String isUniqueTaskExsits(String taskPoolType, String dimensionIdentity) {
        String selectSql = "SELECT TASK_ID, STATE FROM NP_ASYNCTASK WHERE TASKPOOL_TYPE=? AND DIMENSION_IDENTIFY=? AND (STATE=? OR STATE=?)";
        Object[] args = new Object[]{taskPoolType, dimensionIdentity, TaskState.WAITING.getValue(), TaskState.PROCESSING.getValue()};
        List list = this.jdbcTemplate.query(selectSql, args, SHORTER_OBJECT_MAPPER);
        if (list.isEmpty()) {
            return null;
        }
        return ((AsyncTask)list.get(0)).getTaskId();
    }

    @Override
    public void insertTaskCompleteFlag(String taskId, long startTime) {
        String sql = "INSERT INTO NP_ASYNCTASK_COMPLETEFLAG (TASK_ID, COMPLETE_FLAG, CREATE_TIME) VALUES (?, 0, ?)";
        Object[] args = new Object[]{taskId, startTime};
        this.update(sql, args);
    }

    @Override
    public int updateCompleteTask(String taskId) {
        StringBuilder sql = new StringBuilder();
        if (taskId.startsWith("nr-")) {
            sql.append("UPDATE NP_ASYNCTASK");
            sql.append(" SET COMPLETE_FLAG=1 WHERE TASK_ID=?");
        } else {
            sql.append("UPDATE NP_ASYNCTASK_COMPLETEFLAG");
            sql.append(" SET COMPLETE_FLAG = 1 WHERE TASK_ID = ?");
        }
        return this.update(sql.toString(), new Object[]{taskId});
    }

    @Override
    public void deleteStaleCompleteFlag(long time) {
        String sql = "DELETE FROM NP_ASYNCTASK_COMPLETEFLAG WHERE CREATE_TIME <= ?";
        Object[] args = new Object[]{time - AUTODELETEHISTORYDATA_DAY * 24L * 60L * 60L * 1000L};
        this.update("DELETE FROM NP_ASYNCTASK_COMPLETEFLAG WHERE CREATE_TIME <= ?", args);
    }

    @Override
    public void deleteJunkCompleteFlag() {
        ArrayList<JobInstanceBean> jobs = new ArrayList<JobInstanceBean>();
        RealTimeQueryParam queryParam = new RealTimeQueryParam();
        queryParam.setStartTime(Long.valueOf(System.currentTimeMillis() - AUTODELETEHISTORYDATA_DAY * 24L * 60L * 60L * 1000L));
        try {
            jobs.addAll(this.getAllInstancesByCondition(queryParam, "", new ArrayList<String>()));
        }
        catch (JQException e) {
            logger.error(e.getMessage(), e);
        }
        String queryCompleteFlagSql = "SELECT TASK_ID FROM NP_ASYNCTASK_COMPLETEFLAG";
        List taskIds = this.jdbcTemplate.queryForList("SELECT TASK_ID FROM NP_ASYNCTASK_COMPLETEFLAG", String.class);
        ArrayList junkTaskIds = new ArrayList();
        taskIds.forEach(taskId -> {
            boolean isContains = false;
            for (JobInstanceBean jobInstanceBean : jobs) {
                if (!taskId.equals(jobInstanceBean.getInstanceId())) continue;
                isContains = true;
                jobs.remove(jobInstanceBean);
                break;
            }
            if (!isContains) {
                junkTaskIds.add(taskId);
            }
        });
        if (!junkTaskIds.isEmpty()) {
            StringBuilder deleteSql = new StringBuilder();
            deleteSql.append("DELETE FROM NP_ASYNCTASK_COMPLETEFLAG WHERE TASK_ID = ?");
            ArrayList<Object[]> list = new ArrayList<Object[]>();
            Object[] args = new Object[1];
            for (String junkTaskId : junkTaskIds) {
                args[0] = junkTaskId;
                list.add(args);
            }
            this.update(deleteSql.toString(), list);
        }
    }

    public int updateCompleteTasks(List<String> taskIds) {
        if (taskIds == null || taskIds.size() <= 0) {
            return -1;
        }
        String nrSql = "UPDATE NP_ASYNCTASK SET COMPLETE_FLAG=1 WHERE TASK_ID=?";
        String biSql = "UPDATE NP_ASYNCTASK_COMPLETEFLAG SET COMPLETE_FLAG = 1 WHERE TASK_ID = ?";
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>();
        for (String taskId : taskIds) {
            Object[] argValue = new Object[]{taskId};
            batchArgs.add(argValue);
        }
        this.update(nrSql, batchArgs);
        this.update(biSql, batchArgs);
        return 1;
    }

    @Override
    public Map<String, Object> queryDetails(List<String> taskIds) {
        HashMap<String, Object> detailMap = new HashMap<String, Object>();
        Map<String, String> detailStringMap = this.queryDetailStrings(taskIds);
        detailStringMap.entrySet().forEach(mapEntry -> {
            String detail = (String)mapEntry.getValue();
            if (detail != null && (detail.startsWith("DETAILSTRING:") || ((String)mapEntry.getKey()).startsWith("nr-"))) {
                if (!((String)mapEntry.getKey()).startsWith("nr-")) {
                    detail = detail.substring("DETAILSTRING:".length());
                }
                detailMap.put((String)mapEntry.getKey(), SimpleParamConverter.SerializationUtils.deserialize(detail));
            } else {
                detailMap.put((String)mapEntry.getKey(), detail);
            }
        });
        return detailMap;
    }

    @Override
    public Map<String, String> queryDetailStrings(List<String> taskIds) {
        HashMap<String, String> detailMap = new HashMap<String, String>();
        if (taskIds == null || taskIds.size() <= 0 || taskIds.size() > 900) {
            return detailMap;
        }
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT TASK_ID AS task_id, DETAIL AS detail FROM NP_ASYNCTASK WHERE TASK_ID IN (");
        Object[] args = new Object[taskIds.size()];
        boolean existNrTask = false;
        for (int index = 0; index < taskIds.size(); ++index) {
            if (!taskIds.get(index).startsWith("nr-")) continue;
            if (existNrTask) {
                sql.append(",");
            }
            sql.append("?");
            args[index] = taskIds.get(index);
            existNrTask = true;
        }
        sql.append(")");
        if (existNrTask) {
            List taskList = this.jdbcTemplate.queryForList(sql.toString(), args);
            for (Map map : taskList) {
                Object taskId = map.get("task_id");
                String detailData = (String)map.get("detail");
                if (taskId == null) continue;
                detailMap.put(taskId.toString(), detailData);
            }
        }
        for (String taskId : taskIds) {
            if (taskId.startsWith("nr-")) continue;
            try {
                RealTimeJobManager.getInstance().getMonitorManager();
                detailMap.put(taskId, JobMonitorManager.getInstanceDetail((String)taskId));
            }
            catch (JobsException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return detailMap;
    }

    @Override
    public String queryArgs(String taskId) {
        try {
            Map params = JobMonitorManager.getInstanceParams((String)taskId);
            return (String)params.get("NR_ARGS");
        }
        catch (JobsException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public String queryTaskKey(String taskId) {
        try {
            JobInstanceBean jobInstanceBean = RealTimeJobManager.getInstance().getMonitorManager().getJobInstanceByGuid(taskId);
            return jobInstanceBean.getQueryField1();
        }
        catch (JobsException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public String queryFormSchemeKey(String taskId) {
        try {
            JobInstanceBean jobInstanceBean = RealTimeJobManager.getInstance().getMonitorManager().getJobInstanceByGuid(taskId);
            return jobInstanceBean.getQueryField2();
        }
        catch (JobsException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    private AsyncTask queryRealTimeJobInstance(String taskId, boolean simple) {
        try {
            ImmediatelyJobInfo immediatelyJobInfo = ImmediatelyJobRunner.getInstance().getJobInfo(taskId);
            if (immediatelyJobInfo != null) {
                AsyncTaskImpl asyncTask = new AsyncTaskImpl();
                asyncTask.setTaskId(immediatelyJobInfo.getInstanceId());
                asyncTask.setPublishType("immediately");
                asyncTask.setProcess((double)immediatelyJobInfo.getProgress() / 100.0);
                asyncTask.setState(TaskState.convertRealTimeTaskState((int)immediatelyJobInfo.getState(), (int)immediatelyJobInfo.getResult()));
                asyncTask.setResult(immediatelyJobInfo.getPrompt());
                asyncTask.setDetail(null);
                return asyncTask;
            }
            JobInstanceBean jobInstanceBean = RealTimeJobManager.getInstance().getMonitorManager().getJobInstanceByGuid(taskId, simple);
            if (Objects.nonNull(jobInstanceBean)) {
                return this.jobInstanceBeanToAsyncTask(jobInstanceBean, true);
            }
            return null;
        }
        catch (JobsException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    private AsyncTask jobInstanceBeanToAsyncTask(JobInstanceBean jobInstanceBean, boolean includeClob) {
        AsyncTaskImpl asyncTask = new AsyncTaskImpl();
        if (jobInstanceBean != null) {
            asyncTask.setTaskId(jobInstanceBean.getInstanceId());
            asyncTask.setTaskKey(jobInstanceBean.getQueryField1());
            asyncTask.setFormSchemeKey(jobInstanceBean.getQueryField2());
            asyncTask.setTaskPoolType(jobInstanceBean.getGroupId());
            asyncTask.setPublishType("dispatch");
            asyncTask.setDependTaskId("");
            asyncTask.setProcess(Double.valueOf(jobInstanceBean.getProgress()) / 100.0);
            asyncTask.setCreateUserId(jobInstanceBean.getUserguid());
            asyncTask.setState(TaskState.convertRealTimeTaskState((int)jobInstanceBean.getState(), (int)jobInstanceBean.getResult()));
            asyncTask.setPriority(2);
            asyncTask.setCreateTime(Instant.ofEpochMilli(jobInstanceBean.getStarttime()));
            asyncTask.setWaitingTime(Instant.now());
            asyncTask.setProcessTime(Instant.ofEpochMilli(jobInstanceBean.getStarttime()));
            asyncTask.setFinishTime(Instant.ofEpochMilli(jobInstanceBean.getEndtime()));
            asyncTask.setCancelTime(Instant.now());
            asyncTask.setResult(jobInstanceBean.getResult() == 5 ? "overtime" : jobInstanceBean.getPrompt());
            asyncTask.setServeId(jobInstanceBean.getNode());
            asyncTask.setEffectTime(Instant.now());
            asyncTask.setDimensionIdentify("");
            if (includeClob) {
                asyncTask.setArgs(null);
                asyncTask.setContext(null);
            }
        }
        return asyncTask;
    }

    public List<JobInstanceBean> getAllInstancesByCondition(RealTimeQueryParam queryParam, String userID, List<String> categorys) throws JQException {
        Integer page;
        String jobTitle = queryParam.getJobTitle();
        String categorysStr = queryParam.getCategory();
        try {
            if (StringUtils.isNotEmpty((String)categorysStr)) {
                String[] categorysStrArr;
                for (String string : categorysStrArr = categorysStr.split(",")) {
                    categorys.add(Html.encodeQueryString((String)string));
                }
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)RealTimeErrorEnum.EXCEPTION_500, "\u975e\u6cd5\u7684\u4efb\u52a1\u5206\u7c7bcategorys", (Throwable)e);
        }
        String statesStr = queryParam.getStates();
        ArrayList<State> states = new ArrayList<State>();
        String filterStr = null;
        try {
            if (StringUtils.isNotEmpty((String)statesStr)) {
                String[] stateStrArr;
                for (String stateStr : stateStrArr = statesStr.split(",")) {
                    if ("SUCCESS".equalsIgnoreCase(stateStr)) {
                        filterStr = "(BJI_RESULT = 100)";
                        states.add(State.FINISHED);
                        continue;
                    }
                    if ("FAILTURE".equalsIgnoreCase(stateStr)) {
                        filterStr = "((BJI_RESULT <> 1) AND (BJI_RESULT <> 100))";
                        states.add(State.FINISHED);
                        continue;
                    }
                    states.add(State.valueOf((String)stateStr));
                }
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)RealTimeErrorEnum.EXCEPTION_500, "\u975e\u6cd5\u7684\u8fd0\u884c\u72b6\u6001states", (Throwable)e);
        }
        String execNodesStr = queryParam.getExecNode();
        ArrayList<String> arrayList = new ArrayList<String>();
        if (StringUtils.isNotEmpty((String)execNodesStr)) {
            String[] execNodeArr;
            for (String execNodeStr : execNodeArr = execNodesStr.split(",")) {
                arrayList.add(Html.encodeQueryString((String)execNodeStr));
            }
        }
        String execUserTitle = queryParam.getExecUserTitle();
        Long startTime = queryParam.getStartTime();
        Long endTime = queryParam.getEndTime();
        Integer pageSize = queryParam.getPageSize();
        if (pageSize == null) {
            pageSize = 10000;
        }
        if ((page = queryParam.getPage()) == null) {
            page = 1;
        }
        JobOperationManager manager = new JobOperationManager();
        try {
            return manager.getRealTimeJobInstances(jobTitle, categorys, states, arrayList, execUserTitle, userID, startTime, endTime, pageSize * (page - 1), pageSize * page, queryParam.getQueryField1(), queryParam.getQueryField2(), filterStr);
        }
        catch (JobsException e) {
            throw new JQException((ErrorEnum)RealTimeErrorEnum.EXCEPTION_500, e.getMessage(), (Throwable)e);
        }
    }
}

