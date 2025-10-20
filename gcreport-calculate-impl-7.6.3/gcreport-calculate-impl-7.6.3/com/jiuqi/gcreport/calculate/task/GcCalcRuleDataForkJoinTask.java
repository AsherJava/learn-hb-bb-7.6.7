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
import com.jiuqi.gcreport.calculate.task.callback.GcCalcRuleDataForkJoinTaskCallBack;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GcCalcRuleDataForkJoinTask<T extends Serializable>
extends RecursiveTask<Integer> {
    private static final long serialVersionUID = 1L;
    private transient Logger logger = LoggerFactory.getLogger(GcCalcRuleDataForkJoinTask.class);
    private static final int SINGLE_THREAD_COUNT = 20;
    private GcCalcEnvContext calcEnvContext;
    private List<T> ruleDatas;
    private transient GcCalcRuleDataForkJoinTaskCallBack<T> callBack;
    private int startIndex;
    private int endIndex;

    public GcCalcRuleDataForkJoinTask(GcCalcEnvContext calcEnvContext, List<T> ruleDatas, GcCalcRuleDataForkJoinTaskCallBack<T> callBack) {
        this(calcEnvContext, ruleDatas, 0, ruleDatas.size() - 1, callBack);
    }

    private GcCalcRuleDataForkJoinTask(GcCalcEnvContext calcEnvContext, List<T> ruleDatas, int startIndex, int endIndex, GcCalcRuleDataForkJoinTaskCallBack<T> callBack) {
        this.calcEnvContext = calcEnvContext;
        this.ruleDatas = Collections.synchronizedList(ruleDatas);
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.callBack = callBack;
    }

    @Override
    protected Integer compute() {
        if (this.endIndex == -1) {
            return 0;
        }
        if (this.endIndex - this.startIndex <= 20) {
            NpContextHolder.setContext((NpContext)this.calcEnvContext.getCalcContextExpandVariableCenter().getNpContext());
            int count = this.execute();
            return count;
        }
        int middle = (this.startIndex + this.endIndex) / 2;
        GcCalcRuleDataForkJoinTask<T> left = new GcCalcRuleDataForkJoinTask<T>(this.calcEnvContext, this.ruleDatas, this.startIndex, middle, this.callBack);
        GcCalcRuleDataForkJoinTask<T> right = new GcCalcRuleDataForkJoinTask<T>(this.calcEnvContext, this.ruleDatas, middle + 1, this.endIndex, this.callBack);
        GcCalcRuleDataForkJoinTask.invokeAll(left, right);
        return (Integer)right.join() + (Integer)left.join();
    }

    private int execute() {
        int count = 0;
        this.logger.info("\u6b63\u5728\u6267\u884c\u5408\u5e76\u89c4\u5219\u6e90\u6570\u636e\u8ba1\u7b97\u4efb\u52a1\uff0c\u5f53\u524d\u5408\u5e76\u8ba1\u7b97\u6279\u6b21\u53f7[{}]\uff0c\u5f53\u524d\u7ebf\u7a0b\u540d[{}], \u961f\u5217\u4efb\u52a1\u6570\u91cf[{}]\u3002", this.calcEnvContext.getCalcArgments().getSn(), Thread.currentThread().getName(), GcCalcRuleDataForkJoinTask.getQueuedTaskCount());
        for (int index = this.startIndex; index <= this.endIndex; ++index) {
            Serializable unionRule = (Serializable)this.ruleDatas.get(index);
            this.callBack.run(unionRule);
            ++count;
        }
        return count;
    }
}

