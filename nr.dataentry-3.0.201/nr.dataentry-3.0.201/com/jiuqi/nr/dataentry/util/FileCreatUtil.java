/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.bi.security.SecurityContentException
 */
package com.jiuqi.nr.dataentry.util;

import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.bi.security.SecurityContentException;
import java.io.File;
import java.io.FileOutputStream;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileCreatUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileCreatUtil.class);

    public static void createFileWithPatch(String fileName, String filePath, byte[] dataByte) {
        try {
            PathUtils.validatePathManipulation((String)filePath);
            PathUtils.validatePathManipulation((String)fileName);
        }
        catch (SecurityContentException e) {
            throw new RuntimeException(e);
        }
        File pathFile = new File(FilenameUtils.normalize(filePath));
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        String fileInfo = filePath + fileName;
        File file = new File(fileInfo);
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);){
            fileOutputStream.write(dataByte);
            fileOutputStream.flush();
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }
}

