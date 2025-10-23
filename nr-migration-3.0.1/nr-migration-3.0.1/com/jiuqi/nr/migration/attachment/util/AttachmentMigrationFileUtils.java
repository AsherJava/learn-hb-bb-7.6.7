/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.migration.attachment.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

public class AttachmentMigrationFileUtils {
    private static final Logger logger = LoggerFactory.getLogger(AttachmentMigrationFileUtils.class);

    public static boolean unzip(File cacheDir) throws IOException {
        File cachedZipFile;
        ZipFile zipfile;
        if (cacheDir.listFiles().length > 0 && (zipfile = new ZipFile(cachedZipFile = cacheDir.listFiles()[0], Charset.forName("GBK"))) != null) {
            AttachmentMigrationFileUtils.handleDirectory(cacheDir, zipfile);
            AttachmentMigrationFileUtils.handleFiles(cacheDir, zipfile);
            zipfile.close();
            return true;
        }
        return false;
    }

    private static void handleFiles(File cacheDir, ZipFile zipfile) throws IOException {
        Enumeration<? extends ZipEntry> e = zipfile.entries();
        while (e.hasMoreElements()) {
            boolean createSuccess;
            ZipEntry entry = e.nextElement();
            if (entry.isDirectory()) continue;
            File entryFile = new File(cacheDir, entry.getName());
            if (!entryFile.getParentFile().exists()) {
                entryFile.getParentFile().mkdirs();
            }
            if (!(createSuccess = entryFile.createNewFile())) continue;
            BufferedInputStream is = new BufferedInputStream(zipfile.getInputStream(entry));
            Throwable throwable = null;
            try {
                BufferedOutputStream dest = new BufferedOutputStream(new FileOutputStream(entryFile), 1024);
                Throwable throwable2 = null;
                try {
                    int len;
                    byte[] dataByte = new byte[1024];
                    while ((len = is.read(dataByte, 0, 1024)) != -1) {
                        dest.write(dataByte, 0, len);
                    }
                    dest.flush();
                }
                catch (Throwable throwable3) {
                    throwable2 = throwable3;
                    throw throwable3;
                }
                finally {
                    if (dest == null) continue;
                    if (throwable2 != null) {
                        try {
                            dest.close();
                        }
                        catch (Throwable throwable4) {
                            throwable2.addSuppressed(throwable4);
                        }
                        continue;
                    }
                    dest.close();
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
    }

    private static void handleDirectory(File cacheDir, ZipFile zipfile) {
        Enumeration<? extends ZipEntry> dir = zipfile.entries();
        while (dir.hasMoreElements()) {
            ZipEntry entry = dir.nextElement();
            if (!entry.isDirectory()) continue;
            String name = entry.getName();
            name = name.substring(0, name.length() - 1);
            File fileObject = new File(cacheDir + name);
            fileObject.mkdirs();
        }
    }

    public static File cacheBatchAttachmentInfos(MultipartFile zipFile) throws IOException {
        File cacheDir;
        logger.info("\u9644\u4ef6\u8fc1\u79fb\uff1a\u521b\u5efa\u672c\u5730\u7f13\u5b58...");
        String filename = zipFile.getOriginalFilename();
        if (filename.contains(".")) {
            filename = filename.substring(0, filename.lastIndexOf("."));
        }
        if ((cacheDir = AttachmentMigrationFileUtils.getCacheDir()) != null && cacheDir.exists()) {
            logger.info("\u9644\u4ef6\u8fc1\u79fb\uff1a\u672c\u5730\u7f13\u5b58\u76ee\u5f55 " + cacheDir.getAbsolutePath() + " \u521b\u5efa\u6210\u529f\u3002");
            File destZipFile = new File(cacheDir, zipFile.getOriginalFilename());
            zipFile.transferTo(destZipFile);
        }
        return cacheDir;
    }

    public static List<File> cacheSingleAttachmentInfos(MultipartFile[] multipartFiles) throws IOException {
        logger.info("\u9644\u4ef6\u8fc1\u79fb\uff1a\u521b\u5efa\u672c\u5730\u7f13\u5b58...");
        ArrayList<File> list = new ArrayList<File>(2);
        String filename = "";
        for (MultipartFile file : multipartFiles) {
            if (file.getOriginalFilename().contains("index.json")) continue;
            filename = file.getOriginalFilename();
            filename = filename.contains(".") ? filename.substring(0, filename.indexOf(".")) : filename;
        }
        File destDir = AttachmentMigrationFileUtils.getCacheDir();
        if (destDir != null && destDir.exists()) {
            logger.info("\u9644\u4ef6\u8fc1\u79fb\uff1a\u672c\u5730\u7f13\u5b58 " + destDir.getAbsolutePath() + " \u521b\u5efa\u6210\u529f\u3002");
            for (MultipartFile file : multipartFiles) {
                File destZipFile = new File(destDir, file.getOriginalFilename());
                file.transferTo(destZipFile);
                list.add(destZipFile);
            }
        }
        return list;
    }

    public static void deleteSingleAttachmentCache(File destDir) {
        logger.info("\u9644\u4ef6\u8fc1\u79fb\uff1a\u5f00\u59cb\u5220\u9664\u672c\u5730\u7f13\u5b58...");
        StringBuilder logger = new StringBuilder("\n");
        if (destDir.exists() && destDir.isDirectory()) {
            File[] files;
            for (File f : files = destDir.listFiles()) {
                AttachmentMigrationFileUtils.deleteFiles(f, logger);
            }
            if (destDir.delete()) {
                logger.append(destDir.getAbsolutePath() + " \u5220\u9664\u6210\u529f\u3002\n");
            }
        }
        logger.append("\u9644\u4ef6\u8fc1\u79fb\uff1a\u7f13\u5b58\u5220\u9664\u7ed3\u675f\u3002");
        AttachmentMigrationFileUtils.logger.info(logger.toString());
    }

    public static void deleteBatchAttachmentCache(File cacheFile) {
        logger.info("\u9644\u4ef6\u8fc1\u79fb\uff1a\u5f00\u59cb\u5220\u9664\u672c\u5730\u7f13\u5b58...");
        StringBuilder logger = new StringBuilder("\n");
        if (cacheFile != null && cacheFile.isDirectory()) {
            File[] files;
            for (File f : files = cacheFile.listFiles()) {
                AttachmentMigrationFileUtils.deleteFiles(f, logger);
            }
            try {
                Files.delete(cacheFile.toPath());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.append("\u9644\u4ef6\u8fc1\u79fb\uff1a\u7f13\u5b58\u5220\u9664\u7ed3\u675f\u3002");
        AttachmentMigrationFileUtils.logger.info(logger.toString());
    }

    private static File getCacheDir() {
        String key = UUID.randomUUID().toString().replace("-", "");
        File destDir = new File(System.getProperty("java.io.tmpdir") + key);
        if (!destDir.exists() && !destDir.mkdir()) {
            logger.warn("\u9644\u4ef6\u8fc1\u79fb\uff1a\u7f13\u5b58\u76ee\u5f55" + destDir.getAbsolutePath() + "\u521b\u5efa\u5931\u8d25\u3002");
            String cacheLocation = ClassUtils.getDefaultClassLoader().getResource("").getPath().replaceFirst("/", "");
            destDir = new File(cacheLocation + key);
            if (!destDir.exists() && !destDir.mkdir()) {
                logger.warn("\u9644\u4ef6\u8fc1\u79fb\uff1a\u7f13\u5b58\u76ee\u5f55" + destDir.getAbsolutePath() + "\u521b\u5efa\u5931\u8d25\u3002");
                return null;
            }
        }
        return destDir;
    }

    private static void deleteFiles(File fDir, StringBuilder logger) {
        if (fDir != null && fDir.exists()) {
            if (fDir.isDirectory()) {
                File[] files;
                for (File f : files = fDir.listFiles()) {
                    if (!f.isFile()) continue;
                    logger.append(", ").append(f.getName()).append(f.delete() ? "\u5220\u9664\u6210\u529f\u3002\n" : "\u5220\u9664\u5931\u8d25\u3002\n");
                }
            }
            logger.append(", ").append(fDir.getName()).append(fDir.delete() ? "\u5220\u9664\u6210\u529f\u3002\n" : "\u5220\u9664\u5931\u8d25\u3002\n");
        }
    }
}

