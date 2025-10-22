/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.csvreader.CsvWriter
 */
package com.jiuqi.gcreport.archive.util;

import com.csvreader.CsvWriter;
import java.io.File;
import java.nio.charset.Charset;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArchiveCsvUtil {
    private static final Logger logger = LoggerFactory.getLogger(ArchiveCsvUtil.class);

    public static File createCsv(String[] headers, List<String[]> rowDataList) {
        File tempFile = null;
        try (CsvWriter csvWriter = null;){
            tempFile = File.createTempFile("archiveTemp" + System.currentTimeMillis(), ".csv");
            csvWriter = new CsvWriter(tempFile.getCanonicalPath(), ',', Charset.forName("UTF-8"));
            csvWriter.writeRecord(headers);
            for (String[] rowData : rowDataList) {
                csvWriter.writeRecord(rowData);
            }
            File file = tempFile;
            return file;
        }
    }

    public static void deleteTempFile(File file) {
        boolean result = file.delete();
        if (!result) {
            logger.error("\u5220\u9664\u5f52\u6863\u4e34\u65f6\u6587\u4ef6\u5931\u8d25\uff1a" + file.getAbsolutePath());
        }
    }
}

