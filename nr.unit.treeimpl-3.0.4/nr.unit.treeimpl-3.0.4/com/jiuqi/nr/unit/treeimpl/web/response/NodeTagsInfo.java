/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.tag.management.intf.ITagFacade
 */
package com.jiuqi.nr.unit.treeimpl.web.response;

import com.jiuqi.nr.tag.management.intf.ITagFacade;
import java.util.List;

public class NodeTagsInfo {
    private List<ITagFacade> allTags;
    private List<String> nodeTagKeys;

    public List<ITagFacade> getAllTags() {
        return this.allTags;
    }

    public void setAllTags(List<ITagFacade> allTags) {
        this.allTags = allTags;
    }

    public List<String> getNodeTagKeys() {
        return this.nodeTagKeys;
    }

    public void setNodeTagKeys(List<String> nodeTagKeys) {
        this.nodeTagKeys = nodeTagKeys;
    }
}

