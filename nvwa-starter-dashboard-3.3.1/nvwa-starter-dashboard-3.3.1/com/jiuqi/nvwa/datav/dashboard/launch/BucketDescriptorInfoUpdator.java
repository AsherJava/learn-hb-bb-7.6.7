/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectStorageException
 *  com.jiuqi.bi.oss.ObjectStorageManager
 */
package com.jiuqi.nvwa.datav.dashboard.launch;

import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.ObjectStorageManager;

class BucketDescriptorInfoUpdator {
    BucketDescriptorInfoUpdator() {
    }

    public void updateBucketDescriptor(String bucketName) throws ObjectStorageException {
        ObjectStorageManager mgr = ObjectStorageManager.getInstance();
        try {
            mgr.setBucketDesc(bucketName, "\u5b58\u653e\u4eea\u8868\u76d8\u76f8\u5173\u7684\u56fe\u7247\u3001\u9644\u4ef6\u7b49\u8d44\u6e90");
        }
        catch (NoSuchMethodError e) {
            throw new ObjectStorageException("\u7a0b\u5e8f\u7248\u672c\u8fc7\u4f4e\uff0c\u8bf7\u5347\u7ea7\u7a0b\u5e8f", (Throwable)e);
        }
    }
}

