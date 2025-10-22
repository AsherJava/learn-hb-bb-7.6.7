/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.setting.tree.pojo;

import com.jiuqi.nr.bpm.setting.tree.pojo.IFlowNode;

public class ParamInfo
implements IFlowNode {
    private String key;
    private String title;
    private boolean expand;

    @Override
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isExpand() {
        return this.expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }
}

