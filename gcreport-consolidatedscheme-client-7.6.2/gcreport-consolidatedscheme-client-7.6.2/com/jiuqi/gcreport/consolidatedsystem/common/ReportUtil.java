/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.hssf.usermodel.HSSFRow
 *  org.apache.poi.hssf.usermodel.HSSFSheet
 *  org.apache.poi.hssf.usermodel.HSSFWorkbook
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.consolidatedsystem.common;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

public class ReportUtil {
    public static String File2Base64(File file) {
        String base64 = null;
        try (FileInputStream fis = new FileInputStream(file);){
            int n;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            base64 = Base64.getEncoder().encodeToString(bos.toByteArray());
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return base64;
    }

    public static void base642File(String buf, File dir, String fileName) {
        File file = null;
        try (FileOutputStream fos = new FileOutputStream(file);
             BufferedOutputStream bos = new BufferedOutputStream(fos);){
            bos.write(Base64.getDecoder().decode(buf));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File multipartFile2File(MultipartFile file) {
        File f = null;
        if (file.equals("") || file.getSize() <= 0L) {
            file = null;
        } else {
            try {
                InputStream ins = file.getInputStream();
                f = new File(file.getOriginalFilename());
                ReportUtil.inputStream2File(ins, f);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f;
    }

    public static void inputStream2File(InputStream ins, File file) {
        try (FileOutputStream os = new FileOutputStream(file);){
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                ((OutputStream)os).write(buffer, 0, bytesRead);
            }
            ((OutputStream)os).close();
            ins.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HSSFWorkbook exportExe(String sheetName, List<Map> list) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(sheetName);
        HSSFRow head = sheet.createRow(0);
        head.createCell(0).setCellValue("\u7f16\u7801");
        head.createCell(1).setCellValue("\u540d\u79f0");
        head.createCell(2).setCellValue("\u63cf\u8ff0");
        head.createCell(3).setCellValue("\u7236\u8282\u70b9\u7f16\u7801");
        head.createCell(4).setCellValue("\u5173\u8054\u6a21\u677f\u7f16\u7801");
        for (int row = 0; row < list.size(); ++row) {
            Map map = list.get(row);
            HSSFRow hssfRow = sheet.createRow(row + 1);
            hssfRow.createCell(0).setCellValue(map.get("CODE").toString());
            hssfRow.createCell(1).setCellValue(map.get("TITLE") == null ? "" : map.get("TITLE").toString());
            hssfRow.createCell(2).setCellValue(map.get("DES") == null ? "" : map.get("DES").toString());
            hssfRow.createCell(3).setCellValue(map.get("PARENTNAME") == null ? "" : map.get("PARENTNAME").toString());
            hssfRow.createCell(4).setCellValue(map.get("TMPLNAME") == null ? "" : map.get("TMPLNAME").toString());
        }
        return wb;
    }
}

