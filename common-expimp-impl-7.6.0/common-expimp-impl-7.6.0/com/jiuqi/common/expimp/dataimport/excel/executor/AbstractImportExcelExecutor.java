/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.commons.io.FileUtils
 *  org.apache.poi.common.usermodel.HyperlinkType
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.FillPatternType
 *  org.apache.poi.ss.usermodel.Font
 *  org.apache.poi.ss.usermodel.Hyperlink
 *  org.apache.poi.ss.usermodel.IndexedColors
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.ss.util.CellUtil
 */
package com.jiuqi.common.expimp.dataimport.excel.executor;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.expimp.common.ExpImpFileTypeEnum;
import com.jiuqi.common.expimp.dataimport.ImportExecutor;
import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.common.expimp.dto.ExcelErrorLinkInfoDTO;
import com.jiuqi.common.expimp.util.ExpImpUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellUtil;

public abstract class AbstractImportExcelExecutor
implements ImportExecutor {
    private static final String EXCEL_IMPORT_FILE_PATH = "/dataimport/excel/";
    private static final String ERROR_CELL_STYLE_KEY = "errorCellStyleKey";
    private static final String LINK_CELL_STYLE_KEY = "linkCellStyleKey";

    @Override
    public final ExpImpFileTypeEnum getFileType() {
        return ExpImpFileTypeEnum.EXCEL;
    }

    @Override
    public Object downloadDataImportResult(HttpServletRequest request, HttpServletResponse response, String executorName, String importSn) throws Exception {
        Objects.requireNonNull(importSn, "\u5bfc\u5165\u6279\u6b21\u53f7\u4e0d\u80fd\u4e3a\u7a7a\u3002");
        request.setCharacterEncoding("utf-8");
        ServletOutputStream outputStream = response.getOutputStream();
        File parentFile = new File(EXCEL_IMPORT_FILE_PATH + executorName + "/" + importSn);
        if (!parentFile.exists()) {
            throw new BusinessRuntimeException("\u5bfc\u5165\u7ed3\u679c\u6587\u4ef6\u4e0d\u5b58\u5728\uff0c\u65e0\u6cd5\u4e0b\u8f7d\u3002");
        }
        File[] files = parentFile.listFiles();
        if (files == null || files.length == 0) {
            throw new BusinessRuntimeException("\u5bfc\u5165\u7ed3\u679c\u6587\u4ef6\u4e0d\u5b58\u5728\uff0c\u65e0\u6cd5\u4e0b\u8f7d\u3002");
        }
        File file = files[0];
        byte[] bytes = FileUtils.readFileToByteArray((File)file);
        response.setHeader("Charset", "UTF-8");
        response.setHeader("Content-Type", "application/vnd.ms-excel");
        String fileName = new String(file.getName().getBytes("UTF-8"));
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        outputStream.write(bytes);
        outputStream.close();
        return "\u4e0b\u8f7d\u6210\u529f\u3002";
    }

    public void buildErrorExcelLink(ImportContext context, Workbook workbook, Sheet sheet, List<ExcelErrorLinkInfoDTO> excelErrorLinkInfos) {
        Sheet errorMsgSheet = workbook.createSheet(sheet.getSheetName() + "_\u9519\u8bef\u4fe1\u606f");
        CellStyle errorCellStyle = this.getErrorCellStyle(context, workbook);
        CellStyle linkCellStyle = this.getLinkCellStyle(context, workbook);
        errorMsgSheet.setDefaultColumnWidth(16);
        block3: for (int i = 0; i < excelErrorLinkInfos.size(); ++i) {
            ExcelErrorLinkInfoDTO excelRowErrorLinkInfo = excelErrorLinkInfos.get(i);
            Set<Integer> errorCellColNums = excelRowErrorLinkInfo.getErrorCellColNums();
            Row row = sheet.getRow(excelRowErrorLinkInfo.getErrorCellRowNum());
            for (int col = row.getFirstCellNum(); col < row.getLastCellNum(); ++col) {
                if (!errorCellColNums.contains(col)) continue;
                row.getCell(col).setCellStyle(errorCellStyle);
            }
            Row errorMsgSheetRow = errorMsgSheet.createRow(i);
            Cell errorCellRowCell = CellUtil.getCell((Row)errorMsgSheetRow, (int)0);
            Cell errorMsgCell = CellUtil.getCell((Row)errorMsgSheetRow, (int)1);
            switch (i) {
                case 0: {
                    CellStyle headCellStyle = ExpImpUtils.buildDefaultHeadCellStyle(workbook);
                    errorCellRowCell.setCellStyle(headCellStyle);
                    errorMsgCell.setCellStyle(headCellStyle);
                    errorCellRowCell.setCellValue("\u9519\u8bef\u884c");
                    errorMsgCell.setCellValue("\u9519\u8bef\u8be6\u60c5");
                    continue block3;
                }
                default: {
                    Hyperlink hyperlink = workbook.getCreationHelper().createHyperlink(HyperlinkType.DOCUMENT);
                    hyperlink.setAddress("#" + sheet.getSheetName() + "!A" + (excelRowErrorLinkInfo.getErrorCellRowNum() + 1));
                    errorCellRowCell.setCellValue("\u7b2c" + (excelRowErrorLinkInfo.getErrorCellRowNum() + 1) + "\u884c");
                    errorCellRowCell.setCellStyle(linkCellStyle);
                    errorCellRowCell.setHyperlink(hyperlink);
                    errorMsgCell.setCellValue(excelRowErrorLinkInfo.getErrorCellMsg());
                }
            }
        }
    }

    protected CellStyle getErrorCellStyle(ImportContext context, Workbook workbook) {
        if (context.getVarMap().get(ERROR_CELL_STYLE_KEY) == null) {
            CellStyle style = workbook.createCellStyle();
            style.setLeftBorderColor(IndexedColors.RED.index);
            style.setRightBorderColor(IndexedColors.RED.index);
            style.setTopBorderColor(IndexedColors.RED.index);
            style.setBottomBorderColor(IndexedColors.RED.index);
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style.setFillForegroundColor(IndexedColors.RED.index);
            context.getVarMap().put(ERROR_CELL_STYLE_KEY, style);
        }
        CellStyle errorStyle = (CellStyle)context.getVarMap().get(ERROR_CELL_STYLE_KEY);
        return errorStyle;
    }

    protected CellStyle getLinkCellStyle(ImportContext context, Workbook workbook) {
        if (context.getVarMap().get(LINK_CELL_STYLE_KEY) == null) {
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setUnderline((byte)1);
            font.setColor(IndexedColors.RED.index);
            style.setFont(font);
            context.getVarMap().put(LINK_CELL_STYLE_KEY, style);
        }
        CellStyle linkStyle = (CellStyle)context.getVarMap().get(LINK_CELL_STYLE_KEY);
        return linkStyle;
    }

    public void saveErrorExcel(ImportContext context, Workbook workbook) throws IOException {
        File saveFile = new File(EXCEL_IMPORT_FILE_PATH + context.getExecutor() + "/" + context.getSn() + "/" + context.getFileName());
        FileOutputStream fileOutputStream = null;
        if (!saveFile.exists()) {
            saveFile.getParentFile().mkdirs();
            saveFile.createNewFile();
        }
        fileOutputStream = new FileOutputStream(saveFile);
        workbook.write((OutputStream)fileOutputStream);
        fileOutputStream.close();
    }
}

