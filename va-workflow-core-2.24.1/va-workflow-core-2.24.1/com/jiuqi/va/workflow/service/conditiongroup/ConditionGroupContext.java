/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 */
package com.jiuqi.va.workflow.service.conditiongroup;

import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConditionGroupContext {
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    private BaseDataDefineClient baseDataDefineClient;
    @Autowired
    private OrgDataClient orgDataClient;

    public BaseDataClient getBaseDataClient() {
        return this.baseDataClient;
    }

    public BaseDataDefineClient getBaseDataDefineClient() {
        return this.baseDataDefineClient;
    }

    public OrgDataClient getOrgDataClient() {
        return this.orgDataClient;
    }
}

