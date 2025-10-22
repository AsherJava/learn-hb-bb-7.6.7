/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tag.management.environment;

import com.jiuqi.nr.tag.management.environment.BaseTagContextData;

public class TagDeleteContextData
extends BaseTagContextData {
    private String[] tagKeys;

    public String[] getTagKeys() {
        return this.tagKeys;
    }

    public void setTagKeys(String[] tagKeys) {
        this.tagKeys = tagKeys;
    }
}

