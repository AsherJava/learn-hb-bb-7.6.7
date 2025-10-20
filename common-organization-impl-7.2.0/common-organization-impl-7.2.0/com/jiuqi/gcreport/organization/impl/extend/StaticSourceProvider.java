/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.organization.api.vo.OrgDataParam
 */
package com.jiuqi.gcreport.organization.impl.extend;

import com.jiuqi.gcreport.organization.api.vo.OrgDataParam;
import java.util.Map;

public interface StaticSourceProvider {
    public Map<String, ? extends Object> getBase64IconMap(OrgDataParam var1);
}

