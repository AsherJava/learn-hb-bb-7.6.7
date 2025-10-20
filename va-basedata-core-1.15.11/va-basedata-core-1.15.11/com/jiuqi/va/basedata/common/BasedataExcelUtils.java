/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryChildrenType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.StorageFieldConsts
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.domain.enumdata.EnumDataDO
 *  com.jiuqi.va.domain.enumdata.EnumDataDTO
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.feign.util.RequestContextUtil
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.apache.poi.hssf.util.HSSFColor$HSSFColorPredefined
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.FillPatternType
 *  org.apache.poi.ss.usermodel.Font
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.usermodel.VerticalAlignment
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.xssf.streaming.SXSSFWorkbook
 */
package com.jiuqi.va.basedata.common;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jiuqi.va.basedata.common.BaseDataCoreI18nUtil;
import com.jiuqi.va.basedata.domain.BaseDataExcleColumn;
import com.jiuqi.va.basedata.domain.BaseDataImportTemplateDTO;
import com.jiuqi.va.basedata.service.BaseDataService;
import com.jiuqi.va.basedata.service.EnumDataService;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.StorageFieldConsts;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.domain.enumdata.EnumDataDO;
import com.jiuqi.va.domain.enumdata.EnumDataDTO;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.feign.util.RequestContextUtil;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
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

public class BasedataExcelUtils {
    private static Logger logger = LoggerFactory.getLogger(BasedataExcelUtils.class);
    private static BaseDataService baseDataClient = (BaseDataService)ApplicationContextRegister.getBean(BaseDataService.class);
    private static EnumDataService enumDataClient = (EnumDataService)ApplicationContextRegister.getBean(EnumDataService.class);
    private static AuthUserClient authUserClient = (AuthUserClient)ApplicationContextRegister.getBean(AuthUserClient.class);
    private static DataModelClient dataModelClient = (DataModelClient)ApplicationContextRegister.getBean(DataModelClient.class);
    private static BaseDataDefineClient baseDataDefineClient = (BaseDataDefineClient)ApplicationContextRegister.getBean(BaseDataDefineClient.class);
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

    public static List<BaseDataExcleColumn> getExcelColumns(String tableName, boolean queryflag) {
        return BasedataExcelUtils.getExcelColumnsByBasedataDefine(tableName, queryflag);
    }

