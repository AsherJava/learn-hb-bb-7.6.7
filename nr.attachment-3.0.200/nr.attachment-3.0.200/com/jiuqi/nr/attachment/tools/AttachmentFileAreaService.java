/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.bi.oss.ObjectStorageException
 */
package com.jiuqi.nr.attachment.tools;

import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.nr.attachment.message.FileInfo;
import com.jiuqi.nr.attachment.provider.param.FileBucketNameParam;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface AttachmentFileAreaService {
    public FileInfo upload(FileBucketNameParam var1, String var2, InputStream var3, Map<String, String> var4);

    public FileInfo uploadByKey(FileBucketNameParam var1, String var2, String var3, InputStream var4, Map<String, String> var5);

    public void uploadByKey(FileBucketNameParam var1, String var2, String var3, String var4, String var5, InputStream var6, Map<String, String> var7);

    public FileInfo delete(FileBucketNameParam var1, String var2);

    public void batchDelete(FileBucketNameParam var1, List<String> var2);

    public void batchDeletePic(FileBucketNameParam var1, List<String> var2);

    public void update(FileBucketNameParam var1, String var2, Map<String, String> var3) throws ObjectStorageException;

    public void batchupdate(FileBucketNameParam var1, List<String> var2, Map<String, String> var3);

    public List<String> batchFileCopy(FileBucketNameParam var1, List<String> var2);

    public FileInfo getFileInfo(FileBucketNameParam var1, String var2);

    public ObjectInfo getObjectInfo(FileBucketNameParam var1, String var2);

    public List<FileInfo> getFileInfoByKeys(FileBucketNameParam var1, List<String> var2);

    public List<FileInfo> getFileInfoByKeys(FileBucketNameParam var1, List<String> var2, boolean var3);

    public List<ObjectInfo> getObjectInfoByKeys(FileBucketNameParam var1, List<String> var2);

    public List<FileInfo> getFileInfoByProp(FileBucketNameParam var1, String var2, String var3);

    public List<FileInfo> getFileInfoByProp(FileBucketNameParam var1, String var2, String var3, boolean var4);

    public List<ObjectInfo> getObjectInfoByProp(FileBucketNameParam var1, String var2, String var3);

    public List<ObjectInfo> getObjectInfoByProp(FileBucketNameParam var1, String var2, String var3, boolean var4);

    public byte[] download(FileBucketNameParam var1, String var2);

    public void download(FileBucketNameParam var1, String var2, OutputStream var3);

    public String getPath(FileBucketNameParam var1, String var2, String var3);

    public List<String> existFile(FileBucketNameParam var1, List<String> var2);

    public List<String> existFile(FileBucketNameParam var1, List<String> var2, int var3);
}

