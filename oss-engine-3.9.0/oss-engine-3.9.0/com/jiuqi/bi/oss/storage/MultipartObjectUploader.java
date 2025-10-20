/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.oss.storage;

import com.jiuqi.bi.oss.Bucket;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.storage.MultipartUploadResult;
import com.jiuqi.bi.oss.storage.ObjectSliceInfo;
import com.jiuqi.bi.oss.storage.PartEtag;
import java.io.InputStream;

public abstract class MultipartObjectUploader {
    protected Bucket bucket;

    public MultipartObjectUploader(Bucket bucket) {
        this.bucket = bucket;
    }

    public abstract String initUpload(ObjectInfo var1) throws ObjectStorageException;

    public abstract PartEtag uploadPart(String var1, String var2, InputStream var3, ObjectSliceInfo var4) throws ObjectStorageException;

    public abstract MultipartUploadResult finishUpload(String var1, String var2) throws ObjectStorageException;

    public abstract void abortUploadPart(String var1, String var2) throws ObjectStorageException;

    public boolean supportCheckSum() {
        return false;
    }
}

