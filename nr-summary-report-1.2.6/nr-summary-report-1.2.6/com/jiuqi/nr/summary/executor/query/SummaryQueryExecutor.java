/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridCell
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.parameter.engine.IParameterEnv
 *  com.jiuqi.bi.parameter.engine.NullParameterEnv
 *  com.jiuqi.bi.quickreport.engine.IReportEngine
 *  com.jiuqi.bi.quickreport.engine.IReportListener
 *  com.jiuqi.bi.quickreport.engine.ReportEngineException
 *  com.jiuqi.bi.quickreport.engine.ReportEngineFactory
 *  com.jiuqi.bi.quickreport.model.QuickReport
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  javax.servlet.http.HttpServletResponse
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.summary.executor.query;

import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.parameter.engine.IParameterEnv;
import com.jiuqi.bi.parameter.engine.NullParameterEnv;
import com.jiuqi.bi.quickreport.engine.IReportEngine;
import com.jiuqi.bi.quickreport.engine.IReportListener;
import com.jiuqi.bi.quickreport.engine.ReportEngineException;
import com.jiuqi.bi.quickreport.engine.ReportEngineFactory;
import com.jiuqi.bi.quickreport.model.QuickReport;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.exception.SummaryErrorEnum;
import com.jiuqi.nr.summary.executor.query.QueryPageConfig;
import com.jiuqi.nr.summary.executor.query.SummaryQueryException;
import com.jiuqi.nr.summary.executor.query.ds.SummaryDSModel;
import com.jiuqi.nr.summary.executor.query.ds.SummaryDSModelBuilder;
import com.jiuqi.nr.summary.executor.query.engine.QuickReportExecuteListener;
import com.jiuqi.nr.summary.executor.query.export.SummaryExportExecutor;
import com.jiuqi.nr.summary.executor.query.grid.GridHelper;
import com.jiuqi.nr.summary.executor.query.grid.QuickReportBuilder;
import com.jiuqi.nr.summary.model.cell.DataCell;
import com.jiuqi.nr.summary.model.cell.MainCell;
import com.jiuqi.nr.summary.model.cell.SummaryZb;
import com.jiuqi.nr.summary.model.report.SummaryFloatRegion;
import com.jiuqi.nr.summary.model.report.SummaryReportModel;
import com.jiuqi.nr.summary.model.soulution.SummarySolutionModel;
import com.jiuqi.nr.summary.utils.SummaryReportModelHelper;
import com.jiuqi.nr.summary.utils.SummaryReportUtil;
import com.jiuqi.nr.summary.vo.ExportConfig;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

public class SummaryQueryExecutor {
    private static final Logger logger = LoggerFactory.getLogger(SummaryQueryExecutor.class);

    public GridData query(SummarySolutionModel solutionModel, SummaryReportModel reportModel, DimensionValueSet dim, QueryPageConfig pageConfig) throws SummaryCommonException {
        try {
            this.processGridData(reportModel);
            SummaryReportModelHelper reportModelHelper = new SummaryReportModelHelper(reportModel);
            Map<String, SummaryDSModel> modelMap = this.getDataSetModel(solutionModel, reportModelHelper);
            QuickReport quickReport = new QuickReportBuilder(reportModelHelper, modelMap).build();
            QuickReportExecuteListener executeListener = new QuickReportExecuteListener(modelMap, dim, pageConfig);
            IReportEngine reportEngine = SummaryQueryExecutor.getiReportEngine(quickReport, executeListener);
            GridData gridData = reportEngine.getPrimarySheet().getGridData();
            return gridData;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SummaryCommonException(SummaryErrorEnum.QUERY_RESULT_FAIL);
        }
    }

