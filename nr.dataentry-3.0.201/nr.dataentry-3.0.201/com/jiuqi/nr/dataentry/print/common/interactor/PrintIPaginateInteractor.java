/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.grid.GridData
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IExpressionEvaluator
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.excel.obj.ExportMeasureSetting
 *  com.jiuqi.nr.data.excel.obj.ExportOps
 *  com.jiuqi.nr.data.excel.param.FormExpPar
 *  com.jiuqi.nr.data.excel.service.IDataExportService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.RegionSettingDefine
 *  com.jiuqi.nr.definition.facade.RegionTabSettingDefine
 *  com.jiuqi.nr.definition.facade.print.common.define.AbstractPageNumberGenerateStrategy
 *  com.jiuqi.nr.definition.facade.print.common.define.IPageNumberGenerateStrategy
 *  com.jiuqi.nr.definition.facade.print.common.interactor.GridDataContentStream
 *  com.jiuqi.nr.definition.facade.print.common.interactor.PaginateInteractorBase
 *  com.jiuqi.nr.definition.facade.print.common.interactor.adjustment.AdjustmentResponse
 *  com.jiuqi.nr.definition.facade.print.common.interactor.adjustment.FilterChain
 *  com.jiuqi.nr.definition.facade.print.common.other.PrintUtil
 *  com.jiuqi.nr.definition.facade.print.common.param.IPrintParamBase
 *  com.jiuqi.nr.definition.facade.print.core.ReportTemplateObject
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.base.RegionData
 *  com.jiuqi.nr.jtable.quickGridUtil.GridDataTransform
 *  com.jiuqi.nr.jtable.service.ICustomRegionsGradeService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.quickreport.api.query.Wrapper
 *  com.jiuqi.nvwa.quickreport.api.query.option.Option
 *  com.jiuqi.nvwa.quickreport.dto.ReportData
 *  com.jiuqi.nvwa.quickreport.web.query.GridController
 *  com.jiuqi.xg.process.IContentStream
 *  com.jiuqi.xg.process.IDrawObject
 *  com.jiuqi.xg.process.IDrawPage
 *  com.jiuqi.xg.process.ITemplateElement
 *  com.jiuqi.xg.process.ITemplateObject
 *  com.jiuqi.xg.process.ITemplatePage
 *  com.jiuqi.xlib.utils.CollectionUtils
 *  com.jiuqi.xlib.utils.StringUtils
 */
package com.jiuqi.nr.dataentry.print.common.interactor;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IExpressionEvaluator;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.excel.obj.ExportMeasureSetting;
import com.jiuqi.nr.data.excel.obj.ExportOps;
import com.jiuqi.nr.data.excel.param.FormExpPar;
import com.jiuqi.nr.data.excel.service.IDataExportService;
import com.jiuqi.nr.dataentry.bean.IExportFacade;
import com.jiuqi.nr.dataentry.internal.service.ReportExportService;
import com.jiuqi.nr.dataentry.print.common.param.PdfExportParam;
import com.jiuqi.nr.dataentry.print.common.param.PrintParam;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.RegionSettingDefine;
import com.jiuqi.nr.definition.facade.RegionTabSettingDefine;
import com.jiuqi.nr.definition.facade.print.common.define.AbstractPageNumberGenerateStrategy;
import com.jiuqi.nr.definition.facade.print.common.define.IPageNumberGenerateStrategy;
import com.jiuqi.nr.definition.facade.print.common.interactor.GridDataContentStream;
import com.jiuqi.nr.definition.facade.print.common.interactor.PaginateInteractorBase;
import com.jiuqi.nr.definition.facade.print.common.interactor.adjustment.AdjustmentResponse;
import com.jiuqi.nr.definition.facade.print.common.interactor.adjustment.FilterChain;
import com.jiuqi.nr.definition.facade.print.common.other.PrintUtil;
import com.jiuqi.nr.definition.facade.print.common.param.IPrintParamBase;
import com.jiuqi.nr.definition.facade.print.core.ReportTemplateObject;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.quickGridUtil.GridDataTransform;
import com.jiuqi.nr.jtable.service.ICustomRegionsGradeService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.quickreport.api.query.Wrapper;
import com.jiuqi.nvwa.quickreport.api.query.option.Option;
import com.jiuqi.nvwa.quickreport.dto.ReportData;
import com.jiuqi.nvwa.quickreport.web.query.GridController;
import com.jiuqi.xg.process.IContentStream;
import com.jiuqi.xg.process.IDrawObject;
import com.jiuqi.xg.process.IDrawPage;
import com.jiuqi.xg.process.ITemplateElement;
import com.jiuqi.xg.process.ITemplateObject;
import com.jiuqi.xg.process.ITemplatePage;
import com.jiuqi.xlib.utils.CollectionUtils;
import com.jiuqi.xlib.utils.StringUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

