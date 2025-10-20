/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.util.zip;

import com.jiuqi.bi.util.StringUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipHelper {
    private ZipHelper() {
    }

    public static int zipDirectory(String dir, OutputStream outStream) throws IOException {
        return ZipHelper.zipDirectory(dir, outStream, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int zipDirectory(String dir, OutputStream outStream, FilenameFilter filter) throws IOException {
        try (ZipOutputStream zip = new ZipOutputStream(outStream);){
            File root = new File(dir);
            int n = ZipHelper.zipFiles(root, "", zip, filter);
            return n;
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

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int unzipFile(String dir, InputStream inStream) throws IOException {
        int fileSize = 0;
        try (ZipInputStream zip = new ZipInputStream(inStream);){
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                String fullName = dir + "/" + entry.getName();
                if (entry.isDirectory()) {
                    File path = new File(fullName);
                    if (path.exists() || path.mkdirs()) continue;
                    throw new IOException("\u65e0\u6cd5\u521b\u5efa\u6587\u4ef6\u76ee\u5f55\uff1a" + fullName);
                }
                ZipHelper.unzip(fullName, zip);
                ++fileSize;
            }
        }
        return fileSize;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void unzip(String fileName, ZipInputStream zip) throws IOException {
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

