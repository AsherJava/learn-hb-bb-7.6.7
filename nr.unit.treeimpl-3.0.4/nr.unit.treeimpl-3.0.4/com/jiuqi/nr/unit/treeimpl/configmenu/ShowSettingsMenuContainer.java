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
package com.jiuqi.nr.unit.treeimpl.configmenu;

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
public class ShowSettingsMenuContainer
implements IMenuContainer {
    public static final String CONTAINER_ID = "show-settings-menu-container";
    @Resource
    private UnitTreeI18nHelper unitTreeI18nHelper;

    public String getContainerId() {
        return CONTAINER_ID;
    }

    public String getContainerName() {
        return "unit-tree-entity-row-source";
    }

    public String getContainerTitle() {
        return "\u5355\u4f4d\u6811\u5168\u5c40\u8bbe\u7f6e\u6309\u94ae";
    }

    public IMenuContainerType getContainerType() {
        return IMenuContainerType.FILTER_MENU;
    }

    public int getOrdinary() {
        return 90;
    }

    public List<IMenuItemObject> getRegisterMenus(IUnitTreeContext context) {
        ArrayList<IMenuItemObject> registerMenus = new ArrayList<IMenuItemObject>();
        for (FilterMenu m : FilterMenu.values()) {
            MenuItemObject menu = new MenuItemObject();
            menu.setKey(m.code);
            menu.setCode(m.code);
            menu.setTitle(this.unitTreeI18nHelper.getMessage(m.unitTreeI18nKeys.key, m.title));
            menu.setIcon(m.icon);
            registerMenus.add((IMenuItemObject)menu);
        }
        return registerMenus;
    }

    public List<IMenuItemObject> getDisplayItems(IUnitTreeContext context, IBaseNodeData currNode) {
        return this.getRegisterMenus(context);
    }

    private static enum FilterMenu {
        UNIT_SELECTOR_FILTER(UnitTreeI18nKeys.TRANSFER_STATE, "filter-unitselector", "\u5355\u4f4d\u7b5b\u9009", ""),
        SHOW_TAG_MANAGER(UnitTreeI18nKeys.SHOW_TAG_MANAGER, "show-tag-manager", "\u7ba1\u7406\u6807\u8bb0", ""),
        DISPLAY_FIELDS_SETTING(UnitTreeI18nKeys.DISPLAY_FIELDS_SETTING, "display-fields-setting", "\u663e\u793a\u5b57\u6bb5\u8bbe\u7f6e", "");

        public UnitTreeI18nKeys unitTreeI18nKeys;
        public String code;
        public String title;
        public String icon;

        private FilterMenu(UnitTreeI18nKeys unitTreeI18nKeys, String code, String title, String icon) {
            this.unitTreeI18nKeys = unitTreeI18nKeys;
            this.code = code;
            this.title = title;
            this.icon = icon;
        }
    }
}

