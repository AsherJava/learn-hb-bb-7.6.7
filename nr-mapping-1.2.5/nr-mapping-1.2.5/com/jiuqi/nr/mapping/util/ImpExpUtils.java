/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.mapping.util;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.NumberFormat;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Workbook;

public class ImpExpUtils {
    public static String getStringValue(Cell cell) {
        if (cell != null) {
            String value = null;
            CellType cellType = cell.getCellType();
            if (cellType == CellType.STRING) {
                value = cell.getStringCellValue();
            } else if (cellType == CellType.NUMERIC) {
                double number = cell.getNumericCellValue();
                NumberFormat nf = NumberFormat.getInstance();
                nf.setGroupingUsed(false);
                value = nf.format(number);
            }
            return value;
        }
        return null;
    }

    public static void export(String fileName, HttpServletResponse response, Workbook workbook) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        BufferedOutputStream outputStream = new BufferedOutputStream((OutputStream)response.getOutputStream());
        response.setContentType("application/octet-stream");
        workbook.write(outputStream);
        ((OutputStream)outputStream).flush();
    }
}

