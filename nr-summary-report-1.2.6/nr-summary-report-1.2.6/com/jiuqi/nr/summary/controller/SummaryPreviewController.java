/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTask
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nvwa.cellbook.model.CellBook
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.server.util.ParameterConvertor
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.summary.controller;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTask;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.summary.api.service.IRuntimeSummarySolutionService;
import com.jiuqi.nr.summary.authority.SummaryReportAuthService;
import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.exception.SummaryErrorEnum;
import com.jiuqi.nr.summary.executor.query.SummaryQueryExecutor;
import com.jiuqi.nr.summary.job.SumAsyncTaskExecutor;
import com.jiuqi.nr.summary.model.PageInfo;
import com.jiuqi.nr.summary.model.report.SummaryReport;
import com.jiuqi.nr.summary.model.report.SummaryReportModel;
import com.jiuqi.nr.summary.model.soulution.SummarySolutionModel;
import com.jiuqi.nr.summary.service.SummaryPreviewService;
import com.jiuqi.nr.summary.utils.ParameterBuilder;
import com.jiuqi.nr.summary.utils.SumParamUtil;
import com.jiuqi.nr.summary.vo.BaseInfo;
import com.jiuqi.nr.summary.vo.ExportConfig;
import com.jiuqi.nr.summary.vo.ParameterBuildInfo;
import com.jiuqi.nr.summary.vo.ParameterModelItem;
import com.jiuqi.nr.summary.vo.ParameterModelWrapper;
import com.jiuqi.nr.summary.vo.PreviewConfigVo;
import com.jiuqi.nr.summary.vo.PreviewInitParam;
import com.jiuqi.nr.summary.vo.PreviewInitReqParam;
import com.jiuqi.nr.summary.vo.PreviewParamter;
import com.jiuqi.nr.summary.vo.PreviewResult;
import com.jiuqi.nr.summary.vo.ProgressInfo;
import com.jiuqi.nr.summary.vo.SumParamVO;
import com.jiuqi.nr.summary.vo.SumSoluTargetDimRangeVO;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.server.util.ParameterConvertor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/summary_report"})
public class SummaryPreviewController {
    private static final Logger logger = LoggerFactory.getLogger(SummaryPreviewController.class);
    @Autowired
    private SummaryPreviewService summaryPreviewService;
    @Autowired
    private IRuntimeSummarySolutionService runtimeSummarySolutionService;
    @Autowired
    private ParameterBuilder parameterBuilder;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private SumParamUtil sumParamUtil;
    @Autowired
    private SumAsyncTaskExecutor sumAsyncTaskExecutor;
    @Autowired
    private SummaryReportAuthService summaryReportAuthService;

    @PostMapping(value={"/preview/init-param"})
    public PreviewInitParam getInitParam(@RequestBody PreviewInitReqParam reqParam) throws Exception {
        return this.summaryPreviewService.getInitParam(reqParam);
    }

