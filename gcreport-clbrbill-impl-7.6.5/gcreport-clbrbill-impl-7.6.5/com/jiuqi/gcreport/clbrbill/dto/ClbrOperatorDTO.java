/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbrbill.dto;

import com.jiuqi.gcreport.clbrbill.dto.ClbrMetaInfo;

public class ClbrOperatorDTO {
    private ClbrMetaInfo meta;
    private Object content;

    public ClbrMetaInfo getMeta() {
        return this.meta;
    }

    public void setMeta(ClbrMetaInfo meta) {
        this.meta = meta;
    }

    public Object getContent() {
        return this.content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}

