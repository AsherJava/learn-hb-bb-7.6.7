/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.privilege.Privilege
 *  com.jiuqi.np.authz2.privilege.service.PrivilegeService
 *  com.jiuqi.nvwa.authority.extend.SuperInheritPathProvider
 *  com.jiuqi.nvwa.authority.resource.Resource
 *  com.jiuqi.nvwa.authority.resource.ResourceGroupItem
 */
package com.jiuqi.nr.dataresource.authority;

import com.jiuqi.np.authz2.privilege.Privilege;
import com.jiuqi.np.authz2.privilege.service.PrivilegeService;
import com.jiuqi.nr.dataresource.NodeType;
import com.jiuqi.nr.dataresource.ResourceNode;
import com.jiuqi.nr.dataresource.authority.Util;
import com.jiuqi.nr.dataresource.dao.IDataResourceDao;
import com.jiuqi.nr.dataresource.dao.IDataResourceDefineDao;
import com.jiuqi.nr.dataresource.dao.IDataResourceDefineGroupDao;
import com.jiuqi.nr.dataresource.entity.DataResourceDO;
import com.jiuqi.nr.dataresource.entity.ResourceTreeDO;
import com.jiuqi.nr.dataresource.entity.ResourceTreeGroup;
import com.jiuqi.nr.dataresource.loader.DataResourceLevelLoader;
import com.jiuqi.nr.dataresource.service.IDataResourceService;
import com.jiuqi.nr.dataresource.service.impl.AuthRootBuildTreeVisitor;
import com.jiuqi.nvwa.authority.extend.SuperInheritPathProvider;
import com.jiuqi.nvwa.authority.resource.Resource;
import com.jiuqi.nvwa.authority.resource.ResourceGroupItem;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class DataResourceInheritPathProvider
implements SuperInheritPathProvider {
    @Autowired
    private IDataResourceDefineGroupDao groupDao;
    @Autowired
    private IDataResourceDefineDao defineDao;
    @Autowired
    private IDataResourceDao resourceDao;
    @Autowired
    private PrivilegeService privilegeService;
    @Autowired
    private DataResourceLevelLoader loader;
    @Autowired
    private IDataResourceService resourceService;

    public String getResourceCategoryId() {
        return "DataResourceTreeResourceCategoryImpl_ID";
    }

    public Set<String> computeIfChildrens(String resourceId, Set<String> resourceIds) {
        return null;
    }

    private String getResourceId(Object resource) {
        if (resource instanceof Resource) {
            return ((Resource)resource).getId();
        }
        if (resource instanceof String) {
            return String.valueOf(resource);
        }
        throw new RuntimeException(String.format("\u67e5\u8be2\u4e0a\u7ea7\u8d44\u6e90\u51fa\u9519: %s", this.getClass().toString()));
    }

    public Resource getParent(Object resource) {
        List<Object> parent = this.getParent(null, resource);
        if (parent.isEmpty()) {
            return null;
        }
        return (Resource)parent.get(0);
    }

    public List<Object> getParent(Privilege privilege, Object resource) {
        String resourceId = this.getResourceId(resource);
        if (!StringUtils.hasText(resourceId)) {
            return Collections.emptyList();
        }
        String[] arr = Util.splitResourceId(resourceId);
        NodeType nodeType = NodeType.valueOf(Integer.valueOf(arr[0]));
        String dataSourceId = arr[1];
        if ("00000000-0000-0000-0000-000000000000".equals(dataSourceId)) {
            return Collections.emptyList();
        }
        switch (nodeType) {
            case TREE_GROUP: {
                ResourceTreeGroup group = (ResourceTreeGroup)this.groupDao.get(dataSourceId);
                if (group == null) break;
                ResourceTreeGroup pgroup = (ResourceTreeGroup)this.groupDao.get(group.getParentKey());
                if (pgroup != null) {
                    return Arrays.asList(ResourceGroupItem.createResourceGroupItem((String)Util.getResourceIdByType(pgroup.getKey(), NodeType.TREE_GROUP.getValue()), (String)pgroup.getTitle(), (boolean)true));
                }
                return Arrays.asList(ResourceGroupItem.createResourceGroupItem((String)Util.getResourceIdByType("00000000-0000-0000-0000-000000000000", NodeType.TREE_GROUP.getValue()), (String)"\u5168\u90e8\u8d44\u6e90\u6811", (boolean)true));
            }
            case TREE: {
                ResourceTreeDO tree = (ResourceTreeDO)this.defineDao.get(dataSourceId);
                if (tree == null) break;
                ResourceTreeGroup pgroup = (ResourceTreeGroup)this.groupDao.get(tree.getGroupKey());
                if (pgroup != null) {
                    return Arrays.asList(ResourceGroupItem.createResourceGroupItem((String)Util.getResourceIdByType(pgroup.getKey(), NodeType.TREE_GROUP.getValue()), (String)pgroup.getTitle(), (boolean)true));
                }
                return Arrays.asList(ResourceGroupItem.createResourceGroupItem((String)Util.getResourceIdByType("00000000-0000-0000-0000-000000000000", NodeType.TREE_GROUP.getValue()), (String)"\u5168\u90e8\u8d44\u6e90\u6811", (boolean)true));
            }
            case RESOURCE_GROUP: {
                DataResourceDO pTreeInnerGroup;
                DataResourceDO treeInnerGroup = (DataResourceDO)this.resourceDao.get(dataSourceId);
                if (treeInnerGroup == null) break;
                if (StringUtils.hasText(treeInnerGroup.getParentKey()) && (pTreeInnerGroup = (DataResourceDO)this.resourceDao.get(treeInnerGroup.getParentKey())) != null) {
                    return Arrays.asList(ResourceGroupItem.createResourceGroupItem((String)Util.getResourceIdByType(pTreeInnerGroup.getKey(), NodeType.RESOURCE_GROUP.getValue()), (String)pTreeInnerGroup.getTitle(), (boolean)true));
                }
                ResourceTreeDO ptree = (ResourceTreeDO)this.defineDao.get(treeInnerGroup.getResourceDefineKey());
                return Arrays.asList(ResourceGroupItem.createResourceGroupItem((String)Util.getResourceIdByType(ptree.getKey(), NodeType.TREE.getValue()), (String)ptree.getTitle(), (boolean)true));
            }
        }
        return Collections.emptyList();
    }

    public List<String> getChildren(Privilege privilege, String resourceId) {
        throw new UnsupportedOperationException("\u6570\u636e\u8d44\u6e90\u5c1a\u672a\u652f\u6301\u83b7\u53d6\u4e0b\u7ea7\u7ee7\u627f\u65b9\u5f0f\u3002");
    }

    public Set<String> computeIfChildren(String resourceId, Set<String> resourceIds) {
        String[] arr = Util.splitResourceId(resourceId);
        NodeType nodeType = NodeType.valueOf(Integer.valueOf(arr[0]));
        String dataSourceId = arr[1];
        AuthRootBuildTreeVisitor build = new AuthRootBuildTreeVisitor(this.privilegeService, this.resourceService, false);
        this.loader.walkDataResourceTree(new ResourceNode(dataSourceId, nodeType.getValue()), build);
        List<Resource> resources = build.getValue();
        List childrenIds = resources.stream().map(r -> r.getId()).collect(Collectors.toList());
        return resourceIds.stream().filter(r -> childrenIds.contains(r)).collect(Collectors.toSet());
    }
}

