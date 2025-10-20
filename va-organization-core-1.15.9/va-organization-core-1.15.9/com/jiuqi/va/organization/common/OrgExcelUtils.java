/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.StorageFieldConsts
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryChildrenType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.org.ZB
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.apache.poi.hssf.util.HSSFColor$HSSFColorPredefined
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.DataFormat
 *  org.apache.poi.ss.usermodel.FillPatternType
 *  org.apache.poi.ss.usermodel.Font
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.usermodel.VerticalAlignment
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.xssf.streaming.SXSSFWorkbook
 */
package com.jiuqi.va.organization.common;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.StorageFieldConsts;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.org.ZB;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.organization.common.OrgCoreI18nUtil;
import com.jiuqi.va.organization.domain.OrgExcelColumn;
import com.jiuqi.va.organization.domain.OrgImportTemplateDO;
import com.jiuqi.va.organization.domain.OrgImportTemplateDTO;
import com.jiuqi.va.organization.service.OrgCategoryService;
import com.jiuqi.va.organization.service.OrgDataService;
import com.jiuqi.va.organization.service.impl.OrgImportTemplateServiceImpl;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class OrgExcelUtils {
    private static Logger logger = LoggerFactory.getLogger(OrgExcelUtils.class);
    private static OrgDataService orgDataClient = (OrgDataService)ApplicationContextRegister.getBean(OrgDataService.class);
    private static OrgCategoryService orgCategoryClient = (OrgCategoryService)ApplicationContextRegister.getBean(OrgCategoryService.class);
    private static DataModelClient dataModelClient = (DataModelClient)ApplicationContextRegister.getBean(DataModelClient.class);
    private static final Set<String> upperCaseField = Stream.of("CODE").collect(Collectors.toSet());
    private static String offset = "  ";
    private static String whiteSpace1 = "";
    private static String whiteSpace2 = whiteSpace1 + offset;
    private static String whiteSpace3 = whiteSpace2 + offset;
    private static String whiteSpace4 = whiteSpace3 + offset;
    private static String whiteSpace5 = whiteSpace4 + offset;
    private static String whiteSpace6 = whiteSpace5 + offset;
    private static String whiteSpace7 = whiteSpace6 + offset;
    private static String whiteSpace8 = whiteSpace7 + offset;
    private static String whiteSpace9 = whiteSpace8 + offset;

    public static List<OrgExcelColumn> getExcelColumnsByTemplateColList(String tableName, List<Map<String, Object>> colList) {
        Collections.sort(colList, (o1, o2) -> {
            if ((Integer)o1.get("column") < (Integer)o2.get("column")) {
                return -1;
            }
            return 1;
        });
        ArrayList<Map<String, Object>> sortList = new ArrayList<Map<String, Object>>();
        int i = 1;
        for (Map<String, Object> col : colList) {
            while ((Integer)col.get("column") > i) {
                sortList.add(null);
                ++i;
            }
            sortList.add(col);
            ++i;
        }
        colList = sortList;
        DataModelDO define = OrgExcelUtils.getDataModalDefine(tableName);
        OrgCategoryDO category = OrgExcelUtils.getDefine(tableName);
        ArrayList<OrgExcelColumn> res = new ArrayList<OrgExcelColumn>();
        HashMap<String, OrgExcelColumn> colSearchMap = new HashMap<String, OrgExcelColumn>();
        for (DataModelColumn column : define.getColumns()) {
            colSearchMap.put(column.getColumnName(), OrgExcelUtils.getExcelColumnByDataModelColumn(column, category));
        }
        HashSet<String> requiredFieldSet = new HashSet<String>();
        requiredFieldSet.add("CODE");
        requiredFieldSet.add("NAME");
        for (Map<String, Object> col : colList) {
            DataModelColumn column;
            if (col == null) {
                res.add(null);
                continue;
            }
            column = (OrgExcelColumn)((Object)colSearchMap.get(col.get("name")));
            if (column != null) {
                column.setColumnTitle(col.get("title").toString());
                Object checkObj = col.get("checkval");
                Boolean checkval = checkObj == null ? Boolean.valueOf(false) : (checkObj instanceof Boolean ? (Boolean)checkObj : Boolean.valueOf("1".equals(checkObj) || "true".equals(checkObj)));
                column.setCheckval(checkval);
                if (requiredFieldSet.contains(column.getColumnName())) {
                    column.setNullable(Boolean.valueOf(false));
                }
                String reftable = (String)col.get("reftable");
                String reffield = (String)col.get("reffield");
                if (!StringUtils.hasText(reftable) || !StringUtils.hasText(reffield)) {
                    if (column.getMappingType() != null && column.getMappingType() != 0) {
                        column.setMappingType(null);
                    }
                    column.setMapping(null);
                } else {
                    column.setMapping(reftable + "." + reffield);
                    if (reftable.equals("MD_ORG") || reftable.startsWith("MD_ORG_")) {
                        column.setMappingType(Integer.valueOf(4));
                    } else if (reftable.startsWith("MD_")) {
                        column.setMappingType(Integer.valueOf(1));
                    } else if (reftable.startsWith("EM_")) {
                        column.setMappingType(Integer.valueOf(2));
                    } else if (reftable.startsWith("AUTH_USER")) {
                        column.setMappingType(Integer.valueOf(3));
                    } else {
                        if (column.getMappingType() != null && column.getMappingType() != 0) {
                            column.setMappingType(null);
                        }
                        column.setMapping(null);
                    }
                }
            }
            res.add((OrgExcelColumn)column);
        }
        return res;
    }

    public static Workbook getTemplateExcel(OrgImportTemplateDO template, OrgImportTemplateDTO param) {
        SXSSFWorkbook workbook = null;
        try {
            workbook = new SXSSFWorkbook(100);
            List templateDataList = JSONUtil.parseMapArray((String)template.getTemplatedata().toString());
            String tableName = null;
            List fields = null;
            List<OrgExcelColumn> excelColumns = null;
            Sheet sheet = null;
            List<OrgDO> dataList = null;
            for (Map templateData : templateDataList) {
                tableName = (String)templateData.get(OrgImportTemplateServiceImpl.TemplateFields.categoryname.toString());
                sheet = workbook.createSheet(OrgExcelUtils.getDefine(tableName).getTitle());
                fields = (List)templateData.get(OrgImportTemplateServiceImpl.TemplateFields.fields.toString());
                excelColumns = OrgExcelUtils.getExcelColumnsByTemplateColList(tableName, fields);
                if (param != null && param.getShowStopped() == 1) {
                    OrgExcelColumn stopflagCol = new OrgExcelColumn();
                    stopflagCol.setColumnName("STOPFLAG");
                    stopflagCol.setColumnTitle(StorageFieldConsts.getFtStopFlag());
                    stopflagCol.setColumnType(DataModelType.ColumnType.INTEGER);
                    excelColumns.add(stopflagCol);
                }
                OrgExcelUtils.buildTemplateSheet(excelColumns, sheet, (Workbook)workbook);
                if (param == null) continue;
                dataList = OrgExcelUtils.getOrgDataList(tableName, param);
                OrgExcelUtils.buildDataSheet(excelColumns, dataList, sheet, (Workbook)workbook);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return workbook;
    }

    private static void buildTemplateSheet(List<OrgExcelColumn> columnList, Sheet sheet, Workbook workbook) {
        if (columnList == null || columnList.isEmpty()) {
            return;
        }
        Row row = sheet.createRow(0);
        row.setHeightInPoints(20.0f);
        int defaultWidth = 5120;
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short)12);
        CellStyle headStyle = workbook.createCellStyle();
        headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.SEA_GREEN.getIndex());
        headStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headStyle.setAlignment(HorizontalAlignment.LEFT);
        headStyle.setFont(font);
        CellStyle headStyleStop = workbook.createCellStyle();
        headStyleStop.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headStyleStop.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_40_PERCENT.getIndex());
        for (int i = 0; i < columnList.size(); i = (int)((short)(i + 1))) {
            OrgExcelColumn column = columnList.get(i);
            if (column == null) continue;
            sheet.setColumnWidth(i, defaultWidth);
            Cell cell = row.createCell(i);
            cell.setCellValue(column.getColumnTitle());
            if ("STOPFLAG".equals(column.getColumnName())) {
                cell.setCellStyle(headStyleStop);
                continue;
            }
            cell.setCellStyle(headStyle);
        }
    }

    private static void buildDataSheet(List<OrgExcelColumn> columnList, List<OrgDO> dataList, Sheet sheet, Workbook workbook) {
        if (dataList == null || dataList.isEmpty()) {
            return;
        }
        CellStyle dateStyle = workbook.createCellStyle();
        DataFormat dateFormatter = workbook.createDataFormat();
        dateStyle.setDataFormat(dateFormatter.getFormat("yyyy/MM/dd"));
        CellStyle timeStyle = workbook.createCellStyle();
        DataFormat timeFormatter = workbook.createDataFormat();
        timeStyle.setDataFormat(timeFormatter.getFormat("yyyy/MM/dd HH:mm:ss"));
        CellStyle errorStyle = workbook.createCellStyle();
        errorStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        errorStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
        DecimalFormat numericStyle = new DecimalFormat("0");
        Row curRow = null;
        Cell cell = null;
        for (int i = 0; i < dataList.size(); ++i) {
            curRow = sheet.createRow(i + 1);
            for (int j = 0; j < columnList.size(); ++j) {
                cell = curRow.createCell(j);
                try {
                    OrgExcelUtils.setCellValue(cell, dataList.get(i), columnList.get(j), dateStyle, timeStyle, numericStyle);
                    continue;
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    cell.setCellValue("");
                    cell.setCellStyle(errorStyle);
                }
            }
        }
    }

    private static void setCellValue(Cell cell, OrgDO data, OrgExcelColumn field, CellStyle dateStyle, CellStyle timeStyle, DecimalFormat numericStyle) {
        if (field == null) {
            return;
        }
        String fieldName = field.getColumnName().toLowerCase();
        Object value = data.get((Object)fieldName);
        if ("ordinal".equals(fieldName)) {
            if (value instanceof BigDecimal) {
                cell.setCellValue(((BigDecimal)value).toPlainString());
            } else {
                cell.setCellValue(numericStyle.format(Double.valueOf(value.toString())));
            }
            return;
        }
        DataModelType.ColumnType type = field.getColumnType();
        if (value == null || !StringUtils.hasText(value.toString())) {
            if (type.equals((Object)DataModelType.ColumnType.INTEGER) && field.getMappingType() != null && field.getMappingType() == 0) {
                cell.setCellValue(OrgCoreI18nUtil.getMessage("org.enum.confirm.no", new Object[0]));
            }
            return;
        }
        String mapping = field.getMapping();
        String refTable = mapping == null ? "" : mapping.split("\\.")[0];
        HashMap showTitleMap = data.get((Object)"showTitleMap") == null ? new HashMap() : (HashMap)data.get((Object)"showTitleMap");
        int level = data.getParents().split("\\/").length;
        block0 : switch (type) {
            case UUID: 
            case NVARCHAR: 
            case CLOB: {
                if (StringUtils.hasText(refTable)) {
                    Object refValue = showTitleMap.get(fieldName);
                    cell.setCellValue(refValue == null ? "" : refValue.toString());
                    break;
                }
                if (fieldName.equals("parentcode")) {
                    Object refValue = showTitleMap.get(fieldName);
                    cell.setCellValue(refValue == null || "-".equals(refValue) ? "" : refValue.toString());
                    break;
                }
                if (fieldName.equals("name")) {
                    switch (level) {
                        case 1: {
                            cell.setCellValue(whiteSpace1 + value.toString());
                            break block0;
                        }
                        case 2: {
                            cell.setCellValue(whiteSpace2 + value.toString());
                            break block0;
                        }
                        case 3: {
                            cell.setCellValue(whiteSpace3 + value.toString());
                            break block0;
                        }
                        case 4: {
                            cell.setCellValue(whiteSpace4 + value.toString());
                            break block0;
                        }
                        case 5: {
                            cell.setCellValue(whiteSpace5 + value.toString());
                            break block0;
                        }
                        case 6: {
                            cell.setCellValue(whiteSpace6 + value.toString());
                            break block0;
                        }
                        case 7: {
                            cell.setCellValue(whiteSpace7 + value.toString());
                            break block0;
                        }
                        case 8: {
                            cell.setCellValue(whiteSpace8 + value.toString());
                            break block0;
                        }
                        case 9: {
                            cell.setCellValue(whiteSpace9 + value.toString());
                            break block0;
                        }
                    }
                    cell.setCellValue(value.toString());
                    break;
                }
                cell.setCellValue(value.toString());
                break;
            }
            case INTEGER: {
                if (field.getMappingType() != null && field.getMappingType() == 0) {
                    cell.setCellValue(Integer.valueOf(value.toString()) == 1 ? OrgCoreI18nUtil.getMessage("org.enum.confirm.yes", new Object[0]) : OrgCoreI18nUtil.getMessage("org.enum.confirm.no", new Object[0]));
                    break;
                }
                cell.setCellValue((double)Integer.valueOf(value.toString()).intValue());
                break;
            }
            case NUMERIC: {
                cell.setCellValue(Double.valueOf(value.toString()).doubleValue());
                break;
            }
            case DATE: {
                if (value instanceof Date) {
                    cell.setCellValue((Date)value);
                    if (dateStyle == null) break;
                    cell.setCellStyle(dateStyle);
                    break;
                }
                cell.setCellValue(value.toString());
                break;
            }
            case TIMESTAMP: {
                if (value instanceof Date) {
                    cell.setCellValue((Date)value);
                    if (timeStyle == null) break;
                    cell.setCellStyle(timeStyle);
                    break;
                }
                cell.setCellValue(value.toString());
                break;
            }
        }
    }

    private static OrgCategoryDO getDefine(String tableName) {
        OrgCategoryDO orgCategoryDO = new OrgCategoryDO();
        orgCategoryDO.setName(tableName);
        PageVO<OrgCategoryDO> res = orgCategoryClient.list(orgCategoryDO);
        if (res != null && res.getRows() != null && !res.getRows().isEmpty()) {
            return (OrgCategoryDO)res.getRows().get(0);
        }
        throw new RuntimeException("\u83b7\u53d6" + tableName + "\u8868\u5b9a\u4e49\u5931\u8d25");
    }

    public static DataModelDO getDataModalDefine(String tableName) {
        DataModelDTO defineParam = new DataModelDTO();
        defineParam.setName(tableName);
        defineParam.setDeepClone(Boolean.valueOf(false));
        return dataModelClient.get(defineParam);
    }

    private static List<OrgDO> getOrgDataList(String tableName, OrgImportTemplateDTO param) {
        PageVO<OrgDO> queryRes;
        Date versionDate = (Date)param.getVersionDate();
        String rootCode = param.getRootCode();
        int showStopped = param.getShowStopped();
        OrgDTO queryParam = new OrgDTO();
        queryParam.setCategoryname(tableName);
        queryParam.setStopflag(Integer.valueOf(showStopped == 2 ? 0 : -1));
        queryParam.setRecoveryflag(Integer.valueOf(0));
        queryParam.setAuthType(OrgDataOption.AuthType.MANAGE);
        queryParam.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL_WITH_REF);
        queryParam.addExtInfo("RefShowCode", (Object)true);
        if (versionDate != null) {
            queryParam.setVersionDate(versionDate);
        }
        if (StringUtils.hasText(rootCode)) {
            queryParam.setCode(rootCode);
            queryParam.setQueryChildrenType(OrgDataOption.QueryChildrenType.ALL_CHILDREN_WITH_SELF);
        }
        return (queryRes = orgDataClient.list(queryParam)) == null ? null : queryRes.getRows();
    }

    private static OrgExcelColumn getExcelColumnByDataModelColumn(DataModelColumn dataModelColumn, OrgCategoryDO category) {
        OrgExcelColumn res = new OrgExcelColumn();
        res.setColumnName(dataModelColumn.getColumnName());
        res.setColumnTitle(dataModelColumn.getColumnTitle());
        res.setColumnType(dataModelColumn.getColumnType());
        res.setLengths(dataModelColumn.getLengths());
        res.setNullable(dataModelColumn.isNullable());
        res.setDefaultVal(dataModelColumn.getDefaultVal());
        res.setMappingType(dataModelColumn.getMappingType());
        res.setMapping(dataModelColumn.getMapping());
        res.setPkey(dataModelColumn.isPkey());
        res.setColumnAttr(dataModelColumn.getColumnAttr());
        res.setCheckval(true);
        res.setUppercase(upperCaseField.contains(dataModelColumn.getColumnName()));
        ZB zb = category.getZbByName(dataModelColumn.getColumnName());
        res.setMultiple(zb != null && zb.getMultiple() != null && zb.getMultiple() == 1);
        return res;
    }

    public static void checkHeader(Sheet sheet, List<OrgExcelColumn> columns, Integer firstLine) {
        if (firstLine == 1) {
            return;
        }
        Row row = sheet.getRow(firstLine - 2);
        for (int i = 0; i < columns.size(); ++i) {
            if (columns.get(i) == null) continue;
            Cell cell = row.getCell(i);
            if (cell == null) {
                throw new RuntimeException("\u5bfc\u5165\u6587\u4ef6\u8868\u5934\u540d\u79f0\u548c\u6a21\u677f\u4e0d\u4e00\u81f4");
            }
            String cellValue = cell.getStringCellValue();
            if (columns.get(i).getColumnTitle().equals(cellValue)) continue;
            throw new RuntimeException("\u5bfc\u5165\u6587\u4ef6\u8868\u5934\u540d\u79f0\u548c\u6a21\u677f\u4e0d\u4e00\u81f4");
        }
    }
}

