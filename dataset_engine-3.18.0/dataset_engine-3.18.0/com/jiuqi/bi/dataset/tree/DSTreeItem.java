/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.dataset.tree;

import com.jiuqi.bi.dataset.IDSTreeItem;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

public final class DSTreeItem
implements IDSTreeItem {
    private String code;
    private String title;
    private Double value;
    private List<DSTreeItem> children = new ArrayList<DSTreeItem>();
    private String parent;

    @Override
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Double getValue() {
        return this.value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public List<DSTreeItem> getChildren() {
        return this.children;
    }

    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String toString() {
        return StringUtils.isEmpty((String)this.title) ? this.code : this.title;
    }
}

