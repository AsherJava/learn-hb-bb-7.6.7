/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.io.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {
    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

    public static File createIfNotExists(String path) throws IOException {
        File file = new File(FilenameUtils.normalize(path));
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    public static void deleteFiles(String path) {
        File file = new File(FilenameUtils.normalize(path));
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
        } else {
            File[] subFiles;
            for (File subfile : subFiles = file.listFiles()) {
                FileUtil.deleteFiles(subfile.getAbsolutePath());
            }
            file.delete();
        }
    }

    public static List<File> getFiles(String path, String ignoreDir) {
        File[] subFiles;
        ArrayList<File> files = new ArrayList<File>();
        File file = new File(FilenameUtils.normalize(path));
        if (!file.exists()) {
            return null;
        }
        if (file.isFile()) {
            files.add(file);
            return files;
        }
        for (File subfile : subFiles = file.listFiles()) {
            List<File> subFileList;
            if (subfile.isDirectory() && subfile.getName().equalsIgnoreCase(ignoreDir) || null == (subFileList = FileUtil.getFiles(subfile.getAbsolutePath(), ignoreDir))) continue;
            files.addAll(subFileList);
        }
        return files;
    }

    public static void deleteFiles(File file) {
        if (file.isFile()) {
            file.delete();
        } else {
            File[] subFiles;
            for (File subfile : subFiles = file.listFiles()) {
                FileUtil.deleteFiles(subfile.getAbsolutePath());
            }
            file.delete();
        }
    }

    public static void unZip(File srcFile, String destDirPath) throws RuntimeException {
        long start = System.currentTimeMillis();
        if (!srcFile.exists()) {
            throw new RuntimeException(srcFile.getPath() + "\u6240\u6307\u6587\u4ef6\u4e0d\u5b58\u5728");
        }
        try (ZipFile zipFile = new ZipFile(srcFile, Charset.forName("GBK"));){
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                log.info("\u89e3\u538b" + entry.getName());
                if (entry.isDirectory()) {
                    String dirPath = destDirPath + "/" + entry.getName();
                    File dir = new File(FilenameUtils.normalize(dirPath));
                    dir.mkdirs();
                    continue;
                }
                File targetFile = new File(FilenameUtils.normalize(destDirPath + "/" + entry.getName()));
                if (!targetFile.getParentFile().exists()) {
                    targetFile.getParentFile().mkdirs();
                }
                targetFile.createNewFile();
                InputStream is = zipFile.getInputStream(entry);
                Throwable throwable = null;
                try {
                    FileOutputStream fos = new FileOutputStream(targetFile);
                    Throwable throwable2 = null;
                    try {
                        int len;
                        byte[] buf = new byte[1024];
                        while ((len = is.read(buf)) != -1) {
                            fos.write(buf, 0, len);
                        }
                    }
                    catch (Throwable throwable3) {
                        throwable2 = throwable3;
                        throw throwable3;
                    }
                    finally {
                        if (fos == null) continue;
                        if (throwable2 != null) {
                            try {
                                fos.close();
                            }
                            catch (Throwable throwable4) {
                                throwable2.addSuppressed(throwable4);
                            }
                            continue;
                        }
                        fos.close();
                    }
                }
                catch (Throwable throwable5) {
                    throwable = throwable5;
                    throw throwable5;
                }
                finally {
                    if (is == null) continue;
                    if (throwable != null) {
                        try {
                            is.close();
                        }
                        catch (Throwable throwable6) {
                            throwable.addSuppressed(throwable6);
                        }
                        continue;
                    }
                    is.close();
                }
            }
            long end = System.currentTimeMillis();
            log.info("\u89e3\u538b\u5b8c\u6210\uff0c\u8017\u65f6\uff1a" + (end - start) + " ms");
        }
        catch (Exception e) {
            throw new RuntimeException("unzip error from ZipUtil", e);
        }
    }
}

