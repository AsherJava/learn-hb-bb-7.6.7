/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.attachment.intf;

import com.jiuqi.nr.dataentry.attachment.intf.IAttachmentContext;

public class IAttachmentChangeFileCategoryContext
extends IAttachmentContext {
    private String fileKey;
    private String fileCategory;

    public String getFileKey() {
        return this.fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getFileCategory() {
        return this.fileCategory;
    }

    public void setFileCategory(String fileCategory) {
        this.fileCategory = fileCategory;
    }
}

