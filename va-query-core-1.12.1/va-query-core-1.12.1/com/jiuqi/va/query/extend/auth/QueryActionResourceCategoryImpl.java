/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.authority.extend.DefaultResourceCategory
 *  com.jiuqi.nvwa.authority.privilege.PrivilegeDefinition
 *  com.jiuqi.nvwa.authority.privilege.PrivilegeDefinitionItem
 *  com.jiuqi.nvwa.authority.resource.Resource
 *  com.jiuqi.nvwa.authority.resource.ResourceGroupItem
 *  com.jiuqi.nvwa.authority.resource.ResourceItem
 *  com.jiuqi.nvwa.authority.util.AuthorityConst$Category_Type
 *  com.jiuqi.nvwa.authority.vo.GranteeInfo
 *  com.jiuqi.va.query.template.plugin.ToolBarPlugin
 *  com.jiuqi.va.query.template.vo.QueryTemplate
 *  com.jiuqi.va.query.template.vo.TemplateToolbarInfoVO
 *  com.jiuqi.va.query.tree.vo.MenuTreeVO
 */
package com.jiuqi.va.query.extend.auth;

import com.jiuqi.nvwa.authority.extend.DefaultResourceCategory;
import com.jiuqi.nvwa.authority.privilege.PrivilegeDefinition;
import com.jiuqi.nvwa.authority.privilege.PrivilegeDefinitionItem;
import com.jiuqi.nvwa.authority.resource.Resource;
import com.jiuqi.nvwa.authority.resource.ResourceGroupItem;
import com.jiuqi.nvwa.authority.resource.ResourceItem;
import com.jiuqi.nvwa.authority.util.AuthorityConst;
import com.jiuqi.nvwa.authority.vo.GranteeInfo;
import com.jiuqi.va.query.template.plugin.ToolBarPlugin;
import com.jiuqi.va.query.template.service.impl.TemplateDesignServiceImpl;
import com.jiuqi.va.query.template.vo.QueryTemplate;
import com.jiuqi.va.query.template.vo.TemplateToolbarInfoVO;
import com.jiuqi.va.query.tree.service.impl.MenuTreeServiceImpl;
import com.jiuqi.va.query.tree.vo.MenuTreeVO;
import com.jiuqi.va.query.util.DCQuerySpringContextUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class QueryActionResourceCategoryImpl
extends DefaultResourceCategory {
    private static final long serialVersionUID = 1L;
    public static final String ID = "VAQueryAction_d215baaa-17b8-4f67";

    public String getGroupTitle() {
        return "\u4f4e\u4ee3\u7801";
    }

    public String getId() {
        return ID;
    }

    public String getTitle() {
        return "\u67e5\u8be2\u5b9a\u4e49\u52a8\u4f5c";
    }

    public int getSeq() {
        return 304;
    }

    public AuthorityConst.Category_Type getCategoryType() {
        return AuthorityConst.Category_Type.NORMAL;
    }

    @Deprecated
    public List<PrivilegeDefinition> getPrivilegeDefinition() {
        ArrayList<PrivilegeDefinition> list = new ArrayList<PrivilegeDefinition>();
        PrivilegeDefinitionItem grandDefinition = new PrivilegeDefinitionItem();
        grandDefinition.setPrivilegeId("22222222-3333-3333-3333-222222222222");
        grandDefinition.setPrivilegeTitle("\u6388\u6743");
        list.add((PrivilegeDefinition)grandDefinition);
        return list;
    }

    public List<String> getBasePrivilegeIds() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("22222222-3333-3333-3333-222222222222");
        return list;
    }

    public List<Resource> getRootResources(GranteeInfo granteeInfo) {
        ArrayList<Resource> list = new ArrayList<Resource>();
        MenuTreeServiceImpl menuTreeService = DCQuerySpringContextUtils.getBean(MenuTreeServiceImpl.class);
        List<MenuTreeVO> tree = menuTreeService.getTree();
        for (MenuTreeVO menuTreeVO : tree) {
            list.add((Resource)ResourceGroupItem.createResourceGroupItem((String)menuTreeVO.getId(), (String)menuTreeVO.getTitle(), (boolean)false));
        }
        return list;
    }

    public List<Resource> getChildResources(String resourceGroupId, GranteeInfo granteeInfo) {
        ArrayList<Resource> list = new ArrayList<Resource>();
        MenuTreeServiceImpl menuTreeService = DCQuerySpringContextUtils.getBean(MenuTreeServiceImpl.class);
        MenuTreeVO menuByParam = menuTreeService.getMenuByParam(menuTreeService.getTree(), new ArrayList<MenuTreeVO>(), vo -> vo.getId().equals(resourceGroupId));
        List children = menuByParam.getChildren();
        if (CollectionUtils.isEmpty(children) && "group".equals(menuByParam.getNodeType())) {
            return list;
        }
        if (CollectionUtils.isEmpty(children)) {
            TemplateDesignServiceImpl designService = DCQuerySpringContextUtils.getBean(TemplateDesignServiceImpl.class);
            QueryTemplate template = designService.getTemplate(menuByParam.getId());
            List tools = ((ToolBarPlugin)template.getPluginByClass(ToolBarPlugin.class)).getTools();
            for (TemplateToolbarInfoVO tool : tools) {
                if (!Boolean.TRUE.equals(tool.getEnableAuth())) continue;
                list.add((Resource)ResourceItem.createResourceItem((String)tool.getId(), (String)tool.getTitle()));
            }
            return list;
        }
        for (MenuTreeVO child : children) {
            list.add((Resource)ResourceGroupItem.createResourceGroupItem((String)child.getId(), (String)child.getTitle(), (boolean)false));
        }
        return list;
    }
}

