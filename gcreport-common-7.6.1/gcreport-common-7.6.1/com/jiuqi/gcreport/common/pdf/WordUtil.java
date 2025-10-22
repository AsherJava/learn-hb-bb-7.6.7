/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 */
package com.jiuqi.gcreport.common.pdf;

import com.jiuqi.common.base.BusinessRuntimeException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

public class WordUtil {
    private static Logger logger = LoggerFactory.getLogger(WordUtil.class);
    public static final String rootPath = WordUtil.getRootPath();

    private static String getRootPath() {
        File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
            return path.getParentFile().getParentFile().getParentFile().getPath();
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean fileToZip(String sourceFilePath, String zipFilePath, String fileName) {
        File zipFile;
        boolean flag = false;
        File sourceFile = new File(sourceFilePath);
        if (!sourceFile.exists()) {
            System.out.println("\u5f85\u538b\u7f29\u7684\u6587\u4ef6\u76ee\u5f55\uff1a" + sourceFilePath + "\u4e0d\u5b58\u5728.");
            return flag;
        }
        File zippath = new File(zipFilePath);
        if (!zippath.exists()) {
            zippath.mkdirs();
        }
        if ((zipFile = new File(zipFilePath + File.separator + fileName)).exists()) {
            logger.debug(zipFilePath + "\u76ee\u5f55\u4e0b\u5b58\u5728\u540d\u5b57\u4e3a:" + fileName + "\u6253\u5305\u6587\u4ef6.");
            return flag;
        }
        File[] sourceFiles = sourceFile.listFiles();
        if (null == sourceFiles || sourceFiles.length < 1) {
            logger.debug("\u5f85\u538b\u7f29\u7684\u6587\u4ef6\u76ee\u5f55\uff1a" + sourceFilePath + "\u91cc\u9762\u4e0d\u5b58\u5728\u6587\u4ef6\uff0c\u65e0\u9700\u538b\u7f29.");
            return flag;
        }
        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(fos));){
            byte[] bufs = new byte[10240];
            for (int i = 0; i < sourceFiles.length; ++i) {
                ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());
                zos.putNextEntry(zipEntry);
                try (FileInputStream fis = new FileInputStream(sourceFiles[i]);
                     BufferedInputStream bis = new BufferedInputStream(fis, 10240);){
                    int read = 0;
                    while ((read = bis.read(bufs, 0, 10240)) != -1) {
                        zos.write(bufs, 0, read);
                    }
                    continue;
                }
            }
            flag = true;
        }
        catch (Exception e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
        return flag;
    }

    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        try {
            for (int i = 0; i < tempList.length; ++i) {
                temp = path.endsWith(File.separator) ? new File(path + tempList[i]) : new File(path + File.separator + tempList[i]);
                if (temp.isFile()) {
                    temp.delete();
                }
                if (!temp.isDirectory()) continue;
                WordUtil.delAllFile(path + File.separator + tempList[i]);
                flag = true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
        return flag;
    }
}

