/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.i18n.ext.I18NResource
 *  com.jiuqi.np.i18n.ext.I18NResourceItem
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.nr.dataentry.i18n;

import com.jiuqi.np.i18n.ext.I18NResource;
import com.jiuqi.np.i18n.ext.I18NResourceItem;
import com.jiuqi.nr.dataentry.bean.FTemplateConfig;
import com.jiuqi.nr.dataentry.bean.impl.TemplateConfigImpl;
import com.jiuqi.nr.dataentry.dao.TemplateConfigDao;
import com.jiuqi.nr.dataentry.service.IPlugInService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.map.HashedMap;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class I18nButtonsGroup
implements I18NResource {
    private static final long serialVersionUID = 1L;
    @Autowired
    private TemplateConfigDao templateConfigDao;
    @Autowired
    private IPlugInService plugInService;

    public String name() {
        return "\u65b0\u62a5\u8868/\u6570\u636e\u5f55\u5165/\u5de5\u5177\u680f\u6309\u94ae\u5206\u7ec4";
    }

    public String getNameSpace() {
        return "nr";
    }

    public List<I18NResourceItem> getCategory(String parentId) {
        ArrayList<I18NResourceItem> resourceObjects = new ArrayList<I18NResourceItem>();
        if (null == parentId || "".equals(parentId)) {
            Map<String, String> allCategory = this.getAllCategory();
            for (String key : allCategory.keySet()) {
                String categoryName = allCategory.get(key);
                I18NResourceItem i18nResourceItem = new I18NResourceItem(key, categoryName, false);
                resourceObjects.add(i18nResourceItem);
            }
        }
        return resourceObjects;
    }

    public List<I18NResourceItem> getResource(String parentId) {
        ArrayList<I18NResourceItem> resourceObjects = new ArrayList<I18NResourceItem>();
        if (null == parentId || "".equals(parentId)) {
            return resourceObjects;
        }
        Map<String, String> resourItem = this.getTemplateResource(parentId);
        for (String key : resourItem.keySet()) {
            String title = resourItem.get(key);
            resourceObjects.add(new I18NResourceItem(key, title));
        }
        return resourceObjects;
    }

    private Map<String, String> getAllCategory() {
        HashedMap<String, String> categoryNames = new HashedMap<String, String>();
        List<TemplateConfigImpl> allTemplates = this.getAllTemplates();
        for (TemplateConfigImpl template : allTemplates) {
            categoryNames.put(template.getCode(), template.getTitle());
        }
        return categoryNames;
    }

    private Map<String, String> getTemplateResource(String parentId) {
        HashedMap<String, String> resourItem = new HashedMap<String, String>();
        FTemplateConfig template = null;
        template = parentId.equals("default") ? this.getDefaultTemplate() : this.templateConfigDao.getTemplateConfigByCode(parentId);
        if (template != null) {
            String templateConfig = template.getTemplateConfig();
            JSONObject jsonObject = new JSONObject(templateConfig);
            JSONObject toolBarView = jsonObject.getJSONObject("toolBarView");
            JSONArray chooseButtons = toolBarView.getJSONArray("chooseButtons");
            for (int i = 0; i < chooseButtons.length(); ++i) {
                JSONObject button = chooseButtons.getJSONObject(i);
                String actionType = button.getString("actionType");
                String code = button.getString("code");
                String title = button.getString("title");
                if (!actionType.equals("GROUP") || !code.startsWith("grp")) continue;
                resourItem.put(code, title);
            }
        }
        return resourItem;
    }

    private List<TemplateConfigImpl> getAllTemplates() {
        ArrayList<TemplateConfigImpl> templates = new ArrayList<TemplateConfigImpl>();
        List<TemplateConfigImpl> allTemplate = this.templateConfigDao.getAllTemplateConfig();
        TemplateConfigImpl defaultTemplate = this.getDefaultTemplate();
        templates.add(defaultTemplate);
        templates.addAll(allTemplate);
        return templates;
    }

    private TemplateConfigImpl getDefaultTemplate() {
        TemplateConfigImpl templateConfig = new TemplateConfigImpl();
        String content = this.plugInService.getDefaultTemplate();
        templateConfig.setCode("default");
        templateConfig.setTitle("\u9ed8\u8ba4\u6a21\u677f");
        templateConfig.setTemplateConfig(content);
        return templateConfig;
    }
}

