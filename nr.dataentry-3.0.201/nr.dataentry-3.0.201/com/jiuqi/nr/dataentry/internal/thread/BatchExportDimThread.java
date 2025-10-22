/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.TempAssistantTable
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.office.excel2.CacheSXSSFWorkbook
 *  com.jiuqi.nr.attachment.input.CommonParamsDTO
 *  com.jiuqi.nr.attachment.message.FileInfo
 *  com.jiuqi.nr.attachment.service.FileOperationService
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.datascheme.api.AdjustPeriod
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.io.dataset.impl.RegionDataSet
 *  com.jiuqi.nr.io.params.base.RegionData
 *  com.jiuqi.nr.io.params.base.TableContext
 *  com.jiuqi.nr.io.params.input.ExpViewFields
 *  com.jiuqi.nr.io.params.input.OptTypes
 *  com.jiuqi.nr.io.params.output.ExportFieldDefine
 *  com.jiuqi.nr.jtable.params.base.FormData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.base.RegionData
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo
 *  com.jiuqi.nr.jtable.params.output.EntityData
 *  com.jiuqi.nr.jtable.params.output.SecretLevelInfo
 *  com.jiuqi.nr.jtable.params.output.SecretLevelItem
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.service.ISecretLevelService
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.dataentry.internal.thread;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.TempAssistantTable;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.office.excel2.CacheSXSSFWorkbook;
import com.jiuqi.nr.attachment.input.CommonParamsDTO;
import com.jiuqi.nr.attachment.message.FileInfo;
import com.jiuqi.nr.attachment.service.FileOperationService;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.BatchExportData;
import com.jiuqi.nr.dataentry.bean.BatchExportInfo;
import com.jiuqi.nr.dataentry.bean.BatchExportParam;
import com.jiuqi.nr.dataentry.bean.ExportData;
import com.jiuqi.nr.dataentry.bean.ExportHandleCurrParam;
import com.jiuqi.nr.dataentry.bean.ExportHandleParam;
import com.jiuqi.nr.dataentry.export.IReportExport;
import com.jiuqi.nr.dataentry.internal.service.BatchExportExcelServiceImpl;
import com.jiuqi.nr.dataentry.model.BatchDimensionParam;
import com.jiuqi.nr.dataentry.paramInfo.FormReadWriteAccessData;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessProvider;
import com.jiuqi.nr.dataentry.service.ExportExcelNameService;
import com.jiuqi.nr.dataentry.util.BatchExportConsts;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.dataentry.util.DataEntryUtil;
import com.jiuqi.nr.dataentry.util.ExcelErrorUtil;
import com.jiuqi.nr.datascheme.api.AdjustPeriod;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.io.dataset.impl.RegionDataSet;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.io.params.input.ExpViewFields;
import com.jiuqi.nr.io.params.input.OptTypes;
import com.jiuqi.nr.io.params.output.ExportFieldDefine;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.params.output.SecretLevelInfo;
import com.jiuqi.nr.jtable.params.output.SecretLevelItem;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.service.ISecretLevelService;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import javax.sql.DataSource;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class BatchExportDimThread
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
    private static final Logger log = LoggerFactory.getLogger(BatchExportExcelServiceImpl.class);
    private double numbOfIndex;

    public BatchExportDimThread(ExportHandleParam exportHandleParam, List<BatchExportData> datas, ExportHandleCurrParam exportHandleCurrParam, double numbOfIndex) {
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
            log.error(e.getMessage(), e);
        }
        finally {
            latch.countDown();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
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
            JtableContext jtableContext = info.getContext();
            String companyType = this.exportHandleParam.getCompanyType();
            String dateType = this.exportHandleParam.getDateType();
            String dateDir = this.exportHandleParam.getDateDir();
            PathUtils.validatePathManipulation((String)dateDir);
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
            TempAssistantTable tempTable = null;
            HashMap<String, List<String>> menuMap = new HashMap<String, List<String>>();
            try (Connection connection = this.dataSource.getConnection();){
                IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
                if (listEntity.size() > DataEngineUtil.getMaxInSize((IDatabase)database)) {
                    tempTable = new TempAssistantTable(listEntity, 6);
                    tempTable.createTempTable(connection);
                    tempTable.insertIntoTempTable(connection);
                }
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            HashMap cacheRegionForm = new HashMap();
            HashMap<String, String> infoMap = new HashMap<String, String>();
            if (simplifyExportFileHierarchy) {
                for (Map map : currentDimension) {
                    Set keySet = map.keySet();
                    Iterator iterator = keySet.iterator();
                    while (iterator.hasNext()) {
                        String key = (String)iterator.next();
                        infoMap.put(((DimensionValue)map.get(key)).getValue(), key);
                    }
                }
            }
            ExecutorContext executorContext = this.getExecutorContext(jtableContext.getFormSchemeKey());
            ArrayList<String> arrayList = new ArrayList<String>();
            for (Map map : currentDimension) {
                int denominator;
                int incrementAndGet = count.incrementAndGet();
                int n = denominator = allResultDimension.size() / 10 == 0 ? 1 : allResultDimension.size() / 10;
                if (incrementAndGet % denominator == 0) {
                    double pro = (double)incrementAndGet * 1.0 / (double)allResultDimension.size();
                    String proString = String.format("%.2f", pro).toString();
                    log.info("\u5f53\u524d\u6267\u884c\u5bfc\u51fa\u5355\u4f4d\u8fdb\u5ea6" + proString);
                }
                ArrayList<String> fileGroupKeys = new ArrayList<String>();
                CacheSXSSFWorkbook workbook = new CacheSXSSFWorkbook(2000);
                try {
                    ByteArrayOutputStream os = new ByteArrayOutputStream(0xA00000);
                    Throwable throwable = null;
                    try {
                        IFormSchemeService iFormSchemeService;
                        AdjustPeriod adjustPeriod;
                        boolean mapperUsed = false;
                        String fileName = "";
                        LinkedHashMap<String, String> illegleSheet = new LinkedHashMap<String, String>();
                        JtableContext dimensionContext = new JtableContext(jtableContext);
                        dimensionContext.setDimensionSet(map);
                        FormReadWriteAccessData formReadWriteAccessData = new FormReadWriteAccessData(dimensionContext, Consts.FormAccessLevel.FORM_READ, this.exportHandleParam.getFormKeys());
                        FormReadWriteAccessData accessForms = this.readWriteAccessProvider.getAccessForms(formReadWriteAccessData, this.exportHandleParam.getReadWriteAccessCacheManager());
                        List<String> dimensionValueformKeys = accessForms.getFormKeys();
                        ArrayList<String> dimensionValueformKeysAfterFormat = new ArrayList<String>();
                        for (String formKey : dimensionValueformKeys) {
                            IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
                            FormDefine formDefineOne = runTimeViewController.queryFormById(formKey);
                            if (formDefineOne.getFormType() == FormType.FORM_TYPE_ANALYSISREPORT) continue;
                            dimensionValueformKeysAfterFormat.add(formKey);
                        }
                        if (!formKeys.isEmpty()) {
                            Iterator its = dimensionValueformKeysAfterFormat.iterator();
                            while (its.hasNext()) {
                                String formKey;
                                formKey = (String)its.next();
                                if (formKeys.contains(formKey)) continue;
                                its.remove();
                            }
                        }
                        double allSize = (double)currentDimension.size() * (double)dimensionValueformKeysAfterFormat.size();
                        if (allSizePre > 0.0) {
                            weight = allSize / allSizePre;
                            index *= weight;
                            allSizePre = allSize;
                        } else {
                            allSizePre = allSize;
                        }
                        ArrayList<Integer> deletes = new ArrayList<Integer>();
                        HashMap<String, String> sheetCodeNameMap = new HashMap<String, String>();
                        for (int i = 0; i < dimensionValueformKeysAfterFormat.size(); ++i) {
                            try {
                                index += 1.0;
                                BatchExportParam param = new BatchExportParam();
                                JtableContext paramContext = new JtableContext(jtableContext);
                                paramContext.setDimensionSet(map);
                                paramContext.setFormKey((String)dimensionValueformKeysAfterFormat.get(i));
                                param.setContext(paramContext);
                                param.setLabel(info.isTableTab());
                                param.setBackground(info.isCellBackGround());
                                param.setArithmeticBackground(info.isArithmeticBackground());
                                param.setSumData(info.isSumData());
                                param.setExportEmptyTable(info.isExportEmptyTable());
                                param.setPrintSchemeKey(info.getPrintSchemeKey());
                                param.setRegionFilterListInfo(info.getRegionFilterListInfo());
                                FormData formDefine = this.jtableParamService.getReport(paramContext.getFormKey(), null);
                                String sheetName = "";
                                String formName = this.exportExcelNameService.compileNameInfo(paramContext.getFormKey(), paramContext, "SHEET_NAME", false, param.getContext().getUnitViewKey());
                                if (DataEntryUtil.sheetNameVolidate(formName).equals(formName)) {
                                    sheetName = formName;
                                } else {
                                    mapperUsed = true;
                                    sheetName = DataEntryUtil.sheetNameVolidate(formName);
                                    illegleSheet.put(sheetName, formName);
                                }
                                if (sheetCodeNameMap.keySet().contains(sheetName) && !sheetName.contains(formDefine.getCode())) {
                                    int indexOfSheetName = workbook.getSheetIndex(sheetName);
                                    String formCode = (String)sheetCodeNameMap.get(sheetName);
                                    String sheetNameNew = sheetName + sysSeparator + formCode;
                                    workbook.setSheetName(indexOfSheetName, sheetNameNew);
                                    sheetCodeNameMap.put(sheetNameNew, formCode);
                                    sheetName = sheetName + sysSeparator + formDefine.getCode();
                                }
                                sheetCodeNameMap.put(sheetName, formDefine.getCode());
                                param.setSheetName(sheetName);
                                IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
                                IDataDefinitionRuntimeController iDataDefinitionRuntimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
                                List dataLinkDefineList = runTimeViewController.getAllLinksInForm(paramContext.getFormKey());
                                List fieldKeys = runTimeViewController.getFieldKeysInForm(paramContext.getFormKey());
                                List fieldDefineList = iDataDefinitionRuntimeController.queryFieldDefines((Collection)fieldKeys);
                                boolean isNotEmpty = true;
                                ISecretLevelService iSecretLevelService = this.exportHandleParam.getiSecretLevelService();
                                boolean secretLevelEnable = iSecretLevelService.secretLevelEnable(param.getContext().getTaskKey());
                                if (secretLevelEnable) {
                                    SecretLevelInfo secretLevelInfo = iSecretLevelService.getSecretLevel(param.getContext());
                                    if (info.getSecretLevel() != null && secretLevelInfo != null && secretLevelInfo.getSecretLevelItem().getTitle() != null) {
                                        info.setThisSecretLevel(secretLevelInfo.getSecretLevelItem().getTitle());
                                        if (iSecretLevelService.compareSercetLevel(secretLevelInfo.getSecretLevelItem(), iSecretLevelService.getSecretLevelItem(info.getSecretLevel()))) {
                                            info.setSecretLevel(secretLevelInfo.getSecretLevelItem().getTitle());
                                        }
                                    } else if (secretLevelInfo != null && secretLevelInfo.getSecretLevelItem().getTitle() != null) {
                                        info.setSecretLevel(secretLevelInfo.getSecretLevelItem().getTitle());
                                        info.setThisSecretLevel(secretLevelInfo.getSecretLevelItem().getTitle());
                                    }
                                }
                                if (!(isNotEmpty = dataLinkDefineList.size() != fieldDefineList.size() ? this.reportExportService.export(param, (SXSSFWorkbook)workbook, null, fileGroupKeys, menuMap) : this.reportExportService.export(param, (SXSSFWorkbook)workbook, null, fileGroupKeys, menuMap))) {
                                    deletes.add(i);
                                }
                                double percent = index / allSize * 0.7;
                                if (i % 10 != 0) continue;
                                asyncTaskMonitor.progressAndMessage((firstLevel + percent) * this.numbOfIndex * this.numbOfIndex, "");
                                continue;
                            }
                            catch (Exception e) {
                                log.error("\u5355\u4f4d\u5bfc\u51fa\u51fa\u9519\uff0c\u5355\u4f4d{}\uff0c\u9519\u8bef\u4fe1\u606f{}", dimensionValueformKeysAfterFormat.get(i), e.getMessage(), e);
                            }
                        }
                        if (!info.isExportEmptyTable()) {
                            if (deletes.size() == dimensionValueformKeysAfterFormat.size()) continue;
                            if (deletes.size() > 0) {
                                String hiddenSheetName = "HIDDENSHEETNAME";
                                SXSSFSheet hiddenSheet = workbook.getSheet(hiddenSheetName);
                                if (hiddenSheet != null) {
                                    workbook.setSheetOrder(hiddenSheetName, workbook.getNumberOfSheets() - 1);
                                }
                                deletes.sort(Comparator.reverseOrder());
                                for (Integer delete : deletes) {
                                    SXSSFSheet sheetAt = workbook.getSheetAt(delete.intValue());
                                    illegleSheet.remove(sheetAt.getSheetName());
                                    workbook.removeSheetAt(delete.intValue());
                                }
                            }
                        }
                        if (mapperUsed && illegleSheet.size() > 0) {
                            ExcelErrorUtil.createMapper((SXSSFWorkbook)workbook, illegleSheet);
                        }
                        ArrayList<EntityData> entityReturnInfos = null;
                        HashMap tempMap = new HashMap();
                        if (entityKeys != null && entityKeys.size() > 0) {
                            int loc = 0;
                            entityReturnInfos = new ArrayList<EntityData>();
                            for (int x = 0; x < entityKeys.size(); ++x) {
                                entityReturnInfos.add(null);
                            }
                            for (Map.Entry entry : map.entrySet()) {
                                EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
                                JtableContext entityQueryContext = new JtableContext(jtableContext);
                                entityQueryContext.setFormKey("");
                                if (entityKeys.size() > loc) {
                                    entityQueryByKeyInfo.setEntityViewKey(entityKeys.get(loc));
                                    entityQueryByKeyInfo.setEntityKey(((DimensionValue)entry.getValue()).getValue());
                                    entityQueryByKeyInfo.setContext(entityQueryContext);
                                    EntityData entrunInfo = this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo).getEntity();
                                    int indexYuan = listEntity.indexOf(entityKeys.get(loc));
                                    entityReturnInfos.set(indexYuan, entrunInfo);
                                    tempMap.put(indexYuan, entry.getKey());
                                }
                                ++loc;
                            }
                        }
                        String location = "";
                        if (StringUtils.isNotEmpty((String)dateDir)) {
                            location = location + dateDir;
                        }
                        if (null == entityReturnInfos) {
                            entityReturnInfos = new ArrayList();
                        }
                        String sysExcelInfo = this.exportExcelNameService.getSysOptionOfExcelName();
                        String sysSeparatorCode = this.exportExcelNameService.getSysSeparator();
                        if (jtableContext.getDimensionSet().containsKey("ADJUST") && !((DimensionValue)jtableContext.getDimensionSet().get("ADJUST")).getValue().equals("0") && StringUtils.isNotEmpty((String)((DimensionValue)jtableContext.getDimensionSet().get("ADJUST")).getValue()) && StringUtils.isNotEmpty((String)(adjustPeriod = (iFormSchemeService = (IFormSchemeService)BeanUtil.getBean(IFormSchemeService.class)).queryAdjustPeriods(jtableContext.getFormSchemeKey(), ((DimensionValue)jtableContext.getDimensionSet().get("DATATIME")).getValue(), ((DimensionValue)jtableContext.getDimensionSet().get("ADJUST")).getValue())).getTitle())) {
                            location = location + adjustPeriod.getTitle() + BatchExportConsts.SEPARATOR;
                        }
                        block80: for (int i = 0; i < entityReturnInfos.size(); ++i) {
                            String type = (String)tempMap.get(i);
                            if (dateType.equals(type) || null == entityReturnInfos.get(i)) continue;
                            if (companyType.equals(type)) {
                                fileName = this.exportExcelNameService.compileNameInfo("", dimensionContext, "EXCEL_NAME", false, dimensionContext.getUnitViewKey());
                                String nameInfoS = iNvwaSystemOptionService.get("nr-data-entry-export", "EXCEL_NAME");
                                String[] nameArray = nameInfoS.replace("[", "").replace("]", "").replace("\"", "").split(",");
                                String[] fileNames = fileName.split(sysSeparatorCode);
                                for (int j = 0; j < fileNames.length; ++j) {
                                    if (!repeatCompanyNameList.contains(fileNames[j]) || Arrays.asList(nameArray).contains("1")) continue;
                                    fileName = fileName + sysSeparatorCode + ((EntityData)entityReturnInfos.get(i)).getCode();
                                    continue block80;
                                }
                                continue;
                            }
                            String title = ((EntityData)entityReturnInfos.get(i)).getTitle();
                            int strNum = 0;
                            if (simplifyExportFileHierarchy) {
                                String entityId = ((EntityData)entityReturnInfos.get(i)).getId();
                                String entityStr = (String)infoMap.get(entityId);
                                for (String str : infoMap.values()) {
                                    if (!entityStr.equals(str)) continue;
                                    ++strNum;
                                }
                            }
                            if (StringUtils.isEmpty((String)title)) continue;
                            if (strNum == 1) {
                                int tagIndex = location.lastIndexOf(BatchExportConsts.SEPARATOR);
                                String beforeLocation = location.substring(0, tagIndex);
                                location = beforeLocation + "_" + (String)title + BatchExportConsts.SEPARATOR;
                                continue;
                            }
                            location = location + (String)title + BatchExportConsts.SEPARATOR;
                        }
                        try {
                            workbook.write((OutputStream)os);
                        }
                        catch (Exception e) {
                            log.error(e.getMessage(), e);
                        }
                        finally {
                            workbook.dispose();
                        }
                        byte[] byteArray = os.toByteArray();
                        BatchExportData data = new BatchExportData();
                        data.setData(new ExportData(fileName, byteArray));
                        data.setLocation(location);
                        for (BatchExportData exportData : this.datas) {
                            if (!data.getData().getFileName().equals(exportData.getData().getFileName()) || !info.isMultiplePeriod()) continue;
                            data.getData().setFileName(data.getData().getFileName() + sysSeparatorCode + System.currentTimeMillis());
                        }
                        this.datas.add(data);
                        if (info.isExportEnclosure() && fileGroupKeys.size() > 0) {
                            String encloLocation = location;
                            for (String fileGroupKey : fileGroupKeys) {
                                IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
                                TaskDefine taskDefine = runTimeViewController.queryTaskDefine(info.getContext().getTaskKey());
                                CommonParamsDTO commonParamsDTO = new CommonParamsDTO();
                                commonParamsDTO.setDataSchemeKey(taskDefine.getDataScheme());
                                commonParamsDTO.setTaskKey(taskDefine.getKey());
                                FileOperationService fileOperationService = (FileOperationService)BeanUtil.getBean(FileOperationService.class);
                                ISecretLevelService secretLevelService = (ISecretLevelService)BeanUtil.getBean(ISecretLevelService.class);
                                boolean secretLevelEnable = secretLevelService.secretLevelEnable(info.getContext().getTaskKey());
                                SecretLevelItem secretLevel = secretLevelService.getSecretLevelItem(info.getThisSecretLevel());
                                List files = fileOperationService.getFileOrPicInfoByGroup(fileGroupKey, commonParamsDTO);
                                if (files == null || files.size() <= 0) continue;
                                for (FileInfo file : files) {
                                    String fileNameOfEnclosure = file.getName();
                                    String extension = file.getExtension();
                                    if (arrayList != null && arrayList.contains(fileNameOfEnclosure)) {
                                        fileNameOfEnclosure = this.formatEncloName(arrayList, fileNameOfEnclosure, extension, 1);
                                    }
                                    arrayList.add(fileNameOfEnclosure);
                                    boolean canAccess = true;
                                    String fileName1 = fileNameOfEnclosure;
                                    if (StringUtils.isNotEmpty((String)file.getSecretlevel()) && secretLevelEnable) {
                                        SecretLevelItem secretLevelItem = secretLevelService.getSecretLevelItem(file.getSecretlevel());
                                        canAccess = secretLevelService.compareSercetLevel(secretLevel, secretLevelItem) && secretLevelService.canAccess(secretLevelItem);
                                        fileName1 = fileNameOfEnclosure.substring(0, fileNameOfEnclosure.length() - extension.length()) + sysSeparatorCode + secretLevelService.getSecretLevelItem(file.getSecretlevel()).getTitle() + extension;
                                    }
                                    if (!canAccess) continue;
                                    byte[] databytes = fileOperationService.downloadFile(file.getKey(), commonParamsDTO);
                                    BatchExportData fileData = new BatchExportData();
                                    fileData.setData(new ExportData(fileName1, true, databytes));
                                    fileData.setLocation(encloLocation);
                                    this.datas.add(fileData);
                                }
                            }
                        }
                        if (this.datas.size() < 500 || !info.getExcelType().equals("zip")) continue;
                        String resultLocation = info.getLocation();
                        PathUtils.validatePathManipulation((String)resultLocation);
                        File file = new File(FilenameUtils.normalize(resultLocation));
                        if (!file.exists()) {
                            file.mkdirs();
                        }
                        for (BatchExportData exdata : this.datas) {
                            byte[] databytes = exdata.getData().getData();
                            if (null == databytes || databytes.length <= 0) continue;
                            String suffix = ".xlsx";
                            String filePath = exdata.getLocation() + exdata.getData().getFileName().replace("/", "-").replace("\\", "-").replace("*", "\u203b").replace("<", "").replace(">", "").replace("?", "") + suffix;
                            try {
                                FileOutputStream fileOutputStream = new FileOutputStream(FilenameUtils.normalize(resultLocation + BatchExportConsts.SEPARATOR + filePath));
                                Throwable throwable2 = null;
                                try {
                                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                                    Throwable throwable3 = null;
                                    try {
                                        PathUtils.validatePathManipulation((String)FilenameUtils.normalize(resultLocation + BatchExportConsts.SEPARATOR + filePath));
                                        File file1 = new File(FilenameUtils.normalize(resultLocation + BatchExportConsts.SEPARATOR + filePath));
                                        if (!file1.exists()) {
                                            file1.getParentFile().mkdirs();
                                            try {
                                                file1.createNewFile();
                                            }
                                            catch (IOException e) {
                                                log.error(e.getMessage(), e);
                                            }
                                        }
                                        bufferedOutputStream.write(exdata.getData().getData());
                                    }
                                    catch (Throwable throwable4) {
                                        throwable3 = throwable4;
                                        throw throwable4;
                                    }
                                    finally {
                                        if (bufferedOutputStream == null) continue;
                                        if (throwable3 != null) {
                                            try {
                                                bufferedOutputStream.close();
                                            }
                                            catch (Throwable throwable5) {
                                                throwable3.addSuppressed(throwable5);
                                            }
                                            continue;
                                        }
                                        bufferedOutputStream.close();
                                    }
                                }
                                catch (Throwable throwable6) {
                                    throwable2 = throwable6;
                                    throw throwable6;
                                }
                                finally {
                                    if (fileOutputStream == null) continue;
                                    if (throwable2 != null) {
                                        try {
                                            fileOutputStream.close();
                                        }
                                        catch (Throwable throwable7) {
                                            throwable2.addSuppressed(throwable7);
                                        }
                                        continue;
                                    }
                                    fileOutputStream.close();
                                }
                            }
                            catch (IOException e) {
                                log.error(e.getMessage(), e);
                            }
                        }
                        this.datas.clear();
                    }
                    catch (Throwable throwable8) {
                        throwable = throwable8;
                        throw throwable8;
                    }
                    finally {
                        if (os == null) continue;
                        if (throwable != null) {
                            try {
                                os.close();
                            }
                            catch (Throwable i) {
                                throwable.addSuppressed(i);
                            }
                            continue;
                        }
                        os.close();
                    }
                }
                catch (Exception e) {
                    log.error("\u5bfc\u51fa\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    throw e;
                }
                finally {
                    if (workbook == null) continue;
                    workbook.dispose();
                }
            }
            try {
                Throwable throwable = null;
                try (Connection connection2 = this.dataSource.getConnection();){
                    IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection2);
                    if (listEntity.size() > DataEngineUtil.getMaxInSize((IDatabase)database) && connection2 != null) {
                        tempTable.dropTempTable(connection2);
                    }
                }
                catch (Throwable throwable9) {
                    Throwable throwable10 = throwable9;
                    throw throwable9;
                }
            }
            catch (Exception e2) {
                log.error(e2.getMessage(), e2);
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
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

    private Map<String, Map<String, Object>> queryDatas(JtableContext paramContext, String companyType, TempAssistantTable tempTable, List<String> listEntity, ExecutorContext executorContext) {
        HashMap<String, Map<String, Object>> regionDatas = new HashMap<String, Map<String, Object>>();
        Map dimensionSet = paramContext.getDimensionSet();
        List<com.jiuqi.nr.io.params.base.RegionData> regions = this.getRegions(paramContext.getFormKey());
        for (com.jiuqi.nr.io.params.base.RegionData regionData : regions) {
            if (regionData.getRegionTop() != 1) continue;
            DimensionValueSet dimensionSets = new DimensionValueSet();
            if (null != dimensionSet) {
                for (String key : dimensionSet.keySet()) {
                    if (!"".equals(((DimensionValue)dimensionSet.get(key)).getValue()) && ((DimensionValue)dimensionSet.get(key)).getValue().contains(";")) {
                        dimensionSets.setValue(key, Arrays.asList(((DimensionValue)dimensionSet.get(key)).getValue().split(";")));
                        continue;
                    }
                    if ("".equals(((DimensionValue)dimensionSet.get(key)).getValue())) continue;
                    dimensionSets.setValue(key, (Object)((DimensionValue)dimensionSet.get(key)).getValue());
                }
            }
            TableContext context = new TableContext(paramContext.getTaskKey(), paramContext.getFormSchemeKey(), paramContext.getFormKey(), dimensionSets, OptTypes.FORM, ".txt");
            context.setExpEntryFields(ExpViewFields.KEY);
            if (tempTable != null) {
                context.setTempAssistantTable(companyType, tempTable);
            }
            context.setExpEnumFields(ExpViewFields.CODETITLE);
            RegionDataSet fixRegion = new RegionDataSet(context, regionData);
            List fieldDataList = fixRegion.getFieldDataList();
            while (fixRegion.hasNext()) {
                ArrayList next = (ArrayList)fixRegion.next();
                if (null == next || next.isEmpty()) continue;
                HashMap row = new HashMap();
                String rowKey = regionData.getKey();
                for (int k = 0; k < fieldDataList.size(); ++k) {
                    row.put(((ExportFieldDefine)fieldDataList.get(k)).getCode(), next.get(k));
                    if (!((ExportFieldDefine)fieldDataList.get(k)).getCode().contains("MDCODE") && !"ID".equals(((ExportFieldDefine)fieldDataList.get(k)).getCode()) && !companyType.replace("_ID", "").equals(((ExportFieldDefine)fieldDataList.get(k)).getCode())) continue;
                    rowKey = rowKey + next.get(k).toString();
                }
                regionDatas.put(rowKey, row);
            }
        }
        return regionDatas;
    }

    private List<com.jiuqi.nr.io.params.base.RegionData> getRegions(String formKey) {
        ArrayList<com.jiuqi.nr.io.params.base.RegionData> regions = new ArrayList<com.jiuqi.nr.io.params.base.RegionData>();
        List allRegionDefines = this.jtableParamService.getRegions(formKey);
        for (RegionData dataRegionDefine : allRegionDefines) {
            com.jiuqi.nr.io.params.base.RegionData regionData = new com.jiuqi.nr.io.params.base.RegionData();
            BeanUtils.copyProperties(dataRegionDefine, regionData);
            regions.add(regionData);
        }
        return regions;
    }

    protected void closeConnection(Connection connection) {
        if (connection != null) {
            DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
        }
    }

    protected Connection getConnection() {
        return DataSourceUtils.getConnection((DataSource)this.jdbcTemplate.getDataSource());
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