    public IReportEngine getReportEngine(SummarySolutionModel solutionModel, SummaryReportModel reportModel, DimensionValueSet dim, QueryPageConfig pageConfig) throws SummaryQueryException {
        try {
            this.processGridData(reportModel);
            SummaryReportModelHelper reportModelHelper = new SummaryReportModelHelper(reportModel);
            Map<String, SummaryDSModel> modelMap = this.getDataSetModel(solutionModel, reportModelHelper);
            QuickReport quickReport = new QuickReportBuilder(reportModelHelper, modelMap).build();
            QuickReportExecuteListener executeListener = new QuickReportExecuteListener(modelMap, dim, pageConfig);
            return SummaryQueryExecutor.getiReportEngine(quickReport, executeListener);
        }
        catch (Exception e) {
            throw new SummaryQueryException(e);
        }
    }

    private void processGridData(SummaryReportModel summaryReportModel) {
        List<DataCell> dataCells;
        GridData griddata = summaryReportModel.getGridData();
        if (ObjectUtils.isEmpty(griddata)) {
            return;
        }
        GridHelper gridHelper = new GridHelper(griddata);
        List<MainCell> mainCells = summaryReportModel.getConfig().getMainCells();
        if (!CollectionUtils.isEmpty(mainCells)) {
            mainCells.forEach(mainCell -> {
                SummaryZb innerDimZb = mainCell.getInnerDimZb();
                if (!ObjectUtils.isEmpty(innerDimZb)) {
                    GridCell gridCell = gridHelper.getCellEx(SummaryReportUtil.getPosition(mainCell));
                    gridCell.setShowText(innerDimZb.getTitle());
                }
            });
        }
        if (!CollectionUtils.isEmpty(dataCells = summaryReportModel.getDataCells())) {
            dataCells.forEach(dataCell -> {
                GridCell gridCell = gridHelper.getCellEx(SummaryReportUtil.getPosition(dataCell));
                gridCell.setCellData(null);
            });
        }
    }

    @NotNull
    private static IReportEngine getiReportEngine(QuickReport quickReport, QuickReportExecuteListener executeListener) throws ReportEngineException {
        IReportEngine reportEngine = ReportEngineFactory.createEngine(null, (QuickReport)quickReport, (IParameterEnv)new NullParameterEnv(null));
        reportEngine.setLanguage(Locale.CHINESE.getLanguage());
        reportEngine.setListener((IReportListener)executeListener);
        reportEngine.initParamEnv();
        reportEngine.open(128);
        return reportEngine;
    }

    public void export(HttpServletResponse response, ExportConfig exportConfig) throws SummaryCommonException {
        new SummaryExportExecutor(exportConfig).export(response);
    }

    private Map<String, SummaryDSModel> getDataSetModel(SummarySolutionModel summarySolutionModel, SummaryReportModelHelper reportModelHelper) {
        IEntityMetaService entityMetaService = (IEntityMetaService)SpringBeanUtils.getBean(IEntityMetaService.class);
        HashMap<String, SummaryDSModel> modelMap = new HashMap<String, SummaryDSModel>();
        SummaryDSModelBuilder dsModelBuilder = new SummaryDSModelBuilder(entityMetaService, reportModelHelper.getReportName(), reportModelHelper.getReportTitle() + "\u56fa\u5b9a\u533a\u57df");
        SummaryDSModel fixModel = dsModelBuilder.build(summarySolutionModel, reportModelHelper);
        modelMap.put(fixModel.getName(), fixModel);
        List<SummaryFloatRegion> floatRegions = reportModelHelper.getFloatRegions();
        for (SummaryFloatRegion region : floatRegions) {
            dsModelBuilder = new SummaryDSModelBuilder(entityMetaService, region.getTableName(), "\u6d6e\u52a8\u533a\u57df_" + region.getPosition());
            dsModelBuilder.setFloatRegion(region);
            SummaryDSModel floatModel = dsModelBuilder.build(summarySolutionModel, reportModelHelper);
            modelMap.put(floatModel.getName(), floatModel);
        }
        return modelMap;
    }
}

