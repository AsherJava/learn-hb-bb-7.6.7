/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 */
package com.jiuqi.gcreport.inputdata.flexible.processor.executor.impl;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.inputdata.inputdata.dao.RealTimeOffsetDetailLogDao;
import com.jiuqi.gcreport.inputdata.inputdata.dao.RealTimeOffsetLogDao;
import com.jiuqi.gcreport.inputdata.inputdata.dao.RealTimeOffsetRelLogDao;
import com.jiuqi.gcreport.inputdata.inputdata.entity.RealTimeOffsetDetailLogEO;
import com.jiuqi.gcreport.inputdata.inputdata.entity.RealTimeOffsetLogEO;
import com.jiuqi.gcreport.inputdata.inputdata.entity.RealTimeOffsetRelLogEO;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RealTimeOffsetMonitor {
    private RealTimeOffsetLogEO log;
    private Map<String, RealTimeOffsetRelLogEO> relLogs;
    private Map<String, RealTimeOffsetDetailLogEO> condGroupLogs;

    public static RealTimeOffsetMonitor newInstance(String unionRuleId, int changedNum, int relGroupNum) {
        RealTimeOffsetMonitor monitor = new RealTimeOffsetMonitor();
        monitor.log = RealTimeOffsetMonitor.createRealTimeOffsetLogE(unionRuleId, changedNum, relGroupNum);
        monitor.relLogs = new HashMap<String, RealTimeOffsetRelLogEO>();
        monitor.condGroupLogs = new HashMap<String, RealTimeOffsetDetailLogEO>();
        return monitor;
    }

    public void finish() {
        this.log.setEndTime(Instant.now().toEpochMilli());
        this.log.setElapsedTime(this.log.getEndTime() - this.log.getBeginTime());
        ((RealTimeOffsetLogDao)SpringContextUtils.getBean(RealTimeOffsetLogDao.class)).save(this.log);
        ((RealTimeOffsetRelLogDao)SpringContextUtils.getBean(RealTimeOffsetRelLogDao.class)).addBatch(new ArrayList<RealTimeOffsetRelLogEO>(this.relLogs.values()));
        ((RealTimeOffsetDetailLogDao)SpringContextUtils.getBean(RealTimeOffsetDetailLogDao.class)).addBatch(new ArrayList<RealTimeOffsetDetailLogEO>(this.condGroupLogs.values()));
    }

    public void beginRelOffset(String relGroupKey) {
        RealTimeOffsetRelLogEO relLog = new RealTimeOffsetRelLogEO();
        this.relLogs.put(relGroupKey, relLog);
        relLog.setId(UUIDUtils.newUUIDStr());
        relLog.setLogId(this.log.getId());
        relLog.setUnionRuleId(this.log.getUnionRuleId());
        relLog.setRelGroupKey(relGroupKey);
        relLog.setBeginTime(Instant.now().toEpochMilli());
    }

    public void setRelOffsetNum(String relGroupKey, int num) {
        RealTimeOffsetRelLogEO relLog = this.relLogs.get(relGroupKey);
        relLog.setRelNum(num);
    }

    public void finishRelOffset(String relGroupKey) {
        RealTimeOffsetRelLogEO relLog = this.relLogs.get(relGroupKey);
        relLog.setSkipOffset(0);
        relLog.setEndTime(Instant.now().toEpochMilli());
        relLog.setElapsedTime(relLog.getEndTime() - relLog.getBeginTime());
    }

    public void skipRelOffset(String relGroupKey, String reason) {
        RealTimeOffsetRelLogEO relLog = this.relLogs.get(relGroupKey);
        relLog.setSkipOffset(1);
        relLog.setSkipReason(reason);
        relLog.setEndTime(Instant.now().toEpochMilli());
        relLog.setElapsedTime(relLog.getEndTime() - relLog.getBeginTime());
    }

    public void beginCondGroupOffset(String relGroupKey, String condGroupKey) {
        RealTimeOffsetDetailLogEO detailLog = new RealTimeOffsetDetailLogEO();
        this.condGroupLogs.put(condGroupKey, detailLog);
        detailLog.setId(UUIDUtils.newUUIDStr());
        detailLog.setRelLogId(this.relLogs.get(relGroupKey).getId());
        detailLog.setUnionRuleId(this.log.getUnionRuleId());
        detailLog.setLogId(this.log.getId());
        detailLog.setBeginTime(Instant.now().toEpochMilli());
    }

    public void setCondGroupOffsetNum(String condGroupKey, int debitNum, int creditNum) {
        RealTimeOffsetDetailLogEO detailLog = this.condGroupLogs.get(condGroupKey);
        detailLog.setDebitNum(debitNum);
        detailLog.setCreditNum(creditNum);
    }

    public void setCondGroupSortOrder(String condGroupKey, boolean sortOrder) {
        RealTimeOffsetDetailLogEO detailLog = this.condGroupLogs.get(condGroupKey);
        detailLog.setSortOffset(sortOrder ? 1 : 0);
    }

    public void finishCondGroupOffset(String condGroupKey, int matchedNum) {
        RealTimeOffsetDetailLogEO detailLog = this.condGroupLogs.get(condGroupKey);
        detailLog.setMatchedNum(matchedNum);
        detailLog.setEndTime(Instant.now().toEpochMilli());
        detailLog.setElapsedTime(detailLog.getEndTime() - detailLog.getBeginTime());
    }

    private static RealTimeOffsetLogEO createRealTimeOffsetLogE(String unionRuleId, int changedNum, int relGroupNum) {
        RealTimeOffsetLogEO realTimeOffsetLogE = new RealTimeOffsetLogEO();
        realTimeOffsetLogE.setId(UUIDUtils.newUUIDStr());
        realTimeOffsetLogE.setUnionRuleId(unionRuleId);
        realTimeOffsetLogE.setChangedNum(changedNum);
        realTimeOffsetLogE.setRelGroupNum(relGroupNum);
        realTimeOffsetLogE.setBeginTime(Instant.now().toEpochMilli());
        return realTimeOffsetLogE;
    }
}

