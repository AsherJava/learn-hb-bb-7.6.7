/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.params.base;

import java.io.Serializable;

public class IsNewAttachmentInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean isNewAttachment = false;

    public boolean isNewAttachment() {
        return this.isNewAttachment;
    }

    public void setNewAttachment(boolean newAttachment) {
        this.isNewAttachment = newAttachment;
    }
}

