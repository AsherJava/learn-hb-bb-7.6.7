/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.office.excel.WorkbookContext
 *  com.jiuqi.bi.office.excel.WorksheetWriterEx
 *  com.jiuqi.bi.office.excel.print.PaperSize
 *  com.jiuqi.bi.office.excel.print.PrintSetting
 *  com.jiuqi.bi.office.excel.print.Zoom
 *  com.jiuqi.bi.quickreport.model.QuickReport
 *  com.jiuqi.bi.quickreport.model.WorksheetModel
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.definitions.FormulaCallBack
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IExpressionEvaluator
 *  com.jiuqi.np.dataengine.intf.IFieldValueUpdateProcessor
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IFormulaRunner
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.definition.common.FormulaCheckType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.grid.Font
 *  com.jiuqi.np.office.excel2.WorksheetWriter2
 *  com.jiuqi.np.office.excel2.filter.FilterColCondition
 *  com.jiuqi.np.office.excel2.filter.FilterOperator
 *  com.jiuqi.np.office.excel2.filter.FilterRegionCondition
 *  com.jiuqi.np.office.excel2.group.GroupDirection
 *  com.jiuqi.np.office.excel2.label.ExcelLabel
 *  com.jiuqi.np.office.excel2.link.CellLink
 *  com.jiuqi.np.office.excel2.print.IExcelPrintSetup
 *  com.jiuqi.np.util.ColorUtil
 *  com.jiuqi.nr.common.params.PagerInfo
 *  com.jiuqi.nr.conditionalstyle.controller.IConditionalStyleController
 *  com.jiuqi.nr.conditionalstyle.facade.ConditionalStyle
 *  com.jiuqi.nr.data.logic.internal.util.FileFieldValueProcessor
 *  com.jiuqi.nr.datacrud.IDataValue
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.IQueryInfo
 *  com.jiuqi.nr.datacrud.IRegionDataSet
 *  com.jiuqi.nr.datacrud.IRowData
 *  com.jiuqi.nr.datacrud.LinkSort
 *  com.jiuqi.nr.datacrud.MultiDimensionalDataSet
 *  com.jiuqi.nr.datacrud.PageInfo
 *  com.jiuqi.nr.datacrud.QueryInfoBuilder
 *  com.jiuqi.nr.datacrud.RegionGradeInfo
 *  com.jiuqi.nr.datacrud.impl.RegionRelation
 *  com.jiuqi.nr.datacrud.impl.RegionRelationFactory
 *  com.jiuqi.nr.datacrud.spi.RowFilter
 *  com.jiuqi.nr.datacrud.spi.filter.FormulaFilter
 *  com.jiuqi.nr.datareport.obj.RegionNumber
 *  com.jiuqi.nr.datareport.obj.RegionNumberManager
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 *  com.jiuqi.nr.definition.common.DataLinkEditMode
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormFoldingDirEnum
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IPrintRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.BigDataDefine
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.PrintTemplateDefine
 *  com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.facade.RegionSettingDefine
 *  com.jiuqi.nr.definition.facade.RegionTabSettingDefine
 *  com.jiuqi.nr.definition.facade.RowNumberSetting
 *  com.jiuqi.nr.definition.facade.print.PrintSchemeAttributeDefine
 *  com.jiuqi.nr.definition.facade.print.PrintTemplateAttributeDefine
 *  com.jiuqi.nr.definition.facade.print.WordLabelDefine
 *  com.jiuqi.nr.definition.facade.print.common.define.DefaultPageNumberGenerateStrategy
 *  com.jiuqi.nr.definition.facade.print.common.define.IPageNumberGenerateStrategy
 *  com.jiuqi.nr.definition.facade.print.common.other.PrintUtil
 *  com.jiuqi.nr.definition.facade.print.common.param.IPrintParamBase
 *  com.jiuqi.nr.definition.facade.print.common.parse.ParseContext
 *  com.jiuqi.nr.definition.facade.print.common.parse.WordLabelParseExecuter
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.internal.service.RunTimeExtentStyleService
 *  com.jiuqi.nr.definition.print.service.IPrintLabelService
 *  com.jiuqi.nr.definition.util.ExtentStyle
 *  com.jiuqi.nr.definition.util.GridDataTransform
 *  com.jiuqi.nr.io.service.IOEntityIsolateCondition
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.grid2.Grid2CellField
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.Grid2FieldList
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.GridCellStyleData
 *  com.jiuqi.nvwa.grid2.GridEnums
 *  com.jiuqi.nvwa.grid2.GridEnums$DataType
 *  com.jiuqi.nvwa.grid2.GridEnums$GridBorderStyle
 *  com.jiuqi.nvwa.grid2.GridEnums$TextAlignment
 *  com.jiuqi.nvwa.grid2.json.Grid2DataConst$Cell_MODE
 *  com.jiuqi.nvwa.quickreport.api.query.Wrapper
 *  com.jiuqi.nvwa.quickreport.api.query.option.Option
 *  com.jiuqi.nvwa.quickreport.common.NvwaQuickReportException
 *  com.jiuqi.nvwa.quickreport.dto.ReportData
 *  com.jiuqi.nvwa.quickreport.service.QuickReportModelService
 *  com.jiuqi.nvwa.quickreport.util.QuickReportConverter
 *  com.jiuqi.nvwa.quickreport.web.query.GridController
 *  com.jiuqi.nvwa.quickreport.web.query.SheetGridData
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  org.jetbrains.annotations.NotNull
 *  org.json.JSONObject
 */
