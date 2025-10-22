/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.org.impl.util.bean;

import com.jiuqi.gcreport.org.impl.util.base.GcOrgBaseModel;
import com.jiuqi.gcreport.org.impl.util.bean.GcOrgModelProvider;
import org.springframework.beans.factory.InitializingBean;

public class GcOrgQueryModel
extends GcOrgBaseModel
implements InitializingBean {
    GcOrgQueryModel() {
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        GcOrgModelProvider.setGcOrgQueryModel(this);
    }
}

