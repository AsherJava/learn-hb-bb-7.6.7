/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.org.impl.util.bean;

import com.jiuqi.gcreport.org.impl.util.bean.GcOrgManageModel;
import com.jiuqi.gcreport.org.impl.util.bean.GcOrgOtherModel;
import com.jiuqi.gcreport.org.impl.util.bean.GcOrgQueryModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

public class DeployGcOrgModelConfiguration {
    @Lazy(value=false)
    @Bean
    public GcOrgQueryModel initGcOrgQueryModel() {
        return new GcOrgQueryModel();
    }

    @Lazy(value=false)
    @Bean
    public GcOrgManageModel initGcOrgManageModel() {
        return new GcOrgManageModel();
    }

    @Lazy(value=false)
    @Bean
    public GcOrgOtherModel initGcOrgOtherModel() {
        return new GcOrgOtherModel();
    }
}

