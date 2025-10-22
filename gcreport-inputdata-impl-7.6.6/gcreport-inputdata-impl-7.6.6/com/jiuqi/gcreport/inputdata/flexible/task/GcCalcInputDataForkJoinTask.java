/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.gcreport.inputdata.flexible.task;

import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.inputdata.flexible.task.callback.GcCalcInputDataForkJoinTaskCallBack;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GcCalcInputDataForkJoinTask<InputDataEo>
extends RecursiveTask<Integer> {
    private static final long serialVersionUID = 1L;
    private Logger logger = LoggerFactory.getLogger(GcCalcInputDataForkJoinTask.class);
    private static final int SINGLE_THREAD_COUNT = 20;
    private GcCalcEnvContext calcEnvContext;
    private List<InputDataEo> inputDataEos;
    private GcCalcInputDataForkJoinTaskCallBack<InputDataEo> callBack;
    private int startIndex;
    private int endIndex;

    public GcCalcInputDataForkJoinTask(GcCalcEnvContext calcEnvContext, List<InputDataEo> inputDataEos, GcCalcInputDataForkJoinTaskCallBack<InputDataEo> callBack) {
        this(calcEnvContext, inputDataEos, 0, inputDataEos.size() - 1, callBack);
    }

    private GcCalcInputDataForkJoinTask(GcCalcEnvContext calcEnvContext, List<InputDataEo> inputDataEos, int startIndex, int endIndex, GcCalcInputDataForkJoinTaskCallBack<InputDataEo> callBack) {
        this.calcEnvContext = calcEnvContext;
        this.inputDataEos = Collections.synchronizedList(inputDataEos);
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
        GcCalcInputDataForkJoinTask<InputDataEo> left = new GcCalcInputDataForkJoinTask<InputDataEo>(this.calcEnvContext, this.inputDataEos, this.startIndex, middle, this.callBack);
        GcCalcInputDataForkJoinTask<InputDataEo> right = new GcCalcInputDataForkJoinTask<InputDataEo>(this.calcEnvContext, this.inputDataEos, middle + 1, this.endIndex, this.callBack);
        GcCalcInputDataForkJoinTask.invokeAll(left, right);
        return (Integer)right.join() + (Integer)left.join();
    }

    private int execute() {
        int count = 0;
        this.logger.info("\u6b63\u5728\u6267\u884c\u5408\u5e76\u89c4\u5219\u5185\u90e8\u5f55\u5165\u8868\u6570\u636e\u6e05\u6d17\u4efb\u52a1\uff0c\u5f53\u524d\u5408\u5e76\u8ba1\u7b97\u6279\u6b21\u53f7[{}]\uff0c\u5f53\u524d\u7ebf\u7a0b\u540d[{}], \u961f\u5217\u4efb\u52a1\u6570\u91cf[{}]\u3002", this.calcEnvContext.getCalcArgments().getSn(), Thread.currentThread().getName(), GcCalcInputDataForkJoinTask.getQueuedTaskCount());
        for (int index = this.startIndex; index <= this.endIndex; ++index) {
            InputDataEo unionRule = this.inputDataEos.get(index);
            this.callBack.run(unionRule);
            ++count;
        }
        return count;
    }
}

