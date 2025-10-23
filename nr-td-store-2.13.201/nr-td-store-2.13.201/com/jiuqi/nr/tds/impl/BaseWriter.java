/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tds.impl;

import com.jiuqi.nr.tds.FileIOException;
import com.jiuqi.nr.tds.api.DataTableWriter;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseWriter
implements DataTableWriter {
    protected final File dataDir;
    protected final String tableName;
    private static final Logger logger = LoggerFactory.getLogger(BaseWriter.class);

    protected BaseWriter(File dataDir, String tableName) {
        this.dataDir = dataDir;
        this.tableName = tableName;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public File getFile() {
        File zipFile = new File(this.dataDir, this.tableName + ".TDS");
        if (zipFile.exists()) {
            return zipFile;
        }
        try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(zipFile.toPath(), new OpenOption[0]));){
            File[] files = this.dataDir.listFiles();
            if (files == null || files.length == 0) {
                if (logger.isDebugEnabled()) {
                    logger.debug(" \u6ca1\u6709\u8981\u538b\u7f29\u7684\u6587\u4ef6");
                }
                File file = zipFile;
                return file;
            }
            for (File file : files) {
                if (file.isDirectory() || file.equals(zipFile)) continue;
                try (FileInputStream fis = new FileInputStream(file);){
                    int length;
                    ZipEntry zipEntry = new ZipEntry(file.getName());
                    zos.putNextEntry(zipEntry);
                    byte[] buffer = new byte[4096];
                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }
                    zos.closeEntry();
                }
                Files.delete(file.toPath());
            }
            File file = zipFile;
            return file;
        }
        catch (Exception e) {
            throw new FileIOException("\u83b7\u53d6\u6570\u636e\u6587\u4ef6\u5931\u8d25", e);
        }
    }

    @Override
    public void destroy() {
        File[] files = this.dataDir.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.delete() || !logger.isDebugEnabled()) continue;
            logger.debug(" \u65e0\u6cd5\u5220\u9664\u6587\u4ef6 {}", (Object)file.getAbsolutePath());
        }
    }
}

