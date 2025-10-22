/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.StringUtils
 */
package com.jiuqi.np.dataengine.event;

import com.jiuqi.np.definition.common.StringUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FixedParentItem {
    private String parentsValue;
    private List<String> parentKeys = new ArrayList<String>(4);
    private Set<String> parentSet = new HashSet<String>(4);
    private String parentKey;
    private boolean notExsit;
    private boolean hasCycle;

    public List<String> getParentKeys() {
        return this.parentKeys;
    }

    public void setParentKeys(List<String> parentKeys) {
        this.parentKeys = parentKeys;
    }

    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public boolean isNotExsit() {
        return this.notExsit;
    }

    public void setNotExsit(boolean notExsit) {
        this.notExsit = notExsit;
    }

    public boolean isHasCycle() {
        return this.hasCycle;
    }

    public void setHasCycle(boolean hasCycle) {
        this.hasCycle = hasCycle;
    }

    public String getParents() {
        StringBuilder parents = new StringBuilder();
        if (this.notExsit || this.hasCycle) {
            return parents.toString();
        }
        if (!StringUtils.isEmpty((String)this.parentsValue)) {
            parents.append(this.parentsValue);
            parents.append("/");
        }
        for (int index = this.parentKeys.size() - 1; index >= 0; --index) {
            parents.append(this.parentKeys.get(index)).append("/");
        }
        int length = parents.length();
        if (length > 0) {
            parents.setLength(length - 1);
        }
        return parents.toString();
    }

    public void addParentKey(String parentValue) {
        if (this.parentSet.contains(parentValue)) {
            this.hasCycle = true;
            this.parentKey = parentValue;
            return;
        }
        this.parentSet.add(parentValue);
        this.parentKeys.add(parentValue);
    }

    public void setParentsValue(String parents) {
        this.parentsValue = parents;
    }
}

