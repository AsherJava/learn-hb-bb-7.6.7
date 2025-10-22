/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.fielddatacrud.config;

import com.jiuqi.nr.fielddatacrud.config.FieldDataProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.nr.fielddatacrud"})
@EnableConfigurationProperties(value={FieldDataProperties.class})
public class FieldDataCrudConfig {
}