public class PrintIPaginateInteractor
extends PaginateInteractorBase {
    private static final Logger logger = LoggerFactory.getLogger(PrintIPaginateInteractor.class);
    private String reportGuid;
    private Map<String, String> patternAndValue = new HashMap<String, String>();
    private Map<String, IContentStream> reportElementContentCache;
    private IPrintParamBase param;
    private IPageNumberGenerateStrategy pageNumberGenerateStrategy;
    private Map<String, String> replaceMap;
    private boolean printSumCover;
    private ExecutorContext executorContext;
    private IExpressionEvaluator expressionEvaluator;

    public PrintIPaginateInteractor(IPrintParamBase param) {
        this.param = param;
        this.reportGuid = param.getFormKey();
        IDataAccessProvider dataAccessProvider = (IDataAccessProvider)BeanUtil.getBean(IDataAccessProvider.class);
        IDataDefinitionRuntimeController runtimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
        IRunTimeViewController runtimeView = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        IEntityViewRunTimeController entityViewRunTimeController = (IEntityViewRunTimeController)BeanUtil.getBean(IEntityViewRunTimeController.class);
        this.expressionEvaluator = dataAccessProvider.newExpressionEvaluator();
        String formSchemeKey = param.getFormSchemeKey();
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(runtimeView, runtimeController, entityViewRunTimeController, formSchemeKey);
        ExecutorContext executorContext = new ExecutorContext(runtimeController);
        executorContext.setEnv((IFmlExecEnvironment)environment);
        executorContext.setJQReportModel(true);
        this.executorContext = executorContext;
    }

    public boolean adjustment(ITemplateObject templateObj, IDrawObject drawObj, int pageIndex) {
        AdjustmentResponse res = new AdjustmentResponse();
        FilterChain filterChain = FilterChain.getInstance();
        filterChain.doFilter(templateObj, drawObj, pageIndex, (PaginateInteractorBase)this, res);
        logger.debug(res.isAdjustment() + "==" + res.getMsg());
        return res.isAdjustment();
    }

    public boolean isWithinDrawScope(ITemplateObject templateObj, int pageIndex) {
        if (templateObj.getDrawScope() == 7) {
            return true;
        }
        return super.isWithinDrawScope(templateObj, pageIndex);
    }

    public FormExpPar getFormExpPar(IPrintParamBase param) {
        FormExpPar dataExpPar = new FormExpPar();
        if (param instanceof PrintParam && ((PrintParam)param).getContext().getDimensionSet().containsKey("DATASNAPSHOTID")) {
            dataExpPar.setDataSnapshotId(((DimensionValue)((PrintParam)param).getContext().getDimensionSet().get("DATASNAPSHOTID")).getValue());
        }
        dataExpPar.setFormKey(param.getFormKey());
        dataExpPar.setFormSchemeKey(param.getFormSchemeKey());
        IRunTimeViewController runtimeView = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        IPeriodEntityAdapter iPeriodEntityAdapter = (IPeriodEntityAdapter)BeanUtil.getBean(IPeriodEntityAdapter.class);
        FormSchemeDefine formScheme = runtimeView.getFormScheme(param.getFormSchemeKey());
        String dataTimeDimensionName = iPeriodEntityAdapter.getPeriodEntity(formScheme.getDateTime()).getDimensionName();
        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(param.getDimensionValueSet());
        dataExpPar.setDimensionCombination(dimensionCombinationBuilder.getCombination());
        ExportOps exportOps = new ExportOps();
        Map measureMap = param.getMeasureMap();
        PrintParam printParam = (PrintParam)param;
        String millennial = printParam.getContext().getMillennial();
        if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)millennial) && !millennial.equals("0")) {
            exportOps.setThousands(Boolean.valueOf(millennial.equals("1")));
        }
        ExportMeasureSetting exportMeasureSetting = new ExportMeasureSetting();
        for (Map.Entry entry : measureMap.entrySet()) {
            exportMeasureSetting.setKey((String)entry.getKey());
            exportMeasureSetting.setCode((String)entry.getValue());
        }
        if (StringUtils.hasText((String)param.getDecimal())) {
            exportMeasureSetting.setDecimal(Integer.parseInt(param.getDecimal()));
        }
        exportOps.setExportMeasureSetting(exportMeasureSetting);
        if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)printParam.getContext().getMillennialDecimal())) {
            exportOps.setDisplayDecimalPlaces(Integer.valueOf(printParam.getContext().getMillennialDecimal()));
        }
        exportOps.setEmptyForm(param.isExportEmptyTable());
        exportOps.setFormulaSchemeKey(param.getFormulaSchemeKey());
        exportOps.setOnlyStyle(false);
        exportOps.setExp0Form(printParam.isExportZero());
        List tabs = param.getTabs();
        if (!CollectionUtils.isEmpty((Collection)tabs)) {
            HashMap tabMap = new HashMap();
            IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class);
            FormDefine formDefine = runTimeViewController.queryFormById(param.getFormKey());
            List allRegionsInForm = runTimeViewController.getAllRegionsInForm(formDefine.getKey());
            for (DataRegionDefine dataRegionDefine : allRegionsInForm) {
                List regionTabSetting;
                RegionSettingDefine regionSetting = runTimeViewController.getRegionSetting(dataRegionDefine.getKey());
                if (regionSetting == null || null == (regionTabSetting = regionSetting.getRegionTabSetting())) continue;
                for (RegionTabSettingDefine regionTab : regionTabSetting) {
                    if (!tabs.contains(regionTab.getTitle())) continue;
                    if (!tabMap.containsKey(dataRegionDefine.getKey())) {
                        tabMap.put(dataRegionDefine.getKey(), new ArrayList());
                    }
                    ((List)tabMap.get(dataRegionDefine.getKey())).add(regionTab.getTitle());
                }
            }
            exportOps.setTabs(tabMap);
        }
        exportOps.setLabel(param.isLabel());
        exportOps.setSumData(true);
        IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        String dataTime = ((DimensionValue)printParam.getContext().getDimensionSet().get(dataTimeDimensionName)).getValue();
        try {
            ICustomRegionsGradeService iCustomRegionsGradeService = (ICustomRegionsGradeService)BeanUtil.getBean(ICustomRegionsGradeService.class);
            List regionDataList = jtableParamService.getRegions(param.getFormKey());
            List regionKeys = regionDataList.stream().map(RegionData::getKey).collect(Collectors.toList());
            Map customRegionsGrade = iCustomRegionsGradeService.getCustomRegionsGrade(regionKeys, dataTime);
            exportOps.setGradeInfos(customRegionsGrade);
        }
        catch (NoSuchBeanDefinitionException e) {
            logger.info("iCustomRegionsGradeService\u65b9\u6cd5\u672a\u5b9e\u73b0,\u65e0\u6cd5\u83b7\u53d6iCustomRegionsGradeService\u7ec4\u4ef6");
        }
        dataExpPar.setGridDataFormatted(true);
        dataExpPar.setOps(exportOps);
        return dataExpPar;
    }

    public <TDataSource> TDataSource getDataSource(ITemplateElement<TDataSource> tmplElement) {
        String kind = tmplElement.getKind();
        if ("element_report".equals(kind)) {
            ReportTemplateObject reportObj = (ReportTemplateObject)tmplElement;
            if (null == this.reportElementContentCache) {
                this.reportElementContentCache = new HashMap<String, IContentStream>();
            }
            if (this.reportElementContentCache.containsKey(reportObj.getReportGuid())) {
                IContentStream oldContent = this.reportElementContentCache.get(reportObj.getReportGuid());
                if (oldContent.available() == 0) {
                    oldContent.reset(null);
                }
                return (TDataSource)oldContent;
            }
            GridDataContentStream gridDataContentStream = null;
            com.jiuqi.grid.GridData reportGridData = reportObj.getGridData();
            if (this.printSumCover) {
                gridDataContentStream = new GridDataContentStream(reportGridData);
                this.reportElementContentCache.put(reportObj.getReportGuid(), (IContentStream)gridDataContentStream);
                return (TDataSource)gridDataContentStream;
            }
            Grid2Data grid2Data = null;
            IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
            ReportExportService reportExportService = (ReportExportService)BeanUtil.getBean(ReportExportService.class);
            IRunTimeViewController controller = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
            FormDefine formDefine = controller.queryFormById(this.param.getFormKey());
            if (FormType.FORM_TYPE_INSERTANALYSIS.equals((Object)formDefine.getFormType())) {
                ReportData rd = new ReportData();
                rd.setGuid(formDefine.getExtensionProp("analysisGuid").toString());
                Option op = new Option();
                EntityViewData dwEntityInfo = jtableParamService.getDwEntity(this.param.getFormSchemeKey());
                op.addParamValue("MD_ORG", (Object)this.param.getDimensionValueSet().getValue(dwEntityInfo.getDimensionName()).toString());
                EntityViewData periodEntityInfo = jtableParamService.getDataTimeEntity(this.param.getFormSchemeKey());
                op.addParamValue("MD_PERIOD", (Object)this.param.getDimensionValueSet().getValue(periodEntityInfo.getDimensionName()).toString());
                for (int i = 0; i < this.param.getDimensionValueSet().size(); ++i) {
                    op.addParamValue("P_" + this.param.getDimensionValueSet().getName(i), this.param.getDimensionValueSet().getValue(i));
                }
                GridController gridController = (GridController)BeanUtil.getBean(GridController.class);
                Wrapper wp = new Wrapper();
                wp.setOption(op);
                wp.setReportData(rd);
                try {
                    GridData gridData = gridController.getPrimarySheetGridData(wp);
                    Grid2Data analysisGrid2Data = new Grid2Data();
                    grid2Data = GridDataTransform.printGridDataToGrid2Data((GridData)gridData, (Grid2Data)analysisGrid2Data);
                }
                catch (Exception e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            } else {
                grid2Data = null == reportGridData ? jtableParamService.getGridData(this.param.getFormKey()) : PrintUtil.gridDataToGrid2Data((com.jiuqi.grid.GridData)reportGridData, null);
                IDataExportService iDataExportService = (IDataExportService)SpringBeanUtils.getBean(IDataExportService.class);
                FormExpPar formExpPar = this.getFormExpPar(this.param);
                try {
                    grid2Data = iDataExportService.expGrid2Data(formExpPar, grid2Data);
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if (grid2Data == null) {
                    this.param.setEmptyTable(true);
                }
            }
            com.jiuqi.grid.GridData sourceGridData = PrintUtil.grid2DataToGridData((Grid2Data)grid2Data, null);
            gridDataContentStream = new GridDataContentStream(sourceGridData);
            this.reportElementContentCache.put(reportObj.getReportGuid(), (IContentStream)gridDataContentStream);
            return (TDataSource)gridDataContentStream;
        }
        return null;
    }

    public String getReportGuid() {
        return this.reportGuid;
    }

    public void setReportGuid(String reportGuid) {
        this.reportGuid = reportGuid;
    }

    public Map<String, String> getPatternAndValue() {
        return this.patternAndValue;
    }

    public void setPatternAndValue(Map<String, String> patternAndValue) {
        this.patternAndValue = patternAndValue;
    }

    public Map<String, IContentStream> getReportElementContentCache() {
        return this.reportElementContentCache;
    }

    public void setReportElementContentCache(Map<String, IContentStream> reportElementContentCache) {
        this.reportElementContentCache = reportElementContentCache;
    }

    public IPrintParamBase getParam() {
        return this.param;
    }

    public void setParam(IPrintParamBase param) {
        this.param = param;
    }

    public IPageNumberGenerateStrategy getPageNumberGenerateStrategy() {
        return this.pageNumberGenerateStrategy;
    }

    public void setPageNumberGenerateStrategy(IPageNumberGenerateStrategy pageNumberGenerateStrategy) {
        this.pageNumberGenerateStrategy = pageNumberGenerateStrategy;
    }

    public Map<String, String> getReplaceMap() {
        return this.replaceMap;
    }

    public void setReplaceMap(Map<String, String> replaceMap) {
        this.replaceMap = replaceMap;
    }

    public ExecutorContext getExecutorContext() {
        return this.executorContext;
    }

    public void setExecutorContext(ExecutorContext executorContext) {
        this.executorContext = executorContext;
    }

    public IExpressionEvaluator getExpressionEvaluator() {
        return this.expressionEvaluator;
    }

    public void setExpressionEvaluator(IExpressionEvaluator expressionEvaluator) {
        this.expressionEvaluator = expressionEvaluator;
    }

    public static IExportFacade transformation(IPrintParamBase param) {
        PdfExportParam exportParam = new PdfExportParam();
        JtableContext jtableContext = new JtableContext();
        jtableContext.setMeasureMap(param.getMeasureMap());
        jtableContext.setDecimal(param.getDecimal());
        exportParam.setContext(jtableContext);
        jtableContext.setFormKey(param.getFormKey());
        jtableContext.setTaskKey(param.getTaskKey());
        exportParam.setExportEmptyTable(true);
        jtableContext.setFormSchemeKey(param.getFormSchemeKey());
        jtableContext.setFormulaSchemeKey(param.getFormulaSchemeKey());
        jtableContext.setDimensionSet(DimensionValueSetUtil.getDimensionSet((DimensionValueSet)param.getDimensionValueSet()));
        exportParam.setOnlyStyle(false);
        exportParam.setTabs(param.getTabs());
        exportParam.setLabel(param.isLabel());
        exportParam.setSumData(true);
        exportParam.setExportEmptyTable(param.isExportEmptyTable());
        return exportParam;
    }

    public boolean isPrintSumCover() {
        return this.printSumCover;
    }

    public void setPrintSumCover(boolean printSumCover) {
        this.printSumCover = printSumCover;
    }

    public void paginateCallback(ITemplatePage templatePage, List<IDrawPage> drawPages) {
        IPageNumberGenerateStrategy strategy = this.getPageNumberGenerateStrategy();
        if (null == strategy) {
            return;
        }
        if (strategy instanceof AbstractPageNumberGenerateStrategy) {
            ((AbstractPageNumberGenerateStrategy)strategy).accumulate(drawPages.size());
        }
    }
}

