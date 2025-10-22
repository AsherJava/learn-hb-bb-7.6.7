/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 */
package com.jiuqi.nr.dataresource.loader.impl;

import com.jiuqi.nr.dataresource.DataResource;
import com.jiuqi.nr.dataresource.DataResourceKind;
import com.jiuqi.nr.dataresource.DimAttribute;
import com.jiuqi.nr.dataresource.NodeType;
import com.jiuqi.nr.dataresource.ResourceNode;
import com.jiuqi.nr.dataresource.dao.IDataResourceDao;
import com.jiuqi.nr.dataresource.entity.DataResourceDO;
import com.jiuqi.nr.dataresource.loader.DataResourceLoaderStrategy;
import com.jiuqi.nr.dataresource.loader.ResourceNodeVisitor;
import com.jiuqi.nr.dataresource.loader.ReverseDataResourceNodeVisitor;
import com.jiuqi.nr.dataresource.service.IDataLinkService;
import com.jiuqi.nr.dataresource.service.IDataResourceService;
import com.jiuqi.nr.datascheme.api.DataField;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceLoaderStrategy
implements DataResourceLoaderStrategy {
    @Autowired
    private IDataResourceDao resourceDao;
    @Autowired
    private IDataResourceService resourceService;
    @Autowired
    private IDataLinkService linkService;

    @Override
    public boolean matching(int nodeType) {
        return ((NodeType.DIM_GROUP.getValue() | NodeType.RESOURCE_GROUP.getValue() | NodeType.MD_INFO.getValue() | NodeType.TABLE_DIM_GROUP.getValue()) & nodeType) != 0;
    }

    @Override
    public <E> void visitRoot(ResourceNode<E> root, ResourceNodeVisitor<E> resourceNodeVisitor) {
    }

    @Override
    public <E> List<ResourceNode<E>> visitNode(ResourceNode<E> next, ResourceNodeVisitor<E> resourceNodeVisitor) {
        Map<String, E> otherMap;
        String key = next.getKey();
        int nodeType = next.getType();
        ArrayList list = new ArrayList();
        List<Object> resourceDos = Collections.emptyList();
        if (nodeType == NodeType.RESOURCE_GROUP.getValue()) {
            resourceDos = this.resourceDao.getByParent(key);
            List<DataField> fields = this.linkService.getByGroupNoPeriod(key);
            otherMap = resourceNodeVisitor.visitResourceNode(next, resourceDos, null, fields);
        } else if ((nodeType & (NodeType.DIM_GROUP.getValue() | NodeType.TABLE_DIM_GROUP.getValue())) != 0) {
            List<DataResource> dimGroups = this.resourceService.getByParentKey(key);
            List<DimAttribute> list2 = this.linkService.getDimAttributeByGroup(key);
            otherMap = resourceNodeVisitor.visitResourceNode(next, dimGroups, list2, null);
        } else {
            otherMap = resourceNodeVisitor.visitResourceNode(next, resourceDos, null, null);
        }
        for (DataResourceDO dataResourceDO : resourceDos) {
            DataResourceKind resourceKind = dataResourceDO.getResourceKind();
            ResourceNode<E> it = new ResourceNode<E>(dataResourceDO.getKey(), resourceKind.getValue());
            if (otherMap != null) {
                it.setOther(otherMap.get(dataResourceDO.getKey()));
            }
            list.add(it);
        }
        return list;
    }

    @Override
    public <E> ResourceNode<E> visitNode(ResourceNode<E> next, ReverseDataResourceNodeVisitor<E> resourceNodeVisitor) {
        ResourceNode preNode;
        List<DataResourceDO> resourceDos;
        DataResourceDO resourceDO = (DataResourceDO)this.resourceDao.get(next.getKey());
        String defineKey = resourceDO.getResourceDefineKey();
        String parentKey = resourceDO.getParentKey();
        if (parentKey != null) {
            resourceDos = this.resourceDao.getByParent(parentKey);
            preNode = new ResourceNode(parentKey, NodeType.RESOURCE_GROUP.getValue());
        } else {
            resourceDos = this.resourceDao.getByParent(defineKey, null);
            preNode = new ResourceNode(defineKey, NodeType.TREE.getValue());
        }
        resourceNodeVisitor.visitGroupNode(next, resourceDos, null, null);
        return preNode;
    }
}