package com.jiuqi.nr.data.excel.service.impl;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.office.excel.WorkbookContext;
import com.jiuqi.bi.office.excel.WorksheetWriterEx;
import com.jiuqi.bi.office.excel.print.PaperSize;
import com.jiuqi.bi.office.excel.print.PrintSetting;
import com.jiuqi.bi.office.excel.print.Zoom;
import com.jiuqi.bi.quickreport.model.QuickReport;
import com.jiuqi.bi.quickreport.model.WorksheetModel;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.definitions.FormulaCallBack;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IExpressionEvaluator;
import com.jiuqi.np.dataengine.intf.IFieldValueUpdateProcessor;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IFormulaRunner;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.definition.common.FormulaCheckType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.grid.Font;
import com.jiuqi.np.office.excel2.WorksheetWriter2;
import com.jiuqi.np.office.excel2.filter.FilterColCondition;
import com.jiuqi.np.office.excel2.filter.FilterOperator;
import com.jiuqi.np.office.excel2.filter.FilterRegionCondition;
import com.jiuqi.np.office.excel2.group.GroupDirection;
import com.jiuqi.np.office.excel2.label.ExcelLabel;
import com.jiuqi.np.office.excel2.link.CellLink;
import com.jiuqi.np.office.excel2.print.IExcelPrintSetup;
import com.jiuqi.np.util.ColorUtil;
import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.conditionalstyle.controller.IConditionalStyleController;
import com.jiuqi.nr.conditionalstyle.facade.ConditionalStyle;
import com.jiuqi.nr.data.excel.export.ExcelPrintSetup;
import com.jiuqi.nr.data.excel.export.ExpSheetGroup;
import com.jiuqi.nr.data.excel.export.RegionDataSetPageLoader;
import com.jiuqi.nr.data.excel.export.fml.IFmlExportService;
import com.jiuqi.nr.data.excel.export.grid.GridArea;
import com.jiuqi.nr.data.excel.export.grid.GridAreaInfo;
import com.jiuqi.nr.data.excel.extend.IExportExcelPrintSettings;
import com.jiuqi.nr.data.excel.extend.IRegionDataSetProvider;
import com.jiuqi.nr.data.excel.extend.ISheetNameProvider;
import com.jiuqi.nr.data.excel.extend.RegionDataSetProviderFactory;
import com.jiuqi.nr.data.excel.extend.param.SheetNameParam;
import com.jiuqi.nr.data.excel.obj.CustomGridCellStyle;
import com.jiuqi.nr.data.excel.obj.ExcelInfo;
import com.jiuqi.nr.data.excel.obj.ExpFormFolding;
import com.jiuqi.nr.data.excel.obj.ExportCache;
import com.jiuqi.nr.data.excel.obj.ExportMeasureSetting;
import com.jiuqi.nr.data.excel.obj.FilterSort;
import com.jiuqi.nr.data.excel.obj.RegionValidateResult;
import com.jiuqi.nr.data.excel.obj.SheetInfo;
import com.jiuqi.nr.data.excel.param.BatchCSConditionMonitor;
import com.jiuqi.nr.data.excel.param.CellQueryInfo;
import com.jiuqi.nr.data.excel.param.DataInfo;
import com.jiuqi.nr.data.excel.param.DataPrintParam;
import com.jiuqi.nr.data.excel.param.DataQueryPar;
import com.jiuqi.nr.data.excel.param.Excel;
import com.jiuqi.nr.data.excel.param.FilterCondition;
import com.jiuqi.nr.data.excel.service.impl.DataValidatorFactory;
import com.jiuqi.nr.data.excel.service.internal.IExportOptionsService;
import com.jiuqi.nr.data.excel.service.internal.IFormDataService;
import com.jiuqi.nr.data.excel.service.internal.IRegionDataValidator;
import com.jiuqi.nr.data.excel.utils.EnueSheetUtil;
import com.jiuqi.nr.data.excel.utils.ExportUtil;
import com.jiuqi.nr.data.excel.utils.Grid2DataSetValueUtil;
import com.jiuqi.nr.data.logic.internal.util.FileFieldValueProcessor;
import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.IQueryInfo;
import com.jiuqi.nr.datacrud.IRegionDataSet;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.LinkSort;
import com.jiuqi.nr.datacrud.MultiDimensionalDataSet;
import com.jiuqi.nr.datacrud.PageInfo;
import com.jiuqi.nr.datacrud.QueryInfoBuilder;
import com.jiuqi.nr.datacrud.RegionGradeInfo;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.RegionRelationFactory;
import com.jiuqi.nr.datacrud.spi.RowFilter;
import com.jiuqi.nr.datacrud.spi.filter.FormulaFilter;
import com.jiuqi.nr.datareport.obj.RegionNumber;
import com.jiuqi.nr.datareport.obj.RegionNumberManager;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.definition.common.DataLinkEditMode;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormFoldingDirEnum;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IPrintRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.BigDataDefine;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.PrintTemplateDefine;
import com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.RegionSettingDefine;
import com.jiuqi.nr.definition.facade.RegionTabSettingDefine;
import com.jiuqi.nr.definition.facade.RowNumberSetting;
import com.jiuqi.nr.definition.facade.print.PrintSchemeAttributeDefine;
import com.jiuqi.nr.definition.facade.print.PrintTemplateAttributeDefine;
import com.jiuqi.nr.definition.facade.print.WordLabelDefine;
import com.jiuqi.nr.definition.facade.print.common.define.DefaultPageNumberGenerateStrategy;
import com.jiuqi.nr.definition.facade.print.common.define.IPageNumberGenerateStrategy;
import com.jiuqi.nr.definition.facade.print.common.other.PrintUtil;
import com.jiuqi.nr.definition.facade.print.common.param.IPrintParamBase;
import com.jiuqi.nr.definition.facade.print.common.parse.ParseContext;
import com.jiuqi.nr.definition.facade.print.common.parse.WordLabelParseExecuter;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.service.RunTimeExtentStyleService;
import com.jiuqi.nr.definition.print.service.IPrintLabelService;
import com.jiuqi.nr.definition.util.ExtentStyle;
import com.jiuqi.nr.definition.util.GridDataTransform;
import com.jiuqi.nr.io.service.IOEntityIsolateCondition;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.grid2.Grid2CellField;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.Grid2FieldList;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.GridCellStyleData;
import com.jiuqi.nvwa.grid2.GridEnums;
import com.jiuqi.nvwa.grid2.json.Grid2DataConst;
import com.jiuqi.nvwa.quickreport.api.query.Wrapper;
import com.jiuqi.nvwa.quickreport.api.query.option.Option;
import com.jiuqi.nvwa.quickreport.common.NvwaQuickReportException;
import com.jiuqi.nvwa.quickreport.dto.ReportData;
import com.jiuqi.nvwa.quickreport.service.QuickReportModelService;
import com.jiuqi.nvwa.quickreport.util.QuickReportConverter;
import com.jiuqi.nvwa.quickreport.web.query.GridController;
import com.jiuqi.nvwa.quickreport.web.query.SheetGridData;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class FormDataServiceImpl
implements IFormDataService {
    private static final Logger logger = LoggerFactory.getLogger(FormDataServiceImpl.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IPrintRunTimeController printRunTimeController;
    @Autowired
    private IPrintLabelService printLabelService;
    @Autowired
    private GridController gridController;
    @Autowired
    private QuickReportModelService quickReportModelService;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private RegionDataSetProviderFactory regionDataSetProviderFactory;
    @Autowired(required=false)
    private IExportExcelPrintSettings exportExcelPrintSettings;
    @Autowired
    private INvwaSystemOptionService iNvwaSystemOptionService;
    @Autowired
    private IExportOptionsService exportOptionsService;
    @Autowired
    private IConditionalStyleController conditionalStyleController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired(required=false)
    private IOEntityIsolateCondition entityIsolateCondition;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private RegionRelationFactory regionRelationFactory;
    @Autowired
    private IFmlExportService fmlExportService;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean writeSheet(SXSSFWorkbook workbook, SheetInfo sheetInfo, ExportCache exportCache) {
        this.initCanSeeSet(sheetInfo, exportCache);
        boolean haveData = false;
        FormDefine formByKey = exportCache.getFormByKey(sheetInfo.getFormKey());
        for (int count = 0; sheetInfo.canWrite() && count < this.exportOptionsService.getSheetSplitMaxNum(); ++count) {
            SXSSFSheet sheet;
            block23: {
                Grid2Data grid2Data;
                String sheetName = FormDataServiceImpl.getCurSheetName(sheetInfo, count, exportCache);
                sheetInfo.setSheetName(sheetName);
                sheet = workbook.getSheet(sheetName);
                if (null != sheet) break;
                sheet = workbook.createSheet(sheetName);
                sheet.setRowSumsBelow(false);
                if (FormType.FORM_TYPE_INSERTANALYSIS.equals((Object)formByKey.getFormType())) {
                    try {
                        Wrapper wp = this.getWrapper(sheetInfo, exportCache, formByKey);
                        SheetGridData primarySheetData = this.gridController.getPrimarySheetData(wp, false);
                        GridData gridData = primarySheetData.getGridData();
                        Grid2Data nwGridData = GridDataTransform.gridDataToGrid2Data((GridData)gridData, (Grid2Data)new Grid2Data());
                        WorkbookContext workbookContext = new WorkbookContext((Workbook)workbook);
                        List<ExcelLabel> labels = this.handleLabel(sheetInfo, nwGridData, exportCache);
                        int rowDeviation = FormDataServiceImpl.getLabelRowDeviation(labels);
                        if (rowDeviation > 0) {
                            gridData.insertRow(1, rowDeviation);
                        }
                        PrintSetting printSetting = FormDataServiceImpl.handleBIPrintSettingRowDeviation(primarySheetData.getPrintSetting(), rowDeviation);
                        WorksheetWriterEx worksheetWriterEx = new WorksheetWriterEx(workbookContext, (Sheet)sheet, gridData, printSetting);
                        worksheetWriterEx.writeWorkSheet();
                        FormDataServiceImpl.processBackColor(sheetInfo, sheet);
                        this.processPrintLabel(nwGridData, sheet, workbook, labels);
                        break block23;
                    }
                    catch (Exception e) {
                        logger.error(e.getMessage(), e);
                        break;
                    }
                    finally {
                        sheetInfo.stopWrite();
                    }
                }
                ExcelPrintSetup excelPrintSetup = exportCache.getExcelPrintSetup(sheetInfo.getExportOps().getPrintSchemeKey(), sheetInfo.getFormKey());
                sheetInfo.setExcelPrintSetup(excelPrintSetup);
                Grid2Data formStyle = this.getFormStyle(sheetInfo.getFormKey());
                if (sheetInfo.getExportOps().isOnlyStyle()) {
                    grid2Data = formStyle;
                    this.setSheetGroups(sheetInfo, exportCache);
                    sheetInfo.stopWrite();
                } else {
                    grid2Data = this.fillFormData(formStyle, sheetInfo, exportCache);
                }
                if (grid2Data == null) {
                    workbook.removeSheetAt(workbook.getSheetIndex(sheet));
                    logger.error("\u5f02\u5e38\u4e2d\u65ad:grid2Data\u4e3a\u7a7a-{}", (Object)sheetInfo.getFormKey());
                    break;
                }
                WorksheetWriter2 worksheetWriter2 = new WorksheetWriter2(grid2Data, (Sheet)sheet, workbook);
                int labelRowCont = 0;
                List<ExcelLabel> labels = this.handleLabel(sheetInfo, grid2Data, exportCache);
                sheetInfo.setUpperLabelRowCount(FormDataServiceImpl.getLabelRowDeviation(labels));
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
                if (exportCache.expFml()) {
                    int rowDeviation = FormDataServiceImpl.getLabelRowDeviation(labels);
                    if (rowDeviation > 0) {
                        for (GridArea gridArea : sheetInfo.getCurSheetArea().getGridAreaList()) {
                            gridArea.moveDown(rowDeviation);
                        }
                    }
                    sheetInfo.getCurSheetArea().setMoreRow(rowDeviation);
                    if (count == 0 && sheetInfo.getExcelInfo() != null) {
                        sheetInfo.getExcelInfo().getFormAreaInfo().put(sheetInfo.getFormKey(), sheetInfo.getCurSheetArea());
                    }
                    worksheetWriter2.setFormulaMap(this.fmlExportService.getExcelFormulas(sheetInfo, exportCache));
                }
                worksheetWriter2.setLabels(labels);
                worksheetWriter2.setBackground(sheetInfo.getExportOps().isExpCellBColor());
                worksheetWriter2.setFilters(sheetInfo.getFilters());
                worksheetWriter2.setLinks(sheetInfo.getLinks());
                worksheetWriter2.setSheetGroups(sheetInfo.getSheetGroups());
                List<String> startAndEndList = sheetInfo.getStartAndEndList();
                if (startAndEndList != null && startAndEndList.size() == 1) {
                    startAndEndList = null;
                }
                worksheetWriter2.setExcelPrintSetup((IExcelPrintSetup)sheetInfo.getExcelPrintSetup());
                worksheetWriter2.writeSheet(startAndEndList);
                this.setEnumDropDown(workbook, sheetInfo, exportCache);
            }
            if (this.exportExcelPrintSettings != null) {
                this.exportExcelPrintSettings.exportExcelPrintSettings(sheet, sheetInfo);
            }
            haveData = true;
        }
        if (sheetInfo.getMultiDimensionalDataSet() != null && FormType.FORM_TYPE_NEWFMDM != formByKey.getFormType()) {
            sheetInfo.getMultiDimensionalDataSet().removeRegionDataSet(sheetInfo.getDimensionCombination());
        }
        return haveData;
    }

    private void setEnumDropDown(SXSSFWorkbook workbook, SheetInfo sheetInfo, ExportCache exportCache) {
        Boolean expEnumDropDown = sheetInfo.getExportOps().getExpEnumDropDown();
        if (expEnumDropDown == null) {
            String dropDown = this.iNvwaSystemOptionService.get("nr-data-entry-export", "EXPORT_EXCEL_DROPDOWN");
            if (!"1".equals(dropDown)) {
                return;
            }
            String dropDownNum = this.iNvwaSystemOptionService.get("nr-data-entry-export", "EXPORT_EXCEL_DROPDOWN_NUM");
            if (dropDownNum == null || dropDownNum.isEmpty()) {
                dropDownNum = "1000";
            }
            EnueSheetUtil.setEntityIsolateCondition(this.entityIsolateCondition);
            EnueSheetUtil.setEnueSheet(workbook, sheetInfo, exportCache, Integer.parseInt(dropDownNum));
        } else {
            if (!expEnumDropDown.booleanValue()) {
                return;
            }
            EnueSheetUtil.setEntityIsolateCondition(this.entityIsolateCondition);
            EnueSheetUtil.setEnueSheet(workbook, sheetInfo, exportCache, -1);
        }
    }

    @NonNull
    private Wrapper getWrapper(SheetInfo sheetInfo, ExportCache exportCache, FormDefine formByKey) throws NvwaQuickReportException {
        ReportData rd = new ReportData();
        String reportGuid = String.valueOf(formByKey.getExtensionProp("analysisGuid"));
        rd.setGuid(reportGuid);
        QuickReport quickReport = this.quickReportModelService.getQuickReportByGuidOrId(reportGuid);
        if (quickReport.getWorksheets() != null) {
            ExcelPrintSetup excelPrintSetup = exportCache.getExcelPrintSetup(sheetInfo.getExportOps().getPrintSchemeKey(), sheetInfo.getFormKey());
            PrintSetting biPrintSetting = FormDataServiceImpl.getBIPrintSetting(excelPrintSetup);
            for (WorksheetModel worksheet : quickReport.getWorksheets()) {
                worksheet.setPrintSetting(biPrintSetting);
            }
        }
        JSONObject reportEditorJson = QuickReportConverter.buildReportEditorJson((QuickReport)quickReport);
        rd.setData(reportEditorJson.toString());
        Option op = this.getOption(sheetInfo.getDimensionCombination());
        Wrapper wp = new Wrapper();
        wp.setOption(op);
        wp.setReportData(rd);
        return wp;
    }

    @NonNull
    private static PrintSetting getBIPrintSetting(ExcelPrintSetup excelPrintSetup) {
        boolean pageScale;
        PrintSetting printSetting = new PrintSetting();
        printSetting.setLandscape(excelPrintSetup.isLandscape());
        printSetting.setPaperSize(FormDataServiceImpl.getBIPaperSize(excelPrintSetup.getPaperSize()));
        if (excelPrintSetup.getTopMarginCM() >= 0.0) {
            printSetting.getMargin().setTop(excelPrintSetup.getTopMarginCM());
        }
        if (excelPrintSetup.getLeftMarginCM() >= 0.0) {
            printSetting.getMargin().setLeft(excelPrintSetup.getLeftMarginCM());
        }
        if (excelPrintSetup.getRightMarginCM() >= 0.0) {
            printSetting.getMargin().setRight(excelPrintSetup.getRightMarginCM());
        }
        if (excelPrintSetup.getBottomMarginCM() >= 0.0) {
            printSetting.getMargin().setBottom(excelPrintSetup.getBottomMarginCM());
        }
        printSetting.setHorzCenter(excelPrintSetup.isHorizontallyCenter());
        printSetting.setVertCenter(excelPrintSetup.isVerticallyCenter());
        if (excelPrintSetup.getRowBreakIndex() != null && excelPrintSetup.getRowBreakIndex().length > 0 && excelPrintSetup.getFitHeight() <= 0) {
            for (int rowBreakIndex : excelPrintSetup.getRowBreakIndex()) {
                printSetting.getBreakRows().add(rowBreakIndex + 1);
            }
        }
        if (excelPrintSetup.getColumnBreakIndex() != null && excelPrintSetup.getColumnBreakIndex().length > 0 && excelPrintSetup.getFitWidth() <= 0) {
            for (int columnBreakIndex : excelPrintSetup.getColumnBreakIndex()) {
                printSetting.getBreakCols().add(columnBreakIndex + 1);
            }
        }
        boolean bl = pageScale = excelPrintSetup.getFitWidth() >= 0 || excelPrintSetup.getFitHeight() >= 0;
        if (pageScale) {
            int fitHeight = Math.max(0, excelPrintSetup.getFitHeight());
            int fitWidth = Math.max(0, excelPrintSetup.getFitWidth());
            Zoom zoom = new Zoom(fitHeight, fitWidth);
            printSetting.setZoom(zoom);
        }
        printSetting.setLeftToRight(excelPrintSetup.isLeft2Right());
        return printSetting;
    }

    private static PrintSetting handleBIPrintSettingRowDeviation(PrintSetting printSetting, int rowDeviation) {
        if (rowDeviation <= 0 || printSetting == null) {
            return printSetting;
        }
        if (printSetting.getBreakRows() != null) {
            printSetting.getBreakRows().replaceAll(integer -> integer + rowDeviation);
        }
        return printSetting;
    }

    private static PaperSize getBIPaperSize(short poiPaperSize) {
        switch (poiPaperSize) {
            case 8: {
                return PaperSize.A3_PAPER;
            }
            case 11: {
                return PaperSize.A5_PAPER;
            }
            case 12: {
                return PaperSize.B4_PAPER;
            }
            case 13: {
                return PaperSize.B5_PAPER;
            }
            case 7: {
                return PaperSize.EXECUTIVE_PAPER;
            }
            case 5: {
                return PaperSize.LEGAL_PAPER;
            }
            case 1: {
                return PaperSize.LETTER_PAPER;
            }
            case 6: {
                return PaperSize.STATEMENT_PAPER;
            }
            case 3: {
                return PaperSize.TABLOID_PAPER;
            }
        }
        return PaperSize.A4_PAPER;
    }

    private static String getCurSheetName(SheetInfo sheetInfo, int page, ExportCache exportCache) {
        ISheetNameProvider sheetNameProvider = exportCache.getSheetNameProvider();
        if (sheetNameProvider != null) {
            String excelName = sheetInfo.getExcelInfo() == null ? null : sheetInfo.getExcelInfo().getExcelName();
            SheetNameParam sheetNameParam = new SheetNameParam(sheetInfo.getDimensionCombination(), sheetInfo.getFormKey(), page, excelName);
            String provideSheetName = sheetNameProvider.getSheetName(sheetNameParam);
            if (provideSheetName != null) {
                logger.debug("\u539fsheet\u540d\uff1a{}\uff0c\u4f7f\u7528\u6269\u5c55\u7684sheet\u540d\uff1a{}", (Object)sheetInfo.getOriginalSheetName(), (Object)provideSheetName);
                return provideSheetName;
            }
        }
        String sheetCount = page == 0 ? "" : "(" + page + ")";
        return sheetInfo.getOriginalSheetName() + sheetCount;
    }

    private static int getLabelRowDeviation(List<ExcelLabel> labels) {
        int rowDeviation = 0;
        if (!CollectionUtils.isEmpty(labels)) {
            for (ExcelLabel label : labels) {
                int rowTo = label.getRowIndex() + label.getRowSpan();
                if (!label.isUpper() || rowTo <= rowDeviation) continue;
                rowDeviation = rowTo;
            }
        }
        return rowDeviation;
    }

    private static void processBackColor(SheetInfo sheetInfo, Sheet sheet) {
        if (!sheetInfo.getExportOps().isExpCellBColor()) {
            for (Row cells : sheet) {
                for (Cell cell : cells) {
                    CellStyle cellStyle = cell.getCellStyle();
                    if (cellStyle == null) continue;
                    cellStyle.setFillPattern(FillPatternType.NO_FILL);
                    cellStyle.setFillForegroundColor(null);
                }
            }
        }
    }

    private void processPrintLabel(Grid2Data nwGridData, Sheet sheet, Workbook workbook, List<ExcelLabel> labels) {
        int labelRowCont = 0;
        if (labels != null && labels.size() > 0) {
            for (ExcelLabel excelLabel : labels) {
                if (!excelLabel.isUpper() || excelLabel.getRowIndex() <= labelRowCont) continue;
                labelRowCont = excelLabel.getRowIndex();
            }
        }
        if (nwGridData.getHeaderColumnCount() > 1 || nwGridData.getHeaderRowCount() > 1) {
            int colIndex = 0;
            int rowIndex = 0;
            if (nwGridData.getHeaderColumnCount() > 1) {
                colIndex = nwGridData.getHeaderColumnCount() - 1;
            }
            if (nwGridData.getHeaderRowCount() > 1) {
                rowIndex = nwGridData.getHeaderRowCount() - 1;
            }
            int rowNum = rowIndex + labelRowCont;
            sheet.createFreezePane(colIndex, rowNum, colIndex, rowNum);
        }
        if (null != labels && !labels.isEmpty()) {
            HashSet<Integer> removedIndex = new HashSet<Integer>();
            for (ExcelLabel excelLabel : labels) {
                SXSSFRow sXSSFRow;
                int rowIndex = excelLabel.getRowIndex() - 1;
                SXSSFRow row = (SXSSFRow)sheet.getRow(rowIndex);
                if (row != null) {
                    if (removedIndex.add(rowIndex)) {
                        sheet.removeRow(row);
                        sXSSFRow = (SXSSFRow)sheet.createRow(rowIndex);
                    } else {
                        sXSSFRow = row;
                    }
                } else {
                    sXSSFRow = (SXSSFRow)sheet.createRow(rowIndex);
                }
                SXSSFCell cell = sXSSFRow.createCell(excelLabel.getColIndex() - 1);
                cell.setCellValue(excelLabel.getText());
                String fontName = excelLabel.getFontName();
                String fontSize = excelLabel.getFontSize();
                boolean bold = excelLabel.isBold();
                boolean italic = excelLabel.isItalic();
                boolean strikeout = excelLabel.isStrikeout();
                boolean underline = excelLabel.isUnderline();
                XSSFFont font = (XSSFFont)workbook.createFont();
                font.setFontHeightInPoints((short)Math.round(Double.parseDouble(fontSize)));
                font.setFontName(fontName);
                if (bold) {
                    font.setBold(true);
                }
                if (italic) {
                    font.setItalic(true);
                }
                if (underline) {
                    font.setUnderline((byte)1);
                }
                if (strikeout) {
                    font.setStrikeout(true);
                }
                XSSFCellStyle style = (XSSFCellStyle)workbook.createCellStyle();
                style.setFont(font);
                if (excelLabel.getRowIndex() == 1 && excelLabel.getColIndex() == nwGridData.getColumnCount() / 2) {
                    style.setAlignment(HorizontalAlignment.CENTER);
                    style.setVerticalAlignment(VerticalAlignment.CENTER);
                }
                cell.setCellStyle(style);
                int rowTo = excelLabel.getRowIndex() + excelLabel.getRowSpan();
                if (excelLabel.getRowSpan() <= 0 && excelLabel.getColSpan() <= 0) continue;
                try {
                    sheet.addMergedRegion(new CellRangeAddress(excelLabel.getRowIndex(), rowTo, excelLabel.getColIndex(), excelLabel.getColIndex() + excelLabel.getColSpan()));
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    private Option getOption(DimensionCombination dimensionCombination) {
        String periodDimensionName = this.periodEngineService.getPeriodAdapter().getPeriodDimensionName();
        Option op = new Option();
        for (FixedDimensionValue fixedDimensionValue : dimensionCombination) {
            String name = fixedDimensionValue.getName();
            Object value = fixedDimensionValue.getValue();
            if (periodDimensionName.equals(name)) {
                op.addParamValue("MD_PERIOD", value);
            } else {
                op.addParamValue(name, value);
            }
            op.addParamValue("P_" + name, value);
        }
        return op;
    }

    private void initCanSeeSet(SheetInfo sheetInfo, ExportCache exportCache) {
        Map<String, List<CellQueryInfo>> conditions = sheetInfo.getExportOps().getConditions();
        if (CollectionUtils.isEmpty(conditions)) {
            return;
        }
        List<DataRegionDefine> sortedRegionsByForm = exportCache.getSortedRegionsByForm(sheetInfo.getFormKey());
        FilterSort filterSort = exportCache.getFilterSort(sheetInfo.getDimensionCombination());
        Map<String, List<RowFilter>> rowFilter = filterSort.getRowFilter();
        Map<String, PageInfo> topPageInfo = filterSort.getTopPageInfo();
        for (DataRegionDefine dataRegionDefine : sortedRegionsByForm) {
            List<CellQueryInfo> cellQueryInfos;
            if (dataRegionDefine.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE || CollectionUtils.isEmpty(cellQueryInfos = conditions.get(dataRegionDefine.getKey()))) continue;
            List<String> selectLinkKey = cellQueryInfos.stream().map(CellQueryInfo::getCellKey).collect(Collectors.toList());
            List<RowFilter> rowFilters = null;
            PageInfo pageInfo = null;
            if (!CollectionUtils.isEmpty(rowFilter)) {
                rowFilters = rowFilter.get(dataRegionDefine.getKey());
            }
            if (!CollectionUtils.isEmpty(topPageInfo)) {
                pageInfo = topPageInfo.get(dataRegionDefine.getKey());
            }
            if (rowFilters == null && pageInfo == null) continue;
            HashSet<String> canSeeSet = new HashSet<String>();
            sheetInfo.getCanSeeSetMap().put(dataRegionDefine.getKey(), canSeeSet);
            IRegionDataSet dataSet = this.queryRegionData(sheetInfo, dataRegionDefine, exportCache, selectLinkKey, rowFilters, false, pageInfo);
            if (dataSet == null) continue;
            for (IRowData rowDatum : dataSet.getRowData()) {
                canSeeSet.add(rowDatum.getRecKey());
            }
        }
    }

    private void checkCSFormStyles(SheetInfo sheetInfo, ExportCache exportCache) {
        exportCache.link2ConditionalStyle().clear();
        exportCache.unconditionalStyleDims().clear();
        DimensionValueSet dimensionValueSet = sheetInfo.getDimensionCombination().toDimensionValueSet();
        FormDefine queryFormById = exportCache.getFormByKey(sheetInfo.getFormKey());
        List<ConditionalStyle> allCSInForm = exportCache.getConditionalStyleByForm(this.conditionalStyleController, sheetInfo.getFormKey());
        FormulaCallBack callback = new FormulaCallBack();
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        ReportFmlExecEnvironment environment = exportCache.getFmlExecEnv(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController);
        FileFieldValueProcessor fileFieldValueProcessor = new FileFieldValueProcessor();
        environment.setFieldValueUpdateProcessor((IFieldValueUpdateProcessor)fileFieldValueProcessor);
        context.setEnv((IFmlExecEnvironment)environment);
        context.setDefaultGroupName(queryFormById.getFormCode());
        context.setAutoDataMasking(false);
        if (allCSInForm != null && !allCSInForm.isEmpty()) {
            for (ConditionalStyle conditionalStyle : allCSInForm) {
                String styleExpression = conditionalStyle.getStyleExpression();
                Formula formula = new Formula();
                formula.setId(conditionalStyle.getKey());
                formula.setFormula(styleExpression);
                formula.setChecktype(Integer.valueOf(FormulaCheckType.FORMULA_CHECK_ERROR.getValue()));
                formula.setFormKey(queryFormById.getKey());
                formula.setReportName(queryFormById.getFormCode());
                callback.getFormulas().add(formula);
                DataLinkDefine dataLinkDefineByXY = this.runTimeViewController.queryDataLinkDefineByXY(queryFormById.getKey(), conditionalStyle.getPosX(), conditionalStyle.getPosY());
                if (exportCache.link2ConditionalStyle().containsKey(dataLinkDefineByXY.getKey())) {
                    exportCache.link2ConditionalStyle().get(dataLinkDefineByXY.getKey()).add(conditionalStyle);
                    continue;
                }
                ArrayList<ConditionalStyle> lsit = new ArrayList<ConditionalStyle>();
                lsit.add(conditionalStyle);
                exportCache.link2ConditionalStyle().put(dataLinkDefineByXY.getKey(), lsit);
            }
        }
        IFormulaRunner runner = this.dataAccessProvider.newFormulaRunner(callback);
        BatchCSConditionMonitor monitor = new BatchCSConditionMonitor();
        try {
            runner.prepareCheck(context, dimensionValueSet, (IMonitor)monitor);
            runner.run((IMonitor)monitor);
            Map<String, Set<DimensionValueSet>> result = monitor.getResult();
            exportCache.unconditionalStyleDims().putAll(result);
        }
        catch (Exception e) {
            sheetInfo.stopWrite();
            logger.error("\u89e3\u6790\u516c\u5f0f\u5931\u8d25\uff1a{},{}", (Object)e.getMessage(), (Object)e);
        }
    }

    @Override
    public List<ExcelLabel> handleLabel(SheetInfo sheetInfo, Grid2Data grid2Data, ExportCache exportCache) {
        if (!sheetInfo.getExportOps().isLabel()) {
            return null;
        }
        ArrayList<ExcelLabel> returnList = new ArrayList<ExcelLabel>();
        HashSet<String> excelLabelSet = new HashSet<String>();
        try {
            List wordLabels;
            List printSchemes;
            PrintTemplateSchemeDefine printTemplateSchemeDefine;
            HashMap<String, String> measure;
            boolean formHasMeasure;
            DataPrintParam printParam = new DataPrintParam();
            printParam.setTaskKey(sheetInfo.getTaskKey());
            printParam.setFormSchemeKey(sheetInfo.getFormSchemeKey());
            printParam.setFormulaSchemeKey(sheetInfo.getFormSchemeKey());
            printParam.setFormKey(sheetInfo.getFormKey());
            printParam.setLabel(true);
            printParam.setDimensionValueSet(sheetInfo.getDimensionCombination().toDimensionValueSet());
            printParam.setExportEmptyTable(sheetInfo.getExportOps().isEmptyForm());
            ExportMeasureSetting exportMeasureSetting = sheetInfo.getExportOps().getExportMeasureSetting();
            FormDefine formByKey = exportCache.getFormByKey(sheetInfo.getFormKey());
            boolean bl = formHasMeasure = formByKey.getMeasureUnit() != null && !formByKey.getMeasureUnit().contains("NotDimession");
            if (exportMeasureSetting != null && StringUtils.isNotEmpty((String)exportMeasureSetting.getKey()) && StringUtils.isNotEmpty((String)exportMeasureSetting.getCode())) {
                measure = new HashMap();
                measure.put(exportMeasureSetting.getKey(), exportMeasureSetting.getCode());
                printParam.setMeassureMap(measure);
            } else if (formHasMeasure) {
                measure = new HashMap<String, String>();
                String[] split = formByKey.getMeasureUnit().split(";");
                measure.put(split[0], split[1]);
                printParam.setMeassureMap(measure);
            }
            String printSchemeKey = sheetInfo.getExportOps().getPrintSchemeKey();
            PrintTemplateAttributeDefine printTemplateAttribute = null;
            PrintSchemeAttributeDefine printSchemeAttributeDefine = null;
            if ((null == printSchemeKey || printSchemeKey.isEmpty()) && (printSchemeAttributeDefine = this.printRunTimeController.getPrintSchemeAttribute(printTemplateSchemeDefine = this.printRunTimeController.queryPrintTemplateSchemeDefine(((PrintTemplateSchemeDefine)(printSchemes = this.printRunTimeController.getAllPrintSchemeByFormScheme(sheetInfo.getFormSchemeKey())).get(0)).getKey()))).getWordLabels().isEmpty()) {
                PrintTemplateDefine printTemPlate = this.printRunTimeController.queryPrintTemplateDefineBySchemeAndForm(((PrintTemplateSchemeDefine)printSchemes.get(0)).getKey(), sheetInfo.getFormKey());
                printTemplateAttribute = this.printRunTimeController.getPrintTemplateAttribute(printTemPlate);
            }
            if (!CollectionUtils.isEmpty(wordLabels = this.printLabelService.getLabelsWithLocation(printSchemeKey, sheetInfo.getFormKey())) && printSchemeAttributeDefine != null) {
                if (printSchemeAttributeDefine.getWordLabels().isEmpty()) {
                    if (printTemplateAttribute != null) {
                        wordLabels = printTemplateAttribute.getWordLabels();
                    }
                } else {
                    wordLabels = printSchemeAttributeDefine.getWordLabels();
                }
            }
            boolean noMoneyInfo = true;
            for (WordLabelDefine wordLabelDefine : wordLabels) {
                if (!wordLabelDefine.getText().contains("RPTMONEYUNIT")) continue;
                noMoneyInfo = false;
            }
            if (noMoneyInfo) {
                printParam.setMeassureMap(null);
            }
            ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
            ReportFmlExecEnvironment environment = exportCache.getFmlExecEnv(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController);
            executorContext.setEnv((IFmlExecEnvironment)environment);
            executorContext.setJQReportModel(true);
            ExcelInfo excelInfo = sheetInfo.getExcelInfo();
            Object pageNumberGenerateStrategy = excelInfo != null ? excelInfo.getPageNumberGenerateStrategy() : new DefaultPageNumberGenerateStrategy();
            ParseContext parseContext = PrintUtil.createLabelParseContext((String)sheetInfo.getFormKey(), (int)0, (int)0, (IPageNumberGenerateStrategy)pageNumberGenerateStrategy, (IPrintParamBase)printParam, (ExecutorContext)executorContext, (IExpressionEvaluator)this.dataAccessProvider.newExpressionEvaluator());
            WordLabelParseExecuter instance = WordLabelParseExecuter.getInstance();
            int rowIndexs = 0;
            int maxIndex = 0;
            HashMap<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
            ArrayList<Integer> showColsIndexs = new ArrayList<Integer>();
            int showColsWidth = 0;
            for (int i = 1; i < grid2Data.getColumnCount(); ++i) {
                if (grid2Data.isColumnHidden(i) || grid2Data.getColumnWidth(i) == 0) continue;
                showColsIndexs.add(i);
                showColsWidth += grid2Data.getColumnWidth(i);
            }
            for (WordLabelDefine wordLabelDefine : wordLabels) {
                int i;
                int colIndex = 0;
                int element = wordLabelDefine.getElement();
                int verticalPos = wordLabelDefine.getVerticalPos();
                int horizontalPos = wordLabelDefine.getHorizontalPos();
                if (verticalPos == 0 && element == 0 && StringUtils.isNotEmpty((String)wordLabelDefine.getText())) {
                    ++rowIndexs;
                }
                if (verticalPos == 0 && element == 1 && horizontalPos == 0) {
                    for (i = 1; i < grid2Data.getColumnCount(); ++i) {
                        if (grid2Data.isColumnHidden(i) || grid2Data.getColumnWidth(i) == 0) continue;
                        colIndex = i;
                        break;
                    }
                } else if (verticalPos == 0 && element == 1 && horizontalPos == 1) {
                    double currentPosition = 0.0;
                    for (int i2 = 0; i2 < showColsIndexs.size() - 1; ++i2) {
                        double nextCol;
                        double thisCol = Math.abs((currentPosition += (double)grid2Data.getColumnWidth(((Integer)showColsIndexs.get(i2)).intValue())) - (double)showColsWidth / 2.0);
                        if (!(thisCol < (nextCol = Math.abs(currentPosition + (double)grid2Data.getColumnWidth(((Integer)showColsIndexs.get(i2 + 1)).intValue()) - (double)showColsWidth / 2.0)))) continue;
                        colIndex = (Integer)showColsIndexs.get(i2);
                        break;
                    }
                } else if (verticalPos == 0 && element == 1 && horizontalPos == 2) {
                    for (i = grid2Data.getColumnCount() - 1; i > 0; --i) {
                        if (grid2Data.isColumnHidden(i) || grid2Data.getColumnWidth(i) == 0) continue;
                        colIndex = i;
                        break;
                    }
                }
                if (colIndex <= 0) continue;
                if (indexMap.containsKey(colIndex)) {
                    indexMap.put(colIndex, (Integer)indexMap.get(colIndex) + 1);
                    continue;
                }
                indexMap.put(colIndex, 1);
            }
            for (Map.Entry entry : indexMap.entrySet()) {
                maxIndex = (Integer)entry.getValue() > maxIndex ? (Integer)entry.getValue() : maxIndex;
            }
            for (WordLabelDefine wordLabelDefine : wordLabels) {
                if (!formHasMeasure && wordLabelDefine.getText().contains("RPTMONEYUNIT")) continue;
                String text = wordLabelDefine.getText();
                HashMap patternAndValue = new HashMap();
                String processedContent = instance.execute(parseContext, text, patternAndValue);
                boolean upper = true;
                int rowIndex = 0;
                int colIndex = 0;
                int element = wordLabelDefine.getElement();
                int verticalPos = wordLabelDefine.getVerticalPos();
                int horizontalPos = wordLabelDefine.getHorizontalPos();
                if (horizontalPos == 0) {
                    for (int i = 1; i < grid2Data.getColumnCount(); ++i) {
                        if (grid2Data.isColumnHidden(i) || grid2Data.getColumnWidth(i) == 0) continue;
                        colIndex = i;
                        break;
                    }
                } else if (horizontalPos == 1) {
                    double currentPosition = 0.0;
                    for (int i = 0; i < showColsIndexs.size() - 1; ++i) {
                        double nextCol;
                        double thisCol = Math.abs((currentPosition += (double)grid2Data.getColumnWidth(((Integer)showColsIndexs.get(i)).intValue())) - (double)showColsWidth / 2.0);
                        if (!(thisCol < (nextCol = Math.abs(currentPosition + (double)grid2Data.getColumnWidth(((Integer)showColsIndexs.get(i + 1)).intValue()) - (double)showColsWidth / 2.0)))) continue;
                        colIndex = (Integer)showColsIndexs.get(i);
                        break;
                    }
                } else if (horizontalPos == 2) {
                    for (int i = grid2Data.getColumnCount() - 1; i > 0; --i) {
                        if (grid2Data.isColumnHidden(i) || grid2Data.getColumnWidth(i) == 0) continue;
                        colIndex = i;
                        break;
                    }
                }
                if (verticalPos == 0 && element == 0) {
                    rowIndex = 1;
                } else if (verticalPos == 0 && element == 1) {
                    Integer integer = (Integer)indexMap.get(colIndex);
                    rowIndex = maxIndex - integer + 1 + rowIndexs;
                } else if (verticalPos == 1 && element == 0) {
                    rowIndex = grid2Data.getRowCount() + 3 + rowIndexs;
                    upper = false;
                } else if (verticalPos == 1 && element == 1) {
                    rowIndex = grid2Data.getRowCount() + 1 + rowIndexs;
                    upper = false;
                }
                Font font = wordLabelDefine.getFont();
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
                            // empty if block
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
            sheetInfo.stopWrite();
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public Grid2Data getFormStyle(String formKey) {
        BigDataDefine formDefine = this.runTimeViewController.getReportDataFromForm(formKey);
        if (null != formDefine) {
            if (formDefine.getData() != null) {
                Grid2Data bytesToGrid = Grid2Data.bytesToGrid((byte[])formDefine.getData());
                String frontScriptFromForm = this.runTimeViewController.getFrontScriptFromForm(formDefine.getKey());
                if (StringUtils.isNotEmpty((String)frontScriptFromForm)) {
                    bytesToGrid.setScript(frontScriptFromForm);
                }
                return bytesToGrid;
            }
            Grid2Data grid2Data = new Grid2Data();
            grid2Data.setRowCount(10);
            grid2Data.setColumnCount(10);
            return grid2Data;
        }
        return null;
    }

    @Override
    public Grid2Data fillFormData(Grid2Data grid2Data, SheetInfo sheetInfo, ExportCache exportCache) {
        Grid2Data result = null;
        HashMap<String, Integer> addRows = null;
        try {
            this.checkCSFormStyles(sheetInfo, exportCache);
            String formKey = sheetInfo.getFormKey();
            List<DataRegionDefine> allRegionsInForm = exportCache.getSortedRegionsByForm(formKey);
            if (CollectionUtils.isEmpty(allRegionsInForm)) {
                sheetInfo.stopWrite();
                result = sheetInfo.getExportOps().isExp0Form() || sheetInfo.getExportOps().isEmptyForm() ? grid2Data : null;
            } else {
                exportCache.initCellStyleCache(grid2Data);
                int lastLineNum = 0;
                addRows = new HashMap<String, Integer>();
                boolean formHaveData = false;
                GridAreaInfo gridAreaInfo = new GridAreaInfo();
                sheetInfo.setCurSheetArea(gridAreaInfo);
                gridAreaInfo.setFormKey(formKey);
                int queryPageLimit = this.exportOptionsService.getQueryPageLimit();
                for (DataRegionDefine dataRegionDefine : allRegionsInForm) {
                    if (dataRegionDefine.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE) {
                        boolean y = this.exportFixRegion(grid2Data, sheetInfo, dataRegionDefine, exportCache);
                        if (y) {
                            formHaveData = true;
                        }
                        sheetInfo.getPagerInfo(this.getPagerInfoKey(dataRegionDefine.getKey(), null), queryPageLimit).setTotal(Integer.MAX_VALUE);
                        continue;
                    }
                    if (dataRegionDefine.getRegionKind() == DataRegionKind.DATA_REGION_COLUMN_LIST) {
                        int moreLine = this.exportColFloatRegion(grid2Data, sheetInfo, dataRegionDefine, lastLineNum, exportCache);
                        if (moreLine != -1) {
                            formHaveData = true;
                            lastLineNum += moreLine;
                            sheetInfo.getDataCountMap().put(dataRegionDefine.getKey(), moreLine + 1);
                        }
                        addRows.put(dataRegionDefine.getKey(), moreLine);
                        sheetInfo.getPagerInfo(this.getPagerInfoKey(dataRegionDefine.getKey(), null), queryPageLimit).setTotal(Integer.MAX_VALUE);
                        continue;
                    }
                    int moreRow = this.exportRowFloatRegion(grid2Data, sheetInfo, dataRegionDefine, lastLineNum, exportCache);
                    if (moreRow != -1) {
                        formHaveData = true;
                        lastLineNum += moreRow;
                        sheetInfo.getDataCountMap().put(dataRegionDefine.getKey(), moreRow + 1);
                    }
                    addRows.put(dataRegionDefine.getKey(), moreRow);
                }
                this.exportLinkCell(grid2Data, sheetInfo, exportCache, addRows);
                result = !formHaveData && !sheetInfo.getExportOps().isEmptyForm() && !sheetInfo.getExportOps().isExp0Form() ? null : grid2Data;
            }
        }
        catch (Exception e) {
            sheetInfo.stopWrite();
            logger.error(e.getMessage(), e);
        }
        this.setSheetGroups(sheetInfo, exportCache, addRows);
        this.handleBreakPrintSetup(sheetInfo, exportCache, addRows);
        return result;
    }

    private String getPagerInfoKey(String dataRegionKey, String tabTitle) {
        String r = dataRegionKey;
        if (tabTitle != null) {
            r = r + ";" + tabTitle;
        }
        return r;
    }

    @Override
    public ExcelInfo getExcelInfo(Excel excel, ExportCache exportCache) {
        ExcelInfo excelInfo = new ExcelInfo();
        excelInfo.setExportOps(exportCache.getExportOps());
        excelInfo.setExcelName(excel.getFileName());
        ArrayList<String> formKeys = new ArrayList<String>();
        excelInfo.setFormKeys(formKeys);
        List<com.jiuqi.nr.data.excel.param.Sheet> sheets = excel.getSheets();
        boolean emptyForm = exportCache.getExportOps().isEmptyForm();
        boolean exp0Form = exportCache.getExportOps().isExp0Form();
        boolean autoFillIsNullTable = exportCache.autoFillIsNullTable();
        IRegionDataValidator regionDataValidator = null;
        boolean expEmptyNotZero = false;
        if (exp0Form) {
            if (emptyForm) {
                formKeys.addAll(sheets.stream().map(DataInfo::getFormKey).collect(Collectors.toList()));
            } else {
                regionDataValidator = DataValidatorFactory.getEmptyNotZeroFormValidator(autoFillIsNullTable);
            }
        } else if (emptyForm) {
            regionDataValidator = DataValidatorFactory.getZeroNotEmptyFormValidator(autoFillIsNullTable);
            expEmptyNotZero = true;
        } else {
            regionDataValidator = DataValidatorFactory.getEmptyZeroFormValidator(autoFillIsNullTable);
        }
        if (regionDataValidator != null) {
            for (com.jiuqi.nr.data.excel.param.Sheet sheet : sheets) {
                String formKey = sheet.getFormKey();
                FormDefine formByKey = exportCache.getFormByKey(formKey);
                if (FormType.FORM_TYPE_INSERTANALYSIS.equals((Object)formByKey.getFormType())) {
                    formKeys.add(formKey);
                    continue;
                }
                List<DataRegionDefine> allRegionsInForm = exportCache.getSortedRegionsByForm(formKey);
                boolean expForm = false;
                if (CollectionUtils.isEmpty(allRegionsInForm)) {
                    expForm = emptyForm;
                } else if (expEmptyNotZero) {
                    expForm = this.isExpForm(sheet, allRegionsInForm, exportCache, regionDataValidator);
                } else {
                    for (DataRegionDefine dataRegionDefine : allRegionsInForm) {
                        if (!this.isExpRegion(sheet, dataRegionDefine, exportCache, regionDataValidator)) continue;
                        expForm = true;
                        break;
                    }
                }
                if (!expForm) continue;
                formKeys.add(formKey);
            }
        }
        Map<String, String> formSheetInfo = FormDataServiceImpl.getFormSheetInfo(exportCache, sheets, excelInfo);
        excelInfo.setFormSheetInfo(formSheetInfo);
        return excelInfo;
    }

    @NotNull
    private static Map<String, String> getFormSheetInfo(ExportCache exportCache, List<com.jiuqi.nr.data.excel.param.Sheet> sheets, ExcelInfo excelInfo) {
        Map formSheetMap = sheets.stream().filter(o -> StringUtils.isNotEmpty((String)o.getFormKey()) && StringUtils.isNotEmpty((String)o.getSheetName())).collect(Collectors.toMap(DataInfo::getFormKey, Function.identity(), (o1, o2) -> o1));
        HashMap<String, String> formSheetInfo = new HashMap<String, String>();
        for (String formKey : excelInfo.getFormKeys()) {
            String sheetName;
            com.jiuqi.nr.data.excel.param.Sheet sheet;
            FormDefine formByKey = exportCache.getFormByKey(formKey);
            if (formByKey == null || (sheet = (com.jiuqi.nr.data.excel.param.Sheet)formSheetMap.get(formKey)) == null || StringUtils.isEmpty((String)(sheetName = sheet.getSheetName()))) continue;
            SheetNameParam sheetNameParam = new SheetNameParam(sheet.getDimensionCombination(), sheet.getFormKey(), 0, excelInfo.getExcelName());
            formSheetInfo.put(formByKey.getFormCode(), ExportUtil.sheetNameValidate(sheetName, exportCache.getSheetNameProvider(), sheetNameParam));
        }
        return formSheetInfo;
    }

    private boolean isExpForm(com.jiuqi.nr.data.excel.param.Sheet sheet, List<DataRegionDefine> allRegionsInForm, ExportCache exportCache, IRegionDataValidator regionDataValidator) {
        ArrayList<RegionValidateResult> regionValidateResults = new ArrayList<RegionValidateResult>();
        for (DataRegionDefine dataRegionDefine : allRegionsInForm) {
            Map<String, List<String>> tabs = exportCache.getExportOps().getTabs();
            List<String> tabTitles = tabs.get(dataRegionDefine.getKey());
            List<RegionTabSettingDefine> tabsByRegionCopy = FormDataServiceImpl.getSelectRegionTabs(dataRegionDefine, exportCache, tabTitles);
            ArrayList<RegionTabSettingDefine> tabsByRegion = new ArrayList<RegionTabSettingDefine>(tabsByRegionCopy);
            PageInfo pageInfo = null;
            if (dataRegionDefine.getRegionKind() == DataRegionKind.DATA_REGION_ROW_LIST) {
                pageInfo = new PageInfo();
                pageInfo.setPageIndex(0);
                pageInfo.setRowsPerPage(this.exportOptionsService.getQueryPageLimit());
            }
            if (CollectionUtils.isEmpty(tabsByRegion)) {
                IRegionDataSet regionDataSet = this.getRegionDataSet(sheet, dataRegionDefine, exportCache, null, pageInfo);
                RegionValidateResult validate = regionDataValidator.validate(regionDataSet);
                if (validate.isPass() && validate.getType() == 0) {
                    return true;
                }
                regionValidateResults.add(validate);
                continue;
            }
            for (RegionTabSettingDefine tab : tabsByRegion) {
                IRegionDataSet regionDataSet;
                RegionValidateResult validate;
                FormulaFilter formulaFilter = null;
                if (StringUtils.isNotEmpty((String)tab.getFilterCondition())) {
                    formulaFilter = new FormulaFilter(tab.getFilterCondition());
                }
                if ((validate = regionDataValidator.validate(regionDataSet = this.getRegionDataSet(sheet, dataRegionDefine, exportCache, formulaFilter, pageInfo))).isPass() && validate.getType() == 0) {
                    return true;
                }
                regionValidateResults.add(validate);
            }
        }
        return regionDataValidator.validateForm(regionValidateResults);
    }

    private boolean isExpRegion(com.jiuqi.nr.data.excel.param.Sheet sheet, DataRegionDefine dataRegionDefine, ExportCache exportCache, IRegionDataValidator regionDataValidator) {
        boolean result = false;
        Map<String, List<String>> tabs = exportCache.getExportOps().getTabs();
        List<String> tabTitles = tabs.get(dataRegionDefine.getKey());
        List<RegionTabSettingDefine> tabsByRegionCopy = FormDataServiceImpl.getSelectRegionTabs(dataRegionDefine, exportCache, tabTitles);
        ArrayList<RegionTabSettingDefine> tabsByRegion = new ArrayList<RegionTabSettingDefine>(tabsByRegionCopy);
        PageInfo pageInfo = null;
        if (dataRegionDefine.getRegionKind() == DataRegionKind.DATA_REGION_ROW_LIST) {
            pageInfo = new PageInfo();
            pageInfo.setPageIndex(0);
            pageInfo.setRowsPerPage(this.exportOptionsService.getQueryPageLimit());
        }
        if (CollectionUtils.isEmpty(tabsByRegion)) {
            IRegionDataSet regionDataSet = this.getRegionDataSet(sheet, dataRegionDefine, exportCache, null, pageInfo);
            result = regionDataValidator.validate(regionDataSet).isPass();
        } else {
            for (RegionTabSettingDefine tab : tabsByRegion) {
                IRegionDataSet regionDataSet;
                FormulaFilter formulaFilter = null;
                if (StringUtils.isNotEmpty((String)tab.getFilterCondition())) {
                    formulaFilter = new FormulaFilter(tab.getFilterCondition());
                }
                if (!(result = regionDataValidator.validate(regionDataSet = this.getRegionDataSet(sheet, dataRegionDefine, exportCache, formulaFilter, pageInfo)).isPass())) continue;
                return true;
            }
        }
        return result;
    }

    private IRegionDataSet getRegionDataSet(com.jiuqi.nr.data.excel.param.Sheet sheet, DataRegionDefine dataRegionDefine, ExportCache exportCache, FormulaFilter tabFilter, PageInfo pageInfo) {
        IRegionDataSetProvider regionDataSetProvider;
        Map<String, List<RowFilter>> rowFilter = exportCache.getExportOps().getRowFilter();
        QueryInfoBuilder queryInfoBuilder = new QueryInfoBuilder(dataRegionDefine.getKey(), sheet.getDimensionCombination()).whereRegionFilter();
        queryInfoBuilder.setDesensitized(true);
        if (rowFilter.containsKey(dataRegionDefine.getKey())) {
            rowFilter.get(dataRegionDefine.getKey()).forEach(arg_0 -> ((QueryInfoBuilder)queryInfoBuilder).where(arg_0));
        }
        if (exportCache.getExportOps().getExportMeasureSetting() != null) {
            queryInfoBuilder.setMeasure(exportCache.getExportOps().getExportMeasureSetting().toMeasure());
        }
        queryInfoBuilder.setFormulaSchemeKey(exportCache.getExportOps().getFormulaSchemeKey());
        if (tabFilter != null) {
            queryInfoBuilder.where((RowFilter)tabFilter);
        }
        if (pageInfo != null) {
            queryInfoBuilder.setPage(pageInfo);
        }
        IQueryInfo queryInfo = queryInfoBuilder.build();
        DataQueryPar dataQueryPar = new DataQueryPar();
        dataQueryPar.setQueryInfo(queryInfo);
        Map<String, RegionGradeInfo> gradeInfos = exportCache.getExportOps().getGradeInfos();
        if (!CollectionUtils.isEmpty(gradeInfos)) {
            RegionGradeInfo regionGradeInfo = gradeInfos.get(dataRegionDefine.getKey());
            dataQueryPar.setRegionGradeInfo(regionGradeInfo);
        }
        if (dataQueryPar.getRegionGradeInfo() == null) {
            RegionRelation regionRelation = this.regionRelationFactory.getRegionRelation(dataRegionDefine.getKey());
            RegionGradeInfo gradeInfo = regionRelation.getGradeInfo();
            if (!exportCache.getExportOps().isSumData()) {
                gradeInfo.setQueryDetails(true);
            }
            dataQueryPar.setRegionGradeInfo(gradeInfo);
        }
        if (StringUtils.isNotEmpty((String)exportCache.getDataSnapshotId())) {
            regionDataSetProvider = this.regionDataSetProviderFactory.getRegionDataSetProvider("snapshotRegionDataSetProvider");
            dataQueryPar.setSnapshotId(exportCache.getDataSnapshotId());
        } else {
            regionDataSetProvider = this.regionDataSetProviderFactory.getRegionDataSetProvider("defaultRegionDataSetProvider");
        }
        return regionDataSetProvider.getRegionDataSet(dataQueryPar);
    }

    private void hideGroup(Grid2Data grid2Data, List<ExpSheetGroup> sheetGroups) {
        if (grid2Data == null || CollectionUtils.isEmpty(sheetGroups)) {
            return;
        }
        for (ExpSheetGroup sheetGroup : sheetGroups) {
            boolean collapsed = sheetGroup.isCollapsed();
            if (!collapsed) continue;
            int startIndex = sheetGroup.getStartIndex() + 1;
            int endIndex = sheetGroup.getEndIndex() + 1;
            if (startIndex <= 0 || endIndex < startIndex) continue;
            GroupDirection groupDirection = sheetGroup.getGroupDirection();
            try {
                for (int i = startIndex; i <= endIndex; ++i) {
                    if (GroupDirection.ROW == groupDirection) {
                        grid2Data.setRowHidden(i, true);
                        continue;
                    }
                    if (GroupDirection.COL != groupDirection) continue;
                    grid2Data.setColumnHidden(i, true);
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private void setSheetGroups(SheetInfo sheetInfo, ExportCache exportCache) {
        this.setSheetGroups(sheetInfo, exportCache, null);
    }

    private void setSheetGroups(SheetInfo sheetInfo, ExportCache exportCache, Map<String, Integer> addRows) {
        List<ExpFormFolding> expFormFoldings = exportCache.getExpFormFoldingOps(sheetInfo.getFormKey());
        if (CollectionUtils.isEmpty(expFormFoldings)) {
            return;
        }
        List<DataRegionDefine> allRegionsInForm = exportCache.getSortedRegionsByForm(sheetInfo.getFormKey());
        Map<DataRegionKind, List<DataRegionDefine>> regionKindListMap = allRegionsInForm.stream().collect(Collectors.groupingBy(DataRegionDefine::getRegionKind));
        List<DataRegionDefine> colRegions = regionKindListMap.get(DataRegionKind.DATA_REGION_COLUMN_LIST);
        List<DataRegionDefine> rowRegions = regionKindListMap.get(DataRegionKind.DATA_REGION_ROW_LIST);
        for (ExpFormFolding expFormFolding : expFormFoldings) {
            int startIdx = expFormFolding.getStartIdx();
            int endIdx = expFormFolding.getEndIdx();
            if (startIdx < 1 || endIdx < 1) continue;
            if (addRows != null) {
                if (FormFoldingDirEnum.ROW_DIRECTION == expFormFolding.getDirection() && !CollectionUtils.isEmpty(rowRegions)) {
                    for (DataRegionDefine rowRegion : rowRegions) {
                        Integer moreRow;
                        if (rowRegion.getRegionBottom() < startIdx) {
                            moreRow = addRows.get(rowRegion.getKey());
                            if (moreRow == null || moreRow <= 0) continue;
                            startIdx += moreRow.intValue();
                            endIdx += moreRow.intValue();
                            continue;
                        }
                        if (rowRegion.getRegionTop() < startIdx || rowRegion.getRegionBottom() > endIdx || (moreRow = addRows.get(rowRegion.getKey())) == null || moreRow <= 0) continue;
                        endIdx += moreRow.intValue();
                    }
                } else if (FormFoldingDirEnum.COL_DIRECTION == expFormFolding.getDirection() && !CollectionUtils.isEmpty(colRegions)) {
                    for (DataRegionDefine colRegion : colRegions) {
                        Integer moreCol;
                        if (colRegion.getRegionRight() < startIdx) {
                            moreCol = addRows.get(colRegion.getKey());
                            if (moreCol == null || moreCol <= 0) continue;
                            startIdx += moreCol.intValue();
                            endIdx += moreCol.intValue();
                            continue;
                        }
                        if (colRegion.getRegionLeft() < startIdx || colRegion.getRegionRight() > endIdx || (moreCol = addRows.get(colRegion.getKey())) == null || moreCol <= 0) continue;
                        endIdx += moreCol.intValue();
                    }
                }
            }
            ExpSheetGroup expSheetGroup = new ExpSheetGroup(expFormFolding, startIdx, endIdx);
            sheetInfo.getSheetGroups().add(expSheetGroup);
        }
    }

    private void handleBreakPrintSetup(SheetInfo sheetInfo, ExportCache exportCache, Map<String, Integer> addRows) {
        int[] columnBreakIndex;
        ExcelPrintSetup excelPrintSetup = sheetInfo.getExcelPrintSetup();
        if (excelPrintSetup == null) {
            return;
        }
        List<DataRegionDefine> allRegionsInForm = exportCache.getSortedRegionsByForm(sheetInfo.getFormKey());
        Map<DataRegionKind, List<DataRegionDefine>> regionKindListMap = allRegionsInForm.stream().collect(Collectors.groupingBy(DataRegionDefine::getRegionKind));
        List<DataRegionDefine> colRegions = regionKindListMap.get(DataRegionKind.DATA_REGION_COLUMN_LIST);
        List<DataRegionDefine> rowRegions = regionKindListMap.get(DataRegionKind.DATA_REGION_ROW_LIST);
        int[] rowBreakIndex = excelPrintSetup.getRowBreakIndex();
        if (rowBreakIndex != null && rowBreakIndex.length > 0) {
            int[] newRowBreak = new int[rowBreakIndex.length];
            for (int i = 0; i < rowBreakIndex.length; ++i) {
                int orgIndex;
                int finalIndex = orgIndex = rowBreakIndex[i];
                if (!CollectionUtils.isEmpty(rowRegions)) {
                    for (DataRegionDefine rowRegion : rowRegions) {
                        Integer moreRow;
                        if (rowRegion.getRegionBottom() > orgIndex || (moreRow = addRows.get(rowRegion.getKey())) == null || moreRow <= 0) continue;
                        finalIndex += moreRow.intValue();
                    }
                }
                newRowBreak[i] = finalIndex - 1;
            }
            excelPrintSetup.setRowBreakIndex(newRowBreak);
        }
        if ((columnBreakIndex = excelPrintSetup.getColumnBreakIndex()) != null && columnBreakIndex.length > 0) {
            int[] newColBreak = new int[columnBreakIndex.length];
            for (int i = 0; i < columnBreakIndex.length; ++i) {
                int orgIndex;
                int finalIndex = orgIndex = columnBreakIndex[i];
                if (!CollectionUtils.isEmpty(colRegions)) {
                    for (DataRegionDefine colRegion : colRegions) {
                        Integer moreRow;
                        if (colRegion.getRegionRight() > orgIndex || (moreRow = addRows.get(colRegion.getKey())) == null || moreRow <= 0) continue;
                        finalIndex += moreRow.intValue();
                    }
                }
                newColBreak[i] = finalIndex - 1;
            }
            excelPrintSetup.setColumnBreakIndex(newColBreak);
        }
    }

    private int exportColFloatRegion(Grid2Data grid2Data, SheetInfo sheetInfo, DataRegionDefine dataRegionDefine, int lastLineNum, ExportCache exportCache) {
        int curLine;
        List rowNumberSetting;
        RegionNumberManager regionNumberManager = null;
        RegionSettingDefine regionSetting = this.runTimeViewController.getRegionSetting(dataRegionDefine.getKey());
        if (regionSetting != null && null != (rowNumberSetting = regionSetting.getRowNumberSetting()) && rowNumberSetting.size() > 0) {
            RegionNumber regionNumber = new RegionNumber((RowNumberSetting)rowNumberSetting.get(0));
            regionNumberManager = new RegionNumberManager(regionNumber);
        }
        int regionStart = curLine = dataRegionDefine.getRegionLeft() + lastLineNum;
        Map<String, List<String>> tabs = sheetInfo.getExportOps().getTabs();
        List<String> tabTitles = tabs.get(dataRegionDefine.getKey());
        List<RegionTabSettingDefine> tabsByRegion = FormDataServiceImpl.getSelectRegionTabs(dataRegionDefine, exportCache, tabTitles);
        boolean regionHaveData = false;
        if (CollectionUtils.isEmpty(tabsByRegion)) {
            IRegionDataSet regionDataSet = this.queryRegionData(sheetInfo, dataRegionDefine, exportCache, null);
            if (regionDataSet != null && regionDataSet.getRowCount() != 0) {
                if ((curLine = this.setDataMethod2(grid2Data, sheetInfo, regionDataSet, exportCache, dataRegionDefine, curLine, regionNumberManager, null)) != -1) {
                    regionHaveData = true;
                }
            } else {
                GridCellData gridCell;
                if (regionNumberManager != null && regionNumberManager.getRegionNumber() != null && (gridCell = grid2Data.getGridCellData(curLine, regionNumberManager.getRegionNumber().getRow())) != null) {
                    Grid2DataSetValueUtil.converterFieldTypeToGridCellData(gridCell, String.valueOf(regionNumberManager.getRegionNumber().getStart()));
                }
                if (regionDataSet != null) {
                    for (IMetaData metaDatum : regionDataSet.getMetaData()) {
                        GridCellData gridCell2;
                        DataLinkDefine dataLinkDefine = metaDatum.getDataLinkDefine();
                        if (dataLinkDefine == null || (gridCell2 = grid2Data.getGridCellData(curLine, dataLinkDefine.getPosY())) == null) continue;
                        Grid2DataSetValueUtil.setCellDataType(metaDatum, gridCell2, exportCache, sheetInfo);
                        if (!sheetInfo.getCustomCellStyles().containsKey(dataLinkDefine.getKey())) continue;
                        CustomGridCellStyle customGridCellStyle = sheetInfo.getCustomCellStyles().get(dataLinkDefine.getKey());
                        gridCell2.setBackGroundColor(ColorUtil.mergeColor((int)gridCell2.getBackGroundColor(), (int)customGridCellStyle.getBackGroundColor()));
                        gridCell2.setBackGroundStyle(customGridCellStyle.getBackGroundStyle());
                    }
                }
            }
            GridArea gridArea = new GridArea(dataRegionDefine);
            gridArea.setRealRegion(regionStart, dataRegionDefine.getRegionTop(), curLine, dataRegionDefine.getRegionBottom());
            gridArea.setRowCount((curLine - regionStart + 1) / gridArea.getRowSpan());
            sheetInfo.getCurSheetArea().addGridArea(gridArea);
        } else {
            for (RegionTabSettingDefine tab : tabsByRegion) {
                IRegionDataSet regionDataSet;
                if (regionNumberManager != null) {
                    regionNumberManager.resetNumber();
                }
                int tabStart = curLine;
                if (tabsByRegion.size() > 1) {
                    grid2Data.insertColumns(curLine, 1, curLine);
                    GridCellData gridCellTab = grid2Data.getGridCellData(1, curLine);
                    gridCellTab.setShowText(tab.getTitle());
                    Grid2CellField grid2CellField = new Grid2CellField(1, gridCellTab.getRowIndex(), grid2Data.getColumnCount() - 1, gridCellTab.getRowIndex());
                    grid2Data.merges().addMergeRect(grid2CellField);
                    ++curLine;
                    GridCellStyleData cellStyleData = new GridCellStyleData();
                    cellStyleData.setHorzAlign(GridEnums.TextAlignment.Fore.ordinal());
                    gridCellTab.setCellStyleData(cellStyleData);
                    gridCellTab.setBottomBorderStyle(GridEnums.GridBorderStyle.SOLID.getStyle());
                }
                FormulaFilter formulaFilter = null;
                if (StringUtils.isNotEmpty((String)tab.getFilterCondition())) {
                    formulaFilter = new FormulaFilter(tab.getFilterCondition());
                }
                if ((regionDataSet = this.queryRegionData(sheetInfo, dataRegionDefine, exportCache, Collections.singletonList(formulaFilter))) != null && regionDataSet.getRowCount() != 0) {
                    if ((curLine = this.setDataMethod2(grid2Data, sheetInfo, regionDataSet, exportCache, dataRegionDefine, curLine, regionNumberManager, null)) != -1) {
                        regionHaveData = true;
                    }
                    grid2Data.insertColumns(curLine + 1, 1, curLine);
                    ++curLine;
                } else {
                    GridCellData gridCell;
                    grid2Data.insertColumns(curLine, 1, curLine);
                    if (regionNumberManager != null && regionNumberManager.getRegionNumber() != null && (gridCell = grid2Data.getGridCellData(curLine, regionNumberManager.getRegionNumber().getRow())) != null) {
                        Grid2DataSetValueUtil.converterFieldTypeToGridCellData(gridCell, String.valueOf(regionNumberManager.getRegionNumber().getStart()));
                    }
                    if (regionDataSet != null) {
                        gridCell = regionDataSet.getMetaData().iterator();
                        while (gridCell.hasNext()) {
                            GridCellData gridCell3;
                            IMetaData metaDatum = (IMetaData)gridCell.next();
                            DataLinkDefine dataLinkDefine = metaDatum.getDataLinkDefine();
                            if (dataLinkDefine == null || (gridCell3 = grid2Data.getGridCellData(curLine, dataLinkDefine.getPosY())) == null) continue;
                            Grid2DataSetValueUtil.setCellDataType(metaDatum, gridCell3, exportCache, sheetInfo);
                            if (!sheetInfo.getCustomCellStyles().containsKey(dataLinkDefine.getKey())) continue;
                            CustomGridCellStyle customGridCellStyle = sheetInfo.getCustomCellStyles().get(dataLinkDefine.getKey());
                            gridCell3.setBackGroundColor(ColorUtil.mergeColor((int)gridCell3.getBackGroundColor(), (int)customGridCellStyle.getBackGroundColor()));
                            gridCell3.setBackGroundStyle(customGridCellStyle.getBackGroundStyle());
                        }
                    }
                    ++curLine;
                }
                String startAndEndS = tabStart + ";" + (curLine - 2);
                sheetInfo.getStartAndEndList().add(startAndEndS);
                GridArea gridArea = new GridArea(dataRegionDefine);
                gridArea.setRealRegion(tabStart + 1, dataRegionDefine.getRegionTop(), curLine - 2 + 1, dataRegionDefine.getRegionBottom());
                gridArea.setRowCount((curLine - 2 - tabStart + 1) / gridArea.getRowSpan());
                sheetInfo.getCurSheetArea().addGridArea(gridArea);
            }
            grid2Data.deleteColumns(curLine, 1);
            --curLine;
        }
        return regionHaveData ? curLine - regionStart - (dataRegionDefine.getRegionRight() - dataRegionDefine.getRegionLeft()) : -1;
    }

    private int setDataMethod2(Grid2Data grid2Data, SheetInfo sheetInfo, IRegionDataSet regionDataSet, ExportCache exportCache, DataRegionDefine dataRegionDefine, int curLine, RegionNumberManager regionNumberManager, String tabTitle) {
        HashMap<String, Integer> linkColMap = new HashMap<String, Integer>();
        for (int i = 0; i < regionDataSet.getMetaData().size(); ++i) {
            IMetaData metaData = (IMetaData)regionDataSet.getMetaData().get(i);
            if (metaData.getDataLinkDefine() == null) continue;
            linkColMap.put(metaData.getLinkKey(), metaData.getDataLinkDefine().getPosX());
        }
        HashMap<String, Integer> everageMap = null;
        HashMap everageVlueMap = null;
        HashMap<String, String> everageColMap = null;
        Set<String> canSeeSet = sheetInfo.getCanSeeSetMap().get(dataRegionDefine.getKey());
        Map<String, List<CellQueryInfo>> conditions = sheetInfo.getExportOps().getConditions();
        List outListCells = null;
        List inListCells = null;
        if (!CollectionUtils.isEmpty(conditions) && conditions.containsKey(dataRegionDefine.getKey())) {
            List<CellQueryInfo> cellQueryInfos = conditions.get(dataRegionDefine.getKey());
            outListCells = cellQueryInfos.stream().filter(o -> !CollectionUtils.isEmpty(o.getOutList()) && StringUtils.isNotEmpty((String)o.getCellKey())).collect(Collectors.toList());
            inListCells = cellQueryInfos.stream().filter(o -> !CollectionUtils.isEmpty(o.getInList()) && StringUtils.isNotEmpty((String)o.getCellKey())).collect(Collectors.toList());
        }
        int beginCol = curLine;
        int regionRight = dataRegionDefine.getRegionRight();
        int regionLeft = dataRegionDefine.getRegionLeft();
        int moreCol = regionRight - regionLeft;
        boolean deleteRow = true;
        int sheetCount = 0;
        boolean haveData = false;
        HashSet<String> linkCells = new HashSet<String>();
        List rowData = regionDataSet.getRowData();
        boolean filledRegion = this.filledRegion(regionDataSet);
        if (!CollectionUtils.isEmpty(rowData)) {
            IRowData data;
            Iterator iterator = rowData.iterator();
            while (iterator.hasNext() && !(haveData = this.haveData(data = (IRowData)iterator.next(), exportCache, filledRegion, sheetInfo.getExportOps().isEmptyForm()))) {
            }
        }
        if (!haveData) {
            for (IMetaData metaDatum : regionDataSet.getMetaData()) {
                GridCellData gridCell;
                DataLinkDefine dataLinkDefine = metaDatum.getDataLinkDefine();
                if (dataLinkDefine == null || (gridCell = grid2Data.getGridCellData(curLine, dataLinkDefine.getPosY())) == null) continue;
                Grid2DataSetValueUtil.setCellDataType(metaDatum, gridCell, exportCache, sheetInfo);
                if (!sheetInfo.getCustomCellStyles().containsKey(dataLinkDefine.getKey())) continue;
                CustomGridCellStyle customGridCellStyle = sheetInfo.getCustomCellStyles().get(dataLinkDefine.getKey());
                gridCell.setBackGroundColor(ColorUtil.mergeColor((int)gridCell.getBackGroundColor(), (int)customGridCellStyle.getBackGroundColor()));
                gridCell.setBackGroundStyle(customGridCellStyle.getBackGroundStyle());
            }
            return -1;
        }
        int queryPageLimit = this.exportOptionsService.getQueryPageLimit();
        PagerInfo pagerInfo = sheetInfo.getPagerInfo(this.getPagerInfoKey(dataRegionDefine.getKey(), tabTitle), queryPageLimit);
        for (IRowData rowDatum : rowData) {
            if (!sheetInfo.getExportOps().isSumData() && rowDatum.getGroupTreeDeep() >= 0) continue;
            if (sheetCount < this.exportOptionsService.getSheetFloatMax()) {
                IDataValue dataValueByLink;
                ++sheetCount;
                if (!CollectionUtils.isEmpty(outListCells)) {
                    for (CellQueryInfo outListCell : outListCells) {
                        dataValueByLink = rowDatum.getDataValueByLink(outListCell.getCellKey());
                        if (dataValueByLink == null || dataValueByLink.getAsNull()) continue;
                        boolean exist = false;
                        for (String outV : outListCell.getOutList()) {
                            if (outV == null || !outV.equals(dataValueByLink.getAsString())) continue;
                            exist = true;
                        }
                        if (exist) continue;
                        String format = exportCache.getDataValueFormatter(sheetInfo.getFormKey()).format(dataValueByLink);
                        if (outListCell.getExcelInList().contains(format)) continue;
                        outListCell.getExcelInList().add(format);
                    }
                }
                if (!CollectionUtils.isEmpty(inListCells)) {
                    block16: for (CellQueryInfo inListCell : inListCells) {
                        dataValueByLink = rowDatum.getDataValueByLink(inListCell.getCellKey());
                        if (dataValueByLink == null || dataValueByLink.getAsNull()) continue;
                        for (String inV : inListCell.getInList()) {
                            if (inV == null || !inV.equals(dataValueByLink.getAsString())) continue;
                            String format = exportCache.getDataValueFormatter(sheetInfo.getFormKey()).format(dataValueByLink);
                            if (inListCell.getExcelInList().contains(format)) continue block16;
                            inListCell.getExcelInList().add(format);
                            continue block16;
                        }
                    }
                }
                DimensionValueSet rowKey = rowDatum.getDimension().toDimensionValueSet();
                if (canSeeSet != null && !canSeeSet.contains(rowDatum.getRecKey())) {
                    grid2Data.setColumnHidden(curLine, true);
                }
                List linkDataValues = rowDatum.getLinkDataValues();
                int headerRowCount = grid2Data.getHeaderRowCount();
                grid2Data.insertColumns(curLine, 1 + moreCol, curLine);
                grid2Data.copyFrom(grid2Data, curLine + moreCol + 1, dataRegionDefine.getRegionTop(), curLine + moreCol * 2 + 1, dataRegionDefine.getRegionBottom(), curLine, dataRegionDefine.getRegionTop());
                if (curLine == headerRowCount) {
                    grid2Data.setHeaderRowCount(headerRowCount);
                }
                for (IDataValue linkDataValue : linkDataValues) {
                    DataLinkDefine dataLinkDefine = linkDataValue.getMetaData().getDataLinkDefine();
                    if (dataLinkDefine == null) continue;
                    GridCellData gridCell = grid2Data.getGridCellData(curLine + (moreCol > 0 ? dataLinkDefine.getPosX() - regionLeft : 0), dataLinkDefine.getPosY());
                    FormDataServiceImpl.setCacheCellStyle(exportCache, dataLinkDefine, gridCell);
                    String posStr = this.getPosition(curLine, dataLinkDefine.getPosY());
                    linkCells.add(posStr);
                    if (gridCell == null) continue;
                    Object floatData = linkDataValue.getAsObject();
                    if (null != everageMap && null != everageVlueMap && everageVlueMap.containsKey(dataLinkDefine.getKey())) {
                        List valueList = (List)everageVlueMap.get(dataLinkDefine.getKey());
                        if (null != floatData) {
                            valueList.add(Double.valueOf(floatData.toString()));
                        } else {
                            valueList.add(0.0);
                        }
                    }
                    Grid2DataSetValueUtil.converterFieldTypeToGridCellData(gridCell, linkDataValue, grid2Data, exportCache, sheetInfo);
                    if (sheetInfo.getCustomCellStyles().containsKey(dataLinkDefine.getKey())) {
                        CustomGridCellStyle customGridCellStyle = sheetInfo.getCustomCellStyles().get(dataLinkDefine.getKey());
                        gridCell.setBackGroundColor(ColorUtil.mergeColor((int)gridCell.getBackGroundColor(), (int)customGridCellStyle.getBackGroundColor()));
                        gridCell.setBackGroundStyle(customGridCellStyle.getBackGroundStyle());
                    }
                    this.setCsStyle(exportCache, rowKey, dataLinkDefine, gridCell);
                }
            } else {
                sheetInfo.getCurSheetArea().setSplitSheet(true);
            }
            if (regionNumberManager != null) {
                GridCellData gridcell;
                regionNumberManager.setNumberStr("");
                RegionNumber regionNumber = regionNumberManager.getRegionNumber();
                if (null != regionNumber && rowDatum.getGroupTreeDeep() < 0 && (gridcell = grid2Data.getGridCellData(curLine, regionNumber.getRow())) != null) {
                    int squNum = FormDataServiceImpl.getSquNum(rowDatum, regionNumber, pagerInfo);
                    Grid2DataSetValueUtil.converterFieldTypeToGridCellData(gridcell, String.valueOf(squNum));
                }
            }
            ++curLine;
            curLine += moreCol;
        }
        int totalCount = regionDataSet.getRowCount();
        if (totalCount == 0) {
            --curLine;
            deleteRow = false;
        }
        if (moreCol > 0 && deleteRow) {
            grid2Data.deleteColumns(curLine, moreCol + 1);
            grid2Data.insertColumns(curLine, 1);
            Grid2FieldList merges = grid2Data.merges();
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
        if (totalCount > 0) {
            grid2Data.deleteColumns(curLine, 1);
            --curLine;
        }
        if (!CollectionUtils.isEmpty(conditions) && conditions.containsKey(dataRegionDefine.getKey())) {
            FilterRegionCondition filterRegionCondition = new FilterRegionCondition();
            List<CellQueryInfo> cellQueryInfos = conditions.get(dataRegionDefine.getKey());
            for (CellQueryInfo cellQueryInfo : cellQueryInfos) {
                ArrayList<FilterColCondition> filterCols = new ArrayList<FilterColCondition>();
                String cellKey = cellQueryInfo.getCellKey();
                Integer celColIndex = (Integer)linkColMap.get(cellKey);
                String celColIndexString = CellReference.convertNumToColString(celColIndex - 1);
                List<FilterCondition> opList = cellQueryInfo.getOpList();
                List<String> excelInList = cellQueryInfo.getExcelInList();
                String shortcuts = cellQueryInfo.getShortcuts();
                if (null != excelInList && !excelInList.isEmpty()) {
                    for (String value : excelInList) {
                        FilterColCondition colCondition = new FilterColCondition();
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
                    FilterColCondition colCondition = new FilterColCondition();
                    switch (shortcuts) {
                        case "moreThanEverage": {
                            colCondition.setFilterOperator(FilterOperator.ABOVE_AVERAGE);
                            break;
                        }
                        case "lessThanEverage": {
                            colCondition.setFilterOperator(FilterOperator.BELOW_AVERAGE);
                            break;
                        }
                        case "topTen;10": {
                            colCondition.setFilterOperator(FilterOperator.TOP10);
                        }
                    }
                    filterCols.add(colCondition);
                    int indexOf = filterCols.indexOf(colCondition);
                    everageMap.put(cellKey, indexOf);
                    everageVlueMap.put(cellKey, new ArrayList());
                    everageColMap.put(cellKey, celColIndexString);
                } else if (null != opList && opList.size() > 0) {
                    for (FilterCondition filterCondition : opList) {
                        FilterColCondition colCondition = new FilterColCondition();
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
                    filterRegionCondition.setSortCol(celColIndexString);
                    filterRegionCondition.setIsAsc("asc".equals(cellQueryInfo.getSort()));
                }
                if (filterCols.size() <= 0) continue;
                filterRegionCondition.addColFilterCondition(celColIndexString, filterCols);
            }
            this.addExcelFilters(dataRegionDefine, curLine, sheetInfo.getFilters(), conditions, filterRegionCondition, everageMap, everageVlueMap, everageColMap, beginCol);
        }
        return curLine;
    }

    private IRegionDataSet queryRegionData(SheetInfo sheetInfo, DataRegionDefine dataRegionDefine, ExportCache exportCache, List<RowFilter> otherRowFilters) {
        return this.queryRegionData(sheetInfo, dataRegionDefine, exportCache, null, otherRowFilters, true, null);
    }

    private IRegionDataSet queryRegionData(SheetInfo sheetInfo, DataRegionDefine dataRegionDefine, ExportCache exportCache, List<String> selectLinkKey, List<RowFilter> otherRowFilters, boolean sorted, PageInfo pageInfo) {
        IRegionDataSetProvider regionDataSetProvider;
        if (dataRegionDefine.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE && StringUtils.isEmpty((String)exportCache.getDataSnapshotId()) && this.exportOptionsService.getMemPerfLevel() != 0) {
            try {
                MultiDimensionalDataSet multiDimensionalDataSet = exportCache.getMultiDimensionalDataSet(dataRegionDefine.getKey(), sheetInfo);
                if (multiDimensionalDataSet == null) {
                    return null;
                }
                sheetInfo.setMultiDimensionalDataSet(multiDimensionalDataSet);
                return multiDimensionalDataSet.getRegionDataSet(sheetInfo.getDimensionCombination());
            }
            catch (Exception e) {
                sheetInfo.stopWrite();
                logger.error(e.getMessage(), e);
                return null;
            }
        }
        Map<String, List<RowFilter>> rowFilter = sheetInfo.getExportOps().getRowFilter();
        Map<String, List<LinkSort>> linkSort = sheetInfo.getExportOps().getLinkSort();
        QueryInfoBuilder queryInfoBuilder = new QueryInfoBuilder(dataRegionDefine.getKey(), sheetInfo.getDimensionCombination()).whereRegionFilter();
        queryInfoBuilder.setDesensitized(true);
        if (selectLinkKey != null) {
            selectLinkKey.forEach(arg_0 -> ((QueryInfoBuilder)queryInfoBuilder).select(arg_0));
        }
        if (rowFilter.containsKey(dataRegionDefine.getKey())) {
            rowFilter.get(dataRegionDefine.getKey()).forEach(arg_0 -> ((QueryInfoBuilder)queryInfoBuilder).where(arg_0));
        }
        if (!CollectionUtils.isEmpty(otherRowFilters)) {
            otherRowFilters.forEach(arg_0 -> ((QueryInfoBuilder)queryInfoBuilder).where(arg_0));
        }
        if (sorted) {
            List<LinkSort> linkSorts;
            FilterSort filterSort;
            if (linkSort.containsKey(dataRegionDefine.getKey())) {
                linkSort.get(dataRegionDefine.getKey()).forEach(arg_0 -> ((QueryInfoBuilder)queryInfoBuilder).orderBy(arg_0));
            }
            if ((filterSort = exportCache.getFilterSort(sheetInfo.getDimensionCombination())) != null && !CollectionUtils.isEmpty(filterSort.getLinkSort()) && !CollectionUtils.isEmpty(linkSorts = filterSort.getLinkSort().get(dataRegionDefine.getKey()))) {
                linkSorts.forEach(arg_0 -> ((QueryInfoBuilder)queryInfoBuilder).orderBy(arg_0));
            }
        }
        if (sheetInfo.getExportOps().getExportMeasureSetting() != null) {
            queryInfoBuilder.setMeasure(sheetInfo.getExportOps().getExportMeasureSetting().toMeasure());
        }
        queryInfoBuilder.setFormulaSchemeKey(sheetInfo.getExportOps().getFormulaSchemeKey());
        if (pageInfo != null) {
            queryInfoBuilder.setPage(pageInfo);
        }
        IQueryInfo queryInfo = queryInfoBuilder.build();
        DataQueryPar dataQueryPar = new DataQueryPar();
        dataQueryPar.setQueryInfo(queryInfo);
        RegionGradeInfo gradeInfo = sheetInfo.getRegionGradeInfo(dataRegionDefine.getKey(), this.regionRelationFactory);
        dataQueryPar.setRegionGradeInfo(gradeInfo);
        if (StringUtils.isNotEmpty((String)exportCache.getDataSnapshotId())) {
            regionDataSetProvider = this.regionDataSetProviderFactory.getRegionDataSetProvider("snapshotRegionDataSetProvider");
            dataQueryPar.setSnapshotId(exportCache.getDataSnapshotId());
        } else {
            regionDataSetProvider = this.regionDataSetProviderFactory.getRegionDataSetProvider("defaultRegionDataSetProvider");
        }
        return regionDataSetProvider.getRegionDataSet(dataQueryPar);
    }

    private RegionDataSetPageLoader getRegionDataSetPageLoader(SheetInfo sheetInfo, DataRegionDefine dataRegionDefine, ExportCache exportCache, List<RowFilter> otherRowFilters, int rowsPerPage) {
        IRegionDataSetProvider regionDataSetProvider;
        List<LinkSort> linkSorts;
        FilterSort filterSort;
        Map<String, List<RowFilter>> rowFilter = sheetInfo.getExportOps().getRowFilter();
        Map<String, List<LinkSort>> linkSort = sheetInfo.getExportOps().getLinkSort();
        QueryInfoBuilder queryInfoBuilder = new QueryInfoBuilder(dataRegionDefine.getKey(), sheetInfo.getDimensionCombination()).whereRegionFilter();
        queryInfoBuilder.setDesensitized(true);
        if (rowFilter.containsKey(dataRegionDefine.getKey())) {
            rowFilter.get(dataRegionDefine.getKey()).forEach(arg_0 -> ((QueryInfoBuilder)queryInfoBuilder).where(arg_0));
        }
        if (!CollectionUtils.isEmpty(otherRowFilters)) {
            otherRowFilters.forEach(arg_0 -> ((QueryInfoBuilder)queryInfoBuilder).where(arg_0));
        }
        if (linkSort.containsKey(dataRegionDefine.getKey())) {
            linkSort.get(dataRegionDefine.getKey()).forEach(arg_0 -> ((QueryInfoBuilder)queryInfoBuilder).orderBy(arg_0));
        }
        if ((filterSort = exportCache.getFilterSort(sheetInfo.getDimensionCombination())) != null && !CollectionUtils.isEmpty(filterSort.getLinkSort()) && !CollectionUtils.isEmpty(linkSorts = filterSort.getLinkSort().get(dataRegionDefine.getKey()))) {
            linkSorts.forEach(arg_0 -> ((QueryInfoBuilder)queryInfoBuilder).orderBy(arg_0));
        }
        if (sheetInfo.getExportOps().getExportMeasureSetting() != null) {
            queryInfoBuilder.setMeasure(sheetInfo.getExportOps().getExportMeasureSetting().toMeasure());
        }
        queryInfoBuilder.setFormulaSchemeKey(sheetInfo.getExportOps().getFormulaSchemeKey());
        String snapshotId = null;
        if (StringUtils.isNotEmpty((String)exportCache.getDataSnapshotId())) {
            snapshotId = exportCache.getDataSnapshotId();
            regionDataSetProvider = this.regionDataSetProviderFactory.getRegionDataSetProvider("snapshotRegionDataSetProvider");
        } else {
            regionDataSetProvider = this.regionDataSetProviderFactory.getRegionDataSetProvider("defaultRegionDataSetProvider");
        }
        RegionGradeInfo gradeInfo = sheetInfo.getRegionGradeInfo(dataRegionDefine.getKey(), this.regionRelationFactory);
        return new RegionDataSetPageLoader(queryInfoBuilder, gradeInfo, snapshotId, regionDataSetProvider, rowsPerPage);
    }

    private void exportLinkCell(Grid2Data grid2Data, SheetInfo sheetInfo, ExportCache exportCache, Map<String, Integer> addRows) {
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
                    String dataLinkKey = (String)formKeyLinkKey.get("dataLinkKey");
                    if (null != dataLinkKey && !"".equals(dataLinkKey)) {
                        DataLinkDefine dataLink = exportCache.getDataLink(dataLinkKey);
                        List<DataRegionDefine> sortedRegionsByForm = exportCache.getSortedRegionsByForm(formKey);
                        int toRow = dataLink.getPosY();
                        for (DataRegionDefine dataRegionDefine : sortedRegionsByForm) {
                            if (DataRegionKind.DATA_REGION_SIMPLE == dataRegionDefine.getRegionKind()) continue;
                            toRow += addRows.get(dataRegionDefine.getKey()).intValue();
                        }
                        cellLink.setToCol(dataLink.getPosX());
                        cellLink.setToRow(toRow);
                    }
                    FormDefine formByKey = exportCache.getFormByKey(formKey);
                    String sheetName = formByKey.getFormCode() + " " + formByKey.getTitle();
                    String excelName = sheetInfo.getExcelInfo() == null ? null : sheetInfo.getExcelInfo().getExcelName();
                    SheetNameParam sheetNameParam = new SheetNameParam(sheetInfo.getDimensionCombination(), sheetInfo.getFormKey(), 0, excelName);
                    String sheetNameValidate = ExportUtil.sheetNameValidate(sheetName, exportCache.getSheetNameProvider(), sheetNameParam);
                    if (!sheetNameValidate.equals(sheetName)) {
                        sheetName = sheetNameValidate;
                    }
                    cellLink.setToSheetName(sheetName);
                }
                sheetInfo.getLinks().add(cellLink);
            }
        }
    }

    private boolean exportFixRegion(Grid2Data grid2Data, SheetInfo sheetInfo, DataRegionDefine dataRegionDefine, ExportCache exportCache) {
        boolean haveData;
        IRegionDataSet regionDataSet = this.queryRegionData(sheetInfo, dataRegionDefine, exportCache, null);
        if (regionDataSet != null) {
            for (IMetaData metaDatum : regionDataSet.getMetaData()) {
                GridCellData cell;
                DataLinkDefine dataLinkDefine = metaDatum.getDataLinkDefine();
                if (dataLinkDefine == null || (cell = grid2Data.getGridCellData(dataLinkDefine.getPosX(), dataLinkDefine.getPosY())) == null) continue;
                Grid2DataSetValueUtil.setCellDataType(metaDatum, cell, exportCache, sheetInfo);
                if (sheetInfo.getCustomCellStyles().containsKey(dataLinkDefine.getKey())) {
                    CustomGridCellStyle customGridCellStyle = sheetInfo.getCustomCellStyles().get(dataLinkDefine.getKey());
                    cell.setBackGroundColor(ColorUtil.mergeColor((int)cell.getBackGroundColor(), (int)customGridCellStyle.getBackGroundColor()));
                    cell.setBackGroundStyle(customGridCellStyle.getBackGroundStyle());
                }
                this.setCsStyle(exportCache, null, dataLinkDefine, cell);
            }
        }
        if (regionDataSet == null || regionDataSet.getRowCount() == 0) {
            return false;
        }
        List fixData = new ArrayList();
        List rowData = regionDataSet.getRowData();
        if (!CollectionUtils.isEmpty(rowData)) {
            fixData = ((IRowData)rowData.get(0)).getLinkDataValues();
        }
        if (haveData = this.haveData((IRowData)rowData.get(0), exportCache, this.filledRegion(regionDataSet), sheetInfo.getExportOps().isEmptyForm())) {
            for (IDataValue fixDatum : fixData) {
                GridCellData cell;
                IMetaData metaData;
                DataLinkDefine dataLinkDefine;
                if (fixDatum == null || (dataLinkDefine = (metaData = fixDatum.getMetaData()).getDataLinkDefine()) == null || (cell = grid2Data.getGridCellData(dataLinkDefine.getPosX(), dataLinkDefine.getPosY())) == null) continue;
                Grid2DataSetValueUtil.converterFieldTypeToGridCellData(cell, fixDatum, grid2Data, exportCache, sheetInfo);
            }
        }
        return haveData;
    }

    private int exportRowFloatRegion(Grid2Data grid2Data, SheetInfo sheetInfo, DataRegionDefine dataRegionDefine, int lastLineNum, ExportCache exportCache) {
        int curLine;
        List rowNumberSetting;
        RegionNumberManager regionNumberManager = null;
        RegionSettingDefine regionSetting = this.runTimeViewController.getRegionSetting(dataRegionDefine.getKey());
        if (regionSetting != null && null != (rowNumberSetting = regionSetting.getRowNumberSetting()) && rowNumberSetting.size() > 0) {
            RegionNumber regionNumber = new RegionNumber((RowNumberSetting)rowNumberSetting.get(0));
            regionNumberManager = new RegionNumberManager(regionNumber);
        }
        int regionStart = curLine = dataRegionDefine.getRegionTop() + lastLineNum;
        Map<String, List<String>> tabs = sheetInfo.getExportOps().getTabs();
        List<String> tabTitles = tabs.get(dataRegionDefine.getKey());
        List<RegionTabSettingDefine> tabsByRegionCopy = FormDataServiceImpl.getSelectRegionTabs(dataRegionDefine, exportCache, tabTitles);
        ArrayList<RegionTabSettingDefine> tabsByRegion = new ArrayList<RegionTabSettingDefine>(tabsByRegionCopy);
        if (tabsByRegion != null && !tabsByRegion.isEmpty()) {
            DimensionValueSet dimensionValueSet = sheetInfo.getDimensionCombination().toDimensionValueSet();
            FormDefine queryFormById = this.runTimeViewController.queryFormById(sheetInfo.getFormKey());
            FormulaCallBack callback = new FormulaCallBack();
            ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
            ReportFmlExecEnvironment environment = exportCache.getFmlExecEnv(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController);
            FileFieldValueProcessor fileFieldValueProcessor = new FileFieldValueProcessor();
            environment.setFieldValueUpdateProcessor((IFieldValueUpdateProcessor)fileFieldValueProcessor);
            context.setEnv((IFmlExecEnvironment)environment);
            context.setDefaultGroupName(queryFormById.getFormCode());
            for (RegionTabSettingDefine regionTabSettingDefine : tabsByRegion) {
                if (!StringUtils.isNotEmpty((String)regionTabSettingDefine.getDisplayCondition())) continue;
                String styleExpression = regionTabSettingDefine.getDisplayCondition();
                Formula formula = new Formula();
                formula.setId(regionTabSettingDefine.getId());
                formula.setFormula(styleExpression);
                formula.setChecktype(Integer.valueOf(FormulaCheckType.FORMULA_CHECK_ERROR.getValue()));
                formula.setFormKey(queryFormById.getKey());
                formula.setReportName(queryFormById.getFormCode());
                callback.getFormulas().add(formula);
            }
            IFormulaRunner runner = this.dataAccessProvider.newFormulaRunner(callback);
            BatchCSConditionMonitor monitor = new BatchCSConditionMonitor();
            try {
                runner.prepareCheck(context, dimensionValueSet, (IMonitor)monitor);
                runner.run((IMonitor)monitor);
                Map<String, Set<DimensionValueSet>> result = monitor.getResult();
                Iterator iterator = tabsByRegion.iterator();
                while (iterator.hasNext()) {
                    RegionTabSettingDefine regionTabSettingDefine = (RegionTabSettingDefine)iterator.next();
                    if (!result.containsKey(regionTabSettingDefine.getId())) continue;
                    iterator.remove();
                }
            }
            catch (Exception e) {
                sheetInfo.stopWrite();
                logger.error("\u89e3\u6790\u516c\u5f0f\u5931\u8d25\uff1a{},{}", (Object)e.getMessage(), (Object)e);
            }
        }
        boolean regionHaveData = false;
        int queryPageLimit = this.exportOptionsService.getQueryPageLimit();
        if (CollectionUtils.isEmpty(tabsByRegion)) {
            PagerInfo pagerInfo = sheetInfo.getPagerInfo(this.getPagerInfoKey(dataRegionDefine.getKey(), null), queryPageLimit);
            RegionDataSetPageLoader regionDataSetPageLoader = this.getRegionDataSetPageLoader(sheetInfo, dataRegionDefine, exportCache, null, pagerInfo.getLimit());
            regionDataSetPageLoader.skipPages(pagerInfo.getOffset());
            regionDataSetPageLoader.next();
            IRegionDataSet regionDataSet = regionDataSetPageLoader.getCurRegionDataSet();
            if (regionDataSet != null && regionDataSet.getRowCount() != 0) {
                if ((curLine = this.setDataMethod(grid2Data, sheetInfo, regionDataSetPageLoader, exportCache, dataRegionDefine, curLine, regionNumberManager, null)) != -1) {
                    regionHaveData = true;
                }
            } else {
                GridCellData gridCell;
                if (regionNumberManager != null && null != regionNumberManager.getRegionNumber() && (gridCell = grid2Data.getGridCellData(regionNumberManager.getRegionNumber().getColumn(), curLine)) != null) {
                    Grid2DataSetValueUtil.converterFieldTypeToGridCellData(gridCell, String.valueOf(regionNumberManager.getRegionNumber().getStart()));
                }
                if (regionDataSet != null) {
                    for (IMetaData metaDatum : regionDataSet.getMetaData()) {
                        GridCellData gridCell2;
                        DataLinkDefine dataLinkDefine = metaDatum.getDataLinkDefine();
                        if (dataLinkDefine == null || (gridCell2 = grid2Data.getGridCellData(dataLinkDefine.getPosX(), curLine)) == null) continue;
                        Grid2DataSetValueUtil.setCellDataType(metaDatum, gridCell2, exportCache, sheetInfo);
                        if (!sheetInfo.getCustomCellStyles().containsKey(dataLinkDefine.getKey())) continue;
                        CustomGridCellStyle customGridCellStyle = sheetInfo.getCustomCellStyles().get(dataLinkDefine.getKey());
                        gridCell2.setBackGroundColor(ColorUtil.mergeColor((int)gridCell2.getBackGroundColor(), (int)customGridCellStyle.getBackGroundColor()));
                        gridCell2.setBackGroundStyle(customGridCellStyle.getBackGroundStyle());
                    }
                }
                pagerInfo.setTotal(Integer.MAX_VALUE);
            }
            GridArea gridArea = new GridArea(dataRegionDefine);
            gridArea.setRealRegion(dataRegionDefine.getRegionLeft(), regionStart, dataRegionDefine.getRegionRight(), curLine);
            gridArea.setRowCount((curLine - regionStart + 1) / gridArea.getRowSpan());
            sheetInfo.getCurSheetArea().addGridArea(gridArea);
        } else {
            for (RegionTabSettingDefine tab : tabsByRegion) {
                String tabTitle = tab.getTitle();
                PagerInfo pagerInfo = sheetInfo.getPagerInfo(this.getPagerInfoKey(dataRegionDefine.getKey(), tabTitle), queryPageLimit);
                if (regionNumberManager != null) {
                    regionNumberManager.resetNumber();
                }
                int tabStart = curLine;
                if (tabsByRegion.size() > 1) {
                    grid2Data.insertRows(curLine, 1, curLine);
                    GridCellData gridCellTab = grid2Data.getGridCellData(1, curLine);
                    gridCellTab.setShowText(tabTitle);
                    Grid2CellField grid2CellField = new Grid2CellField(1, gridCellTab.getRowIndex(), grid2Data.getColumnCount() - 1, gridCellTab.getRowIndex());
                    grid2Data.merges().addMergeRect(grid2CellField);
                    ++curLine;
                    GridCellStyleData cellStyleData = new GridCellStyleData();
                    cellStyleData.setHorzAlign(GridEnums.TextAlignment.Fore.ordinal());
                    gridCellTab.setCellStyleData(cellStyleData);
                    gridCellTab.setBottomBorderStyle(GridEnums.GridBorderStyle.SOLID.getStyle());
                }
                FormulaFilter formulaFilter = null;
                if (StringUtils.isNotEmpty((String)tab.getFilterCondition())) {
                    formulaFilter = new FormulaFilter(tab.getFilterCondition());
                }
                RegionDataSetPageLoader regionDataSetPageLoader = this.getRegionDataSetPageLoader(sheetInfo, dataRegionDefine, exportCache, Collections.singletonList(formulaFilter), pagerInfo.getLimit());
                regionDataSetPageLoader.skipPages(pagerInfo.getOffset());
                regionDataSetPageLoader.next();
                IRegionDataSet regionDataSet = regionDataSetPageLoader.getCurRegionDataSet();
                if (regionDataSet != null && regionDataSet.getRowCount() != 0) {
                    if ((curLine = this.setDataMethod(grid2Data, sheetInfo, regionDataSetPageLoader, exportCache, dataRegionDefine, curLine, regionNumberManager, tabTitle)) != -1) {
                        regionHaveData = true;
                    }
                    grid2Data.insertRows(curLine + 1, 1, curLine);
                    ++curLine;
                } else {
                    GridCellData gridCell;
                    grid2Data.insertRows(curLine, 1, curLine);
                    if (regionNumberManager != null && null != regionNumberManager.getRegionNumber() && (gridCell = grid2Data.getGridCellData(regionNumberManager.getRegionNumber().getColumn(), curLine)) != null) {
                        Grid2DataSetValueUtil.converterFieldTypeToGridCellData(gridCell, String.valueOf(regionNumberManager.getRegionNumber().getStart()));
                    }
                    if (regionDataSet != null) {
                        gridCell = regionDataSet.getMetaData().iterator();
                        while (gridCell.hasNext()) {
                            GridCellData gridCell3;
                            IMetaData metaDatum = (IMetaData)gridCell.next();
                            DataLinkDefine dataLinkDefine = metaDatum.getDataLinkDefine();
                            if (dataLinkDefine == null || (gridCell3 = grid2Data.getGridCellData(dataLinkDefine.getPosX(), curLine)) == null) continue;
                            Grid2DataSetValueUtil.setCellDataType(metaDatum, gridCell3, exportCache, sheetInfo);
                            if (!sheetInfo.getCustomCellStyles().containsKey(dataLinkDefine.getKey())) continue;
                            CustomGridCellStyle customGridCellStyle = sheetInfo.getCustomCellStyles().get(dataLinkDefine.getKey());
                            gridCell3.setBackGroundColor(ColorUtil.mergeColor((int)gridCell3.getBackGroundColor(), (int)customGridCellStyle.getBackGroundColor()));
                            gridCell3.setBackGroundStyle(customGridCellStyle.getBackGroundStyle());
                        }
                    }
                    ++curLine;
                    pagerInfo.setTotal(Integer.MAX_VALUE);
                }
                String startAndEndS = tabStart + ";" + (curLine - 2);
                sheetInfo.getStartAndEndList().add(startAndEndS);
                GridArea gridArea = new GridArea(dataRegionDefine);
                gridArea.setRealRegion(dataRegionDefine.getRegionLeft(), tabStart + 1, dataRegionDefine.getRegionRight(), curLine - 2 + 1);
                gridArea.setRowCount((curLine - 2 - tabStart + 1) / gridArea.getRowSpan());
                sheetInfo.getCurSheetArea().addGridArea(gridArea);
            }
            grid2Data.deleteRows(curLine, 1);
            --curLine;
        }
        if (sheetInfo.getExportOps().isSumData() && regionHaveData && dataRegionDefine.getShowGatherSummaryRow()) {
            GridCellData gridCellData = StringUtils.isEmpty((String)dataRegionDefine.getShowAddress()) ? grid2Data.getGridCellData(1, dataRegionDefine.getRegionTop() + lastLineNum) : Grid2DataSetValueUtil.locateGridCell(grid2Data, dataRegionDefine.getShowAddress());
            gridCellData.setShowText("\u5408\u8ba1");
            gridCellData.setEditText("\u5408\u8ba1");
            gridCellData.setHorzAlign(3);
        }
        return regionHaveData ? curLine - regionStart - (dataRegionDefine.getRegionBottom() - dataRegionDefine.getRegionTop()) : -1;
    }

    @NotNull
    private static List<RegionTabSettingDefine> getSelectRegionTabs(DataRegionDefine dataRegionDefine, ExportCache exportCache, List<String> tabTitles) {
        List<RegionTabSettingDefine> allRegionTabs;
        ArrayList<RegionTabSettingDefine> tabsByRegion = new ArrayList<RegionTabSettingDefine>();
        if (!CollectionUtils.isEmpty(tabTitles) && !CollectionUtils.isEmpty(allRegionTabs = exportCache.getTabsByRegion(dataRegionDefine.getKey()))) {
            Map collect = allRegionTabs.stream().collect(Collectors.toMap(RegionTabSettingDefine::getTitle, Function.identity(), (o1, o2) -> o1));
            for (String tabTitle : tabTitles) {
                if (!collect.containsKey(tabTitle)) continue;
                tabsByRegion.add((RegionTabSettingDefine)collect.get(tabTitle));
            }
        }
        return tabsByRegion;
    }

    private int setDataMethod(Grid2Data grid2Data, SheetInfo sheetInfo, RegionDataSetPageLoader regionDataSetPageLoader, ExportCache exportCache, DataRegionDefine dataRegionDefine, int curLine, RegionNumberManager regionNumberManager, String tabTitle) {
        Grid2Data tempGridData;
        Grid2FieldList merges;
        String posStr;
        HashMap<String, Integer> everageMap = null;
        HashMap everageVlueMap = null;
        Set<String> canSeeSet = sheetInfo.getCanSeeSetMap().get(dataRegionDefine.getKey());
        Map<String, List<CellQueryInfo>> conditions = sheetInfo.getExportOps().getConditions();
        List outListCells = null;
        List inListCells = null;
        if (!CollectionUtils.isEmpty(conditions) && conditions.containsKey(dataRegionDefine.getKey())) {
            List<CellQueryInfo> cellQueryInfos = conditions.get(dataRegionDefine.getKey());
            outListCells = cellQueryInfos.stream().filter(o -> !CollectionUtils.isEmpty(o.getOutList()) && StringUtils.isNotEmpty((String)o.getCellKey())).collect(Collectors.toList());
            inListCells = cellQueryInfos.stream().filter(o -> !CollectionUtils.isEmpty(o.getInList()) && StringUtils.isNotEmpty((String)o.getCellKey())).collect(Collectors.toList());
        }
        int beginRow = curLine;
        int regionTop = dataRegionDefine.getRegionTop();
        int regionBottom = dataRegionDefine.getRegionBottom();
        int moreRow = regionBottom - regionTop;
        boolean deleteRow = true;
        int sheetCount = 0;
        boolean allHaveData = false;
        int totalCount = regionDataSetPageLoader.getCurRegionDataSet().getTotalCount();
        HashSet<String> linkCells = new HashSet<String>();
        int queryPageLimit = this.exportOptionsService.getQueryPageLimit();
        PagerInfo pagerInfo = sheetInfo.getPagerInfo(this.getPagerInfoKey(dataRegionDefine.getKey(), tabTitle), queryPageLimit);
        block10: do {
            if (sheetCount + 1 <= this.exportOptionsService.getSheetFloatMax()) {
                pagerInfo.setOffset(pagerInfo.getOffset() + 1);
            }
            boolean haveData = false;
            IRegionDataSet regionDataSet = regionDataSetPageLoader.getCurRegionDataSet();
            if (regionDataSet == null || regionDataSet.getRowCount() == 0) break;
            List rowData = regionDataSet.getRowData();
            boolean filledRegion = this.filledRegion(regionDataSet);
            if (!CollectionUtils.isEmpty(rowData)) {
                IRowData data;
                Iterator iterator = rowData.iterator();
                while (iterator.hasNext() && !(haveData = this.haveData(data = (IRowData)iterator.next(), exportCache, filledRegion, sheetInfo.getExportOps().isEmptyForm()))) {
                }
            }
            if (!haveData) {
                for (IMetaData metaDatum : regionDataSet.getMetaData()) {
                    DataLinkDefine dataLinkDefine = metaDatum.getDataLinkDefine();
                    if (dataLinkDefine == null) continue;
                    GridCellData gridCell = grid2Data.getGridCellData(dataLinkDefine.getPosX(), curLine);
                    String posStr2 = this.getPosition(dataLinkDefine.getPosX(), curLine);
                    linkCells.add(posStr2);
                    if (gridCell == null) continue;
                    Grid2DataSetValueUtil.setCellDataType(metaDatum, gridCell, exportCache, sheetInfo);
                    if (!sheetInfo.getCustomCellStyles().containsKey(dataLinkDefine.getKey())) continue;
                    Iterator<Object> customGridCellStyle = sheetInfo.getCustomCellStyles().get(dataLinkDefine.getKey());
                    gridCell.setBackGroundColor(ColorUtil.mergeColor((int)gridCell.getBackGroundColor(), (int)((CustomGridCellStyle)((Object)customGridCellStyle)).getBackGroundColor()));
                    gridCell.setBackGroundStyle(((CustomGridCellStyle)((Object)customGridCellStyle)).getBackGroundStyle());
                }
            } else {
                for (IRowData rowDatum : rowData) {
                    if (++sheetCount <= this.exportOptionsService.getSheetFloatMax()) {
                        IDataValue dataValueByLink;
                        if (!sheetInfo.getExportOps().isSumData() && rowDatum.getGroupTreeDeep() >= 0) continue;
                        DimensionValueSet rowKey = rowDatum.getDimension().toDimensionValueSet();
                        List linkDataValues = rowDatum.getLinkDataValues();
                        int headerRowCount = grid2Data.getHeaderRowCount();
                        grid2Data.insertRows(curLine, 1 + moreRow, curLine, moreRow <= 0);
                        grid2Data.copyFrom(grid2Data, dataRegionDefine.getRegionLeft(), curLine + moreRow + 1, dataRegionDefine.getRegionRight(), curLine + moreRow * 2 + 1, dataRegionDefine.getRegionLeft(), curLine);
                        if (curLine == headerRowCount) {
                            grid2Data.setHeaderRowCount(headerRowCount);
                        }
                        if (!CollectionUtils.isEmpty(outListCells)) {
                            for (CellQueryInfo outListCell : outListCells) {
                                dataValueByLink = rowDatum.getDataValueByLink(outListCell.getCellKey());
                                if (dataValueByLink == null || dataValueByLink.getAsNull()) continue;
                                boolean exist = false;
                                for (String outV : outListCell.getOutList()) {
                                    if (outV == null || !outV.equals(dataValueByLink.getAsString())) continue;
                                    exist = true;
                                }
                                if (exist) continue;
                                String format = exportCache.getDataValueFormatter(sheetInfo.getFormKey()).format(dataValueByLink);
                                if (outListCell.getExcelInList().contains(format)) continue;
                                outListCell.getExcelInList().add(format);
                            }
                        }
                        if (!CollectionUtils.isEmpty(inListCells)) {
                            block16: for (CellQueryInfo inListCell : inListCells) {
                                dataValueByLink = rowDatum.getDataValueByLink(inListCell.getCellKey());
                                if (dataValueByLink == null || dataValueByLink.getAsNull()) continue;
                                for (String inV : inListCell.getInList()) {
                                    if (inV == null || !inV.equals(dataValueByLink.getAsString())) continue;
                                    String format = exportCache.getDataValueFormatter(sheetInfo.getFormKey()).format(dataValueByLink);
                                    if (inListCell.getExcelInList().contains(format)) continue block16;
                                    inListCell.getExcelInList().add(format);
                                    continue block16;
                                }
                            }
                        }
                        if (canSeeSet != null && !canSeeSet.contains(rowDatum.getRecKey())) {
                            grid2Data.setRowHidden(curLine, true);
                        }
                        for (IDataValue linkDataValue : linkDataValues) {
                            DataLinkDefine dataLinkDefine = linkDataValue.getMetaData().getDataLinkDefine();
                            if (dataLinkDefine == null) continue;
                            GridCellData gridCell = grid2Data.getGridCellData(dataLinkDefine.getPosX(), curLine + (moreRow > 0 ? dataLinkDefine.getPosY() - regionTop : 0));
                            FormDataServiceImpl.setCacheCellStyle(exportCache, dataLinkDefine, gridCell);
                            posStr = this.getPosition(dataLinkDefine.getPosX(), curLine);
                            linkCells.add(posStr);
                            if (gridCell == null) continue;
                            Object floatData = linkDataValue.getAsObject();
                            if (null != everageMap && null != everageVlueMap && everageVlueMap.containsKey(dataLinkDefine.getKey())) {
                                List valueList = (List)everageVlueMap.get(dataLinkDefine.getKey());
                                if (null != floatData) {
                                    valueList.add(Double.valueOf(floatData.toString()));
                                } else {
                                    valueList.add(0.0);
                                }
                            }
                            if (sheetInfo.getExportOps().isSumData() && floatData != null && (floatData.equals("\u2014\u2014") || floatData.equals("\u5408\u8ba1"))) {
                                gridCell.setShowText(floatData.toString());
                                gridCell.setEditText(floatData.toString());
                                gridCell.setHorzAlign(3);
                            } else {
                                Grid2DataSetValueUtil.converterFieldTypeToGridCellData(gridCell, linkDataValue, grid2Data, exportCache, sheetInfo);
                            }
                            if (sheetInfo.getCustomCellStyles().containsKey(dataLinkDefine.getKey())) {
                                CustomGridCellStyle customGridCellStyle = sheetInfo.getCustomCellStyles().get(dataLinkDefine.getKey());
                                gridCell.setBackGroundColor(ColorUtil.mergeColor((int)gridCell.getBackGroundColor(), (int)customGridCellStyle.getBackGroundColor()));
                                gridCell.setBackGroundStyle(customGridCellStyle.getBackGroundStyle());
                            }
                            this.setCsStyle(exportCache, rowKey, dataLinkDefine, gridCell);
                        }
                        if (regionNumberManager != null) {
                            GridCellData gridcell;
                            regionNumberManager.setNumberStr("");
                            RegionNumber regionNumber = regionNumberManager.getRegionNumber();
                            if (null != regionNumber && rowDatum.getGroupTreeDeep() < 0 && (gridcell = grid2Data.getGridCellData(regionNumber.getColumn(), curLine)) != null) {
                                int squNum = FormDataServiceImpl.getSquNum(rowDatum, regionNumber, pagerInfo);
                                Grid2DataSetValueUtil.converterFieldTypeToGridCellData(gridcell, String.valueOf(squNum));
                            }
                        }
                        allHaveData = true;
                        ++curLine;
                        curLine += moreRow;
                        continue;
                    }
                    sheetInfo.getCurSheetArea().setSplitSheet(true);
                    continue block10;
                }
            }
        } while (sheetCount <= this.exportOptionsService.getSheetFloatMax() && regionDataSetPageLoader.next());
        if (regionDataSetPageLoader.isExistDataNext()) {
            pagerInfo.setTotal(1);
        } else {
            pagerInfo.setTotal(Integer.MAX_VALUE);
        }
        RunTimeExtentStyleService extentStyleService = (RunTimeExtentStyleService)BeanUtil.getBean(RunTimeExtentStyleService.class);
        ExtentStyle extentStyle = extentStyleService.getExtentStyle(dataRegionDefine.getKey());
        if (extentStyle != null && (merges = (tempGridData = extentStyle.getGriddata()).merges()).count() > 0) {
            int regionLeft = dataRegionDefine.getRegionLeft();
            for (int i = 0; i < merges.count(); ++i) {
                Grid2CellField cell = merges.get(i);
                grid2Data.merges().addMergeRect(merges.get(i));
                for (int j = cell.left; j <= cell.right; ++j) {
                    for (int h = cell.top; h <= cell.bottom; ++h) {
                        int row = h + regionTop - 1;
                        int col = j + regionLeft - 1;
                        GridCellData cellData = grid2Data.getGridCellData(col, row);
                        GridCellData tempCellData = tempGridData.getGridCellData(j, h);
                        posStr = this.getPosition(col, row);
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
        if (totalCount == 0) {
            --curLine;
            deleteRow = false;
        }
        if (moreRow > 0 && deleteRow) {
            grid2Data.deleteRows(curLine, moreRow + 1);
            grid2Data.insertRows(curLine, 1);
            Grid2FieldList merges2 = grid2Data.merges();
            ArrayList<Grid2CellField> removeList = new ArrayList<Grid2CellField>();
            for (int i = 0; i < merges2.count(); ++i) {
                Grid2CellField grid2CellField = merges2.get(i);
                if (null == grid2CellField || grid2CellField.top < grid2CellField.bottom || grid2CellField.left < grid2CellField.right) continue;
                removeList.add(grid2CellField);
            }
            for (Grid2CellField grid2CellField : removeList) {
                merges2.remove(grid2CellField);
            }
        }
        if (totalCount > 0) {
            grid2Data.deleteRows(curLine, 1);
            --curLine;
        }
        if (!CollectionUtils.isEmpty(conditions) && conditions.containsKey(dataRegionDefine.getKey())) {
            HashMap<String, String> everageColMap = null;
            HashMap<String, Integer> linkColMap = new HashMap<String, Integer>();
            List<DataLinkDefine> allLinksInRegion = exportCache.getAllLinksInRegion(dataRegionDefine.getKey());
            for (DataLinkDefine dataLinkDefine : allLinksInRegion) {
                if (dataLinkDefine == null) continue;
                linkColMap.put(dataLinkDefine.getKey(), dataLinkDefine.getPosX());
            }
            FilterRegionCondition filterRegionCondition = new FilterRegionCondition();
            List<CellQueryInfo> cellQueryInfos = conditions.get(dataRegionDefine.getKey());
            for (CellQueryInfo cellQueryInfo : cellQueryInfos) {
                ArrayList<FilterColCondition> filterCols = new ArrayList<FilterColCondition>();
                String cellKey = cellQueryInfo.getCellKey();
                Integer celColIndex = (Integer)linkColMap.get(cellKey);
                String celColIndexString = CellReference.convertNumToColString(celColIndex - 1);
                List<FilterCondition> opList = cellQueryInfo.getOpList();
                List<String> excelInList = cellQueryInfo.getExcelInList();
                String shortcuts = cellQueryInfo.getShortcuts();
                if (null != excelInList && !excelInList.isEmpty()) {
                    for (String value : excelInList) {
                        FilterColCondition colCondition = new FilterColCondition();
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
                    FilterColCondition colCondition = new FilterColCondition();
                    switch (shortcuts) {
                        case "moreThanEverage": {
                            colCondition.setFilterOperator(FilterOperator.ABOVE_AVERAGE);
                            break;
                        }
                        case "lessThanEverage": {
                            colCondition.setFilterOperator(FilterOperator.BELOW_AVERAGE);
                            break;
                        }
                        case "topTen;10": {
                            colCondition.setFilterOperator(FilterOperator.TOP10);
                        }
                    }
                    filterCols.add(colCondition);
                    int indexOf = filterCols.indexOf(colCondition);
                    everageMap.put(cellKey, indexOf);
                    everageVlueMap.put(cellKey, new ArrayList());
                    everageColMap.put(cellKey, celColIndexString);
                } else if (null != opList && opList.size() > 0) {
                    for (FilterCondition filterCondition : opList) {
                        FilterOperator filterOperator;
                        if ("eq".equals(filterCondition.getOpCode())) {
                            filterOperator = FilterOperator.EQUAL;
                        } else if ("noteq".equals(filterCondition.getOpCode())) {
                            filterOperator = FilterOperator.NOT_EQUAL;
                        } else if ("more".equals(filterCondition.getOpCode())) {
                            filterOperator = FilterOperator.GREATER_THAN;
                        } else if ("less".equals(filterCondition.getOpCode())) {
                            filterOperator = FilterOperator.LESS_THAN;
                        } else if ("notless".equals(filterCondition.getOpCode())) {
                            filterOperator = FilterOperator.GREATER_THAN_OR_EQUAL;
                        } else if ("notmore".equals(filterCondition.getOpCode())) {
                            filterOperator = FilterOperator.LESS_THAN_OR_EQUAL;
                        } else if ("contain".equals(filterCondition.getOpCode())) {
                            filterOperator = FilterOperator.CONTAIN;
                        } else {
                            if (!"notcontain".equals(filterCondition.getOpCode())) continue;
                            filterOperator = FilterOperator.NOT_CONTAIN;
                        }
                        FilterColCondition colCondition = new FilterColCondition();
                        colCondition.setAnd(Boolean.valueOf("and".equals(cellQueryInfo.getAttendedMode())));
                        colCondition.setValue(filterCondition.getOpValue());
                        colCondition.setFilterOperator(filterOperator);
                        filterCols.add(colCondition);
                    }
                }
                String sort = cellQueryInfo.getSort();
                if (!StringUtils.isEmpty((String)sort)) {
                    filterRegionCondition.setSortCol(celColIndexString);
                    filterRegionCondition.setIsAsc("asc".equals(cellQueryInfo.getSort()));
                }
                if (filterCols.size() <= 0) continue;
                filterRegionCondition.addColFilterCondition(celColIndexString, filterCols);
            }
            this.addExcelFilters(dataRegionDefine, curLine, sheetInfo.getFilters(), conditions, filterRegionCondition, everageMap, everageVlueMap, everageColMap, beginRow);
        }
        return allHaveData ? curLine : -1;
    }

    private static void setCacheCellStyle(ExportCache exportCache, DataLinkDefine dataLinkDefine, GridCellData gridCell) {
        GridCellStyleData cacheCellStyle = exportCache.getCellStyle(dataLinkDefine.getPosY(), dataLinkDefine.getPosX());
        if (cacheCellStyle != null) {
            gridCell.setCellStyleData(cacheCellStyle);
        }
    }

    private static int getSquNum(IRowData rowDatum, RegionNumber regionNumber, PagerInfo pagerInfo) {
        int start = regionNumber.getStart();
        int increment = regionNumber.getIncrement();
        int detailSeqNum = rowDatum.getDetailSeqNum();
        int offset = Math.max(pagerInfo.getOffset(), 0);
        int limit = Math.max(pagerInfo.getLimit(), 0);
        return start + (detailSeqNum - 1) * increment + offset * limit * increment;
    }

    private void setCsStyle(ExportCache exportCache, DimensionValueSet rowKey, DataLinkDefine dataLinkDefine, GridCellData gridCell) {
        if (exportCache.link2ConditionalStyle().containsKey(dataLinkDefine.getKey())) {
            List<ConditionalStyle> list = exportCache.link2ConditionalStyle().get(dataLinkDefine.getKey());
            for (ConditionalStyle conditionalStyle : list) {
                Boolean horizontalBar;
                int fontStyle;
                GridCellStyleData cellStyle;
                Set<DimensionValueSet> set = exportCache.unconditionalStyleDims().get(conditionalStyle.getKey());
                boolean setCsStyle = true;
                if (rowKey != null && set != null) {
                    for (DimensionValueSet dimensionValueSet : set) {
                        int compareTo = rowKey.compareTo(dimensionValueSet);
                        if (compareTo != 0) continue;
                        setCsStyle = false;
                    }
                } else if (exportCache.unconditionalStyleDims().containsKey(conditionalStyle.getKey())) {
                    setCsStyle = false;
                }
                if (!setCsStyle) continue;
                GridCellStyleData orgCellStyleData = gridCell.getCellStyleData();
                if (orgCellStyleData == null) {
                    cellStyle = new GridCellStyleData();
                } else {
                    try {
                        cellStyle = (GridCellStyleData)orgCellStyleData.clone();
                    }
                    catch (CloneNotSupportedException e) {
                        logger.error(e.getMessage(), e);
                        cellStyle = new GridCellStyleData();
                    }
                }
                String conditionForeGroundColor = conditionalStyle.getForeGroundColor();
                if (StringUtils.isNotEmpty((String)conditionForeGroundColor)) {
                    cellStyle.setBackGroundColor(ColorUtil.parseHexColor((String)conditionForeGroundColor));
                }
                if ((fontStyle = cellStyle.getFontStyle()) < 0) {
                    fontStyle = 0;
                }
                if (conditionalStyle.getBold() != null && conditionalStyle.getBold().booleanValue() && (fontStyle & 2) == 0) {
                    fontStyle += 2;
                }
                if (conditionalStyle.getItalic() != null && conditionalStyle.getItalic().booleanValue() && (fontStyle & 4) == 0) {
                    fontStyle += 4;
                }
                if (conditionalStyle.getStrikeThrough() != null && conditionalStyle.getStrikeThrough().booleanValue() && (fontStyle & 0x20) == 0) {
                    fontStyle += 32;
                }
                cellStyle.setFontStyle(fontStyle);
                gridCell.setCellStyleData(cellStyle);
                gridCell.setEditable(conditionalStyle.getReadOnly() == false);
                String conditionFontColor = conditionalStyle.getFontColor();
                if (StringUtils.isNotEmpty((String)conditionFontColor)) {
                    int foreGroundColor = ColorUtil.parseHexColor((String)conditionFontColor);
                    gridCell.setForeGroundColor(foreGroundColor);
                }
                if ((horizontalBar = conditionalStyle.getHorizontalBar()) == null || !horizontalBar.booleanValue()) continue;
                gridCell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Text));
                gridCell.setShowText("-");
                gridCell.setEditText("-");
            }
        }
    }

    private Map<String, String> getEnumPosMap(List<IMetaData> linkDataList) {
        HashMap<String, String> result = new HashMap<String, String>();
        for (IMetaData linkData : linkDataList) {
            Map enumPosMap;
            boolean linkInput;
            DataLinkDefine dataLinkDefine = linkData.getDataLinkDefine();
            if (dataLinkDefine == null) continue;
            DataLinkEditMode editMode = dataLinkDefine.getEditMode();
            boolean bl = linkInput = editMode == DataLinkEditMode.DATA_LINK_INPUT;
            if (!StringUtils.isNotEmpty((String)linkData.getDataField().getRefDataEntityKey()) || linkInput || CollectionUtils.isEmpty(enumPosMap = dataLinkDefine.getEnumPosMap())) continue;
            for (Map.Entry entry : enumPosMap.entrySet()) {
                String enumPos = this.getPosStr(entry.getValue().toString());
                result.put(enumPos, entry.getValue().toString());
            }
        }
        for (IMetaData linkData : linkDataList) {
            if (linkData.getDataLinkDefine() == null) continue;
            String position = this.getPosition(linkData.getDataLinkDefine().getPosX(), linkData.getDataLinkDefine().getPosY());
            result.remove(position);
        }
        return result;
    }

    private String getPosStr(String position) {
        String[] rows;
        String[] englishs = position.split("\\d");
        StringBuilder english = new StringBuilder();
        for (String n : englishs) {
            english.append(n);
        }
        StringBuilder relationRowString = new StringBuilder();
        for (String r : rows = position.split("\\D")) {
            relationRowString.append(r);
        }
        int relationRow = Integer.parseInt(relationRowString.toString());
        int relationCol = Grid2DataSetValueUtil.excelColStrToNum(english.toString(), english.length());
        return this.getPosition(relationCol, relationRow);
    }

    private String getPosition(int col, int row) {
        String posStr = String.valueOf(row);
        return posStr.concat("_").concat(String.valueOf(col));
    }

    private boolean haveData(IRowData rowData, ExportCache exportCache, boolean filledRegion, boolean expEmptyForm) {
        boolean fillEmpty;
        if (CollectionUtils.isEmpty(rowData.getLinkDataValues())) {
            return false;
        }
        boolean bl = fillEmpty = exportCache.autoFillIsNullTable() && filledRegion && !expEmptyForm;
        if (fillEmpty) {
            return false;
        }
        boolean haveData = false;
        for (IDataValue d : rowData.getLinkDataValues()) {
            if (haveData) break;
            if (d == null || d.getAsNull()) continue;
            if (d.getAsObject() instanceof String) {
                haveData = StringUtils.isNotEmpty((String)d.toString());
                continue;
            }
            haveData = d.getAsObject() != null;
        }
        return haveData;
    }

    private boolean filledRegion(IRegionDataSet regionDataSet) {
        boolean filled = true;
        for (IRowData rowDatum : regionDataSet.getRowData()) {
            if (rowDatum.isFilledRow()) continue;
            filled = false;
            break;
        }
        return filled;
    }

    private void addExcelFilters(DataRegionDefine dataRegionDefine, int lineInfo, List<FilterRegionCondition> filters, Map<String, List<CellQueryInfo>> conditions, FilterRegionCondition filterRegionCondition, Map<String, Integer> everageMap, Map<String, List<Double>> everageVlueMap, Map<String, String> everageColMap, int beginRow) {
        if (filterRegionCondition != null && null != conditions && conditions.size() > 0) {
            int regionLeft = dataRegionDefine.getRegionLeft();
            int regionRight = dataRegionDefine.getRegionRight();
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

