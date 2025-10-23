/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataresource.DataResource
 *  com.jiuqi.nr.dataresource.DataResourceDefine
 *  com.jiuqi.nr.dataresource.DataResourceDefineGroup
 *  com.jiuqi.nr.dataresource.DataResourceKind
 *  com.jiuqi.nr.dataresource.dto.SearchDataFieldDTO
 *  com.jiuqi.nr.dataresource.entity.DataResourceDO
 *  com.jiuqi.nr.dataresource.service.IDataLinkService
 *  com.jiuqi.nr.dataresource.service.IDataResourceDefineGroupService
 *  com.jiuqi.nr.dataresource.service.IDataResourceDefineService
 *  com.jiuqi.nr.dataresource.service.IDataResourceService
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.internal.entity.DataFieldDO
 *  com.jiuqi.nvwa.topicnavigator.adapter.bean.RelatedTemplateItem
 *  com.jiuqi.nvwa.topicnavigator.adapter.bean.TemplateNodeItem
 *  com.jiuqi.nvwa.topicnavigator.adapter.templateFactory.ExactQueryDTO
 *  com.jiuqi.nvwa.topicnavigator.adapter.templateFactory.FuzzyQueryDTO
 *  com.jiuqi.nvwa.topicnavigator.adapter.templateFactory.QueryNodeChildrenDTO
 */
package com.jiuqi.nr.topic.extend.zbview;

