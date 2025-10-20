/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 */
package com.jiuqi.va.organization.filter;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;

class BiSyntaxOrganizationFilterContext
implements IContext {
    private final OrgDTO param;
    private final OrgDO data;

    public BiSyntaxOrganizationFilterContext(OrgDTO param, OrgDO data) {
        this.param = param;
        this.data = data;
    }

    public OrgDTO getParam() {
        return this.param;
    }

    public OrgDO getData() {
        return this.data;
    }
}

