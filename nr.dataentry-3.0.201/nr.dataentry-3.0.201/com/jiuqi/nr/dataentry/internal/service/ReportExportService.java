/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.JsonMappingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.google.gson.Gson
 *  com.google.gson.reflect.TypeToken
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.syntax.ast.adjust.InsertRowsAdjustor
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaShowType
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.dataengine.intf.IExpressionEvaluator
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.np.definition.internal.parser.NumberFormatParser
 *  com.jiuqi.np.grid.Font
 *  com.jiuqi.np.office.excel2.WorksheetWriter2
 *  com.jiuqi.np.office.excel2.filter.FilterColCondition
 *  com.jiuqi.np.office.excel2.filter.FilterOperator
 *  com.jiuqi.np.office.excel2.filter.FilterRegionCondition
 *  com.jiuqi.np.office.excel2.label.ExcelLabel
 *  com.jiuqi.np.office.excel2.link.CellLink
 *  com.jiuqi.np.util.ColorUtil
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.params.PagerInfo
 *  com.jiuqi.nr.data.access.service.IDataAccessService
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IPrintRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.PrintTemplateDefine
 *  com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.facade.print.PrintSchemeAttributeDefine
 *  com.jiuqi.nr.definition.facade.print.PrintTemplateAttributeDefine
 *  com.jiuqi.nr.definition.facade.print.WordLabelDefine
 *  com.jiuqi.nr.definition.facade.print.common.other.PrintUtil
 *  com.jiuqi.nr.definition.facade.print.common.param.IPrintParamBase
 *  com.jiuqi.nr.definition.facade.print.common.parse.ParseContext
 *  com.jiuqi.nr.definition.facade.print.common.parse.WordLabelParseExecuter
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.service.RunTimeExtentStyleService
 *  com.jiuqi.nr.definition.print.service.IPrintLabelService
 *  com.jiuqi.nr.definition.util.ExtentStyle
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.file.FileStatus
 *  com.jiuqi.nr.file.impl.FileInfoService
 *  com.jiuqi.nr.io.service.impl.DataAuthReadable
 *  com.jiuqi.nr.jtable.common.LinkType
 *  com.jiuqi.nr.jtable.dataset.AbstractRegionRelationEvn
 *  com.jiuqi.nr.jtable.dataset.IRegionExportDataSet
 *  com.jiuqi.nr.jtable.dataset.IReportExportDataSet
 *  com.jiuqi.nr.jtable.dataset.impl.FloatRegionRelationEvn
 *  com.jiuqi.nr.jtable.dataset.impl.RegionExportDataSetImpl
 *  com.jiuqi.nr.jtable.dataset.impl.RegionNumberManager
 *  com.jiuqi.nr.jtable.dataset.impl.SimpleRegionRelationEvn
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.EnumLinkData
 *  com.jiuqi.nr.jtable.params.base.FieldData
 *  com.jiuqi.nr.jtable.params.base.FormData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.base.LinkData
 *  com.jiuqi.nr.jtable.params.base.MeasureViewData
 *  com.jiuqi.nr.jtable.params.base.RegionData
 *  com.jiuqi.nr.jtable.params.base.RegionTab
 *  com.jiuqi.nr.jtable.params.input.CellQueryInfo
 *  com.jiuqi.nr.jtable.params.input.FilterCondition
 *  com.jiuqi.nr.jtable.params.input.FormulaQueryInfo
 *  com.jiuqi.nr.jtable.params.input.GradeCellInfo
 *  com.jiuqi.nr.jtable.params.input.MeasureQueryInfo
 *  com.jiuqi.nr.jtable.params.input.RegionGradeInfo
 *  com.jiuqi.nr.jtable.params.input.RegionQueryInfo
 *  com.jiuqi.nr.jtable.params.output.FormulaDataLinkNodeInfo
 *  com.jiuqi.nr.jtable.params.output.MeasureData
 *  com.jiuqi.nr.jtable.params.output.RegionDataSet
 *  com.jiuqi.nr.jtable.params.output.SecretLevelInfo
 *  com.jiuqi.nr.jtable.params.output.SecretLevelItem
 *  com.jiuqi.nr.jtable.quickGridUtil.GridDataTransform
 *  com.jiuqi.nr.jtable.service.IJtableDataQueryService
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.service.IJtableResourceService
 *  com.jiuqi.nr.jtable.service.ISecretLevelService
 *  com.jiuqi.nr.jtable.service.impl.SnapshotDataQueryServiceImpl
 *  com.jiuqi.nr.jtable.util.DataFormaterCache
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.jtable.util.RegionGradeDataLoader
 *  com.jiuqi.nvwa.grid2.Grid2CellField
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.Grid2FieldList
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.GridCellStyleData
 *  com.jiuqi.nvwa.grid2.GridEnums
 *  com.jiuqi.nvwa.grid2.GridEnums$BackgroundStyle
 *  com.jiuqi.nvwa.grid2.GridEnums$DataType
 *  com.jiuqi.nvwa.grid2.GridEnums$GridBorderStyle
 *  com.jiuqi.nvwa.grid2.GridEnums$TextAlignment
 *  com.jiuqi.nvwa.grid2.graphics.Point
 *  com.jiuqi.nvwa.grid2.json.Grid2DataConst$Cell_MODE
 *  com.jiuqi.nvwa.quickreport.api.query.Wrapper
 *  com.jiuqi.nvwa.quickreport.api.query.option.Option
 *  com.jiuqi.nvwa.quickreport.dto.ReportData
 *  com.jiuqi.nvwa.quickreport.web.query.GridController
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.syntax.ast.adjust.InsertRowsAdjustor;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.intf.IExpressionEvaluator;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.np.definition.internal.parser.NumberFormatParser;
import com.jiuqi.np.grid.Font;
import com.jiuqi.np.office.excel2.WorksheetWriter2;
import com.jiuqi.np.office.excel2.filter.FilterColCondition;
import com.jiuqi.np.office.excel2.filter.FilterOperator;
import com.jiuqi.np.office.excel2.filter.FilterRegionCondition;
import com.jiuqi.np.office.excel2.label.ExcelLabel;
import com.jiuqi.np.office.excel2.link.CellLink;
import com.jiuqi.np.util.ColorUtil;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.dataentry.bean.BatchExportParam;
import com.jiuqi.nr.dataentry.bean.ExportParam;
import com.jiuqi.nr.dataentry.bean.IExportFacade;
import com.jiuqi.nr.dataentry.bean.RegionFilterListInfo;
import com.jiuqi.nr.dataentry.export.IReportExport;
import com.jiuqi.nr.dataentry.internal.service.EnueSheetUtil;
import com.jiuqi.nr.dataentry.internal.service.IExportExcelPrintSettings;
import com.jiuqi.nr.dataentry.print.common.interactor.PrintIPaginateInteractor;
import com.jiuqi.nr.dataentry.print.common.param.PdfExportParam;
import com.jiuqi.nr.dataentry.print.common.param.PrintParam;
import com.jiuqi.nr.dataentry.service.IDataEntryParamService;
import com.jiuqi.nr.dataentry.util.DataEntryUtil;
import com.jiuqi.nr.dataentry.util.Grid2DataSetValueUtil;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IPrintRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.PrintTemplateDefine;
import com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.print.PrintSchemeAttributeDefine;
import com.jiuqi.nr.definition.facade.print.PrintTemplateAttributeDefine;
import com.jiuqi.nr.definition.facade.print.WordLabelDefine;
import com.jiuqi.nr.definition.facade.print.common.other.PrintUtil;
import com.jiuqi.nr.definition.facade.print.common.param.IPrintParamBase;
import com.jiuqi.nr.definition.facade.print.common.parse.ParseContext;
import com.jiuqi.nr.definition.facade.print.common.parse.WordLabelParseExecuter;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.service.RunTimeExtentStyleService;
import com.jiuqi.nr.definition.print.service.IPrintLabelService;
import com.jiuqi.nr.definition.util.ExtentStyle;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.file.FileStatus;
import com.jiuqi.nr.file.impl.FileInfoService;
import com.jiuqi.nr.io.service.impl.DataAuthReadable;
import com.jiuqi.nr.jtable.common.LinkType;
import com.jiuqi.nr.jtable.dataset.AbstractRegionRelationEvn;
import com.jiuqi.nr.jtable.dataset.IRegionExportDataSet;
import com.jiuqi.nr.jtable.dataset.IReportExportDataSet;
import com.jiuqi.nr.jtable.dataset.impl.FloatRegionRelationEvn;
import com.jiuqi.nr.jtable.dataset.impl.RegionExportDataSetImpl;
import com.jiuqi.nr.jtable.dataset.impl.RegionNumberManager;
import com.jiuqi.nr.jtable.dataset.impl.SimpleRegionRelationEvn;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.EnumLinkData;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.base.MeasureViewData;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.base.RegionTab;
import com.jiuqi.nr.jtable.params.input.CellQueryInfo;
import com.jiuqi.nr.jtable.params.input.FilterCondition;
import com.jiuqi.nr.jtable.params.input.FormulaQueryInfo;
import com.jiuqi.nr.jtable.params.input.GradeCellInfo;
import com.jiuqi.nr.jtable.params.input.MeasureQueryInfo;
import com.jiuqi.nr.jtable.params.input.RegionGradeInfo;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import com.jiuqi.nr.jtable.params.output.FormulaDataLinkNodeInfo;
import com.jiuqi.nr.jtable.params.output.MeasureData;
import com.jiuqi.nr.jtable.params.output.RegionDataSet;
import com.jiuqi.nr.jtable.params.output.SecretLevelInfo;
import com.jiuqi.nr.jtable.params.output.SecretLevelItem;
import com.jiuqi.nr.jtable.quickGridUtil.GridDataTransform;
import com.jiuqi.nr.jtable.service.IJtableDataQueryService;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.service.IJtableResourceService;
import com.jiuqi.nr.jtable.service.ISecretLevelService;
import com.jiuqi.nr.jtable.service.impl.SnapshotDataQueryServiceImpl;
import com.jiuqi.nr.jtable.util.DataFormaterCache;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.jtable.util.RegionGradeDataLoader;
import com.jiuqi.nvwa.grid2.Grid2CellField;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.Grid2FieldList;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.GridCellStyleData;
import com.jiuqi.nvwa.grid2.GridEnums;
import com.jiuqi.nvwa.grid2.graphics.Point;
import com.jiuqi.nvwa.grid2.json.Grid2DataConst;
import com.jiuqi.nvwa.quickreport.api.query.Wrapper;
import com.jiuqi.nvwa.quickreport.api.query.option.Option;
import com.jiuqi.nvwa.quickreport.dto.ReportData;
import com.jiuqi.nvwa.quickreport.web.query.GridController;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class ReportExportService
implements IReportExport {
    private static final Logger logger = LoggerFactory.getLogger(ReportExportService.class);
    @Resource
    private IRunTimeViewController controller;
    @Resource
    private IDataEntryParamService dataEntryParamService;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IJtableResourceService jtableResourceService;
    @Autowired
    private IPrintRunTimeController printRunTimeController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IPrintLabelService iPrintLabelService;
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private FileService fileService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    private INvwaSystemOptionService iNvwaSystemOptionService;
    @Autowired
    private GridController gridController;
    @Autowired
    private ITaskOptionController taskOptionController;
    @Autowired
    private IJtableEntityService jtableEntityService;
    @Resource
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private SnapshotDataQueryServiceImpl snapshotDataQueryService;
    private ThreadLocal<Integer> DataSnapshotRows = ThreadLocal.withInitial(() -> 0);
    @Autowired
    private IJtableDataQueryService jtableDataQueryService;
    @Autowired
    private DataAuthReadable dataAuthReadable;
    @Autowired(required=false)
    private IExportExcelPrintSettings exportExcelPrintSettings;

    @Override
    public boolean export(IExportFacade info, SXSSFWorkbook workbook, Map<String, Map<String, Object>> regionDatas, List<String> fileGroupKeys, Map<String, List<String>> menuMap) {
        PagerInfo pagerInfo = new PagerInfo();
        JtableContext jtableContext = info.getContext();
        Boolean onlyStyle = null;
        if (!info.isOnlyStyle()) {
            DimensionValueSet dimensionValueSet;
            DimensionCombinationBuilder builder;
            onlyStyle = info.isOnlyStyle();
            IDataAccessService dataAccessService = this.dataAuthReadable.readable(jtableContext.getTaskKey(), jtableContext.getFormSchemeKey());
            List hasAuthForms = this.dataAuthReadable.hasAuthForms(dataAccessService, (builder = new DimensionCombinationBuilder(dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((JtableContext)jtableContext))).getCombination(), Arrays.asList(jtableContext.getFormKey()));
            if (hasAuthForms == null || hasAuthForms.isEmpty()) {
                if (info instanceof BatchExportParam) {
                    ((BatchExportParam)info).setOnlyStyle(true);
                } else if (info instanceof ExportParam) {
                    ((ExportParam)info).setOnlyStyle(true);
                }
            }
        }
        int count = 0;
        String sheetCount = "(" + count + ")";
        while (pagerInfo.getTotal() != Integer.MAX_VALUE) {
            SXSSFSheet sheet;
            sheetCount = pagerInfo.getLimit() == 0 || count == 0 ? "" : "(" + count + ")";
            if (count > 10 || null != (sheet = workbook.getSheet(info.getSheetName() + sheetCount))) break;
            sheet = workbook.createSheet(info.getSheetName() + sheetCount);
            sheet.setRowSumsBelow(false);
            Grid2Data grid2Data = null;
            Grid2Data grid2Dta = null;
            if (null != regionDatas && pagerInfo.getTotal() == 0) {
                Map<String, Object> map = regionDatas.get(jtableContext.getFormKey() + "grid2DataCache");
                if (map != null) {
                    Object object = map.get(jtableContext.getFormKey());
                    if (null != object) {
                        grid2Data = Grid2Data.bytesToGrid((byte[])((byte[])object));
                    } else {
                        grid2Data = this.jtableParamService.getGridData(jtableContext.getFormKey());
                        HashMap<String, byte[]> value = new HashMap<String, byte[]>();
                        value.put(jtableContext.getFormKey(), Grid2Data.gridToBytes((Grid2Data)grid2Data));
                        regionDatas.put(jtableContext.getFormKey() + "grid2DataCache", value);
                    }
                } else {
                    grid2Data = this.jtableParamService.getGridData(jtableContext.getFormKey());
                    HashMap<String, byte[]> value = new HashMap<String, byte[]>();
                    value.put(jtableContext.getFormKey(), Grid2Data.gridToBytes((Grid2Data)grid2Data));
                    regionDatas.put(jtableContext.getFormKey() + "grid2DataCache", value);
                }
            } else {
                if (null != regionDatas) {
                    regionDatas.remove(jtableContext.getFormKey() + "grid2DataCache");
                }
                grid2Data = this.jtableParamService.getGridData(jtableContext.getFormKey());
            }
            ArrayList<String> startAndEndList = new ArrayList<String>();
            HashMap<String, String> formulaMap = new HashMap<String, String>();
            ArrayList<FilterRegionCondition> filters = new ArrayList<FilterRegionCondition>();
            ArrayList<CellLink> links = new ArrayList<CellLink>();
            FormDefine formDefine = this.controller.queryFormById(jtableContext.getFormKey());
            if (FormType.FORM_TYPE_INSERTANALYSIS.equals((Object)formDefine.getFormType())) {
                ReportData rd = new ReportData();
                rd.setGuid(formDefine.getExtensionProp("analysisGuid").toString());
                Option op = new Option();
                EntityViewData dwEntityInfo = this.jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
                op.addParamValue("MD_ORG", (Object)((DimensionValue)jtableContext.getDimensionSet().get(dwEntityInfo.getDimensionName())).getValue());
                EntityViewData periodEntityInfo = this.jtableParamService.getDataTimeEntity(jtableContext.getFormSchemeKey());
                op.addParamValue("MD_PERIOD", (Object)((DimensionValue)jtableContext.getDimensionSet().get(periodEntityInfo.getDimensionName())).getValue());
                for (Map.Entry entry : jtableContext.getDimensionSet().entrySet()) {
                    op.addParamValue("P_" + (String)entry.getKey(), (Object)((DimensionValue)entry.getValue()).getValue());
                }
                Wrapper wp = new Wrapper();
                wp.setOption(op);
                wp.setReportData(rd);
                try {
                    GridData gridData = this.gridController.getPrimarySheetGridData(wp);
                    Grid2Data analysisGrid2Data = new Grid2Data();
                    grid2Data = GridDataTransform.gridDataToGrid2Data((GridData)gridData, (Grid2Data)analysisGrid2Data);
                }
                catch (Exception e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
                grid2Dta = grid2Data;
            } else {
                grid2Dta = this.generateGrid2Dta(info, grid2Data, sheet, startAndEndList, formulaMap, filters, links, regionDatas, pagerInfo, fileGroupKeys);
                if (grid2Dta == null) {
                    if (onlyStyle != null) {
                        if (info instanceof BatchExportParam) {
                            ((BatchExportParam)info).setOnlyStyle(onlyStyle);
                        } else if (info instanceof ExportParam) {
                            ((ExportParam)info).setOnlyStyle(onlyStyle);
                        }
                    }
                    return false;
                }
            }
            WorksheetWriter2 worksheetWriter2 = new WorksheetWriter2(grid2Dta, (Sheet)sheet, workbook);
            int labelRowCont = 0;
            List<ExcelLabel> labels = this.handlerLabek(info, grid2Data);
            if (labels != null && labels.size() > 0) {
                for (ExcelLabel excelLabel : labels) {
                    if (!excelLabel.isUpper() || excelLabel.getRowIndex() <= labelRowCont) continue;
                    labelRowCont = excelLabel.getRowIndex();
                }
            }
            if (grid2Data.getHeaderColumnCount() > 1 || grid2Data.getHeaderRowCount() > 1) {
                int colIndex = 0;
                int rowIndex = 0;
                if (grid2Data.getHeaderColumnCount() > 1) {
                    colIndex = grid2Data.getHeaderColumnCount() - 1;
                }
                if (grid2Data.getHeaderRowCount() > 1) {
                    rowIndex = grid2Data.getHeaderRowCount() - 1;
                }
                int rowNum = rowIndex + labelRowCont;
                sheet.createFreezePane(colIndex, rowNum, colIndex, rowNum);
            }
            worksheetWriter2.setFormulaMap(formulaMap);
            worksheetWriter2.setLabels(labels);
            worksheetWriter2.setBackground(info.isBackground());
            worksheetWriter2.setFilters(filters);
            worksheetWriter2.setLinks(links);
            worksheetWriter2.writeSheet(startAndEndList);
            ++count;
            String dropDown = this.iNvwaSystemOptionService.get("nr-data-entry-export", "EXPORT_EXCEL_DROPDOWN");
            if (dropDown.equals("1")) {
                String dropDownNum = this.iNvwaSystemOptionService.get("nr-data-entry-export", "EXPORT_EXCEL_DROPDOWN_NUM");
                if (dropDownNum == null || dropDownNum.equals("")) {
                    dropDownNum = "1000";
                }
                EnueSheetUtil enueSheetUtil = new EnueSheetUtil(this.controller, this.entityViewRunTimeController, this.dataDefinitionRuntimeController, this.jtableEntityService, this.dataAccessProvider, this.entityMetaService, this.jtableResourceService, menuMap, labelRowCont, this.DataSnapshotRows.get(), Integer.parseInt(dropDownNum));
                enueSheetUtil.setEnueSheet(info, workbook, jtableContext);
            }
            if (this.exportExcelPrintSettings == null) continue;
            this.exportExcelPrintSettings.exportExcelPrintSettings(sheet, info);
        }
        if (onlyStyle != null) {
            if (info instanceof BatchExportParam) {
                ((BatchExportParam)info).setOnlyStyle(onlyStyle);
            } else if (info instanceof ExportParam) {
                ((ExportParam)info).setOnlyStyle(onlyStyle);
            }
        }
        return true;
    }

    private List<ExcelLabel> handlerLabek(IExportFacade info, Grid2Data grid2Data) {
        if (!info.isLabel()) {
            return null;
        }
        ArrayList<ExcelLabel> returnList = new ArrayList<ExcelLabel>();
        HashSet<String> excelLabelSet = new HashSet<String>();
        try {
            List wordLabels;
            PrintParam printParam = new PrintParam();
            printParam.setContext(new JtableContext(info.getContext()));
            PrintIPaginateInteractor iteractor = new PrintIPaginateInteractor(printParam);
            String printSchemeKey = info.getPrintSchemeKey();
            PrintTemplateAttributeDefine printTemplateAttribute = null;
            PrintSchemeAttributeDefine printSchemeAttributeDefine = null;
            if (null == printSchemeKey || "".equals(printSchemeKey)) {
                try {
                    List printSchemes = this.printRunTimeController.getAllPrintSchemeByFormScheme(info.getContext().getFormSchemeKey());
                    PrintTemplateSchemeDefine printTemplateSchemeDefine = this.printRunTimeController.queryPrintTemplateSchemeDefine(((PrintTemplateSchemeDefine)printSchemes.get(0)).getKey());
                    printSchemeAttributeDefine = this.printRunTimeController.getPrintSchemeAttribute(printTemplateSchemeDefine);
                    if (printSchemeAttributeDefine.getWordLabels().size() == 0) {
                        PrintTemplateDefine printTemPlate = this.printRunTimeController.queryPrintTemplateDefineBySchemeAndForm(((PrintTemplateSchemeDefine)printSchemes.get(0)).getKey(), printParam.getFormKey());
                        printTemplateAttribute = this.printRunTimeController.getPrintTemplateAttribute(printTemPlate);
                    }
                }
                catch (Exception e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
            if ((wordLabels = this.iPrintLabelService.getLabelsWithLocation(printSchemeKey, info.getContext().getFormKey())) == null || wordLabels.size() < 1 && printSchemeAttributeDefine != null) {
                wordLabels = printSchemeAttributeDefine.getWordLabels().size() == 0 ? printTemplateAttribute.getWordLabels() : printSchemeAttributeDefine.getWordLabels();
            }
            boolean noMoneyInfo = true;
            for (WordLabelDefine wordLabelDefine : wordLabels) {
                if (!wordLabelDefine.getText().contains("RPTMONEYUNIT")) continue;
                noMoneyInfo = false;
            }
            if (noMoneyInfo) {
                printParam.getContext().setMeasureMap(null);
            }
            ParseContext parseContext = PrintUtil.createLabelParseContext((String)info.getContext().getFormKey(), (int)0, (int)0, null, (IPrintParamBase)printParam, (ExecutorContext)iteractor.getExecutorContext(), (IExpressionEvaluator)iteractor.getExpressionEvaluator());
            WordLabelParseExecuter instance = WordLabelParseExecuter.getInstance();
            int rowIndexs = 0;
            int maxIndex = 0;
            HashMap<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
            for (WordLabelDefine printWordLabel : wordLabels) {
                int colIndex = 0;
                int element = printWordLabel.getElement();
                int verticalPos = printWordLabel.getVerticalPos();
                int horizontalPos = printWordLabel.getHorizontalPos();
                if (verticalPos == 0 && element == 0 && StringUtils.isNotEmpty((String)printWordLabel.getText())) {
                    ++rowIndexs;
                }
                if (verticalPos == 0 && element == 1 && horizontalPos == 0) {
                    colIndex = 1;
                } else if (verticalPos == 0 && element == 1 && horizontalPos == 1) {
                    colIndex = grid2Data.getColumnCount() / 2;
                } else if (verticalPos == 0 && element == 1 && horizontalPos == 2) {
                    colIndex = grid2Data.getColumnCount() - 1;
                }
                if (colIndex <= 0) continue;
                if (indexMap.containsKey(colIndex)) {
                    indexMap.put(colIndex, (Integer)indexMap.get(colIndex) + 1);
                    continue;
                }
                indexMap.put(colIndex, 1);
            }
            Iterator<Object> iterator = indexMap.keySet().iterator();
            while (iterator.hasNext()) {
                int index = (Integer)iterator.next();
                maxIndex = (Integer)indexMap.get(index) > maxIndex ? (Integer)indexMap.get(index) : maxIndex;
            }
            boolean formHasMeasure = true;
            FormDefine formDefine = this.controller.queryFormById(info.getContext().getFormKey());
            if (formDefine.getMeasureUnit() == null || formDefine.getMeasureUnit().indexOf("NotDimession") >= 0) {
                formHasMeasure = false;
            }
            for (WordLabelDefine printWordLable : wordLabels) {
                if (!formHasMeasure && printWordLable.getText().contains("RPTMONEYUNIT")) continue;
                String text = printWordLable.getText();
                String processedContent = instance.execute(parseContext, text, iteractor.getPatternAndValue());
                boolean upper = true;
                int rowIndex = 0;
                int colIndex = 0;
                int element = printWordLable.getElement();
                int verticalPos = printWordLable.getVerticalPos();
                int horizontalPos = printWordLable.getHorizontalPos();
                if (verticalPos == 0 && element == 0) {
                    rowIndex = 1;
                } else if (verticalPos == 0 && element == 1) {
                    rowIndex = maxIndex + rowIndexs;
                } else if (verticalPos == 1 && element == 0) {
                    rowIndex = grid2Data.getRowCount() + 3 + rowIndexs;
                    upper = false;
                } else if (verticalPos == 1 && element == 1) {
                    rowIndex = grid2Data.getRowCount() + 1 + rowIndexs;
                    upper = false;
                }
                if (horizontalPos == 0) {
                    colIndex = 1;
                } else if (horizontalPos == 1) {
                    colIndex = grid2Data.getColumnCount() / 2;
                } else if (horizontalPos == 2) {
                    colIndex = grid2Data.getColumnCount() - 1;
                }
                Font font = printWordLable.getFont();
                ExcelLabel excelLabel = new ExcelLabel(processedContent, rowIndex, colIndex);
                excelLabel.setBold(font.getBold());
                excelLabel.setFontColor(font.getColor() + "");
                excelLabel.setFontName(font.getName());
                excelLabel.setFontSize(Math.abs(font.getSize()) + "");
                excelLabel.setItalic(font.getItalic());
                excelLabel.setStrikeout(font.getStrikeout());
                excelLabel.setUnderline(font.getUnderline());
                excelLabel.setUpper(upper);
                String key = excelLabel.getRowIndex() + "_" + excelLabel.getColIndex();
                while (excelLabelSet.contains(key)) {
                    if (excelLabel.getRowIndex() <= maxIndex + rowIndexs && excelLabel.getRowIndex() > 1) {
                        excelLabel.setRowIndex(excelLabel.getRowIndex() + 1);
                        if (horizontalPos == 0) {
                            ++rowIndexs;
                        }
                        if (horizontalPos == 2) {
                            for (ExcelLabel excelLabel2 : returnList) {
                                if (excelLabel2.getRowIndex() != rowIndex || excelLabel2.getColIndex() == excelLabel.getColIndex()) continue;
                                excelLabel2.setRowIndex(excelLabel2.getRowIndex() + 1);
                            }
                        }
                    } else if (excelLabel.getRowIndex() == 1) {
                        excelLabel.setColIndex(excelLabel.getColIndex() + 1);
                    } else {
                        excelLabel.setRowIndex(excelLabel.getRowIndex() + 1);
                    }
                    key = excelLabel.getRowIndex() + "_" + excelLabel.getColIndex();
                }
                excelLabelSet.add(key);
                returnList.add(excelLabel);
            }
            return returnList;
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public Grid2Data generateGrid2Dta(IExportFacade sheetInfo, Grid2Data grid2Data) {
        return this.generateGrid2Dta(sheetInfo, grid2Data, null, null, null, null, null, null, null, new ArrayList<String>());
    }

    public Grid2Data generateGrid2Dta(IExportFacade info, Grid2Data grid2Data, SXSSFSheet sheet, List<String> startAndEndList, Map<String, String> formulaMap, List<FilterRegionCondition> filters, List<CellLink> links, Map<String, Map<String, Object>> regionDatas, PagerInfo pagerInfo, List<String> fileGroupKeys) {
        JtableContext jtableContext = info.getContext();
        Boolean onlyStyle = null;
        if (!info.isOnlyStyle() && info instanceof PdfExportParam) {
            DimensionValueSet dimensionValueSet;
            DimensionCombinationBuilder builder;
            onlyStyle = info.isOnlyStyle();
            IDataAccessService dataAccessService = this.dataAuthReadable.readable(jtableContext.getTaskKey(), jtableContext.getFormSchemeKey());
            List hasAuthForms = this.dataAuthReadable.hasAuthForms(dataAccessService, (builder = new DimensionCombinationBuilder(dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((JtableContext)jtableContext))).getCombination(), Arrays.asList(jtableContext.getFormKey()));
            if ((hasAuthForms == null || hasAuthForms.isEmpty()) && info instanceof PdfExportParam) {
                ((PdfExportParam)info).setOnlyStyle(true);
            }
        }
        try {
            IReportExportDataSet reportExportData = this.jtableResourceService.getReportExportData(jtableContext);
            List calcDataLinks = this.jtableParamService.getCalcDataLinks(jtableContext);
            List extractDataLinks = this.jtableParamService.getExtractDataLinkList(jtableContext);
            Set<String> collectLinks = calcDataLinks.stream().map(one -> one).collect(Collectors.toSet());
            Set<String> extractDataLinkSet = extractDataLinks.stream().map(one -> one).collect(Collectors.toSet());
            List dataRegions = null;
            if (null != regionDatas) {
                Map<String, Object> map = regionDatas.get(jtableContext.getFormKey() + "region");
                if (map != null) {
                    Object object = map.get(jtableContext.getFormKey());
                    if (null != object) {
                        dataRegions = (List)object;
                    } else {
                        dataRegions = reportExportData.getDataRegionList();
                        HashMap<String, List> value = new HashMap<String, List>();
                        value.put(jtableContext.getFormKey(), dataRegions);
                        regionDatas.put(jtableContext.getFormKey() + "region", value);
                    }
                } else {
                    dataRegions = reportExportData.getDataRegionList();
                    HashMap<String, List> value = new HashMap<String, List>();
                    value.put(jtableContext.getFormKey(), dataRegions);
                    regionDatas.put(jtableContext.getFormKey() + "region", value);
                }
            } else {
                dataRegions = reportExportData.getDataRegionList();
            }
            if (CollectionUtils.isEmpty(dataRegions) && !info.isExportEmptyTable()) {
                if (onlyStyle != null && info instanceof PdfExportParam) {
                    ((PdfExportParam)info).setOnlyStyle(onlyStyle);
                }
                return null;
            }
            dataRegions = this.orderRegions(dataRegions);
            int tempLine = 0;
            HashMap<String, Integer> addRows = new HashMap<String, Integer>();
            HashMap<String, CellQueryInfo> sortMap = new HashMap<String, CellQueryInfo>();
            boolean emptyTable = true;
            try {
                this.setFloatSort(info, sortMap);
                for (RegionData region : dataRegions) {
                    int eline;
                    IRegionExportDataSet dataSet;
                    DimensionValue dataSnapshotID;
                    if (region.getType() == 0) {
                        boolean singleEmpty;
                        dataSnapshotID = null;
                        if (info.getContext().getDimensionSet().containsKey("DATASNAPSHOTID")) {
                            dataSnapshotID = (DimensionValue)info.getContext().getDimensionSet().get("DATASNAPSHOTID");
                        }
                        dataSet = reportExportData.getRegionExportDataSet(region);
                        if (dataSnapshotID != null) {
                            info.getContext().getDimensionSet().put("DATASNAPSHOTID", dataSnapshotID);
                        }
                        if (singleEmpty = this.exportFixRegion(info, collectLinks, extractDataLinkSet, dataSet, grid2Data, sheet, regionDatas, fileGroupKeys)) continue;
                        emptyTable = false;
                        continue;
                    }
                    if (sortMap.size() > 0 && sortMap.containsKey(region.getKey())) {
                        region.getCells().add(sortMap.get(region.getKey()));
                    }
                    if (pagerInfo == null) {
                        pagerInfo = new PagerInfo();
                    }
                    pagerInfo.setLimit(20000);
                    if (pagerInfo.getOffset() == 0 || pagerInfo.getTotal() == Integer.MAX_VALUE) {
                        pagerInfo.setOffset(-1);
                        pagerInfo.setTotal(0);
                    }
                    dataSnapshotID = null;
                    if (info.getContext().getDimensionSet().containsKey("DATASNAPSHOTID")) {
                        dataSnapshotID = (DimensionValue)info.getContext().getDimensionSet().get("DATASNAPSHOTID");
                    }
                    dataSet = reportExportData.getRegionExportDataSet(region, true, pagerInfo, !info.isSumData());
                    if (dataSnapshotID != null) {
                        info.getContext().getDimensionSet().put("DATASNAPSHOTID", dataSnapshotID);
                    }
                    if ((eline = this.exportFloatRegion(collectLinks, extractDataLinkSet, dataSet, grid2Data, tempLine, sheet, info, startAndEndList, addRows, filters, pagerInfo, fileGroupKeys)) == -1) continue;
                    tempLine = eline;
                    emptyTable = false;
                }
                if (emptyTable && !info.isExportEmptyTable()) {
                    if (onlyStyle != null && info instanceof PdfExportParam) {
                        ((PdfExportParam)info).setOnlyStyle(true);
                    }
                    return null;
                }
                if (info.isArithmeticFormula() && sheet != null) {
                    this.exportFormula(info, grid2Data, sheet, collectLinks, formulaMap, dataRegions, addRows, reportExportData);
                }
                this.exportLinkCell(grid2Data, jtableContext, links);
            }
            catch (Exception e) {
                pagerInfo.setTotal(Integer.MAX_VALUE);
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            String value = this.taskOptionController.getValue(info.getContext().getTaskKey(), "EXCELFORMULA_CONDITION_123");
            if (value != null && value.equals("1") && sheet != null) {
                this.exportFormula(info, grid2Data, sheet, collectLinks, formulaMap, dataRegions, addRows, reportExportData);
            }
        }
        catch (Exception e) {
            pagerInfo.setTotal(Integer.MAX_VALUE);
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        if (onlyStyle != null && info instanceof PdfExportParam) {
            ((PdfExportParam)info).setOnlyStyle(true);
        }
        return grid2Data;
    }

    private void exportLinkCell(Grid2Data grid2Data, JtableContext jtableContext, List<CellLink> links) {
        int rowCount = grid2Data.getRowCount();
        int columnCount = grid2Data.getColumnCount();
        String pattern = "(http|local)(.*'}|.*\"})";
        Pattern r = Pattern.compile(pattern);
        for (int col = 0; col < columnCount; ++col) {
            for (int row = 0; row < rowCount; ++row) {
                GridCellData gridCellData = grid2Data.getGridCellData(col, row);
                if (Grid2DataConst.Cell_MODE.Cell_MODE_HTML.getIndex() != gridCellData.getCellMode() || gridCellData.getDataType() != GridEnums.getIntValue((Enum)GridEnums.DataType.HotLink)) continue;
                CellLink cellLink = new CellLink();
                cellLink.setShowText(gridCellData.getEditText());
                Matcher m = r.matcher(gridCellData.getShowText());
                if (!m.find()) continue;
                String linkString = m.group().trim().replace("\"}", "").replace("'}", "");
                if (linkString.startsWith("http")) {
                    cellLink.setUrl(linkString);
                    cellLink.setHyperlinkType(HyperlinkType.URL);
                } else if (linkString.startsWith("local")) {
                    FormData formDefine;
                    String sheetName;
                    cellLink.setHyperlinkType(HyperlinkType.DOCUMENT);
                    cellLink.setCol(col);
                    cellLink.setRow(row);
                    linkString = linkString.substring(8);
                    String[] keyAndValues = linkString.split("&");
                    HashMap<String, String> formKeyLinkKey = new HashMap<String, String>();
                    for (String keyAndValue : keyAndValues) {
                        String[] keyValue = keyAndValue.split("=");
                        formKeyLinkKey.put(keyValue[0], keyValue[1]);
                    }
                    String formKey = (String)formKeyLinkKey.get("formKey");
                    jtableContext.setFormKey(formKey);
                    String dataLinkKey = (String)formKeyLinkKey.get("dataLinkKey");
                    if (null != dataLinkKey && !"".equals(dataLinkKey)) {
                        LinkData link = this.jtableParamService.getLink(dataLinkKey);
                        IReportExportDataSet reportExportData = this.jtableResourceService.getReportExportData(jtableContext);
                        List<RegionData> dataRegions = reportExportData.getDataRegionList();
                        dataRegions = this.orderRegions(dataRegions);
                        int toRow = link.getRow();
                        for (int index = 0; index < dataRegions.size(); ++index) {
                            RegionData regionData = dataRegions.get(index);
                            if (regionData.getType() == DataRegionKind.DATA_REGION_SIMPLE.getValue()) continue;
                            int regionBottom = regionData.getRegionBottom();
                            if (link.getRow() >= regionBottom) continue;
                            IRegionExportDataSet floatRegionDataSet = reportExportData.getRegionExportDataSet(regionData);
                            int addRows = 0;
                            while (floatRegionDataSet.hasNext()) {
                                MemoryDataSet floatDataRowSet = (MemoryDataSet)floatRegionDataSet.next();
                                int dataRowCount = floatDataRowSet.size();
                                if (addRows != 0 || dataRowCount <= 1) continue;
                                addRows += dataRowCount - 1;
                            }
                            toRow += addRows;
                        }
                        cellLink.setToCol(link.getCol());
                        cellLink.setToRow(toRow);
                    }
                    if (!DataEntryUtil.sheetNameVolidate(sheetName = DataEntryUtil.getFormTitle(formDefine = this.jtableParamService.getReport(jtableContext.getFormKey(), jtableContext.getFormSchemeKey()))).equals(sheetName)) {
                        sheetName = DataEntryUtil.sheetNameVolidate(sheetName);
                    }
                    cellLink.setToSheetName(sheetName);
                }
                links.add(cellLink);
            }
        }
    }

    private void setFloatSort(IExportFacade info, Map<String, CellQueryInfo> sortMap) {
        Map<String, List<CellQueryInfo>> conditions = info.getConditions();
        if (null != conditions && conditions.size() > 0 && !info.isOnlyStyle()) {
            Set<Map.Entry<String, List<CellQueryInfo>>> entrySet = conditions.entrySet();
            for (Map.Entry<String, List<CellQueryInfo>> entry : entrySet) {
                String reginKey = entry.getKey();
                List<CellQueryInfo> cells = entry.getValue();
                if (null == cells || cells.size() <= 0) continue;
                for (CellQueryInfo cell : cells) {
                    String sort = cell.getSort();
                    if (StringUtils.isEmpty((String)sort)) continue;
                    CellQueryInfo newCellQueryInfo = new CellQueryInfo();
                    newCellQueryInfo.setCellKey(cell.getCellKey());
                    newCellQueryInfo.setSort(sort);
                    sortMap.put(reginKey, newCellQueryInfo);
                }
            }
        }
    }

    private void exportFormula(IExportFacade info, Grid2Data grid2Data, SXSSFSheet sheet, Set<String> collectLinks, Map<String, String> formulaMap, List<RegionData> dataRegions, Map<String, Integer> addRows, IReportExportDataSet reportExportData) {
        String formKey = reportExportData.getFormData().getKey();
        HashMap<String, Integer> orderMap = new HashMap<String, Integer>();
        ArrayList<String> folatRegion = new ArrayList<String>();
        for (RegionData region : dataRegions) {
            if (region.getType() != 0) {
                folatRegion.add(region.getKey());
            }
            orderMap.put(region.getKey(), region.getRegionTop());
        }
        String value = this.taskOptionController.getValue(info.getContext().getTaskKey(), "EXCELFORMULA_CONDITION_123");
        ArrayList<InsertRowsAdjustor> adjustors = new ArrayList<InsertRowsAdjustor>();
        if (folatRegion.size() > 0) {
            for (int i = 0; i < folatRegion.size(); ++i) {
                String regionKey = (String)folatRegion.get(i);
                int start = (Integer)orderMap.get(regionKey);
                int addRow = addRows.get(regionKey) - 1;
                InsertRowsAdjustor insertRowsAdjustor = new InsertRowsAdjustor(reportExportData.getFormData().getCode(), start, addRow);
                adjustors.add(insertRowsAdjustor);
            }
        }
        if (folatRegion.size() > 1) {
            Collections.reverse(adjustors);
        }
        block2: for (String calcDataLink : collectLinks) {
            FormulaQueryInfo formulaQueryInfo = new FormulaQueryInfo();
            formulaQueryInfo.setContext(info.getContext());
            formulaQueryInfo.setUseType(DataEngineConsts.FormulaType.CALCULATE.toString());
            formulaQueryInfo.setDataLinkKey(calcDataLink);
            formulaQueryInfo.setShowType(DataEngineConsts.FormulaShowType.EXCEL.getValue());
            formulaQueryInfo.setFormKey(info.getContext().getFormKey());
            formulaQueryInfo.setAdjustorList(adjustors);
            List formulaDatas = this.jtableParamService.getFormulaList(formulaQueryInfo);
            List dataLinkNodes = null;
            String formula = "";
            String localCell = "";
            for (Object formulaData : formulaDatas) {
                if (!calcDataLink.equals(formulaData.getAssignDataLinkKey())) continue;
                dataLinkNodes = formulaData.getDataLinkNodes();
                String[] splits = formulaData.getFormula().split("=");
                formula = splits[1].trim();
                localCell = splits[0].trim();
                break;
            }
            HashMap<String, FormDefine> anotherFormMap = new HashMap<String, FormDefine>();
            if (dataLinkNodes != null) {
                Object formulaData;
                formulaData = dataLinkNodes.iterator();
                while (formulaData.hasNext()) {
                    FormulaDataLinkNodeInfo formulaDataLinkNodeInfo = (FormulaDataLinkNodeInfo)formulaData.next();
                    if (formKey.equals(formulaDataLinkNodeInfo.getFormKey())) continue;
                    if (value == null || value.equals("0")) continue block2;
                    FormDefine queryFormById = this.controller.queryFormById(formulaDataLinkNodeInfo.getFormKey());
                    anotherFormMap.put(queryFormById.getFormCode(), queryFormById);
                }
            }
            if (formula.contains("!") && anotherFormMap.containsKey(formula.split("!")[0])) {
                FormDefine currentForm = this.controller.queryFormById(formKey);
                FormDefine formDefine = (FormDefine)anotherFormMap.get(formula.split("!")[0]);
                String oneCell = formDefine.getFormCode() + "!";
                String oneNowCell = "'" + sheet.getSheetName().replace(reportExportData.getFormData().getCode(), formDefine.getFormCode()).replace(currentForm.getTitle(), formDefine.getTitle()) + "'!";
                formula = formula.replaceAll(oneCell, oneNowCell);
                localCell = localCell.replaceAll(oneCell.replace(formDefine.getFormCode(), currentForm.getFormCode()), "");
                formulaMap.put(localCell, formula);
                continue;
            }
            String oneCell = reportExportData.getFormData().getCode() + "!";
            String oneNowCell = "'" + sheet.getSheetName() + "'!";
            formula = formula.replaceAll(oneCell, oneNowCell);
            localCell = localCell.replaceAll(oneCell, "");
            formulaMap.put(localCell, formula);
        }
    }

    private List<RegionData> orderRegions(List<RegionData> dataRegions) {
        ArrayList<RegionData> fixRegins = new ArrayList<RegionData>();
        ArrayList<RegionData> floatRegins = new ArrayList<RegionData>();
        for (RegionData regionData : dataRegions) {
            if (regionData.getType() == 0) {
                fixRegins.add(regionData);
                continue;
            }
            floatRegins.add(regionData);
        }
        if (floatRegins.size() > 0 && ((RegionData)floatRegins.get(0)).getType() == 2) {
            floatRegins.sort((o1, o2) -> o1.getRegionLeft() - o2.getRegionLeft());
        } else {
            floatRegins.sort((o1, o2) -> o1.getRegionTop() - o2.getRegionTop());
        }
        fixRegins.addAll(floatRegins);
        return fixRegins;
    }

    public boolean exportFixRegion(IExportFacade info, Set<String> collectLinks, Set<String> extractDataLinkSet, IRegionExportDataSet fixRegionDataSet, Grid2Data gridData, SXSSFSheet sheet, Map<String, Map<String, Object>> regionDatas, List<String> fileGroupKeys) {
        boolean emptyTable;
        if (fixRegionDataSet == null) {
            return true;
        }
        Map<String, String> enumPosMap = this.getEnumPosMap(fixRegionDataSet.getLinkDataList());
        int calcColor = ColorUtil.htmlColorToInt((String)"#D6F6EF");
        int extractColor = ColorUtil.htmlColorToInt((String)"#FBEEC4");
        List fieldDefines = fixRegionDataSet.getFieldDataList();
        List dataLinkDefines = fixRegionDataSet.getLinkDataList();
        MemoryDataSet dataRowSet = null;
        Object[] fixDatas = null;
        ArrayList<FieldData> dataLink2FieldData = new ArrayList<FieldData>();
        block0: for (LinkData linkData : dataLinkDefines) {
            for (FieldData field : fieldDefines) {
                if (!StringUtils.isNotEmpty((String)linkData.getZbid()) || !linkData.getZbid().equals(field.getFieldKey())) continue;
                field.setDataLinkKey(linkData.getKey());
                if ((field.getDefaultValue() == null || field.getDefaultValue().equals("")) && linkData.getDefaultValue() != null && !linkData.getDefaultValue().equals("")) {
                    field.setDefaultValue(linkData.getDefaultValue());
                }
                dataLink2FieldData.add(field);
                continue block0;
            }
        }
        if (null != regionDatas && !regionDatas.isEmpty() && !info.isOnlyStyle()) {
            Map dimensionSet = info.getContext().getDimensionSet();
            String companyDim = regionDatas.get("companyKey").get("companyKey").toString();
            String dimKey = ((DimensionValue)dimensionSet.get(companyDim)).getValue();
            String rowKey = fixRegionDataSet.getRegionData().getKey() + dimKey;
            Map<String, Object> row = regionDatas.get(rowKey);
            if (null != row) {
                fixDatas = this.dealCacheRow(info, fixRegionDataSet, regionDatas, dataLink2FieldData, rowKey, row);
            } else if (fixRegionDataSet.hasNext() && (dataRowSet = (MemoryDataSet)fixRegionDataSet.next()).size() > 0 && !info.isOnlyStyle()) {
                fixDatas = dataRowSet.getBuffer(0);
            }
        } else if (fixRegionDataSet.hasNext() && (dataRowSet = (MemoryDataSet)fixRegionDataSet.next()).size() > 0 && !info.isOnlyStyle()) {
            fixDatas = dataRowSet.getBuffer(0);
        }
        if (null == fixDatas) {
            List<String[]> datas = this.queryVersionDatas(info, fieldDefines);
            fixDatas = null != datas && !datas.isEmpty() ? (Object[])datas.get(0) : new String[dataLinkDefines.size()];
        }
        if (info.getContext().getDimensionSet().containsKey("DATASNAPSHOTID") && fieldDefines.size() > 0) {
            String snapshotID = ((DimensionValue)info.getContext().getDimensionSet().get("DATASNAPSHOTID")).getValue();
            RegionQueryInfo regionQueryInfo = fixRegionDataSet.getRegionQueryInfo();
            regionQueryInfo.setRegionKey(fixRegionDataSet.getRegionData().getKey());
            RegionDataSet regionDataSet = this.snapshotDataQueryService.queryRegionDatas(regionQueryInfo);
            fixDatas = regionDataSet.getData().size() > 0 ? ((List)regionDataSet.getData().get(0)).toArray() : new String[dataLinkDefines.size()];
        }
        if ((emptyTable = this.isEmptyTable(fixDatas)) && !info.isExportEmptyTable()) {
            return true;
        }
        HashMap<String, FieldData> fieldMap = new HashMap<String, FieldData>();
        for (FieldData fieldData : fieldDefines) {
            fieldMap.put(fieldData.getFieldKey(), fieldData);
        }
        for (int i = 0; i < dataLinkDefines.size(); ++i) {
            LinkData dataLink = (LinkData)dataLinkDefines.get(i);
            GridCellData cell = gridData.getGridCellData(dataLink.getCol(), dataLink.getRow());
            if (info.isArithmeticBackground()) {
                if (collectLinks.contains(dataLink.getKey())) {
                    cell.setBackGroundColor(ColorUtil.mergeColor((int)cell.getBackGroundColor(), (int)calcColor));
                    cell.setBackGroundStyle(GridEnums.getIntValue((Enum)GridEnums.BackgroundStyle.FILL));
                } else if (extractDataLinkSet.contains(dataLink.getKey())) {
                    cell.setBackGroundColor(ColorUtil.mergeColor((int)cell.getBackGroundColor(), (int)extractColor));
                    cell.setBackGroundStyle(GridEnums.getIntValue((Enum)GridEnums.BackgroundStyle.FILL));
                }
            }
            FieldData fieldData = (FieldData)fieldMap.get(dataLink.getZbid());
            FieldType type = FieldType.FIELD_TYPE_STRING;
            if (fieldData != null) {
                type = FieldType.forValue((int)fieldData.getFieldType());
            }
            if ((dataLink.getType() == LinkType.LINK_TYPE_FILE.getValue() || dataLink.getType() == LinkType.LINK_TYPE_PICTURE.getValue()) && fixDatas != null && fixDatas[i] != null && StringUtils.isNotEmpty((String)fixDatas[i].toString())) {
                fileGroupKeys.add(fixDatas[i].toString());
            }
            if (dataLink.getType() == LinkType.LINK_TYPE_FILE.getValue() && fixDatas != null && fixDatas[i] != null && StringUtils.isNotEmpty((String)fixDatas[i].toString())) {
                fixDatas[i] = "";
            }
            if (cell == null || info.isOnlyStyle()) continue;
            String dataReturn = dataLink.getStyle();
            String data = null == fixDatas ? "" : fixDatas[i];
            Grid2DataSetValueUtil.converterFieldTypeToGridCellData(type.getValue(), dataReturn, cell, dataLink, data, gridData, info.getContext(), enumPosMap);
            DataLinkDefine dataLinkDefine = this.controller.queryDataLinkDefine(dataLink.getKey());
            NumberFormatParser numberFormatParser = NumberFormatParser.parse((FormatProperties)dataLinkDefine.getFormatProperties());
            if (!numberFormatParser.isThousandPer()) continue;
            if (StringUtils.isNotEmpty((String)cell.getEditText())) {
                int scale = 0;
                if (cell.getFormatter().contains(".")) {
                    BigDecimal bigDecimal = new BigDecimal(cell.getFormatter().replace("\u2030", ""));
                    scale = bigDecimal.scale();
                }
                BigDecimal bigDecimalOfEditText = new BigDecimal(cell.getEditText()).multiply(new BigDecimal(1000));
                String showText = this.decimalTrans(bigDecimalOfEditText.toPlainString(), scale).toPlainString() + '\u2030';
                cell.setShowText(showText);
                cell.setEditText(showText);
            } else {
                cell.setShowText("");
                cell.setEditText("");
            }
            cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Text));
        }
        return emptyTable;
    }

    private BigDecimal decimalTrans(String number, int scale) {
        BigDecimal decimal = new BigDecimal(number);
        try {
            decimal = decimal.setScale(scale, RoundingMode.HALF_UP);
            return decimal;
        }
        catch (Exception e) {
            logger.error("\u6570\u503c\u8f6c\u6362\u5931\u8d25");
            return decimal;
        }
    }

    private List<String[]> querySnapshotDatas(RegionDataSet regionDataSet, List<FieldData> fieldDefines, boolean floatRegion) {
        if (regionDataSet == null || regionDataSet.getData().size() == 0) {
            return null;
        }
        ArrayList<String[]> list = new ArrayList<String[]>();
        List regionDataSetcells = (List)regionDataSet.getCells().get(regionDataSet.getCells().keySet().toArray()[0]);
        HashMap cells = new HashMap();
        for (int i = 0; i < regionDataSetcells.size(); ++i) {
            cells.put(regionDataSetcells.get(i), i);
        }
        for (List data : regionDataSet.getData()) {
            String[] row = new String[fieldDefines.size()];
            for (int i = 0; i < fieldDefines.size(); ++i) {
                String key = floatRegion ? ((DataLinkDefine)this.controller.getLinksInRegionByField(regionDataSet.getAnnotationResult().getRegionKey(), fieldDefines.get(i).getFieldKey()).get(0)).getKey() : fieldDefines.get(i).getDataLinkKey();
                if (cells.containsKey(key)) {
                    int index = (Integer)cells.get(key);
                    row[i] = data.get(index).toString();
                    if (!floatRegion) continue;
                    if (row[i].equals("true")) {
                        row[i] = "\u662f";
                    }
                    if (!row[i].equals("false")) continue;
                    row[i] = "\u5426";
                    continue;
                }
                row[i] = "";
            }
            list.add(row);
        }
        this.DataSnapshotRows.set(list.size());
        return list;
    }

    private Object[] dealCacheRow(IExportFacade info, IRegionExportDataSet fixRegionDataSet, Map<String, Map<String, Object>> regionDatas, List<FieldData> dataLink2FieldData, String rowKey, Map<String, Object> row) {
        String taskKey = info.getContext().getTaskKey();
        String numberZeroShow = this.taskOptionController.getValue(taskKey, "NUMBER_ZERO_SHOW");
        if (numberZeroShow == null) {
            numberZeroShow = "0";
        }
        Object[] fixDatas = new Object[dataLink2FieldData.size()];
        boolean empty = true;
        RegionData regionData = fixRegionDataSet.getRegionData();
        double measureRate = 1.0;
        AbstractRegionRelationEvn abstractRegionRelationEvn = this.createRegionRelationEvn(regionData, info.getContext());
        FormData form = this.jtableParamService.getReport(info.getContext().getFormKey(), info.getContext().getFormSchemeKey());
        List measures = form.getMeasures();
        if (measures != null && measures.size() > 0) {
            MeasureViewData measureViewData = (MeasureViewData)measures.get(0);
            String formMeasureCode = (String)form.getMeasureValues().get(measureViewData.getKey());
            String selectMeasureCode = (String)info.getContext().getMeasureMap().get(measureViewData.getKey());
            if (StringUtils.isNotEmpty((String)formMeasureCode) && StringUtils.isNotEmpty((String)selectMeasureCode) && !selectMeasureCode.equals(formMeasureCode)) {
                IJtableEntityService jtableEntityService = (IJtableEntityService)BeanUtil.getBean(IJtableEntityService.class);
                MeasureQueryInfo measureQueryInfo = new MeasureQueryInfo();
                measureQueryInfo.setMeasureViewKey(measureViewData.getKey());
                measureQueryInfo.setMeasureValue(formMeasureCode);
                MeasureData formMeasureData = jtableEntityService.queryMeasureDataByCode(measureQueryInfo);
                measureQueryInfo.setMeasureValue(selectMeasureCode);
                MeasureData selectMeasureData = jtableEntityService.queryMeasureDataByCode(measureQueryInfo);
                measureRate = selectMeasureData.getRate() / formMeasureData.getRate();
            }
        }
        for (int i = 0; i < dataLink2FieldData.size(); ++i) {
            Object fieldData;
            int fieldType;
            DataTable dataTable = this.runtimeDataSchemeService.getDataTable(dataLink2FieldData.get(i).getOwnerTableKey());
            fixDatas[i] = row.get(dataTable.getCode() + "." + dataLink2FieldData.get(i).getFieldCode());
            Object value = fixDatas[i];
            if (value != null && !String.valueOf(value).equals("")) {
                Object formatValue;
                fixDatas[i] = formatValue = this.processValue(measureRate, dataLink2FieldData.get(i).getFieldKey(), value, abstractRegionRelationEvn);
            }
            if (fixDatas[i] != null && StringUtils.isNotEmpty((String)fixDatas[i].toString())) {
                empty = false;
            }
            if ((fieldType = dataLink2FieldData.get(i).getFieldType()) == FieldType.FIELD_TYPE_LOGIC.getValue()) {
                fixDatas[i] = fixDatas[i] != null && fixDatas[i].equals("\u662f") ? "true" : "false";
            }
            if (!(fieldType != FieldType.FIELD_TYPE_INTEGER.getValue() && fieldType != FieldType.FIELD_TYPE_FLOAT.getValue() && fieldType != FieldType.FIELD_TYPE_DECIMAL.getValue() || (fieldData = fixDatas[i]) == null || fieldData.toString().equals("") || !((double)Math.abs(Float.parseFloat(fieldData.toString())) < 1.0E-10) || "0".equals(numberZeroShow))) {
                fixDatas[i] = numberZeroShow;
            }
            if (fixDatas[i] == null || !String.valueOf(fixDatas[i]).contains("|")) continue;
            try {
                String json;
                String s;
                fieldData = dataLink2FieldData.get(i);
                DataLinkDefine dataLinkDefine = this.controller.queryDataLinkDefine(fieldData.getDataLinkKey());
                if (dataLinkDefine.getCaptionFieldsString() != null && dataLinkDefine.getCaptionFieldsString().split(";").length == 1) {
                    int title = 0;
                    String s2 = String.valueOf(fixDatas[i]);
                    if (s2 != null && !s2.equals("") && s2.contains("|") && !dataLinkDefine.getCaptionFieldsString().equals("CODE")) {
                        title = 1;
                    }
                    if (s2 == null) continue;
                    String json2 = "[{\"code\":\"" + s2.split("\\|")[0] + "\",\"codevalue\":\"" + s2.split("\\|")[0] + "\",\"title\":\"" + s2.split("\\|")[title] + "\"}]";
                    fixDatas[i] = json2;
                    continue;
                }
                if (dataLinkDefine.getCaptionFieldsString() != null && dataLinkDefine.getCaptionFieldsString().split(";").length == 2) {
                    s = String.valueOf(fixDatas[i]);
                    json = "[{\"code\":\"" + s.split("\\|")[0] + "\",\"codevalue\":\"" + s.split("\\|")[0] + "\",\"title\":\"" + s + "\"}]";
                    fixDatas[i] = json;
                    continue;
                }
                s = String.valueOf(fixDatas[i]);
                json = "[{\"code\":\"" + s.split("\\|")[0] + "\",\"codevalue\":\"" + s.split("\\|")[0] + "\",\"title\":\"" + s + "\"}]";
                fixDatas[i] = json;
                continue;
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
        if (empty) {
            fixDatas = null;
        }
        regionDatas.remove(rowKey);
        return fixDatas;
    }

    private Object processValue(double measureRate, String fieldKey, Object value, AbstractRegionRelationEvn abstractRegionRelationEvn) {
        boolean numberField;
        if (measureRate == 1.0) {
            return value;
        }
        LinkData linkData = abstractRegionRelationEvn.getDataLinkByFiled(fieldKey);
        if (linkData == null) {
            return value;
        }
        FieldData fieldData = abstractRegionRelationEvn.getFieldByDataLink(linkData.getKey());
        if (StringUtils.isNotEmpty((String)fieldData.getMeasureUnit()) && fieldData.getMeasureUnit().contains("NotDimession")) {
            return value;
        }
        FieldType type = FieldType.forValue((int)fieldData.getFieldType());
        boolean bl = numberField = type == FieldType.FIELD_TYPE_FLOAT || type == FieldType.FIELD_TYPE_INTEGER || type == FieldType.FIELD_TYPE_DECIMAL;
        if (numberField) {
            int fractionDigits = fieldData.getFractionDigits();
            try {
                BigDecimal bigDecimal;
                String tempValue = value.toString();
                if (tempValue.contains(",")) {
                    Number number = NumberFormat.getIntegerInstance(Locale.getDefault()).parse(tempValue);
                    bigDecimal = new BigDecimal(number.doubleValue());
                } else {
                    bigDecimal = new BigDecimal(tempValue);
                }
                bigDecimal = new BigDecimal(value.toString());
                bigDecimal = bigDecimal.divide(new BigDecimal(measureRate), fractionDigits, 4);
                return bigDecimal.doubleValue();
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                return value;
            }
        }
        return value;
    }

    private AbstractRegionRelationEvn createRegionRelationEvn(RegionData region, JtableContext jtableContext) {
        Object regionRelationEvn = null;
        regionRelationEvn = region.getType() == DataRegionKind.DATA_REGION_SIMPLE.getValue() ? new SimpleRegionRelationEvn(region, jtableContext) : new FloatRegionRelationEvn(region, jtableContext);
        return regionRelationEvn;
    }

    private List<String[]> queryVersionDatas(IExportFacade info, List<FieldData> fieldDefines) {
        JtableContext context = info.getContext();
        Map dimensionSet = context.getDimensionSet();
        if (!dimensionSet.containsKey("VERSIONID") || ((DimensionValue)dimensionSet.get("VERSIONID")).getValue().equals("00000000-0000-0000-0000-000000000000")) {
            return null;
        }
        String versionDv = ((DimensionValue)dimensionSet.get("VERSIONID")).getValue();
        ISecretLevelService secretLevelService = (ISecretLevelService)BeanUtil.getBean(ISecretLevelService.class);
        List fileList = this.fileInfoService.getFileInfoByGroup(context.getFormKey(), "DataVer", FileStatus.AVAILABLE);
        ArrayList<FileInfo> tableFile = new ArrayList<FileInfo>();
        boolean secretLevelEnable = secretLevelService.secretLevelEnable(info.getContext().getTaskKey());
        SecretLevelInfo secretLevel = secretLevelService.getSecretLevel(context);
        for (FileInfo item : fileList) {
            boolean canAccess = true;
            if (StringUtils.isNotEmpty((String)item.getSecretlevel()) && secretLevelEnable && secretLevel != null) {
                SecretLevelItem secretLevelItem = secretLevelService.getSecretLevelItem(item.getSecretlevel());
                boolean bl = canAccess = secretLevelService.canAccess(secretLevelItem) && secretLevelService.compareSercetLevel(secretLevel.getSecretLevelItem(), secretLevelItem);
            }
            if (!canAccess || !item.getName().equals(versionDv)) continue;
            tableFile.add(item);
        }
        Iterator iterator = tableFile.iterator();
        if (iterator.hasNext()) {
            FileInfo fileInfo = (FileInfo)iterator.next();
            byte[] bs = this.fileService.area("DataVer").download(fileInfo.getKey());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            String resultsss = null;
            try {
                out.write(bs);
                resultsss = new String(out.toByteArray());
            }
            catch (IOException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            Gson gson = new Gson();
            List formList = (List)gson.fromJson(resultsss, new TypeToken<List<?>>(){}.getType());
            HashSet<String> tableKeys = new HashSet<String>();
            for (FieldData fieldDefine : fieldDefines) {
                tableKeys.add(fieldDefine.getOwnerTableKey());
            }
            ArrayList<String[]> lists = new ArrayList<String[]>();
            String[] heads = new String[fieldDefines.size()];
            for (int i = 0; i < heads.length; ++i) {
                DataTable dataTable = this.runtimeDataSchemeService.getDataTable(fieldDefines.get(i).getOwnerTableKey());
                heads[i] = dataTable.getCode() + "." + fieldDefines.get(i).getFieldCode();
            }
            for (Object object : formList) {
                Map o = (Map)object;
                for (Map.Entry table : o.entrySet()) {
                    int i = 0;
                    DataTable dataTable = null;
                    try {
                        dataTable = this.dataSchemeService.getDataTable((String)table.getKey());
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    if (null == dataTable) continue;
                    List deployInfoByDataTableKey = this.dataSchemeService.getDeployInfoByDataTableKey((String)table.getKey());
                    String tableName = ((DataFieldDeployInfo)deployInfoByDataTableKey.get(0)).getTableName();
                    for (Map it : (List)table.getValue()) {
                        Map dimensionSet2 = info.getContext().getDimensionSet();
                        boolean isCurrentRow = true;
                        ExecutorContext context2 = new ExecutorContext(this.dataDefinitionRuntimeController);
                        IDataAssist dataAssist = this.dataAccessProvider.newDataAssist(context2);
                        for (Map.Entry entry : dimensionSet2.entrySet()) {
                            FieldDefine dimensionField = dataAssist.getDimensionField(tableName, (String)entry.getKey());
                            if (((String)entry.getKey()).equals("VERSIONID") || entry.getValue() == null || ((DimensionValue)entry.getValue()).getValue() == null || dimensionField == null || it.get(dimensionField.getCode()) == null || ((DimensionValue)entry.getValue()).getValue().equals(it.get(dimensionField.getCode()))) continue;
                            isCurrentRow = false;
                        }
                        if (!isCurrentRow) continue;
                        for (int j = 0; j < heads.length; ++j) {
                            String[] row;
                            if (!tableName.equals(heads[j].split("\\.")[0])) continue;
                            if (lists.isEmpty() || lists.size() <= i) {
                                row = new String[heads.length];
                                lists.add(row);
                            }
                            row = (String[])lists.get(i);
                            row[j] = String.valueOf(it.get(heads[j].split("\\.")[1]) == null ? "" : it.get(heads[j].split("\\.")[1]));
                        }
                        ++i;
                    }
                }
            }
            return lists;
        }
        return null;
    }

    public int exportFloatRegion(Set<String> collectLinks, Set<String> extractDataLinkSet, IRegionExportDataSet floatRegionDataSet, Grid2Data gridData, int tempLine, SXSSFSheet sheet, IExportFacade info, List<String> startAndEndList, Map<String, Integer> addRows, List<FilterRegionCondition> filters, PagerInfo pagerInfo, List<String> fileGroupKeys) {
        boolean bl;
        int regionStartRow;
        List floatFieldDefines = floatRegionDataSet.getFieldDataList();
        List floatdataLinkDefines = floatRegionDataSet.getLinkDataList();
        floatdataLinkDefines.sort((o1, o2) -> o1.getRow() - o2.getRow());
        RegionNumberManager numberManager = floatRegionDataSet.getNumberManager();
        RegionData regionData = floatRegionDataSet.getRegionData();
        int lineInfo = regionStartRow = (regionData.getType() == 2 ? regionData.getRegionLeft() : regionData.getRegionTop()) + tempLine;
        List<String> tabs = info.getTabs();
        Set tabSets = null;
        if (null != tabs) {
            tabSets = tabs.stream().map(one -> one).collect(Collectors.toSet());
        }
        List regionTabsBeforeOrder = floatRegionDataSet.getRegionTabs();
        List<RegionTab> regionTabs = new ArrayList();
        boolean allTab = info.isExportAllLable();
        List<RegionFilterListInfo> regionFilterListInfo = info.getRegionFilterListInfo();
        List<Object> filterList = new ArrayList();
        if (regionFilterListInfo != null && !regionFilterListInfo.isEmpty()) {
            for (RegionFilterListInfo regionFilterListInfo2 : regionFilterListInfo) {
                if (!regionFilterListInfo2.getAreaKey().equals(regionData.getKey())) continue;
                filterList = regionFilterListInfo2.getFilterConditions();
            }
        }
        if (!allTab) {
            if (tabs != null && !tabs.isEmpty() && !regionTabsBeforeOrder.isEmpty()) {
                for (String string : tabs) {
                    for (RegionTab regionTab : regionTabsBeforeOrder) {
                        if (!regionTab.getTitle().equals(string)) continue;
                        regionTabs.add(regionTab);
                    }
                }
            }
        } else {
            ArrayList<String> tabList = new ArrayList<String>();
            for (RegionTab regionTab : regionTabsBeforeOrder) {
                tabList.add(regionTab.getTitle());
            }
            tabSets = null;
            tabSets = tabList.stream().map(one -> one).collect(Collectors.toSet());
            regionTabs = regionTabsBeforeOrder;
        }
        String extentGridDataIsNullTableStr = this.iNvwaSystemOptionService.get("nr-data-entry-export", "EXTENT_GRID_IS_NULL_TABLE");
        boolean bl2 = false;
        if (extentGridDataIsNullTableStr.equals("1")) {
            bl = true;
        }
        if (!regionTabs.isEmpty() && !info.isOnlyStyle()) {
            RegionTab haveALL = null;
            for (RegionTab regionTab : regionTabs) {
                String condition;
                if (tabSets != null && !tabSets.isEmpty() && !tabSets.contains(regionTab.getTitle()) || !StringUtils.isEmpty((String)(condition = regionTab.getFilter()))) continue;
                haveALL = regionTab;
                break;
            }
            if (null == haveALL) {
                haveALL = (RegionTab)regionTabs.get(0);
            }
            if (null == tabSets || tabSets.size() == 0) {
                if (null != haveALL) {
                    floatRegionDataSet.setRegionTab(haveALL, pagerInfo.getLimit());
                    if (!filterList.isEmpty()) {
                        floatRegionDataSet.setRegionFilter(filterList);
                    }
                    int lineInfoNow = this.setDataMethod(info, collectLinks, extractDataLinkSet, floatRegionDataSet, lineInfo, floatFieldDefines, gridData, numberManager, tempLine, sheet, addRows, filters, pagerInfo, null, fileGroupKeys);
                    RegionExportDataSetImpl regionExportDataSet = (RegionExportDataSetImpl)floatRegionDataSet;
                    RegionDataSet regionDataSet = regionExportDataSet.getRegionDataSet();
                    if (lineInfoNow == -1 || regionDataSet != null && regionDataSet.isRegionOnlyHasExtentGridData() && bl) {
                        return -1;
                    }
                    tempLine += lineInfoNow - lineInfo;
                    lineInfo = lineInfoNow;
                }
            } else {
                boolean allTabIsEmpty = true;
                for (RegionTab regionTab : regionTabs) {
                    if (pagerInfo.getTotal() == Integer.MAX_VALUE) {
                        pagerInfo.setTotal(0);
                    }
                    if (tabSets.size() != 1) {
                        gridData.insertRows(lineInfo, 1, lineInfo);
                        GridCellData gridCellTab = gridData.getGridCellData(1, lineInfo);
                        gridCellTab.setShowText(regionTab.getTitle());
                        Grid2CellField grid2CellField = new Grid2CellField(1, gridCellTab.getRowIndex(), gridData.getColumnCount() - 1, gridCellTab.getRowIndex());
                        gridData.merges().addMergeRect(grid2CellField);
                        ++lineInfo;
                        ++tempLine;
                        GridCellStyleData cellStyleData = new GridCellStyleData();
                        cellStyleData.setHorzAlign(GridEnums.TextAlignment.Fore.ordinal());
                        gridCellTab.setCellStyleData(cellStyleData);
                        gridCellTab.setBottomBorderStyle(GridEnums.GridBorderStyle.SOLID.getStyle());
                    }
                    floatRegionDataSet.setRegionTab(regionTab, pagerInfo.getLimit());
                    if (!filterList.isEmpty()) {
                        floatRegionDataSet.setRegionFilter(filterList);
                    }
                    int lineInfoNow = this.setDataMethod(info, collectLinks, extractDataLinkSet, floatRegionDataSet, lineInfo, floatFieldDefines, gridData, numberManager, tempLine, sheet, addRows, filters, pagerInfo, regionTab.getTitle(), fileGroupKeys);
                    RegionExportDataSetImpl regionExportDataSet = (RegionExportDataSetImpl)floatRegionDataSet;
                    RegionDataSet regionDataSet = regionExportDataSet.getRegionDataSet();
                    if (lineInfoNow == -1 || regionDataSet != null && regionDataSet.isRegionOnlyHasExtentGridData() && bl) {
                        if (regionTabs.indexOf(regionTab) == regionTabs.size() - 1 && allTabIsEmpty) {
                            return -1;
                        }
                        lineInfoNow = lineInfo;
                    } else {
                        allTabIsEmpty = false;
                    }
                    tempLine += lineInfoNow - lineInfo;
                    if (null != sheet && tabSets.size() != 1) {
                        String startAndEndS = lineInfo - 1 + ";" + (lineInfoNow - 2);
                        startAndEndList.add(startAndEndS);
                    }
                    lineInfo = lineInfoNow;
                    if (pagerInfo.getTotal() == Integer.MAX_VALUE) continue;
                }
            }
        } else {
            if (!filterList.isEmpty()) {
                floatRegionDataSet.setRegionFilter(filterList);
            }
            int lineInfoNow = this.setDataMethod(info, collectLinks, extractDataLinkSet, floatRegionDataSet, lineInfo, floatFieldDefines, gridData, numberManager, tempLine, sheet, addRows, filters, pagerInfo, null, fileGroupKeys);
            RegionExportDataSetImpl regionExportDataSet = (RegionExportDataSetImpl)floatRegionDataSet;
            RegionDataSet regionDataSet = regionExportDataSet.getRegionDataSet();
            if (lineInfoNow == -1 || regionDataSet != null && regionDataSet.isRegionOnlyHasExtentGridData() && bl) {
                return -1;
            }
            tempLine += lineInfoNow - lineInfo;
            lineInfo = lineInfoNow;
        }
        return tempLine;
    }

    private int setDataMethod(IExportFacade info, Set<String> collectLinks, Set<String> extractDataLinkSet, IRegionExportDataSet floatRegionDataSet, int lineInfo, List<FieldData> floatFieldDefines, Grid2Data gridData, RegionNumberManager numberManager, int tempLine, SXSSFSheet sheet, Map<String, Integer> addRows, List<FilterRegionCondition> filters, PagerInfo pagerInfo, String currentTab, List<String> fileGroupKeys) {
        int i;
        if (floatRegionDataSet.getRegionData().getType() == 2) {
            pagerInfo.setTotal(Integer.MAX_VALUE);
            return this.setDataMethod2(info, collectLinks, extractDataLinkSet, floatRegionDataSet, lineInfo, floatFieldDefines, gridData, numberManager, tempLine, sheet, addRows, filters, pagerInfo, fileGroupKeys);
        }
        Map<String, List<CellQueryInfo>> conditions = info.getConditions();
        HashMap<String, Boolean> isThousandPeLinkMap = new HashMap<String, Boolean>();
        RegionDataSet queryRegionData = null;
        HashSet<String> canSeeList = null;
        FilterRegionCondition filterRegionCondition = null;
        HashMap<String, Integer> everageMap = null;
        HashMap everageVlueMap = null;
        HashMap<String, String> everageColMap = null;
        if (null != conditions && conditions.size() > 0 && !info.isOnlyStyle()) {
            Set<Map.Entry<String, List<CellQueryInfo>>> entrySet = conditions.entrySet();
            for (Map.Entry<String, List<CellQueryInfo>> entry : entrySet) {
                String key = floatRegionDataSet.getRegionData().getKey();
                if (!key.equals(entry.getKey())) continue;
                RegionQueryInfo queryInfo = new RegionQueryInfo();
                queryInfo.getFilterInfo().setCellQuerys(entry.getValue());
                queryInfo.setContext(info.getContext());
                queryInfo.setRegionKey(entry.getKey());
                queryRegionData = this.jtableResourceService.queryRegionData(queryInfo);
                List data = queryRegionData.getData();
                canSeeList = new HashSet<String>();
                for (List row : data) {
                    canSeeList.add((String)row.get(0));
                }
                filterRegionCondition = new FilterRegionCondition();
                List linkDataList = floatRegionDataSet.getLinkDataList();
                Map<String, LinkData> linkDataMap = linkDataList.stream().collect(Collectors.toMap(LinkData::getKey, LinkData2 -> LinkData2));
                List<CellQueryInfo> cellQueryInfos = entry.getValue();
                for (CellQueryInfo cellQueryInfo : cellQueryInfos) {
                    FilterColCondition colCondition;
                    ArrayList<FilterColCondition> filterCols = new ArrayList<FilterColCondition>();
                    String cellKey = cellQueryInfo.getCellKey();
                    LinkData linkData = linkDataMap.get(cellKey);
                    int col = linkData.getCol();
                    String colString = CellReference.convertNumToColString(col - 1);
                    List opList = cellQueryInfo.getOpList();
                    List inList = cellQueryInfo.getInList();
                    String shortcuts = cellQueryInfo.getShortcuts();
                    if (null != inList && inList.size() > 0) {
                        Iterator iterator = inList.iterator();
                        while (iterator.hasNext()) {
                            String value = (String)iterator.next();
                            colCondition = new FilterColCondition();
                            value = this.handSelect(floatRegionDataSet, linkData, value);
                            colCondition.setValue(value);
                            colCondition.setFilterOperator(FilterOperator.EQUAL);
                            filterCols.add(colCondition);
                        }
                    } else if (null != shortcuts && !"".equals(shortcuts)) {
                        if (null == everageMap) {
                            everageMap = new HashMap<String, Integer>();
                        }
                        if (null == everageVlueMap) {
                            everageVlueMap = new HashMap();
                        }
                        if (null == everageColMap) {
                            everageColMap = new HashMap<String, String>();
                        }
                        FilterColCondition colCondition2 = new FilterColCondition();
                        if ("moreThanEverage".equals(shortcuts)) {
                            colCondition2.setFilterOperator(FilterOperator.ABOVE_AVERAGE);
                        } else if ("lessThanEverage".equals(shortcuts)) {
                            colCondition2.setFilterOperator(FilterOperator.BELOW_AVERAGE);
                        } else if ("topTen;10".equals(shortcuts)) {
                            colCondition2.setFilterOperator(FilterOperator.TOP10);
                        }
                        filterCols.add(colCondition2);
                        int indexOf = filterCols.indexOf(colCondition2);
                        everageMap.put(cellKey, indexOf);
                        everageVlueMap.put(cellKey, new ArrayList());
                        everageColMap.put(cellKey, colString);
                    } else if (null != opList && opList.size() > 0) {
                        for (FilterCondition filterCondition : opList) {
                            colCondition = new FilterColCondition();
                            colCondition.setAnd(Boolean.valueOf("and".equals(cellQueryInfo.getAttendedMode())));
                            colCondition.setValue(filterCondition.getOpValue());
                            if ("eq".equals(filterCondition.getOpCode())) {
                                colCondition.setFilterOperator(FilterOperator.EQUAL);
                            } else if ("noteq".equals(filterCondition.getOpCode())) {
                                colCondition.setFilterOperator(FilterOperator.NOT_EQUAL);
                            } else if ("more".equals(filterCondition.getOpCode())) {
                                colCondition.setFilterOperator(FilterOperator.GREATER_THAN);
                            } else if ("less".equals(filterCondition.getOpCode())) {
                                colCondition.setFilterOperator(FilterOperator.LESS_THAN);
                            } else if ("notless".equals(filterCondition.getOpCode())) {
                                colCondition.setFilterOperator(FilterOperator.GREATER_THAN_OR_EQUAL);
                            } else if ("notmore".equals(filterCondition.getOpCode())) {
                                colCondition.setFilterOperator(FilterOperator.LESS_THAN_OR_EQUAL);
                            } else if ("contain".equals(filterCondition.getOpCode())) {
                                colCondition.setFilterOperator(FilterOperator.CONTAIN);
                            } else if ("notcontain".equals(filterCondition.getOpCode())) {
                                colCondition.setFilterOperator(FilterOperator.NOT_CONTAIN);
                            }
                            filterCols.add(colCondition);
                        }
                    }
                    String sort = cellQueryInfo.getSort();
                    if (!StringUtils.isEmpty((String)sort)) {
                        filterRegionCondition.setSortCol(colString);
                        filterRegionCondition.setIsAsc("asc".equals(cellQueryInfo.getSort()));
                    }
                    if (filterCols.size() <= 0) continue;
                    filterRegionCondition.addColFilterCondition(colString, filterCols);
                }
            }
        }
        int calcColor = ColorUtil.htmlColorToInt((String)"#D6F6EF");
        int extractColor = ColorUtil.htmlColorToInt((String)"#FBEEC4");
        int page = 0;
        int beginRow = lineInfo;
        int regionTop = floatRegionDataSet.getRegionData().getRegionTop();
        int regionBottom = floatRegionDataSet.getRegionData().getRegionBottom();
        int moreRow = regionBottom - regionTop;
        boolean deleteRow = true;
        JtableContext context = info.getContext();
        Map dimensionSet = context.getDimensionSet();
        String versionDv = null;
        if (!(!dimensionSet.containsKey("VERSIONID")) && !((DimensionValue)dimensionSet.get("VERSIONID")).getValue().equals("00000000-0000-0000-0000-000000000000")) {
            versionDv = ((DimensionValue)dimensionSet.get("VERSIONID")).getValue();
        }
        List<Object> lists = new ArrayList();
        if (null != versionDv) {
            lists = this.queryVersionDatas(info, floatFieldDefines);
        }
        RegionDataSet regionDataSet = null;
        if (info.getContext().getDimensionSet().containsKey("DATASNAPSHOTID")) {
            String snapshotID = ((DimensionValue)info.getContext().getDimensionSet().get("DATASNAPSHOTID")).getValue();
            RegionQueryInfo regionQueryInfo = floatRegionDataSet.getRegionQueryInfo();
            regionQueryInfo.setRegionKey(floatRegionDataSet.getRegionData().getKey());
            if (regionQueryInfo.getPagerInfo().getOffset() < 0) {
                regionQueryInfo.getPagerInfo().setOffset(0);
            }
            if ((lists = this.querySnapshotDatas(regionDataSet = this.snapshotDataQueryService.queryRegionDatas(regionQueryInfo, info.isSumData()), floatFieldDefines, true)) == null && !info.isExportEmptyTable()) {
                pagerInfo.setTotal(Integer.MAX_VALUE);
                return -1;
            }
            versionDv = snapshotID;
        }
        List linkDataList = floatRegionDataSet.getLinkDataList();
        RegionGradeInfo grade = floatRegionDataSet.getRegionData().getGrade();
        List gradeCells = grade.getGradeCells();
        HashMap<String, GradeCellInfo> gradeCellMap = new HashMap<String, GradeCellInfo>();
        for (GradeCellInfo gradeCell : gradeCells) {
            gradeCellMap.put(gradeCell.getZbid(), gradeCell);
        }
        for (int linkIndex = 0; linkIndex < linkDataList.size(); ++linkIndex) {
            LinkData linkData = (LinkData)linkDataList.get(linkIndex);
            if (!(linkData instanceof EnumLinkData) || !gradeCellMap.containsKey(linkData.getZbid())) continue;
            GradeCellInfo gradeCellInfo = (GradeCellInfo)gradeCellMap.get(linkData.getZbid());
            EnumLinkData enumLink = (EnumLinkData)linkData;
            if (gradeCellInfo == null || !gradeCellInfo.isTrim()) continue;
            gradeCellInfo.setGradeStruct(this.jtableParamService.getEntity(enumLink.getEntityKey()).getTreeStruct());
        }
        int offset = pagerInfo.getOffset();
        int sheetCount = 0;
        boolean emptyTable = true;
        HashSet<String> linkCells = new HashSet<String>();
        HashMap entityRowMap = new HashMap();
        while (sheetCount < 500000 && (floatRegionDataSet.hasNext() || pagerInfo.getOffset() > -1) && pagerInfo.getTotal() != Integer.MAX_VALUE) {
            Grid2Data tempGridData;
            Grid2FieldList merges;
            GridCellData gridcell;
            MemoryDataSet floatDataRowSet = (MemoryDataSet)floatRegionDataSet.next();
            int rowEmCount = floatDataRowSet.size();
            if (emptyTable) {
                for (i = 0; i < rowEmCount; ++i) {
                    Object[] buffer = floatDataRowSet.getBuffer(i);
                    emptyTable = this.isEmptyTable(buffer);
                }
                if (emptyTable && lists != null && !lists.isEmpty()) {
                    emptyTable = false;
                }
            }
            if (floatRegionDataSet.getTotalCount() == 0) {
                pagerInfo.setTotal(Integer.MAX_VALUE);
            }
            int pageCount = 0;
            pageCount = offset == -1 ? 0 : offset;
            if (pageCount * pagerInfo.getLimit() + pagerInfo.getLimit() >= floatRegionDataSet.getTotalCount()) {
                pagerInfo.setTotal(Integer.MAX_VALUE);
            }
            if (floatDataRowSet.size() == 0 && floatRegionDataSet.getTotalCount() > 0) {
                pagerInfo.setOffset(++offset);
                break;
            }
            int dataRowCount = floatDataRowSet.size();
            if (null != versionDv && !lists.isEmpty()) {
                dataRowCount = lists.size();
                sheetCount += dataRowCount;
            } else {
                sheetCount += floatDataRowSet.size();
            }
            int rowCount = dataRowCount;
            int gridAddRows = dataRowCount * (moreRow + 1);
            if (dataRowCount > 0) {
                if (!info.isOnlyStyle()) {
                    int headerRowCount = gridData.getHeaderRowCount();
                    gridData.insertRows(lineInfo, gridAddRows, lineInfo, true);
                    if (moreRow > 0) {
                        int yy = (moreRow + 1) * dataRowCount + lineInfo;
                        for (int i2 = 0; i2 < dataRowCount; ++i2) {
                            gridData.copyFrom(gridData, 0, yy, gridData.getColumnCount() - 1, yy + moreRow, 0, i2 * (moreRow + 1) + lineInfo);
                        }
                    }
                    if (lineInfo == headerRowCount) {
                        gridData.setHeaderRowCount(headerRowCount);
                    }
                    Grid2FieldList merges2 = gridData.merges();
                    int gridDataShowRow = moreRow + 1;
                    for (int x = 0; x < gridDataShowRow; ++x) {
                        int localGridShowRow = lineInfo + gridAddRows + x;
                        int tempLineInfo = lineInfo + x;
                        int y = 0;
                        while (tempLineInfo < lineInfo + gridAddRows) {
                            for (int col = 0; col < gridData.getColumnCount(); ++col) {
                                gridcell = gridData.getGridCellData(col, localGridShowRow);
                                String showText = gridcell.getShowText();
                                if (null == showText || "".equals(showText)) continue;
                                gridcell = gridData.getGridCellData(col, tempLineInfo);
                                gridcell.setShowText(showText);
                            }
                            tempLineInfo = tempLineInfo + 1 + moreRow;
                            ++y;
                        }
                    }
                } else {
                    if (page != 0) break;
                    dataRowCount = 1;
                }
            } else {
                if (page != 0) break;
                dataRowCount = 1;
                for (int fieldIndex = 0; fieldIndex < floatFieldDefines.size(); ++fieldIndex) {
                    FieldData fieldDefine = floatFieldDefines.get(fieldIndex);
                    LinkData dataLinkDefine = floatRegionDataSet.getLinkDataByField(fieldDefine);
                    GridCellData gridcell2 = gridData.getGridCellData(dataLinkDefine.getCol(), lineInfo);
                    if (null == gridcell2) continue;
                    gridcell2.setShowText("");
                    gridcell2.setEditText("");
                }
            }
            if (!info.isOnlyStyle()) {
                addRows.put(floatRegionDataSet.getRegionData().getKey(), dataRowCount * (moreRow + 1));
            }
            Map<String, String> enumPosMap = this.getEnumPosMap(floatRegionDataSet.getLinkDataList());
            int rowDataIndex = 0;
            while (rowDataIndex < dataRowCount) {
                if (null != queryRegionData && null != canSeeList && !info.isOnlyStyle()) {
                    Object[] buffer = null;
                    buffer = null != versionDv && !lists.isEmpty() ? (Object[])lists.get(rowDataIndex) : floatDataRowSet.getBuffer(rowDataIndex);
                    String id = (String)buffer[buffer.length - 1];
                    if (!canSeeList.contains(id)) {
                        gridData.setRowHidden(lineInfo, true);
                    }
                }
                for (int fieldIndex = 0; fieldIndex < floatFieldDefines.size(); ++fieldIndex) {
                    String dataReturn;
                    FieldData fieldDefine = floatFieldDefines.get(fieldIndex);
                    LinkData dataLinkDefine = floatRegionDataSet.getLinkDataByField(fieldDefine);
                    RegionExportDataSetImpl regionDataSetImpl = (RegionExportDataSetImpl)floatRegionDataSet;
                    Map mergeCell = regionDataSetImpl.getRegionDataSet().getMergeCells();
                    if (dataLinkDefine == null) continue;
                    int temp = dataLinkDefine.getRow() + rowDataIndex * (moreRow + 1) + tempLine + page * pagerInfo.getLimit() * (moreRow + 1);
                    if (temp > lineInfo) {
                        lineInfo = temp;
                    }
                    gridcell = gridData.getGridCellData(dataLinkDefine.getCol(), temp);
                    String posStr = this.getPosition(dataLinkDefine.getCol(), temp);
                    linkCells.add(posStr);
                    FieldType type = FieldType.forValue((int)fieldDefine.getFieldType());
                    if (gridcell == null) continue;
                    if (mergeCell.containsKey(dataLinkDefine.getKey())) {
                        List mergeCellInfo = (List)mergeCell.get(dataLinkDefine.getKey());
                        for (List merge : mergeCellInfo) {
                            if (merge.isEmpty() || merge.size() != 2) continue;
                            int top = (Integer)merge.get(0);
                            int bottom = (Integer)merge.get(1);
                            if (top != rowDataIndex) continue;
                            int tempBottom = dataLinkDefine.getRow() + bottom * (moreRow + 1) + tempLine + page * pagerInfo.getLimit() * (moreRow + 1);
                            gridData.mergeCells(dataLinkDefine.getCol(), temp, dataLinkDefine.getCol(), tempBottom);
                        }
                    }
                    if (info.isArithmeticBackground()) {
                        if (collectLinks.contains(dataLinkDefine.getKey())) {
                            gridcell.setBackGroundColor(ColorUtil.mergeColor((int)gridcell.getBackGroundColor(), (int)calcColor));
                            gridcell.setBackGroundStyle(GridEnums.getIntValue((Enum)GridEnums.BackgroundStyle.FILL));
                        } else if (extractDataLinkSet.contains(dataLinkDefine.getKey())) {
                            gridcell.setBackGroundColor(ColorUtil.mergeColor((int)gridcell.getBackGroundColor(), (int)extractColor));
                            gridcell.setBackGroundStyle(GridEnums.getIntValue((Enum)GridEnums.BackgroundStyle.FILL));
                        }
                    }
                    if (!info.isOnlyStyle()) {
                        dataReturn = dataLinkDefine.getStyle();
                        Object[] buffer = null;
                        if (rowCount != 0) {
                            buffer = null != versionDv && !lists.isEmpty() ? (Object[])lists.get(rowDataIndex) : floatDataRowSet.getBuffer(rowDataIndex);
                        }
                        Object floatData = null;
                        if (null != buffer) {
                            floatData = buffer[fieldIndex];
                        }
                        if (null != everageMap && null != everageVlueMap && everageVlueMap.containsKey(dataLinkDefine.getKey())) {
                            List valueList = (List)everageVlueMap.get(dataLinkDefine.getKey());
                            if (null != floatData) {
                                valueList.add(Double.valueOf(floatData.toString()));
                            } else {
                                valueList.add(0.0);
                            }
                        }
                        if ((dataLinkDefine.getType() == LinkType.LINK_TYPE_FILE.getValue() || dataLinkDefine.getType() == LinkType.LINK_TYPE_PICTURE.getValue()) && floatData != null && StringUtils.isNotEmpty((String)floatData.toString())) {
                            fileGroupKeys.add(floatData.toString());
                        }
                        if (dataLinkDefine.getType() == LinkType.LINK_TYPE_FILE.getValue() && floatData != null && StringUtils.isNotEmpty((String)floatData.toString())) {
                            floatData = "";
                        }
                        DataFormaterCache dataFormaterCache = new DataFormaterCache(context);
                        dataFormaterCache.setJsonData(true);
                        if (null != versionDv && !lists.isEmpty() && dataLinkDefine.getType() == LinkType.LINK_TYPE\uff3fENUM.getValue() && dataLinkDefine.getDataLinkType().equals((Object)DataLinkType.DATA_LINK_TYPE_FIELD) && null != fieldDefine.getEntityKey() && null != floatData && !floatData.equals("")) {
                            GradeCellInfo gradeCellInfo;
                            if (floatData.toString().equals("\u2014\u2014")) {
                                floatData = "";
                                gridcell.setShowText("\u2014\u2014");
                            }
                            Object value = dataLinkDefine.getFormatData(AbstractData.valueOf((String)floatData.toString()), dataFormaterCache);
                            if (dataLinkDefine instanceof EnumLinkData && StringUtils.isNotEmpty((String)floatData.toString()) && gradeCellMap.containsKey(dataLinkDefine.getZbid()) && (gradeCellInfo = (GradeCellInfo)gradeCellMap.get(dataLinkDefine.getZbid())) != null && gradeCellInfo.isTrim() && StringUtils.isNotEmpty((String)gradeCellInfo.getGradeStruct())) {
                                String gradeStruct = gradeCellInfo.getGradeStruct();
                                String[] gradeStructLevels = gradeStruct.split(";");
                                ArrayList levels = gradeCellInfo.getLevels();
                                if (regionDataSet.getRel().size() > rowDataIndex && info.isSumData()) {
                                    List rel = (List)regionDataSet.getRel().get(rowDataIndex);
                                    Integer rowDeep = (Integer)rel.get(2);
                                    if ((Integer)rel.get(4) == RegionGradeDataLoader.groupData && (levels.isEmpty() || levels.contains(rowDeep))) {
                                        int newLength = 0;
                                        for (int level = 0; level < rowDeep; ++level) {
                                            newLength += Integer.parseInt(gradeStructLevels[level]);
                                        }
                                        ObjectMapper mapper = new ObjectMapper();
                                        ArrayList jsonOfFormate = new ArrayList();
                                        try {
                                            List json = (List)mapper.readValue(value.toString(), (TypeReference)new TypeReference<List<Object>>(){});
                                            if (!json.isEmpty()) {
                                                for (Object one : json) {
                                                    String oneJson = mapper.writeValueAsString(one);
                                                    Map oneMap = (Map)mapper.readValue(oneJson, (TypeReference)new TypeReference<Map<String, Object>>(){});
                                                    String enumTitle = (String)oneMap.get("title");
                                                    String enumTitleTrim = enumTitle.substring(0, newLength);
                                                    oneMap.put("title", enumTitleTrim);
                                                    one = mapper.writeValueAsString((Object)oneMap);
                                                    jsonOfFormate.add(one);
                                                }
                                            }
                                            value = jsonOfFormate != null && jsonOfFormate.size() > 0 ? ((Object)jsonOfFormate).toString() : json.toString();
                                        }
                                        catch (JsonMappingException e) {
                                            throw new RuntimeException(e);
                                        }
                                        catch (JsonProcessingException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                }
                            }
                            floatData = value;
                        }
                        if (null != versionDv && dataLinkDefine.getType() == LinkType.LINK_TYPE_BOOLEAN.getValue()) {
                            floatData = null != floatData && floatData.equals("\u662f") ? "true" : "false";
                        }
                        if (info.isSumData() && floatData != null && (floatData.equals("\u2014\u2014") || floatData.equals("\u5408\u8ba1"))) {
                            gridcell.setShowText(floatData.toString());
                            gridcell.setEditText(floatData.toString());
                            gridcell.setHorzAlign(3);
                        } else {
                            Grid2DataSetValueUtil.converterFieldTypeToGridCellData(type.getValue(), dataReturn, gridcell, dataLinkDefine, floatData, gridData, info.getContext(), enumPosMap);
                        }
                        boolean isThousandPer = false;
                        if (!isThousandPeLinkMap.containsKey(dataLinkDefine.getKey())) {
                            DataLinkDefine dataLinkDefineInfo = this.controller.queryDataLinkDefine(dataLinkDefine.getKey());
                            NumberFormatParser numberFormatParser = NumberFormatParser.parse((FormatProperties)dataLinkDefineInfo.getFormatProperties());
                            if (numberFormatParser.isThousandPer()) {
                                isThousandPer = true;
                            }
                            isThousandPeLinkMap.put(dataLinkDefine.getKey(), isThousandPer);
                        } else {
                            isThousandPer = (Boolean)isThousandPeLinkMap.get(dataLinkDefine.getKey());
                        }
                        if (!isThousandPer) continue;
                        if (StringUtils.isNotEmpty((String)gridcell.getEditText())) {
                            int scale = 0;
                            if (gridcell.getFormatter().contains(".")) {
                                BigDecimal bigDecimal = new BigDecimal(gridcell.getFormatter().replace("\u2030", ""));
                                scale = bigDecimal.scale();
                            }
                            BigDecimal bigDecimalOfEditText = new BigDecimal(gridcell.getEditText()).multiply(new BigDecimal(1000));
                            String showText = this.decimalTrans(bigDecimalOfEditText.toPlainString(), scale).toPlainString() + '\u2030';
                            gridcell.setShowText(showText);
                            gridcell.setEditText(showText);
                        } else {
                            gridcell.setShowText("");
                            gridcell.setEditText("");
                        }
                        gridcell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Text));
                        continue;
                    }
                    dataReturn = dataLinkDefine.getStyle();
                    Grid2DataSetValueUtil.converterFieldTypeToGridCellData(type.getValue(), dataReturn, gridcell, dataLinkDefine, "", gridData, info.getContext(), enumPosMap);
                }
                if (numberManager != null) {
                    if (floatDataRowSet.size() == 0 || versionDv != null && !lists.isEmpty()) {
                        numberManager.setNumberStr("");
                    }
                    if (null != numberManager.getRegionNumber()) {
                        GridCellData gridcell3 = gridData.getGridCellData(numberManager.getRegionNumber().getColumn(), lineInfo - moreRow);
                        FieldType type = FieldType.FIELD_TYPE_STRING;
                        if (gridcell3 != null) {
                            Grid2DataSetValueUtil.converterFieldTypeToGridCellData(type.getValue(), "10000020000 01,", gridcell3, null, numberManager.next(), gridData, info.getContext(), enumPosMap);
                        }
                    }
                }
                ++rowDataIndex;
                ++lineInfo;
            }
            RunTimeExtentStyleService extentStyleService = (RunTimeExtentStyleService)BeanUtil.getBean(RunTimeExtentStyleService.class);
            ExtentStyle extentStyle = extentStyleService.getExtentStyle(floatRegionDataSet.getRegionData().getKey());
            if (extentStyle != null && (merges = (tempGridData = extentStyle.getGriddata()).merges()).count() > 0) {
                int regionLeft = floatRegionDataSet.getRegionData().getRegionLeft();
                for (int i3 = 0; i3 < merges.count(); ++i3) {
                    Grid2CellField cell = merges.get(i3);
                    gridData.merges().addMergeRect(merges.get(i3));
                    for (int j = cell.left; j <= cell.right; ++j) {
                        for (int h = cell.top; h <= cell.bottom; ++h) {
                            int row = h + regionTop - 1;
                            int col = j + regionLeft - 1;
                            GridCellData cellData = gridData.getGridCellData(col, row);
                            GridCellData tempCellData = tempGridData.getGridCellData(j, h);
                            String posStr = this.getPosition(col, row);
                            if (!linkCells.contains(posStr)) {
                                cellData.setShowText(StringUtils.isNotEmpty((String)tempCellData.getShowText()) ? tempCellData.getShowText() : "");
                                cellData.setEditText(StringUtils.isNotEmpty((String)tempCellData.getEditText()) ? tempCellData.getEditText() : "");
                            }
                            GridCellStyleData cellStyleData = tempCellData.getCellStyleData();
                            cellData.setCellStyleData(cellStyleData);
                        }
                    }
                }
            }
            int rowSize = 0;
            rowSize = null != versionDv && !lists.isEmpty() ? lists.size() : floatDataRowSet.size();
            if (page == 0 && rowSize == 0 && !info.isOnlyStyle()) {
                --lineInfo;
                deleteRow = false;
            }
            ++page;
            pagerInfo.setOffset(++offset);
            if (floatDataRowSet.size() != 0) continue;
            pagerInfo.setOffset(++offset);
            break;
        }
        if (offset * pagerInfo.getLimit() + pagerInfo.getLimit() >= floatRegionDataSet.getTotalCount()) {
            pagerInfo.setTotal(Integer.MAX_VALUE);
        }
        if (emptyTable && !info.isExportEmptyTable()) {
            return -1;
        }
        if (pagerInfo.getOffset() * pagerInfo.getLimit() >= pagerInfo.getTotal()) {
            pagerInfo.setTotal(0);
        }
        if (moreRow > 0 && !info.isOnlyStyle() && deleteRow) {
            gridData.deleteRows(lineInfo, moreRow + 1);
            gridData.insertRows(lineInfo, 1);
            Grid2FieldList merges = gridData.merges();
            ArrayList<Grid2CellField> removeList = new ArrayList<Grid2CellField>();
            for (i = 0; i < merges.count(); ++i) {
                Grid2CellField grid2CellField = merges.get(i);
                if (null == grid2CellField || grid2CellField.top < grid2CellField.bottom || grid2CellField.left < grid2CellField.right) continue;
                removeList.add(grid2CellField);
            }
            for (Grid2CellField grid2CellField : removeList) {
                merges.remove(grid2CellField);
            }
        }
        if (sheetCount > 0 && !info.isOnlyStyle()) {
            GridCellData gridCellData2;
            int i4;
            boolean confirm;
            List tabs = floatRegionDataSet.getRegionData().getTabs();
            List<String> tabTitles = null;
            if (info.isExportAllLable()) {
                tabTitles = new ArrayList<String>();
                for (RegionTab regionTab : tabs) {
                    tabTitles.add(regionTab.getTitle());
                }
            } else {
                tabTitles = info.getTabs();
            }
            GridCellData gridCellData = gridData.getGridCellData(1, gridData.getRowCount() - 1);
            boolean deleteLastRow = false;
            if (tabTitles != null && tabTitles.size() == 1 && currentTab != null) {
                if (tabTitles.get(0).equals(((RegionTab)tabs.get(tabTitles.size() - 1)).getTitle()) || tabTitles.get(0).equals(currentTab)) {
                    deleteLastRow = true;
                }
            } else if (tabTitles != null && tabTitles.size() > 1 && currentTab != null) {
                if (tabTitles.get(tabTitles.size() - 1).equals(currentTab)) {
                    deleteLastRow = true;
                }
            } else if (currentTab == null) {
                deleteLastRow = true;
            }
            if (tabs == null || tabs.isEmpty() || deleteLastRow) {
                gridData.deleteRows(lineInfo, 1);
                --lineInfo;
            } else if (currentTab != null && currentTab.equals(((RegionTab)tabs.get(tabs.size() - 1)).getTitle())) {
                confirm = true;
                for (i4 = 0; i4 < gridData.getColumnCount(); ++i4) {
                    gridCellData2 = gridData.getGridCellData(i4, gridData.getRowCount() - 2);
                    if (gridCellData2.getEditText() == null || gridCellData2.getEditText().equals("")) continue;
                    confirm = false;
                }
                if (confirm) {
                    gridData.deleteRows(lineInfo, 1);
                    --lineInfo;
                }
            } else if (gridData.getRowCount() > 2 && tabs != null && tabs.isEmpty() && gridData.getGridCellData(1, gridData.getRowCount() - 2).getEditText().equals("") && currentTab == null) {
                gridData.deleteRows(lineInfo, 1);
                --lineInfo;
            } else if (deleteLastRow && gridData.getRowCount() > 2 && !tabs.isEmpty() && gridCellData.getEditText() != null && !gridCellData.getEditText().equals("")) {
                confirm = true;
                for (i4 = 0; i4 < gridData.getColumnCount(); ++i4) {
                    gridCellData2 = gridData.getGridCellData(i4, gridData.getRowCount() - 2);
                    if (gridCellData2.getEditText() == null || gridCellData2.getEditText().equals("")) continue;
                    confirm = false;
                }
                if (confirm) {
                    gridData.deleteRows(lineInfo, 1);
                    --lineInfo;
                }
            }
        }
        if (info.isOnlyStyle()) {
            --lineInfo;
        }
        this.addExcelFilters(info, floatRegionDataSet, lineInfo, filters, conditions, filterRegionCondition, everageMap, everageVlueMap, everageColMap, beginRow);
        if (moreRow > 0) {
            lineInfo -= moreRow;
        }
        return lineInfo;
    }

    private Map<String, String> getEnumPosMap(List<LinkData> linkDataList) {
        HashMap<String, String> enumPosMap = new HashMap<String, String>();
        for (LinkData linkData : linkDataList) {
            EnumLinkData enumLinkData;
            if (!(linkData instanceof EnumLinkData) || (enumLinkData = (EnumLinkData)linkData).getEnumFieldPosMap() == null || enumLinkData.getEnumFieldPosMap().size() <= 0) continue;
            for (Map.Entry enumPair : enumLinkData.getEnumFieldPosMap().entrySet()) {
                String enumPos = this.getPosStr((String)enumPair.getValue());
                enumPosMap.put(enumPos, (String)enumPair.getValue());
            }
        }
        for (LinkData linkData : linkDataList) {
            String position = this.getPosition(linkData.getCol(), linkData.getRow());
            enumPosMap.remove(position);
        }
        return enumPosMap;
    }

    public String getPosStr(String position) {
        String[] rows;
        String[] englishs = position.split("\\d");
        String english = "";
        for (String n : englishs) {
            english = english + n;
        }
        String relationRowString = "";
        for (String r : rows = position.split("\\D")) {
            relationRowString = relationRowString + r;
        }
        int relationRow = Integer.valueOf(relationRowString);
        int relationCol = Grid2DataSetValueUtil.excelColStrToNum(english, english.length());
        return this.getPosition(relationCol, relationRow);
    }

    private String getPosition(int col, int row) {
        String posStr = String.valueOf(row);
        return posStr.concat("_").concat(String.valueOf(col));
    }

    private boolean isEmptyTable(Object[] buffer) {
        if (buffer == null) {
            return true;
        }
        boolean emptyTable = true;
        for (Object fixData : buffer) {
            if (!emptyTable) break;
            if (fixData instanceof String) {
                emptyTable = StringUtils.isEmpty((String)fixData.toString());
                continue;
            }
            if (fixData == null) continue;
            emptyTable = false;
        }
        return emptyTable;
    }

    private int setDataMethod2(IExportFacade info, Set<String> collectLinks, Set<String> extractDataLinkSet, IRegionExportDataSet floatRegionDataSet, int lineInfo, List<FieldData> floatFieldDefines, Grid2Data gridData, RegionNumberManager numberManager, int tempLine, SXSSFSheet sheet, Map<String, Integer> addRows, List<FilterRegionCondition> filters, PagerInfo pagerInfo, List<String> fileGroupKeys) {
        Map<String, List<CellQueryInfo>> conditions = info.getConditions();
        RegionDataSet queryRegionData = null;
        HashSet<String> canSeeList = null;
        FilterRegionCondition filterRegionCondition = null;
        HashMap<String, Integer> everageMap = null;
        HashMap everageVlueMap = null;
        HashMap<String, String> everageColMap = null;
        HashMap<String, Boolean> isThousandPeLinkMap = new HashMap<String, Boolean>();
        if (null != conditions && conditions.size() > 0 && !info.isOnlyStyle()) {
            Set<Map.Entry<String, List<CellQueryInfo>>> entrySet = conditions.entrySet();
            for (Map.Entry<String, List<CellQueryInfo>> entry : entrySet) {
                String key = floatRegionDataSet.getRegionData().getKey();
                if (!key.equals(entry.getKey())) continue;
                RegionQueryInfo queryInfo = new RegionQueryInfo();
                queryInfo.getFilterInfo().setCellQuerys(entry.getValue());
                queryInfo.setContext(info.getContext());
                queryInfo.setRegionKey(entry.getKey());
                queryRegionData = this.jtableResourceService.queryRegionData(queryInfo);
                List data = queryRegionData.getData();
                canSeeList = new HashSet<String>();
                for (List row : data) {
                    canSeeList.add((String)row.get(0));
                }
                filterRegionCondition = new FilterRegionCondition();
                List linkDataList = floatRegionDataSet.getLinkDataList();
                Map<String, LinkData> linkDataMap = linkDataList.stream().collect(Collectors.toMap(LinkData::getKey, LinkData2 -> LinkData2));
                List<CellQueryInfo> cellQueryInfos = entry.getValue();
                for (CellQueryInfo cellQueryInfo : cellQueryInfos) {
                    FilterColCondition colCondition;
                    ArrayList<FilterColCondition> filterCols = new ArrayList<FilterColCondition>();
                    String cellKey = cellQueryInfo.getCellKey();
                    LinkData linkData = linkDataMap.get(cellKey);
                    int col = linkData.getCol();
                    String colString = CellReference.convertNumToColString(col - 1);
                    List opList = cellQueryInfo.getOpList();
                    List inList = cellQueryInfo.getInList();
                    String shortcuts = cellQueryInfo.getShortcuts();
                    if (null != inList && inList.size() > 0) {
                        for (String value : inList) {
                            colCondition = new FilterColCondition();
                            value = this.handSelect(floatRegionDataSet, linkData, value);
                            colCondition.setValue(value);
                            colCondition.setFilterOperator(FilterOperator.EQUAL);
                            filterCols.add(colCondition);
                        }
                    } else if (null != shortcuts && !"".equals(shortcuts)) {
                        if (null == everageMap) {
                            everageMap = new HashMap<String, Integer>();
                        }
                        if (null == everageVlueMap) {
                            everageVlueMap = new HashMap();
                        }
                        if (null == everageColMap) {
                            everageColMap = new HashMap<String, String>();
                        }
                        FilterColCondition colCondition2 = new FilterColCondition();
                        if ("moreThanEverage".equals(shortcuts)) {
                            colCondition2.setFilterOperator(FilterOperator.ABOVE_AVERAGE);
                        } else if ("lessThanEverage".equals(shortcuts)) {
                            colCondition2.setFilterOperator(FilterOperator.BELOW_AVERAGE);
                        } else if ("topTen;10".equals(shortcuts)) {
                            colCondition2.setFilterOperator(FilterOperator.TOP10);
                        }
                        filterCols.add(colCondition2);
                        int indexOf = filterCols.indexOf(colCondition2);
                        everageMap.put(cellKey, indexOf);
                        everageVlueMap.put(cellKey, new ArrayList());
                        everageColMap.put(cellKey, colString);
                    } else if (null != opList && opList.size() > 0) {
                        for (FilterCondition filterCondition : opList) {
                            colCondition = new FilterColCondition();
                            colCondition.setAnd(Boolean.valueOf("and".equals(cellQueryInfo.getAttendedMode())));
                            colCondition.setValue(filterCondition.getOpValue());
                            if ("eq".equals(filterCondition.getOpCode())) {
                                colCondition.setFilterOperator(FilterOperator.EQUAL);
                            } else if ("noteq".equals(filterCondition.getOpCode())) {
                                colCondition.setFilterOperator(FilterOperator.NOT_EQUAL);
                            } else if ("more".equals(filterCondition.getOpCode())) {
                                colCondition.setFilterOperator(FilterOperator.GREATER_THAN);
                            } else if ("less".equals(filterCondition.getOpCode())) {
                                colCondition.setFilterOperator(FilterOperator.LESS_THAN);
                            } else if ("notless".equals(filterCondition.getOpCode())) {
                                colCondition.setFilterOperator(FilterOperator.GREATER_THAN_OR_EQUAL);
                            } else if ("notmore".equals(filterCondition.getOpCode())) {
                                colCondition.setFilterOperator(FilterOperator.LESS_THAN_OR_EQUAL);
                            } else if ("contain".equals(filterCondition.getOpCode())) {
                                colCondition.setFilterOperator(FilterOperator.CONTAIN);
                            } else if ("notcontain".equals(filterCondition.getOpCode())) {
                                colCondition.setFilterOperator(FilterOperator.NOT_CONTAIN);
                            }
                            filterCols.add(colCondition);
                        }
                    }
                    String sort = cellQueryInfo.getSort();
                    if (!StringUtils.isEmpty((String)sort)) {
                        filterRegionCondition.setSortCol(colString);
                        filterRegionCondition.setIsAsc("asc".equals(cellQueryInfo.getSort()));
                    }
                    if (filterCols.size() <= 0) continue;
                    filterRegionCondition.addColFilterCondition(colString, filterCols);
                }
            }
        }
        int calcColor = ColorUtil.htmlColorToInt((String)"#D6F6EF");
        int extractColor = ColorUtil.htmlColorToInt((String)"#FBEEC4");
        int page = 0;
        int beginRow = lineInfo;
        int regionLeft = floatRegionDataSet.getRegionData().getRegionLeft();
        int regionRight = floatRegionDataSet.getRegionData().getRegionRight();
        int moreCol = regionRight - regionLeft;
        boolean deleteRow = true;
        JtableContext context = info.getContext();
        Map dimensionSet = context.getDimensionSet();
        String versionDv = null;
        if (!(!dimensionSet.containsKey("VERSIONID")) && !((DimensionValue)dimensionSet.get("VERSIONID")).getValue().equals("00000000-0000-0000-0000-000000000000")) {
            versionDv = ((DimensionValue)dimensionSet.get("VERSIONID")).getValue();
        }
        List<Object> lists = new ArrayList();
        if (null != versionDv) {
            lists = this.queryVersionDatas(info, floatFieldDefines);
        }
        if (info.getContext().getDimensionSet().containsKey("DATASNAPSHOTID")) {
            RegionDataSet regionDataSet;
            String snapshotID = ((DimensionValue)info.getContext().getDimensionSet().get("DATASNAPSHOTID")).getValue();
            RegionQueryInfo regionQueryInfo = floatRegionDataSet.getRegionQueryInfo();
            regionQueryInfo.setRegionKey(floatRegionDataSet.getRegionData().getKey());
            if (regionQueryInfo.getPagerInfo().getOffset() < 0) {
                regionQueryInfo.getPagerInfo().setOffset(0);
            }
            if ((lists = this.querySnapshotDatas(regionDataSet = this.snapshotDataQueryService.queryRegionDatas(regionQueryInfo, info.isSumData()), floatFieldDefines, true)) == null && !info.isExportEmptyTable()) {
                return -1;
            }
            versionDv = snapshotID;
        }
        boolean emptyTable = true;
        while (floatRegionDataSet.hasNext()) {
            MemoryDataSet floatDataRowSet = (MemoryDataSet)floatRegionDataSet.next();
            int rowEmCount = floatDataRowSet.size();
            if (emptyTable) {
                for (int i = 0; i < rowEmCount; ++i) {
                    Object[] buffer = floatDataRowSet.getBuffer(i);
                    emptyTable = this.isEmptyTable(buffer);
                }
            }
            int dataRowCount = floatDataRowSet.size();
            if (!CollectionUtils.isEmpty(lists)) {
                dataRowCount = lists.size();
            }
            int rowCount = dataRowCount;
            int gridAddRows = dataRowCount * (moreCol + 1);
            if (dataRowCount > 0) {
                if (!info.isOnlyStyle()) {
                    int headerRowCount = gridData.getHeaderRowCount();
                    int rowIndex = 1;
                    GridCellData gridCellData = gridData.getGridCellData(lineInfo, rowIndex);
                    while (gridCellData != null && (gridCellData.getShowText() == null || gridCellData.getShowText().equals("")) && rowIndex < gridData.getRowCount() && rowIndex < 5) {
                        gridCellData = gridData.getGridCellData(lineInfo, ++rowIndex);
                    }
                    gridData.insertColumns(lineInfo, dataRowCount - 1, lineInfo);
                    for (int i = 0; i < dataRowCount; ++i) {
                        gridData.getGridCellData(lineInfo + i, rowIndex).setShowText(gridCellData.getShowText());
                    }
                    if (lineInfo == headerRowCount) {
                        gridData.setHeaderRowCount(headerRowCount);
                    }
                    Grid2FieldList merges = gridData.merges();
                    int gridDataShowRow = moreCol + 1;
                    for (int x = 0; x < gridDataShowRow; ++x) {
                        int tempLineInfo = lineInfo + x;
                        int y = 0;
                        while (tempLineInfo < lineInfo + gridAddRows) {
                            for (int col = 0; col < floatFieldDefines.size(); ++col) {
                                String showText;
                                GridCellData gridcell = gridData.getGridCellData(lineInfo, col);
                                if (moreCol > 0 && gridcell.isMerged()) {
                                    Point mergeInfo = gridcell.getMergeInfo();
                                    int left = mergeInfo.x - gridAddRows + y * (moreCol + 1);
                                    int bottom = gridcell.getColIndex() - gridAddRows + y * (moreCol + 1);
                                    if (null != mergeInfo && bottom - left > 1) {
                                        merges.addMergeRect(new Grid2CellField(left, mergeInfo.y, gridcell.getColIndex(), bottom));
                                    }
                                }
                                if (null != (showText = gridcell.getShowText()) && !"".equals(showText) && null == (gridcell = gridData.getGridCellData(col, tempLineInfo))) continue;
                            }
                            tempLineInfo = tempLineInfo + 1 + moreCol;
                            ++y;
                        }
                    }
                } else {
                    if (page != 0) break;
                    dataRowCount = 1;
                }
            } else {
                if (page != 0) break;
                dataRowCount = 1;
                for (int fieldIndex = 0; fieldIndex < floatFieldDefines.size(); ++fieldIndex) {
                    FieldData fieldDefine = floatFieldDefines.get(fieldIndex);
                    LinkData dataLinkDefine = floatRegionDataSet.getLinkDataByField(fieldDefine);
                    GridCellData gridcell = gridData.getGridCellData(dataLinkDefine.getCol(), lineInfo);
                    if (null == gridcell) continue;
                    gridcell.setShowText("");
                    gridcell.setEditText("");
                }
            }
            if (!info.isOnlyStyle()) {
                addRows.put(floatRegionDataSet.getRegionData().getKey(), dataRowCount * (moreCol + 1));
            }
            Map<String, String> enumPosMap = this.getEnumPosMap(floatRegionDataSet.getLinkDataList());
            int rowNum = lineInfo;
            int rowDataIndex = 0;
            while (rowDataIndex < dataRowCount) {
                Object[] buffer;
                String id;
                if (!(null == queryRegionData || null == canSeeList || info.isOnlyStyle() || CollectionUtils.isEmpty(lists) || canSeeList.contains(id = (String)(buffer = (Object[])lists.get(rowDataIndex))[buffer.length - 1]))) {
                    gridData.setRowHidden(lineInfo, true);
                }
                for (int fieldIndex = 0; fieldIndex < floatFieldDefines.size(); ++fieldIndex) {
                    FieldData fieldDefine = floatFieldDefines.get(fieldIndex);
                    LinkData dataLinkDefine = floatRegionDataSet.getLinkDataByField(fieldDefine);
                    if (dataLinkDefine == null) continue;
                    lineInfo = dataLinkDefine.getCol() + rowDataIndex * (moreCol + 1) + tempLine + page * pagerInfo.getLimit() * (moreCol + 1);
                    GridCellData gridcell = gridData.getGridCellData(rowNum, dataLinkDefine.getRow());
                    FieldType type = FieldType.forValue((int)fieldDefine.getFieldType());
                    if (gridcell == null) continue;
                    if (info.isArithmeticBackground()) {
                        if (collectLinks.contains(dataLinkDefine.getKey())) {
                            gridcell.setBackGroundColor(ColorUtil.mergeColor((int)gridcell.getBackGroundColor(), (int)calcColor));
                            gridcell.setBackGroundStyle(GridEnums.getIntValue((Enum)GridEnums.BackgroundStyle.FILL));
                        } else if (extractDataLinkSet.contains(dataLinkDefine.getKey())) {
                            gridcell.setBackGroundColor(ColorUtil.mergeColor((int)gridcell.getBackGroundColor(), (int)extractColor));
                            gridcell.setBackGroundStyle(GridEnums.getIntValue((Enum)GridEnums.BackgroundStyle.FILL));
                        }
                    }
                    if (info.isOnlyStyle()) continue;
                    String dataReturn = dataLinkDefine.getStyle();
                    Object[] buffer2 = null;
                    if (rowCount != 0) {
                        buffer2 = null != versionDv && !CollectionUtils.isEmpty(lists) ? (Object[])lists.get(rowDataIndex) : floatDataRowSet.getBuffer(rowDataIndex);
                    }
                    Object floatData = null;
                    if (null != buffer2) {
                        floatData = buffer2[fieldIndex];
                    }
                    if (null != everageMap && null != everageVlueMap && everageVlueMap.containsKey(dataLinkDefine.getKey())) {
                        List valueList = (List)everageVlueMap.get(dataLinkDefine.getKey());
                        if (null != floatData) {
                            valueList.add(Double.valueOf(floatData.toString()));
                        } else {
                            valueList.add(0.0);
                        }
                    }
                    if ((dataLinkDefine.getType() == LinkType.LINK_TYPE_FILE.getValue() || dataLinkDefine.getType() == LinkType.LINK_TYPE_PICTURE.getValue()) && floatData != null && StringUtils.isNotEmpty((String)floatData.toString())) {
                        fileGroupKeys.add(floatData.toString());
                    }
                    if (dataLinkDefine.getType() == LinkType.LINK_TYPE_FILE.getValue() && floatData != null && StringUtils.isNotEmpty((String)floatData.toString())) {
                        floatData = "";
                    }
                    Grid2DataSetValueUtil.converterFieldTypeToGridCellData(type.getValue(), dataReturn, gridcell, dataLinkDefine, floatData, gridData, info.getContext(), enumPosMap);
                    boolean isThousandPer = false;
                    if (!isThousandPeLinkMap.containsKey(dataLinkDefine.getKey())) {
                        DataLinkDefine dataLinkDefineInfo = this.controller.queryDataLinkDefine(dataLinkDefine.getKey());
                        NumberFormatParser numberFormatParser = NumberFormatParser.parse((FormatProperties)dataLinkDefineInfo.getFormatProperties());
                        if (numberFormatParser.isThousandPer()) {
                            isThousandPer = true;
                        }
                        isThousandPeLinkMap.put(dataLinkDefine.getKey(), isThousandPer);
                    } else {
                        isThousandPer = (Boolean)isThousandPeLinkMap.get(dataLinkDefine.getKey());
                    }
                    if (!isThousandPer) continue;
                    if (StringUtils.isNotEmpty((String)gridcell.getEditText())) {
                        int scale = 0;
                        if (gridcell.getFormatter().contains(".")) {
                            BigDecimal bigDecimal = new BigDecimal(gridcell.getFormatter().replace("\u2030", ""));
                            scale = bigDecimal.scale();
                        }
                        BigDecimal bigDecimalOfEditText = new BigDecimal(gridcell.getEditText()).multiply(new BigDecimal(1000));
                        String showText = this.decimalTrans(bigDecimalOfEditText.toPlainString(), scale).toPlainString() + '\u2030';
                        gridcell.setShowText(showText);
                        gridcell.setEditText(showText);
                    } else {
                        gridcell.setShowText("");
                        gridcell.setEditText("");
                    }
                    gridcell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Text));
                }
                if (numberManager != null && null != numberManager.getRegionNumber()) {
                    GridCellData gridcell = gridData.getGridCellData(lineInfo, numberManager.getRegionNumber().getRow());
                    FieldType type = FieldType.FIELD_TYPE_STRING;
                    if (gridcell != null) {
                        Grid2DataSetValueUtil.converterFieldTypeToGridCellData(type.getValue(), "10000020000 01,", gridcell, null, numberManager.next(), gridData, info.getContext(), enumPosMap);
                    }
                }
                ++rowNum;
                ++rowDataIndex;
                ++lineInfo;
            }
            int rowSize = lists.size();
            if (page == 0 && rowSize == 0 && !info.isOnlyStyle()) {
                --lineInfo;
                deleteRow = false;
            }
            ++page;
        }
        if (emptyTable && !info.isExportEmptyTable()) {
            return -1;
        }
        if (pagerInfo.getOffset() * pagerInfo.getLimit() >= pagerInfo.getTotal()) {
            pagerInfo.setTotal(0);
        }
        if (moreCol > 0 && !info.isOnlyStyle() && deleteRow) {
            gridData.deleteColumns(lineInfo, moreCol + 1);
            Grid2FieldList merges = gridData.merges();
            ArrayList<Grid2CellField> removeList = new ArrayList<Grid2CellField>();
            for (int i = 0; i < merges.count(); ++i) {
                Grid2CellField grid2CellField = merges.get(i);
                if (null == grid2CellField || grid2CellField.top < grid2CellField.bottom || grid2CellField.left < grid2CellField.right) continue;
                removeList.add(grid2CellField);
            }
            for (Grid2CellField grid2CellField : removeList) {
                merges.remove(grid2CellField);
            }
        }
        this.addExcelFilters(info, floatRegionDataSet, lineInfo, filters, conditions, filterRegionCondition, everageMap, everageVlueMap, everageColMap, beginRow);
        return lineInfo;
    }

    private String handSelect(IRegionExportDataSet floatRegionDataSet, LinkData linkData, String value) {
        if (linkData instanceof EnumLinkData) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                String entityShow = "";
                Map[] resMaps = (Map[])mapper.readValue(value, Map[].class);
                if (resMaps.length > 1) {
                    for (Map oneMap : resMaps) {
                        entityShow = entityShow + oneMap.get("title") + ";";
                    }
                    if (entityShow.endsWith(";")) {
                        entityShow = entityShow.substring(0, entityShow.length() - 1);
                    }
                } else {
                    for (Map oneMap : resMaps) {
                        entityShow = (String)oneMap.get("title");
                    }
                }
                value = entityShow;
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
        return value;
    }

    private void addExcelFilters(IExportFacade info, IRegionExportDataSet floatRegionDataSet, int lineInfo, List<FilterRegionCondition> filters, Map<String, List<CellQueryInfo>> conditions, FilterRegionCondition filterRegionCondition, Map<String, Integer> everageMap, Map<String, List<Double>> everageVlueMap, Map<String, String> everageColMap, int beginRow) {
        if (null != conditions && conditions.size() > 0 && !info.isOnlyStyle()) {
            int regionLeft = floatRegionDataSet.getRegionData().getRegionLeft();
            int regionRight = floatRegionDataSet.getRegionData().getRegionRight();
            int regionTop = beginRow - 1;
            int regionBottom = lineInfo - 1;
            String colLeft = CellReference.convertNumToColString(regionLeft - 1);
            colLeft = colLeft + regionTop;
            String colRight = CellReference.convertNumToColString(regionRight - 1);
            colRight = colRight + regionBottom;
            filterRegionCondition.setRegion(colLeft + ":" + colRight);
            if (null != everageMap && null != everageVlueMap && null != everageColMap) {
                Map filterColConditions = filterRegionCondition.getFilterCols();
                for (Map.Entry<String, String> enerageColEntry : everageColMap.entrySet()) {
                    String cellKey = enerageColEntry.getKey();
                    String colName = enerageColEntry.getValue();
                    List filterCols = (List)filterColConditions.get(colName);
                    Integer index = everageMap.get(cellKey);
                    List<Double> valuelist = everageVlueMap.get(cellKey);
                    FilterColCondition filterColCondition = (FilterColCondition)filterCols.get(index);
                    FilterOperator filterOperator = filterColCondition.getFilterOperator();
                    if (filterOperator == FilterOperator.ABOVE_AVERAGE || filterOperator == FilterOperator.BELOW_AVERAGE) {
                        double avgAge = valuelist.stream().mapToDouble(Double::doubleValue).average().getAsDouble();
                        filterColCondition.setValue(avgAge + "");
                        continue;
                    }
                    if (filterOperator != FilterOperator.TOP10) continue;
                    Collections.sort(valuelist);
                    if (valuelist.size() <= 10) {
                        filterColCondition.setValue(valuelist.get(0).toString());
                        continue;
                    }
                    filterColCondition.setValue(valuelist.get(valuelist.size() - 10).toString());
                }
            }
            if (null != filters) {
                filters.add(filterRegionCondition);
            }
        }
    }
}

