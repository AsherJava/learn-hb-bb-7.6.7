/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.datasource.GcBizJdbcTemplate
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl
 *  com.jiuqi.dc.base.common.jdbc.extractor.IntegerResultSetExtractor
 *  com.jiuqi.dc.base.common.jdbc.extractor.StringRowMapper
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  org.apache.poi.ss.formula.functions.T
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.dc.taskscheduling.log.impl.dao.impl;

import com.jiuqi.common.base.datasource.GcBizJdbcTemplate;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl;
import com.jiuqi.dc.base.common.jdbc.extractor.IntegerResultSetExtractor;
import com.jiuqi.dc.base.common.jdbc.extractor.StringRowMapper;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.taskscheduling.log.impl.dao.TaskLogDao;
import com.jiuqi.dc.taskscheduling.log.impl.data.ArchiveParam;
import com.jiuqi.dc.taskscheduling.log.impl.data.TaskCountQueryDTO;
import com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskItemLogEO;
import com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskLogEO;
import com.jiuqi.dc.taskscheduling.log.impl.enums.DataHandleState;
import com.jiuqi.dc.taskscheduling.log.impl.vo.LogManagerDetailVO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class TaskLogDaoImpl
extends BaseDataCenterDaoImpl
implements TaskLogDao {
    @Override
    public void insertTaskLogs(DcTaskLogEO taskInfo) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ").append("GC_LOG_TASKINFO").append(" \n");
        sql.append("  (ID,TASKTYPE,CREATETIME,MESSAGE, MESSAGEDIGEST, ENDTIME, RESULTLOG, SOURCETYPE, EXT_1, EXT_2, EXT_3, EXT_4, EXT_5) \n");
        sql.append("  VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?) \n");
        String msgDigest = !StringUtils.isEmpty((String)taskInfo.getMessage()) && taskInfo.getMessage().length() > 200 ? taskInfo.getMessage().substring(0, 200) : taskInfo.getMessage();
        Object[] params = new Object[]{taskInfo.getId(), taskInfo.getTaskType(), taskInfo.getCreateTime(), taskInfo.getMessage(), msgDigest, taskInfo.getEndTime(), taskInfo.getResultLog(), taskInfo.getSourceType(), taskInfo.getExt_1(), taskInfo.getExt_2(), taskInfo.getExt_3(), taskInfo.getExt_4(), taskInfo.getExt_5()};
        OuterDataSourceUtils.getJdbcTemplate().update(sql.toString(), params);
    }

    @Override
    public void insertTaskItems(List<DcTaskItemLogEO> taskItemList) {
        if (taskItemList == null || taskItemList.isEmpty()) {
            return;
        }
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ").append("GC_LOG_TASKITEMINFO").append(" \n");
        sql.append("   (ID, TASKTYPE, INSTANCEID, PRENODEID, DIMTYPE, DIMCODE, MESSAGE, MESSAGEDIGEST, EXECUTESTATE, STARTTIME, ENDTIME, RESULTLOG, RUNNERID, QUEUENAME) \n");
        sql.append("  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        ArrayList<Object[]> taskItemLogs = new ArrayList<Object[]>();
        for (DcTaskItemLogEO itemInfoDO : taskItemList) {
            String msgDigest = !StringUtils.isEmpty((String)itemInfoDO.getMessage()) && itemInfoDO.getMessage().length() > 200 ? itemInfoDO.getMessage().substring(0, 200) : itemInfoDO.getMessage();
            Object[] params = new Object[]{itemInfoDO.getId(), itemInfoDO.getTaskType(), itemInfoDO.getInstanceId(), itemInfoDO.getPreNodeId(), itemInfoDO.getDimType(), itemInfoDO.getDimCode(), itemInfoDO.getMessage(), msgDigest, itemInfoDO.getExecuteState(), itemInfoDO.getStartTime(), itemInfoDO.getEndTime(), itemInfoDO.getResultLog(), itemInfoDO.getRunnerId(), itemInfoDO.getQueueName()};
            taskItemLogs.add(params);
        }
        OuterDataSourceUtils.getJdbcTemplate().batchUpdate(sql.toString(), taskItemLogs);
    }

    @Override
    public void updateTaskLog(String id, Map<String, Object> fieldValues) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append("GC_LOG_TASKINFO").append("\n");
        sql.append("   SET ");
        for (String fieldName : fieldValues.keySet()) {
            sql.append(fieldName).append(" = ?, ");
        }
        sql.append(" WHERE ID = ? \n");
        sql.deleteCharAt(sql.lastIndexOf(","));
        List params = fieldValues.values().stream().collect(Collectors.toList());
        params.add(id);
        OuterDataSourceUtils.getJdbcTemplate().update(sql.toString(), params.toArray());
    }

    @Override
    public void updateTaskResult(String id, String resultLog) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append("GC_LOG_TASKINFO").append("\n");
        sql.append("   SET ENDTIME = ?, RESULTLOG = ? \n");
        sql.append(" WHERE ID = ? \n");
        Object[] params = new Object[]{new Date(), resultLog, id};
        OuterDataSourceUtils.getJdbcTemplate().update(sql.toString(), params);
    }

    @Override
    public void updateTaskItemStartById(String id, String queueName, String executeAppName, String serverIp) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append("GC_LOG_TASKITEMINFO").append("\n");
        sql.append("   SET STARTTIME = ?,  EXECUTESTATE = ?, QUEUENAME = ?, EXECUTEAPPNAME = ?, SERVERIP = ? \n");
        sql.append(" WHERE ID = ? \n");
        Object[] params = new Object[]{new Date(), DataHandleState.EXECUTING.getState(), queueName, executeAppName, serverIp, id};
        OuterDataSourceUtils.getJdbcTemplate().update(sql.toString(), params);
    }

    @Override
    public void updateTaskItemProgress(String id, double progress, String log) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append("GC_LOG_TASKITEMINFO").append("\n");
        sql.append("   SET PROGRESS = ?, RESULTLOG = ? \n");
        sql.append(" WHERE ID = ? \n");
        Object[] params = new Object[]{progress, log, id};
        OuterDataSourceUtils.getJdbcTemplate().update(sql.toString(), params);
    }

    @Override
    public void updateTaskItemResultById(String itemId, DataHandleState handleState, String resultLog) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append("GC_LOG_TASKITEMINFO").append("\n");
        sql.append("   SET RESULTLOG = ?, \n");
        sql.append("       EXECUTESTATE = ?, \n");
        sql.append("       PROGRESS = ?, \n");
        sql.append("       ENDTIME = ? \n");
        sql.append(" WHERE ID = ? \n");
        Object[] params = new Object[]{resultLog, handleState.getState(), 1.0, new Date(), itemId};
        OuterDataSourceUtils.getJdbcTemplate().update(sql.toString(), params);
    }

    @Override
    public Integer getStateCountByRunnerId(String runnerId, DataHandleState handleState) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) \n");
        sql.append("  FROM ").append("GC_LOG_TASKITEMINFO").append(" \n");
        sql.append(" WHERE 1 = 1 \n");
        sql.append("   AND RUNNERID = ? \n");
        sql.append("   AND EXECUTESTATE = ? \n");
        return (Integer)OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (ResultSetExtractor)new IntegerResultSetExtractor(), new Object[]{runnerId, handleState.getState()});
    }

    @Override
    public Integer getStateCountByRunnerId(String runnerId, List<DataHandleState> handleStates) {
        Assert.isNotEmpty(handleStates);
        StringBuilder sql = new StringBuilder();
        ArrayList args = CollectionUtils.newArrayList();
        sql.append("SELECT COUNT(*) \n");
        sql.append("  FROM ").append("GC_LOG_TASKITEMINFO").append(" \n");
        sql.append(" WHERE 1 = 1 \n");
        sql.append("   AND RUNNERID = ? \n");
        args.add(runnerId);
        sql.append("   AND EXECUTESTATE IN (");
        for (DataHandleState dataHandleState : handleStates) {
            sql.append("?,");
            args.add(dataHandleState.getState());
        }
        sql.delete(sql.length() - 1, sql.length());
        sql.append("  ) \n");
        return (Integer)OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (ResultSetExtractor)new IntegerResultSetExtractor(), args.toArray());
    }

    @Override
    public List<String> listTaskItemDimCode(String runnerId, DataHandleState handleState) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT T.DIMCODE \n");
        sql.append("  FROM ").append("GC_LOG_TASKITEMINFO").append(" T \n");
        sql.append(" WHERE 1 = 1 \n");
        sql.append("   AND RUNNERID = ? \n");
        sql.append("   AND EXECUTESTATE = ? \n");
        sql.append(" GROUP BY T.DIMCODE \n");
        return OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (RowMapper)new StringRowMapper(), new Object[]{runnerId, handleState.getState()});
    }

    @Override
    public void cancelTask(String runnerId) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append("GC_LOG_TASKITEMINFO").append(" \n");
        sql.append("   SET EXECUTESTATE = ?, RESULTLOG = ?, PROGRESS = ?, STARTTIME = ?, ENDTIME = ? \n");
        sql.append(" WHERE RUNNERID = ? ");
        sql.append("   AND EXECUTESTATE = ? \n");
        sql.append("   AND PRENODEID = ? \n");
        Date cancelTime = new Date();
        OuterDataSourceUtils.getJdbcTemplate().update(sql.toString(), new Object[]{DataHandleState.CANCELED.getState(), "\u5df2\u53d6\u6d88", 1.0, cancelTime, cancelTime, runnerId, DataHandleState.UNEXECUTE.getState(), UUIDUtils.emptyHalfGUIDStr()});
    }

    @Override
    public boolean isCancel(String id) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT EXECUTESTATE \n");
        sql.append("  FROM ").append("GC_LOG_TASKITEMINFO").append(" \n");
        sql.append(" WHERE ID = ? ");
        return (Boolean)OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), rs -> rs.next() ? DataHandleState.CANCELED.getState() == rs.getInt(1) : false, new Object[]{id});
    }

    @Override
    public Double getTaskProgressByRunnerId(String runnerId) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) TOTAL,  \n");
        sql.append("\t   SUM(CASE WHEN ITEM.EXECUTESTATE = ").append(DataHandleState.FAILURE.getState()).append(" THEN 1 ELSE 0 END) FAILED, \n");
        sql.append("\t   SUM(CASE WHEN ITEM.EXECUTESTATE = ").append(DataHandleState.SUCCESS.getState()).append(" THEN 1 ELSE 0 END) SUCCESS, \n");
        sql.append("\t   SUM(CASE WHEN ITEM.EXECUTESTATE = ").append(DataHandleState.UNEXECUTE.getState()).append(" THEN 1 ELSE 0 END) UNEXECUTE, \n");
        sql.append("\t   SUM(CASE WHEN ITEM.EXECUTESTATE = ").append(DataHandleState.EXECUTING.getState()).append(" THEN 1 ELSE 0 END) EXECUTING, \n");
        sql.append("\t   SUM(CASE WHEN ITEM.EXECUTESTATE = ").append(DataHandleState.CANCELED.getState()).append(" THEN 1 ELSE 0 END) CANCELED \n");
        sql.append("  FROM ").append("GC_LOG_TASKITEMINFO").append(" ITEM \n");
        sql.append(" WHERE ITEM.RUNNERID = ?");
        return (Double)OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), rs -> {
            if (rs.next()) {
                int total = rs.getInt(1);
                int unexecute = rs.getInt(4);
                int executing = rs.getInt(5);
                return total == 0 ? 0.0 : NumberUtils.div((double)(total - (unexecute + executing)), (double)total);
            }
            return 0.0;
        }, new Object[]{runnerId});
    }

    @Override
    public Integer getStateCountByRunnerIdAndDimCode(String runnerId, String handlerName, String dimCode, DataHandleState handleState) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) \n");
        sql.append("  FROM ").append("GC_LOG_TASKITEMINFO").append(" \n");
        sql.append(" WHERE 1 = 1 \n");
        sql.append("   AND RUNNERID = ? \n");
        sql.append("   AND TASKTYPE = ? \n");
        sql.append("   AND DIMCODE = ? \n");
        sql.append("   AND EXECUTESTATE = ? \n");
        return (Integer)OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (ResultSetExtractor)new IntegerResultSetExtractor(), new Object[]{runnerId, handlerName, dimCode, handleState.getState()});
    }

    @Override
    public List<DcTaskLogEO> getTaskInfoArchiveLog(String archiveDate) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT TASK.*  \n");
        sql.append("  FROM GC_LOG_TASKINFO TASK  \n");
        sql.append(" WHERE TASK.CREATETIME >= ?  \n");
        sql.append("   AND TASK.CREATETIME < ?  \n");
        Date startDate = DateUtils.parse((String)archiveDate, (String)DateUtils.DEFAULT_DATE_FORMAT);
        return OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (RowMapper)new BeanPropertyRowMapper(DcTaskLogEO.class), new Object[]{startDate, DateUtils.shifting((Date)startDate, (int)5, (int)1)});
    }

    @Override
    public void prepareTaskInfoTemp(String archiveDate) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO GC_TEMP_ARCHIVE (RUNNERID)  \n");
        sql.append("SELECT TASK.ID  \n");
        sql.append("  FROM GC_LOG_TASKINFO TASK  \n");
        sql.append(" WHERE TASK.CREATETIME >= ? AND TASK.CREATETIME < ?  \n");
        Date startDate = DateUtils.parse((String)archiveDate, (String)DateUtils.DEFAULT_DATE_FORMAT);
        OuterDataSourceUtils.getJdbcTemplate().update(sql.toString(), new Object[]{startDate, DateUtils.shifting((Date)startDate, (int)5, (int)1)});
    }

    @Override
    public void deleteTaskInfoLog(String archiveDate) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM GC_LOG_TASKINFO  \n");
        sql.append(" WHERE CREATETIME >= ?  \n");
        sql.append("   AND CREATETIME < ?  \n");
        Date startDate = DateUtils.parse((String)archiveDate, (String)DateUtils.DEFAULT_DATE_FORMAT);
        OuterDataSourceUtils.getJdbcTemplate().update(sql.toString(), new Object[]{startDate, DateUtils.shifting((Date)startDate, (int)5, (int)1)});
    }

    @Override
    public void clearTemp() {
        OuterDataSourceUtils.getJdbcTemplate().update("DELETE FROM GC_TEMP_ARCHIVE");
    }

    @Override
    public void prepareTaskItemInfoTemp() {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO GC_TEMP_ARCHIVE (LOGID)  \n");
        sql.append("SELECT ITEM.ID  \n");
        sql.append("  FROM GC_LOG_TASKITEMINFO ITEM  \n");
        sql.append("  JOIN GC_TEMP_ARCHIVE TEMP  \n");
        sql.append("    ON ITEM.RUNNERID = TEMP.RUNNERID \n");
        OuterDataSourceUtils.getJdbcTemplate().update(sql.toString());
    }

    @Override
    public void prepareSqlExcuteInfoTemp() {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO GC_TEMP_ARCHIVE (SQLEXCUTEID,SQLINFOID)  \n");
        sql.append("SELECT ITEM.ID,ITEM.SQLINFOID  \n");
        sql.append("  FROM GC_LOG_SQLEXECUTE ITEM  \n");
        sql.append("  JOIN GC_TEMP_ARCHIVE TEMP  \n");
        sql.append("    ON ITEM.LOGID = TEMP.LOGID  \n");
        OuterDataSourceUtils.getJdbcTemplate().update(sql.toString());
    }

    @Override
    public void deleteArchiveLog(String tableName, String columnName, String tempColumnName) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM %1$s \n");
        sql.append(" WHERE %2$s IN (SELECT %3$s FROM GC_TEMP_ARCHIVE WHERE %3$s IS NOT NULL) \n");
        OuterDataSourceUtils.getJdbcTemplate().update(String.format(sql.toString(), tableName, columnName, tempColumnName));
    }

    @Override
    public List<T> getArchiveLog(String tablename, Class classType, String columnName, String tempColumnName) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ITEM.*  \n");
        sql.append("  FROM %1$s ITEM  \n");
        sql.append("  JOIN GC_TEMP_ARCHIVE TEMP  \n");
        sql.append("    ON ITEM.%2$s = TEMP.%3$s \n");
        return OuterDataSourceUtils.getJdbcTemplate().query(String.format(sql.toString(), tablename, columnName, tempColumnName), (RowMapper)new BeanPropertyRowMapper(classType));
    }

    @Override
    public List<ArchiveParam> getArchiveDimList(Date endDate) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ##YearPeriod## AS ARCHIVEPERIOD \n");
        sql.append("  FROM GC_LOG_TASKINFO TASK  \n");
        sql.append(" WHERE TASK.CREATETIME <= ?  \n");
        sql.append(" GROUP BY ##YearPeriod## \n");
        sql.append(" ORDER BY ARCHIVEPERIOD  \n");
        GcBizJdbcTemplate jdbcTemplate = OuterDataSourceUtils.getJdbcTemplate();
        IDbSqlHandler sqlHandler = jdbcTemplate.getIDbSqlHandler();
        String excuteSql = sql.toString().replace("##YearPeriod##", sqlHandler.formatDate("TASK.CREATETIME", sqlHandler.hyphenDateFmt()));
        return jdbcTemplate.query(excuteSql, (RowMapper)new BeanPropertyRowMapper(ArchiveParam.class), new Object[]{endDate});
    }

    @Override
    public Integer countTask(TaskCountQueryDTO queryParam) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(1) FROM GC_LOG_TASKITEMINFO T \n");
        sql.append(" WHERE T.RUNNERID = ? \n");
        ArrayList<String> args = new ArrayList<String>();
        args.add(queryParam.getRunnerId());
        if (!StringUtils.isEmpty((String)queryParam.getTaskName())) {
            sql.append(" AND T.TASKTYPE = ? \n");
            args.add(queryParam.getTaskName());
        }
        if (!CollectionUtils.isEmpty(queryParam.getDimCodes())) {
            sql.append(" AND ").append(SqlBuildUtil.getStrInCondi((String)"T.DIMCODE", new ArrayList<String>(queryParam.getDimCodes()))).append("\n");
        }
        if (!CollectionUtils.isEmpty(queryParam.getInstanceIdList())) {
            sql.append(" AND ").append(SqlBuildUtil.getStrInCondi((String)"T.INSTANCEID", new ArrayList<String>(queryParam.getInstanceIdList()))).append("\n");
        }
        if (!CollectionUtils.isEmpty(queryParam.getDataHandleStates())) {
            List handleStateList = queryParam.getDataHandleStates().stream().map(DataHandleState::getState).collect(Collectors.toList());
            sql.append(" AND ").append(SqlBuildUtil.getIntegerInCondi((String)"T.EXECUTESTATE", handleStateList)).append("\n");
        }
        return (Integer)OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (ResultSetExtractor)new IntegerResultSetExtractor(), args.toArray());
    }

    @Override
    public List<LogManagerDetailVO> queryTaskLog(TaskCountQueryDTO queryDto) {
        StringBuilder sql = new StringBuilder();
        ArrayList<String> params = new ArrayList<String>();
        sql.append("SELECT T.ID ,T.DIMCODE ,T.DIMTYPE ,T.INSTANCEID ,T.RESULTLOG  \n");
        sql.append("  FROM GC_LOG_TASKITEMINFO T  \n");
        sql.append(" WHERE 1 = 1 \n");
        if (!StringUtils.isEmpty((String)queryDto.getRunnerId())) {
            sql.append("   AND RUNNERID = ?  \n");
            params.add(queryDto.getRunnerId());
        }
        if (!StringUtils.isEmpty((String)queryDto.getTaskName())) {
            sql.append("   AND T.TASKTYPE = ?  \n");
            params.add(queryDto.getTaskName());
        }
        if (!CollectionUtils.isEmpty(queryDto.getDataHandleStates())) {
            List handleStateList = queryDto.getDataHandleStates().stream().map(DataHandleState::getState).collect(Collectors.toList());
            sql.append(" AND ").append(SqlBuildUtil.getIntegerInCondi((String)"T.EXECUTESTATE", handleStateList)).append("\n");
        }
        sql.append(" ORDER BY STARTTIME ASC \n");
        List taskItemList = OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (RowMapper)new BeanPropertyRowMapper(LogManagerDetailVO.class), params.toArray());
        return taskItemList;
    }
}

