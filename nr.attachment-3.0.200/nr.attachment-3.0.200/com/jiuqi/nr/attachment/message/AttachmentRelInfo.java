/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.attachment.message;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.List;

public class AttachmentRelInfo {
    private DimensionCombination dim;
    private String groupKey;
    private String formSchemeKey;
    private String fieldKey;
    private List<String> fileKeys;

    public AttachmentRelInfo(DimensionCombination dim, String groupKey, String formSchemeKey, String fieldKey, List<String> fileKeys) {
        this.dim = dim;
        this.groupKey = groupKey;
        this.formSchemeKey = formSchemeKey;
        this.fieldKey = fieldKey;
        this.fileKeys = fileKeys;
    }

    public DimensionCombination getDim() {
        return this.dim;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public String getFieldKey() {
        return this.fieldKey;
    }

    public List<String> getFileKeys() {
        return this.fileKeys;
    }
}

