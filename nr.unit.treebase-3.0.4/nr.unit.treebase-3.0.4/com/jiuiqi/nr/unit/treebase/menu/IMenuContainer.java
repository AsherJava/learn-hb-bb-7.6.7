/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 */
package com.jiuiqi.nr.unit.treebase.menu;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.menu.IMenuContainerType;
import com.jiuiqi.nr.unit.treebase.menu.IMenuItemObject;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import java.util.List;

public interface IMenuContainer {
    public String getContainerId();

    public String getContainerName();

    public String getContainerTitle();

    public IMenuContainerType getContainerType();

    public int getOrdinary();

    public List<IMenuItemObject> getRegisterMenus(IUnitTreeContext var1);

    public List<IMenuItemObject> getDisplayItems(IUnitTreeContext var1, IBaseNodeData var2);
}

