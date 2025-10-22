/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.paramInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown=true)
public class DataVersionCompareParam {
    private JtableContext context;
    private List<String> formKeys;
    private UUID initialVersionId;
    private UUID compareVersionId;

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public UUID getInitialVersionId() {
        return this.initialVersionId;
    }

    public void setInitialVersionId(UUID initialVersionId) {
        this.initialVersionId = initialVersionId;
    }

    public UUID getCompareVersionId() {
        return this.compareVersionId;
    }

    public void setCompareVersionId(UUID compareVersionId) {
        this.compareVersionId = compareVersionId;
    }
}

