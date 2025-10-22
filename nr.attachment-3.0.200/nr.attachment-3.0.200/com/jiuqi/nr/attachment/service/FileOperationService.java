/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.attachment.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.attachment.input.AombstoneFileInfo;
import com.jiuqi.nr.attachment.input.AttachmentQueryByFieldInfo;
import com.jiuqi.nr.attachment.input.AttachmentQueryInfo;
import com.jiuqi.nr.attachment.input.BatchDeleteFileInfo;
import com.jiuqi.nr.attachment.input.ChangeFileSecretInfo;
import com.jiuqi.nr.attachment.input.CommonParamsDTO;
import com.jiuqi.nr.attachment.input.DeleteMarkFileInfo;
import com.jiuqi.nr.attachment.input.DownLoadFileInfo;
import com.jiuqi.nr.attachment.input.FileUploadByGroupKeyContext;
import com.jiuqi.nr.attachment.input.FileUploadContext;
import com.jiuqi.nr.attachment.message.FileInfo;
import com.jiuqi.nr.attachment.output.RowDataValues;
import com.jiuqi.nr.attachment.service.FileBatchOperationService;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;

public interface FileOperationService {
    public String uploadFiles(FileUploadContext var1);

    public void uploadFilesByGroupKey(FileUploadByGroupKeyContext var1);

    public void deleteFile(AombstoneFileInfo var1);

    @Deprecated
    public void physicalDeleteFile(Set<String> var1);

    public void physicalDeleteFile(Set<String> var1, CommonParamsDTO var2);

    public void physicalDeletePicture(List<String> var1, CommonParamsDTO var2);

    public void batchDeleteFile(BatchDeleteFileInfo var1);

    public void deleteMarkFile(DeleteMarkFileInfo var1, AsyncTaskMonitor var2);

    public void changeFileInfo(ChangeFileSecretInfo var1);

    public void batchUpdateGroupKey(List<String> var1, String var2, CommonParamsDTO var3);

    public RowDataValues searchFile(AttachmentQueryInfo var1);

    public RowDataValues searchFileByField(AttachmentQueryByFieldInfo var1);

    public String downloadFile(DownLoadFileInfo var1, OutputStream var2);

    public byte[] downloadFile(String var1, CommonParamsDTO var2);

    public void downloadFile(String var1, OutputStream var2, CommonParamsDTO var3);

    public void downLoadThumbnail(String var1, OutputStream var2, CommonParamsDTO var3);

    public String getFilePath(String var1, String var2, CommonParamsDTO var3);

    @Deprecated
    public List<FileInfo> getFileInfoByGroup(String var1);

    public List<FileInfo> getFileInfoByGroup(String var1, CommonParamsDTO var2);

    public List<FileInfo> getFileOrPicInfoByGroup(String var1, CommonParamsDTO var2);

    @Deprecated
    public FileInfo getFileInfoByKey(String var1);

    public FileInfo getFileInfoByKey(String var1, CommonParamsDTO var2);

    public List<FileInfo> getFileInfoByKeys(List<String> var1, CommonParamsDTO var2);

    @Deprecated
    public FileBatchOperationService getBatchOperationService(DimensionCombination var1);

    public List<String> getGroupKeys(String var1, DimensionValueSet var2, String var3, String var4);

    public String getCurrentSplitTableName(DataScheme var1);

    public List<String> existFile(List<String> var1, CommonParamsDTO var2);

    public List<FileInfo> existFile(List<FileInfo> var1, String var2, DimensionValueSet var3, String var4, String var5, String var6);
}

