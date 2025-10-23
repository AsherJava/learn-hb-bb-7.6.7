/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.rest.vo;

import com.jiuqi.nr.zbquery.bean.impl.ResourceTreeAdaptNode;

public class ResourceTreeAdaptNodeVo
extends ResourceTreeAdaptNode {
    private String treeType;

    public ResourceTreeAdaptNodeVo(String treeType, ResourceTreeAdaptNode adaptNode) {
        super(adaptNode);
        this.treeType = treeType;
    }

    public String getTreeType() {
        return this.treeType;
    }

    public void setTreeType(String treeType) {
        this.treeType = treeType;
    }
}

