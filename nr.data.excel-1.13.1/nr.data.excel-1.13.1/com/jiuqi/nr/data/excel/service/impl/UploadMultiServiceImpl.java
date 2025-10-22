/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.i18n.helper.I18nHelper
 *  com.jiuqi.nr.common.constant.ErrorCode
 *  com.jiuqi.nr.common.importdata.ImportResultExcelFileObject
 *  com.jiuqi.nr.common.importdata.ImportResultSheetObject
 *  com.jiuqi.nr.common.importdata.ResultErrorInfo
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.util.DimensionValueSetUtil
 *  com.jiuqi.nr.data.access.service.IDataAccessService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.access.util.DataAccesslUtil
 *  com.jiuqi.nr.data.common.CommonMessage
 *  com.jiuqi.nr.data.common.logger.DataImportLogger
 *  com.jiuqi.nr.data.common.param.ImportFileDataRange
 *  com.jiuqi.nr.data.common.service.dto.ImportCancledResult
 *  com.jiuqi.nr.dataentity.entity.IDataEntity
 *  com.jiuqi.nr.dataentity.entity.IDataEntityRow
 *  com.jiuqi.nr.dataentity.service.DataEntityService
 *  com.jiuqi.nr.datascheme.api.AdjustPeriod
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.FMDMAttributeDTO
 *  com.jiuqi.nr.fmdm.FMDMDataDTO
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.fmdm.IFMDMAttributeService
 *  com.jiuqi.nr.fmdm.IFMDMData
 *  com.jiuqi.nr.fmdm.IFMDMDataService
 *  com.jiuqi.nr.fmdm.exception.FMDMQueryException
 *  com.jiuqi.nr.formtype.common.EntityRowBizCodeGetter
 *  com.jiuqi.nr.formtype.service.IFormTypeApplyService
 *  com.jiuqi.nr.period.common.language.LanguageCommon
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodRow
 */
