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
public class TagContextMenuContainer
implements IMenuContainer {
    public static final String CONTAINER_ID = "tag-context-menu-container";
    @Resource
    private UnitTreeI18nHelper unitTreeI18nHelper;

    public String getContainerId() {
        return CONTAINER_ID;
    }

    public String getContainerName() {
        return "unit-tree-entity-row-source";
    }

    public String getContainerTitle() {
        return "\u5355\u4f4d\u6811\u6807\u8bb0\u8282\u70b9\u3001\u663e\u793a\u6807\u8bb0";
    }

    public IMenuContainerType getContainerType() {
        return IMenuContainerType.CONTEXT_MENU;
    }

    public int getOrdinary() {
        return 99;
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
        return this.getRegisterMenus(context);
    }

    private static enum MenuConst {
        TAG_NODE(UnitTreeI18nKeys.TAG_NODE.key, UnitTreeI18nKeys.TAG_NODE.title, "#icon-_Txingxing"),
        SHOW_NODE_TAGS(UnitTreeI18nKeys.SHOW_NODE_TAGS.key, UnitTreeI18nKeys.SHOW_NODE_TAGS.title, "#icon-_Txingxing");

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

