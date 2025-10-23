/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.common.util.TimeDimUtils
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.adapter.impl.org.util.OrgAdapterUtil
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.common.utils.BqlTimeDimUtils
 *  com.jiuqi.nvwa.cellbook.converter.CellBookGriddataConverter
 *  com.jiuqi.nvwa.cellbook.model.CellBook
 */
package com.jiuqi.nr.summary.service.impl;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.common.util.TimeDimUtils;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.adapter.impl.org.util.OrgAdapterUtil;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.common.utils.BqlTimeDimUtils;
import com.jiuqi.nr.summary.api.service.IDesignSummarySolutionService;
import com.jiuqi.nr.summary.api.service.IRuntimeSummarySolutionService;
import com.jiuqi.nr.summary.consts.SummaryErrorEnum;
import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.executor.query.QueryPageConfig;
import com.jiuqi.nr.summary.executor.query.SummaryQueryExecutor;
import com.jiuqi.nr.summary.internal.service.ISummaryConfigService;
import com.jiuqi.nr.summary.model.PageInfo;
import com.jiuqi.nr.summary.model.report.SummaryReport;
import com.jiuqi.nr.summary.model.report.SummaryReportModel;
import com.jiuqi.nr.summary.model.soulution.SummarySolution;
import com.jiuqi.nr.summary.model.soulution.SummarySolutionModel;
import com.jiuqi.nr.summary.service.SummaryParamService;
import com.jiuqi.nr.summary.service.SummaryPreviewService;
import com.jiuqi.nr.summary.utils.SummaryLoggerHelper;
import com.jiuqi.nr.summary.vo.CondParam;
import com.jiuqi.nr.summary.vo.NodeType;
import com.jiuqi.nr.summary.vo.PreviewConfigVo;
import com.jiuqi.nr.summary.vo.PreviewInitParam;
import com.jiuqi.nr.summary.vo.PreviewInitReqParam;
import com.jiuqi.nr.summary.vo.ResultParam;
import com.jiuqi.nr.summary.vo.SiderParam;
import com.jiuqi.nr.summary.vo.SummaryConfigItem;
import com.jiuqi.nr.summary.vo.SummaryConfigVO;
import com.jiuqi.nvwa.cellbook.converter.CellBookGriddataConverter;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class SummaryPreviewServiceImpl
implements SummaryPreviewService {
    @Autowired
    private SummaryParamService summaryParamService;
    @Autowired
    private IDesignSummarySolutionService designSummarySolutionService;
    @Autowired
    private IRuntimeSummarySolutionService runtimeSummarySolutionService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private ISummaryConfigService summaryConfigService;

    @Override
    public PreviewInitParam getInitParam(PreviewInitReqParam reqParam) throws Exception {
        PreviewInitParam initParam = new PreviewInitParam();
        List<String> sumSoluGroups = reqParam.getSumSoluGroups();
        List<String> sumSolus = reqParam.getSumSolus();
        List<String> sumReports = reqParam.getSumReports();
        if (!CollectionUtils.isEmpty(sumSoluGroups)) {
            initParam = this.buildInitParamWithSingleGroup(sumSoluGroups.get(0), reqParam);
        } else if (!CollectionUtils.isEmpty(sumSolus)) {
            initParam = this.buildInitParamWithSingleSolu(sumSolus.get(0), reqParam, true);
        } else if (!CollectionUtils.isEmpty(sumReports)) {
            initParam = this.buildInitParamWithSingleReport(sumReports.get(0), reqParam);
        } else {
            SummaryConfigVO config = this.summaryConfigService.getConfig(reqParam.getMenuId());
            if (config == null) {
                return initParam;
            }
            List<SummaryConfigItem> configItems = config.getItems();
            if (!CollectionUtils.isEmpty(configItems)) {
                String key = configItems.get(0).getKey();
                int type = configItems.get(0).getType();
                if (type == NodeType.SUMMARY_SOLUTION_GROUP.getCode()) {
                    initParam = this.buildInitParamWithSingleGroup(key, reqParam);
                } else if (type == NodeType.SUMMARY_SOLUTION.getCode()) {
                    initParam = this.buildInitParamWithSingleSolu(key, reqParam, true);
                } else if (type == NodeType.SUMMARY_REPORT.getCode()) {
                    initParam = this.buildInitParamWithSingleReport(key, reqParam);
                }
            }
        }
        return initParam;
    }

    private PreviewInitParam buildInitParamWithSingleGroup(String soluGroupKey, PreviewInitReqParam reqParam) {
        PreviewInitParam initParam = null;
        List<SummarySolution> solutions = this.runtimeSummarySolutionService.getSummarySolutionDefinesByGroup(soluGroupKey);
        if (solutions.size() == 1) {
            initParam = this.buildInitParamWithSingleSolu(solutions.get(0).getKey(), reqParam, true);
        } else {
            ArrayList<String> soluGroups = new ArrayList<String>();
            soluGroups.add(soluGroupKey);
            initParam = new PreviewInitParam();
            SiderParam siderParam = new SiderParam();
            siderParam.setTreeId("flexible_sum_solution_tree");
            siderParam.setSumSoluGroups(soluGroups);
            siderParam.setSumSolus(reqParam.getSumSolus());
            siderParam.setSumReports(reqParam.getSumReports());
            initParam.setSiderParam(siderParam);
        }
        return initParam;
    }

    private PreviewInitParam buildInitParamWithSingleSolu(String soluKey, PreviewInitReqParam reqParam, boolean needReport) {
        PreviewInitParam initParam = new PreviewInitParam();
        CondParam condParam = new CondParam();
        SiderParam siderParam = new SiderParam();
        ResultParam resultParam = new ResultParam();
        condParam.setSoluKey(soluKey);
        condParam.setWithMaster(false);
        SummarySolutionModel solutionModel = this.runtimeSummarySolutionService.getSummarySolutionModel(soluKey);
        siderParam.setTreeId("preview_unit_tree");
        siderParam.setTaskId(solutionModel.getMainTask());
        siderParam.setSumSoluGroups(reqParam.getSumSoluGroups());
        siderParam.setSumSolus(reqParam.getSumSolus());
        siderParam.setSumReports(reqParam.getSumReports());
        siderParam.setTargetDimensionKey(solutionModel.getTargetDimension());
        siderParam.setTargetDimensionRange(solutionModel.getTargetDimensionRange());
        siderParam.setTargetDimensionValues(solutionModel.getTargetDimensionValues());
        siderParam.setTargetDimensionFilter(solutionModel.getTargetDimensionFilter());
        if (needReport) {
            List<SummaryReport> reportDefines = this.runtimeSummarySolutionService.getSummaryReportDefinesBySolu(soluKey);
            resultParam.setReports(reportDefines);
        }
        initParam.setCondParam(condParam);
        initParam.setSiderParam(siderParam);
        initParam.setResultParam(resultParam);
        return initParam;
    }

    private PreviewInitParam buildInitParamWithSingleReport(String reportKey, PreviewInitReqParam reqParam) {
        SummaryReport reportDefine = this.runtimeSummarySolutionService.getSummaryReportDefine(reportKey);
        String solution = reportDefine.getSolution();
        PreviewInitParam initParam = this.buildInitParamWithSingleSolu(solution, reqParam, false);
        ArrayList<SummaryReport> reports = new ArrayList<SummaryReport>();
        reports.add(reportDefine);
        initParam.getResultParam().setReports(reports);
        return initParam;
    }

    @Override
    public CellBook getSummaryResult(PreviewConfigVo configVo) throws JQException, SummaryCommonException {
        SummaryReportModel summaryReportModel = this.runtimeSummarySolutionService.getSummaryReportModel(configVo.getReportKey());
        String solutionKey = this.designSummarySolutionService.getSummaryReport(configVo.getReportKey()).getSolution();
        SummarySolutionModel summarySolutionModel = this.runtimeSummarySolutionService.getSummarySolutionModel(solutionKey);
        TaskDefine mainTask = this.summaryParamService.getTaskDefine(summarySolutionModel.getMainTask());
        DimensionValueSet dimensionValueSet = this.transDimensionValueSet(configVo.getConditions(), mainTask, summarySolutionModel.getTargetDimension());
        QueryPageConfig queryPageConfig = this.tansPageConfig(configVo.getPageInfos());
        try {
            GridData result = new SummaryQueryExecutor().query(summarySolutionModel, summaryReportModel, dimensionValueSet, queryPageConfig);
            CellBook cellBook = this.transCellBook(result);
            return cellBook;
        }
        catch (Exception e) {
            SummaryLoggerHelper.error("\u67e5\u8be2\u6c47\u603b\u6307\u6807\u6570\u636e", "\u6c47\u603b\u8868\u6807\u9898\uff1a" + summaryReportModel.getTitle());
            throw new JQException((ErrorEnum)SummaryErrorEnum.SUMMARY_EXCEPTION_130, e.getMessage(), (Throwable)e);
        }
    }

    private DimensionValueSet transDimensionValueSet(Map<String, String[]> conditions, TaskDefine mainTask, String targetDimension) {
        Object mainValue;
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        String mainKey = this.entityMetaService.getEntityCode(targetDimension);
        if (OrgAdapterUtil.isOrg((String)targetDimension)) {
            mainKey = "MD_ORG";
        }
        if (ObjectUtils.isEmpty(mainValue = this.getValue(conditions.get(mainKey)))) {
            mainValue = this.getValue(conditions.get("MD_ORG"));
        }
        dimensionValueSet.setValue(mainKey, mainValue);
        String timeDimKey = mainTask.getDateTime();
        String timeCode = BqlTimeDimUtils.getBqlTimeDimTable((String)timeDimKey);
        Object timeValue = this.getDataTimeValue(conditions.get(timeCode), mainTask.getPeriodType());
        if (ObjectUtils.isEmpty(timeValue)) {
            timeValue = this.getDataTimeValue(conditions.get(timeDimKey), mainTask.getPeriodType());
        }
        dimensionValueSet.setValue("DATATIME", timeValue);
        String dims = mainTask.getDims();
        if (StringUtils.hasLength(dims)) {
            String[] dimArr;
            for (String dimKey : dimArr = dims.split(";")) {
                String dimCode = this.entityMetaService.getEntityCode(dimKey);
                if (!conditions.containsKey(dimCode)) continue;
                dimensionValueSet.setValue(dimCode, this.getValue(conditions.get(dimCode)));
            }
        }
        return dimensionValueSet;
    }

    private Object getValue(String[] values) {
        if (values != null) {
            if (values.length == 1) {
                return values[0];
            }
            return Arrays.asList(values);
        }
        return null;
    }

    private Object getDataTimeValue(String[] values, PeriodType periodType) {
        if (values != null) {
            List list = Arrays.stream(values).map(value -> TimeDimUtils.getDataTimeByTimeDim((String)value, (String)String.valueOf((char)Character.toUpperCase(periodType.code())))).collect(Collectors.toList());
            if (list.size() == 1) {
                return list.get(0);
            }
            return list;
        }
        return null;
    }

    private QueryPageConfig tansPageConfig(Map<Integer, PageInfo> pageInfos) {
        QueryPageConfig queryPageConfig = new QueryPageConfig();
        queryPageConfig.setPageInfos(pageInfos == null ? new HashMap() : pageInfos);
        return queryPageConfig;
    }

    private CellBook transCellBook(GridData gridData) {
        if (gridData != null) {
            CellBook cellBook = new CellBook();
            CellBookGriddataConverter.gridDataToCellBook((GridData)gridData, (CellBook)cellBook, (String)"result", (String)"result");
            return cellBook;
        }
        return null;
    }
}

