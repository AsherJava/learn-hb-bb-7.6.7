/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.common.NodeIconGetter
 *  com.jiuqi.nvwa.authority.extend.DefaultResourceCategory
 *  com.jiuqi.nvwa.authority.privilege.PrivilegeDefinition
 *  com.jiuqi.nvwa.authority.privilege.PrivilegeDefinitionItem
 *  com.jiuqi.nvwa.authority.resource.Resource
 *  com.jiuqi.nvwa.authority.resource.ResourceGroupItem
 *  com.jiuqi.nvwa.authority.resource.ResourceItem
 *  com.jiuqi.nvwa.authority.resource.ResourceNode
 *  com.jiuqi.nvwa.authority.resource.ResourceSearchResult
 *  com.jiuqi.nvwa.authority.service.AuthorityService
 *  com.jiuqi.nvwa.authority.tree.ITree
 *  com.jiuqi.nvwa.authority.util.AuthorityConst$AuthzRightAreaPlan
 *  com.jiuqi.nvwa.authority.util.AuthorityConst$Category_Type
 *  com.jiuqi.nvwa.authority.vo.AuthorityTreeChildReq
 *  com.jiuqi.nvwa.authority.vo.AuthorityTreeInitReq
 *  com.jiuqi.nvwa.authority.vo.GranteeInfo
 */
