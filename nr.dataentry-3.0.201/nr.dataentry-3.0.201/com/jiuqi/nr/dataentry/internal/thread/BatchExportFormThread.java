/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.bi.security.SecurityContentException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.office.excel2.CacheSXSSFWorkbook
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.FormData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo
 *  com.jiuqi.nr.jtable.params.output.EntityData
 *  com.jiuqi.nr.jtable.params.output.SecretLevelInfo
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.service.ISecretLevelService
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.dataentry.internal.thread;

import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.bi.security.SecurityContentException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.office.excel2.CacheSXSSFWorkbook;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.BatchExportData;
import com.jiuqi.nr.dataentry.bean.BatchExportInfo;
import com.jiuqi.nr.dataentry.bean.BatchExportParam;
import com.jiuqi.nr.dataentry.bean.ExportHandleCurrParam;
import com.jiuqi.nr.dataentry.bean.ExportHandleParam;
import com.jiuqi.nr.dataentry.export.IReportExport;
import com.jiuqi.nr.dataentry.internal.service.BatchExportExcelServiceImpl;
import com.jiuqi.nr.dataentry.paramInfo.FormReadWriteAccessData;
import com.jiuqi.nr.dataentry.provider.DimensionValueProvider;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessProvider;
import com.jiuqi.nr.dataentry.service.ExportExcelNameService;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.dataentry.util.DataEntryUtil;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.params.output.SecretLevelInfo;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.service.ISecretLevelService;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BatchExportFormThread
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

    public BatchExportFormThread(ExportHandleParam exportHandleParam, double allSize, List<BatchExportData> datas, ExportHandleCurrParam exportHandleCurrParam, double numOfIndex) {
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
    }

    @Override
    public void run() {
        CountDownLatch latch = this.exportHandleParam.getLatch();
        try {
            NpContextHolder.setContext((NpContext)this.exportHandleParam.getNpContext());
            this.ExportExcelByForm();
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
     * WARNING - void declaration
     */
    public void ExportExcelByForm() throws IOException {
        double firstLevel = 0.05;
        String companyType = this.exportHandleParam.getCompanyType();
        String dateType = this.exportHandleParam.getDateType();
        List<EntityViewData> entityList = this.exportHandleParam.getEntityList();
        String dateDir = this.exportHandleParam.getDateDir();
        try {
            PathUtils.validatePathManipulation((String)dateDir);
        }
        catch (SecurityContentException e) {
            throw new RuntimeException(e);
        }
        List<String> companyList = this.exportHandleParam.getCompanyList();
        AsyncTaskMonitor asyncTaskMonitor = this.exportHandleParam.getAsyncTaskMonitor();
        List<Map<String, DimensionValue>> allResultDimension = this.exportHandleParam.getDimensionInfoBuild().getList();
        List<String> listEntity = this.exportHandleParam.getListEntity();
        List<String> formKeys = this.exportHandleParam.getFormKeys();
        List<String> repeatCompanyNameList = this.exportHandleParam.getRepeatCompanyNameList();
        BatchExportInfo info = this.exportHandleParam.getInfo();
        List<String> splitAllForms = this.exportHandleCurrParam.getSplitAllForms();
        JtableContext jtableContext = info.getContext();
        double index = 0.0;
        ArrayList<Map<String, DimensionValue>> exportList = new ArrayList<Map<String, DimensionValue>>();
        HashMap<String, String> infoMap = new HashMap<String, String>();
        INvwaSystemOptionService iNvwaSystemOptionService = (INvwaSystemOptionService)BeanUtil.getBean(INvwaSystemOptionService.class);
        String value = iNvwaSystemOptionService.get("nr-data-entry-export", "SIMPLIFY_EXPORT_FILE_HIERARCHY");
        boolean simplifyExportFileHierarchy = "1".equals(value);
        HashMap<String, List<String>> menuMap = new HashMap<String, List<String>>();
        if (simplifyExportFileHierarchy) {
            for (Map<String, DimensionValue> map : allResultDimension) {
                Set<String> keySet = map.keySet();
                for (String key : keySet) {
                    infoMap.put(map.get(key).getValue(), key);
                }
            }
        }
        for (Map<String, DimensionValue> mapNoUnit : allResultDimension) {
            DimensionValue dimensionValue = mapNoUnit.get(companyType);
            if (dimensionValue == null) continue;
            mapNoUnit.remove(companyType);
            boolean res = this.entryContains(exportList, mapNoUnit);
            if (res) continue;
            exportList.add(mapNoUnit);
            ArrayList<String> fileNameList = new ArrayList<String>();
            block51: for (int i = 0; i < splitAllForms.size(); ++i) {
                ArrayList<String> fileGroupKeys = new ArrayList<String>();
                HashMap secretLevelItemMap = new HashMap();
                Integer fileSize = fileGroupKeys.size();
                try (CacheSXSSFWorkbook workbook = new CacheSXSSFWorkbook(2000);){
                    String fileName = "";
                    BatchExportParam param = new BatchExportParam();
                    JtableContext paramContext = new JtableContext(jtableContext);
                    param.setContext(paramContext);
                    param.setLabel(info.isTableTab());
                    param.setBackground(info.isCellBackGround());
                    param.setArithmeticBackground(info.isArithmeticBackground());
                    param.setSumData(info.isSumData());
                    param.setExportEmptyTable(info.isExportEmptyTable());
                    param.setPrintSchemeKey(info.getPrintSchemeKey());
                    param.setRegionFilterListInfo(info.getRegionFilterListInfo());
                    FormData formDefine = this.jtableParamService.getReport(splitAllForms.get(i), null);
                    fileName = this.exportExcelNameService.compileNameInfo(splitAllForms.get(i), param.getContext(), "SHEET_NAME", false, param.getContext().getUnitViewKey());
                    String location = "";
                    boolean mapperUsed = false;
                    LinkedHashMap<String, String> illegleSheet = new LinkedHashMap<String, String>();
                    if (StringUtils.isNotEmpty((String)dateDir)) {
                        location = location + dateDir;
                    }
                    HashMap<String, DimensionValue> newDim = new HashMap<String, DimensionValue>();
                    newDim.putAll(mapNoUnit);
                    ArrayList<EntityData> entityReturnInfos = null;
                    HashMap tempMap = null;
                    int allSheetCount = companyList.size();
                    int entityIndex = -1;
                    ArrayList<Integer> deletes = new ArrayList<Integer>();
                    HashMap<String, Integer> sheetNameMap = new HashMap<String, Integer>();
                    for (String companyValue : companyList) {
                        ++entityIndex;
                        dimensionValue.setValue(companyValue);
                        newDim.put(companyType, dimensionValue);
                        paramContext.setDimensionSet(newDim);
                        ArrayList<String> errorDimensionList = new ArrayList<String>();
                        JtableContext jtableContextInfo = new JtableContext();
                        jtableContextInfo.setDimensionSet(newDim);
                        jtableContextInfo.setFormSchemeKey(jtableContext.getFormSchemeKey());
                        jtableContextInfo.setTaskKey(jtableContext.getTaskKey());
                        List<Map<String, DimensionValue>> splitDimensionValueList = this.dimensionValueProvider.splitDimensionValueList(jtableContextInfo, errorDimensionList, true);
                        if (splitDimensionValueList.isEmpty()) {
                            --allSheetCount;
                            --entityIndex;
                            continue;
                        }
                        ArrayList<String> tempFormKeys = new ArrayList<String>();
                        tempFormKeys.add(splitAllForms.get(i));
                        FormReadWriteAccessData oneFormReadWrite = new FormReadWriteAccessData(paramContext, Consts.FormAccessLevel.FORM_READ, (List<String>)tempFormKeys);
                        FormReadWriteAccessData result = this.readWriteAccessProvider.getAccessForms(oneFormReadWrite, this.exportHandleParam.getReadWriteAccessCacheManager());
                        List<String> formKeysList = result.getFormKeys();
                        ArrayList<String> haveSeeList = new ArrayList<String>();
                        for (String string : formKeysList) {
                            IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
                            FormDefine formDefineOne = runTimeViewController.queryFormById(string);
                            if (formDefineOne.getFormType() == FormType.FORM_TYPE_ANALYSISREPORT) continue;
                            haveSeeList.add(string);
                        }
                        if (formKeys.isEmpty()) {
                            if (!haveSeeList.contains(splitAllForms.get(i))) {
                                double percent = index / this.allSize * 0.9;
                                asyncTaskMonitor.progressAndMessage((firstLevel + percent) * this.numOfIndex * this.numOfIndex, "");
                                --allSheetCount;
                                --entityIndex;
                                continue;
                            }
                        } else {
                            if (!haveSeeList.contains(splitAllForms.get(i))) {
                                double percent = index / this.allSize * 0.9;
                                asyncTaskMonitor.progressAndMessage((firstLevel + percent) * this.numOfIndex * this.numOfIndex, "");
                                --allSheetCount;
                                --entityIndex;
                                continue;
                            }
                            if (!formKeys.contains(splitAllForms.get(i))) {
                                double percent = index / this.allSize * 0.9;
                                asyncTaskMonitor.progressAndMessage((firstLevel + percent) * this.numOfIndex * this.numOfIndex, "");
                                --allSheetCount;
                                continue block51;
                            }
                        }
                        paramContext.setFormKey(splitAllForms.get(i));
                        index += 1.0;
                        ArrayList<String> entityKeys = new ArrayList<String>();
                        block54: for (Map.Entry entityFind : newDim.entrySet()) {
                            for (int t = 0; t < entityList.size(); ++t) {
                                if (!((String)entityFind.getKey()).equals(entityList.get(t).getDimensionName())) continue;
                                entityKeys.add(entityList.get(t).getKey());
                                continue block54;
                            }
                        }
                        if (entityKeys == null || entityKeys.size() <= 0) continue;
                        boolean bl = false;
                        entityReturnInfos = new ArrayList<EntityData>();
                        for (int x = 0; x < entityKeys.size(); ++x) {
                            entityReturnInfos.add(null);
                        }
                        tempMap = new HashMap();
                        for (Map.Entry entry : newDim.entrySet()) {
                            void var60_82;
                            JtableContext entityContext = new JtableContext(jtableContext);
                            entityContext.setFormKey(splitAllForms.get(i));
                            EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
                            if (listEntity.indexOf("ADJUST") == -1 && ((String)entry.getKey()).equals("ADJUST")) continue;
                            entityQueryByKeyInfo.setEntityViewKey((String)entityKeys.get((int)var60_82));
                            entityQueryByKeyInfo.setEntityKey(((DimensionValue)entry.getValue()).getValue());
                            entityQueryByKeyInfo.setContext(entityContext);
                            EntityData entrunInfo = this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo).getEntity();
                            int indexYuan = listEntity.indexOf(entityKeys.get((int)var60_82));
                            entityReturnInfos.set(indexYuan, entrunInfo);
                            ++var60_82;
                            tempMap.put(indexYuan, entry.getKey());
                        }
                        String sheetName = "";
                        for (int j = 0; j < entityReturnInfos.size(); ++j) {
                            String type = (String)tempMap.get(j);
                            if (null == entityReturnInfos.get(j) || !companyType.equals(type)) continue;
                            sheetName = this.exportExcelNameService.compileNameInfo("", paramContext, "EXCEL_NAME", false, paramContext.getUnitViewKey());
                            if (StringUtils.isEmpty((String)sheetName)) {
                                sheetName = ((EntityData)entityReturnInfos.get(j)).getRowCaption();
                            }
                            if (sheetName.length() > 31) {
                                sheetName = sheetName.substring(0, 31);
                            }
                            if (sheetNameMap.get(sheetName) == null) {
                                sheetNameMap.put(sheetName, 0);
                                continue;
                            }
                            int count = (Integer)sheetNameMap.get(sheetName) + 1;
                            sheetNameMap.put(sheetName, count);
                            String countStr = "(" + count + ")";
                            int countSize = countStr.length();
                            int sheetNameSize = sheetName.length();
                            String sheetNameAfterSub = "";
                            if (sheetNameSize + countSize > 31) {
                                sheetNameAfterSub = sheetName.substring(0, 31 - countSize);
                            }
                            sheetName = !sheetNameAfterSub.equals("") ? sheetNameAfterSub + countStr : sheetName + countStr;
                        }
                        String formName = sheetName;
                        String remakeName = DataEntryUtil.sheetNameVolidate(sheetName);
                        if (!remakeName.equals(sheetName)) {
                            mapperUsed = true;
                            sheetName = remakeName;
                            illegleSheet.put(sheetName, formName);
                        }
                        param.setSheetName(sheetName);
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
                        boolean isNotEmpty = this.reportExportService.export(param, (SXSSFWorkbook)workbook, null, fileGroupKeys, menuMap);
                        if (!fileSize.equals(fileGroupKeys.size())) {
                            fileSize = fileGroupKeys.size();
                            secretLevelItemMap.put(fileGroupKeys.get(fileSize - 1), info.getThisSecretLevel());
                        }
                        if (!isNotEmpty) {
                            SXSSFSheet sheet = workbook.getSheet("HIDDENSHEETNAME");
                            if (sheet != null) {
                                deletes.add(entityIndex + 1);
                            } else {
                                deletes.add(entityIndex);
                            }
                        }
                        double percent = index / this.allSize * 0.7;
                        if (i % 10 != 0) continue;
                        asyncTaskMonitor.progressAndMessage((firstLevel + percent) * this.numOfIndex * this.numOfIndex, "");
                    }
                }
                catch (Exception e) {
                    log.error("\u5bfc\u51fa\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    try {
                        throw e;
                    }
                    catch (SecurityContentException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
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
        int havalength = exportList.size();
        block0: for (int index = 0; index < havalength; ++index) {
            Map<String, DimensionValue> haveOne = exportList.get(index);
            for (Map.Entry<String, DimensionValue> entry : mapNoUnit.entrySet()) {
                if (haveOne.get(entry.getKey()).getValue().equals(entry.getValue().getValue())) continue;
                continue block0;
            }
            return true;
        }
        return false;
    }
}

