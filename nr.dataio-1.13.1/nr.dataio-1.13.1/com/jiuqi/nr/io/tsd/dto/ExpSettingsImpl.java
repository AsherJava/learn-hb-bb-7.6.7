/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.service.ExpSettings
 */
package com.jiuqi.nr.io.tsd.dto;

import com.jiuqi.nr.data.common.service.ExpSettings;

public class ExpSettingsImpl
implements ExpSettings {
    private boolean exportAttachments;

    public boolean isExportAttachments() {
        return this.exportAttachments;
    }

    public void setExportAttachments(boolean exportAttachments) {
        this.exportAttachments = exportAttachments;
    }
}

