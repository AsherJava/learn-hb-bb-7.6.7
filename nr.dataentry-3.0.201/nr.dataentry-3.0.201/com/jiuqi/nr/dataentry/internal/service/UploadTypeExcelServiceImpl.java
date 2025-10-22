/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.np.definition.internal.parser.NumberFormatParser
 *  com.jiuqi.np.graphics.Point
 *  com.jiuqi.np.i18n.helper.I18nHelper
 *  com.jiuqi.nr.common.constant.ErrorCode
 *  com.jiuqi.nr.common.importdata.ImportErrorDataInfo
 *  com.jiuqi.nr.common.importdata.ImportResultExcelFileObject
 *  com.jiuqi.nr.common.importdata.ImportResultRegionObject
 *  com.jiuqi.nr.common.importdata.ImportResultReportObject
 *  com.jiuqi.nr.common.importdata.ImportResultSheetObject
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.io.params.base.RegionData
 *  com.jiuqi.nr.io.params.base.TableContext
 *  com.jiuqi.nr.io.params.input.ExpViewFields
 *  com.jiuqi.nr.io.params.input.OptTypes
 *  com.jiuqi.nr.io.params.output.ExportFieldDefine
 *  com.jiuqi.nr.io.sb.dataset.impl.SBRegionDataSet
 *  com.jiuqi.nr.jtable.common.LinkType
 *  com.jiuqi.nr.jtable.dataset.IRegionImportDataSet
 *  com.jiuqi.nr.jtable.dataset.IReportImportDataSet
 *  com.jiuqi.nr.jtable.exception.JTableException
 *  com.jiuqi.nr.jtable.params.base.FormData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.base.LinkData
 *  com.jiuqi.nr.jtable.params.base.RegionData
 *  com.jiuqi.nr.jtable.params.base.RegionTab
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.service.IJtableResourceService
 *  com.jiuqi.nr.jtable.util.RegionSettingUtil
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.graphics.Point
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.np.definition.internal.parser.NumberFormatParser;
import com.jiuqi.np.graphics.Point;
import com.jiuqi.np.i18n.helper.I18nHelper;
import com.jiuqi.nr.common.constant.ErrorCode;
import com.jiuqi.nr.common.importdata.ImportErrorDataInfo;
import com.jiuqi.nr.common.importdata.ImportResultExcelFileObject;
import com.jiuqi.nr.common.importdata.ImportResultRegionObject;
import com.jiuqi.nr.common.importdata.ImportResultReportObject;
import com.jiuqi.nr.common.importdata.ImportResultSheetObject;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.SheetNameOfFormResultInfo;
import com.jiuqi.nr.dataentry.bean.UploadParam;
import com.jiuqi.nr.dataentry.excelUpload.RegionMatchInfo;
import com.jiuqi.nr.dataentry.excelUpload.ReportFeature;
import com.jiuqi.nr.dataentry.excelUpload.ReportLinkDataCache;
import com.jiuqi.nr.dataentry.excelUpload.ReportMatchResult;
import com.jiuqi.nr.dataentry.excelUpload.Sheet2GridAdapter;
import com.jiuqi.nr.dataentry.internal.service.ExportExcelNameServiceImpl;
import com.jiuqi.nr.dataentry.paramInfo.FormReadWriteAccessData;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessProvider;
import com.jiuqi.nr.dataentry.readwrite.impl.ReadWriteAccessCacheManager;
import com.jiuqi.nr.dataentry.service.ICheckImportData;
import com.jiuqi.nr.dataentry.service.IUploadExcelFilterRowService;
import com.jiuqi.nr.dataentry.service.IUploadTypeExcelService;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.dataentry.util.DataEntryUtil;
import com.jiuqi.nr.dataentry.util.ExcelImportUtil;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.io.params.input.ExpViewFields;
import com.jiuqi.nr.io.params.input.OptTypes;
import com.jiuqi.nr.io.params.output.ExportFieldDefine;
import com.jiuqi.nr.io.sb.dataset.impl.SBRegionDataSet;
import com.jiuqi.nr.jtable.common.LinkType;
import com.jiuqi.nr.jtable.dataset.IRegionImportDataSet;
import com.jiuqi.nr.jtable.dataset.IReportImportDataSet;
import com.jiuqi.nr.jtable.exception.JTableException;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.base.RegionTab;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.service.IJtableResourceService;
import com.jiuqi.nr.jtable.util.RegionSettingUtil;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UploadTypeExcelServiceImpl
implements IUploadTypeExcelService {
    private static final Logger logger = LoggerFactory.getLogger(UploadTypeExcelServiceImpl.class);
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IJtableResourceService jtableResourceService;
    @Autowired
    private ReadWriteAccessProvider readWriteAccessProvider;
    @Autowired
    private ExportExcelNameServiceImpl exportExcelNameService;
    @Autowired
    private INvwaSystemOptionService iNvwaSystemOptionService;
    @Autowired(required=false)
    private IUploadExcelFilterRowService uploadExcelFilterRowService;
    @Autowired(required=false)
    private List<ICheckImportData> checkImportDataList;
    @Autowired
    @Qualifier(value="nr")
    private I18nHelper i18nHelper;
    private String reportRes = "success";
    private String reportMessage = "message";
    private String JtableDataStr = "JtableData";
    private String[] chinaChars = new String[]{"\uff08", "\uff09", "(", ")"};

    @Override
    public ImportResultExcelFileObject upload(List<Sheet> sheetList, String excelName, List<UploadParam> params, AsyncTaskMonitor asyncTaskMonitor, double begin, double oneSpan, ReadWriteAccessCacheManager readWriteAccessCacheManager) {
        Map<String, String> sheetNameToCompanyNameMap = new HashMap<String, String>();
        for (Sheet sheet : sheetList) {
            if (!"(\u9875\u540d\u6620\u5c04\u8868)".equals(sheet.getSheetName())) continue;
            sheetNameToCompanyNameMap = DataEntryUtil.sheetNameToCompanyName(sheet);
            break;
        }
        return this.uploadSheet(sheetList, excelName, params, asyncTaskMonitor, begin, oneSpan, true, sheetNameToCompanyNameMap, readWriteAccessCacheManager);
    }

    @Override
    public ImportResultExcelFileObject upload(Workbook workbook, String fileName, UploadParam param, AsyncTaskMonitor asyncTaskMonitor, double begin, double span, ReadWriteAccessCacheManager readWriteAccessCacheManager) {
        Map<String, String> sheetNameToCompanyNameMap = null;
        int sheetCount = workbook.getNumberOfSheets();
        ArrayList<Sheet> sheetList = new ArrayList<Sheet>();
        ArrayList<UploadParam> params = new ArrayList<UploadParam>();
        for (int i = 0; i < sheetCount; ++i) {
            Sheet sheet = workbook.getSheetAt(i);
            sheetList.add(sheet);
            String sheetName = sheet.getSheetName();
            if ("(\u9875\u540d\u6620\u5c04\u8868)".equals(sheetName)) {
                sheetNameToCompanyNameMap = DataEntryUtil.sheetNameToCompanyName(sheet);
            }
            params.add(param);
        }
        return this.uploadSheet(sheetList, fileName, params, asyncTaskMonitor, begin, span, false, sheetNameToCompanyNameMap, readWriteAccessCacheManager);
    }

    private ImportResultExcelFileObject uploadSheet(List<Sheet> sheetList, String excelName, List<UploadParam> params, AsyncTaskMonitor asyncTaskMonitor, double begin, double oneSpan, boolean isExcelName, Map<String, String> sheetNameToCompanyNameMap, ReadWriteAccessCacheManager accessCacheManager) {
        ImportResultExcelFileObject resObject = new ImportResultExcelFileObject();
        resObject.setFileName(excelName);
        try {
            String[] splitTop = null;
            double tempSpan = oneSpan / (double)sheetList.size();
            if (isExcelName && (splitTop = excelName.contains(" ") ? excelName.split(" ") : (excelName.contains("&") ? excelName.split("&") : excelName.split("_"))).length < 2) {
                resObject.getFileError().setErrorCode(ErrorCode.FILEERROR);
                resObject.getFileError().setErrorInfo("excel\u540d\u79f0\uff1a" + excelName + "\u65e0\u6cd5\u89e3\u6790");
                resObject.setFileName(excelName);
                return resObject;
            }
            for (int i = 0; i < sheetList.size(); ++i) {
                Sheet sheet = sheetList.get(i);
                String sheetName = sheet.getSheetName().trim();
                if ("(\u9875\u540d\u6620\u5c04\u8868)".equals(sheetName) || sheetName.startsWith("(\u9519\u8bef\u4fe1\u606f\u6620\u5c04\u8868)") || "HIDDENSHEETNAME".equals(sheetName)) continue;
                if (null != sheetNameToCompanyNameMap && sheetNameToCompanyNameMap.containsKey(sheetName)) {
                    sheetName = sheetNameToCompanyNameMap.get(sheetName);
                }
                String[] split = null;
                String sysSeparator = "";
                if (isExcelName) {
                    split = splitTop;
                } else {
                    sysSeparator = this.getSysSeparator("SHEET_NAME");
                    split = sheetName.contains(" ") ? sheetName.split(" ") : (sheetName.contains("&") ? sheetName.split("&") : sheetName.split("_"));
                }
                SheetNameOfFormResultInfo sheetNameOfFormResultInfo = this.getFormDefineBySheetName(sheetName, sysSeparator, split, params.get(i).getFormSchemeKey(), params.get(i), accessCacheManager);
                if (!sheetNameOfFormResultInfo.isResultType()) {
                    ImportResultSheetObject sheetItem = new ImportResultSheetObject();
                    ImportResultReportObject importResultReportObject = new ImportResultReportObject();
                    importResultReportObject.getReportError().setErrorCode(ErrorCode.FILEERROR);
                    importResultReportObject.getReportError().setErrorInfo(StringUtils.isNotEmpty((String)sheetNameOfFormResultInfo.getNoFormInfo()) ? sheetNameOfFormResultInfo.getNoFormInfo() : "\u6ca1\u6709\u5728\u5bf9\u5e94\u7684\u62a5\u8868\u65b9\u6848\u4e2d\u627e\u5230" + sheetName + "\u5bf9\u5e94\u7684\u8868\u5355");
                    if (sheetNameOfFormResultInfo.getFormDefine() != null) {
                        importResultReportObject.setReportName(sheetNameOfFormResultInfo.getFormDefine().getTitle());
                    }
                    sheetItem.setImportResultReportObject(importResultReportObject);
                    sheetItem.setSheetName(sheetName);
                    resObject.getImportResultSheetObjectList().add(sheetItem);
                } else {
                    FormData formData;
                    ImportResultReportObject importResultReportObject;
                    FormDefine formDefine = sheetNameOfFormResultInfo.getFormDefine();
                    String formCode = formDefine.getFormCode();
                    String formName = formDefine.getTitle();
                    Map<String, Object> res = this.getReport(formDefine, sheetName, params.get(i).getFormSchemeKey(), params.get(i), accessCacheManager);
                    boolean success = (Boolean)res.get(this.reportRes);
                    if (!success) {
                        String message = (String)res.get(this.reportMessage);
                        ImportResultSheetObject sheetItem = new ImportResultSheetObject();
                        importResultReportObject = new ImportResultReportObject();
                        importResultReportObject.getReportError().setErrorCode(ErrorCode.REPORTERROR);
                        importResultReportObject.getReportError().setErrorInfo(message);
                        sheetItem.setImportResultReportObject(importResultReportObject);
                        sheetItem.setSheetName(sheetName);
                        resObject.getImportResultSheetObjectList().add(sheetItem);
                    } else {
                        formData = (FormData)res.get(this.JtableDataStr);
                        if (!StringUtils.isEmpty((String)formName)) {
                            String title = formData.getTitle();
                            for (int y = 0; y < this.chinaChars.length / 2; ++y) {
                                formName = formName.replaceAll(this.chinaChars[y], this.chinaChars[y + this.chinaChars.length / 2]);
                                title = title.replaceAll(this.chinaChars[y], this.chinaChars[y + this.chinaChars.length / 2]);
                            }
                            if (!title.trim().equals(formName.trim())) {
                                ImportResultSheetObject sheetItem = new ImportResultSheetObject();
                                ImportResultReportObject importResultReportObject2 = new ImportResultReportObject();
                                importResultReportObject2.getReportError().setErrorCode(ErrorCode.REPORTERROR);
                                importResultReportObject2.getReportError().setErrorInfo("\u6ca1\u6709\u5728\u5bf9\u5e94\u7684\u62a5\u8868\u65b9\u6848\u4e2d\u627e\u5230" + formCode + " " + formName + "\u5bf9\u5e94\u7684\u8868\u5355");
                                sheetItem.setImportResultReportObject(importResultReportObject2);
                                sheetItem.setSheetName(sheetName);
                                resObject.getImportResultSheetObjectList().add(sheetItem);
                                continue;
                            }
                        }
                        JtableContext jtableContext = new JtableContext();
                        jtableContext.setFormSchemeKey(params.get(i).getFormSchemeKey());
                        jtableContext.setFormKey(formData.getKey());
                        jtableContext.setDimensionSet(params.get(i).getDimensionSet());
                        try {
                            importResultReportObject = this.proceeReport(formData, sheetList.get(i), params.get(i));
                            if (null != importResultReportObject.getReportError().getErrorCode() || importResultReportObject.getImportResultRegionObjectList().size() > 0) {
                                ImportResultSheetObject importResultSheetObject = this.transformationError(excelName, importResultReportObject, sheetList.get(i), formCode + " " + formName);
                                resObject.getImportResultSheetObjectList().add(importResultSheetObject);
                            } else {
                                resObject.addRelationDimensions(params.get(i).getDimensionSet());
                            }
                        }
                        catch (Exception e) {
                            logger.error("\u5bfc\u5165\u5931\u8d25\u6709\u672a\u77e5\u5f02\u5e38\uff1a" + e.getMessage(), e);
                            ImportResultSheetObject sheetItem = new ImportResultSheetObject();
                            sheetItem.setSheetName(sheetName);
                            ImportResultReportObject reportItem = new ImportResultReportObject();
                            reportItem.getReportError().setErrorCode(ErrorCode.SYSTEMERROR);
                            reportItem.setReportName(formCode + " " + formName);
                            sheetItem.setImportResultReportObject(reportItem);
                            if (e instanceof JTableException) {
                                JTableException exception = (JTableException)e;
                                Object[] datas = exception.getDatas();
                                if (null != datas && datas.length > 0) {
                                    reportItem.getReportError().setErrorInfo(Arrays.toString(datas));
                                } else {
                                    reportItem.getReportError().setErrorInfo(exception.getMessage() + " " + exception.getErrorCode());
                                }
                            } else {
                                reportItem.getReportError().setErrorInfo(e.getMessage());
                            }
                            resObject.getImportResultSheetObjectList().add(sheetItem);
                        }
                    }
                    formData = this.jtableParamService.getReport(formDefine.getKey(), params.get(i).getFormSchemeKey());
                    if (FormType.FORM_TYPE_NEWFMDM.name().equals(formData.getFormType())) {
                        resObject.setFmdmed(true);
                    }
                }
                asyncTaskMonitor.progressAndMessage(begin + tempSpan * (double)(i + 1), "");
            }
            asyncTaskMonitor.progressAndMessage(begin + oneSpan, "");
            return resObject;
        }
        catch (Exception e) {
            logger.error(e.toString());
            resObject.getFileError().setErrorCode(ErrorCode.SYSTEMERROR);
            resObject.getFileError().setErrorInfo(e.getMessage());
            return resObject;
        }
    }

    private ImportResultSheetObject transformationError(String excelName, ImportResultReportObject importResultReportObject, Sheet sheet, String formName) {
        ImportResultSheetObject importResultSheetObject = new ImportResultSheetObject();
        importResultSheetObject.setSheetName(sheet.getSheetName());
        importResultReportObject.setReportName(formName);
        importResultSheetObject.setImportResultReportObject(importResultReportObject);
        return importResultSheetObject;
    }

    private ImportResultReportObject proceeReport(FormData formData, Sheet reportSheet, UploadParam param) {
        ReportMatchResult reportMatchResult = null;
        try {
            reportMatchResult = this.matchSheetToReport(formData, reportSheet);
        }
        catch (Exception e) {
            logger.error("\u8868\u6837\u5339\u914d\u9519\u8bef\uff1a" + e.getMessage(), e);
            ImportResultReportObject reportResult = new ImportResultReportObject();
            if (StringUtils.isNotEmpty((String)this.i18nHelper.getMessage("REPORTERROR"))) {
                ErrorCode.REPORTERROR.setErrorCodeMsg(this.i18nHelper.getMessage("REPORTERROR"));
            }
            reportResult.getReportError().setErrorCode(ErrorCode.REPORTERROR);
            String message = "\u8868\u6837\u5339\u914d\u5931\u8d25";
            if (StringUtils.isNotEmpty((String)this.i18nHelper.getMessage("TABLESAMPLEERROR"))) {
                message = this.i18nHelper.getMessage("TABLESAMPLEERROR");
            }
            reportResult.getReportError().setErrorInfo(message);
            return reportResult;
        }
        if (null == reportMatchResult) {
            ImportResultReportObject reportResult = new ImportResultReportObject();
            if (StringUtils.isNotEmpty((String)this.i18nHelper.getMessage("REPORTERROR"))) {
                ErrorCode.REPORTERROR.setErrorCodeMsg(this.i18nHelper.getMessage("REPORTERROR"));
            }
            reportResult.getReportError().setErrorCode(ErrorCode.REPORTERROR);
            String message = "\u8868\u6837\u5339\u914d\u5931\u8d25";
            if (StringUtils.isNotEmpty((String)this.i18nHelper.getMessage("TABLESAMPLEERROR"))) {
                message = this.i18nHelper.getMessage("TABLESAMPLEERROR");
            }
            reportResult.getReportError().setErrorInfo(message);
            return reportResult;
        }
        return this.saveData(formData, reportMatchResult, param);
    }

    private SheetNameOfFormResultInfo getFormDefineBySheetName(String sheetName, String sysSeparator, String[] split, String formSchemeKey, UploadParam param, ReadWriteAccessCacheManager accessCacheManager) throws Exception {
        ArrayList<FormDefine> formDefineList;
        SheetNameOfFormResultInfo sheetNameOfFormResultInfo = new SheetNameOfFormResultInfo();
        JtableContext jtableContext = new JtableContext();
        jtableContext.setTaskKey(param.getTaskKey());
        jtableContext.setFormSchemeKey(param.getFormSchemeKey());
        jtableContext.setFormulaSchemeKey(param.getFormulaSchemeKey());
        jtableContext.setDimensionSet(param.getDimensionSet());
        jtableContext.setVariableMap(param.getVariableMap());
        List formDefines = this.runtimeView.queryAllFormDefinesByFormScheme(formSchemeKey);
        ArrayList<String> formKeysByUser = new ArrayList<String>();
        for (FormDefine formDefine : formDefines) {
            formKeysByUser.add(formDefine.getKey());
        }
        FormReadWriteAccessData formReadWriteAccessData = new FormReadWriteAccessData(jtableContext, Consts.FormAccessLevel.FORM_DATA_WRITE, formKeysByUser);
        FormReadWriteAccessData result = this.readWriteAccessProvider.getAccessForms(formReadWriteAccessData, accessCacheManager);
        FormDefine formDefine = null;
        if (sysSeparator.equals("") || !sysSeparator.equals("") && !sheetName.contains(sysSeparator)) {
            for (FormDefine formDefineInfo : formDefines) {
                if (!formDefineInfo.getTitle().equals(sheetName) && !formDefineInfo.getFormCode().equals(sheetName)) continue;
                formDefine = formDefineInfo;
                break;
            }
        } else if (!sysSeparator.equals("") && sheetName.contains(sysSeparator)) {
            String formCode;
            int n;
            int n2;
            String[] stringArray;
            formDefineList = new ArrayList();
            String[] splitOfSysSep = sheetName.split(sysSeparator);
            for (FormDefine formDefineInfo : formDefines) {
                stringArray = splitOfSysSep;
                n2 = stringArray.length;
                for (n = 0; n < n2; ++n) {
                    formCode = stringArray[n];
                    if (!formDefineInfo.getFormCode().equals(formCode) && !formDefineInfo.getTitle().equals(formCode)) continue;
                    formDefineList.add(formDefineInfo);
                }
            }
            if (formDefineList.size() > 1) {
                for (FormDefine formDefineInfo : formDefineList) {
                    stringArray = splitOfSysSep;
                    n2 = stringArray.length;
                    for (n = 0; n < n2; ++n) {
                        formCode = stringArray[n];
                        if (!formDefineInfo.getFormCode().equals(formCode)) continue;
                        formDefine = formDefineInfo;
                    }
                }
            } else if (formDefineList.size() == 1) {
                formDefine = (FormDefine)formDefineList.get(0);
            }
        }
        if (formDefine == null) {
            formDefineList = new ArrayList<FormDefine>();
            for (FormDefine formDefineInfo : formDefines) {
                for (String formCode : split) {
                    if (!formDefineInfo.getFormCode().equals(formCode) && !formDefineInfo.getTitle().equals(formCode)) continue;
                    formDefineList.add(formDefineInfo);
                }
            }
            if (formDefineList.size() > 1) {
                for (FormDefine formDefineInfo : formDefineList) {
                    for (String formCode : split) {
                        if (!formDefineInfo.getFormCode().equals(formCode)) continue;
                        formDefine = formDefineInfo;
                    }
                }
            } else if (formDefineList.size() == 1) {
                formDefine = (FormDefine)formDefineList.get(0);
            }
        }
        if (formDefine != null) {
            String formKey = formDefine.getKey();
            sheetNameOfFormResultInfo.setFormDefine(formDefine);
            if (result.getFormKeys().contains(formKey)) {
                sheetNameOfFormResultInfo.setResultType(true);
            } else if (result.getNoAccessnFormKeys().contains(formKey)) {
                sheetNameOfFormResultInfo.setNoFormInfo(result.getOneFormKeyReason(formKey));
            }
        }
        return sheetNameOfFormResultInfo;
    }

    private ImportResultReportObject saveData(FormData formData, ReportMatchResult reportMatchResult, UploadParam param) {
        JtableContext jtableContext = new JtableContext();
        jtableContext.setFormSchemeKey(param.getFormSchemeKey());
        jtableContext.setFormKey(formData.getKey());
        jtableContext.setDimensionSet(param.getDimensionSet());
        jtableContext.setTaskKey(param.getTaskKey());
        jtableContext.setFormulaSchemeKey(param.getFormulaSchemeKey());
        if (param.getVariableMap() != null) {
            jtableContext.setVariableMap(param.getVariableMap());
        }
        if (param.isAppending()) {
            Map variableMap = jtableContext.getVariableMap();
            variableMap.put("isAppending", param.isAppending());
        }
        IReportImportDataSet reportImportData = this.jtableResourceService.getReportImportData(jtableContext);
        ImportResultReportObject importResultReportObject = new ImportResultReportObject();
        ArrayList<IRegionImportDataSet> resImport = new ArrayList<IRegionImportDataSet>();
        List regions = this.jtableParamService.getRegions(formData.getKey());
        HashMap<String, Integer> tabs = new HashMap<String, Integer>();
        HashMap<String, Boolean> isThousandPeLinkMap = new HashMap<String, Boolean>();
        boolean isOneSheet = false;
        ArrayList<ImportResultRegionObject> importResultRegionObjectList = new ArrayList<ImportResultRegionObject>();
        for (int i = 0; i < regions.size(); ++i) {
            List linkDataList;
            ImportResultRegionObject importResultRegionObject;
            HashMap<String, LinkData> tempMap;
            RegionData regionData = (RegionData)regions.get(i);
            IRegionImportDataSet regionImportDataSet = reportImportData.getRegionImportDataSet(regionData);
            List regionTabs = regionImportDataSet.getRegionTabs();
            if (!regionTabs.isEmpty()) {
                Object regionTab2;
                tempMap = new HashMap<String, LinkData>();
                ArrayList tabTitls = new ArrayList();
                for (Object regionTab2 : regionTabs) {
                    tabTitls.add(regionTab2.getTitle());
                }
                int regionTabSizes = 0;
                regionTab2 = regionTabs.iterator();
                while (regionTab2.hasNext()) {
                    List<String> cellValue;
                    RegionTab regionTab3 = (RegionTab)regionTab2.next();
                    List linkDataList2 = this.jtableParamService.getLinks(regionData.getKey());
                    if (linkDataList2.isEmpty() || null == (cellValue = reportMatchResult.getCellValue(((LinkData)linkDataList2.get(0)).getKey().toString()))) continue;
                    for (int j = 0; j < cellValue.size(); ++j) {
                        String cellData = cellValue.get(j);
                        if (!regionTab3.getTitle().equals(cellData)) continue;
                        ++regionTabSizes;
                    }
                }
                if (regionTabSizes > 1) {
                    importResultRegionObject = new ImportResultRegionObject();
                    importResultRegionObject.setRegionKey(regionData.getKey());
                    importResultRegionObject.getRegionError().setErrorCode(ErrorCode.REGIONERROR);
                    importResultRegionObject.getRegionError().setErrorInfo("\u4e00\u4e2a\u6d6e\u52a8\u533a\u57df\u5185\u6709\u591a\u4e2a\u9875\u7b7e\u884c\uff0c\u65e0\u6cd5\u5bfc\u5165");
                    this.collectError(importResultReportObject, importResultRegionObject, reportMatchResult, tempMap, reportMatchResult.getSheetBeginRow());
                    continue;
                }
                if (0 == regionTabSizes) {
                    tabs.put(regionData.getKey(), 0);
                } else {
                    tabs.put(regionData.getKey(), 1);
                }
                List linkDataList3 = regionData.getDataLinks();
                if (!(linkDataList3.isEmpty() || formData.getFormType().equals(FormType.FORM_TYPE_ACCOUNT.name()) && regionData.getRegionTop() > 1)) {
                    MemoryDataSet dataRowSet = new MemoryDataSet();
                    List<String> cellValue = reportMatchResult.getCellValue(((LinkData)linkDataList3.get(0)).getKey().toString());
                    ImportResultRegionObject importResultRegionObjectOfThousandPer = new ImportResultRegionObject();
                    importResultRegionObjectOfThousandPer.setRegionKey(regionData.getKey());
                    ArrayList<ImportErrorDataInfo> importErrorDataInfoListOfThousandPer = new ArrayList<ImportErrorDataInfo>();
                    importResultRegionObjectOfThousandPer.setImportErrorDataInfoList(importErrorDataInfoListOfThousandPer);
                    if (null != cellValue) {
                        int temp = 0;
                        for (int j = 0; j < cellValue.size(); ++j) {
                            boolean isTabRow = false;
                            String cellData = cellValue.get(j);
                            Iterator iterator = tabTitls.iterator();
                            while (iterator.hasNext()) {
                                String tabTitle = (String)iterator.next();
                                if (!tabTitle.equals(cellData)) continue;
                                isTabRow = true;
                            }
                            if (isTabRow) {
                                --temp;
                                continue;
                            }
                            Object[] rowData = new Object[linkDataList3.size()];
                            int index = 0;
                            for (int k = 0; k < linkDataList3.size(); ++k) {
                                LinkData linkData = (LinkData)linkDataList3.get(k);
                                boolean isThousandPerLink = false;
                                if (!isThousandPeLinkMap.containsKey(linkData.getKey())) {
                                    DataLinkDefine dataLinkDefineInfo = this.runtimeView.queryDataLinkDefine(linkData.getKey());
                                    NumberFormatParser numberFormatParser = NumberFormatParser.parse((FormatProperties)dataLinkDefineInfo.getFormatProperties());
                                    if (numberFormatParser.isThousandPer()) {
                                        isThousandPerLink = true;
                                    }
                                    isThousandPeLinkMap.put(linkData.getKey(), isThousandPerLink);
                                } else {
                                    isThousandPerLink = (Boolean)isThousandPeLinkMap.get(linkData.getKey());
                                }
                                if (!StringUtils.isNotEmpty((String)linkData.getZbid())) continue;
                                if (null == reportMatchResult.getCellValue(linkData.getKey().toString())) {
                                    rowData[index] = "";
                                } else if ((linkData.getType() == LinkType.LINK_TYPE_TEXT.getValue() || linkData.getType() == LinkType.LINK_TYPE_STRING.getValue()) && reportMatchResult.getCellValue(linkData.getKey().toString()).get(j) != null) {
                                    rowData[index] = reportMatchResult.getCellValue(linkData.getKey().toString()).get(j).toString().trim();
                                } else {
                                    String cellNum = reportMatchResult.getCellValue(linkData.getKey().toString()).get(j);
                                    if (isThousandPerLink) {
                                        boolean isThousanPerNum = this.checkNumIsThousandPer(cellNum, linkData, tempMap, importErrorDataInfoListOfThousandPer, j);
                                        if (isThousanPerNum) {
                                            if (StringUtils.isNotEmpty((String)cellNum)) {
                                                cellNum = new BigDecimal(cellNum.replace("\u2030", "")).divide(new BigDecimal(1000)).toPlainString();
                                                rowData[index] = cellNum;
                                            } else {
                                                rowData[index] = "IMPORT_INVALID_DATA";
                                            }
                                        } else {
                                            rowData[index] = "";
                                        }
                                    } else {
                                        rowData[index] = cellNum;
                                    }
                                }
                                tempMap.put(j + temp + "_" + linkData.getKey().toString(), linkData);
                                ++index;
                            }
                            try {
                                dataRowSet.add(rowData);
                                continue;
                            }
                            catch (DataSetException e) {
                                logger.error(e.toString());
                            }
                        }
                    }
                    ImportResultRegionObject importResultRegionObject2 = regionImportDataSet.importDataRowSet(dataRowSet);
                    importResultRegionObject2.setRegionKey(regionData.getKey());
                    resImport.add(regionImportDataSet);
                    Set sets = tabs.keySet();
                    int addRows = 0;
                    for (String regionKey : sets) {
                        addRows += ((Integer)tabs.get(regionKey)).intValue();
                    }
                    this.collectError(importResultReportObject, importResultRegionObject2, reportMatchResult, tempMap, addRows + reportMatchResult.getSheetBeginRow());
                    continue;
                }
                if (linkDataList3.isEmpty() || !formData.getFormType().equals(FormType.FORM_TYPE_ACCOUNT.name()) || regionData.getRegionTop() <= 1) continue;
                try {
                    this.sbRegionImport(reportMatchResult, param, jtableContext, regionData, tempMap);
                }
                catch (Exception e) {
                    logger.error(e.getMessage());
                    ImportResultRegionObject importResultRegionObject3 = new ImportResultRegionObject();
                    importResultRegionObject3.setRegionKey(regionData.getKey());
                    importResultRegionObject3.getRegionError().setErrorCode(ErrorCode.REGIONERROR);
                    importResultRegionObject3.getRegionError().setErrorInfo(e.getMessage());
                    this.collectError(importResultReportObject, importResultRegionObject3, reportMatchResult, tempMap, reportMatchResult.getSheetBeginRow());
                }
                continue;
            }
            tempMap = new HashMap();
            if (!isOneSheet) {
                if (this.checkImportDataList != null && this.checkImportDataList.size() > 0) {
                    for (ICheckImportData checkImportData : this.checkImportDataList) {
                        ImportResultRegionObject importResultRegionObjectOfCheck = checkImportData.checkImportData(formData, reportMatchResult, param);
                        importResultRegionObjectList.add(importResultRegionObjectOfCheck);
                    }
                }
                isOneSheet = true;
            }
            if (!((linkDataList = this.jtableParamService.getLinks(regionData.getKey())).isEmpty() || formData.getFormType().equals(FormType.FORM_TYPE_ACCOUNT.name()) && regionData.getRegionTop() > 1)) {
                MemoryDataSet dataRowSet = new MemoryDataSet();
                List<String> cellValue = reportMatchResult.getCellValue(((LinkData)linkDataList.get(0)).getKey().toString());
                ImportResultRegionObject importResultRegionObjectOfThousandPer = new ImportResultRegionObject();
                importResultRegionObjectOfThousandPer.setRegionKey(regionData.getKey());
                ArrayList<ImportErrorDataInfo> importErrorDataInfoListOfThousandPer = new ArrayList<ImportErrorDataInfo>();
                importResultRegionObjectOfThousandPer.setImportErrorDataInfoList(importErrorDataInfoListOfThousandPer);
                if (null != cellValue) {
                    for (int j = 0; j < cellValue.size(); ++j) {
                        Object[] rowData = new Object[linkDataList.size()];
                        int index = 0;
                        for (int k = 0; k < linkDataList.size(); ++k) {
                            LinkData linkData = (LinkData)linkDataList.get(k);
                            boolean isThousandPerLink = false;
                            if (!isThousandPeLinkMap.containsKey(linkData.getKey())) {
                                DataLinkDefine dataLinkDefineInfo = this.runtimeView.queryDataLinkDefine(linkData.getKey());
                                NumberFormatParser numberFormatParser = NumberFormatParser.parse((FormatProperties)dataLinkDefineInfo.getFormatProperties());
                                if (numberFormatParser.isThousandPer()) {
                                    isThousandPerLink = true;
                                }
                                isThousandPeLinkMap.put(linkData.getKey(), isThousandPerLink);
                            } else {
                                isThousandPerLink = (Boolean)isThousandPeLinkMap.get(linkData.getKey());
                            }
                            if (!StringUtils.isNotEmpty((String)linkData.getZbid())) continue;
                            if (null == reportMatchResult.getCellValue(linkData.getKey().toString())) {
                                rowData[index] = "";
                            } else if ((linkData.getType() == LinkType.LINK_TYPE_TEXT.getValue() || linkData.getType() == LinkType.LINK_TYPE_STRING.getValue()) && reportMatchResult.getCellValue(linkData.getKey().toString()).get(j) != null) {
                                rowData[index] = reportMatchResult.getCellValue(linkData.getKey().toString()).get(j).toString().trim();
                            } else {
                                String cellNum = reportMatchResult.getCellValue(linkData.getKey().toString()).get(j);
                                if (isThousandPerLink) {
                                    boolean isThousanPerNum = this.checkNumIsThousandPer(cellNum, linkData, tempMap, importErrorDataInfoListOfThousandPer, j);
                                    if (isThousanPerNum) {
                                        if (StringUtils.isNotEmpty((String)cellNum)) {
                                            cellNum = new BigDecimal(cellNum.replace("\u2030", "")).divide(new BigDecimal(1000)).toPlainString();
                                            rowData[index] = cellNum;
                                        } else {
                                            rowData[index] = "IMPORT_INVALID_DATA";
                                        }
                                    } else {
                                        rowData[index] = "";
                                    }
                                } else {
                                    rowData[index] = cellNum;
                                }
                            }
                            tempMap.put(j + "_" + linkData.getKey().toString(), linkData);
                            ++index;
                        }
                        try {
                            dataRowSet.add(rowData);
                            continue;
                        }
                        catch (DataSetException e) {
                            logger.error(e.toString());
                        }
                    }
                }
                if (importResultRegionObjectOfThousandPer.getImportErrorDataInfoList().size() > 0) {
                    importResultRegionObjectList.add(importResultRegionObjectOfThousandPer);
                }
                ImportResultRegionObject importResultRegionObject4 = regionImportDataSet.importDataRowSet(dataRowSet);
                importResultRegionObject4.setRegionKey(regionData.getKey());
                resImport.add(regionImportDataSet);
                if (importResultRegionObjectList != null && importResultRegionObjectList.size() > 0) {
                    for (ImportResultRegionObject importResultRegionObjectOfCheck : importResultRegionObjectList) {
                        this.collectError(importResultReportObject, importResultRegionObjectOfCheck, reportMatchResult, tempMap, reportMatchResult.getSheetBeginRow());
                    }
                }
                this.collectError(importResultReportObject, importResultRegionObject4, reportMatchResult, tempMap, reportMatchResult.getSheetBeginRow());
                continue;
            }
            if (linkDataList.isEmpty() || !formData.getFormType().equals(FormType.FORM_TYPE_ACCOUNT.name()) || regionData.getRegionTop() <= 1) continue;
            try {
                this.sbRegionImport(reportMatchResult, param, jtableContext, regionData, tempMap);
                continue;
            }
            catch (Exception e) {
                logger.error(e.getMessage());
                importResultRegionObject = new ImportResultRegionObject();
                importResultRegionObject.setRegionKey(regionData.getKey());
                importResultRegionObject.getRegionError().setErrorCode(ErrorCode.REGIONERROR);
                importResultRegionObject.getRegionError().setErrorInfo(e.getMessage());
                this.collectError(importResultReportObject, importResultRegionObject, reportMatchResult, tempMap, reportMatchResult.getSheetBeginRow());
            }
        }
        if (importResultReportObject.getReportError().getErrorCode() == null && importResultReportObject.getImportResultRegionObjectList().size() == 0) {
            for (IRegionImportDataSet iRegionImportDataSet : resImport) {
                iRegionImportDataSet.commitRangeData();
            }
        }
        return importResultReportObject;
    }

    private boolean checkNumIsThousandPer(String cellNum, LinkData linkData, Map<String, LinkData> tempMap, List<ImportErrorDataInfo> importErrorDataInfoListOfThousandPer, int num) {
        boolean isThousanPerNum = false;
        if (StringUtils.isEmpty((String)cellNum)) {
            isThousanPerNum = true;
        } else if (cellNum.contains("\u2030")) {
            try {
                cellNum = new BigDecimal(cellNum.replace("\u2030", "")).divide(new BigDecimal(1000)).toPlainString();
                isThousanPerNum = true;
            }
            catch (Exception e) {
                tempMap.put(num + "_" + linkData.getKey(), linkData);
                ImportErrorDataInfo importErrorDataInfo = new ImportErrorDataInfo();
                importErrorDataInfo.setDataLinkKey(linkData.getKey());
                importErrorDataInfo.setDataIndex(Integer.valueOf(num));
                importErrorDataInfo.getDataError().setErrorCode(ErrorCode.DATAERROR);
                importErrorDataInfo.getDataError().setErrorInfo("\u5bfc\u5165\u6570\u636e\u8f6c\u6362\u5343\u5206\u6bd4\u5f02\u5e38\uff01\u9519\u8bef\u503c:" + cellNum);
                importErrorDataInfoListOfThousandPer.add(importErrorDataInfo);
            }
        } else {
            tempMap.put(num + "_" + linkData.getKey(), linkData);
            ImportErrorDataInfo importErrorDataInfo = new ImportErrorDataInfo();
            importErrorDataInfo.setDataLinkKey(linkData.getKey());
            importErrorDataInfo.setDataIndex(Integer.valueOf(num));
            importErrorDataInfo.getDataError().setErrorCode(ErrorCode.DATAERROR);
            importErrorDataInfo.getDataError().setErrorInfo("\u6307\u6807:" + linkData.getZbtitle() + " ;\u53ea\u80fd\u8f93\u5165\u5343\u5206\u6bd4\u6570\u636e\u3002\u9519\u8bef\u503c:" + cellNum);
            importErrorDataInfo.setFieldTitle(linkData.getZbtitle());
            importErrorDataInfoListOfThousandPer.add(importErrorDataInfo);
        }
        return isThousanPerNum;
    }

    private void sbRegionImport(ReportMatchResult reportMatchResult, UploadParam param, JtableContext jtableContext, RegionData regionData, Map<String, LinkData> tempMap) throws Exception {
        List linkDataList = this.jtableParamService.getLinks(regionData.getKey());
        List<String> cellValue = reportMatchResult.getCellValue(((LinkData)linkDataList.get(0)).getKey().toString());
        DimensionValueSet dimensionSet = new DimensionValueSet();
        Map<String, DimensionValue> dimsMap = param.getDimensionSet();
        for (String dimKey : dimsMap.keySet()) {
            dimensionSet.setValue(dimKey, (Object)dimsMap.get(dimKey).getValue());
        }
        TableContext tableContext = new TableContext(jtableContext.getTaskKey(), jtableContext.getFormSchemeKey(), jtableContext.getFormKey(), dimensionSet, OptTypes.FORM, "excel", "", null);
        if (param.isAppending()) {
            tableContext.setFloatImpOpt(0);
        }
        tableContext.setExpEnumFields(ExpViewFields.TITLE);
        com.jiuqi.nr.io.params.base.RegionData regionDataS = new com.jiuqi.nr.io.params.base.RegionData();
        regionDataS.initialize(this.runtimeView.queryDataRegionDefine(regionData.getKey()));
        ArrayList<ExportFieldDefine> fields = new ArrayList<ExportFieldDefine>();
        for (LinkData link : linkDataList) {
            ExportFieldDefine e = new ExportFieldDefine(link.getTitle(), link.getZbcode(), 0, 0);
            fields.add(e);
        }
        SBRegionDataSet regionDataSet = new SBRegionDataSet(tableContext, regionDataS, fields);
        if (null != cellValue) {
            for (int j = 0; j < cellValue.size(); ++j) {
                Object[] rowData = new Object[linkDataList.size()];
                int index = 0;
                for (int k = 0; k < linkDataList.size(); ++k) {
                    LinkData linkData = (LinkData)linkDataList.get(k);
                    if (!StringUtils.isNotEmpty((String)linkData.getZbid())) continue;
                    rowData[index] = null == reportMatchResult.getCellValue(linkData.getKey().toString()) ? "" : ((linkData.getType() == LinkType.LINK_TYPE_TEXT.getValue() || linkData.getType() == LinkType.LINK_TYPE_STRING.getValue()) && reportMatchResult.getCellValue(linkData.getKey().toString()).get(j) != null ? reportMatchResult.getCellValue(linkData.getKey().toString()).get(j).toString().trim() : reportMatchResult.getCellValue(linkData.getKey().toString()).get(j));
                    tempMap.put(j + "_" + linkData.getKey().toString(), linkData);
                    ++index;
                }
                try {
                    regionDataSet.importDatas(Arrays.asList(rowData));
                    continue;
                }
                catch (Exception e) {
                    logger.error(e.toString());
                    throw new Exception(e.getMessage());
                }
            }
        }
        try {
            regionDataSet.commit();
        }
        catch (Exception e) {
            logger.error(e.toString());
            throw new Exception(e.getMessage());
        }
    }

    private void collectError(ImportResultReportObject importResultReportObject, ImportResultRegionObject importResultRegionObject, ReportMatchResult reportMatchResult, Map<String, LinkData> tempMap, int addTempRows) {
        if (null != importResultRegionObject.getRegionError().getErrorCode()) {
            importResultReportObject.getImportResultRegionObjectList().add(importResultRegionObject);
        } else {
            List importErrorList = importResultRegionObject.getImportErrorDataInfoList();
            if (null != importErrorList && importErrorList.size() > 0) {
                List<RegionMatchInfo> matchList = reportMatchResult.getCompleteMatchList();
                if (((ImportErrorDataInfo)importErrorList.get(0)).getDataIndex() != null) {
                    for (ImportErrorDataInfo importErrorDataOne : importErrorList) {
                        Integer index = importErrorDataOne.getDataIndex();
                        if (null == index) continue;
                        String dataLinkKey = importErrorDataOne.getDataLinkKey();
                        if (null != dataLinkKey) {
                            LinkData linkData = tempMap.get(index + "_" + dataLinkKey.toString());
                            if (null == linkData) continue;
                            int addRows = 0;
                            if (null != matchList && matchList.size() > 0) {
                                for (RegionMatchInfo regionMatchInfo : matchList) {
                                    int startRow = regionMatchInfo.getRegion().getRegionBottom();
                                    int beforeRow = linkData.getRow();
                                    if (importResultRegionObject.getRegionKey().equals(regionMatchInfo.getRegion().getKey())) {
                                        addRows += index.intValue();
                                        continue;
                                    }
                                    if (startRow >= beforeRow) continue;
                                    int endRow = regionMatchInfo.getMatchEnd();
                                    int temp = endRow - regionMatchInfo.getMatchStart();
                                    addRows += temp;
                                }
                            }
                            importErrorDataOne.setExcelLocation(new Point(linkData.getCol(), linkData.getRow() + addRows + addTempRows));
                            continue;
                        }
                        if (null == matchList || matchList.size() <= 0) continue;
                        for (RegionMatchInfo regionMatchInfo : matchList) {
                            if (!importResultRegionObject.getRegionKey().equals(regionMatchInfo.getRegion().getKey())) continue;
                            int startRow = regionMatchInfo.getMatchStart();
                            importErrorDataOne.setExcelLocation(new Point(1, startRow + index + 1));
                        }
                    }
                }
                importResultReportObject.getImportResultRegionObjectList().add(importResultRegionObject);
            }
        }
    }

    private ReportMatchResult matchSheetToReport(FormData formData, Sheet reportSheet) {
        FormDefine formDefine = this.runtimeView.queryFormById(formData.getKey());
        Sheet2GridAdapter sheetGrid = ExcelImportUtil.getSheetCells(reportSheet, this.uploadExcelFilterRowService, formDefine);
        List<LinkData> linkdatas = this.getAllLinkDatas(formData);
        ReportLinkDataCache linkDataCache = new ReportLinkDataCache();
        linkDataCache.init(linkdatas);
        Grid2Data sampleGridData = this.jtableParamService.getGridData(formData.getKey());
        int matchStartRow = this.getSheetMatchStartRow(sampleGridData, sheetGrid, linkDataCache);
        int matchStartCol = this.getSheetMatchStartCol(sampleGridData, sheetGrid, linkDataCache);
        sheetGrid.setMatchStartRow(matchStartRow);
        sheetGrid.setMatchStartCol(matchStartCol);
        if (matchStartRow == -1) {
            return null;
        }
        int matchEndRow = this.getSheetMatchEndRow(formData, sampleGridData, sheetGrid, linkDataCache, linkDataCache);
        int matchEndCol = this.getSheetMatchEndCol(formData, sampleGridData, sheetGrid, linkDataCache, linkDataCache);
        sheetGrid.setMatchEndRow(matchEndRow);
        sheetGrid.setMatchEndCol(matchEndCol);
        List<RegionMatchInfo> regionMatchInfoList = this.getFloatAreaMatchInfo(formData, sampleGridData, linkDataCache);
        List<RegionMatchInfo> completeMatchList = this.matchFloatAreaFormExcel(regionMatchInfoList, sheetGrid);
        boolean isAllMatch = true;
        if (completeMatchList.size() != regionMatchInfoList.size()) {
            isAllMatch = false;
        }
        return this.generateMatchResult(formData.getTitle(), completeMatchList, sampleGridData.getRowCount(), isAllMatch, sheetGrid, linkDataCache, sampleGridData);
    }

    private int getSheetMatchStartRow(Grid2Data sampleGridData, Sheet2GridAdapter sheetGrid, ReportLinkDataCache linkDataCache) {
        ReportFeature rf = new ReportFeature(sampleGridData, linkDataCache);
        boolean isMath = true;
        int startRow = -1;
        for (int row = 0; row < sheetGrid.getRowCount(); ++row) {
            boolean columnHidden;
            String gridDataShow;
            isMath = true;
            for (int col = 0; col < sheetGrid.getColCount(); ++col) {
                String text = sheetGrid.getShowText(row, col);
                gridDataShow = rf.getCellText(1, col + 1);
                if (StringUtils.isEmpty((String)gridDataShow) || gridDataShow.equals("NULL") || gridDataShow.equals("null") || this.valid(gridDataShow).equals(this.valid(text)) || rf.getFeatures().size() < sheetGrid.getColCount() - col) continue;
                isMath = false;
                break;
            }
            if (!isMath) continue;
            int colCount = sheetGrid.getColCount();
            if (!(colCount >= sampleGridData.getColumnCount() - 1 || (columnHidden = sampleGridData.isColumnHidden(sampleGridData.getColumnCount() - 1)) || null == (gridDataShow = rf.getCellText(1, sampleGridData.getColumnCount() - 1)) || "".equals(gridDataShow) || gridDataShow.equals(sheetGrid.getShowText(row, sampleGridData.getColumnCount() - 2)))) {
                return -1;
            }
            startRow = row;
            break;
        }
        return startRow;
    }

    private int getSheetMatchStartCol(Grid2Data sampleGridData, Sheet2GridAdapter sheetGrid, ReportLinkDataCache linkDataCache) {
        ReportFeature rf = new ReportFeature(sampleGridData, linkDataCache);
        boolean isMath = true;
        int startCol = -1;
        for (int col = 0; col < sheetGrid.getColCount(); ++col) {
            boolean rowHidden;
            String gridDataShow;
            isMath = true;
            for (int row = 0; row < sheetGrid.getRowCount(); ++row) {
                String text = sheetGrid.getShowText(row, col);
                gridDataShow = rf.getCellText(row + 1, 1);
                if (StringUtils.isEmpty((String)gridDataShow) || gridDataShow.equals("NULL") || gridDataShow.equals("null") || this.valid(gridDataShow).equals(this.valid(text)) || rf.getFeatures().size() < sheetGrid.getRowCount() - row) continue;
                isMath = false;
                break;
            }
            if (!isMath) continue;
            int rowCount = sheetGrid.getRowCount();
            if (!(rowCount >= sampleGridData.getRowCount() - 1 || (rowHidden = sampleGridData.isRowHidden(sampleGridData.getRowCount() - 1)) || null == (gridDataShow = rf.getCellText(sampleGridData.getRowCount() - 1, 1)) || "".equals(gridDataShow) || gridDataShow.equals(sheetGrid.getShowText(sampleGridData.getRowCount() - 2, col)))) {
                return -1;
            }
            startCol = col;
            break;
        }
        return startCol;
    }

    private int getSheetMatchEndRow(FormData formData, Grid2Data sampleGridData, Sheet2GridAdapter sheetGrid, ReportLinkDataCache linkDataCache, ReportLinkDataCache linkDataCache2) {
        int tempRows;
        List<RegionData> regionList = this.getRegionDatasOrderly(formData);
        if (0 == regionList.size()) {
            return sheetGrid.getRowCount();
        }
        RegionData lastRegion = regionList.get(regionList.size() - 1);
        int gridDataRowCount = sampleGridData.getRowCount() - 1;
        Boolean isFloat = DataRegionKind.DATA_REGION_SIMPLE.getValue() != lastRegion.getType() && lastRegion.getRegionBottom() == gridDataRowCount;
        int gridDataColCounts = sampleGridData.getColumnCount();
        if (!isFloat.booleanValue()) {
            block0: for (tempRows = gridDataRowCount; tempRows >= 0; --tempRows) {
                for (int col = 0; col < gridDataColCounts; ++col) {
                    GridCellData cell = sampleGridData.getGridCellData(col, tempRows);
                    if (cell != null && cell.isMerged() && cell.getMergeInfo() != null) {
                        GridCellData cellData;
                        com.jiuqi.nvwa.grid2.graphics.Point point = cell.getMergeInfo();
                        cell = cellData = sampleGridData.getGridCellData(point.x, point.y);
                    }
                    if (cell != null && StringUtils.isNotEmpty((String)cell.getShowText())) break block0;
                }
            }
            if (lastRegion.getRegionTop() == tempRows + 1) {
                return sheetGrid.getRowCount();
            }
        }
        boolean isMath = true;
        boolean isAllNull = false;
        int endRow = sheetGrid.getRowCount();
        for (int row = sheetGrid.getRowCount() - 1; row >= 0; --row) {
            int col;
            isMath = true;
            isAllNull = false;
            for (col = 0; col < sheetGrid.getColCount(); ++col) {
                String sheetCell = sheetGrid.getShowText(row, col);
                if (sheetCell != null && !"".equals(sheetCell)) {
                    isAllNull = false;
                }
                if (isFloat.booleanValue()) {
                    boolean isMerge = sheetGrid.isMergeCell(row, col);
                    if (sampleGridData.getGridCellData(col + 1, tempRows) == null || isMerge == sampleGridData.getGridCellData(col + 1, tempRows).isMerged()) continue;
                    isMath = false;
                    break;
                }
                if (linkDataCache == null || linkDataCache.hasLinkData(tempRows, col + 1)) continue;
                String text = sheetGrid.getShowText(row, col);
                GridCellData cell = sampleGridData.getGridCellData(col + 1, tempRows);
                if (null == cell) {
                    isMath = false;
                    break;
                }
                if (!StringUtils.isNotEmpty((String)cell.getShowText()) || cell.getShowText().equals(text)) continue;
                isMath = false;
                break;
            }
            if (isAllNull) {
                isMath = false;
            }
            if (isMath) {
                for (col = 0; col < sheetGrid.getColCount(); ++col) {
                    if (isFloat.booleanValue() || linkDataCache == null || linkDataCache.hasLinkData(tempRows, col + 1)) continue;
                    if (row <= 1) break;
                    String text = sheetGrid.getShowText(row - 1, col);
                    GridCellData cell = sampleGridData.getGridCellData(col + 1, tempRows);
                    if (null == cell || StringUtils.isNotEmpty((String)cell.getShowText()) && !cell.getShowText().equals(text)) break;
                }
            }
            if (!isMath) continue;
            endRow = row + 1 + (gridDataRowCount - tempRows);
            break;
        }
        return endRow;
    }

    private int getSheetMatchEndCol(FormData formData, Grid2Data sampleGridData, Sheet2GridAdapter sheetGrid, ReportLinkDataCache linkDataCache, ReportLinkDataCache linkDataCache2) {
        int tempCols;
        List<RegionData> regionList = this.getRegionDatasOrderly(formData);
        if (0 == regionList.size()) {
            return sheetGrid.getColCount();
        }
        RegionData lastRegion = regionList.get(regionList.size() - 1);
        int gridDataColCount = sampleGridData.getColumnCount() - 1;
        Boolean isFloat = DataRegionKind.DATA_REGION_SIMPLE.getValue() != lastRegion.getType() && lastRegion.getRegionRight() == gridDataColCount;
        int gridDataRowCounts = sampleGridData.getRowCount();
        if (!isFloat.booleanValue()) {
            block0: for (tempCols = gridDataColCount; tempCols >= 0; --tempCols) {
                for (int row = 0; row < gridDataRowCounts; ++row) {
                    GridCellData cell = sampleGridData.getGridCellData(row, tempCols);
                    if (cell != null && cell.isMerged() && cell.getMergeInfo() != null) {
                        GridCellData cellData;
                        com.jiuqi.nvwa.grid2.graphics.Point point = cell.getMergeInfo();
                        cell = cellData = sampleGridData.getGridCellData(point.x, point.y);
                    }
                    if (cell != null && StringUtils.isNotEmpty((String)cell.getShowText())) break block0;
                }
            }
            if (lastRegion.getRegionRight() == tempCols + 1) {
                return sheetGrid.getColCount();
            }
        }
        boolean isMath = true;
        boolean isAllNull = false;
        int endCol = sheetGrid.getColCount();
        for (int col = sheetGrid.getColCount() - 1; col >= 0; --col) {
            int row;
            isMath = true;
            isAllNull = false;
            for (row = 0; row < sheetGrid.getRowCount(); ++row) {
                String sheetCell = sheetGrid.getShowText(row, col);
                if (sheetCell != null && !"".equals(sheetCell)) {
                    isAllNull = false;
                }
                if (isFloat.booleanValue()) {
                    boolean isMerge = sheetGrid.isMergeCell(row, col);
                    if (sampleGridData.getGridCellData(tempCols, row + 1) != null && isMerge == sampleGridData.getGridCellData(tempCols, row + 1).isMerged()) continue;
                    isMath = false;
                    break;
                }
                if (linkDataCache == null || linkDataCache.hasLinkData(row + 1, tempCols)) continue;
                String text = sheetGrid.getShowText(row, col);
                GridCellData cell = sampleGridData.getGridCellData(tempCols, row + 1);
                if (null == cell) {
                    isMath = false;
                    break;
                }
                if (!StringUtils.isNotEmpty((String)cell.getShowText()) || cell.getShowText().equals(text)) continue;
                isMath = false;
                break;
            }
            if (isAllNull) {
                isMath = false;
            }
            if (isMath) {
                for (row = 0; row < sheetGrid.getRowCount(); ++row) {
                    if (isFloat.booleanValue() || linkDataCache == null || linkDataCache.hasLinkData(row + 1, tempCols)) continue;
                    if (col <= 1) break;
                    String text = sheetGrid.getShowText(row, col - 1);
                    GridCellData cell = sampleGridData.getGridCellData(tempCols, row + 1);
                    if (null == cell || StringUtils.isNotEmpty((String)cell.getShowText()) && !cell.getShowText().equals(text)) break;
                }
            }
            if (!isMath) continue;
            endCol = col + 1 + (gridDataColCount - tempCols);
            break;
        }
        return endCol;
    }

    private List<RegionData> getRegionDatasOrderly(FormData formData) {
        List regionList = this.jtableParamService.getRegions(formData.getKey());
        Collections.sort(regionList, new Comparator<RegionData>(){

            @Override
            public int compare(RegionData area1, RegionData area2) {
                if (area1.getRegionTop() == area2.getRegionTop()) {
                    return area1.getRegionLeft() - area2.getRegionLeft();
                }
                return area1.getRegionTop() - area2.getRegionTop();
            }
        });
        return regionList;
    }

    private List<RegionMatchInfo> getFloatAreaMatchInfo(FormData formData, Grid2Data sampleGridData, ReportLinkDataCache linkDataCache) {
        ArrayList<RegionMatchInfo> regionMatchInfoList = new ArrayList<RegionMatchInfo>();
        List<RegionData> regionList = this.getRegionDatasOrderly(formData);
        int preAreaEndRow = 0;
        for (int i = 1; i < regionList.size(); ++i) {
            RegionData region = regionList.get(i);
            if (region.getParentKey() != null) continue;
            RegionMatchInfo regionMatchInfo = new RegionMatchInfo(region, sampleGridData, linkDataCache, preAreaEndRow);
            regionMatchInfo.setEndDistance(sampleGridData.getRowCount() - region.getRegionBottom() - 1);
            regionMatchInfoList.add(regionMatchInfo);
            preAreaEndRow = region.getRegionBottom();
        }
        return regionMatchInfoList;
    }

    private List<RegionMatchInfo> matchFloatAreaFormExcel(List<RegionMatchInfo> areaFeatureList, Sheet2GridAdapter sheetGrid) {
        ArrayList<RegionMatchInfo> matchRegionList = new ArrayList<RegionMatchInfo>();
        int sheetRowCount = sheetGrid.getRowCount();
        int sheetColCount = sheetGrid.getColCount();
        int featureStartRow = sheetGrid.getMatchStartRow();
        int featureStartCol = sheetGrid.getMatchStartCol();
        block0: for (int i = 0; i < areaFeatureList.size(); ++i) {
            StringBuilder sb;
            RegionMatchInfo regionInfo = areaFeatureList.get(i);
            regionInfo.setMatchEnd(sheetGrid.getMatchEndRow() - regionInfo.getEndDistance() - 1);
            if (DataRegionKind.DATA_REGION_COLUMN_LIST.getValue() == regionInfo.getRegion().getType()) {
                regionInfo.setMatchEndCol(sheetGrid.getMatchEndCol() - regionInfo.getFeatureDistance() - regionInfo.getEndDistance());
            } else {
                regionInfo.setMatchEndCol(sheetGrid.getMatchEndCol() - regionInfo.getEndDistance() - 1);
            }
            String featureStr = regionInfo.getFeatureText();
            if (DataRegionKind.DATA_REGION_COLUMN_LIST.getValue() == regionInfo.getRegion().getType()) {
                for (int col = featureStartCol; col < sheetColCount; ++col) {
                    sb = new StringBuilder();
                    for (int row = featureStartRow; row < sheetRowCount; ++row) {
                        if (sheetGrid.isMergeCell(row, col)) {
                            if (!sheetGrid.isMergeCellFirstCell(row, col)) continue;
                            sb.append(sheetGrid.getShowText(row, col));
                            continue;
                        }
                        sb.append(sheetGrid.getShowText(row, col));
                    }
                    if (!this.valid(sb.toString()).startsWith(this.valid(featureStr))) continue;
                    int excelAreaStartCol = col + regionInfo.getFeatureDistance();
                    regionInfo.setMatchStartCol(excelAreaStartCol);
                    if (i > 0) {
                        int preAreaEndCol = excelAreaStartCol - regionInfo.getPreFixRectangle() - 1;
                        areaFeatureList.get(i - 1).setMatchEndCol(preAreaEndCol);
                    }
                    matchRegionList.add(regionInfo);
                    featureStartCol = excelAreaStartCol;
                    continue block0;
                }
                continue;
            }
            if (DataRegionKind.DATA_REGION_ROW_LIST.getValue() != regionInfo.getRegion().getType()) continue;
            for (int row = featureStartRow; row < sheetRowCount; ++row) {
                sb = new StringBuilder();
                for (int col = 0; col < sheetColCount; ++col) {
                    if (sheetGrid.isMergeCell(row, col)) {
                        if (!sheetGrid.isMergeCellFirstCell(row, col)) continue;
                        sb.append(sheetGrid.getShowText(row, col));
                        continue;
                    }
                    sb.append(sheetGrid.getShowText(row, col));
                }
                if (!this.valid(sb.toString()).startsWith(this.valid(featureStr))) continue;
                int excelAreaStartRow = row + regionInfo.getFeatureDistance();
                regionInfo.setMatchStart(excelAreaStartRow);
                if (i > 0) {
                    int preAreaEndRow = excelAreaStartRow - regionInfo.getPreFixRectangle() - 1;
                    areaFeatureList.get(i - 1).setMatchEnd(preAreaEndRow);
                }
                matchRegionList.add(regionInfo);
                featureStartRow = excelAreaStartRow;
                if (!sheetGrid.isMergeCell(featureStartRow, 0) || regionInfo.getMatchEnd() != 0) continue block0;
                regionInfo.setMatchEnd(sheetRowCount);
                continue block0;
            }
        }
        return matchRegionList;
    }

    private ReportMatchResult generateMatchResult(String reportCode, List<RegionMatchInfo> regionMatchInfoList, int gridRowCount, boolean isMatchAll, Sheet2GridAdapter sheetGrid, ReportLinkDataCache linkDataCache, Grid2Data grid2Data) {
        int excelFixBeginRow;
        int mapFixBeginRow;
        ReportMatchResult result = new ReportMatchResult();
        result.setSheetBeginRow(sheetGrid.getMatchStartRow());
        ArrayList<String> lackZbAuthCells = new ArrayList<String>();
        RegionMatchInfo lastRegion = null;
        if (regionMatchInfoList != null) {
            for (RegionMatchInfo regionMatchInfo : regionMatchInfoList) {
                this.setFloatMatchResult(result, regionMatchInfo, lackZbAuthCells, sheetGrid, linkDataCache, grid2Data);
                RegionData region = regionMatchInfo.getRegion();
                if (region.getType() == DataRegionKind.DATA_REGION_COLUMN_LIST.getValue()) {
                    int mapFixBeginCol = region.getRegionLeft() - regionMatchInfo.getPreFixRectangle();
                    int excelFixBeginCol = regionMatchInfo.getMatchStartCol() - regionMatchInfo.getPreFixRectangle();
                    this.setFixMatchResult(result, 1, mapFixBeginCol, 0, excelFixBeginCol, sheetGrid.getRowCount(), region.getRegionLeft(), lackZbAuthCells, sheetGrid, linkDataCache, grid2Data);
                } else {
                    mapFixBeginRow = region.getRegionTop() - regionMatchInfo.getPreFixRectangle();
                    excelFixBeginRow = regionMatchInfo.getMatchStart() - regionMatchInfo.getPreFixRectangle();
                    this.setFixMatchResult(result, mapFixBeginRow, 1, excelFixBeginRow, 0, region.getRegionTop(), sheetGrid.getColCount() + 1, lackZbAuthCells, sheetGrid, linkDataCache, grid2Data);
                }
                result.setBeginRows(region.getKey(), regionMatchInfo.getMatchStart());
            }
            if (!regionMatchInfoList.isEmpty()) {
                lastRegion = regionMatchInfoList.get(regionMatchInfoList.size() - 1);
            }
        }
        if (!isMatchAll) {
            return result;
        }
        if (lastRegion != null) {
            mapFixBeginRow = lastRegion.getRegion().getRegionBottom() + 1;
            excelFixBeginRow = lastRegion.getMatchEnd() + 1;
        } else {
            mapFixBeginRow = 1;
            excelFixBeginRow = sheetGrid.getMatchStartRow();
        }
        this.setFixMatchResult(result, mapFixBeginRow, 1, excelFixBeginRow, 0, gridRowCount, sheetGrid.getColCount() + 1, lackZbAuthCells, sheetGrid, linkDataCache, grid2Data);
        result.setCompleteMatchList(regionMatchInfoList);
        return result;
    }

    private void setFloatMatchResult(ReportMatchResult result, RegionMatchInfo regionMatchInfo, List<String> needZbAuthCells, Sheet2GridAdapter sheetGrid, ReportLinkDataCache linkDataCache, Grid2Data grid2Data) {
        RegionData region = regionMatchInfo.getRegion();
        int regionSpan = region.getRegionBottom() - region.getRegionTop() + 1;
        int regionSpanCol = region.getRegionRight() - region.getRegionLeft() + 1;
        if (region.getType() == DataRegionKind.DATA_REGION_COLUMN_LIST.getValue()) {
            for (int col = 0; col < regionSpanCol; ++col) {
                for (int row = 0; row < sheetGrid.getRowCount(); ++row) {
                    if (!linkDataCache.hasLinkData(row + 1, col + region.getRegionLeft())) continue;
                    LinkData linkdata = linkDataCache.getLinkData(row + 1, col + region.getRegionLeft());
                    GridCellData gridCellData = grid2Data.getGridCellData(col + region.getRegionLeft(), row + 1);
                    boolean isCellIsEdit = true;
                    if (gridCellData != null && !gridCellData.getCellStyleData().isEditable()) {
                        isCellIsEdit = false;
                    }
                    boolean isFileField = false;
                    if (linkdata.getType() == LinkType.LINK_TYPE_FILE.getValue() || linkdata.getType() == LinkType.LINK_TYPE_PICTURE.getValue()) {
                        isFileField = true;
                    }
                    ArrayList<String> valueList = new ArrayList<String>();
                    if (this.validateZBAtuth(linkdata)) {
                        for (int c = col + regionMatchInfo.getMatchStartCol(); c <= regionMatchInfo.getMatchEndCol(); c += regionSpanCol) {
                            if (isCellIsEdit && !isFileField) {
                                valueList.add(sheetGrid.getShowText(row, c));
                                continue;
                            }
                            if (RegionSettingUtil.checkRegionSettingContainDefaultVal((RegionData)region, (LinkData)linkdata)) {
                                valueList.add(sheetGrid.getShowText(row, col));
                                continue;
                            }
                            valueList.add("IMPORT_INVALID_DATA");
                        }
                        result.setCellValue(linkdata.getKey().toString(), valueList);
                        continue;
                    }
                    needZbAuthCells.add(String.format("[%d,%d]", linkdata.getRow(), linkdata.getCol()));
                }
            }
        } else {
            for (int row = 0; row < regionSpan; ++row) {
                for (int col = 0; col < sheetGrid.getColCount(); ++col) {
                    if (!linkDataCache.hasLinkData(row + region.getRegionTop(), col + 1)) continue;
                    LinkData linkdata = linkDataCache.getLinkData(row + region.getRegionTop(), col + 1);
                    GridCellData gridCellData = grid2Data.getGridCellData(col + 1, row + region.getRegionTop());
                    boolean isCellIsEdit = true;
                    if (gridCellData != null && !gridCellData.getCellStyleData().isEditable()) {
                        isCellIsEdit = false;
                    }
                    boolean isFileField = false;
                    if (linkdata.getType() == LinkType.LINK_TYPE_FILE.getValue() || linkdata.getType() == LinkType.LINK_TYPE_PICTURE.getValue()) {
                        isFileField = true;
                    }
                    ArrayList<String> valueList = new ArrayList<String>();
                    if (this.validateZBAtuth(linkdata)) {
                        for (int r = row + regionMatchInfo.getMatchStart(); r <= regionMatchInfo.getMatchEnd(); r += regionSpan) {
                            if (isCellIsEdit && !isFileField) {
                                valueList.add(sheetGrid.getShowText(r, col));
                                continue;
                            }
                            if (RegionSettingUtil.checkRegionSettingContainDefaultVal((RegionData)region, (LinkData)linkdata)) {
                                valueList.add(sheetGrid.getShowText(r, col));
                                continue;
                            }
                            valueList.add("IMPORT_INVALID_DATA");
                        }
                        result.setCellValue(linkdata.getKey().toString(), valueList);
                        continue;
                    }
                    needZbAuthCells.add(String.format("[%d,%d]", linkdata.getRow(), linkdata.getCol()));
                }
            }
        }
        int realCount = regionMatchInfo.getMatchEnd() - regionMatchInfo.getMatchStart() + 1;
        double flaotRowCount = Math.ceil(realCount / regionSpan);
        result.setFloatRowCount(region.getKey(), (int)flaotRowCount);
    }

    private void setFixMatchResult(ReportMatchResult result, int mapBeginRow, int mapBeginCol, int excelBeginRow, int excelBeginCol, int endRow, int endCol, List<String> needZbAuthCells, Sheet2GridAdapter sheetGrid, ReportLinkDataCache linkDataCache, Grid2Data grid2Data) {
        int row = mapBeginRow;
        int eRow = excelBeginRow;
        while (row < endRow) {
            int col = mapBeginCol;
            int eCol = excelBeginCol;
            while (col < endCol) {
                if (linkDataCache.hasLinkData(row, col)) {
                    try {
                        LinkData linkdata = linkDataCache.getLinkData(row, col);
                        GridCellData gridCellData = grid2Data.getGridCellData(col, row);
                        boolean isCellIsEdit = true;
                        if (gridCellData != null && !gridCellData.getCellStyleData().isEditable()) {
                            isCellIsEdit = false;
                        }
                        boolean isFileField = false;
                        if (linkdata.getType() == LinkType.LINK_TYPE_FILE.getValue() || linkdata.getType() == LinkType.LINK_TYPE_PICTURE.getValue()) {
                            isFileField = true;
                        }
                        String text = sheetGrid.getShowText(eRow, eCol);
                        ArrayList<String> valueList = new ArrayList<String>();
                        if (this.validateZBAtuth(linkdata)) {
                            if (isCellIsEdit && !isFileField) {
                                valueList.add(text);
                            } else {
                                valueList.add("IMPORT_INVALID_DATA");
                            }
                            result.setCellValue(linkdata.getKey().toString(), valueList);
                        } else {
                            needZbAuthCells.add(String.format("[%d,%d]", linkdata.getRow(), linkdata.getCol()));
                        }
                    }
                    catch (Exception e) {
                        logger.error("\u5339\u914d\u56fa\u5b9a\u533a\u57df\u51fa\u9519\uff0c\u9519\u8bef\u4fe1\u606f\u4e3a\uff1a" + e);
                    }
                }
                ++col;
                ++eCol;
            }
            ++row;
            ++eRow;
        }
    }

    private boolean validateZBAtuth(LinkData linkdata) {
        return true;
    }

    private List<LinkData> getAllLinkDatas(FormData formData) {
        ArrayList<LinkData> linkdatas = new ArrayList<LinkData>();
        List regions = this.jtableParamService.getRegions(formData.getKey());
        for (RegionData region : regions) {
            linkdatas.addAll(this.jtableParamService.getLinks(region.getKey()));
        }
        return linkdatas;
    }

    private Map<String, Object> getReport(FormDefine formDefine, String sheetName, String formSchemeKey, UploadParam param, ReadWriteAccessCacheManager accessCacheManager) {
        HashMap<String, Object> res = new HashMap<String, Object>();
        try {
            if (null == formDefine) {
                logger.warn("\u6ca1\u6709\u5728\u5bf9\u5e94\u7684\u62a5\u8868\u65b9\u6848\u4e2d\u627e\u5230" + sheetName + "\u5bf9\u5e94\u7684\u8868\u5355");
                res.put(this.reportRes, false);
                res.put(this.reportMessage, "\u6ca1\u6709\u5728\u5bf9\u5e94\u7684\u62a5\u8868\u65b9\u6848\u4e2d\u627e\u5230" + sheetName + "\u5bf9\u5e94\u7684\u8868\u5355");
                return res;
            }
            ArrayList<String> formKeysByUser = new ArrayList<String>();
            formKeysByUser.add(formDefine.getKey());
            JtableContext jtableContext = new JtableContext();
            jtableContext.setTaskKey(param.getTaskKey());
            jtableContext.setFormSchemeKey(formSchemeKey);
            jtableContext.setFormulaSchemeKey(param.getFormulaSchemeKey());
            jtableContext.setDimensionSet(param.getDimensionSet());
            jtableContext.setVariableMap(param.getVariableMap());
            FormReadWriteAccessData formReadWriteAccessData = new FormReadWriteAccessData(jtableContext, Consts.FormAccessLevel.FORM_DATA_WRITE, formKeysByUser);
            FormReadWriteAccessData result = this.readWriteAccessProvider.getAccessForms(formReadWriteAccessData, accessCacheManager);
            if (result.getFormKeys().contains(formDefine.getKey())) {
                FormData report = this.jtableParamService.getReport(formDefine.getKey(), formSchemeKey);
                res.put(this.reportRes, true);
                res.put(this.JtableDataStr, report);
                return res;
            }
            res.put(this.reportRes, false);
            res.put(this.reportMessage, result.getOneFormKeyReason(formDefine.getKey()));
            return res;
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            res.put(this.reportRes, false);
            res.put(this.reportMessage, e.getMessage());
            return res;
        }
    }

    private String valid(String str) {
        if (null != str) {
            if (str.indexOf("\r\n") != -1) {
                str = str.replaceAll("\r\n", "");
            }
            if (str.indexOf("\n") != -1) {
                str = str.replaceAll("\n", "");
            }
        } else {
            str = "null";
        }
        return str;
    }

    private String getSysSeparator(String optionKey) {
        String separator = "";
        String sysExcelNameInfo = this.iNvwaSystemOptionService.get("nr-data-entry-export", optionKey);
        String[] nameArray = sysExcelNameInfo.replace("[", "").replace("]", "").replace("\"", "").split(",");
        if (nameArray.length > 1) {
            separator = this.exportExcelNameService.getSysSeparator();
        }
        return separator;
    }
}

