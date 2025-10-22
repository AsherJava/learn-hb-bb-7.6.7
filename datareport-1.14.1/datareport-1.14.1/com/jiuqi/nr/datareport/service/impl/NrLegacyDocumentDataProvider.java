/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.quickreport.engine.IReportEngine
 *  com.jiuqi.bi.quickreport.engine.ReportEngineException
 *  com.jiuqi.bi.quickreport.model.QuickReport
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.exception.ExpressionException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IExpressionEvaluator
 *  com.jiuqi.np.sql.type.Convert
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.ReportTagDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController
 *  com.jiuqi.nr.definition.reportTag.common.ReportTagType
 *  com.jiuqi.nvwa.datav.dashboard.adapter.chart.ChartImageConfig
 *  com.jiuqi.nvwa.datav.dashboard.adapter.chart.IDashboardScreenshotSdk
 *  com.jiuqi.nvwa.datav.dashboard.domain.DashboardModel
 *  com.jiuqi.nvwa.datav.dashboard.domain.Widget
 *  com.jiuqi.nvwa.datav.dashboard.enums.LayoutType
 *  com.jiuqi.nvwa.datav.dashboard.manager.DashboardManager
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.ParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.office.template.document.DocumentContext
 *  com.jiuqi.nvwa.office.template.document.ILegacyDocumentDataProvider
 *  com.jiuqi.nvwa.office.template.document.fragment.context.WordImageContext
 *  com.jiuqi.nvwa.quickreport.common.NvwaQuickReportException
 *  com.jiuqi.nvwa.quickreport.service.QuickReportModelService
 */
