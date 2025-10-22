/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.domain.enumdata.EnumDataDO
 *  com.jiuqi.va.domain.enumdata.EnumDataDTO
 *  com.jiuqi.va.domain.org.OrgCacheDO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  com.jiuqi.va.feign.client.EnumDataClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.reportdatasync.util;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.domain.enumdata.EnumDataDO;
import com.jiuqi.va.domain.enumdata.EnumDataDTO;
import com.jiuqi.va.domain.org.OrgCacheDO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.feign.client.EnumDataClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartFile;

public class ReportDataSyncOrgFilePortUtil {
    private static Environment environment;
    private static final String[] OrgBaseHeads;
    private static final String[] OrgBasefield;
    private static BaseDataDefineClient baseDataDefineClient;
    private static String offset;
    private static String whiteSpace1;
    private static String whiteSpace2;
    private static String whiteSpace3;
    private static String whiteSpace4;
    private static String whiteSpace5;
    private static String whiteSpace6;
    private static String whiteSpace7;
    private static String whiteSpace8;
    private static String whiteSpace9;

    public static List<OrgDTO> getOrgListByExcle(MultipartFile multipartFile, LinkedHashMap<String, String> orgExtMap, Map<String, DataModelColumn> fieldTypeMap) {
        ArrayList<OrgDTO> res = new ArrayList<OrgDTO>();
        LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
        for (int i = 0; i < OrgBasefield.length; ++i) {
            fieldMap.put(OrgBasefield[i], OrgBaseHeads[i]);
        }
        fieldMap.putAll(orgExtMap);
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(multipartFile.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);
            Set keys = fieldMap.keySet();
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); ++i) {
                Row row = sheet.getRow(i);
                if (row.getCell(0) == null || row.getCell(0).getStringCellValue() == null || row.getCell(0).getStringCellValue().equals("")) continue;
                int j = 0;
                OrgDTO org = new OrgDTO();
                for (String key : keys) {
                    ReportDataSyncOrgFilePortUtil.setOrgValue(key, row.getCell(j), org, fieldTypeMap);
                    ++j;
                }
                if (!ReportDataSyncOrgFilePortUtil.checkOrg(org)) continue;
                res.add(org);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    private static Object getCellValueByCell(Cell cell) {
        if (cell == null || cell.toString().trim().equals("")) {
            return "";
        }
        Object cellValue = "";
        CellType cellType = cell.getCellType();
        if (cellType == CellType.FORMULA) {
            cellValue = "";
            return cellValue;
        }
        switch (cellType) {
            case STRING: {
                cellValue = cell.getStringCellValue().trim();
                break;
            }
            case BOOLEAN: {
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            }
            case NUMERIC: {
                if (DateUtil.isCellDateFormatted(cell)) {
                    cellValue = cell.getDateCellValue();
                    break;
                }
                cellValue = cell.getNumericCellValue();
                break;
            }
            default: {
                cellValue = "";
            }
        }
        cellValue = !org.springframework.util.StringUtils.hasText(cellValue.toString()) ? "" : cellValue;
        return cellValue;
    }

    private static boolean checkOrg(OrgDTO org) {
        return org.springframework.util.StringUtils.hasText(org.getCode()) && org.springframework.util.StringUtils.hasText(org.getName());
    }

    private static void setOrgValue(String key, Cell cell, OrgDTO org, Map<String, DataModelColumn> fieldTypeMap) {
        if (!org.springframework.util.StringUtils.hasText(key) || cell == null || cell.getCellType().equals((Object)CellType.BLANK)) {
            return;
        }
        Object value = ReportDataSyncOrgFilePortUtil.getCellValueByCell(cell);
        DataModelColumn column = fieldTypeMap.get(key);
        if (key.equalsIgnoreCase("code")) {
            org.setCode(cell.getStringCellValue());
            return;
        }
        if (key.equalsIgnoreCase("name")) {
            org.setName(cell.getStringCellValue());
            return;
        }
        if (key.equalsIgnoreCase("parentcode")) {
            org.setParentcode(cell.getStringCellValue());
            return;
        }
        if (!fieldTypeMap.keySet().contains(key)) {
            return;
        }
        String colName = column.getColumnName().toLowerCase();
        DataModelType.ColumnType type = column.getColumnType();
        switch (type) {
            case UUID: 
            case NVARCHAR: 
            case CLOB: {
                org.put(colName, (Object)value.toString());
                break;
            }
            case INTEGER: {
                Double dvalue = Double.valueOf(value.toString());
                if (dvalue == null) break;
                org.put(colName, (Object)dvalue.intValue());
                break;
            }
            case NUMERIC: {
                if (!(value instanceof Double) || value == null) break;
                Integer[] columnLength = column.getLengths();
                if (columnLength.length == 2 && columnLength[1] != null && columnLength[1] > 0) {
                    String StringVal = String.format("%." + columnLength[1] + "f", value);
                    if (StringVal.length() > columnLength[0]) break;
                    Double doubleVal = Double.valueOf(StringVal);
                    org.put(colName, (Object)doubleVal);
                    break;
                }
                org.put(colName, value);
                break;
            }
            case DATE: {
                org.put(colName, value);
                break;
            }
            case TIMESTAMP: {
                org.put(colName, value);
                break;
            }
        }
    }

    public static Workbook exportExcel(String title, PageVO<OrgDO> baseOrgs, LinkedHashMap<String, String> orgExtMap, Map<String, DataModelColumn> fieldTypeMap, List<String> headCodes, List<String> headTitles) {
        ArrayList<OrgDO> dataList = baseOrgs.getRows();
        LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
        for (int i = 0; i < OrgBasefield.length; ++i) {
            fieldMap.put(OrgBasefield[i], OrgBaseHeads[i]);
        }
        Map<String, Object> orgCode2GzCodeMap = baseOrgs.getRows().stream().filter(orgDO -> !StringUtils.isEmpty((String)((String)orgDO.get((Object)"gzcode")))).collect(Collectors.toMap(OrgDO::getCode, orgDo -> orgDo.get((Object)"gzcode"), (o1, o2) -> o1));
        fieldMap.putAll(orgExtMap);
        int defaultWidth = 5120;
        ArrayList<OrgDO> orgDataList = null;
        if (dataList.size() > 0 && dataList.get(0) instanceof OrgCacheDO) {
            orgDataList = new ArrayList<OrgDO>();
            for (OrgDO orgDO2 : dataList) {
                orgDataList.add(new OrgDO((Map)orgDO2));
            }
        } else {
            orgDataList = dataList;
        }
        ReportDataSyncOrgFilePortUtil.handleRelationValue(fieldMap, fieldTypeMap, (List<OrgDO>)orgDataList);
        SXSSFWorkbook workbook = null;
        try {
            workbook = new SXSSFWorkbook(1000);
            SXSSFSheet sheet = workbook.createSheet(title);
            SXSSFRow row = sheet.createRow(0);
            Font font = workbook.createFont();
            font.setFontHeightInPoints((short)12);
            CellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.SEA_GREEN.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            style.setAlignment(HorizontalAlignment.LEFT);
            style.setFont(font);
            ArrayList heads = new ArrayList();
            fieldMap.forEach((key, value) -> {
                heads.add(value);
                headTitles.add((String)value);
                headCodes.add((String)key);
            });
            for (int i = 0; i < fieldMap.size(); i = (int)((short)(i + 1))) {
                sheet.setColumnWidth(i, defaultWidth);
                SXSSFCell cell = row.createCell(i);
                cell.setCellValue(new HSSFRichTextString(heads.get(i) == null ? "" : (String)heads.get(i)));
                cell.setCellStyle(style);
            }
            int index = 1;
            CellStyle dateStyle = null;
            CellStyle timeStyle = null;
            String dateformat = "yyyy/MM/dd";
            CellStyle defaultStyle = workbook.createCellStyle();
            defaultStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            defaultStyle.setAlignment(HorizontalAlignment.LEFT);
            CellStyle defaultStyle2 = workbook.createCellStyle();
            defaultStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
            defaultStyle2.setAlignment(HorizontalAlignment.RIGHT);
            dateStyle = workbook.createCellStyle();
            DataFormat dateFormatter = workbook.createDataFormat();
            dateStyle.setDataFormat(dateFormatter.getFormat(dateformat));
            timeStyle = workbook.createCellStyle();
            DataFormat timeFormatter = workbook.createDataFormat();
            timeStyle.setDataFormat(timeFormatter.getFormat("yyyy/MM/dd hh:MM:ss"));
            for (OrgDO org : orgDataList) {
                row = sheet.createRow(index);
                int cellIndex = 0;
                String gzcode = (String)org.get((Object)"gzcode");
                String parentCode = (String)org.get((Object)"parentcode");
                boolean existsGzCode = !org.springframework.util.StringUtils.isEmpty(gzcode);
                for (String key2 : fieldMap.keySet()) {
                    SXSSFCell cell = row.createCell(cellIndex);
                    Object val = org.getValueOf(key2);
                    if (existsGzCode) {
                        if ("CODE".equals(key2) || "ORGCODE".equals(key2)) {
                            val = gzcode;
                        }
                        if ("SHOWTITLE".equals(key2)) {
                            val = org.getCode() + " " + org.getName();
                        }
                    }
                    if ("PARENTCODE".equals(key2) && orgCode2GzCodeMap.containsKey(parentCode)) {
                        val = orgCode2GzCodeMap.get(parentCode);
                    }
                    if ("GCPARENTS".equals(key2)) {
                        String[] parentCodes = val.toString().split("/");
                        StringBuilder gzParentCodes = new StringBuilder();
                        for (int i = 0; i < parentCodes.length; ++i) {
                            String code = orgCode2GzCodeMap.containsKey(parentCodes[i]) ? orgCode2GzCodeMap.get(parentCodes[i]).toString() : parentCodes[i];
                            gzParentCodes.append(code);
                            if (i == parentCodes.length - 1) continue;
                            gzParentCodes.append("/");
                        }
                        val = gzParentCodes.toString();
                    }
                    CellStyle cellStyle = defaultStyle;
                    if (DataModelType.ColumnType.INTEGER.equals((Object)fieldTypeMap.get(key2).getColumnType()) || DataModelType.ColumnType.NUMERIC.equals((Object)fieldTypeMap.get(key2).getColumnType())) {
                        cellStyle = defaultStyle2;
                    }
                    cell.setCellStyle(cellStyle);
                    ReportDataSyncOrgFilePortUtil.setCellValue(cell, val, fieldTypeMap.get(key2), dateStyle, timeStyle, org);
                    ++cellIndex;
                }
                ++index;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return workbook;
    }

    private static void handleRelationValue(LinkedHashMap<String, String> fieldMap, Map<String, DataModelColumn> fieldTypeMap, List<OrgDO> orgDataList) {
        if (orgDataList == null || orgDataList.size() == 0) {
            return;
        }
        String orgCate = orgDataList.get(0).getCategoryname();
        HashMap<String, Map<String, BaseDataDO>> basedataMap = new HashMap<String, Map<String, BaseDataDO>>();
        HashMap<String, Map<String, OrgDO>> orgMap = new HashMap<String, Map<String, OrgDO>>();
        HashMap<String, Map<String, EnumDataDO>> enumDataMap = new HashMap<String, Map<String, EnumDataDO>>();
        for (String key : fieldMap.keySet()) {
            DataModelColumn col = fieldTypeMap.get(key);
            if (col.getColumnName().equals("PARENTCODE")) {
                ReportDataSyncOrgFilePortUtil.setOrgMap(orgCate, orgMap);
                continue;
            }
            if (col.getMapping() == null) continue;
            String refTableName = col.getMapping().split("\\.")[0];
            int mappingType = col.getMappingType();
            if (mappingType == 1) {
                ReportDataSyncOrgFilePortUtil.loadBaseData(refTableName, basedataMap);
                continue;
            }
            if (mappingType == 4) {
                ReportDataSyncOrgFilePortUtil.setOrgMap(refTableName, orgMap);
                continue;
            }
            if (mappingType != 2) continue;
            ReportDataSyncOrgFilePortUtil.loadEnumData(refTableName, enumDataMap);
        }
        Object value = null;
        for (OrgDO orgDO : orgDataList) {
            for (String key : fieldMap.keySet()) {
                OrgDO ref;
                DataModelColumn col = fieldTypeMap.get(key);
                value = orgDO.get((Object)key.toLowerCase());
                if (value == null || !org.springframework.util.StringUtils.hasText(value.toString())) continue;
                if (col.getColumnName().equals("PARENTCODE")) {
                    OrgDO parent = (OrgDO)((Map)orgMap.get(orgCate)).get(orgDO.getParentcode());
                    orgDO.setParentcode(parent == null ? null : parent.getCode());
                    continue;
                }
                if (col.getMapping() == null) continue;
                String refTableName = col.getMapping().split("\\.")[0];
                int mappingType = col.getMappingType();
                if (mappingType == 1) {
                    if (value instanceof List) {
                        StringBuilder bdbsb = new StringBuilder();
                        for (String objcode : (List)value) {
                            BaseDataDO baseDataDO = (BaseDataDO)((Map)basedataMap.get(refTableName)).get(objcode);
                            if (baseDataDO == null) continue;
                            bdbsb.append(",").append(baseDataDO.getCode());
                        }
                        if (bdbsb.length() <= 0) continue;
                        orgDO.put(key.toLowerCase(), (Object)bdbsb.substring(1));
                        continue;
                    }
                    BaseDataDO baseDataDO = (BaseDataDO)((Map)basedataMap.get(refTableName)).get(value);
                    if (baseDataDO == null) continue;
                    orgDO.put(key.toLowerCase(), (Object)baseDataDO.getCode());
                    continue;
                }
                if (mappingType == 4) {
                    ref = (OrgDO)((Map)orgMap.get(refTableName)).get(value);
                    orgDO.put(key.toLowerCase(), (Object)(ref == null ? null : ref.getCode()));
                    continue;
                }
                if (mappingType != 2) continue;
                ref = (EnumDataDO)((Map)enumDataMap.get(refTableName)).get(value);
                orgDO.put(key.toLowerCase(), (Object)(ref == null ? null : ref.getVal()));
            }
        }
    }

    private static void loadEnumData(String tableName, Map<String, Map<String, EnumDataDO>> enumDataMap) {
        EnumDataClient enumDataClient = (EnumDataClient)ApplicationContextRegister.getBean(EnumDataClient.class);
        EnumDataDTO enumDataDTO = new EnumDataDTO();
        enumDataDTO.setBiztype(tableName);
        List enumDatas = enumDataClient.list(enumDataDTO);
        HashMap<String, EnumDataDO> map = new HashMap<String, EnumDataDO>();
        if (enumDatas != null && enumDatas.size() != 0) {
            for (EnumDataDO data : enumDatas) {
                map.put(data.getVal(), data);
            }
        }
        enumDataMap.put(tableName, map);
    }

    private static void setOrgMap(String categoryname, Map<String, Map<String, OrgDO>> allOrgMap) {
        if (allOrgMap.get(categoryname) != null) {
            return;
        }
        OrgDataClient orgDataClient = (OrgDataClient)ApplicationContextRegister.getBean(OrgDataClient.class);
        OrgDTO queryParam = new OrgDTO();
        queryParam.setCategoryname(categoryname);
        queryParam.setStopflag(Integer.valueOf(-1));
        queryParam.setRecoveryflag(Integer.valueOf(-1));
        queryParam.setAuthType(OrgDataOption.AuthType.NONE);
        queryParam.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
        HashMap<String, OrgDO> allOrg = new HashMap<String, OrgDO>();
        PageVO temp = orgDataClient.list(queryParam);
        if (temp != null && temp.getRows() != null) {
            for (OrgDO org : temp.getRows()) {
                allOrg.put(org.getCode(), org);
            }
        }
        allOrgMap.put(categoryname, allOrg);
    }

    public static BaseDataDefineDO getDefine(String tableName) {
        BaseDataDefineDTO refDefineParam = new BaseDataDefineDTO();
        refDefineParam.setName(tableName);
        refDefineParam.setDeepClone(Boolean.valueOf(false));
        PageVO res = baseDataDefineClient.list(refDefineParam);
        if (res != null && res.getRows() != null && res.getRows().size() > 0) {
            return (BaseDataDefineDO)res.getRows().get(0);
        }
        throw new RuntimeException("\u83b7\u53d6" + tableName + "\u8868\u5b9a\u4e49\u5931\u8d25");
    }

    private static void loadBaseData(String tableName, Map<String, Map<String, BaseDataDO>> basedataMap) {
        if (basedataMap.get(tableName) != null) {
            return;
        }
        BaseDataClient baseDataClient = (BaseDataClient)ApplicationContextRegister.getBean(BaseDataClient.class);
        BaseDataDTO queryParam = new BaseDataDTO();
        queryParam.setTableName(tableName);
        queryParam.setStopflag(Integer.valueOf(-1));
        queryParam.setRecoveryflag(Integer.valueOf(-1));
        queryParam.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
        queryParam.setIgnoreShareFields(Boolean.valueOf(true));
        queryParam.setAuthType(BaseDataOption.AuthType.NONE);
        PageVO queryRes = baseDataClient.list(queryParam);
        HashMap<String, BaseDataDO> dataMap = new HashMap<String, BaseDataDO>();
        if (queryRes != null && queryRes.getRows() != null) {
            for (BaseDataDO data : queryRes.getRows()) {
                dataMap.put(data.getObjectcode(), data);
            }
        }
        basedataMap.put(tableName, dataMap);
    }

    private static void setCellValue(SXSSFCell cell, Object value, DataModelColumn dataModelColumn, CellStyle dateStyle, CellStyle timeStyle, OrgDO org) {
        if (value == null || !org.springframework.util.StringUtils.hasText(value.toString())) {
            return;
        }
        String fieldName = dataModelColumn.getColumnName();
        int level = org.getParents().split("\\/").length;
        block0 : switch (dataModelColumn.getColumnType()) {
            case UUID: 
            case NVARCHAR: 
            case CLOB: {
                if (fieldName.equalsIgnoreCase("name")) {
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
                cell.setCellValue(Integer.valueOf(value.toString()).intValue());
                break;
            }
            case NUMERIC: {
                cell.setCellValue(Double.valueOf(value.toString()));
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

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void exportExcelTemplate(String title, String[] orgExtHeads, HttpServletResponse response) {
        String[] orgHeaders = new String[OrgBaseHeads.length + orgExtHeads.length];
        System.arraycopy(OrgBaseHeads, 0, orgHeaders, 0, OrgBaseHeads.length);
        System.arraycopy(orgExtHeads, 0, orgHeaders, OrgBaseHeads.length, orgExtHeads.length);
        int defaultWidth = 6400;
        SXSSFWorkbook workbook = null;
        try {
            workbook = new SXSSFWorkbook(1000);
            SXSSFSheet sheet = workbook.createSheet(title.split("\\(")[0]);
            SXSSFRow row = sheet.createRow(0);
            Font font = workbook.createFont();
            font.setFontHeightInPoints((short)12);
            CellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.SEA_GREEN.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            style.setAlignment(HorizontalAlignment.LEFT);
            style.setFont(font);
            for (int i = 0; i < orgHeaders.length; i = (int)((short)(i + 1))) {
                sheet.setColumnWidth(i, defaultWidth);
                SXSSFCell cell = row.createCell(i);
                cell.setCellValue(new HSSFRichTextString(orgHeaders[i] == null ? "" : orgHeaders[i]));
                cell.setCellStyle(style);
            }
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename*=utf-8'zh_cn'" + URLEncoder.encode(title + ".xlsx", "UTF-8"));
            workbook.write((OutputStream)response.getOutputStream());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (workbook != null) {
                try {
                    workbook.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static {
        String[] stringArray;
        String[] stringArray2;
        environment = (Environment)ApplicationContextRegister.getBean(Environment.class);
        if (Boolean.valueOf(environment.getProperty("nvwa.organization.modify-org-code.allow")).booleanValue()) {
            String[] stringArray3 = new String[5];
            stringArray3[0] = "\u673a\u6784\u4ee3\u7801";
            stringArray3[1] = "\u673a\u6784\u7f16\u7801";
            stringArray3[2] = "\u673a\u6784\u540d\u79f0";
            stringArray3[3] = "\u673a\u6784\u7b80\u79f0";
            stringArray2 = stringArray3;
            stringArray3[4] = "\u4e0a\u7ea7\u673a\u6784";
        } else {
            String[] stringArray4 = new String[4];
            stringArray4[0] = "\u673a\u6784\u4ee3\u7801";
            stringArray4[1] = "\u673a\u6784\u540d\u79f0";
            stringArray4[2] = "\u673a\u6784\u7b80\u79f0";
            stringArray2 = stringArray4;
            stringArray4[3] = "\u4e0a\u7ea7\u673a\u6784";
        }
        OrgBaseHeads = stringArray2;
        if (Boolean.valueOf(environment.getProperty("nvwa.organization.modify-org-code.allow")).booleanValue()) {
            String[] stringArray5 = new String[5];
            stringArray5[0] = "CODE";
            stringArray5[1] = "ORGCODE";
            stringArray5[2] = "NAME";
            stringArray5[3] = "SHORTNAME";
            stringArray = stringArray5;
            stringArray5[4] = "PARENTCODE";
        } else {
            String[] stringArray6 = new String[4];
            stringArray6[0] = "CODE";
            stringArray6[1] = "NAME";
            stringArray6[2] = "SHORTNAME";
            stringArray = stringArray6;
            stringArray6[3] = "PARENTCODE";
        }
        OrgBasefield = stringArray;
        baseDataDefineClient = (BaseDataDefineClient)ApplicationContextRegister.getBean(BaseDataDefineClient.class);
        offset = "  ";
        whiteSpace1 = "";
        whiteSpace2 = whiteSpace1 + offset;
        whiteSpace3 = whiteSpace2 + offset;
        whiteSpace4 = whiteSpace3 + offset;
        whiteSpace5 = whiteSpace4 + offset;
        whiteSpace6 = whiteSpace5 + offset;
        whiteSpace7 = whiteSpace6 + offset;
        whiteSpace8 = whiteSpace7 + offset;
        whiteSpace9 = whiteSpace8 + offset;
    }
}