    public static List<BaseDataExcleColumn> getExcelColumnsByTemplateColList(String tableName, List<Map<String, Object>> colList) {
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
        DataModelDO define = BasedataExcelUtils.getDataModalDefine(tableName);
        List<Map<String, Object>> showFields = BasedataExcelUtils.getBasedataShowFieldsByTableName(tableName);
        ArrayList<BaseDataExcleColumn> res = new ArrayList<BaseDataExcleColumn>();
        HashMap<String, BaseDataExcleColumn> colSearchMap = new HashMap<String, BaseDataExcleColumn>();
        HashMap<String, Map<String, Object>> showFieldSearchMap = new HashMap<String, Map<String, Object>>();
        for (Map<String, Object> showField : showFields) {
            showFieldSearchMap.put((String)showField.get("columnName"), showField);
        }
        for (DataModelColumn column : define.getColumns()) {
            colSearchMap.put(column.getColumnName(), BasedataExcelUtils.getExcelColumnByDataModelColumn(column));
        }
        HashSet<String> requiredFieldSet = new HashSet<String>();
        requiredFieldSet.add("CODE");
        requiredFieldSet.add("NAME");
        String colName = null;
        String colTitle = null;
        Object colType = null;
        Object colUppercase = null;
        Object colLengths = null;
        BaseDataExcleColumn column = null;
        Object checkObj = null;
        Boolean checkval = false;
        for (Map<String, Object> col : colList) {
            Map showField;
            if (col == null) {
                res.add(null);
                continue;
            }
            colName = (String)col.get("name");
            colTitle = (String)col.get("title");
            if (!colSearchMap.containsKey(colName) && col.containsKey("impExtColumn")) {
                column = new BaseDataExcleColumn();
                column.setColumnName(colName);
                column.setColumnTitle(colTitle);
                colType = col.get("type");
                if (colType instanceof DataModelType.ColumnType) {
                    column.setColumnType((DataModelType.ColumnType)colType);
                } else if (colType != null) {
                    column.setColumnType(DataModelType.ColumnType.valueOf((String)colType.toString()));
                }
                colUppercase = col.get("uppercase");
                if (colUppercase instanceof Boolean) {
                    column.setUppercase((Boolean)colUppercase);
                } else if (colUppercase instanceof String) {
                    column.setUppercase(Boolean.parseBoolean((String)colUppercase));
                } else {
                    column.setUppercase(Boolean.FALSE);
                }
                colLengths = col.get("lengths");
                if (colLengths instanceof Integer) {
                    column.setLengths(new Integer[]{(Integer)colLengths});
                } else if (colLengths instanceof Integer[]) {
                    column.setLengths((Integer[])colLengths);
                }
                res.add(column);
                continue;
            }
            column = (BaseDataExcleColumn)((Object)colSearchMap.get(colName));
            if (column == null) continue;
            column.setColumnTitle(colTitle);
            checkObj = col.get("checkval");
            checkval = checkObj == null ? Boolean.valueOf(false) : (checkObj instanceof Boolean ? (Boolean)checkObj : Boolean.valueOf("1".equals(checkObj) || "true".equals(checkObj)));
            column.setCheckval(checkval);
            if (requiredFieldSet.contains(column.getColumnName())) {
                column.setNullable(false);
            }
            if ((showField = (Map)showFieldSearchMap.get(column.getColumnName())) != null) {
                Boolean multiple = (Boolean)showField.get("multiple");
                column.setMultiple(multiple == null ? Boolean.FALSE : multiple);
                Boolean required = (Boolean)showField.get("required");
                column.setNullable(required == null ? Boolean.TRUE : required == false);
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
                    column.setMappingType(4);
                } else if (reftable.startsWith("MD_")) {
                    column.setMappingType(1);
                } else if (reftable.startsWith("EM_")) {
                    column.setMappingType(2);
                } else if (reftable.startsWith("AUTH_USER")) {
                    column.setMappingType(3);
                } else {
                    if (column.getMappingType() != null && column.getMappingType() != 0) {
                        column.setMappingType(null);
                    }
                    column.setMapping(null);
                }
            }
            res.add(column);
        }
        BasedataExcelUtils.handleDriveField(tableName, res);
        return res;
    }

    private static void handleDriveField(String tableName, List<BaseDataExcleColumn> columns) {
        BaseDataDefineDO basedataDefine = BasedataExcelUtils.getDefine(tableName);
        if (basedataDefine.getSharetype() != 0) {
            return;
        }
        ArrayNode jsonarray = JSONUtil.parseObject((String)basedataDefine.getDefine()).withArray("fieldProps");
        List listMap = null;
        if (jsonarray != null) {
            listMap = JSONUtil.parseMapArray((String)jsonarray.toString());
        }
        HashMap driverQueryMap = new HashMap();
        if (listMap != null && !listMap.isEmpty()) {
            listMap.forEach(col -> {
                String columnName = (String)col.get("columnName");
                String driveField = (String)col.get("driveField");
                if (StringUtils.hasText(columnName) && StringUtils.hasText(driveField)) {
                    driverQueryMap.put(columnName, col);
                }
            });
        }
        for (BaseDataExcleColumn column : columns) {
            if (column == null || !driverQueryMap.containsKey(column.getColumnName())) continue;
            column.setDriveField((String)((Map)driverQueryMap.get(column.getColumnName())).get("driveField"));
        }
    }

    public static List<Map<String, Object>> getBasedataShowFieldsByTableName(String tableName) {
        ArrayList<Map<String, Object>> showColumns = new ArrayList<Map<String, Object>>();
        BaseDataDefineDO basedataDefine = BasedataExcelUtils.getDefine(tableName);
        ObjectNode objectNode = JSONUtil.parseObject((String)basedataDefine.getDefine());
        ArrayNode jsonarray = objectNode.withArray("showFields");
        if (jsonarray == null || jsonarray.isEmpty()) {
            jsonarray = objectNode.withArray("defaultShowFields");
        }
        if (jsonarray == null || jsonarray.isEmpty()) {
            return showColumns;
        }
        return JSONUtil.parseMapArray((String)jsonarray.toString());
    }

    public static List<BaseDataExcleColumn> getExcelColumnsByBasedataDefine(String tableName, boolean queryflag) {
        List<Map<String, Object>> showColumns = BasedataExcelUtils.getBasedataShowFieldsByTableName(tableName);
        DataModelDO define = BasedataExcelUtils.getDataModalDefine(tableName);
        ArrayList<BaseDataExcleColumn> res = new ArrayList<BaseDataExcleColumn>();
        HashMap<String, BaseDataExcleColumn> colSearchMap = new HashMap<String, BaseDataExcleColumn>();
        for (BaseDataExcleColumn baseDataExcleColumn : BasedataExcelUtils.getExcelColumnListByDataModelColumnList(define.getColumns())) {
            colSearchMap.put(baseDataExcleColumn.getColumnName(), baseDataExcleColumn);
        }
        for (Map map : showColumns) {
            BaseDataExcleColumn column = (BaseDataExcleColumn)((Object)colSearchMap.get(map.get("columnName")));
            Boolean isQueryColumn = (Boolean)map.get("isQueryColumn");
            if (column == null || queryflag && (isQueryColumn == null || !isQueryColumn.booleanValue())) continue;
            column.setNullable((Boolean)map.get("required") == false);
            column.setColumnTitle(map.get("columnTitle").toString());
            res.add(column);
        }
        BasedataExcelUtils.handleDriveField(tableName, res);
        return res;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void exportData(String[] tableNames, Map<String, List<BaseDataExcleColumn>> columnListMap, Map<String, List<BaseDataDO>> dataListMap, String fileName, Object ... obj) {
        Workbook workbook = null;
        try (OutputStream outputStream = RequestContextUtil.getOutputStream();){
            RequestContextUtil.setResponseContentType((String)"arraybuffer");
            RequestContextUtil.setResponseCharacterEncoding((String)"utf-8");
            RequestContextUtil.setResponseHeader((String)"content-disposition", (String)("attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx"));
            BaseDataImportTemplateDTO param = new BaseDataImportTemplateDTO();
            param.setUnitcode(RequestContextUtil.getParameter((String)"unitcode"));
            param.setRootCode(RequestContextUtil.getParameter((String)"rootCode"));
            if (StringUtils.hasText(RequestContextUtil.getParameter((String)"showStopped"))) {
                param.setShowStopped(Integer.parseInt(RequestContextUtil.getParameter((String)"showStopped")));
            }
            workbook = BasedataExcelUtils.getDataExcel(tableNames, columnListMap, dataListMap, param);
            workbook.write(outputStream);
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        finally {
            if (workbook != null) {
                try {
                    workbook.close();
                }
                catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
                if (workbook instanceof SXSSFWorkbook) {
                    ((SXSSFWorkbook)workbook).dispose();
                }
            }
        }
    }

    public static Workbook getTemplateExcel(String ... tableNames) {
        return BasedataExcelUtils.getTemplateExcel(tableNames, null);
    }

    public static Workbook getTemplateExcel(String[] tableNames, Map<String, List<BaseDataExcleColumn>> columnListMap) {
        SXSSFWorkbook workbook = null;
        ArrayList allColumn = new ArrayList();
        if (columnListMap == null) {
            columnListMap = new HashMap<String, List<BaseDataExcleColumn>>();
        }
        for (String tableName : tableNames) {
            if (columnListMap.containsKey(tableName)) continue;
            columnListMap.put(tableName, BasedataExcelUtils.getExcelColumns(tableName, false));
            allColumn.addAll(columnListMap.get(tableName));
        }
        try {
            workbook = new SXSSFWorkbook(100);
            for (String tableName : tableNames) {
                BasedataExcelUtils.buildTemplateSheet(columnListMap.get(tableName), workbook.createSheet(BasedataExcelUtils.getDefine(tableName).getTitle().replace("/", "_").replace("\\", "_")), (Workbook)workbook);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return workbook;
    }

    public static Workbook getDataExcel(String ... tableNames) {
        return BasedataExcelUtils.getDataExcel(tableNames, null, null);
    }

    public static Workbook getDataExcel(String[] tableNames, Map<String, List<BaseDataExcleColumn>> columnListMap, Map<String, List<BaseDataDO>> dataListMap) {
        return BasedataExcelUtils.getDataExcel(tableNames, columnListMap, dataListMap, null);
    }

    public static Workbook getDataExcel(String[] tableNames, Map<String, List<BaseDataExcleColumn>> columnListMap, Map<String, List<BaseDataDO>> dataListMap, BaseDataImportTemplateDTO param) {
        columnListMap = columnListMap == null ? new HashMap<String, List<BaseDataExcleColumn>>() : columnListMap;
        dataListMap = dataListMap == null ? new HashMap<String, List<BaseDataDO>>() : dataListMap;
        List cols = null;
        for (String tableName : tableNames) {
            if (!columnListMap.containsKey(tableName)) {
                cols = BasedataExcelUtils.getExcelColumns(tableName, true);
                columnListMap.put(tableName, cols);
            } else {
                cols = (List)columnListMap.get(tableName);
            }
            if (param != null && param.getShowStopped() == 1) {
                BaseDataExcleColumn stopflagCol = new BaseDataExcleColumn();
                stopflagCol.setColumnName("STOPFLAG");
                stopflagCol.setColumnTitle(StorageFieldConsts.getFtStopFlag());
                stopflagCol.setColumnType(DataModelType.ColumnType.INTEGER);
                cols.add(stopflagCol);
            }
            if (dataListMap.containsKey(tableName)) continue;
            dataListMap.put(tableName, BasedataExcelUtils.getBaseDataList(tableName, cols, param));
        }
        Workbook workbook = BasedataExcelUtils.getTemplateExcel(tableNames, columnListMap);
        if (workbook != null) {
            for (int i = 0; i < tableNames.length; ++i) {
                BasedataExcelUtils.buildDataSheet(tableNames[i], (List)columnListMap.get(tableNames[i]), (List)dataListMap.get(tableNames[i]), workbook.getSheetAt(i), workbook);
            }
        }
        return workbook;
    }

    private static void buildTemplateSheet(List<BaseDataExcleColumn> columnList, Sheet sheet, Workbook workbook) {
        if (columnList == null || columnList.isEmpty()) {
            return;
        }
        Row row = sheet.createRow(0);
        row.setHeightInPoints(20.0f);
        int defaultWidth = 5120;
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short)12);
        CellStyle headStyle = workbook.createCellStyle();
        headStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.SEA_GREEN.getIndex());
        headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headStyle.setAlignment(HorizontalAlignment.LEFT);
        headStyle.setFont(font);
        CellStyle headStyleStop = workbook.createCellStyle();
        headStyleStop.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headStyleStop.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_40_PERCENT.getIndex());
        for (int i = 0; i < columnList.size(); i = (int)((short)(i + 1))) {
            BaseDataExcleColumn column = columnList.get(i);
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

    private static void buildDataSheet(String tableName, List<BaseDataExcleColumn> columnList, List<BaseDataDO> dataList, Sheet sheet, Workbook workbook) {
        if (dataList == null || dataList.isEmpty()) {
            return;
        }
        BaseDataDefineDTO param = new BaseDataDefineDTO();
        param.setName(tableName);
        param.setDeepClone(Boolean.valueOf(false));
        BaseDataDefineDO define = baseDataDefineClient.get(param);
        boolean isTree = define.getStructtype() == 2 || define.getStructtype() == 3;
        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(workbook.createDataFormat().getFormat("yyyy/MM/dd"));
        CellStyle timeStyle = workbook.createCellStyle();
        timeStyle.setDataFormat(workbook.createDataFormat().getFormat("yyyy/MM/dd HH:mm:ss"));
        CellStyle errorStyle = workbook.createCellStyle();
        errorStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        errorStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
        DecimalFormat numericStyle = new DecimalFormat("0");
        Row curRow = null;
        Cell cell = null;
        BaseDataDO data = null;
        for (int i = 0; i < dataList.size(); ++i) {
            data = dataList.get(i);
            curRow = sheet.createRow(i + 1);
            for (int j = 0; j < columnList.size(); ++j) {
                cell = curRow.createCell(j);
                try {
                    BasedataExcelUtils.setCellValue(cell, data, columnList.get(j), dateStyle, timeStyle, numericStyle, isTree);
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

    public static void buildEnumSheet(Workbook workbook, List<BaseDataExcleColumn> columnList) {
        for (BaseDataExcleColumn column : columnList) {
            Integer mappingType = column.getMappingType();
            if (mappingType == null) continue;
            if (mappingType == 2 && column.getMapping() != null) {
                String enumType = column.getMapping().split("\\.")[0];
                List<EnumDataDO> enumDataList = BasedataExcelUtils.getEnumDataList(enumType);
                Sheet sheet = workbook.createSheet(column.getColumnTitle());
                List<BaseDataExcleColumn> enumColumnList = BasedataExcelUtils.getEnumColumnList();
                BasedataExcelUtils.buildTemplateSheet(enumColumnList, sheet, workbook);
                for (int i = 0; i < enumDataList.size(); ++i) {
                    Row row = sheet.createRow(i + 1);
                    EnumDataDO data = enumDataList.get(i);
                    Cell valueCell = row.createCell(0);
                    valueCell.setCellValue(data.getVal());
                    Cell titleCell = row.createCell(1);
                    titleCell.setCellValue(data.getTitle());
                }
                continue;
            }
            if (column.getColumnType() == DataModelType.ColumnType.INTEGER && mappingType != 0) continue;
        }
    }

    private static List<BaseDataExcleColumn> getEnumColumnList() {
        ArrayList<BaseDataExcleColumn> res = new ArrayList<BaseDataExcleColumn>();
        BaseDataExcleColumn valueColumn = new BaseDataExcleColumn();
        valueColumn.setColumnTitle("\u679a\u4e3e\u503c");
        res.add(valueColumn);
        BaseDataExcleColumn titleColumn = new BaseDataExcleColumn();
        titleColumn.setColumnTitle("\u679a\u4e3e\u540d\u79f0");
        res.add(titleColumn);
        return res;
    }

    private static void setCellValue(Cell cell, BaseDataDO data, BaseDataExcleColumn field, CellStyle dateStyle, CellStyle timeStyle, DecimalFormat numericStyle, boolean isTree) {
        if (field == null) {
            return;
        }
        String fieldName = field.getColumnName().toLowerCase();
        Object value = data.get((Object)fieldName);
        if ("ordinal".equalsIgnoreCase(fieldName)) {
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
                cell.setCellValue(BaseDataCoreI18nUtil.getMessage("basedata.enum.confirm.no", new Object[0]));
            }
            return;
        }
        String mapping = field.getMapping();
        String refTable = mapping == null ? "" : mapping.split("\\.")[0];
        HashMap showTitleMap = data.get((Object)"showTitleMap") == null ? new HashMap() : (HashMap)data.get((Object)"showTitleMap");
        int level = 0;
        if (isTree) {
            level = data.getParents().split("\\/").length;
        }
        block0 : switch (type) {
            case UUID: 
            case NVARCHAR: 
            case CLOB: {
                if (StringUtils.hasText(refTable) || fieldName.equals("parentcode")) {
                    Object refValue = showTitleMap.get(fieldName);
                    cell.setCellValue(refValue == null ? "" : refValue.toString());
                    break;
                }
                if (isTree && fieldName.equals("name")) {
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
                    cell.setCellValue(Integer.valueOf(value.toString()) == 1 ? BaseDataCoreI18nUtil.getMessage("basedata.enum.confirm.yes", new Object[0]) : BaseDataCoreI18nUtil.getMessage("basedata.enum.confirm.no", new Object[0]));
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

    public static void userFormat(String tableName, List<BaseDataDO> dataList) {
        if (!StringUtils.hasText(tableName) || dataList == null || dataList.isEmpty()) {
            return;
        }
        DataModelDO dataModelDO = BasedataExcelUtils.getDataModalDefine(tableName);
        ArrayList<String> userFieldList = new ArrayList<String>();
        for (DataModelColumn column : dataModelDO.getColumns()) {
            if (column.getMappingType() == null || column.getMappingType() != 3) continue;
            userFieldList.add(column.getColumnName().toLowerCase());
        }
        List<UserDO> userList = BasedataExcelUtils.getUserList();
        HashMap<String, String> userQueryMap = new HashMap<String, String>();
        for (UserDO user : userList) {
            userQueryMap.put(user.getId(), user.getUsername());
        }
        for (BaseDataDO data : dataList) {
            Iterator iterator = userFieldList.iterator();
            while (iterator.hasNext()) {
                String userField;
                data.put(userField, userQueryMap.get(data.get((Object)(userField = (String)iterator.next())) != null ? data.get((Object)userField).toString() : data.get((Object)userField)));
            }
        }
    }

    public static BaseDataDefineDO getDefine(String tableName) {
        BaseDataDefineDTO refDefineParam = new BaseDataDefineDTO();
        refDefineParam.setName(tableName);
        refDefineParam.setDeepClone(Boolean.valueOf(false));
        PageVO res = baseDataDefineClient.list(refDefineParam);
        if (res != null && res.getRows() != null && !res.getRows().isEmpty()) {
            return (BaseDataDefineDO)res.getRows().get(0);
        }
        throw new RuntimeException(BaseDataCoreI18nUtil.getMessage("basedata.error.bddefine.not.found", tableName));
    }

    public static DataModelDO getDataModalDefine(String tableName) {
        DataModelDTO defineParam = new DataModelDTO();
        defineParam.setName(tableName);
        defineParam.setDeepClone(Boolean.valueOf(false));
        return dataModelClient.get(defineParam);
    }

    public static List<BaseDataDO> getBaseDataList(String tableName, String ... params) {
        BaseDataImportTemplateDTO tmpParam = new BaseDataImportTemplateDTO();
        if (params != null) {
            tmpParam.setRootCode(params[0]);
            if (params.length > 1) {
                tmpParam.setUnitcode(params[1]);
            }
        }
        return BasedataExcelUtils.getBaseDataList(tableName, null, tmpParam);
    }

    public static List<BaseDataDO> getBaseDataList(String tableName, List<BaseDataExcleColumn> cols, BaseDataImportTemplateDTO param) {
        PageVO<BaseDataDO> queryRes;
        BaseDataDTO queryParam = new BaseDataDTO();
        queryParam.setTableName(tableName);
        queryParam.setStopflag(Integer.valueOf(-1));
        queryParam.setRecoveryflag(Integer.valueOf(0));
        queryParam.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL_WITH_REF);
        queryParam.setAuthType(BaseDataOption.AuthType.NONE);
        queryParam.addExtInfo("RefShowCode", (Object)(cols != null ? JSONUtil.toJSONString(cols) : "[]"));
        if (param != null) {
            queryParam.setStopflag(Integer.valueOf(param.getShowStopped() == 2 ? 0 : -1));
            queryParam.setUnitcode(param.getUnitcode());
            if (StringUtils.hasText(param.getRootCode())) {
                queryParam.setCode(param.getRootCode());
                queryParam.setQueryChildrenType(BaseDataOption.QueryChildrenType.ALL_CHILDREN_WITH_SELF);
            }
        }
        return (queryRes = baseDataClient.list(queryParam)) == null ? null : queryRes.getRows();
    }

    private static List<EnumDataDO> getEnumDataList(String tableName) {
        EnumDataDTO enumDataDTO = new EnumDataDTO();
        enumDataDTO.setBiztype(tableName);
        return enumDataClient.list(enumDataDTO);
    }

    protected static List<UserDO> getUserList() {
        UserDTO userDTO = new UserDTO();
        PageVO page = authUserClient.list(userDTO);
        return page.getRows();
    }

    public static List<BaseDataExcleColumn> getExcelColumnListByDataModelColumnList(List<DataModelColumn> dataModelColumnList) {
        ArrayList<BaseDataExcleColumn> res = new ArrayList<BaseDataExcleColumn>();
        if (dataModelColumnList == null || dataModelColumnList.isEmpty()) {
            return res;
        }
        for (DataModelColumn column : dataModelColumnList) {
            res.add(BasedataExcelUtils.getExcelColumnByDataModelColumn(column));
        }
        return res;
    }

    public static BaseDataExcleColumn getExcelColumnByDataModelColumn(DataModelColumn dataModelColumn) {
        BaseDataExcleColumn res = new BaseDataExcleColumn();
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
        res.setMultiple(false);
        res.setCheckval(true);
        res.setUppercase(upperCaseField.contains(dataModelColumn.getColumnName()));
        return res;
    }

    public static void checkHeader(Sheet sheet, List<BaseDataExcleColumn> columns, Integer firstLine) {
        if (firstLine == 1) {
            return;
        }
        Row row = sheet.getRow(firstLine - 2);
        for (int i = 0; i < columns.size(); ++i) {
            if (columns.get(i) == null) continue;
            Cell cell = row.getCell(i);
            if (cell == null) {
                throw new RuntimeException(BaseDataCoreI18nUtil.getMessage("basedata.error.template.import.check.excel.header", new Object[0]));
            }
            String cellValue = cell.getStringCellValue();
            if (columns.get(i).getColumnTitle().equals(cellValue)) continue;
            throw new RuntimeException(BaseDataCoreI18nUtil.getMessage("basedata.error.template.import.check.excel.header", new Object[0]));
        }
    }
}