    @PostMapping(value={"/preview/parameter"})
    public List<PreviewParamter> getParameter(@RequestBody ParameterBuildInfo buildInfo) throws SummaryCommonException {
        ParameterModelWrapper parameterModelWrapper = this.parameterBuilder.buildParameter(buildInfo);
        ArrayList<PreviewParamter> result = new ArrayList<PreviewParamter>();
        ParameterModelItem periodParam = parameterModelWrapper.getPeriodParam();
        ParameterModelItem masterParam = parameterModelWrapper.getMasterParam();
        List<ParameterModelItem> sceneParams = parameterModelWrapper.getSceneParams();
        try {
            Object paramValue;
            if (periodParam != null) {
                paramValue = ParameterConvertor.toJson(null, (ParameterModel)periodParam.getParam(), (boolean)false).toString();
                result.add(new PreviewParamter(periodParam.getName(), 1, (String)paramValue));
            }
            if (masterParam != null) {
                paramValue = ParameterConvertor.toJson(null, (ParameterModel)masterParam.getParam(), (boolean)false).toString();
                result.add(new PreviewParamter(masterParam.getName(), 2, (String)paramValue));
            }
            if (!CollectionUtils.isEmpty(sceneParams)) {
                for (ParameterModelItem modelItem : sceneParams) {
                    String paramValue2 = ParameterConvertor.toJson(null, (ParameterModel)modelItem.getParam(), (boolean)false).toString();
                    result.add(new PreviewParamter(modelItem.getName(), 3, paramValue2));
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SummaryCommonException(SummaryErrorEnum.XFFORM_LOAD_FAILED);
        }
        return result;
    }

    @PostMapping(value={"/preview/result"})
    public PreviewResult getPreviewResult(@RequestBody PreviewConfigVo previewConfigVo) throws Exception {
        SummaryReportModel summaryReportModel;
        if (CollectionUtils.isEmpty(previewConfigVo.getPageInfos()) && !ObjectUtils.isEmpty((summaryReportModel = this.runtimeSummarySolutionService.getSummaryReportModel(previewConfigVo.getReportKey())).getPageConfig()) && !CollectionUtils.isEmpty(summaryReportModel.getPageConfig().getPageInfos())) {
            previewConfigVo.setPageInfos(summaryReportModel.getPageConfig().getPageInfos());
        }
        CellBook cellBook = this.summaryPreviewService.getSummaryResult(previewConfigVo);
        PreviewResult previewResult = new PreviewResult(cellBook);
        Map<Integer, PageInfo> pageInfos = previewConfigVo.getPageInfos();
        if (CollectionUtils.isEmpty(pageInfos) || pageInfos.size() <= 1) {
            previewResult.setPageInfos(null);
        } else {
            previewResult.setPageInfos(pageInfos);
        }
        return previewResult;
    }

    @PostMapping(value={"/sum"})
    public String doSum(@RequestBody SumParamVO sumParamVO) throws SummaryCommonException {
        if (!this.summaryReportAuthService.canSumSolution(sumParamVO.getSolutionKey())) {
            throw new SummaryCommonException(SummaryErrorEnum.NO_SUM_AUTH_SOLUTION);
        }
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)sumParamVO));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)this.sumAsyncTaskExecutor);
        return this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
    }

    @GetMapping(value={"/sum/progress/{asyncTaskId}"})
    public ProgressInfo getSumProgress(@PathVariable String asyncTaskId) {
        AsyncTask asyncTask = this.asyncTaskManager.queryTask(asyncTaskId);
        return new ProgressInfo(asyncTask);
    }

    @GetMapping(value={"/preview/reports/{soluKey}"})
    public List<BaseInfo> getReports(@PathVariable String soluKey) {
        List<SummaryReport> reports = this.runtimeSummarySolutionService.getSummaryReportDefinesBySolu(soluKey);
        return reports.stream().map(r -> {
            BaseInfo info = new BaseInfo();
            info.setKey(r.getKey());
            info.setCode(r.getName());
            info.setTitle(r.getTitle());
            return info;
        }).collect(Collectors.toList());
    }

    @GetMapping(value={"/solution/targetDimRange/{soluKey}"})
    public SumSoluTargetDimRangeVO getSoluTargetDimRange(@PathVariable String soluKey) {
        SumSoluTargetDimRangeVO dimRangeVO = new SumSoluTargetDimRangeVO();
        SummarySolutionModel solutionModel = this.runtimeSummarySolutionService.getSummarySolutionModel(soluKey);
        dimRangeVO.setTargetDimensionRange(solutionModel.getTargetDimensionRange());
        dimRangeVO.setTargetDimensionValues(solutionModel.getTargetDimensionValues());
        dimRangeVO.setTargetDimensionFilter(solutionModel.getTargetDimensionFilter());
        return dimRangeVO;
    }

    @PostMapping(value={"/export"})
    public void export(HttpServletResponse response, @RequestBody ExportConfig exportConfig) throws SummaryCommonException {
        new SummaryQueryExecutor().export(response, exportConfig);
    }
}

