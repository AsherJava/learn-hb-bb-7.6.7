/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.attachment.intf;

import com.jiuqi.nr.dataentry.attachment.intf.IAttachmentGridDataPageContext;

public class FilePoolAtachmentContext
extends IAttachmentGridDataPageContext {
    private boolean notReferences = false;

    public boolean isNotReferences() {
        return this.notReferences;
    }

    public void setNotReferences(boolean notReferences) {
        this.notReferences = notReferences;
    }
}

