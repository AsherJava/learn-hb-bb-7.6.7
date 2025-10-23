/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.task.api.common;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class FileDownload {
    public static void exportTxtFile(HttpServletResponse response, String text, String fileName) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/octet-stream");
        try {
            fileName = URLEncoder.encode(fileName, "utf-8").replace("\\+", "%20");
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        response.setHeader("Content-Disposition", "attachment;filename*=UTF-8''" + fileName);
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        try (ServletOutputStream outStr = response.getOutputStream();
             BufferedOutputStream buff = new BufferedOutputStream((OutputStream)outStr);){
            buff.write(text.getBytes(StandardCharsets.UTF_8));
            buff.flush();
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}

