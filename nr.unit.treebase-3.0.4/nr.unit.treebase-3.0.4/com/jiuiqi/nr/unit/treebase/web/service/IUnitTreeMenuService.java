/*
 * Decompiled with CFR 0.152.
 */
package com.jiuiqi.nr.unit.treebase.web.service;

import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData;
import com.jiuiqi.nr.unit.treebase.menu.IMenuItemObject;
import java.util.List;

public interface IUnitTreeMenuService {
    public List<IMenuItemObject> getContextMenuItems(UnitTreeContextData var1);

    public List<IMenuItemObject> getFilterMenusItems(UnitTreeContextData var1);
}

