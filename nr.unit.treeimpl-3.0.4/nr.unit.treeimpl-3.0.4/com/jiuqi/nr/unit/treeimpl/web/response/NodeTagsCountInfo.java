/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.unit.treeimpl.web.response;

import com.jiuqi.nr.unit.treeimpl.web.response.NodeTagsCountObject;
import java.util.List;

public class NodeTagsCountInfo {
    private Integer totalCount;
    private List<NodeTagsCountObject> countObjects;

    public Integer getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<NodeTagsCountObject> getCountObjects() {
        return this.countObjects;
    }

    public void setCountObjects(List<NodeTagsCountObject> countObjects) {
        this.countObjects = countObjects;
    }
}

