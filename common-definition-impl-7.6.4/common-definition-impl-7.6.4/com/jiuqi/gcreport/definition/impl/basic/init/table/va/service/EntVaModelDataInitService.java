/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  org.apache.shiro.util.ThreadContext
 */
package com.jiuqi.gcreport.definition.impl.basic.init.table.va.service;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.definition.impl.basic.init.table.va.intf.FEntVaBaseApplicationInitialization;
import com.jiuqi.gcreport.definition.impl.basic.init.table.va.intf.FEntVaSortOrder;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import java.util.Comparator;
import java.util.List;
import org.apache.shiro.util.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class EntVaModelDataInitService {
    @Autowired(required=false)
    private List<FEntVaBaseApplicationInitialization> baseDataInitialization;

    public void init(boolean force, String tenant) {
        ThreadContext.put((Object)"SECURITY_TENANT_KEY", (Object)this.getCurrTenant(tenant));
        ThreadContext.put((Object)"NONE_AUTH_KEY", (Object)"true");
        try {
            this.initGcTable(force, tenant);
        }
        finally {
            ThreadContext.remove((Object)"SECURITY_TENANT_KEY");
            ThreadContext.remove((Object)"NONE_AUTH_KEY");
        }
    }

    private void initGcTable(boolean force, String tenant) {
        if (CollectionUtils.isEmpty(this.baseDataInitialization)) {
            return;
        }
        this.baseDataInitialization.sort(Comparator.comparing(FEntVaSortOrder::getSortOrder));
        this.baseDataInitialization.forEach(d -> {
            try {
                d.init(force, tenant);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private String getCurrTenant(String tenant) {
        NpContext context;
        String tenantId = tenant;
        if (StringUtils.isEmpty((String)tenantId) && StringUtils.isEmpty((String)(tenantId = (context = NpContextHolder.getContext()).getTenant()))) {
            tenantId = "__default_tenant__";
        }
        return tenantId;
    }
}

