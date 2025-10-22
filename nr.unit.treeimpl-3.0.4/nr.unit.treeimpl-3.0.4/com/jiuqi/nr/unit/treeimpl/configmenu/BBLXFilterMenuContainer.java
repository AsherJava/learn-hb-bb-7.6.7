/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper
 *  com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeFilterCondition
 *  com.jiuiqi.nr.unit.treebase.entity.query.ICommonEntityDataQuery
 *  com.jiuiqi.nr.unit.treebase.menu.IMenuContainer
 *  com.jiuiqi.nr.unit.treebase.menu.IMenuContainerType
 *  com.jiuiqi.nr.unit.treebase.menu.IMenuItemObject
 *  com.jiuiqi.nr.unit.treebase.menu.MenuItemObject
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityRefer
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
import com.jiuiqi.nr.unit.treebase.entity.query.ICommonEntityDataQuery;
import com.jiuiqi.nr.unit.treebase.menu.IMenuContainer;
import com.jiuiqi.nr.unit.treebase.menu.IMenuContainerType;
import com.jiuiqi.nr.unit.treebase.menu.IMenuItemObject;
import com.jiuiqi.nr.unit.treebase.menu.MenuItemObject;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityRefer;
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
public class BBLXFilterMenuContainer
implements IMenuContainer {
    public static final String CONTAINER_ID = "bblx-filter-menu-container";
    @Resource
    private UnitTreeI18nHelper unitTreeI18nHelper;
    @Resource
    private ICommonEntityDataQuery entityRowQuery;
    @Resource
    private IUnitTreeContextWrapper contextWrapper;

    public String getContainerId() {
        return CONTAINER_ID;
    }

    public String getContainerName() {
        return "unit-tree-entity-row-source";
    }

    public String getContainerTitle() {
        return "\u62a5\u8868\u7c7b\u578b\u83dc\u5355";
    }

    public IMenuContainerType getContainerType() {
        return IMenuContainerType.FILTER_MENU;
    }

    public int getOrdinary() {
        return 97;
    }

    public List<IMenuItemObject> getRegisterMenus(IUnitTreeContext context) {
        IEntityTable bblxTableData;
        ArrayList<IMenuItemObject> registerMenus = new ArrayList<IMenuItemObject>();
        IEntityRefer bblxEntityRefer = this.contextWrapper.getBBLXEntityRefer(context.getEntityDefine());
        if (bblxEntityRefer != null && (bblxTableData = this.entityRowQuery.makeIEntityTable(bblxEntityRefer.getReferEntityId())) != null) {
            List<String> filterBBLXValue = this.getFilterBBLXValue(context);
            List allBBLXRows = bblxTableData.getAllRows();
            if (null != allBBLXRows && !allBBLXRows.isEmpty()) {
                MenuItemObject parentMenu = new MenuItemObject();
                parentMenu.setKey(FilterMenu.BBLX_FILTER.code);
                parentMenu.setCode(FilterMenu.BBLX_FILTER.code);
                parentMenu.setTitle(this.unitTreeI18nHelper.getMessage(FilterMenu.BBLX_FILTER.unitTreeI18nKeys.key, FilterMenu.BBLX_FILTER.title));
                parentMenu.setIcon(FilterMenu.BBLX_FILTER.icon);
                ArrayList<MenuItemObject> childrenMenus = new ArrayList<MenuItemObject>();
                for (IEntityRow row : allBBLXRows) {
                    MenuItemObject menu = new MenuItemObject();
                    menu.setKey(row.getCode());
                    menu.setCode(parentMenu.getCode());
                    menu.setTitle(row.getTitle());
                    menu.setChecked(Boolean.valueOf(filterBBLXValue.contains(row.getCode())));
                    HashMap<String, String> data = new HashMap<String, String>();
                    data.put("value", row.getCode());
                    data.put("title", row.getTitle());
                    data.put("type", "filter-by-bblx");
                    menu.setData(data);
                    childrenMenus.add(menu);
                }
                parentMenu.setChildren(childrenMenus);
                registerMenus.add((IMenuItemObject)parentMenu);
            }
        }
        return registerMenus;
    }

    private List<String> getFilterBBLXValue(IUnitTreeContext context) {
        UnitTreeFilterCondition filterCondition = UnitTreeFilterCondition.translate2FilterCondition((JSONObject)context.getCustomVariable());
        if (filterCondition != null) {
            return filterCondition.getBblx();
        }
        return new ArrayList<String>();
    }

    public List<IMenuItemObject> getDisplayItems(IUnitTreeContext context, IBaseNodeData currNode) {
        return this.getRegisterMenus(context);
    }

    private static enum FilterMenu {
        BBLX_FILTER(UnitTreeI18nKeys.BBLX_STATE, "filter-condition", "\u5355\u4f4d\u7c7b\u578b", "");

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

