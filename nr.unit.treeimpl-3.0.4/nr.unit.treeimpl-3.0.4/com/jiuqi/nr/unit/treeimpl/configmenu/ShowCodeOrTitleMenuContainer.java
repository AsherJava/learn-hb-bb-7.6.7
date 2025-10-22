/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper
 *  com.jiuiqi.nr.unit.treebase.menu.IMenuContainer
 *  com.jiuiqi.nr.unit.treebase.menu.IMenuContainerType
 *  com.jiuiqi.nr.unit.treebase.menu.IMenuItemObject
 *  com.jiuiqi.nr.unit.treebase.menu.IMenuItemType
 *  com.jiuiqi.nr.unit.treebase.menu.MenuItemObject
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.unit.treecommon.i18n.unittree.UnitTreeI18nHelper
 *  com.jiuqi.nr.unit.treecommon.i18n.unittree.UnitTreeI18nKeys
 *  com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMDisplayScheme
 *  com.jiuqi.nr.unit.treestore.fmdmdisplay.intf.FMDMDisplaySchemeService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.unit.treeimpl.configmenu;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper;
import com.jiuiqi.nr.unit.treebase.menu.IMenuContainer;
import com.jiuiqi.nr.unit.treebase.menu.IMenuContainerType;
import com.jiuiqi.nr.unit.treebase.menu.IMenuItemObject;
import com.jiuiqi.nr.unit.treebase.menu.IMenuItemType;
import com.jiuiqi.nr.unit.treebase.menu.MenuItemObject;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.unit.treecommon.i18n.unittree.UnitTreeI18nHelper;
import com.jiuqi.nr.unit.treecommon.i18n.unittree.UnitTreeI18nKeys;
import com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMDisplayScheme;
import com.jiuqi.nr.unit.treestore.fmdmdisplay.intf.FMDMDisplaySchemeService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class ShowCodeOrTitleMenuContainer
implements IMenuContainer {
    static final String NAME_OF_FIELD = "fieldKey";
    public static final String CONTAINER_ID = "show-code-or-title-menu-container";
    @Resource
    private UnitTreeI18nHelper unitTreeI18nHelper;
    @Resource
    private FMDMDisplaySchemeService displaySchemeService;
    @Resource
    private IEntityMetaService metaService;
    @Resource
    private IUnitTreeContextWrapper contextWrapper;

    public ShowCodeOrTitleMenuContainer(UnitTreeI18nHelper unitTreeI18nHelper) {
        this.unitTreeI18nHelper = unitTreeI18nHelper;
    }

    public String getContainerId() {
        return CONTAINER_ID;
    }

    public String getContainerName() {
        return "unit-tree-entity-row-source";
    }

    public String getContainerTitle() {
        return "\u663e\u793a\u4ee3\u7801\u3001\u663e\u793a\u540d\u79f0\u83dc\u5355";
    }

    public IMenuContainerType getContainerType() {
        return IMenuContainerType.FILTER_MENU;
    }

    public int getOrdinary() {
        return 100;
    }

    public List<IMenuItemObject> getRegisterMenus(IUnitTreeContext context) {
        ArrayList<IMenuItemObject> registerMenus = new ArrayList<IMenuItemObject>();
        IEntityDefine entityDefine = context.getEntityDefine();
        IEntityModel entityModel = this.metaService.getEntityModel(entityDefine.getId());
        FMDMDisplayScheme displayScheme = this.displaySchemeService.getCurrentDisplayScheme(context.getFormScheme().getKey(), entityDefine.getId());
        for (FilterMenu m : FilterMenu.values()) {
            MenuItemObject menu = new MenuItemObject();
            menu.setKey(m.code);
            menu.setCode(m.code);
            menu.setType(IMenuItemType.CHECKED);
            menu.setTitle(this.unitTreeI18nHelper.getMessage(m.unitTreeI18nKeys.key, m.title));
            menu.setIcon(m.icon);
            menu.setData(this.getMenuData(entityModel, m));
            menu.setChecked(Boolean.valueOf(this.isChecked(displayScheme, entityModel, m)));
            registerMenus.add((IMenuItemObject)menu);
        }
        return registerMenus;
    }

    public List<IMenuItemObject> getDisplayItems(IUnitTreeContext context, IBaseNodeData currNode) {
        return this.getRegisterMenus(context);
    }

    private Map<String, Object> getMenuData(IEntityModel entityModel, FilterMenu menuDefine) {
        HashMap<String, Object> data = new HashMap<String, Object>();
        if (FilterMenu.SHOW_CODE == menuDefine) {
            data.put(NAME_OF_FIELD, entityModel.getCodeField().getID());
        } else if (FilterMenu.SHOW_TITLE == menuDefine) {
            data.put(NAME_OF_FIELD, entityModel.getNameField().getID());
        }
        return data;
    }

    private boolean isChecked(FMDMDisplayScheme displayScheme, IEntityModel entityModel, FilterMenu menuDefine) {
        List fields;
        if (displayScheme != null && (fields = displayScheme.getFields()) != null && !fields.isEmpty()) {
            if (FilterMenu.SHOW_CODE == menuDefine) {
                return fields.contains(entityModel.getCodeField().getID());
            }
            if (FilterMenu.SHOW_TITLE == menuDefine) {
                return fields.contains(entityModel.getNameField().getID());
            }
        }
        return FilterMenu.SHOW_TITLE == menuDefine;
    }

    private static enum FilterMenu {
        SHOW_CODE(UnitTreeI18nKeys.SHOW_CODE, "show-code", "\u663e\u793a\u4ee3\u7801", ""),
        SHOW_TITLE(UnitTreeI18nKeys.SHOW_TITLE, "show-title", "\u663e\u793a\u540d\u79f0", "");

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

