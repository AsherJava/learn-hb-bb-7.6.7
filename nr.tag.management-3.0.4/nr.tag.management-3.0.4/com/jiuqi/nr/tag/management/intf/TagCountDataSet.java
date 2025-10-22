/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 */
package com.jiuqi.nr.tag.management.intf;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.nr.tag.management.intf.ITagCountDataSet;
import com.jiuqi.nr.tag.management.intf.ITagFacade;
import java.util.List;
import java.util.Map;

public class TagCountDataSet
implements ITagCountDataSet {
    private List<ITagFacade> tags;
    private Map<String, List<String>> dataSet;

    @Override
    @JsonIgnore
    public boolean isEmpty() {
        return this.dataSet.isEmpty();
    }

    @Override
    @JsonIgnore
    public boolean containsKey(String key) {
        return this.dataSet.containsKey(key);
    }

    @Override
    public List<String> getResultSet(String key) {
        return this.dataSet.get(key);
    }

    public List<ITagFacade> getTags() {
        return this.tags;
    }

    public void setTags(List<ITagFacade> tags) {
        this.tags = tags;
    }

    @Override
    public Map<String, List<String>> getDataSet() {
        return this.dataSet;
    }

    public void setDataSet(Map<String, List<String>> dataSet) {
        this.dataSet = dataSet;
    }
}

