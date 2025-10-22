/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.attachment.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.attachment.input.CommonParamsDTO;
import com.jiuqi.nr.attachment.input.DeleteMarkFileInfo;
import com.jiuqi.nr.attachment.input.FileUploadByGroupKeyContext;
import com.jiuqi.nr.attachment.message.FileInfo;
import com.jiuqi.nr.attachment.output.FileImportResult;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;

public interface AttachmentIOService {
    public FileImportResult uploadByGroup(FileUploadByGroupKeyContext var1);

    @Deprecated
    public List<FileInfo> getFileByGroup(String var1, String var2);

    public List<FileInfo> getFileByGroup(String var1, CommonParamsDTO var2);

    public List<FileInfo> getFileByGroup(String var1, String var2, CommonParamsDTO var3);

    @Deprecated
    public byte[] download(String var1);

    public byte[] download(String var1, CommonParamsDTO var2);

    @Deprecated
    public void download(String var1, OutputStream var2);

    public void download(String var1, CommonParamsDTO var2, OutputStream var3);

    public void batchMarkDeletion(String var1, String var2, DimensionCollection var3, String var4);

    public void batchMarkDeletion(String var1, String var2, List<DimensionCombination> var3, String var4);

    public void batchMarkDeletion(String var1, String var2, Set<String> var3);

    @Deprecated
    public void markDeletion(String var1, DimensionCombination var2, String var3);

    @Deprecated
    public void batchMarkDeletion(String var1, DimensionCollection var2, String var3);

    @Deprecated
    public void remark(String var1, List<String> var2);

    @Deprecated
    public void deleteMarkFile(DeleteMarkFileInfo var1, AsyncTaskMonitor var2);
}

