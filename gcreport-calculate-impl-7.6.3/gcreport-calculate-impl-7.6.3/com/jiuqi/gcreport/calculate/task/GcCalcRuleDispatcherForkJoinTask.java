/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.gcreport.calculate.task;

import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.rule.dispatcher.GcCalcRuleDispatcher;
import com.jiuqi.gcreport.calculate.task.callback.GcCalcRuleDispatcherForkJoinTaskCallBack;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GcCalcRuleDispatcherForkJoinTask<T extends GcCalcRuleDispatcher>
extends RecursiveTask<Integer> {
    private static final long serialVersionUID = 1L;
    private transient Logger logger = LoggerFactory.getLogger(GcCalcRuleDispatcherForkJoinTask.class);
    private static final int SINGLE_THREAD_COUNT = 1;
    private GcCalcEnvContext calcEnvContext;
    private transient List<T> ruleDispatchers;
    private transient GcCalcRuleDispatcherForkJoinTaskCallBack<T> callBack;
    private int startIndex;
    private int endIndex;

    public GcCalcRuleDispatcherForkJoinTask(GcCalcEnvContext calcEnvContext, List<T> ruleDispatchers, GcCalcRuleDispatcherForkJoinTaskCallBack<T> callBack) {
        this(calcEnvContext, ruleDispatchers, 0, ruleDispatchers.size() - 1, callBack);
    }

    private GcCalcRuleDispatcherForkJoinTask(GcCalcEnvContext calcEnvContext, List<T> ruleDispatchers, int startIndex, int endIndex, GcCalcRuleDispatcherForkJoinTaskCallBack<T> callBack) {
        this.calcEnvContext = calcEnvContext;
        this.ruleDispatchers = Collections.synchronizedList(ruleDispatchers);
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.callBack = callBack;
    }

    @Override
    protected Integer compute() {
        if (this.endIndex == -1) {
            return 0;
        }
        if (this.endIndex - this.startIndex <= 1) {
            NpContextHolder.setContext((NpContext)this.calcEnvContext.getCalcContextExpandVariableCenter().getNpContext());
            return this.execute();
        }
        int middle = (this.startIndex + this.endIndex) / 2;
        GcCalcRuleDispatcherForkJoinTask<T> left = new GcCalcRuleDispatcherForkJoinTask<T>(this.calcEnvContext, this.ruleDispatchers, this.startIndex, middle, this.callBack);
        GcCalcRuleDispatcherForkJoinTask<T> right = new GcCalcRuleDispatcherForkJoinTask<T>(this.calcEnvContext, this.ruleDispatchers, middle + 1, this.endIndex, this.callBack);
        GcCalcRuleDispatcherForkJoinTask.invokeAll(left, right);
        return (Integer)right.join() + (Integer)left.join();
    }

    private Integer execute() {
        int count = 0;
        this.logger.info("\u6b63\u5728\u6267\u884c\u5408\u5e76\u89c4\u5219\u5206\u53d1\u5668\u8ba1\u7b97\u4efb\u52a1\uff0c\u5f53\u524d\u5408\u5e76\u8ba1\u7b97\u6279\u6b21\u53f7[{}]\uff0c\u5f53\u524d\u7ebf\u7a0b\u540d[{}], \u961f\u5217\u4efb\u52a1\u6570\u91cf[{}]\u3002", this.calcEnvContext.getCalcArgments().getSn(), Thread.currentThread().getName(), GcCalcRuleDispatcherForkJoinTask.getQueuedTaskCount());
        for (int index = this.startIndex; index <= this.endIndex; ++index) {
            GcCalcRuleDispatcher ruleDispatcher = (GcCalcRuleDispatcher)this.ruleDispatchers.get(index);
            this.callBack.run(ruleDispatcher);
            ++count;
        }
        return count;
    }
}

