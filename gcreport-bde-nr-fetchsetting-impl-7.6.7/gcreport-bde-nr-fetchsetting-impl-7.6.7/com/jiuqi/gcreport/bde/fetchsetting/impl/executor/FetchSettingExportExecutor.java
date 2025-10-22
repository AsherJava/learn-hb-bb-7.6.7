/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.FetchDimensionClient
 *  com.jiuqi.bde.common.constant.RegionTypeEnum
 *  com.jiuqi.bde.common.exception.BdeRuntimeException
 *  com.jiuqi.bde.floatmodel.client.FloatRowAnalysisClient
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.bde.common.utils.BdeClientUtil
 *  com.jiuqi.gcreport.bde.fetchsetting.client.enums.BizTypeEnum
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO
 *  com.jiuqi.va.domain.common.JSONUtil
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor;

import com.jiuqi.bde.bizmodel.client.FetchDimensionClient;
import com.jiuqi.bde.common.constant.RegionTypeEnum;
import com.jiuqi.bde.common.exception.BdeRuntimeException;
import com.jiuqi.bde.floatmodel.client.FloatRowAnalysisClient;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.bde.common.utils.BdeClientUtil;
import com.jiuqi.gcreport.bde.fetchsetting.client.enums.BizTypeEnum;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.ExcelRegionInfo;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FetchSettingExportContext;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FetchSettingExportParam;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.BdeXSSFDataValidationConstraint;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.consts.ComboBoxGroup;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.enums.ExportBeforeInnerColumnHandlerEnum;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.enums.ExportBehindInnerColumnHandlerEnum;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.enums.FloatExportBeforeInnerColumnHandlerEnum;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.gather.ImpExpHandleAdaptorGather;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.gather.ImpExpHandleGather;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.handler.ImpExpInnerColumnHandler;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.utils.FetchSettingNrUtil;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.utils.ImportInnerColumnUtil;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSchemeNrService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.impl.ExportBuildCellSersive;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.impl.ProcessMergedService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.utils.FetchSettingLogHelperUtil;
import com.jiuqi.va.domain.common.JSONUtil;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class FetchSettingExportExecutor
extends AbstractExportExcelMultiSheetExecutor {
    @Autowired
    private FetchSchemeNrService fetchSchemeService;
    @Autowired
    private FetchDimensionClient dimensionRestRequest;
    @Autowired
    private FloatRowAnalysisClient floatRowAnalysisClient;
    @Autowired
    private ImpExpHandleGather impExpHandlerGather;
    @Autowired
    private ImpExpHandleAdaptorGather adaptorGather;

    protected List<ExportExcelSheet> exportExcelSheets(ExportContext context, Workbook workbook) {
        FetchSettingExportParam exportParam = (FetchSettingExportParam)JSONUtil.parseObject((String)context.getParam(), FetchSettingExportParam.class);
        if (StringUtils.isEmpty((String)exportParam.getFormSchemeId())) {
            throw new BdeRuntimeException("\u62a5\u8868\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a\u3002");
        }
        if (StringUtils.isEmpty((String)exportParam.getFetchSchemeId())) {
            throw new BdeRuntimeException("\u53d6\u6570\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a\u3002");
        }
        FetchSettingExportContext fetchSettingExportContext = this.convertParamToContext(exportParam);
        FetchSettingLogHelperUtil.logFetchSettingExport();
        return this.impExpHandlerGather.getExpServiceByBizType(fetchSettingExportContext.getBizType().getCode()).listExportExcelSheets(context, fetchSettingExportContext, workbook);
    }

    private FetchSettingExportContext convertParamToContext(FetchSettingExportParam exportParam) {
        FetchSettingExportContext exportContext = new FetchSettingExportContext();
        BeanUtils.copyProperties(exportParam, exportContext);
        FetchSchemeVO fetchScheme = this.fetchSchemeService.getFetchScheme(exportParam.getFetchSchemeId());
        exportContext.setBizType(BizTypeEnum.getEnumByCode((String)fetchScheme.getBizType()));
        GcBaseDataCenterTool tool = GcBaseDataCenterTool.getInstance();
        List subjectAgingList = tool.queryBasedataItems("MD_AGING");
        exportContext.setAgingBaseDataList(subjectAgingList);
        exportContext.setFormDefines(this.adaptorGather.getHandleAdaptor(exportContext.getBizType().getCode()).listFormDefine(exportParam.getFormSchemeId(), exportParam.getFormKeyData()));
        exportContext.setBizModelDTOs(FetchSettingNrUtil.getBizModelDTOs());
        exportContext.setDimensions((List)BdeClientUtil.parseResponse((BusinessResponseEntity)this.dimensionRestRequest.listAllDimension()));
        exportContext.setFixColumns(ImportInnerColumnUtil.getFixedImportInnerColumns(exportContext.getDimensions()));
        exportContext.setFloatRegionHandlerVOS((List)this.floatRowAnalysisClient.listAllFloatRegionHandler().getData());
        exportContext.setFloatColumns(ImportInnerColumnUtil.getFloatImportInnerColumns(exportContext.getDimensions()));
        return exportContext;
    }

    protected void callBackSheet(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet) {
        if ("\u586b\u62a5\u8bf4\u660e".equals(excelSheet.getSheetName())) {
            sheet.setColumnWidth(1, 30000);
            return;
        }
        sheet.setColumnWidth(1, 9150);
        List floatColumns = (List)context.getVarMap().get("FLOATCOLUMNS");
        List fixColumns = (List)context.getVarMap().get("FIXCOLUMNS");
        List excelRegionInfos = (List)context.getVarMap().get(String.valueOf(excelSheet.getSheetNo()));
        if (CollectionUtils.isEmpty(excelRegionInfos)) {
            return;
        }
        boolean hasFixRegion = excelRegionInfos.stream().anyMatch(item -> RegionTypeEnum.FIXED.equals((Object)item.getRegionTypeEnum()));
        if (hasFixRegion) {
            sheet.createFreezePane(5, 2, 5, 2);
        } else {
            sheet.setColumnWidth(0, 5000);
            sheet.setColumnWidth(2, 5000);
            sheet.setColumnWidth(3, 6700);
            sheet.setColumnWidth(4, 5700);
            sheet.createFreezePane(7, 4, 7, 4);
        }
        ComboBoxGroup comboBoxGroup = new ComboBoxGroup(context);
        for (ExcelRegionInfo excelRegionInfo : excelRegionInfos) {
            if (RegionTypeEnum.FIXED.equals((Object)excelRegionInfo.getRegionTypeEnum())) {
                this.fixComboBox(sheet, excelRegionInfo, comboBoxGroup, fixColumns);
                continue;
            }
            this.floatComboBox(excelSheet, sheet, excelRegionInfo, comboBoxGroup, floatColumns);
        }
    }

    private void fixComboBox(Sheet sheet, ExcelRegionInfo excelRegionInfo, ComboBoxGroup comboBoxGroup, List<ImpExpInnerColumnHandler> fixColumns) {
        int startRow = excelRegionInfo.getStartIndex() + 2;
        CellRangeAddressList signRegions = new CellRangeAddressList(startRow, excelRegionInfo.getEndIndex() - 1, 2, 2);
        sheet.addValidationData(this.setCellDataValidation(comboBoxGroup.getSignDropDownData(), signRegions, sheet));
        if (comboBoxGroup.getFetchSourceDropDownData().length > 0) {
            CellRangeAddressList fetchSourceRegions = new CellRangeAddressList(2, excelRegionInfo.getEndIndex() - 1, 3, 3);
            String strFormula = "\u586b\u62a5\u8bf4\u660e!$A$28:$A$" + (27 + comboBoxGroup.getFetchSourceDropDownData().length);
            sheet.addValidationData(this.getDataValidationByReference(sheet, strFormula, fetchSourceRegions));
        }
        if (comboBoxGroup.getFetchTypeDropDownData().length > 0) {
            CellRangeAddressList fetchTypeRegions = new CellRangeAddressList(2, excelRegionInfo.getEndIndex() - 1, 4, 4);
            Integer rowIndex = 78 + comboBoxGroup.getFetchSourceDropDownData().length;
            String strFormula = String.format("\u586b\u62a5\u8bf4\u660e!$B$%1$d:$%2$s$%1$d", rowIndex, this.getExcelColumnNumber(comboBoxGroup.getFetchTypeDropDownData().length));
            sheet.addValidationData(this.getDataValidationByReference(sheet, strFormula, fetchTypeRegions));
        }
        Map<String, Integer> titleAndIndex = fixColumns.stream().collect(Collectors.toMap(item -> item.getLabel()[1], fixColumns::indexOf, (K1, K2) -> K1));
        for (String title : titleAndIndex.keySet()) {
            CellRangeAddressList regions = new CellRangeAddressList(excelRegionInfo.getStartIndex() + 2, excelRegionInfo.getEndIndex() - 1, titleAndIndex.get(title), titleAndIndex.get(title));
            if (title.endsWith("\u5339\u914d\u89c4\u5219")) {
                sheet.addValidationData(this.setCellDataValidation(comboBoxGroup.getMatchRuleDropDownData(), regions, sheet));
                continue;
            }
            if (ExportBeforeInnerColumnHandlerEnum.SUMTYPENAME.getLabel()[1].equals(title)) {
                sheet.addValidationData(this.setCellDataValidation(comboBoxGroup.getSumTypeDropDownData(), regions, sheet));
                continue;
            }
            if (ExportBeforeInnerColumnHandlerEnum.DIMTYPENAME.getLabel()[1].equals(title)) {
                if (comboBoxGroup.getDimTypeDropDownData().length <= 0) continue;
                sheet.addValidationData(this.setCellDataValidation(comboBoxGroup.getCflOrDjDropDownData(), regions, sheet));
                continue;
            }
            if (ExportBehindInnerColumnHandlerEnum.AGINGRANGETYPENAME.getLabel()[1].equals(title)) {
                if (comboBoxGroup.getAgingRangeTypeDropDownData().length <= 0) continue;
                sheet.addValidationData(this.setCellDataValidation(comboBoxGroup.getAgingRangeTypeDropDownData(), regions, sheet));
                continue;
            }
            if (!ExportBehindInnerColumnHandlerEnum.AGINGGROUP.getLabel()[1].equals(title) || comboBoxGroup.getAgingGroupDropDownData().length <= 0) continue;
            sheet.addValidationData(this.setCellDataValidation(comboBoxGroup.getAgingGroupDropDownData(), regions, sheet));
        }
    }

    public String getExcelColumnNumber(int celNum) {
        int num = celNum + 1;
        String tem = "";
        while (num > 0) {
            int lo = (num - 1) % 26;
            tem = (char)(lo + 65) + tem;
            num = (num - 1) / 26;
        }
        return tem;
    }

    private DataValidation getDataValidationByReference(Sheet sheet, String Reference, CellRangeAddressList fetchSourceRegions) {
        XSSFDataValidationConstraint constraint;
        DataValidationHelper dataValidationHelper = sheet.getDataValidationHelper();
        DataValidation createValidation = dataValidationHelper.createValidation(constraint = new XSSFDataValidationConstraint(3, Reference), fetchSourceRegions);
        if (createValidation instanceof XSSFDataValidation) {
            createValidation.setSuppressDropDownArrow(true);
        } else {
            createValidation.setSuppressDropDownArrow(false);
        }
        return createValidation;
    }

    private void floatComboBox(ExportExcelSheet excelSheet, Sheet sheet, ExcelRegionInfo excelRegionInfo, ComboBoxGroup comboBoxGroup, List<ImpExpInnerColumnHandler> floatColumns) {
        String[] fieldArray = new String[]{};
        if (((Object[])excelSheet.getRowDatas().get(excelRegionInfo.getStartIndex() + 1))[5] != null && !StringUtils.isEmpty((String)((Object[])excelSheet.getRowDatas().get(excelRegionInfo.getStartIndex() + 1))[5].toString())) {
            fieldArray = ((Object[])excelSheet.getRowDatas().get(excelRegionInfo.getStartIndex() + 1))[5].toString().split(",");
        }
        CellRangeAddressList fieldTypeRegions = new CellRangeAddressList(4 + excelRegionInfo.getStartIndex(), excelRegionInfo.getEndIndex() - 1, 2, 2);
        sheet.addValidationData(this.setCellDataValidation(comboBoxGroup.getFieldType(), fieldTypeRegions, sheet));
        CellRangeAddressList floatConfigTypeRegions = new CellRangeAddressList(1 + excelRegionInfo.getStartIndex(), 1 + excelRegionInfo.getStartIndex(), 1, 1);
        sheet.addValidationData(this.setCellDataValidation(comboBoxGroup.getFloatConfigType(), floatConfigTypeRegions, sheet));
        CellRangeAddressList signRegions = new CellRangeAddressList(4 + excelRegionInfo.getStartIndex(), excelRegionInfo.getEndIndex() - 1, 4, 4);
        sheet.addValidationData(this.setCellDataValidation(comboBoxGroup.getSignDropDownData(), signRegions, sheet));
        if (comboBoxGroup.getFetchSourceDropDownData().length > 0) {
            CellRangeAddressList fetchSourceRegions = new CellRangeAddressList(4 + excelRegionInfo.getStartIndex(), excelRegionInfo.getEndIndex() - 1, 5, 5);
            String strFormula = "\u586b\u62a5\u8bf4\u660e!$A$28:$A$" + (27 + comboBoxGroup.getFetchSourceDropDownData().length);
            sheet.addValidationData(this.getDataValidationByReference(sheet, strFormula, fetchSourceRegions));
        }
        if (comboBoxGroup.getFetchTypeDropDownData().length > 0) {
            CellRangeAddressList fetchTypeRegions = new CellRangeAddressList(4 + excelRegionInfo.getStartIndex(), excelRegionInfo.getEndIndex() - 1, 6, 6);
            Integer rowIndex = 78 + comboBoxGroup.getFetchSourceDropDownData().length;
            String strFormula = String.format("\u586b\u62a5\u8bf4\u660e!$B$%1$d:$%2$s$%1$d", rowIndex, this.getExcelColumnNumber(comboBoxGroup.getFetchTypeDropDownData().length));
            sheet.addValidationData(this.getDataValidationByReference(sheet, strFormula, fetchTypeRegions));
        }
        if (fieldArray.length > 0) {
            CellRangeAddressList kmRegions = new CellRangeAddressList(4 + excelRegionInfo.getStartIndex(), excelRegionInfo.getEndIndex() - 1, 7, 7);
            CellRangeAddressList excludeKmRegions = new CellRangeAddressList(4 + excelRegionInfo.getStartIndex(), excelRegionInfo.getEndIndex() - 1, 8, 8);
            CellRangeAddressList xjllRegions = new CellRangeAddressList(4 + excelRegionInfo.getStartIndex(), excelRegionInfo.getEndIndex() - 1, 19, 19);
            CellRangeAddressList floatConfigFieldRegions = new CellRangeAddressList(4 + excelRegionInfo.getStartIndex(), excelRegionInfo.getEndIndex() - 1, 3, 3);
            sheet.addValidationData(this.setCellDataValidation(fieldArray, kmRegions, sheet));
            sheet.addValidationData(this.setCellDataValidation(fieldArray, excludeKmRegions, sheet));
            sheet.addValidationData(this.setCellDataValidation(fieldArray, xjllRegions, sheet));
            sheet.addValidationData(this.setCellDataValidation(fieldArray, floatConfigFieldRegions, sheet));
            if (comboBoxGroup.getDimTypeDropDownData().length > 0) {
                CellRangeAddressList assRegions = new CellRangeAddressList(4 + excelRegionInfo.getStartIndex(), excelRegionInfo.getEndIndex() - 1, FloatExportBeforeInnerColumnHandlerEnum.values().length, floatColumns.size() - ExportBehindInnerColumnHandlerEnum.values().length - 1);
                sheet.addValidationData(this.setCellDataValidation(fieldArray, assRegions, sheet));
            }
        }
        for (int i = 0; i < floatColumns.size(); ++i) {
            CellRangeAddressList regions = new CellRangeAddressList(4 + excelRegionInfo.getStartIndex(), excelRegionInfo.getEndIndex() - 1, i, i);
            ImpExpInnerColumnHandler exportInnerColumnHandler = floatColumns.get(i);
            if ("\u91cd\u5206\u7c7b\u6c47\u603b\u7c7b\u578b".equals(exportInnerColumnHandler.getLabel()[1])) {
                sheet.addValidationData(this.setCellDataValidation(comboBoxGroup.getSumTypeDropDownData(), regions, sheet));
                continue;
            }
            if (FloatExportBeforeInnerColumnHandlerEnum.SUMTYPENAME.getLabel()[1].equals(exportInnerColumnHandler.getLabel()[1])) {
                if (comboBoxGroup.getDimTypeDropDownData().length <= 0) continue;
                sheet.addValidationData(this.setCellDataValidation(comboBoxGroup.getDimTypeDropDownData(), regions, sheet));
                continue;
            }
            if (ExportBehindInnerColumnHandlerEnum.AGINGRANGETYPENAME.getLabel()[1].equals(exportInnerColumnHandler.getLabel()[1])) {
                if (comboBoxGroup.getAgingRangeTypeDropDownData().length <= 0) continue;
                sheet.addValidationData(this.setCellDataValidation(comboBoxGroup.getAgingRangeTypeDropDownData(), regions, sheet));
                continue;
            }
            if (ExportBehindInnerColumnHandlerEnum.AGINGGROUP.getLabel()[1].equals(exportInnerColumnHandler.getLabel()[1])) {
                if (comboBoxGroup.getAgingGroupDropDownData().length <= 0) continue;
                sheet.addValidationData(this.setCellDataValidation(comboBoxGroup.getAgingGroupDropDownData(), regions, sheet));
                continue;
            }
            if (!exportInnerColumnHandler.getLabel()[1].endsWith("\u79d1\u76ee") || fieldArray.length <= 0) continue;
            sheet.addValidationData(this.setCellDataValidation(fieldArray, regions, sheet));
        }
    }

    public String getName() {
        return "fetchSettingExportExecutor";
    }

    private DataValidation setCellDataValidation(String[] dropDownData, CellRangeAddressList regions, Sheet sheet) {
        DataValidationHelper dataValidationHelper = sheet.getDataValidationHelper();
        DataValidationConstraint createExplicitListConstraint = null;
        createExplicitListConstraint = dataValidationHelper instanceof XSSFDataValidationHelper ? new BdeXSSFDataValidationConstraint(dropDownData) : dataValidationHelper.createExplicitListConstraint(dropDownData);
        DataValidation createValidation = dataValidationHelper.createValidation(createExplicitListConstraint, regions);
        if (createValidation instanceof XSSFDataValidation) {
            createValidation.setSuppressDropDownArrow(true);
        } else {
            createValidation.setSuppressDropDownArrow(false);
        }
        return createValidation;
    }

    protected void processMergedRegions(ExportContext context, SXSSFWorkbook workbook, ExportExcelSheet excelSheet) {
        ProcessMergedService processMergedService = new ProcessMergedService();
        processMergedService.processMergedRegions(context, excelSheet);
    }

    protected void buildSheetDataCells(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet) {
        ExportBuildCellSersive exportBuildCellSersive = new ExportBuildCellSersive(this);
        exportBuildCellSersive.buildSheetDataCells(context, excelSheet, workbook, sheet);
    }

    public void writeCellValue(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet, Row row, Cell cell, Object cellValue) {
        super.writeCellValue(context, excelSheet, workbook, sheet, row, cell, cellValue);
    }

    public void callBackRow(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet, Row row) {
        super.callBackRow(context, excelSheet, workbook, sheet, row);
    }

    public void callBackCell(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet, Row row, Cell cell, Object cellValue) {
        super.callBackCell(context, excelSheet, workbook, sheet, row, cell, cellValue);
    }
}

