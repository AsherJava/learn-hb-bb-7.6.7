/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.certify.BdeRequestCertifyConfig
 *  com.jiuqi.bde.common.constant.RequestSourceTypeEnum
 *  com.jiuqi.bde.log.enums.FetchDimType
 *  com.jiuqi.bde.logmanager.client.LogManagerCondition
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl
 *  com.jiuqi.dc.base.common.jdbc.extractor.IntegerResultSetExtractor
 *  com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskItemLogEO
 *  com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskLogEO
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.logmanager.impl.dao.impl;

import com.jiuqi.bde.common.certify.BdeRequestCertifyConfig;
import com.jiuqi.bde.common.constant.RequestSourceTypeEnum;
import com.jiuqi.bde.log.enums.FetchDimType;
import com.jiuqi.bde.logmanager.client.LogManagerCondition;
import com.jiuqi.bde.logmanager.impl.dao.TaskLogManagerDao;
import com.jiuqi.bde.logmanager.impl.intf.FetchExecuteStatus;
import com.jiuqi.bde.logmanager.impl.intf.FetchFormStatus;
import com.jiuqi.bde.logmanager.impl.intf.FetchSqlLogDTO;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl;
import com.jiuqi.dc.base.common.jdbc.extractor.IntegerResultSetExtractor;
import com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskItemLogEO;
import com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskLogEO;
import com.jiuqi.va.mapper.common.JDialectUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

