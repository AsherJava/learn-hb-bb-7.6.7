/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.output.ReturnInfo
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.dataentry.attachment.service;

import com.jiuqi.nr.dataentry.attachment.intf.AttachmentDownloadContext;
import com.jiuqi.nr.dataentry.attachment.intf.DelAllNotReferAttachContext;
import com.jiuqi.nr.dataentry.attachment.intf.ForceDeleteAttachmentContext;
import com.jiuqi.nr.dataentry.attachment.intf.HistoricalAttachmentClearingContext;
import com.jiuqi.nr.dataentry.attachment.intf.IAttachmentChangeFileCategoryContext;
import com.jiuqi.nr.dataentry.attachment.intf.IAttachmentChangeFileSecretContext;
import com.jiuqi.nr.dataentry.attachment.intf.IAttachmentSaveFilesContext;
import com.jiuqi.nr.dataentry.attachment.intf.ReFileNameContext;
import com.jiuqi.nr.dataentry.attachment.message.SaveFilesResult;
import com.jiuqi.nr.dataentry.bean.BatchDownLoadEnclosureInfo;
import com.jiuqi.nr.dataentry.paramInfo.FilesUploadInfo;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

public interface AttachmentOperationService {
    public ReturnInfo uploadFiles(FilesUploadInfo var1);

    public SaveFilesResult saveFiles(IAttachmentSaveFilesContext var1);

    public ReturnInfo removeAttachmentFiles(IAttachmentChangeFileSecretContext var1);

    public ReturnInfo batchDeleteFiles(BatchDownLoadEnclosureInfo var1);

    public ReturnInfo updateJtableFilesSecret(IAttachmentChangeFileSecretContext var1);

    public ReturnInfo updateFilesCategory(IAttachmentChangeFileCategoryContext var1);

    public void downloadAttachmentFiles(AttachmentDownloadContext var1, HttpServletResponse var2) throws Exception;

    public ReturnInfo rename(ReFileNameContext var1);

    public ReturnInfo historicalAttachmentClearing(HistoricalAttachmentClearingContext var1);

    public ReturnInfo forceDeleteAttachment(ForceDeleteAttachmentContext var1);

    public ReturnInfo deleteAllNotReferencesAttachment(DelAllNotReferAttachContext var1);

    public void downloadFiles(String var1, List<String> var2, HttpServletResponse var3);
}

