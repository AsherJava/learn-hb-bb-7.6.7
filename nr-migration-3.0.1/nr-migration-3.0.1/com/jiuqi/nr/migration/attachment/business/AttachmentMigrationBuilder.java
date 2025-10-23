/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.Bucket
 *  com.jiuqi.bi.oss.BucketService
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.bi.oss.ObjectStorageException
 *  com.jiuqi.bi.oss.ObjectStorageManager
 *  com.jiuqi.bi.oss.ObjectStorageService
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.nr.migration.attachment.business;

import com.jiuqi.bi.oss.Bucket;
import com.jiuqi.bi.oss.BucketService;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.ObjectStorageManager;
import com.jiuqi.bi.oss.ObjectStorageService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.migration.attachment.bean.AttachmentInfo;

public class AttachmentMigrationBuilder {
    public static final String JTABLE_FILE_AREA = "JTABLEAREA";
    public static final String GROUPKEY = "fileGroupKey";

    public static ObjectStorageService objService() throws ObjectStorageException {
        BucketService bucketService = ObjectStorageManager.getInstance().createBucketService();
        boolean exist = bucketService.existBucket(JTABLE_FILE_AREA);
        if (!exist) {
            Bucket bucket = new Bucket(JTABLE_FILE_AREA);
            bucketService.createBucket(bucket);
            bucketService.close();
        }
        ObjectStorageService objService = ObjectStorageManager.getInstance().createObjectService(JTABLE_FILE_AREA);
        return objService;
    }

    public static ObjectInfo getObjInfo(AttachmentInfo attachmentInfo) {
        ObjectInfo resInfo = new ObjectInfo();
        resInfo.setKey(attachmentInfo.getId());
        resInfo.setOwner(NpContextHolder.getContext().getUserName());
        resInfo.setSize((long)attachmentInfo.getSize());
        resInfo.setCreateTime(attachmentInfo.getCreateTime());
        String fileName = attachmentInfo.getTitle();
        if (fileName != null) {
            resInfo.setName(fileName);
            if (fileName.contains(".")) {
                resInfo.setExtension(fileName.substring(fileName.lastIndexOf(".")));
            }
        }
        if (attachmentInfo.getGroupId() != null) {
            resInfo.getExtProp().put(GROUPKEY, attachmentInfo.getGroupId());
        }
        return resInfo;
    }
}

