/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.authority.restapi.RestApiPermissionResourceGatherer
 */
package com.jiuqi.nvwa.authz.init;

import com.jiuqi.nvwa.authority.restapi.RestApiPermissionResourceGatherer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NvwaAuthzStarterInit {
    @Autowired
    private RestApiPermissionResourceGatherer restApiPermissionResourceGatherer;

    public void init() throws Exception {
        this.restApiPermissionResourceGatherer.run();
    }
}

