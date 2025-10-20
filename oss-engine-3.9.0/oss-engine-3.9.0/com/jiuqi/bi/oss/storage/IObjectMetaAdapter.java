/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.oss.storage;

import com.jiuqi.bi.oss.Bucket;
import com.jiuqi.bi.oss.ObjectFilterCondition;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectOrderField;
import com.jiuqi.bi.oss.ObjectStorageMetadataException;
import java.util.List;
import java.util.Map;

public interface IObjectMetaAdapter {
    public void createBucket(Bucket var1) throws ObjectStorageMetadataException;

    public void deleteBucket(String var1) throws ObjectStorageMetadataException;

    public Bucket getBucket(String var1) throws ObjectStorageMetadataException;

    public boolean isBucketLocked(String var1) throws ObjectStorageMetadataException;

    public void markBucketStatus(String var1, String var2) throws ObjectStorageMetadataException;

    public boolean existBucket(String var1) throws ObjectStorageMetadataException;

    public void updateBucketConfig(Bucket var1) throws ObjectStorageMetadataException;

    public void updateBucketDesc(String var1, String var2) throws ObjectStorageMetadataException;

    public void makeObjectLinkEnable(String var1) throws ObjectStorageMetadataException;

    public List<Bucket> listBucket() throws ObjectStorageMetadataException;

    public void addObjectInfo(String var1, ObjectInfo var2) throws ObjectStorageMetadataException;

    public ObjectInfo getObjectInfo(String var1, String var2) throws ObjectStorageMetadataException;

    public List<ObjectInfo> find(String var1, ObjectFilterCondition var2, ObjectOrderField var3) throws ObjectStorageMetadataException;

    public void batchModifyObjectProp(String var1, List<String> var2, String var3, String var4) throws ObjectStorageMetadataException;

    public int getObjectSize(String var1) throws ObjectStorageMetadataException;

    public boolean existObject(String var1, String var2) throws ObjectStorageMetadataException;

    public boolean deleteObject(String var1, String var2) throws ObjectStorageMetadataException;

    public int batchDeleteObject(String var1, List<String> var2) throws ObjectStorageMetadataException;

    public int getObjectLinkTag(String var1, String var2) throws ObjectStorageMetadataException;

    public boolean deleteObjectLink(String var1, String var2) throws ObjectStorageMetadataException;

    public int batchDeleteObjectLink(String var1, List<String> var2) throws ObjectStorageMetadataException;

    public String findObjectStoreKeyByMd5(String var1, String var2, long var3) throws ObjectStorageMetadataException;

    public String findObjectStoreKeyByObjKey(String var1, String var2) throws ObjectStorageMetadataException;

    public List<ObjectInfo> listObject(String var1, int var2, int var3) throws ObjectStorageMetadataException;

    public List<String> getExpireObjectKeys(String var1) throws ObjectStorageMetadataException;

    public Map<String, String> getDeadObjectLinks() throws ObjectStorageMetadataException;

    public void addObjectLink(String var1, String var2, String var3) throws ObjectStorageMetadataException;

    public void updateObjectLinkDelTag(String var1, String var2) throws ObjectStorageMetadataException;
}

