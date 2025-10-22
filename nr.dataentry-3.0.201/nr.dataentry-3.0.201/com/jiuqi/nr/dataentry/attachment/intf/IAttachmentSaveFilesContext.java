/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.attachment.intf;

import com.jiuqi.nr.dataentry.attachment.intf.IAttachmentContext;

public class IAttachmentSaveFilesContext
extends IAttachmentContext {
    private String dataLinkKey;
    private String fieldKey;
    private String fileGroupKey;
    private String saveType;

    public String getDataLinkKey() {
        return this.dataLinkKey;
    }

    public void setDataLinkKey(String dataLinkKey) {
        this.dataLinkKey = dataLinkKey;
    }

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getFileGroupKey() {
        return this.fileGroupKey;
    }

    public void setFileGroupKey(String fileGroupKey) {
        this.fileGroupKey = fileGroupKey;
    }

    public String getSaveType() {
        return this.saveType;
    }

    public void setSaveType(String saveType) {
        this.saveType = saveType;
    }
}

