/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeFilterCondition
 *  com.jiuiqi.nr.unit.treebase.menu.IMenuContainer
 *  com.jiuiqi.nr.unit.treebase.menu.IMenuContainerType
 *  com.jiuiqi.nr.unit.treebase.menu.IMenuItemObject
 *  com.jiuiqi.nr.unit.treebase.menu.MenuItemObject
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.tag.management.environment.TagQueryContextData
 *  com.jiuqi.nr.tag.management.intf.ITagFacade
 *  com.jiuqi.nr.tag.management.intf.ITagQueryContext
 *  com.jiuqi.nr.tag.management.service.ITagQueryTemplateHelper
 *  com.jiuqi.nr.unit.treecommon.i18n.unittree.UnitTreeI18nHelper
 *  com.jiuqi.nr.unit.treecommon.i18n.unittree.UnitTreeI18nKeys
 *  javax.annotation.Resource
 *  org.json.JSONObject
 */
package com.jiuqi.nr.unit.treeimpl.configmenu;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeFilterCondition;
import com.jiuiqi.nr.unit.treebase.menu.IMenuContainer;
import com.jiuiqi.nr.unit.treebase.menu.IMenuContainerType;
import com.jiuiqi.nr.unit.treebase.menu.IMenuItemObject;
import com.jiuiqi.nr.unit.treebase.menu.MenuItemObject;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.tag.management.environment.TagQueryContextData;
import com.jiuqi.nr.tag.management.intf.ITagFacade;
import com.jiuqi.nr.tag.management.intf.ITagQueryContext;
import com.jiuqi.nr.tag.management.service.ITagQueryTemplateHelper;
import com.jiuqi.nr.unit.treecommon.i18n.unittree.UnitTreeI18nHelper;
import com.jiuqi.nr.unit.treecommon.i18n.unittree.UnitTreeI18nKeys;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class TagsFilterMenuContainer
implements IMenuContainer {
    public static final String CONTAINER_ID = "tags-filter-menu-container";
    @Resource
    private ITagQueryTemplateHelper iTagQueryTemplateHelper;
    @Resource
    private UnitTreeI18nHelper unitTreeI18nHelper;

    public String getContainerId() {
        return CONTAINER_ID;
    }

    public String getContainerName() {
        return "unit-tree-entity-row-source";
    }

    public String getContainerTitle() {
        return "\u6807\u8bb0\u7b5b\u9009\u8fc7\u6ee4\u83dc\u5355";
    }

    public IMenuContainerType getContainerType() {
        return IMenuContainerType.FILTER_MENU;
    }

    public int getOrdinary() {
        return 95;
    }

    public List<IMenuItemObject> getRegisterMenus(IUnitTreeContext context) {
        ArrayList<IMenuItemObject> registerMenus = new ArrayList<IMenuItemObject>();
        TagQueryContextData tagQueryContextData = new TagQueryContextData();
        tagQueryContextData.setFormScheme(context.getFormScheme().getKey());
        tagQueryContextData.setPeriod(context.getPeriod());
        tagQueryContextData.setEntityId(context.getEntityDefine().getId());
        tagQueryContextData.setDimValueSet(context.getDimValueSet());
        tagQueryContextData.setCustomVariable(context.getCustomVariable());
        List tags = this.iTagQueryTemplateHelper.getQueryTemplate().getInfoTags((ITagQueryContext)tagQueryContextData);
        if (null != tags && !tags.isEmpty()) {
            List<String> filterTagValue = this.getFilterTagValue(context);
            MenuItemObject parentMenu = new MenuItemObject();
            parentMenu.setKey(FilterMenu.TAGS_FILTER.code);
            parentMenu.setCode(FilterMenu.TAGS_FILTER.code);
            parentMenu.setTitle(this.unitTreeI18nHelper.getMessage(FilterMenu.TAGS_FILTER.unitTreeI18nKeys.key, FilterMenu.TAGS_FILTER.title));
            parentMenu.setIcon(FilterMenu.TAGS_FILTER.icon);
            ArrayList<MenuItemObject> childrenMenus = new ArrayList<MenuItemObject>();
            for (ITagFacade tag : tags) {
                MenuItemObject menu = new MenuItemObject();
                menu.setKey(tag.getKey());
                menu.setCode(parentMenu.getCode());
                menu.setTitle(tag.getTitle());
                menu.setChecked(Boolean.valueOf(filterTagValue.contains(tag.getKey())));
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("value", tag.getKey());
                data.put("title", tag.getTitle());
                data.put("type", "filter-by-tags");
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

    private List<String> getFilterTagValue(IUnitTreeContext context) {
        UnitTreeFilterCondition filterCondition = UnitTreeFilterCondition.translate2FilterCondition((JSONObject)context.getCustomVariable());
        if (filterCondition != null) {
            return filterCondition.getTags();
        }
        return new ArrayList<String>();
    }

    private static enum FilterMenu {
        TAGS_FILTER(UnitTreeI18nKeys.TAG_STATE, "filter-condition", "\u6807\u8bb0\u7b5b\u9009", "");

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

