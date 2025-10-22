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
 *  com.jiuqi.nr.definition.facade.TaskDefine
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
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.unit.treecommon.i18n.unittree.UnitTreeI18nHelper;
import com.jiuqi.nr.unit.treecommon.i18n.unittree.UnitTreeI18nKeys;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class MainDimensionMenuContainer
implements IMenuContainer {
    public static final String CONTAINER_ID = "main-dimension-menu-container";
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
        return "\u5355\u4f4d\u6811\u65b0\u589e/\u5220\u9664\u5355\u4f4d\uff0c\u53f3\u952e\u83dc\u5355\u6309\u94ae\u63a7\uff08\u5c01\u9762\u4ee3\u7801\u63a7\u5236\uff09";
    }

    public IMenuContainerType getContainerType() {
        return IMenuContainerType.CONTEXT_MENU;
    }

    public int getOrdinary() {
        return 100;
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
        TaskDefine taskDefine = context.getTaskDefine();
        if (taskDefine != null) {
            List<IMenuItemObject> registerMenus = this.getRegisterMenus(context);
            if (this.contextWrapper.canAddDimension(taskDefine)) {
                displayMenus.add(this.findMenuItem(registerMenus, MenuConst.ADD_MAIN_DIMENSION));
            }
            if (this.contextWrapper.canAddDimension(taskDefine) && this.contextWrapper.hasUnitEditOperation(context.getEntityDefine().getId(), currNode.getKey(), context.getPeriodEntity().getKey(), context.getPeriod())) {
                displayMenus.add(this.findMenuItem(registerMenus, MenuConst.DELETE_MAIN_DIMENSION));
            }
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
        ADD_MAIN_DIMENSION(UnitTreeI18nKeys.ADD.key, UnitTreeI18nKeys.ADD.title, "#icon-_Ttianjia"),
        DELETE_MAIN_DIMENSION(UnitTreeI18nKeys.DELETE.key, UnitTreeI18nKeys.DELETE.title, "#icon-_Tshanchu");

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

