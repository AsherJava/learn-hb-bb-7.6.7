/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 */
package com.jiuqi.gcreport.org.impl.external.service;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.org.impl.external.vo.GcOrgExternalApiParam;

public interface GcOrgExternalService {
    public BusinessResponseEntity<Object> operate(GcOrgExternalApiParam var1);
}

