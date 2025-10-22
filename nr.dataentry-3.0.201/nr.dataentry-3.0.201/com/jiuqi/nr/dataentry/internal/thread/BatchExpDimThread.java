/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.common.param.CommonParams
 *  com.jiuqi.nr.data.excel.extend.export.cellstyle.CustomCellStyleProvider
 *  com.jiuqi.nr.data.excel.obj.BatchExportOps
 *  com.jiuqi.nr.data.excel.obj.DWShow
 *  com.jiuqi.nr.data.excel.obj.ExpExcelSyncResult
 *  com.jiuqi.nr.data.excel.obj.ExportMeasureSetting
 *  com.jiuqi.nr.data.excel.obj.ExportOps
 *  com.jiuqi.nr.data.excel.obj.FormShow
 *  com.jiuqi.nr.data.excel.obj.SplitChar
 *  com.jiuqi.nr.data.excel.param.BatchExpPar
 *  com.jiuqi.nr.data.excel.param.ExcelRule
 *  com.jiuqi.nr.data.excel.param.TitleShowSetting
 *  com.jiuqi.nr.data.excel.service.IDataExportService
 *  com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil
 *  com.jiuqi.nr.datacrud.spi.RowFilter
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.base.RegionData
 *  com.jiuqi.nr.jtable.service.ICustomRegionsGradeService
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.dataentry.internal.thread;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.common.param.CommonParams;
import com.jiuqi.nr.data.excel.extend.export.cellstyle.CustomCellStyleProvider;
import com.jiuqi.nr.data.excel.obj.BatchExportOps;
import com.jiuqi.nr.data.excel.obj.DWShow;
import com.jiuqi.nr.data.excel.obj.ExpExcelSyncResult;
import com.jiuqi.nr.data.excel.obj.ExportMeasureSetting;
import com.jiuqi.nr.data.excel.obj.ExportOps;
import com.jiuqi.nr.data.excel.obj.FormShow;
import com.jiuqi.nr.data.excel.obj.SplitChar;
import com.jiuqi.nr.data.excel.param.BatchExpPar;
import com.jiuqi.nr.data.excel.param.ExcelRule;
import com.jiuqi.nr.data.excel.param.TitleShowSetting;
import com.jiuqi.nr.data.excel.service.IDataExportService;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.datacrud.spi.RowFilter;
import com.jiuqi.nr.dataentry.bean.BatchExportData;
import com.jiuqi.nr.dataentry.bean.BatchExportInfo;
import com.jiuqi.nr.dataentry.bean.ExportHandleCurrParam;
import com.jiuqi.nr.dataentry.bean.ExportHandleParam;
import com.jiuqi.nr.dataentry.bean.ExportRuleSettings;
import com.jiuqi.nr.dataentry.export.IReportExport;
import com.jiuqi.nr.dataentry.internal.service.BatchExportExcelServiceImpl;
import com.jiuqi.nr.dataentry.internal.service.util.ExportUtil;
import com.jiuqi.nr.dataentry.model.BatchDimensionParam;
import com.jiuqi.nr.dataentry.provider.CustomCellStyleProviderImpl;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessProvider;
import com.jiuqi.nr.dataentry.service.ExportExcelNameService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.service.ICustomRegionsGradeService;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class BatchExpDimThread
extends Thread {
    private static final int MAXFILENAMELENGTH = 90;
    private static final int STOREDISKSZIE = 500;
    private ExportHandleParam exportHandleParam;
    private List<BatchExportData> datas;
    private IJtableParamService jtableParamService;
    private ReadWriteAccessProvider readWriteAccessProvider;
    private DataSource dataSource;
    private IReportExport reportExportService;
    private IJtableEntityService jtableEntityService;
    private JdbcTemplate jdbcTemplate;
    private ExportHandleCurrParam exportHandleCurrParam;
    private ExportExcelNameService exportExcelNameService;
    private Vector<String> unitCodes;
    private static final Logger log = LoggerFactory.getLogger(BatchExportExcelServiceImpl.class);
    private double numbOfIndex;
    private final IDataExportService dataExportService;
    private final ExcelRule rule;
    private final String expPath;

    public BatchExpDimThread(ExportHandleParam exportHandleParam, List<BatchExportData> datas, ExportHandleCurrParam exportHandleCurrParam, double numbOfIndex, IDataExportService dataExportService, ExcelRule rule, String expPath, Vector<String> unitCodes) {
        this.unitCodes = unitCodes;
        this.exportHandleParam = exportHandleParam;
        this.datas = datas;
        this.jtableParamService = exportHandleParam.getJtableParamService();
        this.readWriteAccessProvider = exportHandleParam.getReadWriteAccessProvider();
        this.jtableParamService = exportHandleParam.getJtableParamService();
        this.dataSource = exportHandleParam.getDataSource();
        this.reportExportService = exportHandleParam.getReportExportService();
        this.jtableEntityService = exportHandleParam.getJtableEntityService();
        this.jdbcTemplate = exportHandleParam.getJdbcTemplate();
        this.exportHandleCurrParam = exportHandleCurrParam;
        this.exportExcelNameService = exportHandleParam.getExportExcelNameService();
        this.numbOfIndex = numbOfIndex;
        this.rule = rule;
        this.dataExportService = dataExportService;
        this.expPath = expPath;
    }

    @Override
    public void run() {
        CountDownLatch latch = this.exportHandleParam.getLatch();
        try {
            Thread.sleep(1000L);
            NpContextHolder.setContext((NpContext)this.exportHandleParam.getNpContext());
            this.exportExcelByDim();
        }
        catch (Exception e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e.getMessage(), e);
        }
        finally {
            latch.countDown();
        }
    }

    public void exportExcelByDim() {
        try {
            String sysSeparator = this.exportExcelNameService.getSysSeparator();
            BatchExportInfo info = this.exportHandleParam.getInfo();
            AtomicInteger count = new AtomicInteger(0);
            HashMap regionDatas = new HashMap();
            BatchDimensionParam dimensionInfoBuild = this.exportHandleParam.getDimensionInfoBuild();
            INvwaSystemOptionService iNvwaSystemOptionService = (INvwaSystemOptionService)BeanUtil.getBean(INvwaSystemOptionService.class);
            String value = iNvwaSystemOptionService.get("nr-data-entry-export", "SIMPLIFY_EXPORT_FILE_HIERARCHY");
            boolean simplifyExportFileHierarchy = "1".equals(value);
            if (info.getRuleSettings() != null) {
                simplifyExportFileHierarchy = info.getRuleSettings().isSimplifyExportFileHierarchy();
            }
            JtableContext jtableContext = info.getContext();
            String companyType = this.exportHandleParam.getCompanyType();
            String dateType = this.exportHandleParam.getDateType();
            String dateDir = this.exportHandleParam.getDateDir();
            List<String> formKeys = this.exportHandleParam.getFormKeys();
            List<String> listEntity = this.exportHandleParam.getListEntity();
            List<Map<String, DimensionValue>> allResultDimension = dimensionInfoBuild.getList();
            List<String> repeatCompanyNameList = this.exportHandleParam.getRepeatCompanyNameList();
            double firstLevel = 0.05;
            double index = 0.0;
            double allSizePre = 0.0;
            double weight = 1.0;
            List<Map<String, DimensionValue>> currentDimension = this.exportHandleCurrParam.getCurrentDimension();
            int currentIndex = this.exportHandleCurrParam.getCurrentIndex();
            AsyncTaskMonitor asyncTaskMonitor = this.exportHandleParam.getAsyncTaskMonitor();
            log.info("\u7b2c{}\u4e2a\u7ebf\u7a0b\u5bfc\u51fa\u7684\u5355\u4f4d\u4e2a\u6570\u4e3a\uff1a{}", (Object)currentIndex, (Object)currentDimension.size());
            List<String> entityKeys = this.exportHandleParam.getDimensionInfoBuild().getEntitys();
            HashMap menuMap = new HashMap();
            HashMap cacheRegionForm = new HashMap();
            HashMap<String, String> infoMap = new HashMap<String, String>();
            if (simplifyExportFileHierarchy) {
                for (Map<String, DimensionValue> map : currentDimension) {
                    Set<String> keySet = map.keySet();
                    for (String key : keySet) {
                        infoMap.put(map.get(key).getValue(), key);
                    }
                }
            }
            ExecutorContext executorContext = this.getExecutorContext(jtableContext.getFormSchemeKey());
            ArrayList fileNameList = new ArrayList();
            IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
            List formDefines = CollectionUtils.isEmpty(this.exportHandleParam.getFormKeys()) ? runTimeViewController.queryAllFormDefinesByFormScheme(jtableContext.getFormSchemeKey()) : runTimeViewController.queryFormsById(this.exportHandleParam.getFormKeys());
            IPeriodEntityAdapter iPeriodEntityAdapter = (IPeriodEntityAdapter)BeanUtil.getBean(IPeriodEntityAdapter.class);
            FormSchemeDefine formScheme = runTimeViewController.getFormScheme(jtableContext.getFormSchemeKey());
            String dataTimeDimensionName = iPeriodEntityAdapter.getPeriodEntity(formScheme.getDateTime().toString()).getDimensionName();
            List expForms = formDefines.stream().filter(o -> o != null && o.getFormType() != FormType.FORM_TYPE_ANALYSISREPORT).map(IBaseMetaItem::getKey).collect(Collectors.toList());
            Map mergedDimension = DimensionValueSetUtil.mergeDimension(currentDimension);
            ExportUtil.handleMdCurDim(mergedDimension, info.getContext().getDimensionSet());
            DimensionCollectionUtil dimensionCollectionUtil = (DimensionCollectionUtil)BeanUtil.getBean(DimensionCollectionUtil.class);
            DimensionCollection dimensionCollection = dimensionCollectionUtil.getDimensionCollection(mergedDimension, jtableContext.getFormSchemeKey());
            BatchExpPar batchExpPar = new BatchExpPar();
            batchExpPar.setForms(expForms);
            batchExpPar.setFormSchemeKey(jtableContext.getFormSchemeKey());
            batchExpPar.setDimensionCollection(dimensionCollection);
            batchExpPar.setRule(this.rule);
            ExportOps exportOps = new ExportOps();
            ICustomRegionsGradeService iCustomRegionsGradeService = this.exportHandleParam.getiCustomRegionsGradeService();
            IJtableParamService jtableParamService = this.exportHandleParam.getJtableParamService();
            Map customRegionsGrade = new HashMap();
            if (info.isSumData()) {
                String dataTime = ((DimensionValue)jtableContext.getDimensionSet().get(dataTimeDimensionName)).getValue();
                if (iCustomRegionsGradeService != null) {
                    ArrayList regionKeys = new ArrayList();
                    for (String string : formKeys) {
                        List list = jtableParamService.getRegions(string);
                        List regionSingleKeys = list.stream().map(RegionData::getKey).collect(Collectors.toList());
                        regionKeys.addAll(regionSingleKeys);
                    }
                    customRegionsGrade = iCustomRegionsGradeService.getCustomRegionsGrade(regionKeys, dataTime);
                    exportOps.setGradeInfos(customRegionsGrade);
                } else {
                    log.info("iCustomRegionsGradeService\u65b9\u6cd5\u4e3a\u5b9e\u73b0,\u65e0\u6cd5\u83b7\u53d6iCustomRegionsGradeService\u7ec4\u4ef6");
                }
            } else {
                exportOps.setGradeInfos(customRegionsGrade);
            }
            exportOps.setEmptyForm(info.isExportEmptyTable());
            exportOps.setExpExcelDirSheet(info.isExpExcelDirSheet());
            exportOps.setEt(info.isExportETFile());
            exportOps.setExpCellBColor(info.isCellBackGround());
            String millennial = jtableContext.getMillennial();
            if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)millennial) && !millennial.equals("0")) {
                exportOps.setThousands(Boolean.valueOf(millennial.equals("1")));
            }
            Map measureMap = info.getContext().getMeasureMap();
            ExportMeasureSetting exportMeasureSetting = new ExportMeasureSetting();
            for (Map.Entry entry : measureMap.entrySet()) {
                exportMeasureSetting.setKey((String)entry.getKey());
                exportMeasureSetting.setCode((String)entry.getValue());
            }
            if (StringUtils.hasText(info.getContext().getDecimal())) {
                exportMeasureSetting.setDecimal(Integer.parseInt(info.getContext().getDecimal()));
            }
            exportOps.setExportMeasureSetting(exportMeasureSetting);
            if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)info.getContext().getMillennialDecimal())) {
                exportOps.setDisplayDecimalPlaces(Integer.valueOf(Integer.parseInt(info.getContext().getMillennialDecimal())));
            }
            exportOps.setFormulaSchemeKey(info.getContext().getFormulaSchemeKey());
            exportOps.setPrintSchemeKey(info.getPrintSchemeKey());
            exportOps.setLabel(info.isTableTab());
            exportOps.setSumData(info.isSumData());
            exportOps.setExp0Form(info.isExportZero());
            if (info.isArithmeticBackground() && info.isArithmeticBackground()) {
                CustomCellStyleProviderImpl customCellStyleProviderImpl = new CustomCellStyleProviderImpl(info.getContext());
                batchExpPar.setCustomCellStyleProvider((CustomCellStyleProvider)customCellStyleProviderImpl);
            }
            Map<String, List<RowFilter>> map = ExportUtil.getRowFilterMap(info.getRegionFilterListInfo());
            exportOps.setRowFilter(map);
            if (info.getRuleSettings() != null) {
                ExportRuleSettings exportRuleSettings = info.getRuleSettings();
                TitleShowSetting titleShowSetting = new TitleShowSetting();
                exportOps.setTitleShowSetting(titleShowSetting);
                if (exportRuleSettings.getSheetName() != null) {
                    for (String sheetName : exportRuleSettings.getSheetName()) {
                        if (sheetName.equals(FormShow.TITLE.getKey())) {
                            titleShowSetting.addFormShow(FormShow.TITLE);
                            continue;
                        }
                        if (sheetName.equals(FormShow.CODE.getKey())) {
                            titleShowSetting.addFormShow(FormShow.CODE);
                            continue;
                        }
                        if (!sheetName.equals(FormShow.SERIAL_NUM.getKey())) continue;
                        titleShowSetting.addFormShow(FormShow.SERIAL_NUM);
                    }
                }
                if (exportRuleSettings.getExcelName() != null) {
                    for (String excelName : exportRuleSettings.getExcelName()) {
                        if (excelName.equals(DWShow.TITLE.getKey())) {
                            titleShowSetting.addDwShow(DWShow.TITLE);
                            continue;
                        }
                        if (excelName.equals(DWShow.CODE.getKey())) {
                            titleShowSetting.addDwShow(DWShow.CODE);
                            continue;
                        }
                        if (excelName.equals(DWShow.PERIOD_TITLE.getKey())) {
                            titleShowSetting.addDwShow(DWShow.PERIOD_TITLE);
                            continue;
                        }
                        if (!excelName.equals(DWShow.TASK_TITLE.getKey())) continue;
                        titleShowSetting.addDwShow(DWShow.TASK_TITLE);
                    }
                }
                if (exportRuleSettings.getSeparatorCode() != null) {
                    if (exportRuleSettings.getSeparatorCode().equals(SplitChar.SPACE.getKey())) {
                        titleShowSetting.setSplitChar(SplitChar.SPACE);
                    } else if (exportRuleSettings.getSeparatorCode().equals(SplitChar.AND.getKey())) {
                        titleShowSetting.setSplitChar(SplitChar.AND);
                    } else if (exportRuleSettings.getSeparatorCode().equals(SplitChar.UNDERLINE.getKey())) {
                        titleShowSetting.setSplitChar(SplitChar.UNDERLINE);
                    }
                }
                titleShowSetting.setSimplifyExpFileHierarchy(Boolean.valueOf(exportRuleSettings.isSimplifyExportFileHierarchy()));
            } else {
                TitleShowSetting titleShowSetting = new TitleShowSetting();
                titleShowSetting.setSimplifyExpFileHierarchy(Boolean.valueOf(simplifyExportFileHierarchy));
                exportOps.setTitleShowSetting(titleShowSetting);
            }
            batchExpPar.setOps(exportOps);
            BatchExportOps batchExportOps = new BatchExportOps();
            batchExportOps.setExpAppendedFile(info.isExportEnclosure());
            batchExpPar.setBatchExportOps(batchExportOps);
            CommonParams commonParams = new CommonParams();
            commonParams.setMonitor(asyncTaskMonitor);
            ExpExcelSyncResult expExcelSyncResult = this.dataExportService.expExcelSync(batchExpPar, this.expPath, commonParams);
            if (asyncTaskMonitor.isCancel()) {
                asyncTaskMonitor.canceled("stop_execute", (Object)"stop_execute");
                return;
            }
            if (!expExcelSyncResult.isNoDataNoExp()) {
                this.datas.add(new BatchExportData());
            }
            Set expDws = expExcelSyncResult.getExpDws();
            for (String expDw : expDws) {
                this.unitCodes.add(expDw);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private ExecutorContext getExecutorContext(String formSchemeKey) {
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
        ExecutorContext executorContext = new ExecutorContext(dataDefinitionRuntimeController);
        IEntityViewRunTimeController entityViewRunTimeController = (IEntityViewRunTimeController)BeanUtil.getBean(IEntityViewRunTimeController.class);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(runTimeViewController, dataDefinitionRuntimeController, entityViewRunTimeController, formSchemeKey);
        executorContext.setEnv((IFmlExecEnvironment)environment);
        executorContext.setJQReportModel(true);
        return executorContext;
    }
}

