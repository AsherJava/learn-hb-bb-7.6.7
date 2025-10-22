/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.attachment.message.FileInfo
 *  com.jiuqi.nr.attachment.output.FilePoolFiles
 *  com.jiuqi.nr.attachment.output.ReferencesInfo
 *  com.jiuqi.nr.common.itree.ITree
 */
package com.jiuqi.nr.dataentry.attachment.service;

import com.jiuqi.nr.attachment.message.FileInfo;
import com.jiuqi.nr.attachment.output.FilePoolFiles;
import com.jiuqi.nr.attachment.output.ReferencesInfo;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.dataentry.attachment.intf.AttachmentReferencesContext;
import com.jiuqi.nr.dataentry.attachment.intf.FileGridDataContext;
import com.jiuqi.nr.dataentry.attachment.intf.FilePoolAtachmentContext;
import com.jiuqi.nr.dataentry.attachment.intf.IAttachmentContext;
import com.jiuqi.nr.dataentry.attachment.intf.IAttachmentGridDataContext;
import com.jiuqi.nr.dataentry.attachment.intf.IAttachmentGridDataPageContext;
import com.jiuqi.nr.dataentry.attachment.intf.QueryFileInfoParam;
import com.jiuqi.nr.dataentry.attachment.message.AttachmentDetails;
import com.jiuqi.nr.dataentry.attachment.message.FileDetails;
import com.jiuqi.nr.dataentry.attachment.message.FileNode;
import com.jiuqi.nr.dataentry.attachment.message.FormDataInfo;
import com.jiuqi.nr.dataentry.attachment.message.GridDataInfo;
import com.jiuqi.nr.dataentry.paramInfo.FilesUploadInfo;
import java.util.List;

public interface IAttachmentService {
    public List<ITree<FileNode>> loadFieldGroups(IAttachmentContext var1);

    public AttachmentDetails loadDetails(IAttachmentGridDataContext var1);

    public GridDataInfo loadGridPageData(IAttachmentGridDataPageContext var1);

    public GridDataInfo searchByFilename(IAttachmentGridDataPageContext var1);

    public List<FormDataInfo> getFormData(IAttachmentContext var1);

    public FilesUploadInfo uploadVerification(FilesUploadInfo var1);

    public List<ReferencesInfo> getAttachmentReferences(AttachmentReferencesContext var1);

    public GridDataInfo getFilePoolAttachment(FilePoolAtachmentContext var1);

    public List<FilePoolFiles> getAllFilePoolFiles(IAttachmentGridDataPageContext var1);

    public List<FileInfo> getFileInfoByGroupKey(String var1, String var2);

    public FileDetails loadDetails(FileGridDataContext var1);

    public int queryFileCount(QueryFileInfoParam var1);

    public List<FileInfo> queryFileList(QueryFileInfoParam var1);
}

