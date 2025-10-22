/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper
 *  com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeFilterCondition
 *  com.jiuiqi.nr.unit.treebase.menu.IMenuContainer
 *  com.jiuiqi.nr.unit.treebase.menu.IMenuContainerType
 *  com.jiuiqi.nr.unit.treebase.menu.IMenuItemObject
 *  com.jiuiqi.nr.unit.treebase.menu.MenuItemObject
 *  com.jiuqi.nr.bpm.de.dataflow.bean.WorkFlowTreeState
 *  com.jiuqi.nr.bpm.de.dataflow.tree.util.TreeState
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.unit.treecommon.i18n.unittree.UnitTreeI18nHelper
 *  com.jiuqi.nr.unit.treecommon.i18n.unittree.UnitTreeI18nKeys
 *  javax.annotation.Resource
 *  org.json.JSONObject
 */
package com.jiuqi.nr.unit.treeimpl.configmenu;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper;
import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeFilterCondition;
import com.jiuiqi.nr.unit.treebase.menu.IMenuContainer;
import com.jiuiqi.nr.unit.treebase.menu.IMenuContainerType;
import com.jiuiqi.nr.unit.treebase.menu.IMenuItemObject;
import com.jiuiqi.nr.unit.treebase.menu.MenuItemObject;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkFlowTreeState;
import com.jiuqi.nr.bpm.de.dataflow.tree.util.TreeState;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.unit.treecommon.i18n.unittree.UnitTreeI18nHelper;
import com.jiuqi.nr.unit.treecommon.i18n.unittree.UnitTreeI18nKeys;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class WorkFlowFilterMenuContainer
implements IMenuContainer {
    public static final String CONTAINER_ID = "work-flow-filter-menu-container";
    @Resource
    private UnitTreeI18nHelper unitTreeI18nHelper;
    @Resource
    private TreeState flowWorkState;
    @Resource
    private IUnitTreeContextWrapper contextWrapper;

    public String getContainerId() {
        return CONTAINER_ID;
    }

    public String getContainerName() {
        return "unit-tree-entity-row-source";
    }

    public String getContainerTitle() {
        return "\u6d41\u7a0b\u72b6\u6001\u83dc\u5355";
    }

    public IMenuContainerType getContainerType() {
        return IMenuContainerType.FILTER_MENU;
    }

    public int getOrdinary() {
        return 99;
    }

    public List<IMenuItemObject> getRegisterMenus(IUnitTreeContext context) {
        List workFlowActions;
        ArrayList<IMenuItemObject> registerMenus = new ArrayList<IMenuItemObject>();
        FormSchemeDefine formScheme = context.getFormScheme();
        if (formScheme != null && this.contextWrapper.isOpenWorkFlow(formScheme) && null != (workFlowActions = this.flowWorkState.getWorkFlowActions(formScheme.getKey())) && !workFlowActions.isEmpty()) {
            List<String> filterStatusValue = this.getFilterStatusValue(context);
            MenuItemObject parentMenu = new MenuItemObject();
            parentMenu.setKey(FilterMenu.WORK_FLOW_FILTER.code);
            parentMenu.setCode(FilterMenu.WORK_FLOW_FILTER.code);
            parentMenu.setTitle(this.unitTreeI18nHelper.getMessage(FilterMenu.WORK_FLOW_FILTER.unitTreeI18nKeys.key, FilterMenu.WORK_FLOW_FILTER.title));
            parentMenu.setIcon(FilterMenu.WORK_FLOW_FILTER.icon);
            ArrayList<MenuItemObject> childrenMenus = new ArrayList<MenuItemObject>();
            for (WorkFlowTreeState wfs : workFlowActions) {
                MenuItemObject menu = new MenuItemObject();
                menu.setKey(wfs.getCode());
                menu.setCode(parentMenu.getCode());
                menu.setTitle(wfs.getTitle());
                menu.setChecked(Boolean.valueOf(filterStatusValue.contains(wfs.getCode())));
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("value", wfs.getCode());
                data.put("title", wfs.getTitle());
                data.put("type", "filter-by-workflowstate");
                menu.setData(data);
                childrenMenus.add(menu);
            }
            parentMenu.setChildren(childrenMenus);
            registerMenus.add((IMenuItemObject)parentMenu);
        }
        return registerMenus;
    }

    public List<IMenuItemObject> getDisplayItems(IUnitTreeContext context, IBaseNodeData currNode) {
        return this.getRegisterMenus(context);
    }

    private List<String> getFilterStatusValue(IUnitTreeContext context) {
        UnitTreeFilterCondition filterCondition = UnitTreeFilterCondition.translate2FilterCondition((JSONObject)context.getCustomVariable());
        if (filterCondition != null) {
            return filterCondition.getUpload();
        }
        return new ArrayList<String>();
    }

    private static enum FilterMenu {
        WORK_FLOW_FILTER(UnitTreeI18nKeys.UPLOAD_STATE, "filter-condition", "\u4e0a\u62a5\u72b6\u6001", "");

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

