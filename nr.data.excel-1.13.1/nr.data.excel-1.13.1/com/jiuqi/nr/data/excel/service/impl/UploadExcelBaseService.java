/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.Html
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.definition.common.Consts$EntityField
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
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
 *  com.jiuqi.nr.common.importdata.ResultErrorInfo
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.conditionalstyle.controller.IConditionalStyleController
 *  com.jiuqi.nr.conditionalstyle.facade.ConditionalStyle
 *  com.jiuqi.nr.data.access.param.EntityDimData
 *  com.jiuqi.nr.data.access.param.IAccessResult
 *  com.jiuqi.nr.data.access.param.IBatchAccessResult
 *  com.jiuqi.nr.data.access.service.IDataAccessService
 *  com.jiuqi.nr.data.access.util.DataAccesslUtil
 *  com.jiuqi.nr.data.common.CommonMessage
 *  com.jiuqi.nr.data.common.logger.DataImportLogger
 *  com.jiuqi.nr.data.common.param.ReadOnlyContext
 *  com.jiuqi.nr.data.common.service.IRegionDataLinkReadOnlyService
 *  com.jiuqi.nr.datacrud.ClearInfoBuilder
 *  com.jiuqi.nr.datacrud.IDataValue
 *  com.jiuqi.nr.datacrud.IRegionDataSet
 *  com.jiuqi.nr.datacrud.IRowData
 *  com.jiuqi.nr.datacrud.ISaveInfo
 *  com.jiuqi.nr.datacrud.LinkSort
 *  com.jiuqi.nr.datacrud.PageInfo
 *  com.jiuqi.nr.datacrud.ParseReturnRes
 *  com.jiuqi.nr.datacrud.QueryInfoBuilder
 *  com.jiuqi.nr.datacrud.ReturnRes
 *  com.jiuqi.nr.datacrud.SaveDataBuilder
 *  com.jiuqi.nr.datacrud.SaveDataBuilderFactory
 *  com.jiuqi.nr.datacrud.SaveResItem
 *  com.jiuqi.nr.datacrud.SaveReturnRes
 *  com.jiuqi.nr.datacrud.SortMode
 *  com.jiuqi.nr.datacrud.api.IDataQueryService
 *  com.jiuqi.nr.datacrud.api.IDataService
 *  com.jiuqi.nr.datacrud.impl.out.CrudException
 *  com.jiuqi.nr.datacrud.impl.out.CrudOperateException
 *  com.jiuqi.nr.datacrud.impl.out.CrudSaveException
 *  com.jiuqi.nr.datacrud.impl.parse.strategy.DecimalParseStrategy
 *  com.jiuqi.nr.datacrud.impl.parse.strategy.FormatEnumParseStrategy
 *  com.jiuqi.nr.datacrud.spi.TypeParseStrategy
 *  com.jiuqi.nr.datacrud.util.TypeStrategyUtil
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory
 *  com.jiuqi.nr.dataservice.core.log.LogDimensionCollection
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.BigDataDefine
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.RegionSettingDefine
 *  com.jiuqi.nr.definition.facade.RegionTabSettingDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.util.EntityDefaultValue
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.FMDMAttributeDTO
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.fmdm.IFMDMAttributeService
 *  com.jiuqi.nr.io.params.base.RegionData
 *  com.jiuqi.nr.io.params.base.TableContext
 *  com.jiuqi.nr.io.params.input.ExpViewFields
 *  com.jiuqi.nr.io.params.input.OptTypes
 *  com.jiuqi.nr.io.params.output.ExportFieldDefine
 *  com.jiuqi.nr.io.sb.dataset.impl.SBRegionDataSet
 *  com.jiuqi.nr.io.service.IOEntityIsolateCondition
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.graphics.Point
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.data.excel.service.impl;

