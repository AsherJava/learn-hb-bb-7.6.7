/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.samecontrol.dao.impl;

import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.samecontrol.dao.SameCtrlExtractLogDao;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlExtractLogEO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class SameCtrlExtractLogDaoImpl
extends GcDbSqlGenericDAO<SameCtrlExtractLogEO, String>
implements SameCtrlExtractLogDao {
    public SameCtrlExtractLogDaoImpl() {
        super(SameCtrlExtractLogEO.class);
    }

    @Override
    public int updateLogToUnLatestStateReportInfo(SameCtrlExtractLogEO sameCtrlExtractLog) {
        String sql = " update GC_SAMETRLEXTRACT_LOG  e set latestFlag = ? \n where e.operate = ? and e.taskId = ? and e.schemeId = ? and e.periodStr = ? and e.orgType = ? and e.changedCode = ? and e.id <> ? and e.latestFlag = ?";
        Integer count = this.execute(sql, new Object[]{0, sameCtrlExtractLog.getOperate(), sameCtrlExtractLog.getTaskId(), sameCtrlExtractLog.getSchemeId(), sameCtrlExtractLog.getPeriodStr(), sameCtrlExtractLog.getOrgType(), sameCtrlExtractLog.getChangedCode(), sameCtrlExtractLog.getId(), 1});
        return count;
    }

    @Override
    public int updateLogToUnLatestStateOffsetInfo(SameCtrlExtractLogEO sameCtrlExtractLog) {
        String sql = " update GC_SAMETRLEXTRACT_LOG  e set latestFlag = ? \n where e.operate = ? and e.taskId = ? and e.schemeId = ? and e.periodStr = ? and e.orgType = ? and e.changedCode = ? and e.virtualParentCode = ? and e.changedParentCode = ? and e.id <> ? and e.latestFlag = ?";
        Integer count = this.execute(sql, new Object[]{0, sameCtrlExtractLog.getOperate(), sameCtrlExtractLog.getTaskId(), sameCtrlExtractLog.getSchemeId(), sameCtrlExtractLog.getPeriodStr(), sameCtrlExtractLog.getOrgType(), sameCtrlExtractLog.getChangedCode(), sameCtrlExtractLog.getVirtualParentCode(), sameCtrlExtractLog.getChangedParentCode(), sameCtrlExtractLog.getId(), 1});
        return count;
    }

    @Override
    public int updateSamrCtrlLogById(SameCtrlExtractLogEO sameCtrlExtractLog) {
        String sql = " update GC_SAMETRLEXTRACT_LOG  e  set info = ?, taskState=?, endTime= ? \n where e.id = ?";
        Integer count = this.execute(sql, new Object[]{sameCtrlExtractLog.getInfo(), sameCtrlExtractLog.getTaskState(), sameCtrlExtractLog.getEndTime(), sameCtrlExtractLog.getId()});
        return count;
    }

    @Override
    public List<SameCtrlExtractLogEO> querySameCtrlExtractLog(SameCtrlExtractLogEO sameCtrlExtractLog) {
        String sql = " select " + SqlUtils.getColumnsSqlByEntity(SameCtrlExtractLogEO.class, (String)"e") + " from " + "GC_SAMETRLEXTRACT_LOG" + " e  where e.latestFlag = ? and e.taskId = ? and e.schemeId = ? and e.periodStr = ? and e.orgType = ? and e.changedCode = ?  order by endTime asc ";
        return this.selectEntity(sql, new Object[]{1, sameCtrlExtractLog.getTaskId(), sameCtrlExtractLog.getSchemeId(), sameCtrlExtractLog.getPeriodStr(), sameCtrlExtractLog.getOrgType(), sameCtrlExtractLog.getChangedCode()});
    }
}

