/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.security.HtmlUtils
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.nrdx.adapter.utils;

import com.jiuqi.bi.security.HtmlUtils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileHelper {
    private static final Logger logger = LoggerFactory.getLogger(FileHelper.class);

    public static void exportFile(File file, String fileName, HttpServletResponse response) throws Exception {
        if (file.exists() && file.isFile()) {
            try (BufferedInputStream ins = new BufferedInputStream(Files.newInputStream(file.toPath(), new OpenOption[0]));
                 BufferedOutputStream ous = new BufferedOutputStream((OutputStream)response.getOutputStream());){
                int bytesRead;
                long fileLength = file.length();
                response.setCharacterEncoding("utf-8");
                String s = fileName + ".nrd";
                String headerValue = HtmlUtils.cleanHeaderValue((String)("attachment;filename=" + URLEncoder.encode(s, "UTF-8")));
                HtmlUtils.validateHeaderValue((String)headerValue);
                response.addHeader("Content-Disposition", headerValue);
                response.addHeader("Content-Length", "" + fileLength);
                response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
                response.setContentType("application/octet-stream");
                long start = 0L;
                byte[] buffer = new byte[8192];
                ((InputStream)ins).skip(start);
                for (long bytesToRead = fileLength; bytesToRead > 0L && (bytesRead = ((InputStream)ins).read(buffer, 0, (int)Math.min((long)buffer.length, bytesToRead))) != -1; bytesToRead -= (long)bytesRead) {
                    try {
                        ((OutputStream)ous).write(buffer, 0, bytesRead);
                        continue;
                    }
                    catch (IOException e) {
                        // empty catch block
                        break;
                    }
                }
                ((OutputStream)ous).flush();
            }
            catch (Exception e) {
                throw e;
            }
            finally {
                if (file.exists()) {
                    // empty if block
                }
            }
        }
    }
}