@Repository
public class TaskLogManagerDaoImpl
extends BaseDataCenterDaoImpl
implements TaskLogManagerDao {
    @Override
    public Integer countLog(LogManagerCondition condition) {
        StringBuffer sql = new StringBuffer();
        ArrayList<Date> params = new ArrayList<Date>();
        sql.append("SELECT  \n");
        sql.append("  COUNT(1)  \n");
        sql.append("  FROM GC_LOG_TASKINFO TASKINFO  \n");
        sql.append(" WHERE 1 = 1  \n");
        if (!StringUtils.isEmpty((String)condition.getUnitCode())) {
            sql.append(String.format("   AND TASKINFO.EXT_2 LIKE '%1$s%%'  \n", condition.getUnitCode()));
        }
        sql.append("   AND").append(SqlBuildUtil.getStrInCondi((String)" TASKTYPE ", this.getSourceTypes())).append("\n");
        if (condition.getStartTime() != null) {
            sql.append("   AND CREATETIME >= ? \n");
            params.add(condition.getStartTime());
        }
        if (condition.getEndTime() != null) {
            sql.append("   AND CREATETIME < ? \n");
            params.add(condition.getEndTime());
        }
        return (Integer)OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (ResultSetExtractor)new IntegerResultSetExtractor(), params.toArray());
    }

    @Override
    public Map<String, FetchExecuteStatus> countExecuteStatus(List<String> requestInstcIdList) {
        Assert.isNotEmpty(requestInstcIdList);
        StringBuffer sql = new StringBuffer();
        sql.append("  SELECT RUNNERID,  \n");
        sql.append("         ID,  \n");
        sql.append("         SUM(CASE WHEN EXECUTESTATE = 1 THEN 1 ELSE 0 END) AS SUCCESSCT,  \n");
        sql.append("         SUM(CASE WHEN EXECUTESTATE IN (2,3) THEN 1 ELSE 0 END) AS EXECUTEINGCT,  \n");
        sql.append("         SUM(CASE WHEN EXECUTESTATE IN (0,4) THEN 1 ELSE 0 END) AS FAILEDCT,  \n");
        sql.append("         MAX(ENDTIME) AS ENDDATE \n");
        sql.append("    FROM GC_LOG_TASKITEMINFO  \n");
        sql.append("   WHERE 1=1  \n");
        sql.append("             AND").append(SqlBuildUtil.getStrInCondi((String)" RUNNERID ", requestInstcIdList)).append("\n");
        sql.append(String.format("             AND DIMTYPE = '%1$s'  \n", FetchDimType.FORM.getName()));
        sql.append("GROUP BY RUNNERID,ID  \n");
        Map statusMap = (Map)OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), rs -> {
            HashMap<String, FetchFormStatus> result = new HashMap<String, FetchFormStatus>();
            String runnerId = null;
            String formLogId = null;
            while (rs.next()) {
                runnerId = rs.getString(1);
                formLogId = rs.getString(2);
                result.computeIfAbsent(formLogId, key -> new FetchFormStatus());
                ((FetchFormStatus)result.get(formLogId)).setRunnerId(runnerId);
                ((FetchFormStatus)result.get(formLogId)).setSuccessCount(rs.getInt(3));
                ((FetchFormStatus)result.get(formLogId)).setExecuteCount(rs.getInt(4));
                ((FetchFormStatus)result.get(formLogId)).setFailedCount(rs.getInt(5));
                ((FetchFormStatus)result.get(formLogId)).setEndDate(rs.getTimestamp(6));
            }
            return result;
        }, new Object[0]);
        sql = new StringBuffer();
        sql.append("SELECT REGIONITEM.PRENODEID,  \n");
        sql.append("       CASE WHEN COMPUTITEM.INSTANCEID IS NULL THEN 0 ELSE SUM(M_SUCCESSCT) END + SUM(CASE WHEN REGIONITEM.EXECUTESTATE = 1 THEN 1 ELSE 0 END) AS SUCCESSCT,  \n");
        sql.append("       CASE WHEN COMPUTITEM.INSTANCEID IS NULL THEN 0 ELSE SUM(M_EXECUTEINGCT) END + SUM(CASE WHEN REGIONITEM.EXECUTESTATE IN (2,3) THEN 1 ELSE 0 END) AS EXECUTEINGCT,  \n");
        sql.append("       CASE WHEN COMPUTITEM.INSTANCEID IS NULL THEN 0 ELSE SUM(M_FAILEDCT) END + SUM(CASE WHEN REGIONITEM.EXECUTESTATE IN (0,4) THEN 1 ELSE 0 END) AS FAILEDCT  \n");
        sql.append("  FROM GC_LOG_TASKITEMINFO REGIONITEM  \n");
        sql.append("    LEFT JOIN ( SELECT INSTANCEID,  \n");
        sql.append("                  SUM(CASE WHEN EXECUTESTATE = 1 THEN 1 ELSE 0 END) AS M_SUCCESSCT,  \n");
        sql.append("              SUM(CASE WHEN EXECUTESTATE IN (2,3) THEN 1 ELSE 0 END) AS M_EXECUTEINGCT,  \n");
        sql.append("                  SUM(CASE WHEN EXECUTESTATE IN (0,4) THEN 1 ELSE 0 END) AS M_FAILEDCT  \n");
        sql.append("             FROM GC_LOG_TASKITEMINFO  \n");
        sql.append("            WHERE 1=1  \n");
        sql.append("              AND").append(SqlBuildUtil.getStrInCondi((String)" RUNNERID ", requestInstcIdList)).append("\n");
        sql.append(String.format("             AND DIMTYPE = '%1$s'  \n", FetchDimType.COMPUTAT.getName()));
        sql.append("         GROUP BY INSTANCEID ) COMPUTITEM  \n");
        sql.append("      ON COMPUTITEM.INSTANCEID = REGIONITEM.INSTANCEID  \n");
        sql.append("   WHERE 1=1  \n");
        sql.append("     AND").append(SqlBuildUtil.getStrInCondi((String)" REGIONITEM.RUNNERID ", requestInstcIdList)).append("\n");
        sql.append(String.format("     AND REGIONITEM.DIMTYPE = '%1$s'  \n", FetchDimType.REGION.getName()));
        sql.append("GROUP BY REGIONITEM.PRENODEID, COMPUTITEM.INSTANCEID, REGIONITEM.INSTANCEID  \n");
        Map executeStatusMap = (Map)OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), rs -> {
            HashMap<String, FetchExecuteStatus> result = new HashMap<String, FetchExecuteStatus>();
            String formLogId = null;
            while (rs.next()) {
                formLogId = rs.getString(1);
                result.computeIfAbsent(formLogId, key -> new FetchExecuteStatus());
                ((FetchExecuteStatus)result.get(formLogId)).setSuccessCount(((FetchExecuteStatus)result.get(formLogId)).getSuccessCount() + rs.getInt(2));
                ((FetchExecuteStatus)result.get(formLogId)).setExecuteCount(((FetchExecuteStatus)result.get(formLogId)).getExecuteCount() + rs.getInt(3));
                ((FetchExecuteStatus)result.get(formLogId)).setFailedCount(((FetchExecuteStatus)result.get(formLogId)).getFailedCount() + rs.getInt(4));
            }
            return result;
        }, new Object[0]);
        for (Map.Entry formStatusEntry : statusMap.entrySet()) {
            if (executeStatusMap.get(formStatusEntry.getKey()) == null) continue;
            ((FetchFormStatus)formStatusEntry.getValue()).setSuccessCount(((FetchFormStatus)formStatusEntry.getValue()).getSuccessCount() + ((FetchExecuteStatus)executeStatusMap.get(formStatusEntry.getKey())).getSuccessCount());
            ((FetchFormStatus)formStatusEntry.getValue()).setExecuteCount(((FetchFormStatus)formStatusEntry.getValue()).getExecuteCount() + ((FetchExecuteStatus)executeStatusMap.get(formStatusEntry.getKey())).getExecuteCount());
            ((FetchFormStatus)formStatusEntry.getValue()).setFailedCount(((FetchFormStatus)formStatusEntry.getValue()).getFailedCount() + ((FetchExecuteStatus)executeStatusMap.get(formStatusEntry.getKey())).getFailedCount());
        }
        HashMap fetchTaskExecuteMap = CollectionUtils.newHashMap();
        for (FetchFormStatus formStatus : statusMap.values()) {
            fetchTaskExecuteMap.computeIfAbsent(formStatus.getRunnerId(), key -> new FetchExecuteStatus());
            ((FetchExecuteStatus)fetchTaskExecuteMap.get(formStatus.getRunnerId())).setEndDate(formStatus.getEndDate());
            if (formStatus.getExecuteCount() > 0) {
                ((FetchExecuteStatus)fetchTaskExecuteMap.get(formStatus.getRunnerId())).setExecuteCount(((FetchExecuteStatus)fetchTaskExecuteMap.get(formStatus.getRunnerId())).getExecuteCount() + 1);
                continue;
            }
            if (formStatus.getFailedCount() > 0) {
                ((FetchExecuteStatus)fetchTaskExecuteMap.get(formStatus.getRunnerId())).setFailedCount(((FetchExecuteStatus)fetchTaskExecuteMap.get(formStatus.getRunnerId())).getFailedCount() + 1);
                continue;
            }
            ((FetchExecuteStatus)fetchTaskExecuteMap.get(formStatus.getRunnerId())).setSuccessCount(((FetchExecuteStatus)fetchTaskExecuteMap.get(formStatus.getRunnerId())).getSuccessCount() + 1);
        }
        return fetchTaskExecuteMap;
    }

    @Override
    public Map<String, FetchExecuteStatus> countTaskExecuteStatus(String runnerId) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT REGIONITEM.ID,  \n");
        sql.append("       CASE WHEN SUM(M_SUCCESSCT)IS NULL THEN SUM(CASE WHEN REGIONITEM.EXECUTESTATE = 1 THEN 1 ELSE 0 END) ELSE SUM(M_SUCCESSCT) END  AS SUCCESSCT,  \n");
        sql.append("       CASE WHEN SUM(M_EXECUTEINGCT) IS NULL THEN SUM(CASE WHEN REGIONITEM.EXECUTESTATE IN (2,3) THEN 1 ELSE 0 END) ELSE SUM(M_EXECUTEINGCT) END AS EXECUTEINGCT,  \n");
        sql.append("       CASE WHEN SUM(M_FAILEDCT) IS NULL THEN SUM(CASE WHEN REGIONITEM.EXECUTESTATE IN (0,4) THEN 1 ELSE 0 END) ELSE SUM(M_FAILEDCT) END  AS FAILEDCT,  \n");
        sql.append("       CASE WHEN MAX(COMPUTITEM.M_ENDTIME) IS NULL THEN MAX(REGIONITEM.ENDTIME) ELSE MAX(COMPUTITEM.M_ENDTIME) END  AS ENDDATE  \n");
        sql.append("  FROM GC_LOG_TASKITEMINFO REGIONITEM  \n");
        sql.append("    LEFT JOIN (SELECT INSTANCEID,  \n");
        sql.append("                  SUM(CASE WHEN EXECUTESTATE = 1 THEN 1 ELSE 0 END) AS M_SUCCESSCT,  \n");
        sql.append("                  SUM(CASE WHEN EXECUTESTATE IN (2,3) THEN 1 ELSE 0 END) AS M_EXECUTEINGCT,  \n");
        sql.append("                  SUM(CASE WHEN EXECUTESTATE IN (0,4) THEN 1 ELSE 0 END) AS M_FAILEDCT,  \n");
        sql.append("                  MAX(ENDTIME) AS M_ENDTIME   \n");
        sql.append("             FROM GC_LOG_TASKITEMINFO  \n");
        sql.append("            WHERE 1=1  \n");
        sql.append("              AND  RUNNERID = ? \n");
        sql.append(String.format("             AND DIMTYPE = '%1$s'  \n", FetchDimType.COMPUTAT.getName()));
        sql.append("         GROUP BY INSTANCEID ) COMPUTITEM  \n");
        sql.append("      ON COMPUTITEM.INSTANCEID = REGIONITEM.INSTANCEID  \n");
        sql.append("   WHERE 1=1  \n");
        sql.append("     AND  REGIONITEM.RUNNERID = ? \n");
        sql.append(String.format("     AND REGIONITEM.DIMTYPE = '%1$s'  \n", FetchDimType.REGION.getName()));
        sql.append("GROUP BY REGIONITEM.ID  \n");
        return (Map)OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), rs -> {
            HashMap<String, FetchExecuteStatus> result = new HashMap<String, FetchExecuteStatus>();
            String id = null;
            while (rs.next()) {
                id = rs.getString(1);
                result.computeIfAbsent(id, key -> new FetchExecuteStatus());
                ((FetchExecuteStatus)result.get(id)).setSuccessCount(rs.getInt(2));
                ((FetchExecuteStatus)result.get(id)).setExecuteCount(rs.getInt(3));
                ((FetchExecuteStatus)result.get(id)).setFailedCount(rs.getInt(4));
                ((FetchExecuteStatus)result.get(id)).setEndDate(rs.getTimestamp(5));
            }
            return result;
        }, new Object[]{runnerId, runnerId});
    }

    @Override
    public Map<String, FetchExecuteStatus> countItemExecuteStatus(String requestInstcId) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT PRENODEID, \n");
        sql.append("       SUM(CASE WHEN EXECUTESTATE = 1 THEN 1 ELSE 0 END) AS SUCCESSCOUNT,  \n");
        sql.append("       SUM(CASE WHEN EXECUTESTATE IN (2,3) THEN 1 ELSE 0 END) AS EXECUTEINGCOUNT,  \n");
        sql.append("       SUM(CASE WHEN EXECUTESTATE IN (0,4) THEN 1 ELSE 0 END) AS FAILEDCOUNT,  \n");
        sql.append("       MAX(ENDTIME) AS ENDDATE \n");
        sql.append("  FROM GC_LOG_TASKITEMINFO  \n");
        sql.append(" WHERE 1 = 1  \n");
        sql.append("   AND RUNNERID = ? \n");
        sql.append(" GROUP BY PRENODEID  \n");
        return (Map)OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), rs -> {
            HashMap<String, FetchExecuteStatus> result = new HashMap<String, FetchExecuteStatus>();
            String prenodeId = null;
            while (rs.next()) {
                prenodeId = rs.getString(1);
                result.computeIfAbsent(prenodeId, key -> new FetchExecuteStatus());
                ((FetchExecuteStatus)result.get(prenodeId)).setSuccessCount(rs.getInt(2));
                ((FetchExecuteStatus)result.get(prenodeId)).setExecuteCount(rs.getInt(3));
                ((FetchExecuteStatus)result.get(prenodeId)).setFailedCount(rs.getInt(4));
                ((FetchExecuteStatus)result.get(prenodeId)).setEndDate(rs.getTimestamp(5));
            }
            return result;
        }, new Object[]{requestInstcId});
    }

    @Override
    public List<DcTaskLogEO> selectLog(LogManagerCondition condition) {
        StringBuffer sql = new StringBuffer();
        ArrayList<Object> params = new ArrayList<Object>();
        sql.append("SELECT  ");
        sql.append(" TASKINFO.ID,  ");
        sql.append(" CREATETIME,  ");
        sql.append(" MESSAGE  ");
        sql.append(" FROM GC_LOG_TASKINFO TASKINFO  ");
        sql.append(" WHERE 1 = 1  ");
        sql.append("   AND").append(SqlBuildUtil.getStrInCondi((String)" TASKTYPE ", this.getSourceTypes())).append("\n");
        if (!StringUtils.isEmpty((String)condition.getRequestInstcId())) {
            sql.append("   AND TASKINFO.ID = ? \n");
            params.add(condition.getRequestInstcId());
        }
        if (!StringUtils.isEmpty((String)condition.getUnitCode())) {
            sql.append(String.format("   AND TASKINFO.EXT_2 LIKE '%1$s%%'  \n", condition.getUnitCode()));
        }
        if (condition.getStartTime() != null) {
            sql.append("   AND CREATETIME >= ? \n");
            params.add(condition.getStartTime());
        }
        if (condition.getEndTime() != null) {
            sql.append("   AND CREATETIME < ? \n");
            params.add(condition.getEndTime());
        }
        sql.append("   ORDER BY CREATETIME DESC  \n");
        String tempSql = condition.getPage() == null ? sql.toString() : JDialectUtil.getInstance().pagin(condition.getPage().intValue(), condition.getPageSize().intValue(), sql.toString());
        List taskInfoList = OuterDataSourceUtils.getJdbcTemplate().query(tempSql, (rs, rowNum) -> {
            DcTaskLogEO taskItemInfo = new DcTaskLogEO();
            taskItemInfo.setId(rs.getString(1));
            taskItemInfo.setCreateTime((Date)rs.getTimestamp(2));
            taskItemInfo.setMessage(rs.getString(3));
            return taskItemInfo;
        }, params.toArray());
        return taskInfoList;
    }

    @Override
    public List<DcTaskItemLogEO> selectItemLog(DcTaskItemLogEO taskInfo) {
        StringBuffer sql = new StringBuffer();
        ArrayList<Object> params = new ArrayList<Object>();
        sql.append("SELECT ITEMINFO.ID,  \n");
        sql.append("       ITEMINFO.DIMTYPE,  \n");
        sql.append("       ITEMINFO.DIMCODE,  \n");
        sql.append("       ITEMINFO.STARTTIME,  \n");
        sql.append("       ITEMINFO.ENDTIME,  \n");
        sql.append("       ITEMINFO.EXECUTESTATE,  \n");
        sql.append("       ITEMINFO.MESSAGE AS MESSAGE,  \n");
        sql.append("       ITEMINFO.RESULTLOG AS RESULTLOG   \n");
        sql.append("  FROM GC_LOG_TASKITEMINFO ITEMINFO  \n");
        sql.append(" WHERE 1 = 1 \n");
        if (!StringUtils.isEmpty((String)taskInfo.getInstanceId())) {
            sql.append("   AND RUNNERID = ?  \n");
            params.add(taskInfo.getInstanceId());
        }
        if (!StringUtils.isEmpty((String)taskInfo.getDimType())) {
            sql.append("   AND DIMTYPE = ?  \n");
            params.add(taskInfo.getDimType());
        }
        if (!StringUtils.isEmpty((String)taskInfo.getPreNodeId())) {
            sql.append("   AND PRENODEID = ?  \n");
            params.add(taskInfo.getPreNodeId());
        }
        if (taskInfo.getExecuteState() != null) {
            sql.append("   AND EXECUTESTATE = ?  \n");
            params.add(taskInfo.getExecuteState());
        }
        sql.append("   ORDER BY STARTTIME, DIMTYPE  \n");
        List taskItemList = (List)OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), rs -> {
            ArrayList<DcTaskItemLogEO> result = new ArrayList<DcTaskItemLogEO>();
            DcTaskItemLogEO taskItemInfo = null;
            while (rs.next()) {
                taskItemInfo = new DcTaskItemLogEO();
                taskItemInfo.setId(rs.getString(1));
                taskItemInfo.setDimType(rs.getString(2));
                taskItemInfo.setDimCode(rs.getString(3));
                taskItemInfo.setStartTime((Date)rs.getTimestamp(4));
                taskItemInfo.setEndTime((Date)rs.getTimestamp(5));
                taskItemInfo.setExecuteState(Integer.valueOf(rs.getInt(6)));
                taskItemInfo.setMessage(rs.getString(7));
                taskItemInfo.setResultLog(rs.getString(8));
                result.add(taskItemInfo);
            }
            return result;
        }, params.toArray());
        return taskItemList;
    }

    @Override
    public String getResultLogById(String id) {
        ArrayList idList = CollectionUtils.newArrayList();
        idList.add(id);
        Map<String, String> resultLogMap = this.getResultLogByIdList(idList);
        return resultLogMap.get(id);
    }

    public Map<String, String> getResultLogByIdList(List<String> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return new HashMap<String, String>();
        }
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ID, RESULTLOG FROM DC_LOG_RESULTLOG WHERE 1 = 1 ");
        sql.append("   AND").append(SqlBuildUtil.getStrInCondi((String)" ID ", idList)).append("\n");
        return (Map)OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), rs -> {
            HashMap<String, String> result = new HashMap<String, String>();
            while (rs.next()) {
                result.put(rs.getString(1), rs.getString(2));
            }
            return result;
        }, new Object[0]);
    }

    public Map<String, String> getSqlFullTextByIdList(List<String> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return new HashMap<String, String>();
        }
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ID, SQLFULLTEXT FROM GC_LOG_SQLINFO WHERE 1 = 1 ");
        sql.append("   AND").append(SqlBuildUtil.getStrInCondi((String)" ID ", idList)).append("\n");
        return (Map)OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), rs -> {
            HashMap<String, String> result = new HashMap<String, String>();
            while (rs.next()) {
                result.put(rs.getString(1), rs.getString(2));
            }
            return result;
        }, new Object[0]);
    }

    @Override
    public List<FetchSqlLogDTO> getExecuteSqlById(String requestInstcId, String requestTaskId) {
        ArrayList<String> params = new ArrayList<String>();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ITEMINFO.DIMTYPE,  ");
        sql.append("       EXESQL.SQLINFOID,  ");
        sql.append("       EXESQL.EXECUTEPARAM,  ");
        sql.append("       EXESQL.STARTTIME,  ");
        sql.append("       EXESQL.ENDTIME  ");
        sql.append("  FROM GC_LOG_SQLEXECUTE EXESQL  ");
        sql.append("  JOIN GC_LOG_TASKITEMINFO ITEMINFO  ");
        sql.append("    ON ITEMINFO.ID = EXESQL.LOGID  ");
        sql.append(" WHERE 1 = 1  ");
        sql.append("   AND RUNNERID = ?  \n");
        params.add(requestInstcId);
        if (!StringUtils.isEmpty((String)requestTaskId)) {
            sql.append("   AND PRENODEID = ? \n");
            params.add(requestTaskId);
        }
        sql.append("   ORDER BY ITEMINFO.STARTTIME,EXESQL.STARTTIME  \n");
        List sqlLogList = (List)OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), rs -> {
            ArrayList<FetchSqlLogDTO> result = new ArrayList<FetchSqlLogDTO>();
            FetchSqlLogDTO sqlLog = null;
            while (rs.next()) {
                sqlLog = new FetchSqlLogDTO();
                sqlLog.setTitle(rs.getString(1));
                sqlLog.setSqlInfoId(rs.getString(2));
                sqlLog.setExecuteParam(rs.getString(3));
                sqlLog.setStartTime(DateUtils.format((Date)new Date(rs.getLong(4)), (String)"yyyy-MM-dd HH:mm:ss"));
                sqlLog.setEndTime(rs.getObject(5) == null ? null : DateUtils.format((Date)new Date(rs.getLong(5)), (String)"yyyy-MM-dd HH:mm:ss"));
                if (rs.getObject(5) == null) {
                    sqlLog.setCostTime(null);
                } else {
                    BigDecimal costTime = NumberUtils.div((BigDecimal)NumberUtils.sub((BigDecimal)rs.getBigDecimal(5), (BigDecimal)rs.getBigDecimal(4)), (double)1000.0);
                    double doubleValue = costTime.setScale(2, 0).doubleValue();
                    sqlLog.setCostTime(doubleValue);
                }
                result.add(sqlLog);
            }
            return result;
        }, params.toArray());
        if (CollectionUtils.isEmpty((Collection)sqlLogList)) {
            return sqlLogList;
        }
        List<String> idList = sqlLogList.stream().map(FetchSqlLogDTO::getSqlInfoId).collect(Collectors.toList());
        Map<String, String> messageMap = this.getSqlFullTextByIdList(idList);
        for (FetchSqlLogDTO sqlLog : sqlLogList) {
            if (!messageMap.containsKey(sqlLog.getSqlInfoId())) continue;
            sqlLog.setSql(messageMap.get(sqlLog.getSqlInfoId()));
        }
        return sqlLogList;
    }

    public List<String> getSourceTypes() {
        ArrayList<String> sourceTypes = new ArrayList<String>();
        for (RequestSourceTypeEnum requestSourceType : RequestSourceTypeEnum.values()) {
            if (RequestSourceTypeEnum.DATACHECK_FETCH.getCode().equals(requestSourceType.getCode())) continue;
            sourceTypes.add(requestSourceType.getCode());
            for (String certifyName : BdeRequestCertifyConfig.getCertifyName()) {
                sourceTypes.add(String.format("%1$s_%2$s", requestSourceType.getCode(), certifyName));
            }
        }
        return sourceTypes;
    }
}

