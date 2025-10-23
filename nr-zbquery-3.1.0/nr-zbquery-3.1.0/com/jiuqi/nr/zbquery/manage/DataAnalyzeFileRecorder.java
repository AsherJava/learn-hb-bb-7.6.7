/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.ex.TransferFileCacheException
 *  com.jiuqi.bi.transfer.engine.intf.IFileRecorder
 */
package com.jiuqi.nr.zbquery.manage;

import com.jiuqi.bi.transfer.engine.ex.TransferFileCacheException;
import com.jiuqi.bi.transfer.engine.intf.IFileRecorder;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataAnalyzeFileRecorder
implements IFileRecorder {
    private static final Logger logger = LoggerFactory.getLogger(DataAnalyzeFileRecorder.class);
    private File file;

    public DataAnalyzeFileRecorder(File file) {
        this.file = file;
    }

    private InputStream getFileInputStream() {
        try {
            return new FileInputStream(this.file);
        }
        catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public byte[] getData(String path) throws TransferFileCacheException {
        InputStream in = this.getFileInputStream();
        if (in == null) {
            return null;
        }
        String finalPath = path.replaceAll("\\\\", "/");
        try (ZipInputStream zis = new ZipInputStream(in);){
            ZipEntry entry;
            String name;
            do {
                if ((entry = zis.getNextEntry()) == null) return null;
            } while (!(name = entry.getName()).equals(finalPath));
            byte[] buff = new byte[512];
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream();){
                int len;
                while ((len = zis.read(buff, 0, 512)) != -1) {
                    bos.write(buff, 0, len);
                }
            }
            byte[] byArray = bos.toByteArray();
            return byArray;
        }
        catch (IOException e) {
            throw new TransferFileCacheException(e.getMessage(), (Throwable)e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public InputStream getDataStream(String path) throws TransferFileCacheException {
        InputStream in = this.getFileInputStream();
        if (in == null) {
            return null;
        }
        String finalPath = path.replaceAll("\\\\", "/");
        try (ZipInputStream zis = new ZipInputStream(in);){
            ZipEntry entry;
            do {
                if ((entry = zis.getNextEntry()) == null) return null;
            } while (!entry.getName().equals(finalPath));
            String tempFilePath = this.genTempFile(path);
            File f = new File(tempFilePath);
            if (!f.exists()) {
                f.createNewFile();
            }
            byte[] buff = new byte[512];
            try (FileOutputStream bos = new FileOutputStream(f);){
                int len;
                while ((len = zis.read(buff, 0, 512)) != -1) {
                    bos.write(buff, 0, len);
                }
            }
            FileInputStream fileInputStream = new FileInputStream(f);
            return fileInputStream;
        }
        catch (IOException e) {
            throw new TransferFileCacheException(e.getMessage(), (Throwable)e);
        }
    }

    private String genTempFile(String path) {
        String tempDir = System.getProperty("java.io.tmpdir");
        StringBuilder b = new StringBuilder();
        b.append(tempDir).append(File.separator).append("DataAnalyzeFileRecorder").append(File.separator).append(UUID.randomUUID().toString()).append("_").append(path);
        return b.toString();
    }

    public void destroy() throws TransferFileCacheException {
    }
}

