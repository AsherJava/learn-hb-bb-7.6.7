/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PreviewThreadCount {
    @Value(value="${jiuqi.nr.attachment.preview.thread-count:5}")
    private int previewThreadCount;

    public int getPreviewThreadCount() {
        return this.previewThreadCount;
    }
}

