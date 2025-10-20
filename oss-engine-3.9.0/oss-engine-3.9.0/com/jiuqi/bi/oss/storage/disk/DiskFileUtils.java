/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.bi.security.SecurityContentException
 */
package com.jiuqi.bi.oss.storage.disk;

import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.bi.security.SecurityContentException;
import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DiskFileUtils {
    private static final Logger log = LoggerFactory.getLogger(DiskFileUtils.class);
    private static final String DIR_PREFIX = "part-";

    public static final File computePathByKey(File root, int partationSize, String bucketName, String key, boolean tryFindInRoot) {
        try {
            PathUtils.validatePathManipulation((String)root.getPath());
            PathUtils.validatePathManipulation((String)bucketName);
        }
        catch (SecurityContentException e) {
            throw new RuntimeException(e);
        }
        File f = new File(root, bucketName);
        if (partationSize <= 1) {
            return f;
        }
        int v = key.hashCode();
        int partNum = v % partationSize + 1;
        File file = new File(f, DIR_PREFIX + partNum);
        if (tryFindInRoot) {
            try {
                PathUtils.validatePathManipulation((String)key);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            File dest = new File(file, key);
            if (!dest.exists() && (dest = new File(f, key)).exists()) {
                return f;
            }
        }
        return file;
    }

    public static final void validateObjectKey(String key) throws ObjectStorageException {
        try {
            PathUtils.validatePathManipulation((String)key);
        }
        catch (SecurityContentException e) {
            throw new ObjectStorageException(e.getMessage());
        }
        char[] chars = key.toCharArray();
        for (int i = 0; i < chars.length - 1; ++i) {
            if (chars[i] != '.' || chars[i + 1] != '.' || (i + 2 >= chars.length || chars[i + 2] != '/') && (i <= 0 || chars[i - 1] != '/')) continue;
            throw new ObjectStorageException("\u975e\u6cd5\u7684key");
        }
    }

    public static final void deleteFileRecurisve(File f) {
        if (f.isDirectory()) {
            File[] subs;
            for (File sub : subs = f.listFiles()) {
                DiskFileUtils.deleteFileRecurisve(sub);
            }
        }
        f.delete();
    }
}

