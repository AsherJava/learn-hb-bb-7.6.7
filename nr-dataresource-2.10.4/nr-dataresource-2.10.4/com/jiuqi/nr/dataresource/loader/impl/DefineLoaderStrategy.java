/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataresource.loader.impl;

import com.jiuqi.nr.dataresource.DataResourceKind;
import com.jiuqi.nr.dataresource.NodeType;
import com.jiuqi.nr.dataresource.ResourceNode;
import com.jiuqi.nr.dataresource.dao.IDataResourceDao;
import com.jiuqi.nr.dataresource.dao.IDataResourceDefineDao;
import com.jiuqi.nr.dataresource.dao.IDataResourceDefineGroupDao;
import com.jiuqi.nr.dataresource.entity.DataResourceDO;
import com.jiuqi.nr.dataresource.entity.ResourceTreeDO;
import com.jiuqi.nr.dataresource.entity.ResourceTreeGroup;
import com.jiuqi.nr.dataresource.loader.DataResourceLoaderStrategy;
import com.jiuqi.nr.dataresource.loader.ResourceNodeVisitor;
import com.jiuqi.nr.dataresource.loader.ReverseDataResourceNodeVisitor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefineLoaderStrategy
implements DataResourceLoaderStrategy {
    @Autowired
    private IDataResourceDefineDao defineDao;
    @Autowired
    private IDataResourceDao resourceDao;
    @Autowired
    private IDataResourceDefineGroupDao groupDao;

    @Override
    public boolean matching(int nodeType) {
        return (NodeType.TREE.getValue() & nodeType) != 0;
    }

    @Override
    public <E> void visitRoot(ResourceNode<E> root, ResourceNodeVisitor<E> resourceNodeVisitor) {
        String key = root.getKey();
        ResourceTreeDO treeDO = (ResourceTreeDO)this.defineDao.get(key);
        E e = resourceNodeVisitor.visitRootIsDefineNode(treeDO);
        if (e != null) {
            root.setOther(e);
        }
    }

    @Override
    public <E> List<ResourceNode<E>> visitNode(ResourceNode<E> next, ResourceNodeVisitor<E> resourceNodeVisitor) {
        String key = next.getKey();
        ArrayList list = new ArrayList();
        List<DataResourceDO> resourceDos = this.resourceDao.getByParent(key, null);
        Map<String, E> others = resourceNodeVisitor.visitResourceDefineNode(next, resourceDos);
        for (DataResourceDO resourceDo : resourceDos) {
            DataResourceKind resourceKind = resourceDo.getResourceKind();
            ResourceNode<E> it = new ResourceNode<E>(resourceDo.getKey(), resourceKind.getValue());
            if (others != null) {
                it.setOther(others.get(resourceDo.getKey()));
            }
            list.add(it);
        }
        return list;
    }

    @Override
    public <E> ResourceNode<E> visitNode(ResourceNode<E> next, ReverseDataResourceNodeVisitor<E> resourceNodeVisitor) {
        ResourceTreeDO treeDO = (ResourceTreeDO)this.defineDao.get(next.getKey());
        String groupKey = treeDO.getGroupKey();
        List<ResourceTreeGroup> groups = this.groupDao.getByParent(groupKey);
        List<ResourceTreeDO> defines = this.defineDao.getByResourceGroupKey(groupKey);
        resourceNodeVisitor.visitGoDeNode(next, groups, defines);
        return new ResourceNode(groupKey, NodeType.TREE_GROUP.getValue());
    }
}

