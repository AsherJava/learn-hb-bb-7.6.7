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
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.unit.treecommon.i18n.unittree.UnitTreeI18nHelper
 *  com.jiuqi.nr.unit.treecommon.i18n.unittree.UnitTreeI18nKeys
 *  com.jiuqi.nr.unit.treestore.systemconfig.UnitTreeSystemConfig
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.unit.treeimpl.contextmenu;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper;
import com.jiuiqi.nr.unit.treebase.menu.IMenuContainer;
import com.jiuiqi.nr.unit.treebase.menu.IMenuContainerType;
import com.jiuiqi.nr.unit.treebase.menu.IMenuItemObject;
import com.jiuiqi.nr.unit.treebase.menu.MenuItemObject;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.unit.treecommon.i18n.unittree.UnitTreeI18nHelper;
import com.jiuqi.nr.unit.treecommon.i18n.unittree.UnitTreeI18nKeys;
import com.jiuqi.nr.unit.treestore.systemconfig.UnitTreeSystemConfig;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class ReminderContextMenuContainer
implements IMenuContainer {
    public static final String CONTAINER_ID = "reminder-context-menu-container";
    @Resource
    private UnitTreeI18nHelper unitTreeI18nHelper;
    @Resource
    private IUnitTreeContextWrapper contextWrapper;
    @Resource
    private UnitTreeSystemConfig unitTreeSystemConfig;

    public String getContainerId() {
        return CONTAINER_ID;
    }

    public String getContainerName() {
        return "unit-tree-entity-row-source";
    }

    public String getContainerTitle() {
        return "\u5355\u4f4d\u6811\u50ac\u62a5\u3001\u586b\u62a5\u65f6\u95f4\u8bbe\u7f6e";
    }

    public IMenuContainerType getContainerType() {
        return IMenuContainerType.CONTEXT_MENU;
    }

    public int getOrdinary() {
        return 96;
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
        boolean showTimeDeadline;
        ArrayList<IMenuItemObject> displayMenus = new ArrayList<IMenuItemObject>();
        List<IMenuItemObject> registerMenus = this.getRegisterMenus(context);
        FormSchemeDefine formScheme = context.getFormScheme();
        if (this.unitTreeSystemConfig.isShowReminder() && this.contextWrapper.isOpenWorkFlow(context.getFormScheme())) {
            displayMenus.add(this.findMenuItem(registerMenus, MenuConst.URGE_TO_REPORT));
        }
        if ((showTimeDeadline = this.contextWrapper.isShowTimeDeadline(formScheme)) && this.contextWrapper.hasUnitAuditOperation(context.getEntityDefine().getId(), currNode.getKey(), context.getPeriodEntity().getKey(), context.getPeriod())) {
            displayMenus.add(this.findMenuItem(registerMenus, MenuConst.SUBMISSION_TIME_SETTING));
        }
        if (showTimeDeadline) {
            displayMenus.add(this.findMenuItem(registerMenus, MenuConst.SUBMISSION_TIME_SHOW));
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
        URGE_TO_REPORT(UnitTreeI18nKeys.URGE_TO_REPORT.key, UnitTreeI18nKeys.URGE_TO_REPORT.title, "#icon-_GJZpiliangdaochu"),
        SUBMISSION_TIME_SETTING(UnitTreeI18nKeys.SUBMISSION_TIME_SETTING.key, UnitTreeI18nKeys.SUBMISSION_TIME_SETTING.title, "#icon-_GJZzidingyihuizong"),
        SUBMISSION_TIME_SHOW(UnitTreeI18nKeys.SUBMISSION_TIME_SHOW.key, UnitTreeI18nKeys.SUBMISSION_TIME_SHOW.title, "#icon-_GJZpiliangdaochu");

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

