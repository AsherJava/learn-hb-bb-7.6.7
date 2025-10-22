/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.service;

import com.jiuqi.nr.attachment.input.AombstoneFileInfo;
import com.jiuqi.nr.attachment.input.ChangeFileCategoryInfo;
import com.jiuqi.nr.attachment.input.CommonParamsDTO;
import com.jiuqi.nr.attachment.input.FilePoolAllFileContext;
import com.jiuqi.nr.attachment.input.FilePoolContext;
import com.jiuqi.nr.attachment.input.FilePoolUploadContext;
import com.jiuqi.nr.attachment.input.FileRelateGroupInfo;
import com.jiuqi.nr.attachment.input.FileUploadRelevanceInfo;
import com.jiuqi.nr.attachment.input.RenameInfo;
import com.jiuqi.nr.attachment.input.SearchContext;
import com.jiuqi.nr.attachment.message.FileInfo;
import com.jiuqi.nr.attachment.output.FileBaseRefInfo;
import com.jiuqi.nr.attachment.output.FileInfosAndGroup;
import com.jiuqi.nr.attachment.output.FilePoolFiles;
import com.jiuqi.nr.attachment.output.ReferencesInfo;
import com.jiuqi.nr.attachment.output.RowDataValues;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface FilePoolService {
    public boolean isOpenFilepool();

    public boolean isOpenFileCategory();

    public List<FileInfo> uploadFilesLocally(FilePoolUploadContext var1);

    public String uploadFilesFromFilePool(FileRelateGroupInfo var1);

    @Deprecated
    public String uploadFilesAndRelevance(FileUploadRelevanceInfo var1);

    @Deprecated
    public void tombstoneFile(AombstoneFileInfo var1);

    public void markDelFileByGroupKey(Set<String> var1);

    public void markDelFileByGroupKey(String var1, Set<String> var2);

    public void rename(RenameInfo var1);

    public void updateCreateTime(FileRelateGroupInfo var1);

    public void changeFileCategory(ChangeFileCategoryInfo var1);

    public void changeFileInfoAfterUpload(String var1, String var2, String var3, CommonParamsDTO var4);

    public RowDataValues search(SearchContext var1);

    public List<ReferencesInfo> getReferences(String var1, String var2);

    public Map<String, List<FileBaseRefInfo>> getBaseReferences(List<String> var1, String var2);

    public RowDataValues getFileInfoByFilePool(FilePoolContext var1);

    public List<FilePoolFiles> getFilePoolFiles(FilePoolAllFileContext var1);

    public List<FilePoolFiles> getFilePoolFiles(FilePoolAllFileContext var1, boolean var2);

    @Deprecated
    public List<FileInfo> getFileInfoByGroup(String var1, String var2);

    public List<FileInfo> getFileInfoByGroup(String var1, CommonParamsDTO var2);

    @Deprecated
    public List<FileInfo> getFileInfoByGroup(String var1, String var2, boolean var3);

    public List<FileInfo> getFileInfoByGroup(String var1, boolean var2, CommonParamsDTO var3);

    @Deprecated
    public List<FileInfosAndGroup> getFileInfoByGroup(List<String> var1, String var2);

    public List<FileInfosAndGroup> getFileInfoByGroup(List<String> var1, CommonParamsDTO var2);

    public boolean judgeFileOverwritten(String var1, String var2);
}

