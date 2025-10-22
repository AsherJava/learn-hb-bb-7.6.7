/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.attachment.intf;

import com.jiuqi.nr.dataentry.attachment.intf.IAttachmentGridDataContext;

public class IAttachmentGridDataPageContext
extends IAttachmentGridDataContext {
    private String searchInfo;
    private String fileTypeCode;
    private String fileCategoryCode;
    private String fileName;

    public String getSearchInfo() {
        return this.searchInfo;
    }

    public void setSearchInfo(String searchInfo) {
        this.searchInfo = searchInfo;
    }

    public String getFileTypeCode() {
        return this.fileTypeCode;
    }

    public void setFileTypeCode(String fileTypeCode) {
        this.fileTypeCode = fileTypeCode;
    }

    public String getFileCategoryCode() {
        return this.fileCategoryCode;
    }

    public void setFileCategoryCode(String fileCategoryCode) {
        this.fileCategoryCode = fileCategoryCode;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}

