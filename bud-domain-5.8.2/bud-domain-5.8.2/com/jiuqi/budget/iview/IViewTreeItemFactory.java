/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.iview;

import com.jiuqi.budget.init.BaseDO;
import com.jiuqi.budget.iview.IViewTreeItem;

public interface IViewTreeItemFactory<T extends BaseDO, E> {
    public IViewTreeItem<E> createIViewTreeItem(T var1);
}

