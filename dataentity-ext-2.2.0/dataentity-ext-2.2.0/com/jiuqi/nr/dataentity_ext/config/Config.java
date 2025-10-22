/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentity_ext.config;

import com.jiuqi.nr.dataentity_ext.internal.db.EntityDataTempTable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.nr.dataentity_ext"})
public class Config {
    @Bean
    public EntityDataTempTable getEntityDataTempTable() {
        return new EntityDataTempTable(false);
    }
}

