/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.common.enums.TaskStateEnum
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.gcreport.calculate.dao.impl;

import com.jiuqi.gcreport.calculate.common.GcCalcLogOperateEnum;
import com.jiuqi.gcreport.calculate.dao.GcCalcLogDao;
import com.jiuqi.gcreport.calculate.entity.GcCalcLogEO;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.common.enums.TaskStateEnum;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.util.StringUtils;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class GcCalcLogDaoImpl
extends GcDbSqlGenericDAO<GcCalcLogEO, String>
implements GcCalcLogDao {
    public GcCalcLogDaoImpl() {
        super(GcCalcLogEO.class);
    }

    @Override
    public GcCalcLogEO queryLatestLogs(GcCalcLogOperateEnum operateEnum, String taskId, String currency, String periodStr, String orgType, String orgId, String selectAdjustCode) {
        Objects.requireNonNull(operateEnum, "\u5408\u5e76\u65e5\u5fd7\u64cd\u4f5c\u7c7b\u578b\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(taskId, "\u4efb\u52a1\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(periodStr, "\u65f6\u671f\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(orgType, "\u5355\u4f4d\u7c7b\u578b\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(orgId, "\u5355\u4f4d\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        String adjustCode = StringUtils.isEmpty((String)selectAdjustCode) ? "0" : selectAdjustCode;
        String sql = " select " + SqlUtils.getColumnsSqlByEntity(GcCalcLogEO.class, (String)"e") + "\n from " + "GC_CALC_LOG" + "  e \n where e.operate = ? and e.taskId = ? and e.currency = ? and e.period = ? and e.adjust = ? and e.orgType = ? and e.orgId = ? and e.latestFlag = ?";
        List calcLogEOs = this.selectEntity(sql, new Object[]{operateEnum.getName(), taskId, currency, periodStr, adjustCode, orgType, orgId, 1});
        if (CollectionUtils.isEmpty(calcLogEOs)) {
            return null;
        }
        return (GcCalcLogEO)((Object)calcLogEOs.get(0));
    }

    @Override
    public Long queryLockLogBeginTimeByDim(GcCalcLogOperateEnum operateEnum, String taskId, String currency, String periodStr, String orgType, String orgId, String selectAdjustCode) {
        Objects.requireNonNull(operateEnum, "\u5408\u5e76\u65e5\u5fd7\u64cd\u4f5c\u7c7b\u578b\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(taskId, "\u4efb\u52a1\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(periodStr, "\u65f6\u671f\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(orgType, "\u5355\u4f4d\u7c7b\u578b\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(orgId, "\u5355\u4f4d\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        String adjustCode = StringUtils.isEmpty((String)selectAdjustCode) ? "0" : selectAdjustCode;
        String sql = " select e.begintime as BEGINTIME \n from GC_CALC_LOG  e \n where e.operate = ? and e.taskId = ? and e.currency = ? and e.period = ? and e.adjust = ? and e.orgType = ? and e.orgId = ? and e.lockFlag = ?";
        List recordSet = this.selectFirstList(Long.class, sql, new Object[]{operateEnum.getName(), taskId, currency, periodStr, adjustCode, orgType, orgId, 1});
        Long beginTime = null;
        if (!recordSet.isEmpty() && recordSet.get(0) != null) {
            beginTime = (Long)recordSet.get(0);
        }
        return beginTime;
    }

    @Override
    public Integer unLockLogByDim(GcCalcLogOperateEnum operateEnum, String taskId, String currency, String periodStr, String orgType, String orgId, String selectAdjustCode) {
        Objects.requireNonNull(operateEnum, "\u5408\u5e76\u65e5\u5fd7\u64cd\u4f5c\u7c7b\u578b\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(taskId, "\u4efb\u52a1\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(periodStr, "\u65f6\u671f\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(orgType, "\u5355\u4f4d\u7c7b\u578b\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(orgId, "\u5355\u4f4d\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        String adjustCode = StringUtils.isEmpty((String)selectAdjustCode) ? "0" : selectAdjustCode;
        String sql = " update GC_CALC_LOG  e set lockFlag = ? \n where e.operate = ? and e.taskId = ? and e.currency = ? and e.period = ? and e.adjust = ? and e.orgType = ? and e.orgId = ? and e.lockFlag = ?";
        Integer count = this.execute(sql, new Object[]{0, operateEnum.getName(), taskId, currency, periodStr, adjustCode, orgType, orgId, 1});
        return count;
    }

    @Override
    public List<GcCalcLogEO> queryLatestLogs(GcCalcLogOperateEnum operateEnum, String taskId, String currency, String periodStr, TaskStateEnum taskStateEnum, String selectAdjustCode) {
        Objects.requireNonNull(operateEnum, "\u5408\u5e76\u65e5\u5fd7\u64cd\u4f5c\u7c7b\u578b\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(taskId, "\u4efb\u52a1\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(periodStr, "\u65f6\u671f\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        String adjustCode = StringUtils.isEmpty((String)selectAdjustCode) ? "0" : selectAdjustCode;
        String sql = " select " + SqlUtils.getColumnsSqlByEntity(GcCalcLogEO.class, (String)"e") + "\n from " + "GC_CALC_LOG" + "  e \n where e.operate = ? and e.taskstate = ? and e.taskId = ? and e.currency = ? and e.period = ? and e.adjust = ? and e.latestFlag = ?";
        return this.selectEntity(sql, new Object[]{operateEnum.getName(), taskStateEnum.getCode(), taskId, currency, periodStr, adjustCode, 1});
    }

    @Override
    public GcCalcLogEO queryLogById(String logId) {
        Objects.requireNonNull(logId, "\u5408\u5e76\u65e5\u5fd7ID\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        String sql = " select " + SqlUtils.getColumnsSqlByEntity(GcCalcLogEO.class, (String)"e") + "\n from " + "GC_CALC_LOG" + "  e \n where e.id = ?";
        List calcLogInfoEOs = this.selectEntity(sql, new Object[]{logId});
        if (CollectionUtils.isEmpty(calcLogInfoEOs)) {
            return null;
        }
        return (GcCalcLogEO)((Object)calcLogInfoEOs.get(0));
    }

    @Override
    public List<GcCalcLogEO> queryLatestLogs(GcCalcLogOperateEnum operateEnum, String taskId, String currency, String periodStr, String selectAdjustCode) {
        Objects.requireNonNull(operateEnum, "\u5408\u5e76\u65e5\u5fd7\u64cd\u4f5c\u7c7b\u578b\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(taskId, "\u4efb\u52a1\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(periodStr, "\u65f6\u671f\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        String adjustCode = StringUtils.isEmpty((String)selectAdjustCode) ? "0" : selectAdjustCode;
        String sql = " select " + SqlUtils.getColumnsSqlByEntity(GcCalcLogEO.class, (String)"e") + "\n from " + "GC_CALC_LOG" + "  e \n where e.operate = ?  and e.taskId = ? and e.currency = ? and e.period = ? and e.adjust = ? and e.latestFlag = ?";
        return this.selectEntity(sql, new Object[]{operateEnum.getName(), taskId, currency, periodStr, adjustCode, 1});
    }

    @Override
    public List<GcCalcLogEO> queryLatestLogs(GcCalcLogOperateEnum operateEnum, String taskId, String periodStr, String selectAdjustCode) {
        Objects.requireNonNull(operateEnum, "\u5408\u5e76\u65e5\u5fd7\u64cd\u4f5c\u7c7b\u578b\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(taskId, "\u4efb\u52a1\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(periodStr, "\u65f6\u671f\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        String adjustCode = StringUtils.isEmpty((String)selectAdjustCode) ? "0" : selectAdjustCode;
        String sql = " select " + SqlUtils.getColumnsSqlByEntity(GcCalcLogEO.class, (String)"e") + "\n from " + "GC_CALC_LOG" + "  e \n where e.operate = ?  and e.taskId = ? and e.period = ? and e.adjust = ? and e.latestFlag = ?";
        return this.selectEntity(sql, new Object[]{operateEnum.getName(), taskId, periodStr, adjustCode, 1});
    }

    @Override
    public Integer updateLogToUnLatestState(GcCalcLogOperateEnum operateEnum, String taskId, String currency, String periodStr, String orgType, String orgId, String excludeLogId, String selectAdjustCode) {
        Objects.requireNonNull(operateEnum, "\u5408\u5e76\u65e5\u5fd7\u64cd\u4f5c\u7c7b\u578b\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(taskId, "\u4efb\u52a1\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(periodStr, "\u65f6\u671f\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(orgType, "\u5355\u4f4d\u7c7b\u578b\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(orgId, "\u5355\u4f4d\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        String adjustCode = StringUtils.isEmpty((String)selectAdjustCode) ? "0" : selectAdjustCode;
        String sql = " update GC_CALC_LOG  e set latestFlag = ? \n where e.operate = ? and e.taskId = ? and e.currency = ? and e.period = ? and e.adjust = ? and e.orgType = ? and e.orgId = ? and e.id <> ? and e.latestFlag = ?";
        Integer count = this.execute(sql, new Object[]{0, operateEnum.getName(), taskId, currency, periodStr, adjustCode, orgType, orgId, excludeLogId, 1});
        return count;
    }

    @Override
    public Integer updateCalcLog(String logId, String loginfo, TaskStateEnum taskStateEnum) {
        Objects.requireNonNull(logId, "\u5408\u5e76\u65e5\u5fd7ID\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        String sql = " update GC_CALC_LOG  e  set info = ?,taskState=?, endtime= ?, lockflag=?\n where e.id = ?";
        Integer count = this.execute(sql, new Object[]{loginfo, taskStateEnum.getCode(), new Date().getTime(), 0, logId});
        return count;
    }

    @Override
    public Integer updateCalcLog(GcCalcLogOperateEnum operateEnum, String taskId, String currency, String periodStr, String orgType, String orgId, String loginfo, TaskStateEnum taskStateEnum, String selectAdjustCode) {
        Objects.requireNonNull(operateEnum, "\u5408\u5e76\u65e5\u5fd7\u64cd\u4f5c\u7c7b\u578b\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(taskId, "\u4efb\u52a1\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(periodStr, "\u65f6\u671f\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(orgType, "\u5355\u4f4d\u7c7b\u578b\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(orgId, "\u5355\u4f4d\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        String adjustCode = StringUtils.isEmpty((String)selectAdjustCode) ? "0" : selectAdjustCode;
        String sql = " update GC_CALC_LOG  e  set info = ?,taskState=?, endtime= ?, lockflag=? \n where e.operate = ? and e.taskId = ? and e.currency = ? and e.period = ? and e.adjust = ? and e.orgType = ? and e.orgId = ? and e.latestFlag = ?";
        Integer count = this.execute(sql, new Object[]{loginfo, taskStateEnum.getCode(), new Date().getTime(), 0, operateEnum.getName(), taskId, currency, periodStr, adjustCode, orgType, orgId, 1});
        return count;
    }

    @Override
    public Integer updateCalcLog(String logId, TaskStateEnum taskStateEnum) {
        Objects.requireNonNull(logId, "\u5408\u5e76\u65e5\u5fd7ID\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        String sql = " update GC_CALC_LOG  e  set taskState=? \n where e.id = ?";
        Integer count = this.execute(sql, new Object[]{logId, taskStateEnum.getCode()});
        return count;
    }

    @Override
    public GcCalcLogEO queryCurrOrgLatestCalcLogEO(GcCalcLogOperateEnum operateEnum, List<String> taskIdList, String orgId, String currency, String periodStr, String selectAdjustCode) {
        Objects.requireNonNull(operateEnum, "\u5408\u5e76\u65e5\u5fd7\u64cd\u4f5c\u7c7b\u578b\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(taskIdList, "\u4efb\u52a1\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(periodStr, "\u65f6\u671f\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(orgId, "\u5355\u4f4d\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        String adjustCode = StringUtils.isEmpty((String)selectAdjustCode) ? "0" : selectAdjustCode;
        String sql = " select " + SqlUtils.getColumnsSqlByEntity(GcCalcLogEO.class, (String)"e") + "\n from " + "GC_CALC_LOG" + "  e \n where e.operate = ? and " + SqlUtils.getConditionOfIdsUseOr(taskIdList, (String)"e.taskId") + " and e.period = ? and e.adjust = ? and e.orgId = ? and e.currency = ? and e.latestFlag = ? order by endtime desc";
        List calcLogEOs = this.selectEntity(sql, new Object[]{operateEnum.getName(), periodStr, adjustCode, orgId, currency, 1});
        if (CollectionUtils.isEmpty(calcLogEOs)) {
            return null;
        }
        return (GcCalcLogEO)((Object)calcLogEOs.get(0));
    }
}

