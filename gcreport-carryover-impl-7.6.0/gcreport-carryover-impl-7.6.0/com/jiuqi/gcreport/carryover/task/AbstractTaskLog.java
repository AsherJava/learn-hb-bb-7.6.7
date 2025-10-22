/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.expimp.progress.common.ProgressData
 *  com.jiuqi.common.expimp.progress.service.ProgressService
 *  com.jiuqi.gcreport.carryover.vo.QueryParamsVO
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.temp.dto.OnekeyProgressDataImpl
 *  com.jiuqi.gcreport.temp.dto.TaskLog
 */
package com.jiuqi.gcreport.carryover.task;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.expimp.progress.common.ProgressData;
import com.jiuqi.common.expimp.progress.service.ProgressService;
import com.jiuqi.gcreport.carryover.service.GcCarryOverProcessService;
import com.jiuqi.gcreport.carryover.vo.QueryParamsVO;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.temp.dto.OnekeyProgressDataImpl;
import com.jiuqi.gcreport.temp.dto.TaskLog;
import java.math.BigDecimal;
import java.util.Date;

public abstract class AbstractTaskLog {
    private static ThreadLocal<TaskLog> taskLogLocal = new ThreadLocal();
    private static ThreadLocal<Integer> subTaskFullWeightLocal = new ThreadLocal();
    private static ThreadLocal<Float> subTaskFullPercentLocal = new ThreadLocal();

    protected String formatSecond(long second) {
        Object[] array;
        String format;
        if (second == 0L) {
            return "0" + GcI18nUtil.getMessage((String)"gc.carryover.time.second");
        }
        String formatedTime = "";
        Double s = Long.valueOf(second).doubleValue();
        Integer hours = (int)(s / 3600.0);
        Integer minutes = (int)(s / 60.0 - (double)(hours * 60));
        Integer seconds = (int)(s - (double)(minutes * 60) - (double)(hours * 60 * 60));
        if (hours > 0) {
            format = "%1$,d\u65f6%2$,d\u5206%3$,d\u79d2";
            array = new Object[]{hours, minutes, seconds};
        } else if (minutes > 0) {
            format = "%1$,d\u5206%2$,d\u79d2";
            array = new Object[]{minutes, seconds};
        } else {
            format = "%1$,d\u79d2";
            array = new Object[]{seconds};
        }
        formatedTime = String.format(format, array);
        formatedTime = formatedTime.replace("\u65f6", GcI18nUtil.getMessage((String)"gc.carryover.time.hour"));
        formatedTime = formatedTime.replace("\u5206", GcI18nUtil.getMessage((String)"gc.carryover.time.minute"));
        formatedTime = formatedTime.replace("\u79d2", GcI18nUtil.getMessage((String)"gc.carryover.time.second"));
        return formatedTime;
    }

    protected void initTaskLog(QueryParamsVO queryParamsVO) {
        String orgCode = ((GcOrgCacheVO)queryParamsVO.getOrgList().get(0)).getCode();
        OnekeyProgressDataImpl onekeyProgressData = new OnekeyProgressDataImpl(this.generateSN("carryOver", queryParamsVO.getTaskLogId(), orgCode));
        ProgressService progressService = (ProgressService)SpringContextUtils.getBean(ProgressService.class);
        progressService.createProgressData((ProgressData)onekeyProgressData);
        TaskLog taskLog = new TaskLog(onekeyProgressData);
        ((GcCarryOverProcessService)SpringContextUtils.getBean(GcCarryOverProcessService.class)).setTaskProcess(queryParamsVO.getTaskLogId() + "_" + orgCode, taskLog);
        taskLog.setCreateDate(new Date());
        taskLogLocal.set(taskLog);
    }

    private String generateSN(String code, String taskLogId, String orgCode) {
        return code + "_" + taskLogId + "_" + orgCode;
    }

    protected void logInfo(String msg, Float percent) {
        if (percent.floatValue() >= 1.0f) {
            percent = Float.valueOf(0.99f);
        }
        taskLogLocal.get().writeInfoLog(msg, percent);
    }

    protected void logWarn(String msg, Float percent) {
        if (percent.floatValue() >= 1.0f) {
            percent = Float.valueOf(0.99f);
        }
        taskLogLocal.get().writeWarnLog(msg, percent);
    }

    protected void logError(String msg, Float percent) {
        if (percent.floatValue() >= 1.0f) {
            percent = Float.valueOf(0.99f);
        }
        taskLogLocal.get().writeErrorLog(msg, percent);
    }

    protected Float plusWeight(int weight) {
        Float subTaskFullPercent = subTaskFullPercentLocal.get();
        if (null == subTaskFullPercent) {
            subTaskFullPercent = Float.valueOf(1.0f);
        }
        Integer subTaskFullWeight = subTaskFullWeightLocal.get();
        Assert.isNotNull((Object)subTaskFullWeight);
        float percent = (float)weight * subTaskFullPercent.floatValue() / (float)subTaskFullWeight.intValue();
        TaskLog taskLog = taskLogLocal.get();
        Float processPercent = Float.valueOf(taskLog.getProcessPercent());
        processPercent = null == processPercent ? Float.valueOf(percent) : Float.valueOf(processPercent.floatValue() + percent);
        BigDecimal percentTemp = new BigDecimal(processPercent.floatValue());
        processPercent = Float.valueOf(percentTemp.setScale(2, 4).floatValue());
        return processPercent;
    }

    protected void setSubTaskFullPercent(Float percent) {
        subTaskFullPercentLocal.set(percent);
    }

    public void setSubTaskFullWeight(Integer subTaskFullWeight) {
        subTaskFullWeightLocal.set(subTaskFullWeight);
    }

    protected void finish() {
        taskLogLocal.get().setFinish(true);
        taskLogLocal.remove();
        subTaskFullWeightLocal.remove();
        subTaskFullPercentLocal.remove();
    }
}

