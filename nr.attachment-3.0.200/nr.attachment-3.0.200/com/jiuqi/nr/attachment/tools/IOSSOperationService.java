/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectInfo
 */
package com.jiuqi.nr.attachment.tools;

import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.nr.attachment.exception.OSSOperationException;
import com.jiuqi.nr.attachment.message.FileInfo;
import com.jiuqi.nr.attachment.provider.param.FileBucketNameParam;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface IOSSOperationService {
    public void init(FileBucketNameParam var1) throws OSSOperationException;

    public FileInfo uploadByKey(String var1, String var2, InputStream var3, Map<String, String> var4) throws OSSOperationException;

    public List<String> batchCopy(List<String> var1) throws OSSOperationException;

    public void batchDelete(List<String> var1) throws OSSOperationException;

    public void batchDeletePic(List<String> var1) throws OSSOperationException;

    public void update(String var1, Map<String, String> var2) throws OSSOperationException;

    public void batchUpdate(List<String> var1, Map<String, String> var2) throws OSSOperationException;

    public void download(String var1, OutputStream var2) throws OSSOperationException;

    public List<ObjectInfo> getObjectInfoByKeys(List<String> var1, boolean var2) throws OSSOperationException;

    public List<ObjectInfo> getObjectInfoByProp(String var1, String var2, boolean var3) throws OSSOperationException;

    public Map<String, List<ObjectInfo>> batchGetObjectInfoByProp(String var1, List<String> var2, boolean var3) throws OSSOperationException;
}

