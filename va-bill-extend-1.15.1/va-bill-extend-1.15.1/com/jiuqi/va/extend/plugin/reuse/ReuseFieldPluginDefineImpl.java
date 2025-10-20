/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.model.PluginDefineImpl
 */
package com.jiuqi.va.extend.plugin.reuse;

import com.jiuqi.va.biz.impl.model.PluginDefineImpl;
import com.jiuqi.va.extend.domain.BillReuseField;
import java.util.List;

public class ReuseFieldPluginDefineImpl
extends PluginDefineImpl {
    private List<BillReuseField> reUseFields;
    private Boolean copyAttachment;

    public Boolean getCopyAttachment() {
        return this.copyAttachment;
    }

    public void setCopyAttachment(Boolean canCopyAttachment) {
        this.copyAttachment = canCopyAttachment;
    }

    public List<BillReuseField> getReUseFields() {
        return this.reUseFields;
    }

    public void setReUseFields(List<BillReuseField> reUseFields) {
        this.reUseFields = reUseFields;
    }
}