import com.jiuqi.nr.dataresource.DataResource;
import com.jiuqi.nr.dataresource.DataResourceDefine;
import com.jiuqi.nr.dataresource.DataResourceDefineGroup;
import com.jiuqi.nr.dataresource.DataResourceKind;
import com.jiuqi.nr.dataresource.dto.SearchDataFieldDTO;
import com.jiuqi.nr.dataresource.entity.DataResourceDO;
import com.jiuqi.nr.dataresource.service.IDataLinkService;
import com.jiuqi.nr.dataresource.service.IDataResourceDefineGroupService;
import com.jiuqi.nr.dataresource.service.IDataResourceDefineService;
import com.jiuqi.nr.dataresource.service.IDataResourceService;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDO;
import com.jiuqi.nvwa.topicnavigator.adapter.bean.RelatedTemplateItem;
import com.jiuqi.nvwa.topicnavigator.adapter.bean.TemplateNodeItem;
import com.jiuqi.nvwa.topicnavigator.adapter.templateFactory.ExactQueryDTO;
import com.jiuqi.nvwa.topicnavigator.adapter.templateFactory.FuzzyQueryDTO;
import com.jiuqi.nvwa.topicnavigator.adapter.templateFactory.QueryNodeChildrenDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class ZbViewTreeService {
    @Autowired
    private IDataResourceDefineGroupService groupService;
    @Autowired
    private IDataResourceDefineService treeService;
    @Autowired
    private IDataResourceService resourceService;
    @Autowired
    private IDataLinkService zbService;
    @Autowired
    private IRuntimeDataSchemeService iRuntimeDataSchemeService;

    public List<RelatedTemplateItem> getByParent(String parentId) {
        List views;
        List groups;
        ArrayList<RelatedTemplateItem> res = new ArrayList<RelatedTemplateItem>();
        if ("com.jiuqi.nr.topic.extend.zbview".equals(parentId)) {
            parentId = "00000000-0000-0000-0000-000000000000";
        }
        if (!CollectionUtils.isEmpty(groups = this.groupService.getByParentKey(parentId))) {
            for (DataResourceDefineGroup g : groups) {
                res.add(this.buildTreeGroup(g));
            }
        }
        if (!CollectionUtils.isEmpty(views = this.treeService.getByGroupKey(parentId))) {
            for (DataResourceDefine view : views) {
                res.add(this.buildTreeNode(view));
            }
        }
        return res;
    }

    public RelatedTemplateItem exactQueryRelatedTemplate(String resourceId) {
        DataResourceDefine tree = this.treeService.get(resourceId);
        if (tree != null) {
            RelatedTemplateItem item = this.buildTreeNode(tree);
            ArrayList<String> path = new ArrayList<String>();
            ArrayList<String> titlePath = new ArrayList<String>();
            this.buildPath(tree.getGroupKey(), path, titlePath);
            path.add(tree.getKey());
            titlePath.add(tree.getTitle());
            item.getPath().addAll(path);
            item.getTitlePath().addAll(titlePath);
            return item;
        }
        DataResourceDefineGroup group = this.groupService.get(resourceId);
        if (group != null) {
            RelatedTemplateItem item = this.buildTreeGroup(group);
            ArrayList<String> path = new ArrayList<String>();
            ArrayList<String> titlePath = new ArrayList<String>();
            this.buildPath(group.getParentKey(), path, titlePath);
            path.add(group.getKey());
            titlePath.add(group.getTitle());
            item.getPath().addAll(path);
            item.getTitlePath().addAll(titlePath);
            return item;
        }
        return null;
    }

    public List<RelatedTemplateItem> fuzzyQueryRelatedTemplates(String searchKey) {
        ArrayList<RelatedTemplateItem> res = new ArrayList<RelatedTemplateItem>();
        List views = this.treeService.fuzzySearch(searchKey);
        if (!CollectionUtils.isEmpty(views)) {
            for (DataResourceDefine view : views) {
                RelatedTemplateItem item = this.buildTreeNode(view);
                ArrayList<String> path = new ArrayList<String>();
                ArrayList<String> titlePath = new ArrayList<String>();
                this.buildPath(view.getGroupKey(), path, titlePath);
                path.add(view.getKey());
                titlePath.add(view.getTitle());
                item.getPath().addAll(path);
                item.getTitlePath().addAll(titlePath);
                res.add(item);
            }
        }
        return res;
    }

    public List<TemplateNodeItem> getTempNodeChildren(QueryNodeChildrenDTO dto) {
        if (!StringUtils.hasText(dto.getPid())) {
            return this.getTempNodeChildrenByView(dto.getTemplateId());
        }
        return this.getTempNodeChildrenByParent(dto.getPid(), dto.getTemplateId());
    }

    private List<TemplateNodeItem> getTempNodeChildrenByView(String zbViewKey) {
        ArrayList<TemplateNodeItem> res;
        block7: {
            List<DataResourceDO> resources;
            block5: {
                DataResourceDO firstResource;
                block8: {
                    block6: {
                        res = new ArrayList<TemplateNodeItem>();
                        DataResourceDefine tree = this.treeService.get(zbViewKey);
                        if (tree == null) {
                            return res;
                        }
                        resources = this.getResourceByZbView(zbViewKey);
                        if (CollectionUtils.isEmpty(resources)) {
                            return null;
                        }
                        int size = resources.size();
                        if (size != 1) break block5;
                        firstResource = resources.get(0);
                        if (firstResource.getResourceKind() == DataResourceKind.DIM_GROUP) {
                            return res;
                        }
                        if (!StringUtils.hasText(firstResource.getLinkZb())) break block6;
                        res.add(this.buildResource((DataResource)firstResource));
                        break block7;
                    }
                    List firstNodeChildSources = this.resourceService.getByParent(zbViewKey, firstResource.getKey());
                    if (CollectionUtils.isEmpty(firstNodeChildSources)) break block8;
                    res.add(this.buildResource((DataResource)firstResource));
                    break block7;
                }
                List zbs = this.zbService.getByGroupNoPeriod(firstResource.getKey());
                if (CollectionUtils.isEmpty(zbs)) break block7;
                for (DataField zb : zbs) {
                    res.add(this.buildZB(zb, zbViewKey));
                }
                break block7;
            }
            for (DataResourceDO r : resources) {
                if (r.getResourceKind() == DataResourceKind.DIM_GROUP) continue;
                res.add(this.buildResource((DataResource)r));
            }
        }
        return res;
    }

    private List<TemplateNodeItem> getTempNodeChildrenByParent(String parentKey, String zbViewKey) {
        ArrayList<TemplateNodeItem> res = new ArrayList<TemplateNodeItem>();
        DataResourceDefine tree = this.treeService.get(zbViewKey);
        if (tree == null) {
            return res;
        }
        DataResourceDO parentResource = this.resourceService.getByKey(parentKey);
        if (parentResource != null) {
            List zbs;
            List resources = this.resourceService.getByParent(zbViewKey, parentKey);
            if (!CollectionUtils.isEmpty(resources)) {
                for (DataResource r : resources) {
                    if (r.getResourceKind() == DataResourceKind.DIM_GROUP) continue;
                    res.add(this.buildResource(r));
                }
            }
            if (!CollectionUtils.isEmpty(zbs = this.zbService.getByGroupNoPeriod(parentKey))) {
                for (DataField zb : zbs) {
                    res.add(this.buildZB(zb, parentKey));
                }
            }
        }
        return res;
    }

    private List<DataResourceDO> getResourceByZbView(String zbViewKey) {
        ArrayList<DataResourceDO> res = new ArrayList<DataResourceDO>();
        List resources = this.resourceService.getByParent(zbViewKey, null);
        if (CollectionUtils.isEmpty(resources)) {
            return null;
        }
        for (DataResourceDO r : resources) {
            if (r.getResourceKind() == DataResourceKind.DIM_GROUP) continue;
            res.add(r);
        }
        return res;
    }

    public TemplateNodeItem exactQueryTempNode(ExactQueryDTO dto) {
        String zbViewKey = dto.getTemplateId();
        String nodeId = dto.getNodeId();
        DataResourceDefine zbView = this.treeService.get(zbViewKey);
        if (zbView == null) {
            return null;
        }
        if (nodeId.indexOf("@") > -1) {
            String zbId = nodeId.split("@")[0];
            String parentId = nodeId.split("@")[1];
            DataField zb = this.iRuntimeDataSchemeService.getDataField(zbId);
            if (zb == null) {
                return null;
            }
            TemplateNodeItem item = this.buildZB(zb, parentId);
            if (zbViewKey.equals(parentId)) {
                item.getPath().add(item.getId());
                item.getTitlePath().add(item.getTitle());
            } else {
                DataResourceDO parentResource = this.resourceService.getByKey(parentId);
                if (parentResource == null) {
                    return null;
                }
                this.buildResourcePath(parentResource.getParentKey(), item.getPath(), item.getTitlePath());
                item.getPath().add(parentResource.getKey());
                item.getTitlePath().add(parentResource.getTitle());
                item.getPath().add(item.getId());
                item.getTitlePath().add(item.getTitle());
            }
            return item;
        }
        DataResourceDO dataResource = this.resourceService.getByKey(nodeId);
        if (dataResource != null) {
            TemplateNodeItem item = this.buildResource((DataResource)dataResource);
            this.buildResourcePath(dataResource.getParentKey(), item.getPath(), item.getTitlePath());
            item.getPath().add(item.getId());
            item.getTitlePath().add(item.getTitle());
            return item;
        }
        return null;
    }

    private void buildResourcePath(String resourceId, List<String> path, List<String> titlePath) {
        if (!StringUtils.hasText(resourceId)) {
            return;
        }
        DataResourceDO dataResource = this.resourceService.getByKey(resourceId);
        if (dataResource != null) {
            this.buildResourcePath(dataResource.getParentKey(), path, titlePath);
            path.add(dataResource.getKey());
            titlePath.add(dataResource.getTitle());
        }
    }

    public List<TemplateNodeItem> fuzzyQueryTempNodes(FuzzyQueryDTO dto) {
        ArrayList<TemplateNodeItem> res = new ArrayList<TemplateNodeItem>();
        String zbViewKey = dto.getTemplateId();
        DataResourceDefine zbView = this.treeService.get(zbViewKey);
        if (zbView == null) {
            return null;
        }
        List searchDatas = this.zbService.searchByDefineKey(zbViewKey, dto.getSearchKey());
        if (!CollectionUtils.isEmpty(searchDatas)) {
            for (SearchDataFieldDTO s : searchDatas) {
                DataResourceDO parentResource = this.resourceService.getByKey(s.getGroup().getKey());
                if (parentResource == null) continue;
                DataFieldDO zb = new DataFieldDO();
                zb.setKey(s.getKey());
                zb.setTitle(s.getTitle());
                zb.setCode(s.getCode());
                TemplateNodeItem item = this.buildZB((DataField)zb, s.getGroup().getKey());
                this.buildResourcePath(parentResource.getParentKey(), item.getPath(), item.getTitlePath());
                item.getPath().add(parentResource.getKey());
                item.getTitlePath().add(parentResource.getTitle());
                item.getPath().add(item.getId());
                item.getTitlePath().add(item.getTitle());
                res.add(item);
            }
        }
        return res;
    }

    private TemplateNodeItem buildZB(DataField zb, String parentId) {
        if (zb == null) {
            return null;
        }
        TemplateNodeItem item = new TemplateNodeItem();
        item.setId(zb.getKey() + "@" + parentId);
        item.setName(zb.getCode());
        item.setMessageValue(zb.getCode());
        item.setTitle(zb.getTitle());
        item.setParentId(parentId);
        item.setLeaf(true);
        item.setCanSelect(true);
        return item;
    }

    private TemplateNodeItem buildResource(DataResource r) {
        TemplateNodeItem item = new TemplateNodeItem();
        item.setId(r.getKey());
        if (StringUtils.hasText(r.getLinkZb())) {
            DataField zb = this.iRuntimeDataSchemeService.getDataField(r.getLinkZb());
            item.setMessageValue(zb.getCode());
        }
        item.setTitle(r.getTitle());
        item.setParentId(StringUtils.hasText(r.getParentKey()) ? r.getParentKey() : r.getResourceDefineKey());
        item.setLeaf(false);
        item.setCanSelect(false);
        return item;
    }

    private void buildPath(String parentId, List<String> path, List<String> titlePath) {
        if ("00000000-0000-0000-0000-000000000000".equals(parentId)) {
            path.add("com.jiuqi.nr.topic.extend.zbview");
            titlePath.add("\u6307\u6807\u89c6\u56fe");
            return;
        }
        DataResourceDefineGroup group = this.groupService.get(parentId);
        if (group != null) {
            this.buildPath(group.getParentKey(), path, titlePath);
            path.add(group.getKey());
            titlePath.add(group.getTitle());
        }
    }

    private RelatedTemplateItem buildTreeNode(DataResourceDefine view) {
        RelatedTemplateItem item = new RelatedTemplateItem();
        item.setId(view.getKey());
        item.setTitle(view.getTitle());
        item.setLeaf(true);
        item.setType("topicZbView");
        item.setIcon("nr-iconfont icon-16_SHU_A_NR_shujuziyuanshu");
        return item;
    }

    private RelatedTemplateItem buildTreeGroup(DataResourceDefineGroup group) {
        RelatedTemplateItem item = new RelatedTemplateItem();
        item.setId(group.getKey());
        item.setTitle(group.getTitle());
        item.setLeaf(false);
        item.setType("topicZbView");
        item.setIcon("nr-iconfont icon-16_SHU_A_NR_fenzu");
        return item;
    }
}

