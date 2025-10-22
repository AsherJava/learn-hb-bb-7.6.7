/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.GcDiffRewriteWayEnum
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.GcFormulaDiffRewriteWayEnum
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.onekeymerge.vo.ReturnObject
 *  com.jiuqi.gcreport.temp.dto.TaskLog
 *  com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController
 *  javax.annotation.Resource
 */
package com.jiuqi.gcreport.onekeymerge.task;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.GcDiffRewriteWayEnum;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.GcFormulaDiffRewriteWayEnum;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.onekeymerge.service.IFloatBalanceService;
import com.jiuqi.gcreport.onekeymerge.task.GcDiffUnitReWriteTask;
import com.jiuqi.gcreport.onekeymerge.task.rewrite.GcDiffUnitReWriteSubTaskGather;
import com.jiuqi.gcreport.onekeymerge.task.rewrite.IGcDiffUnitReWriteSubTask;
import com.jiuqi.gcreport.onekeymerge.util.EfdcUtils;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.onekeymerge.vo.ReturnObject;
import com.jiuqi.gcreport.temp.dto.TaskLog;
import com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcDiffUnitReWriteTaskImpl
implements GcDiffUnitReWriteTask {
    @Autowired
    RuntimeViewController runtimeViewController;
    @Autowired
    private IFloatBalanceService floatBalanceService;
    @Resource
    private ConsolidatedTaskService consolidatedTaskService;
    @Resource
    private GcDiffUnitReWriteSubTaskGather gcDiffUnitReWriteSubTaskGather;
    private static final Logger logger = LoggerFactory.getLogger(GcDiffUnitReWriteTaskImpl.class);

    @Override
    public ReturnObject doTask(GcActionParamsVO paramsVO, List<String> hbUnitIds, List<String> diffUnitIds, TaskLog taskLog) {
        try {
            return this.dataRewriteNoBalance(paramsVO, hbUnitIds, diffUnitIds, taskLog);
        }
        catch (Exception e) {
            logger.error("\u5dee\u989d\u56de\u5199\u51fa\u9519", e);
            throw e;
        }
    }

    public ReturnObject dataRewriteNoBalance(GcActionParamsVO paramsVO, List<String> hbUnitIds, List<String> diffUnitIds, TaskLog taskLog) {
        ConsolidatedTaskVO taskOption = this.consolidatedTaskService.getTaskBySchemeId(paramsVO.getSchemeId(), paramsVO.getPeriodStr());
        String diffRewriteWay = taskOption.getDiffRewriteWay();
        Assert.isNotEmpty((String)diffRewriteWay, (String)"\u672a\u5728\u5408\u5e76\u4f53\u7cfb\u7ba1\u7406\u7684\u62a5\u8868\u9875\u7b7e\u914d\u7f6e\u5dee\u989d\u56de\u5199\u65b9\u5f0f", (Object[])new Object[0]);
        IGcDiffUnitReWriteSubTask gcDiffUnitReWriteSubTask = this.getGcDiffUnitReWriteSubTask(paramsVO, diffUnitIds, diffRewriteWay);
        String format = GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.dataRewriteNoBalance", (Object[])new Object[]{GcI18nUtil.getMessage((String)("gc.calculate.onekeymerge.calcDone.message." + gcDiffUnitReWriteSubTask.getName()))});
        taskLog.writeInfoLog(format, Float.valueOf(36.0f)).syncTaskLog();
        Date startDate = DateUtils.now();
        ReturnObject returnObject = gcDiffUnitReWriteSubTask.doTask(paramsVO, hbUnitIds, diffUnitIds, taskLog);
        taskLog.writeInfoLog(GcI18nUtil.getMessage((String)("gc.calculate.onekeymerge.calcDone.message." + gcDiffUnitReWriteSubTask.getName())) + GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.dataRewriteNoBalanceEnd") + "," + GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.use") + ":" + DateUtils.diffOf((Date)startDate, (Date)DateUtils.now(), (int)13) + "s", Float.valueOf(42.0f));
        return returnObject;
    }

    private IGcDiffUnitReWriteSubTask getGcDiffUnitReWriteSubTask(GcActionParamsVO paramsVO, List<String> diffUnitIds, String diffRewriteWay) {
        if (GcDiffRewriteWayEnum.SUBJECT_MAPPING.getCode().equals(diffRewriteWay)) {
            return this.gcDiffUnitReWriteSubTaskGather.getByName(diffRewriteWay);
        }
        String fetchScheme = EfdcUtils.getFetchScheme(paramsVO, diffUnitIds.get(0));
        if (StringUtils.isEmpty((String)fetchScheme)) {
            throw new BusinessRuntimeException("\u672a\u83b7\u53d6\u5230\u5dee\u989d\u5355\u4f4d\u7684\u53d6\u6570\u65b9\u6848");
        }
        if (fetchScheme.length() > 16) {
            return this.gcDiffUnitReWriteSubTaskGather.getByName(GcFormulaDiffRewriteWayEnum.EFDC.getCode());
        }
        return this.gcDiffUnitReWriteSubTaskGather.getByName(GcFormulaDiffRewriteWayEnum.BDE.getCode());
    }
}

