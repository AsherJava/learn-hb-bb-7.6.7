/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.i18n.domain.VaI18nResourceItem
 *  com.jiuqi.va.i18n.intf.VaI18nModuleIntf
 *  com.jiuqi.va.query.template.enumerate.PluginEnum
 *  com.jiuqi.va.query.template.plugin.DataSourcePlugin
 *  com.jiuqi.va.query.template.plugin.QueryFieldsPlugin
 *  com.jiuqi.va.query.template.plugin.ToolBarPlugin
 *  com.jiuqi.va.query.template.vo.QueryTemplate
 *  com.jiuqi.va.query.template.vo.TemplateFieldSettingVO
 *  com.jiuqi.va.query.template.vo.TemplateInfoVO
 *  com.jiuqi.va.query.template.vo.TemplateParamsVO
 *  com.jiuqi.va.query.template.vo.TemplateToolbarInfoVO
 *  com.jiuqi.va.query.tree.vo.MenuTreeVO
 */
package com.jiuqi.va.query.i18n;

import com.jiuqi.va.i18n.domain.VaI18nResourceItem;
import com.jiuqi.va.i18n.intf.VaI18nModuleIntf;
import com.jiuqi.va.query.template.dao.TemplateInfoDao;
import com.jiuqi.va.query.template.enumerate.PluginEnum;
import com.jiuqi.va.query.template.plugin.DataSourcePlugin;
import com.jiuqi.va.query.template.plugin.QueryFieldsPlugin;
import com.jiuqi.va.query.template.plugin.ToolBarPlugin;
import com.jiuqi.va.query.template.service.TemplateDesignService;
import com.jiuqi.va.query.template.vo.QueryTemplate;
import com.jiuqi.va.query.template.vo.TemplateFieldSettingVO;
import com.jiuqi.va.query.template.vo.TemplateInfoVO;
import com.jiuqi.va.query.template.vo.TemplateParamsVO;
import com.jiuqi.va.query.template.vo.TemplateToolbarInfoVO;
import com.jiuqi.va.query.tree.service.MenuTreeService;
import com.jiuqi.va.query.tree.vo.MenuTreeVO;
import com.jiuqi.va.query.util.DCQuerySpringContextUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Component
public class VaQueryI18nModule
extends VaI18nModuleIntf {
    private static final long serialVersionUID = 1L;

    public String getGroupName() {
        return "VA";
    }

    public String getName() {
        return "VaQuery";
    }

    public String getTitle() {
        return "\u81ea\u5b9a\u4e49\u67e5\u8be2";
    }

    public List<VaI18nResourceItem> getCategory(String parentId) {
        if (!StringUtils.hasText(parentId)) {
            return null;
        }
        String[] nodes = parentId.split("#");
        if (nodes.length < 2) {
            return null;
        }
        MenuTreeService menuTreeService = DCQuerySpringContextUtils.getBean(MenuTreeService.class);
        List<MenuTreeVO> menuTreeVOS = menuTreeService.getTree();
        ArrayList<VaI18nResourceItem> resourceList = new ArrayList<VaI18nResourceItem>();
        if (parentId.endsWith("#VaQuery")) {
            for (MenuTreeVO menuTreeVO : menuTreeVOS) {
                VaI18nResourceItem defineTitleItem = new VaI18nResourceItem();
                defineTitleItem.setName(menuTreeVO.getCode());
                defineTitleItem.setTitle(menuTreeVO.getTitle());
                defineTitleItem.setGroupFlag(true);
                defineTitleItem.setCategoryFlag(true);
                resourceList.add(defineTitleItem);
            }
            return resourceList;
        }
        String parents = String.join((CharSequence)"/", Arrays.copyOfRange(nodes, 2, nodes.length));
        this.findGroup(menuTreeVOS, resourceList, parents);
        return resourceList;
    }

    public void findGroup(List<MenuTreeVO> menuTreeVOS, List<VaI18nResourceItem> result, String parents) {
        for (MenuTreeVO item : menuTreeVOS) {
            String itemParents = item.getParents();
            if ("group".equals(item.getNodeType()) && parents.equals(itemParents) && item.getChildren() != null && !item.getChildren().isEmpty()) {
                for (MenuTreeVO child : item.getChildren()) {
                    VaI18nResourceItem defineTitleItem = new VaI18nResourceItem();
                    defineTitleItem.setName(child.getCode());
                    defineTitleItem.setTitle(child.getTitle());
                    defineTitleItem.setGroupFlag(true);
                    defineTitleItem.setCategoryFlag(true);
                    result.add(defineTitleItem);
                }
                continue;
            }
            if ("query".equals(item.getNodeType()) && parents.equals(itemParents) && item.getChildren() == null) {
                VaI18nResourceItem toolsColsItem = new VaI18nResourceItem();
                toolsColsItem.setName("toolbars");
                toolsColsItem.setTitle("\u5de5\u5177\u680f");
                toolsColsItem.setCategoryFlag(true);
                result.add(toolsColsItem);
                VaI18nResourceItem showColsItem = new VaI18nResourceItem();
                showColsItem.setName("showcol");
                showColsItem.setTitle("\u663e\u793a\u5217");
                showColsItem.setCategoryFlag(true);
                result.add(showColsItem);
                VaI18nResourceItem queryColsItem = new VaI18nResourceItem();
                queryColsItem.setName("param");
                queryColsItem.setTitle("\u67e5\u8be2\u53c2\u6570");
                queryColsItem.setCategoryFlag(true);
                result.add(queryColsItem);
                continue;
            }
            if (item.getChildren() == null || item.getChildren().isEmpty()) continue;
            this.findGroup(item.getChildren(), result, parents);
        }
    }

    public List<VaI18nResourceItem> getResource(String parentId) {
        ArrayList<VaI18nResourceItem> resourceList;
        block11: {
            List params;
            QueryTemplate template;
            String nameCode;
            block12: {
                block10: {
                    resourceList = new ArrayList<VaI18nResourceItem>();
                    TemplateInfoDao templateInfoDao = DCQuerySpringContextUtils.getBean(TemplateInfoDao.class);
                    if (!StringUtils.hasText(parentId)) {
                        return null;
                    }
                    if (parentId.split("#").length < 2) {
                        return null;
                    }
                    String[] nodes = parentId.split("#");
                    nameCode = "";
                    String templateCode = "";
                    TemplateInfoVO templatesByCode = null;
                    if (!(parentId.endsWith("#toolbars") || parentId.endsWith("#showcol") || parentId.endsWith("#param"))) {
                        templateCode = parentId.split("#")[nodes.length - 1];
                        templatesByCode = templateInfoDao.getTemplateInfoByCode(templateCode);
                        if (templatesByCode == null) {
                            return null;
                        }
                        VaI18nResourceItem showColItemResource = new VaI18nResourceItem();
                        showColItemResource.setCategoryFlag(false);
                        showColItemResource.setName(templatesByCode.getCode());
                        showColItemResource.setTitle(templatesByCode.getTitle());
                        resourceList.add(showColItemResource);
                        return resourceList;
                    }
                    nameCode = parentId.split("#")[nodes.length - 1];
                    templateCode = parentId.split("#")[nodes.length - 2];
                    templatesByCode = templateInfoDao.getTemplateInfoByCode(templateCode);
                    if (!StringUtils.hasText(nameCode)) {
                        return null;
                    }
                    TemplateDesignService templateDesignService = DCQuerySpringContextUtils.getBean(TemplateDesignService.class);
                    template = templateDesignService.getTemplate(templatesByCode.getId());
                    if (template == null) {
                        return resourceList;
                    }
                    if (!"showcol".equals(nameCode)) break block10;
                    List fields = ((QueryFieldsPlugin)template.getPluginByName(PluginEnum.queryFields.name(), QueryFieldsPlugin.class)).getFields();
                    if (ObjectUtils.isEmpty(fields)) break block11;
                    for (TemplateFieldSettingVO field : fields) {
                        VaI18nResourceItem showColItemResource = new VaI18nResourceItem();
                        showColItemResource.setCategoryFlag(false);
                        showColItemResource.setName(field.getId());
                        showColItemResource.setTitle(field.getTitle());
                        resourceList.add(showColItemResource);
                    }
                    break block11;
                }
                if (!"toolbars".equals(nameCode)) break block12;
                List tools = ((ToolBarPlugin)template.getPluginByName(PluginEnum.toolBar.name(), ToolBarPlugin.class)).getTools();
                if (ObjectUtils.isEmpty(tools)) break block11;
                for (TemplateToolbarInfoVO toolbar : tools) {
                    VaI18nResourceItem toolbarItemResource = new VaI18nResourceItem();
                    toolbarItemResource.setName(toolbar.getId());
                    toolbarItemResource.setTitle(toolbar.getTitle());
                    resourceList.add(toolbarItemResource);
                }
                break block11;
            }
            if ("param".equals(nameCode) && !ObjectUtils.isEmpty(params = ((DataSourcePlugin)template.getPluginByName(PluginEnum.dataSource.name(), DataSourcePlugin.class)).getParams())) {
                for (TemplateParamsVO param : params) {
                    VaI18nResourceItem paramItemResource = new VaI18nResourceItem();
                    paramItemResource.setName(param.getId());
                    paramItemResource.setTitle(param.getTitle());
                    resourceList.add(paramItemResource);
                }
            }
        }
        return resourceList;
    }
}

