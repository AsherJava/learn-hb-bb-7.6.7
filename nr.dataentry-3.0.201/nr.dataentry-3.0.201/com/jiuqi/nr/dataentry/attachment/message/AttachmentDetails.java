/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.output.SecretLevelItem
 */
package com.jiuqi.nr.dataentry.attachment.message;

import com.jiuqi.nr.dataentry.attachment.message.FileCategoryInfo;
import com.jiuqi.nr.dataentry.attachment.message.FileCountInfo;
import com.jiuqi.nr.dataentry.attachment.message.FileTypeInfo;
import com.jiuqi.nr.dataentry.attachment.message.GridDataInfo;
import com.jiuqi.nr.dataentry.attachment.message.ToolBarInfo;
import com.jiuqi.nr.jtable.params.output.SecretLevelItem;
import java.util.List;

public class AttachmentDetails {
    private List<ToolBarInfo> toolbar;
    private List<SecretLevelItem> confidential;
    private FileCountInfo fileCountInfo;
    private GridDataInfo gridData;
    private List<FileTypeInfo> fileTypeInfos;
    private List<FileCategoryInfo> fileCategoryInfos;
    private boolean openSecretLevel;
    private boolean openFilepool;
    private boolean openFileCategory;
    private boolean isSystemUser = false;

    public List<ToolBarInfo> getToolbar() {
        return this.toolbar;
    }

    public void setToolbar(List<ToolBarInfo> toolbar) {
        this.toolbar = toolbar;
    }

    public List<SecretLevelItem> getConfidential() {
        return this.confidential;
    }

    public void setConfidential(List<SecretLevelItem> confidential) {
        this.confidential = confidential;
    }

    public FileCountInfo getFileCountInfo() {
        return this.fileCountInfo;
    }

    public void setFileCountInfo(FileCountInfo fileCountInfo) {
        this.fileCountInfo = fileCountInfo;
    }

    public GridDataInfo getGridData() {
        return this.gridData;
    }

    public void setGridData(GridDataInfo gridData) {
        this.gridData = gridData;
    }

    public List<FileTypeInfo> getFileTypeInfos() {
        return this.fileTypeInfos;
    }

    public void setFileTypeInfos(List<FileTypeInfo> fileTypeInfos) {
        this.fileTypeInfos = fileTypeInfos;
    }

    public List<FileCategoryInfo> getFileCategoryInfos() {
        return this.fileCategoryInfos;
    }

    public void setFileCategoryInfos(List<FileCategoryInfo> fileCategoryInfos) {
        this.fileCategoryInfos = fileCategoryInfos;
    }

    public boolean isOpenSecretLevel() {
        return this.openSecretLevel;
    }

    public void setOpenSecretLevel(boolean openSecretLevel) {
        this.openSecretLevel = openSecretLevel;
    }

    public boolean isOpenFilepool() {
        return this.openFilepool;
    }

    public void setOpenFilepool(boolean openFilepool) {
        this.openFilepool = openFilepool;
    }

    public boolean isOpenFileCategory() {
        return this.openFileCategory;
    }

    public void setOpenFileCategory(boolean openFileCategory) {
        this.openFileCategory = openFileCategory;
    }

    public boolean isSystemUser() {
        return this.isSystemUser;
    }

    public void setSystemUser(boolean systemUser) {
        this.isSystemUser = systemUser;
    }
}

