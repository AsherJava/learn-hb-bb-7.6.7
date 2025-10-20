/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.data.DataFieldDefineImpl
 *  com.jiuqi.va.biz.intf.data.DataFieldDefine
 *  com.jiuqi.va.biz.intf.data.DataTable
 *  com.jiuqi.va.biz.intf.value.ValueType
 *  com.jiuqi.va.biz.utils.Utils
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.commons.codec.binary.Base64
 *  org.apache.poi.hssf.util.HSSFColor$HSSFColorPredefined
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.CellType
 *  org.apache.poi.ss.usermodel.FillPatternType
 *  org.apache.poi.ss.usermodel.Font
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.VerticalAlignment
 *  org.apache.poi.xssf.streaming.SXSSFCell
 *  org.apache.poi.xssf.streaming.SXSSFRow
 *  org.apache.poi.xssf.streaming.SXSSFSheet
 *  org.apache.poi.xssf.streaming.SXSSFWorkbook
 *  org.apache.poi.xssf.usermodel.XSSFCellStyle
 *  org.apache.poi.xssf.usermodel.XSSFColor
 *  org.apache.poi.xssf.usermodel.XSSFFont
 */
package com.jiuqi.va.bill.utils;

import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.bill.utils.BillUtils;
import com.jiuqi.va.bill.utils.LargeExcelFileReaderUtil;
import com.jiuqi.va.biz.impl.data.DataFieldDefineImpl;
import com.jiuqi.va.biz.intf.data.DataFieldDefine;
import com.jiuqi.va.biz.intf.data.DataTable;
import com.jiuqi.va.biz.intf.value.ValueType;
import com.jiuqi.va.biz.utils.Utils;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelUtil {
    private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
    private static final String SHOWTITLE = "showTitle";
    private static final String TITLE = "title";

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void exportExcel(List<Map<String, Object>> titleList, List<Map<String, Object>> tableDataList, String selecttype, HttpServletResponse response) {
        SXSSFWorkbook workbook = new SXSSFWorkbook(1000);
        try {
            SXSSFSheet sheet;
            try {
                sheet = workbook.createSheet("\u5f02\u5e38\u6570\u636e");
            }
            catch (Exception e) {
                throw new NullPointerException(BillCoreI18nUtil.getMessage("va.billcore.excelutil.lackfont"));
            }
            SXSSFRow row = sheet.createRow(0);
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREEN.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            HashMap<String, String> state = new HashMap<String, String>();
            state.put("fieldName", "_stateMessage");
            state.put(TITLE, BillCoreI18nUtil.getMessage("va.billcore.excelutil.status"));
            HashMap<String, String> index = new HashMap<String, String>();
            index.put("fieldName", "_index");
            index.put(TITLE, BillCoreI18nUtil.getMessage("va.billcore.excelutil.index"));
            titleList.add(0, state);
            titleList.add(0, index);
            for (int i = 0; i < titleList.size(); ++i) {
                SXSSFCell cell = row.createCell(i);
                cell.setCellValue(titleList.get(i).get(TITLE).toString());
                cell.setCellStyle(style);
            }
            CellStyle redStyle = workbook.createCellStyle();
            Font redFont = workbook.createFont();
            redFont.setBold(true);
            redFont.setColor((short)10);
            redStyle.setFont(redFont);
            int column = 1;
            if (selecttype.equals("1")) {
                int length_1 = tableDataList.size();
                for (int i = 0; i < length_1; ++i) {
                    Map<String, Object> data = tableDataList.get(i);
                    SXSSFRow row1 = sheet.createRow(column);
                    for (int j = 0; j < titleList.size(); ++j) {
                        SXSSFCell cell1 = row1.createCell(j);
                        Object key = titleList.get(j).get("fieldName");
                        Object value = data.get(key);
                        if (data.get("_state").toString().equals("0")) {
                            cell1.setCellStyle(redStyle);
                        }
                        cell1.setCellType(CellType.STRING);
                        cell1.setCellValue(value != null ? value.toString() : "");
                    }
                    ++column;
                }
            } else {
                int length_1 = tableDataList.size();
                for (int i = 0; i < length_1; ++i) {
                    Map<String, Object> data = tableDataList.get(i);
                    if (data.get("_state").toString().equals("1")) continue;
                    SXSSFRow row1 = sheet.createRow(column);
                    for (int j = 0; j < titleList.size(); ++j) {
                        SXSSFCell cell1 = row1.createCell(j);
                        Object key = titleList.get(j).get("fieldName");
                        Object value = data.get(key);
                        cell1.setCellStyle(redStyle);
                        cell1.setCellType(CellType.STRING);
                        cell1.setCellValue(value != null ? value.toString() : "");
                    }
                    ++column;
                }
            }
            sheet.flushRows();
            ExcelUtil.setBrowser(response, workbook, "\u5f02\u5e38\u6570\u636e.xlsx");
        }
        catch (Exception e) {
            logger.error("\u5bfc\u51fa\u89e3\u6790\u5931\u8d25\uff1a" + e.getMessage(), e);
        }
        finally {
            try {
                workbook.close();
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public static void exportExcel(HttpServletResponse response, BillModelImpl model, Map<String, Object> params, Map<String, List<Map<String, Object>>> tableFields, Map<String, String> idNames) {
        List tableNames = (List)params.get("tableName");
        String fileName = model.getDefine().getMetaInfo() == null ? model.getDefine().getName() + BillCoreI18nUtil.getMessage("va.billcore.excelutil.subtableexportdata") + ".xlsx" : model.getDefine().getMetaInfo().getTitle() + BillCoreI18nUtil.getMessage("va.billcore.excelutil.subtableexportdata") + ".xlsx";
        logger.info("\u5bfc\u51fa\u89e3\u6790\u5f00\u59cb\uff0cfileName:{}", (Object)fileName);
        SXSSFWorkbook workbook = new SXSSFWorkbook(1000);
        try {
            HashMap<String, Integer> sheetNames = new HashMap<String, Integer>();
            for (String s : tableNames) {
                SXSSFSheet sheet;
                String tableName = null;
                try {
                    tableName = idNames != null ? idNames.get(s) : s;
                    model.getTable(tableName);
                }
                catch (Exception e) {
                    throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.excelutil.notcontainsubtable", new Object[]{tableName}));
                }
                DataTable table = model.getTable(tableName);
                try {
                    String sheetName = table.getTitle() + "||" + tableName;
                    if (sheetNames.containsKey(sheetName)) {
                        sheetNames.put(sheetName, (Integer)sheetNames.get(sheetName) + 1);
                        sheet = workbook.createSheet(sheetName + sheetNames.get(sheetName));
                    } else {
                        sheetNames.put(sheetName, 0);
                        sheet = workbook.createSheet(sheetName);
                    }
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    throw new NullPointerException(BillCoreI18nUtil.getMessage("va.billcore.excelutil.lackfont"));
                }
                String finalTableName = tableName;
                List<Map<String, Object>> list = Optional.ofNullable(tableFields.get(s)).orElseThrow(() -> new NullPointerException(BillCoreI18nUtil.getMessage("va.billcore.excelutil.notcontainfield", new Object[]{table.getTitle() + "(" + finalTableName + ")"})));
                List<DataFieldDefine> fields = list.stream().map(value -> (DataFieldDefine)table.getDefine().getFields().get(String.valueOf(value.get("name")))).collect(Collectors.toList());
                ExcelUtil.setTitle(workbook, sheet, fields);
                ExcelUtil.setData(workbook, sheet, table.getFilterRowsData(), fields, model, params, tableFields.get(s));
            }
            ExcelUtil.setBrowser(response, workbook, fileName);
        }
        catch (Exception e) {
            logger.error("\u5bfc\u51fa\u89e3\u6790\u5931\u8d25\uff1a" + e.getMessage(), e);
            throw new BillException(e.getMessage());
        }
        finally {
            try {
                workbook.close();
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private static void setTitle(SXSSFWorkbook workbook, SXSSFSheet sheet, List<DataFieldDefine> fields) {
        try {
            SXSSFRow row = sheet.createRow(0);
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREEN.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            for (int i = 0; i < fields.size(); ++i) {
                int width = 3072;
                if (fields.get(i).getValueType().equals((Object)ValueType.BOOLEAN)) {
                    width = 1536;
                } else if (fields.get(i).getValueType().equals((Object)ValueType.DATE)) {
                    width = 3072;
                } else if (fields.get(i).getValueType().equals((Object)ValueType.DATETIME)) {
                    width = 5120;
                } else if (fields.get(i).getRefTableName() == null && fields.get(i).getValueType().equals((Object)ValueType.STRING)) {
                    width = 6144;
                }
                if (fields.get(i).getLength() > 200) {
                    width = 9216;
                }
                sheet.setColumnWidth(i, width);
                SXSSFCell cell = row.createCell(i);
                cell.setCellValue(fields.get(i).getTitle());
                cell.setCellStyle(style);
            }
        }
        catch (Exception e) {
            logger.error("\u5bfc\u51fa\u65f6\u8bbe\u7f6e\u8868\u5934\u5931\u8d25\uff1a" + e.getMessage(), e);
        }
    }

    public static String getTemplate(List<Map<String, String>> titles) {
        String result = null;
        try (ByteArrayOutputStream os = new ByteArrayOutputStream();
             SXSSFWorkbook workbook = new SXSSFWorkbook();){
            SXSSFSheet sheet;
            try {
                sheet = workbook.createSheet(BillCoreI18nUtil.getMessage("va.billcore.excelutil.tempfile"));
            }
            catch (Exception e) {
                throw new NullPointerException(BillCoreI18nUtil.getMessage("va.billcore.excelutil.lackfont"));
            }
            SXSSFRow row = sheet.createRow(0);
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREEN.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            for (int i = 0; i < titles.size(); ++i) {
                int width = titles.get(i).get(TITLE).getBytes().length * 256 + 512;
                sheet.setColumnWidth(i, width);
                SXSSFCell cell = row.createCell(i);
                cell.setCellValue(titles.get(i).get(TITLE));
                cell.setCellStyle(style);
            }
            workbook.write((OutputStream)os);
            result = Base64.encodeBase64String((byte[])os.toByteArray());
        }
        catch (IOException e) {
            logger.error("\u6a21\u677f\u4e0b\u8f7d\u5931\u8d25\uff1a{}", (Object)e.getMessage(), (Object)e);
        }
        return result;
    }

    private static void setData(SXSSFWorkbook workbook, SXSSFSheet sheet, List<Map<String, Object>> list, List<DataFieldDefine> fields, BillModelImpl model, Map<String, Object> params, List<Map<String, Object>> fieldSums) {
        try {
            ArrayList<Map<String, Object>> mulRowsData;
            int rowNum = 1;
            try {
                mulRowsData = model.getTable(fields.get(0).getTable().getTableName() + "_M").getRowsData();
            }
            catch (Exception e) {
                logger.error("\u83b7\u53d6\u591a\u9009\u5b50\u8868\u5931\u8d25\uff1a{}", (Object)e.getMessage(), (Object)e);
                mulRowsData = new ArrayList();
            }
            int option = (Integer)params.get("option");
            Object isShowSumRow = params.get("isShowSumRow");
            HashMap<Integer, BigDecimal> sumMap = new HashMap<Integer, BigDecimal>();
            for (int i = 0; i < list.size(); ++i) {
                SXSSFRow row = sheet.createRow(rowNum);
                Map<String, Object> rowMap = list.get(i);
                for (int j = 0; j < fields.size(); ++j) {
                    String value;
                    boolean isRef;
                    String name = fields.get(j).getName();
                    Object fieldValue = rowMap.get(name);
                    if (fieldValue == null) {
                        row.createCell(j).setCellValue("");
                        continue;
                    }
                    DataFieldDefineImpl dataFieldDefine = (DataFieldDefineImpl)fields.get(j);
                    if (dataFieldDefine.isEncryptedStorage()) continue;
                    StringBuilder sb = new StringBuilder();
                    boolean bl = isRef = fields.get(j).getRefTableName() != null;
                    if (isRef) {
                        if (fields.get(j).isMultiChoice()) {
                            ExcelUtil.getRefTableData(mulRowsData, fieldValue, option, sb, true);
                        } else {
                            ExcelUtil.getRefTableData(null, fieldValue, option, sb, false);
                        }
                        value = sb.toString();
                        row.createCell(j).setCellValue(value);
                        continue;
                    }
                    if (dataFieldDefine.isBillPenetrate()) {
                        Map map = (Map)fieldValue;
                        value = (String)map.get("value");
                        row.createCell(j).setCellValue(value);
                        continue;
                    }
                    if (fieldValue instanceof Map) {
                        if (((Map)fieldValue).containsKey(SHOWTITLE)) {
                            row.createCell(j).setCellValue((String)((Map)fieldValue).get(SHOWTITLE));
                            continue;
                        }
                        row.createCell(j).setCellValue((String)((Map)fieldValue).get(TITLE));
                        continue;
                    }
                    value = String.valueOf(fieldValue);
                    ValueType valueType = fields.get(j).getValueType();
                    if (ValueType.DATE.equals((Object)valueType)) {
                        row.createCell(j).setCellValue(value.split(" ")[0]);
                        continue;
                    }
                    if (ValueType.DATETIME.equals((Object)valueType)) {
                        if (model.getContext().getContextValue("X--timeZone") == null) {
                            row.createCell(j).setCellValue(value);
                            continue;
                        }
                        String stringDate = BillUtils.getStringDate(Utils.parseDate((String)value), (String)model.getContext().getContextValue("X--timeZone"));
                        row.createCell(j).setCellValue(stringDate);
                        continue;
                    }
                    if (ValueType.DECIMAL.equals((Object)valueType)) {
                        ExcelUtil.handleSum(fieldSums, isShowSumRow, sumMap, j, value);
                        row.createCell(j).setCellValue(Double.parseDouble(value));
                        continue;
                    }
                    if (ValueType.INTEGER.equals((Object)valueType)) {
                        ExcelUtil.handleSum(fieldSums, isShowSumRow, sumMap, j, value);
                        row.createCell(j).setCellValue((double)Integer.parseInt(value));
                        continue;
                    }
                    row.createCell(j).setCellValue(value);
                }
                if (rowNum % 100 == 0) {
                    sheet.flushRows(1000);
                }
                ++rowNum;
            }
            if (isShowSumRow != null && ((Boolean)isShowSumRow).booleanValue() && !sumMap.isEmpty()) {
                XSSFColor customColor = new XSSFColor(new Color(255, 157, 0), null);
                XSSFFont font = (XSSFFont)workbook.createFont();
                font.setColor(customColor);
                XSSFCellStyle style = (XSSFCellStyle)workbook.createCellStyle();
                style.setFont((Font)font);
                SXSSFRow row = sheet.createRow(rowNum);
                for (int i = 0; i < fields.size(); ++i) {
                    if (!sumMap.containsKey(i)) continue;
                    SXSSFCell cell = row.createCell(i);
                    cell.setCellValue(Double.parseDouble(String.valueOf(sumMap.get(i))));
                    cell.setCellStyle((CellStyle)style);
                }
            }
            sheet.flushRows();
            logger.info("\u8868\u683c\u8d4b\u503c\u6210\u529f\uff01");
        }
        catch (Exception e) {
            logger.error("\u8868\u683c\u8d4b\u503c\u5931\u8d25\uff1a" + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private static void handleSum(List<Map<String, Object>> fieldSums, Object isShowSumRow, Map<Integer, BigDecimal> sumMap, int j, String value) {
        Object canSum;
        if (isShowSumRow != null && ((Boolean)isShowSumRow).booleanValue() && (canSum = fieldSums.get(j).get("canSum")) != null && ((Boolean)canSum).booleanValue()) {
            String finalValue = value;
            if (sumMap.containsKey(j)) {
                sumMap.put(j, sumMap.get(j).add(new BigDecimal(finalValue)));
            } else {
                sumMap.computeIfAbsent(j, k -> new BigDecimal(finalValue));
            }
        }
    }

    private static void getRefTableData(List<Map<String, Object>> field, Object fieldValue, int option, StringBuilder sb, boolean isMuti) {
        if (isMuti) {
            Iterator<Map<String, Object>> iterator = field.iterator();
            while (iterator.hasNext()) {
                Map<String, Object> next = iterator.next();
                if (!next.get("BINDINGID").equals(fieldValue)) continue;
                ExcelUtil.getRefTableData(null, next.get("BINDINGVALUE"), option, sb, false);
                sb.append(",");
                iterator.remove();
            }
            sb.deleteCharAt(sb.length() - 1);
        } else {
            Map map = (Map)fieldValue;
            String name = ((String)map.get("name")).split("\\|\\|")[0];
            if (option == 1) {
                sb.append(name);
            } else if (option == 2) {
                sb.append(map.get(TITLE));
            } else if (option == 3) {
                sb.append(name).append(" ").append(map.get(TITLE));
            } else if (option == 4) {
                Object showTitle = map.get(SHOWTITLE);
                if (showTitle != null) {
                    sb.append(showTitle);
                } else {
                    sb.append(map.get(TITLE));
                }
            }
        }
    }

    private static void setBrowser(HttpServletResponse response, SXSSFWorkbook workbook, String fileName) {
        try {
            response.setHeader("Content-Disposition", "attachment;filename*=utf-8'zh_cn'" + URLEncoder.encode(fileName, "UTF-8"));
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            workbook.write((OutputStream)response.getOutputStream());
            logger.info("\u8bbe\u7f6e\u6d4f\u89c8\u5668\u4e0b\u8f7d\u6210\u529f\uff01");
        }
        catch (Exception e) {
            logger.error("\u8bbe\u7f6e\u6d4f\u89c8\u5668\u4e0b\u8f7d\u5931\u8d25\uff1a" + e.getMessage(), e);
        }
    }

    public static Map<String, Object> readXls(byte[] data, BillModelImpl model, String tableName, List<Map<String, Object>> titles, Integer startRow, Integer startColumn) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        List<Map<String, Object>> rowData = null;
        try {
            LargeExcelFileReaderUtil util = new LargeExcelFileReaderUtil(startRow, startColumn);
            util.processOneSheet(data, titles);
            rowData = util.getMyDataList(titles);
        }
        catch (Exception e) {
            logger.error("\u8bfb\u53d6excel\u6587\u4ef6\u5931\u8d25\uff1a" + e.getMessage(), e);
            throw new BillException(e.getMessage());
        }
        result.put("tableData", rowData);
        result.put("total", rowData.size());
        return result;
    }
}

