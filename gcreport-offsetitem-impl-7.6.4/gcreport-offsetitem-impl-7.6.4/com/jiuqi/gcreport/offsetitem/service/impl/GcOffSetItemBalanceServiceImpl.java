/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.util.NumberUtils
 *  com.jiuqi.gcreport.common.util.ReflectionUtils
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.offsetitem.service.impl;

import com.jiuqi.gcreport.common.util.NumberUtils;
import com.jiuqi.gcreport.common.util.ReflectionUtils;
import com.jiuqi.gcreport.offsetitem.dao.GcOffSetVchrItemBalanceDao;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemBalanceEO;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetItemBalanceService;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GcOffSetItemBalanceServiceImpl
implements GcOffSetItemBalanceService {
    @Autowired
    private GcOffSetVchrItemBalanceDao balanceDao;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void syncUpdateBalance(boolean isPlus, List<GcOffSetVchrItemAdjustEO> itemEOs) {
        this.updateBalance(isPlus, itemEOs);
    }

    @Override
    @Async
    @Transactional(rollbackFor={Exception.class})
    public Future<Boolean> asyncUpdateBalance(boolean isPlus, List<GcOffSetVchrItemAdjustEO> itemEOs) {
        try {
            this.updateBalance(isPlus, itemEOs);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new AsyncResult<Boolean>(false);
        }
        return new AsyncResult<Boolean>(true);
    }

    @Transactional(rollbackFor={Exception.class})
    void updateBalance(boolean isPlus, List<GcOffSetVchrItemAdjustEO> itemEOs) {
        Objects.requireNonNull(itemEOs, "\u62b5\u9500\u5206\u5f55\u6570\u636e\u4e0d\u5141\u8bb8\u4e3a\u7a7a");
        if (itemEOs.isEmpty()) {
            return;
        }
        List<GcOffSetVchrItemAdjustEO> groupItemEOs = this.regroupDatas(itemEOs);
        this.batchInsertBalanceWhenNotExists(groupItemEOs);
        groupItemEOs.forEach(groupItemEO -> this.balanceDao.updateCurrentPeriodBalance(isPlus, (GcOffSetVchrItemAdjustEO)groupItemEO));
    }

    private List<GcOffSetVchrItemAdjustEO> regroupDatas(List<GcOffSetVchrItemAdjustEO> itemEOs) {
        List<GcOffSetVchrItemAdjustEO> groupEOs = itemEOs.stream().collect(Collectors.groupingBy(itemEO -> {
            String groupKey = GcOffSetVchrItemBalanceEO.BALANCE_DIMENSION_FIELDS.stream().map(dimensionField -> String.valueOf(ReflectionUtils.getFieldValue((Object)itemEO, (String)dimensionField))).reduce("key", (s1, s2) -> s1 + "_" + s2);
            return groupKey;
        })).values().stream().map(items -> this.mergeItem((List<GcOffSetVchrItemAdjustEO>)items)).collect(Collectors.toList()).stream().sorted(Comparator.comparing(GcOffSetVchrItemAdjustEO::getDefaultPeriod)).collect(Collectors.toList());
        return groupEOs;
    }

    private GcOffSetVchrItemAdjustEO mergeItem(List<GcOffSetVchrItemAdjustEO> itemEOs) {
        GcOffSetVchrItemAdjustEO groupEO = new GcOffSetVchrItemAdjustEO();
        GcOffSetVchrItemAdjustEO firstItem = itemEOs.get(0);
        GcOffSetVchrItemBalanceEO.BALANCE_DIMENSION_FIELDS.forEach(fieldName -> ReflectionUtils.setFieldValue((Object)groupEO, (String)fieldName, (Object)ReflectionUtils.getFieldValue((Object)firstItem, (String)fieldName)));
        String offSetCurr = firstItem.getOffSetCurr();
        groupEO.setOffSetCurr(offSetCurr);
        itemEOs.forEach(itemEO -> {
            Double groupOffSetDebit = groupEO.getOffSetDebit();
            Double itemOffSetDebit = itemEO.getOffSetDebit();
            Double groupOffSetCredit = groupEO.getOffSetCredit();
            Double itemOffSetCredit = itemEO.getOffSetCredit();
            ReflectionUtils.setFieldValue((Object)groupEO, (String)"offSetDebit", (Object)NumberUtils.sum((Double)groupOffSetDebit, (Double)itemOffSetDebit));
            ReflectionUtils.setFieldValue((Object)groupEO, (String)"offSetCredit", (Object)NumberUtils.sum((Double)groupOffSetCredit, (Double)itemOffSetCredit));
        });
        return groupEO;
    }

    @Transactional(propagation=Propagation.REQUIRES_NEW)
    void batchInsertBalanceWhenNotExists(List<GcOffSetVchrItemAdjustEO> groupItemEOs) {
        this.balanceDao.batchInsertBalanceWhenNotExists(groupItemEOs);
    }
}

