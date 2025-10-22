/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 */
package com.jiuqi.gcreport.calculate.util;

import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GcCalcRuleUtils {
    private static Logger LOGGER = LoggerFactory.getLogger(GcCalcRuleUtils.class);
    public static final ForkJoinPool CALC_INPUTDATA_FORK_JOIN_POOL = new ForkJoinPool(Runtime.getRuntime().availableProcessors(), pool -> {
        ForkJoinWorkerThread worker = ForkJoinPool.defaultForkJoinWorkerThreadFactory.newThread(pool);
        worker.setName("ForkJoinPool-gc-calc-inputdata-worker-thread" + worker.getPoolIndex());
        return worker;
    }, null, false);
    public static final ForkJoinPool CALC_RULE_DISPATCHER_FORK_JOIN_POOL = new ForkJoinPool(Runtime.getRuntime().availableProcessors(), pool -> {
        ForkJoinWorkerThread worker = ForkJoinPool.defaultForkJoinWorkerThreadFactory.newThread(pool);
        worker.setName("ForkJoinPool-gc-calc-rule-dispatcher-worker-thread" + worker.getPoolIndex());
        return worker;
    }, null, false);
    public static final ForkJoinPool CALC_RULE_PROCESSOR_FORK_JOIN_POOL = new ForkJoinPool(Runtime.getRuntime().availableProcessors(), pool -> {
        ForkJoinWorkerThread worker = ForkJoinPool.defaultForkJoinWorkerThreadFactory.newThread(pool);
        worker.setName("ForkJoinPool-gc-calc-rule-processor-worker-thread" + worker.getPoolIndex());
        return worker;
    }, null, false);

    public static String getStartLogStr() {
        return GcI18nUtil.getMessage((String)"gc.calculate.calc.rule.log.start");
    }

    public static String getSuccessLogStr() {
        return GcI18nUtil.getMessage((String)"gc.calculate.calc.rule.log.success");
    }

    public static String getEndLogStr() {
        return GcI18nUtil.getMessage((String)"gc.calculate.calc.rule.log.end");
    }

    public static String getExceptionLogStr() {
        return GcI18nUtil.getMessage((String)"gc.calculate.calc.rule.log.exception");
    }

    public static final ForkJoinPool getInputDataForkJoinPool() {
        LOGGER.debug(CALC_INPUTDATA_FORK_JOIN_POOL.toString());
        return CALC_INPUTDATA_FORK_JOIN_POOL;
    }

    public static final ForkJoinPool getRuleDispatcherForkJoinPool() {
        LOGGER.debug(CALC_RULE_DISPATCHER_FORK_JOIN_POOL.toString());
        return CALC_RULE_DISPATCHER_FORK_JOIN_POOL;
    }

    public static final ForkJoinPool getRuleProcessorForkJoinPool() {
        LOGGER.debug(CALC_RULE_PROCESSOR_FORK_JOIN_POOL.toString());
        return CALC_RULE_PROCESSOR_FORK_JOIN_POOL;
    }
}

