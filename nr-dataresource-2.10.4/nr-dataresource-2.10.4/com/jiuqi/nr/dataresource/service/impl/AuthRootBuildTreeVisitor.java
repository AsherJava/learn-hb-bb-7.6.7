/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.privilege.service.PrivilegeService
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.VisitorResult
 *  com.jiuqi.nvwa.authority.resource.Resource
 *  com.jiuqi.nvwa.authority.resource.ResourceGroupItem
 *  com.jiuqi.nvwa.authority.resource.ResourceItem
 */
package com.jiuqi.nr.dataresource.service.impl;

import com.jiuqi.np.authz2.privilege.service.PrivilegeService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.dataresource.DataResource;
import com.jiuqi.nr.dataresource.DataResourceDefine;
import com.jiuqi.nr.dataresource.DataResourceDefineGroup;
import com.jiuqi.nr.dataresource.DataResourceKind;
import com.jiuqi.nr.dataresource.DimAttribute;
import com.jiuqi.nr.dataresource.NodeType;
import com.jiuqi.nr.dataresource.ResourceNode;
import com.jiuqi.nr.dataresource.authority.Util;
import com.jiuqi.nr.dataresource.loader.ResourceNodeVisitor;
import com.jiuqi.nr.dataresource.service.IDataResourceService;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.VisitorResult;
import com.jiuqi.nvwa.authority.resource.Resource;
import com.jiuqi.nvwa.authority.resource.ResourceGroupItem;
import com.jiuqi.nvwa.authority.resource.ResourceItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AuthRootBuildTreeVisitor
implements ResourceNodeVisitor<Void> {
    private List<Resource> list = new ArrayList<Resource>();
    private PrivilegeService privilegeService;
    protected final IDataResourceService resourceService;
    private boolean ok;
    private boolean needBreak = true;

    public AuthRootBuildTreeVisitor(PrivilegeService privilegeService, IDataResourceService resourceService) {
        this.privilegeService = privilegeService;
        this.resourceService = resourceService;
    }

    public AuthRootBuildTreeVisitor(PrivilegeService privilegeService, IDataResourceService resourceService, boolean needBreak) {
        this.privilegeService = privilegeService;
        this.resourceService = resourceService;
        this.needBreak = needBreak;
    }

    @Override
    public VisitorResult preVisitNode(ResourceNode<Void> ele) {
        if (this.needBreak && this.ok) {
            return VisitorResult.TERMINATE;
        }
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
    public <DG extends DataResourceDefineGroup, DR extends DataResourceDefine> Map<String, Void> visitGroupNode(ResourceNode<Void> ele, List<DG> groups, List<DR> resourceDefines) {
        boolean hasDelegate;
        String resourceId;
        for (DataResourceDefineGroup g : groups) {
            resourceId = Util.getResourceIdByType(g.getKey(), NodeType.TREE_GROUP.getValue());
            hasDelegate = this.privilegeService.hasDelegateAuth("DataResource_read", NpContextHolder.getContext().getIdentityId(), (Object)resourceId);
            if (!hasDelegate) continue;
            this.list.add((Resource)ResourceGroupItem.createResourceGroupItem((String)resourceId, (String)g.getTitle(), (boolean)true));
        }
        for (DataResourceDefine r : resourceDefines) {
            resourceId = Util.getResourceIdByType(r.getKey(), NodeType.TREE.getValue());
            hasDelegate = this.privilegeService.hasDelegateAuth("DataResource_read", NpContextHolder.getContext().getIdentityId(), (Object)resourceId);
            if (!hasDelegate) continue;
            this.list.add((Resource)ResourceGroupItem.createResourceGroupItem((String)resourceId, (String)r.getTitle(), (boolean)true));
        }
        this.ok = true;
        return null;
    }

    @Override
    public <DG extends DataResource> Map<String, Void> visitResourceDefineNode(ResourceNode<Void> ele, List<DG> dataGroups) {
        this.buildResourceGroup(dataGroups);
        this.ok = true;
        return null;
    }

    @Override
    public <DG extends DataResource, DA extends DimAttribute, DF extends DataField> Map<String, Void> visitResourceNode(ResourceNode<Void> ele, List<DG> dataGroups, List<DA> dimAttributes, List<DF> dataFields) {
        this.buildResourceGroup(dataGroups);
        this.ok = true;
        return null;
    }

    private <DG extends DataResource> void buildResourceGroup(List<DG> dataGroups) {
        for (DataResource g : dataGroups) {
            if (g.getResourceKind() == DataResourceKind.DIM_GROUP || g.getResourceKind() == DataResourceKind.TABLE_DIM_GROUP) continue;
            String resourceId = Util.getResourceIdByType(g.getKey(), NodeType.RESOURCE_GROUP.getValue());
            boolean hasDelegate = this.privilegeService.hasDelegateAuth("DataResource_read", NpContextHolder.getContext().getIdentityId(), (Object)resourceId);
            if (!hasDelegate) continue;
            List<DataResource> children = this.resourceService.getByParentKey(g.getKey());
            if (children.isEmpty()) {
                this.list.add((Resource)ResourceItem.createResourceItem((String)resourceId, (String)g.getTitle()));
                continue;
            }
            this.list.add((Resource)ResourceGroupItem.createResourceGroupItem((String)resourceId, (String)g.getTitle(), (boolean)true));
        }
    }

    public List<Resource> getValue() {
        return this.list;
    }
}

