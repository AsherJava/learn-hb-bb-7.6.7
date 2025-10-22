/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.Grouped
 *  com.jiuqi.nr.datascheme.api.core.VisitorResult
 */
package com.jiuqi.nr.dataresource.service.impl;

import com.jiuqi.nr.dataresource.DataResource;
import com.jiuqi.nr.dataresource.DataResourceDefine;
import com.jiuqi.nr.dataresource.DataResourceDefineGroup;
import com.jiuqi.nr.dataresource.DimAttribute;
import com.jiuqi.nr.dataresource.ResourceNode;
import com.jiuqi.nr.dataresource.dao.IDataResourceDao;
import com.jiuqi.nr.dataresource.dao.IDimAttributeDao;
import com.jiuqi.nr.dataresource.dao.IResourceLinkDao;
import com.jiuqi.nr.dataresource.loader.ResourceNodeVisitor;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.Grouped;
import com.jiuqi.nr.datascheme.api.core.VisitorResult;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DeleteNodeVisitor
implements ResourceNodeVisitor<Void> {
    private final IDataResourceDao resourceDao;
    private final IResourceLinkDao linkDao;
    private final IDimAttributeDao attributeDao;

    @Override
    public VisitorResult preVisitNode(ResourceNode<Void> ele) {
        return null;
    }

    @Override
    public Void visitRootIsDefineNode(DataResourceDefine define) {
        return null;
    }

    @Override
    public Void visitRootIsGroupNode(DataResourceDefineGroup group) {
        return null;
    }

    @Override
    public <DG extends DataResource> Map<String, Void> visitResourceDefineNode(ResourceNode<Void> ele, List<DG> dataGroups) {
        return null;
    }

    @Override
    public <DG extends DataResource, DA extends DimAttribute, DF extends DataField> Map<String, Void> visitResourceNode(ResourceNode<Void> ele, List<DG> dataGroups, List<DA> dimAttributes, List<DF> dataFields) {
        List<String> collect = dataGroups.stream().map(Grouped::getKey).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            this.resourceDao.delete(collect);
            this.linkDao.delete(collect);
        }
        return null;
    }

    @Override
    public <DG extends DataResourceDefineGroup, DR extends DataResourceDefine> Map<String, Void> visitGroupNode(ResourceNode<Void> ele, List<DG> groups, List<DR> resourceDefines) {
        return null;
    }

    public DeleteNodeVisitor(IDataResourceDao resourceDao, IResourceLinkDao linkDao, IDimAttributeDao attributeDao) {
        this.resourceDao = resourceDao;
        this.linkDao = linkDao;
        this.attributeDao = attributeDao;
    }
}

