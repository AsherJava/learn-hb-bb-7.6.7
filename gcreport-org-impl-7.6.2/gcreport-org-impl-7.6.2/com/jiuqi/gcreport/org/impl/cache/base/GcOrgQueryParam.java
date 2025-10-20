/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 */
package com.jiuqi.gcreport.org.impl.cache.base;

import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.impl.base.GcOrgBaseParam;
import com.jiuqi.gcreport.org.impl.cache.impl.GcOrgParam;
import com.jiuqi.gcreport.org.impl.util.base.GcOrgBaseModel;
import java.util.Date;

public class GcOrgQueryParam
extends GcOrgBaseParam {
    public GcOrgQueryParam(GcOrgBaseModel model, String orgType, Date yp, GcAuthorityType authType) throws Exception {
        super(model, orgType, yp);
        this.setAuthType(authType);
    }

    public void changeAuth(GcAuthorityType authType) {
        this.setAuthType(authType);
    }

    @Override
    public void initQueryParam(GcOrgParam param) {
        super.initQueryParam(param);
    }
}

