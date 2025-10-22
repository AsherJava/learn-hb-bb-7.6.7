/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datawarning.config;

import com.jiuqi.nr.datawarning.service.DataWarningProvider;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages={"com.jiuqi.nr.datawarning"})
@Configuration
public class DataWarningConfig {
    public DataWarningProvider getDataWarningProvider() {
        return new DataWarningProvider();
    }
}

