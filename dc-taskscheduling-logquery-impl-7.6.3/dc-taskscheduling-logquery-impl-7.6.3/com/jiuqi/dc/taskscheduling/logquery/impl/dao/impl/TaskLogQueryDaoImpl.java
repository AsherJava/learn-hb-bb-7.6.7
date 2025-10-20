/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.datasource.GcBizJdbcTemplate
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl
 *  com.jiuqi.dc.base.common.jdbc.extractor.IntegerResultSetExtractor
 *  com.jiuqi.dc.base.common.jdbc.extractor.StringRowMapper
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.DataHandleState
 *  com.jiuqi.dc.taskscheduling.log.impl.vo.LogManagerDetailVO
 *  com.jiuqi.dc.taskscheduling.logquery.client.dto.LogManagerDTO
 *  com.jiuqi.dc.taskscheduling.logquery.client.vo.ExecuteStateVO
 *  com.jiuqi.dc.taskscheduling.logquery.client.vo.LogManagerVO
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.dc.taskscheduling.logquery.impl.dao.impl;

import com.jiuqi.common.base.datasource.GcBizJdbcTemplate;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl;
import com.jiuqi.dc.base.common.jdbc.extractor.IntegerResultSetExtractor;
import com.jiuqi.dc.base.common.jdbc.extractor.StringRowMapper;
import com.jiuqi.dc.taskscheduling.log.impl.enums.DataHandleState;
import com.jiuqi.dc.taskscheduling.log.impl.vo.LogManagerDetailVO;
import com.jiuqi.dc.taskscheduling.logquery.client.dto.LogManagerDTO;
import com.jiuqi.dc.taskscheduling.logquery.client.vo.ExecuteStateVO;
import com.jiuqi.dc.taskscheduling.logquery.client.vo.LogManagerVO;
import com.jiuqi.dc.taskscheduling.logquery.impl.dao.TaskLogQueryDao;
import com.jiuqi.dc.taskscheduling.logquery.impl.exp.ExecuteErrorRecordColumn;
import com.jiuqi.dc.taskscheduling.logquery.impl.exp.ExecuteRecordColumn;
import com.jiuqi.dc.taskscheduling.logquery.impl.exp.SqlRecordColumn;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class TaskLogQueryDaoImpl
extends BaseDataCenterDaoImpl
implements TaskLogQueryDao {
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
    public List<LogManagerVO> listOverView(LogManagerDTO dto) {
        StringBuilder sql = new StringBuilder();
        ArrayList<Object> params = new ArrayList<Object>();
        sql.append("SELECT T.ID, T.TASKTYPE, T.CREATETIME, T.ENDTIME, T.RESULTLOG,\n");
        sql.append("\t   COUNT(*) TOTAL,  \n");
        sql.append("\t   SUM(CASE WHEN ITEM.EXECUTESTATE = ").append(DataHandleState.FAILURE.getState()).append(" THEN 1 ELSE 0 END) FAILED, \n");
        sql.append("\t   SUM(CASE WHEN ITEM.EXECUTESTATE = ").append(DataHandleState.SUCCESS.getState()).append(" THEN 1 ELSE 0 END) SUCCESS, \n");
        sql.append("\t   SUM(CASE WHEN ITEM.EXECUTESTATE = ").append(DataHandleState.UNEXECUTE.getState()).append(" THEN 1 ELSE 0 END) UNEXECUTE, \n");
        sql.append("\t   SUM(CASE WHEN ITEM.EXECUTESTATE = ").append(DataHandleState.EXECUTING.getState()).append(" THEN 1 ELSE 0 END) EXECUTING, \n");
        sql.append("\t   SUM(CASE WHEN ITEM.EXECUTESTATE = ").append(DataHandleState.CANCELED.getState()).append(" THEN 1 ELSE 0 END) CANCELED, \n");
        sql.append("       SUM(CASE WHEN ITEM.PRENODEID = '").append(UUIDUtils.emptyHalfGUIDStr()).append("' AND ITEM.EXECUTESTATE = ").append(DataHandleState.UNEXECUTE.getState()).append(" THEN 1 ELSE 0 END) HASCANCEL \n");
        sql.append("  FROM ").append("GC_LOG_TASKINFO").append(" T \n");
        sql.append(" INNER JOIN ").append("GC_LOG_TASKITEMINFO").append(" ITEM ON T.ID = ITEM.RUNNERID \n");
        sql.append(" WHERE 1 = 1 \n");
        if (!StringUtils.isEmpty((String)dto.getId())) {
            sql.append("   AND T.ID = ? \n");
            params.add(dto.getId());
        }
        if (!StringUtils.isEmpty((String)dto.getTaskType())) {
            sql.append("   AND T.TASKTYPE = ? \n").append(" \n");
            params.add(dto.getTaskType());
        }
        if (dto.getStartTime() != null) {
            sql.append("   AND T.CREATETIME >= ? \n");
            params.add(dto.getStartTime());
        }
        if (dto.getEndTime() != null) {
            sql.append("   AND T.CREATETIME <= ? \n");
            params.add(dto.getEndTime());
        }
        sql.append(" GROUP BY T.ID, T.TASKTYPE, T.CREATETIME, T.ENDTIME, T.RESULTLOG \n");
        sql.append(" ORDER BY T.CREATETIME DESC, T.TASKTYPE ASC \n");
        GcBizJdbcTemplate jdbcTemplate = OuterDataSourceUtils.getJdbcTemplate();
        String tempSql = jdbcTemplate.getIDbSqlHandler().getPageSql(sql.toString(), dto.getPage().intValue(), dto.getPageSize().intValue());
        return jdbcTemplate.query(tempSql, (rs, rowNum) -> {
            LogManagerVO logManagerVO = new LogManagerVO();
            logManagerVO.setId(rs.getString(1));
            logManagerVO.setTaskType(rs.getString(2));
            logManagerVO.setCreateTime((Date)rs.getTimestamp(3));
            logManagerVO.setEndTime((Date)rs.getTimestamp(4));
            logManagerVO.setResultLog(rs.getString(5));
            logManagerVO.setTotal(Integer.valueOf(rs.getInt(6)));
            logManagerVO.setFailed(Integer.valueOf(rs.getInt(7)));
            logManagerVO.setSuccess(Integer.valueOf(rs.getInt(8)));
            logManagerVO.setUnExecute(Integer.valueOf(rs.getInt(9)));
            logManagerVO.setExecuting(Integer.valueOf(rs.getInt(10)));
            logManagerVO.setCanceled(Integer.valueOf(rs.getInt(11)));
            logManagerVO.setHasCancel(Boolean.valueOf(rs.getInt(12) > 0));
            return logManagerVO;
        }, params.toArray());
    }

    @Override
    public Integer listOverTotal(LogManagerDTO dto) {
        StringBuilder sql = new StringBuilder();
        ArrayList<Object> params = new ArrayList<Object>();
        sql.append("SELECT COUNT(DISTINCT T.ID)\n");
        sql.append("  FROM ").append("GC_LOG_TASKINFO").append(" T \n");
        sql.append(" INNER JOIN ").append("GC_LOG_TASKITEMINFO").append(" ITEM ON T.ID = ITEM.RUNNERID \n");
        sql.append(" WHERE 1 = 1 \n");
        if (!StringUtils.isEmpty((String)dto.getId())) {
            sql.append("   AND T.ID = ? \n");
            params.add(dto.getId());
        }
        if (!StringUtils.isEmpty((String)dto.getTaskType())) {
            sql.append("   AND T.TASKTYPE = ? \n");
            params.add(dto.getTaskType());
        }
        if (dto.getStartTime() != null) {
            sql.append("   AND T.CREATETIME >= ? \n");
            params.add(dto.getStartTime());
        }
        if (dto.getEndTime() != null) {
            sql.append("   AND T.CREATETIME <= ? \n");
            params.add(dto.getEndTime());
        }
        return (Integer)OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), rs -> rs.next() ? rs.getInt(1) : 0, params.toArray());
    }

    @Override
    public List<LogManagerDetailVO> listDetail(LogManagerDTO dto) {
        StringBuilder sql = new StringBuilder();
        ArrayList<Object> params = new ArrayList<Object>();
        sql.append("SELECT ID, INSTANCEID, PRENODEID, TASKTYPE, DIMTYPE, DIMCODE, MESSAGEDIGEST AS MESSAGE, \n");
        sql.append("  EXECUTESTATE, STARTTIME, ENDTIME, PROGRESS, RUNNERID, QUEUENAME \n");
        sql.append("  FROM \n").append("GC_LOG_TASKITEMINFO").append("\n");
        sql.append(" WHERE 1 = 1 \n");
        if (!StringUtils.isEmpty((String)dto.getId())) {
            sql.append("   AND ID = ? \n");
            params.add(dto.getId());
        }
        if (!StringUtils.isEmpty((String)dto.getTaskType())) {
            sql.append("   AND TASKTYPE = ? \n");
            params.add(dto.getTaskType());
        }
        if (dto.getStartTime() != null) {
            sql.append("   AND STARTTIME >= ? \n");
            params.add(dto.getStartTime());
        }
        if (dto.getEndTime() != null) {
            sql.append("   AND STARTTIME <= ? \n");
            params.add(dto.getEndTime());
        }
        if (!StringUtils.isEmpty((String)dto.getInstanceId())) {
            sql.append("   AND INSTANCEID = ? \n");
            params.add(dto.getInstanceId());
        }
        if (!StringUtils.isEmpty((String)dto.getRunnerId())) {
            sql.append("   AND RUNNERID = ? \n");
            params.add(dto.getRunnerId());
        }
        if (Objects.equals("LEVEL", dto.getShowType())) {
            sql.append(" AND PRENODEID = ? \n");
            params.add(UUIDUtils.emptyHalfGUIDStr());
        }
        if (!StringUtils.isEmpty((String)dto.getPreNodeId())) {
            sql.append("   AND PRENODEID = ? \n");
            params.add(dto.getPreNodeId());
        }
        if (!StringUtils.isEmpty((String)dto.getDimType())) {
            sql.append("   AND DIMTYPE = ? \n");
            params.add(dto.getDimType());
        }
        if (!StringUtils.isEmpty((String)dto.getDimCode())) {
            sql.append("   AND DIMCODE LIKE ? \n");
            params.add("%" + dto.getDimCode() + "%");
        }
        if (dto.getExecuteStates() != null && dto.getExecuteStates().size() > 0) {
            sql.append("   AND ").append(SqlBuildUtil.getIntegerInCondi((String)"EXECUTESTATE", (List)dto.getExecuteStates())).append(" \n");
        }
        if (dto.getDimCodes() != null && dto.getDimCodes().size() > 0) {
            sql.append("   AND ").append(SqlBuildUtil.getStrInCondi((String)"DIMCODE", (List)dto.getDimCodes())).append(" \n");
        }
        sql.append(" ORDER BY STARTTIME DESC \n");
        GcBizJdbcTemplate jdbcTemplate = OuterDataSourceUtils.getJdbcTemplate();
        String tempSql = jdbcTemplate.getIDbSqlHandler().getPageSql(sql.toString(), dto.getPage().intValue(), dto.getPageSize().intValue());
        return jdbcTemplate.query(tempSql, (rs, rowNum) -> {
            LogManagerDetailVO detailVO = new LogManagerDetailVO();
            detailVO.setId(rs.getString(1));
            detailVO.setInstanceId(rs.getString(2));
            detailVO.setPrenodeId(rs.getString(3));
            detailVO.setTaskType(rs.getString(4));
            detailVO.setDimType(rs.getString(5));
            detailVO.setDimCode(rs.getString(6));
            detailVO.setMessage(rs.getString(7));
            detailVO.setExecuteState(Integer.valueOf(rs.getInt(8)));
            detailVO.setStartTime((Date)rs.getTimestamp(9));
            detailVO.setEndTime((Date)rs.getTimestamp(10));
            detailVO.setProgress(Double.valueOf(rs.getDouble(11)));
            detailVO.setRunnerId(rs.getString(12));
            detailVO.setQueueName(rs.getString(13));
            return detailVO;
        }, params.toArray());
    }

    @Override
    public Integer listDetailTotal(LogManagerDTO dto) {
        StringBuilder sql = new StringBuilder();
        ArrayList<Object> params = new ArrayList<Object>();
        sql.append("SELECT COUNT(*)\n");
        sql.append("  FROM ").append("GC_LOG_TASKITEMINFO").append("\n");
        sql.append(" WHERE 1 = 1 \n");
        if (!StringUtils.isEmpty((String)dto.getId())) {
            sql.append("   AND ID = ? \n");
            params.add(dto.getId());
        }
        if (!StringUtils.isEmpty((String)dto.getTaskType())) {
            sql.append("   AND TASKTYPE = ? \n");
            params.add(dto.getTaskType());
        }
        if (dto.getStartTime() != null) {
            sql.append("   AND STARTTIME >= ? \n");
            params.add(dto.getStartTime());
        }
        if (dto.getEndTime() != null) {
            sql.append("   AND STARTTIME <= ? \n");
            params.add(dto.getEndTime());
        }
        if (!StringUtils.isEmpty((String)dto.getInstanceId())) {
            sql.append("   AND INSTANCEID = ? \n");
            params.add(dto.getInstanceId());
        }
        if (!StringUtils.isEmpty((String)dto.getRunnerId())) {
            sql.append("   AND RUNNERID = ? \n");
            params.add(dto.getRunnerId());
        }
        if (Objects.equals("LEVEL", dto.getShowType())) {
            sql.append(" AND PRENODEID = ? \n");
            params.add(UUIDUtils.emptyHalfGUIDStr());
        }
        if (!StringUtils.isEmpty((String)dto.getPreNodeId())) {
            sql.append("   AND PRENODEID = ? \n");
            params.add(dto.getPreNodeId());
        }
        if (!StringUtils.isEmpty((String)dto.getDimType())) {
            sql.append("   AND DIMTYPE = ? \n");
            params.add(dto.getDimType());
        }
        if (!StringUtils.isEmpty((String)dto.getDimCode())) {
            sql.append("   AND DIMCODE LIKE ? \n");
            params.add("%" + dto.getDimCode() + "%");
        }
        if (dto.getExecuteStates() != null && dto.getExecuteStates().size() > 0) {
            sql.append("   AND ").append(SqlBuildUtil.getIntegerInCondi((String)"EXECUTESTATE", (List)dto.getExecuteStates())).append(" \n");
        }
        if (dto.getDimCodes() != null && dto.getDimCodes().size() > 0) {
            sql.append("   AND ").append(SqlBuildUtil.getStrInCondi((String)"DIMCODE", (List)dto.getDimCodes())).append(" \n");
        }
        return (Integer)OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), rs -> {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return null;
        }, params.toArray());
    }

    @Override
    public LogManagerVO listOverRefresh(String id) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT T.ID, T.TASKTYPE, T.CREATETIME, T.ENDTIME, T.RESULTLOG \n");
        sql.append("  FROM ").append("GC_LOG_TASKINFO").append(" T \n");
        sql.append(" WHERE T.ID = ? \n");
        sql.append(" GROUP BY T.ID, T.TASKTYPE, T.CREATETIME, T.ENDTIME, T.RESULTLOG \n");
        return (LogManagerVO)OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), rs -> {
            if (rs.next()) {
                LogManagerVO logManagerVO = new LogManagerVO();
                logManagerVO.setId(rs.getString(1));
                logManagerVO.setTaskType(rs.getString(2));
                logManagerVO.setCreateTime((Date)rs.getDate(3));
                logManagerVO.setEndTime((Date)rs.getDate(4));
                logManagerVO.setResultLog(rs.getString(5));
                return logManagerVO;
            }
            return null;
        }, new Object[]{id});
    }

    @Override
    public ExecuteStateVO listOverExecute(String id) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT \n");
        sql.append("\t   COUNT(*) TOTAL,  \n");
        sql.append("\t   SUM(CASE WHEN ITEM.EXECUTESTATE = ").append(DataHandleState.FAILURE.getState()).append(" THEN 1 ELSE 0 END) FAILED, \n");
        sql.append("\t   SUM(CASE WHEN ITEM.EXECUTESTATE = ").append(DataHandleState.SUCCESS.getState()).append(" THEN 1 ELSE 0 END) SUCCESS, \n");
        sql.append("\t   SUM(CASE WHEN ITEM.EXECUTESTATE = ").append(DataHandleState.UNEXECUTE.getState()).append(" THEN 1 ELSE 0 END) UNEXECUTE, \n");
        sql.append("\t   SUM(CASE WHEN ITEM.EXECUTESTATE = ").append(DataHandleState.EXECUTING.getState()).append(" THEN 1 ELSE 0 END) EXECUTING, \n");
        sql.append("\t   SUM(CASE WHEN ITEM.EXECUTESTATE = ").append(DataHandleState.CANCELED.getState()).append(" THEN 1 ELSE 0 END) CANCELED, \n");
        sql.append("       SUM(CASE WHEN ITEM.PRENODEID = '").append(UUIDUtils.emptyHalfGUIDStr()).append("' AND ITEM.EXECUTESTATE = ").append(DataHandleState.UNEXECUTE.getState()).append(" THEN 1 ELSE 0 END) HASCANCEL \n");
        sql.append("  FROM ").append("GC_LOG_TASKITEMINFO").append(" ITEM \n");
        sql.append(" WHERE ITEM.RUNNERID = ?");
        return (ExecuteStateVO)OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), rs -> {
            if (rs.next()) {
                ExecuteStateVO stateVO = new ExecuteStateVO();
                stateVO.setTotal(Integer.valueOf(rs.getInt(1)));
                stateVO.setFailed(Integer.valueOf(rs.getInt(2)));
                stateVO.setSuccess(Integer.valueOf(rs.getInt(3)));
                stateVO.setUnExecute(Integer.valueOf(rs.getInt(4)));
                stateVO.setExecuting(Integer.valueOf(rs.getInt(5)));
                stateVO.setCanceled(Integer.valueOf(rs.getInt(6)));
                stateVO.setHasCancel(Boolean.valueOf(rs.getInt(7) > 0));
                return stateVO;
            }
            return null;
        }, new Object[]{id});
    }

    @Override
    public LogManagerDetailVO getHandleStateByItemId(String id) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT EXECUTESTATE, PROGRESS \n");
        sql.append("  FROM ").append("GC_LOG_TASKITEMINFO").append("\n");
        sql.append(" WHERE ID = ? \n");
        return (LogManagerDetailVO)OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), rs -> {
            LogManagerDetailVO progress = new LogManagerDetailVO();
            if (rs.next()) {
                progress.setExecuteState(Integer.valueOf(rs.getInt(1)));
                progress.setProgress(Double.valueOf(rs.getDouble(2)));
            }
            return progress;
        }, new Object[]{id});
    }

    @Override
    public LogManagerDetailVO getResultLog(String id) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT T.ID, T.INSTANCEID, T.PRENODEID, \n");
        sql.append("       T.TASKTYPE, T.DIMTYPE, T.DIMCODE, \n");
        sql.append("       CASE WHEN T.MESSAGE IS NOT NULL THEN ##MESSAGE## ELSE ##MESSAGEDIGEST## END AS MESSAGE,  \n");
        sql.append("       T.EXECUTESTATE, T.STARTTIME, T.ENDTIME, T.RESULTLOG, T.RUNNERID, T.QUEUENAME, T.EXECUTEAPPNAME \n");
        sql.append("  FROM ").append("GC_LOG_TASKITEMINFO").append(" T \n");
        sql.append(" WHERE ID = ? \n");
        String querySql = sql.toString();
        GcBizJdbcTemplate jdbcTemplate = OuterDataSourceUtils.getJdbcTemplate();
        querySql = querySql.replace("##MESSAGE##", jdbcTemplate.getIDbSqlHandler().toChar("T.MESSAGE"));
        querySql = querySql.replace("##MESSAGEDIGEST##", jdbcTemplate.getIDbSqlHandler().toChar("T.MESSAGEDIGEST"));
        return (LogManagerDetailVO)jdbcTemplate.query(querySql, rs -> {
            if (rs.next()) {
                LogManagerDetailVO detailVO = new LogManagerDetailVO();
                detailVO.setId(rs.getString(1));
                detailVO.setInstanceId(rs.getString(2));
                detailVO.setPrenodeId(rs.getString(3));
                detailVO.setTaskType(rs.getString(4));
                detailVO.setDimType(rs.getString(5));
                detailVO.setDimCode(rs.getString(6));
                detailVO.setMessage(rs.getString(7));
                detailVO.setExecuteState(Integer.valueOf(rs.getInt(8)));
                detailVO.setStartTime((Date)rs.getDate(9));
                detailVO.setEndTime((Date)rs.getDate(10));
                detailVO.setResultLog(rs.getString(11));
                detailVO.setRunnerId(rs.getString(12));
                detailVO.setQueueName(rs.getString(13));
                detailVO.setExecuteAppName(rs.getString(14));
                return detailVO;
            }
            return null;
        }, new Object[]{id});
    }

    @Override
    public List<String> getOverTaskType() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT TASKTYPE\n");
        sql.append("  FROM ").append("GC_LOG_TASKINFO").append(" \n");
        sql.append(" WHERE TASKTYPE IS NOT NULL \n");
        sql.append(" GROUP BY TASKTYPE \n");
        return OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (RowMapper)new StringRowMapper());
    }

    @Override
    public List<String> getDetailTaskType(LogManagerDTO dto) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT TASKTYPE\n");
        sql.append("  FROM ").append("GC_LOG_TASKITEMINFO").append("\n");
        sql.append(" WHERE TASKTYPE IS NOT NULL \n");
        ArrayList<String> args = new ArrayList<String>();
        if (!StringUtils.isEmpty((String)dto.getRunnerId())) {
            sql.append(" AND RUNNERID = ? \n");
            args.add(dto.getRunnerId());
        }
        if (!StringUtils.isEmpty((String)dto.getInstanceId())) {
            sql.append(" AND INSTANCEID = ? \n");
            args.add(dto.getInstanceId());
        }
        if (!StringUtils.isEmpty((String)dto.getPreNodeId())) {
            sql.append(" AND PRENODEID = ? \n");
            args.add(dto.getPreNodeId());
        }
        sql.append(" GROUP BY TASKTYPE \n");
        return OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (RowMapper)new StringRowMapper(), args.toArray());
    }

    @Override
    public List<Map<String, Object>> getExecuteRecordByCondition(LogManagerDTO dto) {
        StringBuilder sql = new StringBuilder();
        ArrayList paramList = CollectionUtils.newArrayList();
        sql.append("SELECT T.TASKTYPE, T.DIMCODE, T.EXECUTESTATE, T.STARTTIME, T.ENDTIME, \n");
        sql.append("  CASE WHEN T.MESSAGE IS NOT NULL THEN ##MESSAGE## ELSE ##MESSAGEDIGEST## END AS MESSAGE,  \n");
        sql.append("  ##RESULTLOG## RESULTLOG \n");
        if (dto.getContainSql().booleanValue()) {
            sql.append(", SI.SQLFULLTEXT, S.STARTTIME AS SQLSTARTTIME, S.ENDTIME AS SQLENDTIME, S.EXECUTEPARAM \n");
        }
        sql.append("  FROM ").append("GC_LOG_TASKITEMINFO").append(" T \n");
        if (dto.getContainSql().booleanValue()) {
            sql.append("  LEFT JOIN ").append("GC_LOG_SQLEXECUTE").append(" S ON T.ID = S.LOGID \n");
            sql.append("  LEFT JOIN ").append("GC_LOG_SQLINFO").append(" SI ON SI.ID = S.SQLINFOID \n");
        }
        sql.append(" WHERE 1 = 1");
        if (!StringUtils.isEmpty((String)dto.getRunnerId())) {
            sql.append(" AND T.RUNNERID = ? \n");
            paramList.add(dto.getRunnerId());
        }
        if (!StringUtils.isEmpty((String)dto.getTaskType())) {
            sql.append(" AND T.TASKTYPE = ? \n");
            paramList.add(dto.getTaskType());
        }
        if (!StringUtils.isEmpty((String)dto.getDimCode())) {
            sql.append(" AND T.DIMCODE = ? \n");
            paramList.add(dto.getDimCode());
        }
        if (!CollectionUtils.isEmpty((Collection)dto.getExecuteStates())) {
            sql.append(SqlBuildUtil.getIntegerInCondi((String)" AND T.EXECUTESTATE", (List)dto.getExecuteStates())).append("\n");
        }
        if (Objects.nonNull(dto.getStartTime())) {
            sql.append(" AND T.STARTTIME >= ? \n");
            paramList.add(dto.getStartTime());
        }
        if (Objects.nonNull(dto.getEndTime())) {
            sql.append(" AND T.STARTTIME <= ? \n");
            paramList.add(dto.getEndTime());
        }
        sql.append(" ORDER BY T.STARTTIME \n");
        if (dto.getContainSql().booleanValue()) {
            sql.append(", S.STARTTIME \n");
        }
        GcBizJdbcTemplate jdbcTemplate = OuterDataSourceUtils.getJdbcTemplate();
        String querySql = sql.toString();
        querySql = querySql.replace("##RESULTLOG##", jdbcTemplate.getIDbSqlHandler().toChar("T.RESULTLOG"));
        querySql = querySql.replace("##MESSAGE##", jdbcTemplate.getIDbSqlHandler().toChar("T.MESSAGE"));
        querySql = querySql.replace("##MESSAGEDIGEST##", jdbcTemplate.getIDbSqlHandler().toChar("T.MESSAGEDIGEST"));
        return jdbcTemplate.query(querySql, (rs, rowNum) -> {
            HashMap record = CollectionUtils.newHashMap();
            for (ExecuteRecordColumn column : ExecuteRecordColumn.values()) {
                if (!dto.getContainSql().booleanValue() && column.isSqlColumn().booleanValue()) continue;
                record.put(column.name(), column.getObject(rs));
            }
            return record;
        }, paramList.toArray());
    }

    @Override
    public List<Map<String, Object>> getExecuteErrorRecord(String taskId) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT T.TASKTYPE, T.DIMCODE, T.STARTTIME, T.ENDTIME, T.RESULTLOG \n");
        sql.append("  FROM ").append("GC_LOG_TASKITEMINFO").append(" T \n");
        sql.append(" WHERE T.RUNNERID = ? \n");
        sql.append("   AND T.EXECUTESTATE = 0 \n");
        sql.append(" ORDER BY T.STARTTIME \n");
        String querySql = sql.toString();
        return OuterDataSourceUtils.getJdbcTemplate().query(querySql, (rs, rowNum) -> {
            HashMap record = CollectionUtils.newHashMap();
            for (ExecuteErrorRecordColumn column : ExecuteErrorRecordColumn.values()) {
                record.put(column.name(), column.getObject(rs));
            }
            return record;
        }, new Object[]{taskId});
    }

    @Override
    public List<Map<String, Object>> getSqlRecord(String taskItemId) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT SI.SQLFULLTEXT, S.STARTTIME AS STARTTIME, S.ENDTIME AS ENDTIME, S.EXECUTEPARAM \n");
        sql.append("  FROM ").append("GC_LOG_SQLEXECUTE").append(" S \n");
        sql.append("  LEFT JOIN ").append("GC_LOG_SQLINFO").append(" SI ON SI.ID = S.SQLINFOID \n");
        sql.append(" WHERE S.LOGID = ? \n");
        sql.append(" ORDER BY S.STARTTIME \n");
        return OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (rs, rowNum) -> {
            HashMap record = CollectionUtils.newHashMap();
            for (SqlRecordColumn column : SqlRecordColumn.values()) {
                record.put(column.name(), column.getObject(rs));
            }
            return record;
        }, new Object[]{taskItemId});
    }
}

