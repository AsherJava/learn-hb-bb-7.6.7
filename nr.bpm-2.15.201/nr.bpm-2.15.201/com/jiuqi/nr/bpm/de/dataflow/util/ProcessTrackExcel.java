/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.bpm.de.dataflow.util;

import com.jiuqi.nr.bpm.setting.pojo.ProcessTrackExcelInfo;
import com.jiuqi.nr.bpm.upload.utils.I18nUtil;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ProcessTrackExcel {
    private static final Logger logger = LoggerFactory.getLogger(ProcessTrackExcel.class);

    public void excelData(HttpServletResponse response, List<ProcessTrackExcelInfo> list) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = !I18nUtil.isChinese() ? wb.createSheet("Process Flow Sheet") : wb.createSheet("\u6d41\u7a0b\u8ddf\u8e2a\u56fe");
        HSSFCellStyle style = wb.createCellStyle();
        this.createTitle(sheet, style);
        this.createBody(sheet, list);
        this.setHttpServletResponse(response, wb);
    }

    private void createTitle(HSSFSheet sheet, HSSFCellStyle style) {
        HSSFRow row = sheet.createRow(0);
        style.setAlignment(HorizontalAlignment.CENTER);
        HSSFCell cell = null;
        if (!I18nUtil.isChinese()) {
            cell = row.createCell(0);
            cell.setCellValue("number");
            cell.setCellStyle(style);
            cell = row.createCell(1);
            cell.setCellValue("node name");
            cell.setCellStyle(style);
            cell = row.createCell(2);
            cell.setCellValue("state");
            cell = row.createCell(3);
            cell.setCellValue("executor");
            cell.setCellStyle(style);
            cell = row.createCell(4);
            cell.setCellValue("action");
            cell.setCellStyle(style);
            cell = row.createCell(5);
            cell.setCellValue("explain");
            cell.setCellStyle(style);
            cell = row.createCell(6);
            cell.setCellValue("time");
            cell.setCellStyle(style);
        } else {
            cell = row.createCell(0);
            cell.setCellValue("\u5e8f\u53f7");
            cell.setCellStyle(style);
            cell = row.createCell(1);
            cell.setCellValue("\u6d41\u7a0b\u8282\u70b9");
            cell.setCellStyle(style);
            cell = row.createCell(2);
            cell.setCellValue("\u72b6\u6001");
            cell = row.createCell(3);
            cell.setCellValue("\u6267\u884c\u8005");
            cell.setCellStyle(style);
            cell = row.createCell(4);
            cell.setCellValue("\u52a8\u4f5c");
            cell.setCellStyle(style);
            cell = row.createCell(5);
            cell.setCellValue("\u8bf4\u660e");
            cell.setCellStyle(style);
            cell = row.createCell(6);
            cell.setCellValue("\u65f6\u95f4");
            cell.setCellStyle(style);
        }
    }

    private void createBody(HSSFSheet sheet, List<ProcessTrackExcelInfo> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        for (int i = 0; i < list.size(); ++i) {
            HSSFRow row = sheet.createRow(i + 1);
            HSSFCell cell = row.createCell(0);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(list.get(i).getNum());
            cell = row.createCell(1);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(list.get(i).getNodeName());
            cell = row.createCell(2);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(list.get(i).getActionState());
            cell = row.createCell(3);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(list.get(i).getUser());
            cell = row.createCell(4);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(list.get(i).getActionName());
            cell = row.createCell(5);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(list.get(i).getDesc());
            cell = row.createCell(6);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(list.get(i).getTime());
        }
        for (int colIndex = 0; colIndex < 11; ++colIndex) {
            sheet.autoSizeColumn(colIndex);
        }
    }

    private void setHttpServletResponse(HttpServletResponse response, HSSFWorkbook wb) {
        String fileName = !I18nUtil.isChinese() ? "Process Flow Sheet.xls" : "\u6d41\u7a0b\u8ddf\u8e2a.xls";
        try (BufferedOutputStream os = new BufferedOutputStream((OutputStream)response.getOutputStream());){
            fileName = new String(fileName.getBytes("gbk"), "iso8859-1");
            response.reset();
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setContentType("application/x-download;charset=utf-8");
            wb.write(os);
            os.flush();
            os.close();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}

