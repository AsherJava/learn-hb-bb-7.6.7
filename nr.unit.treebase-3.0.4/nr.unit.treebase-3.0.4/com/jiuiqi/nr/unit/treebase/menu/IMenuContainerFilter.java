/*
 * Decompiled with CFR 0.152.
 */
package com.jiuiqi.nr.unit.treebase.menu;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.menu.IMenuItemObject;
import java.util.List;

public interface IMenuContainerFilter {
    public String getContainerId();

    public List<IMenuItemObject> doFilterMenus(List<IMenuItemObject> var1, IUnitTreeContext var2);
}

