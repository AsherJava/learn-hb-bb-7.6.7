/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.zip.ZipEntry
 *  com.jiuqi.bi.util.zip.ZipOutputStream
 */
package com.jiuqi.nr.formtype.common.io;

import com.jiuqi.bi.util.zip.ZipEntry;
import com.jiuqi.bi.util.zip.ZipOutputStream;
import com.jiuqi.nr.formtype.common.io.JsonDataConverter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class ZipUtils {
    private static final Logger logger = LoggerFactory.getLogger(ZipUtils.class);
    private static final String ZIP_FILE_SUFFIX = ".zip";
    private static final String ZIP_ENCODING = "GBK";
    public static final String SEPARATOR = File.separator;

    public static String newZip(String dir, String zipFileName, List<ZipSubFile> subFiles) {
        Assert.notNull((Object)dir, "dir must not be null.");
        Assert.notNull((Object)zipFileName, "zipFileName must not be null.");
        Assert.notNull(subFiles, "subFiles must not be null.");
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        String zipPath = dir + SEPARATOR + zipFileName;
        if (!zipFileName.endsWith(ZIP_FILE_SUFFIX)) {
            zipPath = zipPath + ZIP_FILE_SUFFIX;
        }
        try (FileOutputStream outputStream = new FileOutputStream(zipPath);){
            ZipUtils.zip(outputStream, subFiles);
        }
        catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return zipPath;
    }

    public static void zip(OutputStream outputStream, List<ZipSubFile> subFiles) {
        try (ZipOutputStream zos = new ZipOutputStream(outputStream);){
            zos.setEncoding(ZIP_ENCODING);
            for (ZipSubFile zipSubFile : subFiles) {
                zos.putNextEntry(new ZipEntry(zipSubFile.getSubFilePath()));
                zos.write(zipSubFile.getSubFileData());
            }
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static List<ZipSubFile> unZip(File zipFile) {
        Assert.notNull((Object)zipFile, "subFiles must not be null.");
        try (ZipInputStream zipInputStream = new ZipInputStream((InputStream)new FileInputStream(zipFile), Charset.forName(ZIP_ENCODING));){
            List<ZipSubFile> list = ZipUtils.unZip(zipInputStream);
            return list;
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public static List<ZipSubFile> unZip(InputStream inputStream) {
        Assert.notNull((Object)inputStream, "subFiles must not be null.");
        ArrayList<ZipSubFile> subFiles = new ArrayList<ZipSubFile>();
        try (ZipInputStream zipInputStream = new ZipInputStream(inputStream, Charset.forName(ZIP_ENCODING));){
            java.util.zip.ZipEntry zipEntry = null;
            while (null != (zipEntry = zipInputStream.getNextEntry())) {
                if (zipEntry.isDirectory()) continue;
                subFiles.add(new ZipSubFile(zipEntry.getName().replace("\\\\", "/"), ZipUtils.readBytes(zipInputStream)));
            }
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return subFiles;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static byte[] readBytes(InputStream in) {
        int size = 0;
        int buffSize = 1024;
        byte[] temp = new byte[buffSize];
        try (ByteArrayOutputStream out = new ByteArrayOutputStream(buffSize);){
            while ((size = in.read(temp)) != -1) {
                out.write(temp, 0, size);
            }
            byte[] byArray = out.toByteArray();
            return byArray;
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static class ZipSubFile {
        private String subFilePath;
        private byte[] subFileData;

        public ZipSubFile(String subFilePath, byte[] subFileData) {
            Assert.notNull((Object)subFilePath, "subFilePath must not be null.");
            Assert.notNull((Object)subFileData, "subFileData must not be null.");
            this.subFilePath = subFilePath;
            this.subFileData = subFileData;
        }

        public ZipSubFile(String subFilePath, JsonDataConverter<?> subFileData, JsonDataConverter.IJsonModuleRegister moduleRegister) throws IOException {
            Assert.notNull((Object)subFilePath, "subFilePath must not be null.");
            Assert.notNull(subFileData, "subFileData must not be null.");
            this.subFilePath = subFilePath;
            this.subFileData = subFileData.serializer(moduleRegister);
        }

        public ZipSubFile(String subFilePath, InputStream subFileData) {
            Assert.notNull((Object)subFilePath, "subFilePath must not be null.");
            Assert.notNull((Object)subFileData, "subFileData must not be null.");
            this.subFilePath = subFilePath;
            this.subFileData = ZipUtils.readBytes(subFileData);
        }

        public String getSubFileName() {
            return new File(this.subFilePath).getName();
        }

        public String getSubFilePath() {
            return this.subFilePath;
        }

        public byte[] getSubFileData() {
            return this.subFileData;
        }

        public InputStream getSubFileInputStream() {
            return new ByteArrayInputStream(this.subFileData);
        }

        public <V> JsonDataConverter<V> getSubFileJsonDataConverter(Class<V> v, JsonDataConverter.IJsonModuleRegister moduleRegister) throws IOException {
            return JsonDataConverter.deserializer(this.subFileData, v, moduleRegister);
        }

        public String toString() {
            return "ZipSubFile [subFilePath=" + this.subFilePath + ", subFileData=" + new String(this.subFileData) + "]";
        }
    }
}

