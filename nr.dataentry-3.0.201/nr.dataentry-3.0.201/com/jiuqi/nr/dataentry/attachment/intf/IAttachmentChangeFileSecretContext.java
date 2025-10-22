/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.attachment.intf;

import com.jiuqi.nr.dataentry.attachment.intf.IAttachmentContext;

public class IAttachmentChangeFileSecretContext
extends IAttachmentContext {
    private String fileKey;
    private String fileSecret;

    public String getFileKey() {
        return this.fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getFileSecret() {
        return this.fileSecret;
    }

    public void setFileSecret(String fileSecret) {
        this.fileSecret = fileSecret;
    }
}

