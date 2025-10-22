/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.privilege.service.PrivilegeService
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nvwa.authority.extend.DefaultResourceCategory
 *  com.jiuqi.nvwa.authority.privilege.PrivilegeDefinition
 *  com.jiuqi.nvwa.authority.privilege.PrivilegeDefinitionItem
 *  com.jiuqi.nvwa.authority.resource.Resource
 *  com.jiuqi.nvwa.authority.resource.ResourceGroupItem
 *  com.jiuqi.nvwa.authority.resource.ResourceItem
 *  com.jiuqi.nvwa.authority.util.AuthorityConst$Category_Type
 *  com.jiuqi.nvwa.authority.vo.GranteeInfo
 */
package com.jiuqi.nr.query.auth.authz2;

import com.jiuqi.np.authz2.privilege.service.PrivilegeService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.query.auth.authz2.QueryModelResource;
import com.jiuqi.nr.query.dao.IQueryModalDefineDao;
import com.jiuqi.nr.query.dao.IQueryModalGroupDao;
import com.jiuqi.nr.query.querymodal.QueryModelType;
import com.jiuqi.nvwa.authority.extend.DefaultResourceCategory;
import com.jiuqi.nvwa.authority.privilege.PrivilegeDefinition;
import com.jiuqi.nvwa.authority.privilege.PrivilegeDefinitionItem;
import com.jiuqi.nvwa.authority.resource.Resource;
import com.jiuqi.nvwa.authority.resource.ResourceGroupItem;
import com.jiuqi.nvwa.authority.resource.ResourceItem;
import com.jiuqi.nvwa.authority.util.AuthorityConst;
import com.jiuqi.nvwa.authority.vo.GranteeInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueryModalResourcess
extends DefaultResourceCategory {
    private static final Logger log = LoggerFactory.getLogger(QueryModalResourcess.class);
    public static final String QUERYMODELID = "b8079ac0-dc15-11e8-969b-64006a6432d8";
    @Autowired
    IQueryModalGroupDao mGDao;
    @Autowired
    IQueryModalDefineDao mdDao;
    @Autowired
    private PrivilegeService privilegeService;

    public String getId() {
        return QUERYMODELID;
    }

    public String getTitle() {
        return "\u591a\u7ef4\u67e5\u8be2\u6a21\u677f";
    }

    public int getSeq() {
        return 800;
    }

    public AuthorityConst.Category_Type getCategoryType() {
        return AuthorityConst.Category_Type.NORMAL;
    }

    public String getGroupTitle() {
        return "\u62a5\u8868";
    }

    public boolean isSupportReject() {
        return true;
    }

    public List<String> getBasePrivilegeIds() {
        return Collections.singletonList("query_model_resource_read");
    }

    public List<PrivilegeDefinition> getPrivilegeDefinition() {
        ArrayList<PrivilegeDefinition> list = new ArrayList<PrivilegeDefinition>();
        PrivilegeDefinitionItem superItem = new PrivilegeDefinitionItem();
        superItem.setPrivilegeId("22222222-1111-1111-1111-222222222222");
        superItem.setPrivilegeTitle("\u540c\u4e0a\u7ea7");
        list.add((PrivilegeDefinition)superItem);
        PrivilegeDefinitionItem readItem = new PrivilegeDefinitionItem();
        readItem.setPrivilegeId("query_model_resource_read");
        readItem.setPrivilegeTitle("\u8bbf\u95ee");
        list.add((PrivilegeDefinition)readItem);
        PrivilegeDefinitionItem writeItem = new PrivilegeDefinitionItem();
        writeItem.setPrivilegeId("query_model_resource_write");
        writeItem.setPrivilegeTitle("\u7f16\u8f91");
        list.add((PrivilegeDefinition)writeItem);
        PrivilegeDefinitionItem grandDefinition = new PrivilegeDefinitionItem();
        grandDefinition.setPrivilegeId("22222222-2222-2222-2222-222222222222");
        grandDefinition.setPrivilegeTitle("\u6388\u6743");
        list.add((PrivilegeDefinition)grandDefinition);
        return list;
    }

    public List<Resource> getRootResources(GranteeInfo granteeInfo) {
        ArrayList<Resource> roots = new ArrayList<Resource>();
        try {
            ArrayList allResourceId = new ArrayList();
            this.mGDao.getModalGroupByParentId(QUERYMODELID, QueryModelType.OWNER).forEach(gr -> allResourceId.add(new QueryModelResource(gr.getGroupId(), "queryModelGroup", gr.getGroupName())));
            this.mdDao.getModalsByGroupId(QUERYMODELID, QueryModelType.OWNER).forEach(md -> allResourceId.add(new QueryModelResource(md.getId(), "QueryModel", md.getTitle())));
            for (QueryModelResource resource : allResourceId) {
                boolean hasDelegateAuth = this.privilegeService.hasDelegateAuth("query_model_resource_read", NpContextHolder.getContext().getIdentityId(), (Object)resource);
                if (!hasDelegateAuth) continue;
                boolean isGroup = resource.getType().equals("queryModelGroup");
                if (isGroup) {
                    roots.add((Resource)ResourceGroupItem.createResourceGroupItem((String)resource.getId(), (String)resource.getTitle(), (boolean)true));
                    continue;
                }
                roots.add((Resource)ResourceItem.createResourceItem((String)resource.getId(), (String)resource.getTitle()));
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return roots;
    }

    public List<Resource> getChildResources(String resourceGroupId, GranteeInfo granteeInfo) {
        ArrayList<Resource> resources = new ArrayList<Resource>();
        ArrayList allResourceId = new ArrayList();
        try {
            this.mGDao.getModalGroupByParentId(resourceGroupId, QueryModelType.OWNER).forEach(gr -> allResourceId.add(new QueryModelResource(gr.getGroupId(), "queryModelGroup", gr.getGroupName())));
            this.mdDao.getModalsByGroupId(resourceGroupId, QueryModelType.OWNER).forEach(md -> allResourceId.add(new QueryModelResource(md.getId(), "QueryModel", md.getTitle())));
            for (QueryModelResource resource : allResourceId) {
                boolean hasDelegateAuth = this.privilegeService.hasDelegateAuth("query_model_resource_read", NpContextHolder.getContext().getIdentityId(), (Object)resource);
                if (!hasDelegateAuth) continue;
                boolean isGroup = resource.getType().equals("queryModelGroup");
                if (isGroup) {
                    resources.add((Resource)ResourceGroupItem.createResourceGroupItem((String)resource.getId(), (String)resource.getTitle(), (boolean)true));
                    continue;
                }
                resources.add((Resource)ResourceItem.createResourceItem((String)resource.getId(), (String)resource.getTitle()));
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return resources;
    }
}