package com.jiuqi.nr.singlequeryimport.auth;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.common.NodeIconGetter;
import com.jiuqi.nr.singlequeryimport.auth.FinalaccountQueryAuthResourceType;
import com.jiuqi.nr.singlequeryimport.auth.share.service.AuthShareService;
import com.jiuqi.nr.singlequeryimport.bean.QueryModelNode;
import com.jiuqi.nr.singlequeryimport.service.impl.QueryModleServiceImpl;
import com.jiuqi.nvwa.authority.extend.DefaultResourceCategory;
import com.jiuqi.nvwa.authority.privilege.PrivilegeDefinition;
import com.jiuqi.nvwa.authority.privilege.PrivilegeDefinitionItem;
import com.jiuqi.nvwa.authority.resource.Resource;
import com.jiuqi.nvwa.authority.resource.ResourceGroupItem;
import com.jiuqi.nvwa.authority.resource.ResourceItem;
import com.jiuqi.nvwa.authority.resource.ResourceNode;
import com.jiuqi.nvwa.authority.resource.ResourceSearchResult;
import com.jiuqi.nvwa.authority.service.AuthorityService;
import com.jiuqi.nvwa.authority.tree.ITree;
import com.jiuqi.nvwa.authority.util.AuthorityConst;
import com.jiuqi.nvwa.authority.vo.AuthorityTreeChildReq;
import com.jiuqi.nvwa.authority.vo.AuthorityTreeInitReq;
import com.jiuqi.nvwa.authority.vo.GranteeInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class FinalaccountQueryAuthResource
extends DefaultResourceCategory {
    private static final Logger logger = LoggerFactory.getLogger(FinalaccountQueryAuthResource.class);
    private static final long serialVersionUID = 286849170715603870L;
    @Autowired
    private QueryModleServiceImpl queryModleService;
    @Autowired
    private AuthShareService authShareService;
    @Autowired
    private AuthorityService authorityService;

    public String getId() {
        return "finalaccountquery-auth-resource-category";
    }

    public String getTitle() {
        return "\u6a2a\u5411\u8fc7\u5f55\u8868\u67e5\u8be2";
    }

    public String getGroupTitle() {
        return "\u62a5\u8868";
    }

    public int getSeq() {
        return 0;
    }

    public AuthorityConst.AuthzRightAreaPlan getAuthRightAreaPlan() {
        return AuthorityConst.AuthzRightAreaPlan.All;
    }

    public AuthorityConst.Category_Type getCategoryType() {
        return AuthorityConst.Category_Type.NORMAL;
    }

    public List<String> getBasePrivilegeIds() {
        return Collections.singletonList("finalaccountquery_auth_resource_read");
    }

    public boolean isSupportReject() {
        return true;
    }

    public List<PrivilegeDefinition> getPrivilegeDefinition(GranteeInfo granteeInfo) {
        ArrayList<PrivilegeDefinition> list = new ArrayList<PrivilegeDefinition>();
        PrivilegeDefinitionItem item = new PrivilegeDefinitionItem();
        item = new PrivilegeDefinitionItem();
        item.setPrivilegeId("finalaccountquery_auth_resource_read");
        item.setPrivilegeTitle("\u8bbf\u95ee");
        list.add((PrivilegeDefinition)item);
        item = new PrivilegeDefinitionItem();
        item.setPrivilegeId("finalaccountquery_auth_resource_write");
        item.setPrivilegeTitle("\u7f16\u8f91");
        list.add((PrivilegeDefinition)item);
        item = new PrivilegeDefinitionItem();
        item.setPrivilegeId("finalaccountquery_auth_resource_accredit");
        item.setPrivilegeTitle("\u6388\u6743");
        list.add((PrivilegeDefinition)item);
        return list;
    }

    public List<Resource> getRootResources(GranteeInfo granteeInfo) {
        ArrayList<Resource> resultList = new ArrayList<Resource>();
        try {
            List<QueryModelNode> taskModelList = this.queryModleService.getTaskModel();
            if (!CollectionUtils.isEmpty(taskModelList)) {
                for (QueryModelNode taskNode : taskModelList) {
                    ResourceGroupItem item = ResourceGroupItem.createResourceGroupItem((String)FinalaccountQueryAuthResourceType.FQ_TASK.toResourceId(taskNode.getId()), (String)taskNode.getTitle(), (boolean)false);
                    item.setIcons(NodeIconGetter.getIconByType((NodeType)NodeType.SCHEME_GROUP));
                    resultList.add((Resource)item);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public List<Resource> getChildResources(String resourceGroupId, GranteeInfo granteeInfo, Object param) {
        ArrayList<Resource> resultList = new ArrayList<Resource>();
        try {
            String groupKey;
            String formSchemeId;
            List<QueryModelNode> modleList;
            String[] parts;
            if (resourceGroupId.startsWith(FinalaccountQueryAuthResourceType.FQ_TASK.getPrefix())) {
                String taskId = FinalaccountQueryAuthResourceType.FQ_TASK.toObjectId(resourceGroupId);
                List<QueryModelNode> formSchemeModleList = this.queryModleService.getFormSchemeModel(taskId);
                if (null != formSchemeModleList && formSchemeModleList.size() > 0) {
                    for (QueryModelNode formScheme : formSchemeModleList) {
                        ResourceGroupItem item = ResourceGroupItem.createResourceGroupItem((String)FinalaccountQueryAuthResourceType.FQ_FROM_SCHEME.toResourceId(formScheme.getId()), (String)formScheme.getTitle(), (boolean)false);
                        item.setIcons(NodeIconGetter.getIconByType((NodeType)NodeType.SCHEME));
                        resultList.add((Resource)item);
                    }
                }
            } else if (resourceGroupId.startsWith(FinalaccountQueryAuthResourceType.FQ_FROM_SCHEME.getPrefix())) {
                String formSchemeId2 = FinalaccountQueryAuthResourceType.FQ_FROM_SCHEME.toObjectId(resourceGroupId);
                List<QueryModelNode> groupModleList = this.queryModleService.getGroupModel(formSchemeId2);
                if (null != groupModleList && groupModleList.size() > 0) {
                    for (QueryModelNode groupNode : groupModleList) {
                        ResourceGroupItem item = ResourceGroupItem.createResourceGroupItem((String)FinalaccountQueryAuthResourceType.FQ_GROUP.toResourceId(formSchemeId2 + "#" + groupNode.getId()), (String)groupNode.getTitle(), (boolean)true);
                        item.setIcons(NodeIconGetter.getIconByType((NodeType)NodeType.GROUP));
                        resultList.add((Resource)item);
                    }
                }
            } else if (resourceGroupId.startsWith(FinalaccountQueryAuthResourceType.FQ_GROUP.getPrefix()) && (parts = FinalaccountQueryAuthResourceType.FQ_GROUP.toObjectId(resourceGroupId).split("#")).length == 2 && null != (modleList = this.queryModleService.getModel(formSchemeId = parts[0], groupKey = parts[1])) && modleList.size() > 0) {
                for (QueryModelNode modelNode : modleList) {
                    ResourceItem item = ResourceItem.createResourceItem((String)FinalaccountQueryAuthResourceType.FQ_MODEL_NODE.toResourceId(modelNode.getId()), (String)modelNode.getTitle());
                    item.setIcons(NodeIconGetter.getIconByType((NodeType)NodeType.TABLE));
                    resultList.add((Resource)item);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public boolean enableSearch() {
        return true;
    }

    public List<ResourceSearchResult> searchResource(String fuzzyTitle, String key) {
        ArrayList<ResourceSearchResult> resultList = new ArrayList<ResourceSearchResult>();
        GranteeInfo granteeInfo = new GranteeInfo();
        granteeInfo.setOwnerId(NpContextHolder.getContext().getUserId());
        logger.info("searchResource: fuzzyTitle={}, key={}", (Object)fuzzyTitle, (Object)key);
        AuthorityTreeInitReq authorityTreeInitReq = new AuthorityTreeInitReq();
        authorityTreeInitReq.setGranteeInfo(granteeInfo);
        authorityTreeInitReq.setCategoryId("finalaccountquery-auth-resource-category");
        List authorityTree = this.authorityService.getAuthorityTree(authorityTreeInitReq);
        this.buildTree(((ITree)authorityTree.get(0)).getChildren());
        if (StringUtils.hasLength(key)) {
            ResourceSearchResult searchResult = new ResourceSearchResult();
            this.searchNode(authorityTree, key, searchResult);
            if (null != searchResult.getKey() && null != searchResult.getTitle()) {
                resultList.add(searchResult);
            }
        } else {
            this.searchNodeByTitle(authorityTree, fuzzyTitle, resultList);
        }
        return resultList;
    }

    public void buildTree(List<ITree<ResourceNode>> authorityTre) {
        GranteeInfo granteeInfo = new GranteeInfo();
        granteeInfo.setOwnerId(NpContextHolder.getContext().getUserId());
        if (!authorityTre.isEmpty()) {
            for (ITree<ResourceNode> nodeITree : authorityTre) {
                AuthorityTreeChildReq authorityTreeChildReq = new AuthorityTreeChildReq();
                authorityTreeChildReq.setCategoryId("finalaccountquery-auth-resource-category");
                authorityTreeChildReq.setGranteeInfo(granteeInfo);
                authorityTreeChildReq.setKey(nodeITree.getKey());
                List authorityChildTree = this.authorityService.getAuthorityChildTree(authorityTreeChildReq);
                authorityChildTree.forEach(n -> n.setParent(nodeITree));
                nodeITree.setChildren(authorityChildTree);
                if (authorityChildTree.isEmpty()) continue;
                this.buildTree(authorityChildTree);
            }
        }
    }

    public void searchNode(List<ITree<ResourceNode>> authorityTre, String key, ResourceSearchResult resourceSearchResult) {
        if (!authorityTre.isEmpty()) {
            for (ITree<ResourceNode> nodeITree : authorityTre) {
                if (nodeITree.getKey().equals(key)) {
                    ArrayList<String> path = new ArrayList<String>();
                    resourceSearchResult.setKey(key);
                    resourceSearchResult.setTitle(nodeITree.getTitle());
                    this.getParent(nodeITree, path);
                    Collections.reverse(path);
                    resourceSearchResult.setPath(path);
                    continue;
                }
                if (nodeITree.getChildren().isEmpty()) continue;
                this.searchNode(nodeITree.getChildren(), key, resourceSearchResult);
            }
        }
    }

    public void searchNodeByTitle(List<ITree<ResourceNode>> authorityTre, String title, List<ResourceSearchResult> resultList) {
        if (!authorityTre.isEmpty()) {
            for (ITree<ResourceNode> nodeITree : authorityTre) {
                if (nodeITree.getTitle().contains(title)) {
                    ResourceSearchResult resourceSearchResult = new ResourceSearchResult();
                    ArrayList<String> path = new ArrayList<String>();
                    resourceSearchResult.setKey(title);
                    resourceSearchResult.setTitle(nodeITree.getTitle());
                    this.getParent(nodeITree, path);
                    Collections.reverse(path);
                    resourceSearchResult.setPath(path);
                    resultList.add(resourceSearchResult);
                    if (nodeITree.getChildren().isEmpty()) continue;
                    this.searchNodeByTitle(nodeITree.getChildren(), title, resultList);
                    continue;
                }
                if (nodeITree.getChildren().isEmpty()) continue;
                this.searchNodeByTitle(nodeITree.getChildren(), title, resultList);
            }
        }
    }

    public void getParent(ITree<ResourceNode> nodeITree, List<String> path) {
        if (null != nodeITree.getParent()) {
            path.add(nodeITree.getKey());
            this.getParent((ITree<ResourceNode>)nodeITree.getParent(), path);
        } else {
            path.add(nodeITree.getKey());
        }
    }
}

