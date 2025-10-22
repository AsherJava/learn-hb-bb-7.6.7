/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
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
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.dataentry.internal.thread;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
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
import com.jiuqi.nr.dataentry.provider.CustomCellStyleProviderImpl;
import com.jiuqi.nr.dataentry.provider.DimensionValueProvider;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessProvider;
import com.jiuqi.nr.dataentry.service.ExportExcelNameService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class BatchExpFormThread
extends Thread {
    private static final int STOREDISKSZIE = 500;
    private ExportHandleParam exportHandleParam;
    private double allSize;
    private List<BatchExportData> datas;
    private IJtableParamService jtableParamService;
    private ReadWriteAccessProvider readWriteAccessProvider;
    private IReportExport reportExportService;
    private IJtableEntityService jtableEntityService;
    private DimensionValueProvider dimensionValueProvider;
    private ExportHandleCurrParam exportHandleCurrParam;
    private ExportExcelNameService exportExcelNameService;
    private static final Logger log = LoggerFactory.getLogger(BatchExportExcelServiceImpl.class);
    private double numOfIndex;
    private final IDataExportService dataExportService;
    private final ExcelRule rule;
    private final String expPath;

    public BatchExpFormThread(ExportHandleParam exportHandleParam, double allSize, List<BatchExportData> datas, ExportHandleCurrParam exportHandleCurrParam, double numOfIndex, IDataExportService dataExportService, ExcelRule rule, String expPath) {
        this.allSize = allSize;
        this.exportHandleParam = exportHandleParam;
        this.datas = datas;
        this.jtableParamService = exportHandleParam.getJtableParamService();
        this.readWriteAccessProvider = exportHandleParam.getReadWriteAccessProvider();
        this.jtableParamService = exportHandleParam.getJtableParamService();
        this.reportExportService = exportHandleParam.getReportExportService();
        this.jtableEntityService = exportHandleParam.getJtableEntityService();
        this.dimensionValueProvider = exportHandleParam.getDimensionValueProvider();
        this.exportHandleCurrParam = exportHandleCurrParam;
        this.exportExcelNameService = exportHandleParam.getExportExcelNameService();
        this.numOfIndex = numOfIndex;
        this.rule = rule;
        this.dataExportService = dataExportService;
        this.expPath = expPath;
    }

    @Override
    public void run() {
        CountDownLatch latch = this.exportHandleParam.getLatch();
        try {
            NpContextHolder.setContext((NpContext)this.exportHandleParam.getNpContext());
            this.ExportExcelByForm();
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        finally {
            latch.countDown();
        }
    }

    public void ExportExcelByForm() {
        String millennial;
        double firstLevel = 0.05;
        String companyType = this.exportHandleParam.getCompanyType();
        String dateType = this.exportHandleParam.getDateType();
        List<EntityViewData> entityList = this.exportHandleParam.getEntityList();
        String dateDir = this.exportHandleParam.getDateDir();
        List<String> companyList = this.exportHandleParam.getCompanyList();
        AsyncTaskMonitor asyncTaskMonitor = this.exportHandleParam.getAsyncTaskMonitor();
        List<Map<String, Object>> allResultDimension = this.exportHandleParam.getDimensionInfoBuild().getList();
        DimensionCollection dimCollection = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.buildDimensionCollection((Map)this.exportHandleParam.getInfo().getContext().getDimensionSet(), (String)this.exportHandleParam.getInfo().getContext().getFormSchemeKey());
        List dimCollectionList = dimCollection.getDimensionCombinations();
        HashSet<String> unitKeys = new HashSet<String>();
        EntityViewData dwEntity1 = this.jtableParamService.getDwEntity(this.exportHandleParam.getInfo().getContext().getFormSchemeKey());
        for (DimensionCombination fixedDimensionValues : dimCollectionList) {
            unitKeys.add(fixedDimensionValues.getValue(dwEntity1.getDimensionName()).toString());
        }
        allResultDimension = allResultDimension.stream().filter(item -> unitKeys.contains(((DimensionValue)item.get(dwEntity1.getDimensionName())).getValue())).collect(Collectors.toList());
        List<String> listEntity = this.exportHandleParam.getListEntity();
        List<String> formKeys = this.exportHandleParam.getFormKeys();
        List<String> repeatCompanyNameList = this.exportHandleParam.getRepeatCompanyNameList();
        BatchExportInfo info = this.exportHandleParam.getInfo();
        List<String> splitAllForms = this.exportHandleCurrParam.getSplitAllForms();
        JtableContext jtableContext = info.getContext();
        double index = 0.0;
        ArrayList exportList = new ArrayList();
        HashMap<String, String> infoMap = new HashMap<String, String>();
        INvwaSystemOptionService iNvwaSystemOptionService = (INvwaSystemOptionService)BeanUtil.getBean(INvwaSystemOptionService.class);
        String value = iNvwaSystemOptionService.get("nr-data-entry-export", "SIMPLIFY_EXPORT_FILE_HIERARCHY");
        boolean simplifyExportFileHierarchy = "1".equals(value);
        if (info.getRuleSettings() != null) {
            simplifyExportFileHierarchy = info.getRuleSettings().isSimplifyExportFileHierarchy();
        }
        HashMap menuMap = new HashMap();
        if (simplifyExportFileHierarchy) {
            for (Map<String, Object> map : allResultDimension) {
                Set<String> keySet = map.keySet();
                for (String key : keySet) {
                    infoMap.put(((DimensionValue)map.get(key)).getValue(), key);
                }
            }
        }
        Map mergedDimension = DimensionValueSetUtil.mergeDimension(allResultDimension);
        ExportUtil.handleMdCurDim(mergedDimension, info.getContext().getDimensionSet());
        DimensionValue dwDimValue = (DimensionValue)mergedDimension.get(companyType);
        dwDimValue.setValue(String.join((CharSequence)";", companyList));
        DimensionCollectionUtil dimensionCollectionUtil = (DimensionCollectionUtil)BeanUtil.getBean(DimensionCollectionUtil.class);
        DimensionCollection dimensionCollection = dimensionCollectionUtil.getDimensionCollection(mergedDimension, jtableContext.getFormSchemeKey());
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        List formDefines = CollectionUtils.isEmpty(this.exportHandleParam.getFormKeys()) ? runTimeViewController.queryAllFormDefinesByFormScheme(jtableContext.getFormSchemeKey()) : runTimeViewController.queryFormsById(this.exportHandleParam.getFormKeys());
        List expForms = formDefines.stream().filter(o -> o != null && o.getFormType() != FormType.FORM_TYPE_ANALYSISREPORT).map(IBaseMetaItem::getKey).collect(Collectors.toList());
        BatchExpPar batchExpPar = new BatchExpPar();
        batchExpPar.setForms(expForms);
        batchExpPar.setFormSchemeKey(jtableContext.getFormSchemeKey());
        batchExpPar.setDimensionCollection(dimensionCollection);
        batchExpPar.setRule(this.rule);
        ExportOps exportOps = new ExportOps();
        exportOps.setEmptyForm(info.isExportEmptyTable());
        exportOps.setExpExcelDirSheet(info.isExpExcelDirSheet());
        exportOps.setEt(info.isExportETFile());
        exportOps.setExpCellBColor(info.isCellBackGround());
        exportOps.setExp0Form(info.isExportZero());
        if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)info.getContext().getMillennialDecimal())) {
            exportOps.setDisplayDecimalPlaces(Integer.valueOf(Integer.parseInt(info.getContext().getMillennialDecimal())));
        }
        if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)(millennial = jtableContext.getMillennial())) && !millennial.equals("0")) {
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
        exportOps.setFormulaSchemeKey(info.getContext().getFormulaSchemeKey());
        exportOps.setPrintSchemeKey(info.getPrintSchemeKey());
        exportOps.setLabel(info.isTableTab());
        exportOps.setSumData(info.isSumData());
        if (info.isArithmeticBackground()) {
            CustomCellStyleProviderImpl customCellStyleProvider = new CustomCellStyleProviderImpl(info.getContext());
            batchExpPar.setCustomCellStyleProvider((CustomCellStyleProvider)customCellStyleProvider);
        }
        if (info.getRuleSettings() != null) {
            ExportRuleSettings ruleSettings = info.getRuleSettings();
            TitleShowSetting titleShowSetting = new TitleShowSetting();
            exportOps.setTitleShowSetting(titleShowSetting);
            if (ruleSettings.getSheetName() != null) {
                for (String sheetName : ruleSettings.getSheetName()) {
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
            if (ruleSettings.getExcelName() != null) {
                for (String excelName : ruleSettings.getExcelName()) {
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
            if (ruleSettings.getSeparatorCode() != null) {
                if (ruleSettings.getSeparatorCode().equals(SplitChar.SPACE.getKey())) {
                    titleShowSetting.setSplitChar(SplitChar.SPACE);
                } else if (ruleSettings.getSeparatorCode().equals(SplitChar.AND.getKey())) {
                    titleShowSetting.setSplitChar(SplitChar.AND);
                } else if (ruleSettings.getSeparatorCode().equals(SplitChar.UNDERLINE.getKey())) {
                    titleShowSetting.setSplitChar(SplitChar.UNDERLINE);
                }
            }
            titleShowSetting.setSimplifyExpFileHierarchy(Boolean.valueOf(ruleSettings.isSimplifyExportFileHierarchy()));
        } else {
            TitleShowSetting titleShowSetting = new TitleShowSetting();
            titleShowSetting.setSimplifyExpFileHierarchy(Boolean.valueOf(simplifyExportFileHierarchy));
            exportOps.setTitleShowSetting(titleShowSetting);
        }
        Map<String, List<RowFilter>> rowFilterMap = ExportUtil.getRowFilterMap(info.getRegionFilterListInfo());
        exportOps.setRowFilter(rowFilterMap);
        batchExpPar.setOps(exportOps);
        BatchExportOps batchExportOps = new BatchExportOps();
        batchExportOps.setExpAppendedFile(info.isExportEnclosure());
        batchExpPar.setBatchExportOps(batchExportOps);
        CommonParams commonParams = new CommonParams();
        commonParams.setMonitor(asyncTaskMonitor);
        try {
            ExpExcelSyncResult expExcelSyncResult = this.dataExportService.expExcelSync(batchExpPar, this.expPath, commonParams);
            if (asyncTaskMonitor.isCancel()) {
                asyncTaskMonitor.canceled("stop_execute", (Object)"stop_execute");
                return;
            }
            if (!expExcelSyncResult.isNoDataNoExp()) {
                this.datas.add(new BatchExportData());
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private String formatEncloName(List<String> fileNameList, String fileName, String extension, int index) {
        String nameInfo = fileName.replace(extension, "");
        String fileNameInfo = nameInfo + "(" + index + ")" + extension;
        if (fileNameList.contains(fileNameInfo)) {
            fileName = nameInfo + extension;
            fileNameInfo = this.formatEncloName(fileNameList, fileName, extension, index + 1);
        }
        return fileNameInfo;
    }

    private boolean entryContains(List<Map<String, DimensionValue>> exportList, Map<String, DimensionValue> mapNoUnit) {
        if (exportList.size() == 0) {
            return false;
        }
        block0: for (Map<String, DimensionValue> haveOne : exportList) {
            for (Map.Entry<String, DimensionValue> entry : mapNoUnit.entrySet()) {
                if (haveOne.get(entry.getKey()).getValue().equals(entry.getValue().getValue())) continue;
                continue block0;
            }
            return true;
        }
        return false;
    }
}

