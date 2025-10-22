/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.bi.security.SecurityContentException
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.customExcelBatchImport.utils;

import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.bi.security.SecurityContentException;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomExcelImportUtil {
    private static final Logger log = LoggerFactory.getLogger(CustomExcelImportUtil.class);

    public static String getSysSeparator(INvwaSystemOptionService iNvwaSystemOptionService) {
        String separatorMessage = iNvwaSystemOptionService.get("nr-data-entry-export", "SEPARATOR_CODE");
        String separator = " ";
        if (separatorMessage.equals("1")) {
            separator = "_";
        } else if (separatorMessage.equals("2")) {
            separator = "&";
        }
        return separator;
    }

    public static String getUnZipPath(String key) throws JQException {
        return System.getProperty("java.io.tmpdir") + File.separator + "customExcel" + File.separator + key + File.separator + System.currentTimeMillis() + File.separator;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void unZip(File srcFile, String destDirPath) throws RuntimeException {
        if (!srcFile.exists()) {
            throw new RuntimeException(srcFile.getPath() + "\u6240\u6307\u6587\u4ef6\u4e0d\u5b58\u5728");
        }
        try {
            PathUtils.validatePathManipulation((String)srcFile.getPath());
        }
        catch (SecurityContentException e) {
            throw new RuntimeException(e);
        }
        InputStream is = null;
        try (ZipFile zipFile = new ZipFile(srcFile, Charset.forName("GBK"));){
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String entryName = entry.getName();
                entryName = entryName.replace("\\", File.separator);
                if (entry.isDirectory()) {
                    String dirPath = destDirPath + entryName;
                    FileUtils.forceMkdir(new File(FilenameUtils.normalize(dirPath)));
                    continue;
                }
                File targetFile = new File(FilenameUtils.normalize(destDirPath + entryName));
                FileUtils.forceMkdirParent(targetFile);
                is = zipFile.getInputStream(entry);
                try {
                    FileOutputStream fos = new FileOutputStream(targetFile);
                    Throwable throwable = null;
                    try {
                        int len;
                        byte[] buf = new byte[1024];
                        while ((len = is.read(buf)) != -1) {
                            fos.write(buf, 0, len);
                        }
                    }
                    catch (Throwable throwable2) {
                        throwable = throwable2;
                        throw throwable2;
                    }
                    finally {
                        if (fos == null) continue;
                        if (throwable != null) {
                            try {
                                fos.close();
                            }
                            catch (Throwable throwable3) {
                                throwable.addSuppressed(throwable3);
                            }
                            continue;
                        }
                        fos.close();
                    }
                }
                catch (Exception e) {
                    log.error(e.getMessage(), e);
                    throw new RuntimeException("unzip error from ZipUtil", e);
                    return;
                }
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("unzip error from ZipUtil", e);
        }
    }
}