package com.jiuqi.nr.data.excel.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.i18n.helper.I18nHelper;
import com.jiuqi.nr.common.constant.ErrorCode;
import com.jiuqi.nr.common.importdata.ImportResultExcelFileObject;
import com.jiuqi.nr.common.importdata.ImportResultSheetObject;
import com.jiuqi.nr.common.importdata.ResultErrorInfo;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.util.DimensionValueSetUtil;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.data.common.CommonMessage;
import com.jiuqi.nr.data.common.logger.DataImportLogger;
import com.jiuqi.nr.data.common.param.ImportFileDataRange;
import com.jiuqi.nr.data.common.service.dto.ImportCancledResult;
import com.jiuqi.nr.data.excel.consts.BatchExportConsts;
import com.jiuqi.nr.data.excel.param.UploadParam;
import com.jiuqi.nr.data.excel.param.bean.ExcelImportResultItem;
import com.jiuqi.nr.data.excel.param.bean.ImportResultObject;
import com.jiuqi.nr.data.excel.param.bean.UploadExcelByUnit;
import com.jiuqi.nr.data.excel.param.bean.ZipExcelDimensionObject;
import com.jiuqi.nr.data.excel.service.impl.UploadExcelBaseService;
import com.jiuqi.nr.data.excel.utils.DataExcelUtils;
import com.jiuqi.nr.data.excel.utils.ExcelErrorUtil;
import com.jiuqi.nr.data.excel.utils.ExcelImportUtil;
import com.jiuqi.nr.data.excel.utils.ExportUtil;
import com.jiuqi.nr.dataentity.entity.IDataEntity;
import com.jiuqi.nr.dataentity.entity.IDataEntityRow;
import com.jiuqi.nr.dataentity.service.DataEntityService;
import com.jiuqi.nr.datascheme.api.AdjustPeriod;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.FMDMDataDTO;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import com.jiuqi.nr.fmdm.IFMDMData;
import com.jiuqi.nr.fmdm.IFMDMDataService;
import com.jiuqi.nr.fmdm.exception.FMDMQueryException;
import com.jiuqi.nr.formtype.common.EntityRowBizCodeGetter;
import com.jiuqi.nr.formtype.service.IFormTypeApplyService;
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
import java.lang.invoke.LambdaMetafactory;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UploadMultiServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(UploadMultiServiceImpl.class);
    private static final String REPORT_IMPORT = "reportImport";
    private static final String EXCEL_IMPUT_STEAM = "excelImputSteam";
    private static final String NO_REPORT_NUM = "noReportNum";
    private static final String REPORT_NUM = "reportNum";
    private static final String ADJUST = "ADJUST";
    private static final String fileDimError = "\u6587\u4ef6\u5339\u914d\u5931\u8d25: \u6ca1\u6709\u627e\u5230\u5339\u914d\u7684\u7ef4\u5ea6";
    private static final int BUFFER_SIZE = 2048;
    @Autowired
    private UploadExcelBaseService uploadExcelBaseService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IFormTypeApplyService iFormTypeApplyService;
    @Autowired
    private IFormSchemeService iFormSchemeService;
    @Autowired
    @Qualifier(value="nr")
    private I18nHelper i18nHelper;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private DataEntityService dataEntityService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IDataAccessServiceProvider iDataAccessServiceProvider;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IFMDMDataService fMDMDataService;
    @Autowired
    private IFMDMAttributeService fmdmAttributeService;
    @Autowired
    private DimensionProviderFactory dimensionProviderFactory;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    private DataAccesslUtil dataAccesslUtil;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ImportResultObject upload(File file, UploadParam param, AsyncTaskMonitor asyncTaskMonitor, CommonMessage message, DataImportLogger dataImportLogger) {
        importCancledResult = message.getImportCancledResult();
        formScheme = this.runtimeView.getFormScheme(param.getFormSchemeKey());
        if (formScheme == null) {
            dataImportLogger.importError("\u53c2\u6570\u62a5\u8868\u65b9\u6848Key\u4e0d\u80fd\u4e3a\u7a7a");
            throw new IllegalArgumentException("\u53c2\u6570\u62a5\u8868\u65b9\u6848Key\u4e0d\u80fd\u4e3a\u7a7a.");
        }
        dataAccessService = this.iDataAccessServiceProvider.getDataAccessService(formScheme.getTaskKey(), param.getFormSchemeKey());
        resultObject = new ImportResultObject();
        try {
            block206: {
                block210: {
                    block207: {
                        path = strDate = param.getFilePath();
                        isSingleFile = false;
                        if (file.isFile() && (file.getName().endsWith(".zip") || file.getName().endsWith(".ZIP"))) {
                            path = this.uploadZip(file, strDate);
                        } else if (file.isFile() && (file.getName().endsWith(".xlsx") || file.getName().endsWith(".xls") || file.getName().endsWith(".et"))) {
                            isSingleFile = true;
                        }
                        importMap = this.fileList(path, param);
                        if (this.checkAsyncTaskCancling(asyncTaskMonitor, importCancledResult)) {
                            dataImportLogger.cancelImport();
                            return resultObject;
                        }
                        date = this.getPeriodTitle(param.getFormSchemeKey(), param.getDimensionSet().getValue("DATATIME").toString());
                        if (!importMap.containsKey("excelImputSteam")) {
                            item = new ExcelImportResultItem();
                            errorCode = ErrorCode.FILEERROR.getErrorCodeMsg();
                            if (StringUtils.isNotEmpty((String)this.i18nHelper.getMessage("FILEERROR"))) {
                                errorCode = this.i18nHelper.getMessage("FILEERROR");
                            }
                            message1 = "zip\u4e2d\u6ca1\u6709excel\u6587\u4ef6";
                            if (StringUtils.isNotEmpty((String)this.i18nHelper.getMessage("ZIPNOTEXCELERROR"))) {
                                message1 = this.i18nHelper.getMessage("ZIPNOTEXCELERROR");
                            }
                            item.setErrorCode(errorCode);
                            item.setErrorInfo(message1);
                            resultObject.getFails().add(item);
                            return resultObject;
                        }
                        dwEntityID = this.dataAccesslUtil.contextEntityId(formScheme.getDw());
                        entityModel = this.entityMetaService.getEntityModel(dwEntityID);
                        dims = formScheme.getDims();
                        dimNameEntityIdMap = new HashMap<String, String>();
                        if (dims != null && !dims.equals("")) {
                            for (String entityId : dims.split(";")) {
                                dimensionName = this.entityMetaService.getDimensionName(entityId);
                                dimNameEntityIdMap.put(dimensionName, entityId);
                            }
                        }
                        dimAttributeByReportDimMap = new HashMap<Integer, String>();
                        for (ZipExcelDimensionObject zipExcelDimensionObject : (ArrayList)importMap.get("excelImputSteam")) {
                            if (param.getDimensionSet().toDimensionValueSet().hasValue("ADJUST") || zipExcelDimensionObject.getDate() != null && !zipExcelDimensionObject.getDate().equals("") && zipExcelDimensionObject.getDate() != zipExcelDimensionObject.getFileName()) continue;
                            zipExcelDimensionObject.setDate(date);
                            dimension = zipExcelDimensionObject.getDimension();
                            dimensionValueSet = param.getDimensionSet().toDimensionValueSet();
                            if (!dimension.isEmpty() || dimensionValueSet.size() <= 2) continue;
                            noTimeAndCompany = new ArrayList<EntityViewDefine>();
                            if (dims != null) {
                                dimsEntityIds = dims.split(";");
                                for (i = 0; i < dimsEntityIds.length; ++i) {
                                    entityView = this.entityViewRunTimeController.buildEntityView(dimsEntityIds[i]);
                                    noTimeAndCompany.add(entityView);
                                }
                            }
                            indexMap = new HashMap<K, V>();
                            if (noTimeAndCompany != null && !noTimeAndCompany.isEmpty()) {
                                for (i = 0; i < noTimeAndCompany.size(); ++i) {
                                    indexMap.put(this.entityMetaService.getDimensionName(((EntityViewDefine)noTimeAndCompany.get(i)).getEntityId()), i);
                                    dimension.add("");
                                }
                            }
                            for (i = 0; i < dimensionValueSet.size(); ++i) {
                                key = dimensionValueSet.getName(i);
                                if (key.equals("DATATIME") || key.equals(this.entityMetaService.getDimensionName(dwEntityID))) continue;
                                dimAttributeByReportDim = this.iFormSchemeService.getDimAttributeByReportDim(param.getFormSchemeKey(), (String)dimNameEntityIdMap.get(key));
                                if (dimAttributeByReportDim != null) {
                                    attribute = entityModel.getAttribute(dimAttributeByReportDim);
                                    if (attribute != null && attribute.isMultival()) {
                                        dimensionValue = dimensionValueSet.getValue(key);
                                        if (dimensionValue == null) continue;
                                        if (indexMap != null && indexMap.containsKey(key)) {
                                            dimension.set((Integer)indexMap.get(key), dimensionValue.toString());
                                            continue;
                                        }
                                        dimension.add(dimensionValue.toString());
                                        continue;
                                    }
                                    dimensionValue = dimensionValueSet.getValue(key);
                                    if (dimensionValue != null) {
                                        if (indexMap != null && indexMap.containsKey(key)) {
                                            dimension.set((Integer)indexMap.get(key), dimensionValue.toString());
                                        } else {
                                            dimension.add(dimensionValue.toString());
                                        }
                                    }
                                    attrIndex = -1;
                                    attrIndex = indexMap != null && indexMap.containsKey(key) != false ? (Integer)indexMap.get(key) : Integer.valueOf(dimension.size() - 1);
                                    dimAttributeByReportDimMap.put(attrIndex, dimAttributeByReportDim);
                                    continue;
                                }
                                if (indexMap == null || !indexMap.containsKey(key)) continue;
                                value = param.getDimensionSet().getValue(key);
                                dimension.set((Integer)indexMap.get(key), value == null ? "" : value.toString());
                            }
                        }
                        periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(formScheme.getDateTime());
                        periodItemGroupByTitle = ExportUtil.getPeriodItemGroupByTitle(periodProvider);
                        uid = UUID.randomUUID().toString();
                        if (null == importMap || importMap.size() <= 1) break block206;
                        isReportImport = (Boolean)importMap.get("reportImport");
                        zipExcelDimensionObjectList = (List)importMap.get("excelImputSteam");
                        if (zipExcelDimensionObjectList.isEmpty()) {
                            item = new ExcelImportResultItem();
                            item.setErrorCode(ErrorCode.FILEERROR.getErrorCodeMsg());
                            item.setErrorInfo("zip\u4e2d\u6ca1\u6709excel\u6587\u4ef6");
                            resultObject.getFails().add(item);
                            return resultObject;
                        }
                        if (isSingleFile) {
                            for (ZipExcelDimensionObject objItem : zipExcelDimensionObjectList) {
                                objItem.setDate(param.getDimensionSet().getValue("DATATIME").toString());
                                objItem.setDateCode(param.getDimensionSet().getValue("DATATIME").toString());
                            }
                        }
                        deleteList = new ArrayList<String>();
                        haveQuery = new HashMap<String, Map<String, String>>();
                        formSchemeKey = param.getFormSchemeKey();
                        noTimeAndCompany = new ArrayList<EntityViewDefine>();
                        dimsEntityIds = null;
                        if (dims != null) {
                            dimsEntityIds = dims.split(";");
                            for (i = 0; i < dimsEntityIds.length; ++i) {
                                entityView = this.entityViewRunTimeController.buildEntityView(dimsEntityIds[i]);
                                noTimeAndCompany.add(entityView);
                            }
                        }
                        dwEntity = this.entityViewRunTimeController.buildEntityView(dwEntityID);
                        dataTimeEntity = this.entityViewRunTimeController.buildEntityView(formScheme.getDateTime());
                        dataTimeName = "DATATIME";
                        dimensionSet = new HashMap<String, DimensionValue>();
                        dataTime = new HashMap<String, DimensionValue>();
                        relationDimensions = new ArrayList<Map<String, DimensionValue>>();
                        periodDimensionValue = new DimensionValue();
                        if (isSingleFile) {
                            periodDimensionValue.setValue(((ZipExcelDimensionObject)zipExcelDimensionObjectList.get(0)).getDate());
                        } else {
                            block198: {
                                try {
                                    dataTimeValue = ExportUtil.getDateValue(((ZipExcelDimensionObject)zipExcelDimensionObjectList.get(0)).getDate(), periodItemGroupByTitle);
                                }
                                catch (Exception e) {
                                    dataTimeValue = param.getDimensionSet().getValue("DATATIME").toString();
                                    if (!this.iFormSchemeService.enableAdjustPeriod(formSchemeKey)) break block198;
                                    adjustDimensionValue = new DimensionValue();
                                    adjustDimensionValue.setValue((String)param.getDimensionSet().getValue("ADJUST"));
                                    adjustDimensionValue.setName("ADJUST");
                                    dimensionSet.put("ADJUST", adjustDimensionValue);
                                    dataTime.put("ADJUST", adjustDimensionValue);
                                }
                            }
                            periodDimensionValue.setValue(dataTimeValue);
                        }
                        periodDimensionValue.setName(dataTimeName);
                        dimensionSet.put(dataTimeName, periodDimensionValue);
                        dataTime.put(dataTimeName, periodDimensionValue);
                        dimensionSet3 = param.getDimensionSet();
                        names = dimensionSet3.getNames();
                        for (String string : names) {
                            if (dataTimeName.equals(string) || "ADJUST".equals(string)) continue;
                            dimvalue = new DimensionValue();
                            dimvalue.setName(string);
                            value = dimensionSet3.getValue(string);
                            dimvalue.setValue(value != null ? value.toString() : "");
                            dimensionSet.put(string, dimvalue);
                        }
                        asyncTaskMonitor.progressAndMessage(0.05, "");
                        begin = 0.1;
                        oneSpan = (0.8 - begin) / (double)zipExcelDimensionObjectList.size();
                        noTimeAndCompanyIndex = noTimeAndCompany.size();
                        if (!isReportImport) break block207;
                        importCancledResult.setImportFileDataRange(ImportFileDataRange.REPORT);
                        iEntityTable = null;
                        if (this.iFormTypeApplyService.enableNrFormTypeMgr()) {
                            context = new ExecutorContext(this.dataDefinitionRuntimeController);
                            context.setPeriodView(formScheme.getDateTime());
                            dataTimeDimension = DimensionValueSetUtil.getDimensionValueSet(dataTime);
                            dataEntity = this.dataEntityService.getIEntityTable(dwEntity, context, dataTimeDimension, formSchemeKey);
                            iEntityTable = dataEntity.getEntityTable();
                        }
                        taskDefine = this.runtimeView.queryTaskDefine(formScheme.getTaskKey());
                        view = this.runtimeView.getViewByFormSchemeKey(formSchemeKey);
                        rowFilterExpression = null;
                        if (view != null) {
                            rowFilterExpression = view.getRowFilterExpression();
                        }
                        dimAttributeMap = null;
                        attributeCodeMap = new HashMap<K, V>();
                        singleValDims = new ArrayList<EntityViewDefine>();
                        singleDimNames = new StringBuilder();
                        fmdmAttributeDTO = new FMDMAttributeDTO();
                        fmdmAttributeDTO.setFormSchemeKey(formSchemeKey);
                        fmdmAttributes = null;
                        excelFileResult = new ImportResultExcelFileObject();
                        for (ZipExcelDimensionObject zipExcelDimensionObject : zipExcelDimensionObjectList) {
                            if (this.checkAsyncTaskCancling(asyncTaskMonitor, importCancledResult)) {
                                dataImportLogger.cancelImport();
                                return resultObject;
                            }
                            fileName = zipExcelDimensionObject.getFileName();
                            copyPath = zipExcelDimensionObject.getFileAddress().replace(fileName, "") + UUID.randomUUID();
                            copyFile = Paths.get(copyPath + BatchExportConsts.SEPARATOR + fileName, new String[0]).normalize().toFile();
                            try {
                                FileUtils.copyFile(zipExcelDimensionObject.getFile(), copyFile);
                            }
                            catch (Exception e) {
                                UploadMultiServiceImpl.logger.error("\u6784\u5efa\u9519\u8bef\u6587\u4ef6\u65f6\u53d1\u751f\u5f02\u5e38\uff1a{}", (Object)e.getMessage(), (Object)e);
                            }
                            workbook = null;
                            try {
                                workbook = ExcelImportUtil.create(copyFile);
                                sheetCount = workbook.getNumberOfSheets();
                                haveCompanySheet = false;
                                sheetNameToCompanyNameMap = null;
                                for (i = 0; i < sheetCount; ++i) {
                                    sheet = workbook.getSheetAt(i);
                                    sheetName = sheet.getSheetName();
                                    if (!"(\u9875\u540d\u6620\u5c04\u8868)".equals(sheetName)) continue;
                                    haveCompanySheet = true;
                                    sheetNameToCompanyNameMap = DataExcelUtils.sheetNameToCompanyName(sheet);
                                    break;
                                }
                                sheetList = new ArrayList<Sheet>();
                                uploadParamList = new ArrayList<UploadParam>();
                                block104: for (i = 0; i < sheetCount; ++i) {
                                    block208: {
                                        block209: {
                                            sheet = workbook.getSheetAt(i);
                                            sheetName = sheet.getSheetName();
                                            if (sheetName.equals("HIDDENSHEETNAME") || sheetName.equals("\u76ee\u5f55")) break block208;
                                            if (null != sheetNameToCompanyNameMap && haveCompanySheet && sheetNameToCompanyNameMap.containsKey(sheetName)) {
                                                sheetName = sheetNameToCompanyNameMap.get(sheetName);
                                            }
                                            if ("(\u9875\u540d\u6620\u5c04\u8868)".equals(sheetName) || "HIDDENSHEETNAME".equals(sheetName)) break block208;
                                            sortDimensionNames = zipExcelDimensionObject.getDimension();
                                            dimensionSetTemp = new HashMap<K, V>();
                                            dimensionSetTemp.putAll(dimensionSet);
                                            var71_110 = "";
                                            if (sheetName.contains(" ")) {
                                                sheetName = sheetName.replace(" ", "|");
                                            } else if (sheetName.contains("_")) {
                                                sheetName = sheetName.replace("_", "|");
                                            } else if (sheetName.contains("&")) {
                                                sheetName = sheetName.replace("&", "|");
                                            }
                                            if (this.iFormTypeApplyService.enableNrFormTypeMgr()) {
                                                var71_111 = this.getEntityKeyOfGZW(iEntityTable, sheetName);
                                            }
                                            if (StringUtils.isEmpty((String)var71_112)) {
                                                var71_113 = this.getKey(dwEntity, haveQuery, sheetName, formSchemeKey, true, dataTime);
                                            }
                                            if (!StringUtils.isEmpty((String)var71_114)) break block209;
                                            sheetResult = new ImportResultSheetObject();
                                            sheetError = sheetResult.getSheetError();
                                            if (sheetError == null) {
                                                sheetError = new ResultErrorInfo();
                                                sheetResult.setSheetError(sheetError);
                                            }
                                            sheetResult.getSheetError().setErrorInfo("\u6587\u4ef6\u5339\u914d\u5931\u8d25\uff1a\u5f53\u524d\u7ef4\u5ea6\u4e0b\uff0c\u6839\u636esheet\u9875\u540d\u79f0\u6ca1\u6709\u627e\u5230\u5bf9\u5e94\u7684\u5355\u4f4d\uff01");
                                            sheetResult.getSheetError().setErrorCode(ErrorCode.SHEETERROR);
                                            sheetResult.setSheetName(sheetName);
                                            item = new ExcelImportResultItem();
                                            item.setErrorCode(ErrorCode.FILEERROR.getErrorCodeMsg());
                                            item.setErrorInfo("\u6587\u4ef6\u5339\u914d\u5931\u8d25\uff1a\u5f53\u524d\u7ef4\u5ea6\u4e0b\uff0c\u6839\u636esheet\u9875\u540d\u79f0\u6ca1\u6709\u627e\u5230\u5bf9\u5e94\u7684\u5355\u4f4d\uff01");
                                            item.setFileName(zipExcelDimensionObject.getFileName());
                                            item.setSheetName(sheetName);
                                            resultObject.getFails().add(item);
                                            resultObject.setSuccess(false);
                                            break block208;
                                        }
                                        tempCompany = new DimensionValue();
                                        dwDimensionName = this.entityMetaService.getDimensionName(dwEntity.getEntityId());
                                        if (null != dwEntity) {
                                            tempCompany.setName(dwDimensionName);
                                            tempCompany.setValue((String)var71_114);
                                            dimensionSetTemp.put(dwDimensionName, tempCompany);
                                        }
                                        if (sortDimensionNames != null && !sortDimensionNames.isEmpty() && this.iFormSchemeService.enableAdjustPeriod(formSchemeKey)) {
                                            adjustPeriodsList = this.iFormSchemeService.queryAdjustPeriods(formSchemeKey, ((DimensionValue)dimensionSetTemp.get("DATATIME")).getValue());
                                            adjustPeriodCode = "";
                                            for (AdjustPeriod adjustPeriod : adjustPeriodsList) {
                                                if (!adjustPeriod.getTitle().equals(sortDimensionNames.get(0))) continue;
                                                adjustPeriodCode = adjustPeriod.getCode();
                                            }
                                            adjustDim = new DimensionValue();
                                            adjustDim.setName("ADJUST");
                                            dimensionSetTemp.put("ADJUST", adjustDim);
                                            if (StringUtils.isNotEmpty((String)adjustPeriodCode)) {
                                                sortDimensionNames.remove(0);
                                                adjustDim.setValue(adjustPeriodCode);
                                            } else if (dimensionSet.containsKey("ADJUST") && StringUtils.isNotEmpty((String)((DimensionValue)dimensionSet.get("ADJUST")).getValue())) {
                                                adjustDim.setValue(((DimensionValue)dimensionSet.get("ADJUST")).getValue());
                                            } else {
                                                adjustDim.setValue("0");
                                            }
                                        }
                                        hasAllDimCatalogue = false;
                                        if (sortDimensionNames == null) ** GOTO lbl420
                                        if (sortDimensionNames.size() == noTimeAndCompanyIndex) {
                                            hasAllDimCatalogue = true;
                                        } else if (dimAttributeMap == null) {
                                            dimAttributeMap = this.dataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme()).stream().filter((Predicate<DataDimension>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$upload$0(com.jiuqi.nr.datascheme.api.DataDimension ), (Lcom/jiuqi/nr/datascheme/api/DataDimension;)Z)()).collect((Supplier<HashMap>)LambdaMetafactory.metafactory(null, null, null, ()Ljava/lang/Object;, <init>(), ()Ljava/util/HashMap;)(), (BiConsumer<HashMap, DataDimension>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;Ljava/lang/Object;)V, lambda$upload$1(java.util.HashMap com.jiuqi.nr.datascheme.api.DataDimension ), (Ljava/util/HashMap;Lcom/jiuqi/nr/datascheme/api/DataDimension;)V)(), (BiConsumer<HashMap, HashMap>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;Ljava/lang/Object;)V, putAll(java.util.Map<? extends K, ? extends V> ), (Ljava/util/HashMap;Ljava/util/HashMap;)V)());
                                            iterator = noTimeAndCompany.iterator();
                                            while (iterator.hasNext()) {
                                                entityViewDefine = (EntityViewDefine)iterator.next();
                                                attribute = (String)dimAttributeMap.get(entityViewDefine.getEntityId());
                                                if (!StringUtils.isNotEmpty((String)attribute) || entityModel.getAttribute(attribute).isMultival()) continue;
                                                singleValDims.add(entityViewDefine);
                                                singleDimNames.append(this.entityMetaService.getDimensionName(entityViewDefine.getEntityId())).append(";");
                                                iterator.remove();
                                            }
                                        }
                                        index = 0;
lbl314:
                                        // 2 sources

                                        while (index < sortDimensionNames.size()) {
                                            dimensionValue = sortDimensionNames.get(index);
                                            entityViewData = (EntityViewDefine)noTimeAndCompany.get(index);
                                            if (dimAttributeByReportDimMap.containsKey(index)) {
                                                dimAttributeByReportDim = (String)dimAttributeByReportDimMap.get(index);
                                                newEntityQuery = this.entityDataService.newEntityQuery();
                                                masterKeys = new DimensionValueSet();
                                                masterKeys.setValue("DATATIME", param.getDimensionSet().getValue("DATATIME"));
                                                masterKeys.setValue(this.entityMetaService.getDimensionName(dwEntityID), (Object)var71_114);
                                                newEntityQuery.setMasterKeys(masterKeys);
                                                entityView = this.entityViewRunTimeController.buildEntityView(dwEntityID);
                                                newEntityQuery.setEntityView(entityView);
                                                executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
                                                executorContext.setPeriodView(formScheme.getDateTime());
                                                executeFullBuild = newEntityQuery.executeFullBuild((IContext)executorContext);
                                                findByCode = executeFullBuild.findByCode((String)var71_114);
                                                if (findByCode != null && (asString = findByCode.getAsString(dimAttributeByReportDim)) != null && !asString.equals("")) {
                                                    dimensionValue = asString;
                                                }
                                            }
                                            if (!StringUtils.isEmpty((String)(isKey = this.getKey(entityViewData, haveQuery, dimensionValue, formSchemeKey, false, dataTime)))) ** GOTO lbl-1000
                                            this.failMatchDimension(resultObject, zipExcelDimensionObject, excelFileResult, fileName, workbook);
                                            if (null == workbook) ** GOTO lbl406
                                            ** GOTO lbl401
                                        }
                                        ** GOTO lbl420
                                    }
lbl338:
                                    // 2 sources

                                }
                                ** GOTO lbl560
                            }
                            catch (IOException e) {
                                block200: {
                                    block204: {
                                        block203: {
                                            block202: {
                                                block201: {
                                                    try {
                                                        UploadMultiServiceImpl.logger.error("\u5bfc\u5165\u5931\u8d25\u6709\u672a\u77e5\u5f02\u5e38\uff1a" + e.getMessage(), e);
                                                        excelFileResult.getFileError().setErrorCode(ErrorCode.SYSTEMERROR);
                                                        excelFileResult.getFileError().setErrorInfo(e.getMessage());
                                                        excelFileResult.setFileName(zipExcelDimensionObject.getFileName());
                                                        workBookError = ExcelErrorUtil.exportExcel(resultObject, excelFileResult, workbook);
                                                        var63_91 = null;
                                                        try {
                                                            out = new FileOutputStream(zipExcelDimensionObject.getFileAddress());
                                                            var65_96 = null;
                                                            try {
                                                                workBookError.write(out);
                                                            }
                                                            catch (Throwable var66_101) {
                                                                var65_96 = var66_101;
                                                                throw var66_101;
                                                            }
                                                            finally {
                                                                if (out != null) {
                                                                    if (var65_96 != null) {
                                                                        try {
                                                                            out.close();
                                                                        }
                                                                        catch (Throwable var66_100) {
                                                                            var65_96.addSuppressed(var66_100);
                                                                        }
                                                                    } else {
                                                                        out.close();
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        catch (Throwable var64_95) {
                                                            var63_91 = var64_95;
                                                            throw var64_95;
                                                        }
                                                        finally {
                                                            if (workBookError != null) {
                                                                if (var63_91 != null) {
                                                                    try {
                                                                        workBookError.close();
                                                                    }
                                                                    catch (Throwable var64_94) {
                                                                        var63_91.addSuppressed(var64_94);
                                                                    }
                                                                } else {
                                                                    workBookError.close();
                                                                }
                                                            }
                                                        }
                                                        dataImportLogger.importError("\u5bfc\u5165\u5931\u8d25\u6709\u672a\u77e5\u5f02\u5e38\uff1a" + e.getMessage());
                                                        if (null != workbook) {
                                                        }
                                                        break block200;
                                                    }
                                                    catch (Throwable var100_162) {
                                                        if (null != workbook) {
                                                            try {
                                                                workbook.close();
                                                            }
                                                            catch (IOException e) {
                                                                UploadMultiServiceImpl.logger.error("\u51fa\u9519\u539f\u56e0\uff1a{}", (Object)e.getMessage(), (Object)e);
                                                            }
                                                        }
                                                        try {
                                                            FileUtils.deleteDirectory(new File(copyPath));
                                                            throw var100_162;
                                                        }
                                                        catch (IOException e) {
                                                            UploadMultiServiceImpl.logger.error("\u5220\u9664\u4e34\u65f6\u76ee\u5f55\u3010{}\u3011\u65f6\u51fa\u9519\uff1a{}", new Object[]{copyPath, e.getMessage(), e});
                                                        }
                                                        throw var100_162;
                                                    }
lbl401:
                                                    // 1 sources

                                                    try {
                                                        workbook.close();
                                                    }
                                                    catch (IOException e) {
                                                        UploadMultiServiceImpl.logger.error("\u51fa\u9519\u539f\u56e0\uff1a{}", (Object)e.getMessage(), (Object)e);
                                                    }
lbl406:
                                                    // 3 sources

                                                    try {
                                                        FileUtils.deleteDirectory(new File(copyPath));
                                                    }
                                                    catch (IOException e) {
                                                        UploadMultiServiceImpl.logger.error("\u5220\u9664\u4e34\u65f6\u76ee\u5f55\u3010{}\u3011\u65f6\u51fa\u9519\uff1a{}", new Object[]{copyPath, e.getMessage(), e});
                                                    }
                                                    continue;
lbl-1000:
                                                    // 1 sources

                                                    {
                                                        dimensionName = this.entityMetaService.getDimensionName(entityViewData.getEntityId());
                                                        temp = new DimensionValue();
                                                        temp.setName(dimensionName);
                                                        temp.setValue(isKey);
                                                        dimensionSetTemp.put(dimensionName, temp);
                                                        ++index;
                                                        ** GOTO lbl314
lbl420:
                                                        // 2 sources

                                                        if (param.getDimensionSet().getValue("ADJUST") != null && !dimensionSetTemp.containsKey("ADJUST")) {
                                                            value = new DimensionValue();
                                                            value.setName("ADJUST");
                                                            value.setValue(param.getDimensionSet().getValue("ADJUST").toString());
                                                            dimensionSetTemp.put("ADJUST", value);
                                                        }
                                                        paramTemp = new UploadParam();
                                                        paramTemp.setFormSchemeKey(formSchemeKey);
                                                        paramTemp.setAppending(param.isAppending());
                                                        paramTemp.setRegionReadOnlyDataLinks(param.getRegionReadOnlyDataLinks());
                                                        builders = new DimensionCollectionBuilder();
                                                        dimensionValueSetTemp = DimensionValueSetUtil.getDimensionValueSet((Map)dimensionSetTemp);
                                                        for (j = 0; j < dimensionValueSetTemp.size(); ++j) {
                                                            if (singleDimNames.toString().contains(dimensionValueSetTemp.getName(j))) continue;
                                                            if (dwDimensionName.equals(dimensionValueSetTemp.getName(j))) {
                                                                dimensionProviderData = new DimensionProviderData(Collections.singletonList((String)dimensionValueSetTemp.getValue(j)), taskDefine.getDataScheme());
                                                                if (StringUtils.isNotEmpty((String)rowFilterExpression)) {
                                                                    dimensionProviderData.setFilter(rowFilterExpression);
                                                                }
                                                                dimensionProvider = this.dimensionProviderFactory.getDimensionProvider("PROVIDER_FILTERDWBYVERSION", dimensionProviderData);
                                                                builders.addVariableDW(dimensionValueSetTemp.getName(j), dwEntity.getEntityId(), dimensionProvider);
                                                                continue;
                                                            }
                                                            if (dataTimeName.equals(dimensionValueSetTemp.getName(j))) {
                                                                builders.setEntityValue(dimensionValueSetTemp.getName(j), dataTimeEntity.getEntityId(), new Object[]{dimensionValueSetTemp.getValue(j)});
                                                                continue;
                                                            }
                                                            builders.setEntityValue(dimensionValueSetTemp.getName(j), null, new Object[]{dimensionValueSetTemp.getValue(j)});
                                                        }
                                                        dimensionCombinations = builders.getCollection().getDimensionCombinations();
                                                        if (!dimensionCombinations.isEmpty()) ** GOTO lbl-1000
                                                        this.noOrgErrorDeal(excelFileResult, zipExcelDimensionObject, resultObject, fileName, workbook);
                                                        if (null == workbook) break block201;
                                                    }
                                                    try {
                                                        workbook.close();
                                                    }
                                                    catch (IOException e) {
                                                        UploadMultiServiceImpl.logger.error("\u51fa\u9519\u539f\u56e0\uff1a{}", (Object)e.getMessage(), (Object)e);
                                                    }
                                                }
                                                try {
                                                    FileUtils.deleteDirectory(new File(copyPath));
                                                }
                                                catch (IOException e) {
                                                    UploadMultiServiceImpl.logger.error("\u5220\u9664\u4e34\u65f6\u76ee\u5f55\u3010{}\u3011\u65f6\u51fa\u9519\uff1a{}", new Object[]{copyPath, e.getMessage(), e});
                                                }
                                                continue;
lbl-1000:
                                                // 1 sources

                                                {
                                                    splitDimensionValueList = new ArrayList<Map>();
                                                    for (DimensionCombination dimensionCombination : dimensionCombinations) {
                                                        dimensionSet2 = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionCombination.toDimensionValueSet());
                                                        splitDimensionValueList.add(dimensionSet2);
                                                    }
                                                    paramTemp.setDimensionSet((DimensionCombination)dimensionCombinations.get(0));
                                                    if (null != splitDimensionValueList && !splitDimensionValueList.isEmpty()) ** GOTO lbl-1000
                                                    this.failMatchDimension(resultObject, zipExcelDimensionObject, excelFileResult, fileName, workbook);
                                                    if (null == workbook) break block202;
                                                }
                                                try {
                                                    workbook.close();
                                                }
                                                catch (IOException e) {
                                                    UploadMultiServiceImpl.logger.error("\u51fa\u9519\u539f\u56e0\uff1a{}", (Object)e.getMessage(), (Object)e);
                                                }
                                            }
                                            try {
                                                FileUtils.deleteDirectory(new File(copyPath));
                                            }
                                            catch (IOException e) {
                                                UploadMultiServiceImpl.logger.error("\u5220\u9664\u4e34\u65f6\u76ee\u5f55\u3010{}\u3011\u65f6\u51fa\u9519\uff1a{}", new Object[]{copyPath, e.getMessage(), e});
                                            }
                                            continue;
lbl-1000:
                                            // 1 sources

                                            {
                                                fmdmDataDTO = new FMDMDataDTO();
                                                fmdmDataDTO.setDimensionValueSet(((DimensionCombination)dimensionCombinations.get(0)).toDimensionValueSet());
                                                fmdmDataDTO.setFormSchemeKey(formScheme.getKey());
                                                try {
                                                    fmdmDatas = this.fMDMDataService.list(fmdmDataDTO);
                                                    dimensionCombination = (DimensionCombination)dimensionCombinations.get(0);
                                                    dimensionNames = dimensionCombination.getNames();
                                                    dimCheck = false;
                                                    asString = dimensionNames.iterator();
                                                    while (asString.hasNext()) {
                                                        string = (String)asString.next();
                                                        value = dimensionCombination.getValue(string);
                                                        if (value != null && !value.equals("")) continue;
                                                        dimCheck = true;
                                                    }
                                                    if (fmdmDatas == null || fmdmDatas.isEmpty() || dimCheck) {
                                                        sheetResult = new ImportResultSheetObject();
                                                        sheetError = sheetResult.getSheetError();
                                                        if (sheetError == null) {
                                                            sheetError = new ResultErrorInfo();
                                                            sheetResult.setSheetError(sheetError);
                                                        }
                                                        sheetResult.getSheetError().setErrorInfo("\u6587\u4ef6\u5339\u914d\u5931\u8d25: \u6ca1\u6709\u627e\u5230\u5339\u914d\u7684\u7ef4\u5ea6");
                                                        sheetResult.getSheetError().setErrorCode(ErrorCode.SHEETERROR);
                                                        item = new ExcelImportResultItem();
                                                        item.setErrorCode(ErrorCode.FILEERROR.getErrorCodeMsg());
                                                        item.setErrorInfo("\u6587\u4ef6\u5339\u914d\u5931\u8d25: \u6ca1\u6709\u627e\u5230\u5339\u914d\u7684\u7ef4\u5ea6");
                                                        item.setFileName(zipExcelDimensionObject.getFileName());
                                                        resultObject.getFails().add(item);
                                                        resultObject.setSuccess(false);
                                                        if (null == workbook) break block203;
                                                    }
                                                    ** GOTO lbl-1000
                                                }
                                                catch (FMDMQueryException e) {
                                                    UploadMultiServiceImpl.logger.error("\u5c01\u9762\u4ee3\u7801\u6570\u636e\u67e5\u8be2\u51fa\u9519{}", (Object)e.getMessage(), (Object)e);
                                                    break block204;
                                                }
                                            }
                                            try {
                                                workbook.close();
                                            }
                                            catch (IOException e) {
                                                UploadMultiServiceImpl.logger.error("\u51fa\u9519\u539f\u56e0\uff1a{}", (Object)e.getMessage(), (Object)e);
                                            }
                                        }
                                        try {
                                            FileUtils.deleteDirectory(new File(copyPath));
                                        }
                                        catch (IOException e) {
                                            UploadMultiServiceImpl.logger.error("\u5220\u9664\u4e34\u65f6\u76ee\u5f55\u3010{}\u3011\u65f6\u51fa\u9519\uff1a{}", new Object[]{copyPath, e.getMessage(), e});
                                        }
                                        continue;
lbl-1000:
                                        // 1 sources

                                        {
                                            if (hasAllDimCatalogue) break block204;
                                            dimensionValueSet = ((DimensionCombination)dimensionCombinations.get(0)).toDimensionValueSet();
                                            for (EntityViewDefine singleValDim : singleValDims) {
                                                dimName = this.entityMetaService.getDimensionName(singleValDim.getEntityId());
                                                code = (String)attributeCodeMap.get(singleValDim.getEntityId());
                                                value = null;
                                                if (StringUtils.isEmpty((String)code)) {
                                                    if (fmdmAttributes == null) {
                                                        fmdmAttributes = this.fmdmAttributeService.list(fmdmAttributeDTO);
                                                    }
                                                    if ((hbdwlxAttribute = (IFMDMAttribute)fmdmAttributes.stream().filter((Predicate<IFMDMAttribute>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$upload$2(com.jiuqi.np.definition.facade.EntityViewDefine com.jiuqi.nr.fmdm.IFMDMAttribute ), (Lcom/jiuqi/nr/fmdm/IFMDMAttribute;)Z)((EntityViewDefine)singleValDim)).findFirst().orElse(null)) != null) {
                                                        code = hbdwlxAttribute.getCode();
                                                        value = (String)((IFMDMData)fmdmDatas.get(0)).getAsObject(code);
                                                    }
                                                } else {
                                                    value = (String)((IFMDMData)fmdmDatas.get(0)).getAsObject(code);
                                                }
                                                if (StringUtils.isEmpty((String)value)) {
                                                    value = ((DimensionValue)dimensionSet.get(dimName)).getValue();
                                                }
                                                dimensionValueSet.setValue(dimName, (Object)value);
                                            }
                                            paramTemp.setDimensionSet(new DimensionCombinationBuilder(dimensionValueSet).getCombination());
                                        }
                                    }
                                    sheetList.add(sheet);
                                    uploadParamList.add(paramTemp);
                                    ** continue;
lbl560:
                                    // 1 sources

                                    if (!sheetList.isEmpty()) {
                                        allDimensionSet = new HashMap<K, DimensionValue>();
                                        for (UploadParam uploadParam : uploadParamList) {
                                            dimensionSet2 = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)uploadParam.getDimensionSet().toDimensionValueSet());
                                            if (allDimensionSet.isEmpty()) {
                                                for (Map.Entry var71_115 : dimensionSet2.entrySet()) {
                                                    newDimension = new DimensionValue();
                                                    newDimension.setName(((DimensionValue)var71_115.getValue()).getName());
                                                    newDimension.setValue(((DimensionValue)var71_115.getValue()).getValue());
                                                    allDimensionSet.put(var71_115.getKey(), newDimension);
                                                }
                                                continue;
                                            }
                                            if (null == dwEntity) continue;
                                            dimensionName = this.entityMetaService.getDimensionName(dwEntity.getEntityId());
                                            var71_116 = (DimensionValue)dimensionSet2.get(dimensionName);
                                            allDimensionUnitValue = (DimensionValue)allDimensionSet.get(dimensionName);
                                            value = allDimensionUnitValue.getValue();
                                            value = ";" + var71_116.getValue();
                                            allDimensionUnitValue.setValue(value);
                                            allDimensionSet.put(dimensionName, allDimensionUnitValue);
                                        }
                                        importExcelFileResult = this.uploadExcelBaseService.upload(sheetList, zipExcelDimensionObject.getFileName(), uploadParamList, asyncTaskMonitor, begin, oneSpan, dataAccessService, message, dataImportLogger);
                                        importExcelFileResult.getImportResultSheetObjectList().addAll(excelFileResult.getImportResultSheetObjectList());
                                        importExcelFileResult.setFileName(zipExcelDimensionObject.getFileName());
                                        if (!importExcelFileResult.isSuccessIs().booleanValue()) {
                                            try {
                                                workBookError = ExcelErrorUtil.exportExcel(resultObject, importExcelFileResult, workbook);
                                                var69_105 = null;
                                                try {
                                                    filePath = zipExcelDimensionObject.getFileAddress();
                                                    var71_117 = filePath.replace(param.getFilePath(), param.getFilePath() + uid);
                                                    errorFile = new File(var71_117);
                                                    try {
                                                        errorFile.getParentFile().mkdirs();
                                                        errorFile.createNewFile();
                                                    }
                                                    catch (Exception e) {
                                                        UploadMultiServiceImpl.logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                                                    }
                                                    out = new FileOutputStream(errorFile);
                                                    var74_121 = null;
                                                    try {
                                                        workBookError.write(out);
                                                    }
                                                    catch (Throwable var75_126) {
                                                        var74_121 = var75_126;
                                                        throw var75_126;
                                                    }
                                                    finally {
                                                        if (out != null) {
                                                            if (var74_121 != null) {
                                                                try {
                                                                    out.close();
                                                                }
                                                                catch (Throwable var75_125) {
                                                                    var74_121.addSuppressed(var75_125);
                                                                }
                                                            } else {
                                                                out.close();
                                                            }
                                                        }
                                                    }
                                                }
                                                catch (Throwable var70_108) {
                                                    var69_105 = var70_108;
                                                    throw var70_108;
                                                }
                                                finally {
                                                    if (workBookError != null) {
                                                        if (var69_105 != null) {
                                                            try {
                                                                workBookError.close();
                                                            }
                                                            catch (Throwable var70_107) {
                                                                var69_105.addSuppressed(var70_107);
                                                            }
                                                        } else {
                                                            workBookError.close();
                                                        }
                                                    }
                                                }
                                            }
                                            catch (Exception e) {
                                                UploadMultiServiceImpl.logger.error("\u5bfc\u5165\u5931\u8d25\u6709\u672a\u77e5\u5f02\u5e38\uff1a" + e.getMessage(), e);
                                            }
                                        } else {
                                            this.deleteFile(zipExcelDimensionObject, deleteList);
                                            importCancledResult.getSuccessFiles().add(this.getFilePathInZip(zipExcelDimensionObject));
                                        }
                                        relationDimensions.addAll(importExcelFileResult.getRelationDimensions());
                                    }
                                    if (null != workbook) {
                                        try {
                                            workbook.close();
                                        }
                                        catch (IOException e) {
                                            UploadMultiServiceImpl.logger.error("\u51fa\u9519\u539f\u56e0\uff1a{}", (Object)e.getMessage(), (Object)e);
                                        }
                                    }
                                    try {
                                        FileUtils.deleteDirectory(new File(copyPath));
                                    }
                                    catch (IOException e) {
                                        UploadMultiServiceImpl.logger.error("\u5220\u9664\u4e34\u65f6\u76ee\u5f55\u3010{}\u3011\u65f6\u51fa\u9519\uff1a{}", new Object[]{copyPath, e.getMessage(), e});
                                    }
                                    try {
                                        workbook.close();
                                    }
                                    catch (IOException e) {
                                        UploadMultiServiceImpl.logger.error("\u51fa\u9519\u539f\u56e0\uff1a{}", (Object)e.getMessage(), (Object)e);
                                    }
                                }
                                try {
                                    FileUtils.deleteDirectory(new File(copyPath));
                                }
                                catch (IOException e) {
                                    UploadMultiServiceImpl.logger.error("\u5220\u9664\u4e34\u65f6\u76ee\u5f55\u3010{}\u3011\u65f6\u51fa\u9519\uff1a{}", new Object[]{copyPath, e.getMessage(), e});
                                }
                            }
                            begin += oneSpan;
                        }
                        break block210;
                    }
                    iEntityTable = null;
                    if (this.iFormTypeApplyService.enableNrFormTypeMgr()) {
                        context = new ExecutorContext(this.dataDefinitionRuntimeController);
                        context.setPeriodView(formScheme.getDateTime());
                        dataTimeDimension = DimensionValueSetUtil.getDimensionValueSet(dataTime);
                        dataEntity = this.dataEntityService.getIEntityTable(dwEntity, context, dataTimeDimension, formSchemeKey);
                        iEntityTable = dataEntity.getEntityTable();
                    }
                    this.uploadExcelNameIsUnit(param, asyncTaskMonitor, resultObject, zipExcelDimensionObjectList, deleteList, haveQuery, formSchemeKey, noTimeAndCompany, dwEntity, dimensionSet, dataTime, begin, oneSpan, noTimeAndCompanyIndex, relationDimensions, uid, iEntityTable, dataAccessService, message, dimAttributeByReportDimMap, dataImportLogger);
                    if (this.checkAsyncTaskCancling(asyncTaskMonitor, importCancledResult)) {
                        dataImportLogger.cancelImport();
                        return resultObject;
                    }
                }
                if (null != (fails = resultObject.getFails()) && fails.size() > 0) {
                    patchNew = path.replace(param.getFilePath(), param.getFilePath() + uid);
                    fileDelete = new File(patchNew);
                    UploadMultiServiceImpl.deleteFile(fileDelete);
                    nextFils = fileDelete.listFiles();
                    source = nextFils[0].getPath();
                    excelInfoName = "excel\u5bfc\u5165\u5185\u5bb9.zip";
                    location = FilenameUtils.normalize(patchNew + BatchExportConsts.SEPARATOR + excelInfoName);
                    fileZop = new File(FilenameUtils.normalize(location));
                    if (!fileZop.exists()) {
                        fileZop.createNewFile();
                    }
                    outZip = new FileOutputStream(fileZop);
                    var53_71 = null;
                    try {
                        UploadMultiServiceImpl.toZip(source, outZip, deleteList);
                        resultObject.setLocation(location);
                    }
                    catch (Throwable var54_74) {
                        var53_71 = var54_74;
                        throw var54_74;
                    }
                    finally {
                        if (outZip != null) {
                            if (var53_71 != null) {
                                try {
                                    outZip.close();
                                }
                                catch (Throwable var54_73) {
                                    var53_71.addSuppressed(var54_73);
                                }
                            } else {
                                outZip.close();
                            }
                        }
                    }
                } else {
                    resultObject.setSuccess(true);
                }
                if (resultObject == null) return resultObject;
                resultObject.setRelationDimensions(relationDimensions);
                return resultObject;
            }
            item = new ExcelImportResultItem();
            item.setErrorCode(ErrorCode.FILEERROR.getErrorCodeMsg());
            item.setErrorInfo("zip\u4e2d\u6ca1\u6709excel\u6587\u4ef6");
            resultObject.getFails().add(item);
            return resultObject;
        }
        catch (Exception e) {
            UploadMultiServiceImpl.logger.error("\u5bfc\u5165\u5931\u8d25\u6709\u672a\u77e5\u5f02\u5e38\uff1a" + e.getMessage(), e);
            item = new ExcelImportResultItem();
            item.setErrorCode(ErrorCode.SYSTEMERROR.getErrorCodeMsg());
            item.setErrorInfo(e.getMessage());
            resultObject.getFails().add(item);
        }
        return resultObject;
    }

    private String getPeriodTitle(String formSchemeKey, String period) {
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        FormSchemeDefine formScheme = runTimeViewController.getFormScheme(formSchemeKey);
        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(formScheme.getDateTime());
        return periodProvider.getPeriodTitle(period);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void uploadExcelNameIsUnit(UploadParam param, AsyncTaskMonitor asyncTaskMonitor, ImportResultObject resultObject, List<ZipExcelDimensionObject> zipExcelDimensionObjectList, List<String> deleteList, Map<String, Map<String, String>> haveQuery, String formSchemeKey, List<EntityViewDefine> noTimeAndCompany, EntityViewDefine companyEntityView, Map<String, DimensionValue> dimensionSet, Map<String, DimensionValue> dataTime, double begin, double oneSpan, int noTimeAndCompanyIndex, List<Map<String, DimensionValue>> relationDimensions, String uid, IEntityTable iEntityTable, IDataAccessService dataAccessService, CommonMessage message, Map<Integer, String> dimAttributeByReportDimMap, DataImportLogger dataImportLogger) throws IOException {
        ImportCancledResult importCancledResult = message.getImportCancledResult();
        ArrayList<UploadExcelByUnit> canImportList = new ArrayList<UploadExcelByUnit>();
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formSchemeKey);
        EntityViewDefine dataTimeEntity = this.entityViewRunTimeController.buildEntityView(formScheme.getDateTime());
        TaskDefine taskDefine = this.runtimeView.queryTaskDefine(formScheme.getTaskKey());
        EntityViewDefine view = this.runtimeView.getViewByFormSchemeKey(formSchemeKey);
        String rowFilterExpression = null;
        if (view != null) {
            rowFilterExpression = view.getRowFilterExpression();
        }
        String dwEntityID = this.dataAccesslUtil.contextEntityId(formScheme.getDw());
        IEntityModel entityModel = null;
        ArrayList<EntityViewDefine> singleValDims = new ArrayList<EntityViewDefine>();
        StringBuilder singleDimNames = new StringBuilder();
        Map dimAttributeMap = null;
        HashMap attributeCodeMap = new HashMap();
        FMDMAttributeDTO fmdmAttributeDTO = new FMDMAttributeDTO();
        fmdmAttributeDTO.setFormSchemeKey(formSchemeKey);
        Collection fmdmAttributes = null;
        block11: for (ZipExcelDimensionObject zipExcelDimensionObject : zipExcelDimensionObjectList) {
            if (this.checkAsyncTaskCancling(asyncTaskMonitor, importCancledResult)) {
                dataImportLogger.cancelImport();
                return;
            }
            ImportResultExcelFileObject excelFileResult = new ImportResultExcelFileObject();
            try {
                Object asString;
                String dataTimeValue;
                DimensionValue periodDimensionValue;
                HashMap<String, DimensionValue> dimensionSetTemp;
                String companyNameFromExcel;
                String fileName;
                List<String> sortDimensionNames;
                UploadParam paramTemp;
                block69: {
                    paramTemp = new UploadParam();
                    paramTemp.setFormSchemeKey(formSchemeKey);
                    paramTemp.setAppending(param.isAppending());
                    paramTemp.setRegionReadOnlyDataLinks(param.getRegionReadOnlyDataLinks());
                    sortDimensionNames = zipExcelDimensionObject.getDimension();
                    fileName = zipExcelDimensionObject.getFileName();
                    companyNameFromExcel = fileName.substring(0, fileName.lastIndexOf("."));
                    if (companyNameFromExcel.contains(" ")) {
                        companyNameFromExcel = companyNameFromExcel.replace(" ", "|");
                    } else if (companyNameFromExcel.contains("_")) {
                        companyNameFromExcel = companyNameFromExcel.replace("_", "|");
                    } else if (companyNameFromExcel.contains("&")) {
                        companyNameFromExcel = companyNameFromExcel.replace("&", "|");
                    }
                    dimensionSetTemp = new HashMap<String, DimensionValue>();
                    periodDimensionValue = new DimensionValue();
                    IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(formScheme.getDateTime());
                    Map<String, List<IPeriodRow>> periodItemGroupByTitle = ExportUtil.getPeriodItemGroupByTitle(periodProvider);
                    try {
                        dataTimeValue = ExportUtil.getDateValue(zipExcelDimensionObject.getDate(), periodItemGroupByTitle);
                    }
                    catch (Exception e2) {
                        dataTimeValue = param.getDimensionSet().getValue("DATATIME").toString();
                        if (!this.iFormSchemeService.enableAdjustPeriod(formSchemeKey)) break block69;
                        DimensionValue adjustDimensionValue = new DimensionValue();
                        adjustDimensionValue.setName(ADJUST);
                        adjustDimensionValue.setValue((String)param.getDimensionSet().getValue(ADJUST));
                        dimensionSet.put(ADJUST, adjustDimensionValue);
                    }
                }
                periodDimensionValue.setValue(dataTimeValue);
                periodDimensionValue.setName("DATATIME");
                if (periodDimensionValue.getValue() != null && !periodDimensionValue.getValue().equals("")) {
                    dimensionSet.put("DATATIME", periodDimensionValue);
                } else if (dimensionSet.get("DATATIME").getValue() == null || dimensionSet.get("DATATIME").getValue().equals("")) {
                    periodDimensionValue.setValue(param.getDimensionSet().getPeriodDimensionValue().getValue().toString());
                    dimensionSet.put("DATATIME", periodDimensionValue);
                }
                dimensionSetTemp.putAll(dimensionSet);
                String companyKey = "";
                if (this.iFormTypeApplyService.enableNrFormTypeMgr()) {
                    companyKey = this.getEntityKeyOfGZW(iEntityTable, companyNameFromExcel);
                }
                if (StringUtils.isEmpty((String)companyKey)) {
                    companyKey = this.getKey(companyEntityView, haveQuery, companyNameFromExcel, formSchemeKey, true, dimensionSet);
                }
                if (StringUtils.isEmpty((String)companyKey)) {
                    this.noOrgErrorDeal(excelFileResult, zipExcelDimensionObject, resultObject, fileName, null);
                    continue;
                }
                DimensionValue tempCompany = new DimensionValue();
                String dimensionNametemp = this.entityMetaService.getDimensionName(companyEntityView.getEntityId());
                tempCompany.setName(dimensionNametemp);
                tempCompany.setValue(companyKey);
                dimensionSetTemp.put(dimensionNametemp, tempCompany);
                if (sortDimensionNames != null && !sortDimensionNames.isEmpty() && this.iFormSchemeService.enableAdjustPeriod(formSchemeKey)) {
                    List adjustPeriodsList = this.iFormSchemeService.queryAdjustPeriods(formSchemeKey, ((DimensionValue)dimensionSetTemp.get("DATATIME")).getValue());
                    String adjustPeriodCode = "";
                    for (AdjustPeriod adjustPeriod : adjustPeriodsList) {
                        if (!adjustPeriod.getTitle().equals(sortDimensionNames.get(0))) continue;
                        adjustPeriodCode = adjustPeriod.getCode();
                    }
                    DimensionValue adjustDim = new DimensionValue();
                    adjustDim.setName(ADJUST);
                    dimensionSetTemp.put(ADJUST, adjustDim);
                    if (StringUtils.isNotEmpty((String)adjustPeriodCode)) {
                        sortDimensionNames.remove(0);
                        adjustDim.setValue(adjustPeriodCode);
                    } else if (dimensionSet.containsKey(ADJUST) && StringUtils.isNotEmpty((String)dimensionSet.get(ADJUST).getValue())) {
                        adjustDim.setValue(dimensionSet.get(ADJUST).getValue());
                    } else {
                        adjustDim.setValue("0");
                    }
                }
                boolean hasAllDimCatalogue = false;
                if (sortDimensionNames != null) {
                    if (sortDimensionNames.size() == noTimeAndCompanyIndex) {
                        hasAllDimCatalogue = true;
                    } else if (entityModel == null) {
                        entityModel = this.entityMetaService.getEntityModel(dwEntityID);
                        dimAttributeMap = this.dataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme()).stream().filter(e -> e.getDimensionType().equals((Object)DimensionType.DIMENSION)).collect(HashMap::new, (map, e) -> map.put(e.getDimKey(), e.getDimAttribute()), HashMap::putAll);
                        Iterator<EntityViewDefine> iterator = noTimeAndCompany.iterator();
                        while (iterator.hasNext()) {
                            EntityViewDefine entityViewDefine = iterator.next();
                            String attribute = (String)dimAttributeMap.get(entityViewDefine.getEntityId());
                            if (!StringUtils.isNotEmpty((String)attribute) || entityModel.getAttribute(attribute).isMultival()) continue;
                            singleValDims.add(entityViewDefine);
                            singleDimNames.append(this.entityMetaService.getDimensionName(entityViewDefine.getEntityId())).append(";");
                            iterator.remove();
                        }
                    }
                    for (int index = 0; index < sortDimensionNames.size(); ++index) {
                        String isKey;
                        Object dimensionValue = sortDimensionNames.get(index);
                        EntityViewDefine entityViewData = noTimeAndCompany.get(index);
                        if (dimAttributeByReportDimMap.containsKey(index)) {
                            String dimAttributeByReportDim = dimAttributeByReportDimMap.get(index);
                            IEntityQuery newEntityQuery = this.entityDataService.newEntityQuery();
                            DimensionValueSet masterKeys = new DimensionValueSet();
                            masterKeys.setValue("DATATIME", param.getDimensionSet().getValue("DATATIME"));
                            masterKeys.setValue(this.entityMetaService.getDimensionName(dwEntityID), (Object)companyKey);
                            newEntityQuery.setMasterKeys(masterKeys);
                            EntityViewDefine entityView = this.entityViewRunTimeController.buildEntityView(dwEntityID);
                            newEntityQuery.setEntityView(entityView);
                            ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
                            executorContext.setPeriodView(formScheme.getDateTime());
                            IEntityTable executeFullBuild = newEntityQuery.executeFullBuild((IContext)executorContext);
                            IEntityRow findByCode = executeFullBuild.findByCode(companyKey);
                            if (findByCode != null && (asString = findByCode.getAsString(dimAttributeByReportDim)) != null && !((String)asString).equals("")) {
                                dimensionValue = asString;
                            }
                        }
                        if (StringUtils.isEmpty((String)(isKey = this.getKey(entityViewData, haveQuery, (String)dimensionValue, formSchemeKey, false, dataTime)))) {
                            this.failMatchDimension(resultObject, zipExcelDimensionObject, excelFileResult, fileName, null);
                            continue block11;
                        }
                        String dimensionName = this.entityMetaService.getDimensionName(entityViewData.getEntityId());
                        DimensionValue temp = new DimensionValue();
                        temp.setName(dimensionName);
                        temp.setValue(isKey);
                        dimensionSetTemp.put(dimensionName, temp);
                    }
                }
                if (this.iFormSchemeService.enableAdjustPeriod(formSchemeKey) && !dimensionSetTemp.containsKey(ADJUST)) {
                    DimensionValue adjustDim = new DimensionValue();
                    adjustDim.setName(ADJUST);
                    adjustDim.setValue("0");
                    dimensionSetTemp.put(ADJUST, adjustDim);
                }
                DimensionCollectionBuilder builders = new DimensionCollectionBuilder();
                DimensionValueSet dimensionValueSetTemp = DimensionValueSetUtil.getDimensionValueSet(dimensionSetTemp);
                for (int i = 0; i < dimensionValueSetTemp.size(); ++i) {
                    if (singleDimNames.toString().contains(dimensionValueSetTemp.getName(i))) continue;
                    if (dimensionNametemp.equals(dimensionValueSetTemp.getName(i))) {
                        DimensionProviderData dimensionProviderData = new DimensionProviderData(Collections.singletonList((String)dimensionValueSetTemp.getValue(i)), taskDefine.getDataScheme());
                        if (StringUtils.isNotEmpty((String)rowFilterExpression)) {
                            dimensionProviderData.setFilter(rowFilterExpression);
                        }
                        VariableDimensionValueProvider dimensionProvider = this.dimensionProviderFactory.getDimensionProvider("PROVIDER_FILTERDWBYVERSION", dimensionProviderData);
                        builders.addVariableDW(dimensionValueSetTemp.getName(i), companyEntityView.getEntityId(), dimensionProvider);
                        continue;
                    }
                    if ("DATATIME".equals(dimensionValueSetTemp.getName(i))) {
                        builders.setEntityValue(dimensionValueSetTemp.getName(i), dataTimeEntity.getEntityId(), new Object[]{dimensionValueSetTemp.getValue(i)});
                        continue;
                    }
                    builders.setEntityValue(dimensionValueSetTemp.getName(i), null, new Object[]{dimensionValueSetTemp.getValue(i)});
                }
                List dimensionCombinations = builders.getCollection().getDimensionCombinations();
                if (dimensionCombinations.isEmpty()) {
                    this.noOrgErrorDeal(excelFileResult, zipExcelDimensionObject, resultObject, fileName, null);
                    continue;
                }
                ArrayList<Map> splitDimensionValueList = new ArrayList<Map>();
                for (DimensionCombination dimensionCombination : dimensionCombinations) {
                    Map dimensionSet2 = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionCombination.toDimensionValueSet());
                    splitDimensionValueList.add(dimensionSet2);
                }
                paramTemp.setDimensionSet((DimensionCombination)dimensionCombinations.get(0));
                ArrayList errorDimensionList = new ArrayList();
                if (null == splitDimensionValueList || splitDimensionValueList.isEmpty()) {
                    this.failMatchDimension(resultObject, zipExcelDimensionObject, excelFileResult, fileName, null);
                    continue;
                }
                FMDMDataDTO fmdmDataDTO = new FMDMDataDTO();
                fmdmDataDTO.setDimensionValueSet(((DimensionCombination)dimensionCombinations.get(0)).toDimensionValueSet());
                fmdmDataDTO.setFormSchemeKey(formScheme.getKey());
                try {
                    List fmdmDatas = this.fMDMDataService.list(fmdmDataDTO);
                    DimensionCombination dimensionCombination = (DimensionCombination)dimensionCombinations.get(0);
                    Collection names = dimensionCombination.getNames();
                    boolean dimCheck = false;
                    asString = names.iterator();
                    while (asString.hasNext()) {
                        String string = (String)asString.next();
                        Object value = dimensionCombination.getValue(string);
                        if (value != null && !value.equals("")) continue;
                        dimCheck = true;
                    }
                    if (fmdmDatas == null || fmdmDatas.isEmpty() || dimCheck) {
                        ImportResultSheetObject sheetResult = new ImportResultSheetObject();
                        ResultErrorInfo sheetError = sheetResult.getSheetError();
                        if (sheetError == null) {
                            sheetError = new ResultErrorInfo();
                            sheetResult.setSheetError(sheetError);
                        }
                        sheetResult.getSheetError().setErrorInfo(fileDimError);
                        sheetResult.getSheetError().setErrorCode(ErrorCode.SHEETERROR);
                        ExcelImportResultItem item = new ExcelImportResultItem();
                        item.setErrorCode(ErrorCode.FILEERROR.getErrorCodeMsg());
                        item.setErrorInfo(fileDimError);
                        item.setFileName(zipExcelDimensionObject.getFileName());
                        resultObject.getFails().add(item);
                        resultObject.setSuccess(false);
                        continue;
                    }
                    if (!hasAllDimCatalogue) {
                        DimensionValueSet dimensionValueSet = ((DimensionCombination)dimensionCombinations.get(0)).toDimensionValueSet();
                        for (EntityViewDefine singleValDim : singleValDims) {
                            String dimName = this.entityMetaService.getDimensionName(singleValDim.getEntityId());
                            String code = (String)attributeCodeMap.get(singleValDim.getEntityId());
                            String value = null;
                            if (StringUtils.isEmpty((String)code)) {
                                IFMDMAttribute hbdwlxAttribute;
                                if (fmdmAttributes == null) {
                                    fmdmAttributes = this.fmdmAttributeService.list(fmdmAttributeDTO);
                                }
                                if ((hbdwlxAttribute = (IFMDMAttribute)fmdmAttributes.stream().filter(e -> e.getReferEntityId() != null && e.getReferEntityId().equals(singleValDim.getEntityId())).findFirst().orElse(null)) != null) {
                                    code = hbdwlxAttribute.getCode();
                                    value = (String)((IFMDMData)fmdmDatas.get(0)).getAsObject(code);
                                }
                            } else {
                                value = (String)((IFMDMData)fmdmDatas.get(0)).getAsObject(code);
                            }
                            if (StringUtils.isEmpty((String)value)) {
                                value = dimensionSet.get(dimName).getValue();
                            }
                            dimensionValueSet.setValue(dimName, (Object)value);
                        }
                        paramTemp.setDimensionSet(new DimensionCombinationBuilder(dimensionValueSet).getCombination());
                    }
                }
                catch (FMDMQueryException e3) {
                    logger.error("\u5c01\u9762\u4ee3\u7801\u6570\u636e\u67e5\u8be2\u51fa\u9519" + e3.getMessage(), e3);
                }
                UploadExcelByUnit uploadExcelByUnit = new UploadExcelByUnit();
                uploadExcelByUnit.setParam(paramTemp);
                uploadExcelByUnit.setZipExcelDimensionObject(zipExcelDimensionObject);
                canImportList.add(uploadExcelByUnit);
            }
            catch (Exception e4) {
                logger.error("\u5bfc\u5165\u5931\u8d25\u6709\u672a\u77e5\u5f02\u5e38\uff1a" + e4.getMessage(), e4);
                excelFileResult.getFileError().setErrorCode(ErrorCode.SYSTEMERROR);
                excelFileResult.getFileError().setErrorInfo(e4.getMessage());
                excelFileResult.setFileName(zipExcelDimensionObject.getFileName());
                this.writeErrorFile(null, zipExcelDimensionObject.getFile(), excelFileResult, resultObject);
            }
        }
        if (!canImportList.isEmpty()) {
            HashMap<String, List<String>> dimensionMap = new HashMap<String, List<String>>();
            for (UploadExcelByUnit uploadExcelByUnit : canImportList) {
                UploadParam tempParam = uploadExcelByUnit.getParam();
                Map tempDimension = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)tempParam.getDimensionSet().toDimensionValueSet());
                for (Map.Entry entry : tempDimension.entrySet()) {
                    String key = (String)entry.getKey();
                    List<String> dimensionValues = null;
                    if (dimensionMap.containsKey(key)) {
                        dimensionValues = (List)dimensionMap.get(key);
                    } else {
                        dimensionValues = new ArrayList();
                        dimensionMap.put(key, dimensionValues);
                    }
                    if (dimensionValues.contains(((DimensionValue)entry.getValue()).getValue())) continue;
                    dimensionValues.add(((DimensionValue)entry.getValue()).getValue());
                }
            }
            HashMap<String, DimensionValue> hashMap = new HashMap<String, DimensionValue>();
            for (Map.Entry entry : dimensionMap.entrySet()) {
                String key = (String)entry.getKey();
                List values = (List)entry.getValue();
                String valueString = this.listToString(values, ";");
                DimensionValue oneDimension = new DimensionValue();
                oneDimension.setName(key);
                oneDimension.setValue(valueString);
                hashMap.put(key, oneDimension);
            }
            for (UploadExcelByUnit uploadExcelByUnit : canImportList) {
                if (this.checkAsyncTaskCancling(asyncTaskMonitor, importCancledResult)) {
                    dataImportLogger.cancelImport();
                    return;
                }
                ImportResultExcelFileObject excelFileResult = new ImportResultExcelFileObject();
                ZipExcelDimensionObject zipExcelDimensionObject = uploadExcelByUnit.getZipExcelDimensionObject();
                File input = zipExcelDimensionObject.getFile();
                Workbook workbook = null;
                try {
                    workbook = ExcelImportUtil.create(input);
                    String fileName = zipExcelDimensionObject.getFileName();
                    excelFileResult = this.uploadExcelBaseService.upload(workbook, fileName, uploadExcelByUnit.getParam(), asyncTaskMonitor, begin, oneSpan, dataAccessService, message, dataImportLogger);
                    workbook.close();
                    if (!excelFileResult.isSuccessIs().booleanValue()) {
                        this.writeErrorFile(null, input, excelFileResult, resultObject);
                    } else {
                        workbook = null;
                        this.deleteFile(zipExcelDimensionObject, deleteList);
                        importCancledResult.getSuccessFiles().add(this.getFilePathInZip(uploadExcelByUnit.getZipExcelDimensionObject()));
                    }
                    relationDimensions.addAll(excelFileResult.getRelationDimensions());
                }
                catch (Exception e5) {
                    logger.error("\u5bfc\u5165\u5931\u8d25\u6709\u672a\u77e5\u5f02\u5e38\uff1a" + e5.getMessage(), e5);
                    excelFileResult.getFileError().setErrorCode(ErrorCode.SYSTEMERROR);
                    excelFileResult.getFileError().setErrorInfo(e5.getMessage());
                    excelFileResult.setFileName(zipExcelDimensionObject.getFileName());
                }
                finally {
                    if (null != workbook) {
                        workbook.close();
                    }
                }
                begin += oneSpan;
            }
        }
    }

    private void failMatchDimension(ImportResultObject resultObject, ZipExcelDimensionObject zipExcelDimensionObject, ImportResultExcelFileObject excelFileResult, String fileName, Workbook workbook) throws IOException, InvalidFormatException {
        excelFileResult.getFileError().setErrorInfo("\u6587\u4ef6\u5339\u914d\u5931\u8d25\uff1a\u6ca1\u6709\u627e\u5230\u5339\u914d\u7684\u7ef4\u5ea6");
        excelFileResult.getFileError().setErrorCode(ErrorCode.FILEERROR);
        excelFileResult.setFileName(fileName);
        this.writeErrorFile(workbook, zipExcelDimensionObject.getFile(), excelFileResult, resultObject);
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

    private String getKey(EntityViewDefine entityView, Map<String, Map<String, String>> haveQuery, String name, String formSchemeKey, boolean isCompany, Map<String, DimensionValue> dataTime) {
        FormSchemeDefine formSchemeDefine = this.runtimeView.getFormScheme(formSchemeKey);
        String dimensionName = this.entityMetaService.getDimensionName(entityView.getEntityId());
        if (name.contains("(") && name.endsWith(")")) {
            name = name.split("\\(")[0].trim();
        }
        String key = "";
        String search = name;
        Map<String, String> recordValues = haveQuery.get(entityView.getEntityId() + dimensionName + dataTime.get("DATATIME").getValue());
        if (null == recordValues) {
            recordValues = new HashMap<String, String>();
        }
        if (recordValues.containsKey(name)) {
            return recordValues.get(name);
        }
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(dataTime);
        DimensionValueSet dimensionValueSet1 = new DimensionValueSet();
        dimensionValueSet1.setValue("DATATIME", dimensionValueSet.getValue("DATATIME"));
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        context.setPeriodView(formSchemeDefine.getDateTime());
        IDataEntity dataEntity = null;
        try {
            dataEntity = this.dataEntityService.getIEntityTable(entityView, context, dimensionValueSet1, formSchemeKey);
        }
        catch (Exception e1) {
            logger.error(e1.getMessage());
        }
        ArrayList<IEntityRow> entityDataList = new ArrayList<IEntityRow>();
        if (dataEntity != null && dataEntity.getEntityTable() != null && !name.contains("|")) {
            IDataEntityRow allRow = dataEntity.getAllRow();
            List rowList = allRow.getRowList();
            for (IEntityRow entityData : rowList) {
                String title = entityData.getTitle();
                String code = entityData.getCode();
                if (StringUtils.isEmpty((String)title)) {
                    title = entityData.getTitle();
                }
                if (!name.equals(title) && !code.equals(search)) continue;
                entityDataList.add(entityData);
            }
            if (entityDataList.size() > 1) {
                for (IEntityRow entityData : entityDataList) {
                    if (!entityData.getCode().equals(name) && !entityData.getTitle().equals(name)) continue;
                    key = entityData.getEntityKeyData();
                }
            } else if (entityDataList.size() == 1 && entityDataList.get(0) != null) {
                key = ((IEntityRow)entityDataList.get(0)).getEntityKeyData();
            }
        } else if (isCompany && name.contains("|")) {
            String[] split = name.split("\\|");
            String[] stringArray = split;
            int n = stringArray.length;
            for (int i = 0; i < n; ++i) {
                String splitInfo;
                search = splitInfo = stringArray[i];
                if (dataEntity == null || dataEntity.getEntityTable() == null) {
                    try {
                        dataEntity = this.dataEntityService.getIEntityTable(entityView, context, dimensionValueSet, formSchemeKey);
                    }
                    catch (Exception e) {
                        logger.error(e.getMessage());
                    }
                }
                if (null == dataEntity || null == dataEntity.getEntityTable()) continue;
                IDataEntityRow allRow = dataEntity.getAllRow();
                List rowList = allRow.getRowList();
                for (IEntityRow entityData : rowList) {
                    String code = entityData.getCode();
                    String title = entityData.getTitle();
                    if (!code.equals(search) && !title.equals(search)) continue;
                    entityDataList.add(entityData);
                }
            }
            if (entityDataList.size() > 1) {
                for (IEntityRow entityData : entityDataList) {
                    for (String entityCode : split) {
                        if (!entityData.getCode().equals(entityCode)) continue;
                        key = entityData.getEntityKeyData();
                    }
                }
            } else if (entityDataList.size() == 1 && entityDataList.get(0) != null) {
                key = ((IEntityRow)entityDataList.get(0)).getEntityKeyData();
            }
        }
        recordValues.put(name, key);
        haveQuery.put(entityView.getEntityId() + dimensionName + dataTime.get("DATATIME").getValue(), recordValues);
        return key;
    }

    private Map<String, Object> fileList(String path, UploadParam param) {
        Integer reportImportNum;
        File rootFile = Paths.get(path, new String[0]).normalize().toFile();
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
        File[] fs = rootFile.listFiles();
        File rootPathFile = Paths.get(rootPath, new String[0]).normalize().toFile();
        if (rootPathFile.isFile()) {
            fs = new File[]{rootFile};
            rootPath = rootFile.getParent();
        }
        for (File f : fs) {
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
            String pathName = path.substring(rootPath.length() + 1).replace("\\", "/");
            String[] temp2 = pathName.split("/");
            pathName = this.findDate(temp2, pathName);
            String[] temp = pathName.split("/");
            String fileName = temp[temp.length - 1];
            if (!(fileName.endsWith(".xls") || fileName.endsWith(".xlsx") || fileName.endsWith(".et"))) {
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
            List allFormDefine = this.runtimeView.queryAllFormDefinesByFormScheme(param.getFormSchemeKey());
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
                if (!(!tempName.contains("\u5e74") || tempName.contains("\u671f") || tempName.contains("xls") || tempName.contains("xlsx") || tempName.contains("et"))) {
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

    private String uploadZip(File zipFile, String strDate) throws IOException {
        String descDir = zipFile.getParent() + BatchExportConsts.SEPARATOR;
        try (ZipFile zipNow = new ZipFile(zipFile, Charset.forName("GBK"));){
            File pathFile = new File(FilenameUtils.normalize(descDir));
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
                        String outPath = (descDir + zipEntryName).replace("\\", "/");
                        File file = new File(FilenameUtils.normalize(outPath.substring(0, outPath.lastIndexOf(47))));
                        if (!file.exists()) {
                            file.mkdirs();
                        }
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

    private void deleteFile(ZipExcelDimensionObject zipExcelDimensionObject, List<String> deleteList) {
        block3: {
            Path filePath = Paths.get(zipExcelDimensionObject.getFileAddress(), new String[0]);
            if (Files.exists(filePath, new LinkOption[0]) && Files.isRegularFile(filePath, new LinkOption[0])) {
                try {
                    Files.delete(filePath);
                }
                catch (IOException e) {
                    if (deleteList == null) break block3;
                    deleteList.add(filePath.toAbsolutePath().toString());
                }
            }
        }
    }

    private static void deleteFile(File file) {
        if (file.exists() && !file.isFile() && file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; ++i) {
                UploadMultiServiceImpl.deleteFile(files[i]);
            }
            file.delete();
        }
    }

    public static void toZip(String srcDir, OutputStream out, List<String> deleteList) {
        try (ZipOutputStream zos = new ZipOutputStream(out);){
            File sourceFile = new File(srcDir);
            UploadMultiServiceImpl.compress(sourceFile, zos, sourceFile.getName(), true, deleteList);
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
                try {
                    zos.putNextEntry(new ZipEntry(name));
                    try (FileInputStream in = new FileInputStream(sourceFile);){
                        int len;
                        while ((len = in.read(buf)) != -1) {
                            zos.write(buf, 0, len);
                        }
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
                        UploadMultiServiceImpl.compress(file, zos, name + "/" + file.getName(), KeepDirStructure, deleteList);
                        continue;
                    }
                    UploadMultiServiceImpl.compress(file, zos, file.getName(), KeepDirStructure, deleteList);
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

    private void noOrgErrorDeal(ImportResultExcelFileObject excelFileResult, ZipExcelDimensionObject zipExcelDimensionObject, ImportResultObject resultObject, String fileName, Workbook workbook) throws Exception {
        excelFileResult.getFileError().setErrorInfo("\u6587\u4ef6\u5339\u914d\u5931\u8d25\uff1a\u5f53\u524d\u7ef4\u5ea6\u4e0b\uff0c\u6839\u636eexcel\u540d\u79f0\u6ca1\u6709\u627e\u5230\u5bf9\u5e94\u7684\u5355\u4f4d\uff01");
        excelFileResult.getFileError().setErrorCode(ErrorCode.FILEERROR);
        excelFileResult.setFileName(fileName);
        this.writeErrorFile(workbook, zipExcelDimensionObject.getFile(), excelFileResult, resultObject);
    }

    private boolean checkAsyncTaskCancling(AsyncTaskMonitor asyncTaskMonitor, ImportCancledResult importCancledResult) {
        if (asyncTaskMonitor != null && asyncTaskMonitor.isCancel()) {
            importCancledResult.setProgress(asyncTaskMonitor.getLastProgress());
            return true;
        }
        return false;
    }

    private String getFilePathInZip(ZipExcelDimensionObject zipExcelDimensionObject) {
        StringBuilder path = new StringBuilder();
        path.append(zipExcelDimensionObject.getDate()).append(File.separator);
        zipExcelDimensionObject.getDimension().forEach(e -> path.append((String)e).append(File.separator));
        path.append(zipExcelDimensionObject.getFileName());
        return path.toString();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    private void writeErrorFile(Workbook readWB, File writeFile, ImportResultExcelFileObject excelFileResult, ImportResultObject resultObject) {
        block50: {
            String copyPath;
            block48: {
                copyPath = null;
                if (readWB == null) {
                    copyPath = writeFile.getAbsolutePath().replace(writeFile.getName(), "") + UUID.randomUUID();
                    File copyFile = Paths.get(copyPath + BatchExportConsts.SEPARATOR + writeFile.getName(), new String[0]).normalize().toFile();
                    FileUtils.copyFile(writeFile, copyFile);
                    readWB = ExcelImportUtil.create(copyFile);
                }
                try (Workbook workbook = ExcelErrorUtil.exportExcel(resultObject, excelFileResult, readWB);
                     FileOutputStream out = new FileOutputStream(writeFile);){
                    workbook.write(out);
                }
                catch (Exception e) {
                    excelFileResult.getFileError().setErrorCode(ErrorCode.SYSTEMERROR);
                    excelFileResult.getFileError().setErrorInfo(e.getMessage());
                    excelFileResult.setFileName(writeFile.getName());
                }
                if (null == readWB) break block48;
                try {
                    readWB.close();
                }
                catch (IOException e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a{}", (Object)e.getMessage(), (Object)e);
                }
            }
            if (StringUtils.isNotEmpty((String)copyPath)) {
                try {
                    FileUtils.deleteDirectory(new File(copyPath));
                }
                catch (IOException e) {
                    logger.error("\u5220\u9664\u4e34\u65f6\u76ee\u5f55\u3010{}\u3011\u65f6\u51fa\u9519\uff1a{}", copyPath, e.getMessage(), e);
                }
            }
            break block50;
            catch (Exception e) {
                block49: {
                    try {
                        logger.error("\u6784\u5efa\u9519\u8bef\u6587\u4ef6\u65f6\u53d1\u751f\u5f02\u5e38\uff1a{}", (Object)e.getMessage(), (Object)e);
                        if (null == readWB) break block49;
                    }
                    catch (Throwable throwable) {
                        if (null != readWB) {
                            try {
                                readWB.close();
                            }
                            catch (IOException e2) {
                                logger.error("\u51fa\u9519\u539f\u56e0\uff1a{}", (Object)e2.getMessage(), (Object)e2);
                            }
                        }
                        if (StringUtils.isNotEmpty(copyPath)) {
                            try {
                                FileUtils.deleteDirectory(new File(copyPath));
                            }
                            catch (IOException e3) {
                                logger.error("\u5220\u9664\u4e34\u65f6\u76ee\u5f55\u3010{}\u3011\u65f6\u51fa\u9519\uff1a{}", copyPath, e3.getMessage(), e3);
                            }
                        }
                        throw throwable;
                    }
                    try {
                        readWB.close();
                    }
                    catch (IOException e4) {
                        logger.error("\u51fa\u9519\u539f\u56e0\uff1a{}", (Object)e4.getMessage(), (Object)e4);
                    }
                }
                if (StringUtils.isNotEmpty(copyPath)) {
                    try {
                        FileUtils.deleteDirectory(new File(copyPath));
                    }
                    catch (IOException e5) {
                        logger.error("\u5220\u9664\u4e34\u65f6\u76ee\u5f55\u3010{}\u3011\u65f6\u51fa\u9519\uff1a{}", copyPath, e5.getMessage(), e5);
                    }
                }
            }
        }
    }

    private static /* synthetic */ boolean lambda$upload$2(EntityViewDefine singleValDim, IFMDMAttribute e) {
        return e.getReferEntityId() != null && e.getReferEntityId().equals(singleValDim.getEntityId());
    }

    private static /* synthetic */ void lambda$upload$1(HashMap map, DataDimension e) {
        map.put(e.getDimKey(), e.getDimAttribute());
    }

    private static /* synthetic */ boolean lambda$upload$0(DataDimension e) {
        return e.getDimensionType().equals((Object)DimensionType.DIMENSION);
    }
}

