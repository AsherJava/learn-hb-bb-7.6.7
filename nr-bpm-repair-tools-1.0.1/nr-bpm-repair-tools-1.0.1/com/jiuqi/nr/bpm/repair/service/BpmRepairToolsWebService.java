/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataentry.bean.DataEntryInitParam
 *  com.jiuqi.nr.dataentry.bean.FuncExecResult
 *  com.jiuqi.nr.dataentry.internal.service.FuncExecuteServiceImpl
 *  com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils
 *  com.jiuqi.nr.datascheme.api.core.Ordered
 *  com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.web.vo.AdjustPeriodVO
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 */
package com.jiuqi.nr.bpm.repair.service;

import com.jiuqi.nr.bpm.repair.web.param.PeriodParam;
import com.jiuqi.nr.bpm.repair.web.param.PeriodRange;
import com.jiuqi.nr.dataentry.bean.DataEntryInitParam;
import com.jiuqi.nr.dataentry.bean.FuncExecResult;
import com.jiuqi.nr.dataentry.internal.service.FuncExecuteServiceImpl;
import com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils;
import com.jiuqi.nr.datascheme.api.core.Ordered;
import com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.web.vo.AdjustPeriodVO;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BpmRepairToolsWebService {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IAdjustPeriodService adjustPeriodService;
    @Autowired
    private FuncExecuteServiceImpl periodQueryUtil;

    public PeriodParam getPeriodParam(String taskId) throws Exception {
        TaskDefine taskDefine = this.runTimeViewController.getTask(taskId);
        if (taskDefine != null) {
            DataEntryInitParam queryParam = new DataEntryInitParam();
            queryParam.setTaskKey(taskId);
            FuncExecResult dataEntryEnv = this.periodQueryUtil.getDataEntryEnv(queryParam);
            PeriodParam periodParam = new PeriodParam();
            periodParam.setDataSchemeKey(taskDefine.getDataScheme());
            periodParam.setCurrentPeriod(dataEntryEnv.getCurrentPeriodInfo());
            periodParam.setCurrentPeriodTitle(dataEntryEnv.getCurrentPeriodTitle());
            periodParam.setRangePeriods(dataEntryEnv.getPeriodRangeList().stream().map(e -> {
                PeriodRange periodRange = new PeriodRange();
                periodRange.setBegin(e.getStartPeriod());
                periodRange.setEnd(e.getEndPeriod());
                return periodRange;
            }).collect(Collectors.toList()));
            List adjustPeriods = this.adjustPeriodService.queryAdjustPeriods(taskDefine.getDataScheme());
            Map<String, List<AdjustPeriodVO>> collect = adjustPeriods.stream().filter(AdjustUtils::isAdjustData).sorted(Comparator.comparing(Ordered::getOrder, String::compareTo)).map(AdjustPeriodVO::convertToVO).filter(Objects::nonNull).collect(Collectors.groupingBy(AdjustPeriodVO::getPeriod));
            HashMap<String, List<String>> toAdjustPeriodMap = new HashMap<String, List<String>>();
            for (Map.Entry<String, List<AdjustPeriodVO>> entry : collect.entrySet()) {
                toAdjustPeriodMap.put(entry.getKey(), entry.getValue().stream().map(AdjustPeriodVO::getCode).collect(Collectors.toList()));
            }
            periodParam.setAdjustPeriods(toAdjustPeriodMap);
            return periodParam;
        }
        throw new RuntimeException("\u4efb\u52a1\u4e0d\u5b58\u5728\uff1a" + taskId);
    }
}

