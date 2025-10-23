/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectStorageException
 *  com.jiuqi.bi.oss.ObjectStorageManager
 *  com.jiuqi.bi.oss.ObjectStorageService
 */
package com.jiuqi.nr.migration.transferdata.common;

import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.ObjectStorageManager;
import com.jiuqi.bi.oss.ObjectStorageService;
import java.io.InputStream;

public class OSSTransUtil {
    private final String OSSKEY1_EXPORT = "3d03343c-68f3-4abb-b99d-973af3e427d5";
    private final String OSSKEY2_IMPORT = "3d03343c-68f3-4abb-b99d-973af3e427d6";
    private static final ObjectStorageManager mgr = ObjectStorageManager.getInstance();

    public static String saveToOss(String ossKey, InputStream is) {
        try {
            ObjectStorageService objectStorageService = mgr.createTemporaryObjectService();
            objectStorageService.upload(ossKey, is);
        }
        catch (ObjectStorageException e) {
            throw new RuntimeException(e);
        }
        return ossKey;
    }

    public static InputStream getFromOss(String ossKey) {
        try {
            ObjectStorageService objectStorageService = mgr.createTemporaryObjectService();
            return objectStorageService.download(ossKey);
        }
        catch (ObjectStorageException e) {
            throw new RuntimeException(e);
        }
    }
}

