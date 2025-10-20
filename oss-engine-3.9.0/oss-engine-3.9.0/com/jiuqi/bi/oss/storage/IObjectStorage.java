/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.oss.storage;

import com.jiuqi.bi.oss.Bucket;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.storage.MultipartObjectUploader;
import com.jiuqi.bi.oss.storage.StorageConfig;
import java.io.InputStream;
import java.io.OutputStream;

public interface IObjectStorage
extends AutoCloseable {
    default public String name() {
        return null;
    }

    public void initialize(StorageConfig var1) throws ObjectStorageException;

    public boolean createBucket(Bucket var1) throws ObjectStorageException;

    public boolean deleteBucket(String var1) throws ObjectStorageException;

    public void upload(String var1, String var2, InputStream var3, ObjectInfo var4) throws ObjectStorageException;

    public MultipartObjectUploader createMultipartObjectUploader(Bucket var1) throws ObjectStorageException;

    public InputStream download(String var1, String var2) throws ObjectStorageException;

    public void download(String var1, String var2, OutputStream var3) throws ObjectStorageException;

    public boolean existObject(String var1, String var2) throws ObjectStorageException;

    public boolean deleteObject(String var1, String var2) throws ObjectStorageException;

    public void move(String var1, String var2, String var3) throws ObjectStorageException;

    public String copy(String var1, String var2) throws ObjectStorageException;

    @Override
    public void close() throws ObjectStorageException;
}

