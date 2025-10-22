/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.FetchDimensionClient
 *  com.jiuqi.bde.floatmodel.client.FloatRowAnalysisClient
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.expimp.converters.ExpImpConverter
 *  com.jiuqi.common.expimp.converters.impl.DefaultExpImpConverter
 *  com.jiuqi.common.expimp.dataimport.common.ImportContext
 *  com.jiuqi.common.expimp.dataimport.excel.common.ImportExcelSheet
 *  com.jiuqi.common.expimp.dataimport.excel.common.ImportExcelSheet$ImportExcelSheetRowData
 *  com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelMultiSheetExecutor
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.bde.common.utils.BdeClientUtil
 *  com.jiuqi.gcreport.bde.fetchsetting.client.enums.BizTypeEnum
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO
 *  com.jiuqi.va.domain.common.JSONUtil
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor;

import com.jiuqi.bde.bizmodel.client.FetchDimensionClient;
import com.jiuqi.bde.floatmodel.client.FloatRowAnalysisClient;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.expimp.converters.ExpImpConverter;
import com.jiuqi.common.expimp.converters.impl.DefaultExpImpConverter;
import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.common.expimp.dataimport.excel.common.ImportExcelSheet;
import com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelMultiSheetExecutor;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.bde.common.utils.BdeClientUtil;
import com.jiuqi.gcreport.bde.fetchsetting.client.enums.BizTypeEnum;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FetchSettingExcelContext;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FetchSettingExportContext;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.gather.ImpExpHandleAdaptorGather;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.gather.ImpExpHandleGather;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.utils.FetchSettingNrUtil;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.utils.ImportInnerColumnUtil;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSchemeNrService;
import com.jiuqi.va.domain.common.JSONUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class FetchSettingImportExecutor
extends AbstractImportExcelMultiSheetExecutor {
    private static final Logger log = LoggerFactory.getLogger(FetchSettingImportExecutor.class);
    @Autowired
    private FetchDimensionClient dimensionRestRequest;
    @Autowired
    private FloatRowAnalysisClient floatRowAnalysisClient;
    @Autowired
    private FetchSchemeNrService fetchSchemeService;
    @Autowired
    private ImpExpHandleGather impExpHandlerGather;
    @Autowired
    private ImpExpHandleAdaptorGather adaptorGather;
    private static final HSSFDataFormatter HSSF_DATA_FORMATTER = new HSSFDataFormatter();

    public Object importExcelSheets(ImportContext context, List<ImportExcelSheet> excelSheets) {
        FetchSettingExcelContext exportParam = (FetchSettingExcelContext)JSONUtil.parseObject((String)context.getParam(), FetchSettingExcelContext.class);
        if (StringUtils.isEmpty((String)exportParam.getFormSchemeId())) {
            return "\u62a5\u8868\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a";
        }
        if (StringUtils.isEmpty((String)exportParam.getFetchSchemeId())) {
            return "\u53d6\u6570\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a";
        }
        FetchSettingExcelContext fetchSettingExcelContext = this.convertParamToContext(exportParam);
        return this.impExpHandlerGather.getImpServiceByBizType(fetchSettingExcelContext.getBizType().getCode()).importSheet(fetchSettingExcelContext, excelSheets);
    }

    private FetchSettingExcelContext convertParamToContext(FetchSettingExcelContext exportParam) {
        FetchSettingExportContext exportContext = new FetchSettingExportContext();
        BeanUtils.copyProperties(exportParam, exportContext);
        FetchSchemeVO fetchScheme = this.fetchSchemeService.getFetchScheme(exportParam.getFetchSchemeId());
        exportContext.setBizType(BizTypeEnum.getEnumByCode((String)fetchScheme.getBizType()));
        exportContext.setFormDefines(this.adaptorGather.getHandleAdaptor(exportContext.getBizType().getCode()).listFormDefine(exportParam.getFormSchemeId(), null));
        exportContext.setBizModelDTOs(FetchSettingNrUtil.getBizModelDTOs());
        exportContext.setDimensions((List)BdeClientUtil.parseResponse((BusinessResponseEntity)this.dimensionRestRequest.listAllDimension()));
        exportContext.setFixColumns(ImportInnerColumnUtil.getFixedImportInnerColumns(exportContext.getDimensions()));
        exportContext.setFloatColumns(ImportInnerColumnUtil.getFloatImportInnerColumns(exportContext.getDimensions()));
        GcBaseDataCenterTool tool = GcBaseDataCenterTool.getInstance();
        List subjectAgingList = tool.queryBasedataItems("MD_AGING");
        exportContext.setAgingBaseDataList(subjectAgingList);
        exportContext.setFloatRegionHandlerVOS((List)this.floatRowAnalysisClient.listAllFloatRegionHandler().getData());
        return exportContext;
    }

    public String getName() {
        return "fetchSettingImportExecutor";
    }

    protected List<ImportExcelSheet.ImportExcelSheetRowData> getSheetRowDatas(ImportContext context, Workbook workbook, Sheet sheet, ImportExcelSheet excelSheetInfo) {
        ArrayList<ImportExcelSheet.ImportExcelSheetRowData> excelSheetDatas = new ArrayList<ImportExcelSheet.ImportExcelSheetRowData>();
        int firstRowNum = sheet.getFirstRowNum();
        int lastRowNum = sheet.getLastRowNum();
        int rowDataLength = this.getRowDataLength(sheet, excelSheetInfo);
        for (int j = firstRowNum; j <= lastRowNum; ++j) {
            ImportExcelSheet.ImportExcelSheetRowData rowData;
            boolean checkValid;
            Row row = sheet.getRow(j);
            if (row == null || !(checkValid = this.checkExcelRowData(context, excelSheetInfo, workbook, sheet, row, rowData = this.getSheetRowData(context, excelSheetInfo, workbook, sheet, row, rowDataLength)))) continue;
            excelSheetDatas.add(rowData);
        }
        return excelSheetDatas;
    }

    protected ImportExcelSheet.ImportExcelSheetRowData getSheetRowData(ImportContext context, ImportExcelSheet excelSheetInfo, Workbook workbook, Sheet sheet, Row row, int rowDataLength) {
        ImportExcelSheet.ImportExcelSheetRowData excelSheetDataRow = new ImportExcelSheet.ImportExcelSheetRowData();
        excelSheetDataRow.setRowIndex(Integer.valueOf(row.getRowNum()));
        short lastCellNum = row.getLastCellNum();
        if (lastCellNum < 0) {
            excelSheetDataRow.setRowData(new Object[rowDataLength + 2]);
            return excelSheetDataRow;
        }
        Object[] rowData = new String[lastCellNum];
        for (short y = 0; y < lastCellNum; y = (short)(y + 1)) {
            Cell cell = row.getCell(y, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            Object cellValue = this.getSheetCellData(context, excelSheetInfo, workbook, sheet, row, cell);
            rowData[y] = cellValue == null ? "" : cellValue;
        }
        excelSheetDataRow.setRowData(rowData);
        return excelSheetDataRow;
    }

    protected Object getSheetCellData(ImportContext context, ImportExcelSheet excelSheet, Workbook workbook, Sheet sheet, Row row, Cell cell) {
        if (cell == null) {
            return null;
        }
        String cellValue = this.getCellValue(cell);
        Object formatValue = null;
        try {
            ExpImpConverter expImpConverter = excelSheet.getConverter();
            if (expImpConverter == null) {
                expImpConverter = new DefaultExpImpConverter();
                excelSheet.setConverter(expImpConverter);
            }
            formatValue = expImpConverter.convertExcelToJavaData(context, excelSheet, row.getRowNum(), cell.getColumnIndex(), (Object)cellValue);
        }
        catch (Exception e) {
            log.error("\u6570\u636e\u5bfc\u5165\u83b7\u53d6\u683c\u5f0f\u5316\u5de5\u5177\u5f02\u5e38\u3002", e);
        }
        return formatValue;
    }

    private String getCellValue(Cell cell) {
        String cellValue = null;
        if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
            cellValue = DateUtils.format((Date)DateUtil.getJavaDate(cell.getNumericCellValue()));
        }
        if (ObjectUtils.isEmpty(cellValue)) {
            cellValue = HSSF_DATA_FORMATTER.formatCellValue(cell);
        }
        return cellValue;
    }

    private int getRowDataLength(Sheet sheet, ImportExcelSheet excelSheetInfo) {
        if ("\u6d6e\u52a8\u533a\u57df\u6807\u8bc6".equals(sheet.getRow(0).getCell(0).getStringCellValue())) {
            return sheet.getRow(3).getLastCellNum() - sheet.getRow(3).getFirstCellNum();
        }
        int rowSize = 0;
        Integer sheetHeadSize = excelSheetInfo.getSheetHeadSize() == null ? 1 : excelSheetInfo.getSheetHeadSize();
        for (int i = 0; i < sheetHeadSize; ++i) {
            Row row = sheet.getRow(i);
            if (row == null) continue;
            int currentRowSize = row.getLastCellNum() - row.getFirstCellNum();
            rowSize = Math.max(rowSize, currentRowSize);
        }
        return rowSize;
    }

    protected int[] getReadSheetNos() {
        return null;
    }
}

