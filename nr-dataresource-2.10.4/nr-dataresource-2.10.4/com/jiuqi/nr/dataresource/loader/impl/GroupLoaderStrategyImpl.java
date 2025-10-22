/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataresource.loader.impl;

import com.jiuqi.nr.dataresource.NodeType;
import com.jiuqi.nr.dataresource.ResourceNode;
import com.jiuqi.nr.dataresource.dao.IDataResourceDefineDao;
import com.jiuqi.nr.dataresource.dao.IDataResourceDefineGroupDao;
import com.jiuqi.nr.dataresource.entity.ResourceTreeDO;
import com.jiuqi.nr.dataresource.entity.ResourceTreeGroup;
import com.jiuqi.nr.dataresource.i18n.ResourceTreeI18Util;
import com.jiuqi.nr.dataresource.loader.DataResourceLoaderStrategy;
import com.jiuqi.nr.dataresource.loader.ResourceNodeVisitor;
import com.jiuqi.nr.dataresource.loader.ReverseDataResourceNodeVisitor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupLoaderStrategyImpl
implements DataResourceLoaderStrategy {
    @Autowired
    private IDataResourceDefineGroupDao groupDao;
    @Autowired
    private IDataResourceDefineDao defineDao;

    @Override
    public boolean matching(int nodeType) {
        return (NodeType.TREE_GROUP.getValue() & nodeType) != 0;
    }

    @Override
    public <E> void visitRoot(ResourceNode<E> root, ResourceNodeVisitor<E> resourceNodeVisitor) {
        ResourceTreeGroup resourceTreeGroup;
        String key = root.getKey();
        if ("00000000-0000-0000-0000-000000000000".equals(key)) {
            resourceTreeGroup = new ResourceTreeGroup();
            resourceTreeGroup.setKey(key);
            resourceTreeGroup.setTitle(ResourceTreeI18Util.getRootTitle());
        } else {
            resourceTreeGroup = (ResourceTreeGroup)this.groupDao.get(key);
        }
        E e = resourceNodeVisitor.visitRootIsGroupNode(resourceTreeGroup);
        if (e != null) {
            root.setOther(e);
        }
    }

    @Override
    public <E> List<ResourceNode<E>> visitNode(ResourceNode<E> next, ResourceNodeVisitor<E> resourceNodeVisitor) {
        ResourceNode<E> it;
        ArrayList list = new ArrayList();
        List<ResourceTreeGroup> byParent = this.groupDao.getByParent(next.getKey());
        List<ResourceTreeDO> defines = this.defineDao.getByResourceGroupKey(next.getKey());
        Map<String, E> others = resourceNodeVisitor.visitGroupNode(next, byParent, defines);
        for (ResourceTreeGroup item : byParent) {
            it = new ResourceNode<E>(item.getKey(), NodeType.TREE_GROUP.getValue());
            if (others != null) {
                it.setOther(others.get(item.getKey()));
            }
            list.add(it);
        }
        for (ResourceTreeDO define : defines) {
            it = new ResourceNode(define.getKey(), NodeType.TREE.getValue());
            if (others != null) {
                it.setOther(others.get(define.getKey()));
            }
            list.add(it);
        }
        return list;
    }

    @Override
    public <E> ResourceNode<E> visitNode(ResourceNode<E> next, ReverseDataResourceNodeVisitor<E> resourceNodeVisitor) {
        if ("00000000-0000-0000-0000-000000000000".equals(next.getKey())) {
            resourceNodeVisitor.visitRootNode(next);
            return null;
        }
        ResourceTreeGroup group = (ResourceTreeGroup)this.groupDao.get(next.getKey());
        String parentKey = group.getParentKey();
        List<ResourceTreeGroup> groups = this.groupDao.getByParent(parentKey);
        List<ResourceTreeDO> defines = this.defineDao.getByResourceGroupKey(parentKey);
        resourceNodeVisitor.visitGoDeNode(next, groups, defines);
        return new ResourceNode(parentKey, NodeType.TREE_GROUP.getValue());
    }
}

