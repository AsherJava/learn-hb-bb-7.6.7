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
 *  com.jiuiqi.nr.unit.treebase.node.state.TerminalStateManagement
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.state.common.StateConst
 *  com.jiuqi.nr.unit.treecommon.i18n.unittree.UnitTreeI18nHelper
 *  com.jiuqi.nr.unit.treecommon.i18n.unittree.UnitTreeI18nKeys
 *  com.jiuqi.nr.unit.treecommon.utils.NRSystemUtils
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.unit.treeimpl.contextmenu;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper;
import com.jiuiqi.nr.unit.treebase.menu.IMenuContainer;
import com.jiuiqi.nr.unit.treebase.menu.IMenuContainerType;
import com.jiuiqi.nr.unit.treebase.menu.IMenuItemObject;
import com.jiuiqi.nr.unit.treebase.menu.MenuItemObject;
import com.jiuiqi.nr.unit.treebase.node.state.TerminalStateManagement;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.state.common.StateConst;
import com.jiuqi.nr.unit.treecommon.i18n.unittree.UnitTreeI18nHelper;
import com.jiuqi.nr.unit.treecommon.i18n.unittree.UnitTreeI18nKeys;
import com.jiuqi.nr.unit.treecommon.utils.NRSystemUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class TerminalContextMenuContainer
implements IMenuContainer {
    public static final String CONTAINER_ID = "terminal-context-menu-container";
    @Resource
    private UnitTreeI18nHelper unitTreeI18nHelper;
    @Resource
    private IUnitTreeContextWrapper contextWrapper;
    @Resource
    private RoleService roleService;
    @Resource
    private TerminalStateManagement terminalStateMgr;

    public String getContainerId() {
        return CONTAINER_ID;
    }

    public String getContainerName() {
        return "unit-tree-entity-row-source";
    }

    public String getContainerTitle() {
        return "\u5355\u4f4d\u6811\u7ec8\u6b62\u586b\u62a5\u8bbe\u7f6e";
    }

    public IMenuContainerType getContainerType() {
        return IMenuContainerType.CONTEXT_MENU;
    }

    public int getOrdinary() {
        return 98;
    }

    public List<IMenuItemObject> getRegisterMenus(IUnitTreeContext context) {
        ArrayList<IMenuItemObject> registerMenus = new ArrayList<IMenuItemObject>();
        MenuItemObject menuItemObject = new MenuItemObject();
        menuItemObject.setCode(MenuConst.SHOW_TERMINAL_STATE.code);
        menuItemObject.setTitle(this.unitTreeI18nHelper.getMessage(MenuConst.SHOW_TERMINAL_STATE.code, MenuConst.SHOW_TERMINAL_STATE.title));
        menuItemObject.setIcon(MenuConst.SHOW_TERMINAL_STATE.icon);
        registerMenus.add((IMenuItemObject)menuItemObject);
        return registerMenus;
    }

    public List<IMenuItemObject> getDisplayItems(IUnitTreeContext context, IBaseNodeData currNode) {
        ArrayList<IMenuItemObject> displayMenus = new ArrayList<IMenuItemObject>();
        if (this.canOperateTerminal(context)) {
            List<IMenuItemObject> registerMenus = this.getRegisterMenus(context);
            IMenuItemObject terMenu = registerMenus.get(0);
            this.changeMenuTitle(context, (MenuItemObject)terMenu, currNode.getKey());
            displayMenus.addAll(registerMenus);
        }
        return displayMenus;
    }

    private void changeMenuTitle(IUnitTreeContext context, MenuItemObject terMenu, String currentNodeKey) {
        if (StringUtils.isNotEmpty((String)currentNodeKey)) {
            StateConst stateConst = this.terminalStateMgr.getTerminalState(context, currentNodeKey);
            StateConst stateConst2 = stateConst = stateConst == null ? StateConst.STARTFILL : stateConst;
            if (stateConst.getValue() == StateConst.STARTFILL.getValue()) {
                terMenu.setTitle("\u7ec8\u6b62\u586b\u62a5");
            } else if (stateConst.getValue() == StateConst.ENDFILL.getValue()) {
                terMenu.setTitle("\u5f00\u542f\u586b\u62a5");
            }
        }
    }

    private boolean canOperateTerminal(IUnitTreeContext unitTreeContext) {
        boolean canOperate = this.contextWrapper.isOpenTerminal(unitTreeContext.getFormScheme());
        if (canOperate && !(canOperate = NRSystemUtils.isSystemIdentity((String)NRSystemUtils.getCurrentUserId()))) {
            Set userBelongRoles = this.roleService.getIdByIdentity(NRSystemUtils.getCurrentUserId());
            String roleId = this.contextWrapper.openTerminalRole(unitTreeContext.getTaskDefine());
            canOperate = null != userBelongRoles && userBelongRoles.contains(roleId);
        }
        return canOperate;
    }

    private static enum MenuConst {
        SHOW_TERMINAL_STATE(UnitTreeI18nKeys.SHOW_TERMINAL_STATE.key, UnitTreeI18nKeys.SHOW_TERMINAL_STATE.title, "#icon-_Wjichengdejujuequanxian");

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

