/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.bi.security.SecurityContentException
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.i18n.helper.I18nHelper
 *  com.jiuqi.np.log.enums.OperLevel
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.common.constant.ErrorCode
 *  com.jiuqi.nr.common.importdata.ImportResultExcelFileObject
 *  com.jiuqi.nr.common.importdata.ImportResultSheetObject
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.logic.internal.util.entity.EntityUtil
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory
 *  com.jiuqi.nr.dataservice.core.log.LogDimensionCollection
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.formtype.common.EntityRowBizCodeGetter
 *  com.jiuqi.nr.formtype.service.IFormTypeApplyService
 *  com.jiuqi.nr.jtable.aop.JLoggerAspect
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo
 *  com.jiuqi.nr.jtable.params.output.EntityData
 *  com.jiuqi.nr.jtable.params.output.EntityReturnInfo
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.period.common.language.LanguageCommon
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodRow
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.bi.security.SecurityContentException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.i18n.helper.I18nHelper;
import com.jiuqi.np.log.enums.OperLevel;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.common.constant.ErrorCode;
import com.jiuqi.nr.common.importdata.ImportResultExcelFileObject;
import com.jiuqi.nr.common.importdata.ImportResultSheetObject;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.logic.internal.util.entity.EntityUtil;
import com.jiuqi.nr.dataentry.bean.ExcelImportResultItem;
import com.jiuqi.nr.dataentry.bean.ImportResultItem;
import com.jiuqi.nr.dataentry.bean.ImportResultObject;
import com.jiuqi.nr.dataentry.bean.UploadParam;
import com.jiuqi.nr.dataentry.bean.ZipExcelDimensionObject;
import com.jiuqi.nr.dataentry.internal.service.ExportExcelNameServiceImpl;
import com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor;
import com.jiuqi.nr.dataentry.options.DataentryOptionsUtil;
import com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo;
import com.jiuqi.nr.dataentry.provider.DimensionValueProvider;
import com.jiuqi.nr.dataentry.readwrite.bean.ReadWriteAccessCacheParams;
import com.jiuqi.nr.dataentry.readwrite.impl.ReadWriteAccessCacheManager;
import com.jiuqi.nr.dataentry.service.IBatchCalculateService;
import com.jiuqi.nr.dataentry.service.IUploadTypeExcelService;
import com.jiuqi.nr.dataentry.service.IUploadTypeService;
import com.jiuqi.nr.dataentry.util.BatchExportConsts;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.dataentry.util.DataEntryUtil;
import com.jiuqi.nr.dataentry.util.ExcelErrorUtil;
import com.jiuqi.nr.dataentry.util.ExcelImportUtil;
import com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper;
import com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory;
import com.jiuqi.nr.dataservice.core.log.LogDimensionCollection;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.formtype.common.EntityRowBizCodeGetter;
import com.jiuqi.nr.formtype.service.IFormTypeApplyService;
import com.jiuqi.nr.jtable.aop.JLoggerAspect;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.params.output.EntityReturnInfo;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.period.common.language.LanguageCommon;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodRow;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service(value="upload_type_zip")
public class UploadZipServiceImpl
implements IUploadTypeService {
    private static final Logger logger = LoggerFactory.getLogger(UploadZipServiceImpl.class);
    private static final String REPORT_IMPORT = "reportImport";
    private static final String EXCEL_IMPUT_STEAM = "excelImputSteam";
    private static final String NO_REPORT_NUM = "noReportNum";
    private static final String REPORT_NUM = "reportNum";
    private final Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]");
    private static final int BUFFER_SIZE = 2048;
    public static final String MODULEEXCEL = "EXCEL\u5bfc\u5165";
    @Autowired
    private IUploadTypeExcelService uploadTypeExcelService;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IJtableEntityService jtableEntityService;
    @Autowired
    private DimensionValueProvider dimensionValueProvider;
    @Autowired
    private CacheObjectResourceRemote cacheObjectResourceRemote;
    @Autowired
    private IBatchCalculateService batchCalculateService;
    @Autowired
    private JLoggerAspect jLoggerAspect;
    @Autowired
    private DataentryOptionsUtil dataentryOptionsUtil;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private ExportExcelNameServiceImpl exportExcelNameService;
    @Autowired
    private IFormTypeApplyService iFormTypeApplyService;
    @Autowired
    private IFormSchemeService iFormSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IRunTimeViewController controller;
    @Autowired
    private IEntityViewRunTimeController entityController;
    @Autowired
    private IDataDefinitionRuntimeController dtaDefinitionRuntimeController;
    @Autowired
    @Qualifier(value="nr")
    private I18nHelper i18nHelper;
    @Autowired
    private EntityUtil entityUtil;
    @Autowired
    private DataServiceLoggerFactory dataServiceLoggerFactory;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public ImportResultObject upload(File file, UploadParam param, AsyncTaskMonitor asyncTaskMonitor) {
        LogDimensionCollection logDimension;
        ImportResultObject resultObject;
        DataServiceLogHelper logHelper;
        block104: {
            logHelper = this.dataServiceLoggerFactory.getLogger("EXCEL\u5bfc\u5165\u670d\u52a1", OperLevel.USER_OPER);
            resultObject = new ImportResultObject();
            logDimension = null;
            try {
                block105: {
                    ArrayList<Map<String, DimensionValue>> relationDimensions;
                    ArrayList<String> deleteList;
                    String uid;
                    String path;
                    String strDate;
                    block108: {
                        JtableContext jtableContext;
                        IEntityTable iEntityTable;
                        int noTimeAndCompanyIndex;
                        double oneSpan;
                        double begin;
                        HashMap<String, DimensionValue> dataTime;
                        HashMap<String, DimensionValue> dimensionSet;
                        EntityViewData dwEntity;
                        List noTimeAndCompany;
                        String formSchemeKey;
                        HashMap<String, Map<String, String>> haveQuery;
                        List zipExcelDimensionObjectList;
                        HashMap<String, String> dimAttributeByReportDimMap;
                        block106: {
                            FormSchemeDefine formScheme2 = this.controller.getFormScheme(param.getFormSchemeKey());
                            try {
                                logDimension = new LogDimensionCollection();
                                String dimensionName2 = this.entityMetaService.getDimensionName(formScheme2.getDw());
                                logDimension.setDw(formScheme2.getDw(), new String[]{param.getDimensionSet().get(dimensionName2).getValue()});
                                logDimension.setPeriod(formScheme2.getDateTime(), param.getDimensionSet().get("DATATIME").getValue());
                            }
                            catch (Exception e1) {
                                logger.error("\u6784\u5efa\u65e5\u5fd7\u7ef4\u5ea6\u51fa\u9519");
                            }
                            logHelper.info(param.getTaskKey(), logDimension, "EXCEL\u5bfc\u5165\u5f00\u59cb", "Excel\u5bfc\u5165,\u5f00\u59cb\u89e3\u538bzip\u6587\u4ef6");
                            strDate = param.getFileLocation();
                            path = this.uploadZip(file, strDate);
                            Map<String, Object> importMap = this.fileList(path, param);
                            String date = this.getPeriodTitle(param.getFormSchemeKey(), param.getDimensionSet().get("DATATIME").getValue());
                            if (!importMap.containsKey(EXCEL_IMPUT_STEAM)) {
                                ExcelImportResultItem item = new ExcelImportResultItem();
                                String errorCode = ErrorCode.FILEERROR.getErrorCodeMsg();
                                if (StringUtils.isNotEmpty((String)this.i18nHelper.getMessage("FILEERROR"))) {
                                    errorCode = this.i18nHelper.getMessage("FILEERROR");
                                }
                                String message = "zip\u4e2d\u6ca1\u6709excel\u6587\u4ef6";
                                if (StringUtils.isNotEmpty((String)this.i18nHelper.getMessage("ZIPNOTEXCELERROR"))) {
                                    message = this.i18nHelper.getMessage("ZIPNOTEXCELERROR");
                                }
                                item.setErrorCode(errorCode);
                                item.setErrorInfo(message);
                                resultObject.getFails().add(item);
                                logHelper.error(param.getTaskKey(), logDimension, "EXCEL\u5bfc\u5165\u5f02\u5e38", message);
                                return resultObject;
                            }
                            FormSchemeDefine formScheme = this.controller.getFormScheme(param.getFormSchemeKey());
                            EntityViewData dwEntity2 = this.jtableParamService.getEntity(this.entityUtil.getContextMainDimId(formScheme.getDw()));
                            IEntityModel entityModel = this.entityMetaService.getEntityModel(this.entityUtil.getContextMainDimId(formScheme.getDw()));
                            String dims = formScheme.getDims();
                            HashMap<String, String> dimNameEntityIdMap = new HashMap<String, String>();
                            if (dims != null && !dims.equals("")) {
                                for (String entityId : dims.split(";")) {
                                    String dimensionName = this.entityMetaService.getDimensionName(entityId);
                                    dimNameEntityIdMap.put(dimensionName, entityId);
                                }
                            }
                            dimAttributeByReportDimMap = new HashMap<String, String>();
                            for (ZipExcelDimensionObject zipExcelDimensionObject : (ArrayList)importMap.get(EXCEL_IMPUT_STEAM)) {
                                List<String> dimension;
                                if (!param.getDimensionSet().containsKey("ADJUST") && (zipExcelDimensionObject.getDate() == null || zipExcelDimensionObject.getDate().equals("") || zipExcelDimensionObject.getDate() == zipExcelDimensionObject.getFileName())) {
                                    zipExcelDimensionObject.setDate(date);
                                }
                                if ((dimension = zipExcelDimensionObject.getDimension()) != null && (!dimension.isEmpty() || param.getDimensionSet().size() <= 2)) continue;
                                for (String key : param.getDimensionSet().keySet()) {
                                    DimensionValue dimensionValue;
                                    String dimAttributeByReportDim;
                                    if (key.equals("DATATIME") || key.equals(dwEntity2.getDimensionName()) || (dimAttributeByReportDim = this.iFormSchemeService.getDimAttributeByReportDim(param.getFormSchemeKey(), (String)dimNameEntityIdMap.get(key))) == null) continue;
                                    IEntityAttribute attribute = entityModel.getAttribute(dimAttributeByReportDim);
                                    if (attribute != null && attribute.isMultival()) {
                                        dimensionValue = param.getDimensionSet().get(key);
                                        if (dimensionValue == null) continue;
                                        dimension.add(dimensionValue.getValue());
                                        continue;
                                    }
                                    dimensionValue = param.getDimensionSet().get(key);
                                    if (dimensionValue != null) {
                                        dimension.add(dimensionValue.getValue());
                                    }
                                    dimAttributeByReportDimMap.put(key, dimAttributeByReportDim);
                                }
                            }
                            uid = UUID.randomUUID().toString();
                            if (null == importMap || importMap.size() <= 1) break block105;
                            boolean isReportImport = (Boolean)importMap.get(REPORT_IMPORT);
                            zipExcelDimensionObjectList = (List)importMap.get(EXCEL_IMPUT_STEAM);
                            if (zipExcelDimensionObjectList.size() <= 0) {
                                ExcelImportResultItem item = new ExcelImportResultItem();
                                item.setErrorCode(ErrorCode.FILEERROR.getErrorCodeMsg());
                                item.setErrorInfo("zip\u4e2d\u6ca1\u6709excel\u6587\u4ef6");
                                resultObject.getFails().add(item);
                                logHelper.error(param.getTaskKey(), logDimension, "EXCEL\u5bfc\u5165\u5f02\u5e38", "zip\u4e2d\u6ca1\u6709excel\u6587\u4ef6");
                                return resultObject;
                            }
                            deleteList = new ArrayList<String>();
                            haveQuery = new HashMap<String, Map<String, String>>();
                            formSchemeKey = param.getFormSchemeKey();
                            noTimeAndCompany = this.jtableParamService.getDimEntityList(formSchemeKey);
                            dwEntity = this.jtableParamService.getDwEntity(formSchemeKey);
                            EntityViewData dataTimeEntity = this.jtableParamService.getDataTimeEntity(formSchemeKey);
                            dimensionSet = new HashMap<String, DimensionValue>();
                            dataTime = new HashMap<String, DimensionValue>();
                            relationDimensions = new ArrayList<Map<String, DimensionValue>>();
                            DimensionValue periodDimensionValue = new DimensionValue();
                            DimensionValue oldDimension = param.getDimensionSet().get(dataTimeEntity.getDimensionName());
                            String oldDateValue = oldDimension.getValue();
                            PeriodWrapper periodWrapper = PeriodUtil.getPeriodWrapper((String)oldDateValue);
                            periodDimensionValue.setValue(this.getDateValue(periodWrapper.getType(), ((ZipExcelDimensionObject)zipExcelDimensionObjectList.get(0)).getDate(), param.getFormSchemeKey()));
                            periodDimensionValue.setName(dataTimeEntity.getDimensionName());
                            dimensionSet.put(dataTimeEntity.getDimensionName(), periodDimensionValue);
                            dataTime.put(dataTimeEntity.getDimensionName(), periodDimensionValue);
                            asyncTaskMonitor.progressAndMessage(0.05, "");
                            begin = 0.1;
                            oneSpan = (0.8 - begin) / (double)zipExcelDimensionObjectList.size();
                            noTimeAndCompanyIndex = noTimeAndCompany.size() - 1;
                            if (!isReportImport) break block106;
                            iEntityTable = null;
                            if (this.iFormTypeApplyService.enableNrFormTypeMgr()) {
                                jtableContext = new JtableContext();
                                jtableContext.setTaskKey(param.getTaskKey());
                                jtableContext.setFormSchemeKey(param.getFormSchemeKey());
                                jtableContext.setFormulaSchemeKey(param.getFormulaSchemeKey());
                                jtableContext.setDimensionSet(param.getDimensionSet());
                                jtableContext.setVariableMap(param.getVariableMap());
                                iEntityTable = this.exportExcelNameService.getEntityDataList(jtableContext, param.getDimensionSet());
                            }
                            ImportResultExcelFileObject excelFileResult = new ImportResultExcelFileObject();
                            block54: for (ZipExcelDimensionObject zipExcelDimensionObject : zipExcelDimensionObjectList) {
                                block100: {
                                    Workbook workbook = null;
                                    File input = zipExcelDimensionObject.getFile();
                                    try {
                                        ImportResultExcelFileObject importExcelFileResult;
                                        block102: {
                                            block107: {
                                                Workbook workbook2;
                                                workbook = ExcelImportUtil.create(input);
                                                int sheetCount = workbook.getNumberOfSheets();
                                                boolean haveCompanySheet = false;
                                                Map<String, String> sheetNameToCompanyNameMap = null;
                                                for (int i = 0; i < sheetCount; ++i) {
                                                    Sheet sheet = workbook.getSheetAt(i);
                                                    String sheetName = sheet.getSheetName();
                                                    if (!"(\u9875\u540d\u6620\u5c04\u8868)".equals(sheetName)) continue;
                                                    haveCompanySheet = true;
                                                    sheetNameToCompanyNameMap = DataEntryUtil.sheetNameToCompanyName(sheet);
                                                    break;
                                                }
                                                ArrayList<Sheet> sheetList = new ArrayList<Sheet>();
                                                ArrayList<UploadParam> uploadParamList = new ArrayList<UploadParam>();
                                                for (int i = 0; i < sheetCount; ++i) {
                                                    void var55_78;
                                                    void var55_76;
                                                    Sheet sheet = workbook.getSheetAt(i);
                                                    String sheetName = sheet.getSheetName();
                                                    if (sheetName.equals("HIDDENSHEETNAME")) continue;
                                                    if (null != sheetNameToCompanyNameMap && haveCompanySheet && sheetNameToCompanyNameMap.containsKey(sheetName)) {
                                                        sheetName = sheetNameToCompanyNameMap.get(sheetName);
                                                    }
                                                    if ("(\u9875\u540d\u6620\u5c04\u8868)".equals(sheetName) || "HIDDENSHEETNAME".equals(sheetName)) continue;
                                                    List<String> sortDimensionNames = zipExcelDimensionObject.getDimension();
                                                    HashMap dimensionSetTemp = new HashMap();
                                                    dimensionSetTemp.putAll(dimensionSet);
                                                    String string = "";
                                                    if (sheetName.contains(" ")) {
                                                        sheetName = sheetName.replace(" ", "|");
                                                    } else if (sheetName.contains("_")) {
                                                        sheetName = sheetName.replace("_", "|");
                                                    } else if (sheetName.contains("&")) {
                                                        sheetName = sheetName.replace("&", "|");
                                                    }
                                                    if (this.iFormTypeApplyService.enableNrFormTypeMgr()) {
                                                        String string2 = this.getEntityKeyOfGZW(iEntityTable, sheetName);
                                                    }
                                                    if (StringUtils.isEmpty((String)var55_76)) {
                                                        String string3 = this.getKey(dwEntity, haveQuery, sheetName, formSchemeKey, true, dataTime);
                                                    }
                                                    if (StringUtils.isEmpty((String)var55_78)) {
                                                        ImportResultSheetObject sheetResult = new ImportResultSheetObject();
                                                        sheetResult.getSheetError().setErrorInfo("\u6587\u4ef6\u5339\u914d\u5931\u8d25\uff1a\u5f53\u524d\u7ef4\u5ea6\u4e0b\uff0c\u6839\u636esheet\u9875\u540d\u79f0\u6ca1\u6709\u627e\u5230\u5bf9\u5e94\u7684\u5355\u4f4d\uff01");
                                                        sheetResult.getSheetError().setErrorCode(ErrorCode.SHEETERROR);
                                                        sheetResult.setSheetName(sheetName);
                                                        excelFileResult.getImportResultSheetObjectList().add(sheetResult);
                                                        logHelper.error(param.getTaskKey(), logDimension, "EXCEL\u5bfc\u5165\u5f02\u5e38", "\u6587\u4ef6\u5339\u914d\u5931\u8d25\uff1a\u5f53\u524d\u7ef4\u5ea6\u4e0b\uff0c\u6839\u636esheet\u9875\u540d\u79f0\u6ca1\u6709\u627e\u5230\u5bf9\u5e94\u7684\u5355\u4f4d\uff01");
                                                        continue block54;
                                                    }
                                                    DimensionValue tempCompany = new DimensionValue();
                                                    if (null != dwEntity) {
                                                        tempCompany.setName(dwEntity.getDimensionName());
                                                        tempCompany.setValue((String)var55_78);
                                                        dimensionSetTemp.put(dwEntity.getDimensionName(), tempCompany);
                                                    }
                                                    if (null != sortDimensionNames && sortDimensionNames.size() > 0) {
                                                        for (int index = 0; index < sortDimensionNames.size(); ++index) {
                                                            if (index <= noTimeAndCompanyIndex) {
                                                                String isKey;
                                                                String dimensionValue = sortDimensionNames.get(index);
                                                                EntityViewData entityViewData = (EntityViewData)noTimeAndCompany.get(index);
                                                                if (dimAttributeByReportDimMap.containsKey(entityViewData.getDimensionName())) {
                                                                    String asString;
                                                                    String dimAttributeByReportDim = (String)dimAttributeByReportDimMap.get(entityViewData.getDimensionName());
                                                                    IEntityQuery newEntityQuery = this.entityDataService.newEntityQuery();
                                                                    DimensionValueSet masterKeys = new DimensionValueSet();
                                                                    masterKeys.setValue("DATATIME", (Object)param.getDimensionSet().get("DATATIME"));
                                                                    masterKeys.setValue(dwEntity2.getDimensionName(), (Object)var55_78);
                                                                    newEntityQuery.setMasterKeys(masterKeys);
                                                                    EntityViewDefine entityView = this.entityController.buildEntityView(formScheme.getDw());
                                                                    newEntityQuery.setEntityView(entityView);
                                                                    ExecutorContext executorContext = new ExecutorContext(this.dtaDefinitionRuntimeController);
                                                                    IEntityTable executeFullBuild = newEntityQuery.executeFullBuild((IContext)executorContext);
                                                                    IEntityRow findByCode = executeFullBuild.findByCode((String)var55_78);
                                                                    if (findByCode != null && (asString = findByCode.getAsString(dimAttributeByReportDim)) != null && !asString.equals("")) {
                                                                        dimensionValue = asString;
                                                                    }
                                                                }
                                                                if (StringUtils.isEmpty((String)(isKey = this.getKey(entityViewData, haveQuery, dimensionValue, formSchemeKey, false, dataTime)))) {
                                                                    ImportResultSheetObject sheetResult = new ImportResultSheetObject();
                                                                    sheetResult.getSheetError().setErrorInfo("\u6587\u4ef6\u5339\u914d\u5931\u8d25\uff1a\u6ca1\u6709\u627e\u5230\u5339\u914d\u7684\u7ef4\u5ea6");
                                                                    sheetResult.getSheetError().setErrorCode(ErrorCode.SHEETERROR);
                                                                    sheetResult.setSheetName(sheetName);
                                                                    excelFileResult.getImportResultSheetObjectList().add(sheetResult);
                                                                    logHelper.error(param.getTaskKey(), logDimension, "EXCEL\u5bfc\u5165\u5f02\u5e38", "\u6587\u4ef6\u5339\u914d\u5931\u8d25\uff1a\u6ca1\u6709\u627e\u5230\u5339\u914d\u7684\u7ef4\u5ea6");
                                                                    continue block54;
                                                                }
                                                                DimensionValue temp = new DimensionValue();
                                                                temp.setName(entityViewData.getDimensionName());
                                                                temp.setValue(isKey);
                                                                dimensionSetTemp.put(entityViewData.getDimensionName(), temp);
                                                                continue;
                                                            }
                                                            ImportResultSheetObject sheetResult = new ImportResultSheetObject();
                                                            sheetResult.getSheetError().setErrorInfo("\u6587\u4ef6\u5339\u914d\u5931\u8d25\uff1a\u6ca1\u6709\u627e\u5230\u5339\u914d\u7684\u7ef4\u5ea6");
                                                            sheetResult.getSheetError().setErrorCode(ErrorCode.SHEETERROR);
                                                            sheetResult.setSheetName(sheetName);
                                                            excelFileResult.getImportResultSheetObjectList().add(sheetResult);
                                                            logHelper.error(param.getTaskKey(), logDimension, "EXCEL\u5bfc\u5165\u5f02\u5e38", "\u6587\u4ef6\u5339\u914d\u5931\u8d25\uff1a\u6ca1\u6709\u627e\u5230\u5339\u914d\u7684\u7ef4\u5ea6");
                                                            continue block54;
                                                        }
                                                    }
                                                    UploadParam paramTemp = new UploadParam();
                                                    paramTemp.setFormSchemeKey(formSchemeKey);
                                                    if (param.getDimensionSet().containsKey("ADJUST") && !dimensionSetTemp.containsKey("ADJUST")) {
                                                        dimensionSetTemp.put("ADJUST", param.getDimensionSet().get("ADJUST"));
                                                    }
                                                    paramTemp.setDimensionSet(dimensionSetTemp);
                                                    paramTemp.setTaskKey(param.getTaskKey());
                                                    paramTemp.setFormulaSchemeKey(param.getFormulaSchemeKey());
                                                    if (param.getVariableMap() != null) {
                                                        paramTemp.setVariableMap(param.getVariableMap());
                                                    }
                                                    sheetList.add(sheet);
                                                    uploadParamList.add(paramTemp);
                                                }
                                                if (sheetList.size() <= 0) break block100;
                                                HashMap allDimensionSet = new HashMap();
                                                for (UploadParam uploadParam : uploadParamList) {
                                                    Map<String, DimensionValue> dimensionSet2 = uploadParam.getDimensionSet();
                                                    if (allDimensionSet.isEmpty()) {
                                                        for (Map.Entry entry : dimensionSet2.entrySet()) {
                                                            DimensionValue newDimension = new DimensionValue();
                                                            newDimension.setName(((DimensionValue)entry.getValue()).getName());
                                                            newDimension.setValue(((DimensionValue)entry.getValue()).getValue());
                                                            allDimensionSet.put(entry.getKey(), newDimension);
                                                        }
                                                        continue;
                                                    }
                                                    if (null == dwEntity) continue;
                                                    DimensionValue dimensionUnitValue = dimensionSet2.get(dwEntity.getDimensionName());
                                                    DimensionValue dimensionValue = (DimensionValue)allDimensionSet.get(dwEntity.getDimensionName());
                                                    String value = dimensionValue.getValue();
                                                    value = ";" + dimensionUnitValue.getValue();
                                                    dimensionValue.setValue(value);
                                                    allDimensionSet.put(dwEntity.getDimensionName(), dimensionValue);
                                                }
                                                JtableContext jtableContext2 = new JtableContext();
                                                jtableContext2.setTaskKey(param.getTaskKey());
                                                jtableContext2.setFormSchemeKey(param.getFormSchemeKey());
                                                jtableContext2.setFormulaSchemeKey(param.getFormulaSchemeKey());
                                                jtableContext2.setDimensionSet(allDimensionSet);
                                                jtableContext2.setVariableMap(param.getVariableMap());
                                                ReadWriteAccessCacheParams readWriteAccessCacheParams = new ReadWriteAccessCacheParams(jtableContext2, null, Consts.FormAccessLevel.FORM_DATA_WRITE);
                                                ReadWriteAccessCacheManager readWriteAccessCacheManager = (ReadWriteAccessCacheManager)BeanUtil.getBean(ReadWriteAccessCacheManager.class);
                                                readWriteAccessCacheManager.initCache(readWriteAccessCacheParams);
                                                importExcelFileResult = this.uploadTypeExcelService.upload(sheetList, zipExcelDimensionObject.getFileName(), uploadParamList, asyncTaskMonitor, begin, oneSpan, readWriteAccessCacheManager);
                                                importExcelFileResult.getImportResultSheetObjectList().addAll(excelFileResult.getImportResultSheetObjectList());
                                                importExcelFileResult.setFileName(zipExcelDimensionObject.getFileName());
                                                if (importExcelFileResult.isSuccessIs().booleanValue()) break block107;
                                                Workbook workbook3 = null;
                                                try {
                                                    workbook2 = ExcelErrorUtil.exportExcel(resultObject, importExcelFileResult, ExcelImportUtil.create(input));
                                                    String filePath = zipExcelDimensionObject.getFileAddress();
                                                    String filePathNew = filePath.replace(param.getFileLocation(), param.getFileLocation() + uid);
                                                    PathUtils.validatePathManipulation((String)filePathNew);
                                                    File errorFile = new File(filePathNew);
                                                    try (FileOutputStream out = new FileOutputStream(errorFile);){
                                                        errorFile.getParentFile().mkdirs();
                                                        errorFile.createNewFile();
                                                        workbook2.write(out);
                                                    }
                                                    catch (Exception e) {
                                                        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                                                    }
                                                    if (workbook2 != null) {
                                                    }
                                                    break block102;
                                                }
                                                catch (Exception e) {
                                                    logger.error("\u5bfc\u5165\u5931\u8d25\u6709\u672a\u77e5\u5f02\u5e38\uff1a" + e.getMessage(), e);
                                                    logHelper.error(param.getTaskKey(), logDimension, "EXCEL\u5bfc\u5165\u5f02\u5e38", "\u5bfc\u5165\u5931\u8d25\u6709\u672a\u77e5\u5f02\u5e38\uff1a" + e.getMessage());
                                                    break block102;
                                                }
                                                workbook2.close();
                                                break block102;
                                                finally {
                                                    if (workbook3 != null) {
                                                        workbook3.close();
                                                    }
                                                }
                                            }
                                            this.deleteFile(zipExcelDimensionObject, deleteList);
                                        }
                                        relationDimensions.addAll(importExcelFileResult.getRelationDimensions());
                                    }
                                    catch (IOException e) {
                                        logger.error("\u5bfc\u5165\u5931\u8d25\u6709\u672a\u77e5\u5f02\u5e38\uff1a" + e.getMessage(), e);
                                        logHelper.error(param.getTaskKey(), logDimension, "EXCEL\u5bfc\u5165\u5f02\u5e38", "\u5bfc\u5165\u5931\u8d25\u6709\u672a\u77e5\u5f02\u5e38\uff1a" + e.getMessage());
                                        excelFileResult.getFileError().setErrorCode(ErrorCode.SYSTEMERROR);
                                        excelFileResult.getFileError().setErrorInfo(e.getMessage());
                                        excelFileResult.setFileName(zipExcelDimensionObject.getFileName());
                                        workbook = ExcelErrorUtil.exportExcel(resultObject, excelFileResult, ExcelImportUtil.create(input));
                                    }
                                    finally {
                                        try {
                                            if (null == workbook) continue;
                                            workbook.close();
                                        }
                                        catch (Exception e) {
                                            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                                        }
                                        continue;
                                    }
                                }
                                begin += oneSpan;
                            }
                            break block108;
                        }
                        iEntityTable = null;
                        if (this.iFormTypeApplyService.enableNrFormTypeMgr()) {
                            jtableContext = new JtableContext();
                            jtableContext.setTaskKey(param.getTaskKey());
                            jtableContext.setFormSchemeKey(param.getFormSchemeKey());
                            jtableContext.setFormulaSchemeKey(param.getFormulaSchemeKey());
                            jtableContext.setDimensionSet(param.getDimensionSet());
                            jtableContext.setVariableMap(param.getVariableMap());
                            iEntityTable = this.exportExcelNameService.getEntityDataList(jtableContext, param.getDimensionSet());
                        }
                        this.uploadExcelNameIsUnit(param, asyncTaskMonitor, resultObject, zipExcelDimensionObjectList, deleteList, haveQuery, formSchemeKey, noTimeAndCompany, dwEntity, dimensionSet, dataTime, begin, oneSpan, noTimeAndCompanyIndex, relationDimensions, uid, iEntityTable, dimAttributeByReportDimMap);
                    }
                    List<ImportResultItem> fails = resultObject.getFails();
                    if (null != fails && fails.size() > 0) {
                        String patchNew = path.replace(param.getFileLocation(), param.getFileLocation() + uid);
                        PathUtils.validatePathManipulation((String)patchNew);
                        File fileDelete = new File(patchNew);
                        UploadZipServiceImpl.deleteFile(fileDelete);
                        File[] nextFils = fileDelete.listFiles();
                        String source = nextFils[0].getPath();
                        String location = strDate + BatchExportConsts.SEPARATOR + strDate + ".zip";
                        PathUtils.validatePathManipulation((String)FilenameUtils.normalize(BatchExportConsts.UPLOADDIR + BatchExportConsts.SEPARATOR + location));
                        File fileZop = new File(FilenameUtils.normalize(BatchExportConsts.UPLOADDIR + BatchExportConsts.SEPARATOR + location));
                        if (!fileZop.exists()) {
                            fileZop.createNewFile();
                        }
                        try (FileOutputStream outZip = new FileOutputStream(fileZop);){
                            UploadZipServiceImpl.toZip(source, outZip, deleteList);
                            resultObject.setLocation(location);
                        }
                        catch (Exception e) {
                            logger.error("\u5bfc\u5165\u5931\u8d25\u6709\u672a\u77e5\u5f02\u5e38\uff1a" + e.getMessage(), e);
                        }
                    } else {
                        resultObject.setSuccess(true);
                    }
                    if ((("".equals(param.getAutoCacl()) || Consts.ASYNC_PARAM_AUTOCACL_NULL == param.getAutoCacl()) && this.dataentryOptionsUtil.autoCaclUpload() || "1".equals(param.getAutoCacl())) && !relationDimensions.isEmpty()) {
                        Map mergeDimension = DimensionValueSetUtil.mergeDimension(relationDimensions);
                        String childrenTaskId = UUID.randomUUID().toString();
                        SimpleAsyncProgressMonitor childrenAsyncTaskMonitor = new SimpleAsyncProgressMonitor(childrenTaskId, this.cacheObjectResourceRemote, asyncTaskMonitor);
                        BatchCalculateInfo batchCalculateInfo = new BatchCalculateInfo();
                        batchCalculateInfo.setDimensionSet(mergeDimension);
                        batchCalculateInfo.setFormSchemeKey(param.getFormSchemeKey());
                        batchCalculateInfo.setFormulaSchemeKey(param.getFormulaSchemeKey());
                        batchCalculateInfo.setTaskKey(param.getTaskKey());
                        batchCalculateInfo.setVariableMap(param.getVariableMap());
                        this.jLoggerAspect.log(batchCalculateInfo.getContext(), "\u6570\u636e\u6267\u884c\u81ea\u52a8\u8fd0\u7b97");
                        this.batchCalculateService.batchCalculateForm(batchCalculateInfo, childrenAsyncTaskMonitor);
                    }
                    break block104;
                }
                ExcelImportResultItem item = new ExcelImportResultItem();
                item.setErrorCode(ErrorCode.FILEERROR.getErrorCodeMsg());
                item.setErrorInfo("zip\u4e2d\u6ca1\u6709excel\u6587\u4ef6");
                resultObject.getFails().add(item);
                logHelper.error(param.getTaskKey(), logDimension, "EXCEL\u5bfc\u5165\u5f02\u5e38", "zip\u4e2d\u6ca1\u6709excel\u6587\u4ef6");
                return resultObject;
            }
            catch (Exception e) {
                logger.error("\u5bfc\u5165\u5931\u8d25\u6709\u672a\u77e5\u5f02\u5e38\uff1a" + e.getMessage(), e);
                logHelper.error(param.getTaskKey(), logDimension, "EXCEL\u5bfc\u5165\u5f02\u5e38", "\u5bfc\u5165\u5931\u8d25\u6709\u672a\u77e5\u5f02\u5e38\uff1a" + e.getMessage());
                ExcelImportResultItem item = new ExcelImportResultItem();
                item.setErrorCode(ErrorCode.SYSTEMERROR.getErrorCodeMsg());
                item.setErrorInfo(e.getMessage());
                resultObject.getFails().add(item);
            }
        }
        logHelper.info(param.getTaskKey(), logDimension, "EXCEL\u5bfc\u5165\u5b8c\u6210", "Excel\u5bfc\u5165,\u5b8c\u6210");
        return resultObject;
    }

    private String getPeriodTitle(String formSchemeKey, String period) {
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        FormSchemeDefine formScheme = runTimeViewController.getFormScheme(formSchemeKey);
        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(formScheme.getDateTime());
        return periodProvider.getPeriodTitle(period);
    }

    /*
     * Exception decompiling
     */
    private void uploadExcelNameIsUnit(UploadParam param, AsyncTaskMonitor asyncTaskMonitor, ImportResultObject resultObject, List<ZipExcelDimensionObject> zipExcelDimensionObjectList, List<String> deleteList, Map<String, Map<String, String>> haveQuery, String formSchemeKey, List<EntityViewData> noTimeAndCompany, EntityViewData companyEntityView, Map<String, DimensionValue> dimensionSet, Map<String, DimensionValue> dataTime, double begin, double oneSpan, int noTimeAndCompanyIndex, List<Map<String, DimensionValue>> relationDimensions, String uid, IEntityTable iEntityTable, Map<String, String> dimAttributeByReportDimMap) throws IOException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 4 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private Workbook failMatchDimension(ImportResultObject resultObject, ZipExcelDimensionObject zipExcelDimensionObject, ImportResultExcelFileObject excelFileResult, String fileName) throws IOException, InvalidFormatException {
        Workbook workbook = null;
        try (FileOutputStream out = new FileOutputStream(zipExcelDimensionObject.getFileAddress());){
            excelFileResult.getFileError().setErrorInfo("\u6587\u4ef6\u5339\u914d\u5931\u8d25\uff1a\u6ca1\u6709\u627e\u5230\u5339\u914d\u7684\u7ef4\u5ea6");
            excelFileResult.getFileError().setErrorCode(ErrorCode.FILEERROR);
            excelFileResult.setFileName(fileName);
            workbook = ExcelErrorUtil.exportExcel(resultObject, excelFileResult, ExcelImportUtil.create(zipExcelDimensionObject.getFile()));
            workbook.write(out);
        }
        catch (EncryptedDocumentException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return workbook;
    }

    private String getEntityKeyOfGZW(IEntityTable iEntityTable, String excelNameInfo) {
        String entityKey;
        EntityRowBizCodeGetter entityRowBizCodeGetter = this.iFormTypeApplyService.getEntityRowBizCodeGetter(iEntityTable);
        String string = entityKey = entityRowBizCodeGetter.getIEntityRowByBizCode(excelNameInfo, "|") != null ? entityRowBizCodeGetter.getIEntityRow(excelNameInfo, "|").getEntityKeyData() : "";
        if (StringUtils.isEmpty((String)entityKey) && excelNameInfo.split("\\|").length == 3) {
            String[] excelNameArray = excelNameInfo.split("\\|");
            String string2 = entityKey = entityRowBizCodeGetter.getIEntityRowByBizCode(excelNameInfo = excelNameArray[0] + "|" + excelNameArray[1], "|") != null ? entityRowBizCodeGetter.getIEntityRow(excelNameInfo, "|").getEntityKeyData() : "";
            if (StringUtils.isEmpty((String)entityKey)) {
                excelNameInfo = excelNameArray[1] + "|" + excelNameArray[2];
                entityKey = entityRowBizCodeGetter.getIEntityRowByBizCode(excelNameInfo, "|") != null ? entityRowBizCodeGetter.getIEntityRowByBizCode(excelNameInfo, "|").getEntityKeyData() : "";
            }
        }
        return entityKey;
    }

    private String getKey(EntityViewData entityView, Map<String, Map<String, String>> haveQuery, String name, String formSchemeKey, boolean isCompany, Map<String, DimensionValue> dataTime) {
        String key = "";
        String search = name;
        Map<String, String> recordValues = haveQuery.get(entityView.getKey() + entityView.getDimensionName());
        if (null == recordValues) {
            recordValues = new HashMap<String, String>();
        }
        if (recordValues.containsKey(name)) {
            return recordValues.get(name);
        }
        JtableContext context = new JtableContext();
        context.setFormSchemeKey(formSchemeKey);
        context.setDimensionSet(dataTime);
        EntityQueryByViewInfo query = new EntityQueryByViewInfo();
        query.setEntityViewKey(entityView.getKey());
        query.setSearch(search);
        query.setContext(context);
        query.setMatchAll(true);
        EntityReturnInfo returnInfo = this.jtableEntityService.queryEntityData(query);
        ArrayList<EntityData> entityDataList = new ArrayList<EntityData>();
        if (null != returnInfo && null != returnInfo.getEntitys() && returnInfo.getEntitys().size() > 0) {
            List entitys = returnInfo.getEntitys();
            for (EntityData entityData : entitys) {
                String title = entityData.getTitle();
                String code = entityData.getCode();
                if (StringUtils.isEmpty((String)title)) {
                    title = entityData.getRowCaption();
                }
                if (!name.contains(title) && !code.equals(search)) continue;
                entityDataList.add(entityData);
            }
            if (entityDataList.size() > 1) {
                for (EntityData entityData : entityDataList) {
                    if (!entityData.getCode().equals(name)) continue;
                    key = entityData.getId();
                }
            } else if (entityDataList.size() == 1 && entityDataList.get(0) != null) {
                key = ((EntityData)entityDataList.get(0)).getId();
            }
        } else if (isCompany && name.contains("|")) {
            String[] split = name.split("\\|");
            String[] stringArray = split;
            int entityData = stringArray.length;
            for (int i = 0; i < entityData; ++i) {
                String splitInfo;
                search = splitInfo = stringArray[i];
                query.setSearch(search);
                returnInfo = this.jtableEntityService.queryEntityData(query);
                if (null == returnInfo || null == returnInfo.getEntitys() || returnInfo.getEntitys().size() <= 0) continue;
                List entitys = returnInfo.getEntitys();
                for (EntityData entityData2 : entitys) {
                    String code = entityData2.getCode();
                    String title = entityData2.getTitle();
                    if (!code.equals(search) && !title.equals(title)) continue;
                    entityDataList.add(entityData2);
                }
            }
            if (entityDataList.size() > 1) {
                for (EntityData entityData3 : entityDataList) {
                    for (String entityCode : split) {
                        if (!entityData3.getCode().equals(entityCode)) continue;
                        key = entityData3.getId();
                    }
                }
            } else if (entityDataList.size() == 1 && entityDataList.get(0) != null) {
                key = ((EntityData)entityDataList.get(0)).getId();
            }
        }
        recordValues.put(name, key);
        haveQuery.put(entityView.getKey() + entityView.getDimensionName(), recordValues);
        return key;
    }

    private Map<String, Object> fileList(String path, UploadParam param) throws SecurityContentException {
        Integer reportImportNum;
        PathUtils.validatePathManipulation((String)path);
        File rootFile = new File(path);
        HashMap<String, Object> resMap = new HashMap<String, Object>();
        this.foreachFile(rootFile.getPath(), rootFile, resMap, param);
        Integer noReportImportNum = (Integer)resMap.get(NO_REPORT_NUM);
        if (null == noReportImportNum) {
            noReportImportNum = 0;
        }
        if (null == (reportImportNum = (Integer)resMap.get(REPORT_NUM))) {
            reportImportNum = 0;
        }
        resMap.put(REPORT_IMPORT, reportImportNum > noReportImportNum);
        return resMap;
    }

    private void foreachFile(String rootPath, File rootFile, Map<String, Object> resMap, UploadParam param) {
        File[] fs;
        for (File f : fs = rootFile.listFiles()) {
            Integer n;
            Integer formDefine3;
            if (f.isDirectory()) {
                this.foreachFile(rootPath, f, resMap, param);
            }
            if (!f.isFile()) continue;
            ArrayList<ZipExcelDimensionObject> zipExcelDimensionObjectList = (ArrayList<ZipExcelDimensionObject>)resMap.get(EXCEL_IMPUT_STEAM);
            if (null == zipExcelDimensionObjectList) {
                zipExcelDimensionObjectList = new ArrayList<ZipExcelDimensionObject>();
            }
            ZipExcelDimensionObject oneZipExcel = new ZipExcelDimensionObject();
            String path = f.getPath();
            oneZipExcel.setFileAddress(path);
            String pathName = path.substring(rootPath.length() + 1).replaceAll("\\\\", "/");
            String[] temp2 = pathName.split("/");
            pathName = this.findDate(temp2, pathName);
            String[] temp = pathName.split("/");
            String fileName = temp[temp.length - 1];
            String suffix = fileName.substring(fileName.lastIndexOf(46)).toLowerCase();
            if (!(suffix.equals(".xls") || suffix.equals(".xlsx") || suffix.equals(".et"))) {
                f.delete();
                continue;
            }
            String fileName2FormKey = fileName.substring(0, fileName.lastIndexOf("."));
            if (fileName.contains(" ")) {
                fileName2FormKey = fileName2FormKey.replace(" ", "|");
            } else if (fileName.contains("_")) {
                fileName2FormKey = fileName2FormKey.replace("_", "|");
            } else if (fileName.contains("&")) {
                fileName2FormKey = fileName2FormKey.replace("&", "|");
            }
            List allFormDefine = this.controller.queryAllFormDefinesByFormScheme(param.getFormSchemeKey());
            boolean form = false;
            if (fileName2FormKey.contains("|")) {
                String[] split = fileName2FormKey.split("\\|");
                String split0 = split[0];
                String split1 = split[1];
                for (FormDefine formDefine2 : allFormDefine) {
                    if (!formDefine2.getFormCode().equals(split0) && !formDefine2.getTitle().equals(split0) && !formDefine2.getFormCode().equals(split1) && !formDefine2.getTitle().equals(split1)) continue;
                    form = true;
                    break;
                }
            } else {
                for (FormDefine formDefine3 : allFormDefine) {
                    if (!formDefine3.getFormCode().equals(fileName2FormKey) && !formDefine3.getTitle().equals(fileName2FormKey)) continue;
                    form = true;
                    break;
                }
            }
            if (!form) {
                Integer noReportImportNum = (Integer)resMap.get(NO_REPORT_NUM);
                if (null == noReportImportNum) {
                    noReportImportNum = 0;
                }
                formDefine3 = noReportImportNum;
                n = noReportImportNum = Integer.valueOf(noReportImportNum + 1);
                resMap.put(NO_REPORT_NUM, noReportImportNum);
            } else {
                Integer reportImportNum = (Integer)resMap.get(REPORT_NUM);
                if (null == reportImportNum) {
                    reportImportNum = 0;
                }
                formDefine3 = reportImportNum;
                n = reportImportNum = Integer.valueOf(reportImportNum + 1);
                resMap.put(REPORT_NUM, reportImportNum);
            }
            ArrayList<String> sortList = new ArrayList<String>();
            for (int x = 1; x < temp.length - 1; ++x) {
                sortList.add(temp[x]);
            }
            oneZipExcel.setFileName(fileName);
            oneZipExcel.setDimension(sortList);
            oneZipExcel.setDate(temp[0]);
            zipExcelDimensionObjectList.add(oneZipExcel);
            resMap.put(EXCEL_IMPUT_STEAM, zipExcelDimensionObjectList);
        }
    }

    private String findDate(String[] temp2, String pathName) {
        for (int i = 0; i < temp2.length; ++i) {
            String tempName = LanguageCommon.getPeriodItemTitleRe((String)temp2[i]);
            if (tempName.contains("\u5e74")) {
                if (i + 1 >= temp2.length) continue;
                tempName = temp2[i + 1];
                if (tempName.contains("\u5e74") && !tempName.contains("\u671f") && !tempName.contains("xls") && !tempName.contains("xlsx")) {
                    pathName = pathName.substring(pathName.indexOf("/") + 1);
                    temp2 = pathName.split("/");
                    return this.findDate(temp2, pathName);
                }
                return pathName;
            }
            if (temp2.length == 1) continue;
            pathName = pathName.substring(pathName.indexOf("/") + 1);
            temp2 = pathName.split("/");
            return this.findDate(temp2, pathName);
        }
        return pathName;
    }

    private String uploadZip(File zipFile, String strDate) throws IOException, SecurityContentException {
        String descDir = zipFile.getParent() + BatchExportConsts.SEPARATOR;
        try (ZipFile zipNow = new ZipFile(zipFile, Charset.forName("GBK"));){
            PathUtils.validatePathManipulation((String)descDir);
            File pathFile = new File(descDir);
            if (!pathFile.exists()) {
                pathFile.mkdirs();
            }
            Enumeration<? extends ZipEntry> entries = zipNow.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String zipEntryName = entry.getName();
                try {
                    InputStream in = zipNow.getInputStream(entry);
                    Throwable throwable = null;
                    try {
                        String outPath = (descDir + zipEntryName).replaceAll("\\\\", "/");
                        PathUtils.validatePathManipulation((String)FilenameUtils.normalize(outPath.substring(0, outPath.lastIndexOf(47))));
                        File file = new File(FilenameUtils.normalize(outPath.substring(0, outPath.lastIndexOf(47))));
                        if (!file.exists()) {
                            file.mkdirs();
                        }
                        PathUtils.validatePathManipulation((String)FilenameUtils.normalize(outPath));
                        if (new File(FilenameUtils.normalize(outPath)).isDirectory()) continue;
                        File outPathFile = new File(FilenameUtils.normalize(outPath));
                        File fileParent = outPathFile.getParentFile();
                        if (!fileParent.exists()) {
                            fileParent.mkdirs();
                        }
                        if (!outPathFile.exists()) {
                            outPathFile.createNewFile();
                        }
                        FileOutputStream out2 = new FileOutputStream(FilenameUtils.normalize(outPath));
                        Throwable throwable2 = null;
                        try {
                            int len;
                            byte[] buf1 = new byte[1024];
                            while ((len = in.read(buf1)) > 0) {
                                out2.write(buf1, 0, len);
                            }
                        }
                        catch (Throwable throwable3) {
                            throwable2 = throwable3;
                            throw throwable3;
                        }
                        finally {
                            if (out2 == null) continue;
                            if (throwable2 != null) {
                                try {
                                    out2.close();
                                }
                                catch (Throwable throwable4) {
                                    throwable2.addSuppressed(throwable4);
                                }
                                continue;
                            }
                            out2.close();
                        }
                    }
                    catch (Throwable throwable5) {
                        throwable = throwable5;
                        throw throwable5;
                    }
                    finally {
                        if (in == null) continue;
                        if (throwable != null) {
                            try {
                                in.close();
                            }
                            catch (Throwable throwable6) {
                                throwable.addSuppressed(throwable6);
                            }
                            continue;
                        }
                        in.close();
                    }
                }
                catch (Exception e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
        }
        return descDir;
    }

    private void deleteFile(ZipExcelDimensionObject zipExcelDimensionObject, List<String> deleteList) throws SecurityContentException {
        boolean res;
        PathUtils.validatePathManipulation((String)zipExcelDimensionObject.getFileAddress());
        File excelFile = new File(zipExcelDimensionObject.getFileAddress());
        if (excelFile.exists() && excelFile.isFile() && !(res = excelFile.delete()) && null != deleteList) {
            deleteList.add(excelFile.getPath());
        }
    }

    private String getDateValue(int type, String fileName, String formSchemeKey) {
        String res = "";
        fileName = LanguageCommon.getPeriodItemTitleRe((String)fileName);
        switch (type) {
            case 1: {
                res = fileName.substring(0, 4) + "N0001";
                break;
            }
            case 2: {
                res = fileName.substring(0, 4) + (fileName.contains("\u4e0a\u534a\u5e74") ? "H0001" : "H0002");
                break;
            }
            case 3: {
                if (fileName.contains("\u7b2c\u4e00\u5b63\u5ea6") || fileName.contains("1\u5b63")) {
                    res = fileName.substring(0, 4) + "J0001";
                    break;
                }
                if (fileName.contains("\u7b2c\u4e8c\u5b63\u5ea6") || fileName.contains("2\u5b63")) {
                    res = fileName.substring(0, 4) + "J0002";
                    break;
                }
                if (fileName.contains("\u7b2c\u4e09\u5b63\u5ea6") || fileName.contains("3\u5b63")) {
                    res = fileName.substring(0, 4) + "J0003";
                    break;
                }
                if (!fileName.contains("\u7b2c\u56db\u5b63\u5ea6") && !fileName.contains("4\u5b63")) break;
                res = fileName.substring(0, 4) + "J0004";
                break;
            }
            case 5: {
                SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy\u5e74MM\u6708");
                try {
                    Date date = simpleDate.parse(fileName);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    String yearStr = calendar.get(1) + "";
                    int month = calendar.get(2);
                    res = yearStr + "X00";
                    int count = 0;
                    if (fileName.contains("\u4e0a\u65ec")) {
                        count = month * 3 + 1;
                    } else if (fileName.contains("\u4e2d\u65ec")) {
                        count = month * 3 + 2;
                    } else if (fileName.contains("\u4e0b\u65ec")) {
                        count = (month + 1) * 3;
                    }
                    if (count < 10) {
                        res = res + "0" + count;
                        break;
                    }
                    res = res + count;
                }
                catch (ParseException e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
                break;
            }
            case 4: {
                SimpleDateFormat simple = new SimpleDateFormat("yyyy\u5e74MM\u6708");
                try {
                    Date date = simple.parse(fileName);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    String yearStr = calendar.get(1) + "";
                    int month = calendar.get(2) + 1;
                    if (month < 10) {
                        res = yearStr + "Y000" + month;
                        break;
                    }
                    res = yearStr + "Y00" + month;
                }
                catch (ParseException e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
                break;
            }
            case 6: {
                SimpleDateFormat simple2 = new SimpleDateFormat("yyyy\u5e74MM\u6708dd\u65e5");
                try {
                    Date date = simple2.parse(fileName);
                    GregorianCalendar gregorianCalendar = new GregorianCalendar();
                    gregorianCalendar.setTime(date);
                    PeriodWrapper periodWrapper = PeriodUtil.currentPeriod((GregorianCalendar)gregorianCalendar, (int)type, (int)0);
                    res = periodWrapper.toString();
                }
                catch (ParseException e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
                break;
            }
            case 7: {
                SimpleDateFormat simple3 = new SimpleDateFormat("yyyy\u5e74MM\u6708dd\u65e5");
                try {
                    Date date = simple3.parse(fileName);
                    GregorianCalendar gregorianCalendar = new GregorianCalendar();
                    gregorianCalendar.setTime(date);
                    PeriodWrapper periodWrapper = PeriodUtil.currentPeriod((GregorianCalendar)gregorianCalendar, (int)type, (int)0);
                    res = periodWrapper.toString();
                }
                catch (ParseException e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
                break;
            }
            case 8: {
                IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
                FormSchemeDefine formScheme = runTimeViewController.getFormScheme(formSchemeKey);
                IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(formScheme.getDateTime());
                List iPeriodRowList = periodProvider.getPeriodItems();
                for (IPeriodRow iPeriodRow : iPeriodRowList) {
                    if (!iPeriodRow.getTitle().equals(fileName)) continue;
                    res = iPeriodRow.getCode();
                }
                break;
            }
        }
        return res;
    }

    private static void deleteFile(File file) throws SecurityContentException {
        PathUtils.validatePathManipulation((String)file.getPath());
        if (file.exists() && !file.isFile() && file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; ++i) {
                UploadZipServiceImpl.deleteFile(files[i]);
            }
            file.delete();
        }
    }

    public static void toZip(String srcDir, OutputStream out, List<String> deleteList) {
        try (ZipOutputStream zos = new ZipOutputStream(out);){
            File sourceFile = new File(srcDir);
            UploadZipServiceImpl.compress(sourceFile, zos, sourceFile.getName(), true, deleteList);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void compress(File sourceFile, ZipOutputStream zos, String name, boolean KeepDirStructure, List<String> deleteList) throws Exception {
        byte[] buf = new byte[2048];
        if (sourceFile.isFile()) {
            if (deleteList.contains(sourceFile.getPath())) {
                zos.closeEntry();
            } else {
                try (FileInputStream in = new FileInputStream(sourceFile);){
                    int len;
                    zos.putNextEntry(new ZipEntry(name));
                    while ((len = in.read(buf)) != -1) {
                        zos.write(buf, 0, len);
                    }
                }
                catch (Exception e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
                finally {
                    if (zos != null) {
                        try {
                            zos.closeEntry();
                        }
                        catch (IOException e) {
                            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                        }
                    }
                }
            }
        } else {
            File[] listFiles = sourceFile.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                if (KeepDirStructure) {
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    zos.closeEntry();
                }
            } else {
                for (File file : listFiles) {
                    if (KeepDirStructure) {
                        UploadZipServiceImpl.compress(file, zos, name + "/" + file.getName(), KeepDirStructure, deleteList);
                        continue;
                    }
                    UploadZipServiceImpl.compress(file, zos, file.getName(), KeepDirStructure, deleteList);
                }
            }
        }
    }

    private String listToString(List<String> list, String separator) {
        if (list.size() == 1) {
            return list.get(0);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); ++i) {
            sb.append(list.get(i));
            if (i >= list.size() - 1) continue;
            sb.append(separator);
        }
        return sb.toString();
    }
}

