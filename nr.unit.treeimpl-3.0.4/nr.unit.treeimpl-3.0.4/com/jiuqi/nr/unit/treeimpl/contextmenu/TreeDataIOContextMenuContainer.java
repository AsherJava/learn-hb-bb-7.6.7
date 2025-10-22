/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
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
public class TreeDataIOContextMenuContainer
implements IMenuContainer {
    public static final String CONTAINER_ID = "tree-data-io-context-menu-container";
    @Resource
    private UnitTreeI18nHelper unitTreeI18nHelper;

    public String getContainerId() {
        return CONTAINER_ID;
    }

    public String getContainerName() {
        return "unit-tree-entity-row-source";
    }

    public String getContainerTitle() {
        return "\u5355\u4f4d\u6811\u5bfc\u51fa\u7ea7\u6b21\u6811";
    }

    public IMenuContainerType getContainerType() {
        return IMenuContainerType.CONTEXT_MENU;
    }

    public int getOrdinary() {
        return 97;
    }

    public List<IMenuItemObject> getRegisterMenus(IUnitTreeContext context) {
        ArrayList<IMenuItemObject> registerMenus = new ArrayList<IMenuItemObject>();
        MenuItemObject menuItemObject = new MenuItemObject();
        menuItemObject.setCode(MenuConst.EXPORT_LEVEL_TREE.code);
        menuItemObject.setTitle(this.unitTreeI18nHelper.getMessage(MenuConst.EXPORT_LEVEL_TREE.code, MenuConst.EXPORT_LEVEL_TREE.title));
        menuItemObject.setIcon(MenuConst.EXPORT_LEVEL_TREE.icon);
        registerMenus.add((IMenuItemObject)menuItemObject);
        return registerMenus;
    }

    public List<IMenuItemObject> getDisplayItems(IUnitTreeContext context, IBaseNodeData currNode) {
        return this.getRegisterMenus(context);
    }

    private static enum MenuConst {
        EXPORT_LEVEL_TREE(UnitTreeI18nKeys.EXPORT_LEVEL_TREE.key, UnitTreeI18nKeys.EXPORT_LEVEL_TREE.title, "#icon-_GJZpiliangdaochu");

        public String code;
        public String title;
        public String icon;

        private MenuConst(String code, String title, String icon) {
            this.code = code;
            this.title = title;
            this.icon = icon;
        }
    }
}

