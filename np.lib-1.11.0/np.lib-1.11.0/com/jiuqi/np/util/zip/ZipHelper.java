/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.bi.security.SecurityContentException
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.np.util.zip;

import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.bi.security.SecurityContentException;
import com.jiuqi.bi.util.StringUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipHelper {
    public static final String GZIP_ENCODE_UTF_8 = "UTF-8";

    private ZipHelper() {
    }

    public static int zipDirectory(String dir, OutputStream outStream) throws IOException {
        return ZipHelper.zipDirectory(dir, outStream, null);
    }

    public static int zipDirectory(String dir, OutputStream outStream, FilenameFilter filter) throws IOException {
        try (ZipOutputStream zip = new ZipOutputStream(outStream, Charset.forName("GB2312"));){
            File root = new File(dir);
            int n = ZipHelper.zipFiles(root, "", zip, filter);
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
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = input.read(buffer)) > 0) {
                zip.write(buffer, 0, len);
            }
        }
    }

    private static int zipFiles(File dir, String path, ZipOutputStream zip, FilenameFilter filter) throws IOException {
        File[] subFiles;
        int fileSize = 0;
        for (File subFile : subFiles = dir.listFiles()) {
            if (".".equals(subFile.getName()) || "..".equals(subFile.getName())) continue;
            if (subFile.isFile()) {
                if (filter != null && !filter.accept(dir, subFile.getName())) continue;
                ZipHelper.zipFile(path, subFile, zip);
                ++fileSize;
                continue;
            }
            if (!subFile.isDirectory()) continue;
            String subPath = StringUtils.isEmpty((String)path) ? subFile.getName() : path + "/" + subFile.getName();
            fileSize += ZipHelper.zipFiles(subFile, subPath, zip, filter);
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
                byte[] buffer = new byte[1024];
                while ((len = ((InputStream)inStream).read(buffer)) >= 0) {
                    zip.write(buffer, 0, len);
                }
            }
            finally {
                zip.closeEntry();
            }
        }
    }

    public static int unzipFile(String dir, InputStream inStream) throws IOException {
        int fileSize = 0;
        try (ZipInputStream zip = new ZipInputStream(inStream, Charset.forName("GB2312"));){
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                String fullName = dir + "/" + entry.getName();
                PathUtils.validatePathManipulation((String)fullName);
                if (entry.isDirectory()) {
                    File path = new File(fullName);
                    if (path.exists() || path.mkdirs()) continue;
                    throw new IOException("\u65e0\u6cd5\u521b\u5efa\u6587\u4ef6\u76ee\u5f55\uff1a" + fullName);
                }
                ZipHelper.unzip(fullName, zip);
                ++fileSize;
            }
        }
        catch (SecurityContentException e) {
            throw new IOException(e.getMessage(), e);
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
            byte[] buffer = new byte[1024];
            while ((len = unzip.read(buffer)) >= 0) {
                output.write(buffer, 0, len);
            }
        }
    }

    private static void unzip(String fileName, ZipInputStream zip) throws IOException, SecurityContentException {
        PathUtils.validatePathManipulation((String)fileName);
        File file = new File(fileName);
        File dir = file.getParentFile();
        if (!dir.isDirectory() && !dir.mkdirs()) {
            throw new IOException("\u65e0\u6cd5\u521b\u5efa\u6587\u4ef6\u76ee\u5f55\uff1a" + dir);
        }
        try (FileOutputStream out = new FileOutputStream(fileName);){
            int len;
            byte[] buffer = new byte[1024];
            while ((len = zip.read(buffer)) >= 0) {
                out.write(buffer, 0, len);
            }
        }
    }
}