import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.Html;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
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
import com.jiuqi.nr.common.importdata.ResultErrorInfo;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.conditionalstyle.controller.IConditionalStyleController;
import com.jiuqi.nr.conditionalstyle.facade.ConditionalStyle;
import com.jiuqi.nr.data.access.param.EntityDimData;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.param.IBatchAccessResult;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.data.common.CommonMessage;
import com.jiuqi.nr.data.common.logger.DataImportLogger;
import com.jiuqi.nr.data.common.param.ReadOnlyContext;
import com.jiuqi.nr.data.common.service.IRegionDataLinkReadOnlyService;
import com.jiuqi.nr.data.excel.exception.ImportContainManyEmptyRowException;
import com.jiuqi.nr.data.excel.extend.ICheckImportData;
import com.jiuqi.nr.data.excel.param.CommonInitData;
import com.jiuqi.nr.data.excel.param.DataInfo;
import com.jiuqi.nr.data.excel.param.ExcelRule;
import com.jiuqi.nr.data.excel.param.ParseParam;
import com.jiuqi.nr.data.excel.param.RegionTab;
import com.jiuqi.nr.data.excel.param.UploadParam;
import com.jiuqi.nr.data.excel.param.bean.SheetNameOfFormResultInfo;
import com.jiuqi.nr.data.excel.param.upload.RegionMatchInfo;
import com.jiuqi.nr.data.excel.param.upload.ReportFeature;
import com.jiuqi.nr.data.excel.param.upload.ReportLinkDataCache;
import com.jiuqi.nr.data.excel.param.upload.ReportMatchResult;
import com.jiuqi.nr.data.excel.param.upload.Sheet2GridAdapter;
import com.jiuqi.nr.data.excel.utils.DataExcelUtils;
import com.jiuqi.nr.data.excel.utils.ExcelImportUtil;
import com.jiuqi.nr.datacrud.ClearInfoBuilder;
import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IRegionDataSet;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.ISaveInfo;
import com.jiuqi.nr.datacrud.LinkSort;
import com.jiuqi.nr.datacrud.PageInfo;
import com.jiuqi.nr.datacrud.ParseReturnRes;
import com.jiuqi.nr.datacrud.QueryInfoBuilder;
import com.jiuqi.nr.datacrud.ReturnRes;
import com.jiuqi.nr.datacrud.SaveDataBuilder;
import com.jiuqi.nr.datacrud.SaveDataBuilderFactory;
import com.jiuqi.nr.datacrud.SaveResItem;
import com.jiuqi.nr.datacrud.SaveReturnRes;
import com.jiuqi.nr.datacrud.SortMode;
import com.jiuqi.nr.datacrud.api.IDataQueryService;
import com.jiuqi.nr.datacrud.api.IDataService;
import com.jiuqi.nr.datacrud.impl.out.CrudException;
import com.jiuqi.nr.datacrud.impl.out.CrudOperateException;
import com.jiuqi.nr.datacrud.impl.out.CrudSaveException;
import com.jiuqi.nr.datacrud.impl.parse.strategy.DecimalParseStrategy;
import com.jiuqi.nr.datacrud.impl.parse.strategy.FormatEnumParseStrategy;
import com.jiuqi.nr.datacrud.spi.TypeParseStrategy;
import com.jiuqi.nr.datacrud.util.TypeStrategyUtil;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory;
import com.jiuqi.nr.dataservice.core.log.LogDimensionCollection;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.BigDataDefine;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.RegionSettingDefine;
import com.jiuqi.nr.definition.facade.RegionTabSettingDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.util.EntityDefaultValue;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import com.jiuqi.nr.io.params.base.RegionData;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.io.params.input.ExpViewFields;
import com.jiuqi.nr.io.params.input.OptTypes;
import com.jiuqi.nr.io.params.output.ExportFieldDefine;
import com.jiuqi.nr.io.sb.dataset.impl.SBRegionDataSet;
import com.jiuqi.nr.io.service.IOEntityIsolateCondition;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class UploadExcelBaseService {
    private static final Logger logger = LoggerFactory.getLogger(UploadExcelBaseService.class);
    public static final String SEPARATOR_ONE = " ";
    public static final String SEPARATOR_TWO = "_";
    public static final String SEPARATOR_THREE = "&";
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private IDataService iDataService;
    @Autowired
    private IDataQueryService dataQueryService;
    @Autowired
    private SaveDataBuilderFactory saveDataBuilderFactory;
    @Autowired
    private INvwaSystemOptionService iNvwaSystemOptionService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    @Qualifier(value="nr")
    private I18nHelper i18nHelper;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IFMDMAttributeService fMDMAttributeService;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private ITaskOptionController taskOptionController;
    @Autowired
    private INvwaSystemOptionService nvwaSystemOptionService;
    @Autowired
    private DataAccesslUtil dataAccesslUtil;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private DimensionProviderFactory dimensionProviderFactory;
    @Autowired
    private TypeStrategyUtil typeStrategyUtil;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IConditionalStyleController conditionalStyleController;
    @Autowired(required=false)
    private List<IRegionDataLinkReadOnlyService> readOnlyServices;
    @Autowired(required=false)
    private List<ICheckImportData> checkImportDataList;
    private String reportRes = "success";
    private String reportMessage = "message";
    private String JtableDataStr = "JtableData";
    private static final String IS_MATCH_REPORT = "ismatchreport";
    private static final String MATCH_REPORT_RESULT = "matchreportresult";
    private static final String IMPORT_INVALID_DATA = "IMPORT_INVALID_DATA";
    private static final String RECORDKEY = "RECORDKEY";
    private String[] chinaChars = new String[]{"\uff08", "\uff09", "(", ")"};
    @Autowired(required=false)
    private IOEntityIsolateCondition entityIsolateCondition;
    static Map<String, String> FILETYPEMAP = new HashMap<String, String>();

    public ImportResultExcelFileObject upload(List<Sheet> sheetList, String excelName, List<UploadParam> params, AsyncTaskMonitor asyncTaskMonitor, double begin, double oneSpan, IDataAccessService dataAccessService, CommonMessage message, DataImportLogger dataImportLogger) {
        Map<String, String> sheetNameToCompanyNameMap = new HashMap<String, String>();
        for (Sheet sheet : sheetList) {
            if (!"(\u9875\u540d\u6620\u5c04\u8868)".equals(sheet.getSheetName())) continue;
            sheetNameToCompanyNameMap = DataExcelUtils.sheetNameToCompanyName(sheet);
            break;
        }
        return this.uploadSheet(sheetList, excelName, params, asyncTaskMonitor, begin, oneSpan, true, sheetNameToCompanyNameMap, dataAccessService, message, dataImportLogger);
    }

    public ImportResultExcelFileObject upload(Workbook workbook, String fileName, UploadParam param, AsyncTaskMonitor asyncTaskMonitor, double begin, double span, IDataAccessService dataAccessService, CommonMessage message, DataImportLogger dataImportLogger) {
        Map<String, String> sheetNameToCompanyNameMap = null;
        int sheetCount = workbook.getNumberOfSheets();
        ArrayList<Sheet> sheetList = new ArrayList<Sheet>();
        ArrayList<UploadParam> params = new ArrayList<UploadParam>();
        for (int i = 0; i < sheetCount; ++i) {
            Sheet sheet = workbook.getSheetAt(i);
            sheetList.add(sheet);
            String sheetName = sheet.getSheetName();
            if ("(\u9875\u540d\u6620\u5c04\u8868)".equals(sheetName)) {
                sheetNameToCompanyNameMap = DataExcelUtils.sheetNameToCompanyName(sheet);
            }
            if (param.getExcelRule() != null) {
                ExcelRule excelRule = param.getExcelRule();
                ParseParam parseParam = new ParseParam();
                parseParam.setDirectory(param.getFilePath());
                parseParam.setFileName(fileName.split("\\.")[0]);
                FormSchemeDefine formSchemeDefine = this.runtimeView.getFormScheme(param.getFormSchemeKey());
                parseParam.setFormSchemeDefine(formSchemeDefine);
                parseParam.setSheetName(sheetName);
                DataInfo dataInfo = excelRule.parseDataInfo(parseParam);
                UploadParam param1 = new UploadParam();
                param1.setAppending(param.isAppending());
                param1.setDimensionSet(dataInfo.getDimensionCombination());
                param1.setExcelRule(param.getExcelRule());
                param1.setFilePath(param.getFilePath());
                param1.setFormSchemeKey(param.getFormSchemeKey());
                param1.setRegionReadOnlyDataLinks(param.getRegionReadOnlyDataLinks());
                params.add(param1);
                continue;
            }
            params.add(param);
        }
        return this.uploadSheet(sheetList, fileName, params, asyncTaskMonitor, begin, span, false, sheetNameToCompanyNameMap, dataAccessService, message, dataImportLogger);
    }

    private ImportResultExcelFileObject uploadSheet(List<Sheet> sheetList, String excelName, List<UploadParam> params, AsyncTaskMonitor asyncTaskMonitor, double begin, double oneSpan, boolean isExcelName, Map<String, String> sheetNameToCompanyNameMap, IDataAccessService dataAccessService, CommonMessage message, DataImportLogger dataImportLogger) {
        ImportResultExcelFileObject resObject = new ImportResultExcelFileObject();
        resObject.setFileName(excelName);
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(params.get(0).getFormSchemeKey());
        HashMap<String, FormDefine> formDefineMap = null;
        ArrayList<String> successDatas = new ArrayList<String>();
        try {
            String[] splitTop = null;
            double tempSpan = oneSpan / (double)sheetList.size();
            String expandedName = excelName.replace(".xlsx", "").replace(".xls", "").replace(".et", "");
            if (isExcelName) {
                splitTop = excelName.contains(SEPARATOR_ONE) ? expandedName.split(SEPARATOR_ONE) : (excelName.contains(SEPARATOR_THREE) ? expandedName.split(SEPARATOR_THREE) : (excelName.contains(SEPARATOR_TWO) ? expandedName.split(SEPARATOR_TWO) : expandedName.split(SEPARATOR_ONE)));
            }
            for (int i = 0; i < sheetList.size(); ++i) {
                block31: {
                    ImportResultSheetObject sheetItem;
                    String[] split;
                    Sheet sheet = sheetList.get(i);
                    String sheetName = sheet.getSheetName();
                    if ("(\u9875\u540d\u6620\u5c04\u8868)".equals(sheetName) || sheetName.startsWith("(\u9519\u8bef\u4fe1\u606f\u6620\u5c04\u8868)") || "HIDDENSHEETNAME".equals(sheetName)) continue;
                    if (null != sheetNameToCompanyNameMap && sheetNameToCompanyNameMap.containsKey(sheetName)) {
                        sheetName = sheetNameToCompanyNameMap.get(sheetName);
                    }
                    String sysSeparator = "";
                    if (isExcelName) {
                        split = splitTop;
                        sheetName = expandedName;
                    } else {
                        sysSeparator = this.getSysSeparator();
                        split = sysSeparator != null && !sysSeparator.isEmpty() && sheetName.contains(sysSeparator) ? sheetName.split(sysSeparator) : (sheetName.contains(SEPARATOR_THREE) ? sheetName.split(SEPARATOR_THREE) : (sheetName.contains(SEPARATOR_TWO) ? sheetName.split(SEPARATOR_TWO) : sheetName.split(SEPARATOR_ONE)));
                    }
                    boolean isFristMatchSuccess = true;
                    ResultErrorInfo errorInfo = new ResultErrorInfo();
                    FormDefine formDefine = null;
                    ImportResultReportObject importResultReportObject = null;
                    String dimensionName = this.entityMetaService.getDimensionName(formScheme.getDw());
                    try {
                        SheetNameOfFormResultInfo sheetNameOfFormResultInfo = this.getFormDefineBySheetName(sheetName, sysSeparator, split, params.get(i).getFormSchemeKey(), params.get(i), dataAccessService);
                        if (!sheetNameOfFormResultInfo.isResultType()) {
                            if (sheetName.equals("\u76ee\u5f55")) continue;
                            isFristMatchSuccess = false;
                            errorInfo.setErrorCode(ErrorCode.FILEERROR);
                            errorInfo.setErrorInfo(StringUtils.isNotEmpty((String)sheetNameOfFormResultInfo.getNoFormInfo()) ? sheetNameOfFormResultInfo.getNoFormInfo() : this.notFindReporterror(sheetName));
                        } else {
                            formDefine = sheetNameOfFormResultInfo.getFormDefine();
                            Map<String, Object> res = this.getReport(formDefine, sheetName, params.get(i), dataAccessService, message);
                            boolean success = (Boolean)res.get(this.reportRes);
                            if (!success) {
                                isFristMatchSuccess = false;
                                errorInfo.setErrorCode(ErrorCode.FILEERROR);
                                errorInfo.setErrorInfo((String)res.get(this.reportMessage));
                            } else {
                                formDefine = (FormDefine)res.get(this.JtableDataStr);
                                this.setAppending(params.get(i), successDatas, isExcelName, formDefine.getFormCode(), dimensionName);
                                Map<String, Object> resultMap = this.proceeReport(formDefine, sheetList.get(i), params.get(i), dataImportLogger);
                                if (resultMap != null) {
                                    boolean isMatchReport = (Boolean)resultMap.get(IS_MATCH_REPORT);
                                    importResultReportObject = (ImportResultReportObject)resultMap.get(MATCH_REPORT_RESULT);
                                    if (!isMatchReport) {
                                        isFristMatchSuccess = false;
                                    }
                                }
                            }
                        }
                        if (!isFristMatchSuccess) {
                            FormDefine reverseFormDefine;
                            if (formDefineMap == null) {
                                formDefineMap = new HashMap<String, FormDefine>();
                                List formDefines = this.runtimeView.queryAllFormDefinesByFormScheme(params.get(i).getFormSchemeKey());
                                for (FormDefine sysFormDefine : formDefines) {
                                    String spliceName = this.getFormSpliceName(sysFormDefine);
                                    formDefineMap.put(spliceName, sysFormDefine);
                                }
                            }
                            if ((reverseFormDefine = (FormDefine)formDefineMap.get(sheetName)) != null) {
                                formDefine = reverseFormDefine;
                                Map<String, Object> reverseRes = this.getReport(formDefine, sheetName, params.get(i), dataAccessService, message);
                                boolean reverseSuccess = (Boolean)reverseRes.get(this.reportRes);
                                if (!reverseSuccess) {
                                    importResultReportObject = null;
                                    errorInfo.setErrorCode(ErrorCode.FILEERROR);
                                    errorInfo.setErrorInfo((String)reverseRes.get(this.reportMessage));
                                } else {
                                    boolean reverseMatchReport;
                                    formDefine = (FormDefine)reverseRes.get(this.JtableDataStr);
                                    this.setAppending(params.get(i), successDatas, isExcelName, formDefine.getFormCode(), dimensionName);
                                    Map<String, Object> reverseresultMap = this.proceeReport(formDefine, sheetList.get(i), params.get(i), dataImportLogger);
                                    if (reverseresultMap != null && (reverseMatchReport = ((Boolean)reverseresultMap.get(IS_MATCH_REPORT)).booleanValue())) {
                                        importResultReportObject = (ImportResultReportObject)reverseresultMap.get(MATCH_REPORT_RESULT);
                                    }
                                }
                            }
                        }
                        if (importResultReportObject == null) {
                            sheetItem = new ImportResultSheetObject();
                            ImportResultReportObject resultObj = new ImportResultReportObject();
                            resultObj.setReportError(errorInfo);
                            resultObj.setReportName(formDefine == null ? "" : formDefine.getTitle());
                            sheetItem.setImportResultReportObject(resultObj);
                            sheetItem.setSheetName(sheetName);
                            resObject.getImportResultSheetObjectList().add(sheetItem);
                            if (isExcelName) {
                                resObject.setFileName(excelName);
                                ResultErrorInfo fileError = new ResultErrorInfo();
                                fileError.setErrorInfo(resultObj.getReportError().getErrorInfo());
                                fileError.setErrorCode(ErrorCode.FILEERROR);
                                resObject.setFileError(fileError);
                            }
                            this.buildLog(params, dataImportLogger, formScheme, i, resultObj);
                        } else {
                            if (null != importResultReportObject.getReportError().getErrorCode() || !importResultReportObject.getImportResultRegionObjectList().isEmpty()) {
                                ImportResultSheetObject importResultSheetObject = this.transformationError(excelName, importResultReportObject, sheetList.get(i), formDefine == null ? "" : formDefine.getFormCode() + SEPARATOR_ONE + formDefine.getTitle());
                                resObject.getImportResultSheetObjectList().add(importResultSheetObject);
                            } else {
                                DimensionValueSet combineDim = params.get(i).getDimensionSet().toDimensionValueSet();
                                if (isExcelName) {
                                    successDatas.add((String)combineDim.getValue("MDCODE"));
                                } else {
                                    successDatas.add(formDefine.getFormCode());
                                }
                                HashMap<String, DimensionValue> map = new HashMap<String, DimensionValue>();
                                for (int j = 0; j < combineDim.size(); ++j) {
                                    DimensionValue dimensionValue = new DimensionValue();
                                    dimensionValue.setName(combineDim.getName(j));
                                    dimensionValue.setValue(combineDim.getValue(j).toString());
                                    map.put(combineDim.getName(j), dimensionValue);
                                }
                                resObject.addRelationDimensions(map);
                            }
                            FormDefine formData = this.runtimeView.queryFormById(formDefine.getKey());
                            if (FormType.FORM_TYPE_NEWFMDM.name().equals(formData.getFormType().name())) {
                                resObject.setFmdmed(true);
                            }
                        }
                    }
                    catch (Exception e) {
                        logger.error("\u5bfc\u5165\u5931\u8d25\u6709\u672a\u77e5\u5f02\u5e38\uff1a{}", (Object)e.getMessage(), (Object)e);
                        sheetItem = new ImportResultSheetObject();
                        sheetItem.setSheetName(sheetName);
                        ImportResultReportObject reportItem = new ImportResultReportObject();
                        reportItem.getReportError().setErrorCode(ErrorCode.SYSTEMERROR);
                        reportItem.setReportName(formDefine == null ? "" : formDefine.getFormCode() + SEPARATOR_ONE + formDefine.getTitle());
                        sheetItem.setImportResultReportObject(reportItem);
                        reportItem.getReportError().setErrorInfo(e.getMessage());
                        resObject.getImportResultSheetObjectList().add(sheetItem);
                        this.buildLog(params, dataImportLogger, formScheme, i, reportItem);
                        if (!(e instanceof ImportContainManyEmptyRowException)) break block31;
                        resObject.getFileError().setErrorCode(ErrorCode.SYSTEMERROR);
                        resObject.getFileError().setErrorInfo(e.getMessage());
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
            dataImportLogger.importError("\u5bfc\u5165\u8fc7\u7a0b\u4e2d\u53d1\u751f\u5f02\u5e38" + e.getMessage());
            return resObject;
        }
    }

    private void setAppending(UploadParam uploadParam, List<String> successDatas, boolean isExcelName, String formCode, String dwDimName) {
        if (uploadParam.isAppending() || !uploadParam.isAppending() && !successDatas.isEmpty()) {
            String judgeData;
            String string = judgeData = isExcelName ? (String)uploadParam.getDimensionSet().getValue(dwDimName) : formCode;
            if (uploadParam.isAppending() || successDatas.contains(judgeData)) {
                uploadParam.setSplitSheets(true);
            }
        }
    }

    private void buildLog(List<UploadParam> params, DataImportLogger dataImportLogger, FormSchemeDefine formScheme, int i, ImportResultReportObject reportItem) {
        try {
            String dimensionName = this.entityMetaService.getDimensionName(formScheme.getDw());
            LogDimensionCollection dimensionCollection = new LogDimensionCollection();
            dimensionCollection.setDw(formScheme.getDw(), new String[]{params.get(i).getDimensionSet().toDimensionValueSet().getValue(dimensionName).toString()});
            dimensionCollection.setPeriod(formScheme.getDateTime(), params.get(i).getDimensionSet().getPeriodDimensionValue().getValue().toString());
            dataImportLogger.importError(reportItem.getReportError().getErrorInfo(), dimensionCollection);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private ImportResultSheetObject transformationError(String excelName, ImportResultReportObject importResultReportObject, Sheet sheet, String formName) {
        ImportResultSheetObject importResultSheetObject = new ImportResultSheetObject();
        importResultSheetObject.setSheetName(sheet.getSheetName());
        importResultReportObject.setReportName(formName);
        importResultSheetObject.setImportResultReportObject(importResultReportObject);
        return importResultSheetObject;
    }

    private Map<String, Object> proceeReport(FormDefine formData, Sheet reportSheet, UploadParam param, DataImportLogger dataImportLogger) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        ReportMatchResult reportMatchResult = null;
        try {
            reportMatchResult = this.matchSheetToReport(formData, reportSheet);
        }
        catch (ImportContainManyEmptyRowException e1) {
            String message = "\u8868\u6837\u5339\u914d\u8fc7\u7a0b\u4e2d\u53d1\u751f\u9519\u8bef\uff1a" + e1.getMessage();
            dataImportLogger.importError(message);
            logger.error(message, e1);
            throw e1;
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
            dataImportLogger.importError("\u8868\u6837\u5339\u914d\u9519\u8bef\uff1a" + e.getMessage());
            resultMap.put(IS_MATCH_REPORT, false);
            resultMap.put(MATCH_REPORT_RESULT, reportResult);
            return resultMap;
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
            dataImportLogger.importError(message);
            resultMap.put(IS_MATCH_REPORT, false);
            resultMap.put(MATCH_REPORT_RESULT, reportResult);
            return resultMap;
        }
        boolean multiTab = false;
        ImportResultReportObject reportResult = new ImportResultReportObject();
        List<RegionMatchInfo> completeMatchList = reportMatchResult.getCompleteMatchList();
        if (completeMatchList != null && !completeMatchList.isEmpty()) {
            for (RegionMatchInfo regionMatchInfo : completeMatchList) {
                DataRegionDefine regionDefine = regionMatchInfo.getRegion();
                if (regionDefine.getRegionKind().equals((Object)DataRegionKind.DATA_REGION_SIMPLE)) continue;
                ArrayList<String> tabTitles = new ArrayList<String>();
                RegionSettingDefine regionSettingDefine = this.runtimeView.getRegionSetting(regionDefine.getKey());
                if (null != regionSettingDefine && regionSettingDefine.getRegionTabSetting() != null) {
                    for (RegionTabSettingDefine regionTabSettingDefine : regionSettingDefine.getRegionTabSetting()) {
                        tabTitles.add(regionTabSettingDefine.getTitle());
                    }
                }
                ArrayList<String> tabRowNames = new ArrayList<String>();
                Map<Integer, String> tabNames = reportMatchResult.getTabNames();
                for (int i = regionMatchInfo.getMatchStart(); i < regionMatchInfo.getMatchEnd() - 1; ++i) {
                    String tabName = tabNames.get(i);
                    if (!StringUtils.isNotEmpty((String)tabName) || !tabTitles.contains(tabName)) continue;
                    tabRowNames.add(tabName);
                }
                if (tabRowNames.size() > 1) {
                    multiTab = true;
                    ImportResultRegionObject importResultRegionObject = new ImportResultRegionObject();
                    importResultRegionObject.setRegionKey(regionDefine.getKey());
                    importResultRegionObject.getRegionError().setErrorCode(ErrorCode.REGIONERROR);
                    importResultRegionObject.getRegionError().setErrorInfo("\u4e00\u4e2a\u6d6e\u52a8\u533a\u57df\u5185\u6709\u591a\u4e2a\u9875\u7b7e\u884c\uff0c\u65e0\u6cd5\u5bfc\u5165");
                    reportResult.getImportResultRegionObjectList().add(importResultRegionObject);
                    continue;
                }
                reportMatchResult.getRegionTabNums().put(regionDefine.getKey(), tabRowNames.size());
            }
        }
        if (multiTab) {
            resultMap.put(IS_MATCH_REPORT, true);
            resultMap.put(MATCH_REPORT_RESULT, reportResult);
            return resultMap;
        }
        try {
            ImportResultReportObject reportResult2 = this.saveData(formData, reportMatchResult, param, dataImportLogger);
            resultMap.put(IS_MATCH_REPORT, true);
            resultMap.put(MATCH_REPORT_RESULT, reportResult2);
            return resultMap;
        }
        catch (DataSetException | CrudException | CrudOperateException e) {
            logger.error(e.getMessage());
            dataImportLogger.importError("\u5bfc\u5165\u8fc7\u7a0b\u4e2d\u53d1\u751f\u5f02\u5e38" + e.getMessage());
            return null;
        }
    }

    private SheetNameOfFormResultInfo getFormDefineBySheetName(String sheetName, String sysSeparator, String[] split, String formSchemeKey, UploadParam param, IDataAccessService dataAccessService) throws Exception {
        ArrayList<FormDefine> formDefineList;
        SheetNameOfFormResultInfo sheetNameOfFormResultInfo = new SheetNameOfFormResultInfo();
        List formDefines = this.runtimeView.queryAllFormDefinesByFormScheme(formSchemeKey);
        ArrayList<FormDefine> formDefineListAfterFilter = new ArrayList<FormDefine>();
        ArrayList<String> formKeysByUser = new ArrayList<String>();
        FormDefine tmpFormDefine = null;
        for (FormDefine formDefine : formDefines) {
            FormDefine splitOfSysSep;
            formKeysByUser.add(formDefine.getKey());
            if (sysSeparator.equals("") || !sysSeparator.equals("") && !sheetName.contains(sysSeparator)) {
                for (FormDefine formDefineInfo : formDefineListAfterFilter) {
                    if (!formDefineInfo.getTitle().equals(sheetName) && !formDefineInfo.getFormCode().equals(sheetName)) continue;
                    tmpFormDefine = formDefineInfo;
                }
                continue;
            }
            if (sysSeparator.equals("") || !sheetName.contains(sysSeparator)) continue;
            for (String formCode : splitOfSysSep = sheetName.split(sysSeparator)) {
                if (!formDefine.getFormCode().equals(formCode) && !formDefine.getTitle().equals(formCode)) continue;
                tmpFormDefine = formDefine;
            }
        }
        DimensionCollectionBuilder builder = new DimensionCollectionBuilder();
        param.getDimensionSet().forEach(e -> builder.setEntityValue(e.getName(), e.getEntityID(), new Object[]{e.getValue()}));
        IBatchAccessResult writeAccess = dataAccessService.getWriteAccess(builder.getCollection(), formDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList()));
        FormDefine formDefine = null;
        HashMap<String, String> resions = new HashMap<String, String>();
        ArrayList<String> accessFormKeys = new ArrayList<String>();
        for (FormDefine formDefineInfo : formDefines) {
            IAccessResult access = writeAccess.getAccess(param.getDimensionSet(), formDefineInfo.getKey());
            if (access.haveAccess()) {
                formDefineListAfterFilter.add(formDefineInfo);
                accessFormKeys.add(formDefineInfo.getKey());
                continue;
            }
            resions.put(formDefineInfo.getKey(), access.getMessage());
        }
        if (sysSeparator.equals("") || !sysSeparator.equals("") && !sheetName.contains(sysSeparator)) {
            for (FormDefine formDefineInfo : formDefineListAfterFilter) {
                if (!formDefineInfo.getTitle().equals(sheetName) && !formDefineInfo.getFormCode().equals(sheetName)) continue;
                formDefine = formDefineInfo;
                break;
            }
        } else if (!sysSeparator.equals("") && sheetName.contains(sysSeparator)) {
            formDefineList = new ArrayList();
            String[] splitOfSysSep = sheetName.split(sysSeparator);
            for (FormDefine formDefineInfo : formDefines) {
                if (!formDefineInfo.getTitle().equals(sheetName) && !formDefineInfo.getFormCode().equals(sheetName)) continue;
                formDefine = formDefineInfo;
                break;
            }
            if (formDefine == null) {
                String formCode;
                int n;
                int n2;
                String[] stringArray;
                for (FormDefine formDefineInfo : formDefines) {
                    if (formDefineInfo.getFormCode().equals(sheetName) || formDefineInfo.getTitle().equals(sheetName)) {
                        formDefine = formDefineInfo;
                        break;
                    }
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
            if (accessFormKeys.contains(formDefine.getKey())) {
                sheetNameOfFormResultInfo.setResultType(true);
            } else if (resions.containsKey(formKey)) {
                sheetNameOfFormResultInfo.setNoFormInfo((String)resions.get(formKey));
            }
        } else if (tmpFormDefine != null && resions.containsKey(tmpFormDefine.getKey())) {
            sheetNameOfFormResultInfo.setNoFormInfo((String)resions.get(tmpFormDefine.getKey()));
        }
        return sheetNameOfFormResultInfo;
    }

    private ImportResultReportObject saveData(FormDefine formData, ReportMatchResult reportMatchResult, UploadParam param, DataImportLogger dataImportLogger) throws Exception {
        ImportResultReportObject importResultReportObject = new ImportResultReportObject();
        List regions = this.runtimeView.getAllRegionsInForm(formData.getKey());
        FormSchemeDefine formSchemeDefine = this.runtimeView.getFormScheme(param.getFormSchemeKey());
        String dwDimName = this.entityMetaService.getDimensionName(formSchemeDefine.getDw());
        String unitCode = null;
        if (param.getDimensionSet() != null) {
            unitCode = (String)param.getDimensionSet().getValue(dwDimName);
        }
        String matchAll = this.iNvwaSystemOptionService.get("nr-data-entry-group", "JTABLE_ENTITY_MATCH_ALL");
        boolean isMatchAll = false;
        if (matchAll != null && matchAll.equals("1")) {
            isMatchAll = true;
        }
        boolean roundImport = false;
        String roundImportStr = this.nvwaSystemOptionService.get("nr-data-entry-group", "MATCH_ROUND_OF_IMPORT");
        if ("1".equals(roundImportStr)) {
            roundImport = true;
        }
        boolean overLengthTruncated = false;
        String value = this.taskOptionController.getValue(formSchemeDefine.getTaskKey(), "IllegalDataImport_2132");
        if (value.equals("1")) {
            overLengthTruncated = true;
        }
        List conditionalStyles = this.conditionalStyleController.getAllCSInForm(formData.getKey());
        HashMap<String, Boolean> linkHorizontalBarMap = new HashMap<String, Boolean>();
        for (ConditionalStyle conditionalStyle : conditionalStyles) {
            String linkKey = conditionalStyle.getLinkKey();
            if (linkHorizontalBarMap.get(linkKey) == null) {
                linkHorizontalBarMap.put(linkKey, conditionalStyle.getHorizontalBar());
                continue;
            }
            linkHorizontalBarMap.compute(linkKey, (k, horizontalBar) -> horizontalBar != false || conditionalStyle.getHorizontalBar() != false);
        }
        HashMap<String, Integer> tabs = new HashMap<String, Integer>();
        HashedMap<String, Boolean> isThousandPeLinkMap = new HashedMap<String, Boolean>();
        boolean isOneSheet = false;
        ArrayList<ImportResultRegionObject> importResultRegionObjectList = new ArrayList<ImportResultRegionObject>();
        Map<String, CommonInitData> regionReadLinkMap = param.getRegionReadOnlyDataLinks();
        for (int i = 0; i < regions.size(); ++i) {
            ImportResultRegionObject importResultRegionObject;
            CrudSaveException e1;
            SaveReturnRes saveRegionData;
            ISaveInfo build;
            List saveResItemList;
            SaveReturnRes saveReturnRes;
            ReturnRes setData;
            Object object;
            Integer k2;
            ParseReturnRes res;
            DimensionCombinationBuilder rowDim;
            TypeParseStrategy typeParseStrategy;
            DataField dataField;
            FieldDefine queryFieldDefine;
            Object object2;
            String key;
            Object dimensionSet;
            ArrayList<Integer> linkIndex;
            int addRowCount;
            ArrayList<DimensionCombination> dimensionCombinationList;
            int floatOrderNum;
            String dimensionName;
            FieldDefine queryFieldDefine2;
            String[] split;
            String cellNum;
            HashMap<String, DataLinkDefine> tempMap;
            DataRegionDefine regionData = (DataRegionDefine)regions.get(i);
            int saveRowData = 0;
            if (!param.isAppending() && regionData.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE) {
                saveRowData = 1;
            }
            HashSet<String> readOnlyDataLinks = new HashSet<String>();
            CommonInitData initData = regionReadLinkMap.get(regionData.getKey());
            if (initData == null) {
                initData = new CommonInitData();
            }
            if (this.readOnlyServices != null && !this.readOnlyServices.isEmpty()) {
                ReadOnlyContext readOnlyContext = new ReadOnlyContext();
                readOnlyContext.setTaskKey(formSchemeDefine.getTaskKey());
                readOnlyContext.setFormSchemeKey(formSchemeDefine.getKey());
                readOnlyContext.setFormKey(formData.getKey());
                readOnlyContext.setRegionKey(((DataRegionDefine)regions.get(i)).getKey());
                readOnlyContext.setDimensionValueSet(param.getDimensionSet().toDimensionValueSet());
                for (IRegionDataLinkReadOnlyService readOnlyService : this.readOnlyServices) {
                    Set dataLinks;
                    if (!readOnlyService.isAllOrgShare(readOnlyContext)) {
                        dataLinks = readOnlyService.getReadOnlyDataLinks(readOnlyContext);
                        if (dataLinks == null || dataLinks.isEmpty()) continue;
                        readOnlyDataLinks.addAll(dataLinks);
                        continue;
                    }
                    if (!initData.isInit()) {
                        dataLinks = readOnlyService.getReadOnlyDataLinks(readOnlyContext);
                        if (dataLinks == null || dataLinks.isEmpty()) continue;
                        readOnlyDataLinks.addAll(dataLinks);
                        initData.getCommonKeys().addAll(dataLinks);
                        continue;
                    }
                    readOnlyDataLinks.addAll(initData.getCommonKeys());
                }
                if (!initData.isInit()) {
                    initData.setInit(true);
                    regionReadLinkMap.put(regionData.getKey(), initData);
                }
            }
            SaveDataBuilder saveDataBuilder = this.saveDataBuilderFactory.createCheckSaveDataBuilder(regionData.getKey(), param.getDimensionSet());
            if (roundImport) {
                TypeParseStrategy intType;
                TypeParseStrategy typeParseStrategy2 = saveDataBuilder.getTypeParseStrategy(DataFieldType.BIGDECIMAL.getValue());
                if (typeParseStrategy2 instanceof DecimalParseStrategy) {
                    ((DecimalParseStrategy)typeParseStrategy2).setRoundingMode(RoundingMode.HALF_UP);
                }
                if ((intType = saveDataBuilder.getTypeParseStrategy(DataFieldType.INTEGER.getValue())) instanceof DecimalParseStrategy) {
                    ((DecimalParseStrategy)intType).setRoundingMode(RoundingMode.HALF_UP);
                }
            }
            FormatEnumParseStrategy enumParseStrategy = this.typeStrategyUtil.initFormatEnumParseStrategy(regionData.getKey());
            enumParseStrategy.setEntityMatchAll(isMatchAll);
            enumParseStrategy.setOverLengthTruncated(overLengthTruncated);
            saveDataBuilder.registerParseStrategy(DataFieldType.STRING.getValue(), (TypeParseStrategy)enumParseStrategy);
            ArrayList<RegionTab> regionTabs = new ArrayList<RegionTab>();
            HashMap<String, IFMDMAttribute> linkFmdmAttr = new HashMap<String, IFMDMAttribute>();
            RegionSettingDefine regionSettingDefine = this.runtimeView.getRegionSetting(regionData.getKey());
            if (null != regionSettingDefine && regionSettingDefine.getRegionTabSetting() != null) {
                for (RegionTabSettingDefine regionTabSettingDefine : regionSettingDefine.getRegionTabSetting()) {
                    RegionTab regionTab = new RegionTab(regionTabSettingDefine);
                    regionTabs.add(regionTab);
                }
            }
            if (!regionTabs.isEmpty()) {
                tempMap = new HashMap<String, DataLinkDefine>();
                int regionTabSizes = reportMatchResult.getRegionTabNums().get(regionData.getKey());
                if (0 == regionTabSizes) {
                    tabs.put(regionData.getKey(), 0);
                } else {
                    tabs.put(regionData.getKey(), 1);
                }
                List linkDataList = this.runtimeView.getAllLinksInRegion(regionData.getKey());
                Iterator iterator = linkDataList.iterator();
                while (iterator.hasNext()) {
                    DataLinkDefine link = (DataLinkDefine)iterator.next();
                    if (!readOnlyDataLinks.contains(link.getKey())) continue;
                    iterator.remove();
                }
                ArrayList<DataLinkDefine> linkDataListRemove = new ArrayList<DataLinkDefine>();
                int floatOrderIndex = -1;
                if (!(linkDataList.isEmpty() || formData.getFormType().equals((Object)FormType.FORM_TYPE_ACCOUNT) && regionData.getRegionTop() > 1)) {
                    block192: {
                        ArrayList<Object[]> dataRowSet = new ArrayList<Object[]>();
                        List<Object> cellValue = reportMatchResult.getCellValue(((DataLinkDefine)linkDataList.get(0)).getKey().toString());
                        ImportResultRegionObject importResultRegionObjectOfThousandPer = new ImportResultRegionObject();
                        importResultRegionObjectOfThousandPer.setRegionKey(regionData.getKey());
                        ArrayList<ImportErrorDataInfo> importErrorDataInfoListOfThousandPer = new ArrayList<ImportErrorDataInfo>();
                        importResultRegionObjectOfThousandPer.setImportErrorDataInfoList(importErrorDataInfoListOfThousandPer);
                        if (null != cellValue) {
                            for (int j = 0; j < cellValue.size(); ++j) {
                                Object[] rowData = new Object[linkDataList.size()];
                                for (int k3 = 0; k3 < linkDataList.size(); ++k3) {
                                    boolean isThousanPerNum;
                                    String cellFormat;
                                    Boolean horizontalBar2;
                                    DataLinkDefine linkData = (DataLinkDefine)linkDataList.get(k3);
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
                                    if (linkData.getType() == DataLinkType.DATA_LINK_TYPE_FIELD) {
                                        FieldDefine queryFieldDefine3 = null;
                                        try {
                                            queryFieldDefine3 = this.runtimeView.queryFieldDefine(linkData.getLinkExpression());
                                        }
                                        catch (Exception e) {
                                            logger.error(e.getMessage());
                                        }
                                        if (queryFieldDefine3 == null) continue;
                                        if (null == reportMatchResult.getCellValue(linkData.getKey().toString())) {
                                            rowData[k3] = "";
                                        } else {
                                            int fieldType = queryFieldDefine3.getType().getValue();
                                            if ((fieldType == FieldType.FIELD_TYPE_TEXT.getValue() || fieldType == FieldType.FIELD_TYPE_STRING.getValue()) && reportMatchResult.getCellValue(linkData.getKey().toString()).get(j) != null) {
                                                rowData[k3] = reportMatchResult.getCellValue(linkData.getKey().toString()).get(j).toString().trim();
                                            } else if (fieldType == FieldType.FIELD_TYPE_DATE.getValue() || fieldType == FieldType.FIELD_TYPE_DATE_TIME.getValue()) {
                                                rowData[k3] = reportMatchResult.getCellValue(linkData.getKey().toString()).get(j);
                                            } else {
                                                cellNum = (String)reportMatchResult.getCellValue(linkData.getKey().toString()).get(j);
                                                if ("-".equals(cellNum) && (fieldType == FieldType.FIELD_TYPE_FLOAT.getValue() || fieldType == FieldType.FIELD_TYPE_INTEGER.getValue() || fieldType == FieldType.FIELD_TYPE_DECIMAL.getValue())) {
                                                    horizontalBar2 = (Boolean)linkHorizontalBarMap.get(linkData.getKey());
                                                    rowData[k3] = horizontalBar2 != null && horizontalBar2.booleanValue() ? "" : cellNum;
                                                } else if (isThousandPerLink) {
                                                    cellFormat = reportMatchResult.getCellFormat(linkData.getKey()).get(j);
                                                    isThousanPerNum = this.checkNumIsThousandPer(cellNum, cellFormat, linkData, tempMap, importErrorDataInfoListOfThousandPer, j);
                                                    if (isThousanPerNum) {
                                                        if (StringUtils.isNotEmpty((String)cellNum)) {
                                                            cellNum = new BigDecimal(cellNum.contains("\u2030") ? cellNum.replace("\u2030", "") : cellNum).divide(new BigDecimal(1000)).toPlainString();
                                                            rowData[k3] = cellNum;
                                                        } else {
                                                            rowData[k3] = "";
                                                        }
                                                    } else {
                                                        rowData[k3] = cellNum;
                                                    }
                                                } else {
                                                    rowData[k3] = cellNum;
                                                }
                                            }
                                        }
                                        tempMap.put(linkData.getKey(), linkData);
                                    } else if (linkData.getType() == DataLinkType.DATA_LINK_TYPE_FMDM) {
                                        IFMDMAttribute fmdmAttribute = null;
                                        if (linkFmdmAttr.containsKey(linkData.getLinkExpression())) {
                                            fmdmAttribute = (IFMDMAttribute)linkFmdmAttr.get(linkData.getLinkExpression());
                                        } else {
                                            FMDMAttributeDTO fmdmAttributeDTO = new FMDMAttributeDTO();
                                            fmdmAttributeDTO.setEntityId(formSchemeDefine.getDw());
                                            fmdmAttributeDTO.setFormSchemeKey(formSchemeDefine.getKey());
                                            fmdmAttributeDTO.setAttributeCode(linkData.getLinkExpression());
                                            fmdmAttributeDTO.setZBKey(linkData.getLinkExpression());
                                            fmdmAttribute = this.fMDMAttributeService.queryByZbKey(fmdmAttributeDTO);
                                            if (fmdmAttribute == null) continue;
                                            linkFmdmAttr.put(linkData.getLinkExpression(), fmdmAttribute);
                                        }
                                        if (fmdmAttribute == null) continue;
                                        ColumnModelType columnModelType = fmdmAttribute.getColumnType();
                                        if (null == reportMatchResult.getCellValue(linkData.getKey().toString())) {
                                            rowData[k3] = "";
                                        } else if ((columnModelType == ColumnModelType.STRING || linkData.getType().getValue() == FieldType.FIELD_TYPE_STRING.getValue()) && reportMatchResult.getCellValue(linkData.getKey().toString()).get(j) != null) {
                                            rowData[k3] = reportMatchResult.getCellValue(linkData.getKey()).get(j).toString().trim();
                                        } else {
                                            cellNum = (String)reportMatchResult.getCellValue(linkData.getKey().toString()).get(j);
                                            if ("-".equals(cellNum) && (columnModelType == ColumnModelType.DOUBLE || columnModelType == ColumnModelType.INTEGER || columnModelType == ColumnModelType.BIGDECIMAL)) {
                                                horizontalBar2 = (Boolean)linkHorizontalBarMap.get(linkData.getKey());
                                                rowData[k3] = horizontalBar2 != null && horizontalBar2.booleanValue() ? "" : cellNum;
                                            } else if (isThousandPerLink) {
                                                cellFormat = reportMatchResult.getCellFormat(linkData.getKey()).get(j);
                                                isThousanPerNum = this.checkNumIsThousandPer(cellNum, cellFormat, linkData, tempMap, importErrorDataInfoListOfThousandPer, j);
                                                if (isThousanPerNum) {
                                                    if (StringUtils.isNotEmpty((String)cellNum)) {
                                                        cellNum = new BigDecimal(cellNum.contains("\u2030") ? cellNum.replace("\u2030", "") : cellNum).divide(new BigDecimal(1000)).toPlainString();
                                                        rowData[k3] = cellNum;
                                                    } else {
                                                        rowData[k3] = "";
                                                    }
                                                } else {
                                                    rowData[k3] = cellNum;
                                                }
                                            } else {
                                                rowData[k3] = cellNum;
                                            }
                                        }
                                        tempMap.put(linkData.getKey(), linkData);
                                    }
                                    if (rowData[k3] == null || !rowData[k3].equals(IMPORT_INVALID_DATA)) continue;
                                    linkDataListRemove.add(linkData);
                                }
                                dataRowSet.add(rowData);
                            }
                        }
                        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
                        IDataAssist newDataAssist = this.dataAccessProvider.newDataAssist(context);
                        String bizKeyFields = regionData.getBizKeyFields();
                        HashMap regionDimsIndex = new HashMap();
                        if (bizKeyFields != null && !bizKeyFields.equals("")) {
                            try {
                                HashMap<String, Integer> tempLinks = new HashMap<String, Integer>();
                                for (int k4 = 0; k4 < linkDataList.size(); ++k4) {
                                    DataLinkDefine dataLinkDefine = (DataLinkDefine)linkDataList.get(k4);
                                    if (dataLinkDefine.getLinkExpression() == null) continue;
                                    tempLinks.put(dataLinkDefine.getLinkExpression(), k4);
                                }
                                for (String string : split = bizKeyFields.split(";")) {
                                    queryFieldDefine2 = this.runtimeView.queryFieldDefine(string);
                                    if (queryFieldDefine2 == null) continue;
                                    dimensionName = newDataAssist.getDimensionName(queryFieldDefine2);
                                    regionDimsIndex.put(dimensionName, tempLinks.get(queryFieldDefine2.getKey()));
                                }
                            }
                            catch (Exception e12) {
                                logger.error(e12.getMessage());
                            }
                        }
                        floatOrderNum = this.getFloatOrderNum(param, regionData.getKey());
                        dimensionCombinationList = new ArrayList();
                        addRowCount = 0;
                        linkIndex = new ArrayList();
                        for (DataLinkDefine item : linkDataListRemove) {
                            linkIndex.add(linkDataList.indexOf(item));
                        }
                        linkDataList.removeAll(linkDataListRemove);
                        floatOrderIndex = this.addLinkDataList(regionData, saveDataBuilder, linkDataList, floatOrderIndex);
                        for (int item = 0; item < linkDataListRemove.size(); ++item) {
                            if (linkDataList.contains(linkDataListRemove.get(item))) continue;
                            linkDataList.add((Integer)linkIndex.get(item), linkDataListRemove.get(item));
                        }
                        for (Object[] row : dataRowSet) {
                            ReturnRes addRow;
                            int weight = 0;
                            boolean checkEmptyRow = this.checkEmptyRow(row);
                            if (addRowCount != 0 && regionData.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE && checkEmptyRow || regionData.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE && checkEmptyRow) continue;
                            dimensionSet = param.getDimensionSet();
                            if (regionData.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE) {
                                DimensionCombinationBuilder builder = new DimensionCombinationBuilder(dimensionSet.toDimensionValueSet());
                                if (regionDimsIndex.size() > 0) {
                                    for (Map.Entry entry : regionDimsIndex.entrySet()) {
                                        key = (String)entry.getKey();
                                        if (key.equals(RECORDKEY)) {
                                            builder.setValue(RECORDKEY, null, (Object)UUID.randomUUID().toString());
                                            continue;
                                        }
                                        object2 = row[(Integer)regionDimsIndex.get(key)];
                                        queryFieldDefine = null;
                                        try {
                                            queryFieldDefine = this.runtimeView.queryFieldDefine(((DataLinkDefine)linkDataList.get((Integer)regionDimsIndex.get(key))).getLinkExpression());
                                        }
                                        catch (Exception e) {
                                            logger.error(e.getMessage());
                                        }
                                        dataField = (DataField)queryFieldDefine;
                                        if (dataField.getRefDataEntityKey() != null) {
                                            typeParseStrategy = saveDataBuilder.getTypeParseStrategy(DataFieldType.STRING.getValue());
                                            rowDim = new DimensionCombinationBuilder(dimensionSet.toDimensionValueSet());
                                            typeParseStrategy.setRowKey(rowDim.getCombination());
                                            res = typeParseStrategy.checkParse((DataLinkDefine)linkDataList.get((Integer)regionDimsIndex.get(key)), dataField, object2);
                                            if (res.getCode() == 0 && res.getAbstractData() != null) {
                                                object2 = res.getAbstractData().getAsObject();
                                            }
                                        }
                                        builder.setValue(key, null, object2);
                                    }
                                    if (regionData.getAllowDuplicateKey()) {
                                        builder.setValue(RECORDKEY, null, (Object)UUID.randomUUID().toString());
                                    }
                                } else {
                                    builder.setValue(RECORDKEY, null, (Object)UUID.randomUUID().toString());
                                }
                                dimensionSet = builder.getCombination();
                            }
                            if ((addRow = saveDataBuilder.addRow((DimensionCombination)dimensionSet, saveRowData)).getCode() != 0) {
                                if (addRow.getCode() == 1202) {
                                    for (Map.Entry entry : regionDimsIndex.entrySet()) {
                                        key = (String)entry.getKey();
                                        if (key.equals(RECORDKEY) || (k2 = (Integer)regionDimsIndex.get(key)) == null) continue;
                                        this.collectErrorMessage(reportMatchResult, importResultReportObject, importResultRegionObjectList, regionData, tempMap, linkDataList, k2, addRow.getMessage(), addRowCount);
                                        break;
                                    }
                                    ++addRowCount;
                                    continue;
                                }
                                throw new RuntimeException(addRow.getMessage());
                            }
                            dimensionCombinationList.add((DimensionCombination)dimensionSet);
                            if (floatOrderIndex != -1) {
                                saveDataBuilder.setData(floatOrderIndex, (Object)floatOrderNum);
                                floatOrderNum += 10;
                            }
                            for (int k5 = 0; k5 < row.length; ++k5) {
                                object = row[k5];
                                if (object != null && object.equals(IMPORT_INVALID_DATA)) {
                                    ++weight;
                                    continue;
                                }
                                setData = saveDataBuilder.setData(k5 - weight, object != null && object.equals("") ? null : object);
                                if (setData.isSuccess()) continue;
                                this.collectErrorMessage(reportMatchResult, importResultReportObject, importResultRegionObjectList, regionData, tempMap, linkDataList, k5, setData.getMessage(), addRowCount);
                            }
                            ++addRowCount;
                        }
                        if (!param.isAppending() && !param.isSplitSheets() && regionData.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE) {
                            saveDataBuilder.enableDeleteBeforeSave(true);
                        }
                        saveReturnRes = saveDataBuilder.checkData();
                        saveResItemList = saveReturnRes.getSaveResItems();
                        this.collectSaveErrorMessage(reportMatchResult, importResultReportObject, importResultRegionObjectList, regionData, tempMap, linkDataList, saveResItemList);
                        if (!importResultRegionObjectList.isEmpty()) {
                            return importResultReportObject;
                        }
                        build = saveDataBuilder.build();
                        try {
                            saveRegionData = this.iDataService.saveRegionData(build);
                            if (StringUtils.isNotEmpty((String)unitCode)) {
                                dataImportLogger.addFormToUnit(unitCode, formData.getFormCode());
                            }
                            if (saveRegionData.getCode() != 0) {
                                if (saveRegionData.getSaveResItems() != null && saveRegionData.getSaveResItems().size() > 0) {
                                    for (SaveResItem saveResItem : saveRegionData.getSaveResItems()) {
                                        this.collectErrorMessage(reportMatchResult, importResultReportObject, importResultRegionObjectList, regionData, tempMap, saveResItem.getLinkKey(), saveResItem.getMessage(), saveResItem.getRowIndex() != -1 ? saveResItem.getRowIndex() - 1 : addRowCount);
                                    }
                                    return importResultReportObject;
                                }
                                importResultReportObject.getReportError().setErrorCode(ErrorCode.SYSTEMERROR);
                                importResultReportObject.setReportName(formData.getFormCode() + SEPARATOR_ONE + formData.getTitle());
                                importResultReportObject.getReportError().setErrorInfo(saveRegionData.getMessage());
                                return importResultReportObject;
                            }
                            if (saveRegionData.getMessage() != null && saveRegionData.getMessage().equals("\u533a\u57df\u53ea\u8bfb,\u7ec8\u6b62\u6267\u884c\u6e05\u9664\u533a\u57df\u6570\u636e")) {
                                importResultReportObject.getReportError().setErrorCode(ErrorCode.SYSTEMERROR);
                                importResultReportObject.setReportName(formData.getFormCode() + SEPARATOR_ONE + formData.getTitle());
                                importResultReportObject.getReportError().setErrorInfo(saveRegionData.getMessage());
                                return importResultReportObject;
                            }
                        }
                        catch (CrudException | CrudOperateException e) {
                            if (StringUtils.isNotEmpty((String)unitCode)) {
                                dataImportLogger.addFormToUnit(unitCode, formData.getFormCode());
                            }
                            if (!(e instanceof CrudSaveException)) break block192;
                            if (e instanceof CrudSaveException) {
                                e1 = (CrudSaveException)e;
                                List items = e1.getItems();
                                if (items != null && !items.isEmpty()) {
                                    for (SaveResItem saveResItem : items) {
                                        this.collectErrorMessage(reportMatchResult, importResultReportObject, importResultRegionObjectList, regionData, tempMap, saveResItem.getLinkKey(), saveResItem.getMessage(), saveResItem.getRowIndex() != -1 ? saveResItem.getRowIndex() - 1 : addRowCount);
                                    }
                                    return importResultReportObject;
                                }
                            }
                            if (!(e instanceof CrudOperateException)) break block192;
                            importResultReportObject.getReportError().setErrorCode(ErrorCode.SYSTEMERROR);
                            importResultReportObject.setReportName(formData.getFormCode() + SEPARATOR_ONE + formData.getTitle());
                            importResultReportObject.getReportError().setErrorInfo(e.getMessage());
                            return importResultReportObject;
                        }
                    }
                    importResultRegionObject = new ImportResultRegionObject();
                    importResultRegionObject.setRegionKey(regionData.getKey());
                    Set sets = tabs.keySet();
                    int addRows = 0;
                    for (String regionKey : sets) {
                        addRows += ((Integer)tabs.get(regionKey)).intValue();
                    }
                    this.collectError(importResultReportObject, importResultRegionObject, reportMatchResult, tempMap, addRows + reportMatchResult.getSheetBeginRow());
                    continue;
                }
                if (linkDataList.isEmpty() || !formData.getFormType().equals((Object)FormType.FORM_TYPE_ACCOUNT) || regionData.getRegionTop() <= 1) continue;
                try {
                    this.sbRegionImport(reportMatchResult, param, formData.getKey(), regionData, tempMap);
                }
                catch (Exception e) {
                    logger.error(e.getMessage());
                    ImportResultRegionObject importResultRegionObject2 = new ImportResultRegionObject();
                    importResultRegionObject2.setRegionKey(regionData.getKey());
                    importResultRegionObject2.getRegionError().setErrorCode(ErrorCode.REGIONERROR);
                    importResultRegionObject2.getRegionError().setErrorInfo(e.getMessage());
                    this.collectError(importResultReportObject, importResultRegionObject2, reportMatchResult, tempMap, reportMatchResult.getSheetBeginRow());
                }
                continue;
            }
            tempMap = new HashMap();
            if (!isOneSheet) {
                if (this.checkImportDataList != null && !this.checkImportDataList.isEmpty()) {
                    for (ICheckImportData checkImportData : this.checkImportDataList) {
                        ImportResultRegionObject importResultRegionObjectOfCheck = checkImportData.checkImportData(formData, reportMatchResult, param);
                        if (importResultRegionObjectOfCheck == null || CollectionUtils.isEmpty(importResultRegionObjectOfCheck.getImportErrorDataInfoList())) continue;
                        importResultRegionObjectList.add(importResultRegionObjectOfCheck);
                    }
                }
                isOneSheet = true;
            }
            List linkDataList = this.runtimeView.getAllLinksInRegion(regionData.getKey());
            Iterator iterator = linkDataList.iterator();
            while (iterator.hasNext()) {
                DataLinkDefine link = (DataLinkDefine)iterator.next();
                if (link.getPosX() >= regionData.getRegionLeft() && link.getPosY() >= regionData.getRegionTop() && link.getPosX() <= regionData.getRegionRight() && link.getPosY() <= regionData.getRegionBottom() && link.getType() != DataLinkType.DATA_LINK_TYPE_FORMULA && !readOnlyDataLinks.contains(link.getKey())) continue;
                iterator.remove();
            }
            ArrayList<DataLinkDefine> linkDataListRemove = new ArrayList<DataLinkDefine>();
            int floatOrderIndex = -1;
            if (!(linkDataList.isEmpty() || formData.getFormType().equals((Object)FormType.FORM_TYPE_ACCOUNT) && regionData.getRegionTop() > 1)) {
                block193: {
                    ArrayList<Object[]> dataRowSet = new ArrayList<Object[]>();
                    List<Object> cellValue = reportMatchResult.getCellValue(((DataLinkDefine)linkDataList.get(0)).getKey().toString());
                    ImportResultRegionObject importResultRegionObjectOfThousandPer = new ImportResultRegionObject();
                    importResultRegionObjectOfThousandPer.setRegionKey(regionData.getKey());
                    ArrayList<ImportErrorDataInfo> importErrorDataInfoListOfThousandPer = new ArrayList<ImportErrorDataInfo>();
                    importResultRegionObjectOfThousandPer.setImportErrorDataInfoList(importErrorDataInfoListOfThousandPer);
                    if (null != cellValue) {
                        for (int j = 0; j < cellValue.size(); ++j) {
                            Object[] rowData = new Object[linkDataList.size()];
                            for (int k6 = 0; k6 < linkDataList.size(); ++k6) {
                                DataLinkDefine linkData = (DataLinkDefine)linkDataList.get(k6);
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
                                if (linkData.getType() == DataLinkType.DATA_LINK_TYPE_FIELD) {
                                    FieldDefine queryFieldDefine4 = null;
                                    try {
                                        queryFieldDefine4 = this.runtimeView.queryFieldDefine(linkData.getLinkExpression());
                                    }
                                    catch (Exception e) {
                                        logger.error(e.getMessage());
                                    }
                                    if (queryFieldDefine4 == null) continue;
                                    if (null == reportMatchResult.getCellValue(linkData.getKey().toString())) {
                                        rowData[k6] = "";
                                    } else {
                                        List<Object> cellValue2 = reportMatchResult.getCellValue(linkData.getKey().toString());
                                        if (cellValue2.size() <= j) continue;
                                        int fieldType = queryFieldDefine4.getType().getValue();
                                        if ((fieldType == FieldType.FIELD_TYPE_TEXT.getValue() || fieldType == FieldType.FIELD_TYPE_STRING.getValue()) && reportMatchResult.getCellValue(linkData.getKey().toString()).get(j) != null) {
                                            rowData[k6] = reportMatchResult.getCellValue(linkData.getKey().toString()).get(j).toString().trim();
                                        } else if (fieldType == FieldType.FIELD_TYPE_DATE.getValue() || fieldType == FieldType.FIELD_TYPE_DATE_TIME.getValue()) {
                                            rowData[k6] = cellValue2.get(j);
                                        } else {
                                            cellNum = (String)reportMatchResult.getCellValue(linkData.getKey().toString()).get(j);
                                            if ("-".equals(cellNum) && (fieldType == FieldType.FIELD_TYPE_FLOAT.getValue() || fieldType == FieldType.FIELD_TYPE_INTEGER.getValue() || fieldType == FieldType.FIELD_TYPE_DECIMAL.getValue())) {
                                                Boolean horizontalBar3 = (Boolean)linkHorizontalBarMap.get(linkData.getKey());
                                                rowData[k6] = horizontalBar3 != null && horizontalBar3.booleanValue() ? "" : cellNum;
                                            } else if (isThousandPerLink) {
                                                String cellFormat = reportMatchResult.getCellFormat(linkData.getKey()).get(j);
                                                boolean isThousanPerNum = this.checkNumIsThousandPer(cellNum, cellFormat, linkData, tempMap, importErrorDataInfoListOfThousandPer, j);
                                                if (isThousanPerNum) {
                                                    if (StringUtils.isNotEmpty((String)cellNum)) {
                                                        cellNum = new BigDecimal(cellNum.contains("\u2030") ? cellNum.replace("\u2030", "") : cellNum).divide(new BigDecimal(1000)).toPlainString();
                                                        rowData[k6] = cellNum;
                                                    } else {
                                                        rowData[k6] = "";
                                                    }
                                                } else {
                                                    rowData[k6] = cellNum;
                                                }
                                            } else {
                                                rowData[k6] = cellNum;
                                            }
                                        }
                                    }
                                    tempMap.put(linkData.getKey(), linkData);
                                } else if (linkData.getType() == DataLinkType.DATA_LINK_TYPE_FMDM || linkData.getType() == DataLinkType.DATA_LINK_TYPE_INFO) {
                                    IFMDMAttribute fmdmAttribute = null;
                                    if (linkFmdmAttr.containsKey(linkData.getLinkExpression())) {
                                        fmdmAttribute = (IFMDMAttribute)linkFmdmAttr.get(linkData.getLinkExpression());
                                    } else {
                                        FMDMAttributeDTO fmdmAttributeDTO = new FMDMAttributeDTO();
                                        fmdmAttributeDTO.setEntityId(formSchemeDefine.getDw());
                                        fmdmAttributeDTO.setFormSchemeKey(formSchemeDefine.getKey());
                                        fmdmAttributeDTO.setAttributeCode(linkData.getLinkExpression());
                                        fmdmAttributeDTO.setZBKey(linkData.getLinkExpression());
                                        fmdmAttribute = this.fMDMAttributeService.queryByZbKey(fmdmAttributeDTO);
                                        if (fmdmAttribute == null) continue;
                                        linkFmdmAttr.put(linkData.getLinkExpression(), fmdmAttribute);
                                    }
                                    if (fmdmAttribute == null) continue;
                                    ColumnModelType columnModelType = fmdmAttribute.getColumnType();
                                    if (null == reportMatchResult.getCellValue(linkData.getKey().toString())) {
                                        rowData[k6] = "";
                                    } else if ((columnModelType == ColumnModelType.STRING || linkData.getType().getValue() == FieldType.FIELD_TYPE_STRING.getValue()) && reportMatchResult.getCellValue(linkData.getKey().toString()).get(j) != null) {
                                        rowData[k6] = reportMatchResult.getCellValue(linkData.getKey()).get(j).toString().trim();
                                    } else {
                                        String cellNum2 = (String)reportMatchResult.getCellValue(linkData.getKey().toString()).get(j);
                                        if ("-".equals(cellNum2) && (columnModelType == ColumnModelType.DOUBLE || columnModelType == ColumnModelType.INTEGER || columnModelType == ColumnModelType.BIGDECIMAL)) {
                                            Boolean horizontalBar4 = (Boolean)linkHorizontalBarMap.get(linkData.getKey());
                                            rowData[k6] = horizontalBar4 != null && horizontalBar4.booleanValue() ? "" : cellNum2;
                                        } else if (isThousandPerLink) {
                                            String cellFormat = reportMatchResult.getCellFormat(linkData.getKey()).get(j);
                                            boolean isThousanPerNum = this.checkNumIsThousandPer(cellNum2, cellFormat, linkData, tempMap, importErrorDataInfoListOfThousandPer, j);
                                            if (isThousanPerNum) {
                                                if (StringUtils.isNotEmpty((String)cellNum2)) {
                                                    cellNum2 = new BigDecimal(cellNum2.contains("\u2030") ? cellNum2.replace("\u2030", "") : cellNum2).divide(new BigDecimal(1000)).toPlainString();
                                                    rowData[k6] = cellNum2;
                                                } else {
                                                    rowData[k6] = "";
                                                }
                                            } else {
                                                rowData[k6] = cellNum2;
                                            }
                                        } else {
                                            rowData[k6] = cellNum2;
                                        }
                                    }
                                    tempMap.put(linkData.getKey(), linkData);
                                }
                                if (rowData[k6] == null || !rowData[k6].equals(IMPORT_INVALID_DATA)) continue;
                                linkDataListRemove.add(linkData);
                            }
                            dataRowSet.add(rowData);
                        }
                    }
                    if (importResultRegionObjectList != null && !importResultRegionObjectList.isEmpty()) {
                        for (ImportResultRegionObject importResultRegionObjectOfCheck : importResultRegionObjectList) {
                            this.collectError(importResultReportObject, importResultRegionObjectOfCheck, reportMatchResult, tempMap, reportMatchResult.getSheetBeginRow());
                        }
                    }
                    importResultReportObject.setImportResultRegionObjectList(importResultRegionObjectList);
                    ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
                    IDataAssist newDataAssist = this.dataAccessProvider.newDataAssist(context);
                    String bizKeyFields = regionData.getBizKeyFields();
                    HashMap regionDimsIndex = new HashMap();
                    HashMap<String, FieldDefine> regionDimsDefine = new HashMap<String, FieldDefine>();
                    if (bizKeyFields != null && !bizKeyFields.equals("")) {
                        try {
                            HashMap<String, Integer> tempLinks = new HashMap<String, Integer>();
                            for (int k7 = 0; k7 < linkDataList.size(); ++k7) {
                                DataLinkDefine dataLinkDefine = (DataLinkDefine)linkDataList.get(k7);
                                if (dataLinkDefine.getLinkExpression() == null) continue;
                                tempLinks.put(dataLinkDefine.getLinkExpression(), k7);
                            }
                            for (String string : split = bizKeyFields.split(";")) {
                                queryFieldDefine2 = this.runtimeView.queryFieldDefine(string);
                                if (queryFieldDefine2 == null) continue;
                                dimensionName = newDataAssist.getDimensionName(queryFieldDefine2);
                                regionDimsIndex.put(dimensionName, tempLinks.get(queryFieldDefine2.getKey()));
                                regionDimsDefine.put(dimensionName, queryFieldDefine2);
                            }
                        }
                        catch (Exception e13) {
                            logger.error(e13.getMessage());
                        }
                    }
                    floatOrderNum = this.getFloatOrderNum(param, regionData.getKey());
                    dimensionCombinationList = new ArrayList<DimensionCombination>();
                    addRowCount = 0;
                    linkIndex = new ArrayList<Integer>();
                    for (DataLinkDefine item : linkDataListRemove) {
                        linkIndex.add(linkDataList.indexOf(item));
                    }
                    linkDataList.removeAll(linkDataListRemove);
                    floatOrderIndex = this.addLinkDataList(regionData, saveDataBuilder, linkDataList, floatOrderIndex);
                    for (int item = 0; item < linkDataListRemove.size(); ++item) {
                        if (linkDataList.contains(linkDataListRemove.get(item))) continue;
                        linkDataList.add((Integer)linkIndex.get(item), linkDataListRemove.get(item));
                    }
                    for (Object[] row : dataRowSet) {
                        ReturnRes addRow;
                        int weight = 0;
                        boolean checkEmptyRow = this.checkEmptyRow(row);
                        if (addRowCount != 0 && regionData.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE && checkEmptyRow || regionData.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE && checkEmptyRow) continue;
                        dimensionSet = param.getDimensionSet();
                        if (regionData.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE) {
                            DimensionCombinationBuilder builder = new DimensionCombinationBuilder(dimensionSet.toDimensionValueSet());
                            if (regionDimsIndex.size() > 0) {
                                for (Map.Entry entry : regionDimsIndex.entrySet()) {
                                    key = (String)entry.getKey();
                                    if (key.equals(RECORDKEY)) {
                                        builder.setValue(RECORDKEY, null, (Object)UUID.randomUUID().toString());
                                        continue;
                                    }
                                    if (regionDimsIndex.get(key) == null) continue;
                                    object2 = row[(Integer)regionDimsIndex.get(key)];
                                    if (regionDimsDefine.containsKey(key)) {
                                        FieldDefine fieldDefine = (FieldDefine)regionDimsDefine.get(key);
                                        if (object2 == null || object2.equals("")) {
                                            object2 = fieldDefine.getDefaultValue();
                                        }
                                    }
                                    queryFieldDefine = null;
                                    try {
                                        queryFieldDefine = this.runtimeView.queryFieldDefine(((DataLinkDefine)linkDataList.get((Integer)regionDimsIndex.get(key))).getLinkExpression());
                                    }
                                    catch (Exception e) {
                                        logger.error(e.getMessage());
                                    }
                                    dataField = (DataField)queryFieldDefine;
                                    if (dataField.getRefDataEntityKey() != null) {
                                        typeParseStrategy = saveDataBuilder.getTypeParseStrategy(DataFieldType.STRING.getValue());
                                        rowDim = new DimensionCombinationBuilder(dimensionSet.toDimensionValueSet());
                                        typeParseStrategy.setRowKey(rowDim.getCombination());
                                        res = typeParseStrategy.checkParse((DataLinkDefine)linkDataList.get((Integer)regionDimsIndex.get(key)), dataField, object2);
                                        if (res.getCode() == 0 && res.getAbstractData() != null) {
                                            object2 = res.getAbstractData().getAsObject();
                                        }
                                    }
                                    builder.setValue(key, null, object2);
                                }
                                if (regionData.getAllowDuplicateKey()) {
                                    builder.setValue(RECORDKEY, null, (Object)UUID.randomUUID().toString());
                                }
                            } else {
                                builder.setValue(RECORDKEY, null, (Object)UUID.randomUUID().toString());
                            }
                            dimensionSet = builder.getCombination();
                        }
                        if ((addRow = saveDataBuilder.addRow((DimensionCombination)dimensionSet, saveRowData)).getCode() != 0) {
                            if (addRow.getCode() == 1202) {
                                for (Map.Entry entry : regionDimsIndex.entrySet()) {
                                    key = (String)entry.getKey();
                                    if (key.equals(RECORDKEY) || (k2 = (Integer)regionDimsIndex.get(key)) == null) continue;
                                    this.collectErrorMessage(reportMatchResult, importResultReportObject, importResultRegionObjectList, regionData, tempMap, linkDataList, k2, addRow.getMessage(), addRowCount);
                                    break;
                                }
                                ++addRowCount;
                                continue;
                            }
                            throw new RuntimeException(addRow.getMessage());
                        }
                        dimensionCombinationList.add((DimensionCombination)dimensionSet);
                        if (floatOrderIndex != -1) {
                            saveDataBuilder.setData(floatOrderIndex, (Object)floatOrderNum);
                            floatOrderNum += 10;
                        }
                        for (int k8 = 0; k8 < row.length; ++k8) {
                            object = row[k8];
                            if (object != null && object.equals(IMPORT_INVALID_DATA)) {
                                ++weight;
                                continue;
                            }
                            setData = saveDataBuilder.setData(k8 - weight, object != null && object.equals("") ? null : object);
                            if (setData.isSuccess()) continue;
                            this.collectErrorMessage(reportMatchResult, importResultReportObject, importResultRegionObjectList, regionData, tempMap, linkDataList, k8, setData.getMessage(), addRowCount);
                        }
                        ++addRowCount;
                    }
                    if (!param.isAppending() && !param.isSplitSheets() && regionData.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE) {
                        saveDataBuilder.enableDeleteBeforeSave(true);
                    }
                    saveReturnRes = saveDataBuilder.checkData();
                    saveResItemList = saveReturnRes.getSaveResItems();
                    this.collectSaveErrorMessage(reportMatchResult, importResultReportObject, importResultRegionObjectList, regionData, tempMap, linkDataList, saveResItemList);
                    if (!importResultRegionObjectList.isEmpty()) {
                        return importResultReportObject;
                    }
                    build = saveDataBuilder.build();
                    try {
                        saveRegionData = this.iDataService.saveRegionData(build);
                        if (StringUtils.isNotEmpty((String)unitCode)) {
                            dataImportLogger.addFormToUnit(unitCode, formData.getFormCode());
                        }
                        if (saveRegionData.getCode() != 0 && saveRegionData.getCode() != 1001) {
                            if (saveRegionData.getSaveResItems() != null && saveRegionData.getSaveResItems().size() > 0) {
                                for (SaveResItem saveResItem : saveRegionData.getSaveResItems()) {
                                    this.collectErrorMessage(reportMatchResult, importResultReportObject, importResultRegionObjectList, regionData, tempMap, saveResItem.getLinkKey(), saveResItem.getMessage(), saveResItem.getRowIndex() != -1 ? saveResItem.getRowIndex() - 1 : addRowCount);
                                }
                                return importResultReportObject;
                            }
                            importResultReportObject.getReportError().setErrorCode(ErrorCode.SYSTEMERROR);
                            importResultReportObject.setReportName(formData.getFormCode() + SEPARATOR_ONE + formData.getTitle());
                            importResultReportObject.getReportError().setErrorInfo(saveRegionData.getMessage());
                            return importResultReportObject;
                        }
                        if (saveRegionData.getMessage() != null && saveRegionData.getMessage().equals("\u533a\u57df\u53ea\u8bfb,\u7ec8\u6b62\u6267\u884c\u6e05\u9664\u533a\u57df\u6570\u636e")) {
                            importResultReportObject.getReportError().setErrorCode(ErrorCode.SYSTEMERROR);
                            importResultReportObject.setReportName(formData.getFormCode() + SEPARATOR_ONE + formData.getTitle());
                            importResultReportObject.getReportError().setErrorInfo(saveRegionData.getMessage());
                            return importResultReportObject;
                        }
                    }
                    catch (CrudException | CrudOperateException e) {
                        if (StringUtils.isNotEmpty((String)unitCode)) {
                            dataImportLogger.addFormToUnit(unitCode, formData.getFormCode());
                        }
                        if (e instanceof CrudSaveException) {
                            e1 = (CrudSaveException)e;
                            List items = e1.getItems();
                            if (items != null && !items.isEmpty()) {
                                for (SaveResItem saveResItem : items) {
                                    this.collectErrorMessage(reportMatchResult, importResultReportObject, importResultRegionObjectList, regionData, tempMap, saveResItem.getLinkKey(), saveResItem.getMessage(), saveResItem.getRowIndex() != -1 ? saveResItem.getRowIndex() - 1 : addRowCount);
                                }
                                return importResultReportObject;
                            }
                        }
                        if (!(e instanceof CrudOperateException)) break block193;
                        importResultReportObject.getReportError().setErrorCode(ErrorCode.SYSTEMERROR);
                        importResultReportObject.setReportName(formData.getFormCode() + SEPARATOR_ONE + formData.getTitle());
                        importResultReportObject.getReportError().setErrorInfo(e.getMessage());
                        return importResultReportObject;
                    }
                }
                importResultRegionObject = new ImportResultRegionObject();
                importResultRegionObject.setRegionKey(regionData.getKey());
                if (importResultRegionObjectList != null && importResultRegionObjectList.size() > 0) {
                    for (ImportResultRegionObject importResultRegionObjectOfCheck : importResultRegionObjectList) {
                        this.collectError(importResultReportObject, importResultRegionObjectOfCheck, reportMatchResult, tempMap, reportMatchResult.getSheetBeginRow());
                    }
                }
                this.collectError(importResultReportObject, importResultRegionObject, reportMatchResult, tempMap, reportMatchResult.getSheetBeginRow());
                continue;
            }
            if (linkDataList.isEmpty() || !formData.getFormType().equals((Object)FormType.FORM_TYPE_ACCOUNT) || regionData.getRegionTop() <= 1) continue;
            try {
                this.sbRegionImport(reportMatchResult, param, formData.getKey(), regionData, tempMap);
                continue;
            }
            catch (Exception e) {
                logger.error(e.getMessage());
                ImportResultRegionObject importResultRegionObject3 = new ImportResultRegionObject();
                importResultRegionObject3.setRegionKey(regionData.getKey());
                importResultRegionObject3.getRegionError().setErrorCode(ErrorCode.REGIONERROR);
                importResultRegionObject3.getRegionError().setErrorInfo(e.getMessage());
                this.collectError(importResultReportObject, importResultRegionObject3, reportMatchResult, tempMap, reportMatchResult.getSheetBeginRow());
                return importResultReportObject;
            }
        }
        return importResultReportObject;
    }

    private int addLinkDataList(DataRegionDefine regionData, SaveDataBuilder saveDataBuilder, List<DataLinkDefine> linkDataList, int floatOrderIndex) {
        if (!linkDataList.isEmpty()) {
            Iterator<DataLinkDefine> iterator = linkDataList.iterator();
            while (iterator.hasNext()) {
                DataLinkDefine next = iterator.next();
                int addLink = saveDataBuilder.addLink(next.getKey());
                if (addLink != -1) continue;
                iterator.remove();
            }
            if (regionData.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE) {
                floatOrderIndex = saveDataBuilder.addLink("FLOATORDER");
            }
        }
        return floatOrderIndex;
    }

    private int getFloatOrderNum(UploadParam param, String regionKey) {
        int floatOrderNum = 10;
        if (param.isSplitSheets()) {
            QueryInfoBuilder queryInfoBuilder = QueryInfoBuilder.create((String)regionKey, (DimensionCombination)param.getDimensionSet());
            queryInfoBuilder.orderBy(new LinkSort("FLOATORDER", SortMode.DESC));
            PageInfo pageInfo = new PageInfo();
            pageInfo.setRowsPerPage(1);
            pageInfo.setPageIndex(0);
            queryInfoBuilder.setPage(pageInfo);
            IRegionDataSet regionDataSet = this.dataQueryService.queryRegionData(queryInfoBuilder.build());
            try {
                IDataValue dataValue;
                List rowDatas = regionDataSet.getRowData();
                if (rowDatas != null && !rowDatas.isEmpty() && (dataValue = ((IRowData)regionDataSet.getRowData().get(0)).getDataValueByLink("FLOATORDER")) != null) {
                    floatOrderNum = dataValue.getAsInt() + 10;
                }
            }
            catch (DataTypeException e) {
                logger.error("\u67e5\u8be2\u533a\u57dffloatOrder\u6700\u5927\u503c\u8fc7\u7a0b\u4e2d\u53d1\u751f\u9519\u8bef\uff1a{}", (Object)e.getMessage(), (Object)e);
            }
        }
        return floatOrderNum;
    }

    private void dimensionCombination2Collection(FormDefine formData, UploadParam param, DataRegionDefine regionData, Map<String, Integer> regionDimsIndex, List<DimensionCombination> dimensionCombinationList, DimensionCollection buildDimensionCollection) throws CrudOperateException {
        if (!FormType.FORM_TYPE_NEWFMDM.equals((Object)formData.getFormType())) {
            if (buildDimensionCollection != null) {
                ClearInfoBuilder builder = ClearInfoBuilder.create((String)regionData.getKey(), (DimensionCollection)buildDimensionCollection);
                this.iDataService.clearRegionData(builder.build());
            } else {
                ClearInfoBuilder builder = ClearInfoBuilder.create((String)regionData.getKey(), (DimensionCombination)param.getDimensionSet());
                this.iDataService.clearRegionData(builder.build());
            }
        }
    }

    private DimensionCollection buildDimensionCollection(Map<String, DimensionValue> dimensionSet, String formSchemeKey, Map<String, Integer> regionDimsIndex) {
        EntityDimData dw = this.dataAccesslUtil.getDwEntityDimData(formSchemeKey);
        EntityDimData period = this.dataAccesslUtil.getPeriodEntityDimData(formSchemeKey);
        List dimEntities = this.dataAccesslUtil.getDimEntityDimData(formSchemeKey);
        FormSchemeDefine formScheme = this.dataAccesslUtil.queryFormScheme(formSchemeKey);
        String dataScheme = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey()).getDataScheme();
        DimensionCollectionBuilder builder = new DimensionCollectionBuilder();
        this.buildPeriodDimension(builder, period, dimensionSet);
        this.buildDimension(dimensionSet, formSchemeKey, dataScheme, builder, dw, "PROVIDER_FILTERDWBYVERSION", true);
        for (EntityDimData dimEntity : dimEntities) {
            DimensionValue currenCy = dimensionSet.get("MD_CURRENCY");
            if ("MD_CURRENCY".equals(dimEntity.getDimensionName()) && currenCy != null && ("PROVIDER_BASECURRENCY".equals(currenCy.getValue()) || "PROVIDER_PBASECURRENCY".equals(currenCy.getValue()))) {
                this.buildCurrency(dataScheme, dimensionSet.get("MD_CURRENCY").getValue(), builder, dimEntity);
                continue;
            }
            if ("ADJUST".equals(dimEntity.getDimensionName())) {
                DimensionValue adjustDim = dimensionSet.get("ADJUST");
                if (Objects.isNull(adjustDim)) continue;
                String adjust = adjustDim.getValue();
                builder.setEntityValue(dimEntity.getDimensionName(), "ADJUST", new Object[]{adjust});
                continue;
            }
            boolean entityRefer = this.entityMetaService.estimateEntityRefer(dw.getEntityId(), dimEntity.getEntityId());
            if (entityRefer) {
                this.buildDimension(dimensionSet, formSchemeKey, dataScheme, builder, dimEntity, "PROVIDER_FILTERDIMBYDW", false);
                continue;
            }
            this.buildDimension(dimensionSet, formSchemeKey, dataScheme, builder, dimEntity, "PROVIDER_ALLNODE", false);
        }
        if (regionDimsIndex != null && regionDimsIndex.size() > 0) {
            for (String key : regionDimsIndex.keySet()) {
                DimensionValue dimensionValue;
                Object value = builder.getCollection().combineWithoutVarDim().getValue(key);
                if (value != null || !dimensionSet.containsKey(key) || (dimensionValue = dimensionSet.get(key)) == null || dimensionValue.getValue() == null) continue;
                String[] split = dimensionValue.getValue().split(";");
                builder.setValue(key, new Object[]{Arrays.asList(split)});
            }
        }
        return builder.getCollection();
    }

    private void buildDimension(Map<String, DimensionValue> dimensionSet, String formSchemeKey, String dataScheme, DimensionCollectionBuilder builder, EntityDimData entityData, String type, boolean dw) {
        String value;
        DimensionValue dimensionValue = dimensionSet.get(entityData.getDimensionName());
        List<Object> choosedValues = new ArrayList();
        DimensionProviderData dimensionProviderData = new DimensionProviderData(choosedValues, dataScheme);
        if (dimensionValue != null && dimensionValue.getValue() != null && StringUtils.isNotEmpty((String)(value = dimensionValue.getValue()))) {
            choosedValues = Arrays.asList(value.split(";"));
            dimensionProviderData.setChoosedValues(choosedValues);
        }
        VariableDimensionValueProvider dimensionProvider = this.dimensionProviderFactory.getDimensionProvider(type, dimensionProviderData);
        if (dw) {
            String rowFilterExpression;
            EntityViewDefine view = this.runTimeViewController.getViewByFormSchemeKey(formSchemeKey);
            if (view != null && StringUtils.isNotEmpty((String)(rowFilterExpression = view.getRowFilterExpression()))) {
                dimensionProviderData.setFilter(rowFilterExpression);
            }
            if (!choosedValues.isEmpty()) {
                if (choosedValues.size() == 1) {
                    builder.setDWValue(entityData.getDimensionName(), entityData.getEntityId(), new Object[]{choosedValues.get(0)});
                } else {
                    builder.setDWValue(entityData.getDimensionName(), entityData.getEntityId(), new Object[]{choosedValues});
                }
            } else {
                builder.addVariableDW(entityData.getDimensionName(), entityData.getEntityId(), dimensionProvider);
            }
        } else if ("PROVIDER_ALLNODE".equals(type) && !CollectionUtils.isEmpty(choosedValues)) {
            builder.setEntityValue(entityData.getDimensionName(), entityData.getEntityId(), choosedValues.toArray());
        } else {
            builder.addVariableDimension(entityData.getDimensionName(), entityData.getEntityId(), dimensionProvider);
        }
    }

    private void buildPeriodDimension(DimensionCollectionBuilder builder, EntityDimData period, Map<String, DimensionValue> dimensionSet) {
        String value = dimensionSet.get(period.getDimensionName()).getValue();
        ArrayList<String> choosedValues = new ArrayList<String>();
        if (StringUtils.isNotEmpty((String)value) && value.contains(";")) {
            choosedValues.addAll(Arrays.asList(value.split(";")));
            builder.setEntityValue(period.getDimensionName(), period.getEntityId(), new Object[]{choosedValues});
        } else if (StringUtils.isNotEmpty((String)value)) {
            builder.setEntityValue(period.getDimensionName(), period.getEntityId(), new Object[]{value});
        }
    }

    private void buildCurrency(String dataScheme, String type, DimensionCollectionBuilder builder, EntityDimData dimEntity) {
        DimensionProviderData dimensionProviderData = new DimensionProviderData(null, dataScheme);
        VariableDimensionValueProvider dimensionProvider = this.dimensionProviderFactory.getDimensionProvider(type, dimensionProviderData);
        builder.addVariableDimension(dimEntity.getDimensionName(), dimEntity.getEntityId(), dimensionProvider);
    }

    private boolean checkEmptyRow(Object[] row) {
        boolean isEmpty = true;
        for (Object object : row) {
            if (object == null || object.equals("") || object.equals(IMPORT_INVALID_DATA)) continue;
            return false;
        }
        return isEmpty;
    }

    private void collectSaveErrorMessage(ReportMatchResult reportMatchResult, ImportResultReportObject importResultReportObject, List<ImportResultRegionObject> importResultRegionObjectList, DataRegionDefine regionData, Map<String, DataLinkDefine> tempMap, List<DataLinkDefine> linkDataList, List<SaveResItem> saveResItemList) {
        Map dataLinkDefineMap = linkDataList.stream().collect(HashMap::new, (map, param) -> map.put(param.getKey(), param), HashMap::putAll);
        ArrayList<ImportErrorDataInfo> importErrorDataInfoList = new ArrayList<ImportErrorDataInfo>();
        for (SaveResItem saveResItem : saveResItemList) {
            if (saveResItem.getLevel() != 0) continue;
            DataLinkDefine dataLinkDefine = (DataLinkDefine)dataLinkDefineMap.get(saveResItem.getLinkKey());
            List messages = saveResItem.getMessages();
            String message = messages != null && !messages.isEmpty() ? (String)messages.get(0) : "";
            ResultErrorInfo dataError = new ResultErrorInfo();
            dataError.setErrorCode(ErrorCode.DATAERROR);
            dataError.setErrorInfo(message);
            ImportErrorDataInfo importFieldErrorDataInfo = new ImportErrorDataInfo();
            importFieldErrorDataInfo.setDataLinkKey(dataLinkDefine == null ? null : dataLinkDefine.getKey());
            importFieldErrorDataInfo.setFieldKey(dataLinkDefine == null ? null : dataLinkDefine.getLinkExpression());
            importFieldErrorDataInfo.setDataIndex(Integer.valueOf(saveResItem.getRowIndex()));
            importFieldErrorDataInfo.setDataError(dataError);
            importFieldErrorDataInfo.setRegionKey(regionData.getKey());
            importErrorDataInfoList.add(importFieldErrorDataInfo);
        }
        if (!importErrorDataInfoList.isEmpty()) {
            ImportResultRegionObject importResultRegionObject = new ImportResultRegionObject();
            importResultRegionObject.setRegionKey(regionData.getKey());
            importResultRegionObject.setImportErrorDataInfoList(importErrorDataInfoList);
            importResultRegionObjectList.add(importResultRegionObject);
            importResultReportObject.setImportResultRegionObjectList(importResultRegionObjectList);
            this.collectError(importResultReportObject, importResultRegionObject, reportMatchResult, tempMap, reportMatchResult.getSheetBeginRow());
        }
    }

    private void collectErrorMessage(ReportMatchResult reportMatchResult, ImportResultReportObject importResultReportObject, List<ImportResultRegionObject> importResultRegionObjectList, DataRegionDefine regionData, Map<String, DataLinkDefine> tempMap, List<DataLinkDefine> linkDataList, int k, String message, int errIndex) {
        ImportResultRegionObject importResultRegionObject = new ImportResultRegionObject();
        importResultReportObject.setImportResultRegionObjectList(importResultRegionObjectList);
        ImportErrorDataInfo importFieldErrorDataInfo = new ImportErrorDataInfo();
        importFieldErrorDataInfo.setDataLinkKey(linkDataList.get(k).getKey());
        importFieldErrorDataInfo.setFieldKey(linkDataList.get(k).getLinkExpression());
        importFieldErrorDataInfo.setDataIndex(Integer.valueOf(errIndex));
        ResultErrorInfo dataError = new ResultErrorInfo();
        dataError.setErrorCode(ErrorCode.DATAERROR);
        dataError.setErrorInfo(message);
        importFieldErrorDataInfo.setDataError(dataError);
        importFieldErrorDataInfo.setRegionKey(regionData.getKey());
        ArrayList<ImportErrorDataInfo> importErrorDataInfoList = new ArrayList<ImportErrorDataInfo>();
        importErrorDataInfoList.add(importFieldErrorDataInfo);
        importResultRegionObject.setRegionKey(linkDataList.get(k).getRegionKey());
        importResultRegionObject.setImportErrorDataInfoList(importErrorDataInfoList);
        importResultRegionObjectList.add(importResultRegionObject);
        this.collectError(importResultReportObject, importResultRegionObject, reportMatchResult, tempMap, reportMatchResult.getSheetBeginRow());
    }

    private void collectErrorMessage(ReportMatchResult reportMatchResult, ImportResultReportObject importResultReportObject, List<ImportResultRegionObject> importResultRegionObjectList, DataRegionDefine regionData, Map<String, DataLinkDefine> tempMap, String linkKey, String message, int errIndex) {
        DataLinkDefine queryDataLinkDefine = null;
        if (linkKey != null && !linkKey.equals("")) {
            queryDataLinkDefine = this.runtimeView.queryDataLinkDefine(linkKey);
        }
        ImportResultRegionObject importResultRegionObject = new ImportResultRegionObject();
        importResultReportObject.setImportResultRegionObjectList(importResultRegionObjectList);
        ImportErrorDataInfo importFieldErrorDataInfo = new ImportErrorDataInfo();
        if (queryDataLinkDefine != null) {
            importFieldErrorDataInfo.setDataLinkKey(queryDataLinkDefine.getKey());
            importFieldErrorDataInfo.setFieldKey(queryDataLinkDefine.getLinkExpression());
            importResultRegionObject.setRegionKey(queryDataLinkDefine.getRegionKey());
        } else {
            importResultRegionObject.setRegionKey(regionData.getKey());
        }
        importFieldErrorDataInfo.setDataIndex(Integer.valueOf(errIndex));
        ResultErrorInfo dataError = new ResultErrorInfo();
        dataError.setErrorCode(ErrorCode.DATAERROR);
        dataError.setErrorInfo(message);
        importFieldErrorDataInfo.setDataError(dataError);
        importFieldErrorDataInfo.setRegionKey(regionData.getKey());
        ArrayList<ImportErrorDataInfo> importErrorDataInfoList = new ArrayList<ImportErrorDataInfo>();
        importErrorDataInfoList.add(importFieldErrorDataInfo);
        importResultRegionObject.setImportErrorDataInfoList(importErrorDataInfoList);
        importResultRegionObjectList.add(importResultRegionObject);
        this.collectError(importResultReportObject, importResultRegionObject, reportMatchResult, tempMap, reportMatchResult.getSheetBeginRow());
    }

    private static String checkEnumLink(DataLinkDefine dataLinkDefine, IFMDMAttribute fmdmAttribute) {
        IEntityMetaService entityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
        String linkInfo = "\u5355\u5143\u683c (key:" + dataLinkDefine.getKey() + ";title:" + dataLinkDefine.getTitle() + "; \u5750\u6807[" + dataLinkDefine.getRowNum() + "," + dataLinkDefine.getColNum() + "])\u914d\u7f6e\u7684";
        try {
            String pname;
            IEntityModel entityModel = entityMetaService.getEntityModel(fmdmAttribute.getReferEntityId());
            Iterator attributes = entityModel.getAttributes();
            HashMap<String, IEntityAttribute> attributeMap = new HashMap<String, IEntityAttribute>();
            while (attributes.hasNext()) {
                IEntityAttribute attribute = (IEntityAttribute)attributes.next();
                attributeMap.put(attribute.getCode(), attribute);
            }
            if (StringUtils.isNotEmpty((String)dataLinkDefine.getEnumShowFullPath()) && !attributeMap.containsKey(pname = dataLinkDefine.getEnumShowFullPath())) {
                return linkInfo + "\u5168\u8def\u5f84\u6307\u6807\u672a\u627e\u5230:" + pname;
            }
            if (StringUtils.isNotEmpty((String)dataLinkDefine.getCaptionFieldsString())) {
                String[] captionFields;
                for (String captionField : captionFields = dataLinkDefine.getCaptionFieldsString().split(";")) {
                    if (attributeMap.containsKey(captionField)) continue;
                    return linkInfo + "\u663e\u793a\u6307\u6807\u672a\u627e\u5230:" + captionField;
                }
            }
            if (StringUtils.isNotEmpty((String)dataLinkDefine.getDropDownFieldsString())) {
                String[] dropDownFields;
                for (String dropDownField : dropDownFields = dataLinkDefine.getDropDownFieldsString().split(";")) {
                    if (attributeMap.containsKey(dropDownField)) continue;
                    return linkInfo + "\u4e0b\u62c9\u6307\u6807\u672a\u627e\u5230:" + dropDownField;
                }
            }
        }
        catch (Exception e) {
            return linkInfo + e.getMessage();
        }
        return "";
    }

    private Object dataTransferOri(FieldDefine def, DataLinkDefine dataLinkDefine, Object data, UploadParam param, Map<String, IEntityTable> cache, boolean isMatchAll) throws Exception {
        if (null == data || data.equals("")) {
            return data;
        }
        DataField dataField = (DataField)def;
        if (dataField.getRefDataEntityKey() != null) {
            return data;
        }
        if (dataField.getRefDataEntityKey() != null) {
            IEntityTable iEntityTable = null;
            if (cache.containsKey(dataLinkDefine.getKey() + dataField.getRefDataEntityKey())) {
                iEntityTable = cache.get(dataLinkDefine.getKey() + dataField.getRefDataEntityKey());
            } else {
                EntityViewDefine queryEntityView = this.runtimeView.getViewByLinkDefineKey(dataLinkDefine.getKey());
                DimensionValueSet masterKey = new DimensionValueSet();
                masterKey.setValue("DATATIME", param.getDimensionSet().getValue("DATATIME"));
                ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
                FormSchemeDefine formScheme = this.runtimeView.getFormScheme(param.getFormSchemeKey());
                String dimensionName = this.entityMetaService.getDimensionName(formScheme.getDw());
                Object value = param.getDimensionSet().getValue(dimensionName);
                String unitKey = value != null ? value.toString() : null;
                try {
                    TableContext tableContext = new TableContext();
                    tableContext.setTaskKey(formScheme.getTaskKey());
                    tableContext.setFormSchemeKey(formScheme.getKey());
                    tableContext.setDimensionSet(masterKey);
                    iEntityTable = this.getIEntityTable(queryEntityView, tableContext, executorContext, unitKey);
                }
                catch (Exception e1) {
                    logger.error(e1.getMessage());
                }
                cache.put(dataLinkDefine.getKey() + dataField.getRefDataEntityKey(), iEntityTable);
            }
            if (null != iEntityTable) {
                FieldDefine refer = null;
                String referCode = "";
                DataField df = (DataField)def;
                if (null != df.getRefDataEntityKey()) {
                    try {
                        refer = this.dataDefinitionRuntimeController.queryFieldDefine(df.getRefDataEntityKey());
                        if (refer == null) {
                            ColumnModelDefine colum = this.dataModelService.getColumnModelDefineByID(df.getRefDataFieldKey());
                            referCode = colum.getCode();
                        } else {
                            referCode = refer.getCode();
                        }
                    }
                    catch (Exception e) {
                        logger.debug("\u6ca1\u6709\u627e\u5173\u8054\u7684\u6307\u6807{}", (Object)e.getMessage());
                    }
                }
                boolean allowNotLeafNodeRefer = dataLinkDefine.getAllowNotLeafNodeRefer();
                IEntityRow findByCode = iEntityTable.findByCode(data.toString());
                IEntityRow byEntityKey = iEntityTable.findByEntityKey(data.toString());
                if (data.toString().contains("|")) {
                    int directChildCount;
                    String[] split = data.toString().split("\\|");
                    findByCode = iEntityTable.findByCode(split[0]);
                    if (findByCode == null) {
                        findByCode = iEntityTable.findByCode(split[1]);
                    }
                    if (!allowNotLeafNodeRefer && findByCode != null && (directChildCount = iEntityTable.getDirectChildCount(findByCode.getEntityKeyData())) != 0) {
                        findByCode = null;
                    }
                    if ((byEntityKey = iEntityTable.findByEntityKey(split[0])) == null) {
                        byEntityKey = iEntityTable.findByEntityKey(split[1]);
                    }
                    if (!allowNotLeafNodeRefer && byEntityKey != null && (directChildCount = iEntityTable.getDirectChildCount(byEntityKey.getEntityKeyData())) != 0) {
                        findByCode = null;
                    }
                }
                if (findByCode != null) {
                    return findByCode.getEntityKeyData();
                }
                if (null != byEntityKey) {
                    return byEntityKey.getEntityKeyData();
                }
                List allRows = iEntityTable.getAllRows();
                String multiTitle = "";
                String string = data.toString();
                String[] multiCodeTitle = string.split(";");
                ArrayList<String> asList = new ArrayList<String>();
                asList.addAll(Arrays.asList(multiCodeTitle));
                for (IEntityRow row : allRows) {
                    String next;
                    Iterator iterator;
                    if (isMatchAll) {
                        if (row.getCode().equals(string) || row.getTitle().equals(string) || string.contains("|") && (row.getCode().equals(string.split("\\|")[0]) || row.getCode().equals(string.split("\\|")[1]))) {
                            if (Consts.EntityField.ENTITY_FIELD_KEY.fieldKey.equals(referCode)) {
                                return null != row.getEntityKeyData() ? row.getEntityKeyData() : row.getCode();
                            }
                            return row.getCode();
                        }
                        if (!string.contains(";")) continue;
                        iterator = asList.iterator();
                        while (iterator.hasNext()) {
                            next = (String)iterator.next();
                            if (!row.getCode().equals(next) && !row.getTitle().equals(next) && (!next.contains("|") || !row.getCode().equals(next.split("\\|")[0]) && !row.getCode().equals(next.split("\\|")[1]))) continue;
                            multiTitle = multiTitle.length() > 0 ? multiTitle + ";" + row.getEntityKeyData() : multiTitle + row.getEntityKeyData();
                            iterator.remove();
                        }
                        continue;
                    }
                    if (row.getCode().equalsIgnoreCase(string) || row.getTitle().equalsIgnoreCase(string) || string.contains("|") && (row.getCode().equalsIgnoreCase(string.split("\\|")[0]) || row.getCode().equalsIgnoreCase(string.split("\\|")[1]))) {
                        if (Consts.EntityField.ENTITY_FIELD_KEY.fieldKey.equals(referCode)) {
                            return null != row.getEntityKeyData() ? row.getEntityKeyData() : row.getCode();
                        }
                        return row.getCode();
                    }
                    if (!string.contains(";")) continue;
                    iterator = asList.iterator();
                    while (iterator.hasNext()) {
                        next = (String)iterator.next();
                        if (!row.getCode().equalsIgnoreCase(next) && !row.getTitle().equalsIgnoreCase(next) && (!next.contains("\\|") || !row.getCode().equalsIgnoreCase(next.split("\\|")[0]) && !row.getCode().equalsIgnoreCase(next.split("\\|")[1]))) continue;
                        multiTitle = multiTitle.length() > 0 ? multiTitle + ";" + row.getEntityKeyData() : multiTitle + row.getEntityKeyData();
                        iterator.remove();
                    }
                }
                if (!multiTitle.equals("")) {
                    return multiTitle;
                }
                if (dataLinkDefine != null && !dataLinkDefine.getAllowUndefinedCode().booleanValue()) {
                    throw new Exception("\u6307\u6807:" + def.getCode() + "\u679a\u4e3ecode\u4e0d\u5b58\u5728:" + data.toString());
                }
                if (dataLinkDefine != null && !dataLinkDefine.getAllowUndefinedCode().booleanValue()) {
                    throw new Exception("\u6307\u6807:" + def.getCode() + "\u679a\u4e3ecode\u4e0d\u5b58\u5728:" + data.toString());
                }
            }
            return data;
        }
        if (def.getType().equals((Object)FieldType.FIELD_TYPE_LOGIC)) {
            data = data.equals("\u662f") || data.equals("\u221a") || data.equals("1");
        } else if (1 == def.getType().getValue() || 3 == def.getType().getValue() || 8 == def.getType().getValue()) {
            BigDecimal initialBigDecimal = null;
            try {
                if (!StringUtils.isEmpty((String)data.toString())) {
                    if (data.toString().contains(",")) {
                        Number number = NumberFormat.getIntegerInstance(Locale.getDefault()).parse(data.toString());
                        initialBigDecimal = new BigDecimal(number.doubleValue());
                    } else {
                        initialBigDecimal = new BigDecimal(data.toString());
                    }
                }
                return initialBigDecimal;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return data;
    }

    private IEntityTable getIEntityTable(EntityViewDefine entityView, TableContext tableContext, ExecutorContext context, String unitKey) throws Exception {
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(entityView);
        entityQuery.setAuthorityOperations(AuthorityType.None);
        DimensionValueSet masterKey = new DimensionValueSet();
        masterKey.setValue("DATATIME", tableContext.getDimensionSet().getValue("DATATIME"));
        entityQuery.setMasterKeys(masterKey);
        if (this.entityIsolateCondition != null) {
            try {
                String isolateCondition = this.entityIsolateCondition.queryIsoCondition(tableContext.getTaskKey(), tableContext.getDimensionSet().getValue("DATATIME").toString(), entityView.getEntityId());
                if (isolateCondition != null && !isolateCondition.equals("")) {
                    entityQuery.setIsolateCondition(isolateCondition);
                } else if (unitKey != null && !unitKey.equals("")) {
                    entityQuery.setIsolateCondition(unitKey);
                }
            }
            catch (Exception e) {
                logger.debug(e.getMessage());
            }
        } else if (unitKey != null && !unitKey.equals("")) {
            entityQuery.setIsolateCondition(unitKey);
        }
        return entityQuery.executeFullBuild((IContext)context);
    }

    private boolean checkNumIsThousandPer(String cellNum, String cellFormat, DataLinkDefine linkData, Map<String, DataLinkDefine> tempMap, List<ImportErrorDataInfo> importErrorDataInfoListOfThousandPer, int num) {
        boolean isThousanPerNum = false;
        if (StringUtils.isEmpty((String)cellNum)) {
            isThousanPerNum = true;
        } else if (cellNum.contains("\u2030") || cellFormat.contains("\u2030")) {
            if (cellNum.contains("\u2030")) {
                cellNum = cellNum.replace("\u2030", "");
            }
            try {
                cellNum = new BigDecimal(cellNum).divide(new BigDecimal(1000)).toPlainString();
                isThousanPerNum = true;
            }
            catch (Exception e) {
                tempMap.put(linkData.getKey(), linkData);
                ImportErrorDataInfo importErrorDataInfo = new ImportErrorDataInfo();
                importErrorDataInfo.setDataLinkKey(linkData.getKey());
                importErrorDataInfo.setDataIndex(Integer.valueOf(num));
                importErrorDataInfo.getDataError().setErrorCode(ErrorCode.DATAERROR);
                importErrorDataInfo.getDataError().setErrorInfo("\u5bfc\u5165\u6570\u636e\u8f6c\u6362\u5343\u5206\u6bd4\u5f02\u5e38\uff01\u9519\u8bef\u503c:" + cellNum);
                importErrorDataInfoListOfThousandPer.add(importErrorDataInfo);
            }
        } else {
            FieldDefine queryFieldDefine = null;
            if (linkData.getType() == DataLinkType.DATA_LINK_TYPE_FIELD) {
                try {
                    queryFieldDefine = this.runtimeView.queryFieldDefine(linkData.getLinkExpression());
                }
                catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
            tempMap.put(linkData.getKey(), linkData);
            ImportErrorDataInfo importErrorDataInfo = new ImportErrorDataInfo();
            importErrorDataInfo.setDataLinkKey(linkData.getKey());
            importErrorDataInfo.setDataIndex(Integer.valueOf(num));
            importErrorDataInfo.getDataError().setErrorCode(ErrorCode.DATAERROR);
            String errorInfo = queryFieldDefine != null ? "\u6307\u6807:" + queryFieldDefine.getTitle() : "\u6307\u6807:" + null + " ;\u53ea\u80fd\u8f93\u5165\u5343\u5206\u6bd4\u6570\u636e\u3002\u9519\u8bef\u503c:" + cellNum;
            importErrorDataInfo.getDataError().setErrorInfo(errorInfo);
            importErrorDataInfo.setFieldTitle(queryFieldDefine != null ? queryFieldDefine.getTitle() : null);
            importErrorDataInfoListOfThousandPer.add(importErrorDataInfo);
        }
        return isThousanPerNum;
    }

    private void sbRegionImport(ReportMatchResult reportMatchResult, UploadParam param, String formKey, DataRegionDefine regionData, Map<String, DataLinkDefine> tempMap) throws Exception {
        List linkDataList = this.runtimeView.getAllLinksInRegion(regionData.getKey());
        List<Object> cellValue = reportMatchResult.getCellValue(((DataLinkDefine)linkDataList.get(0)).getKey().toString());
        DimensionValueSet dimensionSet = param.getDimensionSet().toDimensionValueSet();
        TableContext tableContext = new TableContext(null, param.getFormSchemeKey(), formKey, dimensionSet, OptTypes.FORM, "excel", "", null);
        if (param.isAppending()) {
            tableContext.setFloatImpOpt(0);
        }
        tableContext.setExpEnumFields(ExpViewFields.TITLE);
        RegionData regionDataS = new RegionData();
        regionDataS.initialize(this.runtimeView.queryDataRegionDefine(regionData.getKey()));
        ArrayList<ExportFieldDefine> fields = new ArrayList<ExportFieldDefine>();
        String tableKey = this.runtimeView.queryFieldDefine(((DataLinkDefine)linkDataList.get(0)).getLinkExpression()).getOwnerTableKey();
        List dataFields = this.runtimeDataSchemeService.getDataFieldByTableKeyAndKind(tableKey, new DataFieldKind[]{DataFieldKind.PUBLIC_FIELD_DIM});
        int publicFieldDimNum = 0;
        for (DataField dataField : dataFields) {
            if (Objects.equals(dataField.getCode(), "DATATIME")) continue;
            fields.add(new ExportFieldDefine(dataField.getTitle(), dataField.getCode(), 0, 0));
            ++publicFieldDimNum;
        }
        for (DataLinkDefine link : linkDataList) {
            FieldDefine queryFieldDefine = this.runtimeView.queryFieldDefine(link.getLinkExpression());
            if (queryFieldDefine == null) continue;
            ExportFieldDefine e = new ExportFieldDefine(link.getTitle(), queryFieldDefine.getCode(), 0, 0);
            fields.add(e);
        }
        SBRegionDataSet regionDataSet = new SBRegionDataSet(tableContext, regionDataS, fields);
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(param.getFormSchemeKey());
        String dwEntityID = this.dataAccesslUtil.contextEntityId(formSchemeDefine.getDw());
        String dwDimensionName = this.entityMetaService.getDimensionName(dwEntityID);
        Object orgCodes = dimensionSet.getValue(dwDimensionName);
        if (orgCodes != null) {
            HashSet<String> mdCodeScop = null;
            if (orgCodes instanceof String) {
                mdCodeScop = new HashSet<String>(Arrays.asList(((String)orgCodes).split(";")));
            } else if (orgCodes instanceof List) {
                mdCodeScop = new HashSet((List)orgCodes);
            }
            regionDataSet.setMdCodeScop(mdCodeScop);
        }
        if (null != cellValue) {
            for (int j = 0; j < cellValue.size(); ++j) {
                boolean isAllNull = true;
                ArrayList<Object> rowData = new ArrayList<Object>(linkDataList.size() + publicFieldDimNum);
                for (int i = 0; i < publicFieldDimNum; ++i) {
                    if (((ExportFieldDefine)fields.get(i)).getCode().equals("MDCODE")) {
                        rowData.add(param.getDimensionSet().getDWDimensionValue().getValue());
                        continue;
                    }
                    rowData.add(param.getDimensionSet().getValue(((ExportFieldDefine)fields.get(i)).getCode()));
                }
                int k = 0;
                int l = publicFieldDimNum;
                while (k < linkDataList.size()) {
                    FieldDefine queryFieldDefine;
                    rowData.add(null);
                    DataLinkDefine linkData = (DataLinkDefine)linkDataList.get(k);
                    if (linkData.getType() == DataLinkType.DATA_LINK_TYPE_FIELD && (queryFieldDefine = this.runtimeView.queryFieldDefine(linkData.getLinkExpression())) != null) {
                        Object obj;
                        List<Object> cellValues = reportMatchResult.getCellValue(linkData.getKey());
                        if (cellValues != null && (obj = cellValues.get(j)) != null && !IMPORT_INVALID_DATA.equals(obj)) {
                            if (queryFieldDefine.getType().getValue() == FieldType.FIELD_TYPE_TEXT.getValue() || linkData.getType().getValue() == FieldType.FIELD_TYPE_STRING.getValue()) {
                                obj = obj.toString().trim();
                            }
                            rowData.set(l, obj);
                            if (isAllNull && !obj.equals("")) {
                                isAllNull = false;
                            }
                        }
                        tempMap.put(linkData.getKey(), linkData);
                    }
                    ++k;
                    ++l;
                }
                if (isAllNull) continue;
                try {
                    regionDataSet.importDatas(rowData);
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

    private void collectError(ImportResultReportObject importResultReportObject, ImportResultRegionObject importResultRegionObject, ReportMatchResult reportMatchResult, Map<String, DataLinkDefine> tempMap, int addTempRows) {
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
                            DataLinkDefine linkData = tempMap.get(dataLinkKey);
                            if (null == linkData) continue;
                            int addRows = 0;
                            if (null != matchList && matchList.size() > 0) {
                                for (RegionMatchInfo regionMatchInfo : matchList) {
                                    int startRow = regionMatchInfo.getRegion().getRegionBottom();
                                    int beforeRow = linkData.getPosY();
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
                            importErrorDataOne.setExcelLocation(new Point(linkData.getPosX(), linkData.getPosY() + addRows + addTempRows));
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
            }
        }
    }

    private ReportMatchResult matchSheetToReport(FormDefine formData, Sheet reportSheet) {
        FormDefine formDefine = this.runtimeView.queryFormById(formData.getKey());
        Sheet2GridAdapter sheetGrid = ExcelImportUtil.getSheetCells(reportSheet, formDefine);
        List<DataLinkDefine> linkdatas = this.getAllLinkDatas(formData);
        ReportLinkDataCache linkDataCache = new ReportLinkDataCache();
        linkDataCache.init(linkdatas);
        Grid2Data sampleGridData = this.getGridData(formData.getKey());
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
        try {
            ReportMatchResult reportMatchResult = this.generateMatchResult(formData.getTitle(), completeMatchList, sampleGridData.getRowCount(), isAllMatch, sheetGrid, linkDataCache, sampleGridData);
            reportMatchResult.setTabNames(sheetGrid.getTabNames());
            return reportMatchResult;
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    private int getSheetMatchStartRow(Grid2Data sampleGridData, Sheet2GridAdapter sheetGrid, ReportLinkDataCache linkDataCache) {
        ReportFeature rf = new ReportFeature(sampleGridData, linkDataCache);
        boolean isMath = true;
        int startRow = -1;
        int skipColCount = 0;
        for (int row = 0; row < sheetGrid.getRowCount(); ++row) {
            boolean columnHidden;
            String gridDataShow;
            isMath = true;
            skipColCount = 0;
            int notEmptyColCount = 0;
            for (int col = 0; col < sheetGrid.getColCount(); ++col) {
                Object text = sheetGrid.getShowText(row, col);
                gridDataShow = rf.getCellText(1, col + 1);
                if (!(StringUtils.isEmpty((String)gridDataShow) || gridDataShow.equals("NULL") || gridDataShow.equals("null"))) {
                    ++notEmptyColCount;
                    if (this.valid(gridDataShow).equals(this.valid((String)text))) continue;
                    ++skipColCount;
                    if (rf.getFeatures().size() < sheetGrid.getColCount() - col) continue;
                    isMath = false;
                    break;
                }
                ++skipColCount;
            }
            if (skipColCount >= sheetGrid.getColCount() || !isMath || sheetGrid.getColCount() - skipColCount < notEmptyColCount) continue;
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
                Object text = sheetGrid.getShowText(row, col);
                gridDataShow = rf.getCellText(row + 1, 1);
                if (StringUtils.isEmpty((String)gridDataShow) || gridDataShow.equals("NULL") || gridDataShow.equals("null") || this.valid(gridDataShow).equals(this.valid((String)text)) || rf.getFeatures().size() < sheetGrid.getRowCount() - row) continue;
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

    private int getSheetMatchEndRow(FormDefine formData, Grid2Data sampleGridData, Sheet2GridAdapter sheetGrid, ReportLinkDataCache linkDataCache, ReportLinkDataCache linkDataCache2) {
        int tempRows;
        List<DataRegionDefine> regionList = this.getRegionDatasOrderly(formData);
        if (0 == regionList.size()) {
            return sheetGrid.getRowCount();
        }
        DataRegionDefine lastRegion = regionList.get(regionList.size() - 1);
        int gridDataRowCount = sampleGridData.getRowCount() - 1;
        Boolean isFloat = DataRegionKind.DATA_REGION_SIMPLE.getValue() != lastRegion.getRegionKind().getValue() && lastRegion.getRegionBottom() == gridDataRowCount;
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
                Object sheetCell = sheetGrid.getShowText(row, col);
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
                Object text = sheetGrid.getShowText(row, col);
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
                    Object text = sheetGrid.getShowText(row - 1, col);
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

    private int getSheetMatchEndCol(FormDefine formData, Grid2Data sampleGridData, Sheet2GridAdapter sheetGrid, ReportLinkDataCache linkDataCache, ReportLinkDataCache linkDataCache2) {
        int tempCols;
        List<DataRegionDefine> regionList = this.getRegionDatasOrderly(formData);
        if (0 == regionList.size()) {
            return sheetGrid.getColCount();
        }
        DataRegionDefine lastRegion = regionList.get(regionList.size() - 1);
        int gridDataColCount = sampleGridData.getColumnCount() - 1;
        Boolean isFloat = DataRegionKind.DATA_REGION_SIMPLE.getValue() != lastRegion.getRegionKind().getValue() && lastRegion.getRegionRight() == gridDataColCount;
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
                Object sheetCell = sheetGrid.getShowText(row, col);
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
                Object text = sheetGrid.getShowText(row, col);
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
                    Object text = sheetGrid.getShowText(row, col - 1);
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

    private List<DataRegionDefine> getRegionDatasOrderly(FormDefine formData) {
        List regionList = this.runtimeView.getAllRegionsInForm(formData.getKey());
        Collections.sort(regionList, new Comparator<DataRegionDefine>(){

            @Override
            public int compare(DataRegionDefine area1, DataRegionDefine area2) {
                if (area1.getRegionTop() == area2.getRegionTop()) {
                    return area1.getRegionLeft() - area2.getRegionLeft();
                }
                return area1.getRegionTop() - area2.getRegionTop();
            }
        });
        return regionList;
    }

    private List<RegionMatchInfo> getFloatAreaMatchInfo(FormDefine formData, Grid2Data sampleGridData, ReportLinkDataCache linkDataCache) {
        ArrayList<RegionMatchInfo> regionMatchInfoList = new ArrayList<RegionMatchInfo>();
        List<DataRegionDefine> regionList = this.getRegionDatasOrderly(formData);
        int preAreaEndRow = 0;
        for (int i = 1; i < regionList.size(); ++i) {
            DataRegionDefine region = regionList.get(i);
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
            if (DataRegionKind.DATA_REGION_COLUMN_LIST.getValue() == regionInfo.getRegion().getRegionKind().getValue()) {
                regionInfo.setMatchEndCol(sheetGrid.getMatchEndCol());
            } else {
                regionInfo.setMatchEndCol(sheetGrid.getMatchEndCol() - regionInfo.getEndDistance() - 1);
            }
            String featureStr = regionInfo.getFeatureText();
            if (DataRegionKind.DATA_REGION_COLUMN_LIST.getValue() == regionInfo.getRegion().getRegionKind().getValue()) {
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
            if (DataRegionKind.DATA_REGION_ROW_LIST.getValue() != regionInfo.getRegion().getRegionKind().getValue()) continue;
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

    private ReportMatchResult generateMatchResult(String reportCode, List<RegionMatchInfo> regionMatchInfoList, int gridRowCount, boolean isMatchAll, Sheet2GridAdapter sheetGrid, ReportLinkDataCache linkDataCache, Grid2Data grid2Data) throws Exception {
        int excelFixBeginRow;
        int mapFixBeginRow;
        ReportMatchResult result = new ReportMatchResult();
        result.setSheetBeginRow(sheetGrid.getMatchStartRow());
        ArrayList<String> lackZbAuthCells = new ArrayList<String>();
        RegionMatchInfo lastRegion = null;
        if (regionMatchInfoList != null) {
            for (RegionMatchInfo regionMatchInfo : regionMatchInfoList) {
                this.setFloatMatchResult(result, regionMatchInfo, lackZbAuthCells, sheetGrid, linkDataCache, grid2Data);
                DataRegionDefine region = regionMatchInfo.getRegion();
                if (region.getRegionKind().getValue() == DataRegionKind.DATA_REGION_COLUMN_LIST.getValue()) {
                    int mapFixBeginCol = region.getRegionLeft() - regionMatchInfo.getPreFixRectangle();
                    int excelFixBeginCol = regionMatchInfo.getMatchStartCol() - regionMatchInfo.getPreFixRectangle();
                    this.setFixMatchResult(result, 1, mapFixBeginCol, 0, excelFixBeginCol, sheetGrid.getRowCount() + 1, region.getRegionLeft(), lackZbAuthCells, sheetGrid, linkDataCache, grid2Data);
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
        result.setCompleteMatchList(regionMatchInfoList);
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
        return result;
    }

    private void setFloatMatchResult(ReportMatchResult result, RegionMatchInfo regionMatchInfo, List<String> needZbAuthCells, Sheet2GridAdapter sheetGrid, ReportLinkDataCache linkDataCache, Grid2Data grid2Data) throws Exception {
        DataRegionDefine region = regionMatchInfo.getRegion();
        int regionSpan = region.getRegionBottom() - region.getRegionTop() + 1;
        int regionSpanCol = region.getRegionRight() - region.getRegionLeft() + 1;
        if (region.getRegionKind().getValue() == DataRegionKind.DATA_REGION_COLUMN_LIST.getValue()) {
            for (int col = 0; col < regionSpanCol; ++col) {
                for (int row = 0; row < sheetGrid.getRowCount(); ++row) {
                    FieldDefine queryFieldDefine;
                    if (!linkDataCache.hasLinkData(row + 1, col + region.getRegionLeft())) continue;
                    DataLinkDefine linkdata = linkDataCache.getLinkData(row + 1, col + region.getRegionLeft());
                    boolean isFileField = false;
                    if (linkdata.getType() == DataLinkType.DATA_LINK_TYPE_FIELD && (queryFieldDefine = this.runtimeView.queryFieldDefine(linkdata.getLinkExpression())) != null && (queryFieldDefine.getType() == FieldType.FIELD_TYPE_FILE || queryFieldDefine.getType() == FieldType.FIELD_TYPE_PICTURE)) {
                        isFileField = true;
                    }
                    ArrayList<Object> valueList = new ArrayList<Object>();
                    ArrayList<String> formatList = new ArrayList<String>();
                    if (this.validateZBAtuth(linkdata)) {
                        for (int c = col + regionMatchInfo.getMatchStartCol(); c <= regionMatchInfo.getMatchEndCol(); c += regionSpanCol) {
                            int dataRow = row + sheetGrid.getMatchStartRow();
                            if (!isFileField) {
                                valueList.add(sheetGrid.getShowText(dataRow, c));
                                formatList.add(sheetGrid.getCellFormat(dataRow, c));
                                continue;
                            }
                            if (this.checkRegionSettingContainDefaultVal(region, linkdata)) {
                                valueList.add(sheetGrid.getShowText(dataRow, col));
                                formatList.add(sheetGrid.getCellFormat(dataRow, col));
                                continue;
                            }
                            valueList.add(IMPORT_INVALID_DATA);
                            formatList.add("");
                        }
                        result.setCellValue(linkdata.getKey().toString(), valueList);
                        result.setCellFormat(linkdata.getKey(), formatList);
                        continue;
                    }
                    needZbAuthCells.add(String.format("[%d,%d]", linkdata.getPosY(), linkdata.getPosX()));
                }
            }
        } else {
            for (int row = 0; row < regionSpan; ++row) {
                for (int col = 0; col < sheetGrid.getColCount(); ++col) {
                    FieldDefine queryFieldDefine;
                    if (!linkDataCache.hasLinkData(row + region.getRegionTop(), col + 1)) continue;
                    DataLinkDefine linkdata = linkDataCache.getLinkData(row + region.getRegionTop(), col + 1);
                    boolean isFileField = false;
                    if (linkdata.getType() == DataLinkType.DATA_LINK_TYPE_FIELD && (queryFieldDefine = this.runtimeView.queryFieldDefine(linkdata.getLinkExpression())) != null && (queryFieldDefine.getType() == FieldType.FIELD_TYPE_FILE || queryFieldDefine.getType() == FieldType.FIELD_TYPE_PICTURE)) {
                        isFileField = true;
                    }
                    ArrayList<Object> valueList = new ArrayList<Object>();
                    ArrayList<String> formatList = new ArrayList<String>();
                    if (this.validateZBAtuth(linkdata)) {
                        for (int r = row + regionMatchInfo.getMatchStart(); r <= regionMatchInfo.getMatchEnd(); r += regionSpan) {
                            if (!isFileField || this.checkRegionSettingContainDefaultVal(region, linkdata)) {
                                valueList.add(sheetGrid.getShowText(r, col));
                                formatList.add(sheetGrid.getCellFormat(r, col));
                                continue;
                            }
                            valueList.add(IMPORT_INVALID_DATA);
                            formatList.add("");
                        }
                        result.setCellValue(linkdata.getKey(), valueList);
                        result.setCellFormat(linkdata.getKey(), formatList);
                        continue;
                    }
                    needZbAuthCells.add(String.format("[%d,%d]", linkdata.getPosY(), linkdata.getPosX()));
                }
            }
        }
        int realCount = regionMatchInfo.getMatchEnd() - regionMatchInfo.getMatchStart() + 1;
        double flaotRowCount = Math.ceil((double)realCount / (double)regionSpan);
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
                        FieldDefine queryFieldDefine;
                        DataLinkDefine linkdata = linkDataCache.getLinkData(row, col);
                        boolean isFileField = false;
                        if (linkdata.getType() == DataLinkType.DATA_LINK_TYPE_FIELD && (queryFieldDefine = this.runtimeView.queryFieldDefine(linkdata.getLinkExpression())) != null && (queryFieldDefine.getType() == FieldType.FIELD_TYPE_FILE || queryFieldDefine.getType() == FieldType.FIELD_TYPE_PICTURE)) {
                            isFileField = true;
                        }
                        Object text = sheetGrid.getShowText(eRow, eCol);
                        ArrayList<Object> valueList = new ArrayList<Object>();
                        ArrayList<String> formatList = new ArrayList<String>();
                        if (this.validateZBAtuth(linkdata)) {
                            if (!isFileField) {
                                valueList.add(text);
                                formatList.add(sheetGrid.getCellFormat(eRow, eCol));
                            } else {
                                valueList.add(IMPORT_INVALID_DATA);
                                formatList.add("");
                            }
                            result.setCellValue(linkdata.getKey().toString(), valueList);
                            result.setCellFormat(linkdata.getKey(), formatList);
                        } else {
                            needZbAuthCells.add(String.format("[%d,%d]", linkdata.getPosY(), linkdata.getPosX()));
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

    private boolean validateZBAtuth(DataLinkDefine linkdata) {
        return true;
    }

    private List<DataLinkDefine> getAllLinkDatas(FormDefine formData) {
        ArrayList<DataLinkDefine> linkdatas = new ArrayList<DataLinkDefine>();
        List regions = this.runtimeView.getAllRegionsInForm(formData.getKey());
        for (DataRegionDefine region : regions) {
            linkdatas.addAll(this.runtimeView.getAllLinksInRegion(region.getKey()));
        }
        return linkdatas;
    }

    private Map<String, Object> getReport(FormDefine formDefine, String sheetName, UploadParam param, IDataAccessService dataAccessService, CommonMessage message) {
        HashMap<String, Object> res = new HashMap<String, Object>();
        try {
            if (null == formDefine) {
                logger.warn(this.notFindReporterror(sheetName));
                res.put(this.reportRes, false);
                res.put(this.reportMessage, this.notFindReporterror(sheetName));
                return res;
            }
            IAccessResult writeable = dataAccessService.writeable(param.getDimensionSet(), formDefine.getKey());
            if (writeable.haveAccess()) {
                FormDefine report = this.runtimeView.queryFormById(formDefine.getKey());
                res.put(this.reportRes, true);
                res.put(this.JtableDataStr, report);
                String formName = formDefine.getTitle();
                if (!StringUtils.isEmpty((String)formName)) {
                    if (message.getForms() == null) {
                        message.setForms(new ArrayList());
                    }
                    List forms = message.getForms();
                    message.setForms(forms);
                    if (message.getSuccessDW() == null) {
                        message.setSuccessDW(new ArrayList());
                    }
                    List successDW = message.getSuccessDW();
                    successDW.add(param.getDimensionSet().toDimensionValueSet().getValue(0).toString());
                    String title = report.getTitle();
                    for (int y = 0; y < this.chinaChars.length / 2; ++y) {
                        formName = formName.replaceAll(this.chinaChars[y], this.chinaChars[y + this.chinaChars.length / 2]);
                        title = title.replaceAll(this.chinaChars[y], this.chinaChars[y + this.chinaChars.length / 2]);
                    }
                    if (!title.trim().equals(formName.trim())) {
                        res.put(this.reportRes, false);
                        res.put(this.reportMessage, this.notFindReporterror(formDefine.getFormCode() + SEPARATOR_ONE + formName));
                    }
                }
            } else {
                res.put(this.reportRes, false);
                res.put(this.reportMessage, writeable.getMessage());
            }
            return res;
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a{}", (Object)e.getMessage(), (Object)e);
            res.put(this.reportRes, false);
            res.put(this.reportMessage, e.getMessage());
            return res;
        }
    }

    private String valid(String str) {
        if (null != str) {
            if (str.indexOf("\r\n") != -1) {
                str = str.replace("\r\n", "");
            }
            if (str.indexOf("\n") != -1) {
                str = str.replace("\n", "");
            }
            str = Html.cleanUrlXSS((String)str);
            Pattern scriptPattern = Pattern.compile("<span(.*?)>", 2);
            str = scriptPattern.matcher(str).replaceAll("");
            scriptPattern = Pattern.compile("</span>", 2);
            str = scriptPattern.matcher(str).replaceAll("");
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
            separator = this.getSysSeparator();
        }
        return separator;
    }

    private String getSysSeparator() {
        String separatorMessage = this.iNvwaSystemOptionService.get("nr-data-entry-export", "SEPARATOR_CODE");
        String separator = SEPARATOR_ONE;
        if (separatorMessage.equals("1")) {
            separator = SEPARATOR_TWO;
        } else if (separatorMessage.equals("2")) {
            separator = SEPARATOR_THREE;
        }
        return separator;
    }

    public String getFormSpliceName(FormDefine formDefine) {
        String sheetNameOp = this.iNvwaSystemOptionService.get("nr-data-entry-export", "SHEET_NAME");
        String[] sheetNameCompose = sheetNameOp.split("[\\[\\]\",\\s]");
        String sysSeparator = this.getSysSeparator();
        StringBuilder spliceName = new StringBuilder();
        for (String op : sheetNameCompose) {
            if ("0".equals(op)) {
                spliceName.append(formDefine.getTitle()).append(sysSeparator);
                continue;
            }
            if ("1".equals(op)) {
                spliceName.append(formDefine.getFormCode()).append(sysSeparator);
                continue;
            }
            if (!"2".equals(op) || !StringUtils.isNotEmpty((String)formDefine.getSerialNumber())) continue;
            spliceName.append(formDefine.getSerialNumber()).append(sysSeparator);
        }
        if (spliceName.length() == 0) {
            spliceName.append(formDefine.getFormCode()).append(sysSeparator).append(formDefine.getTitle()).append(sysSeparator);
        }
        spliceName.setLength(spliceName.length() - sysSeparator.length());
        return spliceName.toString();
    }

    public boolean checkRegionSettingContainDefaultVal(DataRegionDefine regionData, DataLinkDefine linkData) {
        RegionSettingDefine regionSetting = this.runtimeView.getRegionSetting(regionData.getKey());
        List regionDefalutSettings = regionSetting.getEntityDefaultValue();
        if (CollectionUtils.isEmpty(regionDefalutSettings)) {
            return false;
        }
        Optional<EntityDefaultValue> enumEntityDefault = regionDefalutSettings.stream().filter(e -> linkData.getLinkExpression().equals(e.getFieldKey())).findAny();
        return enumEntityDefault.isPresent();
    }

    public Grid2Data getGridData(String formKey) {
        BigDataDefine formDefine = this.runtimeView.getReportDataFromForm(formKey);
        if (null != formDefine) {
            if (formDefine.getData() != null) {
                Grid2Data bytesToGrid = Grid2Data.bytesToGrid((byte[])formDefine.getData());
                String frontScriptFromForm = this.runtimeView.getFrontScriptFromForm(formDefine.getKey());
                if (StringUtils.isNotEmpty((String)frontScriptFromForm)) {
                    bytesToGrid.setScript(frontScriptFromForm);
                }
                return bytesToGrid;
            }
            Grid2Data griddata = new Grid2Data();
            griddata.setRowCount(10);
            griddata.setColumnCount(10);
            return griddata;
        }
        return null;
    }

    private void beforeImport(File file, UploadParam param) throws Exception {
        String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
        if (file.getName().endsWith(".xls") || file.getName().endsWith(".xlsx") || file.getName().endsWith(".et")) {
            FormSchemeDefine formSchemeDefine = this.runtimeView.getFormScheme(param.getFormSchemeKey());
            if (formSchemeDefine == null) {
                throw new IllegalArgumentException("formSchemeDefine \u62a5\u8868\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a.");
            }
            EntityViewDefine iEntityDefine = this.entityViewRunTimeController.buildEntityView(formSchemeDefine.getDw());
            String dimensionName = this.entityMetaService.getDimensionName(iEntityDefine.getEntityId());
            if (param.getDimensionSet().getValue(dimensionName).equals("") || param.getDimensionSet().getValue(dimensionName) == null) {
                IEntityTable entityTable = null;
                IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
                iEntityQuery.setEntityView(iEntityDefine);
                iEntityQuery.setAuthorityOperations(AuthorityType.None);
                ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
                executorContext.setPeriodView(formSchemeDefine.getDateTime());
                entityTable = iEntityQuery.executeFullBuild((IContext)executorContext);
                List iEntityRows = entityTable.getAllRows();
                ArrayList<IEntityRow> entityList = new ArrayList<IEntityRow>();
                for (IEntityRow iEntityRow : iEntityRows) {
                    if (!fileName.equals(iEntityRow.getTitle()) && !fileName.equals(iEntityRow.getCode())) continue;
                    entityList.add(iEntityRow);
                }
                String sysSeparator = this.getSysSeparator();
                if (entityList.isEmpty() && fileName.contains(sysSeparator)) {
                    String[] fileNameArray;
                    for (String fileNameInfo : fileNameArray = fileName.split(sysSeparator)) {
                        for (IEntityRow iEntityRow : iEntityRows) {
                            if (!fileNameInfo.equals(iEntityRow.getTitle()) && !fileNameInfo.equals(iEntityRow.getCode())) continue;
                            entityList.add(iEntityRow);
                        }
                    }
                    if (entityList.size() > 1) {
                        entityList.clear();
                        for (String fileNameInfo : fileNameArray) {
                            for (IEntityRow iEntityRow : iEntityRows) {
                                if (!fileNameInfo.equals(iEntityRow.getCode())) continue;
                                entityList.add(iEntityRow);
                            }
                        }
                    }
                }
                if (entityList.size() == 1) {
                    param.getDimensionSet().toDimensionValueSet().setValue(dimensionName, (Object)((IEntityRow)entityList.get(0)).getEntityKeyData());
                } else {
                    String errorCode = ErrorCode.FILEERROR.getErrorCodeMsg();
                    if (StringUtils.isNotEmpty((String)this.i18nHelper.getMessage("FILEERROR"))) {
                        errorCode = this.i18nHelper.getMessage("FILEERROR");
                    }
                    String message = "\u6587\u4ef6\u540d\u79f0\u672a\u5339\u914d\u5230\u5355\u4f4d\u6216\u5355\u4f4d\u4e0d\u552f\u4e00";
                    if (StringUtils.isNotEmpty((String)this.i18nHelper.getMessage("FILENAMEUNITCODEERROR"))) {
                        message = this.i18nHelper.getMessage("FILENAMEUNITCODEERROR");
                    }
                    throw new IllegalArgumentException(message + errorCode);
                }
            }
        }
    }

    private String notFindReporterror(String name) {
        return "\u6ca1\u6709\u5728\u5bf9\u5e94\u7684\u62a5\u8868\u65b9\u6848\u4e2d\u627e\u5230\u540d\u79f0\u4e3a\u3010" + name + "\u3011\u7684\u62a5\u8868";
    }

    static {
        FILETYPEMAP.put("xls", "upload_type_excel");
        FILETYPEMAP.put("zip", "upload_type_zip");
        FILETYPEMAP.put("xlsx", "upload_type_excel");
        FILETYPEMAP.put("et", "upload_type_excel");
    }

    private static class CaseInsensitiveMap<V>
    extends HashMap<String, V> {
        private static final long serialVersionUID = 1L;

        private CaseInsensitiveMap() {
        }

        @Override
        public V get(Object key) {
            return super.get(StringUtils.upperCase((String)((String)key)));
        }

        @Override
        public V put(String key, V value) {
            return super.put(StringUtils.upperCase((String)key), value);
        }

        @Override
        public boolean containsKey(Object key) {
            return super.containsKey(StringUtils.upperCase((String)((String)key)));
        }

        @Override
        public V remove(Object key) {
            return super.remove(StringUtils.upperCase((String)((String)key)));
        }
    }
}

