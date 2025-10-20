/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.dto.GcCalcRuleExecuteStateDTO
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  org.apache.commons.lang3.time.StopWatch
 */
package com.jiuqi.gcreport.calculate.task;

import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.dto.GcCalcRuleExecuteStateDTO;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.task.callback.GcCalcRuleProcessorForkJoinTaskCallBack;
import com.jiuqi.gcreport.calculate.util.GcCalcRuleUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GcCalcRuleProcessorForkJoinTask<T extends AbstractUnionRule>
extends RecursiveTask<String> {
    private static final long serialVersionUID = 1L;
    private transient Logger logger = LoggerFactory.getLogger(GcCalcRuleProcessorForkJoinTask.class);
    private static final int SINGLE_THREAD_COUNT = 1;
    private GcCalcEnvContext calcEnvContext;
    private transient List<T> unionRules;
    private transient GcCalcRuleProcessorForkJoinTaskCallBack<T> callBack;
    private int startIndex;
    private int endIndex;

    public GcCalcRuleProcessorForkJoinTask(GcCalcEnvContext calcEnvContext, List<T> unionRules, GcCalcRuleProcessorForkJoinTaskCallBack<T> callBack) {
        this(calcEnvContext, unionRules, 0, unionRules.size() - 1, callBack);
    }

    private GcCalcRuleProcessorForkJoinTask(GcCalcEnvContext calcEnvContext, List<T> unionRules, int startIndex, int endIndex, GcCalcRuleProcessorForkJoinTaskCallBack<T> callBack) {
        this.calcEnvContext = calcEnvContext;
        this.unionRules = Collections.synchronizedList(unionRules);
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.callBack = callBack;
    }

    @Override
    protected String compute() {
        if (this.endIndex == -1) {
            return "";
        }
        if (this.endIndex - this.startIndex <= 1) {
            NpContextHolder.setContext((NpContext)this.calcEnvContext.getCalcContextExpandVariableCenter().getNpContext());
            String ruleCodes = this.execute();
            return ruleCodes;
        }
        int middle = (this.startIndex + this.endIndex) / 2;
        GcCalcRuleProcessorForkJoinTask<T> left = new GcCalcRuleProcessorForkJoinTask<T>(this.calcEnvContext, this.unionRules, this.startIndex, middle, this.callBack);
        GcCalcRuleProcessorForkJoinTask<T> right = new GcCalcRuleProcessorForkJoinTask<T>(this.calcEnvContext, this.unionRules, middle + 1, this.endIndex, this.callBack);
        GcCalcRuleProcessorForkJoinTask.invokeAll(left, right);
        return (String)right.join() + (String)left.join();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String execute() {
        StringBuilder ruleCodes = new StringBuilder();
        GcCalcArgmentsDTO calcArgments = this.calcEnvContext.getCalcArgments();
        StopWatch refreshStopWatch = StopWatch.createStarted();
        for (int index = this.startIndex; index <= this.endIndex; ++index) {
            StopWatch ruleStopWatch = StopWatch.createStarted();
            AbstractUnionRule unionRule = (AbstractUnionRule)this.unionRules.get(index);
            try {
                this.calcEnvContext.addResultItem(String.format(GcCalcRuleUtils.getStartLogStr(), unionRule.getLocalizedName()));
                this.callBack.run(unionRule);
                ((GcCalcRuleExecuteStateDTO)this.calcEnvContext.getRuleStateMap().get(unionRule.getId())).setSuccessFlag(Boolean.TRUE.booleanValue());
                Integer createOffSetItemCount = ((GcCalcRuleExecuteStateDTO)this.calcEnvContext.getRuleStateMap().get(unionRule.getId())).getCreateOffSetItemCountValue();
                String resultMsg = ((GcCalcRuleExecuteStateDTO)this.calcEnvContext.getRuleStateMap().get(unionRule.getId())).getResultMsg();
                String countMsg = createOffSetItemCount == null ? "" : String.format(GcI18nUtil.getMessage((String)"gc.calculate.calc.rule.createoffsetitem.count"), createOffSetItemCount);
                this.calcEnvContext.addResultItem(String.format(GcCalcRuleUtils.getEndLogStr(), unionRule.getLocalizedName()));
                this.calcEnvContext.addResultItem(resultMsg);
            }
            catch (Exception e) {
                try {
                    ((GcCalcRuleExecuteStateDTO)this.calcEnvContext.getRuleStateMap().get(unionRule.getId())).setSuccessFlag(Boolean.FALSE.booleanValue());
                    String errMsg = String.format(GcCalcRuleUtils.getExceptionLogStr(), unionRule.getLocalizedName(), e.getMessage());
                    this.calcEnvContext.addResultItem(errMsg);
                    this.logger.error(errMsg, e);
                }
                catch (Throwable throwable) {
                    ruleStopWatch.stop();
                    this.logger.info("\u7cfb\u7edf\u6267\u884c\u5408\u5e76\u8ba1\u7b97\u6279\u6b21\u53f7[{}]\uff0c\u7b2c[{}]\u4e2a\u5408\u5e76\u89c4\u5219[\u4ee3\u7801\uff1a{}\uff0c\u540d\u79f0\uff1a{}\uff0c\u7c7b\u578b\uff1a{}]\uff0c\u5f53\u524d\u7ebf\u7a0b\u540d[{}], \u961f\u5217\u4efb\u52a1\u6570\u91cf[{}], \u5f00\u59cb\u65f6\u95f4\uff1a[{}]\uff0c\u7ed3\u675f\u65f6\u95f4\uff1a[{}]\uff0c\u8017\u65f6\uff1a[{}]\u6beb\u79d2\u3002", calcArgments.getSn(), index + 1, unionRule.getRuleCode(), unionRule.getLocalizedName(), unionRule.getRuleTypeDescription(), Thread.currentThread().getName(), GcCalcRuleProcessorForkJoinTask.getQueuedTaskCount(), DateUtils.format((Date)new Date(ruleStopWatch.getStartTime()), (String)"yyyy-MM-dd HH:mm:ss"), DateUtils.format((Date)new Date(ruleStopWatch.getStopTime()), (String)"yyyy-MM-dd HH:mm:ss"), ruleStopWatch.getTime(TimeUnit.MILLISECONDS));
                    if (refreshStopWatch.getTime(TimeUnit.SECONDS) >= 2L || index == this.endIndex) {
                        this.calcEnvContext.addProgressValueAndRefresh(this.calcEnvContext.getRuleStepProgress());
                        refreshStopWatch.reset();
                        refreshStopWatch.start();
                    } else {
                        this.calcEnvContext.addProgressValue(this.calcEnvContext.getRuleStepProgress());
                    }
                    throw throwable;
                }
                ruleStopWatch.stop();
                this.logger.info("\u7cfb\u7edf\u6267\u884c\u5408\u5e76\u8ba1\u7b97\u6279\u6b21\u53f7[{}]\uff0c\u7b2c[{}]\u4e2a\u5408\u5e76\u89c4\u5219[\u4ee3\u7801\uff1a{}\uff0c\u540d\u79f0\uff1a{}\uff0c\u7c7b\u578b\uff1a{}]\uff0c\u5f53\u524d\u7ebf\u7a0b\u540d[{}], \u961f\u5217\u4efb\u52a1\u6570\u91cf[{}], \u5f00\u59cb\u65f6\u95f4\uff1a[{}]\uff0c\u7ed3\u675f\u65f6\u95f4\uff1a[{}]\uff0c\u8017\u65f6\uff1a[{}]\u6beb\u79d2\u3002", calcArgments.getSn(), index + 1, unionRule.getRuleCode(), unionRule.getLocalizedName(), unionRule.getRuleTypeDescription(), Thread.currentThread().getName(), GcCalcRuleProcessorForkJoinTask.getQueuedTaskCount(), DateUtils.format((Date)new Date(ruleStopWatch.getStartTime()), (String)"yyyy-MM-dd HH:mm:ss"), DateUtils.format((Date)new Date(ruleStopWatch.getStopTime()), (String)"yyyy-MM-dd HH:mm:ss"), ruleStopWatch.getTime(TimeUnit.MILLISECONDS));
                if (refreshStopWatch.getTime(TimeUnit.SECONDS) >= 2L || index == this.endIndex) {
                    this.calcEnvContext.addProgressValueAndRefresh(this.calcEnvContext.getRuleStepProgress());
                    refreshStopWatch.reset();
                    refreshStopWatch.start();
                } else {
                    this.calcEnvContext.addProgressValue(this.calcEnvContext.getRuleStepProgress());
                }
            }
            ruleStopWatch.stop();
            this.logger.info("\u7cfb\u7edf\u6267\u884c\u5408\u5e76\u8ba1\u7b97\u6279\u6b21\u53f7[{}]\uff0c\u7b2c[{}]\u4e2a\u5408\u5e76\u89c4\u5219[\u4ee3\u7801\uff1a{}\uff0c\u540d\u79f0\uff1a{}\uff0c\u7c7b\u578b\uff1a{}]\uff0c\u5f53\u524d\u7ebf\u7a0b\u540d[{}], \u961f\u5217\u4efb\u52a1\u6570\u91cf[{}], \u5f00\u59cb\u65f6\u95f4\uff1a[{}]\uff0c\u7ed3\u675f\u65f6\u95f4\uff1a[{}]\uff0c\u8017\u65f6\uff1a[{}]\u6beb\u79d2\u3002", calcArgments.getSn(), index + 1, unionRule.getRuleCode(), unionRule.getLocalizedName(), unionRule.getRuleTypeDescription(), Thread.currentThread().getName(), GcCalcRuleProcessorForkJoinTask.getQueuedTaskCount(), DateUtils.format((Date)new Date(ruleStopWatch.getStartTime()), (String)"yyyy-MM-dd HH:mm:ss"), DateUtils.format((Date)new Date(ruleStopWatch.getStopTime()), (String)"yyyy-MM-dd HH:mm:ss"), ruleStopWatch.getTime(TimeUnit.MILLISECONDS));
            if (refreshStopWatch.getTime(TimeUnit.SECONDS) >= 2L || index == this.endIndex) {
                this.calcEnvContext.addProgressValueAndRefresh(this.calcEnvContext.getRuleStepProgress());
                refreshStopWatch.reset();
                refreshStopWatch.start();
            } else {
                this.calcEnvContext.addProgressValue(this.calcEnvContext.getRuleStepProgress());
            }
            ruleCodes.append(unionRule.getLocalizedName()).append("\uff0c");
        }
        refreshStopWatch.stop();
        return ruleCodes.toString();
    }
}

