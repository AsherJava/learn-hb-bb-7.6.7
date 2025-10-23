/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.authority.extend.SuperInheritPathProvider
 *  com.jiuqi.nvwa.authority.resource.Resource
 */
package com.jiuqi.nr.zbquery.authority;

import com.jiuqi.nr.zbquery.bean.ZBQueryGroup;
import com.jiuqi.nr.zbquery.bean.ZBQueryInfo;
import com.jiuqi.nr.zbquery.service.ZBQueryGroupService;
import com.jiuqi.nr.zbquery.service.ZBQueryInfoService;
import com.jiuqi.nvwa.authority.extend.SuperInheritPathProvider;
import com.jiuqi.nvwa.authority.resource.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

@Deprecated
public class ZBQueryInheritPathProvider
implements SuperInheritPathProvider {
    @Autowired
    private ZBQueryGroupService zbQueryGroupService;
    @Autowired
    private ZBQueryInfoService zbQueryInfoService;

    public String getResourceCategoryId() {
        return "ZBQUERYRESOURCE_ID";
    }

    public Object getParentResource(Object resource) {
        String id = null;
        if (resource instanceof Resource) {
            id = ((Resource)resource).getId();
        } else if (resource instanceof String) {
            id = (String)resource;
        }
        if ("ZBQUERYRESOURCE_G_00000000-0000-0000-0000-000000000000".equals(id)) {
            return null;
        }
        if (StringUtils.hasText(id)) {
            if (id.indexOf("ZBQUERYRESOURCE_G_") > -1) {
                Optional<ZBQueryGroup> group = this.zbQueryGroupService.getQueryGroupById(id = id.substring("ZBQUERYRESOURCE_G_".length()));
                if (group.isPresent()) {
                    if (!StringUtils.hasText(group.get().getParentId()) || "00000000-0000-0000-0000-000000000000".equals(group.get().getParentId())) {
                        return "ZBQUERYRESOURCE_G_00000000-0000-0000-0000-000000000000";
                    }
                    return "ZBQUERYRESOURCE_G_" + group.get().getParentId();
                }
            } else {
                ZBQueryInfo info = this.zbQueryInfoService.getQueryInfoById(id = id.substring("ZBQUERYRESOURCE_I_".length()));
                if (info != null) {
                    return "ZBQUERYRESOURCE_G_" + info.getId();
                }
            }
        }
        return null;
    }

    public Set<String> computeIfChildrens(String resourceId, Set<String> resourceIds) {
        HashSet<String> res = new HashSet<String>();
        if (resourceId.indexOf("ZBQUERYRESOURCE_G_") > -1) {
            String id = resourceId.substring("ZBQUERYRESOURCE_G_".length());
            List<String> groups = this.zbQueryGroupService.getQueryGroupAllChildrenId(id);
            List<String> infos = this.zbQueryInfoService.getQueryInfoByGroups(groups);
            for (String resId : resourceIds) {
                if ((resId.indexOf("ZBQUERYRESOURCE_G_") <= -1 || !groups.contains(resId.substring("ZBQUERYRESOURCE_G_".length()))) && !infos.contains(resId.substring("ZBQUERYRESOURCE_I_".length()))) continue;
                res.add(resId);
            }
        }
        return res;
    }
}

