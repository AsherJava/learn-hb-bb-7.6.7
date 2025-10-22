/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.bi.security.SecurityContentException
 */
package com.jiuqi.np.dataengine.log;

import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.bi.security.SecurityContentException;
import com.jiuqi.np.dataengine.log.LogMeta;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogParser {
    private static final Logger logger = LoggerFactory.getLogger(LogParser.class);

    public static void toCSV(String sourceFileDir) throws SecurityContentException {
        PathUtils.validatePathManipulation((String)sourceFileDir);
        File sourceFile = new File(sourceFileDir);
        String path = sourceFile.getParentFile().getAbsolutePath() + "/SQLLOG.csv";
        PathUtils.validatePathManipulation((String)path);
        File destFile = new File(path);
        try (FileInputStream fis = new FileInputStream(sourceFile);
             InputStreamReader isr = new InputStreamReader((InputStream)fis, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr);
             FileOutputStream fos = new FileOutputStream(destFile, false);
             OutputStreamWriter osw = new OutputStreamWriter((OutputStream)fos, StandardCharsets.UTF_8);
             BufferedWriter bw = new BufferedWriter(osw);
             PrintWriter pw = new PrintWriter(bw, true);){
            String line;
            pw.println(LogMeta.print());
            while ((line = br.readLine()) != null) {
                int index = line.indexOf("DATAQUERY_SQLLOG:");
                if (index < 0) continue;
                pw.println(line.substring(index + "DATAQUERY_SQLLOG:".length()));
            }
            System.out.println("SQLLOG\u63d0\u53d6\u5b8c\u6210\uff0c\u6587\u4ef6\u76ee\u5f55\uff1a" + destFile.getAbsolutePath());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String sourceFileDir = "D:/temp/log.txt";
        try {
            PathUtils.validatePathManipulation((String)sourceFileDir);
            if (args.length > 0) {
                sourceFileDir = args[0];
            }
            LogParser.toCSV(sourceFileDir);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}

