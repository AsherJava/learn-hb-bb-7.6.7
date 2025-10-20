/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset;

import com.jiuqi.bi.dataset.tree.DSTreeItem;
import java.util.List;

public interface IDSTreeItem {
    public String getCode();

    public String getTitle();

    public Double getValue();

    public List<DSTreeItem> getChildren();
}

