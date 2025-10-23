/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.bi.oss.ObjectStorageException
 *  com.jiuqi.bi.oss.ObjectStorageManager
 *  com.jiuqi.bi.oss.ObjectStorageService
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.nr.datascheme.common.io.ZipUtils
 *  com.jiuqi.nr.file.exception.FileException
 *  com.jiuqi.nr.file.impl.FileInfoBuilder
 *  com.jiuqi.nr.file.utils.FileUtils
 */
package com.jiuqi.nr.param.transfer;

import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.ObjectStorageManager;
import com.jiuqi.bi.oss.ObjectStorageService;
import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.nr.datascheme.common.io.ZipUtils;
import com.jiuqi.nr.file.exception.FileException;
import com.jiuqi.nr.file.impl.FileInfoBuilder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class Utils {
    private static final Logger logger = LoggerFactory.getLogger(Utils.class);
    private static final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static ObjectStorageService objService() throws ObjectStorageException {
        ObjectStorageService temporaryObjectService = ObjectStorageManager.getInstance().createTemporaryObjectService();
        return temporaryObjectService;
    }

    public static void deleteAllFilesOfDir(File path) {
        if (path.exists()) {
            if (path.isFile()) {
                path.delete();
                return;
            }
            try {
                PathUtils.validatePathManipulation((String)path.getPath());
                FileUtils.deleteDirectory(path);
            }
            catch (Exception e) {
                logger.error("\u5220\u9664\u4e34\u65f6\u6587\u4ef6\u5931\u8d25" + e.getMessage());
            }
        } else {
            logger.info("\u8981\u5220\u9664\u4e34\u65f6\u6587\u4ef6\u4e0d\u5b58\u5728");
        }
    }

    public static void deleteAllFilesOfDirByPath(String path) {
        if (StringUtils.hasText(path)) {
            File tempFile2 = new File(path);
            Utils.deleteAllFilesOfDir(tempFile2);
        }
    }

    public static String fileUpload(String fileName, InputStream input) throws IOException {
        try {
            if (input == null) {
                throw new IllegalArgumentException("\u6587\u4ef6\u4e0d\u80fd\u4e3a\u7a7a\uff01");
            }
            String currentUser = FileInfoBuilder.resolveCurrentUserName();
            String newKey = com.jiuqi.nr.file.utils.FileUtils.generateFileKey();
            ObjectInfo info = new ObjectInfo();
            info.setKey(newKey);
            info.setName(fileName);
            info.setOwner(currentUser);
            Utils.objService().upload(newKey, input, info);
            return newKey;
        }
        catch (Exception e) {
            throw new FileException("\u6587\u4ef6\u4fdd\u5b58\u5931\u8d25\uff01", (Throwable)e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String fileUpload(String fileName, StringBuilder allMessage) throws Exception {
        String tempDir = ZipUtils.newTempDir();
        try {
            PathUtils.validatePathManipulation((String)tempDir);
        }
        catch (Exception e) {
            logger.error("\u6587\u4ef6\u8def\u5f84\u6821\u9a8c\u5931\u8d25" + e.getMessage());
        }
        File file = new File(tempDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        String filePath = tempDir.concat(File.separator).concat(fileName);
        try (FileWriter writer = new FileWriter(filePath);){
            writer.write(allMessage.toString());
        }
        catch (IOException e) {
            logger.error("\u53c2\u6570\u5bfc\u5165\u5199\u5165\u6587\u4ef6\u65f6\u51fa\u73b0\u9519\u8bef: " + e.getMessage(), e);
        }
        String fileKey = "";
        try (FileInputStream uploadInputStream = new FileInputStream(filePath);){
            fileKey = Utils.fileUpload(fileName, uploadInputStream);
        }
        catch (Exception exception) {
        }
        finally {
            Utils.deleteAllFilesOfDirByPath(tempDir);
        }
        return fileKey;
    }

    public static long stringToOrder(String orderStr) {
        long order = 0L;
        for (int i = 0; i < orderStr.length(); ++i) {
            char ch = orderStr.charAt(i);
            int index = CHARS.indexOf(ch);
            if (index == -1) {
                throw new IllegalArgumentException("Invalid character in order string: " + ch);
            }
            order = order * 36L + (long)index;
        }
        return order;
    }
}

