/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tag.management.environment;

import com.jiuqi.nr.tag.management.environment.TagQueryContextData;
import java.util.List;

public class TagCountContextData
extends TagQueryContextData {
    private List<String> dataKeys;

    public List<String> getDataKeys() {
        return this.dataKeys;
    }

    public void setDataKeys(List<String> dataKeys) {
        this.dataKeys = dataKeys;
    }
}

