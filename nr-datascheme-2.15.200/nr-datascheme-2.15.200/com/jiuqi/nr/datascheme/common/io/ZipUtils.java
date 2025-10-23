/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.bi.security.SecurityContentException
 *  com.jiuqi.bi.util.zip.ZipEntry
 *  com.jiuqi.bi.util.zip.ZipOutputStream
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.common.UUIDUtils
 */
package com.jiuqi.nr.datascheme.common.io;

import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.bi.security.SecurityContentException;
import com.jiuqi.bi.util.zip.ZipEntry;
import com.jiuqi.bi.util.zip.ZipOutputStream;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.datascheme.common.DataSchemeIOErrorEnum;
import com.jiuqi.nr.datascheme.common.io.JsonDataConverter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
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
    public static final String ROOT_LOCATION = System.getProperty("java.io.tmpdir");
    public static final String EXPORTDIR = ROOT_LOCATION + SEPARATOR + ".nr" + SEPARATOR + "AppData" + SEPARATOR + "export";

    public static String newTempDir() {
        String dir = ZipUtils.buildFilePath().toString();
        try {
            ZipUtils.checkFilePath(dir);
        }
        catch (JQException e) {
            throw new RuntimeException(e);
        }
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return dir;
    }

    private static StringBuffer buildFilePath() {
        StringBuffer filePath = new StringBuffer();
        NpContext context = NpContextHolder.getContext();
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formatDate = dateFormat.format(date);
        String dirUU = UUIDUtils.getKey();
        filePath.append(EXPORTDIR).append(SEPARATOR).append(context.getUser().getName()).append(SEPARATOR).append(formatDate).append(SEPARATOR).append(dirUU);
        return filePath;
    }

    public static String newZip(String dir, String zipFileName, List<ZipSubFile> subFiles) {
        Assert.notNull((Object)dir, "dir must not be null.");
        Assert.notNull((Object)zipFileName, "zipFileName must not be null.");
        Assert.notNull(subFiles, "subFiles must not be null.");
        try {
            ZipUtils.checkFilePath(dir);
        }
        catch (JQException e) {
            throw new RuntimeException(e);
        }
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        String zipPath = dir + SEPARATOR + zipFileName;
        try {
            ZipUtils.checkFilePath(zipPath);
        }
        catch (JQException e) {
            throw new RuntimeException(e);
        }
        if (!zipFileName.endsWith(ZIP_FILE_SUFFIX)) {
            zipPath = zipPath + ZIP_FILE_SUFFIX;
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(zipPath);){
            ZipUtils.zip(fileOutputStream, subFiles);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return zipPath;
    }

    private static void checkFilePath(String path) throws JQException {
        try {
            PathUtils.validatePathManipulation((String)path);
        }
        catch (SecurityContentException e) {
            throw new JQException((ErrorEnum)DataSchemeIOErrorEnum.DS_IO_103);
        }
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

