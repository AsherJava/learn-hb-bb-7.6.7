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
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.unit.uselector.contextmenu;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper;
import com.jiuiqi.nr.unit.treebase.menu.IMenuContainer;
import com.jiuiqi.nr.unit.treebase.menu.IMenuContainerType;
import com.jiuiqi.nr.unit.treebase.menu.IMenuItemObject;
import com.jiuiqi.nr.unit.treebase.menu.MenuItemObject;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.unit.uselector.checker.CheckerGroup;
import com.jiuqi.nr.unit.uselector.checker.IRowChecker;
import com.jiuqi.nr.unit.uselector.checker.IRowCheckerHelper;
import com.jiuqi.nr.unit.uselector.source.IUSelectorDataSource;
import com.jiuqi.nr.unit.uselector.source.IUSelectorDataSourceHelper;
import com.jiuqi.nr.unit.uselector.source.IUSelectorEntityRowProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class USelectorContextMenuContainer
implements IMenuContainer {
    public static final String CONTAINER_ID = "uselector-context-menu-container";
    @Resource
    private IRowCheckerHelper checkerHelper;
    @Resource
    private IUSelectorDataSourceHelper dataSourceHelper;
    @Resource
    private IUnitTreeContextWrapper contextWrapper;

    public String getContainerId() {
        return CONTAINER_ID;
    }

    public String getContainerName() {
        return "unit-selector-tree-data-source";
    }

    public String getContainerTitle() {
        return "\u5355\u4f4d\u9009\u62e9\u5668\u53f3\u952e\u83dc\u5355";
    }

    public IMenuContainerType getContainerType() {
        return IMenuContainerType.CONTEXT_MENU;
    }

    public int getOrdinary() {
        return 0;
    }

    public List<IMenuItemObject> getRegisterMenus(IUnitTreeContext context) {
        MenuItemObject item;
        List<IRowChecker> checkers = this.checkerHelper.getCheckersByGroup(CheckerGroup.CONTEXT_MENU);
        ArrayList<IMenuItemObject> registerMenus = new ArrayList<IMenuItemObject>();
        if (null != checkers) {
            for (IRowChecker checker : checkers) {
                item = new MenuItemObject();
                item.setCode(checker.getKeyword());
                item.setTitle(checker.getShowText());
                item.setData(new HashMap());
                item.getData().put("checkValues", checker.getExecutor(context).getValues());
                registerMenus.add((IMenuItemObject)item);
            }
        }
        for (MenuConst m : MenuConst.values()) {
            item = new MenuItemObject();
            item.setCode(m.code);
            item.setTitle(m.title);
            item.setIcon(m.icon);
            registerMenus.add((IMenuItemObject)item);
        }
        return registerMenus;
    }

    public List<IMenuItemObject> getDisplayItems(IUnitTreeContext context, IBaseNodeData currNode) {
        IUSelectorDataSource baseTreeDataSource;
        IUSelectorEntityRowProvider rowProvider;
        IEntityRow entityRow;
        ArrayList<String> listKeywords = new ArrayList<String>();
        if (currNode != null && (entityRow = (rowProvider = (baseTreeDataSource = this.dataSourceHelper.getBaseTreeDataSource(context.getDataSourceId())).getUSelectorEntityRowProvider(context)).findEntityRow(currNode.getKey())) != null) {
            if (rowProvider.hasParent(currNode.getKey())) {
                listKeywords.add("#check-direct-parent");
                listKeywords.add("#check-all-parents");
            }
            if (!rowProvider.isLeaf(currNode.getKey())) {
                listKeywords.add("#check-lower-leaves");
                listKeywords.add("#check-lower-non-leaves");
                listKeywords.add(MenuConst.LEVEL_EXPAND.code);
            }
        }
        if (this.contextWrapper.isTreeExpandAllLevel(context)) {
            listKeywords.add(MenuConst.LEVEL_ALL_EXPAND.code);
        }
        return this.getRegisterMenus(context).stream().filter(m -> listKeywords.contains(m.getCode())).collect(Collectors.toList());
    }

    private static enum MenuConst {
        LEVEL_EXPAND("#expand-level", "\u5c55\u5f00\u6307\u5b9a\u5c42\u7ea7", ""),
        LEVEL_ALL_EXPAND("#expand-all-level", "\u5c55\u5f00\u6240\u6709\u5c42\u7ea7", "");

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

