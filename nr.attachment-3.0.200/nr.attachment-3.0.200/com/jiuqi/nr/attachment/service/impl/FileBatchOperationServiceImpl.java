/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.attachment.service.impl;

import com.jiuqi.nr.attachment.input.BatchDownLoadFileInfo;
import com.jiuqi.nr.attachment.input.DownLoadFileInfo;
import com.jiuqi.nr.attachment.input.FileBatchUploadContext;
import com.jiuqi.nr.attachment.input.FileUploadContext;
import com.jiuqi.nr.attachment.service.FileBatchOperationService;
import com.jiuqi.nr.attachment.service.FileOperationService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.io.OutputStream;

public class FileBatchOperationServiceImpl
implements FileBatchOperationService {
    private final DimensionCombination dimensionCombination;
    private FileOperationService fileOperationService;

    public FileBatchOperationServiceImpl(FileOperationService fileOperationService, DimensionCombination dimensionCombination) {
        this.dimensionCombination = dimensionCombination;
        this.fileOperationService = fileOperationService;
    }

    @Override
    public void uploadFile(FileBatchUploadContext fileBatchUploadContext) {
        FileUploadContext fileUploadContext = new FileUploadContext();
        fileUploadContext.setFormSchemeKey(fileBatchUploadContext.getFormSchemeKey());
        fileUploadContext.setFormKey(fileBatchUploadContext.getFormKey());
        fileUploadContext.setFieldKey(fileBatchUploadContext.getFieldKey());
        fileUploadContext.setDimensionCombination(this.dimensionCombination);
        fileUploadContext.setFileUploadInfos(fileBatchUploadContext.getFileUploadInfos());
        fileUploadContext.setGroupKey(fileBatchUploadContext.getGroupKey());
        fileUploadContext.setDataSchemeKey(fileBatchUploadContext.getDataSchemeKey());
        fileUploadContext.setTaskKey(fileBatchUploadContext.getTaskKey());
        this.fileOperationService.uploadFiles(fileUploadContext);
    }

    @Override
    public void downloadFile(BatchDownLoadFileInfo batchDownLoadFileInfo, OutputStream outputStream) {
        DownLoadFileInfo downLoadFileInfo = new DownLoadFileInfo();
        downLoadFileInfo.setTask(batchDownLoadFileInfo.getTask());
        downLoadFileInfo.setDimensionCombination(this.dimensionCombination);
        downLoadFileInfo.setFormscheme(batchDownLoadFileInfo.getFormscheme());
        downLoadFileInfo.setFileKeys(batchDownLoadFileInfo.getFileKeys());
        this.fileOperationService.downloadFile(downLoadFileInfo, outputStream);
    }
}

