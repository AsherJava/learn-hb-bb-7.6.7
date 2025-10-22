/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryChildrenType
 */
package com.jiuqi.nr.entity.adapter.impl.basedata.auth.rule;

import com.jiuqi.nr.entity.adapter.impl.basedata.auth.rule.OrgChildrenBaseDataRule;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDataOption;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class OrgSelfAndChildrenBaseDataRule
extends OrgChildrenBaseDataRule {
    @Override
    public String getName() {
        return "ORG_SELF_CHILDREN";
    }

    @Override
    public String getTitle() {
        return "\u7528\u6237\u6240\u5c5e\u673a\u6784\u53ca\u5176\u76f4\u63a5\u4e0b\u7ea7\u673a\u6784\u5173\u8054\u7684\u57fa\u7840\u6570\u636e";
    }

    @Override
    protected OrgDataOption.QueryChildrenType getQueryChildrenType() {
        return OrgDataOption.QueryChildrenType.DIRECT_CHILDREN_WITH_SELF;
    }

    @Override
    protected Set<String> getParentCodes(OrgDO orgItems) {
        Set<String> parentCodes = super.getParentCodes(orgItems);
        parentCodes.add(orgItems.getCode());
        return parentCodes;
    }
}

