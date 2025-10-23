/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 */
package com.jiuqi.nr.datascheme.web.param;

import com.jiuqi.nr.datascheme.api.core.NodeType;

public class MoveSchemeVO {
    private NodeType type;
    private String key;
    private String parentKey;
    private Integer move;
    private String schemeKey;

    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public NodeType getType() {
        return this.type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getMove() {
        return this.move;
    }

    public void setMove(Integer move) {
        this.move = move;
    }

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }
}

