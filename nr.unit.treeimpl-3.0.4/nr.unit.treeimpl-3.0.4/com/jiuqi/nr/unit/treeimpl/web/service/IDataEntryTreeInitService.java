/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData
 *  com.jiuiqi.nr.unit.treebase.menu.IMenuItemObject
 */
package com.jiuqi.nr.unit.treeimpl.web.service;

import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData;
import com.jiuiqi.nr.unit.treebase.menu.IMenuItemObject;
import java.util.List;
import java.util.Map;

public interface IDataEntryTreeInitService {
    public Map<String, Object> getEnvironment(UnitTreeContextData var1);

    public List<IMenuItemObject> getFilterMenusItems(UnitTreeContextData var1);

    public int getNodeAllChildrenCount(UnitTreeContextData var1);
}

