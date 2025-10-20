/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.inputdata.inputdata.service.impl;

import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.inputdata.inputdata.dao.OffsetQueueDao;
import com.jiuqi.gcreport.inputdata.inputdata.entity.OffsetQueueEO;
import com.jiuqi.gcreport.inputdata.inputdata.service.OffsetQueueService;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import java.util.Calendar;
import java.util.Collections;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class OffsetQueueServiceImpl
implements OffsetQueueService {
    private final OffsetQueueDao offsetQueueDao;
    private final ConsolidatedOptionService optionService;
    private static final int MINOVERTIMEMINUTE = 10;

    public OffsetQueueServiceImpl(OffsetQueueDao offsetQueueDao, ConsolidatedOptionService optionService) {
        this.offsetQueueDao = offsetQueueDao;
        this.optionService = optionService;
    }

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public boolean joinQueue(OffsetQueueEO offsetQueue) {
        String mergingUnit = this.getMergingUnit(offsetQueue);
        if (offsetQueue.getCreateTime() == null) {
            offsetQueue.setCreateTime(System.currentTimeMillis());
        }
        if (offsetQueue.getId() == null) {
            offsetQueue.setId(UUIDUtils.newUUIDStr());
        }
        Long overTime = this.getOverTime(offsetQueue);
        Long createTimeBak = offsetQueue.getCreateTime();
        offsetQueue.setCreateTime(overTime);
        this.offsetQueueDao.deleteOverTime(offsetQueue, mergingUnit);
        try {
            offsetQueue.setCreateTime(createTimeBak);
            this.offsetQueueDao.save(offsetQueue);
        }
        catch (Exception e) {
            return false;
        }
        if (this.offsetQueueDao.existsBeforeCreateTime(offsetQueue, mergingUnit)) {
            this.offsetQueueDao.deleteById(offsetQueue.getId());
            return false;
        }
        return true;
    }

    private String getMergingUnit(OffsetQueueEO offsetQueue) {
        int relOrgNums;
        String[] idStrs = offsetQueue.getUnitComb().split(",");
        if (idStrs.length == (relOrgNums = 2)) {
            GcOrgCacheVO org2;
            GcOrgCacheVO org1;
            String dateStr = offsetQueue.getNrPeriod();
            YearPeriodObject yp = new YearPeriodObject(null, dateStr);
            GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)"MD_ORG_CORPORATE", (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
            GcOrgCacheVO commonUnit = orgTool.getCommonUnit(org1 = orgTool.getOrgByCode(idStrs[0]), org2 = orgTool.getOrgByCode(idStrs[1]));
            if (commonUnit == null) {
                return UUIDUtils.emptyUUIDStr();
            }
            return commonUnit.getId();
        }
        return null;
    }

    private Long getOverTime(OffsetQueueEO offsetQueue) {
        Integer overTimeMinuteObj = this.optionService.getOptionDataBySchemeId(offsetQueue.getSchemeId(), offsetQueue.getNrPeriod()).getOverTimeMinute();
        int overTimeMinute = overTimeMinuteObj == null ? 10 : Integer.valueOf(String.valueOf(overTimeMinuteObj));
        overTimeMinute = overTimeMinute < 10 ? 10 : overTimeMinute;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(offsetQueue.getCreateTime() - (long)(overTimeMinute * 60000));
        return calendar.getTimeInMillis();
    }

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void removeQueue(String id) {
        if (id == null) {
            return;
        }
        this.offsetQueueDao.deleteById(id);
    }

    @Override
    public Set<String> queryUnitByCondition(String nrPeriod, String taskId, String currencyCode, long createTime, String unionRuleId) {
        if (taskId == null || StringUtils.isEmpty(currencyCode)) {
            return Collections.emptySet();
        }
        return this.offsetQueueDao.queryUnitByCondition(nrPeriod, taskId, currencyCode, createTime, unionRuleId);
    }
}

