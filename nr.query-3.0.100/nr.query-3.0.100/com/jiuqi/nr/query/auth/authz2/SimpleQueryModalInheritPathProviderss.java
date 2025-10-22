/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.authority.extend.SuperInheritPathProvider
 *  com.jiuqi.nvwa.authority.resource.Resource
 *  org.apache.shiro.util.StringUtils
 */
package com.jiuqi.nr.query.auth.authz2;

import com.jiuqi.nr.query.auth.authz2.QueryModelResource;
import com.jiuqi.nr.query.dao.IQueryModalDefineDao;
import com.jiuqi.nr.query.dao.IQueryModalGroupDao;
import com.jiuqi.nr.query.querymodal.QueryModalDefine;
import com.jiuqi.nr.query.querymodal.QueryModalGroup;
import com.jiuqi.nvwa.authority.extend.SuperInheritPathProvider;
import com.jiuqi.nvwa.authority.resource.Resource;
import java.util.Collections;
import java.util.Set;
import org.apache.shiro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SimpleQueryModalInheritPathProviderss
implements SuperInheritPathProvider {
    static final String STRIDROOTGROUP = "b8079ac0-dc15-11e8-969b-64006a6432d8";
    @Autowired
    private IQueryModalGroupDao mGDao;
    @Autowired
    private IQueryModalDefineDao mdDao;

    public String getResourceCategoryId() {
        return "SimpleQueryModalCategory-640c7ab807d0";
    }

    public Object getParentResource(Object resource) {
        String resourceId;
        if (resource instanceof Resource) {
            resourceId = ((Resource)resource).getId();
        } else if (resource instanceof QueryModelResource) {
            resourceId = ((QueryModelResource)resource).getId();
        } else {
            throw new RuntimeException("\u67e5\u8be2\u4e0a\u7ea7\u8d44\u6e90\u9519\u8bef");
        }
        QueryModalDefine md = this.mdDao.getQueryModalDefineById(resourceId);
        if (md != null && md.getGroupId() != null) {
            QueryModelResource modelResource = new QueryModelResource();
            modelResource.setId(md.getGroupId());
            modelResource.setType("simpleQueryModelGroup");
            return modelResource;
        }
        if (STRIDROOTGROUP.equals(resourceId)) {
            return null;
        }
        QueryModalGroup queryModalGroup = this.mGDao.GetQueryModalGroupById(resourceId);
        if (queryModalGroup == null || !StringUtils.hasLength((String)queryModalGroup.getParentGroupId())) {
            return null;
        }
        QueryModelResource parent = new QueryModelResource();
        parent.setId(queryModalGroup.getParentGroupId());
        parent.setType("simpleQueryModelGroup");
        return parent;
    }

    public Set<String> computeIfChildrens(String resourceId, Set<String> resourceIds) {
        return Collections.emptySet();
    }
}

