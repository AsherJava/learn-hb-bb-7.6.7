/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.definition.impl.basic.base.sql.EntSqlTool
 *  com.jiuqi.gcreport.definition.impl.basic.base.sql.dml.EntDmlBatchSql
 *  com.jiuqi.gcreport.definition.impl.basic.base.template.EntNativeSqlTemplate
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.inputdata.function.sumhb.service;

import com.google.common.collect.Lists;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.EntSqlTool;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.dml.EntDmlBatchSql;
import com.jiuqi.gcreport.definition.impl.basic.base.template.EntNativeSqlTemplate;
import com.jiuqi.gcreport.inputdata.function.sumhb.entity.SumHbTempEO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SumHbTempServiceImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(SumHbTempServiceImpl.class);

    public void addBatchSumHbTempsByIsTempTableFlag(EntNativeSqlTemplate template, List<SumHbTempEO> sumHbTempItems, String batchId, String insertSql, boolean isTempTableFlag) {
        if (!isTempTableFlag) {
            this.insertSumHbTemps(template, sumHbTempItems, batchId, insertSql);
        } else {
            ((SumHbTempServiceImpl)SpringContextUtils.getBean(SumHbTempServiceImpl.class)).addBatchSumHbTemps(template, sumHbTempItems, batchId, insertSql);
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public void addBatchSumHbTemps(EntNativeSqlTemplate template, List<SumHbTempEO> sumHbTempItems, String batchId, String insertSql) {
        LOGGER.info("\u5f00\u59cb\u6267\u884c\u3002");
        List sumBatchHbTempItems = Lists.partition(sumHbTempItems, (int)2000);
        CountDownLatch countDownLatch = new CountDownLatch(sumBatchHbTempItems.size());
        for (List sumHbTemps : sumBatchHbTempItems) {
            if (CollectionUtils.isEmpty((Collection)sumHbTemps)) {
                return;
            }
            ((SumHbTempServiceImpl)SpringContextUtils.getBean(SumHbTempServiceImpl.class)).executeAsync(template, sumHbTemps, batchId, insertSql, countDownLatch);
        }
        try {
            countDownLatch.await();
        }
        catch (Exception e) {
            LOGGER.error("SUMHB\u4fdd\u5b58\u4e34\u65f6\u8868\u5f02\u5e38:" + e.getMessage(), e);
        }
        LOGGER.info("\u7ed3\u675f\u3002\u3002\u3002");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Async(value="sumHbBatchTaskExecutor")
    public void executeAsync(EntNativeSqlTemplate template, List<SumHbTempEO> sumHbTempItems, String batchId, String insertSql, CountDownLatch countDownLatch) {
        try {
            this.insertSumHbTemps(template, sumHbTempItems, batchId, insertSql);
        }
        finally {
            countDownLatch.countDown();
        }
    }

    private void insertSumHbTemps(EntNativeSqlTemplate template, List<SumHbTempEO> sumHbTemps, String batchId, String insertSql) {
        ArrayList paramValues = new ArrayList();
        sumHbTemps.forEach(sumHbTemp -> paramValues.add(Arrays.asList(batchId, sumHbTemp.getRegionId(), sumHbTemp.getSubjectCode(), UUIDUtils.newUUIDStr())));
        EntDmlBatchSql entDmlBatchSql = EntSqlTool.newDmlBatchInstance((String)insertSql, paramValues);
        template.executeBatch(entDmlBatchSql);
    }
}

