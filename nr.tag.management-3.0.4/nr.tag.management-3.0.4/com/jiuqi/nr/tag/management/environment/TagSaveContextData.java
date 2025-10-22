/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tag.management.environment;

import com.jiuqi.nr.tag.management.environment.BaseTagContextData;
import com.jiuqi.nr.tag.management.response.TagManagerShowData;
import java.util.List;

public class TagSaveContextData
extends BaseTagContextData {
    private List<TagManagerShowData> tagDataRows;

    public List<TagManagerShowData> getTagDataRows() {
        return this.tagDataRows;
    }

    public void setTagDataRows(List<TagManagerShowData> tagDataRows) {
        this.tagDataRows = tagDataRows;
    }
}

