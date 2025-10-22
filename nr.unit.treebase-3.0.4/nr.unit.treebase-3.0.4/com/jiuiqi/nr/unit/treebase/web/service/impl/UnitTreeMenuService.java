/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  javax.annotation.Resource
 */
package com.jiuiqi.nr.unit.treebase.web.service.impl;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextBuilder;
import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData;
import com.jiuiqi.nr.unit.treebase.menu.IMenuContainer;
import com.jiuiqi.nr.unit.treebase.menu.IMenuContainerFilter;
import com.jiuiqi.nr.unit.treebase.menu.IMenuContainerFilterHelper;
import com.jiuiqi.nr.unit.treebase.menu.IMenuContainerHelper;
import com.jiuiqi.nr.unit.treebase.menu.IMenuContainerType;
import com.jiuiqi.nr.unit.treebase.menu.IMenuItemObject;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSourceID;
import com.jiuiqi.nr.unit.treebase.web.service.IUnitTreeMenuService;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UnitTreeMenuService
implements IUnitTreeMenuService {
    @Resource
    private IMenuContainerHelper menuContainerHelper;
    @Resource
    private IMenuContainerFilterHelper menuFilters;
    @Resource
    private IUnitTreeContextBuilder contextBuilder;
    @Resource
    public IUnitTreeDataSourceID unitTreeDataSourceID;

    @Override
    public List<IMenuItemObject> getContextMenuItems(UnitTreeContextData contextData) {
        return this.getMenuItems(IMenuContainerType.CONTEXT_MENU, contextData);
    }

    @Override
    public List<IMenuItemObject> getFilterMenusItems(UnitTreeContextData contextData) {
        return this.getMenuItems(IMenuContainerType.FILTER_MENU, contextData);
    }

    private List<IMenuItemObject> getMenuItems(IMenuContainerType type, UnitTreeContextData contextData) {
        IUnitTreeContext context = this.contextBuilder.createTreeContext(contextData);
        String menuContainerName = this.unitTreeDataSourceID.getContextMenuSourceID(context);
        List<IMenuContainer> menuContainers = this.menuContainerHelper.getMenuContainers(menuContainerName, type);
        ArrayList<IMenuItemObject> menuItems = new ArrayList<IMenuItemObject>();
        if (!menuContainers.isEmpty()) {
            for (IMenuContainer container : menuContainers) {
                List<IMenuItemObject> displayItems;
                if (container.getContainerType() != type || (displayItems = container.getDisplayItems(context, (IBaseNodeData)contextData.getActionNode())) == null) continue;
                IMenuContainerFilter menuContainerFilter = this.menuFilters.getMenuContainerFilter(container.getContainerId());
                if (menuContainerFilter != null) {
                    menuItems.addAll(menuContainerFilter.doFilterMenus(displayItems, context));
                    continue;
                }
                menuItems.addAll(displayItems);
            }
        }
        return menuItems;
    }
}

