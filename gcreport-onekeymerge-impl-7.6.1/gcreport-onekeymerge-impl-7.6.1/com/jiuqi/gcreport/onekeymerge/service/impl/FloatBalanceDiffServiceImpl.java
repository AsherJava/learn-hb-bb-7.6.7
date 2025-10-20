/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.onekeymerge.vo.GcDiffProcessCondition
 */
package com.jiuqi.gcreport.onekeymerge.service.impl;

import com.jiuqi.gcreport.onekeymerge.dao.FloatBalanceDao;
import com.jiuqi.gcreport.onekeymerge.dao.FloatBalanceDiffDao;
import com.jiuqi.gcreport.onekeymerge.service.FloatBalanceDiffService;
import com.jiuqi.gcreport.onekeymerge.vo.GcDiffProcessCondition;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FloatBalanceDiffServiceImpl
implements FloatBalanceDiffService {
    @Autowired
    private FloatBalanceDiffDao floatBalanceDiffDao;
    @Autowired
    private FloatBalanceDao floatBalanceDao;

    @Override
    public void batchDeleteAllBalance(String unitCode, List<String> subjectCodes, String tableName, GcDiffProcessCondition condition) {
        this.floatBalanceDiffDao.batchDeleteAllBalance(unitCode, subjectCodes, tableName, condition);
    }

    @Override
    public void addBatchFloatBalanceDiffDatas(Map<String, Object> fields, String tableName, List<String> currTableAllFieldCodes) {
        this.floatBalanceDao.intertFloatBalanceDetail(tableName, currTableAllFieldCodes, fields);
    }

    @Override
    public void batchDeleteAllBalanceByOppunitTitle(String unitCode, String oppUnitCode, String oppunitTitle, String tableName, GcDiffProcessCondition condition) {
        this.floatBalanceDiffDao.batchDeleteAllBalanceByOppunitTitle(unitCode, oppUnitCode, oppunitTitle, tableName, condition);
    }
}

