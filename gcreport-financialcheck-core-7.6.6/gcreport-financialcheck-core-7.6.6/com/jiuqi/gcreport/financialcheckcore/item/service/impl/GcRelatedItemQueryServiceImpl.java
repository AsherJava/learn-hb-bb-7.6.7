/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.financialcheckcore.item.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.financialcheckcore.check.dto.BalanceCondition;
import com.jiuqi.gcreport.financialcheckcore.item.dao.GcRelatedItemDao;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.item.service.GcRelatedItemQueryService;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GcRelatedItemQueryServiceImpl
implements GcRelatedItemQueryService {
    @Autowired
    private GcRelatedItemDao gcRelatedItemDao;

    @Override
    public List<GcRelatedItemEO> queryByOffsetCondition(BalanceCondition queryCondition) {
        return this.gcRelatedItemDao.queryByOffsetCondition(queryCondition);
    }

    @Override
    public List<GcRelatedItemEO> findByCheckIds(Collection<String> checkIds) {
        return this.gcRelatedItemDao.findByCheckIds(checkIds);
    }

    @Override
    public List<GcRelatedItemEO> findByCheckSchemeIds(Collection<String> checkSchemeIds) {
        return this.gcRelatedItemDao.findByCheckSchemeIds(checkSchemeIds);
    }

    @Override
    public Set<String> countByIdAndRecordTimestamp(Collection<String> ids, long recordTimestamp) {
        return this.gcRelatedItemDao.countByIdAndRecordTimestamp(ids, recordTimestamp);
    }

    @Override
    public List<GcRelatedItemEO> queryByIds(Collection<String> ids) {
        return this.gcRelatedItemDao.queryByIds(ids);
    }

    @Override
    public List<GcRelatedItemEO> queryUncheckedItemByUnitsAndYear(Set<String> units, List<String> oppUnitIds, int year) {
        return this.gcRelatedItemDao.queryUncheckedItemByUnitsAndYear(units, oppUnitIds, year);
    }

    @Override
    public List<GcRelatedItemEO> listExistRelatedItemsByBatchId(String batchId, boolean isItemWay) {
        return this.gcRelatedItemDao.listExistRelatedItemsByBatchId(batchId, isItemWay);
    }

    @Override
    public List<GcRelatedItemEO> queryUncheckedItemByUnitAndDataTime(Set<String> units, List<String> oppUnitIds, String dataTime, String checkWay, Set<String> localSchemes) {
        return this.gcRelatedItemDao.queryUncheckedItemByUnitAndDataTime(units, oppUnitIds, dataTime, checkWay, localSchemes);
    }

    @Override
    public List<GcRelatedItemEO> queryByGcNumber(String gcNumber) {
        if (StringUtils.isEmpty((String)gcNumber)) {
            return CollectionUtils.newArrayList();
        }
        return this.gcRelatedItemDao.queryByGcNumber(gcNumber);
    }

    @Override
    public List<GcRelatedItemEO> queryByGcNumberAndUnit(String gcNumber, String unitId, String oppUnitId, Integer acctYear) {
        return this.gcRelatedItemDao.queryByGcNumberAndUnit(gcNumber, unitId, oppUnitId, acctYear);
    }
}

