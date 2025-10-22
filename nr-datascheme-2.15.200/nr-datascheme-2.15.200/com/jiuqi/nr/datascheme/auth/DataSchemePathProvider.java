/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.core.Grouped
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataGroupKind
 *  com.jiuqi.nvwa.authority.extend.SuperInheritPathProvider
 *  com.jiuqi.nvwa.authority.resource.Resource
 */
package com.jiuqi.nr.datascheme.auth;

import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.core.Grouped;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataGroupKind;
import com.jiuqi.nr.datascheme.auth.DataSchemeAuthResource;
import com.jiuqi.nr.datascheme.auth.DataSchemeAuthResourceType;
import com.jiuqi.nr.datascheme.common.Consts;
import com.jiuqi.nvwa.authority.extend.SuperInheritPathProvider;
import com.jiuqi.nvwa.authority.resource.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class DataSchemePathProvider
implements SuperInheritPathProvider {
    @Autowired
    private IDesignDataSchemeService iDesignDataSchemeService;

    public String getResourceCategoryId() {
        return "datascheme-auth-resource-category";
    }

    public Resource getParent(Object resource) {
        String resourceId = resource instanceof Resource ? ((Resource)resource).getId() : String.valueOf(resource);
        DataSchemeAuthResourceType parseFrom = DataSchemeAuthResourceType.parseFrom(resourceId);
        String objectId = parseFrom.toObjectId(resourceId);
        String parentKey = null;
        switch (parseFrom) {
            case DATA_SCHEME_GROUP: {
                DesignDataGroup group = this.iDesignDataSchemeService.getDataGroup(objectId);
                parentKey = null == group ? null : group.getParentKey();
                break;
            }
            case DATA_SCHEME: {
                DesignDataScheme scheme = this.iDesignDataSchemeService.getDataScheme(objectId);
                parentKey = null == scheme ? null : scheme.getDataGroupKey();
                break;
            }
        }
        if (null == parentKey) {
            return null;
        }
        Object groupParent = "00000000-0000-0000-0000-000000000000".equals(parentKey) ? Consts.getDataSchemeRootGroup() : ("00000000-0000-0000-0000-111111111111".equals(parentKey) ? Consts.getQueryDataSchemeRootGroup() : this.iDesignDataSchemeService.getDataGroup(parentKey));
        return DataSchemeAuthResource.createResource(groupParent);
    }

    public Set<String> computeIfChildrens(String resourceId, Set<String> resourceIds) {
        if (CollectionUtils.isEmpty(resourceIds)) {
            return resourceIds;
        }
        DataSchemeAuthResourceType type = DataSchemeAuthResourceType.parseFrom(resourceId);
        if (DataSchemeAuthResourceType.DATA_SCHEME_GROUP != type) {
            return Collections.emptySet();
        }
        ArrayList groups = new ArrayList();
        groups.addAll(this.iDesignDataSchemeService.getDataGroupByKind(DataGroupKind.SCHEME_GROUP.getValue()));
        groups.addAll(this.iDesignDataSchemeService.getDataGroupByKind(DataGroupKind.QUERY_SCHEME_GROUP.getValue()));
        Map<String, List<DesignDataGroup>> allGroups = groups.stream().collect(Collectors.groupingBy(Grouped::getParentKey));
        Map<String, List<DesignDataScheme>> allSchemes = this.iDesignDataSchemeService.getAllDataScheme().stream().collect(Collectors.groupingBy(DataScheme::getDataGroupKey));
        String objectId = DataSchemeAuthResourceType.DATA_SCHEME_GROUP.toObjectId(resourceId);
        HashSet<String> allChildren = new HashSet<String>();
        this.getChildren(allChildren, allGroups, objectId);
        List<DesignDataScheme> schemes = allSchemes.get(objectId);
        if (!CollectionUtils.isEmpty(schemes)) {
            for (DesignDataScheme scheme : schemes) {
                allChildren.add(DataSchemeAuthResourceType.DATA_SCHEME.toResourceId(scheme.getKey()));
            }
        }
        resourceIds.retainAll(allChildren);
        return resourceIds;
    }

    private void getChildren(Set<String> children, Map<String, List<DesignDataGroup>> map, String parentKey) {
        List<DesignDataGroup> list = map.get(parentKey);
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        for (DesignDataGroup group : list) {
            children.add(DataSchemeAuthResourceType.DATA_SCHEME_GROUP.toResourceId(group.getKey()));
            this.getChildren(children, map, parentKey);
        }
    }
}

