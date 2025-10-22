/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.single.core.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.util.SingleSecurityUtils;
import com.jiuqi.nr.single.core.util.ZipParam;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZipUtil {
    public static final String GZIP_ENCODE_UTF_8 = "UTF-8";
    public static String[] TRY_CHARSETS = new String[]{"GBK", "GB18030", "GB2312", "UTF-8", "ISO-8859-1"};
    private static final Logger logger = LoggerFactory.getLogger(ZipUtil.class);
    private static final int GZIP_BUFFER_SIZE = 4096;

    private ZipUtil() {
    }

    public static int zipDirectory(String dir, OutputStream outStream) throws IOException {
        return ZipUtil.zipDirectory(dir, outStream, null);
    }

    public static int zipDirectory(String dir, OutputStream outStream, FilenameFilter filter) throws IOException {
        try {
            return ZipUtil.zipDirectory(dir, outStream, filter, "GB2312");
        }
        catch (SingleFileException e) {
            logger.error(e.getMessage(), e);
            throw new IOException(e.getMessage(), e);
        }
    }

    public static int zipDirectory(String dir, OutputStream outStream, FilenameFilter filter, String charSetName) throws IOException, SingleFileException {
        try (ZipOutputStream zip = new ZipOutputStream(outStream, Charset.forName(charSetName));){
            String dirName = FilenameUtils.normalize(dir);
            SingleSecurityUtils.validatePathManipulation(dirName);
            File root = new File(dirName);
            int n = ZipUtil.zipFiles(root, "", zip, filter);
            return n;
        }
    }

    public static void zipStream(InputStream input, OutputStream output, int compressLevel) throws IOException {
        if (input == null) {
            throw new IllegalArgumentException("'input' must not be null.");
        }
        if (output == null) {
            throw new IllegalArgumentException("'output' must not be null.");
        }
        if (compressLevel < 0 || compressLevel > 9) {
            throw new IllegalArgumentException("'compressLevel' must be equal or greater than zreo and less than 10.");
        }
        try (ZipOutputStream zip = new ZipOutputStream(output);){
            zip.setLevel(compressLevel);
            zip.putNextEntry(new ZipEntry(""));
            byte[] buffer = new byte[4096];
            int len = 0;
            while ((len = input.read(buffer)) > 0) {
                zip.write(buffer, 0, len);
            }
        }
    }

    public static byte[] getZipData(byte[] inputData, String charSetName, int compressLevel) throws IOException {
        byte[] result = null;
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(inputData);
             ByteArrayOutputStream outStream = new ByteArrayOutputStream();
             ZipOutputStream zip = new ZipOutputStream((OutputStream)outStream, Charset.forName(charSetName));){
            inputStream.skip(0L);
            zip.setLevel(compressLevel);
            zip.putNextEntry(new ZipEntry(""));
            byte[] buffer = new byte[4096];
            int len = 0;
            while ((len = inputStream.read(buffer)) > 0) {
                zip.write(buffer, 0, len);
            }
            result = outStream.toByteArray();
        }
        return result;
    }

    public static byte[] getUnZipData(byte[] inputData, String charSetName) throws IOException {
        byte[] result = null;
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(inputData);
             ByteArrayOutputStream outStream = new ByteArrayOutputStream();
             ZipInputStream unzip = new ZipInputStream((InputStream)inputStream, Charset.forName(charSetName));){
            int len;
            unzip.getNextEntry();
            byte[] buffer = new byte[4096];
            while ((len = unzip.read(buffer)) >= 0) {
                outStream.write(buffer, 0, len);
            }
            result = outStream.toByteArray();
        }
        return result;
    }

    private static int zipFiles(File dir, String path, ZipOutputStream zip, FilenameFilter filter) throws IOException {
        File[] subFiles;
        int fileSize = 0;
        for (File subFile : subFiles = dir.listFiles()) {
            if (".".equals(subFile.getName()) || "..".equals(subFile.getName())) continue;
            if (subFile.isFile()) {
                if (filter != null && !filter.accept(dir, subFile.getName())) continue;
                ZipUtil.zipFile(path, subFile, zip);
                ++fileSize;
                continue;
            }
            if (!subFile.isDirectory()) continue;
            String subPath = StringUtils.isEmpty((String)path) ? subFile.getName() : path + File.separator + subFile.getName();
            fileSize += ZipUtil.zipFiles(subFile, subPath, zip, filter);
        }
        return fileSize;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void zipFile(String path, File file, ZipOutputStream zip) throws IOException {
        try (FileInputStream inStream = new FileInputStream(file);){
            zip.putNextEntry(new ZipEntry(StringUtils.isEmpty((String)path) ? file.getName() : path + "/" + file.getName()));
            try {
                int len;
                byte[] buffer = new byte[4096];
                while ((len = ((InputStream)inStream).read(buffer)) >= 0) {
                    zip.write(buffer, 0, len);
                }
            }
            finally {
                zip.closeEntry();
            }
        }
        catch (Exception ex) {
            logger.error("\u538b\u7f29\u6587\u4ef6\u51fa\u9519\uff1a" + file.getName() + "," + ex.getMessage(), ex);
            throw ex;
        }
    }

    public static int unzipFile(String dir, InputStream inStream) throws IOException {
        return ZipUtil.unzipFile(dir, inStream, "GB2312");
    }

    public static int unzipFile(String dir, String zipFileName, String charSetName) throws Exception {
        return ZipUtil.unzipFile(dir, zipFileName, charSetName, TRY_CHARSETS);
    }

    public static int unzipFile(String dir, String zipFileName, String charSetName, Set<String> filterNames) throws Exception {
        return ZipUtil.unzipFile(dir, zipFileName, charSetName, TRY_CHARSETS, filterNames);
    }

    public static int unzipFile(String dir, String zipFileName, String charSetName, String[] tryCharSetNames) throws Exception {
        return ZipUtil.unzipFile(dir, zipFileName, charSetName, tryCharSetNames, null);
    }

    public static int unzipFile(String dir, String zipFileName, String charSetName, String[] tryCharSetNames, Set<String> filterNames) throws Exception {
        return ZipUtil.unzipFile(dir, zipFileName, charSetName, tryCharSetNames, filterNames, null);
    }

    public static int unzipFile(String dir, String zipFileName, String charSetName, String[] tryCharSetNames, Set<String> filterNames, ZipParam param) throws Exception {
        String zipFileName1;
        Exception curException;
        boolean isSuccess;
        int r;
        block36: {
            if (StringUtils.isEmpty((String)charSetName)) {
                charSetName = "GBK";
            }
            r = 0;
            isSuccess = true;
            curException = null;
            zipFileName1 = FilenameUtils.normalize(zipFileName);
            SingleSecurityUtils.validatePathManipulation(zipFileName1);
            try (FileInputStream inStream = new FileInputStream(zipFileName1);){
                try {
                    r = ZipUtil.unzipFile(dir, inStream, charSetName, filterNames, param);
                }
                catch (Exception ex) {
                    logger.error("\u89e3\u538b\u7f29\uff1a" + charSetName + "," + ex.getMessage(), ex);
                    if (ex instanceof IllegalArgumentException) {
                        curException = ex;
                        isSuccess = false;
                        break block36;
                    }
                    throw ex;
                }
            }
        }
        if (!isSuccess) {
            logger.debug("\u89e3\u538b\u7f29\uff1a\u4f7f\u7528\u5916\u90e8\u4f20\u5165\u7684\u5b57\u7b26\u96c6" + charSetName + "\u89e3\u6790\u4e32\u5931\u8d25\uff0c\u5c1d\u8bd5\u4f7f\u7528\u5176\u5b83\u5b57\u7b26\u96c6\u89e3\u6790\uff0c\u6587\u4ef6\uff1a" + zipFileName);
            for (String curSetName : tryCharSetNames) {
                Throwable throwable = null;
                try (FileInputStream inStreamNew = new FileInputStream(zipFileName1);){
                    r = ZipUtil.unzipFile(dir, inStreamNew, curSetName, filterNames, param);
                    isSuccess = true;
                    logger.debug("\u89e3\u538b\u7f29\uff1a\u5c1d\u8bd5\u4f7f\u7528\u5b57\u7b26\u96c6" + curSetName + "\u89e3\u6790\u6210\u529f\uff0c\u8def\u5f84\u4e3a\uff1a" + dir);
                    break;
                }
                catch (Exception ex) {
                    logger.error("\u89e3\u538b\u7f29\uff1a\u5c1d\u8bd5\u4f7f\u7528\u5b57\u7b26\u96c6" + curSetName + "\u89e3\u6790\u5931\u8d25\uff01" + ex.getMessage(), ex);
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                }
            }
            if (!isSuccess) {
                logger.debug("\u89e3\u538b\u7f29\uff1a\u4f7f\u7528\u5404\u79cd\u5b57\u7b26\u96c6\u89e3\u6790\uff0c\u4f9d\u7136\u672a\u89e3\u6790\u51fa\u6587\u4ef6\u540d\uff0c\u6240\u6709\u89e3\u6790\u5931\u8d25\uff0c\u7ee7\u7eed\u629b\u51fa\u539f\u59cb\u5f02\u5e38\uff1a");
                if (curException != null) {
                    throw curException;
                }
            }
        }
        return r;
    }

    public static int unzipFile(String dir, InputStream inStream, String charSetName) throws IOException {
        try {
            return ZipUtil.unzipFile(dir, inStream, charSetName, null);
        }
        catch (SingleFileException e) {
            logger.error(e.getMessage(), e);
            throw new IOException(e.getMessage(), e);
        }
    }

    public static int unzipFile(String dir, InputStream inStream, String charSetName, Set<String> filterNames) throws IOException, SingleFileException {
        return ZipUtil.unzipFile(dir, inStream, charSetName, filterNames, null);
    }

    public static int unzipFile(String dir, InputStream inStream, String charSetName, Set<String> filterNames, ZipParam param) throws IOException, SingleFileException {
        int fileSize = 0;
        try (ZipInputStream zip = new ZipInputStream(inStream, Charset.forName(charSetName));){
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                String fullName = dir + File.separator + entry.getName();
                boolean isFilter = ZipUtil.getFileFilter(entry.getName(), filterNames);
                if (entry.isDirectory()) {
                    String fullName1 = FilenameUtils.normalize(fullName);
                    SingleSecurityUtils.validatePathManipulation(fullName1);
                    File path = new File(fullName1);
                    if (!path.exists() && !path.mkdirs()) {
                        throw new IOException("\u65e0\u6cd5\u521b\u5efa\u6587\u4ef6\u76ee\u5f55\uff1a" + fullName);
                    }
                    if (param == null) continue;
                    param.getPathNames().add(entry.getName());
                    continue;
                }
                ZipUtil.unzip(fullName, zip, isFilter);
                ++fileSize;
                if (param == null) continue;
                param.getFileNames().add(entry.getName());
                if (!isFilter) continue;
                param.getFilterFileNames().add(entry.getName());
            }
        }
        return fileSize;
    }

    public static void unzipStream(InputStream input, OutputStream output) throws IOException {
        if (input == null) {
            throw new IllegalArgumentException("'input' must not be null.");
        }
        if (output == null) {
            throw new IllegalArgumentException("'output' must not be null.");
        }
        try (ZipInputStream unzip = new ZipInputStream(input);){
            int len;
            unzip.getNextEntry();
            byte[] buffer = new byte[4096];
            while ((len = unzip.read(buffer)) >= 0) {
                output.write(buffer, 0, len);
            }
        }
    }

    private static void unzip(String fileName, ZipInputStream zip, boolean isFiter) throws IOException, SingleFileException {
        String fileName1 = FilenameUtils.normalize(fileName);
        SingleSecurityUtils.validatePathManipulation(fileName1);
        File file = new File(fileName1);
        File dir = file.getParentFile();
        if (!dir.isDirectory() && !dir.mkdirs()) {
            throw new IOException("\u65e0\u6cd5\u521b\u5efa\u6587\u4ef6\u76ee\u5f55\uff1a" + dir);
        }
        if (isFiter) {
            int len;
            byte[] buffer = new byte[4096];
            while ((len = zip.read(buffer)) >= 0) {
            }
        } else {
            try (FileOutputStream out = new FileOutputStream(fileName1);){
                int len;
                byte[] buffer = new byte[4096];
                while ((len = zip.read(buffer)) >= 0) {
                    out.write(buffer, 0, len);
                }
            }
        }
    }

    private static boolean getFileFilter(String entryName, Set<String> filterNames) {
        boolean isFilter = false;
        if (filterNames != null && StringUtils.isNotEmpty((String)entryName)) {
            if (filterNames.contains(entryName)) {
                isFilter = true;
            } else {
                String entryName2 = entryName;
                int id = entryName2.lastIndexOf("/");
                while (id > 0) {
                    String pathName = entryName2.substring(0, id);
                    String fileName = entryName2.substring(id + 1, entryName2.length());
                    if (filterNames.contains(pathName)) {
                        isFilter = true;
                        break;
                    }
                    if (filterNames.contains(fileName)) {
                        isFilter = true;
                        break;
                    }
                    entryName2 = pathName;
                    id = entryName2.lastIndexOf("/");
                    if (id >= 0) continue;
                    break;
                }
            }
        }
        return isFilter;
    }
}

