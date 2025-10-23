/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.util.TimeDimUtils
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.summary.utils;

import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.util.TimeDimUtils;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.summary.api.service.IRuntimeSummarySolutionService;
import com.jiuqi.nr.summary.common.bean.FSumParam;
import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.exception.SummaryErrorEnum;
import com.jiuqi.nr.summary.executor.sum.SumParam;
import com.jiuqi.nr.summary.model.report.SummaryReportModel;
import com.jiuqi.nr.summary.model.soulution.DimensionData;
import com.jiuqi.nr.summary.model.soulution.SummarySolutionModel;
import com.jiuqi.nr.summary.model.soulution.TargetDimensionRange;
import com.jiuqi.nr.summary.service.SummaryParamService;
import com.jiuqi.nr.summary.vo.SumUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class SumParamUtil {
    @Autowired
    private IRuntimeSummarySolutionService runtimeSolutionService;
    @Autowired
    private SummaryParamService summaryParamService;

    public SumParam buildSumParam(FSumParam fsumParam) throws SummaryCommonException {
        SumParam sumParam = new SumParam();
        this.buildSolutionModel(fsumParam.getSolutionKey(), sumParam);
        this.buildSumUnit(fsumParam.getUnit(), sumParam);
        this.buildCurrentPeriod(fsumParam.getPeriods(), sumParam);
        this.buildDimDataRange(fsumParam.getScenes(), sumParam);
        this.buildSummaryReportModels(fsumParam.getReportKeys(), sumParam);
        this.buildSummaryDataScheme(fsumParam.getSolutionKey(), sumParam);
        this.buildSourceDataScheme(fsumParam.getSolutionKey(), sumParam);
        return sumParam;
    }

    private void buildSolutionModel(String solutionKey, SumParam sumParam) throws SummaryCommonException {
        SummarySolutionModel solutionModel = this.runtimeSolutionService.getSummarySolutionModel(solutionKey);
        if (solutionModel == null) {
            throw new SummaryCommonException(SummaryErrorEnum.SUMPARAM_SOLU_NULL);
        }
        sumParam.setSummarySolutionModel(solutionModel);
    }

    private void buildCurrentPeriod(List<String> periods, SumParam sumParam) throws SummaryCommonException {
        SummarySolutionModel solutionModel = sumParam.getSummarySolutionModel();
        String mainTask = solutionModel.getMainTask();
        TaskDefine taskDefine = null;
        try {
            taskDefine = this.summaryParamService.getTaskDefine(mainTask);
        }
        catch (SummaryCommonException e) {
            throw new SummaryCommonException(SummaryErrorEnum.SUMPARAM_GETTASK_FAIL);
        }
        if (taskDefine == null) {
            throw new SummaryCommonException(SummaryErrorEnum.SUMPARAM_GETTASK_FAIL);
        }
        if (CollectionUtils.isEmpty(periods)) {
            throw new SummaryCommonException(SummaryErrorEnum.SUMPARAM_PERIOD_NULL);
        }
        String periodStr = TimeDimUtils.getDataTimeByTimeDim((String)periods.get(0), (String)String.valueOf((char)Character.toUpperCase(taskDefine.getPeriodType().code())));
        sumParam.setCurrPeriod(new PeriodWrapper(periodStr));
    }

    private void buildSumUnit(SumUnit sumUnit, SumParam sumParam) {
        if (sumUnit != null) {
            SummarySolutionModel solutionModel = sumParam.getSummarySolutionModel();
            solutionModel.setTargetDimensionRange(TargetDimensionRange.LIST);
            solutionModel.setTargetDimensionValues(sumUnit.getCodes());
        }
    }

    private void buildDimDataRange(Map<String, String[]> scenes, SumParam sumParam) {
        if (!CollectionUtils.isEmpty(scenes)) {
            ArrayList<DimensionData> dimDatas = new ArrayList<DimensionData>();
            for (Map.Entry<String, String[]> entry : scenes.entrySet()) {
                DimensionData dimData = new DimensionData();
                dimData.setName(entry.getKey());
                String valueStr = String.join((CharSequence)";", entry.getValue());
                dimData.setValues(valueStr);
                dimDatas.add(dimData);
            }
            sumParam.setDimDataRange(dimDatas);
        }
    }

    private void buildSummaryReportModels(List<String> reportKeys, SumParam sumParam) {
        if (!CollectionUtils.isEmpty(reportKeys)) {
            ArrayList<SummaryReportModel> reportModels = new ArrayList<SummaryReportModel>();
            reportKeys.forEach(key -> {
                SummaryReportModel reportModel = this.runtimeSolutionService.getSummaryReportModel((String)key);
                reportModels.add(reportModel);
            });
            sumParam.setSummaryReportModels(reportModels);
        }
    }

    private void buildSummaryDataScheme(String solutionKey, SumParam sumParam) throws SummaryCommonException {
        DataScheme dataScheme = this.summaryParamService.getSummaryDataScheme(solutionKey);
        if (dataScheme == null) {
            throw new SummaryCommonException(SummaryErrorEnum.SUMPARAM_SCHEME_NULL);
        }
        sumParam.setSummaryDataScheme(dataScheme);
    }

    private void buildSourceDataScheme(String solutionKey, SumParam sumParam) throws SummaryCommonException {
        List<DataScheme> dataSchemes = null;
        try {
            dataSchemes = this.summaryParamService.getDataSchemes(solutionKey);
        }
        catch (SummaryCommonException e) {
            throw new SummaryCommonException(SummaryErrorEnum.SUMPARAM_RELASCHEME_NULL);
        }
        sumParam.setSourceDataScheme(dataSchemes);
    }
}

