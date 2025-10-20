/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.organization.api.vo.OrgDataParam
 */
package com.jiuqi.gcreport.organization.impl.service.impl;

import com.jiuqi.gcreport.organization.api.vo.OrgDataParam;
import com.jiuqi.gcreport.organization.impl.extend.StaticSourceProvider;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class DefaultStaticSourceProvider
implements StaticSourceProvider {
    public Map<String, Object> getBase64IconMap(OrgDataParam orgDataParam) {
        return null;
    }
}