package com.jiuqi.nr.datareport.service.impl;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.engine.IReportEngine;
import com.jiuqi.bi.quickreport.engine.ReportEngineException;
import com.jiuqi.bi.quickreport.model.QuickReport;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IExpressionEvaluator;
import com.jiuqi.np.sql.type.Convert;
import com.jiuqi.nr.datareport.helper.GridDataHelper;
import com.jiuqi.nr.datareport.helper.GridDataTransformUtil;
import com.jiuqi.nr.datareport.helper.ReportUtil;
import com.jiuqi.nr.datareport.obj.NrDocumentParam;
import com.jiuqi.nr.datareport.obj.ReportEnv;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.ReportTagDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController;
import com.jiuqi.nr.definition.reportTag.common.ReportTagType;
import com.jiuqi.nvwa.datav.dashboard.adapter.chart.ChartImageConfig;
import com.jiuqi.nvwa.datav.dashboard.adapter.chart.IDashboardScreenshotSdk;
import com.jiuqi.nvwa.datav.dashboard.domain.DashboardModel;
import com.jiuqi.nvwa.datav.dashboard.domain.Widget;
import com.jiuqi.nvwa.datav.dashboard.enums.LayoutType;
import com.jiuqi.nvwa.datav.dashboard.manager.DashboardManager;
import com.jiuqi.nvwa.framework.parameter.IParameterEnv;
import com.jiuqi.nvwa.framework.parameter.ParameterEnv;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.office.template.document.DocumentContext;
import com.jiuqi.nvwa.office.template.document.ILegacyDocumentDataProvider;
import com.jiuqi.nvwa.office.template.document.fragment.context.WordImageContext;
import com.jiuqi.nvwa.quickreport.common.NvwaQuickReportException;
import com.jiuqi.nvwa.quickreport.service.QuickReportModelService;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class NrLegacyDocumentDataProvider
implements ILegacyDocumentDataProvider {
    private static final Logger logger = LoggerFactory.getLogger(NrLegacyDocumentDataProvider.class);
    @Autowired
    private RuntimeViewController runtimeViewController;
    @Autowired
    private IDataAccessProvider iDataAccessProvider;
    @Autowired
    private GridDataHelper gridDataHelper;
    @Autowired
    private IDashboardScreenshotSdk dashboardScreenshotSdk;
    @Autowired
    private DashboardManager dashboardManager;
    @Autowired
    private QuickReportModelService quickReportModelService;

    public Class<?> getResultType(String tag, String content, DocumentContext documentContext) {
        if ("EXPR".equals(tag)) {
            return String.class;
        }
        if ("TAG".equals(tag)) {
            NrDocumentParam nrDocumentParam = ReportUtil.getNrDocumentParam(documentContext);
            ReportTagDefine reportTag = nrDocumentParam.getTagMap().get(content);
            if (reportTag == null) {
                logger.debug(content + "\u672a\u627e\u5230\u5bf9\u5e94\u7684\u6807\u7b7e");
                return null;
            }
            String expression = reportTag.getExpression();
            if (StringUtils.hasText(expression)) {
                if (ReportTagType.FORM.getKey() == reportTag.getType()) {
                    return GridData.class;
                }
                if (ReportTagType.CHART.getKey() == reportTag.getType()) {
                    return byte[].class;
                }
                if (ReportTagType.QUICK_REPORT.getKey() == reportTag.getType()) {
                    return GridData.class;
                }
                return String.class;
            }
        }
        return null;
    }

    public Object getResult(String tag, String content, DocumentContext documentContext) {
        NrDocumentParam nrDocumentParam = ReportUtil.getNrDocumentParam(documentContext);
        if ("EXPR".equals(tag)) {
            return this.getExpTagValue(content, nrDocumentParam);
        }
        if ("TAG".equals(tag)) {
            ReportTagDefine reportTag = nrDocumentParam.getTagMap().get(content);
            if (reportTag == null) {
                logger.debug(content + "\u672a\u627e\u5230\u5bf9\u5e94\u7684\u6807\u7b7e");
                return null;
            }
            String expression = reportTag.getExpression();
            if (StringUtils.hasText(expression)) {
                if (ReportTagType.FORM.getKey() == reportTag.getType()) {
                    return this.getFormTagValue(expression, nrDocumentParam, documentContext);
                }
                if (ReportTagType.CHART.getKey() == reportTag.getType()) {
                    return this.getChartTagValue(expression, nrDocumentParam, documentContext);
                }
                if (ReportTagType.QUICK_REPORT.getKey() == reportTag.getType()) {
                    return this.getQuickReportTagValue(content, expression, nrDocumentParam, documentContext);
                }
                return this.getExpTagValue(expression, nrDocumentParam);
            }
        }
        return null;
    }

    private String getExpTagValue(String expression, NrDocumentParam nrDocumentParam) {
        try {
            DimensionCombination dimensionCombination = nrDocumentParam.getDimensionCombination();
            return this.parseExpression(dimensionCombination.toDimensionValueSet(), expression, nrDocumentParam.getExecutorContext());
        }
        catch (Exception e) {
            logger.debug(e.getMessage(), e);
            return "";
        }
    }

    private GridData getFormTagValue(String expression, NrDocumentParam nrDocumentParam, DocumentContext documentContext) {
        try {
            ReportEnv reportEnv = nrDocumentParam.getReportEnv();
            FormDefine form = this.runtimeViewController.queryFormByCodeInScheme(reportEnv.getFormSchemeKey(), expression);
            String formKey = form.getKey();
            Grid2Data grid2Data = this.gridDataHelper.getGridData(formKey);
            DimensionCombination dimensionCombination = nrDocumentParam.getDimensionCombination();
            Grid2Data fillGrid2Dta = this.gridDataHelper.fillGrid2Dta(formKey, reportEnv.getFormulaSchemeKey(), dimensionCombination, grid2Data);
            if (fillGrid2Dta == null) {
                fillGrid2Dta = grid2Data;
            }
            GridData gridData = new GridData();
            GridDataTransformUtil.data2ToData(fillGrid2Dta, gridData);
            documentContext.setProperty("WordTableContext", (Object)ReportUtil.getWordTableContext());
            return gridData;
        }
        catch (Exception e) {
            logger.debug(e.getMessage(), e);
            return null;
        }
    }

    private byte[] getChartTagValue(String expression, NrDocumentParam nrDocumentParam, DocumentContext documentContext) {
        try {
            if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)expression)) {
                String[] split = expression.split("@");
                Map<String, DashboardModel> dashboardModelCache = nrDocumentParam.getDashboardModelMap();
                Map<String, String[]> chertParam = nrDocumentParam.getChartParam();
                if (split.length >= 2) {
                    DashboardModel dashboardModel;
                    String dashboardId = split[0];
                    String chartId = split[1];
                    ChartImageConfig imageConfig = new ChartImageConfig();
                    byte[] bytes = this.dashboardScreenshotSdk.renderByGuid(dashboardId, chartId, imageConfig, chertParam);
                    WordImageContext context = new WordImageContext();
                    if (dashboardModelCache.containsKey(dashboardId)) {
                        dashboardModel = dashboardModelCache.get(dashboardId);
                    } else {
                        dashboardModel = this.dashboardManager.getDashboardModel(dashboardId, false);
                        dashboardModelCache.put(dashboardId, dashboardModel);
                    }
                    LayoutType layoutType = dashboardModel.getConfig().getLayout().getLayoutType();
                    if (layoutType == LayoutType.GRID) {
                        context.setHeight(300);
                        context.setWidth(400);
                    } else if (layoutType == LayoutType.FREE) {
                        Widget widget = this.dashboardManager.getWidget(dashboardId, chartId);
                        int height = widget.getRegion().getHeight();
                        int width = widget.getRegion().getWidth();
                        context.setHeight(height);
                        context.setWidth(width);
                    }
                    documentContext.setProperty("WordImageContext", (Object)context);
                    return bytes;
                }
            }
        }
        catch (Exception e) {
            logger.debug(e.getMessage(), e);
        }
        return null;
    }

    private GridData getQuickReportTagValue(String content, String expression, NrDocumentParam nrDocumentParam, DocumentContext documentContext) {
        String userId = NpContextHolder.getContext().getUserId();
        try {
            QuickReport quickReport = this.quickReportModelService.getQuickReportByGuidOrId(expression);
            ParameterEnv parameterEnv = new ParameterEnv(userId, quickReport.getParamModels().stream().filter(Objects::nonNull).collect(Collectors.toList()));
            Map<String, List<Object>> quickReportParam = nrDocumentParam.getQuickReportParam();
            parameterEnv.initValue(quickReportParam);
            IReportEngine engine = ReportUtil.getQuickReportEngine(userId, quickReport, (IParameterEnv)parameterEnv, 0);
            GridData gridData = engine.getPrimarySheet().getGridData();
            documentContext.setProperty("WordTableContext", (Object)ReportUtil.getWordTableContext());
            return gridData;
        }
        catch (NvwaQuickReportException e) {
            logger.error(content + "-" + expression + "\u83b7\u53d6\u5feb\u901f\u5206\u6790\u8868\u5f02\u5e38:" + e.getMessage(), e);
        }
        catch (ParameterException e) {
            logger.error(content + "-" + expression + "\u83b7\u53d6\u5feb\u901f\u5206\u6790\u8868\u65f6\u53c2\u6570\u4f20\u9012\u5f02\u5e38:" + e.getMessage(), e);
        }
        catch (ReportEngineException e) {
            logger.error(content + "-" + expression + "\u67e5\u8be2\u5206\u6790\u8868\u6570\u636e\u5f02\u5e38:" + e.getMessage(), e);
        }
        return null;
    }

    private String parseExpression(DimensionValueSet dimensionValueSet, String expression, ExecutorContext executorContext) throws ExpressionException {
        IExpressionEvaluator evaluator = this.iDataAccessProvider.newExpressionEvaluator();
        String parsedStr = Convert.toString((Object)evaluator.evalValue(expression, executorContext, dimensionValueSet));
        return parsedStr == null ? "" : parsedStr;
    }
}

