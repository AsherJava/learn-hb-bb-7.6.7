/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.runner.VaMapperRunner
 */
package com.jiuqi.va.feign.config;

import com.jiuqi.va.feign.client.TenantInfoClient;
import com.jiuqi.va.mapper.runner.VaMapperRunner;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value=-2147483648)
public class MultiTenantInit
implements ApplicationRunner {
    @Value(value="${spring.datasource.multiTenant}")
    private boolean multiTenant;
    @Autowired
    private TenantInfoClient tenantInfoClient;
    @Autowired
    private VaMapperRunner mapperRunner;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!this.multiTenant) {
            return;
        }
        List<String> list = this.tenantInfoClient.nameList();
        if (list != null && !list.isEmpty()) {
            for (String name : list) {
                this.mapperRunner.sync(name);
            }
        }
    }
}

