/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryChildrenType
 */
package com.jiuqi.nr.entity.adapter.impl.basedata.auth.rule;

import com.jiuqi.nr.entity.adapter.impl.basedata.auth.rule.OrgAllChildrenBaseDataRule;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDataOption;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class OrgChildrenBaseDataRule
extends OrgAllChildrenBaseDataRule {
    @Override
    public String getName() {
        return "ORG_CHILDREN";
    }

    @Override
    public String getTitle() {
        return "\u7528\u6237\u6240\u5c5e\u673a\u6784\u7684\u76f4\u63a5\u4e0b\u7ea7\u673a\u6784\u5173\u8054\u7684\u57fa\u7840\u6570\u636e";
    }

    @Override
    protected OrgDataOption.QueryChildrenType getQueryChildrenType() {
        return OrgDataOption.QueryChildrenType.DIRECT_CHILDREN;
    }

    @Override
    protected Set<String> getParentCodes(OrgDO orgItems) {
        String parents = orgItems.getParents();
        HashSet<String> orgCodes = new HashSet<String>();
        String selfCode = orgItems.getCode();
        if (StringUtils.hasLength(parents)) {
            List<String> paths = Arrays.asList(parents.split("/"));
            String parent = null;
            if (!paths.isEmpty() && selfCode.equals(parent = paths.get(paths.size() - 1)) && paths.size() > 1) {
                parent = paths.get(paths.size() - 2);
            }
            if (parent != null) {
                orgCodes.add(parent);
            }
        }
        orgCodes.remove(selfCode);
        return orgCodes;
    }
}

