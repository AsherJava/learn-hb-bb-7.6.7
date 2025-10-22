/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tag.management.environment;

import com.jiuqi.nr.tag.management.environment.BaseTagContextData;
import com.jiuqi.nr.tag.management.response.TagUnitRangeData;

public class TagSaveUnitRangeContextData
extends BaseTagContextData {
    private String tagKey;
    private TagUnitRangeData tagRange;

    public String getTagKey() {
        return this.tagKey;
    }

    public void setTagKey(String tagKey) {
        this.tagKey = tagKey;
    }

    public TagUnitRangeData getTagRange() {
        return this.tagRange;
    }

    public void setTagRange(TagUnitRangeData tagRange) {
        this.tagRange = tagRange;
    }
}

