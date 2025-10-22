/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper
 *  com.jiuiqi.nr.unit.treebase.menu.IMenuContainer
 *  com.jiuiqi.nr.unit.treebase.menu.IMenuContainerType
 *  com.jiuiqi.nr.unit.treebase.menu.IMenuItemObject
 *  com.jiuiqi.nr.unit.treebase.menu.MenuItemObject
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.unit.treecommon.i18n.unittree.UnitTreeI18nHelper
 *  com.jiuqi.nr.unit.treecommon.i18n.unittree.UnitTreeI18nKeys
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.unit.treeimpl.contextmenu;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper;
import com.jiuiqi.nr.unit.treebase.menu.IMenuContainer;
import com.jiuiqi.nr.unit.treebase.menu.IMenuContainerType;
import com.jiuiqi.nr.unit.treebase.menu.IMenuItemObject;
import com.jiuiqi.nr.unit.treebase.menu.MenuItemObject;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.unit.treecommon.i18n.unittree.UnitTreeI18nHelper;
import com.jiuqi.nr.unit.treecommon.i18n.unittree.UnitTreeI18nKeys;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class ExpandAllLevelMenuContainer
implements IMenuContainer {
    public static final String CONTAINER_ID = "expand-all-level-menu-container";
    @Resource
    private UnitTreeI18nHelper unitTreeI18nHelper;
    @Resource
    private IUnitTreeContextWrapper contextWrapper;

    public String getContainerId() {
        return CONTAINER_ID;
    }

    public String getContainerName() {
        return "unit-tree-entity-row-source";
    }

    public String getContainerTitle() {
        return "\u5c55\u5f00\u6240\u6709\u5c42\u7ea7";
    }

    public IMenuContainerType getContainerType() {
        return IMenuContainerType.CONTEXT_MENU;
    }

    public int getOrdinary() {
        return -1;
    }

    public List<IMenuItemObject> getRegisterMenus(IUnitTreeContext context) {
        ArrayList<IMenuItemObject> registerMenus = new ArrayList<IMenuItemObject>();
        for (MenuConst m : MenuConst.values()) {
            MenuItemObject menuItemObject = new MenuItemObject();
            menuItemObject.setCode(m.code);
            menuItemObject.setTitle(this.unitTreeI18nHelper.getMessage(m.code, m.title));
            menuItemObject.setIcon(m.icon);
            registerMenus.add((IMenuItemObject)menuItemObject);
        }
        return registerMenus;
    }

    public List<IMenuItemObject> getDisplayItems(IUnitTreeContext context, IBaseNodeData currNode) {
        ArrayList<IMenuItemObject> displayMenus = new ArrayList<IMenuItemObject>();
        List<IMenuItemObject> registerMenus = this.getRegisterMenus(context);
        if (this.contextWrapper.isTreeExpandAllLevel(context)) {
            displayMenus.add(this.findMenuItem(registerMenus, MenuConst.TREE_EXPAND_ALL_LEVEL));
        }
        return displayMenus;
    }

    private IMenuItemObject findMenuItem(List<IMenuItemObject> registerMenus, MenuConst menuConst) {
        for (IMenuItemObject m : registerMenus) {
            if (!m.getCode().equals(menuConst.code)) continue;
            return m;
        }
        return null;
    }

    private static enum MenuConst {
        TREE_EXPAND_ALL_LEVEL(UnitTreeI18nKeys.TREE_EXPAND_ALL_LEVEL.key, UnitTreeI18nKeys.TREE_EXPAND_ALL_LEVEL.title, "#icon-_Txingxing");

        public final String code;
        public final String title;
        public final String icon;

        private MenuConst(String code, String title, String icon) {
            this.code = code;
            this.title = title;
            this.icon = icon;
        }
    }
}

